/***************************************************************
 * <p>ProName：LLClaimQueryCustInput.js</p>
 * <p>Title：系统被保人查询</p>
 * <p>Description：系统被保人查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo;

/**
 * 查询被保人信息
 */
function queryCustomerList() {
	
	//个单查询
	if (mAppntNo==null || nullToEmpty(mAppntNo)=="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimQuerySql");
		tSQLInfo.setSqlId("LLClaimQuerySql")		
	} else {
		
		tQueryResultFlag = false;
		tQueryFlag = true;
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimQuerySql");
		tSQLInfo.setSqlId("LLClaimQuerySql2");
		tSQLInfo.addSubPara(mAppntNo);		
	}

	tSQLInfo.addSubPara(nullToEmpty(mCustomerName));
	tSQLInfo.addSubPara(nullToEmpty(mBirthday));
	tSQLInfo.addSubPara(nullToEmpty(mEmpNo));
	tSQLInfo.addSubPara(nullToEmpty(mIDNo));		
	tSQLInfo.addSubPara(nullToEmpty(mGrpRgtNo));
	
	turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}
/**
 * 返回客户信息
 */
function returnSelect() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条被保人信息");
		return false;
	}
	try {
		
		var returnArr = new Array();
		returnArr = CustomerListGrid.getRowData(tSelNo);
		if (returnArr!=null) {

			if (returnArr[0]==null || returnArr[0]=="") {
				alert("请先查询！");
				return false;
			} else {				
				top.opener.afterQueryCustomer(returnArr);
			}		
		} else {
			return false;
		}
	} catch(ex) {
		alert("返回异常："+ ex);
	}
	top.close();
}