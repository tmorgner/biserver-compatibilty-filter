package org.pentaho.platform.compat;

import java.util.Map;

/**
 * Maps the service action ...
 *
 * Viewer: /pentaho/ViewAction?solution={0}&path={1}&action={2}
 */
public class AnalyzerCompatibilityMapper extends AbstractCompatibilityMapper
{
  public AnalyzerCompatibilityMapper()
  {
  }

  public String getPattern()
  {
    return "^/content/analyzer/editor\\?.*";
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
    if ("open".equals(this.lookupParameter(parameters, "command")))
    {
      if ("true".equals(this.lookupParameter(parameters, "edit")))
      {
        // editor mode
        return "editor";
      }
      // viewer mode
      return "viewer";
    }
    return "viewer";
  }
}
