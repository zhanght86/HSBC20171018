/***************************************************************
 * <p>ProName：LCGrpQueryManagerInput.js</p>
 * <p>Title：查询客户经理</p>
 * <p>Description：查询客户经理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-13
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询客户经理信息
 */
function queryManager() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
	tSQLInfo.setSqlId("LCGrpQueryManagerSql1");
	tSQLInfo.addSubPara(fm.PreCustomerNo.value);
	tSQLInfo.addSubPara(fm.PreCustomerName.value);
	tSQLInfo.addSubPara(tManageCom);
	turnPage1.queryModal(tSQLInfo.getString(), ManagerGrid);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/***
 * 返回准客户名
 */
function returnManager() {
	
	var tSelNo = ManagerGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条客户经理信息记录！");
		return false;
	}
	var tManagerrNo = ManagerGrid.getRowColData(tSelNo,1);
	var tManagerName = ManagerGrid.getRowColData(tSelNo,2);
	var tManageCode = ManagerGrid.getRowColData(tSelNo,3);
	
	top.opener.AgentDetailGrid.setRowColData(tMulLineNo,1,tManagerrNo);
	top.opener.AgentDetailGrid.setRowColData(tMulLineNo,2,tManagerName);
	top.opener.AgentDetailGrid.setRowColData(tMulLineNo,3,tManageCode);
	top.close();
}

