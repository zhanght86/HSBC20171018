<%
//**************************************************************************************************
//Name��LLClaimConfirmSave.jsp
//Function�����������ύ
//Author��zhoulei
//Date:
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

//�������
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //���������

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
    loggerDebug("LLClaimConfirmSave","ҳ��ʧЧ,�����µ�½");    
}
else
{
	
	
    //��ȡ����insert||update
    String transact = request.getParameter("fmtransact");
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");

    //��ȡ����ҳ����Ϣ
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLClaimUWMainSchema.setExamConclusion(request.getParameter("DecisionSP")); //��������
    tLLClaimUWMainSchema.setExamIdea(request.getParameter("RemarkSP")); //�������
    tLLClaimUWMainSchema.setExamNoPassReason(request.getParameter("ExamNoPassReason")); //��ͨ��ԭ��
    tLLClaimUWMainSchema.setExamNoPassDesc(request.getParameter("ExamNoPassDesc")); //��ͨ������
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "40");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //��������
    mTransferData.setNameAndValue("RgtObj", request.getParameter("RgtObj"));  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RgtObjNo"));  //��������,���մ洢�����ⰸ��
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
    mTransferData.setNameAndValue("RgtState", request.getParameter("RgtState"));  //��������
    
    mTransferData.setNameAndValue("Popedom",request.getParameter("AuditPopedom")); //���Ȩ��
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag")); //Ԥ����־
    mTransferData.setNameAndValue("Auditer", request.getParameter("Auditer")); //�����
    mTransferData.setNameAndValue("ModifyRgtState",request.getParameter("ModifyRgtState")); //���������޸�Modify by zhaorx 2006-04-17
    
    //ת������
    mTransferData.setNameAndValue("Reject", request.getParameter("DecisionSP"));  //�����Ƿ�ͨ��
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("BusiType", "1003");
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);  
    tVData.add(tLLClaimUWMainSchema); 
        
    
	    try
	    {  
	        //�ύ����
	        loggerDebug("LLClaimConfirmSave","-------------------start workflow---------------------");
	        //wFlag = "0000005055";
//	        ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//	        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//	        {
//	            Content = " ����ȷ��ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//	        }
			String busiName="tWorkFlowUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "����ȷ��ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "����ȷ��ʧ��";
					FlagStr = "Fail";				
				}
			}

	        else
	        {
	            Content = " ����ȷ�ϳɹ���";
	            FlagStr = "Succ";
	        }
	        loggerDebug("LLClaimConfirmSave","-------------------end workflow---------------------");
	    }
	    catch(Exception ex)
	    {
	        Content = "����ȷ��ʧ�ܣ�ԭ����:" + ex.toString();
	        FlagStr = "Fail";
	    } 
	
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
