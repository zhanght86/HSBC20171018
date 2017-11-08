var showInfo;
var sqlresourcename = "reinsure.RIFeeRateBatchInputSql";
var turnPage = new turnPageClass();
window.onfocus = myonfocus;
var ImportPath;

function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

function queryFeeRateBatch() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateBatchInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.FeeTableNo.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, FeeRateBatchGrid);
}

// 提交，保存按钮对应操作
function submitForm() {
	var mySql2 = new SqlClass();
	mySql2.setResourceName(sqlresourcename);
	mySql2.setSqlId("RIFeeRateBatchInputSql4");// 指定使用的Sql的id
	mySql2.addSubPara(fm.FeeTableNo.value);// 指定传入的参数，多个参数顺序添加
	mySql2.addSubPara(fm.InureDate.value);// 指定传入的参数，多个参数顺序添加
	var sql2 = mySql2.getString();
	var result = easyExecSql(sql2);
	if (result != null) {
		myAlert(""+"生效日期必须大于当前生效日期最晚的批次！"+"");
		return;
	}
	
	
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateBatchInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(fm.FeeTableNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.BatchNo.value);// 指定传入的参数，多个参数顺序添加
	var sql = mySql.getString();
	var result = easyExecSql(sql);
	if (result != null) {
		myAlert(""+"费率表批次编号已存在！"+"");
		return;
	}
	if (fm.State.value == '01') {
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIFeeRateBatchInputSql3");// 指定使用的Sql的id
		mySql1.addSubPara(fm.FeeTableNo.value);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql1.getString();

		var tState = easyExecSql(strSQL);
		if (tState == '02' || tState == '' || tState == null) {
			myAlert(""+"费率表状态为无效，批次状态需置为无效！"+"");
			return false;
		}
	}
	try {
		fm.OperateType.value = "INSERT";
		if (verifyInput()) {
			LapseDateCharge();
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIFeeRateBatchSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function LapseDateCharge() {
	var row = FeeRateBatchGrid.mulLineCount;
	if (row != 0) {
		if (FeeRateBatchGrid.getRowColData(row - 1, 5) == ""
				|| FeeRateBatchGrid.getRowColData(row - 1, 5) == null) {
			fm.BatchNoAbove.value = FeeRateBatchGrid.getRowColData(row - 1, 3);
		}
	}
	return;
}
function updateClick() {
	fm.OperateType.value = "UPDATE";
	if (fm.State.value == '01') {
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIFeeRateBatchInputSql3");// 指定使用的Sql的id
		mySql1.addSubPara(fm.FeeTableNo.value);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql1.getString();
		var tState = easyExecSql(strSQL);
		if (tState == '02' || tState == '' || tState == null) {
			myAlert(""+"费率表状态为无效，批次状态需置为无效！"+"");
			return false;
		}
	}
	try {
		if (verifyInput()) {
			if (veriryInput3()) {
				var row = FeeRateBatchGrid.lastFocusRowNo;
				if (row < FeeRateBatchGrid.mulLineCount - 1
						&& fm.LapseDate.value == "") {
					if (FeeRateBatchGrid.getRowColData(row + 1, 1) != ""
							|| FeeRateBatchGrid.getRowColData(row + 1, 1) != null) {
						myAlert(""+"存在下一个批次号，不能将费率表失效日期清空！"+"");
						return false;
					}
				}
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIFeeRateBatchSave.jsp";
				fm.submit(); // 提交
			}
		}
		fm.feeBatchNo.readOnly = "";
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function deleteClick() {
	fm.OperateType.value = "DELETE";
	if (!confirm(""+"你确定要删除该费率表批次吗？"+"")) {
		return false;
	}
	if (veriryInput3()) {
		try {
			var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIFeeRateBatchSave.jsp";
			fm.submit(); // 提交
		} catch (ex) {
			showInfo.close();
			myAlert(ex);
		}
	}
}

function veriryInput3() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateBatchInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(fm.FeeTableNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.BatchNo.value);// 指定传入的参数，多个参数顺序添加
	var sql = mySql.getString();
	var result = easyExecSql(sql);
	if (result == null) {
		myAlert(""+"费率表批次编号不存在！"+"");
		return false;
	}
	return true;
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
		queryFeeRateBatch();
		if (fm.OperateType.value == "DELETE") {
			inputFeeRateBatch();
		}
	}
}

function ShowBatch() {
	var tSel = FeeRateBatchGrid.getSelNo();
	fm.BatchNo.value = FeeRateBatchGrid.getRowColData(tSel - 1, 3);
	fm.InureDate.value = FeeRateBatchGrid.getRowColData(tSel - 1, 4);
	fm.LapseDate.value = FeeRateBatchGrid.getRowColData(tSel - 1, 5);
	fm.SaveDataName.value = FeeRateBatchGrid.getRowColData(tSel - 1, 6);
	fm.State.value = FeeRateBatchGrid.getRowColData(tSel - 1, 8);
	fm.stateName.value = FeeRateBatchGrid.getRowColData(tSel - 1, 9);
	fm.SaveDataNameName.value = FeeRateBatchGrid.getRowColData(tSel - 1, 10);
	fm.feeBatchNo.readOnly = "true";
}

function inputFeeRateBatch() {
	fm.all('BatchNo').value = '';
	fm.all('InureDate').value = '';
	fm.all('LapseDate').value = '';
	fm.SaveDataName.value = "RIFeeRateTable";
	fm.SaveDataNameName.value = ""+"通用费率表"+"";
	fm.State.value = "02";
	fm.stateName.value = ""+"未生效"+"";
	fm.feeBatchNo.readOnly = "";
	fmImport.reset();
}
function FeeRateTableImp() {
	if (fmImport.FileName.value == "" || fmImport.FileName.value == null) {
		myAlert(""+"录入导入文件路径！"+"");
		return false;
	}
	getImportPath();
	ImportFile = fmImport.all('FileName').value;

	var showStr = ""+"正在上载数据……"+"";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+showStr;// encodeURI(encodeURI(showStr));
	showInfo = window.showModelessDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fmImport.action = "./RIFeeRateImportSave.jsp?ImportPath=" + ImportPath
			+ "&FeeTableNo=" + fm.FeeTableNo.value + "&BatchNo="
			+ fm.BatchNo.value;
	fmImport.submit(); // 提交
}

function getImportPath() {
	var strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);

	// 判断是否查询成功
	var result = easyExecSql(strSQL);
	if (result == null) {
		myAlert(""+"未找到上传路径"+"");
		return false;
	}
	ImportPath = result[0][0];
}

// 重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
	}
}

function ClosePage() {
	top.close();
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加操作
}
function alink() {
	window.location.href = "../temp/reinsure/feerateimp/LRFeeRateImport.xls";
}
