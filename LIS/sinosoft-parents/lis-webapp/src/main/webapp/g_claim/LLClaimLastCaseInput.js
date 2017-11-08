/***************************************************************
 * <p>ProName：LLClaimLastCaseInput.js</p>
 * <p>Title：赔案查询</p>
 * <p>Description：赔案查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tSQLInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
turnPage2.pageLineNum = 100;

/**
 * 查询历史赔案
 */
function queryClick() {

	if (mMode=="0") {
		queryClick1();
		return;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),LastCaseListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}

/**
 * 查询赔案（包括在途）
 */
function queryClick1() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql2");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),LastCaseListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}

/**
 * 展示选中的赔案赔付明细
 */
function showSelectDetail () {
	
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择赔案信息");
		return false;
	}
	var tRgtNo = LastCaseListGrid.getRowColData(tSelNo,4);
	var tCustomerNo = LastCaseListGrid.getRowColData(tSelNo,5);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql1");
	
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),LastCaseDetailGrid, 2);	
}

/**
 * 查看全部赔案信息
 */
function showAllDetailInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql1");
	
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(mCustomerNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),LastCaseDetailGrid, 2);
	
}

/**
 * 查看赔案明细信息
 */
function showCaseDetailInfo() {
		
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择赔案信息");
		return false;
	}
	var tGrpRgtNo = LastCaseListGrid.getRowColData(tSelNo,1);
	var tClmState = LastCaseListGrid.getRowColData(tSelNo,13);
	var tCaseType = LastCaseListGrid.getRowColData(tSelNo,14);
	if (tCaseType=="02" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimOutCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tCaseType=="01" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimSpecialCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="30") {
		
		var strUrl="../g_claim/LLClaimCaseReviewInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="40") {
		
		var strUrl="../g_claim/LLClaimCaseCheckInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');			
	} else {
		
		var strUrl="../g_claim/LLClaimCaseApproveInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/**
 * 返回
 */
function goBack() {
	top.close();
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	showInfo = showDialogWindow(urlStr, 0);
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
		showDialogWindow(urlStr, 1);
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		showDialogWindow(urlStr, 1);
	}	
}