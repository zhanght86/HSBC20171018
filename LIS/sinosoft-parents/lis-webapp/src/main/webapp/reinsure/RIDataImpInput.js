var showInfo;
var turnPage = new turnPageClass();
window.onfocus = myonfocus;
var ImportPath;
var sqlresourcename = "reinsure.RIDataImpInputSql";

function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

function veriryInput3() {
	return true;
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
	}
}

function FeeRateTableImp() {
	if (fmImport.FileName.value == "" || fmImport.FileName.value == null) {
		myAlert(""+"请选择导入文件！"+"");
		return false;
	}
	getImportPath();

	ImportFile = fmImport.all('FileName').value;

	var showStr = ""+"正在上载数据……"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	showInfo = window.showModelessDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fmImport.action = "./RIDataImpSave.jsp?ImportPath=" + ImportPath;
	fmImport.submit(); // 提交
}

function getImportPath() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIDataImpInputSql1");//指定使用的Sql的id
	mySql.addSubPara("");// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);

	// 判断是否查询成功
	if (!turnPage.strQueryResult) {
		myAlert(""+"未找到上传路径"+"");
		return;
	}
	// 清空数据容器，两个不同查询共享一个turnPage对象时必须使用，最好加上，容错
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	// 查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

	ImportPath = turnPage.arrDataCacheSet[0][0];
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
