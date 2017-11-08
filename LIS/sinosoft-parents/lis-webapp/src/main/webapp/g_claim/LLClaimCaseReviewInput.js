/***************************************************************
 * <p>ProName：LLClaimCaseReviewInput.js</p>
 * <p>Title：案件审核</p>
 * <p>Description：案件审核</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
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
 * 查询受理信息
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
		
		var tBatchInfo = "<font color='red'>该批次下交接人数【"+ tArr[0][10] +"】,"
			+ "已受理人数【"+ tArr[0][11] +"】,未受理人数【"+ tArr[0][12] +"】,待审核人数【"+ tArr[0][13] +"】,";
		
		if (fm.CaseType.value=="02") {
			tBatchInfo = tBatchInfo + "未回传人数【"+ tArr[0][14] +"】,";
		}
		
		tBatchInfo = tBatchInfo + "已结案人数【"+ tArr[0][15] +"】</font>";
		
		BatchInfo.innerHTML = tBatchInfo;
	}
}
/**
 * 查询已维护的客户信息列表
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
	
	//黑名单的出险人，修改背景色为
	if (turnPage1.strQueryResult) {

		setMulLineColor(CustomerListGrid);
	}
}
//为MulLine设置颜色
function setMulLineColor(ObjGrid) {
	
	//为MulLine设置颜色
	for (var i=0;i<ObjGrid.mulLineCount;i++) {
		
		var tBlackFlag = ObjGrid.getRowColData(i, 18);
		
		if (tBlackFlag=="1") {
			ObjGrid.setColor(i, "#43FA67");
		}
	}	
}
/**
 * 展示选中的客户信息
 */
function showSelectCustomer() {
			
	var i = CustomerListGrid.getSelNo()-1;
	var tRegisterNo = CustomerListGrid.getRowColData(i,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询！");
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

		//先清空原有的数据
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
 * 查询已维护的客户的历史赔付信息列表
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
 * 查询已维护的客户的事件信息列表
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
 * 查询再审意见
 */
//function queryReviewInfo() {
//	
//	//查询是否已存在审核意见，并标记
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
 * 查询复核意见
 */
//function queryCheckInfo() {
//	
//	//查询是否已存在审核意见，并标记
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
 * 查询选中的客户的事件明细信息
 */
function showSelectCase() {
	
	var i = AccidentListGrid.getSelNo()-1;
	var tCaseNo = AccidentListGrid.getRowColData(i,1);
	if (tCaseNo==null || tCaseNo=="") {
		alert("请先查询！");
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
		alert("未查询到事件明细信息!");
		return false;
	}else {
		//先清空原有的数据
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
		alert("出险人理赔类型信息不存在，请检查该出险人是否有效!");
		return false;
	} else {
		
		for (var i=0;i<fm.ClaimType.length;i++) {
		  	
			fm.ClaimType[i].checked = false;
		}
		if (tClaimType!= '' && tClaimType !=null) {
		  	
			for (var j=0;j<fm.ClaimType.length;j++){
		     	
				for (var l=0;l<tClaimType.length;l++){
								
					var tClaim = tClaimType[l].toString();
					tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
					if(fm.ClaimType[j].value == tClaim){				        		
						fm.ClaimType[j].checked = true;
					}
				}
			}
		}
	}
	
	initPayInfo();
	
	queryCaseDutyInfo();
	//查询赔付信息
	queryClaimInfo();
}
/**
 * 查询事件责任信息
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
 * 查询赔付信息
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
 * 全部理算
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
 * 未受理客户
 */
function noAcceptInfo() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("请先保存理赔受理信息");
		return false;
	}
	var strUrl="./LLClaimNoAcceptMain.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";
	window.open(strUrl,"未受理客户",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 批次取消关联保单
 */
function batchNoPay() {
	
	var strUrl="./LLClaimNoPayMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value;
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		
}

/**
 * 保单查询
 */
function showInsuredLCPol() {	
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人");
		return false;
	}	
	var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1&RgtNo="+fm.RegisterNo.value;
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 保全查询
 */
function showInsuredLCEdor() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人");
		return false;
	}	
	var strUrl="./LLClaimQueryEdorMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * 既往赔案查询
 */
function showOldCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人");
		return false;
	}	
	var strUrl="./LLClaimLastCaseMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * 影像件查询
 */
