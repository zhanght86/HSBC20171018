<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	//接收资讯，并作校验处理。
	//输入参数
	
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getAttribute("GI");	
	//输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	String tOperate = request.getParameter("OperateType");
	String ArithSubType = request.getParameter("ArithSubType");
		
	String tAccumulateDefNO = request.getParameter("AccumulateDefNO");//累计风险编码
	String tArithmeticDefID = request.getParameter("ArithmeticDefID");//演算法编码
	String tArithmeticDefName = request.getParameter("ArithmeticDefName");//演算法编码
	String PreceptType = request.getParameter("PreceptType");//演算法编码
	
	RICalDefSchema tRICalDefSchema = new RICalDefSchema();
	RIAccumulateDefSchema tRIAccumulateDefSchema = new RIAccumulateDefSchema();
	
	tRICalDefSchema.setArithmeticDefID(tArithmeticDefID);
	tRICalDefSchema.setArithmeticDefName(tArithmeticDefName);
	tRICalDefSchema.setArithSubType(ArithSubType);
	tRICalDefSchema.setAccumulateDefNO(tAccumulateDefNO);
	tRICalDefSchema.setPreceptType(PreceptType);
	//tRICalDefSchema.setStandbyString1(tArithType);

	if("L".equals(ArithSubType))
	{
		//资料提取演算法
		tRIAccumulateDefSchema.setArithmeticID(tArithmeticDefID);
		tRIAccumulateDefSchema.setAccumulateDefNO(tAccumulateDefNO);	
		//tRICalDefSchema.setArithSubType("01");
	}	
	else
	{
		tRICalDefSchema.setStandbyString2(tAccumulateDefNO);
	}
	
	// 准备传输资料 VData
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ArithSubType",ArithSubType);
	
	tVData.add(tRICalDefSchema);
	tVData.add(tRIAccumulateDefSchema);
	tVData.add(tTransferData);	
	tVData.add(tG);
	
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, tOperate,
			"RIItemCalUI")) {
		if (uiBusinessDelegate.getCErrors() != null
				&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = ""+"保存失败，原因是："+""
					+ uiBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = ""+"保存失败"+"";
			FlagStr = "Fail";
		}
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if ("".equals(FlagStr)) {
		tError = uiBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = ""+"保存成功"+""; 
			FlagStr = "Succ";
		} else {
			Content = ""+"保存失败，原因是："+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
  //添加各种预处理
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>

