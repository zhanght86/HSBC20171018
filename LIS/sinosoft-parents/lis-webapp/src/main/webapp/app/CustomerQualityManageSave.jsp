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
  loggerDebug("CustomerQualityManageSave","\n\n---UWCustomerQualitySave Start---");
//  loggerDebug("CustomerQualityManageSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LDPersonSchema tLDPersonSchema = new LDPersonSchema();
  
  String tCustomerNo = request.getParameter("CustomerNoSel");
  String tCQualityFlag = request.getParameter("CQualityFlag");
  //String tCReasonType = request.getParameter("CReasonType");
  String tReason = request.getParameter("Reason");
  
  tLDPersonSchema.setCustomerNo(tCustomerNo);
  tLDPersonSchema.setBlacklistFlag(tCQualityFlag);
  tLDPersonSchema.setRemark(tReason);
  
  loggerDebug("CustomerQualityManageSave","tCustomerNo:"+tCustomerNo);
  loggerDebug("CustomerQualityManageSave","tCQualityFlag:"+tCQualityFlag);
 // loggerDebug("CustomerQualityManageSave","tCReasonType:"+tCReasonType);
  loggerDebug("CustomerQualityManageSave","tReason:"+tReason);

  VData inVData = new VData();
  inVData.add(tLDPersonSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  //UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();
  String busiName="cbcheckUWQualityManageUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  if (!tBusinessDelegate.submitData(inVData, "CUSTOMER&&UPDATE",busiName)) {
    VData rVData = tBusinessDelegate.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("CustomerQualityManageSave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

