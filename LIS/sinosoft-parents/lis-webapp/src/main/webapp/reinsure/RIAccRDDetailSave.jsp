<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIAccRDQuerySave.jsp
	//�����ܣ��ֳ����ζ���-��Ϣ��ѯ
	//�������ڣ�2011/6/16
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
	String mOperateType = request.getParameter("OperateType");
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	PubFun tPubFun = new PubFun();
	String currentDate = tPubFun.getCurrentDate();
	String currentTime = tPubFun.getCurrentTime();

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//��url��ȡ������������Ӧ��schema
	//t��������Schema.set��������(request.getParameter("��������"));
	RIAccumulateRDCodeSchema tRIAccumulateRDCodeSchema = new RIAccumulateRDCodeSchema();
	RIAccumulateGetDutySchema tRIAccumulateGetDutySchema = new RIAccumulateGetDutySchema();
	tRIAccumulateRDCodeSchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));
	tRIAccumulateRDCodeSchema.setAssociatedCode(request
			.getParameter("AssociatedCode"));
	tRIAccumulateRDCodeSchema.setAssociatedName(request
			.getParameter("AssociatedName"));
	tRIAccumulateGetDutySchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));
	tRIAccumulateGetDutySchema.setAssociatedCode(request
			.getParameter("AssociatedCode"));
	tRIAccumulateGetDutySchema.setGetDutyCode(request
			.getParameter("GetDutyCode"));
	tRIAccumulateGetDutySchema.setGetDutyName(request
			.getParameter("GetDutyName"));

	VData tVData = new VData();
	tVData.add(tG);
	tVData.addElement(tRIAccumulateRDCodeSchema);
	tVData.addElement(tRIAccumulateGetDutySchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIAccRDDetailUI")) {
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

