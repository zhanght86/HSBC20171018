<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//�������ƣ�RIProfitLossDefSave.jsp
	//�����ܣ�ӯ��Ӷ����
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
	//������Ϣ������У�鴦��
	RIProfitDefSchema tRIProfitDefSchema = new RIProfitDefSchema();
	RIProfitRelaSet tRIProfitRelaSet = new RIProfitRelaSet();

	//�������     
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
	//��ȡ������Ϣ 
	tRIProfitDefSchema
			.setRIProfitNo(request.getParameter("RIProfitNo"));

	tRIProfitDefSchema.setRIProfitName(request
			.getParameter("RIProfitName"));
	tRIProfitDefSchema.setReComCode(request.getParameter("RIcomCode"));
	tRIProfitDefSchema.setRIContNo(request.getParameter("ContNo"));
	tRIProfitDefSchema.setRelaType(request.getParameter("RelaType"));
	tRIProfitDefSchema.setRIProfitDes(request
			.getParameter("RIProfitDes"));
	tRIProfitDefSchema.setModifyDate(currentDate);
	tRIProfitDefSchema.setModifyTime(currentTime);
	tRIProfitDefSchema.setOperator(tG.Operator);

	//��ȡ������Ϣ
	String[] strNumber = request.getParameterValues("Mul9GridNo");
	String[] strRIProfitRelaID = request
			.getParameterValues("Mul9Grid1");
	String[] strRIProfitRelaName = request
			.getParameterValues("Mul9Grid2");

	if (strNumber != null) {
		int tLength = strNumber.length;
		for (int i = 0; i < tLength; i++) {

			RIProfitRelaSchema tRIProfitRelaSchema = new RIProfitRelaSchema();
			tRIProfitRelaSchema.setRIProfitNo(request
					.getParameter("RIProfitNo"));
			tRIProfitRelaSchema.setRIProfitRelaID(strRIProfitRelaID[i]);
			tRIProfitRelaSchema
					.setRIProfitRelaName(strRIProfitRelaName[i]);
			tRIProfitRelaSchema.setModifyDate(currentDate);
			tRIProfitRelaSchema.setModifyTime(currentTime);
			tRIProfitRelaSchema.setOperator(tG.Operator);
			tRIProfitRelaSet.add(tRIProfitRelaSchema);
		}
	}
	VData tVData = new VData();
	tVData.addElement(tRIProfitDefSchema);
	tVData.addElement(tRIProfitRelaSet);

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossDefUI")) {
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
