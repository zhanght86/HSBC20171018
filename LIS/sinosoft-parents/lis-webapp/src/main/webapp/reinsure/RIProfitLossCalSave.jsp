<%@include file="/i18n/language.jsp"%>
 <%
 	//程序名称：LRProfitLossCalSave.jsp
 	//程序功能：
 	//创建日期：2007-03-14
 	//创建人  ：张斌
 	//更新记录：  更新人    更新日期     更新原因/内容
 %>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page contentType="text/html;charset=GBK" %>

<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //将操作标志的英文转换成汉字的形式

	RIProLossValueSet tRIProLossValueSet = new RIProLossValueSet();
	RIProLossRelaSchema tRIProLossRelaSchema = new RIProLossRelaSchema();
	RIProLossCalSchema tRIProLossCalSchema = new RIProLossCalSchema();
	RIProLossResultSchema tRIProLossResultSchema = new RIProLossResultSchema();

	tRIProLossRelaSchema.setRIProfitNo(request
			.getParameter("RIProfitNo"));
	tRIProLossCalSchema.setRIProfitNo(request
			.getParameter("RIProfitNo"));
	tRIProLossResultSchema.setRIProfitNo(request
			.getParameter("RIProfitNo"));
	tRIProLossResultSchema.setCalYear(request.getParameter("CalYear"));

	String[] strNumber = request.getParameterValues("Mul10GridNo");
	//String[] strInOutType  = request.getParameterValues("Mul10Grid8");

	if (strNumber != null) {
		int tLength = strNumber.length;
		for (int i = 0; i < tLength; i++) {
			//if(strInOutType[i].equals("02")){
			RIProLossValueSchema tRIProLossValueSchema = new RIProLossValueSchema();
			tRIProLossValueSchema.setRIProfitNo(request
					.getParameter("RIProfitNo"));
			tRIProLossValueSchema.setFactorCode(request
					.getParameterValues("Mul10Grid2")[i]);
			tRIProLossValueSchema.setFactorName(request
					.getParameterValues("Mul10Grid3")[i]);
			tRIProLossValueSchema.setCurrency(request
					.getParameterValues("Mul10Grid6")[i]);
			tRIProLossValueSchema.setFactorValue(request
					.getParameterValues("Mul10Grid5")[i]);
			tRIProLossValueSchema.setCalYear(request
					.getParameter("CalYear"));
			tRIProLossValueSchema.setReComCode(request
					.getParameter("RIcomCode"));
			tRIProLossValueSchema.setRIContNo(request
					.getParameter("ContNo"));
			tRIProLossValueSchema.setBatchNo(request
					.getParameterValues("Mul10Grid9")[i]);
			tRIProLossValueSet.add(tRIProLossValueSchema);
		}
	}
	VData tVData = new VData();
	tVData.addElement(tRIProLossRelaSchema);
	tVData.addElement(tRIProLossValueSet);
	tVData.addElement(tRIProLossCalSchema);
	tVData.addElement(tRIProLossResultSchema);
	tVData.addElement(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossCalUI")) {
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
