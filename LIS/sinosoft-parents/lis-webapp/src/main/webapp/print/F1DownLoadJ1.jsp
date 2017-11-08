<%@ page import="java.io.*"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%
try {
	String tTempFileName = (String)session.getValue("TempFileName");	
	loggerDebug("F1DownLoadJ1","tTempFileName is " + tTempFileName);
	String tAppPath = application.getRealPath("/");
	
	File tFile = new File(tAppPath + "vtsfile/" + tTempFileName);

	FileInputStream tFIS = new FileInputStream(tFile);
	
	com.f1j.ss.BookModelImpl bm = new com.f1j.ss.BookModelImpl();		
	if( tFIS != null )
	{
		// Now, reload data file from mem
		bm.read(tFIS, new com.f1j.ss.ReadParams());
		// Write Excel file to client
		response.setContentType("application/octet-stream");
		response.setContentType("application/xls;charset=GBK"); 
		response.setHeader("CacheControl","Private");
		response.setHeader("Content-Disposition","attachment; filename=data.xls");

		OutputStream ous = response.getOutputStream();
		WriteParams wp = new com.f1j.ss.WriteParams(com.f1j.ss.Constants.eFileExcel97); 
		bm.write(ous, wp);
		ous.flush();
		tFIS.close();
		ous.close();
	}
	else
	{
		loggerDebug("F1DownLoadJ1","There is not any data stream!");
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
//session.putValue("RealPath", null);
%>
