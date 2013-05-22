package org.pentaho.platform.compat;

import java.util.LinkedHashMap;

import junit.framework.TestCase;

@SuppressWarnings("HardCodedStringLiteral")
public class PrptCompatibilityMapperTest extends TestCase
{
  public PrptCompatibilityMapperTest()
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
    final MapperResponse handle = mapper.handle("/content/reporting", params);
    assertTrue(handle.isRedirect());
    assertEquals("/api/repos/:steel-wheels:report.prpt/report", handle.getRedirectPath());
  }

  public void testPrptParameter()
  {
    final LinkedHashMap<String,String[]> params = new LinkedHashMap<String, String[]>();
    params.put("renderMode", new String[] {"PARAMETER"});
    params.put("solution", new String[] {"steel-wheels"});
    params.put("path", new String[] {});
    params.put("action", new String[] {"report.prpt"});

    final PrptCompatibilityMapper mapper = new PrptCompatibilityMapper();
    final MapperResponse handle = mapper.handle("/content/reporting", params);
    assertTrue(handle.isRedirect());
    assertEquals("/api/repos/:steel-wheels:report.prpt/parameter", handle.getRedirectPath());
  }

  public void testPrptXml()
  {
    final LinkedHashMap<String,String[]> params = new LinkedHashMap<String, String[]>();
    params.put("renderMode", new String[] {"XML"});
    params.put("solution", new String[] {"steel-wheels"});
    params.put("path", new String[] {});
    params.put("name", new String[] {"report.prpt"});

    final PrptCompatibilityMapper mapper = new PrptCompatibilityMapper();
    final MapperResponse handle = mapper.handle("/content/reporting", params);
    assertTrue(handle.isRedirect());
    assertEquals("/api/repos/:steel-wheels:report.prpt/parameter", handle.getRedirectPath());
  }

}
