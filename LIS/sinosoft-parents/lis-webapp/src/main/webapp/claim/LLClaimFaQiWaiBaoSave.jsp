<%
//**************************************************************************************************
//程序名称：LLClaimFaQiWaiBaoSave.jsp
//程序功能：发起外包录入时,更改外包录入标志missionprop11为1
//创建日期：2005-10-26
//创建人  ：niuzj
//更新记录：
//**************************************************************************************************
%>

<!--用户校验类-->
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

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

//页面有效性
if(tG == null)
{
    loggerDebug("LLClaimFaQiWaiBaoSave","session has expired");
    return;
}

String sMissionID = request.getParameter("MissionID");
loggerDebug("LLClaimFaQiWaiBaoSave","MissionID = " + sMissionID);
String sSubMissionID = request.getParameter("SubMissionID");
loggerDebug("LLClaimFaQiWaiBaoSave","SubMissionID = " + sSubMissionID);
String sActivityID = request.getParameter("ActivityID");
loggerDebug("LLClaimFaQiWaiBaoSave","ActivityID = " + sActivityID);

//打包数据
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("MissionID", sMissionID);
mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
mTransferData.setNameAndValue("ActivityID", sActivityID);

try
{
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    
    // 数据传输
//    LLClaimFaQiWaiBaoUI tLLClaimFaQiWaiBaoUI = new LLClaimFaQiWaiBaoUI();
//    if (tLLClaimFaQiWaiBaoUI.submitData(tVData,""))
//    {
//        int n = tLLClaimFaQiWaiBaoUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            loggerDebug("LLClaimFaQiWaiBaoSave","Error: "+tLLClaimFaQiWaiBaoUI.mErrors.getError(i).errorMessage);
//            Content = " 申请失败，原因是: " + tLLClaimFaQiWaiBaoUI.mErrors.getError(0).errorMessage;
//        }
//        FlagStr = "Fail";
//    }
	String busiName="LLClaimFaQiWaiBaoUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	    {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
	        	int n = tBusinessDelegate.getCErrors().getErrorCount();
		        for (int i = 0; i < n; i++)
		        {
		            //loggerDebug("LLClaimFaQiWaiBaoSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
		            Content = " 申请失败，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
		        }
	       		FlagStr = "Fail";				   
			}
			else
			{
			   Content = "申请失败";
			   FlagStr = "Fail";				
			} 
	}

    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail")
    {
        //tError = tLLClaimFaQiWaiBaoUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        loggerDebug("LLClaimFaQiWaiBaoSave","tError.getErrorCount:"+tError.getErrorCount());
        if (!tError.needDealError())
        {
            Content = " 申请成功! ";
            FlagStr = "Succ";
        }
        else
        {
            Content = " 申请失败，原因是:";
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
    Content = Content.trim()+".提示：异常终止!";
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
