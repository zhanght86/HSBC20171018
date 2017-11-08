<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String tAction = "";
	String tOperate = "";
	String wFlag = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	//工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
	wFlag = request.getParameter("WorkFlowFlag");
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("ContNo",
			request.getParameter("ProposalContNo"));
	mTransferData.setNameAndValue("PrtNo",
			request.getParameter("PrtNo"));
	mTransferData.setNameAndValue("AppntNo",
			request.getParameter("AppntNo"));
	mTransferData.setNameAndValue("AppntName",
			request.getParameter("AppntName"));
	mTransferData.setNameAndValue("AgentCode",
			request.getParameter("AgentCode"));
	mTransferData.setNameAndValue("ManageCom",
			request.getParameter("ManageCom"));
	mTransferData.setNameAndValue("Operator", tG.Operator);
	mTransferData.setNameAndValue("MakeDate", PubFun.getCurrentDate());
	mTransferData.setNameAndValue("MissionID",
			request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID",
			request.getParameter("SubMissionID"));

	loggerDebug("InputConfirm",
			"ContNo=" + request.getParameter("ProposalContNo"));
	loggerDebug("InputConfirm",
			"PrtNo=" + request.getParameter("PrtNo"));
	loggerDebug("InputConfirm",
			"AppntNo=" + request.getParameter("AppntNo"));
	loggerDebug("InputConfirm",
			"AppntName=" + request.getParameter("AppntName"));
	loggerDebug("InputConfirm",
			"AgentCode=" + request.getParameter("AgentCode"));
	loggerDebug("InputConfirm",
			"ManageCom=" + request.getParameter("ManageCom"));
	loggerDebug("InputConfirm", "Operator=" + tG.Operator);
	loggerDebug("InputConfirm", "MakeDate=" + PubFun.getCurrentDate());
	loggerDebug("InputConfirm",
			"MissionID=" + request.getParameter("MissionID"));
	loggerDebug("InputConfirm",
			"SubMissionID=" + request.getParameter("SubMissionID"));

	loggerDebug("InputConfirm",
			"prtNo==" + request.getParameter("PrtNo"));
	tVData.add(tG);
	loggerDebug("InputConfirm", "wFlag=" + wFlag);
	
	//SYY BEGIN
	String busiName = "WorkFlowUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	mTransferData.setNameAndValue("ActivityID", wFlag);
	tVData.add(mTransferData);
	tVData.add(tG);
	if (tBusinessDelegate.submitData(tVData, "execute", busiName)) {
		FlagStr = "Succ";
		Content = "操作成功";
	} else {
		FlagStr = "Fail";
		Content = "操作失败，原因是："
				+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
/* 		System.out.println(tBusinessDelegate.getCErrors()
				.getErrorCount()); */
				loggerDebug("InputConfirm.jsp",String.valueOf(tBusinessDelegate.getCErrors().getErrorCount()));
	}
	//SYY END
%>
<html>
<script language="javascript">
	var contract='<%=request.getParameter("ProposalContNo")%>';
	var FlagStr='<%=FlagStr%>';
	var Content='<%=Content%>';
	parent.fraInterface.afterSubmit(FlagStr, Content, contract);
</script>
</html>
