<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：LRBsnsBillSave.jsp
	//程序功能：
	//创建日期：2007-02-28
	//创建人  ：zhangbin
	//更新记录：  更新人: zhangbin 更新日期  2008-4-14   更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	GlobalInput tG = new GlobalInput();
	tG.setSchema((GlobalInput) session.getAttribute("GI"));
	CErrors tError = null;

	String FlagStr = "";
	String Content = "";

	String mBillType = request.getParameter("BillType");
	String mStartDate = request.getParameter("StartDate");
	String mEndDate = request.getParameter("EndDate");
	String mRIComCode = request.getParameter("RIComCode");
	String mBillTimes = request.getParameter("BillTimes");

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("BillType", mBillType);
	tTransferData.setNameAndValue("StartDate", mStartDate);
	tTransferData.setNameAndValue("EndDate", mEndDate);
	tTransferData.setNameAndValue("RIComCode", mRIComCode);
	tTransferData.setNameAndValue("BillTimes", mBillTimes);
	
	VData tVData = new VData();
	tVData.add(tG);
	tVData.add(tTransferData);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, "", "RIBsnsBillCalUI")) {
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
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>