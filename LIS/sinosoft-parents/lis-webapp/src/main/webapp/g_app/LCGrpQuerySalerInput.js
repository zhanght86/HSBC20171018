/***************************************************************
 * <p>ProName：LCGrpQuerySalerInput.js</p>
 * <p>Title：查询代理人</p>
 * <p>Description：查询代理人</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-15
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询归属地客户经理信息
 */
function querySaler() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
	tSQLInfo.setSqlId("LCGrpQueryManagerSql5");
	tSQLInfo.addSubPara(fm.SalerName.value);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(tAgentCom);
	turnPage1.queryModal(tSQLInfo.getString(), SalerGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/***
 * 返回代理人信息
 */
function returnSaler() {
	
	var tSelNo = SalerGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条代理人信息记录！");
		return false;
	}
	var tSalerCode = SalerGrid.getRowColData(tSelNo,1);
	var tSalerName = SalerGrid.getRowColData(tSelNo,2);
	var tTeamName = SalerGrid.getRowColData(tSelNo,3);
	
	top.opener.AgentComGrid.setRowColData(tMulLineNo,3,tSalerCode);
	top.opener.AgentComGrid.setRowColData(tMulLineNo,4,tSalerName);
	top.opener.AgentComGrid.setRowColData(tMulLineNo,5,tTeamName);
	top.close();
}

