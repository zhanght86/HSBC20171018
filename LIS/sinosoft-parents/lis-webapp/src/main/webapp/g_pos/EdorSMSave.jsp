<%
/***************************************************************
 * <p>ProName：EdorSMSave.jsp</p>
 * <p>Title：定期结算维护</p>
 * <p>Description：定期结算维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-19
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LPPBalanceOnSchema"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LPPBalanceOnSchema mLPPBalanceOnSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);

			String tGrpContNo = request.getParameter("GrpContNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorType = request.getParameter("EdorType");
			
			if("INSERT".equals(tOperate)){
				String mBalanceOnState = request.getParameter("BalanceOnState");
				String mBalancePeriod = request.getParameter("BalancePeriod");
				String mGracePeriod = request.getParameter("GracePeriod");
				
				mLPPBalanceOnSchema = new LPPBalanceOnSchema();
				mLPPBalanceOnSchema.setGrpContNo(tGrpContNo);
				mLPPBalanceOnSchema.setGrpPropNo(tGrpContNo);
				mLPPBalanceOnSchema.setEdorNo(tEdorNo);
				mLPPBalanceOnSchema.setEdorType(tEdorType);
				mLPPBalanceOnSchema.setBalanceOnState(mBalanceOnState);
				mLPPBalanceOnSchema.setBalancePeriod(mBalancePeriod);
				mLPPBalanceOnSchema.setGracePeriod(mGracePeriod);
			}
		
			TransferData vTransferData = new TransferData();
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorNo",tEdorNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);
			
		
		tVData.add(vTransferData);
		tVData.add(mLPPBalanceOnSchema);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorSMUI")) {
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
