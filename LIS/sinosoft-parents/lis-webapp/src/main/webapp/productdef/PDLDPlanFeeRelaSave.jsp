<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>

<%
	//程序名称：PDLDPlanFeeRelaSave.jsp
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
	PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema=new PD_LDPlanFeeRelaSchema();
	// 准备传输数据 VData
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

		//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		operator = request.getParameter("operator");
		
		//获取前台数据
		/*产品代码*/
		String RiskCode = request.getParameter("RiskCode1");
		/*给付责任代码*/
		String getdutycode = request.getParameter("GetDutyCode");
		/*给付责任代码*/
		String getdutycode1 = request.getParameter("GetDutyCode1");
		/*费用编码*/
		String feecode=request.getParameter("FeeCode");
		/*保单年度金额上限*/
		String MoneyLimitAnnual = request
				.getParameter("MoneyLimitAnnual");
		/*保单年度次数上限*/
		String CountLimitAnnual = request
				.getParameter("CountLimitAnnual");
		/*保单年度天数上限*/
		String DaysLimitAnnual = request
				.getParameter("DaysLimitAnnual");
		/*每次赔偿金额上限*/
		String MoneyLimitEveryTime = request
				.getParameter("MoneyLimitEveryTime");
		/*每次赔偿天数上限*/
		String DaysLimitEveryTime = request
				.getParameter("DaysLimitEveryTime");
		/*每天赔付金额上限*/
		String MoneyLimitEveryDay = request
				.getParameter("MoneyLimitEveryDay");
		/*每天赔偿固定金额*/
		String PayMoneyEveryDay = request
				.getParameter("PayMoneyEveryDay");
		/*等待期*/
		String ObsPeriod = request.getParameter("ObsPeriod");
		/*等待期单位*/
		String ObsPeriodUn = request.getParameter("ObsPeriodUn");
		/*保障计划*/
		String planCode = request.getParameter("PlanCode");	
		/*费用类型*/
		String feeType=request.getParameter("FeeType");
		/*是否扣除自付比例*/
		String StandFlag1=request.getParameter("ifselfPayPercent");
		/*自付比例*/
		String StandFlag2=request.getParameter("selfPayPercent");
		/*免赔额*/
		String PreAuthFlag=request.getParameter("IfPayMoney");
		//PD_LDPlanFeeRela
		if(planCode!=null && "".equals(planCode)){
			planCode="A";
		}
		tPD_LDPlanFeeRelaSchema.setPlanCode(planCode);//保障计划
		tPD_LDPlanFeeRelaSchema.setRiskCode(RiskCode);//产品编码
		/*根据getdutycode获取dutycode*/
		//判断操作类型
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
		tPD_LDPlanFeeRelaSchema.setDutyCode(dutycode);//责任编码
		tPD_LDPlanFeeRelaSchema.setGetDutyCode(getdutycode1);//给付责任编码
		tPD_LDPlanFeeRelaSchema.setFeeCode(feecode);//账单费用项代码
		if(feecode!=null && "0000".equals(feecode)){
			tPD_LDPlanFeeRelaSchema.setFeeType("");//费用类型
		}else{
			tPD_LDPlanFeeRelaSchema.setFeeType(feeType);//费用类型
		}
		tPD_LDPlanFeeRelaSchema.setMoneyLimitAnnual(MoneyLimitAnnual);//保单年度金额上限
		tPD_LDPlanFeeRelaSchema.setCountLimitAnnual(CountLimitAnnual);//保单年度次数上限
		tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual(DaysLimitAnnual);//保单年度天数上限
		tPD_LDPlanFeeRelaSchema
				.setMoneyLimitEveryTime(MoneyLimitEveryTime);//每次赔偿金额上限
		tPD_LDPlanFeeRelaSchema
				.setDaysLimitEveryTime(DaysLimitEveryTime);//每次赔偿天数上限
		tPD_LDPlanFeeRelaSchema
				.setMoneyLimitEveryDay(MoneyLimitEveryDay);//每天赔付金额上限
		tPD_LDPlanFeeRelaSchema.setPayMoneyEveryDay(PayMoneyEveryDay);//每天赔偿固定金额
		tPD_LDPlanFeeRelaSchema.setObsPeriod(ObsPeriod);//等待期
		tPD_LDPlanFeeRelaSchema.setObsPeriodUn(ObsPeriodUn);//等待期单位
		tPD_LDPlanFeeRelaSchema.setStandFlag1(StandFlag1);//是否扣除自负比例
		tPD_LDPlanFeeRelaSchema.setStandFlag2(StandFlag2);//自负比例
		tPD_LDPlanFeeRelaSchema.setPreAuthFlag(PreAuthFlag);//免赔额
			
		tVData.add(tG);
		tVData.add(transferData);
		tVData.add(tPD_LDPlanFeeRelaSchema);
		String busiName = "PDLDPlanFeeRelaUI";

		BusinessDelegate tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, operator, busiName)) {
			VData rVData = tBusinessDelegate.getResult();
			Content = " 处理失败，原因是:"
					+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			VData rVData = tBusinessDelegate.getResult();
			//tCALCODE = (String)rVData.get(0);
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
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

