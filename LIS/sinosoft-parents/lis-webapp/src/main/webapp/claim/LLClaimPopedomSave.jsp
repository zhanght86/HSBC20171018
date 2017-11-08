<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLClaimPopedomSave.jsp
//程序功能：理赔权限信息保存
//创建日期：2005-6-17 11:18
//创建人  ：赵雁
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

<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("hideOperate");    
   
    if(tG == null) 
    {
        loggerDebug("LLClaimPopedomSave","登录信息没有获取!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimPopedomSave","没有获取操作符!!!");
        return;   
    }
    
    //准备数据容器信息
    TransferData tTransferData = new TransferData();    
    
    LLClaimPopedomSchema tLLClaimPopedomSchema = new LLClaimPopedomSchema();   
    tTransferData.setNameAndValue("OriJobCategory",request.getParameter("OriJobCategory"));
    tTransferData.setNameAndValue("OriCaseCategory",request.getParameter("OriCaseCategory"));
    
    
    //理赔权限信息
    if (tOperate.equals("Popedom||INSERT")||tOperate.equals("Popedom||DELETE")||tOperate.equals("Popedom||UPDATE"))
    {
				//准备后台数据
	    	tLLClaimPopedomSchema.setJobCategory(request.getParameter("JobCategory"));
	    	tLLClaimPopedomSchema.setPopedomName(request.getParameter("JobCategoryName"));
	    	tLLClaimPopedomSchema.setPopedomName(request.getParameter("JobCategoryName"));
	    	tLLClaimPopedomSchema.setCaseCategory(request.getParameter("CaseCategory"));
	    	tLLClaimPopedomSchema.setBaseMin(request.getParameter("BaseMin"));
	    	tLLClaimPopedomSchema.setBaseMax(request.getParameter("BaseMax"));
	    	tLLClaimPopedomSchema.setMngCom(request.getParameter("MngCom"));
	    	tLLClaimPopedomSchema.setStartDate(request.getParameter("StartDate"));
	    	tLLClaimPopedomSchema.setEndDate(request.getParameter("EndDate"));
                                      
    }
    //loggerDebug("LLClaimPopedomSave","LLClaimPopedomSave.jsp测试--"+tOperate);
  
    
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLClaimPopedomSchema );
				
        LLClaimPopedomUI tLLClaimPopedomUI = new LLClaimPopedomUI();
       if (tLLClaimPopedomUI.submitData(tVData,tOperate) == false)
        {
          Content = Content + "理赔权限信息保存失败，原因是: " + tLLClaimPopedomUI.mErrors.getError(0).errorMessage;
          FlagStr = "FAIL";
        } else {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }
    

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }
    
//    loggerDebug("LLClaimPopedomSave","LLClaimPopedomSave测试--"+FlagStr);
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterPopedomSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
