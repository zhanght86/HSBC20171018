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
 * 查询保单列表信息
 */
function queryListInfo() {

	tSQLInfo = new SqlClass();

	//mode--1:理赔保单查询；2-其他保单查询
	if (mMode==1) {
		
		tSQLInfo.setResourceName("g_claim.LLClaimQueryPolicySql");
		tSQLInfo.setSqlId("LLClaimQueryPolicySql");		
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(mRgtNo);		
	} else if (mMode==2) {
		
		tSQLInfo.setResourceName("g_claim.LLClaimQueryPolicySql");
		tSQLInfo.setSqlId("LLClaimQueryPolicySql1");		
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(mManageCom);
	}
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),PolicyListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		top.close();
		return false;
	}
}
/**
 * 展示选中的保单明细
 */
function showDetailInfo () {
	
	var tSelNo = PolicyListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条保单信息");
		return false;
	}
	
	var tGrpContNo = PolicyListGrid.getRowColData(tSelNo,2);
	var tGrpPropNo = PolicyListGrid.getRowColData(tSelNo,3);	

	var strUrl="../g_app/LCGrpContPolInput.jsp?GrpPropNo="+ tGrpContNo+"&GrpContNo="+tGrpPropNo+"&Flag=3" ;		
	window.open(strUrl,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		
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