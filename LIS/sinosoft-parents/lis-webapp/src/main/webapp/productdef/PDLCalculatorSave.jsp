<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>

<%
	//�������ƣ�PDLCalculatorSave.jsp
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
	String tCALCODE = "";
	String dutycode="";
	// ׼���������� VData
	VData tVData = new VData();
	PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema = new PD_LDPlanFeeRelaSchema();
	PD_LDPlanFeeRelaSchema mPD_LDPlanFeeRelaSchema = new PD_LDPlanFeeRelaSchema();
	try {
		CErrors tError = null;
		String tRela = "";
		String operator = "";
		TransferData transferData = new TransferData();
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		operator = request.getParameter("operator");
		//��ȡǰ̨����
		/*��Ʒ����*/
		String RiskCode = request.getParameter("RiskCode");
		/*�������δ���*/
		String getdutycode = request.getParameter("GetDutyCode");
		/*�ۼ�������*/
		String calculatorCode = request.getParameter("CalculatorCode");
		/*�ۼ�������*/
		String calculatorName = request.getParameter("CalculatorName");
		/*�ۼ����㼶*/
		String ctrlLevel = request.getParameter("CtrlLevel");
		/*�ۼ�������*/
		String calculatorType = request.getParameter("CalculatorType");
		/*�ۼ���Ҫ��*/
		String ctrlFactorType = request.getParameter("CtrlFactorType");
		/*�ۼ���Ҫ��ֵ*/
		String ctrlFactorValue = request
				.getParameter("CtrlFactorValue");
		/*Ҫ�ص�λ*/
		String ctrlFactorUnit = request.getParameter("CtrlFactorUnit");
		/*Ҫ��ֵ���㷽ʽ*/
		String calMode = request.getParameter("CalMode");
		/*Ҫ��ֵ�㷨����*/
		String CalCode = request.getParameter("CalCode");
		/*Ĭ��ֵ*/
		String defaultValue = request.getParameter("DefaultValue");
		/*�ۼ�����Ч����*/
		String startDate = request.getParameter("StartDate");
		/*�ۼ�����Чֹ��*/
		String endDate = request.getParameter("EndDate");
		/*�ۼ�����Чʱ��*/
		String validPeriod = request.getParameter("ValidPeriod");
		/*�ۼ�����Чʱ����λ*/
		String validPeriodUnit = request
				.getParameter("ValidPeriodUnit");
		/*�ۼ�������*/
		String calOrder = request.getParameter("CalOrder");
		/*�����ۼ������*/
		String calculatorCodeAfter = request
				.getParameter("CalculatorCodeAfter");
		/*��ϸ��ˮ��*/
		String SerialNo = request.getParameter("SerialNo");
		/*��������*/
		String feeType = request.getParameter("FeeType");
		/*�Ը�����*/
		String StandFlag2 = request.getParameter("selfPayPercent");
		if("".equals(StandFlag2)){
			StandFlag2="0";
		}
		/*�Ƿ�۳��Ը�����*/
		String StandFlag1 = request.getParameter("ifselfPayPercent");
		if("".equals(StandFlag1)){
			StandFlag1="N";
		}
		/*�����*/
		String PreAuthFlag = request.getParameter("IfPayMoney");
		if("".equals(PreAuthFlag)){
			PreAuthFlag="0";
		}
		/*�˵�����*/
		String feecode=request.getParameter("FeeCode");
		/*������Ƚ������*/
		String MoneyLimitAnnual = "";
		/*���������������*/
		String DaysLimitAnnual = "";
		/*������ȴ�������*/
		String CountLimitAnnual = "";
		/*ÿ���⳥�������*/
		String MoneyLimitEveryTime="";
		/*ÿ���⸶�������*/
		String MoneyLimitEveryDay = "";
		/*�ȴ���*/
		//String ObsPeriod = request.getParameter("ObsPeriod");
		/*�ȴ��ڵ�λ*/
		//String ObsPeriodUn = request.getParameter("ObsPeriodUn");

		/*����getdutycode��ȡdutycode*/
		String SQL = "select distinct dutycode from pd_lmdutygetrela where getdutycode='"
				+ getdutycode + "'";
		dutycode = new ExeSQL().getOneValue(SQL);
		/*����getdutycode��ȡgetdutykind*/
		/*String SQL1 = "select getdutykind from pd_lmdutygetclm where getdutycode='"
				+ getdutycode + "' and rownum='1'";
		String getdutykind = new ExeSQL().getOneValue(SQL1);
		if (getdutykind == null || "".equals(getdutykind)) {
			getdutykind = "000";
		}*/
		/*�ۼ�����������ɹ���*/
		/*����ǹ�������Ļ������ǲ���Ҫ�����ۼ�������*/
		String flagstr=request.getParameter("FlagStr");
		
		if("0".equals(flagstr) && "".equals(calculatorCode)){
			/*�������β㼶��getdutycode+feecode+A+Ҫ������+�㼶*/
			if("1".equals(ctrlLevel)){
				calculatorCode=getdutycode+feecode+"A"+ctrlFactorType+"1";
			}else if("2".equals(ctrlLevel)){
				/*���β㼶��dutycode+A+Ҫ������+�㼶*/
				calculatorCode=dutycode+"A"+ctrlFactorType+"2";
			}else if("3".equals(ctrlLevel)){
				/*���β����ϣ�riskcode+A+Ҫ������+�㼶*/
					calculatorCode=RiskCode+"A"+ctrlFactorType+"3";
					
			}else if("4".equals(ctrlLevel)){
				calculatorCode=RiskCode+"A"+ctrlFactorType+"4";
			}else{
				calculatorCode="PRODUCTDEF"+"A"+ctrlFactorType+ctrlLevel;
			}
		}
		String getdutykind="A";
		//�����ۼ�����Ҫ�����ͽ����˵���Ϣ�ĸ�ֵ
		if(!"DELETE".equals(operator)){
		if ("1".equals(ctrlFactorType)) {
			MoneyLimitAnnual = ctrlFactorValue;
		}
		if ("2".equals(ctrlFactorType)) {
			DaysLimitAnnual = ctrlFactorValue;
		}
		if ("3".equals(ctrlFactorType)) {
			CountLimitAnnual = ctrlFactorValue;
		}
		if ("4".equals(ctrlFactorType)) {
			MoneyLimitEveryDay = ctrlFactorValue;
		}
		if ("5".equals(ctrlFactorType)) {
			MoneyLimitEveryTime = ctrlFactorValue;
		}
		}else{
			if ("1".equals(ctrlFactorType)) {
				MoneyLimitAnnual = "N/A";
			}
			if ("2".equals(ctrlFactorType)) {
				DaysLimitAnnual = "N/A";
			}
			if ("3".equals(ctrlFactorType)) {
				CountLimitAnnual = "N/A";
			}
			if ("4".equals(ctrlFactorType)) {
				MoneyLimitEveryDay = "N/A";
			}
			if ("5".equals(ctrlFactorType)) {
				MoneyLimitEveryTime = "N/A";
			}
		}
		/*�ۼ��������˵���Ϣ*/
				//pd_lcalculatorfeecode
				if(!"".equals(feecode)){
				PD_LCalculatorFeeCodeSchema mPD_LCalculatorFeeCodeSchema=new PD_LCalculatorFeeCodeSchema();
				PD_LCalculatorFeeCodeSchema tPD_LCalculatorFeeCodeSchema = new PD_LCalculatorFeeCodeSchema();
				tPD_LCalculatorFeeCodeSchema
						.setCalculatorCode(calculatorCode);
				tPD_LCalculatorFeeCodeSchema
						.setFeeType(feecode);
				mPD_LCalculatorFeeCodeSchema=tPD_LCalculatorFeeCodeSchema;
				tVData.add(mPD_LCalculatorFeeCodeSchema);
				}
				//pd_ldplanfeerela ����������Ǹ������ζ��������Ҫִ�иñ�
				if("0".equals(flagstr)){
				tPD_LDPlanFeeRelaSchema.setPlanCode("A");//���ϼƻ� Ŀǰϵͳ�в�û�����ñ��ϼƻ���Ĭ��ֵΪA
				tPD_LDPlanFeeRelaSchema.setRiskCode(RiskCode);//��Ʒ����
				tPD_LDPlanFeeRelaSchema.setDutyCode(dutycode);//���α���
				if ("2".equals(ctrlLevel) && "".equals(feecode)) {//����ۼ����Ĳ㼶��2����û���˵�������£�����Ϊ���β㼶�����ޣ����û���˵��ۼ����Ĳ㼶��1����ʾ�������β㼶������
					tPD_LDPlanFeeRelaSchema.setGetDutyCode("0000");//�������α���
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//�˵����������
					tPD_LDPlanFeeRelaSchema.setFeeType("");//��������
				}else if("1".equals(ctrlLevel) && "".equals(feecode)){
					tPD_LDPlanFeeRelaSchema.setGetDutyCode(getdutycode);//�������α���
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//�˵����������
					tPD_LDPlanFeeRelaSchema.setFeeType("");//��������
				}else if("3".equals(ctrlLevel) && "".equals(feecode)){
					tPD_LDPlanFeeRelaSchema.setDutyCode("0000");//���α���
					tPD_LDPlanFeeRelaSchema.setGetDutyCode("0000");//�������α���
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//�˵����������
					tPD_LDPlanFeeRelaSchema.setFeeType("");//��������
				}else if("4".equals(ctrlLevel) && "".equals(feecode)){
					tPD_LDPlanFeeRelaSchema.setDutyCode("0000");//���α���
					tPD_LDPlanFeeRelaSchema.setGetDutyCode("0000");//�������α���
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//�˵����������
					tPD_LDPlanFeeRelaSchema.setFeeType("");//��������
				}
				else{
					tPD_LDPlanFeeRelaSchema.setGetDutyCode(getdutycode);//�������α���
					tPD_LDPlanFeeRelaSchema.setFeeCode(feecode);//�˵����������
					tPD_LDPlanFeeRelaSchema.setFeeType(feeType);//��������
				}
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitAnnual(MoneyLimitAnnual);//������Ƚ������
				tPD_LDPlanFeeRelaSchema
						.setCountLimitAnnual(CountLimitAnnual);//������ȴ�������
				tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual(DaysLimitAnnual);//���������������
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitEveryTime(MoneyLimitEveryTime);//ÿ���⳥�������
				tPD_LDPlanFeeRelaSchema
						.setDaysLimitEveryTime("");//ÿ���⳥��������
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitEveryDay(MoneyLimitEveryDay);//ÿ���⸶�������
				tPD_LDPlanFeeRelaSchema
						.setPayMoneyEveryDay("");//ÿ���⳥�̶����
				//tPD_LDPlanFeeRelaSchema.setObsPeriod(ObsPeriod);//�ȴ���
				//tPD_LDPlanFeeRelaSchema.setObsPeriodUn(ObsPeriodUn);//�ȴ��ڵ�λ
				tPD_LDPlanFeeRelaSchema.setStandFlag1(StandFlag1);//�Ƿ�۳��Ը�����
				tPD_LDPlanFeeRelaSchema.setStandFlag2(StandFlag2);//�Ը�����
				tPD_LDPlanFeeRelaSchema.setPreAuthFlag(PreAuthFlag);//�����
				mPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaSchema;
				}	
		//����ǰ�˴��ݵĲ������и�ֵ����PD_LCalculatorMain
		PD_LCalculatorMainSchema tPD_LCalculatorMainSchema = new PD_LCalculatorMainSchema();
		/*�ۼ����ľ��aҎ�t���o��؟�ξ��a+�M���~����;��a*/
		tPD_LCalculatorMainSchema.setCalculatorCode(calculatorCode);//�ۼ������
		tPD_LCalculatorMainSchema.setCalculatorName(calculatorName);//�ۼ�������
		tPD_LCalculatorMainSchema.setCtrlLevel(ctrlLevel);//�ۼ����㼶
		tPD_LCalculatorMainSchema.setType(calculatorType);//�ۼ�������
		tPD_LCalculatorMainSchema.setCtrlFactorType(ctrlFactorType);//Ҫ������
		tPD_LCalculatorMainSchema.setCtrlFactorValue(ctrlFactorValue);//Ҫ��ֵ
		tPD_LCalculatorMainSchema.setCtrlFactorUnit(ctrlFactorUnit);//Ҫ�ص�λ
		tPD_LCalculatorMainSchema.setCalMode(calMode);//Ҫ��ֵ���㷽ʽ
		tPD_LCalculatorMainSchema.setCalCode(CalCode);//Ҫ��ֵ���㹫ʽ
		tPD_LCalculatorMainSchema.setDefaultValue(defaultValue);//Ĭ��ֵ
		PD_LCalculatorMainSchema mPD_LCalculatorMainSchema = new PD_LCalculatorMainSchema();
		mPD_LCalculatorMainSchema = tPD_LCalculatorMainSchema;

		//����ǰ�˴��ݵĲ������и�ֵ����PD_LCalculatorNatureTime
		PD_LCalculatorNatureTimeSchema tPD_LCalculatorNatureTimeSchema = new PD_LCalculatorNatureTimeSchema();
		tPD_LCalculatorNatureTimeSchema
				.setCalculatorCode(calculatorCode);//�ۼ������
		tPD_LCalculatorNatureTimeSchema.setStartDate(startDate);//��Ч����
		tPD_LCalculatorNatureTimeSchema.setEndDate(endDate);//��Чֹ��
		PD_LCalculatorNatureTimeSchema mPD_LCalculatorNatureTimeSchema = new PD_LCalculatorNatureTimeSchema();
		mPD_LCalculatorNatureTimeSchema = tPD_LCalculatorNatureTimeSchema;

		//pd_LCalculatorInsuranceTime
		PD_LCalculatorInsuranceTimeSchema tPD_LCalculatorInsuranceTimeSchema = new PD_LCalculatorInsuranceTimeSchema();
		tPD_LCalculatorInsuranceTimeSchema
				.setCalculatorCode(calculatorCode);//�ۼ������
		tPD_LCalculatorInsuranceTimeSchema.setValidPeriod(validPeriod);//��Чʱ��
		tPD_LCalculatorInsuranceTimeSchema
				.setValidPeriodUnit(validPeriodUnit);//��Чʱ����λ
		PD_LCalculatorInsuranceTimeSchema mPD_LCalculatorInsuranceTimeSchema = new PD_LCalculatorInsuranceTimeSchema();
		mPD_LCalculatorInsuranceTimeSchema = tPD_LCalculatorInsuranceTimeSchema;

		//PD_LCalculatorOrder
		PD_LCalculatorOrderSchema tPD_LCalculatorOrderSchema = new PD_LCalculatorOrderSchema();
		tPD_LCalculatorOrderSchema.setCalculatorCode(calculatorCode);//�ۼ�������
		tPD_LCalculatorOrderSchema.setCalOrder(calOrder);//�ۼӲ���
		tPD_LCalculatorOrderSchema.setCalCode(calculatorCodeAfter);//�����ۼ������
		PD_LCalculatorOrderSchema mPD_LCalculatorOrderSchema = new PD_LCalculatorOrderSchema();
		mPD_LCalculatorOrderSchema = tPD_LCalculatorOrderSchema;

		//PD_LCalculatorFactor
		PD_LCalculatorFactorSchema tPD_LCalculatorFactorSchema = new PD_LCalculatorFactorSchema();
		tPD_LCalculatorFactorSchema.setCalculatorCode(calculatorCode);//�ۼ������
		tPD_LCalculatorFactorSchema.setSerialNo(SerialNo);//��ϸ��ˮ��
		tPD_LCalculatorFactorSchema.setRiskCode(RiskCode);//��Ʒ����
		tPD_LCalculatorFactorSchema.setDutyCode(dutycode);//���δ���
		tPD_LCalculatorFactorSchema.setGetDutyCode(getdutycode);//�������δ���
		tPD_LCalculatorFactorSchema.setGetDutyKind(getdutykind);//������������
		PD_LCalculatorFactorSchema mPD_LCalculatorFactorSchema = new PD_LCalculatorFactorSchema();
		mPD_LCalculatorFactorSchema = tPD_LCalculatorFactorSchema;

		//�ж��ۼ����㼶������������β㼶

		//�㷨����
		tCALCODE = CalCode;
		String tCalCodeType = request.getParameter("CalCodeSwitchFlag");

		transferData.setNameAndValue("CALCODE", tCALCODE);
		transferData.setNameAndValue("CalCodeType", tCalCodeType);
		transferData.setNameAndValue("tableName",
				request.getParameter("tableName"));
		transferData.setNameAndValue("RiskCode", RiskCode);
		transferData.setNameAndValue("GetDutyCode", getdutycode);
		transferData.setNameAndValue("CalculatorCode", calculatorCode);
		transferData.setNameAndValue("FlagStr", flagstr);

		tVData.add(tG);
		tVData.add(transferData);
		tVData.add(mPD_LCalculatorMainSchema);
		tVData.add(mPD_LCalculatorNatureTimeSchema);
		tVData.add(mPD_LCalculatorInsuranceTimeSchema);
		tVData.add(mPD_LCalculatorOrderSchema);
		tVData.add(mPD_LCalculatorFactorSchema);
		tVData.add(mPD_LDPlanFeeRelaSchema);

		String busiName = "PDLCalculatorUI";

		BusinessDelegate tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, operator, busiName)) {
			VData rVData = tBusinessDelegate.getResult();
			Content = " ����ʧ�ܣ�ԭ����:"
					+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			VData rVData = tBusinessDelegate.getResult();
			tCALCODE = (String) rVData.get(0);
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
<%=tCALCODE%>
<html>
<script language="javascript">
<%if (tCALCODE != null && !"".equals(tCALCODE) && tCALCODE != "") {%>
	parent.fraInterface.setCalCode("<%=tCALCODE%>");
<%}%>
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

