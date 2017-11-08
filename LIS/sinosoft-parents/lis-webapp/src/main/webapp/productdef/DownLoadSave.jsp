<%@include file="../i18n/language.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%

  //特别提醒：
  //本文件使用的下载方法RequestDispatcher支持WebSphere，
  //但在tomcat上不能下载，还没找到更好的解决方法，敬请注意
  
 /* String fileNameDownload = request.getParameter("FilePath");
  fileNameDownload = fileNameDownload.substring(fileNameDownload.lastIndexOf("/temp/reinsure") + 1);
  
  System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nfileNameDownload:" + fileNameDownload);
  
  String fileNameDisplay = fileNameDownload.substring(fileNameDownload.lastIndexOf("/") + 1);//下载文件时显示的文件保存名称
  System.out.println(fileNameDisplay);
  
  String fileNameDisplayUTF8 = URLEncoder.encode(fileNameDisplay,"UTF-8");
  System.out.println(fileNameDisplayUTF8);
  
  response.setContentType("application/x-download");//设置为下载application/x-download
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

//以下代码在tomcat可以进行下载,但是在WebSphere上下在世无法上到文件,未找到解决方法无法解决

 String FlagStr = "Fail";
	    String Content = "";
	    String FileName = "";
	    String filePath = "";
    	int count = 0;
	
	 //得到excel文件的保存路径
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
//写缓冲区
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

