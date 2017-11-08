/***************************************************************
 * <p>ProName：LLClaimQuerySocialInfoInput.js</p>
 * <p>Title：社保查询</p>
 * <p>Description：社保查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

//"返回"按钮
function getToBack(){
		
	top.close();
}
/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	showInfo = showDialogWindow(urlStr, 0);
	fm.submit();
}

/**
 * 提交数据后返回操作
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