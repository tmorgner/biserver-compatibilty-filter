package org.pentaho.platform.compat;

import java.util.LinkedHashMap;
import java.util.Map;

public class PrptCompatibilityMapper extends AbstractCompatibilityMapper
{
  public PrptCompatibilityMapper()
  {
  }

  public String getPattern()
  {
    return "^/content/reporting(!/).*";
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

  protected Map<String, String[]> computeParameters(final Map<String, String[]> parameters)
  {
    final LinkedHashMap<String,String[]> revisedParams = new LinkedHashMap<String, String[]>(parameters);
    revisedParams.remove("solution");
    revisedParams.remove("action");
    revisedParams.remove("name");
    revisedParams.remove("path");
    return revisedParams;
  }

  protected String computeService(final Map<String, String[]> parameters)
  {
    final String renderMode = lookupParameter(parameters, "renderMode");// NON-NLS
    final String service;
    if ("REPORT".equals(renderMode))// NON-NLS
    {
      service = "report";// NON-NLS
    }
    else if ("PARAMETER".equals(renderMode) ||// NON-NLS
             "XML".equals(renderMode))// NON-NLS
    {
      service = "parameter";// NON-NLS
    }
    else
    {
      service = "report"; // NON-NLS
    }
    return service;
  }
}
