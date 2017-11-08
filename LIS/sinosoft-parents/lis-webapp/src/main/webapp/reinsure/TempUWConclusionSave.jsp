<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//程式名稱：TempUWConclusionSave.jsp
	//程式功能：
	//創建日期：
	//創建人  ：
	//更新記錄：  更新人    更新日期     更新原因/內容
%>
<!--用戶校驗類-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//輸出參數
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	String tContType = request.getParameter("ContType");
	String tOpeData = request.getParameter("OpeData");
	String tContPlanCode = request.getParameter("ContPlanCode");
	String tRiskCode = request.getParameter("RiskCode");
	String tDutyCode = request.getParameter("DutyCode");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tInsuredName = request.getParameter("InsuredName");
	String tCONOPETYPE = request.getParameter("CONOPETYPE");
	String tTempUWConclusion = request.getParameter("TempUWConclusion");
	String tLCTempUWConclusion = request
			.getParameter("lcTempUWConclusion");//再保核賠結論
	String tTempUWConclusionConf = request
			.getParameter("TempUWConclusionConf");
	String tUWFlag = request.getParameter("UWFLAG");
	String tDeleteType = request.getParameter("DeleteType");
	String tSerialNo = request.getParameter("SerialNo");

	VData tVData = new VData();
	TransferData tTransferData = new TransferData();

	tTransferData
			.setNameAndValue("TempUWConclusion", tTempUWConclusion);
	tTransferData.setNameAndValue("LCTempUWConclusion",
			tLCTempUWConclusion);
	tTransferData.setNameAndValue("UWFlag", tUWFlag);
	tTransferData.setNameAndValue("ContType", tContType);
	tTransferData.setNameAndValue("DeleteType", tDeleteType);
	tTransferData.setNameAndValue("OpeData", tOpeData);
	tTransferData.setNameAndValue("SerialNo", tSerialNo);

	tVData.add(tTransferData);
	tVData.add(tG);
	//ContType:'1'-個單；'2'-團單 CONOPETYPE:'01'-全部下結,'02'-選中的下結論   UWFLAG:'1'-核保臨分結論；'0'-再保臨分結論 OPEDATA:IF(ContType=='1') THEN ProposalContNo ELSE ProposalGrpContNo
	if (tContType.equals("1")) {//個險

		if (tCONOPETYPE.equals("01")) {
		} else if (tCONOPETYPE.equals("02")) { //選擇結果
			if (tUWFlag.equals("1")) { //核保臨分結論
				String tChk[] = request
						.getParameterValues("InpRiskInfoGridChk");
				String[] StrNum = request
						.getParameterValues("RiskInfoGridNo");
				String[] proposalNo = request
						.getParameterValues("RiskInfoGrid9");
				String[] dutyCode = request
						.getParameterValues("RiskInfoGrid3");
				String[] state1 = request
						.getParameterValues("RiskInfoGrid11");

				RIDutyStateSet mRIDutyStateSet = new RIDutyStateSet();
				RIDutyStateSchema tRIDutyStateSchema;

				for (int i = 0; i < tChk.length; i++) {
					if (tChk[i].equals("0"))
						continue;
					tRIDutyStateSchema = new RIDutyStateSchema();
					tRIDutyStateSchema.setProposalGrpContNo("000000");
					tRIDutyStateSchema.setProposalNo(proposalNo[i]);
					tRIDutyStateSchema.setDutyCode(dutyCode[i]);
					tRIDutyStateSchema.setState(tTempUWConclusion);
					tRIDutyStateSchema
							.setUWConclusion(tTempUWConclusion);
					tRIDutyStateSchema.setStandbyString1(tSerialNo); //臨分任務序號

					mRIDutyStateSet.add(tRIDutyStateSchema);
				}
				tVData.add(mRIDutyStateSet);

			} else if (tUWFlag.equals("2")) { //再保核賠結論
				String tChk[] = request
						.getParameterValues("InpClaimInfoGridChk");
				String[] CaseNo = request
						.getParameterValues("ClaimInfoGrid7");
				String[] riskcode = request
						.getParameterValues("ClaimInfoGrid4");
				String[] dutyCode = request
						.getParameterValues("ClaimInfoGrid5");
				String[] state = request
						.getParameterValues("ClaimInfoGrid10");

				RIClaimStateSet mRIClaimStateSet = new RIClaimStateSet();
				RIClaimStateSchema mRIClaimStateSchema;
				for (int i = 0; i < tChk.length; i++) {
					if (tChk[i].equals("0"))
						continue;
					mRIClaimStateSchema = new RIClaimStateSchema();
					mRIClaimStateSchema.setCaseNo(CaseNo[i]);
					mRIClaimStateSchema.setRIskCode(riskcode[i]);
					mRIClaimStateSchema.setDutyCode(dutyCode[i]);
					mRIClaimStateSchema.setState(tLCTempUWConclusion);
					mRIClaimStateSchema.setUWConclusion(state[i]);
					mRIClaimStateSchema.setStandbyString1(tSerialNo); //任務序號

					mRIClaimStateSet.add(mRIClaimStateSchema);

				}
				tVData.add(mRIClaimStateSet);

			} else { //再保臨分結論
				String tChk[] = request
						.getParameterValues("InpIndTempListGridChk");
				String[] StrNum = request
						.getParameterValues("IndTempListGridNo");
				String[] proposalNo = request
						.getParameterValues("IndTempListGrid9");
				String[] dutyCode = request
						.getParameterValues("IndTempListGrid3");
				String[] state1 = request
						.getParameterValues("IndTempListGrid12"); //核保結論編碼備份
				RIDutyStateSet mRIDutyStateSet = new RIDutyStateSet();
				RIDutyStateSchema tRIDutyStateSchema;
				for (int i = 0; i < tChk.length; i++) {
					if (tChk[i].equals("0"))
						continue;
					tRIDutyStateSchema = new RIDutyStateSchema();
					tRIDutyStateSchema.setProposalGrpContNo("000000");
					tRIDutyStateSchema.setProposalNo(proposalNo[i]);
					tRIDutyStateSchema.setDutyCode(dutyCode[i]);
					tRIDutyStateSchema.setState(tTempUWConclusion);
					tRIDutyStateSchema.setUWConclusion(state1[i]);
					tRIDutyStateSchema.setStandbyString1(tSerialNo); //臨分任務序號

					mRIDutyStateSet.add(tRIDutyStateSchema);
				}
				tVData.add(mRIDutyStateSet);
			}
		} else if (tCONOPETYPE.equals("03")) { //取消臨分結論
			if (tDeleteType.equals("01")) {
				String tChk[] = request
						.getParameterValues("InpIndRelaListGridChk");
				String[] StrNum = request
						.getParameterValues("IndRelaListGridNo");
				String[] proposalNo = request
						.getParameterValues("IndRelaListGrid9");
				String[] dutyCode = request
						.getParameterValues("IndRelaListGrid3");
				String[] state1 = request
						.getParameterValues("IndRelaListGrid12");

				RIDutyStateSet mRIDutyStateSet = new RIDutyStateSet();
				RIDutyStateSchema tRIDutyStateSchema;
				for (int i = 0; i < tChk.length; i++) {
					if (tChk[i].equals("0"))
						continue;
					tRIDutyStateSchema = new RIDutyStateSchema();
					tRIDutyStateSchema.setProposalGrpContNo("000000");
					tRIDutyStateSchema.setProposalNo(proposalNo[i]);
					tRIDutyStateSchema.setDutyCode(dutyCode[i]);
					tRIDutyStateSchema.setState("02");
					tRIDutyStateSchema.setUWConclusion(state1[i]);
					tRIDutyStateSchema.setStandbyString1(tSerialNo); //臨分任務序號

					mRIDutyStateSet.add(tRIDutyStateSchema);
				}
				tVData.add(mRIDutyStateSet);
			}
		} else if (tCONOPETYPE.equals("04")) { // 處理審核完畢

		}

		BusinessDelegate uiBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!uiBusinessDelegate.submitData(tVData, tCONOPETYPE,
				"RIIndTempConclusionBL")) {
			if (uiBusinessDelegate.getCErrors() != null
					&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
				Content = ""+"保存失败，原因是："+""
						+ uiBusinessDelegate.getCErrors()
								.getFirstError();
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
	} else { //團險
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
