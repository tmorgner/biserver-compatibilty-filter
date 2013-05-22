package org.pentaho.platform.compat;

import java.util.Map;

public class ReportViewerCompatibilityMapper extends AbstractCompatibilityMapper
{
  public ReportViewerCompatibilityMapper()
  {
  }

  public String getPattern()
  {
    return "^/content/reporting/reportviewer/report.html.*";
  }

  protected String computePath(final Map<String, String[]> parameters)
  {
    final String solution = lookupParameter(parameters, "solution"); // NON-NLS
    final String path = lookupParameter(parameters, "path");// NON-NLS
    String name = lookupParameter(parameters, "name");// NON-NLS
    if (name == null)
    {
      name = lookupParameter(parameters, "action");// NON-NLS
    }
    return translatePath(solution, path, name);
  }
}
