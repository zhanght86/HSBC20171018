<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：LDRiskToRateTableSave.jsp
//创建日期：2012-09-19 15:13:22
//创建人  ：Guxin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
 //接收信息，并作校验处理。
 //输入参数

 String FlagStr = "";
 String Content = "";
 
 String targetPath = "";
 String excelSavePath = "select sysvarvalue from LDSYSVAR where sysvar = 'ImportExcelSavePath'";
 ExeSQL exec = new ExeSQL();
 
 String TableName = request.getParameter("TableName");
 String Remark = request.getParameter("Remark");
 String RiskCode = request.getParameter("RiskCode");
 String RiskName = request.getParameter("RiskName");
 String RateType = request.getParameter("RateType");
 //Remark = new String(Remark.getBytes("ISO8859-1"),"GBK");
 //RiskName = new String(RiskName.getBytes("ISO8859-1"),"GBK");
 System.out.println("RiskName:"+RiskName);
 System.out.println("Remark:"+Remark);
 String relativePath = exec.getOneValue(excelSavePath);
 String appPath = application.getRealPath("/").replace('\\', '/');
 if(appPath.endsWith("/"))
 targetPath = appPath + relativePath;
 else
  targetPath = appPath +"/"+ relativePath;
 
 String FileName = "";
 String operator = request.getParameter("ImportType");
 System.out.println("operator:"+operator);
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
			 System.out.println("sdfdfsd "+request.getHeader("Content-type"));
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
				        
				         //tFile = new File(targetPath.replace('/','\\'));
				         boolean b=tFile.mkdirs();
				         if(!b){
				         tFile = null;
				         tFile = new File(targetPath.replace('/','\\'));
				         tFile.mkdirs();
				         }
				       }
				      // System.out.println("FilePath=============="+targetPath +"/"+FileName);					   
				       item.write(new File(targetPath + FileName) ) ;					   
				       Content = targetPath + FileName;
				      System.out.println("UPLOAD FILEPATH:"+Content);
			 		}
			 		catch(Exception e)
			 		{
			 			FlagStr = "Fail";
			 			Content = ""+"上传文件失败！"+"";
			         	e.printStackTrace();
			 		}
			 	}
		 }
			 
			// deal excel
			if(!FlagStr.equals("Fail"))
			{
			 TransferData transferData = new TransferData();
			 GlobalInput tG = new GlobalInput(); 
			 tG=(GlobalInput)session.getAttribute("GI");
			 
			 transferData.setNameAndValue("Remark",Remark);
			 transferData.setNameAndValue("TableName",TableName);
			 transferData.setNameAndValue("RiskCode",RiskCode);
			 transferData.setNameAndValue("RiskName",RiskName);
			 transferData.setNameAndValue("RateType",RateType);
			 transferData.setNameAndValue("FileName",FileName);
			 transferData.setNameAndValue("targetFileName",Content);
			 
			 try
			 {
			  // 准备传输数据 VData
			  VData tVData = new VData();
			  tVData.add(tG);
			  tVData.add(transferData);
			   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(tBusinessDelegate.submitData(tVData, operator, "RateImportExcelBL"))
			  {
				  Content=""+"保存成功"+"";
			  }
			  else
			  {
				  FlagStr = "Fail";
				  Content = ""+"保存失败，原因是:"+"" + tBusinessDelegate.getCErrors().getLastError();
			  }
			 }
			 catch(Exception ex)
			 {
			  Content = ""+"保存失败，原因是:"+"" + ex.toString();
			  FlagStr = "Fail";
			 }
			}
	 }
	 catch(Exception ex)
	 {
		 FlagStr = "Fail";
		 Content = ""+"处理失败！"+"";
		 ex.printStackTrace();
	 }
%>                      

<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

