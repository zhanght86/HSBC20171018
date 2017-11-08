/***************************************************************
 * <p>ProName��LLClaimCaseReviewInput.js</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var tSQLInfo = new SqlClass();
var mFlag;
var mReviewFlag = false;
var mCheckFlag = false;
var tPageNo;
var tIndexNo;
var tSubmitNum = 0;
var tPayRuleFlag = false;
/**
 * ��ѯ������Ϣ
 */
function queryAcceptInfo() {
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql1");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
		
		initAcceptInfo();
		fm.GrpRgtNo.value = mGrpRegisterNo;
		fm.AppntName.value = tArr[0][1];
		fm.PageNo.value = tArr[0][2];
		fm.ConfirmDate.value = tArr[0][3];
		fm.ConfirmOperator.value = tArr[0][4];		
		fm.AcceptCom.value = tArr[0][5];
		fm.AcceptComName.value = tArr[0][6];
		
		fm.ReviewMoney.value = tArr[0][7];
		fm.CaseType.value = tArr[0][8];
		fm.CaseTypeName.value = tArr[0][9];
		
		var tBatchInfo = "<font color='red'>�������½���������"+ tArr[0][10] +"��,"
			+ "������������"+ tArr[0][11] +"��,δ����������"+ tArr[0][12] +"��,�����������"+ tArr[0][13] +"��,";
		
		if (fm.CaseType.value=="02") {
			tBatchInfo = tBatchInfo + "δ�ش�������"+ tArr[0][14] +"��,";
		}
		
		tBatchInfo = tBatchInfo + "�ѽ᰸������"+ tArr[0][15] +"��</font>";
		
		BatchInfo.innerHTML = tBatchInfo;
	}
}
/**
 * ��ѯ��ά���Ŀͻ���Ϣ�б�
 */
function queryCustomerList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql2");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	if (mMode==1) {
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara(mOperator);
	}	
	
	turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2);
	
	//�������ĳ����ˣ��޸ı���ɫΪ
	if (turnPage1.strQueryResult) {

		setMulLineColor(CustomerListGrid);
	}
}
//ΪMulLine������ɫ
function setMulLineColor(ObjGrid) {
	
	//ΪMulLine������ɫ
	for (var i=0;i<ObjGrid.mulLineCount;i++) {
		
		var tBlackFlag = ObjGrid.getRowColData(i, 18);
		
		if (tBlackFlag=="1") {
			ObjGrid.setColor(i, "#43FA67");
		}
	}	
}
/**
 * չʾѡ�еĿͻ���Ϣ
 */
