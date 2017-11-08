<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="java.io.*"%>
<%@ page import="com.sinosoft.print.show.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@ page import="com.sinosoft.service.*"%>
<%
Object obj = request.getAttribute("PrintStream");
loggerDebug("ShowPrint","obj is " + application.getRealPath("/"));
loggerDebug("ShowPrint","obj.getClass().getName()   " + obj.getClass().getName());

String tStr = String.valueOf(obj);
ByteArrayInputStream tInputStream = (ByteArrayInputStream) obj;

String Content = "";
VData tVData = new VData();
tVData.add(tInputStream);
tVData.add("1");

TransferData tTransferData;
String tOutputType = "";	// 模板类型
String tOutput = "";	// 输出类型
String tLanguage = ""; // 输出语言

BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
if(tBusinessDelegate.submitData(tVData,"","PrintControl"))
{
	if(tInputStream!=null){
		tInputStream.close();//释放内存
		tInputStream = null;
	}
	tTransferData = (TransferData)tBusinessDelegate.getResult().get(0);
	tOutputType = (String) tTransferData.getValueByName("OutputType");
	tOutput = (String) tTransferData.getValueByName("Output");
	tLanguage = (String) tTransferData.getValueByName("Language");
	InputStream tOutInputStream = (InputStream)tBusinessDelegate.getResult().get(1);

	session.putValue("PrintStream", tOutInputStream);
	session.putValue("TransferData", tTransferData);
}
loggerDebug("ShowPrint","tOutputType is " + tOutputType);
// 0 - vts; 1 - pdf;
if ("0".equals(tOutputType)&&"0".equals(tOutput))
{
	// 需要制定参数，考虑中！！！！！！！！
	request.getRequestDispatcher("../print/VtsPrint.jsp").forward(request,response);
}
else if ("0".equals(tOutputType)&&"1".equals(tOutput))
{
	request.getRequestDispatcher("../print/PdfJxlPrint.jsp").forward(request,response);
}
else if ("1".equals(tOutputType)&&"1".equals(tOutput))
{
	request.getRequestDispatcher("../print/PdfPrint.jsp").forward(request,response);

}
else if ("3".equals(tOutputType))
{
	request.getRequestDispatcher("../print/XslPrint.jsp").forward(request,response);

}
else if ("4".equals(tOutput))
{//csv输出方式，直接提供文件下载
	request.getRequestDispatcher("../print/CsvPrint.jsp").forward(request,response);

}
else if ("a".equals(tOutputType))
{
	Content="该打印尚未审批通过！！打印语言："+ tLanguage;

}
else
{
	Content="该打印尚未配置相关信息！！打印语言："+ tLanguage;
}
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
     top.close();
  </script>
</html>
