<%
//程序名称：ChangePlanSubWithdraw.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String polNo = request.getParameter("polNo");
  String prtNo = request.getParameter("prtNo");
  String riskCode = request.getParameter("riskCode");
  loggerDebug("ChangePlanSubWithdraw","polNo:" + polNo + " prtNo:" + prtNo + " riskCode:" + riskCode);
  
  GlobalInput tG = new GlobalInput();	
	tG = ( GlobalInput )session.getValue( "GI" );
  
  LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
  tLJTempFeeSchema.setRiskCode(riskCode);
  tLJTempFeeSchema.setOtherNo(prtNo);
  //借助TempFeeNo来传递保单号
  tLJTempFeeSchema.setTempFeeNo(polNo);

  // 准备传输数据 VData
  VData tVData = new VData();
	tVData.addElement(tLJTempFeeSchema);
	tVData.addElement(tG);

  // 数据传输
  ChangePlanSubWithdrawUI tChangePlanSubWithdrawUI   = new ChangePlanSubWithdrawUI();
	if (!tChangePlanSubWithdrawUI.submitData(tVData, "INSERT||MAIN")) {    
      Content = "查询失败，原因是: " + tChangePlanSubWithdrawUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}	else {
		tVData.clear();
		tVData = tChangePlanSubWithdrawUI.getResult();
		
		Content = " 查询成功! ";
    FlagStr = "Succ";
	} // end of if

  
loggerDebug("ChangePlanSubWithdraw",FlagStr);
loggerDebug("ChangePlanSubWithdraw",Content);
%> 

<script>
  try { top.close(); } catch(e) {}
</script>