function showSelectCustomer() {
			
	var i = CustomerListGrid.getSelNo()-1;
	var tRegisterNo = CustomerListGrid.getRowColData(i,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ��");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(i,2);
	
	fm.CustomerNo.value = tCustomerNo;
	fm.RegisterNo.value = tRegisterNo;
	fm.SelNo.value = CustomerListGrid.getSelNo();//add 2011-11-29
	fm.PageIndex.value = turnPage1.pageIndex;//add 2011-11-29
	fm.CaseNo.value = "";
	
	var tCaseType = fm.CaseType.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql3");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(mGrpRegisterNo);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
	if (tArr != null && tArr.length>0) {

		//�����ԭ�е�����
		initCustomerInfo();		
		initCaseInfo();
		initPayInfo();
		//initCaseReviewInfo();
		
		initOnEventDutyListGrid();
		initOffEventDutyListGrid();
		initCaseDutyPayGrid();
		
		fm.BankName.value = tArr[0][0];
		fm.BankProvince.value = tArr[0][1];	
		fm.BankCity.value = tArr[0][2];	
		fm.AccNo.value = tArr[0][3];	
		fm.AccName.value = tArr[0][4];
		fm.PayType.value = tArr[0][5];
		fm.OldClmNo.value = tArr[0][6];
		if (fm.PayType.value==null || fm.PayType.value=="" || fm.PayType.value=="01") {
			fm.PayType.value = "01";
			fm.PayType.checked = false;
		} else {
			fm.PayType.value = "00";
			fm.PayType.checked = true;
		}
	}
	
	queryLastClaimInfo();
	queryCustomerCaseList();
	
	//queryReviewInfo();
	//queryCheckInfo();
}
/**
 * ��ѯ��ά���Ŀͻ�����ʷ�⸶��Ϣ�б�
 */
function queryLastClaimInfo() {
	
	if(fm.RegisterNo.value=="" || fm.RegisterNo.value==null || fm.CustomerNo.value=="" || fm.CustomerNo.value==null) {
		return;
	}
	turnPage6.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql35");
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	
	turnPage6.queryModal(tSQLInfo.getString(),PayInfoListGrid,2,1);	
}
/**
 * ��ѯ��ά���Ŀͻ����¼���Ϣ�б�
 */
function queryCustomerCaseList() {
	
	if(fm.RegisterNo.value=="" || fm.RegisterNo.value==null || fm.CustomerNo.value=="" || fm.CustomerNo.value==null) {
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql5");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),AccidentListGrid,2);	
}
/**
 * ��ѯ�������
 */
//function queryReviewInfo() {
//	
//	//��ѯ�Ƿ��Ѵ����������������
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
//	tSQLInfo.setSqlId("LLClaimCaseReviewSql22");
//	tSQLInfo.addSubPara(fm.RegisterNo.value);
//	
//	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	if (tArr==null || tArr.length<=0) {
//		mReviewFlag = false;
//	} else {
//		mReviewFlag = true;
//	}
//	if (mReviewFlag) {
//
//		$("#ReviewAdvice").css("display","none");
//		$("#AgainReviewAdvice").css("display","");
//		fm.UWFlag.value = "AgainReview";		
//	} else {
//		$("#ReviewAdvice").css("display","");
//		$("#AgainReviewAdvice").css("display","none");
//		fm.UWFlag.value = "Review";	
//	}	
//}
/**
 * ��ѯ�������
 */
//function queryCheckInfo() {
//	
//	//��ѯ�Ƿ��Ѵ����������������
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
//	tSQLInfo.setSqlId("LLClaimCaseReviewSql34");
//	tSQLInfo.addSubPara(fm.RegisterNo.value);
//	tSQLInfo.addSubPara(fm.RegisterNo.value);
//	
//	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	if (tArr==null || tArr.length<=0) {
//		mCheckFlag = false;
//	} else {
//		mCheckFlag = true;
//	}
//	if (mCheckFlag) {
//
//		$("#divCheckConclusion").css("display","");
//		fm.CheckConclusion.value = tArr[0][0];
//		fm.CheckConclusionName.value = tArr[0][1];
//		fm.CheckAdvice.value = tArr[0][2];
//	} else {
//		
//		fm.CheckConclusion.value = "";
//		fm.CheckConclusionName.value = "";
//		fm.CheckAdvice.value = "";
//		$("#divCheckConclusion").css("display","none");		
//	}	
//}
/**
 * ��ѯѡ�еĿͻ����¼���ϸ��Ϣ
 */
function showSelectCase() {
	
	var i = AccidentListGrid.getSelNo()-1;
	var tCaseNo = AccidentListGrid.getRowColData(i,1);
	if (tCaseNo==null || tCaseNo=="") {
		alert("���Ȳ�ѯ��");
		return;
	}
	var tCaseState = AccidentListGrid.getRowColData(i,13);
	fm.CaseNo.value = tCaseNo;
	
	fm.SelNo.value = AccidentListGrid.getSelNo();//add 2011-11-29
	fm.PageIndex.value = turnPage2.pageIndex;//add 2011-11-29	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql6");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("δ��ѯ���¼���ϸ��Ϣ!");
		return false;
	}else {
		//�����ԭ�е�����
		initCaseInfo();
		
		fm.AccReason.value = tArr[0][0];
		fm.AccReasonName.value = tArr[0][1];
		fm.AccDate.value = tArr[0][2];
		fm.ClaimPay.value = tArr[0][3];
		fm.HospitalCode.value = tArr[0][4];
		fm.HospitalName.value = tArr[0][5];
		fm.AccResult1.value = tArr[0][6];
		fm.AccResult1Name.value = tArr[0][7];
		fm.AccResult2.value = tArr[0][8];
		fm.AccResult2Name.value = tArr[0][9];
		fm.DeformityDate.value = tArr[0][10];
		fm.DeathDate.value = tArr[0][11];
		fm.TreatResult.value = tArr[0][12];
		fm.TreatResultName.value = tArr[0][13];
		fm.CaseSource.value = tArr[0][14];
		fm.CaseSourceName.value = tArr[0][15];
		fm.LRCaseNo.value = tArr[0][16];

	/*	fm.ProvinceName.value = tArr[0][19];
		fm.Province.value = tArr[0][20];	
		fm.CityName.value = tArr[0][21];	
		fm.City.value = tArr[0][22];	
		fm.CountyName.value = tArr[0][23];
		fm.County.value = tArr[0][24];*/
		fm.AccidentPlace.value = tArr[0][19];
		fm.AccidentRemarks.value = tArr[0][20];
		fm.Remarks.value = tArr[0][21];
		var tCloseReason = tArr[0][17];
		if (tCloseReason!=null && tCloseReason!="") {
			
			var tCloseInfo = document.getElementById("divCloseAccident");
			tCloseInfo.style.display="";			
			fm.CloseReasonDesc.value = tArr[0][17];
			fm.CloseReasonDescName.value = tArr[0][18];		
			fm.CloseRemarkDesc.value = tArr[0][22];			
		}	
	}	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql7");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);	
	
	var tClaimType = easyExecSql(tSQLInfo.getString());
	
	if (tClaimType == null || tClaimType.length==0) {
		alert("����������������Ϣ�����ڣ�����ó������Ƿ���Ч!");
		return false;
	} else {
		
		for (var i=0;i<fm.ClaimType.length;i++) {
		  	
			fm.ClaimType[i].checked = false;
		}
		if (tClaimType!= '' && tClaimType !=null) {
		  	
			for (var j=0;j<fm.ClaimType.length;j++){
		     	
				for (var l=0;l<tClaimType.length;l++){
								
					var tClaim = tClaimType[l].toString();
					tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//ȡ�������ͺ���λ
					if(fm.ClaimType[j].value == tClaim){				        		
						fm.ClaimType[j].checked = true;
					}
				}
			}
		}
	}
	
	initPayInfo();
	
	queryCaseDutyInfo();
	//��ѯ�⸶��Ϣ
	queryClaimInfo();
}
/**
 * ��ѯ�¼�������Ϣ
 */
function queryCaseDutyInfo() {
	
	turnPage3.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql8");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara("1");
	turnPage3.queryModal(tSQLInfo.getString(),OnEventDutyListGrid,2,1);
	
	turnPage4.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql8");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara("0");
	turnPage4.queryModal(tSQLInfo.getString(),OffEventDutyListGrid,2,1);		
	
}
/**
 * ��ѯ�⸶��Ϣ
 */
function queryClaimInfo() {
	
	turnPage5.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql16");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	turnPage5.queryModal(tSQLInfo.getString(),CaseDutyPayGrid,2,1);	
}
/**
 * ȫ������
 */
function CalPayAll() {
	
	if (fm.PayType.checked) {
		fm.PayType.value = "00";
	} else {
		fm.PayType.value = "01";
	}
	fm.PublicFlag.value = "1";
	
	fm.Operate.value = "G";
	submitForm1();
}
/**
 * δ����ͻ�
 */
function noAcceptInfo() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("���ȱ�������������Ϣ");
		return false;
	}
	var strUrl="./LLClaimNoAcceptMain.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";
	window.open(strUrl,"δ����ͻ�",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ����ȡ����������
 */
function batchNoPay() {
	
	var strUrl="./LLClaimNoPayMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value;
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		
}

/**
 * ������ѯ
 */
function showInsuredLCPol() {	
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������");
		return false;
	}	
	var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1&RgtNo="+fm.RegisterNo.value;
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ��ȫ��ѯ
 */
function showInsuredLCEdor() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������");
		return false;
	}	
	var strUrl="./LLClaimQueryEdorMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * �����ⰸ��ѯ
 */
function showOldCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������");
		return false;
	}	
	var strUrl="./LLClaimLastCaseMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * Ӱ�����ѯ
 */
function queryEasyscan() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRgtNo =CustomerListGrid.getRowColData(tSelNo,1);
	window.open("../easyscan/ScanPagesQueryMainInput.jsp?BussType=G_CM&BussNo="+tRgtNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * BPOУ������ѯ
 */
function BPOCheck() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	
	var strUrl="./LLClaimBPOcheckMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=1200;
	var tHeight=500;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"BPOУ������ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * �籣����Ϣ��ѯ
 */
function querySecurity() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	
	var strUrl="./LLClaimQuerySocialInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�籣����Ϣ��ѯ",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	
}

/**
 * ��ѯ�б�ʱ¼��������˻���Ϣ
 */
function queryAccInfo() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}	
	var strUrl="./LLClaimQueryAccInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&Mode=1" ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"��ѯ�б�ʱ¼��������˻���Ϣ",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
}

/**
 * ����
 */
function calPay() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	
	if (fm.PayType.checked) {
		fm.PayType.value = "00";
	} else {
		fm.PayType.value = "01";
	}
	
	tPageNo = turnPage1.pageIndex;
	tIndexNo = tSelNo+1;	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;	
	
	fm.Operate.value = "P";	
	
	var tRegisterNo = CustomerListGrid.getRowColData(tSelNo,1);
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);	
	//��ͣ�����������ȷ��
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql29");
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(tCustomerNo);
	var tRgtStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tRgtStateArr!=null && tRgtStateArr.length>0) {
			alert("�ø��˰�������ͣ���������㣡");
			return false;
	}		
	submitForm1();
}
/**
 * ���ȫ���˵�
 */
