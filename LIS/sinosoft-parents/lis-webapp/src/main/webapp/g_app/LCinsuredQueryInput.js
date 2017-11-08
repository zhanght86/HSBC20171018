/***************************************************************
 * <p>ProName：LCinsuredQueryInput.js</p>
 * <p>Title：被保人查询</p>
 * <p>Description：被保人查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();


/**
 * 选中一条记录后返回上级页面
 */
function returnback() {
	
	var selno = CustomerGrid.getSelNo();
	if (selno < 1) {
		alert("请选中一行客户信息！");
		return false;
	}
	tArr = CustomerGrid.getRowData(selno-1);
	
	var tFlag = fm.Flag.value;
	if(tFlag =='01'){
		top.opener.showUserInfo(tArr);
		}else if(tFlag == '02'){
		top.opener.showMainUserInfo(tArr);	
		}
	top.close();	
}

/**
 * 查询客户信息
 */
function queryCustomer() {
	
	tSQLInfo.setResourceName("g_app.LCinsuredQuerySql");
	tSQLInfo.setSqlId("LCinsuredQuerySql1");
	tSQLInfo.addSubPara(fm.InsuredName.value);
	
	var res = turnPage1.queryModal(tSQLInfo.getString(), CustomerGrid);
}

/**
 * 查询主被保人客户信息
 */
function queryMainCustomer(){

	tSQLInfo.setResourceName("g_app.LCinsuredQuerySql");
	tSQLInfo.setSqlId("LCinsuredQuerySql2");
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	var res = turnPage1.queryModal(tSQLInfo.getString(), CustomerGrid);
}






