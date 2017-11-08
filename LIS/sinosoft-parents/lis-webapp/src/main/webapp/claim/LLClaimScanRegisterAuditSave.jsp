<%
//**************************************************************************************************
//Name��LLClaimScanRegisterAuditSave.jsp
//Function��������۱���
//Author��zhangyang
//Date: 2010-5-12 11:37
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
<%@page import="com.sinosoft.service.*" %>

<%

	//�������
	LLRegisterIssueSchema tLLRegisterIssueSchema = new LLRegisterIssueSchema(); //���������

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
    loggerDebug("LLClaimScanRegisterAuditSave","ҳ��ʧЧ,�����µ�½");
	}
	else
	{

   	//��ȡ������
   	String transact = request.getParameter("Operate");

    	//��ȡ����ҳ����Ϣ
    tLLRegisterIssueSchema.setRgtNo(request.getParameter("ClmNo")); //�ⰸ��
    tLLRegisterIssueSchema.setIssueConclusion(request.getParameter("IssueConclusion")); //�������
    tLLRegisterIssueSchema.setIssueReason(request.getParameter("AccDesc")); 
    tLLRegisterIssueSchema.setIssueStage("1"); //�˻ؽ׶� 1-���� 2-���� 
    
    

    String sMissionID = request.getParameter("MissionID");
    String sSubMissionID = request.getParameter("SubMissionID");
    String sActivityID = request.getParameter("ActivityID");
  //�������
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("MissionID", sMissionID);
    mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
    mTransferData.setNameAndValue("ActivityID", sActivityID);

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLRegisterIssueSchema);
    tVData.add(mTransferData);

    try
    {
        //�ύ����
        loggerDebug("LLClaimScanRegisterAuditSave","-------------------start LLClaimAuditConclusionSave.jsp---------------------");
//        LLClaimScanIssueConclusionUI tLLClaimScanIssueConclusionUI = new LLClaimScanIssueConclusionUI();
//        if(!tLLClaimScanIssueConclusionUI.submitData(tVData,transact))
//        {
//            Content = " �����ύLLClaimAuditUIʧ�ܣ�ԭ����: " + tLLClaimScanIssueConclusionUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
        String busiName="LLClaimScanIssueConclusionUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		   if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "�����ύLLClaimAuditUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
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
        loggerDebug("LLClaimScanRegisterAuditSave","-------------------end LLClaimAuditConclusionSave.jsp---------------------");
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
