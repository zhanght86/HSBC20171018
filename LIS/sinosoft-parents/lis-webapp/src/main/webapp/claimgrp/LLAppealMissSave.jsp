<%
//**************************************************************************************************
//�������ƣ�LLAppealMissSave.jsp
//�����ܣ�
//�������ڣ�2005-7-25 17:29
//������  ��zl
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

//ҳ����Ч��
if(tG == null)
{
    loggerDebug("LLAppealMissSave","ҳ��ʧЧ,���µ�½!");
    return;
}

String tClaimNo = request.getParameter("ClmNo");

//�������
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("ClaimNo", tClaimNo);

//׼���������� VData
VData tVData = new VData();
tVData.add(tG);
tVData.add(mTransferData);

// ���ݴ���
LLAppealApplyUI tLLAppealApplyUI = new LLAppealApplyUI();

if (!tLLAppealApplyUI.submitData(tVData,""))
{
    Content = " ����ʧ�ܣ�ԭ����: " + tLLAppealApplyUI.mErrors.getError(0).errorMessage;
    FlagStr = "Fail";
}
else
{
    tVData.clear();
    tVData = tLLAppealApplyUI.getResult();
    TransferData tTransferData = new TransferData();
    tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
    loggerDebug("LLAppealMissSave","tTransferData:" + tTransferData);
    String mClmNo = (String) tTransferData.getValueByName("clmno");
    loggerDebug("LLAppealMissSave","���ص��ⰸ��Ϊ:" + mClmNo);
%>
<script language="javascript">
    parent.fraInterface.fm.all("ClmNo").value="<%=mClmNo%>";
</script>
<%
    Content = " ����ɹ�! ";
    FlagStr = "Succ";
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
