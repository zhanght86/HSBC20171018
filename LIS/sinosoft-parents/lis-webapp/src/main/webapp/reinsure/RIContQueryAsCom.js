//该文件中包含客户端需要处理的函数和事件

//程序名称：RIContQueryAsCom.js
//程序功能：合同查询
//创建日期：2011-7-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIContQueryAsComInputSql";

// 查 询按钮
function queryCout() {
	if (!verifyInput()) {
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContQueryAsComInputSql1");//指定使用的Sql的id
	mySql.addSubPara(fm.ReComCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	
	turnPage.queryModal(strSQL, Mul10Grid);
}

function doreset() {
	fm.ReComCode.value = "";
	fm.ReComName.value = "";
	fm.RIContNo.value = "";
	fm.RIContName.value = "";
	Mul10Grid.clearData(); 
	Mul11Grid.clearData(); 
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "ricompanycode") {
		Mul10Grid.clearData(); 
		Mul11Grid.clearData(); 
	}else if (codeName == "ricontno"){
		Mul10Grid.clearData(); 
		Mul11Grid.clearData(); 
	}
}


function queryPrecept() {
	var tRow = Mul10Grid.getSelNo();
	
	if (tRow == 0) {
		myAlert(""+"请从列表中选择要查询的再保合同！"+"");
		return false;
	}
	var sn = Mul10Grid.getRowColData(tRow - 1, 3);
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContQueryAsComInputSql2");//指定使用的Sql的id
	mySql.addSubPara(fm.ReComCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(sn);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, Mul11Grid);
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加操作
}

// 使得从该窗口弹出的窗口能够聚焦
function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	showInfo.close();
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		showDiv(operateButton, "true");
		showDiv(inputButton, "false");
		// 执行下一步操作
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
