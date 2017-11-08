<%
//**************************************************************************************************
//Name��LLReportConfirmSave.jsp
//Function��������Ϣȷ��
//Author��zhoulei
//Date: 2005-6-14 8:42
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
CErrors tError = null;
String FlagStr="";
String Content = "";
String wFlag = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLReportConfirmSave","ҳ��ʧЧ,�����µ�½");
}
else 
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String GrpCustomerNo = request.getParameter("GrpCustomerNo"); //����ͻ���
    String trgtstate[] = request.getParameterValues("rgtstate"); //��������
    //**************************************************
    //��������ز���ʹ��TransferData������ύ
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RgtNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "10");
    if(GrpCustomerNo!=null&&!GrpCustomerNo.equals("")){
//    mTransferData.setNameAndValue("RptorName", request.getParameter("RptorName")); //����������
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("GrpCustomerNo")); //����ͻ���
    mTransferData.setNameAndValue("CustomerName", request.getParameter("GrpName")); //��λ����
    mTransferData.setNameAndValue("CustomerSex", ""); //�������Ա�
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate")); //��������
    mTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo")); //���屣����
    }else{
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("GrpContNo", "");
    }
    mTransferData.setNameAndValue("OtherOperator", "");
    mTransferData.setNameAndValue("OtherFlag", "0");
    mTransferData.setNameAndValue("MngCom", ManageCom);
    if (trgtstate==null)
    {         
      mTransferData.setNameAndValue("RgtState", ""); //��������
    }    
     else 
    {    
      for (int i = 0; i < trgtstate.length; i++)
      {        
        mTransferData.setNameAndValue("RgtState", trgtstate[i]); //��������  
        loggerDebug("LLReportConfirmSave","RgtState:"+trgtstate[i]);     
      }       
    } 
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));

loggerDebug("LLReportConfirmSave","MissionID"+request.getParameter("MissionID"));
loggerDebug("LLReportConfirmSave","SubMissionID"+request.getParameter("SubMissionID"));

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //���ݴ���
    loggerDebug("LLReportConfirmSave","-------------------start workflow---------------------");
    wFlag = "0000009005";
//    ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//    if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//    {
//        Content = " �����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
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
    loggerDebug("LLReportConfirmSave","-------------------end workflow---------------------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