function queryEasyscan() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRgtNo =CustomerListGrid.getRowColData(tSelNo,1);
	window.open("../easyscan/ScanPagesQueryMainInput.jsp?BussType=G_CM&BussNo="+tRgtNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * BPO校验结果查询
 */
function BPOCheck() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	
	var strUrl="./LLClaimBPOcheckMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=1200;
	var tHeight=500;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"BPO校验结果查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * 社保新消息查询
 */
function querySecurity() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	
	var strUrl="./LLClaimQuerySocialInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"社保新消息查询",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	
}

/**
 * 查询承保时录入的银行账户信息
 */
function queryAccInfo() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}	
	var strUrl="./LLClaimQueryAccInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&Mode=1" ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"查询承保时录入的银行账户信息",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
}

/**
 * 理算
 */
function calPay() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
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
	//暂停案件不可审核确认
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql29");
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(tCustomerNo);
	var tRgtStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tRgtStateArr!=null && tRgtStateArr.length>0) {
			alert("该个人案件已暂停，不可理算！");
			return false;
	}		
	submitForm1();
}
/**
 * 审核全部账单
 */
function reviewClmBills() {

	if(fm.CaseType.value="02"){
		
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}		
		
		var tRgtNo = fm.RegisterNo.value;
		var tCaseNo = "";
		if(tRgtNo == ''){
			alert("赔案号为空！");
			return false;
		}
/*		if(tCaseNo == ''){
			alert("事件号为空！");
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
		alert("获取账单类型失败！");
		return false;
	}

 var strUrl="./LLClaimHealthBillESMain.jsp?RgtNo="+ tRgtNo+"&CaseNo="+tCaseNo
  +"&BussNo="+busson+"&BussType="+tBussType+"&SubType=23003"+"&FeeType="+FeeType+"&Flag=0&Mode="+mMode;
 	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"审核全部账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}
/**
 * 受益人信息
 */
function benefitInfo() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);

	var strUrl="./LLClaimBenefitMain.jsp?GrpRgtNo="+mGrpRegisterNo+"&RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo;
	
	window.open(strUrl,"受益人",'width=1000,height=480,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * 启动/暂停案件
 */
function stopORstartCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);	
	var strUrl="./LLClaimSwitchMain.jsp?RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo;
	var tWidth=1100;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"受益人",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 报案关联
 */
function reportRelate() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);	
	var strUrl="./LLClaimAssReportMain.jsp?RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo+"&Mode="+mMode;
	var tWidth=1200;
	var tHeight=500;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"报案关联",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * 调查管理
 */
