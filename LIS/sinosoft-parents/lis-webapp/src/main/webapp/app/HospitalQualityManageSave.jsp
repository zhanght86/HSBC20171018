<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�UWCustomerQualitySave.jsp
//�����ܣ�
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��    ������      ��������      ����ԭ��/����
%>
<!--�û�У����-->
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
    Content = " ����ʧ�ܣ�ԭ����:" + tCErrors.getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("HospitalQualityManageSave",Content + "\n" + FlagStr + "\n---UWHospitalQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

