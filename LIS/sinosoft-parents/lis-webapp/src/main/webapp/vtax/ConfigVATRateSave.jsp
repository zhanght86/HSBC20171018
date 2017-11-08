<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ConfigVATRateSave.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>

  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%

  CErrors tError = null;
  String Content = "";
  String FlagStr = "";
  /* String tOperate=request.getParameter("hideOperate"); */
  String tOperate = request.getParameter("fmtransact");
  String busiName=   "ConfigVATRateUI";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
      String configID = request.getParameter("ConfigID");//主表ID
      String id = request.getParameter("TaxID");//本表id
	  String RiskGrpCode = request.getParameter("RiskGrpCode");//险种组code
	  String FeeTypeCode  = request.getParameter("TFeeTypeCode");  //编码只为了显示用
	  String ManageCom  = request.getParameter("ManageCom");  //机构
	  String StartDate  = request.getParameter("StartDate");
	  String EndDate  = request.getParameter("EndDate");
	  String TaxRate  = request.getParameter("TaxRate"); //税率
	  
	  LDVATTaxConfigSet tLDVATTaxConfigSet = new LDVATTaxConfigSet();
	  LDVATTaxConfigSchema tLDVATTaxConfigSchema = new LDVATTaxConfigSchema();
	  tLDVATTaxConfigSchema.setRiskGrpCode(RiskGrpCode);
	  tLDVATTaxConfigSchema.setManageCom(ManageCom);
	  tLDVATTaxConfigSchema.setStartDate(StartDate);
	  tLDVATTaxConfigSchema.setEndDate(EndDate);
	  tLDVATTaxConfigSchema.setTaxRate(TaxRate);
	  tLDVATTaxConfigSchema.setID(id); 
	  tLDVATTaxConfigSchema.setConfigID(configID);
	  tLDVATTaxConfigSet.add(tLDVATTaxConfigSchema);
	

  VData tVData = new VData();
  tVData.add(tLDVATTaxConfigSet);
  tVData.add(tG);
  BusinessDelegate tBusinessDelegate; 
  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tVData, tOperate, busiName))
  {
	  Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	 	FlagStr = "Fail";
	}
	else
	{
		Content = " 操作成功! ";
	  FlagStr = "Succ";
  }  
 

  %>
  <html>
<script language="javascript">
 parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
