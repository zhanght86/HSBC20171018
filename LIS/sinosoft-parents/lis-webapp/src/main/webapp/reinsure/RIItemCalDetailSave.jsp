<%@include file="/i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%@page contentType="text/html;charset=GBK"%>
<%
	//������Ϣ������У�鴦��
	//�������
	RIItemCalSchema tRIItemCalSchema = new RIItemCalSchema();

	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	String tOperate = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");
	tRIItemCalSchema.setArithmeticDefID(request
			.getParameter("ArithmeticDefID"));
	tRIItemCalSchema.setArithmeticID(request
			.getParameter("KArithmeticDefID"));
	tRIItemCalSchema.setArithmeticName(request
			.getParameter("KArithmeticDefName"));
	tRIItemCalSchema.setArithmeticType(request
			.getParameter("ArithmeticType"));
	tRIItemCalSchema.setCalItemID(request.getParameter("CalItemID"));
	tRIItemCalSchema.setCalItemName(request
			.getParameter("CalItemIDName"));

	tRIItemCalSchema.setCalItemOrder(request
			.getParameter("CalItemOrder"));
	tRIItemCalSchema.setCalItemType(request.getParameter("Business"));
	tRIItemCalSchema
			.setItemCalType(request.getParameter("DistillMode"));
	tRIItemCalSchema
			.setDoubleValue(request.getParameter("DoubleValue"));
	tRIItemCalSchema.setCalClass(request.getParameter("DistillClass"));
	tRIItemCalSchema.setCalSQL(request.getParameter("DistillSQL"));

	if (request.getParameter("TarGetClumn") == null) {
		tRIItemCalSchema.setTarGetClumn("000000");
	} else {
		tRIItemCalSchema.setTarGetClumn(request
				.getParameter("TarGetClumn"));
	}
	tRIItemCalSchema.setReMark(request.getParameter("Remark"));
	tRIItemCalSchema.setStandbyString1(request
			.getParameter("StandbyString1"));
	tRIItemCalSchema.setStandbyString2(request
			.getParameter("StandbyString2"));
	tRIItemCalSchema.setStandbyString3(request
			.getParameter("StandbyString3"));

	// ׼���������� VData
	VData tVData = new VData();
	FlagStr = "";
	tVData.addElement(tRIItemCalSchema);
	tVData.add(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIItemCalDetailBL")) {
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
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>

