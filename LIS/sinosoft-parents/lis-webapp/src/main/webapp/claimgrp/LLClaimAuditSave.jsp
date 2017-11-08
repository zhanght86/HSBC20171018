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
    loggerDebug("LLClaimAuditSave","ҳ��ʧЧ,�����µ�½");
}
else
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
		String mRptNo=request.getParameter("RptNo");
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    //��˽���
    String tAuditConclusion = request.getParameter("pAuditConclusion");
    //��ѯ�����ͬ�ţ�Ϊ��������׼�������ֶ�
    String tGrpSql = " select grpcontno from llreport where rptno='"+mRptNo+"'";
    ExeSQL tExeSQL = new ExeSQL();
    String tGrpContNo = tExeSQL.getOneValue(tGrpSql);
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RgtNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("GrpContNo", tGrpContNo);
    
    if(tAuditConclusion.equals("5"))
    {
    		mTransferData.setNameAndValue("RptorState", "20");
    		String MngCom=request.getParameter("mngNo");
    		mTransferData.setNameAndValue("MngCom",MngCom);  //��������
    		String operator=request.getParameter("operator");
    		loggerDebug("LLClaimAuditSave","operator:"+operator);
    		mTransferData.setNameAndValue("DefaultOperator",operator);  //��������Ա
    }
    else
    {
    		mTransferData.setNameAndValue("RptorState", "40");
    		mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
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
    mTransferData.setNameAndValue("Auditer", request.getParameter("tOperator")); //�����
    mTransferData.setNameAndValue("adjpay", request.getParameter("adjpay"));

    //ת����������
    if(tAuditConclusion.equals("5")){
    //ԭ����0000005035->0000005025�� ���ڸ�Ϊ��0000005035->0000005015��
    mTransferData.setNameAndValue("BudgetFlag", "1");  //������������ ��1 ������
    }
    else if(tAuditConclusion.equals("0")) //||tAuditConclusion.equals("1"))
    {
    	//��0000005035->0000005075��
    	mTransferData.setNameAndValue("Reject", "0");  //������ת������᰸
    	mTransferData.setNameAndValue("BudgetFlag", "");  //������������ ��1 ������
    }
    else
    {
	    //��0000005035->0000005055��
	    mTransferData.setNameAndValue("BudgetFlag", "0");  
	}
    
    mTransferData.setNameAndValue("IsRunBL", "1");  //�Ƿ�����BL
    mTransferData.setNameAndValue("PopedomPhase","B"); //Ȩ�޽׶α�ʾA:���B:����
    
    loggerDebug("LLClaimAuditSave","��������:"+request.getParameter("mRgtState"));
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009035");
    mTransferData.setNameAndValue("AuditConclusion",tAuditConclusion); //��˽���
    mTransferData.setNameAndValue("RgtState",request.getParameter("mRgtState")); //��������

    //�ŵ������жϻ��˺�ı�ʾ
    if(request.getParameter("RgtClass").equals("1") && tAuditConclusion.equals("5")){
    mTransferData.setNameAndValue("OtherOperator", request.getParameter("RgtClass")); //���������(��Ÿ��ձ��)
    }
		
		
		
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    try
    {
        //�ύ����
        loggerDebug("LLClaimAuditSave","-------------------start workflow---------------------");
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
