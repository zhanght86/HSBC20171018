<%
//**************************************************************************************************
//Name：LLRegisterSave.jsp
//Function：立案信息提交
//Author：zhoulei
//Date: 2005-6-15 16:28
//修改：niuzj,2006-01-13,立案登记时需要将LLRegister表中的领取方式GetMode字段默认置成1
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
loggerDebug("LLClaimRepRegRelaSave","######################LLClaimRegisterSave.jsp start#############################");

//输入参数
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //立案表

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");


if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimRepRegRelaSave","页面失效,请重新登陆");
}
else //页面有效
{

	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    tLLRegisterSchema.setRgtNo(request.getParameter("ClmNo")); // 立案号
    tLLRegisterSchema.setRgtObjNo(request.getParameter("RptNo")); // 报案号
    tLLRegisterSchema.setRgtObj("1"); //报案人姓名

    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLRegisterSchema);


    //第一次保存提交时，提交到工作流引擎，其他的直接提交到业务类,当提交工作流时wFlag=9899999999

        try
        {
        	String transact = "INSERT";
            //数据提交
            loggerDebug("LLClaimRepRegRelaSave","---LLClaimRepRegRelaBL submitData and transact="+transact);
//            LLClaimRepRegRelaUI tLLClaimRepRegRelaUI = new LLClaimRepRegRelaUI();
//            
//            if (!tLLClaimRepRegRelaUI.submitData(tVData,transact))
//            {
//                Content = " 关联失败，原因是: " + tLLClaimRepRegRelaUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
		String busiName="LLClaimRepRegRelaUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "关联失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "关联失败";
						   FlagStr = "Fail";				
				}
		   }

            else
            {
                tVData.clear();
                Content = " 数据提交成功！";
                FlagStr = "Succ";
            }
        }
        catch(Exception ex)
        {
            Content = "数据提交LLClaimRegisterUI失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }


}

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit5("<%=FlagStr%>","<%=Content%>");
</script>
</html>
