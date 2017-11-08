<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIDataModifySave.jsp
	//程序功能：业务数据调整
	//创建日期：2011-07-30
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
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String date = PubFun.getCurrentDate();
	String time = PubFun.getCurrentTime();
	RIDataModifyLogSet mRIDataModifyLogSet = new RIDataModifyLogSet();

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	transact = request.getParameter("fmtransact");

	//从url中取出参数付给相应的schema
	RIPolRecordSchema tRIPolRecordSchema = new RIPolRecordSchema();
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	String mOperateType = request.getParameter("OperateType");

	String strEventNo = request.getParameter("Mul11Grid1");
	tRIPolRecordSchema.setEventNo(strEventNo);

	String[] strNumber = request.getParameterValues("Mul13GridNo");
	String[] strcolscode = request.getParameterValues("Mul13Grid1");
	String[] strcolsname = request.getParameterValues("Mul13Grid2");
	String[] strOriValue = request.getParameterValues("Mul13Grid3");
	String[] strModifyValue = request.getParameterValues("Mul13Grid4");

	if (strNumber != null) {
		int tLength = strNumber.length;
		for (int i = 0; i < tLength; i++) {
			RIDataModifyLogSchema tRIDataModifyLogSchema = new RIDataModifyLogSchema();
			tRIDataModifyLogSchema.setClumnCode(strcolscode[i]);
			tRIDataModifyLogSchema.setClumnName(strcolsname[i]);
			tRIDataModifyLogSchema
					.setClumnOriginalValue(strOriValue[i]);
			tRIDataModifyLogSchema
					.setClumnModifyValue(strModifyValue[i]);
			tRIDataModifyLogSchema.setOperator(tG.Operator);
			tRIDataModifyLogSchema.setManageCom(tG.ManageCom);
			tRIDataModifyLogSchema.setMakeDate(date);
			tRIDataModifyLogSchema.setMakeTime(time);
			tRIDataModifyLogSchema.setModifyDate(date);
			tRIDataModifyLogSchema.setModifyTime(time);

			mRIDataModifyLogSet.add(tRIDataModifyLogSchema);
		}
	}

	VData tVData = new VData();
	tVData.add(tRIPolRecordSchema);
	tVData.addElement(mRIDataModifyLogSet);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIDataModifyUI")) {
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

