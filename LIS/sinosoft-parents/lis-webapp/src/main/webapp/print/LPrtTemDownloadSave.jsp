<%@ page import="java.io.*"%><%@
include file="../common/jsp/Log4jUI.jsp"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%
FileInputStream tFin = null;
String Content = "";
String tFilePath = request.getParameter("FilePath");
//��ȡ�ļ���
String tFileName = tFilePath.substring(tFilePath.lastIndexOf("/")+1,tFilePath.lastIndexOf("."));
//����ļ����������
String tOutputTypeName = tFilePath.substring(tFilePath.lastIndexOf(".")+1);
//Ӧ�ø�··��
String tPath = application.getRealPath("").replace('\\','/');
if(!tPath.endsWith("/"))
{
	tPath += "/";
}
File tFile = new File(tPath+tFilePath);
loggerDebug("LPrtTemDownloadSave","�����ļ�="+tPath+tFilePath);
try 
{
	tFin = new FileInputStream(tFile);
	if( tFin != null )
	{
		loggerDebug("LPrtTemDownloadSave","tFin="+tFin+tOutputTypeName);
		// ���ļ����ж�ȡ����
		if(tOutputTypeName.equals("vts"))
		{
			// �����ݽ�����EXCEL�ļ�
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
			// �����ݽ�����������ʽ�ļ�
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
		Content = "�ļ������ڣ�";
	}
}
catch(Exception ex)
{
	try
	{
		//����ļ����س����쳣����ϴ���Դ�ļ���������
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
			Content = "�ļ������ڣ�";
		}
	}
	catch(Exception ex1)
	{
		Content = "�ļ������ڣ�";
	}
}
%>
<html>
  <script language="javascript">
     alert("<%=Content%>");
  </script>    
</html>
