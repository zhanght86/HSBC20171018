<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ReadFromFileSave.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="java.io.*"%>
  
  <%@page import="com.sinosoft.service.*"%>
  
	<%@page language="java" import="com.jspsmart.upload.*"%>
	<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
  
<%
  loggerDebug("ReadFromFileSave","\n\n---ReadFromFileSave Start---");
  
  //上载文件
	int count=0;        
	String fileName = "";
	String filePath = request.getParameter("filePath");
	String serialNo = request.getParameter("serialno");
	String bankCode = request.getParameter("bankCode");
	String busstype = request.getParameter("bussType");				//在js页的fm.action方法中传递的该参数
	String inputDate = PubFun.getCurrentDate();
	String subPath= inputDate.substring(0, 4) + "/" + inputDate.substring(5, 7)
			+ "/" + inputDate.substring(8, 10);
	File upPath=new File(filePath,subPath);
	loggerDebug("ReadFromFileSave","filePath="+filePath);
	loggerDebug("ReadFromFileSave","subPath="+subPath);
	upPath.mkdirs();
	// Initialization
	mySmartUpload.initialize(pageContext);
	mySmartUpload.setTotalMaxFileSize(5000000);
	
	String Content = "";
	String FlagStr = "";

	loggerDebug("ReadFromFileSave","...开始上载文件");
	try	{
		mySmartUpload.upload();
		
		//get the file Name from http stream
		fileName = mySmartUpload.getFiles().getFile(0).getFileName();
		//上传文件重命名，在最后一个'.'号之前追加当前时间（精确到秒）,如无'.'号则追加在文件名最后
		int dotIndex = fileName.lastIndexOf(".");
		int fileLen = fileName.length();
		if(dotIndex==-1){
			fileName = fileName + PubFun.getCurrentTime().replaceAll(":", "");
		}else{
			fileName = fileName.substring(0,dotIndex) + PubFun.getCurrentTime().replaceAll(":", "")+fileName.subSequence(dotIndex,fileLen);			
		}
		if(fileName==null || "".equals(fileName) || "null".equals(fileName))
			throw new IllegalArgumentException("文件名为空！");
//		count = mySmartUpload.save(upPath.getAbsolutePath());
		//将上传文件另存为新生成的文件名
		//mySmartUpload.getFiles().getFile(0).saveAs(upPath.getAbsolutePath()+"\\"+fileName);
		mySmartUpload.getFiles().getFile(0).saveAs(upPath.getAbsolutePath()+"/"+fileName);
		loggerDebug("ReadFromFileSave","filePath:" + upPath);
		loggerDebug("ReadFromFileSave","fileName:" + fileName);
		loggerDebug("ReadFromFileSave","serialNo:" + serialNo);
		loggerDebug("ReadFromFileSave","bankCode:" + bankCode);
		loggerDebug("ReadFromFileSave","bussType:" + busstype);
//		loggerDebug("ReadFromFileSave",count + " file(s) uploaded.");
  
  //ReadFromFileUI readFromFileUI1 = new ReadFromFileUI();
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();          

  TransferData transferData1 = new TransferData();
  transferData1.setNameAndValue("fileName", new File(upPath, fileName).getAbsolutePath());
  transferData1.setNameAndValue("serialNo", serialNo);
  transferData1.setNameAndValue("bankCode", bankCode);
  transferData1.setNameAndValue("bussType", busstype);
  transferData1.setNameAndValue("appPath", application.getRealPath("/"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");

  VData inVData = new VData();
  inVData.add(transferData1);
  inVData.add(tGlobalInput);
  
  //if (!readFromFileUI1.submitData(inVData, "READ")) {
  if (!tBusinessDelegate.submitData(inVData,"READ","ReadFromFileUI")) {
    //VData rVData = readFromFileUI1.getResult();
    VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
    //if(readFromFileUI1.mErrors.needDealError()){
    if(tBusinessDelegate.getCErrors().needDealError()){
    	Content+="但"+tBusinessDelegate.getCErrors().getFirstError();
    }
  	FlagStr = "Succ";
  }

	loggerDebug("ReadFromFileSave",Content + "\n" + FlagStr + "\n---ReadFromFileSave End---\n\n");
	} 
	catch (Exception e) { 
		e.printStackTrace();
		Content = " 处理失败，原因是:" + e.getMessage();
	  	FlagStr = "Fail";
	}

%>                      

<%@page import="java.io.File"%><html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=PubFun.changForJavaScript(Content)%>');
</script>
</html>
