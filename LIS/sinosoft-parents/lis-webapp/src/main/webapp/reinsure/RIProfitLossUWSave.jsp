<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//�������ƣ�RIProfitLossUWSave.jsp
	//�����ܣ�ӯ��Ӷ�����
	//�������ڣ�2011/8/18
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");
	RIProLossResultSchema tRIProLossResultSchema = new RIProLossResultSchema();

	String[] strSelNo = request.getParameterValues("InpMul9GridSel");
	String[] strRIProfitNo = request.getParameterValues("Mul9Grid1");
	String[] strCalYear = request.getParameterValues("Mul9Grid2");
	String[] strCurrency = request.getParameterValues("Mul9Grid8");
	if (strSelNo != null) {
		for (int i = 0; i < strSelNo.length; i++) {
			if ("1".equals(strSelNo[i])) {
				tRIProLossResultSchema.setRIProfitNo(strRIProfitNo[i]);
				tRIProLossResultSchema.setCalYear(strCalYear[i]);
				tRIProLossResultSchema.setCurrency(strCurrency[i]);
			}
		}
	}

	VData tVData = new VData();
	tVData.addElement(tRIProLossResultSchema);
	String mState = request.getParameter("RILossUWReport");
	TransferData transferData = new TransferData();
	transferData.setNameAndValue("state", mState);
	tVData.addElement(transferData);
	tVData.addElement(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossUWUI")) {
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
