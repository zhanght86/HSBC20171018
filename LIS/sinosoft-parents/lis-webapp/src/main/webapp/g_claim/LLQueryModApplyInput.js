/***************************************************************
 * <p>ProName：LLQueryModApplyInput.js</p>
 * <p>Title：保项修改申请查询</p>
 * <p>Description：保项修改申请查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王超
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();

var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();



// 查询保项

function queryClick() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql9");	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryRuleType.value); 
	tSQLInfo.addSubPara(fm.QueryRiskCode.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.ClmmodifyState.value); 
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryApplyDate.value);
	tSQLInfo.addSubPara(fm.QueryEndApplyDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryModItemGrid, 2, 1);
	if (!turnPage1.strQueryResult) {                       
		alert("未查询到符合条件的查询结果！");
		return false;
	}else{
		clearData();
	}
}


function showClmModApplyInfo() {
	
	var tSelNo = QueryModItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的列表信息！");
		return false;
	}
	
	fm.ReasonType.value = QueryModItemGrid.getRowColData(tSelNo-1, 2);
	fm.ReasonTypeName.value = QueryModItemGrid.getRowColData(tSelNo-1, 3);
	fm.RuleType.value = QueryModItemGrid.getRowColData(tSelNo-1, 4);
	fm.RuleTypeName.value = QueryModItemGrid.getRowColData(tSelNo-1, 5);
	fm.RiskCode.value = QueryModItemGrid.getRowColData(tSelNo-1, 6);
	fm.RiskCodeName.value = QueryModItemGrid.getRowColData(tSelNo-1, 7);
	fm.AdjustDirection.value = QueryModItemGrid.getRowColData(tSelNo-1, 8);
	fm.AdjustDirectionName.value = QueryModItemGrid.getRowColData(tSelNo-1, 9);
	fm.UpAdjustRule.value = QueryModItemGrid.getRowColData(tSelNo-1, 10);
	fm.UpAdjustRuleName.value = QueryModItemGrid.getRowColData(tSelNo-1, 11);
	fm.UpAdjustRate.value = QueryModItemGrid.getRowColData(tSelNo-1, 12);
	fm.StartDate.value = QueryModItemGrid.getRowColData(tSelNo-1, 13);
	fm.EndDate.value = QueryModItemGrid.getRowColData(tSelNo-1, 14);
	fm.Remark.value = QueryModItemGrid.getRowColData(tSelNo-1, 15);
	fm.ApplyNo.value = QueryModItemGrid.getRowColData(tSelNo-1, 18);
	fm.ApplyBatchNo.value = QueryModItemGrid.getRowColData(tSelNo-1, 19);
	fm.ApplyState.value = QueryModItemGrid.getRowColData(tSelNo-1, 20);
	
	if (fm.AdjustDirection.value=="02") {
	   	fm.all("UpAdjustRuleTitle").style.display = "none";
		fm.all("UpAdjustRuleInput").style.display = "none";
		fm.all("UpAdjustRateTitle").style.display = "none";
		fm.all("UpAdjustRateInput").style.display = "none";
	} else {
		fm.all("UpAdjustRuleTitle").style.display = "";
		fm.all("UpAdjustRuleInput").style.display = "";
		fm.all("UpAdjustRateTitle").style.display = "";
		fm.all("UpAdjustRateInput").style.display = "";
				
	}
	
	if(fm.RuleType.value=='00' || fm.RuleType.value==""){
	
		if(fm.ApplyState.value=="0" || fm.ApplyState.value=="1"){
			fm.all("CheckInfo").style.display="none";
			fm.all("ApproveInfo").style.display="none";	
		}else if(fm.ApplyState.value=="2"){
			fm.all("CheckInfo").style.display="";
			fm.all("ApproveInfo").style.display="none";	
			queryCheckInfo();
		}else if(fm.ApplyState.value=="3" || fm.ApplyState.value=="4"){
			fm.all("CheckInfo").style.display="";
			fm.all("ApproveInfo").style.display="";	
			queryCheckInfo();
			queryApproveInfo();
		}
		fm.all("divGrpCont").style.display="none";
		fm.all("divGrpContPlan").style.display="none";
			
	}else if(fm.RuleType.value=='01'){

		if(fm.ApplyState.value=="0" || fm.ApplyState.value=="1"){
			fm.all("CheckInfo").style.display="none";
			fm.all("ApproveInfo").style.display="none";	
		}else if(fm.ApplyState.value=="2"){
			fm.all("CheckInfo").style.display="";
			fm.all("ApproveInfo").style.display="none";
			queryCheckInfo();
		}else if(fm.ApplyState.value=="3" || fm.ApplyState.value=="4"){
			fm.all("CheckInfo").style.display="";
			fm.all("ApproveInfo").style.display="";	
			queryCheckInfo();
			queryApproveInfo();
		}
		fm.all("divGrpCont").style.display="";
		fm.all("divGrpContPlan").style.display="none";
		queryAcceptGrpClick();
		
	}else{
	
	 	if(fm.ApplyState.value=="0" || fm.ApplyState.value=="1"){
			fm.all("CheckInfo").style.display="none";
			fm.all("ApproveInfo").style.display="none";
			queryAcceptGrpaClick();	
		}else if(fm.ApplyState.value=="2"){
			fm.all("CheckInfo").style.display="";
			fm.all("ApproveInfo").style.display="none";
			queryCheckInfo();
			queryAcceptGrpaClick();
		}else if(fm.ApplyState.value=="3" || fm.ApplyState.value=="4"){
			fm.all("CheckInfo").style.display="";
			fm.all("ApproveInfo").style.display="";	
			queryCheckInfo();
			queryApproveInfo();
			queryAcceptGrpaClick();
		}
		fm.all("divGrpCont").style.display="none";
		fm.all("divGrpContPlan").style.display="";		 	
	}
	
}

//清空数据
function clearData() {
	
	fm.ReasonType.value = "";
	fm.ReasonTypeName.value = "";
	fm.RuleType.value = "";
	fm.RuleTypeName.value = "";
	fm.RiskCode.value = "";
	fm.RiskCodeName.value = "";
	fm.AdjustDirection.value = "";
	fm.AdjustDirectionName.value = "";
	fm.UpAdjustRule.value = "";
	fm.UpAdjustRuleName.value = "";
	fm.UpAdjustRate.value = "";
	fm.StartDate.value = "";
	fm.EndDate.value = "";
	fm.Remark.value = "";
	fm.ApplyNo.value = "";
	fm.ApplyBatchNo.value = "";
	fm.CheckConclusion.value="";
	fm.CheckConclusionName.value="";	
	fm.CheckIdea.value="";
	fm.ApproveConclusion.value="";
	fm.ApproveConclusionName.value="";	
	fm.ApproveIdea.value="";
	
	initAcceptGrpGrid();
	initAcceptGrpaGrid();
}


//复核信息查询
function queryCheckInfo(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql8");
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	var tCheckInfo = document.getElementById("CheckInfo");
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length<=0) {
		tCheckInfo.style.display = "none";
		return false;
		
	} else {
		
		tCheckInfo.style.display = "";
		}
		
		if (tArr.length>=1) {
			fm.CheckConclusion.value=tArr[0][0];
			fm.CheckConclusionName.value=tArr[0][1];
			fm.CheckIdea.value=tArr[0][2];
		}
}

//审批信息查询
function queryApproveInfo(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql10");
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	var tApproveInfo = document.getElementById("ApproveInfo");
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tArr==null || tArr.length<=0) {
		tApproveInfo.style.display = "none";
		return false;
		
	} else {
		
		tApproveInfo.style.display = "";
		}
		
		if (tArr.length>=1) {
		
			fm.ApproveConclusion.value=tArr[0][0];
			fm.ApproveConclusionName.value=tArr[0][1];
			fm.ApproveIdea.value=tArr[0][2];
		}
}


//已选择保单信息
function queryAcceptGrpClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql3");	
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	tSQLInfo.addSubPara(tManageCom);
	
	turnPage2.pageLineNum = 1000;
	turnPage2.queryModal(tSQLInfo.getString(), AcceptGrpGrid, 2, 1);
	
	initAcceptGrpPlanGrid();
	initPlanGrid();
}


//已选择保单方案信息
function queryAcceptGrpaClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimModApplySql");
	tSQLInfo.setSqlId("LLClaimModApplySql6");
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	tSQLInfo.addSubPara(fm.ApplyNo.value); 
	tSQLInfo.addSubPara(fm.ApplyBatchNo.value);
	
	turnPage3.pageLineNum = 1000;
	turnPage3.queryModal(tSQLInfo.getString(), AcceptGrpaGrid, 2, 1);
}


