<%@ page import="java.io.*"%><%@
include file="../common/jsp/Log4jUI.jsp"%><%@ 
page import="com.sinosoft.lis.f1print.AccessVtsFile"%><%@ 
page import="com.sinosoft.utility.*"%><%
loggerDebug("F1PrintKernelJ1","==================================================================dfdfdfff");
try
{
	loggerDebug("F1PrintKernelJ1","=========================");
	ByteArrayOutputStream dataStream;
	InputStream ins = (InputStream)session.getValue("PrintStream");
	if(ins!=null){
			loggerDebug("F1PrintKernelJ1","========================="+ins);
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		F1PrintParser fp = null;

		ins = (InputStream)session.getValue("PrintStream");
		session.removeAttribute("PrintStream");

		if( ins == null ) {
			XmlExport xmlExport = new XmlExport();

			xmlExport.createDocument("new.vts", "printer");

  		fp = new F1PrintParser(xmlExport.getInputStream(), strTemplatePath);
		} else {
			fp = new F1PrintParser(ins, strTemplatePath);
		}

		dataStream = new ByteArrayOutputStream();
		// Output VTS file to a buffer
    if( !fp.output(dataStream) ) {
      loggerDebug("F1PrintKernelJ1","F1PrintKernel.jsp : fail to parse print data");
    }
	}
	else{
		String strVFPathName = request.getParameter("RealPath");
				loggerDebug("F1PrintKernelJ1","strVFPathName==="+strVFPathName);
		if(strVFPathName==null||strVFPathName.equals("")||strVFPathName.equals("null")){
			strVFPathName = (String)session.getValue("RealPath");	
		}
		loggerDebug("F1PrintKernelJ1","strVFPathName==="+strVFPathName);
		dataStream = new ByteArrayOutputStream();
		// Load VTS file to buffer
		//strVFPathName="e:\\ui\\f1print\\MStemplate\\new.vts";
		AccessVtsFile.loadToBuffer(dataStream,strVFPathName);
		loggerDebug("F1PrintKernelJ1","==> Read VTS file from disk ");
		// Put a stream from buffer which contains VTS file to client
	}
	byte[] bArr = dataStream.toByteArray();
	OutputStream ous = response.getOutputStream();
	ous.write(bArr);
	ous.flush();
	
	ous.close();
	ous=null;
	response.flushBuffer();
	out.clear();
	out = pageContext.pushBody();
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
