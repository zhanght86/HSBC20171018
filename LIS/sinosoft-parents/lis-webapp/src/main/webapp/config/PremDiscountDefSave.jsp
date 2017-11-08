<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：UWCustomerQualitySave.jsp
//程序功能：
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：    更新人      更新日期      更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  loggerDebug("PremDiscountDefSave","\n\n---PremDiscountDefSave Start---");
//  loggerDebug("PremDiscountDefSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LMDiscountSchema tLMDiscountSchema = new LMDiscountSchema();
  
  String tDiscountType = request.getParameter("DiscountType");
  String tDiscountCode = request.getParameter("DiscountCode");
  
  String tCampaignCode = request.getParameter("CampaignCode");
  String tAddFeeDiscFlag = request.getParameter("AddFeeDiscFlag");
  String tRiskCode = request.getParameter("RiskCode");
  String tDutyCode = request.getParameter("DutyCode");
  String tStartDate = request.getParameter("StartDate");
  String tEndDate = request.getParameter("EndDate");
  String tCalCode = request.getParameter("CalCode");
 
  tLMDiscountSchema.setDiscountType(tDiscountType);
  tLMDiscountSchema.setDiscountCode(tDiscountCode);
  tLMDiscountSchema.setCampaignCode(tCampaignCode);
  tLMDiscountSchema.setAddFeeDiscFlag(tAddFeeDiscFlag);
  tLMDiscountSchema.setRiskCode(tRiskCode);
  tLMDiscountSchema.setDutyCode(tDutyCode);
  tLMDiscountSchema.setStartDate(tStartDate);
  tLMDiscountSchema.setEndDate(tEndDate);
  tLMDiscountSchema.setCalCode(tCalCode);

  VData inVData = new VData();
  inVData.add(tLMDiscountSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  //UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();
  String busiName="PremDiscountDefUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(inVData, "",busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(1);
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  tDiscountCode = (String)rVData.get(0);
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("PremDiscountDefSave",Content + "\n" + FlagStr + "\n---PremDiscountDefSave End---\n\n"+tDiscountCode);
%>
<html>
<script language="javascript">
	parent.fraInterface.setDiscountCode("<%=tDiscountCode%>");
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

