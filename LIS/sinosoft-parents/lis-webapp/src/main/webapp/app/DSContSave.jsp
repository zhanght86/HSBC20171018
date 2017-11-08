<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//程序名称：GroupPolInput.jsp
	//程序功能：
	//创建日期：2002-08-15 11:48:43
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String tAction = "";
	String tOperate = "";

	//输入参数
	VData tVData = new VData();
	LBPOContSchema tLBPOContSchema = new LBPOContSchema();
	LBPOAppntSchema tLBPOAppntSchema = new LBPOAppntSchema();
	LBPOInsuredSchema tLBPOInsuredSchema = new LBPOInsuredSchema();
	GlobalInput tG = new GlobalInput();

	tG = (GlobalInput) session.getValue("GI");

	String InputNo = request.getParameter("InputNo");
	loggerDebug("DSContSave","第" + InputNo + "次录入");

	tAction = request.getParameter("fmAction");
	loggerDebug("DSContSave","动作是:" + tAction);
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
	tLDSysTraceSchema.setCreatePos("承保录单");
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
	//三码录入时需要特殊处理

	//合同信息
	//tLBPOContSchema.setGrpContNo(request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","集体合同号是:" + request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","集体合同号是:" + request.getParameter("ContNo"));
	loggerDebug("DSContSave","集体合同号是:" + request.getParameter("MakeDate"));
	//被保人信息修改  
	tLBPOContSchema.setContNo(request.getParameter("ContNo"));
	tLBPOContSchema.setProposalContNo(request
			.getParameter("ProposalContNo"));
	tLBPOContSchema.setGrpContNo(request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","集体合同号是:" + request.getParameter("GrpContNo"));
	loggerDebug("DSContSave","集体合同号是:" + request.getParameter("ContNo"));
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
	//		.getParameter("PolAppntDate")); //投保日期        
	//tLBPOContSchema.setForceUWFlag("0");
	//录入续期账号
	//tLBPOContSchema.setPayMode(request.getParameter("SecPayMode"));
	//tLBPOContSchema.setPayLocation(request.getParameter("SecPayMode"));
	//tLBPOContSchema.setBankCode(request
	//		.getParameter("SecAppntBankCode"));
	//tLBPOContSchema.setBankAccNo(request
	//		.getParameter("SecAppntBankAccNo"));
	//tLBPOContSchema.setAccName(request.getParameter("SecAppntAccName"));
	/*************************************/
	//录入首期账号
	//tLBPOContSchema.setNewPayMode(request.getParameter("PayMode"));
	//tLBPOContSchema.setNewBankCode(request
	//		.getParameter("AppntBankCode"));
	//tLBPOContSchema.setNewBankAccNo(request
	//		.getParameter("AppntBankAccNo"));
	//tLBPOContSchema.setNewAccName(request.getParameter("AppntAccName"));
	//08-07-11后加入，自动续保标志，自动垫交标志，保单递送方式
	//tLBPOContSchema.setRnewFlag(request.getParameter("RnewFlag"));
	//tLBPOContSchema.setAutoPayFlag(request.getParameter("AutoPayFlag"));
	//tLBPOContSchema.setGetPolMode(request.getParameter("GetPolMode"));
	tLBPOContSchema.setInputNo(InputNo);
	tLBPOContSchema.setFillNo(request.getParameter("contFillNo"));
	loggerDebug("DSContSave","====================================>>>>>>>>>inputNo: "
			+ InputNo);
	loggerDebug("DSContSave","save页面 记录合同信息完毕");

	//记录投被关系
	String tRelationToInsured = request
			.getParameter("RelationToInsured");
	loggerDebug("DSContSave","投被关系:" + tRelationToInsured);
	tLBPOAppntSchema.setRelationToInsured(tRelationToInsured);

	//投保人信息
	tLBPOAppntSchema.setFillNo(request.getParameter("AppntFillNo"));
	tLBPOAppntSchema.setAppntNo(request.getParameter("AppntNo"));
	loggerDebug("DSContSave","被保人客户号" + request.getParameter("AppntNo"));
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
	//09-11-19新投保单格式修改 新增两个字段
	tLBPOAppntSchema.setIDExpDate(request.getParameter("AppntIDExpDate"));
	tLBPOAppntSchema.setSocialInsuFlag(request.getParameter("AppntSocialInsuFlag"));

	loggerDebug("DSContSave","save 页面 记录投保人信息完毕");

	//被保人信息
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
	//09-11-20 新投保单格式修改  增加两个字段
	tLBPOInsuredSchema.setIDExpDate(request.getParameter("IDExpDate"));
	tLBPOInsuredSchema.setSocialInsuFlag(request.getParameter("SocialInsuFlag"));

	//被保人地址信息
	loggerDebug("DSContSave","save 页面 记录被保人信息完毕");

	// 准备传输数据 VData                                                           
	tVData.add(tLBPOContSchema);
	tVData.add(tLBPOAppntSchema);
	tVData.add(tLBPOInsuredSchema);
	tVData.add(tG);

	//传递非SCHEMA信息                                                              
	TransferData tTransferData = new TransferData();
	String SavePolType = "";
	String BQFlag = request.getParameter("BQFlag");
	if (BQFlag == null)
		SavePolType = "0";
	else if (BQFlag.equals(""))
		SavePolType = "0";
	else
		SavePolType = BQFlag;

	tTransferData.setNameAndValue("SavePolType", SavePolType); //保全保存标记，默认为0，标识非保全
	tTransferData.setNameAndValue("GrpNo", request
			.getParameter("AppntGrpNo"));
	tTransferData.setNameAndValue("GrpName", request
			.getParameter("AppntGrpName"));
	tTransferData.setNameAndValue("FamilyType", request
			.getParameter("FamilyType"));
	loggerDebug("DSContSave","SavePolType，BQ is 2，other is 0 : "
			+ request.getParameter("BQFlag"));
	tVData.addElement(tTransferData);

	if (tAction.equals("INSERT"))
		tOperate = "INSERT||CONT";
	if (tAction.equals("UPDATE"))
		tOperate = "UPDATE||CONT";

	loggerDebug("DSContSave","save页面 所有数据放入vdata完毕");
	String tbusiName="tbDSContUI";
	BusinessDelegate ttBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//DSContUI tDSContUI = new DSContUI();
	loggerDebug("DSContSave","before submit");
	if (ttBusinessDelegate.submitData(tVData, tOperate,tbusiName) == false) {
		Content = " 操作失败，原因是: "
		+ ttBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";

	} else {
		Content = " 操作成功! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = ttBusinessDelegate.getResult();

		// 显示
		// 保单信息
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
