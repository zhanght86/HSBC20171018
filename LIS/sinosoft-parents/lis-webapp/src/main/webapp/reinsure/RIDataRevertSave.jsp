<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：RIDataRevertSave.jsp
	//程序功能：数据回滚
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
	String mOperateType = request.getParameter("OperateType");

	String mAccumulateDefNO = request.getParameter("AccumulateDefNO");
	String mInsuredNo = request.getParameter("InsuredNo");
	String mStartDate = request.getParameter("StartDate");

	RIDataRevertLogSchema tRIDataRevertLogSchema = new RIDataRevertLogSchema();
	
	tRIDataRevertLogSchema.setAccumulateDefNo(mAccumulateDefNO);
	tRIDataRevertLogSchema.setInsuredNo(mInsuredNo);
	tRIDataRevertLogSchema.setStartDate(mStartDate);
	
	//准备传输数据VData
	VData tVData = new VData();

	//传输schema
	tVData.addElement(tRIDataRevertLogSchema);
	tVData.add(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIDataRevertUI")) {
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
			Content = ""+"数据回滚成功"+"";
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

