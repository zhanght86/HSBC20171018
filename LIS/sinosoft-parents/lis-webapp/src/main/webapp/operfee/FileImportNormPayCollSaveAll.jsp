<%
//程序名称：ReportJSImportChk.jsp
//程序功能：保监会报表精算数据导入
//创建日期：
//创建人 : ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>       
  <%@page import="com.sinosoft.service.*" %> 
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="java.io.*"%>
	<%@ page language="java" import="com.jspsmart.upload.*"%>
	<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
  //接收信息，并作校验处理。
  //输入参数
  // Variables
	int count=0;        
	String FileName=request.getParameter("FileName");
	loggerDebug("FileImportNormPayCollSaveAll","FileName:"+FileName);
	String GrpPolNo=request.getParameter("GrpPolNo1");
	String GrpContNo=request.getParameter("GrpContNo");
	String ImportPath = request.getParameter("ImportPath");
	String ImportConfigFile = request.getParameter("ImportConfigFile");
	loggerDebug("FileImportNormPayCollSaveAll","ImportPath:"+ImportPath);
	loggerDebug("FileImportNormPayCollSaveAll","ImportConfigFile:"+ImportConfigFile);
	String Xmlpath = application.getRealPath("").replace('\\','/');
	loggerDebug("FileImportNormPayCollSaveAll","Xmlpath:"+Xmlpath);

	// Initialization
	mySmartUpload.initialize(pageContext);
	mySmartUpload.setTotalMaxFileSize(10000000);

	loggerDebug("FileImportNormPayCollSaveAll","...开始上载文件");

	// Upload	
	try
	{
		mySmartUpload.upload();
	}
	catch(Exception ex)
	{
		loggerDebug("FileImportNormPayCollSaveAll",ex.toString());
		ex.printStackTrace();
	}

	try {
		// Save the files with their original names in the virtual path "/upload"
		// if it doesn't exist try to save in the physical path "/upload"
		FileName = mySmartUpload.getFiles().getFile(0).getFileName();
		loggerDebug("FileImportNormPayCollSaveAll","FileName:"+FileName);
		count = mySmartUpload.save(ImportPath);
		
		// Save the files with their original names in the virtual path "/upload"
		// count = mySmartUpload.save(ImportPath, mySmartUpload.SAVE_VIRTUAL);

		// Display the number of files uploaded 
		loggerDebug("FileImportNormPayCollSaveAll",count + " file(s) uploaded.");

	} catch (Exception e) { 
		e.printStackTrace();
	}
	
	//输出参数
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr = "Fail";
	String Content = "";
	String Result="";	
	boolean flag=true;
	GlobalInput tG = new GlobalInput();  
    tG=(GlobalInput)session.getValue("GI");  
    if(tG == null) {
		out.println("session has expired");
		return;
    } 
    loggerDebug("FileImportNormPayCollSaveAll","团单号："+GrpPolNo);
    loggerDebug("FileImportNormPayCollSaveAll","文件名："+FileName);
    loggerDebug("FileImportNormPayCollSaveAll","配置文件："+ImportConfigFile);
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("GrpPolNo",GrpPolNo);	
    tTransferData.setNameAndValue("FileName",FileName);	
    tTransferData.setNameAndValue("GrpContNo",GrpContNo);
    tTransferData.setNameAndValue("ConfigFileName",ImportConfigFile);
    tTransferData.setNameAndValue("XmlPath",Xmlpath);
    
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tTransferData );
		tVData.add( tG );
		
		// 数据传输
  	//FileImportPreParePayPersonUI tFileImportPreParePayPersonUI   = new FileImportPreParePayPersonUI();
  	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  			loggerDebug("FileImportNormPayCollSaveAll","before FileImportPreParePayPersonUI!!!!");			
		 if (!tBusinessDelegate.submitData(tVData,"INSERT","FileImportPreParePayPersonUI"))
		{
			int n = ttBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			loggerDebug("FileImportNormPayCollSaveAll","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			Content = " 数据导入失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " 数据导入成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"提示：异常终止!";
}
 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
