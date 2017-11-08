//该文件中包含客户端需要处理的函数和事件

//程序名称：RIBsnsBillLock.js
//程序功能：数据加锁
//创建日期：2011/8/11
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillLockInputSql";
function veriry(state) {
	var str = getCurrentDate();
	if (state == "1") {
		if (fm.LockDate.value == "" || fm.LockDate.value == null) {
			myAlert(""+"加锁日期不能为空！"+"");
			return false;
		}
		if (fm.LockDate.value > str) {
			myAlert(""+"加锁日期不能大于当天!"+"");
			return false;
		}

		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIBsnsBillLockInputSql2");// 指定使用的Sql的id
		mySql1.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
		mySql1.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
		var strSql = mySql1.getString();
		var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
		if (strQueryResult) {
			myAlert(""+"数据已被加锁，不可重复操作，请先解锁！"+"");
			return false;
		}
	} else {
		if (fm.LockDate.value != str) {
			myAlert(""+"解锁日期只能为当天!"+"");
			return false;
		}

		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RIBsnsBillLockInputSql3");// 指定使用的Sql的id
		mySql2.addSubPara("");
		var SQL = mySql2.getString();
		result = easyExecSql(SQL);
		if (result == null) {
			Mul10Grid.clearData();

			myAlert(""+"无效操作，没有加锁的数据！"+"");

			return false;
		}
		var mySql3 = new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId("RIBsnsBillLockInputSql4");// 指定使用的Sql的id
		mySql3.addSubPara(fm.AccumulateDefNO.value);
		mySql3.addSubPara(fm.AccumulateDefNO.value);
		var strSql = mySql3.getString();
		var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
		if (strQueryResult) {
			myAlert(""+"无效操作，数据已是解锁状态!"+"");
			return false;
		}
	}
	return true;
}

function queryClick() {

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillLockInputSql5");// 指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	result = easyExecSql(strSQL);
	if (result == null) {
		Mul10Grid.clearData();
		myAlert(""+"查询结果为空"+"");
		return false;
	}

	turnPage.queryModal(strSQL, Mul10Grid);

}

// 保 存按钮
function saveLock(state) {
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput()) {
			if (veriry(state)) {
				var showStr = ""+"正在保存数据，请您稍候幷且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIBsnsBillLockSave.jsp?state=" + state;
				fm.submit(); // 提交
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

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		// 执行下一步操作

		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIBsnsBillLockInputSql6");// 指定使用的Sql的id
		mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql.getString();

		turnPage.queryModal(strSQL, Mul10Grid);
		fm.lockbutton.style.display = "none";
		fm.unlockbutton.style.display = "none";
	}
}
function afterCodeSelect(codeName, Field) {
	if (codeName == "riaccmucode") {
		Mul10Grid.clearData();
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIBsnsBillLockInputSql7");// 指定使用的Sql的id
		mySql.addSubPara(Field.value);// 指定传入的参数，多个参数顺序添加
		var tSQL = mySql.getString();
		var strArray = easyExecSql(tSQL);
		if (strArray == "1") {
			fm.lockbutton.style.display = "none";
			fm.unlockbutton.style.display = "";
			fm.LockDate.value = getCurrentDate();
			divLockDate1.style.display = "none";
			divLockDate2.style.display = "none";
		} else {
			fm.lockbutton.style.display = "";
			fm.unlockbutton.style.display = "none";
			fm.LockDate.value = "";
			divLockDate1.style.display = "";
			divLockDate2.style.display = "";
		}
	}
}
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
