<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�TempUWConclusionSave.jsp
	//�����ܣ�
	//�������ڣ�
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	//�������
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

	// ׼���������� VData
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

	//����
	if (tContType.equals("1")) {
		if (tCONOPETYPE.equals("02")) { //�Ը����½��۲���
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
		} else if (tCONOPETYPE.equals("03")) { //ȡ������
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
		} else if (tCONOPETYPE.equals("04")) { //ȡ������
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
			Content = ""+"����ʧ�ܣ�ԭ���ǣ�"+""
					+ uiBusinessDelegate.getCErrors().getFirstError();
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
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
