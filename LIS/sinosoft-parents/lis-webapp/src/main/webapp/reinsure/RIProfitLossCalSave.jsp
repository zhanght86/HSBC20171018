<%@include file="/i18n/language.jsp"%>
 <%
 	//�������ƣ�LRProfitLossCalSave.jsp
 	//�����ܣ�
 	//�������ڣ�2007-03-14
 	//������  ���ű�
 	//���¼�¼��  ������    ��������     ����ԭ��/����
 %>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page contentType="text/html;charset=GBK" %>

<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ

	RIProLossValueSet tRIProLossValueSet = new RIProLossValueSet();
	RIProLossRelaSchema tRIProLossRelaSchema = new RIProLossRelaSchema();
	RIProLossCalSchema tRIProLossCalSchema = new RIProLossCalSchema();
	RIProLossResultSchema tRIProLossResultSchema = new RIProLossResultSchema();

	tRIProLossRelaSchema.setRIProfitNo(request
			.getParameter("RIProfitNo"));
	tRIProLossCalSchema.setRIProfitNo(request
			.getParameter("RIProfitNo"));
	tRIProLossResultSchema.setRIProfitNo(request
			.getParameter("RIProfitNo"));
	tRIProLossResultSchema.setCalYear(request.getParameter("CalYear"));

	String[] strNumber = request.getParameterValues("Mul10GridNo");
	//String[] strInOutType  = request.getParameterValues("Mul10Grid8");

	if (strNumber != null) {
		int tLength = strNumber.length;
		for (int i = 0; i < tLength; i++) {
			//if(strInOutType[i].equals("02")){
			RIProLossValueSchema tRIProLossValueSchema = new RIProLossValueSchema();
			tRIProLossValueSchema.setRIProfitNo(request
					.getParameter("RIProfitNo"));
			tRIProLossValueSchema.setFactorCode(request
					.getParameterValues("Mul10Grid2")[i]);
			tRIProLossValueSchema.setFactorName(request
					.getParameterValues("Mul10Grid3")[i]);
			tRIProLossValueSchema.setCurrency(request
					.getParameterValues("Mul10Grid6")[i]);
			tRIProLossValueSchema.setFactorValue(request
					.getParameterValues("Mul10Grid5")[i]);
			tRIProLossValueSchema.setCalYear(request
					.getParameter("CalYear"));
			tRIProLossValueSchema.setReComCode(request
					.getParameter("RIcomCode"));
			tRIProLossValueSchema.setRIContNo(request
					.getParameter("ContNo"));
			tRIProLossValueSchema.setBatchNo(request
					.getParameterValues("Mul10Grid9")[i]);
			tRIProLossValueSet.add(tRIProLossValueSchema);
		}
	}
	VData tVData = new VData();
	tVData.addElement(tRIProLossRelaSchema);
	tVData.addElement(tRIProLossValueSet);
	tVData.addElement(tRIProLossCalSchema);
	tVData.addElement(tRIProLossResultSchema);
	tVData.addElement(tG);
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossCalUI")) {
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
