//该文件中包含客户端需要处理的函数和事件

//程序名称：RIBsnsBillModify.js
//程序功能：账单修改
//创建日期：2011-08-18
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillModifyInputSql";
// 查询账单按钮
function queryBill() {
	if (!verifyInput()) {
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillModifyInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.BillNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.EndDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, Mul10Grid);
	showDiv(divFeeItem1, "false");
	showDiv(divFeeItem2, "false");
	showDiv(divInfoItem, "false");
	showDiv(divButton, "false");

}

// 查询明细按钮
function queryBillDetail() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请选择记录！"+"");
		return false;
	}
	var billno = Mul10Grid.getRowColData(tRow - 1, 1);
	var batchno = Mul10Grid.getRowColData(tRow - 1, 8);
	var currency = Mul10Grid.getRowColData(tRow - 1, 9);
	var dctype = Mul10Grid.getRowColData(tRow - 1, 10);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillModifyInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(batchno);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(billno);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(currency);// 指定传入的参数，多个参数顺序添加
	var strFeeSQL = mySql.getString();

	strArray = easyExecSql(strFeeSQL);
	showDiv(divInfoItem, "true");
	showDiv(divButton, "true");
	if (dctype == "02") {
		showDiv(divFeeItem1, "false");
		showDiv(divFeeItem2, "true");

		Mul12Grid.clearData();
		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				Mul12Grid.addOne("Mul12Grid");
				Mul12Grid.setRowColData(k, 1, strArray[k][0]);
				Mul12Grid.setRowColData(k, 2, strArray[k][1]);
				Mul12Grid.setRowColData(k, 3, strArray[k][3]);
				Mul12Grid.setRowColData(k, 8, strArray[k][4]);
				Mul12Grid.setRowColData(k, 9, strArray[k][5]);
				Mul12Grid.setRowColData(k, 10, strArray[k][6]);
				Mul12Grid.setRowColData(k, (strArray[k][4] == "01")
						^ (strArray[k][2] < 0) ? 6 : 7, Math
						.abs(strArray[k][2]).toString());
				Mul12Grid.setRowColData(k, (strArray[k][4] == "01")
						^ (strArray[k][2] < 0) ? 4 : 5, Math
						.abs(strArray[k][2]).toString());

			}
		}

	} else {
		showDiv(divFeeItem1, "true");
		showDiv(divFeeItem2, "false");

		Mul11Grid.clearData();

		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				Mul11Grid.addOne("Mul11Grid");
				Mul11Grid.setRowColData(k, 1, strArray[k][0]);
				Mul11Grid.setRowColData(k, 2, strArray[k][1]);
				Mul11Grid.setRowColData(k, 3, strArray[k][3]);
				Mul11Grid.setRowColData(k, 4, strArray[k][2]);
				Mul11Grid.setRowColData(k, 5, strArray[k][2]);
				Mul11Grid.setRowColData(k, 6, strArray[k][5]);
				Mul11Grid.setRowColData(k, 7, strArray[k][6]);
			}
		}
	}

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIBsnsBillModifyInputSql3");// 指定使用的Sql的id
	mySql1.addSubPara(batchno);// 指定传入的参数，多个参数顺序添加
	mySql1.addSubPara(billno);// 指定传入的参数，多个参数顺序添加
	mySql1.addSubPara(currency);// 指定传入的参数，多个参数顺序添加
	var strInfoSQL = mySql1.getString();

	strArray = easyExecSql(strInfoSQL);
	Mul13Grid.clearData();
	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			Mul13Grid.addOne("Mul13Grid");
			Mul13Grid.setRowColData(k, 1, strArray[k][0]);
			Mul13Grid.setRowColData(k, 2, strArray[k][1]);
			Mul13Grid.setRowColData(k, 3, strArray[k][3]);
			Mul13Grid.setRowColData(k, 4, strArray[k][2]);
			Mul13Grid.setRowColData(k, 5, strArray[k][2]);
			Mul13Grid.setRowColData(k, 6, strArray[k][4]);
			Mul13Grid.setRowColData(k, 7, strArray[k][5]);
		}
	}
}

