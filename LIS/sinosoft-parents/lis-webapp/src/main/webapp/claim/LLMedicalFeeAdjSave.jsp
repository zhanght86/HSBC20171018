<%
//**************************************************************************************************
//Name��LLMedicalFeeAdjSave.jsp
//Function��ҽ�Ʒ��õ�����ʾ��Ϣ
//Author��quyang
//Date: 2005-7-05 
//Desc: 
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
loggerDebug("LLMedicalFeeAdjSave","#########################LLMedicalFeeAdjSave.jsp start#################################");
//�������
LLClaimDutyFeeSchema tLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema(); //���η���ͳ��


//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//LLMedicalFeeAdjUI tLLMedicalFeeAdjUI = new LLMedicalFeeAdjUI(); //ҽ�Ʒ��õ�����ʾ��Ϣ�޸��ύ��
//String busiName="ClaimWorkFlowUI";
//BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

String busiName="LLMedicalFeeAdjUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLMedicalFeeAdjSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    //**********************************************************************************************
    //��ȡ����ҳ����Ϣ�������ֶ���schema�����String����ɢ����ʹ��TransferData���
    //**********************************************************************************************

    //��ȡ��������insert||first��insert||customer��update��
    String transact = request.getParameter("fmtransact");
    
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    
	//��������
    String operationType = request.getParameter("OperationType");

	loggerDebug("LLMedicalFeeAdjSave","operationType"+operationType);

	if("A".equalsIgnoreCase(operationType) || "B".equalsIgnoreCase(operationType))
	{
		tLLClaimDutyFeeSchema.setAdjSum(request.getParameter("AdjSum")); //�������
		tLLClaimDutyFeeSchema.setAdjReason(request.getParameter("AdjReason")); //����ԭ��
		tLLClaimDutyFeeSchema.setAdjRemark(request.getParameter("AdjRemark")); //������ע
		tLLClaimDutyFeeSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
		tLLClaimDutyFeeSchema.setDutyFeeType(request.getParameter("DutyFeeType")); //��������
		tLLClaimDutyFeeSchema.setDutyFeeCode(request.getParameter("DutyFeeCode")); //���ô���
		tLLClaimDutyFeeSchema.setDutyFeeStaNo(request.getParameter("DutyFeeStaNo")); //���
    
	}
	else if("C".equalsIgnoreCase(operationType))
	{

		tLLClaimDutyFeeSchema.setRealRate(request.getParameter("RealRate")); //ʵ�ʸ�������
		tLLClaimDutyFeeSchema.setAdjReason(request.getParameter("AdjReason1")); //����ԭ��
		tLLClaimDutyFeeSchema.setAdjRemark(request.getParameter("AdjRemark1")); //������ע
		tLLClaimDutyFeeSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
		tLLClaimDutyFeeSchema.setDutyFeeType(request.getParameter("DutyFeeType")); //��������
		tLLClaimDutyFeeSchema.setDutyFeeCode(request.getParameter("DutyFeeCode")); //���ô���
		tLLClaimDutyFeeSchema.setDutyFeeStaNo(request.getParameter("DutyFeeStaNo")); //���

	}
	else if("F".equalsIgnoreCase(operationType) || "D".equalsIgnoreCase(operationType) || "E".equalsIgnoreCase(operationType))
	{
		tLLClaimDutyFeeSchema.setAdjSum(request.getParameter("AdjSum1")); //�������
		tLLClaimDutyFeeSchema.setAdjReason(request.getParameter("AdjReason2")); //����ԭ��
		tLLClaimDutyFeeSchema.setAdjRemark(request.getParameter("AdjRemark2")); //������ע
		tLLClaimDutyFeeSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
		tLLClaimDutyFeeSchema.setDutyFeeType(request.getParameter("DutyFeeType")); //��������
		tLLClaimDutyFeeSchema.setDutyFeeCode(request.getParameter("DutyFeeCode")); //���ô���
		tLLClaimDutyFeeSchema.setDutyFeeStaNo(request.getParameter("DutyFeeStaNo")); //���
	}

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLClaimDutyFeeSchema);


    // ���ݴ���
//	if (!tLLMedicalFeeAdjUI.submitData(tVData,""))
//	{
//
//        Content = " ����ʧ�ܣ�ԭ����: " + tLLMedicalFeeAdjUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//	}
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "����ʧ��";
			FlagStr = "Fail";				
		}
	}

	else
	{
	    Content = " ����ɹ�! ";
    	FlagStr = "Succ";
	}
}
%>


<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
