/***************************************************************
 * <p>ProName：LJTempFeeCancelInput.js</p>
 * <p>Title：财务作废</p>
 * <p>Description：财务作废</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryApplyTempFee() {
	
	initTempFeeInfoGrid();
	initTempFeeDetailInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql5");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInputStartDate.value);
	tSQLInfo.addSubPara(fm.QueryInputEndDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmStartDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), TempFeeInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 选择时处理
 */
function showTempFeeInfo() {
	
	initTempFeeDetailInfoGrid();
	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 3);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql6");
	tSQLInfo.addSubPara(tTempFeeNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), TempFeeDetailInfoGrid, 0, 1);
}

/**
 * 初始查询数据
 */
function initQueryInfo() {

	fm.QueryPayType.value = "";
	fm.QueryPayTypeName.value = "";
	fm.QueryCustBankCode.value = "";
	fm.QueryCustBankName.value = "";
	fm.QueryCustBankAccNo.value = "";
	fm.QueryCustAccName.value = "";
	fm.QueryGrpName.value = "";
	fm.QueryAgentName.value = "";
	fm.QueryApplyStartDate.value = "";
	fm.QueryApplyEndDate.value = "";
	fm.QueryInputStartDate.value = "";
	fm.QueryInputEndDate.value = "";
	fm.QueryConfirmStartDate.value = "";
	fm.QueryConfirmEndDate.value = "";
	fm.State.value = "";
	fm.StateName.value = "";
	fm.QueryManageCom.value = "";
	fm.QueryManageComName.value = "";
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "进账信息列表";
	
	var tTitle = "管理机构^|暂收费号^|进账状态^|交费方式^|客户开户行^|客户银行账号^|客户账户名^|金额" +
			"^|当前使用金额^|锁定金额^|投保单位名称^|客户经理^|申请人^|申请日期^|到账日期^|收款银行^|收款账号" +
			"^|付款单位^|支票号^|录入人^|录入日期^|审核人^|审核日期";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql8");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInputStartDate.value);
	tSQLInfo.addSubPara(fm.QueryInputEndDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmStartDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
