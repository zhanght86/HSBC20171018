<%
/***************************************************************
 * <p>ProName：EdorAcceptDetailSave.jsp</p>
 * <p>Title：保全受理</p>
 * <p>Description：保全受理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LPEdorAppSchema"%>
<%@page import="com.sinosoft.lis.schema.LPEdorAppItemSchema"%>
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
			
			String tPolicyNo = request.getParameter("PolicyNo");
			String tAppDate = request.getParameter("AppDate");
			String tReceiveDate = request.getParameter("ReceiveDate");
			String tAppMode = request.getParameter("AppMode");
			
			String tEdorType = request.getParameter("EdorType");
			String tEdorValDate = request.getParameter("EdorValDate");
			String tGetObj = request.getParameter("GetObj");
			
			String tDelEdorType = request.getParameter("DelEdorType");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("EdorAppNo", tEdorAppNo);
			
			tTransferData.setNameAndValue("GEdorValDate", tEdorValDate);
			tTransferData.setNameAndValue("PolicyNo", tPolicyNo);
			
			LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
			LPEdorAppItemSchema tLPEdorAppItemSchema = new LPEdorAppItemSchema();
			
			if ("RELATE".equals(tOperate)) {
			
				tLPEdorAppSchema.setEdorAcceptNo(tEdorAppNo);
				tLPEdorAppSchema.setOtherNo(tPolicyNo);
				tLPEdorAppSchema.setAppDate(tAppDate);
				tLPEdorAppSchema.setReceiveDate(tReceiveDate);
				tLPEdorAppSchema.setAppType(tAppMode);
			} else if ("ADDEDORTYPE".equals(tOperate)) {
				
				tLPEdorAppItemSchema.setEdorAppNo(tEdorAppNo);
				tLPEdorAppItemSchema.setEdorType(tEdorType);
				tLPEdorAppItemSchema.setEdorValDate(tEdorValDate);
				tLPEdorAppItemSchema.setGetObj(tGetObj);
			} else if ("DELEDORTYPE".equals(tOperate)) {
				
				tLPEdorAppItemSchema.setEdorAppNo(tEdorAppNo);
				tLPEdorAppItemSchema.setEdorType(tDelEdorType);
				tLPEdorAppItemSchema.setEdorValDate(tEdorValDate);
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLPEdorAppSchema);
			tVData.add(tLPEdorAppItemSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorAcceptDetailUI")) {
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