function reviewClmBills() {

	if(fm.CaseType.value="02"){
		
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}		
		
		var tRgtNo = fm.RegisterNo.value;
		var tCaseNo = "";
		if(tRgtNo == ''){
			alert("�ⰸ��Ϊ�գ�");
			return false;
		}
/*		if(tCaseNo == ''){
			alert("�¼���Ϊ�գ�");
			return false;
		}*/
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql9");
	tSQLInfo.addSubPara(tRgtNo);
	var tArr = easyExecSql(tSQLInfo.getString());
	var busson = tRgtNo;
	var tBussType = "G_CM";
	var tSubType = "23003";
 	
 	if(tArr== null || tArr == "") {
 		
 	}else{
	 	busson = tArr[0][0];
		tBussType = tArr[0][1];
		tSubType = tArr[0][2];
 	}
 	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql10");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara("");
	
	var Result = easyExecSql(tSQLInfo.getString());
	var FeeType = "";
	if(Result != null && Result != ""){
		
		var len = Result.length;
		for(var i=0;i<len;i++){
			if(i == 0){
				FeeType = Result[i][0];
				
			}else {
				FeeType += "-" + Result[i][0];
			}
		}
	}
	if(FeeType == ""){
		alert("��ȡ�˵�����ʧ�ܣ�");
		return false;
	}

 var strUrl="./LLClaimHealthBillESMain.jsp?RgtNo="+ tRgtNo+"&CaseNo="+tCaseNo
  +"&BussNo="+busson+"&BussType="+tBussType+"&SubType=23003"+"&FeeType="+FeeType+"&Flag=0&Mode="+mMode;
 	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"���ȫ���˵�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}
/**
 * ��������Ϣ
 */
function benefitInfo() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);

	var strUrl="./LLClaimBenefitMain.jsp?GrpRgtNo="+mGrpRegisterNo+"&RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo;
	
	window.open(strUrl,"������",'width=1000,height=480,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * ����/��ͣ����
 */
function stopORstartCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);	
	var strUrl="./LLClaimSwitchMain.jsp?RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo;
	var tWidth=1100;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"������",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ��������
 */
function reportRelate() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);	
	var strUrl="./LLClaimAssReportMain.jsp?RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo+"&Mode="+mMode;
	var tWidth=1200;
	var tHeight=500;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"��������",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * �������
 */
function showSurvey() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo =CustomerListGrid.getRowColData(tSelNo,1);
	var tInsuredNo = CustomerListGrid.getRowColData(tSelNo,2);
	var tVistFlag = "01";//01 ��ʾ �ⰸ����
	var tManageCom =fm.AcceptCom.value;//�������
	var tAppntNo=fm.AppntNo.value;//Ͷ���˱���	
	if(tGrpRgtNo == "" || tGrpRgtNo == null){
		
		alert("���κ�Ϊ�գ���˲���Ϣ��");
		return false;
    }
   if(tInsuredNo == "" || tInsuredNo == null) {
    
    alert("�����˱���Ϊ�գ���˲���Ϣ��");
		return false;
  	}
		
		var strUrl="LLClaimPreinvestInputMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&custNo="+tInsuredNo+"&surveyType=01&initPhase=02&AppntNo="+tAppntNo+"&ManageCom="+tManageCom+"&Mode="+mMode;

		window.open(strUrl,"�������",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �������ѯ
 */
function question() {
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	/*
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql15");
	tSQLInfo.addSubPara(tRgtNo);
	
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	if(arr != null && arr != ""){
		
		alert("����δ����������������BPO���ݶԽӷ�ȷ��������Ƿ��Ѿ��������,���ύȷ�ϣ�");
		return false;
	}*/
	
	var strUrl="./LLClaimQuestionMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=1200;
	var tHeight=500;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�����",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ����������
 */
function blackList() {
	
	var strUrl="./LLClaimBlackListMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&Mode="+mMode+"&ClmState=30";
	var tWidth=1100;
	var tHeight=700;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"������",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * �����¼�
 */
function addAccident() {
	
	fm.Operate.value="CASEINSERT";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * �޸��¼�
 */
function modifyAccident() {
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���¼���Ϣ");
		return false;
	}
	var tRegisterNo = AccidentListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	
	tPageNo = turnPage2.pageIndex;
	tIndexNo = tSelNo+1;	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;	
	
	fm.Operate.value="CASEUPDATE";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * ɾ���¼�
 */
function deleteAccident() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���������¼���Ϣ");
		return false;
	}
	var tRegisterNo = AccidentListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ�������¼���Ϣ��");
		return;
	}
	if (confirm("ȷ��ɾ�������¼���")) {
		fm.Operate.value="CASEDELETE";
		submitForm();		
	}	
}

/**
 * У���¼���Ϣ
 */
function checkCaseInfo() {
	
	if(!notNull(fm.AccReason,"����ԭ��")){return false;};
	if(!notNull(fm.AccDate,"��������")){return false;};
	if(!notNull(fm.ClaimPay,"������")){return false;};
	if(!notNull(fm.AccResult1,"��Ҫ���")){return false;};
	if(!notNull(fm.TreatResult,"���ƽ��")){return false;};
	if(!notNull(fm.CaseSource,"�¼���Դ")){return false;};
	
	/**
	if(!notNull(fm.Province,"���յص�[ʡ]")){return false;};
	if(!notNull(fm.City,"���յص�[��]")){return false;};
	if(!notNull(fm.County,"���յص�[��/��]")){return false;};
	*/
	if(!notNull(fm.AccidentPlace,"���յص�")){return false;};	
	
	var tGrpRegisterNo = fm.GrpRgtNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	var tRegisterNo = fm.RegisterNo.value;
	var tAccDate = fm.AccDate.value;
	var tAppntNo = fm.AppntNo.value;
	var tHospitalCode = fm.HospitalCode.value;
	var tHospitalName = fm.HospitalName.value;
	var tAccidentType = fm.AccReason.value;
	
	//��������ϢУ��
	if (tCustomerNo==null || tCustomerNo=="") {
		
		alert("����ѡ��һ���ͻ���Ϣ��");
		return false;
	}
	
	//У��������Ƿ����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql11");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(tGrpRegisterNo);
	
	var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistArr==null || tExistArr.length<=0) {
		
		alert("�ó����˲��ڴ˴������£��������!");
		return false;
	}
	
	//�޸ĳ�������ʱ����ҪУ���˵�������
	if(fm.CaseType.value!="02" && fm.Operate.value=="CASEUPDATE"){

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql26");
		tSQLInfo.addSubPara(tRegisterNo);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		var Result = easyExecSql(tSQLInfo.getString());
		if(Result != null && Result != ""){
		
		var len = Result.length;
		
			for(var i=0;i<len;i++){
				var tStateDate=Result[i][0];
				if(dateDiff(tAccDate,tStateDate,'D')< 0 ){
						alert("�������ڲ��������˵����ڣ�");
						return false;
				}
			}
		}
	}else if(fm.CaseType.value=="02" && fm.Operate.value=="CASEUPDATE"){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql27");
		tSQLInfo.addSubPara(tRegisterNo);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		var Result = easyExecSql(tSQLInfo.getString());
		if(Result != null && Result != ""){
			var len = Result.length;
			for(var i=0;i<len;i++){
				var tStateDate=Result[i][0];
				if(dateDiff(tAccDate,tStateDate,'D')< 0){
						alert("�������ڲ��������˵����ڣ�");
						return false;
				}
			}
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql28");
		tSQLInfo.addSubPara(tRegisterNo);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		var Result1 = easyExecSql(tSQLInfo.getString());
		if(Result1 != null && Result1 != ""){
				
			var len = Result1.length;
			for(var i=0;i<len;i++){
				var tStateDate=Result1[i][0];
				if(dateDiff(tAccDate,tStateDate,'D')< 0 ){
						alert("�������ڲ��������˵����ڣ�");
						return false;
				}
			}
		}
	}
	
	
/*	//���������б���������������������죩 ����������
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql8");
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length<=0) {
		
		alert("�ó������Ѿ��������������������������!");
		return false;
	}*/
/*
	//�˱������ղ��������⣬�˱���ͨ���ǿ��������
	var tPGSQL = "select count(1) from LPEdorItem where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and edortype='CT'";
	var tEdorState = easyExecSql(tPGSQL);
	
	var tCSQL = "select count(*) from lmriskapp where "
	         + "riskcode in (select riskcode From lcpol where "
	         + "grpcontno = '"+ tGrpContNo +"' and insuredno = '"+ tCustomerNo +"' and contno='"+ tContNo +"') "
	         + "and RiskPeriod = 'L'";
	var tCount = easyExecSql(tCSQL);
	
	if (tEdorState>0 && tCount>0 ) {
		alert("�ó�������δȷ�ϵı�ȫ���Ѿ��˱�����ȷ�Ϻ�����������");
		return false;
	}	
*/					

/*
		var tSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+ tAccidentDate +"'";
				tSQLBQ = tSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+ tCustomerNo +"' and a.contno='"+ tContNo +"'";
				tSQLBQ = tSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";
		if (tAppntNo!=null) {
			
				tSQLBQ = tSQLBQ+" and b.AppntNo='"+ tAppntNo +"'";
		}
		if (tGrpContNo!=null) {
				tSQLBQ = tSQLBQ+" and b.GrpContNo='"+ tGrpContNo +"'";
		}
		var tArrBQ = easyExecSql(tSQLBQ);
		if ( tArrBQ != null ) {

				alert("���ؾ��棺�ñ��������������ܸ��ı���ı�ȫ��");   
		}
*/		
	//��������У��
	if (dateDiff(tAccDate,mCurrentDate,'D')<0) {	
		
		alert("�������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	
	//�������У��
	var tDeathDate = fm.DeathDate.value;
	if (tDeathDate!=null && tDeathDate!="") {
		
		if (dateDiff(tDeathDate,mCurrentDate,'D')<0) {
			
			alert("������ڲ������ڵ�ǰ���ڣ�");			
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;
		}
		if (dateDiff(tDeathDate,tAccDate,'D')>0) {		
			
			alert("������ڲ������ڳ������ڣ�");
			
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";			
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDeathDate,'D')>180) {
				
				if (confirm("���������������ڵļ������180���Ƿ������")) {
									
				} else {
					
					return false;
				}
			}
		}
	}
	
	//�˲�/ȫ������У��
	var tDianoseDate = fm.DeformityDate.value;
	if (tDianoseDate!=null && tDianoseDate!="") {
		
		if (dateDiff(tDianoseDate,mCurrentDate,'D')<0) {		
			
			alert("�˲�/ȫ�����ڲ������ڵ�ǰ���ڣ�");
			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";			
			return false;
		}
		if (dateDiff(tDianoseDate,tAccDate,'D')>0) {
			
			alert("�˲�/ȫ�����ڲ������ڳ������ڣ�");
			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";				
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDianoseDate,'D')>180) {
				
				if (confirm("�˲�������������ڵļ������180���Ƿ������")) {
									
				} else {
					
					return false;
				}
			}
		}				
		var tDeadCheck = fm.ClaimType[0].checked;//У���������Ϊ��ʵĶ�ѡ���Ƿ�ѡ
		if (!tDeadCheck && tDeathDate!="") {
			
			alert("��¼���ˡ�������ڡ����빴ѡ��������--����ʡ���");
			return false;
		}
		var tMaim = fm.ClaimType[2].checked;//У���������Ϊ�˲еĶ�ѡ���Ƿ�ѡ
		if (!tMaim && tDianoseDate!="") {
			
			alert("��¼���ˡ��˲�/ȫ�����ڡ����빴ѡ��������--���˲С���");
			return false;
		}		

	}	
	var tAllGetClaimType = "";
	//У���������
	
	var tClaimType = new Array;	
	var tDeadFalg = fm.ClaimType[0].checked;//У���Ƿ�ѡ��ʶ�ѡ��
	var tMedicalFlag = fm.ClaimType[3].checked;//�Ƿ�ѡ��������--ҽ��
	var tMaimFalg = fm.ClaimType[2].checked;//�Ƿ�ѡ��������--�˲�
	

	
	//��������
	for (var j=0;j<fm.ClaimType.length;j++) {
  	
		if (fm.ClaimType[j].checked == true) {
      	
			tClaimType[j] = fm.ClaimType[j].value;
			tAllGetClaimType = tAllGetClaimType + "'" + tAccidentType + "" + tClaimType[j] + "',";
		}
	}
	if (tClaimType==null || tClaimType=="") {

		alert("��ѡ��������ͣ�");
		return false;
	}
	tAllGetClaimType = tAllGetClaimType.substr(0,tAllGetClaimType.length-1);
