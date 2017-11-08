//该文件中包含客户端需要处理的函数和事件

//程序名称：RIBsnsBillUW.js
//程序功能：账单审核
//创建日期：2011/6/14
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillUWInputSql";
// 查询账单按钮
function queryBill() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillUWInputSql1");//指定使用的Sql的id
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
	mySql.setSqlId("RIBsnsBillUWInputSql2");//指定使用的Sql的id
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
				Mul12Grid.setRowColData(k, (strArray[k][4] == "01")^(strArray[k][2]<0)?4:5, Math.abs(strArray[k][2]).toString());
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
	mySql1.setSqlId("RIBsnsBillUWInputSql3");//指定使用的Sql的id
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

// 结论保存按钮
function button102() {
	try {
		if (verifyInput()) {
			if (veriry()) {
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.submit(); // 提交
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriry() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请选择记录！"+"");
		return false;
	}
	if (fm.RIUWReport.value == "03") {
				
		var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillUWInputSql4");//指定使用的Sql的id
	mySql.addSubPara(Mul10Grid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(Mul10Grid.getRowColData(tRow - 1, 5));// 指定传入的参数，多个参数顺序添加
	var tSQL = mySql.getString();			
				
		var strArray = easyExecSql(tSQL);
		if (strArray != null) {
			myAlert(""+"前期账单未结算，不能结算当前期次！"+"");
			return false;
		}
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
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
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
