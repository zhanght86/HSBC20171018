<%@include file="/i18n/language.jsp"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	//�������ƣ�RIProfitLossCalmSave.jsp
	//�����ܣ�ӯ��Ӷ�����
	//�������ڣ�2011/8/22
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	RIProLossRelaSchema mRIProLossRelaSchema = new RIProLossRelaSchema();

	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	VData mResult = new VData();
	VData tVData = new VData();
	String[][] mr = new String[0][0];
	String mOperateType = request.getParameter("OperateType");


	if ("ININTPARAM".equals(mOperateType)) {

		//��url��ȡ������������Ӧ��schema
		mRIProLossRelaSchema.setRIProfitNo(request
				.getParameter("RIProfitNo"));
		mRIProLossRelaSchema.setCurrency(request
				.getParameter("Currency"));
		mRIProLossRelaSchema.setReComCode(request
				.getParameter("RIcomCode"));

		tVData.addElement(mRIProLossRelaSchema);
		String calyear = request.getParameter("CalYear");
		TransferData transferData = new TransferData();
		transferData.setNameAndValue("calyear", calyear);
		tVData.addElement(transferData);
		tVData.addElement(tG);
	} else if ("CALCULATE".equals(mOperateType)) {
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
		tRIProLossResultSchema.setCalYear(request
				.getParameter("CalYear"));

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
		tVData.addElement(tRIProLossRelaSchema);
		tVData.addElement(tRIProLossValueSet);
		tVData.addElement(tRIProLossCalSchema);
		tVData.addElement(tRIProLossResultSchema);
		tVData.addElement(tG);

	}

	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"RIProfitLossCalmUI")) {
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
			if ("ININTPARAM".equals(mOperateType)) {
				mResult = uiBusinessDelegate.getResult();
				mr = (String[][]) mResult.get(0);
			}
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

parent.fraInterface.Mul10Grid.clearData();
<%if ("ININTPARAM".equals(mOperateType)) {
				for (int i = 0; i < mr.length; i++) {%>   
parent.fraInterface.Mul10Grid.addOne();
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 1, '<%=mr[i][0]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 2, '<%=mr[i][1]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 3, '<%=mr[i][2]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 4, '<%=mr[i][3]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 5, '<%=mr[i][4]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 6, '<%=mr[i][5]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 7, '<%=mr[i][6]%>');
parent.fraInterface.Mul10Grid.setRowColData(<%=i%>, 8, '<%=mr[i][7]%>');

<%}
			}%>
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
