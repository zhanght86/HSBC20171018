<%
//�������ƣ�LLGrpClaimUserSave.jsp
//�����ܣ������û�������
//�������ڣ�2009/04/17  
//������  ��zhangzheng
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����

%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>

<%
    //׼��ͨ�ò���
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LLGrpClaimUserUI tLLGrpClaimUserUI = new LLGrpClaimUserUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLGrpClaimUserSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLGrpClaimUserSave","-----transact= "+transact);	   
    
    //##########��ȡҳ����Ϣ########### 
    LLGrpClaimUserSchema tLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
    
	//׼����̨����
	tLLGrpClaimUserSchema.setUserCode(request.getParameter("UserCode")); //�û�����
	tLLGrpClaimUserSchema.setUserName(request.getParameter("UserName")); //�û�����
	
	tLLGrpClaimUserSchema.setComCode(request.getParameter("ComCode")); //��������
	tLLGrpClaimUserSchema.setJobCategory(request.getParameter("JobCategory")); //Ȩ�޼���
	tLLGrpClaimUserSchema.setUpUserCode(request.getParameter("UpUserCode")); //�ϼ��û�����

	tLLGrpClaimUserSchema.setReportFlag(request.getParameter("ReportFlag")); //����Ȩ��
	tLLGrpClaimUserSchema.setRegisterFlag(request.getParameter("RegisterFlag")); //����Ȩ��
	tLLGrpClaimUserSchema.setSubmitFlag(request.getParameter("SubmitFlag")); //�ʱ�Ȩ��
	
	tLLGrpClaimUserSchema.setSurveyFlag(request.getParameter("SurveyFlag")); //����Ȩ��
	tLLGrpClaimUserSchema.setPrepayFlag(request.getParameter("PrepayFlag")); //Ԥ��Ȩ��
	tLLGrpClaimUserSchema.setSimpleFlag(request.getParameter("SimpleFlag")); //���װ���Ȩ��
	
	tLLGrpClaimUserSchema.setCertifyScan(request.getParameter("CertifyScan")); //��֤ɨ��Ȩ��
	tLLGrpClaimUserSchema.setTaskAssign(request.getParameter("TaskAssign")); //�������Ȩ��
	tLLGrpClaimUserSchema.setExemptFlag(request.getParameter("ExemptFlag")); //����Ȩ��

	tLLGrpClaimUserSchema.setCheckFlag(request.getParameter("CheckFlag")); //���Ȩ��
	tLLGrpClaimUserSchema.setCheckLevel(request.getParameter("CheckLevel")); //��˼���
	tLLGrpClaimUserSchema.setUWFlag(request.getParameter("UWFlag")); //����Ȩ��
	tLLGrpClaimUserSchema.setUWLevel(request.getParameter("UWLevel")); //��������
	
	tLLGrpClaimUserSchema.setRgtFlag(request.getParameter("RgtFlag")); //�ڸ�״̬
	tLLGrpClaimUserSchema.setRelPhone(request.getParameter("RelPhone")); //�����绰
	
	
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLGrpClaimUserSchema);    	
		if(!tLLGrpClaimUserUI.submitData(tVData,transact))
		{           
			Content = "�ύʧ�ܣ�ԭ����: " + tLLGrpClaimUserUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";    
		}
		else
		{	    
			Content = "�����ύ�ɹ�";
			FlagStr = "Succ";            
		}
    }
    catch(Exception ex)
    {
        Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    loggerDebug("LLGrpClaimUserSave","------LLGrpClaimUserSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterUserSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
