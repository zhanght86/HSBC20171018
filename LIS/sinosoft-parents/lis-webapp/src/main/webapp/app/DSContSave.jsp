<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//�������ƣ�GroupPolInput.jsp
	//�����ܣ�
	//�������ڣ�2002-08-15 11:48:43
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//�������
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String tAction = "";
	String tOperate = "";

	//�������
	VData tVData = new VData();
	LBPOContSchema tLBPOContSchema = new LBPOContSchema();
	LBPOAppntSchema tLBPOAppntSchema = new LBPOAppntSchema();
	LBPOInsuredSchema tLBPOInsuredSchema = new LBPOInsuredSchema();
	GlobalInput tG = new GlobalInput();

	tG = (GlobalInput) session.getValue("GI");

	String InputNo = request.getParameter("InputNo");
	loggerDebug("DSContSave","��" + InputNo + "��¼��");

	tAction = request.getParameter("fmAction");
	loggerDebug("DSContSave","������:" + tAction);
	//if(tAction.equals( "INSERT" )){
	String tempInputTime = "";
	tempInputTime = request.getParameter("InputTime");
	loggerDebug("DSContSave","tempInputTime" + tempInputTime);
	if (tempInputTime.equals("0") || tempInputTime.equals("")
			|| tempInputTime == null) {
		tempInputTime = "0";
	}
	int tInputTime = 0;
	tInputTime = Integer.parseInt(tempInputTime);
	tInputTime = tInputTime + 1;
	InputNo = String.valueOf(tInputTime);
	loggerDebug("DSContSave","InputNo: " + InputNo);
	// }

	LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
	tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
	tLDSysTraceSchema.setCreatePos("�б�¼��");
	tLDSysTraceSchema.setPolState("1002");
	LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
	inLDSysTraceSet.add(tLDSysTraceSchema);
	VData VData3 = new VData();
	VData3.add(tG);
	VData3.add(inLDSysTraceSet);

	String busiName="pubfunLockTableUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   
	//LockTableUI LockTableUI1 = new LockTableUI();
	if (!tBusinessDelegate.submitData(VData3, "INSERT",busiName)) {
		VData rVData = tBusinessDelegate.getResult();
		loggerDebug("DSContSave","LockTable Failed! "
		+ (String) rVData.get(0));
	} else {
		loggerDebug("DSContSave","LockTable Succed!");
	}
	//����¼��ʱ��Ҫ���⴦��

	//��ͬ��Ϣ
	//tLBPOContSchema.setGrpContNo(request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","�����ͬ����:" + request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","�����ͬ����:" + request.getParameter("ContNo"));
	loggerDebug("DSContSave","�����ͬ����:" + request.getParameter("MakeDate"));
	//��������Ϣ�޸�  
	tLBPOContSchema.setContNo(request.getParameter("ContNo"));
	tLBPOContSchema.setProposalContNo(request
			.getParameter("ProposalContNo"));
	tLBPOContSchema.setGrpContNo(request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","�����ͬ����:" + request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","�����ͬ����:" + request.getParameter("ContNo"));
	tLBPOContSchema.setContNo(request.getParameter("ContNo"));
	tLBPOContSchema.setProposalContNo(request
			.getParameter("ProposalContNo"));
	tLBPOContSchema.setPrtNo(request.getParameter("PrtNo"));
	tLBPOContSchema.setManageCom(request.getParameter("ManageCom"));
