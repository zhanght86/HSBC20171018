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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
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
        loggerDebug("LLClaimContPolDealAdjustPaySave","登录信息没有获取!!!");
       return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimContPolDealAdjustPaySave","没有获取操作符!!!");
       return;   
    }
    
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));    
    tTransferData.setNameAndValue("PolNo",request.getParameter("PolNo"));     
    tTransferData.setNameAndValue("FeeOperationType",request.getParameter("FeeOperationType"));
    tTransferData.setNameAndValue("SubFeeOperationType",request.getParameter("SubFeeOperationType"));    
    tTransferData.setNameAndValue("NewPay",request.getParameter("NewPay"));      
    String tAdjRemark = new String(request.getParameter("NewAdjRemark").getBytes("ISO-8859-1"),"GB2312");        
    tTransferData.setNameAndValue("AdjRemark",request.getParameter("NewAdjRemark"));

    tTransferData.setNameAndValue("ContStaDate",request.getParameter("ContStaDate"));
    
    //loggerDebug("LLClaimContPolDealAdjustPaySave",request.getParameter("CustNo"));
    loggerDebug("LLClaimContPolDealAdjustPaySave","--运算符："+tOperate);
              
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

        //LLClaimContPolDealAdjustPayUI tLLClaimContPolDealAdjustPayUI = new LLClaimContPolDealAdjustPayUI();
//       if (tLLClaimContPolDealAdjustPayUI.submitData(tVData,tOperate) == false)
//        {
//            Content = "提示信息:";
//
//        } else {
//            Content = " 保存成功! ";
//            FlagStr = "SUCC";
//        }
//        int n = tLLClaimContPolDealAdjustPayUI.mErrors.getErrorCount();
//        String tMessage="提示信息:";
//        for (int i = 0; i < n; i++)
//        {
//                Content = Content  + tLLClaimContPolDealAdjustPayUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//        }  
		String busiName="LLClaimContPolDealAdjustPayUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLClaimContPolDealAdjustPaySave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = " 提示信息: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "提示信息";
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
    
//    loggerDebug("LLClaimContPolDealAdjustPaySave","LLMedicalFeeInpSave测试--"+FlagStr);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