function showSurvey() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo =CustomerListGrid.getRowColData(tSelNo,1);
	var tInsuredNo = CustomerListGrid.getRowColData(tSelNo,2);
	var tVistFlag = "01";//01 表示 赔案调查
	var tManageCom =fm.AcceptCom.value;//受理机构
	var tAppntNo=fm.AppntNo.value;//投保人编码	
	if(tGrpRgtNo == "" || tGrpRgtNo == null){
		
		alert("批次号为空，请核查信息！");
		return false;
    }
   if(tInsuredNo == "" || tInsuredNo == null) {
    
    alert("出险人编码为空，请核查信息！");
		return false;
  	}
		
		var strUrl="LLClaimPreinvestInputMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&custNo="+tInsuredNo+"&surveyType=01&initPhase=02&AppntNo="+tAppntNo+"&ManageCom="+tManageCom+"&Mode="+mMode;

		window.open(strUrl,"发起调查",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 问题件查询
 */
function question() {
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	/*
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql15");
	tSQLInfo.addSubPara(tRgtNo);
	
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	if(arr != null && arr != ""){
		
		alert("存在未处理的问题件，请与BPO数据对接方确认问题件是否已经处理完毕,再提交确认！");
		return false;
	}*/
	
	var strUrl="./LLClaimQuestionMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=1200;
	var tHeight=500;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"问题件",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 黑名单管理
 */
function blackList() {
	
	var strUrl="./LLClaimBlackListMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&Mode="+mMode+"&ClmState=30";
	var tWidth=1100;
	var tHeight=700;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"黑名单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * 新增事件
 */
function addAccident() {
	
	fm.Operate.value="CASEINSERT";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * 修改事件
 */
function modifyAccident() {
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条事件信息");
		return false;
	}
	var tRegisterNo = AccidentListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询出险人信息！");
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
 * 删除事件
 */
function deleteAccident() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人事件信息");
		return false;
	}
	var tRegisterNo = AccidentListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询出险人事件信息！");
		return;
	}
	if (confirm("确定删除该条事件？")) {
		fm.Operate.value="CASEDELETE";
		submitForm();		
	}	
}

/**
 * 校验事件信息
 */
function checkCaseInfo() {
	
	if(!notNull(fm.AccReason,"出险原因")){return false;};
	if(!notNull(fm.AccDate,"出险日期")){return false;};
	if(!notNull(fm.ClaimPay,"索赔金额")){return false;};
	if(!notNull(fm.AccResult1,"主要诊断")){return false;};
	if(!notNull(fm.TreatResult,"治疗结果")){return false;};
	if(!notNull(fm.CaseSource,"事件来源")){return false;};
	
	/**
	if(!notNull(fm.Province,"出险地点[省]")){return false;};
	if(!notNull(fm.City,"出险地点[市]")){return false;};
	if(!notNull(fm.County,"出险地点[区/县]")){return false;};
	*/
	if(!notNull(fm.AccidentPlace,"出险地点")){return false;};	
	
	var tGrpRegisterNo = fm.GrpRgtNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	var tRegisterNo = fm.RegisterNo.value;
	var tAccDate = fm.AccDate.value;
	var tAppntNo = fm.AppntNo.value;
	var tHospitalCode = fm.HospitalCode.value;
	var tHospitalName = fm.HospitalName.value;
	var tAccidentType = fm.AccReason.value;
	
	//出险人信息校验
	if (tCustomerNo==null || tCustomerNo=="") {
		
		alert("请先选中一条客户信息！");
		return false;
	}
	
	//校验出险人是否存在
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql11");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(tGrpRegisterNo);
	
	var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistArr==null || tExistArr.length<=0) {
		
		alert("该出险人不在此次立案下，请先添加!");
		return false;
	}
	
	//修改出险日期时，需要校验账单的日期
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
						alert("出险日期不能晚于账单起期！");
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
						alert("出险日期不能晚于账单起期！");
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
						alert("出险日期不能晚于账单起期！");
						return false;
				}
			}
		}
	}
	
	
/*	//被保人所有保单已做完生存给付（趸领） 不允许理赔
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql8");
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length<=0) {
		
		alert("该出险人已经做了生存给付，不允许做新增!");
		return false;
	}*/
/*
	//退保万能险不允许理赔，退保普通险是可以理赔的
	var tPGSQL = "select count(1) from LPEdorItem where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and edortype='CT'";
	var tEdorState = easyExecSql(tPGSQL);
	
	var tCSQL = "select count(*) from lmriskapp where "
	         + "riskcode in (select riskcode From lcpol where "
	         + "grpcontno = '"+ tGrpContNo +"' and insuredno = '"+ tCustomerNo +"' and contno='"+ tContNo +"') "
	         + "and RiskPeriod = 'L'";
	var tCount = easyExecSql(tCSQL);
	
	if (tEdorState>0 && tCount>0 ) {
		alert("该出险人有未确认的保全或已经退保，请确认后再做新增！");
		return false;
	}	
*/					

