/***************************************************************
 * <p>ProName：LLClaimQuestionQueryInput.js</p>
 * <p>Title：问题件查询</p>
 * <p>Description：问题件查询</p>
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
	tSQLInfo.setResourceName("g_claim.LLClaimQuestionQuerySql");
	tSQLInfo.setSqlId("LLClaimQuestionQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.InputStartDate.value);
	tSQLInfo.addSubPara(fm.InputEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.DealStartDate.value);
	tSQLInfo.addSubPara(fm.DealEndDate.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(mManageCom);	
						
	turnPage1.queryModal(tSQLInfo.getString(),QuestionListGrid, 2);
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
	var tTitle = "团体批次号^|案件号^|交接流转号^|投保人名称^|被保险人姓名^|"
							+"问题件类型编码^|问题件类型^|问题件描述^|处理状态^|问题件发起人^|发起日期^|"
							+"问题件处理日期^|问题件时效^|发起机构";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimQuestionQuerySql");
	tSQLInfo.setSqlId("LLClaimQuestionQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.InputStartDate.value);
	tSQLInfo.addSubPara(fm.InputEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.DealStartDate.value);
	tSQLInfo.addSubPara(fm.DealEndDate.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}