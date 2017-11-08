<%
//**************************************************************************************************
//Name��LLClaimAuditConclusionSave.jsp
//Function����˽����ύ
//Author��zhoulei
//Date: 2005-7-2 11:37
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//�������
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //���������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimAuditConclusionSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    //��ȡ������
    String transact = request.getParameter("fmtransact");

    //��ȡ����ҳ����Ϣ
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLClaimUWMainSchema.setCaseNo(request.getParameter("RptNo")); //�ְ���
    tLLClaimUWMainSchema.setRgtNo(request.getParameter("RptNo")); //������
    tLLClaimUWMainSchema.setAuditConclusion(request.getParameter("AuditConclusion")); //��˽���
    tLLClaimUWMainSchema.setAuditIdea(request.getParameter("AuditIdea")); //������
    tLLClaimUWMainSchema.setSpecialRemark(request.getParameter("SpecialRemark1")); //���ⱸע
    tLLClaimUWMainSchema.setAuditNoPassReason(request.getParameter("ProtestReason")); //��˲�ͨ��ԭ��
    tLLClaimUWMainSchema.setAuditNoPassDesc(request.getParameter("ProtestReasonDesc")); //��˲�ͨ������
    tLLClaimUWMainSchema.setCheckType("0"); //0--��˽���,1--Ԥ����˽���
    TransferData tTransferData = new TransferData(); //Modify by zhaorx 2006-04-17
    tTransferData.setNameAndValue("ModifyRgtState",request.getParameter("ModifyRgtState")); //���������޸�

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLClaimUWMainSchema);
    tVData.add(tTransferData);

    try
    {
        //�ύ����
        loggerDebug("LLClaimAuditConclusionSave","-------------------start LLClaimAuditConclusionSave.jsp---------------------");
        //LLClaimAuditUI tLLClaimAuditUI = new LLClaimAuditUI();
//        if(!tLLClaimAuditUI.submitData(tVData,transact))
//        {
//            Content = " �����ύLLClaimAuditUIʧ�ܣ�ԭ����: " + tLLClaimAuditUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="LLClaimAuditUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "�����ύLLClaimAuditUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�����ύLLClaimAuditUIʧ��";
		FlagStr = "Fail";				
	}
}

        else
        {
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimAuditConclusionSave","-------------------end LLClaimAuditConclusionSave.jsp---------------------");
    }
    catch(Exception ex)
    {
        Content = "�����ύLLClaimAuditUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
