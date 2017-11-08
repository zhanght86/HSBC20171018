<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：AutoDJAllPrint.jsp
//程序功能：实现自动垫交批量打印所有清单的打印功能
//创建人  ：刘岩松
//创建日期：2004-9-15
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.autopay.*"%>
<%
  loggerDebug("AutoDJAllPrint","开始执行AutoDJAllPrint.jsp");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  String strStartDate = request.getParameter("StartDate");
  String strEndDate = request.getParameter("EndDate");
  String strManageCom = request.getParameter("Station");
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  PrintAutoDJAllUI tPrintAutoDJAllUI = new PrintAutoDJAllUI();

  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.clear();
    tVData.addElement(strStartDate);
    tVData.addElement(strEndDate);
    tVData.addElement(strManageCom);
    tVData.addElement(tG);
    tPrintAutoDJAllUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  mResult = tPrintAutoDJAllUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("AutoDJAllPrint","null");
     tError = tPrintAutoDJAllUI.mErrors;
    Content = "打印失败,原因是没有需要打印的数据信息！";
    FlagStr = "Fail";
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	loggerDebug("AutoDJAllPrint","put session value");
  	response.sendRedirect("../f1print/GetF1Print.jsp");
  }
  %>
  <html>
  <script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
