<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

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
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.fileupload.*"%>

  
<%
 //接收信息，并作校验处理。
 //输入参数

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
 // 上传
 if((isUpload == null) || !(isUpload.equals("1")))
 {
	 dealType = "0";
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
				       System.out.println("FilePath=============="+targetPath +"/"+FileName);
					   
				       item.write(new File(targetPath +"/"+ FileName) ) ;
					   
				       Content = targetPath + FileName;
			 		}
			 		catch(Exception e)
			 		{
			 			FlagStr = "Fail";
			 			Content = ""+"上传文件失败！"+"";
			         	e.printStackTrace();
			 		}
			 	}
		 }
	 }
	 catch(Exception ex)
	 {
		 FlagStr = "Fail";
		 Content = ""+"上传文件失败！"+"";
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
	  // 准备传输数据 VData
	  VData tVData = new VData();
	
	  tVData.add(tG);
	  tVData.add(transferData);
	  PDImportExcelBL tPDImportExcelBL = new PDImportExcelBL();
	  if(tPDImportExcelBL.submitData(tVData,operator))
	  {
		  Content=""+"保存成功"+"";
	  }
	  else
	  {
		  Content = ""+"保存失败，原因是:"+"" + tPDImportExcelBL.mErrors.getFirstError();
	  }
	  
	 }
	 catch(Exception ex)
	 {
	  Content = ""+"保存失败，原因是:"+"" + ex.toString();
	  FlagStr = "Fail";
	 }
 }
 
 //添加各种预处理
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