<%
	//�������ƣ�LLSecondUWAllSave.jsp
	//�����ܣ������˹��˱���ȡ����
	//�������ڣ�2005-1-28 11:10:36
	//������  ��zhangxing
	//���¼�¼��  ������  yuejw  ��������     ����ԭ��/����
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
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
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
    loggerDebug("LLSecondUWAllSave","session has expired");
    return;
}

String sMissionID = request.getParameter("MissionID");
String sSubMissionID = request.getParameter("SubMissionID");
String sActivityID = request.getParameter("ActivityID");
String sBatNo=request.getParameter("SBatNo");
String sCaseNo=request.getParameter("SCaseNo");

//�������
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("MissionID", sMissionID);
mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
mTransferData.setNameAndValue("ActivityID", sActivityID);
mTransferData.setNameAndValue("BatNo", sBatNo);
mTransferData.setNameAndValue("CaseNo", sCaseNo);

try
{
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    
    // ���ݴ���
   // LLSecondUWApplyUI tLLSecondUWApplyUI = new LLSecondUWApplyUI();
   String busiName="claimLLSecondUWApplyUI";
   busiName="tWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if (tBusinessDelegate.submitData(tVData,"apply",busiName))
    {
        int n = tBusinessDelegate.getCErrors().getErrorCount();
        for (int i = 0; i < n; i++)
        {
            loggerDebug("LLSecondUWAllSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
            Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
        }
        FlagStr = "Fail";
    }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
        tError = tBusinessDelegate.getCErrors();
        loggerDebug("LLSecondUWAllSave","tError.getErrorCount:"+tError.getErrorCount());
        if (!tError.needDealError())
        {
            Content = " ����ɹ�! ";
            FlagStr = "Succ";
        }
        else
        {
            Content = " ����ʧ�ܣ�ԭ����:";
            int n = tError.getErrorCount();
            if (n > 0)
            {
                for(int i = 0;i < n;i++)
                {
                    Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
                }
            }
            FlagStr = "Fail";
        }
    }
}
catch(Exception e)
{
    e.printStackTrace();
    Content = Content.trim()+".��ʾ���쳣��ֹ!";
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
