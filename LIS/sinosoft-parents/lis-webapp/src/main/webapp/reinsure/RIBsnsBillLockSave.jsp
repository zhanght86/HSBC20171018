<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIBsnsBillLockSave.jsp
	//�����ܣ����ݼ���
	//�������ڣ�2011/8/11
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
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	RIBsnsBillLockSchema tRIBsnsBillLockSchema = new RIBsnsBillLockSchema();
	tRIBsnsBillLockSchema.setOperator(tG.Operator);
	tRIBsnsBillLockSchema.setMakeDate(currentDate);
	tRIBsnsBillLockSchema.setMakeTime(currentTime);
	tRIBsnsBillLockSchema.setModifyDate(currentDate);
	tRIBsnsBillLockSchema.setModifyTime(currentTime);
	tRIBsnsBillLockSchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));

	//ִ�ж�����insert ��Ӽ�¼
	if ("INSERT".equals(mOperateType)) {
		String tLockState = request.getParameter("state");

		tRIBsnsBillLockSchema.setLockState(tLockState);
		if (tLockState.equals("1")) {
			String mLockDate = request.getParameter("LockDate");
			tRIBsnsBillLockSchema.setLockDate(mLockDate);
		} else {
			tRIBsnsBillLockSchema.setUnLockDate(currentDate);
		}
	}
	VData tVData = new VData();
	tVData.add(tRIBsnsBillLockSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIBsnsBillLockUI")) {
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
