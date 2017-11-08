<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：TempUWConclusionSave.jsp
	//程序功能：
	//创建日期：
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	//输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	String tRIContNo = request.getParameter("RIContNo");
	String tRIPreceptNo = request.getParameter("RIPreceptNo");
	String tContNo = request.getParameter("ContNo");
	String tDutyCode = request.getParameter("DutyCode");
	String tRiskCode = request.getParameter("RiskCode");
	String tContPlanCode = request.getParameter("ContPlanCode");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tInsuredName = request.getParameter("InsuredName");
	String tRelaType = request.getParameter("RelaMode");
	String tDutyCode1 = request.getParameter("DutyCode1");
	String tContPlanMode = request.getParameter("ContPlanMode");
	String tCONOPETYPE = request.getParameter("CONOPETYPE");
	String tCalFeeType = request.getParameter("CalFeeType");
	String tCalFeeTerm = request.getParameter("CalFeeTerm");
	String tContType = request.getParameter("ContType");
	String tSerialNo = request.getParameter("SerialNo");

	// 准备传输数据 VData
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
	RITempContLinkSet mRITempContLinkSet = new RITempContLinkSet();
	RITempContLinkSchema tRITempContLinkSchema;

	tTransferData.setNameAndValue("RIContNo", tRIContNo);
	tTransferData.setNameAndValue("RIPreceptNo", tRIPreceptNo);
	tTransferData.setNameAndValue("ContNo", tContNo);
	tTransferData.setNameAndValue("ContType", tContType);
	tTransferData.setNameAndValue("CalFeeType", tCalFeeType);
	tTransferData.setNameAndValue("CalFeeTerm", tCalFeeTerm);
	tTransferData.setNameAndValue("SerialNo", tSerialNo);

	//个险
	if (tContType.equals("1")) {
		if (tCONOPETYPE.equals("02")) { //对个单下结论操作
			String tChk[] = request
					.getParameterValues("InpIndTempListGridChk");
			String[] StrNum = request
					.getParameterValues("IndTempListGridNo");
			String[] contNo = request
					.getParameterValues("IndTempListGrid1");
			String[] polNo = request
					.getParameterValues("IndTempListGrid8");
			String[] riskCode = request
					.getParameterValues("IndTempListGrid3");
			String[] dutyCode = request
					.getParameterValues("IndTempListGrid4");
			String[] insuredNo = request
					.getParameterValues("IndTempListGrid10");

			for (int i = 0; i < tChk.length; i++) {
				if (tChk[i].equals("1")){

					tRITempContLinkSchema = new RITempContLinkSchema();
					tRITempContLinkSchema.setRIContNo(tRIContNo);
					tRITempContLinkSchema.setRIPreceptNo(tRIPreceptNo);
					tRITempContLinkSchema.setRelaType("01");
					tRITempContLinkSchema.setProposalGrpContNo("000000");
					tRITempContLinkSchema.setProposalNo(polNo[i]);
					tRITempContLinkSchema.setGrpProposalNo("000000");
					tRITempContLinkSchema.setProposalContNo(contNo[i]);
					tRITempContLinkSchema.setContPlanCode("000000");
					tRITempContLinkSchema.setRiskCode(riskCode[i]);
					tRITempContLinkSchema.setDutyCode(dutyCode[i]);
					tRITempContLinkSchema.setInsuredNo(insuredNo[i]);
					tRITempContLinkSchema.setCalFeeType(tCalFeeType);
					tRITempContLinkSchema.setCalFeeTerm(tCalFeeTerm);
					tRITempContLinkSchema.setStandbyString1(tSerialNo);
	
					mRITempContLinkSet.add(tRITempContLinkSchema);
				}
			}
		} else if (tCONOPETYPE.equals("03")) { //取消操作
			String tChk[] = request
					.getParameterValues("InpIndRelaListGridChk");
			String[] StrNum = request
					.getParameterValues("IndRelaListGridNo");
			String[] polNo = request
					.getParameterValues("IndRelaListGrid8");
			String[] riskCode = request
					.getParameterValues("IndRelaListGrid3");
			String[] dutyCode = request
					.getParameterValues("IndRelaListGrid4");
			String[] insuredNo = request
					.getParameterValues("IndRelaListGrid10");
			String[] preceptNo = request
					.getParameterValues("IndRelaListGrid11");
			String[] contNo = request
					.getParameterValues("IndRelaListGrid1");

			for (int i = 0; i < tChk.length; i++) {
				if (tChk[i].equals("0"))
					continue;

				tRITempContLinkSchema = new RITempContLinkSchema();
				tRITempContLinkSchema.setRIContNo(tRIContNo);
				tRITempContLinkSchema.setRIPreceptNo(preceptNo[i]);
				tRITempContLinkSchema.setRelaType("01");
				tRITempContLinkSchema.setProposalGrpContNo("000000");
				tRITempContLinkSchema.setProposalNo(polNo[i]);
				tRITempContLinkSchema.setGrpProposalNo("000000");
				tRITempContLinkSchema.setProposalContNo(contNo[i]);
				tRITempContLinkSchema.setContPlanCode("000000");
				tRITempContLinkSchema.setRiskCode(riskCode[i]);
				tRITempContLinkSchema.setDutyCode(dutyCode[i]);
				tRITempContLinkSchema.setInsuredNo(insuredNo[i]);
				tRITempContLinkSchema.setCalFeeType(tCalFeeType);
				tRITempContLinkSchema.setCalFeeTerm(tCalFeeTerm);
				tRITempContLinkSchema.setStandbyString1(tSerialNo);

				mRITempContLinkSet.add(tRITempContLinkSchema);
			}
		} else if (tCONOPETYPE.equals("04")) { //取消操作
			String[] StrNum = request
					.getParameterValues("IndRelaListGridNo");
			String[] polNo = request
					.getParameterValues("IndRelaListGrid8");
			String[] riskCode = request
					.getParameterValues("IndRelaListGrid3");
			String[] dutyCode = request
					.getParameterValues("IndRelaListGrid4");
			String[] insuredNo = request
					.getParameterValues("IndRelaListGrid10");
			String[] preceptNo = request
					.getParameterValues("IndRelaListGrid11");
			String[] contNo = request
					.getParameterValues("IndRelaListGrid1");

			for (int i = 0; i < StrNum.length; i++) {
				tRITempContLinkSchema = new RITempContLinkSchema();
				tRITempContLinkSchema.setRIContNo(tRIContNo);
				tRITempContLinkSchema.setRIPreceptNo(preceptNo[i]);
				tRITempContLinkSchema.setRelaType("01");
				tRITempContLinkSchema.setProposalGrpContNo("000000");
				tRITempContLinkSchema.setProposalNo(polNo[i]);
				tRITempContLinkSchema.setGrpProposalNo("000000");
				tRITempContLinkSchema.setProposalContNo(contNo[i]);
				tRITempContLinkSchema.setContPlanCode("000000");
				tRITempContLinkSchema.setRiskCode(riskCode[i]);
				tRITempContLinkSchema.setDutyCode(dutyCode[i]);
				tRITempContLinkSchema.setInsuredNo(insuredNo[i]);
				tRITempContLinkSchema.setCalFeeType(tCalFeeType);
				tRITempContLinkSchema.setCalFeeTerm(tCalFeeTerm);
				tRITempContLinkSchema.setStandbyString1(tSerialNo);

				mRITempContLinkSet.add(tRITempContLinkSchema);
			}
		}
		tVData.add(mRITempContLinkSet);
	}
	tVData.add(tTransferData);
	tVData.add(tG);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, tCONOPETYPE,
			"RITempConclusionBL")) {
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
