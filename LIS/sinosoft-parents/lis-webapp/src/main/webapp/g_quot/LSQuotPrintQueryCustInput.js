/***************************************************************
 * <p>ProName：LSQuotPrintQueryCustInput.js</p>
 * <p>Title：报价单打印--查询准客户名称</p>
 * <p>Description：报价单打印--查询准客户名称</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询准客户名
 */
function queryCustomer() {
	
	if (tQuotType == "00") {//一般询价
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintQueryCustSql");
		tSQLInfo.setSqlId("LSQuotPrintQueryCustSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(fm.PreCustomerNo.value);
		tSQLInfo.addSubPara(fm.PreCustomerName.value);
		
	} else if (tQuotType == "01") {//项目询价
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintQueryCustSql");
		tSQLInfo.setSqlId("LSQuotPrintQueryCustSql2");
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(fm.PreCustomerNo.value);
		tSQLInfo.addSubPara(fm.PreCustomerName.value);
	}
	
	initCustInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), CustInfoGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}

}

/***
 * 返回准客户名
 */
function returnCustomer() {
	
	var tSelNo = CustInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条准客户信息记录！");
		return false;
	}
	var tQuery_PreCustomerNo = CustInfoGrid.getRowColData(tSelNo,1);
	var tQuery_PreCustomerName = CustInfoGrid.getRowColData(tSelNo,2);
	var tQuery_QuotNo = CustInfoGrid.getRowColData(tSelNo,3);
	var tQuery_QuotBatNo = CustInfoGrid.getRowColData(tSelNo,4);
	
	top.opener.fm.PreCustomerNo.value = tQuery_PreCustomerNo;
	top.opener.fm.PreCustomerName.value = tQuery_PreCustomerName;
	top.opener.fm.Query_QuotNo.value = tQuery_QuotNo;
	top.opener.fm.Query_QuotBatNo.value = tQuery_QuotBatNo;
	top.opener.queryCustInfo();
	top.close();
}

