<%
//�������ƣ�YBTFinfeeSureSave.jsp
//�����ܣ�
//�������ڣ�
//������  ��
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
  <%@page import="com.sinosoft.lis.finfee.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="java.io.*"%>
	<%@ page language="java" import="com.jspsmart.upload.*"%>
	<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
  //������Ϣ������У�鴦��
  //�������
  // Variables
  	loggerDebug("YBTFinfeeSureSave","save");
	int count=0;        
  //�ϴ�·��
	String ImportPath = request.getParameter("ImportPath");
	loggerDebug("YBTFinfeeSureSave","ImportPath"+ImportPath);
  String feesum=""; //�����ܽ��
  String bankcode=""; //�տ�����
	String FileName ="";
	// Initialization
	mySmartUpload.initialize(pageContext);
	mySmartUpload.setTotalMaxFileSize(10000000);

	loggerDebug("YBTFinfeeSureSave","...��ʼ�����ļ�");
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
		FileName = mySmartUpload.getFiles().getFile(0).getFileName(); //�ļ���
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
	
	//�������
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
	
	  // ׼���������� VData
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
	    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	  }
  }
  else
  {
  	Content = "�����ļ�ʧ��! ";
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    loggerDebug("YBTFinfeeSureSave","---����������");

    tError = tBusinessDelegate.mErrors;
		for (int i=0;i<tError.getErrorCount();i++)
		{
			loggerDebug("YBTFinfeeSureSave","---tError"+tError.getError(i).errorMessage);
		}

    if (!tError.needDealError())
    {                          
    	Content = " �ύ�ɹ�! ";
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
	  	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
	   	FlagStr = "Fail";
	  }
  }
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

