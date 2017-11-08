<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//程序名称：PDRiskDutyFactorSave.jsp
	//程序功能：责任录入要素定义
	//创建日期：2009-3-13
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//接收信息，并作校验处理。
	//输入参数

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

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	operator = request.getParameter("flag");
	submitFlag = request.getParameter("submitFlag");

	String tRiskCode = request.getParameter("RiskCode1");
	//目前是错的，但是为演示，将错就错。
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
		// 准备传输数据 VData
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(tPD_LMRiskDutyFactorSet);
		pDRiskDutyFactorBL.submitData(tVData, operator);

		new RiskState().setState(request.getParameter("RiskCode"),
				""+"契约业务控制"+"->"+"保障计划约定要素"+"", "1");
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = ""+"保存失败，原因是:"+"" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = pDRiskDutyFactorBL.mErrors;
		if (!tError.needDealError()) {
			Content = " "+"保存成功!"+" ";
			FlagStr = "Success";
		} else {
			Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}

	//添加各种预处理
%>
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

