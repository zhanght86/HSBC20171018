<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIDataModifySave.jsp
	//�����ܣ�ҵ�����ݵ���
	//�������ڣ�2011-07-30
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
	String date = PubFun.getCurrentDate();
	String time = PubFun.getCurrentTime();
	RIDataModifyLogSet mRIDataModifyLogSet = new RIDataModifyLogSet();

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	transact = request.getParameter("fmtransact");

	//��url��ȡ������������Ӧ��schema
	RIPolRecordSchema tRIPolRecordSchema = new RIPolRecordSchema();
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	String mOperateType = request.getParameter("OperateType");

	String strEventNo = request.getParameter("Mul11Grid1");
	tRIPolRecordSchema.setEventNo(strEventNo);

	String[] strNumber = request.getParameterValues("Mul13GridNo");
	String[] strcolscode = request.getParameterValues("Mul13Grid1");
	String[] strcolsname = request.getParameterValues("Mul13Grid2");
	String[] strOriValue = request.getParameterValues("Mul13Grid3");
	String[] strModifyValue = request.getParameterValues("Mul13Grid4");

	if (strNumber != null) {
		int tLength = strNumber.length;
		for (int i = 0; i < tLength; i++) {
			RIDataModifyLogSchema tRIDataModifyLogSchema = new RIDataModifyLogSchema();
			tRIDataModifyLogSchema.setClumnCode(strcolscode[i]);
			tRIDataModifyLogSchema.setClumnName(strcolsname[i]);
			tRIDataModifyLogSchema
					.setClumnOriginalValue(strOriValue[i]);
			tRIDataModifyLogSchema
					.setClumnModifyValue(strModifyValue[i]);
			tRIDataModifyLogSchema.setOperator(tG.Operator);
			tRIDataModifyLogSchema.setManageCom(tG.ManageCom);
			tRIDataModifyLogSchema.setMakeDate(date);
			tRIDataModifyLogSchema.setMakeTime(time);
			tRIDataModifyLogSchema.setModifyDate(date);
			tRIDataModifyLogSchema.setModifyTime(time);

			mRIDataModifyLogSet.add(tRIDataModifyLogSchema);
		}
	}

	VData tVData = new VData();
	tVData.add(tRIPolRecordSchema);
	tVData.addElement(mRIDataModifyLogSet);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIDataModifyUI")) {
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

