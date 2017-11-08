<%
//**************************************************************************************************
//Name��LLClaimAuditSave.jsp
//Function����˹����ύ
//Author��zhoulei
//Date: 2005-6-20 15:40
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
    loggerDebug("LLClaimAuditSave","ҳ��ʧЧ,�����µ�½");
}
else
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "30");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate")); //�¹�����
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //��������
    mTransferData.setNameAndValue("RgtObj", request.getParameter("RgtObj"));  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RgtObjNo"));  //��������,���մ洢�����ⰸ��
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
   // mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag"));
    mTransferData.setNameAndValue("PrepayFlag", "0"); //�Ƿ���Ԥ��
    mTransferData.setNameAndValue("AuditPopedom", request.getParameter("AuditPopedom")); //���Ȩ��
    mTransferData.setNameAndValue("Auditer", Operator); //�����
    //------------------------------------------------------------------------------BEG
    //����Ȩ�ޣ���ƥ��������ƺ����service���ȥ������
    //------------------------------------------------------------------------------
//    mTransferData.setNameAndValue("Popedom","B01"); 
    //------------------------------------------------------------------------------END
    //ת����������
    mTransferData.setNameAndValue("BudgetFlag", "0");  //�Ƿ�Ԥ������'0'��ȥ��Ҫ��������ת�������׶�;
    mTransferData.setNameAndValue("IsRunBL", "1");  //�Ƿ�����BL
    mTransferData.setNameAndValue("PopedomPhase","B"); //Ȩ�޽׶α�ʾA:���B:����
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("BusiType","1003");
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    try
    {
        //�ύ����
        loggerDebug("LLClaimAuditSave","-------------------start workflow---------------------");
        //ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//        {
//            Content = " �����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="tWorkFlowUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�����ύClaimWorkFlowUIʧ��";
				FlagStr = "Fail";				
			}
		}

        else
        {
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimAuditSave","-------------------end workflow---------------------");
    }
    catch(Exception ex)
    {
        Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
