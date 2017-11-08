//该文件中包含客户端需要处理的函数和事件

//程序名称：RIProfitLossDefQuery.js
//程序功能：盈余佣金定义查询
//创建日期：2011/8/20
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var arrResult = new Array();
var sqlresourcename = "reinsure.RIProfitLossDefQueryInputSql";

// 查询按钮
function button138() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefQueryInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIProfitName.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RelaType.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, MulDefQueryGrid);

}

// 返回按钮
function button139() {
	var tRow = MulDefQueryGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请您先进行选择!"+"");
		return;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefQueryInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(MulDefQueryGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);

	if (strArray == null) {
		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
		return false;
	}
    
	top.opener.fm.all('RIProfitNo').value = strArray[0][0];
	top.opener.fm.all('RIProfitName').value = strArray[0][1];
	top.opener.fm.all('RIcomCode').value = strArray[0][2];
	top.opener.fm.all('RIcomName').value = strArray[0][6];
	top.opener.fm.all('ContNo').value = strArray[0][3];
	top.opener.fm.all('ContName').value = strArray[0][7];
	top.opener.fm.all('RelaType').value = strArray[0][4];
	top.opener.fm.all('RelaTypeName').value = strArray[0][8];
	top.opener.fm.all('RIProfitDes').value = strArray[0][5];

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIProfitLossDefQueryInputSql3");// 指定使用的Sql的id
	mySql1.addSubPara(MulDefQueryGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql1.getString();
	strArray = easyExecSql(strSQL);

	top.opener.Mul9Grid.clearData();

	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			top.opener.Mul9Grid.addOne("Mul9Grid");
			top.opener.Mul9Grid.setRowColData(k, 1, strArray[k][0]);
			top.opener.Mul9Grid.setRowColData(k, 2, strArray[k][1]);
		}
	}
	top.close();
}
// 关闭页面
function button140() {
	top.close();
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
		showDiv(operateButton, "true");
		showDiv(inputButton, "false");
		// 执行下一步操作
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
