<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%><%@
include file="../common/jsp/Log4jUI.jsp"%>
<%
String tTempleteID = request.getParameter("TempleteID");
loggerDebug("LPrtPrintTestSave",tTempleteID);
String tBusiName = "CreateXMLFile";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
TransferData tTransferData = new TransferData();
XmlExportNew tXmlExportNew = new XmlExportNew();
//HashMap tMap = new HashMap();
ArrayList tArr = new ArrayList();//存放标签
String Content ="";
InputStream tInput;
Enumeration paramNames = request.getParameterNames();
while (paramNames.hasMoreElements())
{
	String paramName = (String) paramNames.nextElement();

	String[] paramValues = request.getParameterValues(paramName);
	if (paramValues.length == 1)
	{
		String paramValue = paramValues[0];
		//tMap.put(paramName,paramValue);
		loggerDebug("LPrtPrintTestSave","参数：" + paramName + "&"+ 0+"&" + paramValue);
		tArr.add(paramName + "&"+0+"&" +paramValue);
	}
	else
	{
		for(int i=0;i<paramValues.length;i++)
		{
			String paramValue = paramValues[i];
			tArr.add(paramName + "&"+(i+1)+"&" +paramValue);
			loggerDebug("LPrtPrintTestSave","参数：" +paramName + "&" + (i+1)+ "&" + paramValue);
		}
	}
}
tTransferData.setNameAndValue("ArrayList",tArr);
tTransferData.setNameAndValue("TempleteID",tTempleteID);
VData tVData=new VData();
tVData.addElement(tTransferData);

if(!tBusinessDelegate.submitData(tVData,"",tBusiName))
{
	if(tBusinessDelegate.getCErrors()!=null && tBusinessDelegate.getCErrors().getErrorCount()>0)
	{
		Content = "xml文件生成失败，原因是" + tBusinessDelegate.getCErrors().getFirstError();
	}
	else
	{
		Content = "xml文件生成失败！";
	}
}
else
{
	tXmlExportNew = (XmlExportNew)tBusinessDelegate.getResult().get(0);
	tInput = tXmlExportNew.getInputStream();
	request.setAttribute("PrintStream", tInput);
	request.getRequestDispatcher("../print/ShowPrintTest.jsp").forward(request,response);
}
/*try
	{
		if( tInput != null )
		{
			// 把数据解析xml文件
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename= 1.xml");
			OutputStream tOutput = response.getOutputStream();
			int start;
			byte[] tBuf = new byte[1024];
			while((start=tInput.read(tBuf))!= -1)
			{
				tOutput.write(tBuf,0,start);
			}
			tOutput.flush();
			tOutput.close();
			tOutput=null;
			response.flushBuffer();
			out.clear();
			out = pageContext.pushBody();
			tInput.close();
		}
		else
		{
			Content = "导出失败！";
		}
	}
	catch(Exception ex)
	{
		if( tInput != null )
		{
			tInput.close();
		}
		Content = "导出失败！";
	}
}
*/
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
  </script>
</html>
