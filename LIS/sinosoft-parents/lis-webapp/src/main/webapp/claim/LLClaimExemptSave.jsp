<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLClaimExemptSave.jsp
//程序功能：豁免信息保存
//创建日期：2005-7-20
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
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String transact = request.getParameter("fmtransact");    
   
    if(tG == null) 
    {
        loggerDebug("LLClaimExemptSave","登录信息没有获取!!!");
        return;
     }
      else if (transact == null || transact == "") 
     {
        loggerDebug("LLClaimExemptSave","没有获取操作符!!!");
        return;   
    }
    
	//准备数据容器信息
	LLClaimDetailSchema tLLClaimDetailSchema=new LLClaimDetailSchema();
	LCPremSchema tLCPremSchema=new LCPremSchema();
	LLExemptSchema tLLExemptSchema=new LLExemptSchema();
	
	//if(transact.equals("LLExempt||Get"))  //[提取豁免信息]
	//{
	    tLLClaimDetailSchema.setClmNo(request.getParameter("ClmNo")); //传入赔案号
	//}
	
	if(transact.equals("LLExempt||Save"))  //[修改保存豁免信息]
	{
	    tLLExemptSchema.setClmNo(request.getParameter("ClmNo")); //传入赔案号<主键>
	    tLLExemptSchema.setPolNo(request.getParameter("PolNo")); //保单险种号码<主键>
	    tLLExemptSchema.setDutyCode(request.getParameter("DutyCode")); //责任编码<主键>
	    tLLExemptSchema.setPayPlanCode(request.getParameter("PayPlanCode")); //交费计划编码<主键>
		tLLExemptSchema.setFreeFlag(request.getParameter("FreeFlag")); //免交标志
		tLLExemptSchema.setFreeRate(request.getParameter("FreeRate")); //免交比率
		tLLExemptSchema.setFreeStartDate(request.getParameter("FreeStartDate")); //免交起期
		tLLExemptSchema.setFreeEndDate(request.getParameter("FreeEndDate")); //免交止期
		tLLExemptSchema.setExemptReason(request.getParameter("ExemptReason")); //豁免原因
        tLLExemptSchema.setExemptDesc(request.getParameter("ExemptDesc")); //豁免描述
		
	}
  
    
    try
    {    
        // 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(transact);
		tVData.add(transact);
		 TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tVData.add(tTransferData);
		tVData.add(tLLExemptSchema);
		tVData.add(tLLClaimDetailSchema);
//    LLClaimExemptUI tLLClaimExemptUI = new LLClaimExemptUI();        
//    tLLClaimExemptUI.submitData(tVData,transact);
//
//    
//    int n = tLLClaimExemptUI.mErrors.getErrorCount();
//    for (int i = 0; i < n; i++)
//    {
//       Content = Content + "提示信息: " + tLLClaimExemptUI.mErrors.getError(i).errorMessage;
//    }
//    loggerDebug("LLClaimExemptSave","豁免返回标志--"+n);
//    
//    if (n == 0 )
//    {
//            Content = " 保存成功! ";
//            FlagStr = "SUCC";
//    }
		String busiName="LLClaimExemptUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLClaimExemptSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content =Content + "提示信息: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "处理失败";
				   FlagStr = "Fail";				
				} 
		}
		else{
		           Content = " 保存成功! ";
		           FlagStr = "SUCC";
		}

  
     
    }catch(Exception e)
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
