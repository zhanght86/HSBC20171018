/***************************************************************
 * <p>ProName：LSQuotQueryAppComInput.js</p>
 * <p>Title：查询适用机构</p>
 * <p>Description：查询适用机构</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-03-29
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询适用机构
 */
function queryAppCom() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql7");
	tSQLInfo.addSubPara(fm.ComNo.value);
	tSQLInfo.addSubPara(fm.ComName.value);
	
	initAppComGrid();
	turnPage1.queryModal(tSQLInfo.getString(), AppComGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/***
 * 返回机构信息
 */
function returnCom() {
	
	var tSelNo = AppComGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条适用机构信息记录！");
		return false;
	}
	var tComNo = AppComGrid.getRowColData(tSelNo,1);
	var tComName = AppComGrid.getRowColData(tSelNo,2);
	top.opener.AppOrgCodeGrid.setRowColData(tMulLineNo,1,tComNo);
	top.opener.AppOrgCodeGrid.setRowColData(tMulLineNo,2,tComName);
	top.close();
}

