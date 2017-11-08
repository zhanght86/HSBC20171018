<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>

<%
	//程序名称：PDLCalculatorSave.jsp
	//程序功能：累加器配置
	//创建日期：2016-5-13
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//接收信息，并作校验处理。
	//输入参数
	String Content = "";
	String FlagStr = "";
	String tCALCODE = "";
	String dutycode="";
	// 准备传输数据 VData
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

		//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		operator = request.getParameter("operator");
		//获取前台数据
		/*产品代码*/
		String RiskCode = request.getParameter("RiskCode");
		/*给付责任代码*/
		String getdutycode = request.getParameter("GetDutyCode");
		/*累加器编码*/
		String calculatorCode = request.getParameter("CalculatorCode");
		/*累加器名称*/
		String calculatorName = request.getParameter("CalculatorName");
		/*累加器层级*/
		String ctrlLevel = request.getParameter("CtrlLevel");
		/*累加器类型*/
		String calculatorType = request.getParameter("CalculatorType");
		/*累加器要素*/
		String ctrlFactorType = request.getParameter("CtrlFactorType");
		/*累加器要素值*/
		String ctrlFactorValue = request
				.getParameter("CtrlFactorValue");
		/*要素单位*/
		String ctrlFactorUnit = request.getParameter("CtrlFactorUnit");
		/*要素值计算方式*/
		String calMode = request.getParameter("CalMode");
		/*要素值算法编码*/
		String CalCode = request.getParameter("CalCode");
		/*默认值*/
		String defaultValue = request.getParameter("DefaultValue");
		/*累加器生效起期*/
		String startDate = request.getParameter("StartDate");
		/*累加器生效止期*/
		String endDate = request.getParameter("EndDate");
		/*累加器生效时长*/
		String validPeriod = request.getParameter("ValidPeriod");
		/*累加器生效时长单位*/
		String validPeriodUnit = request
				.getParameter("ValidPeriodUnit");
		/*累加器步骤*/
		String calOrder = request.getParameter("CalOrder");
		/*后置累加器编号*/
		String calculatorCodeAfter = request
				.getParameter("CalculatorCodeAfter");
		/*明细流水号*/
		String SerialNo = request.getParameter("SerialNo");
		/*费用类型*/
		String feeType = request.getParameter("FeeType");
		/*自付比例*/
		String StandFlag2 = request.getParameter("selfPayPercent");
		if("".equals(StandFlag2)){
			StandFlag2="0";
		}
		/*是否扣除自付比例*/
		String StandFlag1 = request.getParameter("ifselfPayPercent");
		if("".equals(StandFlag1)){
			StandFlag1="N";
		}
		/*免赔额*/
		String PreAuthFlag = request.getParameter("IfPayMoney");
		if("".equals(PreAuthFlag)){
			PreAuthFlag="0";
		}
		/*账单编码*/
		String feecode=request.getParameter("FeeCode");
		/*保单年度金额上限*/
		String MoneyLimitAnnual = "";
		/*保单年度天数上限*/
		String DaysLimitAnnual = "";
		/*保单年度次数上限*/
		String CountLimitAnnual = "";
		/*每次赔偿金额上限*/
		String MoneyLimitEveryTime="";
		/*每天赔付金额上限*/
		String MoneyLimitEveryDay = "";
		/*等待期*/
		//String ObsPeriod = request.getParameter("ObsPeriod");
		/*等待期单位*/
		//String ObsPeriodUn = request.getParameter("ObsPeriodUn");

		/*根据getdutycode获取dutycode*/
		String SQL = "select distinct dutycode from pd_lmdutygetrela where getdutycode='"
				+ getdutycode + "'";
		dutycode = new ExeSQL().getOneValue(SQL);
		/*根据getdutycode获取getdutykind*/
		/*String SQL1 = "select getdutykind from pd_lmdutygetclm where getdutycode='"
				+ getdutycode + "' and rownum='1'";
		String getdutykind = new ExeSQL().getOneValue(SQL1);
		if (getdutykind == null || "".equals(getdutykind)) {
			getdutykind = "000";
		}*/
		/*累加器编码的生成规则*/
		/*如果是关联保存的话，我们不需要生成累加器编码*/
		String flagstr=request.getParameter("FlagStr");
		
		if("0".equals(flagstr) && "".equals(calculatorCode)){
			/*给付责任层级：getdutycode+feecode+A+要素类型+层级*/
			if("1".equals(ctrlLevel)){
				calculatorCode=getdutycode+feecode+"A"+ctrlFactorType+"1";
			}else if("2".equals(ctrlLevel)){
				/*责任层级：dutycode+A+要素类型+层级*/
				calculatorCode=dutycode+"A"+ctrlFactorType+"2";
			}else if("3".equals(ctrlLevel)){
				/*责任层以上：riskcode+A+要素类型+层级*/
					calculatorCode=RiskCode+"A"+ctrlFactorType+"3";
					
			}else if("4".equals(ctrlLevel)){
				calculatorCode=RiskCode+"A"+ctrlFactorType+"4";
			}else{
				calculatorCode="PRODUCTDEF"+"A"+ctrlFactorType+ctrlLevel;
			}
		}
		String getdutykind="A";
		//根据累加器的要素类型进行账单信息的赋值
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
		/*累加器关联账单信息*/
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
				//pd_ldplanfeerela 如果单纯的是给付责任额关联不需要执行该表
				if("0".equals(flagstr)){
				tPD_LDPlanFeeRelaSchema.setPlanCode("A");//保障计划 目前系统中并没有配置保障计划，默认值为A
				tPD_LDPlanFeeRelaSchema.setRiskCode(RiskCode);//产品编码
				tPD_LDPlanFeeRelaSchema.setDutyCode(dutycode);//责任编码
				if ("2".equals(ctrlLevel) && "".equals(feecode)) {//如果累加器的层级是2，且没有账单的情况下，上限为责任层级的上限，如果没有账单累加器的层级是1，表示给付责任层级的上限
					tPD_LDPlanFeeRelaSchema.setGetDutyCode("0000");//给付责任编码
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//账单费用项代码
					tPD_LDPlanFeeRelaSchema.setFeeType("");//费用类型
				}else if("1".equals(ctrlLevel) && "".equals(feecode)){
					tPD_LDPlanFeeRelaSchema.setGetDutyCode(getdutycode);//给付责任编码
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//账单费用项代码
					tPD_LDPlanFeeRelaSchema.setFeeType("");//费用类型
				}else if("3".equals(ctrlLevel) && "".equals(feecode)){
					tPD_LDPlanFeeRelaSchema.setDutyCode("0000");//责任编码
					tPD_LDPlanFeeRelaSchema.setGetDutyCode("0000");//给付责任编码
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//账单费用项代码
					tPD_LDPlanFeeRelaSchema.setFeeType("");//费用类型
				}else if("4".equals(ctrlLevel) && "".equals(feecode)){
					tPD_LDPlanFeeRelaSchema.setDutyCode("0000");//责任编码
					tPD_LDPlanFeeRelaSchema.setGetDutyCode("0000");//给付责任编码
					tPD_LDPlanFeeRelaSchema.setFeeCode("0000");//账单费用项代码
					tPD_LDPlanFeeRelaSchema.setFeeType("");//费用类型
				}
				else{
					tPD_LDPlanFeeRelaSchema.setGetDutyCode(getdutycode);//给付责任编码
					tPD_LDPlanFeeRelaSchema.setFeeCode(feecode);//账单费用项代码
					tPD_LDPlanFeeRelaSchema.setFeeType(feeType);//费用类型
				}
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitAnnual(MoneyLimitAnnual);//保单年度金额上限
				tPD_LDPlanFeeRelaSchema
						.setCountLimitAnnual(CountLimitAnnual);//保单年度次数上限
				tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual(DaysLimitAnnual);//保单年度天数上限
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitEveryTime(MoneyLimitEveryTime);//每次赔偿金额上限
				tPD_LDPlanFeeRelaSchema
						.setDaysLimitEveryTime("");//每次赔偿天数上限
				tPD_LDPlanFeeRelaSchema
						.setMoneyLimitEveryDay(MoneyLimitEveryDay);//每天赔付金额上限
				tPD_LDPlanFeeRelaSchema
						.setPayMoneyEveryDay("");//每天赔偿固定金额
				//tPD_LDPlanFeeRelaSchema.setObsPeriod(ObsPeriod);//等待期
				//tPD_LDPlanFeeRelaSchema.setObsPeriodUn(ObsPeriodUn);//等待期单位
				tPD_LDPlanFeeRelaSchema.setStandFlag1(StandFlag1);//是否扣除自负比例
				tPD_LDPlanFeeRelaSchema.setStandFlag2(StandFlag2);//自负比例
				tPD_LDPlanFeeRelaSchema.setPreAuthFlag(PreAuthFlag);//免赔额
				mPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaSchema;
				}	
		//根据前端传递的参数进行赋值操作PD_LCalculatorMain
		PD_LCalculatorMainSchema tPD_LCalculatorMainSchema = new PD_LCalculatorMainSchema();
		/*累加器的編碼規則：給付責任編碼+費用賬單類型編碼*/
		tPD_LCalculatorMainSchema.setCalculatorCode(calculatorCode);//累加器编号
		tPD_LCalculatorMainSchema.setCalculatorName(calculatorName);//累加器名称
		tPD_LCalculatorMainSchema.setCtrlLevel(ctrlLevel);//累加器层级
		tPD_LCalculatorMainSchema.setType(calculatorType);//累加器类型
		tPD_LCalculatorMainSchema.setCtrlFactorType(ctrlFactorType);//要素类型
		tPD_LCalculatorMainSchema.setCtrlFactorValue(ctrlFactorValue);//要素值
		tPD_LCalculatorMainSchema.setCtrlFactorUnit(ctrlFactorUnit);//要素单位
		tPD_LCalculatorMainSchema.setCalMode(calMode);//要素值计算方式
		tPD_LCalculatorMainSchema.setCalCode(CalCode);//要素值计算公式
		tPD_LCalculatorMainSchema.setDefaultValue(defaultValue);//默认值
		PD_LCalculatorMainSchema mPD_LCalculatorMainSchema = new PD_LCalculatorMainSchema();
		mPD_LCalculatorMainSchema = tPD_LCalculatorMainSchema;

		//根据前端传递的参数进行赋值操作PD_LCalculatorNatureTime
		PD_LCalculatorNatureTimeSchema tPD_LCalculatorNatureTimeSchema = new PD_LCalculatorNatureTimeSchema();
		tPD_LCalculatorNatureTimeSchema
				.setCalculatorCode(calculatorCode);//累加器编号
		tPD_LCalculatorNatureTimeSchema.setStartDate(startDate);//生效起期
		tPD_LCalculatorNatureTimeSchema.setEndDate(endDate);//生效止期
		PD_LCalculatorNatureTimeSchema mPD_LCalculatorNatureTimeSchema = new PD_LCalculatorNatureTimeSchema();
		mPD_LCalculatorNatureTimeSchema = tPD_LCalculatorNatureTimeSchema;

		//pd_LCalculatorInsuranceTime
		PD_LCalculatorInsuranceTimeSchema tPD_LCalculatorInsuranceTimeSchema = new PD_LCalculatorInsuranceTimeSchema();
		tPD_LCalculatorInsuranceTimeSchema
				.setCalculatorCode(calculatorCode);//累加器编号
		tPD_LCalculatorInsuranceTimeSchema.setValidPeriod(validPeriod);//生效时长
		tPD_LCalculatorInsuranceTimeSchema
				.setValidPeriodUnit(validPeriodUnit);//生效时长单位
		PD_LCalculatorInsuranceTimeSchema mPD_LCalculatorInsuranceTimeSchema = new PD_LCalculatorInsuranceTimeSchema();
		mPD_LCalculatorInsuranceTimeSchema = tPD_LCalculatorInsuranceTimeSchema;

		//PD_LCalculatorOrder
		PD_LCalculatorOrderSchema tPD_LCalculatorOrderSchema = new PD_LCalculatorOrderSchema();
		tPD_LCalculatorOrderSchema.setCalculatorCode(calculatorCode);//累加器编码
		tPD_LCalculatorOrderSchema.setCalOrder(calOrder);//累加步骤
		tPD_LCalculatorOrderSchema.setCalCode(calculatorCodeAfter);//后置累加器编号
		PD_LCalculatorOrderSchema mPD_LCalculatorOrderSchema = new PD_LCalculatorOrderSchema();
		mPD_LCalculatorOrderSchema = tPD_LCalculatorOrderSchema;

		//PD_LCalculatorFactor
		PD_LCalculatorFactorSchema tPD_LCalculatorFactorSchema = new PD_LCalculatorFactorSchema();
		tPD_LCalculatorFactorSchema.setCalculatorCode(calculatorCode);//累加器编号
		tPD_LCalculatorFactorSchema.setSerialNo(SerialNo);//明细流水号
		tPD_LCalculatorFactorSchema.setRiskCode(RiskCode);//产品代码
		tPD_LCalculatorFactorSchema.setDutyCode(dutycode);//责任代码
		tPD_LCalculatorFactorSchema.setGetDutyCode(getdutycode);//给付责任代码
		tPD_LCalculatorFactorSchema.setGetDutyKind(getdutykind);//给付责任类型
		PD_LCalculatorFactorSchema mPD_LCalculatorFactorSchema = new PD_LCalculatorFactorSchema();
		mPD_LCalculatorFactorSchema = tPD_LCalculatorFactorSchema;

		//判断累加器层级，如果大于责任层级

		//算法编码
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
			Content = " 处理失败，原因是:"
					+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			VData rVData = tBusinessDelegate.getResult();
			tCALCODE = (String) rVData.get(0);
			Content = " 处理成功! ";
			FlagStr = "Succ";
		}

	} catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	//添加各种预处理
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

