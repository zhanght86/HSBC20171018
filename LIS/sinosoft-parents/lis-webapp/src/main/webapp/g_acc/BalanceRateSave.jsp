<%
/***************************************************************
 * <p>ProName：BalanceRateSave.jsp</p>
 * <p>Title：结算利率公布</p>
 * <p>Description：结算利率公布</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-07-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LDInsuAccRateSchema"%>
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
			
			LDInsuAccRateSchema tLDInsuAccRateSchema = new LDInsuAccRateSchema();
			tLDInsuAccRateSchema.setRiskCode(request.getParameter("RiskCode"));
			tLDInsuAccRateSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
			tLDInsuAccRateSchema.setRateClass("2");
			tLDInsuAccRateSchema.setSRateDate(request.getParameter("SRateDate"));
			tLDInsuAccRateSchema.setARateDate(request.getParameter("ARateDate"));
			tLDInsuAccRateSchema.setStartDate(request.getParameter("StartDate"));
			tLDInsuAccRateSchema.setRateIntv(request.getParameter("RateIntv"));
			tLDInsuAccRateSchema.setRate(request.getParameter("Rate"));
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLDInsuAccRateSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "InsuAccRateUI")) {
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
