<%@ page import="java.io.*"%><%@
include file="../common/jsp/Log4jUI.jsp"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%
	try {
		String strTemplatePath = application.getRealPath("f1print/MStemplate")+"/";		
		//String strTemplatePath = request.getSession().getServletContext().getResource("/").getPath()+"f1print/MStemplate/";
		//loggerDebug("F1PrintKernel","path: "+strTemplatePath);
		F1PrintParser fp = null;
		
		InputStream ins = (InputStream)session.getValue("PrintStream");
		session.removeAttribute("PrintStream");
	
		if( ins == null ) {
			XmlExport xmlExport = new XmlExport();
			xmlExport.createDocument("nofound.vts", "printer");
  		fp = new F1PrintParser(xmlExport.getInputStream(), strTemplatePath);
		} else {
			fp = new F1PrintParser(ins, strTemplatePath);					
		}

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();

		// Output VTS file to a buffer
    if( !fp.output(dataStream) ) {
      loggerDebug("F1PrintKernel","F1PrintKernel.jsp : fail to parse print data");
    }
		
		// Get a inputstream from buffer which contains VTS file need to be output
		byte[] bArr = dataStream.toByteArray();
		
		InputStream insVTS = new ByteArrayInputStream(bArr);
		OutputStream ous = response.getOutputStream();
		
		ous.write(bArr);
		ous.flush();
		ous.close();
		
		ous=null;
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody();
		
		insVTS.reset();
		
		// loggerDebug("F1PrintKernel","Save vts file for downloading ...");
		
		// Prepare data for download
	  session.putValue("F1PrintData", insVTS);
	  loggerDebug("F1PrintKernel","put data to F1PrintData");	
  }catch(java.net.MalformedURLException urlEx){
    urlEx.printStackTrace();
  }catch(java.io.IOException ioEx){
    ioEx.printStackTrace();
  }catch(Exception ex){
  	ex.printStackTrace();
  }
  
  session.removeAttribute("PrintStream");
%>
