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
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
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
    
    //**************************************************
    //��������ز���ʹ��TransferData������ύ
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "10");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("OtherOperator", "");
    mTransferData.setNameAndValue("OtherFlag", "0");
    mTransferData.setNameAndValue("MngCom", ManageCom);
    mTransferData.setNameAndValue("RgtNo", request.getParameter("RptNo"));
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    //���ݴ���
    loggerDebug("LLReportConfirmSave","-------------------start workflow---------------------");
    wFlag = "reportconfirm";
//    ClaimReportConfirmBL tClaimReportConfirmBL = new ClaimReportConfirmBL();
//    if(!tClaimReportConfirmBL.submitData(tVData,wFlag))
//    {
//        Content = " �����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimReportConfirmBL.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
 String busiName="ClaimReportConfirmBL";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

	if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
	   {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
					   Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
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
