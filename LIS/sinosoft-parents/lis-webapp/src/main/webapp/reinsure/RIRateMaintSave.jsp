<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//�������ƣ�RIRateMaintSave.jsp
	//�����ܣ�����ά��
	//�������ڣ�2011/6/17
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
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

	RIInterestSchema tRIInterestSchema = new RIInterestSchema();
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	String mOperateType = request.getParameter("OperateType");

	String strRiskCode = request.getParameter("RiskCode");
	String strRiskName = request.getParameter("RiskName");
	//String strDutyCode = request.getParameter("DutyCode");
	//String strDutyName = request.getParameter("DutyName");

	String strInterestType = request.getParameter("InterestType");
	String strInterestRate = request.getParameter("InterestRate");
	String strStartDate = request.getParameter("StartDate");
	String strEndDate = request.getParameter("EndDate");

	tRIInterestSchema.setSerialNo(request.getParameter("sn"));
	tRIInterestSchema.setRiskCode(strRiskCode);
	tRIInterestSchema.setRiskName(strRiskName);
	//tRIInterestSchema.setDutyCode(strDutyCode);
	//tRIInterestSchema.setDutyName(strDutyName);

	tRIInterestSchema.setInterestType(strInterestType);
	tRIInterestSchema.setInterestRate(strInterestRate);
	tRIInterestSchema.setStartDate(strStartDate);
	tRIInterestSchema.setEndDate(strEndDate);

	tRIInterestSchema.setOperator(tG.Operator);
	tRIInterestSchema.setManageCom(tG.ManageCom);
	tRIInterestSchema.setModifyDate(currentDate);
	tRIInterestSchema.setModifyTime(currentTime);

	tRIInterestSchema.setMakeDate(currentDate);
	tRIInterestSchema.setMakeTime(currentTime);

	VData tVData = new VData();
	tVData.add(tRIInterestSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();

	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIRateMaintUI")) {
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

