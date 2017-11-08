/***************************************************************
 * <p>ProName：PreCustomerQueryInput.js</p>
 * <p>Title：准客户维护查询界面</p>
 * <p>Description：准客户维护查询界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 新增操作
 */
function addClick() {
	
	window.location = "./PreCustomerManageInput.jsp?Flag=1";
}

/**
 * 查询操作
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql1");
	tSQLInfo.addSubPara(fm.PreCustomerName.value);
	
	if (tFlag=="1") {//准客户维护查询
		
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara("");
	} else if (tFlag=="2") {//准客户解锁查询
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tManageCom);
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), PreCustomerGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 选中操作
 */
function GotoDetail() {
	
	var tSelNo = PreCustomerGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tPreCustomerNo = PreCustomerGrid.getRowColData(tSelNo-1, 1);
	
	if (tFlag=="1") {//准客户维护选中后
		
		window.location = "./PreCustomerManageInput.jsp?Flag=2&PreCustomerNo="+tPreCustomerNo;
	} else if (tFlag=="2") {//准客户解锁选中后
		
		window.location = "./PreCustomerUnlockInput.jsp?PreCustomerNo="+tPreCustomerNo;
	}
	
}

//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "准客户号码^|准客户名称^|单位性质^|行业分类^|预计投保总人数^|预计保费规模(元)^|是否承保";
	
	//报表提数SQL	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql1");
	tSQLInfo.addSubPara(fm.PreCustomerName.value);
	if (tFlag=="1") {//准客户维护查询
		
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara("");
	} else if (tFlag=="2") {//准客户解锁查询
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tManageCom);
	}
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	fm.submit();
}
