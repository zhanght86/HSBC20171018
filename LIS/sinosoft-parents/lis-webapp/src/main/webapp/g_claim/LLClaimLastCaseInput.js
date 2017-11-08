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
 * ��ѯ��ʷ�ⰸ
 */
function queryClick() {

	if (mMode=="0") {
		queryClick1();
		return;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),LastCaseListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}

/**
 * ��ѯ�ⰸ��������;��
 */
function queryClick1() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql2");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
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
	var tRgtNo = LastCaseListGrid.getRowColData(tSelNo,4);
	var tCustomerNo = LastCaseListGrid.getRowColData(tSelNo,5);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql1");
	
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),LastCaseDetailGrid, 2);	
}

/**
 * �鿴ȫ���ⰸ��Ϣ
 */
function showAllDetailInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLastCaseSql");
	tSQLInfo.setSqlId("LLClaimLastCaseSql1");
	
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(mCustomerNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),LastCaseDetailGrid, 2);
	
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
	var tClmState = LastCaseListGrid.getRowColData(tSelNo,13);
	var tCaseType = LastCaseListGrid.getRowColData(tSelNo,14);
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
 * ����
 */
function goBack() {
	top.close();
}

/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	showInfo = showDialogWindow(urlStr, 0);
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		showDialogWindow(urlStr, 1);
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		showDialogWindow(urlStr, 1);
	}	
}