<%@ page import="java.io.*"%><%@
include file="../common/jsp/Log4jUI.jsp"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%
FileInputStream tFin = null;
String Content = "";
String tFilePath = request.getParameter("FilePath");
//截取文件名
String tFileName = tFilePath.substring(tFilePath.lastIndexOf("/")+1,tFilePath.lastIndexOf("."));
//获得文件输出类型名
String tOutputTypeName = tFilePath.substring(tFilePath.lastIndexOf(".")+1);
//应用根路路径
String tPath = application.getRealPath("").replace('\\','/');
if(!tPath.endsWith("/"))
{
	tPath += "/";
}
File tFile = new File(tPath+tFilePath);
loggerDebug("LPrtTemDownloadSave","下载文件="+tPath+tFilePath);
try 
{
	tFin = new FileInputStream(tFile);
	if( tFin != null )
	{
		loggerDebug("LPrtTemDownloadSave","tFin="+tFin+tOutputTypeName);
		// 从文件流中读取数据
		if(tOutputTypeName.equals("vts"))
		{
			// 把数据解析成EXCEL文件
			com.f1j.ss.BookModelImpl tFbm = new com.f1j.ss.BookModelImpl();
			tFbm.read(tFin, new com.f1j.ss.ReadParams());
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename="+tFileName+".xls");
			OutputStream tFos = response.getOutputStream();
			WriteParams tFwp = new com.f1j.ss.WriteParams(com.f1j.ss.Constants.eFileExcel97);
			tFbm.write(tFos, tFwp);
			tFos.flush();
			tFos.close();
		}
		else
		{
			// 把数据解析成其他格式文件
			response.reset();
			tFileName =tFileName +"."+ tOutputTypeName;
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+tFileName);
			OutputStream tFou = response.getOutputStream();
			int start;
			byte[] tBuf = new byte[1024];
			while((start=tFin.read(tBuf))!= -1)
			{
				tFou.write(tBuf,0,start);
			}
			tFou.flush();
			tFou.close();
		}
		response.flushBuffer();   
		out.clear();   
		out = pageContext.pushBody();   
		tFin.close();
	}
	else
	{
		Content = "文件不存在！";
	}
}
catch(Exception ex)
{
	try
	{
		//如果文件下载出现异常则把上传的源文件下载下来
		tFin = new FileInputStream(tFile);
		if( tFin != null )
		{
		    response.reset();
			tFileName =tFileName +"."+ tOutputTypeName;
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+tFileName);
			OutputStream tFou1 = response.getOutputStream();
			int start;
			byte[] tBuf = new byte[1024];
			while((start=tFin.read(tBuf))!= -1)
			{
				tFou1.write(tBuf,0,start);
			}
			tFou1.flush();
			tFou1.close();
			response.flushBuffer();   
			out.clear();   
			out = pageContext.pushBody();
			tFin.close();
		}
		else
		{
			Content = "文件不存在！";
		}
	}
	catch(Exception ex1)
	{
		Content = "文件不存在！";
	}
}
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
  </script>    
</html>
