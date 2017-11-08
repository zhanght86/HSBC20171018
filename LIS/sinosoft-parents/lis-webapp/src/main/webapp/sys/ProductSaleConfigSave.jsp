<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%
  loggerDebug("ProductSaleConfigSave","start BounsRiskPreUI.jsp ");
  //需要传入后台参数（最大集合）

  String Content = "";    //处理结果
  String Flag="";  
  String tAction = request.getParameter("hideaction");
 
  String tOtherSQL = "";
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
  //需要保存的信息
  LDRiskRuleSet tLDRiskRuleSet = new LDRiskRuleSet();
  String tRiskCode = request.getParameter("RiskCode");
  String tSaleChnl = request.getParameter("SaleChnl");
  String tComGroup = request.getParameter("ComGroup");
  String tPolApplyDateStart = request.getParameter("PolApplyDateStart");
  String tPolApplyDateEnd = request.getParameter("PolApplyDateEnd");
  String tScanDateStart = request.getParameter("ScanDateStart");
  String tScanTimeStart = request.getParameter("ScanTimeStart");
  String tScanDateEnd = request.getParameter("ScanDateEnd");
  String tScanTimeEnd = request.getParameter("ScanTimeEnd");
  if(tPolApplyDateEnd==null)
  {
	  tPolApplyDateEnd = "";
  }
  if(tScanDateEnd==null)
  {
	  tScanDateEnd = "";
  }
  if(tScanTimeEnd==null)
  {
	  tScanTimeEnd = "";
  }
  //保存操作
  if(tAction.equals("INSERT"))
  {
   	loggerDebug("ProductSaleConfigSave","begin deal Insert..");
   	LDRiskRuleSchema tLDRiskRuleSchema = new LDRiskRuleSchema();
   	tLDRiskRuleSchema.setRiskCode(tRiskCode);
   	tLDRiskRuleSchema.setSaleChnl(tSaleChnl);
   	tLDRiskRuleSchema.setComGroup(tComGroup);
   	tLDRiskRuleSchema.setStartPolApplyDate(tPolApplyDateStart);
   	tLDRiskRuleSchema.setEndPolApplyDate(tPolApplyDateEnd);
   	tLDRiskRuleSchema.setStartScanDate(tScanDateStart);
   	tLDRiskRuleSchema.setStartScanTime(tScanTimeStart);
   	tLDRiskRuleSchema.setEndScanDate(tScanDateEnd);
   	tLDRiskRuleSchema.setEndScanTime(tScanTimeEnd);
   	tLDRiskRuleSet.add(tLDRiskRuleSchema);
  }

  VData tVData = new VData();
  VData mResult = new VData();
  tVData.add(tGI);
  tVData.add(tLDRiskRuleSet);

  ProductSaleConfigUI tProductSaleConfigUI = new ProductSaleConfigUI();
  Flag="Succ";
  Content = "保存成功";
   loggerDebug("ProductSaleConfigSave","33333333333333");
  if(!tProductSaleConfigUI.submitData(tVData,tAction))
  {
    loggerDebug("ProductSaleConfigSave","数据处理错误");
    Flag="Fail";
    Content = " 处理失败，原因是:" +tProductSaleConfigUI.mErrors.getFirstError();
  }
   loggerDebug("ProductSaleConfigSave",Content + "\n" + Flag + "\n---数据处理完毕 End---\n\n");
%>
 
<html>
<script language="javascript"> 
   parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
</script>
</html> 
  
 
