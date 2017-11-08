<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：PDDiscountDefiSave.jsp
//程序功能：
//创建日期：2011-03-03
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
  System.out.println("\n\n---PDDiscountDefiSave Start---");
//  System.out.println("OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getAttribute("GI");
  
  PD_LMDiscountSchema tPD_LMDiscountSchema = new PD_LMDiscountSchema();
  
  String tDiscountType = request.getParameter("DiscountType");
  String tDiscountCode = request.getParameter("DiscountCode");
  
  //String tCampaignCode = request.getParameter("CampaignCode");
  String tAddFeeDiscFlag = request.getParameter("AddFeeDiscFlag");
  String tRiskCode = request.getParameter("RiskCode");
  String tDutyCode = request.getParameter("DutyCode");
  String tStartDate = request.getParameter("StartDate");
  String tEndDate = request.getParameter("EndDate");
  String tCalCode = request.getParameter("CalCode");
 
  tPD_LMDiscountSchema.setDiscountType(tDiscountType);
  tPD_LMDiscountSchema.setDiscountCode(tDiscountCode);
  //tPD_LMDiscountSchema.setCampaignCode(tCampaignCode);
  tPD_LMDiscountSchema.setAddFeeDiscFlag(tAddFeeDiscFlag);
  tPD_LMDiscountSchema.setRiskCode(tRiskCode);
  tPD_LMDiscountSchema.setDutyCode(tDutyCode);
  tPD_LMDiscountSchema.setStartDate(tStartDate);
  tPD_LMDiscountSchema.setEndDate(tEndDate);
  tPD_LMDiscountSchema.setCalCode(tCalCode);

  VData inVData = new VData();
  inVData.add(tPD_LMDiscountSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  String mOperator = request.getParameter("mOperator");
  
  CErrors tError = null;
  String busiName="PDDiscountDefiUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if (!tBusinessDelegate.submitData(inVData, mOperator, busiName)) {
    tError = tBusinessDelegate.getCErrors();
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"处理失败，原因是:"+"" + tError.getFirstError();
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  tDiscountCode = (String)rVData.get(0);
    Content = " "+"处理成功!"+" ";
  	FlagStr = "Succ";
  }

	System.out.println(Content + "\n" + FlagStr + "\n---PDDiscountDefiSave End---\n\n"+tDiscountCode);
%>
<html>
<script type="text/javascript">
	parent.fraInterface.setDiscountCode("<%=tDiscountCode%>");
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>
