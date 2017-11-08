<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CtrlClaimSave.jsp
//程序功能：
//创建日期：2005-11-17 15:12:33
//创建人 ：Sujie
//更新记录： 更新人  更新日期   更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LLDutyCtrlSchema"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.vschema.LLDutyCtrlSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//接收信息，并作校验处理。
//输入参数
LLDutyCtrlSchema tLLDutyCtrlSchema = new LLDutyCtrlSchema();

LLCtrlClaimBL tLLCtrlClaimBL = new LLCtrlClaimBL();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

	//全局变量
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	
	loggerDebug("CtrlClaimSave","begin ...");
	
	String tOperate=request.getParameter("mOperate");	//操作模式
	//String tlevelflag=request.getParameter("levelflag");
	String tGrpContNo = request.getParameter("GrpContNo");	//团体保单号
	String tContType = "2";	//保单类型为集体保单
	String tOccupationType = request.getParameter("OccupationType");	//职业类别
	String tContPlanCode = request.getParameter("ContPlanCode");	//保险计划
	String tCtrlProp = request.getParameter("CtrlProp"); //控制属性
	String tRiskCode = request.getParameter("RiskCode");	//险种
	String tDutyCode = request.getParameter("DutyCode");	//责任编码
	String tGetDutyCode = request.getParameter("GetDutyCode");	//给付责任编码
	
	//CtrlBatchNo
	String tCtrlBatchNo = request.getParameter("CtrlBatchNo"); //控制批次号(只读)

	//将为空的控制层次设置为*	
	if (tOccupationType==null||tOccupationType.equals(""))
		tOccupationType="*";
	if (tCtrlProp==null||tCtrlProp.equals(""))
		tCtrlProp="*";
	if (tContPlanCode==null||tContPlanCode.equals(""))
		tContPlanCode="*";
	if (tRiskCode==null||tRiskCode.equals(""))
		tRiskCode="*";
	if (tDutyCode==null||tDutyCode.equals(""))
		tDutyCode="*";
	if (tGetDutyCode==null||tGetDutyCode.equals(""))
		tGetDutyCode="*";
	
	//获取控制属性
	String tObserveDate = request.getParameter("ObserveDate");	// 观察期
	String tExempt = request.getParameter("Exempt");	//免赔额
	String tExemptDate = request.getParameter("ExemptDate");	//免赔天数
	String tTotalLimit = request.getParameter("TotalLimit");	//总赔付限额
	
	String tClaimRate = request.getParameter("ClaimRate"); //赔付比例
	
	String tStartPay = request.getParameter("StartPay");	//起付线
	String tEndPay = request.getParameter("EndPay");	//封顶线
	
	String tRemark = request.getParameter("Remark");	//备注
	
	tLLDutyCtrlSchema = new LLDutyCtrlSchema();
	tLLDutyCtrlSchema.setGrpContNo(tGrpContNo);
	tLLDutyCtrlSchema.setCtrlBatchNo(tCtrlBatchNo);
	tLLDutyCtrlSchema.setContType(tContType);
	tLLDutyCtrlSchema.setOccupationType(tOccupationType);
	tLLDutyCtrlSchema.setCtrlProp(tCtrlProp);
	tLLDutyCtrlSchema.setContPlanCode(tContPlanCode);
	tLLDutyCtrlSchema.setRiskCode(tRiskCode);
	tLLDutyCtrlSchema.setDutyCode(tDutyCode);
	tLLDutyCtrlSchema.setGetDutyCode(tGetDutyCode);
	
	//备注
	tLLDutyCtrlSchema.setRemark(tRemark);
	
	//观察期
	if (tObserveDate==null || tObserveDate.equals("")) 
	{
	   tObserveDate="-1";
	}
	
	tLLDutyCtrlSchema.setObserveDate(tObserveDate);
	
	//免赔额
	if (tExempt==null || tExempt.equals("")) 
	{
	   tExempt="-1";
	}
	tLLDutyCtrlSchema.setExempt(tExempt);
	
	//免赔天数
	if (tExemptDate==null || tExemptDate.equals("")) 
	{
	   tExemptDate="-1";
	}
	tLLDutyCtrlSchema.setExemptDate(tExemptDate);
	
	//总赔付限额
	if (tTotalLimit==null || tTotalLimit.equals("")) 
	{
	   tTotalLimit="-1";
	}
	tLLDutyCtrlSchema.setTotalLimit(tTotalLimit);
	//赔付比例
	if (tClaimRate==null || tClaimRate.equals("")) 
	{
	   tClaimRate="-1";
	}
	tLLDutyCtrlSchema.setClaimRate(tClaimRate);
	
	//起付线
	if (tStartPay==null || tStartPay.equals("")) 
	{
	   tStartPay="-1";
	}
	tLLDutyCtrlSchema.setStartPay(tStartPay);
	
	//封顶线
	if (tEndPay==null || tEndPay.equals("")) 
	{
		tEndPay="-1";
	}
	tLLDutyCtrlSchema.setEndPay(tEndPay);
	
	
	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";
	
	tVData.add(tG);
	tVData.addElement(tLLDutyCtrlSchema);
	
	try{
		loggerDebug("CtrlClaimSave","this will save the data!!!");
		tLLCtrlClaimBL.submitData(tVData,tOperate,"");
	}
	catch(Exception ex){
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	
	if (!FlagStr.equals("Fail")){
		tError = tLLCtrlClaimBL.mErrors;
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
<<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>  
