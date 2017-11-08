/***************************************************************
 * <p>ProName：LLClaimLastCaseInput.js</p>
 * <p>Title：赔案流程查询</p>
 * <p>Description：赔案流程查询</p>
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
 * 查询赔案
 */
function queryClick() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseFlowSql");
	tSQLInfo.setSqlId("LLClaimCaseFlowSql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.FlowState.value);
	tSQLInfo.addSubPara(fm.ClaimUserCode.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),LastCaseListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}

/**
 * 查询赔案轨迹
 */
function showTrace() {

	turnPage2.pageLineNum = 100;
	var i = LastCaseListGrid.getSelNo()-1;
	var tGrpRgtNo = LastCaseListGrid.getRowColData(i,1);
	var tRegisterNo = LastCaseListGrid.getRowColData(i,5);
	var tCustomerNo = LastCaseListGrid.getRowColData(i,6);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseFlowSql");
	tSQLInfo.setSqlId("LLClaimCaseFlowSql1");
	
	tSQLInfo.addSubPara(tGrpRgtNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(tCustomerNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),LastCaseFlowGrid, 2);	
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
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "批次号^|案件类型^|投保人名称^|受理机构^|"
							+"案件号^|客户号^|出险人姓名^|证件号码^|立案日期^|流程状态^|当前操作人^|"
							+"交接流水号^|索赔金额^|";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseFlowSql");
	tSQLInfo.setSqlId("LLClaimCaseFlowSql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.FlowState.value);
	tSQLInfo.addSubPara(fm.ClaimUserCode.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}