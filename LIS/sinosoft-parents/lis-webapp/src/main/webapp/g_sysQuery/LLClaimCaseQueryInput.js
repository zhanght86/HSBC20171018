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
var turnPage2 = new turnPageClass();
turnPage2.pageLineNum = 100;

/**
 * 查询赔案
 */
function queryClick() {
	
	if (!checkBeforQuery()) {
		return false;
	}	
	initLastCaseListGrid();
	initLastCaseDetailGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseQuerySql");
	tSQLInfo.setSqlId("LLClaimCaseQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(fm.ClmState.value);

	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);	
	
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.AccStartDate.value);
	tSQLInfo.addSubPara(fm.AccEndDate.value);
	
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(mManageCom);
		
				
	turnPage1.queryModal(tSQLInfo.getString(),LastCaseListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}
/**
 * 展示选中的赔案赔付明细
 */
function showSelectDetail () {
	
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择赔案信息");
		return false;
	}
	var tRgtNo = LastCaseListGrid.getRowColData(tSelNo,2);
	var tCustomerNo = LastCaseListGrid.getRowColData(tSelNo,6);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseQuerySql");
	tSQLInfo.setSqlId("LLClaimCaseQuerySql1");
	
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),LastCaseDetailGrid, 2);	
}

/**
 * 查看批次明细信息
 */
function showBatchDetailInfo() {
		
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择赔案信息");
		return false;
	}
	var tGrpRgtNo = LastCaseListGrid.getRowColData(tSelNo,1);
	var tClmState = LastCaseListGrid.getRowColData(tSelNo,26);
	var tCaseType = LastCaseListGrid.getRowColData(tSelNo,27);

	if (tCaseType=="02" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimOutCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tCaseType=="01" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimSpecialCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="30") {
		
		var strUrl="../g_claim/LLClaimCaseReviewInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="40") {
		
		var strUrl="../g_claim/LLClaimCaseCheckInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');			
	} else {
		
		var strUrl="../g_claim/LLClaimCaseApproveInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/**
 * 查看赔案明细信息
 */
function showCaseDetailInfo() {
		
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择赔案信息");
		return false;
	}
	var tGrpRgtNo = LastCaseListGrid.getRowColData(tSelNo,1);
	var tRgtNo = LastCaseListGrid.getRowColData(tSelNo,2);
	var tClmState = LastCaseListGrid.getRowColData(tSelNo,26);
	var tCaseType = LastCaseListGrid.getRowColData(tSelNo,27);

	if (tCaseType=="02" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimOutCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tCaseType=="01" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimSpecialCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="30") {
		
		var strUrl="../g_claim/LLClaimCaseReviewInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="40") {
		
		var strUrl="../g_claim/LLClaimCaseCheckInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');			
	} else {
		
		var strUrl="../g_claim/LLClaimCaseApproveInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"赔案明细",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/**
 * 理赔明细信息导出
 */
function exportClick() {
	
	fm.SheetName.value = "赔案信息";
		
	//给付控制	
	var tParamTitle = "受理机构^|团体批次号^|案件号^|事件号^|团体保单号^|投保人编码^|投保人名称^|保险计划^|"
								 + "险种编码^|险种名称^|责任编码^|责任名称^|保项编码^|保项名称^|"
								 + "被保险人编码^|被保险人姓名^|性别^|出生日期^|证件类型^|证件号码^|员工号^|与主被保人关系^|出险日期^|"
								 + "事故原因^|就诊医院^|索赔金额^|扣除金额^|第三方支付金额^|审核金额^|赔付金额^|拒付金额^|给付结论^|结论说明^|扣除说明^|"
								 + "备注^|申请日期^|结案日期^|转账账户^|转账账户名^|转账日期^|案件状态^|"
								 + "审核人^|复核人^|审批人^|交接流转号^|手机号";
	
	fm.SheetTitle.value = tParamTitle;
	
	if (!checkBeforQuery()) {
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseQuerySql");
	tSQLInfo.setSqlId("LLClaimCaseQuerySql2");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(fm.ClmState.value);

	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);	
	
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.AccStartDate.value);
	tSQLInfo.addSubPara(fm.AccEndDate.value);
	
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(mManageCom);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}

/**
 * 校验数据
 */
function checkBeforQuery() {
	
	if ((fm.GrpRgtNo.value=="" || fm.GrpRgtNo.value==null)
				&& (fm.RgtNo.value=="" || fm.RgtNo.value==null)
					&& (fm.GrpContNo.value=="" || fm.GrpContNo.value==null)
						&& (fm.GrpName.value=="" || fm.GrpName.value==null)
							&& (fm.RiskCode.value=="" || fm.RiskCode.value==null)
								&& (fm.ClmState.value=="" || fm.ClmState.value==null)
									&& (fm.CustomerName.value=="" || fm.CustomerName.value==null)
										&& (fm.IDNo.value=="" || fm.IDNo.value==null)
											&& (fm.AcceptCom.value=="" || fm.AcceptCom.value==null)
												&& (fm.PageNo.value=="" || fm.PageNo.value==null)
													&& (fm.AccStartDate.value=="" || fm.AccStartDate.value==null)
														&& (fm.AccEndDate.value=="" || fm.AccEndDate.value==null)
															&& (fm.StartDate.value=="" || fm.StartDate.value==null)
																&& (fm.EndDate.value=="" || fm.EndDate.value==null)) {
		alert("请至少录入一项查询条件！");
		return false;														
	}
	return true;	
}