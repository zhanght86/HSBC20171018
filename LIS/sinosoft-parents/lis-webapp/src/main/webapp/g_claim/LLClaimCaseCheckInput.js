/***************************************************************
 * <p>ProName：LLClaimCaseCheckInput.js</p>
 * <p>Title：案件复核</p>
 * <p>Description：案件复核</p>
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
var submit = 0;

/**
 * 查询受理信息
 */
function queryAcceptInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql1");
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
		
		fm.SumRealPay.value = tArr[0][7];
		fm.CaseType.value = tArr[0][8];
		fm.CaseTypeName.value = tArr[0][9];
		
		var tBatchInfo = "<font color='red'>该批次下交接人数【"+ tArr[0][10] +"】,"
		+ "已受理人数【"+ tArr[0][11] +"】,未受理人数【"+ tArr[0][12] +"】,待复核人数【"+ tArr[0][13] +"】,";
	
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
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql2");
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
	
	var tCaseType = fm.CaseType.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql3");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(mGrpRegisterNo);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
	if (tArr != null && tArr.length>0) {

		//先清空原有的数据
		initCustomerInfo();		
		initCaseInfo();
		initPayInfo();
		initCaseReviewInfo();
		
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
/*	//统计个人已赔付事件数、赔付金额
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql4");
	tSQLInfo.addSubPara(tCustomerNo);
	var tOtherArr = easyExecSql(tSQLInfo.getString());
	
	if (tOtherArr == null || tOtherArr.length==0) {

		fm.PayCount.value = 0;
		fm.TotalPay.value = 0;
	}else {
		
		fm.PayCount.value = tOtherArr[0][0];
		fm.TotalPay.value = tOtherArr[0][1];		
	}*/	
	queryLastClaimInfo();
	queryCustomerCaseList();	
	queryReviewInfo();
	queryApproveInfo();
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
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql17");
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	turnPage6.queryModal(tSQLInfo.getString(),PayInfoListGrid,2,1);	
}
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
	fm.CaseNo.value = tCaseNo;
	
	fm.SelNo.value = AccidentListGrid.getSelNo();//add 2011-11-29
	fm.PageIndex.value = turnPage2.pageIndex;//add 2011-11-29	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql6");
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
		
	/*	fm.日.value = tArr[0][19];
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
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql7");
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
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql8");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara("1");
	turnPage3.queryModal(tSQLInfo.getString(),OnEventDutyListGrid,2,1);
	
	turnPage4.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql8");
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
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql10");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	turnPage5.queryModal(tSQLInfo.getString(),CaseDutyPayGrid,2,1);	
}
/**
 * 查询已维护的客户的事件信息列表
 */
function queryCustomerCaseList() {
	
	if(fm.RegisterNo.value=="" || fm.RegisterNo.value==null || fm.CustomerNo.value=="" || fm.CustomerNo.value==null) {
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql5");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),AccidentListGrid,2);	
}

/**
 * 未受理客户
 */
function showNoAccepted() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("请先保存理赔受理信息");
		return false;
	}
	var strUrl="./LLClaimNoAcceptMain.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"未受理",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
}
/**
 * 查询再审意见
 */
function queryReviewInfo() {
	
	//查询最近的两次审核结论
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql12");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length<=0) {
		alert("未查询到该出险人的审核信息！");
		return false;
	} else {
		
		$("#ReviewAdvice").css("display","");
		if (tArr.length<=1) {			
			$("#AgainReviewAdvice").css("display","none");
			fm.Conclusion.value = tArr[0][0];
			fm.ConclusionName.value = tArr[0][1];
			fm.ReviewAdvice.value = tArr[0][2];
		} else {
			$("#AgainReviewAdvice").css("display","");
			fm.Conclusion.value = tArr[0][0];
			fm.ConclusionName.value = tArr[0][1];
			fm.ReviewAdvice.value = tArr[1][2];
			fm.AgainReviewAdvice.value = tArr[0][2];			
		}
	}
}

/**
 * 查询审批意见
 */
function queryApproveInfo() {
	
	var mReviewFlag = false;
	//查询是否已存在审核意见，并标记
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql16");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length<=0) {
		mReviewFlag = false;
	} else {
		mReviewFlag = true;
	}
	if (mReviewFlag) {

		$("#divApproveConclusion").css("display","");		
		fm.ApproveConclusion.value = tArr[0][0];
		fm.ApproveConclusionName.value = tArr[0][1];
		fm.ApproveAdvice.value = tArr[0][2];
	} else {
		
		fm.ApproveConclusion.value = "";
		fm.ApproveConclusionName.value = "";
		fm.ApproveAdvice.value = "";
		$("#divApproveConclusion").css("display","none");		
	}	
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
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"问题件",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * 社保新消息查询
 */
