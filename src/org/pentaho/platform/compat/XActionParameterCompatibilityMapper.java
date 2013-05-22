package org.pentaho.platform.compat;

import java.util.Map;

/**
 * Maps the service action ...
 *
 * Parameter-service: /pentaho/ServiceAction?solution={0}&path={1}&action={2}&component=xaction-parameter
 */
public class XActionParameterCompatibilityMapper extends AbstractCompatibilityMapper
{
  public XActionParameterCompatibilityMapper()
  {
  }

  public String getPattern()
  {
    return "^/ServiceAction\\?*.component=xaction-parameter.*";
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

  protected String computeService(final Map<String, String[]> parameters)
  {
    return "parameter";
  }
}
