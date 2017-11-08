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
  String tOperate = request.getParameter("fmtransact");
  String busiName=   "ConfigVATRiskGrpUI";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  String RiskCode  = request.getParameter("RiskCode");
  String RiskGrpCode  = request.getParameter("RiskGrpCode");
  String RecordID = request.getParameter("RecordID"); 
  LDVATGrpSet tLDVATGrpSet = new LDVATGrpSet();
  LDVATGrpSchema tLDVATGrpSchema = new LDVATGrpSchema();
  tLDVATGrpSchema.setRiskCode(RiskCode);
  tLDVATGrpSchema.setRiskGrp(RiskGrpCode);
  tLDVATGrpSchema.setID(RecordID); 
  tLDVATGrpSet.add(tLDVATGrpSchema);
  VData tVData = new VData();
  tVData.add(tLDVATGrpSet);
  tVData.add(tG);
  BusinessDelegate tBusinessDelegate; 
  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if(!tBusinessDelegate.submitData(tVData, tOperate, busiName)){
	  Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	  FlagStr = "Fail";
  }else{
	  Content = " 操作成功! ";
	  FlagStr = "Succ";
	}  
  %>
 <html>
	<script language="javascript">
		parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
	</script>
</html>
