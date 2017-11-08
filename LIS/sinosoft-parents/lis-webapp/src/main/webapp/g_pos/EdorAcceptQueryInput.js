/***************************************************************
 * <p>ProName：EdorAcceptQueryInput.js</p>
 * <p>Title：保全受理</p>
 * <p>Description：保全受理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 公共池查询
 */
function queryClick(QueryFlag) {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ScanManagecom.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.ScanOperator.value);
	tSQLInfo.addSubPara(fm.ScanStartDate.value);
	tSQLInfo.addSubPara(fm.ScanEndtDate.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), PublicGrid, 1, 1);
	
	if (QueryFlag==1) {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/**
 * 个人池查询
 */
function PersonalQuery() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql2");
	tSQLInfo.addSubPara(tOperator);
	
	turnPage2.queryModal(tSQLInfo.getString(), PersonalGrid, 1, 1);
}

/**
 * 申请公共池中的任务到个人池
 */
function appClick() {
	
	var tSelNo = PublicGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tMissionID = PublicGrid.getRowColData(tSelNo-1, 1);
	var tSubMissionID = PublicGrid.getRowColData(tSelNo-1, 2);
	var tActivityID = PublicGrid.getRowColData(tSelNo-1, 3);
	
	fm.action = "./EdorMissionSave.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&DefaultOperator="+tOperator+"&Operate=APPLY";
	submitForm();
}

/**
 * 将个人池中的任务退回到公共池
 */
function returnClick() {
	
	var tSelNo = PersonalGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tMissionID = PersonalGrid.getRowColData(tSelNo-1, 1);
	var tSubMissionID = PersonalGrid.getRowColData(tSelNo-1, 2);
	var tActivityID = PersonalGrid.getRowColData(tSelNo-1, 3);
	
	fm.action = "./EdorMissionSave.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate=REAPPLY";
	submitForm();
}

/**
 * 提交
 */
function submitForm() {
	
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
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
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	initForm();
	queryClick(2);
}

/**
 * 进入个人池任务处理
 */
function GotoDetail() {
	
	var tSelNo = PersonalGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tMissionID = PersonalGrid.getRowColData(tSelNo-1, 1);
	var tSubMissionID = PersonalGrid.getRowColData(tSelNo-1, 2);
	var tActivityID = PersonalGrid.getRowColData(tSelNo-1, 3);
	var tScanManageCom = PersonalGrid.getRowColData(tSelNo-1, 4);
	var tEdorAppNo = PersonalGrid.getRowColData(tSelNo-1, 5);
	
	location.href = "./EdorAcceptDetailInput.jsp?MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&ScanManageCom="+ tScanManageCom +"&EdorAppNo="+ tEdorAppNo;
	//var strUrl = "./EdorESMain.jsp?Pages=./EdorAcceptDetailInput.jsp&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&EdorAppNo="+ tEdorAppNo +"&ScanFlag=1&BussNo="+tEdorAppNo+"&BussType=G_POS&SubType=22001";
	//window.open(strUrl,'EdorESMain','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
}
