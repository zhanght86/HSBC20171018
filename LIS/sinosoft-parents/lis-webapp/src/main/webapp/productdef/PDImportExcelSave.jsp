<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<% 
//�������ƣ�PDRateCashValueSave.jsp
//�����ܣ����ʱ���ֽ��ֵ����
//�������ڣ�2009-3-17
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.fileupload.*"%>

  
<%
 //������Ϣ������У�鴦��
 //�������

 String FlagStr = "";
 String Content = "";
 
 String targetPath = "";
 String excelSavePath = "select sysvarvalue from LDSYSVAR where sysvar = 'ImportExcelSavePath'";
 ExeSQL exec = new ExeSQL();
 String relativePath = exec.getOneValue(excelSavePath);
 String appPath = application.getRealPath("/").replace('\\', '/');

 targetPath = appPath +"/"+ relativePath; 
 
 String FileName = "";
 String operator = request.getParameter("operator");
 String dealType = "";
 String isUpload = request.getParameter("IsUpload");
 // �ϴ�
 if((isUpload == null) || !(isUpload.equals("1")))
 {
	 dealType = "0";
	 try
	 {
			 if(!targetPath.endsWith("/"))
			 {
				 targetPath += "/";
			 }
			 
			 DiskFileUpload fu = new DiskFileUpload();// ���������û��ϴ��ļ���С,��λ:�ֽ�
			 fu.setSizeMax(50000000);
			// maximum size that will be stored in memory?
			// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
			 fu.setSizeThreshold(409600);
			// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
			 fu.setRepositoryPath(targetPath);
			
			 // ��ʼ��ȡ�ϴ���Ϣ
			 List fileItems = fu.parseRequest(request);
			
			//  ���δ���ÿ���ϴ����ļ�
			 Iterator iter = fileItems.iterator();
			
			 if (iter.hasNext())
			 {
			 	FileItem item = (FileItem) iter.next(); 
			 	
			 	if (!item.isFormField())
			 	{
			 		String name = item.getName();
			 		
			 		FileName = name.replace('\\','/');
			 		FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
			 		
			 		//�����ϴ����ļ���ָ����Ŀ¼
			 		try
			 		{
				       File tFile = new File(targetPath);
				       if(!tFile.exists())
				       {
				         tFile = null;
				         tFile = new File(targetPath.replace('/','\\'));
				         tFile.mkdirs();
				       }
				       System.out.println("FilePath=============="+targetPath +"/"+FileName);
					   
				       item.write(new File(targetPath +"/"+ FileName) ) ;
					   
				       Content = targetPath + FileName;
			 		}
			 		catch(Exception e)
			 		{
			 			FlagStr = "Fail";
			 			Content = ""+"�ϴ��ļ�ʧ�ܣ�"+"";
			         	e.printStackTrace();
			 		}
			 	}
		 }
	 }
	 catch(Exception ex)
	 {
		 FlagStr = "Fail";
		 Content = ""+"�ϴ��ļ�ʧ�ܣ�"+"";
		 ex.printStackTrace();
	 }
 }
 else
 {
	 dealType = "1";
	 // deal excel
	 TransferData transferData = new TransferData();
	 GlobalInput tG = new GlobalInput(); 
	 tG=(GlobalInput)session.getAttribute("GI");
	 
	 transferData.setNameAndValue("newTableName", request.getParameter("newTableName"));
	 transferData.setNameAndValue("targetFileName",request.getParameter("targetFileName"));
	 
	 try
	 {
	  // ׼���������� VData
	  VData tVData = new VData();
	
	  tVData.add(tG);
	  tVData.add(transferData);
	  PDImportExcelBL tPDImportExcelBL = new PDImportExcelBL();
	  if(tPDImportExcelBL.submitData(tVData,operator))
	  {
		  Content=""+"����ɹ�"+"";
	  }
	  else
	  {
		  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + tPDImportExcelBL.mErrors.getFirstError();
	  }
	  
	 }
	 catch(Exception ex)
	 {
	  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
	  FlagStr = "Fail";
	 }
 }
 
 //��Ӹ���Ԥ����
%>                      

<html>
<script type="text/javascript">
 if("<%=dealType%>" == "0")
 {
 	parent.fraInterface.afterUpload("<%=FlagStr%>","<%=Content%>");
 }
 else if("<%=dealType%>" == "1")
 {
 	parent.fraInterface.afterdealUpload("<%=FlagStr%>","<%=Content%>");
 }	
 
</script>
</html>