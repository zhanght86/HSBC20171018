<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//接收信息，并作校验处理。
//输入参数
LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();

LCPayRuleFactoryUI tLCPayRuleFactoryUI = new LCPayRuleFactoryUI();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("PayRuleSave","begin ...");

String tOperate=request.getParameter("mOperate");	//操作模式
String GrpContNo = request.getParameter("GrpContNo");	//集体合同号码
String PayRuleCode = request.getParameter("PayRuleCode");	//员工类别
String PayRuleName = request.getParameter("PayRuleName");	//分类说明

//取得险种管理费明细
int lineCount = 0;
String arrCount[] = request.getParameterValues("PayRuleNewGridNo");
if (arrCount != null){
	String tRiskCode[] = request.getParameterValues("PayRuleNewGrid1");	//险种编码
	String tFactoryType[] = request.getParameterValues("PayRuleNewGrid2");	//要素类型
	String tOtherNo[] = request.getParameterValues("PayRuleNewGrid3");	//目标类型
	String tFactory[] = request.getParameterValues("PayRuleNewGrid4");	//要素计算编码
	String tCalRemark[] = request.getParameterValues("PayRuleNewGrid5");	//要素内容
	String tParams[] = request.getParameterValues("PayRuleNewGrid6");	//要素值
	String tFactoryName[] = request.getParameterValues("PayRuleNewGrid7");	//计算编码名称
	String tGrpPolNo[] = request.getParameterValues("PayRuleNewGrid9");	//集体保单险种号码
	String tFactoryCode = "";
	String tFactorySubCode = "";
	
	lineCount = arrCount.length; //行数
	
	for(int i=0;i<lineCount;i++){
		tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
	
		tLCPayRuleFactorySchema.setGrpContNo(GrpContNo);
		tLCPayRuleFactorySchema.setPayRuleCode(PayRuleCode);
		tLCPayRuleFactorySchema.setPayRuleName(PayRuleName);
		tLCPayRuleFactorySchema.setRiskCode(tRiskCode[i]);
		tLCPayRuleFactorySchema.setFactoryType(tFactoryType[i]);
		tLCPayRuleFactorySchema.setOtherNo(tOtherNo[i]);
		tFactoryCode = tFactory[i].substring(0,6);
		tFactorySubCode = tFactory[i].substring(6);
		loggerDebug("PayRuleSave",tFactory[i]+"****"+tFactoryCode+"****"+tFactorySubCode);
		tLCPayRuleFactorySchema.setFactoryCode(tFactoryCode);
		tLCPayRuleFactorySchema.setFactorySubCode(tFactorySubCode);
		tLCPayRuleFactorySchema.setCalRemark(tCalRemark[i]);
		tLCPayRuleFactorySchema.setParams(tParams[i]);
		tLCPayRuleFactorySchema.setFactoryName(tFactoryName[i]);
		tLCPayRuleFactorySchema.setGrpPolNo(tGrpPolNo[i]);
	
		tLCPayRuleFactorySet.add(tLCPayRuleFactorySchema);
		loggerDebug("PayRuleSave","记录"+i+"放入Set！");
	}
}
loggerDebug("PayRuleSave","end ...");

// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCPayRuleFactorySet);
tVData.addElement(GrpContNo);

try{
	loggerDebug("PayRuleSave","this will save the data!!!");
	tLCPayRuleFactoryUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCPayRuleFactoryUI.mErrors;
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
parent.fraInterface.fm.all('PayRuleCode').value="";
parent.fraInterface.fm.all('PayRuleName').value="";
</script>
</html>
