<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpFeeSave.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:12:33
//������ ��CrtHtml���򴴽�
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanDutyParamSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanSchema"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanDutyParamSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//������Ϣ������У�鴦��
//�������

ImportContPlanBL tImportContPlanBL = new ImportContPlanBL();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("ImportContPlanSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String tGrpContNo = request.getParameter("GrpContNo");	//�����ͬ����
String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//����Ͷ��������
loggerDebug("ImportContPlanSave","wangxw@ tProposalGrpContNo"+tProposalGrpContNo);

  TransferData tTransferData = new TransferData(); 
  tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo")); 
  tTransferData.setNameAndValue("ProposalGrpContNo",request.getParameter("ProposalGrpContNo"));
  tTransferData.setNameAndValue("AskpriceNo",request.getParameter("AskpriceNo"));
  tTransferData.setNameAndValue("AskNo",request.getParameter("AskNo"));  
  //tTransferData.setNameAndValue("SupGrpNo",request.getParameter("SupGrpNo")); 
  //tTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo"));
  //tTransferData.setNameAndValue("AddressNo",request.getParameter("AddressNo"));


// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tTransferData);


try{
	loggerDebug("ImportContPlanSave","Import ContPlan data!!!");
	tImportContPlanBL.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tImportContPlanBL.mErrors;
	if (!tError.needDealError()){
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
	}
	else{
		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}
}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
