<%
//**************************************************************************************************
//Name��LLClaimSimpleSave.jsp
//Function�����װ�����Ϣ�ύ
//Author��zhoulei
//Date: 2005-6-21 11:15
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//�������
//LLClaimSchema tLLClaimSchema = new LLClaimSchema(); //�ⰸ��

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//LLClaimSimpleUI tLLClaimSimpleUI = new LLClaimSimpleUI();

//ҳ����Ч
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimSimpleSave","ҳ��ʧЧ,�����µ�½");
}
else
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update

    //**************************************************
    //��������ز���ʹ��TransferData������ύ
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "50");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2"));
    mTransferData.setNameAndValue("RgtClass", "1");  //��������
    mTransferData.setNameAndValue("RgtObj", "1");  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //��������
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
    mTransferData.setNameAndValue("PrepayFlag", "0");  //Ԥ����־
    mTransferData.setNameAndValue("Auditer", "0");  //��˲�����,Ϊ������ͨ��ʱ���ظ��˹���������

    //ת����������
    mTransferData.setNameAndValue("AuditFlag", request.getParameter("SimpleConclusion"));  //��ͨ����Ҫ���

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));

    //ҵ������Ҫ����
    mTransferData.setNameAndValue("PopedomPhase","A"); //Ȩ�޽׶�:���A;
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //���ݴ���
    loggerDebug("LLClaimSimpleSave","-------------------start workflow---------------------");
    String wFlag = "0000005065";
//    ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//    if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//    {
//        Content = " �����ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "�����ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�����ύ������ʧ��";
		FlagStr = "Fail";				
	}
}

    else
    {
        Content = " �����ύ�ɹ���";
        FlagStr = "Succ";
    }
    loggerDebug("LLClaimSimpleSave","-------------------end workflow---------------------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
