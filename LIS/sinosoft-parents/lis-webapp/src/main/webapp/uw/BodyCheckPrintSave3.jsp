<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.print.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
String tPrintType = request.getParameter("PrintType");

loggerDebug("BodyCheckPrintSave3","--------------------start------------------" + tPrintType );

CError cError = new CError( );
boolean operFlag=true;
String tRela  = "";
String FlagStr = "";
String Content = "";
String strOperation = "";


TestPrint tTestPrint = new TestPrint();
tTestPrint.getPrintData(tPrintType);

XmlExportNew txmlExport =  (XmlExportNew)tTestPrint.getResult().get(0);

request.setAttribute("PrintStream", txmlExport.getInputStream()); 
request.getRequestDispatcher("../print/ShowPrint.jsp").forward(request,response);
%>
