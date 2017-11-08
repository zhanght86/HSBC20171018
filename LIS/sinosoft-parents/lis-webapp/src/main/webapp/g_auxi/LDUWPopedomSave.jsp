<%
/***************************************************************
 * <p>ProName：LDUWPopedomSave.jsp</p>
 * <p>Title：核保权限管理</p>
 * <p>Description：核保权限管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LDUWPopedomSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			
			String tPopedomLevel = request.getParameter("PopedomLevel");
			String tPopedomName = request.getParameter("PopedomName");
			String tPerLifeAmnt = request.getParameter("PerLifeAmnt");
			String tPerAcciAmnt = request.getParameter("PerAcciAmnt");
			String tPerIllAmnt = request.getParameter("PerIllAmnt");
			String tPerMedAmnt = request.getParameter("PerMedAmnt");
			String tPremScale = request.getParameter("PremScale");
			String tMainPremRateFloat = request.getParameter("MainPremRateFloat");
			String tMedPremRateFloat = request.getParameter("MedPremRateFloat");
			String tValDate = request.getParameter("ValDate");
			String tEndDate = request.getParameter("EndDate");

			LDUWPopedomSchema tLDUWPopedomSchema = new LDUWPopedomSchema();
			tLDUWPopedomSchema.setPopedomLevel(tPopedomLevel);
			tLDUWPopedomSchema.setPopedomName(tPopedomName);
			tLDUWPopedomSchema.setPerLifeAmnt(tPerLifeAmnt);
			tLDUWPopedomSchema.setPerAcciAmnt(tPerAcciAmnt);
			tLDUWPopedomSchema.setPerIllAmnt(tPerIllAmnt);
			tLDUWPopedomSchema.setPerMedAmnt(tPerMedAmnt);
			tLDUWPopedomSchema.setPremScale(tPremScale);
			tLDUWPopedomSchema.setMainPremRateFloat(tMainPremRateFloat);
			tLDUWPopedomSchema.setMedPremRateFloat(tMedPremRateFloat);
			tLDUWPopedomSchema.setValDate(tValDate);
			tLDUWPopedomSchema.setEndDate(tEndDate);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLDUWPopedomSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LDUWPopedomUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
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
