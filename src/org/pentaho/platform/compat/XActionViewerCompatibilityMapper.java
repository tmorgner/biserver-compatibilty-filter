package org.pentaho.platform.compat;

import java.util.Map;

/**
 * Maps the service action ...
 *
 * Viewer: /pentaho/ViewAction?solution={0}&path={1}&action={2}
 */
public class XActionViewerCompatibilityMapper extends AbstractCompatibilityMapper
{
  public XActionViewerCompatibilityMapper()
  {
  }

  public String getPattern()
  {
    return "^/ViewAction";
  }

  protected String computeService(final Map<String, String[]> parameters)
  {
    return "viewer";
  }
}
