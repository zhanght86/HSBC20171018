<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

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
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
 //������Ϣ������У�鴦��
 //�������

 String FlagStr = "";
 String Content = "";
 
 String targetPath = "";
 String excelSavePath = "select sysvarvalue from LDSYSVAR where sysvar = 'ImportExcelSavePath'";
 ExeSQL exec = new ExeSQL();
 
 String tUploadTempalteID = request.getParameter("UploadTempalteID");
 
 String relativePath = exec.getOneValue(excelSavePath);
 String appPath = application.getRealPath("/").replace('\\', '/');

 targetPath = appPath + relativePath;
 
 String FileName = "";
 String operator = request.getParameter("ImportType");
 loggerDebug("RuleResultImportExcelSave","operator:"+operator);
 String dealType = "";
 String isUpload = request.getParameter("IsUpload");
 // �ϴ�
 //if((isUpload == null) || !(isUpload.equals("1")))
 {
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
				       loggerDebug("RuleResultImportExcelSave","FilePath=============="+targetPath +"/"+FileName);
					   
				       item.write(new File(targetPath + FileName) ) ;
					   
				       Content = targetPath + FileName;
			 		}
			 		catch(Exception e)
			 		{
			 			FlagStr = "Fail";
			 			Content = "�ϴ��ļ�ʧ�ܣ�";
			         	e.printStackTrace();
			 		}
			 	}
		 }
			 
			// deal excel
			if(!FlagStr.equals("Fail"))
			{
			 TransferData transferData = new TransferData();
			 GlobalInput tG = new GlobalInput(); 
			 tG=(GlobalInput)session.getValue("GI");
			 
			 transferData.setNameAndValue("UploadTempalteID",tUploadTempalteID);
			 transferData.setNameAndValue("FileName",FileName);
			 transferData.setNameAndValue("targetFileName",Content);
			 
			 try
			 {
			  // ׼���������� VData
			  VData tVData = new VData();
			
			  tVData.add(tG);
			  tVData.add(transferData);
			  //RuleImportExcelBL tRuleImportExcelBL = new RuleImportExcelBL();
			   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  //if(tRuleImportExcelBL.submitData(tVData,operator))
			  if(tBusinessDelegate.submitData(tVData, operator, "RuleImportExcelBL"))
			  {
				  CErrors tErrors =	tBusinessDelegate.getCErrors();  
				  int i = tErrors.getErrorCount();
				  if(i==0){
					  Content="����ɹ�";
				  }else{
					  FlagStr = "Fail";
					  Content="����ɹ�!������"+i+"������û����ȷ��ɣ�<br />";
					  if(i<10){
						  for(int j=0;j<i;j++){
					  		Content += tErrors.getLastError()+"<br />";
					  		tErrors.removeLastError();
						  }
					  }else{
						  for(int j=0;j<10;j++){
						  	Content +=tErrors.getLastError()+"<br />";
						  	tErrors.removeLastError();
						  }
					  }
				  }
			  }
			  else
			  {
				  FlagStr = "Fail";
				  Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getLastError();
			  }
			  
			 }
			 catch(Exception ex)
			 {
			  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			  FlagStr = "Fail";
			 }
			}
	 }
	 catch(Exception ex)
	 {
		 FlagStr = "Fail";
		 Content = "����ʧ�ܣ�";
		 ex.printStackTrace();
	 }
 }

%>                      

<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

