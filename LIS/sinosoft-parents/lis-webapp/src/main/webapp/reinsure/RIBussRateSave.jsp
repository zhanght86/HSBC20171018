<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIBussRateSave.jsp
	//�����ܣ�����ά��
	//�������ڣ�2011-6-27
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//������Ϣ������У�鴦��

	//�������
	//��������Schema t��������Schema = new ��������Schema();

	//�������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	RIExchangeRateSchema tRIExchangeRateSchema = new RIExchangeRateSchema();
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	String mOperateType = request.getParameter("OperateType");

	String strCurrency = request.getParameter("Currency");
	String strExchangeRate = request.getParameter("ExchangeRate");
	String strStartDate = request.getParameter("StartDate");
	String strEndDate = request.getParameter("EndDate");

	tRIExchangeRateSchema.setSerialNo(request.getParameter("sn"));
	tRIExchangeRateSchema.setCurrency(strCurrency);
	tRIExchangeRateSchema.setExchangeRate(strExchangeRate);
	tRIExchangeRateSchema.setStartDate(strStartDate);
	tRIExchangeRateSchema.setEndDate(strEndDate);

	tRIExchangeRateSchema.setOperator(tG.Operator);
	tRIExchangeRateSchema.setManageCom(tG.ManageCom);
	tRIExchangeRateSchema.setModifyDate(currentDate);
	tRIExchangeRateSchema.setModifyTime(currentTime);

	tRIExchangeRateSchema.setMakeDate(currentDate);
	tRIExchangeRateSchema.setMakeTime(currentTime);

	VData tVData = new VData();
	tVData.add(tRIExchangeRateSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIBussRateUI")) {
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

	String result = "";
	if (FlagStr == "") {
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

