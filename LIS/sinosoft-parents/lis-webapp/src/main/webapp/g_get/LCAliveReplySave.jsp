<%
/***************************************************************
 * <p>ProName：LCAliveReplySave.jsp</p>
 * <p>Title：生调录入</p>
 * <p>Description：生调录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-09-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LCInvestReplaySchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			String tContNo = request.getParameter("ContNo");
			String tInsuredNo = request.getParameter("InsuredNo");
			String tInvestDesc = request.getParameter("InvestDesc");
			String tDeathFlag = request.getParameter("DeathFlag");
			String tDeathDate = request.getParameter("DeathDate");
			
			LCInvestReplaySchema tLCInvestReplaySchema = new LCInvestReplaySchema();
			
			if ("INSERT".equals(tOperate)) {
				
				tLCInvestReplaySchema.setContNo(tContNo);
				tLCInvestReplaySchema.setInsuredNo(tInsuredNo);
				tLCInvestReplaySchema.setInvestDesc(tInvestDesc);
				tLCInvestReplaySchema.setDeathFlag(tDeathFlag);
				tLCInvestReplaySchema.setDeathDate(tDeathDate);
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLCInvestReplaySchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCAliveReplyUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
