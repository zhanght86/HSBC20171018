<%
//�ļ����ƣ�LLClaimEndCaseParaPrint.jsp
//�ļ����ܣ��᰸��֤��ӡ-----���� ����֤���롱�����ⰸ�š��ͻ��š�
//�����ˣ�yuejw
//��������:2005-08-10
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

	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");  
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "ҳ��ʧЧ,�����µ�½";
	    loggerDebug("LLClaimEndCaseParaPrint","ҳ��ʧЧ,�����µ�½");    
	}

		String transact = request.getParameter("fmtransact"); //��ȡ����
		String tPath = application.getRealPath("f1print/NCLtemplate") + "/"; //ģ������·��
		loggerDebug("LLClaimEndCaseParaPrint","tPath="+tPath);
		
		//��֤��ӡ����-----���ڴ�ӡ������֤ʱ���롰��֤���롱
	    LLParaPrintSchema tLLParaPrintSchema = new LLParaPrintSchema();
		tLLParaPrintSchema.setPrtCode(request.getParameter("PrtCode"));
		
	    //Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š��ͻ��š�ģ��·��
	    TransferData mTransferData = new TransferData();
	    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //�ⰸ��
	    mTransferData.setNameAndValue("CustNo", request.getParameter("CustomerNo")); //�ͻ���
	   	mTransferData.setNameAndValue("Path",tPath);
	   	 mTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));

	   	VData tVData = new VData(); //׼������������ VData
	    VData tResult = new VData(); //���պ�̨������
	    XmlExport txmlExport = new XmlExport();
	    tVData.add(tGI);
	    tVData.add(mTransferData);
	    tVData.add(tLLParaPrintSchema);
	    
//	    LLParaPrintUI tLLParaPrintUI=new LLParaPrintUI();
//	    if (!tLLParaPrintUI.submitData(tVData,transact))
//	    {
//	        Content = " �����ύʧ�ܣ�ԭ����: " + tLLParaPrintUI.mErrors.getError(0).errorMessage;
//	        FlagStr = "Fail";
//	    }
String busiName="grpLLParaPrintUI";
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
		    //tResult = tLLParaPrintUI.getResult();	
		      tResult = tBusinessDelegate.getResult();	   
		    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
		    if(txmlExport==null)
		    {
		        FlagStr="FAIL";
		        Content="û�еõ�Ҫ��ʾ�������ļ�";
	            loggerDebug("LLClaimEndCaseParaPrint","û�еõ�Ҫ��ʾ�������ļ�");
		    }
		}
		
		//������ʱ�ļ�-----��ӡ
		if (FlagStr.equals("Succ"))
		{
			ExeSQL tExeSQL = new ExeSQL();
			//��ȡ��ʱ�ļ���
			String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
			String strFilePath = tExeSQL.getOneValue(strSql);
			loggerDebug("LLClaimEndCaseParaPrint","��ʱ�ļ���strFilePath="+strFilePath);
			String strVFFileName = strFilePath+"new.vts";
			
			//��ȡ�����ʱ�ļ���·��
			String strRealPath = application.getRealPath("/").replace('\\','/');
			loggerDebug("LLClaimEndCaseParaPrint","��ʱ�ļ���·��strRealPath="+strRealPath);
			String strVFPathName = strRealPath + strVFFileName;
			
			//������ʱVTS�ļ�
			CombineVts tcombineVts = null;
			String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
			loggerDebug("LLClaimEndCaseParaPrint","strTemplatePath====="+strTemplatePath);
			tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			tcombineVts.output(dataStream);
			
			//�������� dataStream�洢�������ļ�
			loggerDebug("LLClaimEndCaseParaPrint","�洢�ļ���"+strVFPathName);
			AccessVtsFile.saveToFile(dataStream,strVFPathName);
			loggerDebug("LLClaimEndCaseParaPrint","===strVFFileName : "+strVFFileName);
			
			//����Ԥ��ҳ�棬ͬʱ�������ݣ���ˮ�š������־������ԭ��
			session.putValue("RealPath", strVFPathName);
			session.putValue("PrtSeq", request.getParameter("PrtSeq"));
			loggerDebug("LLClaimEndCaseParaPrint","==��ˮ�� : "+session.getValue("PrtSeq"));
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
