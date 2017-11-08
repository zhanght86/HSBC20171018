<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：PDRateCashValueSave.jsp
//程序功能：费率表和现金价值定制
//创建日期：2009-3-17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
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
 //接收信息，并作校验处理。
 //输入参数

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
 // 上传
 //if((isUpload == null) || !(isUpload.equals("1")))
 {
	 try
	 {
			 if(!targetPath.endsWith("/"))
			 {
				 targetPath += "/";
			 }
			 
			 DiskFileUpload fu = new DiskFileUpload();// 设置允许用户上传文件大小,单位:字节
			 fu.setSizeMax(50000000);
			// maximum size that will be stored in memory?
			// 设置最多只允许在内存中存储的数据,单位:字节
			 fu.setSizeThreshold(409600);
			// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
			 fu.setRepositoryPath(targetPath);
			
			 // 开始读取上传信息
			 List fileItems = fu.parseRequest(request);
			
			//  依次处理每个上传的文件
			 Iterator iter = fileItems.iterator();
			
			 if (iter.hasNext())
			 {
			 	FileItem item = (FileItem) iter.next(); 
			 	
			 	if (!item.isFormField())
			 	{
			 		String name = item.getName();
			 		
			 		FileName = name.replace('\\','/');
			 		FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
			 		
			 		//保存上传的文件到指定的目录
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
			 			Content = "上传文件失败！";
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
			  // 准备传输数据 VData
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
					  Content="保存成功";
				  }else{
					  FlagStr = "Fail";
					  Content="保存成功!发现有"+i+"个任务没有正确完成：<br />";
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
				  Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getLastError();
			  }
			  
			 }
			 catch(Exception ex)
			 {
			  Content = "保存失败，原因是:" + ex.toString();
			  FlagStr = "Fail";
			 }
			}
	 }
	 catch(Exception ex)
	 {
		 FlagStr = "Fail";
		 Content = "处理失败！";
		 ex.printStackTrace();
	 }
 }

%>                      

<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

