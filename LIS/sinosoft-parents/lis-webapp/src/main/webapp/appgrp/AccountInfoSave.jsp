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
<%@page import="com.sinosoft.lis.schema.LCGrpAddressSchema"%>
<%@page import="com.sinosoft.lis.schema.LDGrpSchema"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanDutyParamSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//������Ϣ������У�鴦��
//�������
LCAccountInfoUI tLCAccountInfoUI = new LCAccountInfoUI();

LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
LDGrpSchema tLDGrpSchema   = new LDGrpSchema();                //����ͻ�

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("AccountInfoSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String tGrpContNo = request.getParameter("GrpContNo");	//�����ͬ����
loggerDebug("AccountInfoSave","wangxw@ tGrpContNo"+tGrpContNo);
String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//����Ͷ��������

//String tCValiDate=request.getParameter("CValiDate");
  TransferData tTransferData = new TransferData(); 
  tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo")); 
  tTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNo"));
  tTransferData.setNameAndValue("SupGrpNo",request.getParameter("SupGrpNo")); 
  tTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo"));
  tTransferData.setNameAndValue("AddressNo",request.getParameter("AddressNo"));

//�ӹ�˾��Ϣ

	//����ͻ���Ϣ  LDGrp
	tLDGrpSchema.setCustomerNo(request.getParameter("GrpNo"));            //�ͻ�����
	tLDGrpSchema.setGrpName(request.getParameter("GrpName"));             //��λ����
	tLDGrpSchema.setGrpNature(request.getParameter("GrpNature"));         //��λ����
	tLDGrpSchema.setBusinessType(request.getParameter("BusinessType"));   //��ҵ���
	tLDGrpSchema.setPeoples(request.getParameter("Peoples"));             //������
	tLDGrpSchema.setAsset(request.getParameter("Asset"));                 //�ʲ��ܶ�	
	tLDGrpSchema.setCorporation(request.getParameter("Corporation"));     //����	
	tLDGrpSchema.setFax(request.getParameter("Fax"));             //����	
	//tLDGrpSchema.setOnWorkPeoples(request.getParameter("AppntOnWorkPeoples"));             //������
	//tLDGrpSchema.setOffWorkPeoples(request.getParameter("AppntOffWorkPeoples"));             //������
	//tLDGrpSchema.setOtherPeoples(request.getParameter("AppntOtherPeoples"));             //������	    	    	    
	//tLDGrpSchema.setRgtMoney(request.getParameter("RgtMoney"));           //ע���ʱ�
	//tLDGrpSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //���ʲ�������
	//tLDGrpSchema.setMainBussiness(request.getParameter("MainBussiness")); //��Ӫҵ��
	//tLDGrpSchema.setComAera(request.getParameter("ComAera"));             //�����ֲ�����
	//tLDGrpSchema.setPhone(request.getParameter("Phone"));             //�ܻ�
	//tLDGrpSchema.setFoundDate(request.getParameter("FoundDate"));             //����ʱ��
	
	
	//����ͻ���ַ  LCGrpAddress	    
	//tLCGrpAddressSchema.setCustomerNo(request.getParameter("GrpNo"));            //�ͻ�����
	tLCGrpAddressSchema.setAddressNo(request.getParameter("GrpAddressNo"));      //��ַ����
	tLCGrpAddressSchema.setGrpAddress(request.getParameter("GrpAddress"));       //��λ��ַ
	loggerDebug("AccountInfoSave","*******************"+request.getParameter("GrpAddress"));
	tLCGrpAddressSchema.setGrpZipCode(request.getParameter("GrpZipCode"));       //��λ�ʱ�
	//������ϵ��һ
	tLCGrpAddressSchema.setLinkMan1(request.getParameter("LinkMan1"));
	tLCGrpAddressSchema.setDepartment1(request.getParameter("Department1"));
	tLCGrpAddressSchema.setPhone1(request.getParameter("Phone1"));
	tLCGrpAddressSchema.setE_Mail1(request.getParameter("E_Mail1"));
	//tLCGrpAddressSchema.setHeadShip1(request.getParameter("HeadShip1"));
	//tLCGrpAddressSchema.setFax1(request.getParameter("Fax1"));
	//������ϵ�˶�
	tLCGrpAddressSchema.setLinkMan2(request.getParameter("LinkMan2"));
	tLCGrpAddressSchema.setDepartment2(request.getParameter("Department2"));
	tLCGrpAddressSchema.setPhone2(request.getParameter("Phone2"));
	tLCGrpAddressSchema.setE_Mail2(request.getParameter("E_Mail2"));
	//tLCGrpAddressSchema.setHeadShip2(request.getParameter("HeadShip2"));	
	//tLCGrpAddressSchema.setFax2(request.getParameter("Fax2"));



// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.add(tTransferData);
tVData.addElement(tLDGrpSchema);
tVData.addElement(tLCGrpAddressSchema);


try{
	loggerDebug("AccountInfoSave","this will deal the data!!!");
	tLCAccountInfoUI.submitData(tVData,tOperate);
	loggerDebug("AccountInfoSave","deal Accountinfo completely!!!");	
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCAccountInfoUI.mErrors;
	if (!tError.needDealError()){
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
	}
	else{
		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}
	loggerDebug("AccountInfoSave","wangxw@ Content"+ Content);	
}
%>
 
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script> 
