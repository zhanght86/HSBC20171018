/***************************************************************
 * <p>ProName��LLClaimQuerySocialInfoInput.js</p>
 * <p>Title���籣��ѯ</p>
 * <p>Description���籣��ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

//"����"��ť
function getToBack(){
		
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
	queryQuestionInfo();
	fm.IssueFlag.value="";
	fm.IssueFlagName.value="";
	fm.IssueDescribe.value="";
}
function queryQuestionInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimQuerySocialInfoSql");
	tSQLInfo.setSqlId("LLClaimQuerySocialInfoSql1");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tRgtNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuesRecordGrid,"2");
	
}