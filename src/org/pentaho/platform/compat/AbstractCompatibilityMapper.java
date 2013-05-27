package org.pentaho.platform.compat;

import java.util.Map;

public abstract class AbstractCompatibilityMapper implements CompatibilityMapper
{
  private PathMapper pathMapper;

  protected AbstractCompatibilityMapper()
  {
    pathMapper = new PublicPathMapper();
  }

  public PathMapper getPathMapper()
  {
    return pathMapper;
  }

  public void setPathMapper(final PathMapper pathMapper)
  {
    if (pathMapper == null)
    {
      throw new NullPointerException();
    }
    this.pathMapper = pathMapper;
  }

  public MapperResponse handle(final String requestPath, final Map<String, String[]> parameters)
  {
    final String translatedPath = pathMapper.mapPath(computePath(parameters));
    final String service = computeService(parameters);
    final String apiPath = String.format("/api/repos/%s/%s", translatedPath, service);// NON-NLS

    return new MapperResponse(apiPath, parameters);
  }

  protected String computeService(final Map<String, String[]> parameters)
  {
    return "viewer"; // NON-NLS
  }

  protected String getFileComponentParameter()
  {
    return "action";
  }

  protected String computePath(final Map<String, String[]> parameters)
  {
    final String solution = lookupParameter(parameters, "solution"); // NON-NLS
    final String path = lookupParameter(parameters, "path");// NON-NLS
    final String name = lookupParameter(parameters, getFileComponentParameter());// NON-NLS
    return translatePath(solution, path, name);
  }

  protected String translatePath(final String solution, final String path, final String name)
  {
    final StringBuilder b = new StringBuilder();
    MappingHelper.appendConditionally(solution, b);
    MappingHelper.appendConditionally(path, b);
    MappingHelper.appendConditionally(name, b);
    return b.toString().replace('/', ':');
  }

  protected String lookupParameter(final Map<String, String[]> parameter, final String parameterName)
  {
    final String[] o = parameter.get(parameterName);
    if (o == null)
    {
      return null;
    }
    if (o.length == 0)
    {
      return null;
    }
    return o[0];
  }

}
