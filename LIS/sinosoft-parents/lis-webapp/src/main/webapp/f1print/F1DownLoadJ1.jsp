<%@include file="../common/jsp/Log4jUI.jsp"%> 
<%@ page import="java.io.*"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%@
page import="com.sinosoft.lis.f1print.AccessVtsFile"%><%
try {
	// Get a name of VTSFile
	//现在直接通过参数传递文件路径，不需要从session中读取
	//String strVFPathName = (String)session.getValue("RealPath");	
	//session.removeAttribute("RealPath");
	String strVFPathName = request.getParameter("RealPath");
	loggerDebug("F1DownLoadJ1","!!!!!!!!!!!!!!!!!strVFPathName:"+strVFPathName);
	//by wangyc:get get path name from Request first,if value got is null,then get from session
	if(strVFPathName==null||strVFPathName.equals("")||strVFPathName.equals("null")){
		strVFPathName = (String)session.getValue("RealPath");	
		session.removeAttribute("RealPath");
	}
	loggerDebug("F1DownLoadJ1","@@@@@@@@@@@@@@@@@strVFPathName:"+strVFPathName);
	//strVFPathName="D:/hanbin/lis6~hanbin/lis6/ui/vtsfile/6.vts";
	// Load VTS file to buffer
	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	AccessVtsFile.loadToBuffer(dataStream,strVFPathName);
	byte[] bArr = dataStream.toByteArray(); 
	loggerDebug("F1DownLoadJ1","----------------3--------------"+bArr.length);

	InputStream ins = new ByteArrayInputStream(bArr);
	loggerDebug("F1DownLoadJ1","==========1=======2====="+ins.available());

	com.f1j.ss.BookModelImpl bm = new com.f1j.ss.BookModelImpl();		
	if( ins != null )
	{
		// Now, reload data file from mem
		bm.read(ins, new com.f1j.ss.ReadParams());
		// Write Excel file to client
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=data.xls");
		OutputStream ous = response.getOutputStream();
					//loggerDebug("F1DownLoadJ1","=================2====="+ins.available());
			
		WriteParams wp = new com.f1j.ss.WriteParams(com.f1j.ss.Constants.eFileExcel97); 
				loggerDebug("F1DownLoadJ1","------1-----5");

		bm.write(ous, wp);

		ous.flush();
		ins.close();
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
