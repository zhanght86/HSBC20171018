<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�RIDataRevertSave.jsp
	//�����ܣ����ݻع�
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
	String mOperateType = request.getParameter("OperateType");

	String mAccumulateDefNO = request.getParameter("AccumulateDefNO");
	String mInsuredNo = request.getParameter("InsuredNo");
	String mStartDate = request.getParameter("StartDate");

	RIDataRevertLogSchema tRIDataRevertLogSchema = new RIDataRevertLogSchema();
	
	tRIDataRevertLogSchema.setAccumulateDefNo(mAccumulateDefNO);
	tRIDataRevertLogSchema.setInsuredNo(mInsuredNo);
	tRIDataRevertLogSchema.setStartDate(mStartDate);
	
	//׼����������VData
	VData tVData = new VData();

	//����schema
	tVData.addElement(tRIDataRevertLogSchema);
	tVData.add(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIDataRevertUI")) {
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
			Content = ""+"���ݻع��ɹ�"+"";
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

