<%
//�ļ����ƣ�LLPRTClaimAuditiReportSave.jsp
//�ļ����ܣ���ӡ�ⰸ��˱���
//�����ˣ�yuejw
//��������:
%>

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
//�������
//�������
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
    loggerDebug("LLPRTClaimAuditiReportSave","ҳ��ʧЧ,�����µ�½");    
}
//else
//{
    String transact = request.getParameter("fmtransact"); //��ȡ����
	String tPath = application.getRealPath("f1print/NCLtemplate") + "/";
    loggerDebug("LLPRTClaimAuditiReportSave","tPath="+tPath);
	
    //Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š��ͻ���
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("RptNo")); //�ⰸ��
    mTransferData.setNameAndValue("CustNo", request.getParameter("customerNo")); //�ͻ���
//   	mTransferData.setNameAndValue("Path",tPath);
      
    VData tVData = new VData(); //׼������������ VData
    VData tResult = new VData(); //���ܺ�̨������
     XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
   
//	LLPRTClaimAuditiReportUI tLLPRTClaimAuditiReportUI=new LLPRTClaimAuditiReportUI();
//    if (!tLLPRTClaimAuditiReportUI.submitData(tVData,transact))
//    {
//        Content = " �����ύʧ�ܣ�ԭ����: " + tLLPRTClaimAuditiReportUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
String busiName="grpLLPRTClaimAuditiReportUI";
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
	    //tResult = tLLPRTClaimAuditiReportUI.getResult();	
	     tResult = tBusinessDelegate.getResult();	   
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            loggerDebug("LLPRTClaimAuditiReportSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		ExeSQL tExeSQL = new ExeSQL();
		//��ȡ��ʱ�ļ���
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		//-------------------------------------jinsh20070521------------------------------//
		//String strVFFileName = strFilePath+tGI.Operator+"_" + FileQueue.getFileName()+".vts";
		String strVFFileName = "vtsfile/"+tGI.Operator+"_" + FileQueue.getFileName()+".vts";
		//-------------------------------------jinsh20070521------------------------------//
		//��ȡ�����ʱ�ļ���·��
		String strRealPath = application.getRealPath("/").replace('\\','/');
		loggerDebug("LLPRTClaimAuditiReportSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/NCLtemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		//��dataStream�洢�������ļ�
		loggerDebug("LLPRTClaimAuditiReportSave","�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLPRTClaimAuditiReportSave","==> Write VTS file to disk ");
		loggerDebug("LLPRTClaimAuditiReportSave","===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//�����������get��ʽ�������ļ�·��
//		response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
		response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
	}
	else
    {
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
<%
  }
%>
>
