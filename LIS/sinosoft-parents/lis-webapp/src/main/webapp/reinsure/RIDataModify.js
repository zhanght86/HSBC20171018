//该文件中包含客户端需要处理的函数和事件

//程序名称：RIDataModify.js
//程序功能：业务数据调整
//创建日期：2011-07-30
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
var sqlresourcename = "reinsure.RIDataModifyInputSql";
var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();

// 查 询按钮
function button136() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIDataModifyInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.InsuredNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIAccNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RiskCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.DutyCode.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, Mul11Grid);
}

function veriry() {
	for ( var n = 0; n < Mul13Grid.mulLineCount; n++) {
		if (Mul13Grid.getRowColData(n, 1) == null
				|| Mul13Grid.getRowColData(n, 1) == "") {
			myAlert(""+"修改字段存在空行！"+"");
			return false;
		}
	}

	// 校验不能录入重复合同要素
	for ( var n = 0; n < Mul13Grid.mulLineCount; n++) {
		for ( var m = n + 1; m < Mul13Grid.mulLineCount; m++) {
			if (Mul13Grid.getRowColData(n, 1) == Mul13Grid.getRowColData(m, 1)) {
				myAlert(""+"修改字段不能重复！"+"");
				return false;
			}
		}
	}
	return true;
}

// 修 改按钮
function button137() {
	try {
		if (verifyInput()) {
			if (veriry()) {
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIDataModifySave.jsp";
				//fm.submit();
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
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

function afterCodeSelect(codeName, Field) {
	if (codeName == "ridatamodifycol") {
		var tRow = Mul11Grid.getSelNo();
		if (tRow == 0) {
			myAlert(""+"请从列表中选择记录！"+"");
			return false;
		}
		var eventno = Mul11Grid.getRowColData(tRow - 1, 1);
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIDataModifyInputSql2");// 指定使用的Sql的id
		mySql.addSubPara(Field.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(eventno);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql.getString();

		var arrResult = easyExecSql(strSQL);
		var rowNum = Mul13Grid.mulLineCount; // 行数
		for ( var i = 0; i < rowNum; i++) {
			if (Mul13Grid.getRowColData(i, 1) == Field.value) {
				Mul13Grid.setRowColData(i, 3, arrResult[0][0].toString());
			}
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
