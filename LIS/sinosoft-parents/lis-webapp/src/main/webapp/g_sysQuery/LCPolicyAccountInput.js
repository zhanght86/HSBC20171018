/***************************************************************
 * <p>ProName：LCPolicyAccountInput.js</p>
 * <p>Title：账户信息查询</p>
 * <p>Description：账户信息查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	initResultInfoGrid();
	if(!verifyInput2()){
		return false;
	}
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
		tSQLInfo.setSqlId("PolicyAccountSql1");
		
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ContNo.value);
		tSQLInfo.addSubPara(fm.InsuredName.value);
		tSQLInfo.addSubPara(fm.IDNo.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 1, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	
}

/**
 * 导出数据
 */
function exportClick() {

	if (!verifyInput2()) {
		return false;
	}
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "团体保单号^|投保人名称^|客户号^|个人保单号^|姓名^|证件号码^|险种编码^|账户名称^|交费名称^|账户余额^|承保机构";
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
		tSQLInfo.setSqlId("PolicyAccountSql3");
		
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ContNo.value);
		tSQLInfo.addSubPara(fm.InsuredName.value);
		tSQLInfo.addSubPara(fm.IDNo.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

function  queryInfo(){
	
	var tSelNo = QueryResultGrid.getSelNo();
	var tPolNo = QueryResultGrid.getRowColData(tSelNo-1,5);
	var tInsuAccNo = QueryResultGrid.getRowColData(tSelNo-1,9);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
	tSQLInfo.setSqlId("PolicyAccountSql2");
		
	tSQLInfo.addSubPara(tPolNo);
	tSQLInfo.addSubPara(tInsuAccNo);
		
	turnPage2.queryModal(tSQLInfo.getString(),ResultInfoGrid, 1, 1);
		
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}

}

/**
 * 导出数据
 */
function exportClick1() {

	var tSelNo = QueryResultGrid.getSelNo();
	if(tSelNo<1){
		alert("请选择一条被保险人信息");
		return false;
	}
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	var tPolNo = QueryResultGrid.getRowColData(tSelNo-1,5);
	var tInsuAccNo = QueryResultGrid.getRowColData(tSelNo-1,9);
	//报表标题
	var tTitle = "业务号^|业务类型编码^|业务类型名称^|业务日期^|变更类型编码^|变更类型^|变更金额";
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
		tSQLInfo.setSqlId("PolicyAccountSql2");
		
		tSQLInfo.addSubPara(tPolNo);
		tSQLInfo.addSubPara(tInsuAccNo);

	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

function resetClick(){
	fm.GrpContNo.value='';
	fm.GrpName.value='';
	fm.ManageCom.value='';
	fm.ManageComName.value='';
	fm.ContNo.value='';
	fm.InsuredName.value='';
	fm.IDNo.value='';
	
	initQueryResultGrid();
	initResultInfoGrid();
}
