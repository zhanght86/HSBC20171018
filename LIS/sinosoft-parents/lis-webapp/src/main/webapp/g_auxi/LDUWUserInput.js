/***************************************************************
 * <p>ProName：LDUWUserInput.js</p>
 * <p>Title：核保用户管理</p>
 * <p>Description：核保用户管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-06-25
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
		
		initUWUserGrid();
		initInpBox();
	}
}

/**
 * 查询
 */
function querySubmit() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_auxi.LDUWUserSql");
	tSQLInfo.setSqlId("LDUWUserSql1");
	tSQLInfo.addSubPara(fm.UserCode1.value);
	tSQLInfo.addSubPara(fm.UserName1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWUserGrid, 0, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}


/**
 * 查询准客户信息
 */
function queryUser() {
	
	var strUrl = "LDQueryUserMain.jsp";
	window.open(strUrl,"用户查询",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}

/**
 * 展示用户信息
 */
function showUserInfo() {
	
	var tRow = UWUserGrid.getSelNo();
	fm.UserCode.value = UWUserGrid.getRowColData(tRow-1,1);
	fm.UserName.value = UWUserGrid.getRowColData(tRow-1,2);
	fm.SupervisorFlag.value = UWUserGrid.getRowColData(tRow-1,3);
	fm.SupervisorName.value = UWUserGrid.getRowColData(tRow-1,4);
	fm.PopedomLevel.value = UWUserGrid.getRowColData(tRow-1,5);
	fm.PopedomLevelName.value = UWUserGrid.getRowColData(tRow-1,6);
}

/**
 * 增加
 */
function addSubmit() {
	
	mOperate = "INSERT";
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWUserSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * 修改
 */
function modSubmit() {
	
	mOperate = "UPDATE";
	var tRow = UWUserGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LDUWUserSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * 删除
 */
function delSubmit() {
	
	var tRow = UWUserGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	fm.UserCode.value = UWUserGrid.getRowColData(tRow-1,1);
	mOperate = "DELETE";
	fm.action = "./LDUWUserSave.jsp?Operate="+ mOperate;
	submitForm(fm);
}

/**
 * 提交前校验
 */
function checkSubmit() {
	
	if (fm.UserCode.value == null || fm.UserCode.value == "") {
		alert("用户编码不能为空！");
		return false;
	}
	if (fm.UserName.value == null || fm.UserName.value == "") {
		alert("用户姓名不能为空！");
		return false;
	}
	if (fm.SupervisorFlag.value == null || fm.SupervisorFlag.value == "") {
		alert("是否核保主管不能为空！");
		return false;
	}
	if (fm.PopedomLevel.value == null || fm.PopedomLevel.value == "") {
		alert("核保级别不能为空！");
		return false;
	}
	
	if (mOperate == "INSERT") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_auxi.LDUWUserSql");
		tSQLInfo.setSqlId("LDUWUserSql2");
		tSQLInfo.addSubPara(fm.UserCode.value);

		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr[0][0] != 0) {
			alert("该核保用户已存在！");
			return false;
		}
	}
	if (mOperate == "UPDATE") {
		
		var tRow = UWUserGrid.getSelNo();
		var tUserCode = UWUserGrid.getRowColData(tRow-1,1);
		if (fm.UserCode.value  != tUserCode) {
			alert("用户编码不可修改！");
			return false;
		}
	}
	
	return true;
}
