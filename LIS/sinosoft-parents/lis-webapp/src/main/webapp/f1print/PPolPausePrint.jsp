<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：需求保费银行划账成功清单通知书
//程序功能：
//创建人  ：刘岩松
//创建日期：2004-5-28
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
  System.out.println("开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
	String strMainPolNo = request.getParameter("MainPolNo"); 	
	PrintPolPauseUI tPrintPolPauseUI = new PrintPolPauseUI();
  System.out.println("交费通知书号码是"+strMainPolNo)	;
  VData tVData = new VData();
  VData mResult = new VData();
  try
  {
    tVData.addElement(strMainPolNo);
    
    tVData.addElement(tG);
    //调用批单打印的类
    tPrintPolPauseUI.submitData(tVData,"PRINT");
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  mResult = tPrintPolPauseUI.getResult();
  XmlExport txmlExport = new XmlExport();
  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
  if (txmlExport==null)
  {
    System.out.println("null");
    tError = tPrintPolPauseUI.mErrors;
    Content = "打印失败,原因是没有要打印的数据";
    FlagStr = "Fail";
  }
  else
  {
  	session.putValue("PrintStream", txmlExport.getInputStream());
  	System.out.println("put session value");
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