/*
		var tSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+ tAccidentDate +"'";
				tSQLBQ = tSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+ tCustomerNo +"' and a.contno='"+ tContNo +"'";
				tSQLBQ = tSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";
		if (tAppntNo!=null) {
			
				tSQLBQ = tSQLBQ+" and b.AppntNo='"+ tAppntNo +"'";
		}
		if (tGrpContNo!=null) {
				tSQLBQ = tSQLBQ+" and b.GrpContNo='"+ tGrpContNo +"'";
		}
		var tArrBQ = easyExecSql(tSQLBQ);
		if ( tArrBQ != null ) {

				alert("严重警告：该被保险人做过可能更改保额的保全！");   
		}
*/		
	//出险日期校验
	if (dateDiff(tAccDate,mCurrentDate,'D')<0) {	
		
		alert("出险日期不能晚于当前日期！");
		return false;
	}
	
	//身故日期校验
	var tDeathDate = fm.DeathDate.value;
	if (tDeathDate!=null && tDeathDate!="") {
		
		if (dateDiff(tDeathDate,mCurrentDate,'D')<0) {
			
			alert("身故日期不能晚于当前日期！");			
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;
		}
		if (dateDiff(tDeathDate,tAccDate,'D')>0) {		
			
			alert("身故日期不能早于出险日期！");
			
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";			
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDeathDate,'D')>180) {
				
				if (confirm("身故日期与出险日期的间隔大于180天是否继续？")) {
									
				} else {
					
					return false;
				}
			}
		}
	}
	
	//伤残/全残日期校验
	var tDianoseDate = fm.DeformityDate.value;
	if (tDianoseDate!=null && tDianoseDate!="") {
		
		if (dateDiff(tDianoseDate,mCurrentDate,'D')<0) {		
			
			alert("伤残/全残日期不能晚于当前日期！");
			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";			
			return false;
		}
		if (dateDiff(tDianoseDate,tAccDate,'D')>0) {
			
			alert("伤残/全残日期不能早于出险日期！");
			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";				
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDianoseDate,'D')>180) {
				
				if (confirm("伤残日期与出险日期的间隔大于180天是否继续？")) {
									
				} else {
					
					return false;
				}
			}
		}				
		var tDeadCheck = fm.ClaimType[0].checked;//校验出险类型为身故的多选框是否勾选
		if (!tDeadCheck && tDeathDate!="") {
			
			alert("您录入了【身故日期】，请勾选理赔类型--【身故】！");
			return false;
		}
		var tMaim = fm.ClaimType[2].checked;//校验出险类型为伤残的多选框是否勾选
		if (!tMaim && tDianoseDate!="") {
			
			alert("您录入了【伤残/全残日期】，请勾选理赔类型--【伤残】！");
			return false;
		}		

	}	
	var tAllGetClaimType = "";
	//校验出险类型
	
	var tClaimType = new Array;	
	var tDeadFalg = fm.ClaimType[0].checked;//校验是否勾选身故多选框
	var tMedicalFlag = fm.ClaimType[3].checked;//是否勾选出险类型--医疗
	var tMaimFalg = fm.ClaimType[2].checked;//是否勾选出险类型--伤残
	

	
	//出险类型
	for (var j=0;j<fm.ClaimType.length;j++) {
  	
		if (fm.ClaimType[j].checked == true) {
      	
			tClaimType[j] = fm.ClaimType[j].value;
			tAllGetClaimType = tAllGetClaimType + "'" + tAccidentType + "" + tClaimType[j] + "',";
		}
	}
	if (tClaimType==null || tClaimType=="") {

		alert("请选择出险类型！");
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
			
			alert("您录入的“"+ tDutyName +"”,不存在与它相对应的出险原因与出险类型！");
			return false;
		}	
	}
	*/
	if (tDeadFalg) {
		
		if (tDeathDate==null || tDeathDate=="") {
			
			alert("出险类型为身故，请录入【身故日期】！");			
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;
		}
	}
	if (tMaimFalg) {
		
		if (tDianoseDate==null || tDianoseDate=="") {
			
			alert("出险类型为伤残，请录入【伤残/全残日期】！");
			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";			
			return false;
		}
	}	
	
	//校验治疗医院与医院编码是否匹配
		/**
	if (tHospitalCode!=null && tHospitalCode!="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql9");
		tSQLInfo.addSubPara(tHospitalCode);
		tSQLInfo.addSubPara(tHospitalName);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tValidArr==null || tValidArr.length<=0) {
			
			alert("医院名称与医院编码不对应，请检查！");
			return false;
		}
	}**/

	if (fm.Operate.value=="CASEUPDATE") {
		
		//医疗类--校验索赔金额是否大于该事件下所关联的账单的原始金额之和
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
			alert("索赔金额不得小于该事件下账单原始金额之和");		
			return false;
		}		
	}		
	
	return true;	
}

