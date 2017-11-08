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
<%@page import="com.sinosoft.lis.config.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//接收信息，并作校验处理。
//输入参数
LDPlanDutyParamSchema tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
LDPlanDutyParamSet tLDPlanDutyParamSet = new LDPlanDutyParamSet();

LDPlanUI tLDPlanUI = new LDPlanUI();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("LDPlanConfSave","begin ...");

String tOperate=request.getParameter("mOperate");	//操作模式
String PlanCode = request.getParameter("ContPlanCode");	//保障级别
String PlanName = request.getParameter("ContPlanName");	//保障说明
String PlanSql = request.getParameter("PlanSql");	//分类说明
String Peoples3 = request.getParameter("Peoples3");	//可保人数
String PlanType = "0";	//计划类型
String Remark =StrTool.unicodeToGBK(request.getParameter("Remark")); //特别约定
String PlanKind1=request.getParameter("PlanKind1"); //套餐层级1
String PlanKind2=request.getParameter("PlanKind2"); //套餐层级2
String PlanKind3=request.getParameter("PlanKind3"); //套餐层级3
String ManageCom=request.getParameter("ManageCom"); //管理机构

//保险计划要素信息
int lineCount = 0;
String arrCount[] = request.getParameterValues("ContPlanGridNo");
if (arrCount != null){
	String tChk[] = request.getParameterValues("InpContPlanGridChk");
	String tRiskCode[] = request.getParameterValues("ContPlanGrid2");	//险种编码
	String tDutyCode[] = request.getParameterValues("ContPlanGrid3");	//责任编码
	String tCalFactor[] = request.getParameterValues("ContPlanGrid5");	//计算要素码
	String tCalFactorValue[] = request.getParameterValues("ContPlanGrid8");	//计算要素值
	String tRemark[] = request.getParameterValues("ContPlanGrid9");	//特别说明
	String tRiskVersion[] = request.getParameterValues("ContPlanGrid10");	//险种版本
	String tGrpPolNo[] = request.getParameterValues("ContPlanGrid11");	//集体保单险种号码
	String tMainRiskCode[] = request.getParameterValues("ContPlanGrid12");	//主险编码
	String tCalFactorType[] = request.getParameterValues("ContPlanGrid13");	//类型
	
	lineCount = arrCount.length; //行数
	
	for(int i=0;i<lineCount;i++){
		tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
		tLDPlanDutyParamSchema.setContPlanCode(PlanCode);
		tLDPlanDutyParamSchema.setContPlanName(PlanName);
		tLDPlanDutyParamSchema.setRiskCode(tRiskCode[i]);
		loggerDebug("LDPlanConfSave","**********"+tRiskCode[i]);
		tLDPlanDutyParamSchema.setDutyCode(tDutyCode[i]);
		tLDPlanDutyParamSchema.setCalFactor(tCalFactor[i]);
		tLDPlanDutyParamSchema.setCalFactorValue(tCalFactorValue[i]);
		tLDPlanDutyParamSchema.setRemark(tRemark[i]);
		tLDPlanDutyParamSchema.setRiskVersion(tRiskVersion[i]);
		tLDPlanDutyParamSchema.setMainRiskCode(tMainRiskCode[i]);
		tLDPlanDutyParamSchema.setMainRiskVersion(tRiskVersion[i]);
		tLDPlanDutyParamSchema.setCalFactorType(tCalFactorType[i]);
		tLDPlanDutyParamSchema.setPlanType("0");
		tLDPlanDutyParamSet.add(tLDPlanDutyParamSchema);
		loggerDebug("LDPlanConfSave","记录"+i+"放入Set！tGrpPolNo[i] is "+tCalFactor[i]);
	}
}
loggerDebug("LDPlanConfSave","end ...");

// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLDPlanDutyParamSet);
tVData.addElement(PlanCode);
tVData.addElement(PlanType);
tVData.addElement(PlanSql);
tVData.addElement(Peoples3);
tVData.addElement(Remark);
tVData.addElement(PlanKind1);
tVData.addElement(PlanKind2);
tVData.addElement(PlanKind3);
tVData.addElement(ManageCom);

try{
	loggerDebug("LDPlanConfSave","this will save the data!!!");
	tLDPlanUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLDPlanUI.mErrors;
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
