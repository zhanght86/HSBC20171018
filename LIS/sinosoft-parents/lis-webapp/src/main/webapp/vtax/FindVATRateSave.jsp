<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：FindVATRateSave.jsp
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
  String busiName=   "FindVATRateUI";
  GlobalInput tG = new GlobalInput();   /*当前登录信息 */
  tG=(GlobalInput)session.getValue("GI");
	
	 String FeeTypeCode  = request.getParameter("FeeTypeCode1");  //费用类型编码
	  String FeeTypeName  = request.getParameter("FeeTypeName1");  //费用类型名字
	  String Taxible  = request.getParameter("Taxible");  //   是否应税
	  String RiskRele  = request.getParameter("RiskRele"); //  是否险种相关
	  String RecordID = request.getParameter("RecordID");  //id
	  LDVATConfigSet tLDVATConfigSet = new LDVATConfigSet();
	  LDVATConfigSchema tLDVATConfigSchema = new LDVATConfigSchema(); //增值税定义
	  tLDVATConfigSchema.setFeeTypeCode(FeeTypeCode);
	  tLDVATConfigSchema.setFeeTypeName(FeeTypeName);
	  tLDVATConfigSchema.setIsTaxable(Taxible);
	  tLDVATConfigSchema.setIsReleToRiskType(RiskRele);
	  tLDVATConfigSchema.setID(RecordID); 
	  tLDVATConfigSet.add(tLDVATConfigSchema);

  VData tVData = new VData();
  tVData.add(tLDVATConfigSet);
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
