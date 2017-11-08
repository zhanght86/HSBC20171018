/***************************************************************
 * <p>ProName：LCGrpPropQueryInput.js</p>
 * <p>Title：投保单查询</p>
 * <p>Description：投保单查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	
	initQueryResultGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpPropQuerySql");
	tSQLInfo.setSqlId("LCGrpPropQuerySql1");
	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.Salechnl.value);
	tSQLInfo.addSubPara(fm.AgentCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
	/**}else{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpPropQuerySql");
		tSQLInfo.setSqlId("LCGrpPropQuerySql1");
		
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.Salechnl.value);
		tSQLInfo.addSubPara(fm.AgentCode.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 1, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
	**/
}

/**
* 保单明细查询
*/

function policyQuery(){
	var tSelNo = QueryResultGrid.getSelNo();
	
	if(tSelNo<1){
		alert("请选择一条投保单信息!");
		return false;
	}
	var tGrpPropNo = QueryResultGrid.getRowColData(tSelNo-1,2);
	var tContType = QueryResultGrid.getRowColData(tSelNo-1,10);
	window.open("./LCBussQueryMain.jsp?Page=./LCGrpContPolInput.jsp&ContPlanType="+tContType+"&GrpPropNo="+tGrpPropNo ,"投保单查询",'width=1366,height=768,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//重置
function resetClick(){

	fm.ManageCom.value='';
	fm.ManageComName.value='';
	fm.GrpPropNo.value='';
	fm.Salechnl.value='';
	fm.SalechnlName.value='';
	fm.AgentCode.value='';
	fm.AgentCodeName.value='';
}