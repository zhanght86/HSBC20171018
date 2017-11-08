<%
/***************************************************************
 * <p>ProName：LCBalanceOnSave.jsp</p>
 * <p>Title：定期结算维护</p>
 * <p>Description：定期结算维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LDPBalanceOnSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LDPBalanceOnSchema mLDPBalanceOnSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);

			String tGrpPropNo = request.getParameter("GrpPropNo");
			
			if("INSERT".equals(tOperate)){
				String mBalanceOnState = request.getParameter("BalanceOnState");
				String mBalancePeriod = request.getParameter("BalancePeriod");
				String mGracePeriod = request.getParameter("GracePeriod");
				
				mLDPBalanceOnSchema = new LDPBalanceOnSchema();
				mLDPBalanceOnSchema.setGrpContNo(tGrpPropNo);
				mLDPBalanceOnSchema.setGrpPropNo(tGrpPropNo);
				mLDPBalanceOnSchema.setBalanceOnState(mBalanceOnState);
				mLDPBalanceOnSchema.setBalancePeriod(mBalancePeriod);
				mLDPBalanceOnSchema.setGracePeriod(mGracePeriod);
			}
		
		tVData.add(mLDPBalanceOnSchema);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "LDPBalanceOnUI")) {
			tContent = tBusinessDelegate.getCErrors().getFirstError();
			tFlagStr = "Fail";
		} else {
			tFlagStr = "Succ";
			tContent = "处理成功！";
		}
	} catch (Exception ex) {
		ex.printStackTrace();
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
