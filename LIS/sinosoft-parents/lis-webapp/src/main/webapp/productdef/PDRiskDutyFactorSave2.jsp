<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//�������ƣ�PDRiskDutyFactorSave.jsp
	//�����ܣ�����¼��Ҫ�ض���
	//�������ڣ�2009-3-13
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//������Ϣ������У�鴦��
	//�������

	PDRiskDutyFactor2BL pDRiskDutyFactorBL = new PDRiskDutyFactor2BL();

	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String operator = "";
	String submitFlag = "";

	TransferData transferData = new TransferData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	operator = request.getParameter("flag");
	submitFlag = request.getParameter("submitFlag");

	String tRiskCode = request.getParameter("RiskCode1");
	//Ŀǰ�Ǵ�ģ�����Ϊ��ʾ������ʹ�
	String tPayPlayCode = request.getParameter("PayPlanCode");

	//tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
	String tFactor[] = request.getParameterValues("LMDutyParamGrid1");
	String tFactorName[] = request
			.getParameterValues("LMDutyParamGrid2");
	String tChooseFlag[] = request
			.getParameterValues("LMDutyParamGrid3");
	String tValue[] = request.getParameterValues("LMDutyParamGrid4");
	String tReadOnly[] = request.getParameterValues("LMDutyParamGrid5");
	String tDes[] = request.getParameterValues("LMDutyParamGrid6");
	PD_LMRiskDutyFactorSet tPD_LMRiskDutyFactorSet = new PD_LMRiskDutyFactorSet();
	if (tFactor != null) {
		for (int i = 0; i < tFactor.length; i++) {
			PD_LMRiskDutyFactorSchema tPD_LMRiskDutyFactorSchema = new PD_LMRiskDutyFactorSchema();
			tPD_LMRiskDutyFactorSchema.setRiskCode(tRiskCode);
			tPD_LMRiskDutyFactorSchema.setDutyCode(tPayPlayCode);
			tPD_LMRiskDutyFactorSchema.setCalFactor(tFactor[i]);
			tPD_LMRiskDutyFactorSchema.setCalFactorType("1");
			tPD_LMRiskDutyFactorSchema.setFactorName(tFactorName[i]);
			tPD_LMRiskDutyFactorSchema.setChooseFlag(tChooseFlag[i]);
			tPD_LMRiskDutyFactorSchema.setFactorNoti(tDes[i]);
			tPD_LMRiskDutyFactorSchema.setFactorOrder(i);
			tPD_LMRiskDutyFactorSchema.setStandbyflag1(tReadOnly[i]);
			tPD_LMRiskDutyFactorSchema.setStandbyflag2(tValue[i]);
			tPD_LMRiskDutyFactorSchema.setOperator(tG.Operator);
			tPD_LMRiskDutyFactorSet.add(tPD_LMRiskDutyFactorSchema);
			System.out.println(tPD_LMRiskDutyFactorSchema.encode());
		}
	}

	try {
		// ׼���������� VData
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(tPD_LMRiskDutyFactorSet);
		pDRiskDutyFactorBL.submitData(tVData, operator);

		new RiskState().setState(request.getParameter("RiskCode"),
				""+"��Լҵ�����"+"->"+"���ϼƻ�Լ��Ҫ��"+"", "1");
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = pDRiskDutyFactorBL.mErrors;
		if (!tError.needDealError()) {
			Content = " "+"����ɹ�!"+" ";
			FlagStr = "Success";
		} else {
			Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
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

