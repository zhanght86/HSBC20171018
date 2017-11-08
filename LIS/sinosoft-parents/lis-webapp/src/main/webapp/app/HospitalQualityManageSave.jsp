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
  loggerDebug("HospitalQualityManageSave","\n\n---UWCustomerQualitySave Start---");
//  loggerDebug("HospitalQualityManageSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LDHospitalSchema tLDHospitalSchema = new LDHospitalSchema();

  String tHospitalCode = request.getParameter("HospitalCode");
  String tQualityFlag = request.getParameter("QualityFlag");
  String tQualityFlagType = request.getParameter("QualityFlagType");
  String tReason = request.getParameter("Reason");
  
  tLDHospitalSchema.setHospitCode(tHospitalCode);
  tLDHospitalSchema.setBlackListFlag(tQualityFlag);
  tLDHospitalSchema.setReasonType(tQualityFlagType);
  tLDHospitalSchema.setRemark(tReason);
  
  loggerDebug("HospitalQualityManageSave","tHospitalCode:"+tHospitalCode);
  loggerDebug("HospitalQualityManageSave","tQualityFlag:"+tQualityFlag);
  loggerDebug("HospitalQualityManageSave","tQualityFlagType:"+tQualityFlagType);
  loggerDebug("HospitalQualityManageSave","tReason:"+tReason);

  VData inVData = new VData();
  inVData.add(tLDHospitalSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  //UWHospitalManageUI tUWHospitalManageUI = new UWHospitalManageUI();
  String busiName="cbcheckUWHospitalManageUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  if (!tBusinessDelegate.submitData(inVData, "UPDATE",busiName)) {
    CErrors tCErrors = tBusinessDelegate.getCErrors();
    Content = " 处理失败，原因是:" + tCErrors.getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("HospitalQualityManageSave",Content + "\n" + FlagStr + "\n---UWHospitalQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

