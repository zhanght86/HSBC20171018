/***************************************************************
 * <p>ProName：PreCustomerTraceQueryInput.js</p>
 * <p>Title：准客户修改轨迹查询界面</p>
 * <p>Description：准客户修改轨迹查询界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询操作
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql6");
	tSQLInfo.addSubPara(tPreCustomerNo);
	//tSQLInfo.addSubPara(fm.PreCustomerName.value);
	tSQLInfo.addSubPara("");
	
	turnPage1.queryModal(tSQLInfo.getString(), PreCustomerGrid, 2,1);
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
	
	var tTraceID = PreCustomerGrid.getRowColData(tSelNo-1, 1);
	var tPreCustomerNo = PreCustomerGrid.getRowColData(tSelNo-1, 2);
	
	window.location = "./PreCustomerUnlockInput.jsp?TraceID="+tTraceID+"&PreCustomerNo="+tPreCustomerNo;
}
