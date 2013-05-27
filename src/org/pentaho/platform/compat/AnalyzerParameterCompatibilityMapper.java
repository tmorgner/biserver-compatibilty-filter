package org.pentaho.platform.compat;

import java.util.Map;

/**
 * Maps the service action ...
 *
 * Viewer: /pentaho/ViewAction?solution={0}&path={1}&action={2}
 */
public class AnalyzerParameterCompatibilityMapper extends AbstractCompatibilityMapper
{
  public AnalyzerParameterCompatibilityMapper()
  {
  }

  public String getPattern()
  {
    return "^/content/analyzer/parameters";
  }

  protected String computeService(final Map<String, String[]> parameters)
  {
    return "parameter";
  }
}