//  var tRowNum = RiskDutyGrid.mulLineCount;
  /*
	for (var i=0;i<tRowNum;i++) {
	
		var tRiskCode = RiskDutyGrid.getRowColData(i,2);
		var tDutyCode = RiskDutyGrid.getRowColData(i,4);
		var tDutyName = RiskDutyGrid.getRowColData(i,5);
		
		var tGetDutyKindSQL = "select 1 from lmdutygetrela a, lmdutygetclm b where a.getdutycode = b.getdutycode  and a.dutycode = '"+ tDutyCode +"'  and b.getdutykind in ("+ tAllGetClaimType +")";
		var tGetDutyKindResult = easyExecSql(tGetDutyKindSQL);
		if(tGetDutyKindResult==null){
			
			alert("��¼��ġ�"+ tDutyName +"��,�������������Ӧ�ĳ���ԭ����������ͣ�");
			return false;
		}	
	}
	*/
	if (tDeadFalg) {
		
		if (tDeathDate==null || tDeathDate=="") {
			
			alert("��������Ϊ��ʣ���¼�롾������ڡ���");			
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;
		}
	}
	if (tMaimFalg) {
		
		if (tDianoseDate==null || tDianoseDate=="") {
			
			alert("��������Ϊ�˲У���¼�롾�˲�/ȫ�����ڡ���");
			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";			
			return false;
		}
	}	
	
	//У������ҽԺ��ҽԺ�����Ƿ�ƥ��
		/**
	if (tHospitalCode!=null && tHospitalCode!="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql9");
		tSQLInfo.addSubPara(tHospitalCode);
		tSQLInfo.addSubPara(tHospitalName);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tValidArr==null || tValidArr.length<=0) {
			
			alert("ҽԺ������ҽԺ���벻��Ӧ�����飡");
			return false;
		}
	}**/

	if (fm.Operate.value=="CASEUPDATE") {
		
		//ҽ����--У���������Ƿ���ڸ��¼������������˵���ԭʼ���֮��
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql30");
		tSQLInfo.addSubPara(fm.RegisterNo.value);
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		var tOriaSum = 0;
		if (tValidArr==null || tValidArr.length<=0) {
			
			tOriaSum=0;		
		} else {
			tOriaSum = tValidArr[0][0];
		}
		var tAppMoney = fm.ClaimPay.value;
		if (tAppMoney-tOriaSum<0) {
			alert("�������С�ڸ��¼����˵�ԭʼ���֮��");		
			return false;
		}		
	}		
	
	return true;	
}

/**
 * �ر��¼�
 */
