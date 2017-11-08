//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass(); // 使用翻页功能，必须建立为全局变量
var sqlresourcename = "reinsure.RIAccRiskQuerySql";
// 提交，保存按钮对应操作
function submitForm() {
}

function accQuery() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRiskQuerySql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateNo.value);// 指定传入的参数，多个参数顺序添加
	var tSQL = mySql.getString();
	turnPage.queryModal(tSQL, AccumulateGrid);
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {

	// showInfo.close();
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	}
}

// 重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"在Proposal.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
	}
}

function ClosePage() {
	top.close();
}
