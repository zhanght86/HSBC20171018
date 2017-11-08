/***************************************************************
 * <p>ProName：LJTempFeeCancelInput.js</p>
 * <p>Title：财务作废</p>
 * <p>Description：财务作废</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryInfo() {
	
	initQueryInfoGrid();
	initQueryInfo1Grid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	if(_DBT == _DBO){
		tSQLInfo.setSqlId("LJModifyBankSql4");
	}else if(_DBT == _DBM){
		tSQLInfo.setSqlId("LJModifyBankSql12");
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryState.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function showInfo() {
	
	var tSelNo = QueryInfoGrid.getSelNo()-1;
	var tApplyBatNo = QueryInfoGrid.getRowColData(tSelNo, 1);
	
	initQueryInfo1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	tSQLInfo.setSqlId("LJModifyBankSql2");
	tSQLInfo.addSubPara(tApplyBatNo);

	turnPage2.queryModal(tSQLInfo.getString(), QueryInfo1Grid, 0, 1);
}
/**
 * 模拟下拉操作
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	if (value1=='finbusstype') {
		
		var tSql = "1 and codetype=#finbusstype# and codeexp=#get#";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	}
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "批次信息列表";
	
	var tTitle = "管理机构编码^|管理机构^|保单号^|投保人名称^|客户经理^|业务类型^|业务号码^|金额^|被保险人客户号^|被保险人姓名^|被保险人证件号码^|领取人姓名^|领取人证件号^|客户新开户银行^|客户新开户银行所在省^|客户新开户银行所在市" +
			"^|客户新银行账号^|客户新账户名^|审核状态^|审核描述^|失败原因";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	if(_DBT == _DBO){
		tSQLInfo.setSqlId("LJModifyBankSql5");
	}else if(_DBT == _DBM){
		tSQLInfo.setSqlId("LJModifyBankSql13");
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryState.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
