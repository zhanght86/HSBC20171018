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
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//接收信息，并作校验处理。
//输入参数
LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
LCAscriptionRuleFactorySet tLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();

LCAscriptionRuleFactoryUI tLCAscriptionRuleFactoryUI = new LCAscriptionRuleFactoryUI();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("AscriptionRuleSave","begin ...");

String tOperate=request.getParameter("mOperate");	//操作模式
String GrpContNo = request.getParameter("GrpContNo");	//集体合同号码
String AscriptionRuleCode = request.getParameter("AscriptionRuleCode");	//员工类别
String AscriptionRuleName = request.getParameter("AscriptionRuleName");	//分类说明

//取得险种管理费明细
int lineCount = 0;
String arrCount[] = request.getParameterValues("AscriptionRuleNewGridNo");
if (arrCount != null){
	String tRiskCode[] = request.getParameterValues("AscriptionRuleNewGrid1");	//险种编码
	String tAscriptType[] = request.getParameterValues("AscriptionRuleNewGrid2");	//属性       
	String tFactoryType[] = request.getParameterValues("AscriptionRuleNewGrid3");	//要素类型
	String tOtherNo[] = request.getParameterValues("AscriptionRuleNewGrid4");	//目标类型
	String tFactory[] = request.getParameterValues("AscriptionRuleNewGrid5");	//要素计算编码
	String tCalRemark[] = request.getParameterValues("AscriptionRuleNewGrid6");	//要素内容
	String tParams[] = request.getParameterValues("AscriptionRuleNewGrid7");	//要素值
	String tFactoryName[] = request.getParameterValues("AscriptionRuleNewGrid8");	//计算编码名称
	String tGrpPolNo[] = request.getParameterValues("AscriptionRuleNewGrid10");	//集体保单险种号码
	String tFactoryCode = "";
	String tFactorySubCode = "";
	
	lineCount = arrCount.length; //行数
	loggerDebug("AscriptionRuleSave","lineCount:"+lineCount);	
	for(int i=0;i<lineCount;i++){
		tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
	
		tLCAscriptionRuleFactorySchema.setGrpContNo(GrpContNo);
		tLCAscriptionRuleFactorySchema.setAscriptionRuleCode(AscriptionRuleCode);
		tLCAscriptionRuleFactorySchema.setAscriptionRuleName(AscriptionRuleName);
		tLCAscriptionRuleFactorySchema.setRiskCode(tRiskCode[i]);
		tLCAscriptionRuleFactorySchema.setAscriptType(tAscriptType[i]);
		tLCAscriptionRuleFactorySchema.setFactoryType(tFactoryType[i]);
		tLCAscriptionRuleFactorySchema.setOtherNo(tOtherNo[i]);
		tFactoryCode = tFactory[i].substring(0,6);
		tFactorySubCode = tFactory[i].substring(6);
		loggerDebug("AscriptionRuleSave",tFactory[i]+"****"+tFactoryCode+"****"+tFactorySubCode);
		tLCAscriptionRuleFactorySchema.setFactoryCode(tFactoryCode);
		tLCAscriptionRuleFactorySchema.setFactorySubCode(tFactorySubCode);
		tLCAscriptionRuleFactorySchema.setCalRemark(tCalRemark[i]);
		tLCAscriptionRuleFactorySchema.setParams(tParams[i]);
		tLCAscriptionRuleFactorySchema.setFactoryName(tFactoryName[i]);
		tLCAscriptionRuleFactorySchema.setGrpPolNo(tGrpPolNo[i]);
	
		tLCAscriptionRuleFactorySet.add(tLCAscriptionRuleFactorySchema);
		loggerDebug("AscriptionRuleSave","记录"+i+"放入Set！");
	}
}
loggerDebug("AscriptionRuleSave","end ...");

// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCAscriptionRuleFactorySet);
tVData.addElement(GrpContNo);

try{
	loggerDebug("AscriptionRuleSave","this will save the data!!!");
	tLCAscriptionRuleFactoryUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCAscriptionRuleFactoryUI.mErrors;
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