/**
 * 关闭事件
 */
function closeAccident() {
	
	if (mFlag!=null && mFlag=="CLOSE") {
		saveCloseDesc();
	} else {
		
		var tSelNo = AccidentListGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("请选择一条出险人事件信息");
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
 * 保存关闭事件明细
 */
function saveCloseDesc() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人事件信息");
		return false;
	}
	var tState = AccidentListGrid.getRowColData(tSelNo,13);
	if (tState=="1") {
		alert("该事件已关闭，不允许重复操作！");
		return false;
	}
	

	if(!notNull(fm.CloseReasonDesc,"关闭事件原因")){return false;};
	if(!notNull(fm.CloseRemarkDesc,"关闭事件备注")){return false;};
	
	fm.Operate.value="CASECLOSE";
	submitForm();
}

/**
 * 开启事件
 */
function openAccident() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人事件信息");
		return false;
	}
	
	var tState = AccidentListGrid.getRowColData(tSelNo,13);
	if (tState=="0") {
		alert("该事件未关闭，不需要开启！");
		return false;
	}	
	
	fm.Operate.value="CASEOPEN";
	submitForm();	
}
/**
 * 合并事件
 */
function combineAccident() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条出险人的事件信息");
		return false;
	}
	var tState = AccidentListGrid.getRowColData(tSelNo,13);
	if (tState=="1") {
		alert("该事件已关闭，不能合并！");
		return false;
	}	
	
	var strUrl="./LLClaimMergeMain.jsp?RgtNo="+fm.RegisterNo.value+"&CustomerNo="+fm.CustomerNo.value+"&CaseNo="+fm.CaseNo.value;
	var tWidth=1200;
	var tHeight=500;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;
	window.open(strUrl,"交接流转号查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 非医疗账单
 */
function noMedicalBill() {
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条事件信息");
		return false;
	}
	var strUrl="./LLClaimNoMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&AccidentDate="+fm.AccDate.value+"&Mode="+mMode;
	
	var tWidth=window.screen.availWidth;
	var tHeight=500;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"非医疗账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
/**
 * 医疗账单
 */
function MedicalBill() {
	
	//先获取影像件
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
/*	//外包案件--特殊账单历史赔案为外包案件
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
		alert("请选择一条事件信息");
		return false;
	}
		var tRgtNo = fm.RegisterNo.value;
		var tCaseNo = fm.CaseNo.value;
		if(tRgtNo == ''){
			alert("赔案号为空！");
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
		alert("此事件【"+tCaseNo+"】没有关联账单！");
		return false;
	}
	if(FeeType == ""){
		alert("获取账单类型失败！");
		return false;
	}

	var strUrl="./LLClaimHealthBillESMain.jsp?RgtNo="+ tRgtNo+"&CaseNo="+tCaseNo
  +"&BussNo="+busson+"&BussType="+tBussType+"&SubType="+tSubType+"&FeeType="+FeeType+"&Mode="+mMode;
 	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"外包账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (fm.CaseType.value=="01"||fm.CaseType.value=="11"
		|| fm.CaseType.value=="12" || fm.CaseType.value=="13"){
	
		var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	
	var tCaeSelNo=AccidentListGrid.getSelNo()-1;
	var CaseSource="";
	if(tCaeSelNo<0){
		CaseSource="02";//账单生成
	}else{
		
		CaseSource=fm.CaseSource.value;
	}
	
	var strUrl="./LLClaimMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&CaseSource="+CaseSource+"&Mode="+mMode+"&BussNo="+busson+"&BussType="+tBussType+"&SubType="+tSubType;

	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"医疗账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	}
	
}
/**
 * 取消事件责任关联
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
		alert("请至少选择一条事件责任信息");
		return false;
	}

/*		
	var tSelNo = OnEventDutyListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人事件已关联责任信息");
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
 * 关联事件责任
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
		alert("请至少选择一条事件责任信息");
		return false;
	}
		
	fm.Operate.value="DUTYON";
	submitForm();		
}
/**
 * 展示结论调整信息
 */
function showAjustInfo() {
	
	
	var tSelNo = CaseDutyPayGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条事件赔付信息");
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
	
	//判断是否存在对应保项修改规则，若存在则可修改
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
		
		//判断是否存在对应保项修改规则，若存在则可修改
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
			
			//判断是否存在对应保项修改规则，若存在则可修改
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
	
		//查询保项赔付调整信息
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
			
			alert("未查询到赔付信息！");
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
 * 展示调整信息
 */
function showSelectMod(objAdjReason,objAdjReasonName) {
	
	if ((fm.CaseType.value=="01" || fm.CaseType.value=="02") && tPayRuleFlag) {
		showCodeList('queryclmreasontype', [objAdjReason, objAdjReasonName], [0,1], null, null , null, 1, '400');
	} else {
		showCodeList('adjreason', [objAdjReason, objAdjReasonName], [0,1], null, null , null, 1, '400');
	}
}

/**
 * 保存赔付调整信息
 */
function savePayInfo() {
	
	if(!notNull(fm.GiveType,"赔付结论")){return false;};
	var tGiveType = fm.GiveType.value;
	if (tGiveType=="0") {
		
		if(!notNull(fm.AdjReason,"调整原因")){return false;};
		if(!notNull(fm.RealPay,"调整金额")){return false;};

		var tAdjustMoney = fm.RealPay.value;
		var tClaimPay = fm.ClaimPay.value;

/*		if (tClaimPay-tAdjustMoney<0) {
			alert("调整金额不得大于申请金额！");
			fm.RealPay.focus();
			fm.RealPay.style.background='#ffb900';
			return false;
		}*/
	
		
	} else if (tGiveType=="1") {
		
		if(!notNull(fm.NoGiveReason,"拒付原因")){return false;};
		if(!notNull(fm.SpecialRemark,"特殊备注")){return false;};		
	} else {		
		alert("给付结果不存在，请下拉选择！");
		return false;
	}
	if(!notNull(fm.AdjRemark,"赔付结论说明")){return false;};
	fm.Operate.value = "SAVEPAY";
	submitForm();	
}
/**
 * 返回
 */
function goToBack() {
	
	if (mMode==1) {
		top.close();
	} else {
		window.location.href="./LLClaimCaseReviewAppInput.jsp";
	}	
}
/**
 * 审核确认
 */
function caseConfirm() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条出险人信息");
		return false;
	}
	if(!notNull(fm.Conclusion,"审核结论")){return false;};
	if (mReviewFlag) {
		if(!notNull(fm.AgainReviewAdvice,"再审意见")){return false;};
	} else {
		if(!notNull(fm.ReviewAdvice,"审核意见")){return false;};
	}	
	
	var tConclusion = fm.Conclusion.value ;
	var tRgtNo = fm.RegisterNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	
	//暂停案件不可审核确认
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql29");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
	var tRgtStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tRgtStateArr!=null && tRgtStateArr.length>0) {
			alert("该个人案件已暂停，不可审核确认！");
			return false;
	}		
	
	if (tConclusion=="1" || tConclusion=="2") {
		
		//查询是否进行过理算
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql18");
		tSQLInfo.addSubPara(tRgtNo);
		tSQLInfo.addSubPara(tCustomerNo);	
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length<=0) {
			alert("请先进行理算");
			return false;
		}		
	}	
	
	//校验外包案件是否存在未完成的问题件
	var tCaseType = fm.CaseType.value;
	if (tCaseType!=null && tCaseType=="02") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql21");
		tSQLInfo.addSubPara(tRgtNo);
		var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistArr!=null && tExistArr.lengt>=1) {
			alert("该出险人存在未完成的问题件，请先回复问题件！");
			return false;
		}
		
		//存在账单修改的赔案不可提交
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
		tSQLInfo.setSqlId("LLClaimCaseReviewSql36");
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara(tRgtNo);
		var tExistBillArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistBillArr!=null && tExistBillArr.lengt>=1) {
			alert("该出险人存在处于账单修改岗的账单，请修改完毕后再继续操作！");
			return false;
		}
	}	
	
	//个人案件下是否才存在待补充的事件--账单生成的事件为待补充事件
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql23");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
	var tStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tStateArr!=null && tStateArr.lengt>=1) {
		alert("该出险人存在待补充的事件，请先补充事件信息！");
		return false;
	}		
		
	//检验是否有未完成的调查
	if(!checkQueryInq()){
		return false;
	}	
	
	/*********************************
	//校验如果出险人下所有的保项拒付，则不可以下达给付结论
	
	if (tConclusion=="1") {
		
			//校验是否存在给付保项
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("claim.LLClaimCaseReviewSql");
			tSQLInfo.setSqlId("LLClaimCaseReviewSql19");
			tSQLInfo.addSubPara(tRgtNo);
			tSQLInfo.addSubPara(tCustomerNo);	
			
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null || tArr.length<=0) {
				alert("该出现人下的所有保项的赔付结论为“拒付”，不能下达[给付]结论！");
				return false;
			}					
		}
	*********************************/
		
	if (tConclusion=="1" && (fm.CaseType.value=="01" || fm.CaseType.value=="02")) {
					
		//校验医疗的实付金额不得大于账单原始金额--
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
					alert("该出险人下，事件["+tSelCaseNo+"]实付金额不得大于账单原始金额，请检查！");				
					return false;
				}
			}
		}		
	}
	
	//校验是否需要客户信息识别
