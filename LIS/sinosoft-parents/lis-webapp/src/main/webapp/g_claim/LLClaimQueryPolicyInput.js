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
 * ��ѯ�����б���Ϣ
 */
function queryListInfo() {

	tSQLInfo = new SqlClass();

	//mode--1:���Ᵽ����ѯ��2-����������ѯ
	if (mMode==1) {
		
		tSQLInfo.setResourceName("g_claim.LLClaimQueryPolicySql");
		tSQLInfo.setSqlId("LLClaimQueryPolicySql");		
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(mRgtNo);		
	} else if (mMode==2) {
		
		tSQLInfo.setResourceName("g_claim.LLClaimQueryPolicySql");
		tSQLInfo.setSqlId("LLClaimQueryPolicySql1");		
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(mManageCom);
	}
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),PolicyListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		top.close();
		return false;
	}
}
/**
 * չʾѡ�еı�����ϸ
 */
function showDetailInfo () {
	
	var tSelNo = PolicyListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������Ϣ");
		return false;
	}
	
	var tGrpContNo = PolicyListGrid.getRowColData(tSelNo,2);
	var tGrpPropNo = PolicyListGrid.getRowColData(tSelNo,3);	

	var strUrl="../g_app/LCGrpContPolInput.jsp?GrpPropNo="+ tGrpContNo+"&GrpContNo="+tGrpPropNo+"&Flag=3" ;		
	window.open(strUrl,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		
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