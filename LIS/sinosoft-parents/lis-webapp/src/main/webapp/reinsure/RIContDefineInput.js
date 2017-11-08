var showInfo;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIContDefineInputSql";
window.onfocus = myonfocus;
var typeRadio = "XXXX";
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
		if (verifyInput()) {
			if ((veriryInput3()) && (veriryInput7())) {
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIContDefineSave.jsp?ModType=" + typeRadio;
				fm.submit(); // 提交
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput2() {
	if (compareDate(fm.RValidate.value, fm.RInvalidate.value) == 1) {
		myAlert(""+"起始日期不能大于终止日期!"+"");
		return false;
	}

	var rowNum = SignerGrid.mulLineCount; // 行数
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (SignerGrid.getRowColData(i, 1) == 0
				|| SignerGrid.getRowColData(i, 1) == null) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"请录入合同签订人所在公司的名称！"+"");
			return false;
		}
		if (SignerGrid.getRowColData(i, 3) == 0
				|| SignerGrid.getRowColData(i, 3) == null) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"请录入合同签订人的姓名！"+"");
			return false;
		}

		if (!isTel(SignerGrid.getRowColData(i, 5))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"电话录入格式不对！"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 6))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"手机录入格式不对！"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 7))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"传真录入格式不对！"+"");
			return false;
		}
		if (!isEMail(SignerGrid.getRowColData(i, 8))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"电子邮箱录入格式不对！"+"");
			return false;
		}
	}
	return true;
}

function veriryInput3() {
	if (compareDate(fm.RValidate.value, fm.RInvalidate.value) == 1) {
		myAlert(""+"起始日期不能大于终止日期!"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContDefineInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	var rs = easyExecSql(strSQL);
	if (rs != null) {
		myAlert(""+"合同编号已存在，请重新输入！"+"");
		return;
	}
	// if (typeRadio == "1") {
	// if (fm.ModRIContNo.value == null || fm.ModRIContNo.value == ""
	// || fm.ModRIContName.value == null
	// || fm.ModRIContName.value == "") {
	// alert("合同修改模式"修改合同编码"、"修改合同名称"不能为空 ");
	// return false;
	// }
	// }
	var rowNum = SignerGrid.mulLineCount; // 行数

	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (SignerGrid.getRowColData(i, 1) == 0
				|| SignerGrid.getRowColData(i, 1) == null) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"请录入合同签订人所在公司的名称！"+"");
			return false;
		}

		if (SignerGrid.getRowColData(i, 3) == 0
				|| SignerGrid.getRowColData(i, 3) == null) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"请录入合同签订人的姓名！"+"");
			return false;
		}

		// if(SignerGrid.getRowColData(i,4)==0||SignerGrid.getRowColData(i,4)==null)
		// {
		// alert("第"+num+"行,请录入合同签订人的职务！");
		// return false;
		// }

		if (!isTel(SignerGrid.getRowColData(i, 5))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"电话录入格式不对！"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 6))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"手机录入格式不对！"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 7))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"传真录入格式不对！"+"");
			return false;
		}
		if (!isEMail(SignerGrid.getRowColData(i, 8))) {
			myAlert(""+"第"+"" + num + ""+"行"+","+"电子邮箱录入格式不对！"+"");
			return false;
		}
	}

	return true;
}

function queryClick() {
	fm.OperateType.value = "QUERY";
	window.open("./FrameRIContQuery.jsp?RIContNo=" + fm.RIContNo.value
			+ "&PageFlag=CONT");
}