/*	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql32");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);	
	
	var tCheckArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCheckArr!=null && tCheckArr.length>0) {
		
		alert("该出险人赔付金额大于10000，需要进行客户信息识别，请先进行客户信息识别录入！");		
		return false;
	}*/
	
	fm.Operate.value = "REVIEWCONFIRM";	
	submitForm();
}
/**
 * 批量提交复核
 */
function batchCaseConfirm() {
	
	fm.Operate.value = "ALLCONFIRM";
	if(!notNull(fm.Conclusion,"审核结论")){return false;};
	
	if (fm.Conclusion.value=='3') {
		
		alert("审核结论为[关闭]，不允许批量提交");
		return false;
	}
	submitForm();
}
/**
 * 客户信息识别按钮
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
		
		alert("该批次下不存在需要客户信息识别的出险人！");		
		return false;
	}	
	
	var strUrl="../compliance/AmlPerMain.jsp?BussType=CM&BussNo="+mGrpRegisterNo+"&ActivityID=1800501005" ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"客户信息识别",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');			
}

/**
 * 校验是否存在未完成的调查
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
			alert("该出险人存在未完成的调查，请先完成调查！");
			return false;			
		} else {
			return true;
		}
	} else {
		return true;
	}
}

/**
 * 关闭案件暂停或启动页面后操作
 */
