<%
//**************************************************************************************************
//Name：LLClaimContDealSave.jsp
//Function：结论保存并理算
//Author：续涛
//Date:   2005-07-14
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
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

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
        loggerDebug("LLClaimContPolDealAutoSave","登录信息没有获取!!!");
       return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimContPolDealAutoSave","没有获取操作符!!!");
       return;   
    }
    
        loggerDebug("LLClaimContPolDealAutoSave","合同自动处理");
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("ConType",request.getParameter("ConType"));  
  
                  

              
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

//        LLClaimContPolDealAutoUI tLLClaimContPolDealAutoUI = new LLClaimContPolDealAutoUI();
//       if (tLLClaimContPolDealAutoUI.submitData(tVData,tOperate) == false)
//        {
//    
//        } else {
//            Content = " 保存成功! ";
//            FlagStr = "SUCC";
//        }
//        int n = tLLClaimContPolDealAutoUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//                Content = Content + "原因是: " + tLLClaimContPolDealAutoUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//        }  
String busiName="grpLLClaimContPolDealAutoUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLClaimContPolDealAutoSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		  
		   FlagStr = "Fail";				
		} 
}else {
           Content = " 保存成功! ";
          FlagStr = "SUCC";
}

    

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }
    
//    loggerDebug("LLClaimContPolDealAutoSave","LLMedicalFeeInpSave测试--"+FlagStr);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
