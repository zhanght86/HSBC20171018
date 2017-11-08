<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="java.io.*"%>
<%@ page import="com.sinosoft.print.show.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@ page import="com.sinosoft.service.*"%>
<%
InputStream tInputStream = (InputStream)session.getValue("PrintStream");
TransferData tTransferData = (TransferData)session.getValue("TransferData");
tTransferData.setNameAndValue("BCertifyCode", "150,150");
tTransferData.setNameAndValue("BContNo", "50,50");


String tAppPath = application.getRealPath("/");
GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");

String Content = "";
VData tVData = new VData();
tVData.add(tInputStream);
tVData.add(tGlobalInput);
tVData.add(tAppPath);
tVData.add(tTransferData);
	
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
if(tBusinessDelegate.submitData(tVData,"","PdfPrint"))
{
  	String tTempFileName = 	(String)tBusinessDelegate.getResult().get(0);
  	loggerDebug("PdfPrint","tTempFileName is "  +  tTempFileName);
	response.sendRedirect("../vtsfile/"+tTempFileName);
}
else
{
	Content ="��ӡʧ�ܣ�";
}
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
     top.close();
  </script>    
</html>
