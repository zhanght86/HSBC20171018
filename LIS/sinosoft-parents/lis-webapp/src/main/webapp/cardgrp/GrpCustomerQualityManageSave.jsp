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
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  
<%
  loggerDebug("GrpCustomerQualityManageSave","\n\n---UWCustomerQualitySave Start---");
//  loggerDebug("GrpCustomerQualityManageSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LDPersonSchema tLDPersonSchema = new LDPersonSchema();
  LDGrpSchema tLDGrpSchema=new LDGrpSchema();
  
  String tCustomerNo = request.getParameter("CustomerNoSel");
  String tCQualityFlag = request.getParameter("CQualityFlag");
  //String tCReasonType = request.getParameter("CReasonType");
  String tReason = request.getParameter("Reason");
  
  //tLDPersonSchema.setCustomerNo(tCustomerNo);
  //tLDPersonSchema.setBlacklistFlag(tCQualityFlag);
  //tLDPersonSchema.setRemark(tReason);
  tLDGrpSchema.setCustomerNo(tCustomerNo);
  tLDGrpSchema.setBlacklistFlag(tCQualityFlag);
  tLDGrpSchema.setBlackListReason("00"); //û�б��� ��ʱ��00 Ҳ���Ժ������
  tLDGrpSchema.setRemark(tReason);
  
  loggerDebug("GrpCustomerQualityManageSave","tCustomerNo:"+tCustomerNo);
  loggerDebug("GrpCustomerQualityManageSave","tCQualityFlag:"+tCQualityFlag);
 // loggerDebug("GrpCustomerQualityManageSave","tCReasonType:"+tCReasonType);
  loggerDebug("GrpCustomerQualityManageSave","tReason:"+tReason);

  VData inVData = new VData();
  inVData.add(tLDGrpSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();

  if (!tUWQualityManageUI.submitData(inVData, "CUSTOMER||QUALITY")) {
    VData rVData = tUWQualityManageUI.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("GrpCustomerQualityManageSave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

