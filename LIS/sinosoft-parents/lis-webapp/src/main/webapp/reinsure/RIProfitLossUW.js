//该文件中包含客户端需要处理的函数和事件

//程序名称：RIProfitLossUW.js
//程序功能：盈余佣金审核
//创建日期：2011/8/18
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIProfitLossUWInputSql";
function verifyInput() {
	var tRow = Mul9Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"请您先进行选择记录!"+"");
		return;
	}
	if (fm.RILossUWReport.value == "" || fm.RILossUWReport.value == null) {
		myAlert(""+"保存失败！未下达审核结论"+"");
		return false;
	}

	return true;
}
function verify() {

	if (fm.CalYear.value == "" || fm.CalYear.value == null) {
		myAlert(""+"年度不能为空"+"");
		return false;
	}
	if (!isNumeric(fm.CalYear.value)) {
		myAlert(""+"年度格式错误"+"");
		return false;
	}
	if (fm.RIProfitNo.value == "" || fm.RIProfitNo.value == null) {
		myAlert(""+"纯益手续费不能为空"+"");
		return false;
	}

	return true;
}

function queryClick() {
	if (verify()) {
				
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossUWInputSql1");//指定使用的Sql的id
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	/*mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	*/
	var strSQL = mySql.getString();	
	var arr= easyExecSql( strSQL,1,0,1 ); //!!每次出现easyExeSql()查询都要判断是否为空,例:
	if(arr==null)
	{
		myAlert(""+"没有符合条件的数据"+"");
		return;
	}			
		turnPage.queryModal(strSQL, Mul9Grid);
	}

}

function queryClickDetail() {
	var Row = Mul9Grid.getSelNo();

	var riprofitno = Mul9Grid.getRowColData(Row - 1, 1);
	var currency = Mul9Grid.getRowColData(Row - 1, 8);
			
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossUWInputSql2");//指定使用的Sql的id
	mySql.addSubPara(riprofitno);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(currency);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIProfitNo.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.CalYear.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.RIcomCode.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.ContNo.value);// 指定传入的参数，多个参数顺序添加
	var SQL = mySql.getString();			
	temp = easyExecSql(SQL);

	Mul10Grid.clearData();
	for (var i = 0; i < temp.length; i++) {   
			
	Mul10Grid.addOne();
	Mul10Grid.setRowColData(i, 1, temp[i][0]);
	Mul10Grid.setRowColData(i, 2,temp[i][1]);
	Mul10Grid.setRowColData(i, 3,temp[i][2]);
	Mul10Grid.setRowColData(i, 4, temp[i][3]);
	Mul10Grid.setRowColData(i, 5, temp[i][4]);
	Mul10Grid.setRowColData(i, 6, temp[i][5]);
	Mul10Grid.setRowColData(i, 7, temp[i][6]);
	Mul10Grid.setRowColData(i, 8, temp[i][7]);
	
	}
	
	
}

// 结论保存按钮
function button135() {
	fm.OperateType.value = "UPDATE";
	try {
		if (verifyInput()) {
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIProfitLossUWSave.jsp";
			fm.submit(); // 提交
		}

	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
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

		// var strSQL="select a.riprofitno,a.calyear,a.recomcode,(select
		// c.companyname from RIComInfo c where remark = '02' and c.companyno =
		// a.ReComCode),a.ricontno,(select b.RIContName from RIBarGainInfo b
		// where b.RIContNo = a.RIContNo),a.prolosamnt,
		// a.currency,decode(a.state,'02','修改','03','确认') from RIProLossResult a
		// where a.state = '02' order by a.calyear";

		// Mul9GridTurnPage.queryModal(strSQL, Mul9Grid);
		divDetail.style.display = "none";
		// 执行下一步操作
		Mul9Grid.clearData("Mul9Grid");
		fm.CalYear.value = "";
		fm.RILossUWReport.value = "";
		fm.RILossUWReportName.value = "";
		initForm();
	}
}
function afterCodeSelect(codeName, Field) {
	if (codeName == "riprofityear") {
		Mul9Grid.clearData();
		Mul10Grid.clearData();
	}
}
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
