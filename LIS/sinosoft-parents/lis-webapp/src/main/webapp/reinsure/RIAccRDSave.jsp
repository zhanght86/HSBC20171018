<%@include file="/i18n/language.jsp"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//程序名称：RIAccRDSave.jsp
	//程序功能：分出责任定义
	//创建日期：2011/6/16
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

	//输入参数
	//××××Schema t××××Schema = new ××××Schema();
	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getAttribute("GI"));

	RIAccumulateDefSchema mRIAccumulateDefSchema = new RIAccumulateDefSchema();
	RIAccumulateRDCodeSet mRIAccumulateRDCodeSet = new RIAccumulateRDCodeSet();
	RIAccumulateGetDutySet mRIAccumulateGetDutySet = new RIAccumulateGetDutySet();

	//输出参数
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();

	//获取分出责任信息定义基本信息 
	mRIAccumulateDefSchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));
	mRIAccumulateDefSchema.setAccumulateDefName(request
			.getParameter("AccumulateDefName"));
	mRIAccumulateDefSchema.setArithmeticID(request
			.getParameter("ArithmeticDefID"));
	mRIAccumulateDefSchema.setGetDutyType(request
			.getParameter("RIAccType"));
	mRIAccumulateDefSchema.setState(request.getParameter("RIAccState"));
	mRIAccumulateDefSchema.setAccumulateMode(request
			.getParameter("StandbyFlag"));
	mRIAccumulateDefSchema.setCurrency(request
			.getParameter("moneyKind"));
	mRIAccumulateDefSchema.setDIntv(request.getParameter("DIntv"));
	mRIAccumulateDefSchema.setBFFlag(request.getParameter("BFFlag"));
	mRIAccumulateDefSchema.setStandbyFlag(request.getParameter("CalOrder"));
	GlobalInput tG = (GlobalInput) session.getAttribute("GI");

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	transact = request.getParameter("fmtransact");

	//从url中取出参数付给相应的schema
	//t××××Schema.set××××(request.getParameter("××××"));

	//准备传输数据VData
	VData tVData = new VData();
	tVData.addElement(mRIAccumulateDefSchema);
	tVData.addElement(mRIAccumulateRDCodeSet);
	tVData.addElement(mRIAccumulateGetDutySet);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIAccRDUI")) {
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

