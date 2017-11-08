<%@ page import="java.io.*"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%
	try {
		String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
		F1PrintParser fp = null;

		InputStream ins = (InputStream)session.getValue("PrintStream");
		session.removeAttribute("PrintStream");

		if( ins == null ) {
			XmlExport xmlExport = new XmlExport();

			xmlExport.createDocument("new.vts", "printer");

  		fp = new F1PrintParser(xmlExport.getInputStream(), strTemplatePath);
		} else {
			fp = new F1PrintParser(ins, strTemplatePath);
		}

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		int nChar = -1;

		// Output VTS file to a buffer
    if( !fp.output(dataStream) ) {
      loggerDebug("F1PrintKernel","F1PrintKernel.jsp : fail to parse print data");
    }

		// Get a inputstream from buffer which contains VTS file need to be output
		InputStream insVTS = new ByteArrayInputStream(dataStream.toByteArray());
		OutputStream ous = response.getOutputStream();

		while( ( nChar = insVTS.read() ) != -1 ) {
		  ous.write(nChar);
		}

		ous.flush();

		insVTS.reset();

		// loggerDebug("F1PrintKernel","Save vts file for downloading ...");

		// Prepare data for download
	  session.putValue("F1PrintData", insVTS);

  }catch(java.net.MalformedURLException urlEx){
    urlEx.printStackTrace();
  }catch(java.io.IOException ioEx){
    ioEx.printStackTrace();
  }catch(Exception ex){
  	ex.printStackTrace();
  }

  session.removeAttribute("PrintStream");
%>
