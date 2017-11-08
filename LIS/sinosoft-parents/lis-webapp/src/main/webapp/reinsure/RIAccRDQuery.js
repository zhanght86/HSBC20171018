//该文件中包含客户端需要处理的函数和事件

//程序名称：RIAccRDQuery.js
//程序功能：分出责任定义-信息查询
//创建日期：2011/6/16
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var sqlresourcename = "reinsure.RIAccRDQueryInputSql";
var turnPage = new turnPageClass();
window.onfocus = myonfocus;

// 查询按钮
function AccumulateQuery() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDQueryInputSql1");//指定使用的Sql的id
	mySql.addSubPara(fm.AccumulateDefNO.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.AccumulateDefName.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIAccState.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, RIAccumulateGrid);
}

// 返回按钮
function ReturnData() {
	var tRow = RIAccumulateGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请您先进行选择!"+"");
		return;
	}
	
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDQueryInputSql2");//指定使用的Sql的id
	mySql.addSubPara(RIAccumulateGrid.getRowColData(tRow - 1, 1));// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);

	if (strArray == null) {
		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
		return false;
	}
	top.opener.fm.all('AccumulateDefNO').value = strArray[0][0];
	top.opener.fm.all('AccumulateDefName').value = strArray[0][1];
	top.opener.fm.all('ArithmeticDefID').value = strArray[0][3];
	top.opener.fm.all('ArithmeticDefName').value = strArray[0][9];
	// 分出责任类型
	top.opener.fm.all('RIAccType').value = strArray[0][4];
	top.opener.fm.all('RIAccTypeName').value = strArray[0][5];
	top.opener.fm.all('StandbyFlag').value = strArray[0][6];
	// 分出责任状态
	top.opener.fm.all('RIAccState').value = strArray[0][8];
	top.opener.fm.all('RIAccStateName').value = strArray[0][2];
	top.opener.fm.all('moneyKind').value = strArray[0][10];
	top.opener.fm.all('DIntv').value = strArray[0][11];
	top.opener.fm.all('DIntvName').value = strArray[0][13];
	top.opener.fm.all('BFFlagName').value = strArray[0][14];
	top.opener.fm.all('StandbyFlagName').value = strArray[0][15];
	top.opener.fm.all('BFFlag').value = strArray[0][12];
	top.opener.fm.all('CalOrder').value = strArray[0][16];

/*start jintao 8.18*/
	// 分保特性
	//top.opener.fm.all('RIRiskFeature').value = strArray[0][6];
	//top.opener.fm.all('RIRiskFeatureName').value = strArray[0][7];

//	if ("01" == strArray[0][6]) {
//		top.opener.fm.caccspec.style.display = '';
//	} else {
//		top.opener.fm.caccspec.style.display = 'none';
//	}
//	if ("01" == strArray[0][4]) {
//		//top.opener.fm.cacctype.style.display = '';
//		top.opener.divCode1.style.display = "none";
//		top.opener.divName1.style.display = "none";
//	} else {
//		//top.opener.fm.cacctype.style.display = 'none';
//		top.opener.divCode1.style.display = "";
//		top.opener.divName1.style.display = "";
//	}
	/*end jintao 8.18*/
	
//	strArray = easyExecSql(strSQL);
//
//	top.opener.RIAccRiskDutyGrid.clearData();
//
//	if (strArray != null) {
//		for ( var k = 0; k < strArray.length; k++) {
//			top.opener.RIAccRiskDutyGrid.addOne("RIAccRiskDutyGrid");
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 1, strArray[k][0]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 2, strArray[k][1]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 3, strArray[k][2]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 4, strArray[k][3]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 5, strArray[k][4]);
//		}
//	}
//	top.opener.turnPage.queryModal(strSQL, top.opener.RIAccRiskDutyGrid);
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
