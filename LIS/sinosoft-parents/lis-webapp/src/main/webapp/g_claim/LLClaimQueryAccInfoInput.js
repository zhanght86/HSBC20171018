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
	queryAccInfo();
}
function queryAccInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimQueryAccInfoSql");
	tSQLInfo.setSqlId("LLClaimQuerySocialInfoSql1");
	tSQLInfo.addSubPara(tGrpRgtNo);
	tSQLInfo.addSubPara(tRgtNo);
	
//	turnPage1.pageLineNum=1000;
	//turnPage1.queryModal(tSQLInfo.getString(), QuesRecordGrid,"2",1,1);	
	turnPage1.queryModal(tSQLInfo.getString(),QuesRecordGrid,2);	
}

//返回选中的银行账户信息
function returnInfo() {
	
	var tSelNo = QuesRecordGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条账户信息");
		return false;
	}
	try {
		
		var returnArr = new Array();
		returnArr = QuesRecordGrid.getRowData(tSelNo);
		if (returnArr!=null) {
		
			if (returnArr[0]==null || returnArr[0]=="") {
				alert("请先查询！");
				return false;
			} else {				
				top.opener.afterQueryAccInfo(returnArr);
			}		
		} else {
			return false;
		}
	} catch(ex) {
		alert("返回异常："+ ex);
	}
	top.close();
}