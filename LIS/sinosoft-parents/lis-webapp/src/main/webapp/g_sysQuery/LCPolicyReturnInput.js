/***************************************************************
 * <p>ProName：LCPolicyReturnInput.js</p>
 * <p>Title：回执查询</p>
 * <p>Description：回执查询</p>
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
	if(!verifyInput2()){
		return false;
	}
	
	if(fm.SignStartDate.value !="" && fm.SignEndDate.value !="" ){
		 if(fm.SignStartDate.value > fm.SignEndDate.value){
		 	alert("签单起期需小于签单止期！" );
		 	return false;
		 }
	}
	if(fm.ReturnStartDate.value !="" && fm.ReturnEndDate.value !="" ){
		 if(fm.ReturnStartDate.value > fm.ReturnEndDate.value){
		 	alert("回执回销起期需小于回执回销止期！" );
		 	return false;
		 }
	}
	if(fm.CardState.value=='3'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql3");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}else if(fm.CardState.value=='6'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql2");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}else{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql1");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
	
}

/**
 * 导出数据
 */
function exportClick() {

	if (!verifyInput2()) {
		return false;
	}
	if(fm.SignStartDate.value !="" && fm.SignEndDate.value !="" ){
		 if(fm.SignStartDate.value > fm.SignEndDate.value){
		 	alert("签单起期需小于签单止期！" );
		 	return false;
		 }
	}
	if(fm.ReturnStartDate.value !="" && fm.ReturnEndDate.value !="" ){
		 if(fm.ReturnStartDate.value > fm.ReturnEndDate.value){
		 	alert("回执回销起期需小于回执回销止期！" );
		 	return false;
		 }
	}
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "管理机构^|团体保单号^|投保人名称^|客户经理编码^|客户经理^|签单日期^|签收日期^|回销日期^|状态";
	
	if(fm.CardState.value=='3'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql3");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}else if(fm.CardState.value=='6'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql2");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}else{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql1");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}


//重置
function resetClick(){
	
	fm.ManageCom.value='';
	fm.ManageComName.value='';
	fm.GrpContNo.value='';
	fm.GrpName.value='';
	fm.SignStartDate.value='';
	fm.SignEndDate.value='';
	fm.CardState.value='';
	fm.CardStateName.value='';
	fm.ReturnStartDate.value='';
	fm.ReturnEndDate.value='';
	
}
