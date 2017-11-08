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
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
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
    loggerDebug("LLGrpSimpleAuditSave","ҳ��ʧЧ,�����µ�½");
}
else
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");
    //��˽���
    String tSimpleConclusion2 = request.getParameter("SimpleConclusion2");
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    if(tSimpleConclusion2.equals("5")||tSimpleConclusion2.equals("0")){
    	if(tSimpleConclusion2.equals("0")){
    		mTransferData.setNameAndValue("RptorState", "50");
    	}else{
        mTransferData.setNameAndValue("RptorState", "20");
    		String MngCom=request.getParameter("mngNo");
    		mTransferData.setNameAndValue("MngCom",MngCom);  //��������
    		String operator=request.getParameter("operator");
    		loggerDebug("LLGrpSimpleAuditSave","operator:"+operator);
    		mTransferData.setNameAndValue("DefaultOperator",operator);  //��������Ա
    	}
    }else{
    mTransferData.setNameAndValue("RptorState", "40");
    }
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("RgtClass", "1");  //��������
    mTransferData.setNameAndValue("RgtObj", "1");  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //��������
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag"));
    mTransferData.setNameAndValue("AuditPopedom", request.getParameter("AuditPopedom")); //���Ȩ��
    mTransferData.setNameAndValue("Auditer", request.getParameter("Operator")); //�����
    mTransferData.setNameAndValue("RgtNo", mRptNo); //�����
 

    //ת����������
    if(tSimpleConclusion2.equals("5")){
    //ԭ����0000005035->0000005025�� ���ڸ�Ϊ��0000005035->0000005015��
    mTransferData.setNameAndValue("BudgetFlag", "1");  //������������ ��1 ������
    }
    else if(tSimpleConclusion2.equals("0")) //||tAuditConclusion.equals("1"))
    {
    	//��0000005035->0000005075��
    	mTransferData.setNameAndValue("Reject", "0");  //������ת������᰸
    	mTransferData.setNameAndValue("BudgetFlag", "");  //������������ ��1 ������
    }
    else
    {
	    //��0000005035->0000005055��
	    mTransferData.setNameAndValue("BudgetFlag", "0");  //������������ ��0 ��������
	}


    mTransferData.setNameAndValue("IsRunBL", "1");  //�Ƿ�����BL
    mTransferData.setNameAndValue("PopedomPhase","B"); //Ȩ�޽׶α�ʾA:���B:����
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009035");
    mTransferData.setNameAndValue("AuditConclusion",tSimpleConclusion2); //��˽���
    mTransferData.setNameAndValue("RgtState","03"); //��������

    //�ŵ������жϻ��˺�ı�ʾ
    if(request.getParameter("RgtClass").equals("1") && tSimpleConclusion2.equals("5")){
    mTransferData.setNameAndValue("OtherOperator", request.getParameter("RgtClass")); //���������(��Ÿ��ձ��)
    }

    String tsql="select rgtstate from llregister where rgtno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLGrpSimpleAuditSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState","03");
    mTransferData.setNameAndValue("AuditConclusion",tSimpleConclusion2);

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    try
    {
        //�ύ����
        loggerDebug("LLGrpSimpleAuditSave","-------------------start workflow---------------------");
        wFlag = "0000009035";
//        ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//        {
//            Content = " �����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
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
        loggerDebug("LLGrpSimpleAuditSave","-------------------end workflow---------------------");
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
