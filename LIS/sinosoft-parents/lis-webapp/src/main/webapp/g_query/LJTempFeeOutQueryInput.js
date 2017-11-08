/***************************************************************
 * <p>ProName：LJTempFeeOutQueryInput.jsp</p>
 * <p>Title: 暂收退费查询</p>
 * <p>Description：暂收退费查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-08-31
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
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql4");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryTempFeeNo.value);
	tSQLInfo.addSubPara(fm.QueryInBankCode.value);
	tSQLInfo.addSubPara(fm.QueryInBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryAccNo.value);
	tSQLInfo.addSubPara(fm.QueryAccName.value);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	var tQueryPayState = fm.QueryPayState.value;
	if (tQueryPayState=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.QueryPayState.value);
	} else if (tQueryPayState=="1") {
		tSQLInfo.addSubPara(fm.QueryPayState.value);
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=='infinbank') {
		
		var tSql = "1 and a.fincomcode in (select b.findb from ldcom b where b.comcode like #"+ tManageCom +"%#)";
		if (returnType=='0') {
			return showCodeList('infinbank',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('infinbank',value2,value3,null,tSql,'1','1',null);
		}
	}
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "查询结果列表";
	
	var tTitle = "管理机构^|暂收费号^|交费方式^|金额^|客户开户行^|客户开户行所在省^|客户开户行所在市^|客户银行账号^|客户账户名^|付款单位" +
			"^|支票号^|收款银行^|收款账号^|付费状态^|申请人^|申请日期^|审核人^|审核日期^|审核结论";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeOutSql");
	tSQLInfo.setSqlId("LJTempFeeOutSql6");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryTempFeeNo.value);
	tSQLInfo.addSubPara(fm.QueryInBankCode.value);
	tSQLInfo.addSubPara(fm.QueryInBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryAccNo.value);
	tSQLInfo.addSubPara(fm.QueryAccName.value);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	var tQueryPayState = fm.QueryPayState.value;
	if (tQueryPayState=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.QueryPayState.value);
	} else if (tQueryPayState=="1") {
		tSQLInfo.addSubPara(fm.QueryPayState.value);
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
