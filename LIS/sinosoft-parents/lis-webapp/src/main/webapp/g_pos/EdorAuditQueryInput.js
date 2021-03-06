/***************************************************************
 * <p>ProName：EdorAuditQueryInput.js</p>
 * <p>Title：保全审核</p>
 * <p>Description：保全审核</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
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
	tSQLInfo.setSqlId("EdorProcessSql9");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.AcceptOperator.value);
	tSQLInfo.addSubPara(fm.AcceptStartDate.value);
	tSQLInfo.addSubPara(fm.AcceptEndtDate.value);
	tSQLInfo.addSubPara(fm.CheckOperator.value);
	tSQLInfo.addSubPara(fm.CheckStartDate.value);
	tSQLInfo.addSubPara(fm.CheckEndtDate.value);
	tSQLInfo.addSubPara(tOperator);
	
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
	tSQLInfo.setSqlId("EdorProcessSql10");
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
	var tEdorAppNo = PublicGrid.getRowColData(tSelNo-1, 5);
	var tEdorNo = PublicGrid.getRowColData(tSelNo-1, 6);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql21");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult!=null) {
		
		if (strQueryResult[0][0]=="1") {
			alert("请先处理该保全受理的前一个批单！");
			return false;
		}
	}
	
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
	var tEdorAppNo = PersonalGrid.getRowColData(tSelNo-1, 5);
	var tEdorNo = PersonalGrid.getRowColData(tSelNo-1, 6);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorProcessSql");
	tSQLInfo.setSqlId("EdorProcessSql21");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult!=null) {
		
		if (strQueryResult[0][0]=="1") {
			alert("请先处理该保全受理的前一个批单！");
			return false;
		}
	}
	
	location.href = "./EdorAuditDetailInput.jsp?MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&EdorAppNo="+ tEdorAppNo +"&EdorNo="+ tEdorNo;
}
