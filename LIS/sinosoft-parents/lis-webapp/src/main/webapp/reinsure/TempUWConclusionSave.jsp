<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//��ʽ���Q��TempUWConclusionSave.jsp
	//��ʽ���ܣ�
	//�������ڣ�
	//������  ��
	//����ӛ䛣�  ������    ��������     ����ԭ��/����
%>
<!--�Ñ�У��-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//ݔ������
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
			.getParameter("lcTempUWConclusion");//�ٱ����r�YՓ
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
	//ContType:'1'-���Σ�'2'-�F�� CONOPETYPE:'01'-ȫ���½Y,'02'-�x�е��½YՓ   UWFLAG:'1'-�˱��R�ֽYՓ��'0'-�ٱ��R�ֽYՓ OPEDATA:IF(ContType=='1') THEN ProposalContNo ELSE ProposalGrpContNo
	if (tContType.equals("1")) {//���U

		if (tCONOPETYPE.equals("01")) {
		} else if (tCONOPETYPE.equals("02")) { //�x��Y��
			if (tUWFlag.equals("1")) { //�˱��R�ֽYՓ
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
					tRIDutyStateSchema.setStandbyString1(tSerialNo); //�R���΄���̖

					mRIDutyStateSet.add(tRIDutyStateSchema);
				}
				tVData.add(mRIDutyStateSet);

			} else if (tUWFlag.equals("2")) { //�ٱ����r�YՓ
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
					mRIClaimStateSchema.setStandbyString1(tSerialNo); //�΄���̖

					mRIClaimStateSet.add(mRIClaimStateSchema);

				}
				tVData.add(mRIClaimStateSet);

			} else { //�ٱ��R�ֽYՓ
				String tChk[] = request
						.getParameterValues("InpIndTempListGridChk");
				String[] StrNum = request
						.getParameterValues("IndTempListGridNo");
				String[] proposalNo = request
						.getParameterValues("IndTempListGrid9");
				String[] dutyCode = request
						.getParameterValues("IndTempListGrid3");
				String[] state1 = request
						.getParameterValues("IndTempListGrid12"); //�˱��YՓ���a���
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
					tRIDutyStateSchema.setStandbyString1(tSerialNo); //�R���΄���̖

					mRIDutyStateSet.add(tRIDutyStateSchema);
				}
				tVData.add(mRIDutyStateSet);
			}
		} else if (tCONOPETYPE.equals("03")) { //ȡ���R�ֽYՓ
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
					tRIDutyStateSchema.setStandbyString1(tSerialNo); //�R���΄���̖

					mRIDutyStateSet.add(tRIDutyStateSchema);
				}
				tVData.add(mRIDutyStateSet);
			}
		} else if (tCONOPETYPE.equals("04")) { // ̎�팏���ꮅ

		}

		BusinessDelegate uiBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!uiBusinessDelegate.submitData(tVData, tCONOPETYPE,
				"RIIndTempConclusionBL")) {
			if (uiBusinessDelegate.getCErrors() != null
					&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
				Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+""
						+ uiBusinessDelegate.getCErrors()
								.getFirstError();
				FlagStr = "Fail";
			} else {
				Content = ""+"����ʧ��"+"";
				FlagStr = "Fail";
			}
		}

		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if ("".equals(FlagStr)) {
			tError = uiBusinessDelegate.getCErrors();
			if (!tError.needDealError()) {
				Content = ""+"����ɹ�"+"";
				FlagStr = "Succ";
			} else {
				Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+"" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
	} else { //�F�U
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
