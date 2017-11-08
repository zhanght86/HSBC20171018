/***************************************************************
 * <p>ProName��LLClaimLastCaseInput.js</p>
 * <p>Title���ⰸ��ѯ</p>
 * <p>Description���ⰸ��ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
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
 * ��ѯ�ⰸ
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
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}
/**
 * չʾѡ�е��ⰸ�⸶��ϸ
 */
function showSelectDetail () {
	
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("��ѡ���ⰸ��Ϣ");
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
 * �鿴������ϸ��Ϣ
 */
function showBatchDetailInfo() {
		
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("��ѡ���ⰸ��Ϣ");
		return false;
	}
	var tGrpRgtNo = LastCaseListGrid.getRowColData(tSelNo,1);
	var tClmState = LastCaseListGrid.getRowColData(tSelNo,26);
	var tCaseType = LastCaseListGrid.getRowColData(tSelNo,27);

	if (tCaseType=="02" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimOutCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tCaseType=="01" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimSpecialCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="30") {
		
		var strUrl="../g_claim/LLClaimCaseReviewInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="40") {
		
		var strUrl="../g_claim/LLClaimCaseCheckInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');			
	} else {
		
		var strUrl="../g_claim/LLClaimCaseApproveInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1";	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/**
 * �鿴�ⰸ��ϸ��Ϣ
 */
function showCaseDetailInfo() {
		
	var tSelNo = LastCaseListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("��ѡ���ⰸ��Ϣ");
		return false;
	}
	var tGrpRgtNo = LastCaseListGrid.getRowColData(tSelNo,1);
	var tRgtNo = LastCaseListGrid.getRowColData(tSelNo,2);
	var tClmState = LastCaseListGrid.getRowColData(tSelNo,26);
	var tCaseType = LastCaseListGrid.getRowColData(tSelNo,27);

	if (tCaseType=="02" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimOutCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tCaseType=="01" && tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (tClmState=="10") {
		
		var strUrl="../g_claim/LLClaimSpecialCaseInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="30") {
		
		var strUrl="../g_claim/LLClaimCaseReviewInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	} else if (tClmState=="40") {
		
		var strUrl="../g_claim/LLClaimCaseCheckInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');			
	} else {
		
		var strUrl="../g_claim/LLClaimCaseApproveInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=1&RgtNo="+tRgtNo;	
		window.open(strUrl,"�ⰸ��ϸ",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/**
 * ������ϸ��Ϣ����
 */
function exportClick() {
	
	fm.SheetName.value = "�ⰸ��Ϣ";
		
	//��������	
	var tParamTitle = "�������^|�������κ�^|������^|�¼���^|���屣����^|Ͷ���˱���^|Ͷ��������^|���ռƻ�^|"
								 + "���ֱ���^|��������^|���α���^|��������^|�������^|��������^|"
								 + "�������˱���^|������������^|�Ա�^|��������^|֤������^|֤������^|Ա����^|���������˹�ϵ^|��������^|"
								 + "�¹�ԭ��^|����ҽԺ^|������^|�۳����^|������֧�����^|��˽��^|�⸶���^|�ܸ����^|��������^|����˵��^|�۳�˵��^|"
								 + "��ע^|��������^|�᰸����^|ת���˻�^|ת���˻���^|ת������^|����״̬^|"
								 + "�����^|������^|������^|������ת��^|�ֻ���";
	
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
 * У������
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
		alert("������¼��һ���ѯ������");
		return false;														
	}
	return true;	
}