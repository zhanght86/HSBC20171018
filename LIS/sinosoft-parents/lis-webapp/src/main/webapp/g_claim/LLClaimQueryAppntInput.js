/***************************************************************
 * <p>ProName：LLClaimQueryCustInput.js</p>
 * <p>Title：系统团体投保人查询</p>
 * <p>Description：系统团体投保人查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo;

/**
 * 查询团体投保人信息
 */
function queryGrpAppnt() {
	
	//个人保单投保人查询
	if (mContType!=null && mContType=="1") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimQueryAppntSql");
		tSQLInfo.setSqlId("LLClaimQueryAppntSql1");
		tSQLInfo.addSubPara(mAppntName);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mAcceptCom);
	} else if (mContType=="2") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimQueryAppntSql");
		tSQLInfo.setSqlId("LLClaimQueryAppntSql");
		tSQLInfo.addSubPara(mAppntName);
		tSQLInfo.addSubPara(mAcceptCom);
		tSQLInfo.addSubPara(mAppntName);
		tSQLInfo.addSubPara(mManageCom);		
	}	
	turnPage1.queryModal(tSQLInfo.getString(),GrpAppntListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}
/**
 * 返回客户信息
 */
function returnSelect() {
	
	var tSelNo = GrpAppntListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条理赔用户信息");
		return false;
	}
	try {
		
		var returnArr = new Array();
		returnArr = GrpAppntListGrid.getRowData(tSelNo);
		if (returnArr!=null) {

			if (returnArr[0]==null || returnArr[0]=="") {
				alert("请先查询！");
				return false;
			} else {				
				top.opener.afterQueryAppnt(returnArr);
			}		
		} else {
			return false;
		}
	} catch(ex) {
		alert("返回异常："+ ex);
	}
	top.close();
}

function showDetail() {
	
	var tSelNo = GrpAppntListGrid.getSelNo()-1;
	var tAppntType = GrpAppntListGrid.getRowColData(tSelNo,1);
/*	if (tAppntType==null || tAppntType!="1") {
		return false;
	}*/
	var tAppntNo = GrpAppntListGrid.getRowColData(tSelNo,2); 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimQueryAppntSql");
	tSQLInfo.setSqlId("LLClaimQueryAppntSql2");
	tSQLInfo.addSubPara(tAppntNo);
	turnPage2.queryModal(tSQLInfo.getString(),GrpAppntDetailGrid, 2);
}