function ChooseOldCont() {
	for ( var i = 0; i < fm.ContModType.length; i++) {
		if (fm.ContModType[i].checked) {
			typeRadio = fm.ContModType[i].value;
			break;
		}
	}
	// if (typeRadio == "0") {
	// divTitle2.style.display = "none";
	// divInput2.style.display = "none";
	// fm.ModRIContNo.value = "";
	// fm.ModRIContName.value = "";
	// }
	// if (typeRadio == "1") {
	// divTitle2.style.display = "";
	// divInput2.style.display = "";
	// fm.OperateType.value = "CHOICE";
	// window.open("./FrameRIChoiceQuery.jsp");
	// }
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
		fm.RIContNo.value = ContentNO;
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		if (fm.OperateType.value == "DELETE") {
			resetForm();
		}
	}
}
// 修改
function updateClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContDefineInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if (arrResult == null) {
		myAlert(""+"合同编号不存在！"+"");
		return;
	}
	try {
		fm.OperateType.value = "UPDATE";
		if (verifyInput()) {
			if (!confirm(""+"你确定要修改该合同吗？"+"")) {
				return false;
			}
			if ((veriryInput2()) && (veriryInput7()) && (checkContState())) {
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIContDefineSave.jsp?ModType=" + typeRadio;
				fm.submit(); // 提交
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function checkContState() {
	var tContState = fm.ContState.value;
	if (tContState == "01") {
		try {
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIContDefineInputSql3");// 指定使用的Sql的id
			mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
			var tSql = mySql.getString();
			var result = easyExecSql(tSql, 1, 0, 1);

			if (result != 0) {
				myAlert(""+"本合同下有未生效再保方案，请更新所有再保方案为生效后修改"+"");
				return false;
			}
		} catch (ex) {
			myAlert(ex);
			return false;
		}
	}
	return true;
}

function veriryInput4() // UPDATE 校验
{
	if (compareDate(fm.RValidate.value, fm.RInvalidate.value) == 1) {
		myAlert(""+"起始日期不能大于终止日期!"+"");
		return false;
	}
	return true;
}

// 删除
function deleteClick() {
	if (!confirm(""+"你确定要删除该合同吗？"+"")) {
		return false;
	}
	fm.OperateType.value = "DELETE";
	try {
		if (verifyInput()) {
			if (veriryInput5()) {
				var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIContDefineSave.jsp";
				fm.submit(); // 提交
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput5() {
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

function nextStep() {
	if (fm.RIContNo.value == "" || fm.RIContNo.value == null) {
		myAlert(""+"请先查询再保合同再添加分保信息！"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContDefineInputSql4");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if (arrResult == null || arrResult == "") {
		myAlert(""+"合同编号为空或未保存合同基本信息！"+"");
		return false;
	}
	var reContCode = fm.all("RIContNo").value;
	var reCountType = fm.all("ReCountType").value;
	var varSrc = "&ReContCode=" + reContCode;
	window.open(
			"./FrameMainCessInfo.jsp?Interface=RISchemaInput.jsp&reCountType="
					+ reCountType + "" + varSrc, "true"); // +varSrc,
}

// 检查电子邮箱格式是不是正确
function isEMail(strValue) {
	var NUM1 = "@";
	var NUM2 = ".";
	var i;
	if (strValue == null || strValue == "")
		return true;
	for (i = 0; i < strValue.length; i++) {
		if (strValue.indexOf(NUM1) < 0) {
			return false;
		}
		if (strValue.indexOf(NUM2) < 0) {
			return false;
		}
	}
	return true;
}

// 对输入的电话号码进行验证
function isTel(strValue) {
	var NUM = "0123456789-() ";
	var i;
	if (strValue == null || strValue == "")
		return true;
	for (i = 0; i < strValue.length; i++) {
		if (NUM.indexOf(strValue.charAt(i)) < 0)
			return false;

	}
	return true;
}

// 要素校验
function veriryInput7() {
	// 校验不能录入重复合同要素
	for ( var n = 0; n < FactorGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < FactorGrid.mulLineCount; m++) {
			if (FactorGrid.getRowColData(n, 1) == FactorGrid
					.getRowColData(m, 1)) {
				myAlert(""+"不能录入重复要素!"+"");
				return false;
			}
		}
	}
	for ( var n = 0; n < FactorGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < FactorGrid.mulLineCount; m++) {
			if (FactorGrid.getRowColData(n, 2) == FactorGrid
					.getRowColData(m, 2)) {
				myAlert(""+"不能录入重复要素!"+"");
				return false;
			}
		}
	}
	isNull = false; // 检查是否录入要素值标记
	for ( var i = 0; i < FactorGrid.mulLineCount; i++) {
		if (FactorGrid.getRowColData(i, 3) == null
				|| FactorGrid.getRowColData(i, 3) == "") {
			isNull = true;
		}
	}
	if (isNull) {
		myAlert(""+"必须录入要素值!"+"");
		return false;
	}
	return true;
}
