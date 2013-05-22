package org.pentaho.platform.compat;

import java.util.Collections;
import java.util.Map;

public class MapperResponse
{
  private Map<String,String[]> parameters;
  private String redirectPath;
  private boolean redirect;

  public MapperResponse(final String redirectPath)
  {
    this.redirect = (redirectPath != null);
    this.redirectPath = redirectPath;
    this.parameters = Collections.emptyMap();
  }

  public MapperResponse(final String redirectPath, final Map<String, String[]> parameters)
  {
    this(redirectPath);
    this.parameters = parameters;
  }

  public Map<String, String[]> getParameters()
  {
    return parameters;
  }

  public String getRedirectPath()
  {
    return redirectPath;
  }

  public boolean isRedirect()
  {
    return redirect;
  }
}
