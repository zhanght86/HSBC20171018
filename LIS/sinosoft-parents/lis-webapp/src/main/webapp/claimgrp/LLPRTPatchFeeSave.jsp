<%
//**************************************************************************************************
//Name��LLPRTPatchFeeSave.jsp
//Function����������֪ͨ��
//Author��wsz
//Date:   2005-8-8
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI"); 
	//ҳ����Ч
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "ҳ��ʧЧ,�����µ�½";
	    loggerDebug("LLPRTPatchFeeSave","ҳ��ʧЧ,�����µ�½");    
	}
	
    String transact = request.getParameter("fmtransact"); //��ȡ����
	String tPath = application.getRealPath("f1print/NCLtemplate") + "/";
    loggerDebug("LLPRTPatchFeeSave","tPath="+tPath);
    

    //Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š��ͻ���
    TransferData mTransferData = new TransferData();
//    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //�ⰸ��
//    mTransferData.setNameAndValue("PrtCode", "PCT008");
//    mTransferData.setNameAndValue("CustNo", request.getParameter("CustomerNo")); //�ͻ���
	mTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
   	mTransferData.setNameAndValue("Path",tPath);
   	
   	VData tVData = new VData(); //׼������������ VData
    VData tResult = new VData(); //���ܺ�̨������
    XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
    //tVData.add(tLLPRTPatchFeeSchema);
    
//    LLPRTPatchFeeUI tLLPRTPatchFeeUI=new LLPRTPatchFeeUI();
//    if (!tLLPRTPatchFeeUI.submitData(tVData,transact))
//    {
//        Content = " �����ύʧ�ܣ�ԭ����: " + tLLPRTPatchFeeUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
String busiName="grpLLPRTPatchFeeUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "�����ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�����ύʧ��";
		FlagStr = "Fail";				
	}
}

    else
    {
    	Content = " �����ύ�ɹ�! ";
	    FlagStr = "Succ";
	   // tResult = tLLPRTPatchFeeUI.getResult();	  
	     tResult = tBusinessDelegate.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            loggerDebug("LLPRTPatchFeeSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		
		String strVFPathName = tPath+"new.vts";
		loggerDebug("LLPRTPatchFeeSave","strVFPathName====="+strVFPathName);
		//����Ԥ��ҳ�棬ͬʱ�������ݣ���ˮ�š������־������ԭ��
		session.putValue("RealPath", strVFPathName);
		session.putValue("PrtSeq", request.getParameter("PrtSeq"));
		loggerDebug("LLPRTPatchFeeSave","==��ˮ�� : "+session.getValue("PrtSeq"));
		//�����������get��ʽ�������ļ�·��----����·����ȡ�ļ�
		response.sendRedirect("../claim/LLClaimGetF1PrintJ.jsp");	
	}
 else
    {
%>
<html>
<script language="javascript">  
    alert("<%=Content%>");
    top.close();  
</script>
</html>
<%
  }
%>
