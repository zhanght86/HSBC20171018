<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLRgtMAffixListSave.jsp
//程序功能：单证回销
//创建日期：2005-05-25 12:06
//创建人  ：yuejw
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("Operate");
    if(tG == null)
    {
        //loggerDebug("LLIssueReplySave","登录信息没有获取!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        //loggerDebug("LLIssueReplySave","没有获取操作符!!!");
        return;
    }
    String tClmNo=request.getParameter("ClmNo");
    String tAutditno=request.getParameter("Autditno");
    String tIssueReplyConclusion=request.getParameter("IssueReplyConclusion");


   //输入参数
   LLRegisterIssueSchema tLLRegisterIssueSchema = new LLRegisterIssueSchema(); //案件核赔表
	
	 tLLRegisterIssueSchema.setRgtNo(tClmNo); //赔案号
	 tLLRegisterIssueSchema.setAutditNo(tAutditno);
	 tLLRegisterIssueSchema.setIssueReplyConclusion(tIssueReplyConclusion); //问题件回销结论
	
         
    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tOperate );
        tVData.add(tLLRegisterIssueSchema);

//        LLIssueReplyUI tLLIssueReplyUI = new LLIssueReplyUI();
//       if (tLLIssueReplyUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLIssueReplyUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "单证信息保存错误，原因是: " + tLLIssueReplyUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
         String busiName="LLIssueReplyUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLIssueReplySave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = Content + "单证信息保存错误，原因是: " +tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "保存失败";
				   FlagStr = "Fail";				
				} 
		}
         
        else 
        {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
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
