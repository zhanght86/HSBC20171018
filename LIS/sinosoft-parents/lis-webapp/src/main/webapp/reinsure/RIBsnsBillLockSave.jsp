<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIBsnsBillLockSave.jsp
	//程序功能：数据加锁
	//创建日期：2011/8/11
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
	String mOperateType = request.getParameter("OperateType");
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	RIBsnsBillLockSchema tRIBsnsBillLockSchema = new RIBsnsBillLockSchema();
	tRIBsnsBillLockSchema.setOperator(tG.Operator);
	tRIBsnsBillLockSchema.setMakeDate(currentDate);
	tRIBsnsBillLockSchema.setMakeTime(currentTime);
	tRIBsnsBillLockSchema.setModifyDate(currentDate);
	tRIBsnsBillLockSchema.setModifyTime(currentTime);
	tRIBsnsBillLockSchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));

	//执行动作：insert 添加纪录
	if ("INSERT".equals(mOperateType)) {
		String tLockState = request.getParameter("state");

		tRIBsnsBillLockSchema.setLockState(tLockState);
		if (tLockState.equals("1")) {
			String mLockDate = request.getParameter("LockDate");
			tRIBsnsBillLockSchema.setLockDate(mLockDate);
		} else {
			tRIBsnsBillLockSchema.setUnLockDate(currentDate);
		}
	}
	VData tVData = new VData();
	tVData.add(tRIBsnsBillLockSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIBsnsBillLockUI")) {
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
