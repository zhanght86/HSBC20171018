<%
	//�������ƣ�LLSecondUWAllSave.jsp
	//�����ܣ������˹��˱���ȡ����
	//�������ڣ�2005-1-28 11:10:36
	//������  ��zhangxing
	//���¼�¼��  ������  yuejw  ��������     ����ԭ��/����
%> 
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
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
    System.out.println("session has expired");
    return;
}

String sMissionID = request.getParameter("MissionID");
String sSubMissionID = request.getParameter("SubMissionID");
String sActivityID = request.getParameter("ActivityID");

//�������
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("MissionID", sMissionID);
mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
mTransferData.setNameAndValue("ActivityID", sActivityID);

try
{
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    
    // ���ݴ���
    LLSecondUWApplyUI tLLSecondUWApplyUI = new LLSecondUWApplyUI();
    if (tLLSecondUWApplyUI.submitData(tVData,""))
    {
        int n = tLLSecondUWApplyUI.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            System.out.println("Error: "+tLLSecondUWApplyUI.mErrors.getError(i).errorMessage);
            Content = " ����ʧ�ܣ�ԭ����: " + tLLSecondUWApplyUI.mErrors.getError(0).errorMessage;
        }
        FlagStr = "Fail";
    }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
        tError = tLLSecondUWApplyUI.mErrors;
        System.out.println("tError.getErrorCount:"+tError.getErrorCount());
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
