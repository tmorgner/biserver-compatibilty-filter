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

  protected String computeService(final Map<String, String[]> parameters)
  {
    return "parameter";
  }
}
