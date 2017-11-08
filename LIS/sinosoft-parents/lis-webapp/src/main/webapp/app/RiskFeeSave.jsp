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
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//接收信息，并作校验处理。
//输入参数
LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
LCGrpFeeParamSchema tLCGrpFeeParamSchema = new LCGrpFeeParamSchema();
LCGrpFeeParamSet tLCGrpFeeParamSet = new LCGrpFeeParamSet();
//GrpFeeUI tGrpFeeUI = new GrpFeeUI();
   String busiName="tbGrpFeeUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//输出参数
CErrors tError = null;
String tOperate=request.getParameter("mOperate");
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("RiskFeeSave","begin ...");

String GrpPolNo = request.getParameter("GrpPolNo");	//集体保单险种号码

//管理费主表信息
int lineCount = 0;
String arrCount[] = request.getParameterValues("RiskFeeGridNo");
if (arrCount != null){
	String tRadio[] = request.getParameterValues("InpRiskFeeGridSel");	//Radio选项
	String tInsuAccNo[] = request.getParameterValues("RiskFeeGrid1");	//保险帐户号码
	String tPayPlanCode[] = request.getParameterValues("RiskFeeGrid3");	//交费项编码
	String tFeeCode[] = request.getParameterValues("RiskFeeGrid5");	//管理费编码
	String tPayInsuAccName[] = request.getParameterValues("RiskFeeGrid7");	//关系说明
	String tFeeCalMode[] = request.getParameterValues("RiskFeeGrid8");	//管理费计算方式
	String tFeeCalCode[] = request.getParameterValues("RiskFeeGrid9");	//管理费计算公式
	String tFeeValue[] = request.getParameterValues("RiskFeeGrid10");	//固定值
	String tCompareValue[] = request.getParameterValues("RiskFeeGrid11");	//比较值
	String tFeeCalModeType[] = request.getParameterValues("RiskFeeGrid12");	//计算类型
	String tFeePeriod[] = request.getParameterValues("RiskFeeGrid13");	//扣除管理费周期
	String tMaxTime[] = request.getParameterValues("RiskFeeGrid14");	//扣除管理费最大次数
	String tDefaultFlag[] = request.getParameterValues("RiskFeeGrid15");	//缺省标记
	//modify by zhangxing 暂时注掉tInerSerialNo
	//String tInerSerialNo[] = request.getParameterValues("RiskFeeGrid17");    //管理费的顺序号

	lineCount = arrCount.length; //行数

	//选择单选框被选中的数据提交
	for(int i=0;i<lineCount;i++){
		if(tRadio[i].equals("1")){
			tLCGrpFeeSchema = new LCGrpFeeSchema();
			tLCGrpFeeSchema.setGrpPolNo(GrpPolNo);
			tLCGrpFeeSchema.setInsuAccNo(tInsuAccNo[i]);
			tLCGrpFeeSchema.setPayPlanCode(tPayPlanCode[i]);
			tLCGrpFeeSchema.setFeeCode(tFeeCode[i]);
			tLCGrpFeeSchema.setPayInsuAccName(tPayInsuAccName[i]);
			tLCGrpFeeSchema.setFeeCalMode(tFeeCalMode[i]);
			tLCGrpFeeSchema.setFeeCalCode(tFeeCalCode[i]);
			tLCGrpFeeSchema.setFeeValue(tFeeValue[i]);
			tLCGrpFeeSchema.setCompareValue(tCompareValue[i]);
			tLCGrpFeeSchema.setFeeCalModeType(tFeeCalModeType[i]);
			tLCGrpFeeSchema.setFeePeriod(tFeePeriod[i]);
			tLCGrpFeeSchema.setMaxTime(tMaxTime[i]);
			tLCGrpFeeSchema.setDefaultFlag(tDefaultFlag[i]);
			//modify by zhangxing 暂时注掉InerSerialNo
			//tLCGrpFeeSchema.setInerSerialNo(tInerSerialNo[i]);
			tLCGrpFeeSet.add(tLCGrpFeeSchema);
			loggerDebug("RiskFeeSave","当前行被选中"+tFeeCode[i]);
		}
	}
}

//管理费子表信息
lineCount = 0;
String arrCount2[] = request.getParameterValues("RiskFeeParamGridNo");
if (arrCount2 != null){
	String tFeeMin[] = request.getParameterValues("RiskFeeParamGrid1");	//费用下限
	String tFeeMax[] = request.getParameterValues("RiskFeeParamGrid2");	//费用上限
	String tFeeRate[] = request.getParameterValues("RiskFeeParamGrid3");	//管理费比例
	String tFeeID[] = request.getParameterValues("RiskFeeParamGrid4");	//FeeID

	lineCount = arrCount2.length; //行数
	loggerDebug("RiskFeeSave",lineCount+"*******************************");

	for(int i=0;i<lineCount;i++){
		tLCGrpFeeParamSchema = new LCGrpFeeParamSchema();
		tLCGrpFeeParamSchema.setGrpPolNo(GrpPolNo);
		tLCGrpFeeParamSchema.setFeeMin(tFeeMin[i]);
		tLCGrpFeeParamSchema.setFeeMax(tFeeMax[i]);
		tLCGrpFeeParamSchema.setFeeRate(tFeeRate[i]);
		tLCGrpFeeParamSchema.setFeeID(tFeeID[i]);
		tLCGrpFeeParamSet.add(tLCGrpFeeParamSchema);
	}
}
loggerDebug("RiskFeeSave","end ...");

// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCGrpFeeSet);
tVData.addElement(tLCGrpFeeParamSet);

try{
	loggerDebug("RiskFeeSave","this will save the data!!!");
	tBusinessDelegate.submitData(tVData,tOperate,busiName);
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tBusinessDelegate.getCErrors();
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