function closeAccident() {
	
	if (mFlag!=null && mFlag=="CLOSE") {
		saveCloseDesc();
	} else {
		
		var tSelNo = AccidentListGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("��ѡ��һ���������¼���Ϣ");
			return false;
		}
		
		var tCloseInfo = document.getElementById("divCloseAccident");
		tCloseInfo.style.display="";
		fm.CloseReasonDesc.value = "";
		fm.CloseReasonDescName.value = "";
		fm.CloseRemarkDesc.value = "";
		mFlag = "CLOSE";
	}
	
}
/**
 * ����ر��¼���ϸ
 */
function saveCloseDesc() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���������¼���Ϣ");
		return false;
	}
	var tState = AccidentListGrid.getRowColData(tSelNo,13);
	if (tState=="1") {
		alert("���¼��ѹرգ��������ظ�������");
		return false;
	}
	

	if(!notNull(fm.CloseReasonDesc,"�ر��¼�ԭ��")){return false;};
	if(!notNull(fm.CloseRemarkDesc,"�ر��¼���ע")){return false;};
	
	fm.Operate.value="CASECLOSE";
	submitForm();
}

/**
 * �����¼�
 */
function openAccident() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���������¼���Ϣ");
		return false;
	}
	
	var tState = AccidentListGrid.getRowColData(tSelNo,13);
	if (tState=="0") {
		alert("���¼�δ�رգ�����Ҫ������");
		return false;
	}	
	
	fm.Operate.value="CASEOPEN";
	submitForm();	
}
/**
 * �ϲ��¼�
 */
function combineAccident() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ�������˵��¼���Ϣ");
		return false;
	}
	var tState = AccidentListGrid.getRowColData(tSelNo,13);
	if (tState=="1") {
		alert("���¼��ѹرգ����ܺϲ���");
		return false;
	}	
	
	var strUrl="./LLClaimMergeMain.jsp?RgtNo="+fm.RegisterNo.value+"&CustomerNo="+fm.CustomerNo.value+"&CaseNo="+fm.CaseNo.value;
	var tWidth=1200;
	var tHeight=500;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;
	window.open(strUrl,"������ת�Ų�ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ��ҽ���˵�
 */
function noMedicalBill() {
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���¼���Ϣ");
		return false;
	}
	var strUrl="./LLClaimNoMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&AccidentDate="+fm.AccDate.value+"&Mode="+mMode;
	
	var tWidth=window.screen.availWidth;
	var tHeight=500;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"��ҽ���˵�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
/**
 * ҽ���˵�
 */
function MedicalBill() {
	
	//�Ȼ�ȡӰ���
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql9");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	var tArr = easyExecSql(tSQLInfo.getString());
	var busson = fm.RegisterNo.value;
	var tBussType = "G_CM";
	var tSubType = "23003";
 	
 	if(tArr== null || tArr == "") {
 		
 	}else{
	 	busson = tArr[0][0];
		tBussType = tArr[0][1];
		tSubType = tArr[0][2];
 	}
/*	//�������--�����˵���ʷ�ⰸΪ�������
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql33");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	var tArr = easyExecSql(tSQLInfo.getString());
	var tOldCaseType="";
	if (tArr!=null && tArr.length>0) {
		tOldCaseType = tArr[0][0];
	}*/
	if(fm.CaseType.value=="02" ){
		
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���¼���Ϣ");
		return false;
	}
		var tRgtNo = fm.RegisterNo.value;
		var tCaseNo = fm.CaseNo.value;
		if(tRgtNo == ''){
			alert("�ⰸ��Ϊ�գ�");
			return false;
		}


 	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql10");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCaseNo);
	
	var Result = easyExecSql(tSQLInfo.getString());
	var FeeType = "";
	if(Result != null && Result != ""){
		
		var len = Result.length;
		for(var i=0;i<len;i++){
			if(i == 0){
				FeeType = Result[i][0];
				
			}else {
				FeeType += "-" + Result[i][0];
			}
		}
	}else{
		alert("���¼���"+tCaseNo+"��û�й����˵���");
		return false;
	}
	if(FeeType == ""){
		alert("��ȡ�˵�����ʧ�ܣ�");
		return false;
	}

	var strUrl="./LLClaimHealthBillESMain.jsp?RgtNo="+ tRgtNo+"&CaseNo="+tCaseNo
  +"&BussNo="+busson+"&BussType="+tBussType+"&SubType="+tSubType+"&FeeType="+FeeType+"&Mode="+mMode;
 	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"����˵�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (fm.CaseType.value=="01"||fm.CaseType.value=="11"
		|| fm.CaseType.value=="12" || fm.CaseType.value=="13"){
	
		var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	
	var tCaeSelNo=AccidentListGrid.getSelNo()-1;
	var CaseSource="";
	if(tCaeSelNo<0){
		CaseSource="02";//�˵�����
	}else{
		
		CaseSource=fm.CaseSource.value;
	}
	
	var strUrl="./LLClaimMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&CaseSource="+CaseSource+"&Mode="+mMode+"&BussNo="+busson+"&BussType="+tBussType+"&SubType="+tSubType;

	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"ҽ���˵�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	}
	
}
/**
 * ȡ���¼����ι���
 */
function offAssociate() {
	
	var rowNum = OnEventDutyListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(OnEventDutyListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("������ѡ��һ���¼�������Ϣ");
		return false;
	}

/*		
	var tSelNo = OnEventDutyListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���������¼��ѹ���������Ϣ");
		return false;
	}
	fm.PolNo.value = OnEventDutyListGrid.getRowColData(tSelNo,2);
	fm.DutyCode.value = OnEventDutyListGrid.getRowColData(tSelNo,5);
	fm.GetDutyCode.value = OnEventDutyListGrid.getRowColData(tSelNo,7);
*/
	fm.Operate.value="DUTYOFF";
	submitForm();		
}

/**
 * �����¼�����
 */
function onAssociate() {
	
	var rowNum = OffEventDutyListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(OffEventDutyListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("������ѡ��һ���¼�������Ϣ");
		return false;
	}
		
	fm.Operate.value="DUTYON";
	submitForm();		
}
/**
 * չʾ���۵�����Ϣ
 */
function showAjustInfo() {
	
	
	var tSelNo = CaseDutyPayGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���¼��⸶��Ϣ");
		return false;
	}
	
	fm.PolNo.value = CaseDutyPayGrid.getRowColData(tSelNo,2);	
	fm.DutyCode.value = CaseDutyPayGrid.getRowColData(tSelNo,5);
	fm.GetDutyCode.value = CaseDutyPayGrid.getRowColData(tSelNo,7);
	fm.GetDutyKind.value = CaseDutyPayGrid.getRowColData(tSelNo,9);
	
	tPayRuleFlag = false;
	$("#divPayModify").css("display","none");
	var tRiskCode = CaseDutyPayGrid.getRowColData(tSelNo,3);
	var tGrpContNo = CaseDutyPayGrid.getRowColData(tSelNo,1);
	
	//�ж��Ƿ���ڶ�Ӧ�����޸Ĺ�������������޸�
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql37");
	tSQLInfo.addSubPara(tRiskCode);
	tSQLInfo.addSubPara(fm.AccDate.value);
	tSQLInfo.addSubPara(fm.AccDate.value);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.PolNo.value);
	tSQLInfo.addSubPara(fm.DutyCode.value);
	
	var tPayRulePlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPayRulePlanArr!=null && tPayRulePlanArr.length>0) {
		tPayRuleFlag = true;
	} else {
		
		//�ж��Ƿ���ڶ�Ӧ�����޸Ĺ�������������޸�
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql38");
		tSQLInfo.addSubPara(tRiskCode);
		tSQLInfo.addSubPara(fm.AccDate.value);
		tSQLInfo.addSubPara(fm.AccDate.value);
		tSQLInfo.addSubPara(tGrpContNo);
		
		var tPayRuleContArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tPayRuleContArr!=null && tPayRuleContArr.length>0) {
			tPayRuleFlag = true;
		} else {
			
			//�ж��Ƿ���ڶ�Ӧ�����޸Ĺ�������������޸�
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
			tSQLInfo.setSqlId("LLClaimCaseReviewSql39");
			tSQLInfo.addSubPara(tRiskCode);
			tSQLInfo.addSubPara(fm.AccDate.value);
			tSQLInfo.addSubPara(fm.AccDate.value);
			
			var tPayRuleRiskArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPayRuleRiskArr!=null && tPayRuleRiskArr.length>0) {
				tPayRuleFlag = true;
			} else {
				tPayRuleFlag = false;
			}
		}				
	}	
	if (fm.CaseType.value=="11" || fm.CaseType.value=="12" || fm.CaseType.value=="13" || tPayRuleFlag) {
		
		$("#divPayModify").css("display","");
		if (tPayRuleFlag && (fm.CaseType.value=="01" || fm.CaseType.value=="02")) {
			conditionCode1 = " 1 and code=#0# ";
		} else {
			conditionCode1 = "1";
		}
	
		//��ѯ�����⸶������Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql17");
		tSQLInfo.addSubPara(fm.RegisterNo.value);
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		tSQLInfo.addSubPara(fm.PolNo.value);
		tSQLInfo.addSubPara(fm.DutyCode.value);
		tSQLInfo.addSubPara(fm.GetDutyCode.value);
		tSQLInfo.addSubPara(fm.GetDutyKind.value);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tValidArr==null || tValidArr.length<=0) {
			
			alert("δ��ѯ���⸶��Ϣ��");
			return false;
		} else {
			initPayInfo();
	
			fm.GiveType.value = tValidArr[0][0];		
			fm.GiveTypeName.value = tValidArr[0][1];
			
			fm.AdjReason.value = tValidArr[0][2];
			fm.AdjReasonName.value = tValidArr[0][3];
			fm.RealPay.value = tValidArr[0][4];
						
			fm.NoGiveReason.value = tValidArr[0][6];
			fm.NoGiveReasonName.value = tValidArr[0][7];
			fm.SpecialRemark.value = tValidArr[0][8];			
			if (fm.GiveType.value=="0") {
	
				fm.AdjRemark.value = tValidArr[0][5];
				$("#AdjustInfo").css("display","");				
				//showCodeList('llcommendhospital', [objHospCode, objHospName], [0,1], null, condition , 'HospitalName', 1, '400');				
				$("#NoPayInfo").css("display","none");
						
			} else {
				fm.AdjRemark.value = tValidArr[0][9];	
				$("#AdjustInfo").css("display","none");
				$("#NoPayInfo").css("display","");
				
			}
		}
	}
}

