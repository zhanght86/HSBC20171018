<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>


<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	String FlagStr = "";
	String Content = "";
	String Result = "";

	//LLGrpClaimEdorTypeCAUI tLLGrpClaimEdorTypeCAUI = new LLGrpClaimEdorTypeCAUI();
	String busiName="grpLLGrpClaimEdorTypeCAUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	TransferData tTransferData = new TransferData();

	String transact = request.getParameter("fmtransact");
	String tGrpContNo=request.getParameter("GrpContNo");
	String tRptNo =request.getParameter("RptNo");
	String[] tContno = request.getParameterValues("LPAccMoveGrid9");
	String[] tPayPlanCode = request.getParameterValues("LPAccMoveGrid11");
	String[] tRiskCode = request.getParameterValues("LPAccMoveGrid12");
	String[] tPMoveMoney = request.getParameterValues("LPAccMoveGrid6");
	tTransferData.setNameAndValue("tAccTotal",request.getParameter("AccTotal"));

	
	//准备公共账户轨迹信息
	LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
	tLCInsureAccTraceSchema.setGrpContNo(tGrpContNo);
	tLCInsureAccTraceSchema.setOtherNo(tRptNo);
	tLCInsureAccTraceSchema.setOtherType("5"); //代表理赔案例号
	

	//循环准备每个需要转移资金的个人账户信息
	String tChk[] = request.getParameterValues("InpLPAccMoveGridChk");
	LCInsureAccTraceSet pLCInsureAccTraceSet = new LCInsureAccTraceSet();
	for(int index=0;index < tChk.length; index++)
	{
		if("1".equals(tChk[index]))
		{
			LCInsureAccTraceSchema pLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			pLCInsureAccTraceSchema.setGrpContNo(tGrpContNo);
			pLCInsureAccTraceSchema.setOtherNo(tRptNo);
			pLCInsureAccTraceSchema.setOtherType("5"); //代表理赔案例号
			pLCInsureAccTraceSchema.setContNo(tContno[index]);
			pLCInsureAccTraceSchema.setPayPlanCode(tPayPlanCode[index]);
			pLCInsureAccTraceSchema.setRiskCode(tRiskCode[index]);
			pLCInsureAccTraceSchema.setMoney(tPMoveMoney[index]);
			pLCInsureAccTraceSet.add(pLCInsureAccTraceSchema);
			
		}
	}

			VData tVData = new VData();
			tVData.addElement(tG);
			tVData.addElement(tLCInsureAccTraceSchema);
			tVData.addElement(pLCInsureAccTraceSet);
			tVData.addElement(tTransferData);

//		  if(!tLLGrpClaimEdorTypeCAUI.submitData(tVData,transact))
//		  {
//		      Content = " 数据提交LLGrpClaimEdorTypeCAUI失败，原因是: " + tLLGrpClaimEdorTypeCAUI.mErrors.getError(0).errorMessage;
//		      FlagStr = "Fail";
//		  }
		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "数据提交LLGrpClaimEdorTypeCAUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "数据提交LLGrpClaimEdorTypeCAUI失败";
				FlagStr = "Fail";				
			}
		}

		  else
		  {
		      Content = " 保存成功";
		      FlagStr = "Success";
		  }
       
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

