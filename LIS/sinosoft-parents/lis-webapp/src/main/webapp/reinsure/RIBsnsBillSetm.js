//该文件中包含客户端需要处理的函数和事件

//程序名称：RIBsnsBillSetm.js
//程序功能：账单结算
//创建日期：2011/6/17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillSetmInputSql";

// 查询账单按钮
function queryBill() {
	if (!verifyInput()) {
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillSetmInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.BillNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.BsnsStateCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.EndDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.Currency.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, Mul10Grid);
	showDiv(divFeeItem1, "false");
	showDiv(divFeeItem2, "false");
	showDiv(divInfoItem, "false");
}

// 查询明细按钮
function queryBillDetail() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请选择记录！"+"");
		return false;
	}
	var billno = Mul10Grid.getRowColData(tRow - 1, 1);
	var serialno = Mul10Grid.getRowColData(tRow - 1, 8);
	var currency = Mul10Grid.getRowColData(tRow - 1, 9);
	var dctype = Mul10Grid.getRowColData(tRow - 1, 10);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillSetmInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(serialno);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(billno);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(currency);// 指定传入的参数，多个参数顺序添加
	var strFeeSQL = mySql.getString();
	strArray = easyExecSql(strFeeSQL);
	showDiv(divInfoItem, "true");
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
				Mul12Grid.setRowColData(k, 6, strArray[k][5]);
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
				Mul11Grid.setRowColData(k, 5, strArray[k][5]);
			}
		}
	}

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIBsnsBillSetmInputSql3");// 指定使用的Sql的id
	mySql1.addSubPara(serialno);// 指定传入的参数，多个参数顺序添加
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
			Mul13Grid.setRowColData(k, 4, strArray[k][2]);
			Mul13Grid.setRowColData(k, 3, strArray[k][3]);
			Mul13Grid.setRowColData(k, 5, strArray[k][4]);
		}
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
