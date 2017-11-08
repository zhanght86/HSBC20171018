<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIBussRateSave.jsp
	//程序功能：汇率维护
	//创建日期：2011-6-27
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
	//接收信息，并作校验处理。

	//输入参数
	//××××Schema t××××Schema = new ××××Schema();

	//输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	RIExchangeRateSchema tRIExchangeRateSchema = new RIExchangeRateSchema();
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	String mOperateType = request.getParameter("OperateType");

	String strCurrency = request.getParameter("Currency");
	String strExchangeRate = request.getParameter("ExchangeRate");
	String strStartDate = request.getParameter("StartDate");
	String strEndDate = request.getParameter("EndDate");

	tRIExchangeRateSchema.setSerialNo(request.getParameter("sn"));
	tRIExchangeRateSchema.setCurrency(strCurrency);
	tRIExchangeRateSchema.setExchangeRate(strExchangeRate);
	tRIExchangeRateSchema.setStartDate(strStartDate);
	tRIExchangeRateSchema.setEndDate(strEndDate);

	tRIExchangeRateSchema.setOperator(tG.Operator);
	tRIExchangeRateSchema.setManageCom(tG.ManageCom);
	tRIExchangeRateSchema.setModifyDate(currentDate);
	tRIExchangeRateSchema.setModifyTime(currentTime);

	tRIExchangeRateSchema.setMakeDate(currentDate);
	tRIExchangeRateSchema.setMakeTime(currentTime);

	VData tVData = new VData();
	tVData.add(tRIExchangeRateSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIBussRateUI")) {
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

	String result = "";
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

