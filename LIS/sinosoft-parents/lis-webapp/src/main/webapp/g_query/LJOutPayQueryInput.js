/***************************************************************
 * <p>ProName：LJTempFeeOutQueryInput.jsp</p>
 * <p>Title: 暂收退费查询</p>
 * <p>Description：暂收退费查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-08-31
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryInfo() {
	
	initQueryInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	
	
	  if(_DBT==_DBO){
		  tSQLInfo.setSqlId("LJOutPayOutSql4");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LJOutPayOutSql7");
	   }
	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	
	var tQueryPayState = fm.QueryPayState.value;
	if (tQueryPayState=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.QueryPayState.value);
	} else if (tQueryPayState=="1") {
		tSQLInfo.addSubPara(fm.QueryPayState.value);
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}
/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "查询结果列表";
	
	var tTitle = "管理机构^|申请批次号^|保单号^|投保人名称^|中介机构名称^|溢缴金额(元)^|开户行^|开户行所在省^|开户行所在市^|银行账号^|账户名^|付费状态^|申请人^|申请日期^|审核人^|审核日期^|审核结论";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	  if(_DBT==_DBO){
		  tSQLInfo.setSqlId("LJOutPayOutSql5");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LJOutPayOutSql8");
	   }
	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	
	var tQueryPayState = fm.QueryPayState.value;
	if (tQueryPayState=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.QueryPayState.value);
	} else if (tQueryPayState=="1") {
		tSQLInfo.addSubPara(fm.QueryPayState.value);
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
