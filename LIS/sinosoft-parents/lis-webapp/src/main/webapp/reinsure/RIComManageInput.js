var showInfo;
var sqlresourcename = "reinsure.RIComManageInputSql";
var turnPage = new turnPageClass();
window.onfocus = myonfocus;

function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

// 提交，保存按钮对应操作
function submitForm() {
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput() && RelateGrid.checkValue("RelateGrid")) {
			if (veriryInput3()) {
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIComManageSave.jsp";
				fm.submit(); // 提交
			} else {
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput() {

	return ture;

}

function veriryInput3() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIComManageInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.ReComCode.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	var strResult = easyExecSql(strSQL);

	if (strResult != null && strResult != "") {
		myAlert(""+"该再保公司已存在！"+"");
		return false;
	}
	if (fm.ReComName.value == "" || fm.ReComName.value == null) {
		myAlert(""+"请录入公司名称！"+"");
		return false;
	}
	if (!isTel(fm.FaxNo.value)) {
		myAlert(""+"传真录入格式不对！"+"");
		return false;
	}
	if (!isInteger1(fm.PostalCode.value)) {
		return false;
	}

	var rowNum = RelateGrid.mulLineCount; // 行数

	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (RelateGrid.getRowColData(i, 4) == null
				|| RelateGrid.getRowColData(i, 1) == "") {
			myAlert(""+"第"+"" + num + ""+"行"+","+"请录入联系人姓名！"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 4))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"联系电话录入不对！"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 5))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"手机格式录入不对！"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 6))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"传真格式录入不对！"+"");
			return false;
		}
		if (!isEMail(RelateGrid.getRowColData(i, 7))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"电子邮箱录格式入不对！"+"");
			return false;
		}
	}

	return true;
}

function queryClick() {
	fm.OperateType.value = "QUERY";

	window.open("./FrameRIComQuery.jsp?ReComCode=" + fm.ReComCode.value);
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, ContentNO, ReComCode, CertifyCode) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
		fm.ReComCode.value = ContentNO;

		// content="保存成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}

	if (fm.OperateType.value == "DELETE") {
		resetForm();
	}

}

function updateClick() {
	fm.OperateType.value = "UPDATE";
	try {
		if (veriryInput4()) {
			if (verifyInput()) {
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIComManageSave.jsp";
				fm.submit(); // 提交
			} else {
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput4() // UPDATE 校验
{
	if (fm.ReComCode.value == "" || fm.ReComCode.value == null) {
		myAlert(""+"请先查询公司信息！"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIComManageInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(fm.ReComCode.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	var strResult = easyExecSql(strSQL);

	if (strResult == null) {
		myAlert(""+"该再保公司不存在！"+"");
		return false;
	}
	if (fm.ReComName.value == "" || fm.ReComName.value == null) {
		myAlert(""+"请录入公司名称！"+"");
		return false;
	}
	if (!isTel(fm.FaxNo.value)) {
		myAlert(""+"传真录入格式不对！"+"");
		return false;
	}

	if (!isInteger1(fm.PostalCode.value)) {
		return false;
	}

	var rowNum = RelateGrid.mulLineCount; // 行数
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (RelateGrid.getRowColData(i, 1) == ""
				|| RelateGrid.getRowColData(i, 1) == null) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"请录入联系人姓名！"+"");
			return false;
		}

		if (!isTel(RelateGrid.getRowColData(i, 4))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"电话录入不对！"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 5))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"手机录入不对！"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 6))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"传真录入不对！"+"");
			return false;
		}
		if (!isEMail(RelateGrid.getRowColData(i, 7))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"电子邮箱录入不对！"+"");
			return false;
		}
	}
	return true;

}

function deleteClick() {
	fm.OperateType.value = "DELETE";
	if (!confirm(""+"你确定要删除该批次吗？"+"")) {
		return false;
	}
	try {
		if (verifyInput5()) {
			var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIComManageSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function verifyInput5() {
	return true;
}

function afterQuery() {
}

// 重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
	}
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加操作
}

/**
 * 对输入域是否是电话的校验
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            输入数值表达式或字符串表达式
 * @return 布尔值（true--是整数, false--不是整数）
 */
function isTel(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^[0-9(-)]+$/.test(strValue)) {
		return true;
	} else {
		myAlert(""+"电话格式不正确！"+"");
		return false;
	}
	return true;
}

/**
 * 对输入域是否是网址的校验
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            输入数值表达式或字符串表达式
 * @return 布尔值（true--是整数, false--不是整数）
 */
function isWeb(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^http[s?]:\/\//.test(strValue)) {
		return true;
	} else {
		myAlert(""+"网址格式不正确！"+"");
		return false;
	}
	return true;
}

/**
 * 对输入域是否是邮编的校验
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            输入数值表达式或字符串表达式
 * @return 布尔值（true--是整数, false--不是整数）
 */
function isInteger1(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^\d{6}$/.test(strValue)) {
		return true;
	} else {
		myAlert(""+"邮政编码格式不正确！"+"");
		return false;
	}
	return true;
}

/**
 * 对输入域是否是邮箱的校验
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            输入数值表达式或字符串表达式
 * @return 布尔值（true--是整数, false--不是整数）
 */
function isEMail(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^\w+@\w+\..+$/.test(strValue)) {
		return true;
	} else {
		myAlert(""+"邮箱格式不正确！"+"");
		return false;
	}
	return true;
}
