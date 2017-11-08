<%
/***************************************************************
 * <p>ProName：EdorUWDetailSave.jsp</p>
 * <p>Title：保全核保处理</p>
 * <p>Description：保全核保处理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tReinsFlag = request.getParameter("ReinsFlag");	//是否删除再保结论
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("EdorAppNo", tEdorAppNo);
			tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
			tTransferData.setNameAndValue("EdorNo", tEdorNo);
			tTransferData.setNameAndValue("ReinsFlag", tReinsFlag);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorUWDetailUI")) {
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
