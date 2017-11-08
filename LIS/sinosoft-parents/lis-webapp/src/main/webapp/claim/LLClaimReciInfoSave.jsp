<%
//**************************************************************************************************
//程序名称：LLClaimReciInfoSave.jsp
//程序功能：收件人信息录入后的保存
//创建日期：2005-10-25
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
    loggerDebug("LLClaimReciInfoSave","session has expired");
    return;
}

//给后台传递的参数值
String tClmNo = request.getParameter("ClmNo");             //赔案号
loggerDebug("LLClaimReciInfoSave","ClmNo = " + tClmNo); 
                  
String tReciCode = request.getParameter("ReciCode");       //收件人代码
loggerDebug("LLClaimReciInfoSave","ReciCode = " + tReciCode); 
String tReciName = request.getParameter("ReciName");       //收件人姓名
loggerDebug("LLClaimReciInfoSave","ReciName = " + tReciName);
String tRelation = request.getParameter("Relation");       //收件人与出险人关系
loggerDebug("LLClaimReciInfoSave","Relation = " + tRelation);
String tReciAddress = request.getParameter("ReciAddress"); //收件人地址
loggerDebug("LLClaimReciInfoSave","ReciAddress = " + tReciAddress);
String tReciPhone = request.getParameter("ReciPhone");     //收件人电话
loggerDebug("LLClaimReciInfoSave","ReciPhone = " + tReciPhone);
String tReciMobile = request.getParameter("ReciMobile");   //收件人手机
loggerDebug("LLClaimReciInfoSave","ReciMobile = " + tReciMobile);
String tReciZip = request.getParameter("ReciZip");         //收件人邮编
loggerDebug("LLClaimReciInfoSave","ReciZip = " + tReciZip);
String tReciSex = request.getParameter("ReciSex");         //收件人性别
loggerDebug("LLClaimReciInfoSave","ReciSex = " + tReciSex);
String tReciEmail = request.getParameter("ReciEmail");     //收件人Email
loggerDebug("LLClaimReciInfoSave","ReciEmail = " + tReciEmail);
String tRemark = request.getParameter("Remark");           //收件人细节
loggerDebug("LLClaimReciInfoSave","Remark = " + tRemark);


//打包数据
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("ClmNo", tClmNo);

mTransferData.setNameAndValue("ReciCode", tReciCode);
mTransferData.setNameAndValue("ReciName", tReciName);
mTransferData.setNameAndValue("Relation", tRelation);
mTransferData.setNameAndValue("ReciAddress", tReciAddress);
mTransferData.setNameAndValue("ReciPhone", tReciPhone);
mTransferData.setNameAndValue("ReciMobile", tReciMobile);
mTransferData.setNameAndValue("ReciZip", tReciZip);
mTransferData.setNameAndValue("ReciSex", tReciSex);
mTransferData.setNameAndValue("ReciEmail", tReciEmail);
mTransferData.setNameAndValue("Remark", tRemark);

try
{
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    
    // 数据传输
//    LLClaimReciInfoUI tLLClaimReciInfoUI = new LLClaimReciInfoUI();
//    if (tLLClaimReciInfoUI.submitData(tVData,""))
//    {
//        int n = tLLClaimReciInfoUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            loggerDebug("LLClaimReciInfoSave","Error: "+tLLClaimReciInfoUI.mErrors.getError(i).errorMessage);
//            Content = " 保存失败，原因是: " + tLLClaimReciInfoUI.mErrors.getError(0).errorMessage;
//        }
//        FlagStr = "Fail";
//    }
	String busiName="LLClaimReciInfoUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	    {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
	        	int n = tBusinessDelegate.getCErrors().getErrorCount();
		        for (int i = 0; i < n; i++)
		        {
		            loggerDebug("LLClaimReciInfoSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
		            Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
		        }
	       		FlagStr = "Fail";				   
			}
			else
			{
			   Content = "保存失败";
			   FlagStr = "Fail";				
			} 
	}

    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail")
    {
        //tError = tLLClaimReciInfoUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        loggerDebug("LLClaimReciInfoSave","tError.getErrorCount:"+tError.getErrorCount());
        if (!tError.needDealError())
        {
            Content = " 保存成功! ";
            FlagStr = "Succ";
        }
        else
        {
            Content = " 保存失败，原因是:";
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
