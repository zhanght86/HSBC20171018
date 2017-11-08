<%@include file="/i18n/language.jsp"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//程序名称：RIProfitLossCalmSave.jsp
	//程序功能：盈余佣金计算
	//创建日期：2011/8/22
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	RIProLossRelaSchema mRIProLossRelaSchema = new RIProLossRelaSchema();

	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	VData mResult = new VData();
	VData tVData = new VData();
	String[][] mr = new String[0][0];
	String mOperateType = request.getParameter("OperateType");


	if ("ININTPARAM".equals(mOperateType)) {

		//从url中取出参数付给相应的schema
		mRIProLossRelaSchema.setRIProfitNo(request
				.getParameter("RIProfitNo"));
		mRIProLossRelaSchema.setCurrency(request
				.getParameter("Currency"));
		mRIProLossRelaSchema.setReComCode(request
				.getParameter("RIcomCode"));

		tVData.addElement(mRIProLossRelaSchema);
		String calyear = request.getParameter("CalYear");
		TransferData transferData = new TransferData();
		transferData.setNameAndValue("calyear", calyear);
		tVData.addElement(transferData);
		tVData.addElement(tG);
	} else if ("CALCULATE".equals(mOperateType)) {
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
		tRIProLossResultSchema.setCalYear(request
				.getParameter("CalYear"));

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
		tVData.addElement(tRIProLossRelaSchema);
		tVData.addElement(tRIProLossValueSet);
		tVData.addElement(tRIProLossCalSchema);
		tVData.addElement(tRIProLossResultSchema);
		tVData.addElement(tG);

	}

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossCalmUI")) {
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
			if ("ININTPARAM".equals(mOperateType)) {
				mResult = uiBusinessDelegate.getResult();
				mr = (String[][]) mResult.get(0);
			}
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

parent.fraInterface.Mul10Grid.clearData();
<%if ("ININTPARAM".equals(mOperateType)) {
				for (int i = 0; i < mr.length; i++) {%>   
parent.fraInterface.Mul10Grid.addOne();
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 1, '<%=mr[i][0]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 2, '<%=mr[i][1]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 3, '<%=mr[i][2]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 4, '<%=mr[i][3]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 5, '<%=mr[i][4]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 6, '<%=mr[i][5]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 7, '<%=mr[i][6]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 8, '<%=mr[i][7]%>');

<%}
			}%>
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
