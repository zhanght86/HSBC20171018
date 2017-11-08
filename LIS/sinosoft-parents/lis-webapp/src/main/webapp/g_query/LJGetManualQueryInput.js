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
	
	initBatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql8");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryBatchNo1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), BatchInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function queryInfo1() {
	
	initBatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql9");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom2.value);
	tSQLInfo.addSubPara(fm.QueryBussType2.value);
	tSQLInfo.addSubPara(fm.QueryBussNo2.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryBatState2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate4.value);
	tSQLInfo.addSubPara(fm.QueryEndDate4.value);
	tSQLInfo.addSubPara(fm.QueryStartDate5.value);
	tSQLInfo.addSubPara(fm.QueryEndDate5.value);
	tSQLInfo.addSubPara(fm.QueryBatchNo2.value);
	tSQLInfo.addSubPara(fm.QueryInsureCom2.value);
	tSQLInfo.addSubPara(fm.QueryGetDealType.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), QueryInfoGrid, 1, 1);
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
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
		
		var tSql = "finbusstypeget";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	} else if (value1=='outfinbank') {
		
		var tSql = "1 and a.fincomcode in (select b.findb from ldcom b where b.managecom like #"+ tManageCom +"%#)";
		if (returnType=='0') {
			return showCodeList('outfinbank',value2,value3,null,tSql,'and 1','1',null);
		} else {
			return showCodeListKey('outfinbank',value2,value3,null,tSql,'and 1','1',null);
		}
	}
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "手动付费信息列表";
	
	var tTitle = "处理方式^|管理机构^|批次号^|实付号^|保单号^|业务类型^|业务号码^|应付金额^|应付日期" +
			"^|状态^|付款日期^|审核人^|审核日期^|共保公司名称^|确认人^|确认日期^|结论描述";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql9");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom2.value);
	tSQLInfo.addSubPara(fm.QueryBussType2.value);
	tSQLInfo.addSubPara(fm.QueryBussNo2.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryBatState2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate4.value);
	tSQLInfo.addSubPara(fm.QueryEndDate4.value);
	tSQLInfo.addSubPara(fm.QueryStartDate5.value);
	tSQLInfo.addSubPara(fm.QueryEndDate5.value);
	tSQLInfo.addSubPara(fm.QueryBatchNo2.value);
	tSQLInfo.addSubPara(fm.QueryInsureCom2.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
