<%
//�ļ����ƣ�LLPRTInteInqTaskSave.jsp
//�ļ����ܣ���������֪ͨ����ӡ
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
    loggerDebug("LLPRTInteInqTaskSave","ҳ��ʧЧ,�����µ�½");    
}
//else
//{
    String transact = request.getParameter("fmtransact"); //��ȡ����
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
    loggerDebug("LLPRTInteInqTaskSave","tPath="+tPath);
	
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
   
	LLPRTInteInqTaskUI tLLPRTInteInqTaskUI=new LLPRTInteInqTaskUI();
    if (!tLLPRTInteInqTaskUI.submitData(tVData,transact))
    {
        Content = " �����ύʧ�ܣ�ԭ����: " + tLLPRTInteInqTaskUI.mErrors.getError(0).errorMessage;
        FlagStr = "Fail";
    }
    else
    {
    	Content = " �����ύ�ɹ�! ";
	    FlagStr = "Succ";
	    tResult = tLLPRTInteInqTaskUI.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            loggerDebug("LLPRTInteInqTaskSave","û�еõ�Ҫ��ʾ�������ļ�");
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
		loggerDebug("LLPRTInteInqTaskSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		CombineVts tcombineVts = null;
		//�ϲ�VTS�ļ�
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		//��dataStream�洢�������ļ�
		loggerDebug("LLPRTInteInqTaskSave","�洢�ļ���"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLPRTInteInqTaskSave","==> Write VTS file to disk ");
		loggerDebug("LLPRTInteInqTaskSave","===strVFFileName : "+strVFFileName);
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
