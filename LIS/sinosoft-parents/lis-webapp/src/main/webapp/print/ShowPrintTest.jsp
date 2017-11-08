<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="java.io.*"%>
<%@ page import="com.sinosoft.print.show.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@ page import="com.sinosoft.service.*"%>
<%
Object obj = request.getAttribute("PrintStream");
loggerDebug("ShowPrintTest","obj is " + application.getRealPath("/"));
loggerDebug("ShowPrintTest","obj.getClass().getName()   " + obj.getClass().getName());

String tStr = String.valueOf(obj);
ByteArrayInputStream tInputStream = (ByteArrayInputStream) obj;

String Content = "";
//测试的标志
String Type ="2";
VData tVData = new VData();
tVData.add(tInputStream);
tVData.add(Type);

TransferData tTransferData;
String tOutputType = "";	// 模板类型
String tOutput = "";	// 输出类型

BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
if(tBusinessDelegate.submitData(tVData,"","PrintControl"))
{
	tTransferData = (TransferData)tBusinessDelegate.getResult().get(0);
	tOutputType = (String) tTransferData.getValueByName("OutputType");
	tOutput = (String) tTransferData.getValueByName("Output");
	InputStream tOutInputStream = (InputStream)tBusinessDelegate.getResult().get(1);

	session.putValue("PrintStream", tOutInputStream);
	session.putValue("TransferData", tTransferData);
}
loggerDebug("ShowPrintTest","tOutputType is " + tOutputType);
loggerDebug("ShowPrintTest","tOutput is " + tOutput);
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
	Content="该打印尚未审批通过！！";

}
else
{
	Content="该打印尚未配置相关信息！！";
}
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
     top.close();
  </script>
</html>
