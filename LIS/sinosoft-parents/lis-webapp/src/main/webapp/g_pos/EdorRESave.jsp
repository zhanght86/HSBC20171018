<%
/***************************************************************
* <p>ProName：EdorRESave.jsp</p>
 * <p>Title：保单复效</p>
 * <p>Description：保单复效</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LPGrpEdorItemSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	LPGrpEdorItemSchema mLPGrpEdorItemSchema = null;
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			String tOperate = request.getParameter("Operate");
			String tEdorNo = request.getParameter("EdorNo");
			
			String tEdorType = request.getParameter("EdorType");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tGetMoney = request.getParameter("GetMoney");
			String tGetInterest = request.getParameter("GetInterest");
			String tREType = request.getParameter("REtype");
			String tDays = request.getParameter("Days");
			String tEdorValDate = request.getParameter("EdorValDate");
			if("INSERT".equals(tOperate)){
				mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				mLPGrpEdorItemSchema.setGrpContNo(tGrpContNo);
				mLPGrpEdorItemSchema.setEdorAppNo(tEdorAppNo);
				mLPGrpEdorItemSchema.setEdorType(tEdorType);
				mLPGrpEdorItemSchema.setEdorNo(tEdorNo);
				mLPGrpEdorItemSchema.setGetMoney(tGetMoney);
				mLPGrpEdorItemSchema.setGetInterest(tGetInterest);
				mLPGrpEdorItemSchema.setEdorState("1");
			}
			
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);
			vTransferData.setNameAndValue("EdorNo",tEdorNo);
			vTransferData.setNameAndValue("REType",tREType);
			vTransferData.setNameAndValue("Days",tDays);
			vTransferData.setNameAndValue("EdorValDate",tEdorValDate);
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(mLPGrpEdorItemSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorREUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功!";
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
