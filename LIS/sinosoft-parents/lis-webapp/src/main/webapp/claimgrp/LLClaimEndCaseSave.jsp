<%
//Name��LLClaimEndCaseSave.jsp
//Function���᰸
//Author��zhoulei
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
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
//LLEndCaseUI tLLEndCaseUI = new LLEndCaseUI();
String busiName="grpLLEndCaseUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();


//ҳ����Ч
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimEndCaseSave","ҳ��ʧЧ,�����µ�½");    
}
else
{
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("RptNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //���ݴ���
//    if (!tLLEndCaseUI.submitData(tVData,transact))
//    {
//        Content = " �����ύLLEndCaseUIʧ�ܣ�ԭ����: " + tLLEndCaseUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "�����ύLLEndCaseUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�����ύLLEndCaseUIʧ��";
		FlagStr = "Fail";				
	}
}

    else
    {
    	Content = " �����ύ�ɹ�! ";
	    FlagStr = "Succ";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
