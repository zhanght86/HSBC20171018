//该文件中包含客户端需要处理的函数和事件

//程序名称：RIProfitLossCalm.js
//程序功能：盈余佣金计算
//创建日期：2011/8/22
//创建人  ：dukang
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var Mul10GridTurnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIProfitLossCalmInputSql";
function verify() {
	if (fm.CalYear.value == "" || fm.CalYear.value == null) {
		myAlert(""+"年度不能为空"+"");
		return false;
	}
	if (!isNumeric(fm.CalYear.value)) {
		myAlert(""+"年度格式错误"+"");
		return false;
	}
	if (fm.RIProfitNo.value == "" || fm.RIProfitNo.value == null) {
		myAlert(""+"纯益手续费不能为空"+"");
		return false;
	}

	return true;
}
// 初始化参数按钮
function button140() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossCalmInputSql6");// 指定使用的Sql的id
	mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
	var strSql = mySql.getString();
	var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
	if (strQueryResult) {
		myAlert(""+"审核已经确认，不可以再初始化！！"+"");
		return false;
	}

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIProfitLossCalmInputSql1");// 指定使用的Sql的id
	mySql1.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql1.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql1.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql1.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	mySql1.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
	var Sql = mySql1.getString();
	var strResult = easyQueryVer3(Sql, 1, 1, 1);

	if (strResult) {
		if (!confirm(""+"数据已被初始化计算过一次，确定要重新初始化并计算吗？"+"")) {
			return false;
		}
	}

	fm.OperateType.value = "ININTPARAM";
	try {
		if (verifyInput()) {
			var showStr = ""+"正在初始化..."+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function queryClick() {

	if (verify()) {

		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIProfitLossCalmInputSql3");// 指定使用的Sql的id
		mySql1.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
		mySql1.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
		mySql1.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
		mySql1.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
		mySql1.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql1.getString();
		temp1 = easyExecSql(strSQL);
		if (temp1 == null) {
			myAlert(""+"未完成计算，计算结果为空！"+"");
		} else {
			fm.Result.value = temp1;
		}
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIProfitLossCalmInputSql2");// 指定使用的Sql的id
		mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
		mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
		var SQL = mySql.getString();
		temp = easyExecSql(SQL);

		Mul10Grid.clearData();
		for ( var i = 0; i < temp.length; i++) {
			Mul10Grid.addOne();
			Mul10Grid.setRowColData(i, 1, temp[i][0]);
			Mul10Grid.setRowColData(i, 2, temp[i][1]);
			Mul10Grid.setRowColData(i, 3, temp[i][2]);
			Mul10Grid.setRowColData(i, 4, temp[i][3]);
			Mul10Grid.setRowColData(i, 5, temp[i][4]);
			Mul10Grid.setRowColData(i, 6, temp[i][5]);
			Mul10Grid.setRowColData(i, 7, temp[i][6]);
			Mul10Grid.setRowColData(i, 8, temp[i][7]);
			Mul10Grid.setRowColData(i, 9, temp[i][8]);
		}

	}

}

// 计算按钮
function button141() {

	// 如果审核确认则不可以重算
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossCalmInputSql4");// 指定使用的Sql的id
	mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
	var strSql = mySql.getString();
	var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
	if (strQueryResult) {
		myAlert(""+"已经审核确认，不可以重算！！"+"");
		return false;
	}
	var num = Mul10Grid.mulLineCount;
	if (num == '' || num == null || num == 0) {
		myAlert(""+"数据未进行初始化！"+"");
		return false;
	}

	for ( var i = 0; i < Mul10Grid.mulLineCount; i++) {
		num = i + 1;
		if (!isTel(Mul10Grid.getRowColData(i, 5))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"参数值录入不对！"+"");
			Mul10Grid.setRowColData(i, 5, Mul10Grid.getRowColData(i, 8));
			return false;
		}
	}
	fm.OperateType.value = "CALCULATE";
	try {
		if (verifyInput()) {
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.submit(); // 提交

		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function isTel(strValue) {
	var NUM = "0123456789.-";
	var i;
	if (strValue == null || strValue == "")
		return true;
	for (i = 0; i < strValue.length; i++) {
		if (NUM.indexOf(strValue.charAt(i)) < 0)
			return false;

	}
	return true;
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
		// var strSQL="select decode(a.InOutType,'01','收入','支出'), b.factorcode,
		// b.factorname, decode(a.inputtype,'01','系统计算','手工录入'), b.Factorvalue
		// ,decode(a.currency,'01','人民币','02','港币','03','美元'), a.currency from
		// RIProLossRela a,RIProLossValue b where
		// b.RIProfitNo='"+fm.RIProfitNo.value+"' and a.factorcode=b.factorcode
		// and a.Currency =b.Currency order by a.InOutType";
		// l10GridTurnPage.queryModal(strSQL, Mul10Grid);

	}

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossCalmInputSql5");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	temp = easyExecSql(strSQL);
	if (fm.OperateType.value == "CALCULATE") {
		fm.Result.value = temp;
	} else if (fm.OperateType.value == "ININTPARAM") {
		fm.Result.value = "";
	}
}
function afterCodeSelect(codeName, Field) {
	if (codeName == "riprofityear") {
		Mul10Grid.clearData();
	}
}
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
