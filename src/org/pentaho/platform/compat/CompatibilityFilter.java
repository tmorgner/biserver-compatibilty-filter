package org.pentaho.platform.compat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CompatibilityFilter implements Filter
{
  private static final Log logger = LogFactory.getLog(CompatibilityFilter.class);
  private LinkedHashMap<Pattern, CompatibilityMapper> handlers;
  private PathMapper pathMapper;

  public CompatibilityFilter()
  {
    pathMapper = new PublicPathMapper();
    handlers = new LinkedHashMap<Pattern, CompatibilityMapper>();
  }

  public void addHandler(final Class<? extends CompatibilityMapper> handlerClass)
  {
    try
    {
      final CompatibilityMapper compatibilityMapper = handlerClass.newInstance();
      compatibilityMapper.setPathMapper(pathMapper);
      handlers.put(Pattern.compile(compatibilityMapper.getPattern()), compatibilityMapper);
    }
    catch (Exception e)
    {
      logger.warn("Unable to add handler of type " + handlerClass, e); // NON-NLS
    }
  }

  public void init(final FilterConfig filterConfig) throws ServletException
  {
    final Enumeration<String> initParameterNames = filterConfig.getInitParameterNames();
    final ArrayList<String> names = new ArrayList<String>();
    while (initParameterNames.hasMoreElements())
    {
      names.add(initParameterNames.nextElement());
    }
    Collections.sort(names);

    for (int i = 0; i < names.size(); i++)
    {
      final String paramName = names.get(i);
      if (paramName.startsWith("handler.")) // NON-NLS
      {
        final String initParameter = filterConfig.getInitParameter(paramName);
        try
        {
          final Class<?> aClass = Class.forName(initParameter);
          if (CompatibilityMapper.class.isAssignableFrom(aClass))
          {
            //noinspection unchecked
            addHandler((Class<? extends CompatibilityMapper>) aClass);
          }
        }
        catch (ClassNotFoundException e)
        {
          logger.warn(String.format
              ("Unable to load handler for parameter %s (value is %s)", paramName, initParameter), e); // NON-NLS
        }
      }
    }

    if (handlers.isEmpty())
    {
      addHandler(XActionParameterCompatibilityMapper.class);
      addHandler(XActionViewerCompatibilityMapper.class);
      addHandler(ReportViewerCompatibilityMapper.class);
      addHandler(PrptCompatibilityMapper.class);
    }
  }

  public void doFilter(final ServletRequest servletRequest,
                       final ServletResponse servletResponse,
                       final FilterChain filterChain) throws IOException, ServletException
  {
    if (handleLegacyRequest(servletRequest, servletResponse))
    {
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean handleLegacyRequest(final ServletRequest servletRequest,
                                      final ServletResponse servletResponse) throws UnsupportedEncodingException
  {
    if (servletRequest instanceof HttpServletRequest == false)
    {
      return false;
    }
    final HttpServletRequest request = (HttpServletRequest) servletRequest;
    if (servletResponse instanceof HttpServletResponse == false)
    {
      return false;
    }
    final HttpServletResponse response = (HttpServletResponse) servletResponse;

    final String path = computePath(request);
    for (final Map.Entry<Pattern, CompatibilityMapper> entry : handlers.entrySet())
    {
      final Pattern p = entry.getKey();
      if (p.matcher(path).matches())
      {
        final CompatibilityMapper value = entry.getValue();
        final MapperResponse mappingResponse = value.handle(path, request.getParameterMap());
        if (mappingResponse.isRedirect())
        {
          handleRedirect(request, response, mappingResponse);
          return true;
        }
      }
    }
    return false;
  }

  private void handleRedirect(final HttpServletRequest request,
                              final HttpServletResponse response,
                              final MapperResponse mappingResponse) throws UnsupportedEncodingException
  {
    final String fullPath = MappingHelper.computeRedirectPath(request, mappingResponse);
    response.setStatus(301);
    response.setHeader("Location", fullPath); // NON-NLS
    response.setHeader("Connection", "close"); // NON-NLS
  }


  public static String computePath(final HttpServletRequest r)
  {
    final String servletPath = r.getServletPath();
    final String pathInfo = r.getPathInfo();
    if (pathInfo == null)
    {
      return servletPath;
    }
    else
    {
      return servletPath + pathInfo;
    }
  }

  public void destroy()
  {

  }
}
