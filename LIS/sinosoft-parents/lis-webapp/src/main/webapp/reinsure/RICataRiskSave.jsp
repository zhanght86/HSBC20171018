<%@include file="/i18n/language.jsp"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//程序名称：RICataRiskSave.jsp
	//程序功能：巨灾报表
	//创建日期：2011-6-21
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

	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	transact = request.getParameter("fmtransact");

	RIBigRateFeeSchema tRiBigRateFeeSchema = new RIBigRateFeeSchema();
 	if("INSERT".equals(request.getParameter("OperateType"))){
		 tRiBigRateFeeSchema.setRateFee(request.getParameter("RateFee"));
		 tRiBigRateFeeSchema.setOperateDate(request.getParameter("MakeDate"));
	}
 	if("DELETE".equals(request.getParameter("OperateType"))){
		 tRiBigRateFeeSchema.setSerialNo(request.getParameter("SerialNo"));
 	}
	VData tVData = new VData();
	tVData.addElement(tG);
	tVData.addElement(tRiBigRateFeeSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIBigRateFeeUI")) {
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

