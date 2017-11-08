<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpFeeSave.jsp
//程序功能：
//创建日期：2002-08-16 15:12:33
//创建人 ：CrtHtml程序创建
//更新记录： 更新人  更新日期   更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanDutyParamSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanSchema"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanDutyParamSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//接收信息，并作校验处理。
//输入参数
LCContPlanDutyParamSchema tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
LCContPlanDutyParamSet tLCContPlanDutyParamSet = new LCContPlanDutyParamSet();

LCContPlanUI tLCContPlanUI = new LCContPlanUI();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("ContPlanSave","begin ...");

String tOperate=request.getParameter("mOperate");	//操作模式
String tGrpContNo = request.getParameter("GrpContNo");	//集体合同号码
String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//集体投保单号码
String tContPlanCode = request.getParameter("ContPlanCode");	//保障级别
String tContPlanName = request.getParameter("ContPlanName");	//保障说明
String tPlanSql = request.getParameter("PlanSql");	//分类说明
String tPeoples3 = request.getParameter("Peoples3");	//可保人数
String tInsuMult = request.getParameter("InsuMult"); //投保份数
//String tPlanType = "0";	//计划类型
String tPlanType = request.getParameter("RiskPlan1");	//计划类型RiskPlan1
String remark=request.getParameter("RiskPlan");
//String tCValiDate=request.getParameter("CValiDate");
//保险计划基础信息
tLCContPlanSchema.setGrpContNo(tGrpContNo);
tLCContPlanSchema.setProposalGrpContNo(tProposalGrpContNo);
tLCContPlanSchema.setContPlanCode(tContPlanCode);
tLCContPlanSchema.setContPlanName(tContPlanName);
tLCContPlanSchema.setPlanSql(tPlanSql);
tLCContPlanSchema.setPeoples3(tPeoples3);
tLCContPlanSchema.setPlanType(tPlanType);
tLCContPlanSchema.setRemark(remark);
tLCContPlanSchema.setRemark2(tInsuMult);
//保险计划要素信息
int lineCount = 0;
String[] arrCount = request.getParameterValues("ContPlanGridNo");
if (arrCount != null){
	String[] tChk = request.getParameterValues("InpContPlanGridChk");
	String[] tRiskCode = request.getParameterValues("ContPlanGrid2");	//险种编码
	String[] tDutyCode = request.getParameterValues("ContPlanGrid3");	//责任编码
	String[] tCalFactor = request.getParameterValues("ContPlanGrid5");	//计算要素码
	String[] tCalFactorValue = request.getParameterValues("ContPlanGrid8");	//计算要素值
	String[] tRemark = request.getParameterValues("ContPlanGrid9");	//特别说明
	String[] tRiskVersion = request.getParameterValues("ContPlanGrid10");	//险种版本
	String[] tGrpPolNo = request.getParameterValues("ContPlanGrid11");	//集体保单险种号码
	String[] tMainRiskCode = request.getParameterValues("ContPlanGrid12");	//主险编码
	String[] tCalFactorType = request.getParameterValues("ContPlanGrid13");	//类型
	
	lineCount = arrCount.length; //行数
	
	//
	for(int i=0;i<lineCount;i++){
		tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
		tLCContPlanDutyParamSchema.setGrpContNo(tGrpContNo);
		tLCContPlanDutyParamSchema.setProposalGrpContNo(tProposalGrpContNo);
		tLCContPlanDutyParamSchema.setContPlanCode(tContPlanCode);
		tLCContPlanDutyParamSchema.setContPlanName(tContPlanName);
		tLCContPlanDutyParamSchema.setRiskCode(tRiskCode[i]);
//		loggerDebug("ContPlanSave","**********"+tRiskCode[i]);
		tLCContPlanDutyParamSchema.setDutyCode(tDutyCode[i]);
		tLCContPlanDutyParamSchema.setCalFactor(tCalFactor[i]);
		tLCContPlanDutyParamSchema.setCalFactorValue(tCalFactorValue[i]);
		tLCContPlanDutyParamSchema.setRemark(tRemark[i]);
		tLCContPlanDutyParamSchema.setRiskVersion(tRiskVersion[i]);
		tLCContPlanDutyParamSchema.setGrpPolNo(tGrpPolNo[i]);
		tLCContPlanDutyParamSchema.setMainRiskCode(tMainRiskCode[i]);
		tLCContPlanDutyParamSchema.setMainRiskVersion(tRiskVersion[i]);
		tLCContPlanDutyParamSchema.setCalFactorType(tCalFactorType[i]);
		tLCContPlanDutyParamSchema.setPlanType(tPlanType);
		tLCContPlanDutyParamSet.add(tLCContPlanDutyParamSchema);
//		loggerDebug("ContPlanSave","记录"+i+"放入Set！tGrpPolNo[i] is "+tCalFactor[i]);
	}
}

// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCContPlanDutyParamSet);
tVData.addElement(tLCContPlanSchema);
//tVData.addElement(ProposalGrpContNo);
//tVData.addElement(GrpContNo);
//tVData.addElement(ContPlanCode);
//tVData.addElement(PlanType);
//tVData.addElement(PlanSql);
//tVData.addElement(Peoples3);

try{
	loggerDebug("ContPlanSave","this will save the data!!!");
	tLCContPlanUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCContPlanUI.mErrors;
	if (!tError.needDealError()){
		Content = " 保存成功! ";
		FlagStr = "Succ";
	}
	else{
		Content = " 保存失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	}
}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
