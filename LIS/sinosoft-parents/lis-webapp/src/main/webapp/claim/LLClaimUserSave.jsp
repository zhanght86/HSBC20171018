<%
//�������ƣ�LLClaimUserSave.jsp
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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>

<%
    //׼��ͨ�ò���
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LLClaimUserUI tLLClaimUserUI = new LLClaimUserUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimUserSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLClaimUserSave","-----transact= "+transact);	   
    
    //##########��ȡҳ����Ϣ########### 
    LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
    
	//׼����̨����
	tLLClaimUserSchema.setUserCode(request.getParameter("UserCode")); //�û�����
	tLLClaimUserSchema.setUserName(request.getParameter("UserName")); //�û�����
	
	tLLClaimUserSchema.setComCode(request.getParameter("ComCode")); //��������
	tLLClaimUserSchema.setJobCategory(request.getParameter("JobCategory")); //Ȩ�޼���
	tLLClaimUserSchema.setUpUserCode(request.getParameter("UpUserCode")); //�ϼ��û�����

	tLLClaimUserSchema.setReportFlag(request.getParameter("ReportFlag")); //����Ȩ��
	tLLClaimUserSchema.setRegisterFlag(request.getParameter("RegisterFlag")); //����Ȩ��
	tLLClaimUserSchema.setSubmitFlag(request.getParameter("SubmitFlag")); //�ʱ�Ȩ��
	
	tLLClaimUserSchema.setSurveyFlag(request.getParameter("SurveyFlag")); //����Ȩ��
	tLLClaimUserSchema.setPrepayFlag(request.getParameter("PrepayFlag")); //Ԥ��Ȩ��
	tLLClaimUserSchema.setSimpleFlag(request.getParameter("SimpleFlag")); //���װ���Ȩ��
	
	tLLClaimUserSchema.setCertifyScan(request.getParameter("CertifyScan")); //��֤ɨ��Ȩ��
	tLLClaimUserSchema.setTaskAssign(request.getParameter("TaskAssign")); //�������Ȩ��
	tLLClaimUserSchema.setExemptFlag(request.getParameter("ExemptFlag")); //����Ȩ��

	tLLClaimUserSchema.setCheckFlag(request.getParameter("CheckFlag")); //���Ȩ��
	tLLClaimUserSchema.setCheckLevel(request.getParameter("CheckLevel")); //��˼���
	tLLClaimUserSchema.setUWFlag(request.getParameter("UWFlag")); //����Ȩ��
	tLLClaimUserSchema.setUWLevel(request.getParameter("UWLevel")); //��������
	
	tLLClaimUserSchema.setRgtFlag(request.getParameter("RgtFlag")); //�ڸ�״̬
	tLLClaimUserSchema.setRelPhone(request.getParameter("RelPhone")); //�����绰
	
	
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLClaimUserSchema);    	
		if(!tLLClaimUserUI.submitData(tVData,transact))
		{           
			Content = "�ύʧ�ܣ�ԭ����: " + tLLClaimUserUI.mErrors.getError(0).errorMessage;
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
    loggerDebug("LLClaimUserSave","------LLClaimUserSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterUserSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
