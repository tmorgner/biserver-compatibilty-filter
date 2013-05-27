biserver-compatibilty-filter
============================

A compatibility filter that allows the BI-Server 5.0 to accept some of the
URLs created for a BI-Server 4.x. 

This filter handles reports (PRPT) parameter and content, XActions parameter
and content, Pentaho analyzer parameter and content as well as the CDA API
(doQuery, listQueries and listParameters).

To install the filter, copy the jar into pentaho/WEB-INF/lib and add the
following section to the web.xml file after the PentahoAwareCharacterEncodingFilter:

  <filter>
    <filter-name>compatibility-mapper</filter-name>
    <filter-class>org.pentaho.platform.compat.CompatibilityFilter</filter-class>
  </filter>

Do not forget to also add a filter-mapping, or the filter will not be called:

  <filter-mapping>
    <filter-name>compatibility-mapper</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>


To test it, install the filter and have the steel-wheels samples imported.
Then call the old style URL for a report, like this:

http://localhost:8080/pentaho/content/reporting/reportviewer/report.html?solution=steel-wheels&path=/reports&name=Inventory.prpt

This should open up the report viewer.

Or try an old style CDA url:

http://localhost:8080/pentaho/content/cda/listQueries?outputType=xml&solution=bi-developers&path=cda%2Fcdafiles&file=sql-jdbc.cda

This will return a raw XML document.
