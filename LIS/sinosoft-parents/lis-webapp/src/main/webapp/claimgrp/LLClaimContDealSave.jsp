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
        loggerDebug("LLClaimContDealSave","登录信息没有获取!!!");
       return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimContDealSave","没有获取操作符!!!");
       return;   
    }
    
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("CustNo"));
    tTransferData.setNameAndValue("ConType",request.getParameter("ConType"));  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));    
    tTransferData.setNameAndValue("ContStateReason",request.getParameter("StateReason"));
    tTransferData.setNameAndValue("ContEndDate",request.getParameter("ContEndDate"));    
            
    LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();        

    //loggerDebug("LLClaimContDealSave",request.getParameter("CustNo"));
    
    
    //保存终止结论并进行计算
    if (tOperate.equals("CONT"))
    {
        //准备后台数据
        tLLBalanceSchema.setClmNo(request.getParameter("ClmNo"));
        tLLBalanceSchema.setContNo(request.getParameter("ContNo"));                                       
    }
          
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLBalanceSchema );

//        LLClaimContDealUI tLLClaimContDealUI = new LLClaimContDealUI();
//       if (tLLClaimContDealUI.submitData(tVData,tOperate) == false)
//        {
//    
//        } else {
//            Content = " 保存成功! ";
//            FlagStr = "SUCC";
//        }
//        int n = tLLClaimContDealUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//                Content = Content + "原因是: " + tLLClaimContDealUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//        }  
String busiName="grpLLClaimContDealUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLClaimContDealSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
    
//    loggerDebug("LLClaimContDealSave","LLMedicalFeeInpSave测试--"+FlagStr);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