/**
 * չʾ������Ϣ
 */
function showSelectMod(objAdjReason,objAdjReasonName) {
	
	if ((fm.CaseType.value=="01" || fm.CaseType.value=="02") && tPayRuleFlag) {
		showCodeList('queryclmreasontype', [objAdjReason, objAdjReasonName], [0,1], null, null , null, 1, '400');
	} else {
		showCodeList('adjreason', [objAdjReason, objAdjReasonName], [0,1], null, null , null, 1, '400');
	}
}

/**
 * �����⸶������Ϣ
 */
function savePayInfo() {
	
	if(!notNull(fm.GiveType,"�⸶����")){return false;};
	var tGiveType = fm.GiveType.value;
	if (tGiveType=="0") {
		
		if(!notNull(fm.AdjReason,"����ԭ��")){return false;};
		if(!notNull(fm.RealPay,"�������")){return false;};

		var tAdjustMoney = fm.RealPay.value;
		var tClaimPay = fm.ClaimPay.value;

/*		if (tClaimPay-tAdjustMoney<0) {
			alert("�������ô��������");
			fm.RealPay.focus();
			fm.RealPay.style.background='#ffb900';
			return false;
		}*/
	
		
	} else if (tGiveType=="1") {
		
		if(!notNull(fm.NoGiveReason,"�ܸ�ԭ��")){return false;};
		if(!notNull(fm.SpecialRemark,"���ⱸע")){return false;};		
	} else {		
		alert("������������ڣ�������ѡ��");
		return false;
	}
	if(!notNull(fm.AdjRemark,"�⸶����˵��")){return false;};
	fm.Operate.value = "SAVEPAY";
	submitForm();	
}
/**
 * ����
 */
function goToBack() {
	
	if (mMode==1) {
		top.close();
	} else {
		window.location.href="./LLClaimCaseReviewAppInput.jsp";
	}	
}
/**
 * ���ȷ��
 */
function caseConfirm() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ����������Ϣ");
		return false;
	}
	if(!notNull(fm.Conclusion,"��˽���")){return false;};
	if (mReviewFlag) {
		if(!notNull(fm.AgainReviewAdvice,"�������")){return false;};
	} else {
		if(!notNull(fm.ReviewAdvice,"������")){return false;};
	}	
	
	var tConclusion = fm.Conclusion.value ;
	var tRgtNo = fm.RegisterNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	
	//��ͣ�����������ȷ��
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql29");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
	var tRgtStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tRgtStateArr!=null && tRgtStateArr.length>0) {
			alert("�ø��˰�������ͣ���������ȷ�ϣ�");
			return false;
	}		
	
	if (tConclusion=="1" || tConclusion=="2") {
		
		//��ѯ�Ƿ���й�����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql18");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);	
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length<=0) {
			alert("���Ƚ�������");
			return false;
		}		
	}	
	
	//У����������Ƿ����δ��ɵ������
	var tCaseType = fm.CaseType.value;
	if (tCaseType!=null && tCaseType=="02") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql21");
		tSQLInfo.addSubPara(tRgtNo);
		var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistArr!=null && tExistArr.lengt>=1) {
			alert("�ó����˴���δ��ɵ�����������Ȼظ��������");
			return false;
		}
		
		//�����˵��޸ĵ��ⰸ�����ύ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql36");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(tRgtNo);
		var tExistBillArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistBillArr!=null && tExistBillArr.lengt>=1) {
			alert("�ó����˴��ڴ����˵��޸ĸڵ��˵������޸���Ϻ��ټ���������");
			return false;
		}
	}	
	
	//���˰������Ƿ�Ŵ��ڴ�������¼�--�˵����ɵ��¼�Ϊ�������¼�
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql23");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
	var tStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tStateArr!=null && tStateArr.lengt>=1) {
		alert("�ó����˴��ڴ�������¼������Ȳ����¼���Ϣ��");
		return false;
	}		
		
	//�����Ƿ���δ��ɵĵ���
	if(!checkQueryInq()){
		return false;
	}	
	
	/*********************************
	//У����������������еı���ܸ����򲻿����´��������
	
	if (tConclusion=="1") {
		
			//У���Ƿ���ڸ�������
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("claim.LLClaimCaseReviewSql");
			tSQLInfo.setSqlId("LLClaimCaseReviewSql19");
			tSQLInfo.addSubPara(tRgtNo);
			tSQLInfo.addSubPara(tCustomerNo);	
			
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null || tArr.length<=0) {
				alert("�ó������µ����б�����⸶����Ϊ���ܸ����������´�[����]���ۣ�");
				return false;
			}					
		}
	*********************************/
		
	if (tConclusion=="1" && (fm.CaseType.value=="01" || fm.CaseType.value=="02")) {
					
		//У��ҽ�Ƶ�ʵ�����ô����˵�ԭʼ���--
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql20");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);	
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length<=0) {			
			
		} else {
					
			for (var i=0;i<tArr.length;i++) {
				
				var tAppMoney = tArr[i][0];
				var tRealPay = tArr[i][1];
				var tSelCaseNo = tArr[i][2];
				if (tAppMoney-tRealPay<0) {
					alert("�ó������£��¼�["+tSelCaseNo+"]ʵ�����ô����˵�ԭʼ�����飡");				
					return false;
				}
			}
		}		
	}
	
	//У���Ƿ���Ҫ�ͻ���Ϣʶ��
