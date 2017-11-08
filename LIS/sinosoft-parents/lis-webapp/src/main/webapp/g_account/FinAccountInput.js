/***************************************************************
 * <p>ProName：FinAccountInput.js</p>
 * <p>Title：会计科目维护界面</p>
 * <p>Description：维护财务中涉及的会计科目</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态

var tSQLInfo = new SqlClass();


/**
 * 提交数据
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
	fm.Operate.value = mOperate;
	fm.action = "./FinAccountSave.jsp";
	fm.submit(); //提交
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		if (mOperate!="DELETE") {
			queryClick();
			clearInfo();
		} else {
			resetClick();
		}
	}
}

/**
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccountSql");
	tSQLInfo.setSqlId("FinAccountSql1");
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(fm.FinName.value);
	tSQLInfo.addSubPara(fm.FinType.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinAccountGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}

/**
 * 显示会计科目信息
 */
function showFinAccountInfo() {
	
	var tSelNo = FinAccountGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的列表信息！");
		return false;
	}
	
	fm.FinCode.disabled = true;
	fm.FinCode.value = FinAccountGrid.getRowColData(tSelNo-1, 1);
	fm.FinName.value = FinAccountGrid.getRowColData(tSelNo-1, 2);
	fm.FinType.value = FinAccountGrid.getRowColData(tSelNo-1, 3);
	fm.FinTypeName.value = FinAccountGrid.getRowColData(tSelNo-1, 4);
	fm.Remark.value = FinAccountGrid.getRowColData(tSelNo-1, 5);
}

/**
 * 新增
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinCode.value = fm.FinCode.value;
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * 修改
 */
function updateClick() {
	
	var tSelNo = FinAccountGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条会计科目信息！");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinCode.value = FinAccountGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tSelNo = FinAccountGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条会计科目信息！");
		return false;
	}
	
	if (!confirm("确认要删除该会计科目？")) {
		return false
	}
	
	fm.HiddenFinCode.value = FinAccountGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * 重置
 */
function resetClick() {
	
	clearInfo();
	initFinAccountGrid();
}

/**
 * 清除表单数据
 */
function clearInfo() {
	
	fm.FinCode.disabled = false;
	fm.FinCode.value = "";
	fm.FinName.value = "";
	fm.FinType.value = "";
	fm.FinTypeName.value = "";
	fm.Remark.value = "";
}

/**
 * 导出数据
 */
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "会计科目编码^|会计科目名称^|会计科目类型编码^|会计科目类型^|备注^|录入人^|录入日期^|最后修改操作人^|最后修改日期";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccountSql");
	tSQLInfo.setSqlId("FinAccountSql1");
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(fm.FinName.value);
	tSQLInfo.addSubPara(fm.FinType.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
