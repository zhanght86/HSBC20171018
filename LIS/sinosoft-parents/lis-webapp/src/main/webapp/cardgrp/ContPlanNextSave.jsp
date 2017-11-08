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
LCContPlanFactorySchema tLCContPlanFactorySchema = new LCContPlanFactorySchema();
LCContPlanFactorySet tLCContPlanFactorySet = new LCContPlanFactorySet();

LCContPlanFactoryUI tLCContPlanFactoryUI = new LCContPlanFactoryUI();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("ContPlanNextSave","begin ...");

String tOperate=request.getParameter("mOperate");	//操作模式
String GrpContNo = request.getParameter("GrpContNo");	//集体合同号码
String ProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//集体投保单号码

//取得险种管理费明细
int lineCount = 0;
String arrCount[] = request.getParameterValues("ImpartGridNo");
if (arrCount != null){
	String tContPlanCode[] = request.getParameterValues("ImpartGrid1");	//计划编码
	String tRiskCode[] = request.getParameterValues("ImpartGrid2");	//险种编码
	String tFactoryType[] = request.getParameterValues("ImpartGrid3");	//要素类型
	String tOtherNo[] = request.getParameterValues("ImpartGrid4");	//目标类型
	String tFactory[] = request.getParameterValues("ImpartGrid5");	//要素计算编码
	String tCalRemark[] = request.getParameterValues("ImpartGrid6");	//要素内容
	String tParams[] = request.getParameterValues("ImpartGrid7");	//要素值
	String tContPlanName[] = request.getParameterValues("ImpartGrid10");	//计划名称
	String tRiskVersion[] = request.getParameterValues("ImpartGrid11");	//险种版本
	String tFactoryName[] = request.getParameterValues("ImpartGrid12");	//计算编码名称
	String tMainRiskCode[] = request.getParameterValues("ImpartGrid13");	//主险编码
	String tMainRiskVersion[] = request.getParameterValues("ImpartGrid14");	//主险版本
	String tFactoryCode = "";
	String tFactorySubCode = "";
	
	lineCount = arrCount.length; //行数
	
	for(int i=0;i<lineCount;i++){
		tLCContPlanFactorySchema = new LCContPlanFactorySchema();
	
		tLCContPlanFactorySchema.setGrpContNo(GrpContNo);
		tLCContPlanFactorySchema.setProposalGrpContNo(ProposalGrpContNo);
		tLCContPlanFactorySchema.setContPlanCode(tContPlanCode[i]);
		tLCContPlanFactorySchema.setRiskCode(tRiskCode[i]);
		tLCContPlanFactorySchema.setFactoryType(tFactoryType[i]);
		tLCContPlanFactorySchema.setOtherNo(tOtherNo[i]);
		tFactoryCode = tFactory[i].substring(0,6);
		tFactorySubCode = tFactory[i].substring(6);
		loggerDebug("ContPlanNextSave",tFactory[i]+"****"+tFactoryCode+"****"+tFactorySubCode);
		tLCContPlanFactorySchema.setFactoryCode(tFactoryCode);
		tLCContPlanFactorySchema.setFactorySubCode(tFactorySubCode);
		tLCContPlanFactorySchema.setCalRemark(tCalRemark[i]);
		tLCContPlanFactorySchema.setParams(tParams[i]);
		tLCContPlanFactorySchema.setContPlanName(tContPlanName[i]);
		tLCContPlanFactorySchema.setRiskVersion(tRiskVersion[i]);
		tLCContPlanFactorySchema.setFactoryName(tFactoryName[i]);
		tLCContPlanFactorySchema.setMainRiskCode(tMainRiskCode[i]);
		tLCContPlanFactorySchema.setMainRiskVersion(tMainRiskVersion[i]);
		tLCContPlanFactorySchema.setPlanType("0");	//计划类型，默认为固定计划
	
		tLCContPlanFactorySet.add(tLCContPlanFactorySchema);
		loggerDebug("ContPlanNextSave","记录"+i+"放入Set！");
	}
}
loggerDebug("ContPlanNextSave","end ...");

// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCContPlanFactorySet);
tVData.addElement(ProposalGrpContNo);
tVData.addElement(GrpContNo);

try{
	loggerDebug("ContPlanNextSave","this will save the data!!!");
	tLCContPlanFactoryUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCContPlanFactoryUI.mErrors;
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
