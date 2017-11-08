<%@include file="../common/jsp/Log4jUI.jsp"%> 
<%@ page import="java.io.*"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%
	try {
	  /*
	   * Kevin 2003-03-13
	   * 
	   * Note: 
	   * 1. First, get data stream ( a VTS file ) from session;
	   * 2. Then, store data stream to a temp file as FormulaOne type;
	   * 3. new a instance of BookModelImpl, read VTS file from temp file;
	   * 4. write content of this file as Excel type to response object;
	   *
	   * If you skip step 2 and 3, there will be an exception. I don't know why :-)
	   */
		InputStream ins = (InputStream)session.getValue("F1PrintData");
		session.removeAttribute("F1PrintData");
		
		com.f1j.ss.BookModelImpl bm = new com.f1j.ss.BookModelImpl();
		
		if( ins != null ) {
			// Now, reload data file from mem
			bm.read(ins, new com.f1j.ss.ReadParams());

			// Write Excel file to client
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=data.xls");
			
			OutputStream os = response.getOutputStream();
			
			bm.write(os, new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
		  
			os.flush();
			
		} else {
			loggerDebug("F1DownLoad","There is not any data stream!");
		}

  }catch(java.net.MalformedURLException urlEx){
    urlEx.printStackTrace();
  }catch(java.io.IOException ioEx){
    ioEx.printStackTrace();
  }catch(Exception ex){
  	ex.printStackTrace();
  }
  
  session.putValue("F1PrintData", null);
%>
