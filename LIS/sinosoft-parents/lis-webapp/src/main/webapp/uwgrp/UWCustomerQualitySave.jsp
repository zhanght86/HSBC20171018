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
  <%@page import="com.sinosoft.service.*" %>
<%
  loggerDebug("UWCustomerQualitySave","\n\n---UWCustomerQualitySave Start---");
  loggerDebug("UWCustomerQualitySave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("UWCustomerQualitySave","ContNo:" + request.getParameter("ContNo"));
//  loggerDebug("UWCustomerQualitySave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LDPersonSchema tLDPersonSchema = new LDPersonSchema();
  tLDPersonSchema.setCustomerNo(request.getParameter("CustomerNo"));
  tLDPersonSchema.setBlacklistFlag(request.getParameter("BlacklistFlagNo"));
  tLDPersonSchema.setRemark(request.getParameter("Remark"));

  VData inVData = new VData();
  inVData.add(tLDPersonSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  String busiName="cbcheckgrpUWQualityManageUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 // UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();

  if (!tBusinessDelegate.submitData(inVData, "UPDATE",busiName)) {
    VData rVData = tBusinessDelegate.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("UWCustomerQualitySave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

