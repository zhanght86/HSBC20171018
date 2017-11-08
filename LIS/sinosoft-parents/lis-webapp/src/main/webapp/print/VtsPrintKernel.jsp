<%@ page import="java.io.*"%><%@
include file="../common/jsp/Log4jUI.jsp"%><%@
page import="com.sinosoft.print.show.*"%><%@
page import="com.sinosoft.lis.pubfun.*"%><%@ 
page import="com.sinosoft.utility.*"%><%@ 
page import="com.sinosoft.service.*"%><%
try
{
	loggerDebug("VtsPrintKernel","---------------");
	GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");
	String tAppPath = application.getRealPath("/");
	loggerDebug("VtsPrintKernel","tAppPath is " + tAppPath);
	String tPicType = request.getParameter("PicType");
	InputStream tInputStream = (InputStream)session.getValue("PrintStream");
	TransferData tTransferData = (TransferData)session.getValue("TransferData");
	tTransferData.setNameAndValue("PicType", tPicType);
	
	VData tVData = new VData();
	tVData.add(tInputStream);
	tVData.add(tGlobalInput);
	tVData.add(tAppPath);
	tVData.add(tTransferData);
	
	loggerDebug("VtsPrintKernel","西悉尼系想你的了会计法上龙卷风拉开距离xxxxxxxxxxxxxxxxxxxxxxxxxx");
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if(tBusinessDelegate.submitData(tVData,"","VtsPrint"))
	{
	  	ByteArrayOutputStream tByteArrayOutputStream = (ByteArrayOutputStream)tBusinessDelegate.getResult().get(0);
	  	
	  	byte[] bArr = tByteArrayOutputStream.toByteArray();
		OutputStream ous = response.getOutputStream();
		ous.write(bArr);
		ous.flush();
		ous.close();
		out.clear();
		out = pageContext.pushBody();
		String tTempFileName = 	(String)tBusinessDelegate.getResult().get(1);
		session.putValue("TempFileName", tTempFileName);
	}
}
catch(java.net.MalformedURLException urlEx)
{
	urlEx.printStackTrace();
}
catch(java.io.IOException ioEx)
{
	ioEx.printStackTrace();
}
catch(Exception ex)
{
	ex.printStackTrace();
}
%>
