<%@ page import="java.io.*"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%@
page import="com.sinosoft.lis.f1print.AccessVtsFile"%><%
try {
	String[] strVFPathNameList = (String[])session.getValue("vfp");	
	// Get a name of VTSFile
	//����ֱ��ͨ�����������ļ�·��������Ҫ��session�ж�ȡ
	//String strVFPathName = (String)session.getValue("RealPath");	
	//session.removeAttribute("RealPath");
	String strVFPathName = null;
	for(int i=0;i<strVFPathNameList.length;i++){
	strVFPathName=strVFPathNameList[i];
	loggerDebug("F1BatchDownLoadJ1","!!!!!!!!!!!!!!!!!strVFPathName:"+strVFPathName);
	//by wangyc:get get path name from Request first,if value got is null,then get from session
	if(strVFPathName==null||strVFPathName.equals("")||strVFPathName.equals("null")){
		strVFPathName = (String)session.getValue("RealPath");	
		session.removeAttribute("RealPath");
	}
	loggerDebug("F1BatchDownLoadJ1","@@@@@@@@@@@@@@@@@strVFPathName:"+strVFPathName);
	//strVFPathName="D:/hanbin/lis6~hanbin/lis6/ui/vtsfile/6.vts";
	// Load VTS file to buffer
	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	AccessVtsFile.loadToBuffer(dataStream,strVFPathName);
	byte[] bArr = dataStream.toByteArray(); 
	loggerDebug("F1BatchDownLoadJ1","----------------3--------------"+bArr.length);

	InputStream ins = new ByteArrayInputStream(bArr);
	loggerDebug("F1BatchDownLoadJ1","==========1=======2====="+ins.available());
	
	


	com.f1j.ss.BookModelImpl bm = new com.f1j.ss.BookModelImpl();		
	if( ins != null )
	{
		// Now, reload data file from mem
		bm.read(ins, new com.f1j.ss.ReadParams());
		// Write Excel file to client
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=data.xls");
		OutputStream ous = response.getOutputStream();
					//loggerDebug("F1BatchDownLoadJ1","=================2====="+ins.available());
			
		WriteParams wp = new com.f1j.ss.WriteParams(com.f1j.ss.Constants.eFileExcel97); 
				loggerDebug("F1BatchDownLoadJ1","------1-----5");
		bm.write(ous, wp);
		
		ous.flush();
		ins.close();
		ous.close();
		bm.destroy();
	}
	else
	{
		loggerDebug("F1BatchDownLoadJ1","There is not any data stream!");
	}
	
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
