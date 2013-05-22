package org.pentaho.platform.compat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class MappingHelper
{
  public static void appendConditionally(final String path, final StringBuilder b)
  {
    if (path != null && path.length() > 0)
    {
      if (path.startsWith("/"))
      {
        b.append(path);
      }
      else
      {
        b.append("/");
        b.append(path);
      }
    }
  }

  public static String computeRedirectPath(final HttpServletRequest request,
                                           final MapperResponse mappingResponse) throws UnsupportedEncodingException
  {
    final StringBuilder fullPath = new StringBuilder();
    appendPath(request, mappingResponse, fullPath);
    if ("POST".equals(request.getMethod()) == false)    // NON-NLS
    {
      appendParameter(mappingResponse, fullPath);
    }
    else
    {
      // append the query string as it is.
      fullPath.append(request.getQueryString());
    }
    return fullPath.toString();
  }

  private static void appendPath(final HttpServletRequest request,
                                 final MapperResponse mappingResponse,
                                 final StringBuilder fullPath)
  {
    final String contextPath = request.getContextPath();
    if (contextPath != null && "".equals(contextPath) == false)
    {
      MappingHelper.appendConditionally(contextPath, fullPath);
    }
    MappingHelper.appendConditionally(mappingResponse.getRedirectPath(), fullPath);
  }

  public static void appendParameter(final MapperResponse mappingResponse,
                                     final StringBuilder fullPath) throws UnsupportedEncodingException
  {
    final Map<String, String[]> parameters = mappingResponse.getParameters();
    if (parameters.isEmpty() == false)
    {
      boolean first = true;
      for (final Map.Entry<String, String[]> entries : parameters.entrySet())
      {
        final String key = entries.getKey();
        final String[] values = entries.getValue();
        for (int i = 0; i < values.length; i++)
        {
          final String value = values[i];
          if (first)
          {
            fullPath.append("?");
            first = false;
          }
          else
          {
            fullPath.append("&");
          }

          fullPath.append(URLEncoder.encode(key, "UTF-8"));
          fullPath.append("=");
          fullPath.append(URLEncoder.encode(value, "UTF-8"));
        }
      }
    }
  }
}
