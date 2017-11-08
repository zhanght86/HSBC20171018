<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="java.io.*"%>
<%@ page import="com.sinosoft.print.show.pdf.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@ page import="com.sinosoft.service.*"%>
<%
InputStream tInputStream = (InputStream)session.getValue("PrintStream");
TransferData tTransferData = (TransferData)session.getValue("TransferData");

String tAppPath = application.getRealPath("/");
GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");

String Content = "";
VData tVData = new VData();
tVData.add(tInputStream);
tVData.add(tGlobalInput);
tVData.add(tAppPath);
tVData.add(tTransferData);

BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
if(tBusinessDelegate.submitData(tVData,"","CsvPrint"))
{
	String tTempFileName = 	(String)tBusinessDelegate.getResult().get(0);
	loggerDebug("PdfJxlPrint","tTempFileName is "  +  tTempFileName);
	request.setAttribute("FilePath",tAppPath + "/vtsfile/"+tTempFileName);
	request.setAttribute("FileName",tTempFileName);
	request.setAttribute("FileType","csv");
	request.getRequestDispatcher("../common/jsp/FileDownload.jsp").forward(request, response);//调用通用下载页面
}
else
{
	Content ="打印失败！";
}
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
     top.close();
  </script>
</html>
