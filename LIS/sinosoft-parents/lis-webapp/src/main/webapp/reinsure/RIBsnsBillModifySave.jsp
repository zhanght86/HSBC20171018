<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//�������ƣ�RIBsnsBillModifySave.jsp
	//�����ܣ��˵��޸�
	//�������ڣ�2011-08-18
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

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

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	transact = request.getParameter("OperateType");
	VData tVData = new VData();
	//��url��ȡ������������Ӧ��schema
	String[] arrSelNo = request.getParameterValues("InpMul10GridSel");
	String[] arrBillNo = request.getParameterValues("Mul10Grid1");
	String[] arrDCType = request.getParameterValues("Mul10Grid10");
	String[] arrSerialNo = request.getParameterValues("Mul10Grid8");
	String[] arrCurrency = request.getParameterValues("Mul10Grid9");
	String strBillNo = "";
	String strDCType = "";
	String strBatchNo = "";
	String strCurrency = "";
	if (arrSelNo != null) {
		for (int i = 0; i < arrSelNo.length; i++) {
			if ("1".equals(arrSelNo[i])) {
				strBillNo = arrBillNo[i];
				strDCType = arrDCType[i];
				strBatchNo = arrSerialNo[i];
				strCurrency = arrCurrency[i];
			}
		}
	}
	
	RIBsnsBillBatchSchema tRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
	tRIBsnsBillBatchSchema.setBatchNo(strBatchNo);
	tRIBsnsBillBatchSchema.setBillNo(strBillNo);
	tRIBsnsBillBatchSchema.setCurrency(strCurrency);
	
	if ("MODIFY".equals(transact)) {
		RIBsnsBillDetailsSet mFeeSet = new RIBsnsBillDetailsSet();
		RIBsnsBillDetailsSet mInfoSet = new RIBsnsBillDetailsSet();
		if ("02".equals(strDCType)) {
			String[] strFeeNum = request
					.getParameterValues("Mul12GridNo");
			String[] strFeecode = request
					.getParameterValues("Mul12Grid2");
			String[] strInputType = request
					.getParameterValues("Mul12Grid10");
			String[] strDebit = request
					.getParameterValues("Mul12Grid4");
			String[] strCredit = request
					.getParameterValues("Mul12Grid5");
			String[] strDebCre = request
					.getParameterValues("Mul12Grid8");

			for (int i = 0; i < strFeeNum.length; i++) {
				if ("02".equals(strInputType[i])) {
					RIBsnsBillDetailsSchema tRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
					tRIBsnsBillDetailsSchema.setBatchNo(strBatchNo);
					tRIBsnsBillDetailsSchema.setBillNo(strBillNo);
					tRIBsnsBillDetailsSchema.setCurrency(strCurrency);
					tRIBsnsBillDetailsSchema.setFeeCode(strFeecode[i]);
					if ("".equals(strDebit[i])
							^ "01".equals(strDebCre[i])) {
						tRIBsnsBillDetailsSchema.setSummoney(!""
								.equals(strDebit[i])
								? strDebit[i]
								: strCredit[i]);
					} else {
						tRIBsnsBillDetailsSchema.setSummoney("-"
								+ (!"".equals(strDebit[i])
										? strDebit[i]
										: strCredit[i]));
					}
					mFeeSet.add(tRIBsnsBillDetailsSchema);
				}
			}

		} else if ("01".equals(strDCType)) {
			String[] strFeeNum = request
					.getParameterValues("Mul11GridNo");
			String[] strFeecode = request
					.getParameterValues("Mul11Grid2");
			String[] strInputType = request
					.getParameterValues("Mul11Grid7");
			String[] strSummoney = request
					.getParameterValues("Mul11Grid4");

			for (int i = 0; i < strFeeNum.length; i++) {
				if ("02".equals(strInputType[i])) {
					RIBsnsBillDetailsSchema tRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
					tRIBsnsBillDetailsSchema.setBatchNo(strBatchNo);
					tRIBsnsBillDetailsSchema.setBillNo(strBillNo);
					tRIBsnsBillDetailsSchema.setCurrency(strCurrency);
					tRIBsnsBillDetailsSchema.setFeeCode(strFeecode[i]);
					tRIBsnsBillDetailsSchema
							.setSummoney(strSummoney[i]);
					mFeeSet.add(tRIBsnsBillDetailsSchema);
				}
			}
		}

		String[] strInfoNum = request.getParameterValues("Mul13GridNo");
		String[] strFeecode = request.getParameterValues("Mul13Grid2");
		String[] strInputType = request
				.getParameterValues("Mul13Grid7");
		String[] strBillItem = request.getParameterValues("Mul13Grid4");

		for (int i = 0; i < strInfoNum.length; i++) {
			if ("02".equals(strInputType[i])) {
				RIBsnsBillDetailsSchema tRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
				tRIBsnsBillDetailsSchema.setBatchNo(strBatchNo);
				tRIBsnsBillDetailsSchema.setBillNo(strBillNo);
				tRIBsnsBillDetailsSchema.setCurrency(strCurrency);
				tRIBsnsBillDetailsSchema.setFeeCode(strFeecode[i]);
				tRIBsnsBillDetailsSchema.setBillItem(strBillItem[i]);
				mInfoSet.add(tRIBsnsBillDetailsSchema);
			}
		}
		tVData.add(mFeeSet);
		tVData.add(mInfoSet);
		tVData.add(tG);
		tVData.add(tRIBsnsBillBatchSchema);
	} else if ("SUBMIT".equals(transact)|| "RECAL".equals(transact)) {

		tVData.add(tG);
		tVData.add(tRIBsnsBillBatchSchema);
	}

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, transact,
			"RIBsnsBillModifyUI")) {
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

	//��Ӹ���Ԥ����
%>
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
