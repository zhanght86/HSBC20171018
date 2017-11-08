/***************************************************************
 * <p>ProName：LJSendDataQueryInput.jsp</p>
 * <p>Title：付费数据查询</p>
 * <p>Description：付费数据查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-11-18
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
	
	if (isEmpty(fm.QueryStartDate1) || isEmpty(fm.QueryEndDate1)) {
		alert("业务确认起止期必录！");
		return false;
	}
	
	initDataInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LJSendDataQuerySql");
	tSQLInfo.setSqlId("LJSendDataQuerySql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryFinBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryGetMode.value);
	tSQLInfo.addSubPara(fm.QueryFinState.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryGetDealType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
		tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), DataInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
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
	
	if (value1=='conditioncomcode') {
	
		var tSql = " 1 and comgrade in (#01#,#02#) and comcode like #"+ tManageCom +"%%#";
		
		if (returnType=='0') {
			return showCodeList('conditioncomcode',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('conditioncomcode',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='finbusstype') {
		
		var tSql = "1 and codetype=#finbusstype# and codeexp=#get#";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	}
}

//数据导出
function exportData() {
	
	if (isEmpty(fm.QueryStartDate1) || isEmpty(fm.QueryEndDate1)) {
		alert("业务确认起止期必录！");
		return false;
	}
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "处理方式^|管理机构编码^|管理机构^|团体保单号^|团体投保人名称^|业务类型^|"
							+"业务号码^|付费金额^|支付方式^|状态^|业务确认日期^|发盘可提取日期^|"
							+"转账日期^|客户银行^|客户银行账号^|客户账户名^|领取人^|领取人证件号码";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LJSendDataQuerySql");
	tSQLInfo.setSqlId("LJSendDataQuerySql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryFinBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryGetMode.value);
	tSQLInfo.addSubPara(fm.QueryFinState.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryGetDealType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
		tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