function querySheBao() {
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
	window.open(strUrl,"问题件",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
 * 报案查看
 */
function showReport() {
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
	var strUrl="./LLClaimAssReportMain.jsp?RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo+"&Mode=1";
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
		
		var strUrl="LLClaimPreinvestInputMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&custNo="+tInsuredNo+"&surveyType=01&initPhase=03&AppntNo="+tAppntNo+"&ManageCom="+tManageCom+"&Mode=0";

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
	tSQLInfo.setResourceName("g_claim.LLClaimCaseReviewSql");
	tSQLInfo.setSqlId("LLClaimCaseReviewSql15");
	tSQLInfo.addSubPara(tRgtNo);
	
	var arr = easyExecSql(tSQLInfo.getString(),1, 0, 1);
	if(arr != null && arr != ""){
		
		alert("存在未处理的问题件，请与BPO数据对接方确认问题件是否已经处理完毕,再提交确认！");
		return false;
	}*/
	
	var strUrl="./LLClaimQuestionMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"问题件",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 黑名单管理
 */
function blackList() {
	
	var strUrl="./LLClaimBlackListMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&Mode=1&ClmState=40";
	var tWidth=1100;
	var tHeight=700;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"黑名单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
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
  +"&BussNo="+busson+"&BussType="+tBussType+"&SubType=23003"+"&FeeType="+FeeType+"&Flag=0&Mode=1";
 	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"审核全部账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
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
	var strUrl="./LLClaimNoMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&AccidentDate="+fm.AccDate.value+"&Mode=1";
	
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
	var busson = "";
	var tBussType = "";
	var tSubType = "";
 	
 	if(tArr== null || tArr == "") {
 		
 	}else{
	 	busson = tArr[0][0];
		tBussType = tArr[0][1];
		tSubType = tArr[0][2];
 	}
	//外包案件
	if(fm.CaseType.value=="02"){
		
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
  +"&BussNo="+busson+"&BussType="+tBussType+"&SubType="+tSubType+"&FeeType="+FeeType+"&Mode=1";
 	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"外包账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}  else if (fm.CaseType.value=="01"||fm.CaseType.value=="03"){
	
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
	
	var strUrl="./LLClaimMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&CaseSource="+CaseSource+"&Mode=1&BussNo="+busson+"&BussType="+tBussType+"&SubType="+tSubType;

	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"医疗账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	}
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
	
	if (fm.CaseType.value!="01" && fm.CaseType.value!="02") {
		
		$("#divPayModify").css("display","");
	
		//查询保项赔付调整信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
		tSQLInfo.setSqlId("LLClaimCaseCheckSql11");
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
 * 返回
 */
function goToBack() {
	
	window.location.href="./LLClaimCaseCheckAppInput.jsp";
}
/**
 * 核赔履历查看
 *//*
function showUWTrace() {

	var strUrl="./LLClaimUWTraceMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value;
	var tWidth=900;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"交接流转号查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}*/
/**
 * 复核确认
 */
function checkConfirm() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条出险人信息");
		return false;
	}
		
	if(!notNull(fm.ChkConclusion,"复核结论")){return false;};
	if(!notNull(fm.CheckAdvice,"复核意见")){return false;};
	//检查是否存在未处理的问题件
	if (!checkQuestion()) {		
		return false;
	}
	//检验是否有未完成的调查
	if(!checkQueryInq()){
		return false;
	}
			
	fm.Operate.value = "CHECKCONFIRM";
	if(submit != 0){
		return false;
	}
	submit = submit+1;
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
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
 * 校验外包案件是否存在未完成的问题件
 */
function checkQuestion() {
		
	var tCaseType = fm.CaseType.value;
	if (tCaseType!=null && tCaseType=="02") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
		tSQLInfo.setSqlId("LLClaimCaseCheckSql13");
		tSQLInfo.addSubPara(fm.RegisterNo.value);
		var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistArr!=null && tExistArr.lengt>=1) {
			alert("该出险人存在未完成的问题件，请先回复问题件！");
			return false;
		}			
	}
	return true;
}
/**
 * 校验是否存在未完成的调查
 */
function checkQueryInq() {
			
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckSql14");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
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
 * 批量提交复核
 */
function batchCheckConfirm() {
	
	if(!notNull(fm.ChkConclusion,"复核结论")){return false;};	
	
	fm.Operate.value = "ALLCONFIRM";
	if(submit != 0){
		return false;
	}
	submit = submit+1;
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,true);

	showInfo.focus();
	fm.submit();
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
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
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
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
		fm.submit();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,true);
	
		showInfo.focus();
		fm.submit();
		if (fm.Operate.value == "CHECKCONFIRM" || fm.Operate.value == "ALLCONFIRM") {
			
			//校验是否还存在待复核的出险人，若存在停留当前页，否则跳出
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckSql");
			tSQLInfo.setSqlId("LLClaimCaseCheckSql15");
			tSQLInfo.addSubPara(mGrpRegisterNo);
			var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tExistArr==null || tExistArr.length==0) {
				window.location.href="./LLClaimCaseCheckAppInput.jsp";
			} else {
				initForm();
			}			
		}
	}
	submit = 0;
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
	} else if (cCodeName == "chkconclusion") {//普通案件包含：1-通过，2-拒付，3-关闭，4-案件回退；外包案件包含：1-通过，2-拒付，3-关闭。
		
		var tChkConclusion = fm.ChkConclusion.value;
		if (tChkConclusion=="0" || tChkConclusion=="1" || tChkConclusion=="2" || tChkConclusion=="3" || tChkConclusion=="5" || tChkConclusion=="6") {
			
			fm.BatchCaseConfirm.style.display = "";
		} else {
			fm.BatchCaseConfirm.style.display = "none";
		}
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