<%
//�������ƣ�ReportJSImportChk.jsp
//�����ܣ�����ᱨ�������ݵ���
//�������ڣ�
//������ : ck
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
  //������Ϣ������У�鴦��
  //�������
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

	loggerDebug("FileImportNormPayCollSaveAll","...��ʼ�����ļ�");

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
	
	//�������
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
    loggerDebug("FileImportNormPayCollSaveAll","�ŵ��ţ�"+GrpPolNo);
    loggerDebug("FileImportNormPayCollSaveAll","�ļ�����"+FileName);
    loggerDebug("FileImportNormPayCollSaveAll","�����ļ���"+ImportConfigFile);
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
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tTransferData );
		tVData.add( tG );
		
		// ���ݴ���
  	//FileImportPreParePayPersonUI tFileImportPreParePayPersonUI   = new FileImportPreParePayPersonUI();
  	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  			loggerDebug("FileImportNormPayCollSaveAll","before FileImportPreParePayPersonUI!!!!");			
		 if (!tBusinessDelegate.submitData(tVData,"INSERT","FileImportPreParePayPersonUI"))
		{
			int n = ttBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			loggerDebug("FileImportNormPayCollSaveAll","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			Content = " ���ݵ���ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " ���ݵ���ɹ�! ";
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
	Content = Content.trim()+"��ʾ���쳣��ֹ!";
}
 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
