<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//�������ƣ�RICataRiskSave.jsp
	//�����ܣ����ֱ���
	//�������ڣ�2011-6-21
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

	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String mOperateType = request.getParameter("OperateType");

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	transact = request.getParameter("fmtransact");

	RIBigRateFeeSchema tRiBigRateFeeSchema = new RIBigRateFeeSchema();
 	if("INSERT".equals(request.getParameter("OperateType"))){
		 tRiBigRateFeeSchema.setRateFee(request.getParameter("RateFee"));
		 tRiBigRateFeeSchema.setOperateDate(request.getParameter("MakeDate"));
	}
 	if("DELETE".equals(request.getParameter("OperateType"))){
		 tRiBigRateFeeSchema.setSerialNo(request.getParameter("SerialNo"));
 	}
	VData tVData = new VData();
	tVData.addElement(tG);
	tVData.addElement(tRiBigRateFeeSchema);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIBigRateFeeUI")) {
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

