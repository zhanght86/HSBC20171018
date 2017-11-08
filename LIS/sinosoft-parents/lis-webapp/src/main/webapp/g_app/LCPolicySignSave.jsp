<%
/***************************************************************
 * <p>ProName：LCPolicySignSave.jsp</p>
 * <p>Title：保单签发</p>
 * <p>Description：保单签发</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-05
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.*"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tContPlanType = request.getParameter("ContPlanType");
			String tGrpPropNo = request.getParameter("GrpPropNo");
			
			TransferData mTransferData = new TransferData();
			
			mTransferData.setNameAndValue("MissionID", tMissionID);
			mTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			mTransferData.setNameAndValue("ActivityID", tActivityID);
			mTransferData.setNameAndValue("ContPlanType", tContPlanType);
			mTransferData.setNameAndValue("GrpPropNo", tGrpPropNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(mTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCPolicySignUI")) {
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
