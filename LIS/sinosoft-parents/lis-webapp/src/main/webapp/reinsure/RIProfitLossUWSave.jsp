<%@include file="/i18n/language.jsp"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//程序名称：RIProfitLossUWSave.jsp
	//程序功能：盈余佣金审核
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
	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");
	RIProLossResultSchema tRIProLossResultSchema = new RIProLossResultSchema();

	String[] strSelNo = request.getParameterValues("InpMul9GridSel");
	String[] strRIProfitNo = request.getParameterValues("Mul9Grid1");
	String[] strCalYear = request.getParameterValues("Mul9Grid2");
	String[] strCurrency = request.getParameterValues("Mul9Grid8");
	if (strSelNo != null) {
		for (int i = 0; i < strSelNo.length; i++) {
			if ("1".equals(strSelNo[i])) {
				tRIProLossResultSchema.setRIProfitNo(strRIProfitNo[i]);
				tRIProLossResultSchema.setCalYear(strCalYear[i]);
				tRIProLossResultSchema.setCurrency(strCurrency[i]);
			}
		}
	}

	VData tVData = new VData();
	tVData.addElement(tRIProLossResultSchema);
	String mState = request.getParameter("RILossUWReport");
	TransferData transferData = new TransferData();
	transferData.setNameAndValue("state", mState);
	tVData.addElement(transferData);
	tVData.addElement(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossUWUI")) {
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
