<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>
<%@page import="java.util.*" %>
<%
LPrtXmlStyleSchema tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
XmlExportNew tXmlExportNew = new XmlExportNew();
String tTempleteID = request.getParameter("TempleteID");
String tBusiName = "LPrtXmlStyleUI";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
GlobalInput tG = new GlobalInput(); 
tG=(GlobalInput)session.getValue("GI");
CErrors tError =null;
String transact ="";
String Content ="";
String tFileName ="";//导出文件名
InputStream tInput;
tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
VData tVData=new VData();
tVData.addElement(tLPrtXmlStyleSchema);
tVData.addElement(tG);
tVData.addElement(transact);
if(!tBusinessDelegate.submitData(tVData,transact,tBusiName))
{
	if(tBusinessDelegate.getCErrors()!=null && tBusinessDelegate.getCErrors().getErrorCount()>0)
	{
		Content = "导出失败，原因是" + tBusinessDelegate.getCErrors().getFirstError();
	}
	else
	{
		Content = "导出失败！";
	}	
}
else
{
	tXmlExportNew = (XmlExportNew)tBusinessDelegate.getResult().get(0);
	tFileName = (String)tBusinessDelegate.getResult().get(1);
	tInput = tXmlExportNew.getInputStream();
	try 
	{
		if( tInput != null )
		{
			// 把数据解析xml文件
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+tFileName+".xml");
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
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
  </script>    
</html>
