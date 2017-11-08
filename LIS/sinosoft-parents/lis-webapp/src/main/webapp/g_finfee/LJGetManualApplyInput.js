/***************************************************************
 * <p>ProName：LJGetManualApplyInput.js</p>
 * <p>Title: 手动付费申请</p>
 * <p>Description：手动付费申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryInfo(o) {
	
	initApplyBatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql1");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(fm.QueryBatchNo.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), ApplyBatchInfoGrid, 0, 1);
	if (o=="0") {
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/**
 * 申请批次号
 */
function applyClick() {

	if (tComGrade!="03") {
		alert("请在中支机构下进行该操作！");
		return false;
	}
	
	fm.QueryBatchNo.value = "";
	fm.QueryApplyStartDate.value = "";
	fm.QueryApplyEndDate.value = "";
	
	tOperate = "APPLYCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualApplySave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, cBatchNo) {
	
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
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		dealAfterSubmit(cBatchNo);
	}
}

function dealAfterSubmit(cBatchNo) {
	
	fm.QueryBatchNo.value = cBatchNo;
	queryInfo('1');
	ApplyBatchInfoGrid.radioSel(1);
	enterClick();
}

function enterClick() {
	
	var tSelNo = ApplyBatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		return;
	}
	var tAppCom = ApplyBatchInfoGrid.getRowColData(tSelNo, 1);
	var tBatchNo = ApplyBatchInfoGrid.getRowColData(tSelNo, 3);
	var tAppDate = ApplyBatchInfoGrid.getRowColData(tSelNo, 4);
	
	location.href = "./LJGetManualAppSubInput.jsp?AppCom="+ tAppCom +"&ApplyBatchNo="+ tBatchNo +"&AppDate="+ tAppDate;
}