/*	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql32");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);	
	
	var tCheckArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCheckArr!=null && tCheckArr.length>0) {
		
		alert("�ó������⸶������10000����Ҫ���пͻ���Ϣʶ�����Ƚ��пͻ���Ϣʶ��¼�룡");		
		return false;
	}*/
	
	fm.Operate.value = "REVIEWCONFIRM";	
	submitForm();
}
/**
 * �����ύ����
 */
function batchCaseConfirm() {
	
	fm.Operate.value = "ALLCONFIRM";
	if(!notNull(fm.Conclusion,"��˽���")){return false;};
	
	if (fm.Conclusion.value=='3') {
		
		alert("��˽���Ϊ[�ر�]�������������ύ");
		return false;
	}
	submitForm();
}
/**
 * �ͻ���Ϣʶ��ť
 */
function checkInsured() {			
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql32");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara("");	
	
	var tCheckArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCheckArr==null || tCheckArr.length<=0) {
		
		alert("�������²�������Ҫ�ͻ���Ϣʶ��ĳ����ˣ�");		
		return false;
	}	
	
	var strUrl="../compliance/AmlPerMain.jsp?BussType=CM&BussNo="+mGrpRegisterNo+"&ActivityID=1800501005" ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ͻ���Ϣʶ��",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');			
}

/**
 * У���Ƿ����δ��ɵĵ���
 */
function checkQueryInq() {
			
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql25");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistArr!=null) {
		
		var tInqState = tExistArr[0][0];
		if (tInqState!="06" && tInqState!="07") {
			alert("�ó����˴���δ��ɵĵ��飬������ɵ��飡");
			return false;			
		} else {
			return true;
		}
	} else {
		return true;
	}
}

/**
 * �رհ�����ͣ������ҳ������
 */
function afterSwitchCase() {

	initCustomerInfo();
	queryCustomerList();
	setSelRow(CustomerListGrid,turnPage1);
	showSelectCustomer();
}

/**
 * �ύ����
 */
function submitForm() {
	tSubmitNum=tSubmitNum+1;
	if (tSubmitNum>1) {
		return false;
	}
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	fm.submit();
}

/**
 * �ύ����
 */
function submitForm1() {
	
	tSubmitNum=tSubmitNum+1;
	if (tSubmitNum>1) {
		return false;
	}
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./LLClaimCalPortalSave.jsp";
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	tSubmitNum=0;
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();
	} else {
		
		if (fm.Operate.value!="CALINFODELETE") {
			
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
			queryCustomerCaseList();
			showInfo.focus();
		}

		if ((fm.Operate.value).indexOf("CASE")>=0) {

			initCaseInfo();				
			queryCustomerCaseList();
			
			initOnEventDutyListGrid();
			initOffEventDutyListGrid();
			initCaseDutyPayGrid();
			if (fm.Operate.value=="CASEUPDATE"||fm.Operate.value=="CASECLOSE"||fm.Operate.value=="CASEOPEN") {
				
				setSelRow(AccidentListGrid,turnPage2);
				showSelectCase();					
			}		
		} else if ((fm.Operate.value).indexOf("DUTY")>=0) {
				queryCustomerCaseList();
				queryCaseDutyInfo();
				if(fm.Operate.value=="DUTYON"||fm.Operate.value=="DUTYOFF"){
				
					setSelRow(AccidentListGrid,turnPage2);
					showSelectCase();		
				}
		} else if (fm.Operate.value=="SAVEPAY") {
			
			initPayInfo();
			queryCustomerList();
			//queryClaimInfo();
			queryCustomerCaseList();
			setSelRow(AccidentListGrid,turnPage2);
			showSelectCase();
			
		} else if (fm.Operate.value=="G") {
			
			initAcceptInfo();
			queryAcceptInfo();
			initCustomerInfo();
			queryCustomerList();
			
			//У���Ƿ�ʹ���˹���������ǣ���ʾ
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
			tSQLInfo.setSqlId("LLClaimCaseReviewSql31");
			tSQLInfo.addSubPara(fm.GrpRgtNo.value);
			tSQLInfo.addSubPara(mOperator);
			tSQLInfo.addSubPara("");
			tSQLInfo.addSubPara("");
			var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tExistArr!=null && tExistArr.length>0) {
				
				var tAppendString = "";
				for (var i=0;i<tExistArr.length;i++) {
					tAppendString=tAppendString+tExistArr[i][0]+",";
				}
				tAppendString = tAppendString.substring(0,tAppendString.length-1);
				alert("����"+tAppendString+"�ȳ�����ʹ���˹�������뵥�����㣡");
				//��ʾ��ɾ��������Ϣ
				fm.Operate.value="CALINFODELETE";
				fm.action = "./LLClaimCaseReviewSave.jsp";
				fm.submit();
			}
			
		} else if (fm.Operate.value=="P") {
			initAcceptInfo();
			queryAcceptInfo();
			initCustomerInfo();
			queryCustomerList();
			setSelRow(CustomerListGrid,turnPage1);
			showSelectCustomer();
			
			//У���Ƿ�ʹ���˹���������ǣ���ʾ
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
			tSQLInfo.setSqlId("LLClaimCaseReviewSql31");
			tSQLInfo.addSubPara(fm.GrpRgtNo.value);
			tSQLInfo.addSubPara(mOperator);
			tSQLInfo.addSubPara(fm.RegisterNo.value);
			tSQLInfo.addSubPara(fm.CustomerNo.value);
			var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tExistArr!=null && tExistArr.length>0) {
				
				if(confirm("�ó���������ʱ���ڹ�������Ƿ�ȷ��ʹ�ù������")) {
					
					//ϵͳ����ʱĬ��ʹ�ù�������ʡ�ȷ����ʱ�����ò���
					fm.PublicFlag.value = "1";
				} else {
					//�������㲻ʹ�ù�������
					fm.PublicFlag.value = "0";//0-��1-��
					calPay();
				}

			}
			fm.PublicFlag.value = "1";
			
		}else {
			
			if (fm.Operate.value == "REVIEWCONFIRM" || fm.Operate.value == "ALLCONFIRM") {
				
				//У���Ƿ񻹴��ڴ����˵ĳ����ˣ�������ͣ����ǰҳ����������
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
				tSQLInfo.setSqlId("LLClaimCaseReviewSql24");
				tSQLInfo.addSubPara(mGrpRegisterNo);
				tSQLInfo.addSubPara(mOperator);
				var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if (tExistArr==null || tExistArr.length==0) {
					window.location.href="./LLClaimCaseReviewAppInput.jsp";
				}			
			}
			initForm();
		}		
	}	
}