function veriry() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请选择记录！"+"");
		return false;
	}
	return true;
}

function veriryModify() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请选择记录！"+"");
		return false;
	}

	var dctype = Mul10Grid.getRowColData(tRow - 1, 10);
	myAlert(dctype);
	if (dctype == "02") {
		for ( var n = 0; n < Mul12Grid.mulLineCount; n++) {
			if (Mul12Grid.getRowColData(n, 10) == "01"
					&& (Mul12Grid.getRowColData(n, 4) != Mul12Grid
							.getRowColData(n, 6) || Mul12Grid.getRowColData(n,
							5) != Mul12Grid.getRowColData(n, 7))) {
				myAlert(""+"费用项，第"+"" + (n + 1) + ""+"行，系统计算项不能修改！"+"");
				Mul12Grid.setRowColData(n, 4, Mul12Grid.getRowColData(n, 6));
				Mul12Grid.setRowColData(n, 5, Mul12Grid.getRowColData(n, 7));
				return false;
			}
			if (Mul12Grid.getRowColData(n, 4) != ""
					&& Mul12Grid.getRowColData(n, 5) != "") {
				myAlert(""+"费用项，第"+"" + (n + 1) + ""+"行，借贷方不能同时存在！"+"");
				return false;
			}
			if (Mul12Grid.getRowColData(n, 4) == ""
					&& Mul12Grid.getRowColData(n, 5) == "") {
				myAlert(""+"费用项，第"+"" + (n + 1) + ""+"行，借贷方不能同时为空！"+"");
				return false;
			}
		}
	} else {
		for ( var n = 0; n < Mul11Grid.mulLineCount; n++) {
			if (Mul11Grid.getRowColData(n, 7) == "01"
					&& Mul11Grid.getRowColData(n, 4) != Mul11Grid
							.getRowColData(n, 5)) {
				myAlert(""+"费用项，第"+"" + (n + 1) + ""+"行，系统计算项不能修改！"+"");
				Mul11Grid.setRowColData(n, 4, Mul11Grid.getRowColData(n, 5));
				return false;
			}
		}
	}

	for ( var n = 0; n < Mul13Grid.mulLineCount; n++) {
		if (Mul13Grid.getRowColData(n,7) == "01"
				&& Mul13Grid.getRowColData(n, 4) != Mul13Grid.getRowColData(n,
						5)) {
			myAlert(""+"信息项，第"+"" + (n + 1) + ""+"行，系统计算项不能修改！"+"");
			Mul13Grid.setRowColData(n, 4, Mul13Grid.getRowColData(n, 5));
			return false;
		}
	}

	return true;
}

function veriryUW() {
	if (!veriry()) {
		return false;
	}
	return true;
}

// 修 改按钮
function modify() {
	fm.OperateType.value = "MODIFY";
	try {
		if (verifyInput() && veriryModify()) {
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

function reCal() {
	fm.OperateType.value = "RECAL";
	try {
		if (verifyInput() && veriryUW()) {
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

function submitUW() {
	if (!confirm(""+"你确定你已经保存当前修改内容，并提交审核吗？提交审核后不可修改"+"")) {
		return false;
	}
	fm.OperateType.value = "SUBMIT";
	try {
		if (verifyInput() && veriryUW()) {
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.submit();
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function addbillno(v, s) {
	var billno = Mul10Grid.getRowColData(Mul10Grid.getSelNo() - 1, 1);
	switch (s) {
	case "1":
		Mul11Grid.setRowColData(Mul11Grid.mulLineCount - 1, 6, billno);
		break;
	case "2":
		Mul12Grid.setRowColData(Mul12Grid.mulLineCount - 1, 9, billno);
		break;
	case "3":
		Mul13Grid.setRowColData(Mul13Grid.mulLineCount - 1, 6, billno);
		break;
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
		queryBill();
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
