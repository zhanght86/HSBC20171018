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
  
<%
  loggerDebug("RnewHospitalQualitySave","\n\n---UWCustomerQualitySave Start---");
  loggerDebug("RnewHospitalQualitySave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("RnewHospitalQualitySave","ContNo:" + request.getParameter("ContNo"));
//  loggerDebug("RnewHospitalQualitySave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LCHospitalQualityRecordSchema tLCHospitalQualityRecordSchema = new LCHospitalQualityRecordSchema();
  

  String tContNo = request.getParameter("ContNo");
  String tHospitalCode =request.getParameter("HospitalCode");
  String tQualityFlag =request.getParameter("QualityFlag");
  String tRemark =request.getParameter("Remark");
  //���ҽԺ����
  String tCheckedItem1 = request.getParameter("CheckedItem1");
  //���������
  String tCheckedItem2 = request.getParameter("CheckedItem2");
  //���ڸ�����
  String tCheckedItem3 = request.getParameter("CheckedItem3");
  
  tLCHospitalQualityRecordSchema.setContNo(tContNo);
  tLCHospitalQualityRecordSchema.setHospitCode(tHospitalCode);
  tLCHospitalQualityRecordSchema.setQualityFlag(tQualityFlag);
  tLCHospitalQualityRecordSchema.setRemark(tRemark);
  tLCHospitalQualityRecordSchema.setHospitalQuality(tCheckedItem1);
  tLCHospitalQualityRecordSchema.setManagerQuality(tCheckedItem2);
  tLCHospitalQualityRecordSchema.setInsideQuality(tCheckedItem3);
 
  VData inVData = new VData();
  inVData.add(tLCHospitalQualityRecordSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  UWHospitalQualityRecordUI tUWHospitalQualityRecordUI = new UWHospitalQualityRecordUI();

  if (!tUWHospitalQualityRecordUI.submitData(inVData, "INSERT")) {
    VData rVData = tUWHospitalQualityRecordUI.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("RnewHospitalQualitySave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

