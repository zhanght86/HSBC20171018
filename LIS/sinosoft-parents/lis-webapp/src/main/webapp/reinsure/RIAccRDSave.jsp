<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//�������ƣ�RIAccRDSave.jsp
	//�����ܣ��ֳ����ζ���
	//�������ڣ�2011/6/16
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
	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getAttribute("GI"));

	RIAccumulateDefSchema mRIAccumulateDefSchema = new RIAccumulateDefSchema();
	RIAccumulateRDCodeSet mRIAccumulateRDCodeSet = new RIAccumulateRDCodeSet();
	RIAccumulateGetDutySet mRIAccumulateGetDutySet = new RIAccumulateGetDutySet();

	//�������
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();

	//��ȡ�ֳ�������Ϣ���������Ϣ 
	mRIAccumulateDefSchema.setAccumulateDefNO(request
			.getParameter("AccumulateDefNO"));
	mRIAccumulateDefSchema.setAccumulateDefName(request
			.getParameter("AccumulateDefName"));
	mRIAccumulateDefSchema.setArithmeticID(request
			.getParameter("ArithmeticDefID"));
	mRIAccumulateDefSchema.setGetDutyType(request
			.getParameter("RIAccType"));
	mRIAccumulateDefSchema.setState(request.getParameter("RIAccState"));
	mRIAccumulateDefSchema.setAccumulateMode(request
			.getParameter("StandbyFlag"));
	mRIAccumulateDefSchema.setCurrency(request
			.getParameter("moneyKind"));
	mRIAccumulateDefSchema.setDIntv(request.getParameter("DIntv"));
	mRIAccumulateDefSchema.setBFFlag(request.getParameter("BFFlag"));
	mRIAccumulateDefSchema.setStandbyFlag(request.getParameter("CalOrder"));
	GlobalInput tG = (GlobalInput) session.getAttribute("GI");

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	transact = request.getParameter("fmtransact");

	//��url��ȡ������������Ӧ��schema
	//t��������Schema.set��������(request.getParameter("��������"));

	//׼����������VData
	VData tVData = new VData();
	tVData.addElement(mRIAccumulateDefSchema);
	tVData.addElement(mRIAccumulateRDCodeSet);
	tVData.addElement(mRIAccumulateGetDutySet);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIAccRDUI")) {
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

