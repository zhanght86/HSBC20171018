/***************************************************************
 * <p>ProName：LDUWPopedomInput.js</p>
 * <p>Title：核保权限管理</p>
 * <p>Description：核保权限管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交数据
 */
function submitForm(obj) {

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
	obj.submit();
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		initUWPopedomGrid();
		initInpBox();
	}
}

/**
 * 查询
 */
function querySubmit() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_auxi.LDUWPopedomSql");
	tSQLInfo.setSqlId("LDUWPopedomSql1");
	tSQLInfo.addSubPara(fm.PopedomLevel1.value);
	tSQLInfo.addSubPara(fm.PopedomName1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWPopedomGrid, 0, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 展示用户信息
 */
function showUWPopedomInfo() {
	
	var tRow = UWPopedomGrid.getSelNo();
	fm.PopedomLevel.value = UWPopedomGrid.getRowColData(tRow-1,1);
	fm.PopedomName.value = UWPopedomGrid.getRowColData(tRow-1,2);
	fm.PerLifeAmnt.value = UWPopedomGrid.getRowColData(tRow-1,3);
	fm.PerAcciAmnt.value = UWPopedomGrid.getRowColData(tRow-1,4);
	fm.PerIllAmnt.value = UWPopedomGrid.getRowColData(tRow-1,5);
	fm.PerMedAmnt.value = UWPopedomGrid.getRowColData(tRow-1,6);
	fm.PremScale.value = UWPopedomGrid.getRowColData(tRow-1,7);
	fm.MainPremRateFloat.value = UWPopedomGrid.getRowColData(tRow-1,8);
	fm.MedPremRateFloat.value = UWPopedomGrid.getRowColData(tRow-1,9);
	fm.ValDate.value = UWPopedomGrid.getRowColData(tRow-1,10);
	fm.EndDate.value = UWPopedomGrid.getRowColData(tRow-1,11);
}

/**
 * 增加
 */
function addSubmit() {
	
	mOperate = "INSERT";
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWPopedomSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * 修改
 */
function modSubmit() {
	
	mOperate = "UPDATE";
	var tRow = UWPopedomGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWPopedomSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * 删除
 */
function delSubmit() {
	
	var tRow = UWPopedomGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	fm.PopedomLevel.value = UWPopedomGrid.getRowColData(tRow-1,1);
	mOperate = "DELETE";
	fm.action = "./LDUWPopedomSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * 提交前校验
 */
function checkSubmit() {
	
	if (!verifyInput2()) {
		return false;
	}
	if (fm.ValDate.value > fm.EndDate.value) {
		alert("生效日期应小于终止日期！");
		return false;
	}
	if (mOperate == "INSERT") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_auxi.LDUWPopedomSql");
		tSQLInfo.setSqlId("LDUWPopedomSql2");
		tSQLInfo.addSubPara(fm.PopedomLevel.value);

		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr[0][0] != 0) {
			alert("该核保权限已存在！");
			return false;
		}
	}
	
	if (mOperate == "UPDATE") {
		
		var tRow = UWPopedomGrid.getSelNo();
		var tPopedomLevel = UWPopedomGrid.getRowColData(tRow-1,1);
		if (fm.PopedomLevel.value != tPopedomLevel) {
			alert("权限级别不可修改！");
			return false;
		}
	}
	
	return true;
}
