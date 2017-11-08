//该文件中包含客户端需要处理的函数和事件

//程序名称：RIProfitLossDef.js
//程序功能：盈余佣金定义
//创建日期：2011/8/18
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIProfitLossDefInputSql";

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
// 提交，保存按钮对应操作
function submitForm() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	var strSql = mySql.getString();
	var strQueryResult = easyExecSql(strSql);
	if (strQueryResult!=null) {
		myAlert(""+"盈余佣金编号已存在！"+"");
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

				fm.action = "./RIProfitLossDefSave.jsp";
				fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
// 修改
function updateClick() {
	fm.OperateType.value = "UPDATE";
	try {
		if (verifyInput() == true) {

			{				
				var mySql = new SqlClass();
				mySql.setResourceName(sqlresourcename);
				mySql.setSqlId("RIProfitLossDefInputSql2");// 指定使用的Sql的id
				mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
				var strSQL = mySql.getString();
				var arr= easyExecSql(strSQL,1,0,1 ); //!!每次出现easyExeSql()查询都要判断是否为空,例:
				if(arr==null)
				{
					myAlert(""+"没有符合条件的数据"+"");
					return;
				}				
				var num=Mul9Grid.mulLineCount;
				if(num==''||num==null||num==0){
					
					myAlert(""+"关联方案为空！"+"");
					return false;
				}
				var mySql1 = new SqlClass();
				mySql1.setResourceName(sqlresourcename);
				mySql1.setSqlId("RIProfitLossDefInputSql3");// 指定使用的Sql的id
				mySql1.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
				var SQL = mySql1.getString();
				var arr1= easyExecSql(SQL,1,0,1 ); //!!每次出现easyExeSql()查询都要判断是否为空,例:
				
				
				
				
				
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIProfitLossDefSave.jsp";
				fm.submit(); // 提交
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
// 删除
function deleteClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefInputSql4");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	
	

	var arr= easyExecSql( strSQL,1,0,1 ); //!!每次出现easyExeSql()查询都要判断是否为空,例:
	if(arr==null)
	{
		myAlert(""+"没有符合条件的数据"+"");
		return;
	}
	fm.OperateType.value = "DELETE";
	if (!confirm(""+"你确定要删除该批次吗？"+"")) {
		return false;
	}
	try {
		if (verifyInput() == true) {

			var i = 0;
			var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIProfitLossDefSave.jsp";
			fm.submit(); // 提交
		}

	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
function queryClick() {
	var url = "RIProfitLossDefQueryInput.jsp";
	var param = "";
	window.open("./FrameMainCommon.jsp?Interface=" + url + param, "true");
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
		showDiv(operateButton, "true");
		showDiv(inputButton, "false");
		// 执行下一步操作
		if (fm.OperateType.value == "DELETE") {
			initForm();
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
