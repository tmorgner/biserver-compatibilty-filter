package org.pentaho.platform.compat;

import java.util.LinkedHashMap;
import java.util.Map;

public class CdaListQueriesCompatibilityMapper extends AbstractCompatibilityMapper
{
  public CdaListQueriesCompatibilityMapper()
  {
  }

  public String getPattern()
  {
    return "^/content/cda/listQueries";
  }

  protected String getFileComponentParameter()
  {
    return "file";
  }

  public MapperResponse handle(final String requestPath, final Map<String, String[]> parameters)
  {
    final String translatedPath = getPathMapper().mapPath(computePath(parameters));
    final String cdaPath = translatedPath.replace(':', '/');

    final LinkedHashMap<String,String[]> revisedParams = new LinkedHashMap<String, String[]>(parameters);
    revisedParams.remove("solution");
    revisedParams.remove("file");
    revisedParams.put("path", new String[]{cdaPath});

    final String apiPath = "/plugin/cda/api/listQueries";// NON-NLS
    return new MapperResponse(apiPath, revisedParams);
  }
}