/**
 * У��ǿ�Ҫ�أ����۽�
 */
function notNull(tObject,tName) {

	if (tObject!=null && tObject.value=="") {
		alert(tName+"����Ϊ�գ���¼�룡");

		tObject.focus();
		tObject.style.background="#ffb900";
		return false;
	} else if (tObject==null) {
		return false;
	}
	return true;
}
//��ѯͶ������Ϣ��֧������ģ����ѯ
function QueryOnKeyDown(tObject) {

	var keycode = event.keyCode;
	//�س���ascii����13
	if(keycode!="13" && keycode!="9") {
		return;
	}	
	var tObjectName = tObject.name;
	var tObjectValue = tObject.value;
	if (tObjectName=="AppntName") {
		

	}else if (tObjectName=="HospitalName") {
		
		var tHospitalName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql13");
		tSQLInfo.addSubPara(tHospitalName);
		tSQLInfo.addSubPara("");
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			fm.AccResult1.value = "";
			fm.AccResult1Name.value = "";	
			
//			fm.Province.value = "";
//			fm.ProvinceName.value = "";
//			fm.City.value = "";
//			fm.CityName.value = "";
//			fm.County.value = "";
//			fm.CountyName.value = "";
			fm.AccidentPlace.value = "";
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.HospitalCode.value = tArr[0][0];
				fm.HospitalName.value = tArr[0][1];
				
//				fm.Province.value = tArr[0][2];
//				fm.ProvinceName.value = tArr[0][3];
//				fm.City.value = tArr[0][4];
//				fm.CityName.value = tArr[0][5];
//				fm.County.value = tArr[0][6];
//				fm.CountyName.value = tArr[0][7];
				fm.AccidentPlace.value = tArr[0][2];			
			} else {
				var tCodeCondition = "1 and hospitalname like #%"+ tHospitalName +"%#";
				showCodeList('hospital', [fm.HospitalCode,fm.HospitalName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	if (tObjectName=="AccResult1Name") {
		var tAccResultName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql14");
		tSQLInfo.addSubPara(tAccResultName);
		tSQLInfo.addSubPara("1");
		tSQLInfo.addSubPara("");
		
		if (fm.AccResult1Name.value==null || fm.AccResult1Name.value=="") {
			alert("��Ҫ��ϲ���Ϊ��");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			fm.AccResult2.value = "";
			fm.AccResult2Name.value = "";
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AccResult1.value = tArr[0][0];
				fm.AccResult1Name.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and icdname like #%"+ tAccResultName +"%# and icdlevel=#1# ";
				showCodeList('diseasecode', [fm.AccResult1,fm.AccResult1Name], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	if (tObjectName=="AccResult2Name") {
		var tAccResultName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql14");		
		tSQLInfo.addSubPara(tAccResultName);
		tSQLInfo.addSubPara("2");
		tSQLInfo.addSubPara(fm.AccResult1.value);
		
		if(!notNull(fm.AccResult1,"��Ҫ���")){return false;};
		if (fm.AccResult2Name.value==null || fm.AccResult2Name.value=="") {
			alert("������鲻��Ϊ��");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AccResult2.value = tArr[0][0];
				fm.AccResult2Name.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and icdname like #%"+ tAccResultName +"%# and upicdcode=#"+ fm.AccResult1.value +"# and icdlevel=#2#";
				showCodeList('diseasecode', [fm.AccResult2,fm.AccResult2Name], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
}
/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if (cCodeName == "givetype"){
									
			var tGiveType = fm.GiveType.value;
			if (tGiveType=="0") {
															
				$("#AdjustInfo").css("display","");
				$("#NoPayInfo").css("display","none");
			} else {
				$("#AdjustInfo").css("display","none");
				$("#NoPayInfo").css("display","");
			}
	} else if (cCodeName == "givetype") {//��ͨ����������1-ͨ����2-�ܸ���3-�رգ�4-�������ˣ��������������1-ͨ����2-�ܸ���3-�رա�
		
	} else if (cCodeName == "hospital") {
			
			var tHospitalCode = fm.HospitalCode.value;
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
			tSQLInfo.setSqlId("LLClaimCaseReviewSql13");
			tSQLInfo.addSubPara("");
			tSQLInfo.addSubPara(tHospitalCode);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

			if (tArr==null || tArr.length==0) {
				
				fm.HospitalCode.value = "";
				fm.HospitalName.value = "";
			/*	
				fm.Province.value = "";
				fm.ProvinceName.value = "";
				fm.City.value = "";
				fm.CityName.value = "";
				fm.County.value = "";
				fm.CountyName.value = "";*/
				fm.AccidentPlace.value = "";							
				return false;
			} else {
				
				if (tArr.length==1) {
					fm.HospitalCode.value = tArr[0][0];
					fm.HospitalName.value = tArr[0][1];
					
					/*fm.Province.value = tArr[0][2];
					fm.ProvinceName.value = tArr[0][3];
					fm.City.value = tArr[0][4];
					fm.CityName.value = tArr[0][5];
					fm.County.value = tArr[0][6];
					fm.CountyName.value = tArr[0][7];*/
					fm.AccidentPlace.value = tArr[0][8];
				}		
			}
		} else if (cCodeName == "rgtconclusion") {//��ͨ����������1-ͨ����2-�ܸ���3-�رգ�4-�������ˣ��������������1-ͨ����2-�ܸ���3-�رա�
			
			var tReviewConclusion = fm.Conclusion.value;
			if (tReviewConclusion=="0" || tReviewConclusion=="1" || tReviewConclusion=="4") {
				
				fm.BatchConfirmCase.style.display = "";
			} else {
				fm.BatchConfirmCase.style.display = "none";
			}
		}
	
}
/**
 * Ĭ��ѡ�иղ����ļ�¼
 */
function setSelRow(ObjGrid,tTurnPage){			
	
 	var tPageIndex = fm.PageIndex.value;
 	
	if (tPageIndex!=null && tPageIndex!="") {
		for (var i=0; i<tPageIndex; i++) {
			tTurnPage.nextPage();
		}
	}
	var tSelNo =fm.SelNo.value;
	if(tSelNo==null || tSelNo ==""){
		tSelNo = 1;
	}	
	ObjGrid.radioSel(tSelNo);
	
}
/**
 * ����У��¼����
 */
function checkMoney(tObject) {

	var tValue = tObject.value;
	if(((!/^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,4})?$/.test(tValue)) && (!tValue==""))){
		
		alert("��������ȷ�Ľ�") ;
		tObject.className = "warn" ;
		tObject.focus();
		tObject.value="";		
	}
}
function firstPage(tObject,tObjGrid) {
	
	tObject.firstPage();
	setMulLineColor(tObjGrid);
}

function previousPage(tObject,tObjGrid) {
	
	tObject.previousPage();
	setMulLineColor(tObjGrid);
}

function nextPage(tObject,tObjGrid) {
	
	tObject.nextPage();
	setMulLineColor(tObjGrid);
}

function lastPage(tObject,tObjGrid) {
	
	tObject.lastPage();
	setMulLineColor(tObjGrid);
}