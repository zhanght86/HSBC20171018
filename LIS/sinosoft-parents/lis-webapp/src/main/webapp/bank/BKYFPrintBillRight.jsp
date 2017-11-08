<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：PLPsqs.jsp--打印理赔申请书程序
//程序功能：
//创建人  ：刘岩松
//创建日期：2003-02-14
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
 <%@page import="com.sinosoft.lis.f1print.*"%>
<%
  loggerDebug("BKYFPrintBillRight","开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";

  	String strBillNo = request.getParameter("BillNo");
  	String strBankCode = request.getParameter("selBankCode");
  	String strStation = request.getParameter("Station");
  	
  	PrintBillYFRightUI tPrintBillYFRightUI = new PrintBillYFRightUI();
  	loggerDebug("BKYFPrintBillRight","要打印的批单号码是＝＝＝＝"+strBillNo);
  	loggerDebug("BKYFPrintBillRight","要打印的银行代码是＝＝＝＝"+strBankCode);
  	
  	//定义一个全局变量存储赔案号

  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.addElement(strBillNo);
    tVData.addElement(strBankCode);
    tVData.addElement(strStation);
    
    //调用批单打印的类
    tPrintBillYFRightUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tPrintBillYFRightUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    loggerDebug("BKYFPrintBillRight","null");
     tError = tPrintBillYFRightUI.mErrors;
    Content = "打印失败,原因是＝＝"+tError.getFirstError();
    FlagStr = "Fail";
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	loggerDebug("BKYFPrintBillRight","put session value");
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