//	tLBPOContSchema.setBankCode(request.getParameter("AppntBankCode"));
//	tLBPOContSchema.setAccName(request.getParameter("AppntAccName"));
//	tLBPOContSchema
	//		.setBankAccNo(request.getParameter("AppntBankAccNo"));
	//tLBPOContSchema.setPolApplyDate(request
	//		.getParameter("PolAppntDate")); //Ͷ������        
	//tLBPOContSchema.setForceUWFlag("0");
	//¼�������˺�
	//tLBPOContSchema.setPayMode(request.getParameter("SecPayMode"));
	//tLBPOContSchema.setPayLocation(request.getParameter("SecPayMode"));
	//tLBPOContSchema.setBankCode(request
	//		.getParameter("SecAppntBankCode"));
	//tLBPOContSchema.setBankAccNo(request
	//		.getParameter("SecAppntBankAccNo"));
	//tLBPOContSchema.setAccName(request.getParameter("SecAppntAccName"));
	/*************************************/
	//¼�������˺�
	//tLBPOContSchema.setNewPayMode(request.getParameter("PayMode"));
	//tLBPOContSchema.setNewBankCode(request
	//		.getParameter("AppntBankCode"));
	//tLBPOContSchema.setNewBankAccNo(request
	//		.getParameter("AppntBankAccNo"));
	//tLBPOContSchema.setNewAccName(request.getParameter("AppntAccName"));
	//08-07-11����룬�Զ�������־���Զ��潻��־���������ͷ�ʽ
	//tLBPOContSchema.setRnewFlag(request.getParameter("RnewFlag"));
	//tLBPOContSchema.setAutoPayFlag(request.getParameter("AutoPayFlag"));
	//tLBPOContSchema.setGetPolMode(request.getParameter("GetPolMode"));
	tLBPOContSchema.setInputNo(InputNo);
	tLBPOContSchema.setFillNo(request.getParameter("contFillNo"));
	loggerDebug("DSContSave","====================================>>>>>>>>>inputNo: "
			+ InputNo);
	loggerDebug("DSContSave","saveҳ�� ��¼��ͬ��Ϣ���");

	//��¼Ͷ����ϵ
	String tRelationToInsured = request
			.getParameter("RelationToInsured");
	loggerDebug("DSContSave","Ͷ����ϵ:" + tRelationToInsured);
	tLBPOAppntSchema.setRelationToInsured(tRelationToInsured);

	//Ͷ������Ϣ
	tLBPOAppntSchema.setFillNo(request.getParameter("AppntFillNo"));
	tLBPOAppntSchema.setAppntNo(request.getParameter("AppntNo"));
	loggerDebug("DSContSave","�����˿ͻ���" + request.getParameter("AppntNo"));
	tLBPOAppntSchema.setAppntName(request.getParameter("AppntName"));
	tLBPOAppntSchema.setAppntSex(request.getParameter("AppntSex"));
	tLBPOAppntSchema.setAppntBirthday(request
			.getParameter("AppntBirthday"));
	tLBPOAppntSchema.setIDType(request.getParameter("AppntIDType"));
	tLBPOAppntSchema.setIDNo(request.getParameter("AppntIDNo"));
	tLBPOAppntSchema.setRgtAddress(request
			.getParameter("AppntRgtAddress"));
	tLBPOAppntSchema.setMarriage(request.getParameter("AppntMarriage"));
	tLBPOAppntSchema.setDegree(request.getParameter("AppntDegree"));
	tLBPOAppntSchema.setOccupationType(request
			.getParameter("AppntOccupationType"));
	tLBPOAppntSchema.setOccupationCode(request
			.getParameter("AppntOccupationCode"));
	tLBPOAppntSchema.setWorkType(request.getParameter("AppntWorkType"));
	tLBPOAppntSchema.setPluralityType(request
			.getParameter("AppntPluralityType"));
	tLBPOAppntSchema.setSmokeFlag(request
			.getParameter("AppntSmokeFlag"));
	tLBPOAppntSchema.setNativePlace(request
			.getParameter("AppntNativePlace"));
	tLBPOAppntSchema.setNationality(request
			.getParameter("AppntNationality"));
	tLBPOAppntSchema.setBankCode(request.getParameter("AppntBankCode"));
	tLBPOAppntSchema.setAccName(request.getParameter("AppntAccName"));
	tLBPOAppntSchema.setBankAccNo(request
			.getParameter("AppntBankAccNo"));
	tLBPOAppntSchema.setLicenseType(request
			.getParameter("AppntLicenseType"));
	tLBPOAppntSchema.setWorkType(request.getParameter("AppntWorkType"));
	tLBPOAppntSchema.setPluralityType(request
			.getParameter("AppntPluralityType"));
	tLBPOAppntSchema.setAddressNo(request
			.getParameter("AppntAddressNo"));
	tLBPOAppntSchema.setPostalAddress(request
			.getParameter("AppntPostalAddress"));
	tLBPOAppntSchema.setZipCode(request.getParameter("AppntZipCode"));
	tLBPOAppntSchema.setPhone(request.getParameter("AppntPhone"));
	tLBPOAppntSchema.setFax(request.getParameter("AppntFax"));
	tLBPOAppntSchema.setMobile(request.getParameter("AppntMobile"));
	tLBPOAppntSchema.setEMail(request.getParameter("AppntEMail"));
	tLBPOAppntSchema.setHomeAddress(request
			.getParameter("AppntHomeAddress"));
	tLBPOAppntSchema.setHomePhone(request
			.getParameter("AppntHomePhone"));
	tLBPOAppntSchema.setHomeFax(request.getParameter("AppntHomeFax"));
	tLBPOAppntSchema.setHomeZipCode(request
			.getParameter("AppntHomeZipCode"));
	loggerDebug("DSContSave","AppntHomeZipCode:"+request.getParameter("AppntHomeZipCode"));
	tLBPOAppntSchema.setCompanyPhone(request
			.getParameter("AppntCompanyPhone"));
	tLBPOAppntSchema.setCompanyAddress(request
			.getParameter("AppntCompanyAddress"));
	tLBPOAppntSchema.setCompanyZipCode(request
			.getParameter("AppntGrpZipCode"));
	tLBPOAppntSchema.setCompanyFax(request.getParameter("AppntGrpFax"));
	tLBPOAppntSchema.setFillNo(request.getParameter("AppntFillNo"));
	tLBPOAppntSchema.setManageCom(request.getParameter("ManageCom"));
	//09-11-19��Ͷ������ʽ�޸� ���������ֶ�
	tLBPOAppntSchema.setIDExpDate(request.getParameter("AppntIDExpDate"));
	tLBPOAppntSchema.setSocialInsuFlag(request.getParameter("AppntSocialInsuFlag"));

	loggerDebug("DSContSave","save ҳ�� ��¼Ͷ������Ϣ���");

	//��������Ϣ
	tLBPOInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
	loggerDebug("DSContSave",request.getParameter("InsuredNo"));
	tLBPOInsuredSchema.setName(request.getParameter("Name"));
	tLBPOInsuredSchema.setSex(request.getParameter("Sex"));
	tLBPOInsuredSchema.setBirthday(request.getParameter("Birthday"));
	tLBPOInsuredSchema.setIDType(request.getParameter("IDType"));
	tLBPOInsuredSchema.setIDNo(request.getParameter("IDNo"));
	tLBPOInsuredSchema.setNativePlace(request
			.getParameter("NativePlace"));
	tLBPOInsuredSchema.setMarriage(request.getParameter("Marriage"));
	tLBPOInsuredSchema.setWorkType(request.getParameter("WorkType"));
	tLBPOInsuredSchema.setPluralityType(request
			.getParameter("PluralityType"));
	tLBPOInsuredSchema.setOccupationCode(request
			.getParameter("OccupationCode"));
	tLBPOInsuredSchema.setOccupationType(request
			.getParameter("OccupationType"));
	tLBPOInsuredSchema.setRelationToMainInsured(request
			.getParameter("RelationToMainInsured"));
	tLBPOInsuredSchema.setRelationToAppnt(request
			.getParameter("RelationToAppnt"));
	tLBPOInsuredSchema.setContNo(request.getParameter("ContNo"));
	tLBPOInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLBPOInsuredSchema.setContPlanCode(request
			.getParameter("ContPlanCode"));
	tLBPOInsuredSchema
			.setExecuteCom(request.getParameter("ExecuteCom"));
	tLBPOInsuredSchema.setJoinCompanyDate(request
			.getParameter("JoinCompanyDate"));
	tLBPOInsuredSchema.setInsuredPeoples(request
			.getParameter("InsuredPeoples"));
	tLBPOInsuredSchema.setSalary(request.getParameter("Salary"));
	tLBPOInsuredSchema.setBankCode(request.getParameter("BankCode"));
	tLBPOInsuredSchema.setBankAccNo(request.getParameter("BankAccNo"));
	tLBPOInsuredSchema.setAccName(request.getParameter("AccName"));
	tLBPOInsuredSchema.setLicenseType(request
			.getParameter("LicenseType"));
	tLBPOInsuredSchema
			.setRgtAddress(request.getParameter("RgtAddress"));
	tLBPOInsuredSchema
			.setSequenceNo(request.getParameter("SequenceNo"));
	tLBPOInsuredSchema.setPostalAddress(request
			.getParameter("PostalAddress"));
	tLBPOInsuredSchema.setZipCode(request.getParameter("ZipCode"));
	tLBPOInsuredSchema.setPhone(request.getParameter("Phone"));
	tLBPOInsuredSchema.setFax(request.getParameter("Fax"));
	tLBPOInsuredSchema.setMobile(request.getParameter("Mobile"));
	tLBPOInsuredSchema.setEMail(request.getParameter("EMail"));
	tLBPOInsuredSchema.setHomeAddress(request
			.getParameter("HomeAddress"));
	tLBPOInsuredSchema.setHomeZipCode(request
			.getParameter("HomeZipCode"));
	tLBPOInsuredSchema.setHomePhone(request.getParameter("HomePhone"));
	tLBPOInsuredSchema.setHomeFax(request.getParameter("HomeFax"));
	tLBPOInsuredSchema
			.setCompanyPhone(request.getParameter("CompanyPhone"));
	tLBPOInsuredSchema.setCompanyAddress(request
			.getParameter("CompanyAddress"));
	tLBPOInsuredSchema.setCompanyZipCode(request
			.getParameter("GrpZipCode"));
	tLBPOInsuredSchema.setCompanyFax(request.getParameter("GrpFax"));
	tLBPOInsuredSchema.setFillNo(request.getParameter("InsuredFillNo"));
	tLBPOInsuredSchema.setManageCom(request.getParameter("ManageCom"));
	//09-11-20 ��Ͷ������ʽ�޸�  ���������ֶ�
	tLBPOInsuredSchema.setIDExpDate(request.getParameter("IDExpDate"));
	tLBPOInsuredSchema.setSocialInsuFlag(request.getParameter("SocialInsuFlag"));

	//�����˵�ַ��Ϣ
	loggerDebug("DSContSave","save ҳ�� ��¼��������Ϣ���");

	// ׼���������� VData                                                           
	tVData.add(tLBPOContSchema);
	tVData.add(tLBPOAppntSchema);
	tVData.add(tLBPOInsuredSchema);
	tVData.add(tG);

	//���ݷ�SCHEMA��Ϣ                                                              
	TransferData tTransferData = new TransferData();
	String SavePolType = "";
	String BQFlag = request.getParameter("BQFlag");
	if (BQFlag == null)
		SavePolType = "0";
	else if (BQFlag.equals(""))
		SavePolType = "0";
	else
		SavePolType = BQFlag;

	tTransferData.setNameAndValue("SavePolType", SavePolType); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ
	tTransferData.setNameAndValue("GrpNo", request
			.getParameter("AppntGrpNo"));
	tTransferData.setNameAndValue("GrpName", request
			.getParameter("AppntGrpName"));
	tTransferData.setNameAndValue("FamilyType", request
			.getParameter("FamilyType"));
	loggerDebug("DSContSave","SavePolType��BQ is 2��other is 0 : "
			+ request.getParameter("BQFlag"));
	tVData.addElement(tTransferData);

	if (tAction.equals("INSERT"))
		tOperate = "INSERT||CONT";
	if (tAction.equals("UPDATE"))
		tOperate = "UPDATE||CONT";

	loggerDebug("DSContSave","saveҳ�� �������ݷ���vdata���");
	String tbusiName="tbDSContUI";
	BusinessDelegate ttBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//DSContUI tDSContUI = new DSContUI();
	loggerDebug("DSContSave","before submit");
	if (ttBusinessDelegate.submitData(tVData, tOperate,tbusiName) == false) {
		Content = " ����ʧ�ܣ�ԭ����: "
		+ ttBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";

	} else {
		Content = " �����ɹ�! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = ttBusinessDelegate.getResult();

		// ��ʾ
		// ������Ϣ
		LBPOContSchema mLBPOContSchema = new LBPOContSchema();
		mLBPOContSchema.setSchema((LBPOContSchema) tVData
		.getObjectByObjectName("LBPOContSchema", 0));
%>
<script language="javascript">
    	 	parent.fraInterface.document.all("ContNo").value = "<%=mLBPOContSchema.getContNo()%>";
    	 	//alert("contNo==="+parent.fraInterface.document.all("ContNo").value);
    	 	parent.fraInterface.document.all("ProposalContNo").value = "<%=mLBPOContSchema.getProposalContNo()%>";   
    	 	parent.fraInterface.document.all("GrpContNo").value = "<%=mLBPOContSchema.getGrpContNo()%>";    	 	
	    	parent.fraInterface.document.all("AppntMakeDate").value = "<%=mLBPOContSchema.getMakeDate()%>";
	    	parent.fraInterface.document.all("AppntMakeTime").value = "<%=mLBPOContSchema.getMakeTime()%>";
    	</script>
<%
	}
	loggerDebug("DSContSave","Content:" + Content);
	if (tAction.equals("DELETE")) {
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit5("<%=FlagStr%>","<%=Content%>");
</script>
</html>

<%
} else {
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>
</html>


<%
}
%>
