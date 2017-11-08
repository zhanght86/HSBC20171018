/***************************************************************
 * <p>ProName：EdorCancelInput.js</p>
 * <p>Title：保全撤销</p>
 * <p>Description：保全撤销</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-07-14
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 保全受理信息查询
 */
function queryClick(QueryFlag) {
	
	initEdorAppGrid();
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCancelSql");
	tSQLInfo.setSqlId("EdorCancelSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.Managecom.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.ScanOperator.value);
	tSQLInfo.addSubPara(fm.ScanStartDate.value);
	tSQLInfo.addSubPara(fm.ScanEndtDate.value);
	tSQLInfo.addSubPara(fm.AcceptOperator.value);
	tSQLInfo.addSubPara(fm.AcceptStartDate.value);
	tSQLInfo.addSubPara(fm.AcceptEndtDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorAppGrid, 1, 1);
	
	if (QueryFlag==1) {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/**
 * 保全批单信息查询
 */
function showEdorInfo() {
	
	var tSelNo = EdorAppGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tSelNo-1, 2);
	
	divEdorMain.style.display = "";
	divEdorItem.style.display = "none";
	initEdorGrid();
	initEdorItemGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCancelSql");
	tSQLInfo.setSqlId("EdorCancelSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), EdorGrid, 1, 1);
}

/**
 * 保全项目信息查询
 */
function showEdorItemInfo() {
	
	var tSelNo = EdorGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	var tEdorNo = EdorGrid.getRowColData(tSelNo-1, 1);
	
	divEdorItem.style.display = "";
	initEdorItemGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCancelSql");
	tSQLInfo.setSqlId("EdorCancelSql3");
	tSQLInfo.addSubPara(tEdorNo);
	
	turnPage3.queryModal(tSQLInfo.getString(), EdorItemGrid, 1, 1);
}

/**
 * 保全受理撤销
 */
function edorAppCancel() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("请选择一条保全受理信息！");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tCancelReason = EdorAppGrid.getRowColData(tAppSelNo-1, 10);
	
	if (tCancelReason=="") {
		alert("撤销原因不能为空！");
		return false;
	}
	
	fm.Operate.value = "EDORAPPCANCEL";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo;
	submitForm(fm);
}

/**
 * 撤回至保全受理
 */
function cancelToAccept() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("请选择一条保全受理信息！");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tCancelReason = EdorAppGrid.getRowColData(tAppSelNo-1, 10);
	
	if (tCancelReason=="") {
		alert("撤销原因不能为空！");
		return false;
	}
	
	fm.Operate.value = "CANCELTOACCEPT";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo;
	submitForm(fm);
}

/**
 * 保全批单撤销
 */
function edorMainCancel() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("请选择一条保全受理信息！");
		return false;
	}
	
	var tEdorMainSelNo = EdorGrid.getSelNo();
	if (tEdorMainSelNo==0) {
		alert("请选择一条保全批单信息！");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tEdorNo = EdorGrid.getRowColData(tEdorMainSelNo-1, 1);
	var tCancelReason = EdorGrid.getRowColData(tEdorMainSelNo-1, 6);
	
	if (tCancelReason=="") {
		alert("撤销原因不能为空！");
		return false;
	}
	
	fm.Operate.value = "EDORMAINCANCEL";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo;
	submitForm(fm);
}

/**
 * 保全项目撤销
 */
function edorItemCancel() {
	
	var tAppSelNo = EdorAppGrid.getSelNo();
	if (tAppSelNo==0) {
		alert("请选择一条保全受理信息！");
		return false;
	}
	
	var tEdorMainSelNo = EdorGrid.getSelNo();
	if (tEdorMainSelNo==0) {
		alert("请选择一条保全批单信息！");
		return false;
	}
	
	var tEdorItemSelNo = EdorItemGrid.getSelNo();
	if (tEdorItemSelNo==0) {
		alert("请选择一条保全项目信息！");
		return false;
	}
	
	var tEdorAppNo = EdorAppGrid.getRowColData(tAppSelNo-1, 2);
	var tEdorNo = EdorGrid.getRowColData(tEdorMainSelNo-1, 1);
	var tEdorType = EdorItemGrid.getRowColData(tEdorItemSelNo-1, 1);
	var tCancelReason = EdorItemGrid.getRowColData(tEdorItemSelNo-1, 4);
	
	if (tCancelReason=="") {
		alert("撤销原因不能为空！");
		return false;
	}
	
	fm.Operate.value = "EDORITEMCANCEL";
	fm.CancelReason.value = tCancelReason;
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorCancelSave.jsp?EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType;
	submitForm(fm);
}

/**
 * 提交数据
 */
function submitForm(obj) {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
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
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		if (fm.Operate.value == "EDORITEMCANCEL") {
			showEdorItemInfo();
		} else if (fm.Operate.value == "EDORMAINCANCEL") {
			showEdorInfo();
		} else {
			queryClick(2);
			initEdorGrid();
			initEdorItemGrid();
			divEdorMain.style.display = "none";
			divEdorItem.style.display = "none";
		}
	}
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}