<%
//**************************************************************************************************
//Name��LLClaimEndCaseConfirmSave.jsp
//Function���᰸��Ϣȷ��
//Author��zhoulei
//Date: 2005-6-20 11:34
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
    loggerDebug("LLClaimEndCaseConfirmSave","ҳ��ʧЧ,�����µ�½");
}
else 
{
    //**************************************************
    //��������ز���ʹ��TransferData������ύ
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //���ݴ���
    loggerDebug("LLClaimEndCaseConfirmSave","-------------------start workflow---------------------");
    wFlag = "0000005075";
//    ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//    if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//    {
//        Content = " �����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
String busiName="ClaimWorkFlowUI";
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
    loggerDebug("LLClaimEndCaseConfirmSave","-------------------end workflow---------------------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
