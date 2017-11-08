<%
//Name：LLClaimPrepaySave.jsp
//Function：”预付保存“提交
//Author：yuejw
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	//输入参数

	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI"); 
	//LLClaimPrepayUI tLLClaimPrepayUI = new LLClaimPrepayUI(); 
	String busiName="LLClaimPrepayUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "页面失效,请重新登陆";
	    loggerDebug("LLClaimPrepaySave","页面失效,请重新登陆");    
	}
	else //页面有效
	{
	    String Operator  = tGI.Operator ; //保存登陆管理员账号
	    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
	
	    String transact = request.getParameter("fmtransact"); //获取操作insert||update
	    loggerDebug("LLClaimPrepaySave","-----transact= "+transact);
	
	    //***************************************
	    //获取页面信息 
	    //***************************************   
		LLPrepayDetailSchema tLLPrepayDetailSchema=new LLPrepayDetailSchema(); //预付明细记录表
	    tLLPrepayDetailSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
	    tLLPrepayDetailSchema.setCaseNo(request.getParameter("CaseNo")); //分案号
	    tLLPrepayDetailSchema.setPolNo(request.getParameter("PolNo")); //保单号
	    tLLPrepayDetailSchema.setGetDutyKind(request.getParameter("GetDutyKind")); //给付责任类型
	    tLLPrepayDetailSchema.setGetDutyCode(request.getParameter("GetDutyCode")); //给付责任编码
	    //tLLPrepayDetailSchema.setSerialNo(request.getParameter("SerialNo")); //序号，由后台生成    
	    tLLPrepayDetailSchema.setPrepayNo(request.getParameter("PrepayNo")); //预付批次号
	    tLLPrepayDetailSchema.setCurrency(request.getParameter("Currency")); //币种
	    tLLPrepayDetailSchema.setPrepaySum(request.getParameter("PrepaySum")); //预付金额
	    tLLPrepayDetailSchema.setCasePayMode(request.getParameter("CasePayMode")); //支付方式
	    
		LLPrepayClaimSchema tLLPrepayClaimSchema=new LLPrepayClaimSchema(); //预付赔案记录
	    tLLPrepayClaimSchema.setClmNo(request.getParameter("ClmNo")); ////赔案号
	    tLLPrepayClaimSchema.setCaseNo(request.getParameter("ClmNo")); ////分案号
	    tLLPrepayClaimSchema.setRgtNo(request.getParameter("ClmNo")); //立案号
	    tLLPrepayClaimSchema.setCurrency(request.getParameter("Currency"));
	    tLLPrepayClaimSchema.setPrepaySum(request.getParameter("PrepaySum")); //预付赔付金额<用于更新数据>
	
	 	LLClaimDetailSchema tLLClaimDetailSchema=new LLClaimDetailSchema(); //赔付明细表
	    tLLClaimDetailSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
	    tLLClaimDetailSchema.setCaseNo(request.getParameter("CaseNo")); ////分案号
	    tLLClaimDetailSchema.setPolNo(request.getParameter("PolNo")); //保单号
	    tLLClaimDetailSchema.setDutyCode(request.getParameter("DutyCode")); //责任编码    
	    tLLClaimDetailSchema.setGetDutyKind(request.getParameter("GetDutyKind")); //给付责任类型
	    tLLClaimDetailSchema.setGetDutyCode(request.getParameter("GetDutyCode")); //给付责任编码
	    tLLClaimDetailSchema.setCaseRelaNo(request.getParameter("CaseRelaNo")); //受理事故号
	    tLLClaimDetailSchema.setCurrency(request.getParameter("Currency"));
	    tLLClaimDetailSchema.setPrepaySum(request.getParameter("PrepaySum")); //预付金额
	    tLLClaimDetailSchema.setCustomerNo(request.getParameter("CustomerNo")); //客户号码
	
		LLClaimSchema tLLClaimSchema=new LLClaimSchema();
		tLLClaimSchema.setClmNo(request.getParameter("ClmNo")); //赔案号 
		
		LLBalanceSchema tLLBalanceSchema=new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
		tLLBalanceSchema.setFeeOperationType("B");
		tLLBalanceSchema.setSubFeeOperationType("B");
		tLLBalanceSchema.setFeeFinaType("YFPK"); //	预付赔款的财务类型
		tLLBalanceSchema.setBatNo(request.getParameter("PrepayNo")); //预付批次号
		tLLBalanceSchema.setPolNo(request.getParameter("PolNo")); //保单号
		tLLBalanceSchema.setPayFlag("0"); //预付标志
		tLLBalanceSchema.setCurrency(request.getParameter("Currency"));
		tLLBalanceSchema.setPay(request.getParameter("PrepaySum")); //预付金额
		tLLBalanceSchema.setCustomerNo(request.getParameter("CustomerNo"));
		
	    try
	    {
	        //准备传输数据 VData
	        VData tVData = new VData();
	        tVData.add(tGI);
	        tVData.add(transact);
	        tVData.add(tLLPrepayDetailSchema);
	        tVData.add(tLLPrepayClaimSchema);
	        tVData.add(tLLClaimDetailSchema);
	        tVData.add(tLLClaimSchema);
	        tVData.add(tLLBalanceSchema);
	        
	        try
	        {
//  	            if(!tLLClaimPrepayUI.submitData(tVData,transact))
//	            {           
//	                Content = "提交失败，原因是: " + tLLClaimPrepayUI.mErrors.getError(0).errorMessage;
//	                FlagStr = "Fail";    
//	            }
	            if(!tBusinessDelegate.submitData(tVData,transact,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "提交失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "提交失败";
						FlagStr = "Fail";				
					}
				}
	            
	            else
	        	{	    
	                Content = "数据提交成功";
	                FlagStr = "Succ";            
	        	}
	    	}
	    	catch(Exception ex)
	        {
	        	Content = "数据提交LLClaimPrepayUI失败，原因是:" + ex.toString();
	        	FlagStr = "Fail";
	        }	
	    }    
	    catch(Exception ex)
	    {
	        Content = "数据提交LLClaimPrepayUI失败，原因是:" + ex.toString();
	        FlagStr = "Fail";
	    }
	    
	    loggerDebug("LLClaimPrepaySave","------LLClaimPrepaySave.jsp end------");
	    loggerDebug("LLClaimPrepaySave",FlagStr);
	    loggerDebug("LLClaimPrepaySave",Content);
	}    
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
