<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-21 09:25:18
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>

<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="java.util.*"%>
	<%@page import="java.io.*"%>
<%
	    String FlagStr = "Fail";
	    String Content = "";
	    String FileName = "";
	    String filePath = "";
    	int count = 0;
	
	 //�õ�excel�ļ��ı���·��
	 	GlobalInput tG = new GlobalInput(); 	 	 
		tG=(GlobalInput)session.getAttribute("GI");
	 	String FilePath = request.getParameter("FilePath");
	 	FileName = FilePath.substring(FilePath.lastIndexOf("/") + 1);
    System.out.println("FilePath: "+FilePath);
    System.out.println("IndexOf: "+FilePath.lastIndexOf("/"));
    System.out.println("FileName: "+FileName);
    File file = new File(FilePath); 
    response.reset();
    
    String fileName = new String(FileName.getBytes("UTF-8"),"iso-8859-1"); 
  	response.setContentType("application/x-octetstream;charset=GBK");   
    response.setHeader("Content-Disposition","attachment; filename="+fileName+"");
    response.setContentLength((int) file.length());
    
    byte[] buffer = new byte[4096];
    BufferedOutputStream output = null;
    BufferedInputStream input = null;    
		//д������
        try {
            output = new BufferedOutputStream(response.getOutputStream());
            input = new BufferedInputStream(new FileInputStream(file));
            int n = (-1);
            while ((n = input.read(buffer, 0, 4096)) > -1) {
                output.write(buffer, 0, n);
            }
            response.flushBuffer();
        }
        catch (Exception e) {
        } // maybe user cancelled download
        finally {
            if (input != null) input.close();
            if (output != null) output.close();
        }
%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


