/***************************************************************
 * <p>ProName：LDQueryUserInput.js</p>
 * <p>Title：用户查询</p>
 * <p>Description：用户查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryUser() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_auxi.LDQueryUserSql");
	tSQLInfo.setSqlId("LDQueryUserSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.UserCode.value);
	tSQLInfo.addSubPara(fm.UserName.value);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	
	initUserGrid();
	turnPage1.queryModal(tSQLInfo.getString(), UserGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/***
 * 返回准客户名
 */
function returnUser() {
	
	var tSelNo = UserGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条用户信息记录！");
		return false;
	}
	var tUserCode = UserGrid.getRowColData(tSelNo,1);
	var tUserName = UserGrid.getRowColData(tSelNo,2);
		
	top.opener.fm.UserCode.value = tUserCode;
	top.opener.fm.UserName.value = tUserName;
	top.opener.fm.SupervisorFlag.value = "";
	top.opener.fm.SupervisorName.value = "";
	top.opener.fm.PopedomLevel.value = "";
	top.opener.fm.PopedomLevelName.value = "";
	
	top.close();
}

