<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//�������ƣ�RIBsnsBillUWSave.jsp
	//�����ܣ��˵����
	//�������ڣ�2011/6/14
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	RIBsnsBillBatchSchema mRiBsnsBillBatchSchema = new RIBsnsBillBatchSchema();

	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//��url��ȡ������������Ӧ��schema
	String[] arrSelNo = request.getParameterValues("InpMul10GridSel");
	String[] arrBillNo = request.getParameterValues("Mul10Grid1");
	String[] arrSerialNo = request.getParameterValues("Mul10Grid8");
	String[] arrCurrency = request.getParameterValues("Mul10Grid9");
	String strBillNo = "";
	String strSerialNo = "";
	String strCurrency = "";
	if (arrSelNo != null) {
		for (int i = 0; i < arrSelNo.length; i++) {
			if ("1".equals(arrSelNo[i])) {
				strBillNo = arrBillNo[i];
				strSerialNo = arrSerialNo[i];
				strCurrency = arrCurrency[i];
			}
		}
	}
	String strState = request.getParameter("RIUWReport");
	mRiBsnsBillBatchSchema.setBatchNo(strSerialNo);
	mRiBsnsBillBatchSchema.setBillNo(strBillNo);
	mRiBsnsBillBatchSchema.setCurrency(strCurrency);
	mRiBsnsBillBatchSchema.setState(strState);

	VData tVData = new VData();
	tVData.add(tG);
	tVData.add(mRiBsnsBillBatchSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, null, "RIBsnsBillUWUI")) {
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

