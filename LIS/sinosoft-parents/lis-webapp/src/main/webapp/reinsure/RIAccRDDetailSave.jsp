<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIAccRDQuerySave.jsp
	//程序功能：分出责任定义-信息查询
	//创建日期：2011/6/16
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	PubFun tPubFun = new PubFun();
	String currentDate = tPubFun.getCurrentDate();
	String currentTime = tPubFun.getCurrentTime();

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//从url中取出参数付给相应的schema
	//t××××Schema.set××××(request.getParameter("××××"));
	RIAccumulateRDCodeSchema tRIAccumulateRDCodeSchema = new RIAccumulateRDCodeSchema();
	RIAccumulateGetDutySchema tRIAccumulateGetDutySchema = new RIAccumulateGetDutySchema();
	tRIAccumulateRDCodeSchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));
	tRIAccumulateRDCodeSchema.setAssociatedCode(request
			.getParameter("AssociatedCode"));
	tRIAccumulateRDCodeSchema.setAssociatedName(request
			.getParameter("AssociatedName"));
	tRIAccumulateGetDutySchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));
	tRIAccumulateGetDutySchema.setAssociatedCode(request
			.getParameter("AssociatedCode"));
	tRIAccumulateGetDutySchema.setGetDutyCode(request
			.getParameter("GetDutyCode"));
	tRIAccumulateGetDutySchema.setGetDutyName(request
			.getParameter("GetDutyName"));

	VData tVData = new VData();
	tVData.add(tG);
	tVData.addElement(tRIAccumulateRDCodeSchema);
	tVData.addElement(tRIAccumulateGetDutySchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIAccRDDetailUI")) {
		if (uiBusinessDelegate.getCErrors() != null
				&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = ""+"保存失败，原因是："+""
					+ uiBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = ""+"保存失败"+"";
			FlagStr = "Fail";
		}
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = uiBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = ""+"保存成功"+"";
			FlagStr = "Succ";
		} else {
			Content = ""+"保存失败，原因是："+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}

	//添加各种预处理
%>
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

