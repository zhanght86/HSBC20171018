<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>

<%
	//�������ƣ�PDLDPlanFeeRelaSave.jsp
	//�����ܣ��ۼ�������
	//�������ڣ�2016-5-13
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//������Ϣ������У�鴦��
	//�������
	String Content = "";
	String FlagStr = "";
	PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema=new PD_LDPlanFeeRelaSchema();
	// ׼���������� VData
	VData tVData = new VData();
	//String tCALCODE ="";
	try {
		CErrors tError = null;
		String tRela = "";
		String operator = "";
		String Transact = "";
		TransferData transferData = new TransferData();
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		operator = request.getParameter("operator");
		
		//��ȡǰ̨����
		/*��Ʒ����*/
		String RiskCode = request.getParameter("RiskCode1");
		/*�������δ���*/
		String getdutycode = request.getParameter("GetDutyCode");
		/*�������δ���*/
		String getdutycode1 = request.getParameter("GetDutyCode1");
		/*���ñ���*/
		String feecode=request.getParameter("FeeCode");
		/*������Ƚ������*/
		String MoneyLimitAnnual = request
				.getParameter("MoneyLimitAnnual");
		/*������ȴ�������*/
		String CountLimitAnnual = request
				.getParameter("CountLimitAnnual");
		/*���������������*/
		String DaysLimitAnnual = request
				.getParameter("DaysLimitAnnual");
		/*ÿ���⳥�������*/
		String MoneyLimitEveryTime = request
				.getParameter("MoneyLimitEveryTime");
		/*ÿ���⳥��������*/
		String DaysLimitEveryTime = request
				.getParameter("DaysLimitEveryTime");
		/*ÿ���⸶�������*/
		String MoneyLimitEveryDay = request
				.getParameter("MoneyLimitEveryDay");
		/*ÿ���⳥�̶����*/
		String PayMoneyEveryDay = request
				.getParameter("PayMoneyEveryDay");
		/*�ȴ���*/
		String ObsPeriod = request.getParameter("ObsPeriod");
		/*�ȴ��ڵ�λ*/
		String ObsPeriodUn = request.getParameter("ObsPeriodUn");
		/*���ϼƻ�*/
		String planCode = request.getParameter("PlanCode");	
		/*��������*/
		String feeType=request.getParameter("FeeType");
		/*�Ƿ�۳��Ը�����*/
		String StandFlag1=request.getParameter("ifselfPayPercent");
		/*�Ը�����*/
		String StandFlag2=request.getParameter("selfPayPercent");
		/*�����*/
		String PreAuthFlag=request.getParameter("IfPayMoney");
		//PD_LDPlanFeeRela
		if(planCode!=null && "".equals(planCode)){
			planCode="A";
		}
		tPD_LDPlanFeeRelaSchema.setPlanCode(planCode);//���ϼƻ�
		tPD_LDPlanFeeRelaSchema.setRiskCode(RiskCode);//��Ʒ����
		/*����getdutycode��ȡdutycode*/
		//�жϲ�������
		String dutycode="";
		if(!"INSERT".equals(operator)){
			dutycode=request.getParameter("DutyCode");
		}else{
		String SQL="select distinct dutycode from pd_lmdutygetrela where getdutycode='"+getdutycode+"'";
		dutycode=new ExeSQL().getOneValue(SQL);
		}
		if(dutycode==null || "".equals(dutycode)){
			dutycode="0000";
		}
		tPD_LDPlanFeeRelaSchema.setDutyCode(dutycode);//���α���
		tPD_LDPlanFeeRelaSchema.setGetDutyCode(getdutycode1);//�������α���
		tPD_LDPlanFeeRelaSchema.setFeeCode(feecode);//�˵����������
		if(feecode!=null && "0000".equals(feecode)){
			tPD_LDPlanFeeRelaSchema.setFeeType("");//��������
		}else{
			tPD_LDPlanFeeRelaSchema.setFeeType(feeType);//��������
		}
		tPD_LDPlanFeeRelaSchema.setMoneyLimitAnnual(MoneyLimitAnnual);//������Ƚ������
		tPD_LDPlanFeeRelaSchema.setCountLimitAnnual(CountLimitAnnual);//������ȴ�������
		tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual(DaysLimitAnnual);//���������������
		tPD_LDPlanFeeRelaSchema
				.setMoneyLimitEveryTime(MoneyLimitEveryTime);//ÿ���⳥�������
		tPD_LDPlanFeeRelaSchema
				.setDaysLimitEveryTime(DaysLimitEveryTime);//ÿ���⳥��������
		tPD_LDPlanFeeRelaSchema
				.setMoneyLimitEveryDay(MoneyLimitEveryDay);//ÿ���⸶�������
		tPD_LDPlanFeeRelaSchema.setPayMoneyEveryDay(PayMoneyEveryDay);//ÿ���⳥�̶����
		tPD_LDPlanFeeRelaSchema.setObsPeriod(ObsPeriod);//�ȴ���
		tPD_LDPlanFeeRelaSchema.setObsPeriodUn(ObsPeriodUn);//�ȴ��ڵ�λ
		tPD_LDPlanFeeRelaSchema.setStandFlag1(StandFlag1);//�Ƿ�۳��Ը�����
		tPD_LDPlanFeeRelaSchema.setStandFlag2(StandFlag2);//�Ը�����
		tPD_LDPlanFeeRelaSchema.setPreAuthFlag(PreAuthFlag);//�����
			
		tVData.add(tG);
		tVData.add(transferData);
		tVData.add(tPD_LDPlanFeeRelaSchema);
		String busiName = "PDLDPlanFeeRelaUI";

		BusinessDelegate tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, operator, busiName)) {
			VData rVData = tBusinessDelegate.getResult();
			Content = " ����ʧ�ܣ�ԭ����:"
					+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			VData rVData = tBusinessDelegate.getResult();
			//tCALCODE = (String)rVData.get(0);
			Content = " ����ɹ�! ";
			FlagStr = "Succ";
		}

	} catch (Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
	//��Ӹ���Ԥ����
%>
<%=Content%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

