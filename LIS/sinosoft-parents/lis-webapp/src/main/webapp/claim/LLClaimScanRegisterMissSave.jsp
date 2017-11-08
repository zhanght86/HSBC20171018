<%
//**************************************************************************************************
//�������ƣ�LLReportMissSave.jsp
//�����ܣ�
//�������ڣ�2005-6-14 18:13
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
    loggerDebug("LLClaimScanRegisterMissSave","session has expired");
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
    
    //// ���ݴ���
//    ClaimScanApplyUI tClaimScanApplyUI = new ClaimScanApplyUI();
//    if (tClaimScanApplyUI.submitData(tVData,""))
//    {
//        int n = tClaimScanApplyUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            loggerDebug("LLClaimScanRegisterMissSave","Error: "+tClaimScanApplyUI.mErrors.getError(i).errorMessage);
//            Content = " ����ʧ�ܣ�ԭ����: " + tClaimScanApplyUI.mErrors.getError(0).errorMessage;
//        }
//        FlagStr = "Fail";
//    }
	String busiName="ClaimScanApplyUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,"",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            loggerDebug("LLClaimScanRegisterMissSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   Content = "����ʧ��";
		   FlagStr = "Fail";				
		}
   }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
    	//tError = tClaimScanApplyUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        loggerDebug("LLClaimScanRegisterMissSave","tError.getErrorCount:"+tError.getErrorCount());
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
