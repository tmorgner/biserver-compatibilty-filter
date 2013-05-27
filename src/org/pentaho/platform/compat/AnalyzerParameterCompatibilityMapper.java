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
    return "^/content/analyzer/parameters\\?.*";
  }

  protected String computePath(final Map<String, String[]> parameters)
  {
    final String solution = lookupParameter(parameters, "solution"); // NON-NLS
    final String path = lookupParameter(parameters, "path");// NON-NLS
    final String name = lookupParameter(parameters, "action");// NON-NLS
    return translatePath(solution, path, name);
  }

  protected String computeService(final Map<String, String[]> parameters)
  {
    return "parameter";
  }
}
