<%
//�ļ����ƣ�LLClaimEndCasePrintContSave.jsp
//�ļ����ܣ��᰸��֤��ӡ(PCT013)------����-��ͬ������ע,��������
//�����ˣ�yuejw
//��������:2005-08-15
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
	//ҳ����Ч
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "ҳ��ʧЧ,�����µ�½";
	    loggerDebug("LLClaimEndCasePrintContSave","ҳ��ʧЧ,�����µ�½");    
	}
	
    String transact = request.getParameter("fmtransact"); //��ȡ����
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
    loggerDebug("LLClaimEndCasePrintContSave","tPath="+tPath);
    
        //��֤��ӡ����-----���ڴ�ӡ������֤ʱ���롰��֤���롱
    LLParaPrintSchema tLLParaPrintSchema = new LLParaPrintSchema();
	tLLParaPrintSchema.setPrtCode(request.getParameter("PrtCode"));
	
    //Stringʹ��TransferData������ύ-----���ڴ��� �ⰸ�š��ͻ���
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("CustNo", request.getParameter("CustomerNo")); //�ͻ���
   	mTransferData.setNameAndValue("Path",tPath);
   	mTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
   	
   	VData tVData = new VData(); //׼������������ VData
    VData tResult = new VData(); //���ܺ�̨������
    XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLParaPrintSchema);
    
//    LLParaPrintUI tLLParaPrintUI=new LLParaPrintUI();
//    if (!tLLParaPrintUI.submitData(tVData,transact))
//    {
//        Content = " �����ύʧ�ܣ�ԭ����: " + tLLParaPrintUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
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
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="û�еõ�Ҫ��ʾ�������ļ�";
            loggerDebug("LLClaimEndCasePrintContSave","û�еõ�Ҫ��ʾ�������ļ�");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		
		String strVFPathName = tPath+"new.vts";
		loggerDebug("LLClaimEndCasePrintContSave","strVFPathName====="+strVFPathName);	
		//����Ԥ��ҳ�棬ͬʱ�������ݣ���ˮ�š������־������ԭ��
		session.putValue("RealPath", strVFPathName);
		session.putValue("PrtSeq", request.getParameter("PrtSeq"));
		loggerDebug("LLClaimEndCasePrintContSave","==��ˮ�� : "+session.getValue("PrtSeq"));
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
