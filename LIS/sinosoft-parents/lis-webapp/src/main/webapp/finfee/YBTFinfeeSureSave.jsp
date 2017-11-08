<%
//程序名称：YBTFinfeeSureSave.jsp
//程序功能：
//创建日期：
//创建人  ：
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
  <%@page import="com.sinosoft.lis.finfee.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="java.io.*"%>
	<%@ page language="java" import="com.jspsmart.upload.*"%>
	<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
  //接收信息，并作校验处理。
  //输入参数
  // Variables
  	loggerDebug("YBTFinfeeSureSave","save");
	int count=0;        
  //上传路径
	String ImportPath = request.getParameter("ImportPath");
	loggerDebug("YBTFinfeeSureSave","ImportPath"+ImportPath);
  String feesum=""; //交费总金额
  String bankcode=""; //收款银行
	String FileName ="";
	// Initialization
	mySmartUpload.initialize(pageContext);
	mySmartUpload.setTotalMaxFileSize(10000000);

	loggerDebug("YBTFinfeeSureSave","...开始上载文件");
	// Upload	
	try
	{
		mySmartUpload.upload();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		}

	try {

		// Save the files with their original names in the virtual path "/upload"
		// if it doesn't exist try to save in the physical path "/upload"
		FileName = mySmartUpload.getFiles().getFile(0).getFileName(); //文件名
		 feesum = mySmartUpload.getRequest().getParameter("FeeSum");
		 bankcode = mySmartUpload.getRequest().getParameter("BankCode");
			loggerDebug("YBTFinfeeSureSave","FeeSum------"+feesum);
			loggerDebug("YBTFinfeeSureSave","BankCode------"+bankcode);
		loggerDebug("YBTFinfeeSureSave","FileName"+FileName);
		count = mySmartUpload.save(ImportPath);
		
		// Save the files with their original names in the virtual path "/upload"
		// count = mySmartUpload.save(ImportPath, mySmartUpload.SAVE_VIRTUAL);

		// Display the number of files uploaded 
		loggerDebug("YBTFinfeeSureSave",count + " file(s) uploaded.");

	} catch (Exception e) { 
		e.printStackTrace();
	}
	
	//输出参数
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr = "Fail";
	String Content = "";
	String Result="";
	
	TransferData tTransferData = new TransferData();
  

  //YBTFinfeeBL tYBTFinfeeBL   = new YBTFinfeeBL();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();   

	if (count >0)
	{
		GlobalInput tG = new GlobalInput();
	  tG=(GlobalInput)session.getValue("GI");
	
	  // 准备传输数据 VData
	  VData tVData = new VData();
	  FlagStr="";
	  tTransferData.setNameAndValue("FileName",FileName);
	  tTransferData.setNameAndValue("ImportPath",ImportPath);	
	  tTransferData.setNameAndValue("FeeSum",feesum);
	  tTransferData.setNameAndValue("BankCode",bankcode);
	  tVData.add(tTransferData);
		tVData.add(tG);
	  try
	  {
	    loggerDebug("YBTFinfeeSureSave","YBTFinfeeBL--------------");
	    tBusinessDelegate.submitData(tVData,"","YBTFinfeeBL");
	  }
	  catch(Exception ex)
	  {
	    Content = "保存失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	  }
  }
  else
  {
  	Content = "上载文件失败! ";
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    loggerDebug("YBTFinfeeSureSave","---－－－－－");

    tError = tBusinessDelegate.mErrors;
		for (int i=0;i<tError.getErrorCount();i++)
		{
			loggerDebug("YBTFinfeeSureSave","---tError"+tError.getError(i).errorMessage);
		}

    if (!tError.needDealError())
    {                          
    	Content = " 提交成功! ";
    	FlagStr = "Succ";

    	if (tBusinessDelegate.getResult()!=null)
    	{
    		try
    		{
		    	if(tBusinessDelegate.getResult().get(0)!=null&&tBusinessDelegate.getResult().size()>0)
		    	{
		    		Result = (String)tBusinessDelegate.getResult().get(0);
		    	}
		    }
		    catch(Exception e)
		    {
		    }
   	 	}
   	}
	  else                                                                           
	  {
	  	Content = " 保存失败，原因是:" + tError.getFirstError();
	   	FlagStr = "Fail";
	  }
  }
  //添加各种预处理

%>                      
<html>
<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

