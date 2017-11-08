/***************************************************************
 * <p>ProName：LLClaimLastCaseInput.js</p>
 * <p>Title：赔案查询</p>
 * <p>Description：赔案查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tSQLInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

/**
 * 查询按钮
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoAcceptQuerySql");
	tSQLInfo.setSqlId("LLClaimNoAcceptQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);	
						
	turnPage1.queryModal(tSQLInfo.getString(),NoAcceptListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "团体批次号^|案件号^|投保人编码^|投保人名称^|被保险人编码^|被保险人姓名^|"
							+"性别^|出生日期^|证件类型^|证件号码^|申请金额^|账单数^|未受理原因^|"
							+"操作人^|操作时间";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoAcceptQuerySql");
	tSQLInfo.setSqlId("LLClaimNoAcceptQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}