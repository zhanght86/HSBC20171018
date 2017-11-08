<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ReadFromFileSave.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
  
  //�����ļ�
	int count=0;        
	String fileName = "";
	String filePath = request.getParameter("filePath");
	String serialNo = request.getParameter("serialno");
	String bankCode = request.getParameter("bankCode");
	String busstype = request.getParameter("bussType");				//��jsҳ��fm.action�����д��ݵĸò���
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

	loggerDebug("ReadFromFileSave","...��ʼ�����ļ�");
	try	{
		mySmartUpload.upload();
		
		//get the file Name from http stream
		fileName = mySmartUpload.getFiles().getFile(0).getFileName();
		//�ϴ��ļ��������������һ��'.'��֮ǰ׷�ӵ�ǰʱ�䣨��ȷ���룩,����'.'����׷�����ļ������
		int dotIndex = fileName.lastIndexOf(".");
		int fileLen = fileName.length();
		if(dotIndex==-1){
			fileName = fileName + PubFun.getCurrentTime().replaceAll(":", "");
		}else{
			fileName = fileName.substring(0,dotIndex) + PubFun.getCurrentTime().replaceAll(":", "")+fileName.subSequence(dotIndex,fileLen);			
		}
		if(fileName==null || "".equals(fileName) || "null".equals(fileName))
			throw new IllegalArgumentException("�ļ���Ϊ�գ�");
//		count = mySmartUpload.save(upPath.getAbsolutePath());
		//���ϴ��ļ����Ϊ�����ɵ��ļ���
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
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
    //if(readFromFileUI1.mErrors.needDealError()){
    if(tBusinessDelegate.getCErrors().needDealError()){
    	Content+="��"+tBusinessDelegate.getCErrors().getFirstError();
    }
  	FlagStr = "Succ";
  }

	loggerDebug("ReadFromFileSave",Content + "\n" + FlagStr + "\n---ReadFromFileSave End---\n\n");
	} 
	catch (Exception e) { 
		e.printStackTrace();
		Content = " ����ʧ�ܣ�ԭ����:" + e.getMessage();
	  	FlagStr = "Fail";
	}

%>                      

<%@page import="java.io.File"%><html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=PubFun.changForJavaScript(Content)%>');
</script>
</html>
