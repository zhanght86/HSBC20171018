<%@ page import="java.io.*"%><%@
include file="../../common/jsp/Log4jUI.jsp"%><%@
page import="com.sinosoft.utility.*"%><%@
page import="com.f1j.ss.*"%><%@
page import="java.util.*" %><%
FileInputStream tFin = null;
String Content = "";
//�������ļ�·�����ļ���
String tFilePath = (String)request.getAttribute("FilePath");
if(tFilePath==null){tFilePath = request.getParameter("FilePath");}
//�ļ���
String tFileName = (String)request.getAttribute("FileName");
if(tFileName==null){tFilePath = request.getParameter("FileName");}
//�ļ�������
String tOutputTypeName = (String)request.getAttribute("FileType");
if(tOutputTypeName==null){tFilePath = request.getParameter("FileType");}
File tFile = new File(tFilePath);
loggerDebug("FileDownload","�����ļ�=" + tFilePath);
try 
{
	tFin = new FileInputStream(tFile);
	if( tFin != null )
	{
		loggerDebug("FileDownload","tFin="+tFin+tOutputTypeName);
		// ���ļ����ж�ȡ����
		if(tOutputTypeName.equals("vts"))
		{
			if(tFileName.lastIndexOf(".xls")==-1){
				tFileName +=".xls";
			}
			// �����ݽ�����EXCEL�ļ�
			com.f1j.ss.BookModelImpl tFbm = new com.f1j.ss.BookModelImpl();
			tFbm.read(tFin, new com.f1j.ss.ReadParams());
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename="+new String(tFileName.getBytes("gbk"),"iso-8859-1"));   
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
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+new String(tFileName.getBytes("gbk"),"iso-8859-1"));   

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
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+new String(tFileName.getBytes("gbk"),"iso-8859-1"));   
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
