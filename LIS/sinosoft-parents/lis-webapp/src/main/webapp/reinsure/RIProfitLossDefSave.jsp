<%@include file="/i18n/language.jsp"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//程序名称：RIProfitLossDefSave.jsp
	//程序功能：盈余佣金定义
	//创建日期：2011/8/18
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	//接收信息，并作校验处理。
	RIProfitDefSchema tRIProfitDefSchema = new RIProfitDefSchema();
	RIProfitRelaSet tRIProfitRelaSet = new RIProfitRelaSet();

	//输出参数     
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	//获取定义信息 
	tRIProfitDefSchema
			.setRIProfitNo(request.getParameter("RIProfitNo"));

	tRIProfitDefSchema.setRIProfitName(request
			.getParameter("RIProfitName"));
	tRIProfitDefSchema.setReComCode(request.getParameter("RIcomCode"));
	tRIProfitDefSchema.setRIContNo(request.getParameter("ContNo"));
	tRIProfitDefSchema.setRelaType(request.getParameter("RelaType"));
	tRIProfitDefSchema.setRIProfitDes(request
			.getParameter("RIProfitDes"));
	tRIProfitDefSchema.setModifyDate(currentDate);
	tRIProfitDefSchema.setModifyTime(currentTime);
	tRIProfitDefSchema.setOperator(tG.Operator);

	//获取方案信息
	String[] strNumber = request.getParameterValues("Mul9GridNo");
	String[] strRIProfitRelaID = request
			.getParameterValues("Mul9Grid1");
	String[] strRIProfitRelaName = request
			.getParameterValues("Mul9Grid2");

	if (strNumber != null) {
		int tLength = strNumber.length;
		for (int i = 0; i < tLength; i++) {

			RIProfitRelaSchema tRIProfitRelaSchema = new RIProfitRelaSchema();
			tRIProfitRelaSchema.setRIProfitNo(request
					.getParameter("RIProfitNo"));
			tRIProfitRelaSchema.setRIProfitRelaID(strRIProfitRelaID[i]);
			tRIProfitRelaSchema
					.setRIProfitRelaName(strRIProfitRelaName[i]);
			tRIProfitRelaSchema.setModifyDate(currentDate);
			tRIProfitRelaSchema.setModifyTime(currentTime);
			tRIProfitRelaSchema.setOperator(tG.Operator);
			tRIProfitRelaSet.add(tRIProfitRelaSchema);
		}
	}
	VData tVData = new VData();
	tVData.addElement(tRIProfitDefSchema);
	tVData.addElement(tRIProfitRelaSet);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossDefUI")) {
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
	if ("".equals(FlagStr)) {
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
