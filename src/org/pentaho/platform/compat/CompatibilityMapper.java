package org.pentaho.platform.compat;

import java.util.Map;

public interface CompatibilityMapper
{
  public void setPathMapper (PathMapper pathMapper);
  public String getPattern();
  public MapperResponse handle(final String requestPath, final Map<String, String[]> parameters);
}
