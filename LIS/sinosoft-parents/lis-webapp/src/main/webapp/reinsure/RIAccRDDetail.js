var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIAccRDDetailInputSql";
function queryLink() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDDetailInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, RIAccRiskDutyGrid);
}
function showRiskDuty() {
	var tSel = RIAccRiskDutyGrid.getSelNo();
	fm.all('AssociatedCode').value = RIAccRiskDutyGrid.getRowColData(tSel - 1,1);
	fm.all('AssociatedName').value = RIAccRiskDutyGrid.getRowColData(tSel - 1,2);
	//IE11-大小写
	fm.all('GetDutyCode').value = RIAccRiskDutyGrid.getRowColData(tSel - 1, 3);
	//IE11-大小写
	fm.all('GetDutyName').value = RIAccRiskDutyGrid.getRowColData(tSel - 1, 4);
}
function addClick() {
	fm.OperateType.value = "ADD";
	try {
		if (verifyInput()) {
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIAccRDDetailInputSql2");// 指定使用的Sql的id
			mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.AssociatedCode.value);// 指定传入的参数，多个参数顺序添加
			mySql.addSubPara(fm.GetDutyCode.value);// 指定传入的参数，多个参数顺序添加
			var sql = mySql.getString();
			var rs = easyExecSql(sql);
			if (rs != null) {
				myAlert(""+"该责任已经存在，请重新输入！"+"");
				return;
			}
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIAccRDDetailSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}
function deleteClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDDetailInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.AssociatedCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.GetDutyCode.value);// 指定传入的参数，多个参数顺序添加
	var sql = mySql.getString();
	var rs = easyExecSql(sql);
	if (rs == null) {
		myAlert(""+"该责任不存在，请重新输入！"+"");
		return; 
	}
	if (!confirm(""+"您确定要删除吗？"+"")) {
		return false;
	}
	fm.OperateType.value = "DEL";
	try {
		if (verifyInput()) {
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIAccRDDetailSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}
function updateClick() {
	fm.OperateType.value = "UPD";
	try {
		var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action = "./RIAccRDDetailSave.jsp";
		fm.submit(); // 提交
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}

function resetAcc() {
	fm.AssociatedCode.value = "";
	fm.AssociatedName.value = "";
	fm.GetDutyCode.value = "";
	fm.GetDutyName.value = "";
}
function closePage() {
	top.close();
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
		queryLink();
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "rilmrisk") {
		fm.GetDutyCode.value="";
		fm.GetDutyName.value="";
	}
}
