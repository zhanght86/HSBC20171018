<%
//**************************************************************************************************
//�������ƣ�LLClaimPrepayCofirmSave.jsp
//�����ܣ���Ԥ��ȷ�ϡ��ύ��Ԥ���ڵ����������ɴ���˽ڵ�
//�������ڣ�2005-07-08 9:16
//������  ��yuejw
//���¼�¼��
//**************************************************************************************************
%>

<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//ҳ����Ч��
if(tG == null)
{
    loggerDebug("LLClaimPrepayCofirmSave","session has expired");
    return;
}

//�������,����������Ԥ���ڵ㡱�����ɴ���˽ڵ㣬����ص�����˹���������
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("RptNo",request.getParameter("tRptNo")); //"�ⰸ��" 
mTransferData.setNameAndValue("RptorState",request.getParameter("tRptorState")); //"����״̬"
mTransferData.setNameAndValue("CustomerNo",request.getParameter("tCustomerNo")); //"�����˱���"
mTransferData.setNameAndValue("CustomerName",request.getParameter("tCustomerName")); //"���������� 
mTransferData.setNameAndValue("CustomerSex",request.getParameter("tCustomerSex")); //"�������Ա�"
mTransferData.setNameAndValue("AccDate",request.getParameter("tAccDate")); //"��������" 
mTransferData.setNameAndValue("RgtClass",request.getParameter("tRgtClass")); //"��������" 
mTransferData.setNameAndValue("RgtObj",request.getParameter("tRgtObj")); //"��������" 
mTransferData.setNameAndValue("RgtObjNo",request.getParameter("tRgtObjNo")); //"��������" 
mTransferData.setNameAndValue("Popedom",request.getParameter("tPopedom")); //"����ʦȨ��" 
mTransferData.setNameAndValue("PrepayFlag",request.getParameter("tPrepayFlag")); //"Ԥ����־" 
mTransferData.setNameAndValue("ComeWhere",request.getParameter("tComeWhere")); //"����" 
mTransferData.setNameAndValue("Auditer", request.getParameter("tAuditer")); //��˲�����
mTransferData.setNameAndValue("MngCom",request.getParameter("tMngCom")); //"����" 
mTransferData.setNameAndValue("ComFlag",request.getParameter("tComFlag")); //"Ȩ�޿缶��־" 
mTransferData.setNameAndValue("MissionID",request.getParameter("tMissionID"));
mTransferData.setNameAndValue("SubMissionID",request.getParameter("tSubMissionID"));
mTransferData.setNameAndValue("ActivityID",request.getParameter("tActivityID"));

try
{
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    try
    {
        if(!tClaimWorkFlowUI.submitData(tVData,"0000005025"))
        {
            Content = " �ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
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
}
catch(Exception ex)
{
    Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
}
loggerDebug("LLClaimPrepayCofirmSave","------LLClaimPrepayMissSave.jsp end------");
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