function afterSwitchCase() {

	initCustomerInfo();
	queryCustomerList();
	setSelRow(CustomerListGrid,turnPage1);
	showSelectCustomer();
}

/**
 * 提交数据
 */
function submitForm() {
	tSubmitNum=tSubmitNum+1;
	if (tSubmitNum>1) {
		return false;
	}
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	fm.submit();
}

/**
 * 提交数据
 */
function submitForm1() {
	
	tSubmitNum=tSubmitNum+1;
	if (tSubmitNum>1) {
		return false;
	}
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./LLClaimCalPortalSave.jsp";
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	tSubmitNum=0;
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();
	} else {
		
		if (fm.Operate.value!="CALINFODELETE") {
			
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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
			
			//校验是否使用了公共保额，若是，提示
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
				alert("其中"+tAppendString+"等出险人使用了公共保额，请单独理算！");
				//提示后删除理算信息
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
			
			//校验是否使用了公共保额，若是，提示
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
			tSQLInfo.setSqlId("LLClaimCaseReviewSql31");
			tSQLInfo.addSubPara(fm.GrpRgtNo.value);
			tSQLInfo.addSubPara(mOperator);
			tSQLInfo.addSubPara(fm.RegisterNo.value);
			tSQLInfo.addSubPara(fm.CustomerNo.value);
			var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tExistArr!=null && tExistArr.length>0) {
				
				if(confirm("该出险人理算时存在公共保额，是否确定使用公共保额！")) {
					
					//系统理算时默认使用公共保额，故【确定】时，不用操作
					fm.PublicFlag.value = "1";
				} else {
					//重新理算不使用公共保额
					fm.PublicFlag.value = "0";//0-否1-是
					calPay();
				}

			}
			fm.PublicFlag.value = "1";
			
		}else {
			
			if (fm.Operate.value == "REVIEWCONFIRM" || fm.Operate.value == "ALLCONFIRM") {
				
				//校验是否还存在待复核的出险人，若存在停留当前页，否则跳出
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
 * 校验非空要素，并聚焦
 */
function notNull(tObject,tName) {

	if (tObject!=null && tObject.value=="") {
		alert(tName+"不可为空，请录入！");

		tObject.focus();
		tObject.style.background="#ffb900";
		return false;
	} else if (tObject==null) {
		return false;
	}
	return true;
}
//查询投保人信息，支持左右模糊查询
function QueryOnKeyDown(tObject) {

	var keycode = event.keyCode;
	//回车的ascii码是13
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
			alert("未查询到符合条件的查询结果！");
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
			alert("主要诊断不可为空");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
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
		
		if(!notNull(fm.AccResult1,"主要诊断")){return false;};
		if (fm.AccResult2Name.value==null || fm.AccResult2Name.value=="") {
			alert("诊断详情不可为空");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
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
 * 下拉框选择以后调用
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
	} else if (cCodeName == "givetype") {//普通案件包含：1-通过，2-拒付，3-关闭，4-案件回退；外包案件包含：1-通过，2-拒付，3-关闭。
		
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
		} else if (cCodeName == "rgtconclusion") {//普通案件包含：1-通过，2-拒付，3-关闭，4-案件回退；外包案件包含：1-通过，2-拒付，3-关闭。
			
			var tReviewConclusion = fm.Conclusion.value;
			if (tReviewConclusion=="0" || tReviewConclusion=="1" || tReviewConclusion=="4") {
				
				fm.BatchConfirmCase.style.display = "";
			} else {
				fm.BatchConfirmCase.style.display = "none";
			}
		}
	
}
/**
 * 默认选中刚操作的记录
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
 * 正则校验录入金额
 */
function checkMoney(tObject) {

	var tValue = tObject.value;
	if(((!/^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,4})?$/.test(tValue)) && (!tValue==""))){
		
		alert("请输入正确的金额！") ;
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