/***************************************************************
 * <p>ProName：LCGrpQueryAgentComInput.js</p>
 * <p>Title：查询中介机构</p>
 * <p>Description：查询中介机构</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-14
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询信息
 */
function queryAgent() {
	if(tFlag=='0'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
		tSQLInfo.setSqlId("LCGrpQueryManagerSql3");
		tSQLInfo.addSubPara(fm.AgentName.value);
		tSQLInfo.addSubPara(NullToEmpty(tSaleChnl));
		tSQLInfo.addSubPara(tManageCom);
		turnPage1.queryModal(tSQLInfo.getString(), AgentGrid, 1, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}	
	} else if(tFlag=='1') {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
		tSQLInfo.setSqlId("LCGrpQueryManagerSql4");
		tSQLInfo.addSubPara(fm.AgentName.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(NullToEmpty(tSaleDepart));
		tSQLInfo.addSubPara(NullToEmpty(tChnlType));
		tSQLInfo.addSubPara(NullToEmpty(tSaleChnl));
		turnPage1.queryModal(tSQLInfo.getString(), AgentGrid,1,1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	} else if(tFlag=='2') {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
		tSQLInfo.setSqlId("LCGrpQueryManagerSql6");
		tSQLInfo.addSubPara(fm.AgentName.value);
		tSQLInfo.addSubPara(NullToEmpty(tSaleChnl));
		tSQLInfo.addSubPara(tManageCom);
		turnPage1.queryModal(tSQLInfo.getString(), AgentGrid,1,1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/***
 * 返回信息
 */
function returnAgent() {
	if(tFlag=='0'){
		var tSelNo = AgentGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("请选中一条中介机构信息记录！");
			return false;
		}
		var tAgentCom = AgentGrid.getRowColData(tSelNo,1);
		var tAgentName = AgentGrid.getRowColData(tSelNo,2);
		
		top.opener.AgentComGrid.setRowColData(tMulLineNo,1,tAgentCom);
		top.opener.AgentComGrid.setRowColData(tMulLineNo,2,tAgentName);
		top.close();
	}else if(tFlag=='1'){
		var tSelNo = AgentGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("请选中一条网点机构信息记录！");
			return false;
		}
		var tAgentCom = AgentGrid.getRowColData(tSelNo,1);
		var tAgentName = AgentGrid.getRowColData(tSelNo,2);
		
		top.opener.AgentComGrid.setRowColData(tMulLineNo,3,tAgentCom);
		top.opener.AgentComGrid.setRowColData(tMulLineNo,4,tAgentName);
		top.close();
	}else{
		var tSelNo = AgentGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("请选中一条中介机构信息记录！");
			return false;
		}
		var tAgentCom = AgentGrid.getRowColData(tSelNo,1);
		var tAgentName = AgentGrid.getRowColData(tSelNo,2);
		
		top.opener.ZJGrid.setRowColData(tMulLineNo,1,tAgentCom);
		top.opener.ZJGrid.setRowColData(tMulLineNo,2,tAgentName);
		top.close();
	}
}

