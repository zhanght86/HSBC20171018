<%
//�ļ����ƣ�LLPRTInteInqReportSave.jsp
//�ļ����ܣ���ӡ������鱨��
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
    loggerDebug("LLPRTInteInqReportSave","ҳ��ʧЧ,�����µ�½");    
}
//else
//{
    String transact = request.getParameter("fmtransact"); //��ȡ����
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
    loggerDebug("LLPRTInteInqReportSave","tPath="+tPath);
	
    //Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š��ͻ���
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo3")); //�ⰸ��
    mTransferData.setNameAndValue("CustNo", request.getParameter("customerNo")); //�ͻ���
    mTransferData.setNameAndValue("InqNo", request.getParameter("InqNo")); //�������
//   	mTransferData.setNameAndValue("Path",tPath);
      
    VData tVData = new VData(); //׼������������ VData
    VData tResult = new VData(); //���ܺ�̨������
     XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
   
	LLPRTInteInqReportUI tLLPRTInteInqReportUI=new LLPRTInteInqReportUI();
    if (!tLLPRTInteInqReportUI.submitData(tVData,transact))
    {
        Content = " �����ύʧ�ܣ�ԭ����: " + tLLPRTInteInqReportUI.mErrors.getError(0).errorMessage;
        FlagStr = "Fail";
    }
    else
    {
    	Content = " �����ύ�ɹ�! ";
	    FlagStr = "Succ";
	    tResult = tLLPRTInteInqReportUI.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            loggerDebug("LLPRTInteInqReportSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		ExeSQL tExeSQL = new ExeSQL();
		//��ȡ��ʱ�ļ���
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath+tGI.Operator+"_" + FileQueue.getFileName()+".vts";
		//��ȡ�����ʱ�ļ���·��
		String strRealPath = application.getRealPath("/").replace('\\','/');
		loggerDebug("LLPRTInteInqReportSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		//��dataStream�洢�������ļ�
		loggerDebug("LLPRTInteInqReportSave","�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLPRTInteInqReportSave","==> Write VTS file to disk ");
		loggerDebug("LLPRTInteInqReportSave","===strVFFileName : "+strVFFileName);
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
    alert("<%=Content%>");
    top.close();  
</script>
</html>
<%
  }
%>
