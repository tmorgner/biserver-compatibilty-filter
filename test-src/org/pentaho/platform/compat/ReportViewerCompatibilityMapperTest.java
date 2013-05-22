package org.pentaho.platform.compat;

import java.util.LinkedHashMap;

import junit.framework.TestCase;

@SuppressWarnings("HardCodedStringLiteral")
public class ReportViewerCompatibilityMapperTest extends TestCase
{
  public ReportViewerCompatibilityMapperTest()
  {
  }

  public void testPrptContent()
  {
    final LinkedHashMap<String,String[]> params = new LinkedHashMap<String, String[]>();
    params.put("renderMode", new String[] {"REPORT"});
    params.put("solution", new String[] {"steel-wheels"});
    params.put("path", new String[] {});
    params.put("name", new String[] {"report.prpt"});

    final PrptCompatibilityMapper mapper = new PrptCompatibilityMapper();
    final MapperResponse handle = mapper.handle("/content/reporting/reportviewer/report.html", params);
    assertTrue(handle.isRedirect());
    assertEquals("/api/repos/:steel-wheels:report.prpt/viewer", handle.getRedirectPath());
  }
}
