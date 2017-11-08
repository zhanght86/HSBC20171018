//该文件中包含客户端需要处理的函数和事件

//程序名称：RIRateMaint.js
//程序功能：利率维护
//创建日期：2011/6/17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var sqlresourcename = "reinsure.RIRateMaintInputSql";
var turnPage = new turnPageClass();

function veriryInsert() {
	if (fm.EndDate.value != "" && fm.StartDate.value > fm.EndDate.value) {
		myAlert(""+"生效日期不能大于失效日期！"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.InterestType.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RiskCode.value);// 指定传入的参数，多个参数顺序添加
	var tSQL = mySql.getString();
	var strArray = easyExecSql(tSQL);
	if (strArray != null) {
		myAlert(""+"生效日期必须大于当前生效日期最晚的利率"+"");
		return false;
	}
	return true;
}

function veriryDelete() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.InterestType.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RiskCode.value);// 指定传入的参数，多个参数顺序添加
	var tSQL = mySql.getString();
	var strArray = easyExecSql(tSQL);
	if (strArray != null) {
		myAlert(""+"只能删除有效期最晚的数据"+"");
		return false;
	}
	return true;
}
// 保存按钮
function button103() {
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput()) {
			if (veriryInsert()) {
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
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

// 查询按钮
function button104() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql3");// 指定使用的Sql的id
	mySql.addSubPara(fm.RiskCodeQuery.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.InterestTypeQuery.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.StartDateQuery.value);// 指定传入的参数，多个参数顺序添加
	var tSQL = mySql.getString();
	turnPage.queryModal(tSQL, Mul10Grid);
}

// 删除按钮
function button105() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请选择要删除的批次！"+"");
		return false;
	}
	if (!confirm(""+"你确定要删除该批次吗？"+"")) {
		return false;
	}
	var sn = Mul10Grid.getRowColData(tRow - 1, 1);
	fm.OperateType.value = "DELETE";
	try {
		if (veriryDelete()) {
			var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "RIRateMaintSave.jsp?sn=" + sn;
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function showFormInfo() {
	var sn = Mul10Grid.getRowColData(Mul10Grid.getSelNo() - 1, 1);
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql4");// 指定使用的Sql的id
	mySql.addSubPara(sn);// 指定传入的参数，多个参数顺序添加
	var tSQL = mySql.getString();
   var	strArray = easyExecSql(tSQL);
	if (strArray == null) {
		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
		return false;
	}

	fm.all('RiskCode').value = strArray[0][0];
	fm.all('RiskName').value = strArray[0][1];
	fm.all('InterestType').value = strArray[0][4];
	fm.all('InterestTypeName').value = strArray[0][5];
	fm.all('InterestRate').value = strArray[0][6];
	fm.all('StartDate').value = strArray[0][7];
	fm.all('EndDate').value = strArray[0][8];
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
		button104();
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
