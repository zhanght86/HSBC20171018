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
        loggerDebug("LLClaimContPolDealSave","登录信息没有获取!!!");
       return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimContPolDealSave","没有获取操作符!!!");
       return;   
    }
    
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("CustNo"));
    tTransferData.setNameAndValue("ConType",request.getParameter("ConType"));  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));    
    tTransferData.setNameAndValue("PolNo",request.getParameter("PolNo"));        
    tTransferData.setNameAndValue("ContStateReason",request.getParameter("PolStateReason"));
    tTransferData.setNameAndValue("ContEndDate",request.getParameter("ContEndDate"));    
            


    //loggerDebug("LLClaimContPolDealSave",request.getParameter("CustNo"));
              
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

//        LLClaimContPolDealUI tLLClaimContPolDealUI = new LLClaimContPolDealUI();
//       if (tLLClaimContPolDealUI.submitData(tVData,tOperate) == false)
//        {
//    
//        } else {
//            Content = " 保存成功! ";
//            FlagStr = "SUCC";
//        }
//        int n = tLLClaimContPolDealUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//                Content = Content + "原因是: " + tLLClaimContPolDealUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//        }  
String busiName="grpLLClaimContPolDealUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLClaimContPolDealSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content +" 原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   
		   FlagStr = "Fail";				
		} 
}
else {
           Content = " 保存成功! ";
            FlagStr = "SUCC";
}

    

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }
    
//    loggerDebug("LLClaimContPolDealSave","LLMedicalFeeInpSave测试--"+FlagStr);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
