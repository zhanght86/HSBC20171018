<%@include file="../i18n/language.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%

  //�ر����ѣ�
  //���ļ�ʹ�õ����ط���RequestDispatcher֧��WebSphere��
  //����tomcat�ϲ������أ���û�ҵ����õĽ������������ע��
  
 /* String fileNameDownload = request.getParameter("FilePath");
  fileNameDownload = fileNameDownload.substring(fileNameDownload.lastIndexOf("/temp/reinsure") + 1);
  
  System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nfileNameDownload:" + fileNameDownload);
  
  String fileNameDisplay = fileNameDownload.substring(fileNameDownload.lastIndexOf("/") + 1);//�����ļ�ʱ��ʾ���ļ���������
  System.out.println(fileNameDisplay);
  
  String fileNameDisplayUTF8 = URLEncoder.encode(fileNameDisplay,"UTF-8");
  System.out.println(fileNameDisplayUTF8);
  
  response.setContentType("application/x-download");//����Ϊ����application/x-download
  response.addHeader("Content-Disposition","attachment;filename=" + fileNameDisplayUTF8);
  
  try
  {
    RequestDispatcher dis = application.getRequestDispatcher(fileNameDownload);
    if(dis!= null)
    {
        dis.forward(request,response);
    }
    response.flushBuffer();
  }
  catch(Exception e)
  {
      e.printStackTrace();
  }
  finally
  {
  
  }*/
%>

<%

//���´�����tomcat���Խ�������,������WebSphere���������޷��ϵ��ļ�,δ�ҵ���������޷����

 String FlagStr = "Fail";
	    String Content = "";
	    String FileName = "";
	    String filePath = "";
    	int count = 0;
	
	 //�õ�excel�ļ��ı���·��
	 	//GlobalInput tG = new GlobalInput(); 	 	 
		//tG=(GlobalInput)session.getAttribute("GI");
	 	String FilePath = request.getParameter("FilePath");
	 	FileName = FilePath.substring(FilePath.lastIndexOf("/") + 1);
    
    //FilePath = new String(FilePath.getBytes("ISO-8859-1"),"gbk");
    System.out.println(FilePath);
    
    File file = new File(FilePath);
    
    response.reset();
    response.setContentType("application/octet-stream"); 
    response.setHeader("Content-Disposition","attachment; filename="+FileName+"");
    response.setContentLength((int) file.length());
    
    byte[] buffer = new byte[4096];
    BufferedOutputStream output = null;
    BufferedInputStream input = null;    
//д������
        try {
            output = new BufferedOutputStream(response.getOutputStream());
            input = new BufferedInputStream(new FileInputStream(file));
            
            int len = 0;
            while((len = input.read(buffer)) >0)
            {
              output.write(buffer,0,len);
            }
            input.close();
            output.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        } // maybe user cancelled download
        finally {
            if (input != null) input.close();
            if (output != null) output.close();
        }

%>

<html>


<script type="text/javascript">
</script>
</html>

