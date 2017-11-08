<%@include file="/i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%@page contentType="text/html;charset=GBK"%>
<%
	//接收信息，并作校验处理。
	//输入参数
	RIItemCalSchema tRIItemCalSchema = new RIItemCalSchema();

	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	String tOperate = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");
	tRIItemCalSchema.setArithmeticDefID(request
			.getParameter("ArithmeticDefID"));
	tRIItemCalSchema.setArithmeticID(request
			.getParameter("KArithmeticDefID"));
	tRIItemCalSchema.setArithmeticName(request
			.getParameter("KArithmeticDefName"));
	tRIItemCalSchema.setArithmeticType(request
			.getParameter("ArithmeticType"));
	tRIItemCalSchema.setCalItemID(request.getParameter("CalItemID"));
	tRIItemCalSchema.setCalItemName(request
			.getParameter("CalItemIDName"));

	tRIItemCalSchema.setCalItemOrder(request
			.getParameter("CalItemOrder"));
	tRIItemCalSchema.setCalItemType(request.getParameter("Business"));
	tRIItemCalSchema
			.setItemCalType(request.getParameter("DistillMode"));
	tRIItemCalSchema
			.setDoubleValue(request.getParameter("DoubleValue"));
	tRIItemCalSchema.setCalClass(request.getParameter("DistillClass"));
	tRIItemCalSchema.setCalSQL(request.getParameter("DistillSQL"));

	if (request.getParameter("TarGetClumn") == null) {
		tRIItemCalSchema.setTarGetClumn("000000");
	} else {
		tRIItemCalSchema.setTarGetClumn(request
				.getParameter("TarGetClumn"));
	}
	tRIItemCalSchema.setReMark(request.getParameter("Remark"));
	tRIItemCalSchema.setStandbyString1(request
			.getParameter("StandbyString1"));
	tRIItemCalSchema.setStandbyString2(request
			.getParameter("StandbyString2"));
	tRIItemCalSchema.setStandbyString3(request
			.getParameter("StandbyString3"));

	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr = "";
	tVData.addElement(tRIItemCalSchema);
	tVData.add(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIItemCalDetailBL")) {
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
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>

