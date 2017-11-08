<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：AutoDJPrint.jsp
//程序功能：实现自动垫交打印的功能
//创建人  ：刘岩松
//创建日期：2004-5-20
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
  loggerDebug("AutoDJPrint","开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  String strEdorNo = request.getParameter("EdorNo");
  loggerDebug("AutoDJPrint","交费通知书号码是"+strEdorNo);
  String strDate = request.getParameter("Date");
  loggerDebug("AutoDJPrint","借款日期是"+strDate);
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  PrintAutoDJUI tPrintAutoDJUI = new PrintAutoDJUI();

  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.clear();
    tVData.addElement(strEdorNo);
    tVData.addElement(strDate);
    tVData.addElement(tG);
    tPrintAutoDJUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  mResult = tPrintAutoDJUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("AutoDJPrint","null");
     tError = tPrintAutoDJUI.mErrors;
    Content = "打印失败,原因是没有需要打印的数据信息！";
    FlagStr = "Fail";
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	loggerDebug("AutoDJPrint","put session value");
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
