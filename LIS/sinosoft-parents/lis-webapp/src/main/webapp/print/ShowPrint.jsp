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
String tOutputType = "";	// ģ������
String tOutput = "";	// �������
String tLanguage = ""; // �������

BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
if(tBusinessDelegate.submitData(tVData,"","PrintControl"))
{
	if(tInputStream!=null){
		tInputStream.close();//�ͷ��ڴ�
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
	// ��Ҫ�ƶ������������У���������������
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
{//csv�����ʽ��ֱ���ṩ�ļ�����
	request.getRequestDispatcher("../print/CsvPrint.jsp").forward(request,response);

}
else if ("a".equals(tOutputType))
{
	Content="�ô�ӡ��δ����ͨ��������ӡ���ԣ�"+ tLanguage;

}
else
{
	Content="�ô�ӡ��δ���������Ϣ������ӡ���ԣ�"+ tLanguage;
}
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
     top.close();
  </script>
</html>
