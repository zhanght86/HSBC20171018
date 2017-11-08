<%@include file="/i18n/language.jsp"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//程序名称：RIBsnsBillUWSave.jsp
	//程序功能：账单审核
	//创建日期：2011/6/14
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	RIBsnsBillBatchSchema mRiBsnsBillBatchSchema = new RIBsnsBillBatchSchema();

	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//从url中取出参数付给相应的schema
	String[] arrSelNo = request.getParameterValues("InpMul10GridSel");
	String[] arrBillNo = request.getParameterValues("Mul10Grid1");
	String[] arrSerialNo = request.getParameterValues("Mul10Grid8");
	String[] arrCurrency = request.getParameterValues("Mul10Grid9");
	String strBillNo = "";
	String strSerialNo = "";
	String strCurrency = "";
	if (arrSelNo != null) {
		for (int i = 0; i < arrSelNo.length; i++) {
			if ("1".equals(arrSelNo[i])) {
				strBillNo = arrBillNo[i];
				strSerialNo = arrSerialNo[i];
				strCurrency = arrCurrency[i];
			}
		}
	}
	String strState = request.getParameter("RIUWReport");
	mRiBsnsBillBatchSchema.setBatchNo(strSerialNo);
	mRiBsnsBillBatchSchema.setBillNo(strBillNo);
	mRiBsnsBillBatchSchema.setCurrency(strCurrency);
	mRiBsnsBillBatchSchema.setState(strState);

	VData tVData = new VData();
	tVData.add(tG);
	tVData.add(mRiBsnsBillBatchSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, null, "RIBsnsBillUWUI")) {
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

