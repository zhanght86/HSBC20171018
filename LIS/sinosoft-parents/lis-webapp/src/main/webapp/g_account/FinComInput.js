/***************************************************************
 * <p>ProName：FinComInput.js</p>
 * <p>Title：财务机构维护</p>
 * <p>Description：财务机构维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
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
	fm.action = "./FinComSave.jsp";
	fm.submit(); //提交
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
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
		
		if (mOperate!="DELETE") {
			queryClick();
			clearInfo();
		} else {
			resetClick();
		}
	}
}

/**
 * 查询数据
 */
function queryClick() {
	
	turnPage1 = new turnPageClass();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinComSql");
	tSQLInfo.setSqlId("FinComSql1");
	tSQLInfo.addSubPara(fm.FinComCode.value);
	tSQLInfo.addSubPara(fm.FinComName.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinComGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}

function showFinComInfo() {
	
	var tSelNo = FinComGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的列表信息！");
		return false;
	}
	
	fm.FinComCode.disabled = true;
	fm.FinComCode.value = FinComGrid.getRowColData(tSelNo-1, 1);
	fm.FinComName.value = FinComGrid.getRowColData(tSelNo-1, 2);
}

/**
 * 新增数据
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinComCode.value = fm.FinComCode.value;
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * 修改数据
 */
function updateClick() {
	
	var tSelNo = FinComGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条财务机构信息！");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenFinComCode.value = FinComGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	submitForm();
}

/**
 * 删除数据
 */
function deleteClick() {
	
	var tSelNo = FinComGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条财务机构信息！");
		return false;
	}
	
	if (!confirm("确认要删除该财务机构信息？")) {
		return false
	}
	
	fm.HiddenFinComCode.value = FinComGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * 重置数据
 */
function resetClick() {
	
	clearInfo();
	initFinComGrid();
}

/**
 * 清除表单数据
 */
function clearInfo() {
	
	fm.FinComCode.disabled = false;
	fm.FinComCode.value = "";
	fm.FinComName.value = "";
}

/**
 * 导出数据
 */
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "财务机构机构代码^|财务机构名称^|录入人^|录入日期^|最后修改人^|最后修改日期";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinComSql");
	tSQLInfo.setSqlId("FinComSql1");
	tSQLInfo.addSubPara(fm.FinComCode.value);
	tSQLInfo.addSubPara(fm.FinComName.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
