package org.pentaho.platform.compat;

public class PublicPathMapper implements PathMapper
{
  public String mapPath(final String inputPath)
  {
    return ":public" + inputPath;
  }
}
