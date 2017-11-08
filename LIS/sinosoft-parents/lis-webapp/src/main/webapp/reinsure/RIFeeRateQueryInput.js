var sqlresourcename = "reinsure.RIFeeRateQueryInputSql";
var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass(); // 使用翻页功能，必须建立为全局变量

// 提交，保存按钮对应操作
function submitForm() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateQueryInputSql1");// 指定使用的Sql的id
	mySql.addSubPara(fm.FeeTableNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.FeeTableName.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, FeeRateGrid);
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

// 取消按钮对应操作
function cancelForm() {
	showDiv(operateButton, "true");
	showDiv(inputButton, "false");
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加操作
}

// Click事件，当点击增加图片时触发该函数
function addClick() {
	// 下面增加相应的代码
	showDiv(operateButton, "false");
	showDiv(inputButton, "true");
}

// Click事件，当点击“修改”图片时触发该函数
function updateClick() {
	// 下面增加相应的代码
	myAlert("update click");
}

// Click事件，当点击“查询”图片时触发该函数
function queryClick() {
	// 下面增加相应的代码
	myAlert("query click");
	// 查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
	// 因此，表单中的活动名称也可以不用赋值的
}

// Click事件，当点击“删除”图片时触发该函数
function deleteClick() {
	// 下面增加相应的代码
	myAlert("delete click");
}

// 显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}

// 显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug) {
	if (cDebug == "1") {
		parent.fraMain.rows = "0,0,0,0,*";
	} else {
		parent.fraMain.rows = "0,0,0,82,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData() {
	var tRow = FeeRateGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请您先进行选择!"+"");
		return;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateQueryInputSql2");// 指定使用的Sql的id
	mySql.addSubPara(FeeRateGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
		return false;
	}
	top.opener.fm.all('FeeTableNo').value = strArray[0][0];
	top.opener.fm.all('FeeTableName').value = strArray[0][1];
	top.opener.fm.all('FeeTableType').value = strArray[0][2];
	top.opener.fm.all('FeeTableTypeName').value = strArray[0][3];
	//IE11-TextArea
	top.opener.fm.ReMark.value = strArray[0][4];
	top.opener.fm.all('State').value = strArray[0][5];
	top.opener.fm.all('stateName').value = strArray[0][6];
	top.opener.fm.all('UseType').value = strArray[0][7];
	top.opener.fm.all('UseTypeName').value = strArray[0][8];
	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIFeeRateQueryInputSql3");// 指定使用的Sql的id
	mySql1.addSubPara(FeeRateGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql1.getString();
	strArray = easyExecSql(strSQL);
	top.opener.TableClumnDefGrid.clearData();
	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			top.opener.TableClumnDefGrid.addOne("TableClumnDefGrid");
			top.opener.TableClumnDefGrid.setRowColData(k, 1, strArray[k][0]);
			top.opener.TableClumnDefGrid.setRowColData(k, 2, strArray[k][1]);
			top.opener.TableClumnDefGrid.setRowColData(k, 3, strArray[k][2]);
			top.opener.TableClumnDefGrid.setRowColData(k, 4, strArray[k][3]);
			top.opener.TableClumnDefGrid.setRowColData(k, 5, strArray[k][4]);
			top.opener.TableClumnDefGrid.setRowColData(k, 7, strArray[k][5]);
			top.opener.TableClumnDefGrid.setRowColData(k, 9, strArray[k][6]);
		}
	}
	top.close();
}

function ClosePage() {
	top.close();
}
