/***************************************************************
 * <p>ProName：LLClaimAssReportInput.js</p>
 * <p>Title：报案关联</p>
 * <p>Description：报案关联</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-18
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

/**
 * 查询出险人待关联报案信息
 */
function queryOnReportInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAssReportSql");
	tSQLInfo.setSqlId("LLClaimAssReportSql");
	tSQLInfo.addSubPara(mCustomerNo);
	
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimReportGrid,2);		
}

/**
 * 查询出险人已关联关联的报案信息
 */
function queryOffReportInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAssReportSql");
	tSQLInfo.setSqlId("LLClaimAssReportSql1");
	tSQLInfo.addSubPara(mCustomerNo);
	tSQLInfo.addSubPara(mRgtNo);
	turnPage2.queryModal(tSQLInfo.getString(),AssociatedReportGrid,2);
}

/**
 * 报案关联
 */
function associate() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条待关联的报案数据！");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAssReportSql");
	tSQLInfo.setSqlId("LLClaimAssReportSql2");
	tSQLInfo.addSubPara(mRgtNo);
	var tIsRptNo = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tIsRptNo!=""){
		alert("该案件已经关联报案信息");
		return false;
	}
	
	fm.RptNo.value = tRptNo;
	fm.Operate.value = "ASSOCIATE";
	submitForm();
}
/**
 * 取消关联
 */
function removeAssociate() {
	
	var tSelNo = AssociatedReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条待已关联的报案数据！");
		return false;
	}
	var tRptNo = AssociatedReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		return;
	}
	fm.RptNo.value = tRptNo;
	fm.Operate.value = "REMOVEASS";
	submitForm();	
}
/**
 * 报案明细查询
 */
function showReportDetail() {
	
	var tSelNo = AssociatedReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条已关联的报案数据！");
		return false;
	}
	var tRptNo = AssociatedReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		return;
	}
	fm.RptNo.value = tRptNo;	
	window.open("LLClaimReportInput.jsp?Type=3&RptNo="+ tRptNo);
}
/**
 * 返回
 */
function goBack() {
	
	top.close();
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
		initForm();
	}	
}
