<%
//**************************************************************************************************
//程序名称：LLReportMissSave.jsp
//程序功能：
//创建日期：2005-6-14 18:13
//创建人  ：zl
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
    loggerDebug("LLReportMissSave","session has expired");
    return;
}

String sMissionID = request.getParameter("MissionID");
String sSubMissionID = request.getParameter("SubMissionID");
String sActivityID = request.getParameter("ActivityID");

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
//    ClaimApplyUI tClaimApplyUI = new ClaimApplyUI();
//    if (tClaimApplyUI.submitData(tVData,""))
//    {
//        int n = tClaimApplyUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            loggerDebug("LLReportMissSave","Error: "+tClaimApplyUI.mErrors.getError(i).errorMessage);
//            Content = " 申请失败，原因是: " + tClaimApplyUI.mErrors.getError(0).errorMessage;
//        }
//        FlagStr = "Fail";
//    }
	String busiName="ClaimApplyUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	    {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
	        	int n = tBusinessDelegate.getCErrors().getErrorCount();
		        for (int i = 0; i < n; i++)
		        {
		            //loggerDebug("LLReportMissSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
        //tError = tClaimApplyUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        loggerDebug("LLReportMissSave","tError.getErrorCount:"+tError.getErrorCount());
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
