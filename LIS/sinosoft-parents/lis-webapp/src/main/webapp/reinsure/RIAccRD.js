//该文件中包含客户端需要处理的函数和事件

//程序名称：RIAccRD.js
//程序功能：分出责任定义
//创建日期：2011/6/16
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var sqlresourcename = "reinsure.RIAccRDInputSql";
window.onfocus = myonfocus;
var turnPage = new turnPageClass();

function veriryInput() {
	var rowNum = RIAccRiskDutyGrid.mulLineCount; // 行数
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;

		if (RIAccRiskDutyGrid.getRowColData(i, 1) == ""
				|| RIAccRiskDutyGrid.getRowColData(i, 1) == null) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"险种代码录入为空！"+"");
			return false;
		}
		if (RIAccRiskDutyGrid.getRowColData(i, 3) == ""
				|| RIAccRiskDutyGrid.getRowColData(i, 3) == null) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"责任代码录入为空！"+"");
			return false;
		}

	}
	for ( var n = 0; n < RIAccRiskDutyGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < RIAccRiskDutyGrid.mulLineCount; m++) {
			if (RIAccRiskDutyGrid.getRowColData(n, 1) == RIAccRiskDutyGrid
					.getRowColData(m, 1)) {
				myAlert(""+"不能录入重复险种代码!"+"");
				return false;
			}
		}
	}
	return true;

}
// 增加按钮
function submitForm() {
	if (existAccumulateDefNO()) {
		myAlert(""+"分出责任编码已存在，请重新输入！"+"");
		return false;
	}
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput()) {
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIAccRDSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
// 检查分出责任编码
function existAccumulateDefNO() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();

	var arrResult = easyExecSql(strSQL);
	if (arrResult == null) {
		fm.CalOrder.value = "02";
		return false;
	}
	fm.CalOrder.value = arrResult[0][0];
	return true;
}
// 修改按钮
function updateClick() {
	if (!existAccumulateDefNO()) {
		myAlert(""+"分出责任编码不存在，请重新输入！"+"");
		return false;
	}
	fm.OperateType.value = "UPDATE";
	try {
		if (verifyInput()) {
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIAccRDSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

// 查询按钮
function queryClick() {
	var url = "RIAccRDQueryInput.jsp";
	var param = "";
	window.open("./FrameMainCommon.jsp?Interface=" + url + param, "true");
}

// 删除按钮
function deleteClick() {
	if (!existAccumulateDefNO()) {
		myAlert(""+"分出责任编码不存在，请重新输入！"+"");
		return false;
	}
	if (!confirm(""+"你确定要删除该批次吗？"+"")) {
		return false;
	}
	fm.OperateType.value = "DELETE";
	try {
		var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

		fm.action = "./RIAccRDSave.jsp";
		fm.submit(); // 提交
		fm.reset();
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

// 共保费用定义
function button99() {
	var url = "RIAccDistillInput.jsp";
	var param = "&AccNo=" + fm.AccumulateDefNO.value + "&AccName="
			+ fm.AccumulateDefName.value;

	window.open("./FrameMainDistill.jsp?Interface=" + url + param, "true");
}

// 分保特性定义按钮
function button100() {
	var tRow = RIAccRiskDutyGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请您先进行选择!"+"");
		return;
	}

	var url = "RIAccFeaturesInput.jsp";
	var param = "&AccNo=" + fm.AccumulateDefNO.value + "&AccName="
			+ fm.AccumulateDefName.value + "&RiskCode="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 1) + "&RiskName="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 2) + "&DutyCode="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 3) + "&DutyName="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 4);

	window.open("./FrameMainAccumu.jsp?Interface=" + url + param, "true");
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
		if (fm.OperateType.value == "DELETE") {
			fm.reset();
		}
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "riacctype") {
		if (Field.value == "01") {
			fm.cacctype.style.display = '';
			divCode1.style.display = "none";
			divName1.style.display = "none";

			fm.caccspec.style.display = 'none';
			fm.RIRiskFeature.value = '';
			fm.RIRiskFeatureName.value = '';
		} else {
			fm.cacctype.style.display = 'none';
			divCode1.style.display = "";
			divName1.style.display = "";

			fm.RIRiskFeature.value = '00';
			fm.RIRiskFeatureName.value = ''+"正常累计分保"+'';
		}
	}
	if (codeName == "ririskfeature") {
		if (Field.value == "01") {
			fm.caccspec.style.display = '';
		} else {
			fm.caccspec.style.display = 'none';
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
function nextStep() {
	if (fm.AccumulateDefNO.value == "") {
		myAlert(""+"请先查询"+"");
		return;
	}
	var AccumulateDefNO = fm.all('AccumulateDefNO').value;
	var varSrc = "&AccumulateDefNO=" + AccumulateDefNO;
	window.open("./FrameMainCessInfo.jsp?Interface=RIAccRDDetailInput.jsp"
			+ varSrc, "true"); // +varSrc,
}
