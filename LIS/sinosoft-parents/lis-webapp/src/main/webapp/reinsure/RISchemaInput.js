var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
window.onfocus = myonfocus;
var sqlresourcename = "reinsure.RISchemaInputSql";
var reg = /\D/;
var MAXLIMIT = 999999999.00; // 最大限额
function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

function accuRiskInfo() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0 || tSel == null) {
		myAlert(""+"请先选择再保方案！"+"");
		return false;
	}
	var accumulateNo = PreceptSearchGrid.getRowColData(tSel - 1, 4);
	window.open("./FrameAccRiskQuery.jsp?AccumulateNo=" + accumulateNo);
}

// 方案算法信息查询
function receptAthInfo() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0 || tSel == null) {
		myAlert(""+"请先选择再保方案！"+"");
		return false;
	}

	window.open("./RIAthSchemaQueryInput.jsp");
}

// 提交，保存按钮对应操作
function schemaSubmit() {
	if (verifyInput() == true) {
		try {
			if (fm.all('ririskfeature').value == "01") {
				if (fm.YFType.value == "") {
					myAlert(""+"依附主险再保方案不能为空！"+"");
					return;
				}
				fm.OperateType.value = "SCHFEEDIV";
			} else {
				if (fm.CompanyNum.value == "") {
					myAlert(""+"分保公司数必须为数字！"+"");
					return;
				}
				if (fm.DivisionNum.value == "") {
					myAlert(""+"溢额层次数必须为数字！"+"");
					return;
				}
				fm.OperateType.value = "SCHMINSERT";
			}
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RISchemaInputSql1");// 指定使用的Sql的id
			mySql.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
			var sql = mySql.getString();

			var rs = easyExecSql(sql);
			if (rs != null) {
				myAlert(""+"再保方案编号已存在，请重新输入！"+"");
				return;
			}
			if (fm.PreceptState.value == "01") {// 如果状态有效，要校验是否配置了累计方案算法

				var mySql1 = new SqlClass();
				mySql1.setResourceName(sqlresourcename);
				mySql1.setSqlId("RISchemaInputSql2");// 指定使用的Sql的id
				mySql1.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
				var tsql = mySql1.getString();

				var result = easyExecSql(tsql);
				if (result == null) {
					myAlert(""+"没有配置临分算法或方案未录入完成，不能置为有效状态!"+"");
					return false;
				}
			}
			if (verifyInputA1()) {
				if (veriryInput1()) {
					var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
					var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
							+showStr;// encodeURI(encodeURI(showStr));
					showInfo = window
							.showModelessDialog(urlStr, window,
									"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
					fm.action = "./RISchemaSave.jsp";
					fm.submit(); // 提交
				}
			}
			if (fm.all('AttachFalg').value == "01") {
				var mySql2 = new SqlClass();
				mySql2.setResourceName(sqlresourcename);
				mySql2.setSqlId("RISchemaInputSql3");// 指定使用的Sql的id
				mySql2.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
				var strSQL4 = mySql2.getString();

				turnPage3.queryModal(strSQL4, FeeRateGrid);
			}
		} catch (ex) {
			showInfo.close();
			myAlert(ex);
		}
	}
}
// 溢额线设置 ,分保比例线设置
function divComSubmit() {
	fm.OperateType.value = "DIVCOMINSERT";
	try {
		if (verifyInputA1() && verifyInputA2()) {
			if (veriryInput3()) {
				var len = 0;
				var sum = 0;
				var con = 0;
				for ( var m = 0; m < ContCessGrid.mulLineCount; m++) {
					if (ContCessGrid.getRowColData(m, 3) != "MaxLimit"
							&& (parseFloat(ContCessGrid.getRowColData(m, 3)) > MAXLIMIT || parseFloat(ContCessGrid
									.getRowColData(m, 3)) < 0)) {
						myAlert(""+"数值必须是0到"+"" + MAXLIMIT + ""+"之间的数字"+"");
						return;
					}
					// if(ContCessGrid.getRowColData(m,4)=="最大限额")
					// {
					// sum=sum+1;
					// }
					if (ContCessGrid.getRowColData(m, 3) == "MaxLimit") {
						if (m != ContCessGrid.mulLineCount - 1) {
							myAlert(""+"MaxLimit必须在最后一层！"+"");
							return;
						}
						if (ContCessGrid.getRowColData(m, 5) != "05") {
							myAlert(""+"最大限额与MaxLimit必须对应！"+"");
							return;
						}
						con = con + 1;
					}
					if (ContCessGrid.getRowColData(m, 5) == "05") {
						if (m != ContCessGrid.mulLineCount - 1) {
							myAlert(""+"最大限额必须在最后一层！"+"");
							return;
						}
						sum = sum + 1;
					}
					// if(ContCessGrid.getRowColData(m,3)=="MaxLimit")
					// {
					// con=con+1;
					// }
				}
				if (sum > 1) {
					myAlert(""+"只能有一个最大限额"+"");
					return;
				}
				if (con > 1) {
					myAlert(""+"只能有一个MaxLimit"+"");
					return;
				}
				for ( var m = 0; m < ScaleLineGrid.mulLineCount; m++) {
					if (ScaleLineGrid.getRowColData(m, 4) == "02") {
						len = len + 1;
					}
				}
				if (len != 1) {
					myAlert(""+"必须有一个不计算分出"+"");
					return;
				}
				if (!confirm(""+"修改溢额线设置和分保公司设置数据，需要重新进行分保比例设置。确定吗?"+"")) {
					return false;
				}
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RISchemaSave.jsp";
				fm.submit(); // 提交
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
/**
 * 设置分保比例保存
 */
function scaleSubmit() {
	fm.OperateType.value = "SCALEINSERT";
	try {
		if (verifyInput() && veriryInput4()) {
			if (veriryInput4()) {

				var mySql = new SqlClass();
				mySql.setResourceName(sqlresourcename);
				mySql.setSqlId("RISchemaInputSql4");// 指定使用的Sql的id
				mySql.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
				var tSql = mySql.getString();

				var result = easyExecSql(tSql);
				if (result != null) {
					if (!confirm(""+"确定要修改分保比例吗?"+"")) {
						return false;
					}
				}
				var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RISchemaSave.jsp";
				fm.submit(); // 提交
			}
		}
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RISchemaInputSql5");// 指定使用的Sql的id
		mySql1.addSubPara(fm.all('RIPreceptNo').value);// 指定传入的参数，多个参数顺序添加
		var strSQL4 = mySql1.getString();

		turnPage3.queryModal(strSQL4, FeeRateGrid);
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
/**
 * 分保要素保存
 */
function factorSubmit() {
	fm.OperateType.value = "FACTORINSERT";
	try {
		// if(verifyInputA1()==true && verifyInputA2()==true
		// && verifyInput()==true && veriryInput4()==true
		// && verifyInputA3()==true ){
		// if (veriryInput7()==true){
		var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action = "./RISchemaSave.jsp";
		fm.submit(); // 提交
		// }
		// }
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}

/**
 * 设置分保费率佣金率
 */
function feerateSubmit() {
	fm.OperateType.value = "FEERATEINSERT";
	try {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RISchemaInputSql6");// 指定使用的Sql的id
		mySql.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
		var rssql = mySql.getString();

		var result = easyExecSql(rssql);
		if (result == 0) {
			myAlert(""+"请先修改或保存再保方案的基本信息！"+"");
			return;
		}
		if (!FeeVeriryInput()) {

			return;
		}
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RISchemaInputSql7");// 指定使用的Sql的id
		mySql1.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
		var tSql = mySql1.getString();
		var result = easyExecSql(tSql);
		if (result != null) {
			myAlert(""+"已经定义了分保方案险种费率表，不能再定义费率佣金表！"+"");
			return false;
		}
		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RISchemaInputSql8");// 指定使用的Sql的id
		mySql2.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
		var tSql = mySql2.getString();
		result = easyExecSql(tSql);
		if (result == null || result == '') {
			if (!confirm(""+"再保方案还没有配置方案算法"+","+"配置费率表将不能添加费率表算法。是否配置？"+"")) {
				return false;
			}
		}
		if (verifyInput()) {
			var i = 0;
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RISchemaSave.jsp";
			fm.submit(); // 提交
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
function FeeVeriryInput() {
	var bool = true;
	for ( var i = 0; i < FeeRateGrid.mulLineCount; i++) {
		for ( var j = i + 1; j < FeeRateGrid.mulLineCount; j++) {
			if (FeeRateGrid.getRowColData(i, 1) == FeeRateGrid.getRowColData(j,
					1)) {
				if (FeeRateGrid.getRowColData(i, 6) != FeeRateGrid
						.getRowColData(j, 6)) {
					myAlert(""+"同一个公司"+"" + FeeRateGrid.getRowColData(i, 1)
							+ ""+"分保费率表编号必须相同！"+"");
					bool = false;
					return bool;
				}
				if (FeeRateGrid.getRowColData(i, 8) != FeeRateGrid
						.getRowColData(j, 8)) {
					myAlert(""+"同一个公司"+"" + FeeRateGrid.getRowColData(i, 1)
							+ ""+"分保佣金率表编号必须相同！"+"");
					bool = false;
					return bool;
				}
			}
		}
	}
	return bool;
}
function veriryInput1() {
	return true;
}
// 溢额线设置 ,分保比例线设置 校验
function veriryInput3() {
	if (!ContCessGrid.checkValue("ContCessGrid")) {
		return false;
	} // 如果ContCessGrid没有数据，则返回假
	if (!ScaleLineGrid.checkValue("ScaleLineGrid")) {
		return false;
	}
	return true;
}

function veriryInput4() {
	var rowNum = CessScaleGrid.mulLineCount;
	var colNum = CessScaleGrid.colCount;
	if (rowNum == 0) {
		myAlert(""+"分保比例设置列表为空"+"");
		return false;
	}
	for ( var i = 0; i < rowNum; i++) {

		var amount = 0.0;
		for ( var j = 2; j < colNum; j++) {
			var amt = CessScaleGrid.getRowColData(i, j);
			if (isNaN(amt)) {
				myAlert("" + (CessScaleGrid.getRowColData(i, 1)) + "    "+"请输入数字!"+"");
				return;
			}
			if (parseFloat(amt) < 0) {
				myAlert(""+"分保比例必须大于0"+"");
				return false;
			}
			amount = amount + parseFloat(amt);
		}
		if (amount != 1) {
			myAlert("" + (CessScaleGrid.getRowColData(i, 1)) + "    "+"分保比例总和必须等于1"+"");
			return false;
		}
	}
	if (ContCessGrid.getRowColData(0, 5) == "01") {
		for ( var i = 0; i < ScaleLineGrid.mulLineCount; i++) {
			if (ScaleLineGrid.getRowColData(i, 4) == "02") {
				if (CessScaleGrid.getRowColData(rowNum - 1, i + 2) != "1") {
					myAlert(""+"自留额是分出公司自己承担的部分，100"+"%"+"自留"+","+"必须设置为1"+"");
					return;
				}
			}
		}
	}
	return true;
}
// Delete校验
function veriryInput5() {
	return true;
}

// Update校验
function veriryInput6() {
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

function queryClick() {
	fm.OperateType.value = "QUERY";

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RISchemaInputSql9");// 指定使用的Sql的id
	mySql.addSubPara(fm.RIContNo.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, PreceptSearchGrid);
	// var strSQL2 = "select RIPreceptName from RIPrecept where RIPreceptNo =
	// a.RIMAINPRECEPTN";
}

// 显示再保方案
function showPrecept() {
	var tSel = PreceptSearchGrid.getSelNo();
	// 再保方案类型，01-合同分保，02-临时分保
	var preceptType = PreceptSearchGrid.getRowColData(tSel - 1, 10);

	if (preceptType == '02') {
		fm.RelaTempPolBut.style.display = '';
		fm.RelaGrpTempPolBut.style.display = 'none';
		fm.ArithmeticID.style.display = '';
		fm.ArithmeticName.style.display = '';
	} else {
		fm.RelaTempPolBut.style.display = 'none';
		fm.RelaGrpTempPolBut.style.display = 'none';
		fm.ArithmeticID.style.display = '';
		fm.ArithmeticName.style.display = '';

		fm.CalFeeType.value = '';
		fm.CalFeeTypeName.value = '';
		fm.CalFeeTerm.value = '';
		fm.CalFeeTermName.value = '';
		fm.ArithmeticID.value = '';
		fm.ArithmeticName.value = '';
	}

	fm.all('RIContNo').value = PreceptSearchGrid.getRowColData(tSel - 1, 1);
	fm.all('RIPreceptNo').value = PreceptSearchGrid.getRowColData(tSel - 1, 2);
	fm.all('RIPreceptName').value = PreceptSearchGrid
			.getRowColData(tSel - 1, 3);
	fm.all('AccumulateDefNO').value = PreceptSearchGrid.getRowColData(tSel - 1,
			4);
	fm.all('AccumulateDefName').value = PreceptSearchGrid.getRowColData(
			tSel - 1, 5);
	fm.all('CompanyNum').value = PreceptSearchGrid.getRowColData(tSel - 1, 6);
	fm.all('DivisionNum').value = PreceptSearchGrid.getRowColData(tSel - 1, 7);
	fm.all('ArithmeticID').value = PreceptSearchGrid.getRowColData(tSel - 1, 8);
	fm.all('ArithmeticName').value = PreceptSearchGrid.getRowColData(tSel - 1,
			9);
	fm.all('PreceptType').value = PreceptSearchGrid.getRowColData(tSel - 1, 10);
	fm.all('PreceptTypeName').value = PreceptSearchGrid.getRowColData(tSel - 1,
			11);

	fm.all('CompanyNumBackup').value = PreceptSearchGrid.getRowColData(
			tSel - 1, 6);
	fm.all('DivisionNumBackup').value = PreceptSearchGrid.getRowColData(
			tSel - 1, 7);
	fm.all('PreceptState').value = PreceptSearchGrid
			.getRowColData(tSel - 1, 17);
	fm.all('AttachFalg').value = PreceptSearchGrid.getRowColData(tSel - 1, 24);
	fm.all('ArithmeticID').value = PreceptSearchGrid
			.getRowColData(tSel - 1, 28);
	fm.all('RIRiskFeatureName').value = PreceptSearchGrid.getRowColData(
			tSel - 1, 25);

	fm.all('YFType').value = PreceptSearchGrid.getRowColData(tSel - 1, 26);
	// 红利
	fm.all('BonusType').value = PreceptSearchGrid.getRowColData(tSel - 1, 29);
	fm.all('BonusName').value = PreceptSearchGrid.getRowColData(tSel - 1, 30);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RISchemaInputSql10");// 指定使用的Sql的id
	mySql.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
	var strSQLName = mySql.getString();

	var rs = easyExecSql(strSQLName);
	fm.YFName.value = rs;
	fm.PreceptStateName.value = PreceptSearchGrid.getRowColData(tSel - 1, 16);

	if (PreceptSearchGrid.getRowColData(tSel - 1, 10) == "02") {
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RISchemaInputSql11");// 指定使用的Sql的id
		mySql1.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql1.getString();
		var strArray = easyExecSql(strSQL);
		if (strArray != null && strArray != '') {
			fm.all('CalFeeTerm').value = strArray[0][0];
			fm.all('CalFeeTermName').value = strArray[0][1];
			fm.all('CalFeeType').value = strArray[0][2];
			fm.all('CalFeeTypeName').value = strArray[0][3];
		}
	}
	var riPreceptNo;
	// 依附主险时设定在保方案号主险方案号
	if (fm.all('AttachFalg').value == "01") {
		riPreceptNo = fm.all('YFType').value;
	} else {
		riPreceptNo = fm.all('RIPreceptNo').value;
	}
	var mySql11 = new SqlClass();
	mySql11.setResourceName(sqlresourcename);
	mySql11.setSqlId("RISchemaInputSql12");// 指定使用的Sql的id
	mySql11.addSubPara(riPreceptNo);// 指定传入的参数，多个参数顺序添加
	mySql11.addSubPara(MAXLIMIT);// 指定传入的参数，多个参数顺序添加
	var strSQL1 = mySql11.getString();

	var mySql22 = new SqlClass();
	mySql22.setResourceName(sqlresourcename);
	mySql22.setSqlId("RISchemaInputSql13");// 指定使用的Sql的id
	mySql22.addSubPara(riPreceptNo);// 指定传入的参数，多个参数顺序添加
	var strSQL2 = mySql22.getString();

	// 判断依附主险方案是否设定费率佣金
	var mySql44 = new SqlClass();
	mySql44.setResourceName(sqlresourcename);
	mySql44.setSqlId("RISchemaInputSql14");// 指定使用的Sql的id
	mySql44.addSubPara(fm.all('RIPreceptNo').value);// 指定传入的参数，多个参数顺序添加
	var strSQL4 = mySql44.getString();

	var strResult222 = easyExecSql(strSQL4);
	if (strResult222 == null) {
		var mySql33 = new SqlClass();
		mySql33.setResourceName(sqlresourcename);
		mySql33.setSqlId("RISchemaInputSql15");// 指定使用的Sql的id
		mySql33.addSubPara(riPreceptNo);// 指定传入的参数，多个参数顺序添加
		var strSQL3 = mySql33.getString();

	} else {
		strSQL3 = strSQL4;
	}
	var strResult1 = easyExecSql(strSQL1);
	var strResult2 = easyExecSql(strSQL2);

	if (strResult1 == null || strResult2 == null) {
		ContCessGrid.clearData();
		ScaleLineGrid.clearData();
		addScale(riPreceptNo);
		FeeRateGrid.clearData();
	} else {
		ContCessGrid.clearData();
		turnPage1.queryModal(strSQL1, ContCessGrid);
		ScaleLineGrid.clearData();
		turnPage2.queryModal(strSQL2, ScaleLineGrid);
		turnPage3.queryModal(strSQL3, FeeRateGrid);
	}
	// 显示分保比例设置
	addCessScale(riPreceptNo, "SHOW");
	// 显示分保要素
	addFactor(riPreceptNo);
	var riRiskFeature = PreceptSearchGrid.getRowColData(tSel - 1, 23);
	// 根据是否依附主险设定页面显示项
	if (fm.AttachFalg.value == "01") {
		yfCode.style.display = "";
		yfName.style.display = "";
		divNormalRN.style.display = "none";
		sfyf.style.display = "none";

	} else {
		yfCode.style.display = "none";
		yfName.style.display = "none";
		divNormalRN.style.display = "";
		sfyf.style.display = "";

	}
	divArithmetic.style.display = "";
	// 判断主险于依附主险的方案是否一致
	// charge();
	// 设定分保合同号
	riPreceptNo = fm.all('RIPreceptNo').value;
	// 设定在保方案不可写
	fm.RIPreceptNo.readOnly = "true";
	initFactorGrid();
	var mySql55 = new SqlClass();
	mySql55.setResourceName(sqlresourcename);
	mySql55.setSqlId("RISchemaInputSql16");// 指定使用的Sql的id
	mySql55.addSubPara(PreceptSearchGrid.getRowColData(tSel - 1, 2));// 指定传入的参数，多个参数顺序添加
	mySql55.addSubPara(PreceptSearchGrid.getRowColData(tSel - 1, 1));// 指定传入的参数，多个参数顺序添加
	var sqlFi = mySql55.getString();

	turnPage4.queryModal(sqlFi, FactorGrid);
}

// 判断主险于依附主险的方案是否一致
function charge() {
	if (fm.all('AttachFalg').value == "01") {
		var mySql11 = new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId("RISchemaInputSql17");// 指定使用的Sql的id
		mySql11.addSubPara(fm.all('RIPreceptNo').value);// 指定传入的参数，多个参数顺序添加
		var strs = mySql11.getString();

		var mySql12 = new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId("RISchemaInputSql18");// 指定使用的Sql的id
		mySql12.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
		var strs2 = mySql12.getString();

		var rs = easyExecSql(strs);
		var rs2 = easyExecSql(strs2);
		if (rs2 == null) {
			myAlert(""+"主险信息"+"'" + fm.all('YFType').value + "'"+"已修改，请重新设定再保方案！"+"");
			FeeRateGrid.clearData();
			fm.YFType.value = "";
			fm.YFName.value = "";
			return;
		}
		if (rs[0][0] != rs2[0][0]) {
			myAlert(""+"主险信息"+"'" + fm.all('YFType').value + "'"+"已修改，请重新设定再保方案！"+"");
			FeeRateGrid.clearData();
			fm.YFType.value = "";
			fm.YFName.value = "";
			fm.remark.value = rs2[0][0];
		}

	}
}
/**
 * 关联临分保单
 */
function RelaTempPol() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0) {
		myAlert(""+"请选择再保方案"+"");
		return false;
	}
	var state = PreceptSearchGrid.getRowColData(tSel - 1, 17);
	var PreceptType = PreceptSearchGrid.getRowColData(tSel - 1, 10);

	if (state == '02' && PreceptType == '02') {
		myAlert(""+"该方案未生效"+","+"不能关联保单!"+"");
		return false;
	}
	var tRIContNo = PreceptSearchGrid.getRowColData(tSel - 1, 1);
	var tRIPreceptNo = PreceptSearchGrid.getRowColData(tSel - 1, 2);
	var tCalFeeTerm = fm.CalFeeTerm.value;
	var tCalFeeType = fm.CalFeeType.value;
	var tOperateNo = fm.OperateNo.value;
	var tCodeType = "01";
	window.open("./FrameRelaTempPol.jsp?RIContNo=" + tRIContNo
			+ "&RIPreceptNo=" + tRIPreceptNo + "&CalFeeType=" + tCalFeeType
			+ "&CalFeeTerm=" + tCalFeeTerm + "&OperateNo=" + tOperateNo
			+ "&CodeType=" + tCodeType + "&SerialNo=" + fm.SerialNo.value,
			"Temp1");
}

/**
 * 关联团险临分保单
 */
function RelaGrpTempPol() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0) {
		myAlert(""+"请选择再保方案"+"");
		return false;
	}
	var tRIContNo = PreceptSearchGrid.getRowColData(tSel - 1, 1);
	var tRIPreceptNo = PreceptSearchGrid.getRowColData(tSel - 1, 2);
	var tCalFeeTerm = fm.CalFeeTerm.value;
	var tCalFeeType = fm.CalFeeType.value;
	var tOperateNo = fm.OperateNo.value;
	var tCodeType = "05";
	window.open("./FrameRelaTempPol.jsp?RIContNo=" + tRIContNo
			+ "&RIPreceptNo=" + tRIPreceptNo + "&CalFeeType=" + tCalFeeType
			+ "&CalFeeTerm=" + tCalFeeTerm + "&OperateNo=" + tOperateNo
			+ "&CodeType=" + tCodeType, "Temp1");
}

function addFeeRate() {
	var riPreceptNo = fm.all('RIPreceptNo').value;
	var mySql33 = new SqlClass();
	mySql33.setResourceName(sqlresourcename);
	mySql33.setSqlId("RISchemaInputSql19");// 指定使用的Sql的id
	mySql33.addSubPara(riPreceptNo);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql33.getString();

	turnPage3.queryModal(strSQL, FeeRateGrid);
}

// 录入新方案
function inputPrecept() {
	fm.all('RIPreceptNo').value = '';
	fm.all('RIPreceptName').value = '';
	fm.all('AccumulateDefNO').value = ''; //
	fm.all('AccumulateDefName').value = ''; //
	fm.all('DivisionNum').value = ''; // 溢额层次
	fm.all('CompanyNum').value = ''; // 分保公司数

	fm.all('PreceptType').value = '';
	fm.all('PreceptTypeName').value = '';
	fm.all('ArithmeticID').value = '';
	fm.all('ArithmeticName').value = '';

	fm.all('CalFeeTerm').value = '';
	fm.all('CalFeeTermName').value = '';
	fm.all('CalFeeType').value = '';
	fm.all('CalFeeTypeName').value = '';
	fm.all('PreceptState').value = '';

	fm.all('AttachFalg').value = '';
	fm.all('RIRiskFeatureName').value = '';
	// fm.all('ReinsuranceType').value = '';
	// fm.all('ReinsuranceTypeName').value = '';
	// fm.all('ReinsuranceSubType').value = '';
	// fm.all('ReinsuranceSubTypeName').value = '';
	// fm.all('HierarchyNumTypeName').value = '';
	fm.all('YFType').value = '';
	fm.all('YFName').value = '';
	fm.all('BonusType').value = '';
	fm.all('BonusName').value = '';
	fm.PreceptState.value = "02";
	fm.PreceptStateName.value = ""+"未生效"+"";

	yfCode.style.display = 'none';
	yfName.style.display = 'none';
	fm.RelaTempPolBut.style.display = 'none';
	fm.RelaGrpTempPolBut.style.display = 'none';
	fm.ArithmeticID.style.display = '';
	fm.ArithmeticName.style.display = '';

	editForm(); // 显示操作按钮
	ScaleLineGrid.clearData(); // 分保比例线设置 里的值清空
	ContCessGrid.clearData(); // 溢额线设置 里的值清空
	CessScaleGrid.clearData(); // 分保比例设置 里的值清空
	FactorGrid.clearData(); // 合同分保信息里的值清空
	FeeRateGrid.clearData();
	addCessScale("0");
	// 设定在保方案编号可写
	fm.RIPreceptNo.readOnly = "";
	divNormalRN.style.display = "";
	sfyf.style.display = "";
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, result, CertifyCode) {
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

		if (fm.OperateType.value == "SCHMINSERT") {
			fm.RIPreceptNo.value = result;
			queryClick();
			addScale(result); // 溢额线设置,分保比例线设置
		}
		if (fm.OperateType.value == "DIVCOMINSERT") {
			fm.RIPreceptNo.value = result;
			addCessScale(result, "SHOW"); // 分保比例设置
			addFeeRate();
		}
		if (fm.OperateType.value == "SCALEINSERT") {
			fm.RIPreceptNo.value = result;
			addCessScale(result, "SHOW"); // 分保比例设置
			addFeeRate();
		}
		if (fm.OperateType.value == "DELETE") {
			resetForm();
		}
		if (fm.OperateType.value == "UPDATE") {
			queryClick();
			if (fm.UpDe.value == "1") {
				addScale(result);
				addFeeRate();
			}
		}
	}
	queryClick();
}
// 溢额线设置与分保比例线设置 mulLine addOne
function addScale(result) {
	ContCessGrid.clearData();
	ScaleLineGrid.clearData();
	var mySql11 = new SqlClass();
	mySql11.setResourceName(sqlresourcename);
	mySql11.setSqlId("RISchemaInputSql20");// 指定使用的Sql的id
	mySql11.addSubPara(result);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql11.getString();
	var strArray = easyExecSql(strSQL);
	var divNum = parseInt(strArray[0][0]);
	var companyNum = parseInt(strArray[0][1]);
	for ( var i = 0; i < divNum; i++) {
		ContCessGrid.addOne();
		ContCessGrid.setRowColData(i, 1, ""+"第"+"" + (i + 1) + ""+"层"+"");
	}
	for ( var i = 0; i < companyNum; i++) {

		ScaleLineGrid.addOne();
	}

	addCessScale(result);
}

/**
 * 分保比例设置 mulLine addOne result:RIPreceptNo flag: 是否用于显示
 */
function addCessScale(result, flag) {
	// 重置
	var mySql21 = new SqlClass();
	mySql21.setResourceName(sqlresourcename);
	mySql21.setSqlId("RISchemaInputSql21");// 指定使用的Sql的id
	mySql21.addSubPara(result);// 指定传入的参数，多个参数顺序添加
	mySql21.addSubPara(MAXLIMIT);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql21.getString();

	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		x = 0;
	} else {
		x = strArray.length;
	}
	// 重置line
	line = new Array(x);
	if (strArray != null) {
		for ( var i = 0; i < strArray.length; i++) {
			line[i] = strArray[i];
		}
	}
	var mySql22 = new SqlClass();
	mySql22.setResourceName(sqlresourcename);
	mySql22.setSqlId("RISchemaInputSql22");// 指定使用的Sql的id
	mySql22.addSubPara(result);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql22.getString();

	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		y = 0;
	} else {
		y = strArray.length;
	}
	// 重置com
	com = new Array(y);
	if (strArray != null) {
		for ( var i = 0; i < strArray.length; i++) {
			com[i] = strArray[i];
		}
	}
	CessScaleGrid.clearData();
	initCessScaleGrid();

	for ( var i = 0; i < line.length; i++) { // 溢额线设置
		var level = line.length - i;
		CessScaleGrid.addOne("CessScaleGrid");
		CessScaleGrid.setRowColData(i, 1, ""+"第"+"" + level + ""+"层:"+" " + line[level - 1]
				+ "");

	}
	fm.Line.value = x; // “行数”
	fm.Com.value = y; // “列数”
	if (flag == "SHOW") {
		var mySql23 = new SqlClass();
		mySql23.setResourceName(sqlresourcename);
		mySql23.setSqlId("RISchemaInputSql23");// 指定使用的Sql的id
		mySql23.addSubPara(result);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql23.getString();

		var strResult = easyExecSql(strSQL);
		var k = 0;
		if (strResult != null && strResult != "null") {
			for ( var i = x - 1; i >= 0; i--) {
				for ( var j = 0; j < y; j++) {
					CessScaleGrid.setRowColData(i, j + 2, strResult[k] + "");

					k++;
				}
			}
		}
	}
}

function addFactor(result) {
	var mySql24 = new SqlClass();
	mySql24.setResourceName(sqlresourcename);
	mySql24.setSqlId("RISchemaInputSql24");// 指定使用的Sql的id
	mySql24.addSubPara(result);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql24.getString();

	strArray = easyExecSql(strSQL);
	FactorGrid.clearData();

	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			FactorGrid.addOne("FactorGrid");
			FactorGrid.setRowColData(k, 1, strArray[k][0]);
			FactorGrid.setRowColData(k, 2, strArray[k][1]);
			FactorGrid.setRowColData(k, 3, strArray[k][2]);
		}
	}
}

function updateClick() {

	if (verifyInput() == true && verifyInputA1() == true) {
		var mySql38 = new SqlClass();
		mySql38.setResourceName(sqlresourcename);
		mySql38.setSqlId("RISchemaInputSql38");// 指定使用的Sql的id
		mySql38.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
		var sql = mySql38.getString();
		var result = easyExecSql(sql);
		if (result != 0) {
			myAlert(""+"该方案被依附"+","+"不能被修改!"+"");
			return false;
		}

		var mySql25 = new SqlClass();
		mySql25.setResourceName(sqlresourcename);
		mySql25.setSqlId("RISchemaInputSql25");// 指定使用的Sql的id
		mySql25.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
		var sql = mySql25.getString();

		var result = easyExecSql(sql);
		if (result == null) {
			myAlert(""+"再保方案编号不存在，请先保存!"+"");
			return false;
		}
		FactorGrid.clearData();
		if (fm.PreceptState.value == "01") {// 如果状态有效，要校验是否配置了累计方案算法
			var mySql26 = new SqlClass();
			mySql26.setResourceName(sqlresourcename);
			mySql26.setSqlId("RISchemaInputSql26");// 指定使用的Sql的id
			mySql26.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
			var tsql = mySql26.getString();

			var result = easyExecSql(tsql);
			if (result == null) {
				myAlert(""+"没有配置再保方案算法，不能置为有效状态!"+"");
				return false;
			}
		}
		fm.UpDe.value = '';
		fm.OperateType.value = "UPDATE";

		if (fm.all('CompanyNumBackup').value != fm.all('CompanyNum').value
				|| fm.all('DivisionNumBackup').value != fm.all('DivisionNum').value) {
			fm.UpDe.value = '1';
		}
		if (fm.UpDe.value == '1') {
			if (!confirm(""+"你已经对溢额层次数或分保公司数进行了修改"+","+"保存后需重新设置溢额线和分保比例线，确定要修改该方案吗？"+"")) {
				return false;
			}
		} else {
			if (!confirm(""+"你确定要修改该方案吗？"+"")) {
				return false;
			}
		}
		try {
			if (verifyInputA1() == true) {
				if (veriryInput6() == true) {
					var i = 0;
					var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
					var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
							+showStr;// encodeURI(encodeURI(showStr));
					showInfo = window
							.showModelessDialog(urlStr, window,
									"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
					fm.action = "./RISchemaSave.jsp";
					fm.submit(); // 提交
				} else {
				}
			}
			if (fm.all('AttachFalg').value == "01") {

				var mySql27 = new SqlClass();
				mySql27.setResourceName(sqlresourcename);
				mySql27.setSqlId("RISchemaInputSql27");// 指定使用的Sql的id
				mySql27.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
				var strSQL4 = mySql27.getString();

				turnPage3.queryModal(strSQL4, FeeRateGrid);
			}
		} catch (ex) {
			showInfo.close();
			myAlert(ex);
		}
	}
}

function deleteClick() {
	fm.OperateType.value = "DELETE";
	if (!confirm(""+"你确定要删除该再保方案吗？"+"")) {
		return false;
	}
	try {
		if (verifyInputA1() == true) {
			if (veriryInput5() == true) {

				var mySql38 = new SqlClass();
				mySql38.setResourceName(sqlresourcename);
				mySql38.setSqlId("RISchemaInputSql38");// 指定使用的Sql的id
				mySql38.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
				var sql = mySql38.getString();
				var result = easyExecSql(sql);
				if (result != 0) {
					myAlert(""+"该方案被依附"+","+"不能被删除!"+"");
					return false;
				}

				var mySql28 = new SqlClass();
				mySql28.setResourceName(sqlresourcename);
				mySql28.setSqlId("RISchemaInputSql28");// 指定使用的Sql的id
				mySql28.addSubPara(fm.RIPreceptNo.value);// 指定传入的参数，多个参数顺序添加
				var sql = mySql28.getString();

				var result = easyExecSql(sql);
				if (result == null) {
					myAlert(""+"数据库中不存在该再保方案!"+"");
					return false;
				}
				var i = 0;
				var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RISchemaSave.jsp";
				fm.submit(); // 提交
			} else {
			}
		}
		divNormalRN.style.display = "";
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function afterQuery() {
}

// 重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm() {
	try {
		queryClick();
		inputPrecept();
		// 设定在保方案编号可写
		fm.RIPreceptNo.readOnly = "";
	} catch (re) {
		myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
	}
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加操作
}

function ClosePage() {
	top.close();
}

function verifyInputA1() {
	if ((fm.RIPreceptNo.value == "") || (fm.RIPreceptNo.value == null)) {
		myAlert(""+"累计方案编号"+" "+"不能为空"+"");
		return false;
	}
	if ((fm.AccumulateDefNO.value == "") || (fm.AccumulateDefNO.value == null)) {
		myAlert(""+"累计方案编号"+" "+"不能为空"+"");
		return false;
	}
	if ((fm.DivisionNum.value == "") || (fm.DivisionNum.value == null)) {
		myAlert(""+"溢额层次数"+" "+"不能为空"+"");
		return false;
	}
	if (fm.CompanyNum.value == "" || fm.CompanyNum.value == null) {
		myAlert(""+"分保公司数"+" "+"不能为空"+"");
		return false;
	}
	if (parseInt(fm.CompanyNum.value) < 2) {
		myAlert(""+"分保公司数必须大于1！"+"");
		return false;
	}
	if (fm.PreceptType.value == '02') {

	}
	return true;
}

function verifyInputA2() {
	// 溢额线设置 mulline
	var rowNum = ContCessGrid.mulLineCount; // 行数
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (ContCessGrid.getRowColData(i, 1) == ''
				|| ContCessGrid.getRowColData(i, 1) == null) {
			myAlert(""+"溢额线设置"+","+"第"+"" + num + ""+"行"+","+"层级"+" "+"不能为空！"+"");
			return false;
		}

		if (ContCessGrid.getRowColData(i, 3) == ''
				|| ContCessGrid.getRowColData(i, 3) == null) {
			myAlert(""+"溢额线设置"+","+"第"+"" + num + ""+"行"+","+"数值"+" "+"不能为空！"+"");
			return false;
		}

		if (ContCessGrid.getRowColData(i, 4) == ''
				|| ContCessGrid.getRowColData(i, 4) == null) {
			myAlert(""+"溢额线设置"+","+"第"+"" + num + ""+"行"+","+"溢额线类型"+" "+"不能为空！"+"");
			return false;
		}
		if (ContCessGrid.getRowColData(i, 8) == ''
				|| ContCessGrid.getRowColData(i, 5) == null) {
			myAlert(""+"溢额线设置"+","+"第"+"" + num + ""+"行"+","+"币种编码"+" "+"不能为空！"+"");
			return false;
		}
		if (ContCessGrid.getRowColData(i, 3).match(reg) != null
				&& ContCessGrid.getRowColData(i, 3) != "MaxLimit") {
			myAlert(""+"溢额线设置"+","+"第"+"" + num + ""+"行"+","+"必须是数值或MaxLimit！"+"");
			return false;
		}
	}
	// 检查有没有相同的 数值
	for ( var n = 0; n < ContCessGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < ContCessGrid.mulLineCount; m++) {
			var tData1 = ContCessGrid.getRowColData(n, 3);
			var tData2 = ContCessGrid.getRowColData(m, 3);
			if (tData1 != "MaxLimit" && tData1 == tData2) {
				myAlert(""+"溢额线设置"+" "+"第"+"" + (n + 1) + ""+"层的数值不能等于第"+"" + (m + 1) + ""+"层的数值!"+"");
				return false;
			}
			if (tData1 != "MaxLimit" && parseFloat(tData1) > parseFloat(tData2)) {
				myAlert(""+"溢额线设置"+" "+"第"+"" + (n + 1) + ""+"层的数值不能大于第"+"" + (m + 1) + ""+"层的数值"+" !");
				return false;
			}
		}
	}
	// 分保公司设置
	var rowNum = ScaleLineGrid.mulLineCount; // 行数
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (ScaleLineGrid.getRowColData(i, 1) == 0
				|| ScaleLineGrid.getRowColData(i, 1) == null) {
			myAlert(""+"分保公司设置"+","+"第"+"" + num + ""+"行"+","+"公司代码"+" "+"不能为空！"+"");
			return false;
		}
		if (ScaleLineGrid.getRowColData(i, 2) == 0
				|| ScaleLineGrid.getRowColData(i, 2) == null) {
			myAlert(""+"分保公司设置"+","+"第"+"" + num + ""+"行"+","+"公司名称"+" "+"不能为空！"+"");
			return false;
		}
		if (ScaleLineGrid.getRowColData(i, 3) == 0
				|| ScaleLineGrid.getRowColData(i, 3) == null) {
			myAlert(""+"分保公司设置"+","+"第"+"" + num + ""+"行"+","+"分保公司类型"+" "+"不能为空！"+"");
			return false;
		}
	}
	// 不能有相同的再保公司
	for ( var n = 0; n < ScaleLineGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < ScaleLineGrid.mulLineCount; m++) {
			if (ScaleLineGrid.getRowColData(n, 1) == ScaleLineGrid
					.getRowColData(m, 1)) {
				myAlert(""+"分保公司设置"+" "+"不同"+" "+"层"+" "+"不能录入相同的"+" "+"再保公司"+" !");
				return false;
			}
		}
	}
	return true;
}

function verifyInputA3() {
	// 方案级分保信息
	var rowNum = FactorGrid.mulLineCount;
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (FactorGrid.getRowColData(i, 1) == ''
				|| FactorGrid.getRowColData(i, 1) == null) {
			myAlert(""+"方案级分保信息"+","+"第"+"" + num + ""+"行"+","+"要素名称"+" "+"不能为空！"+"");
			return false;
		}
		if (FactorGrid.getRowColData(i, 2) == ''
				|| FactorGrid.getRowColData(i, 2) == null) {
			myAlert(""+"方案级分保信息"+","+"第"+"" + num + ""+"行"+","+"要素代码"+" "+"不能为空！"+"");
			return false;
		}
		if (FactorGrid.getRowColData(i, 3) == ''
				|| FactorGrid.getRowColData(i, 3) == null) {
			myAlert(""+"方案级分保信息"+","+"第"+"" + num + ""+"行"+","+"要素值"+" "+"不能为空！"+"");
			return false;
		}
	}
	return true;
}

function verifyInputA4() {
	var rowNum = CessScaleGrid.mulLineCount;
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (CessScaleGrid.getRowColData(i, 1) == null) {
			myAlert(""+"分保比例设置"+","+"第"+"" + num + ""+"行"+","+"层级"+" "+"不能为空！"+"");
			return false;
		}
	}
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		for ( var j = 1; j <= com.length; j++) {
			if (CessScaleGrid.getRowColData(i, j + 1) == null) {
				myAlert(","+"第"+"" + num + ""+"行"+","+"第"+"" + (j + 1) + ""+"列"+" "+"不能为空！"+"");
				return false;
			}
		}
	}
}

function checkPreceptState() {
	var tRIContNo = fm.RIContNo.value;
	var tPreceptState = fm.PreceptState.value;
	if (tPreceptState == "01") {
		var mySql29 = new SqlClass();
		mySql29.setResourceName(sqlresourcename);
		mySql29.setSqlId("RISchemaInputSql29");// 指定使用的Sql的id
		mySql29.addSubPara(tRIContNo);// 指定传入的参数，多个参数顺序添加
		var tSql = mySql29.getString();

		var result = easyExecSql(tSql, 1, 0, 1);

		if (result[0][0] != "0") {
			myAlert(""+"再保合同未生效，方案不能选择生效状态"+"");
			return false;
		}
	}
	return true;

}

function afterCodeSelect(codeName, Field) {
	// jintao 8.25 9:30
	if (codeName == "rimainprecept") {
		var mySql30 = new SqlClass();
		mySql30.setResourceName(sqlresourcename);
		mySql30.setSqlId("RISchemaInputSql30");// 指定使用的Sql的id
		mySql30.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
		mySql30.addSubPara(MAXLIMIT);// 指定传入的参数，多个参数顺序添加
		var strSQL1 = mySql30.getString();

		var mySql31 = new SqlClass();
		mySql31.setResourceName(sqlresourcename);
		mySql31.setSqlId("RISchemaInputSql31");// 指定使用的Sql的id
		mySql31.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
		var strSQL2 = mySql31.getString();

		var mySql32 = new SqlClass();
		mySql32.setResourceName(sqlresourcename);
		mySql32.setSqlId("RISchemaInputSql32");// 指定使用的Sql的id
		mySql32.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
		var strSQL3 = mySql32.getString();

		turnPage1.queryModal(strSQL1, ContCessGrid);
		// ScaleLineGrid.clearData();
		turnPage2.queryModal(strSQL2, ScaleLineGrid);
		turnPage3.queryModal(strSQL3, FeeRateGrid);
		// 显示分保比例设置
		addCessScale(fm.all('YFType').value, "SHOW");
		// 显示分保要素
		addFactor(fm.all('YFType').value);
		var mySql33 = new SqlClass();
		mySql33.setResourceName(sqlresourcename);
		mySql33.setSqlId("RISchemaInputSql33");// 指定使用的Sql的id
		mySql33.addSubPara(fm.YFType.value);// 指定传入的参数，多个参数顺序添加
		var tSQL = mySql33.getString();

		var arrResult = easyExecSql(tSQL);
		fm.CompanyNum.value = arrResult[0][0];
		// fm.HierarchyNumTypeName.value=arrResult[0][2];
		fm.DivisionNum.value = arrResult[0][1];

		var mySql34 = new SqlClass();
		mySql34.setResourceName(sqlresourcename);
		mySql34.setSqlId("RISchemaInputSql34");// 指定使用的Sql的id
		mySql34.addSubPara(fm.all('YFType').value);// 指定传入的参数，多个参数顺序添加
		var strs2 = mySql34.getString();

		var rs2 = easyExecSql(strs2);
		fm.remark.value = rs2[0][0];

	}

	if (codeName == "precepttype") {
		if (Field.value == "02") {
			fm.RelaTempPolBut.style.display = '';
			fm.RelaGrpTempPolBut.style.display = 'none';
			fm.ArithmeticID.style.display = '';
			fm.ArithmeticName.style.display = '';
		} else {
			fm.RelaTempPolBut.style.display = 'none';
			fm.RelaGrpTempPolBut.style.display = 'none';
			fm.ArithmeticID.style.display = '';
			fm.ArithmeticName.style.display = '';
		}
	} else if (codeName == "riarithmetic") {
		var mySql35 = new SqlClass();
		mySql35.setResourceName(sqlresourcename);
		mySql35.setSqlId("RISchemaInputSql35");// 指定使用的Sql的id
		mySql35.addSubPara(fm.ArithmeticID.value);// 指定传入的参数，多个参数顺序添加
		var tSQL = mySql35.getString();

		var arrResult = easyExecSql(tSQL);
		fm.CalFeeTerm.value = arrResult[0][0];
		fm.CalFeeTermName.value = arrResult[0][1];
		fm.CalFeeType.value = arrResult[0][2];
		fm.CalFeeTypeName.value = arrResult[0][3];
	} else if (codeName == "ricontno") {
		fm.all('RIContNo').value = fm.all('RIContNo1').value;
		queryClick();
	} else if (codeName == "ridivtype") {
		if (Field.value == ""+"最大限额"+"") {
			ContCessGrid.setRowColData(ContCessGrid.mulLineCount - 1, 3,
					"MaxLimit");
		}
		if (fm.DivisionNum.value == "1" && Field.value == ""+"自留额"+"") {
			myAlert(""+"溢额层次数1时不能选择自留额"+"");
			Field.value = "";
			return;
		}
		if (Field.value == ""+"自留额"+"") {
			var row = ContCessGrid.lastFocusRowNo;
			if (row > 0) {
				myAlert(""+"自留额应为第一层"+"");
				Field.value = "";
				return;
			}
		}
	} else if (codeName == "reinsuresubtype") {
		// if(Field.value == '06')
		// {
		// fm.HierarchyNumType.value = "03";
		// fm.HierarchyNumTypeName.value = "赔付率";
		// }
		// else if(Field.value == '04')
		// {
		// fm.HierarchyNumType.value = "02";
		// fm.HierarchyNumTypeName.value = "赔付额";
		// }
		// else if(Field.value == '05')
		// {
		// fm.HierarchyNumType.value = "02";
		// fm.HierarchyNumTypeName.value = "赔付额";
		// }else
		// {
		// fm.HierarchyNumType.value = "01";
		// fm.HierarchyNumTypeName.value = "风险保额";
		// }
	}
	if (codeName == "ririskfeature") {
		if (Field.value == "01") {
			yfCode.style.display = "";
			yfName.style.display = "";
			fm.YFName.value = "";
			fm.YFType.verify = ""+"主险方案"+"|NOTNULL";
			fm.YFType.value = "";
			divNormalRN.style.display = "none";
			sfyf.style.display = "none";
		} else {
			yfCode.style.display = "none";
			yfName.style.display = "none";
			divNormalRN.style.display = "";
			fm.YFType.verify = "";
			sfyf.style.display = "";
		}
	}

	if (codeName == "currency") {
		var rowNum = ContCessGrid.mulLineCount;
		var mySql36 = new SqlClass();
		mySql36.setResourceName(sqlresourcename);
		mySql36.setSqlId("RISchemaInputSql36");// 指定使用的Sql的id
		mySql36.addSubPara(Field.value);// 指定传入的参数，多个参数顺序添加
		var sql = mySql36.getString();

		var result = easyExecSql(sql);
		for ( var i = 0; i < rowNum; i++) {
			ContCessGrid.setRowColData(i, 8, Field.value);
			ContCessGrid.setRowColData(i, 9, result[0][0]);
		}
	}
	if (codeName == "ricompanycode") {
		var row = ScaleLineGrid.lastFocusRowNo;

		var mySql37 = new SqlClass();
		mySql37.setResourceName(sqlresourcename);
		mySql37.setSqlId("RISchemaInputSql37");// 指定使用的Sql的id
		mySql37.addSubPara(Field.value);// 指定传入的参数，多个参数顺序添加
		var mSQL = mySql37.getString();

		var result = easyExecSql(mSQL);
		if (result[0][0] == "01") {
			// 本公司
			ScaleLineGrid.setRowColData(row, 3, ""+"不计算分出"+"");
			ScaleLineGrid.setRowColData(row, 4, "02");

		}
		if (result[0][0] == "02") {
			// 非本公司
			ScaleLineGrid.setRowColData(row, 3, ""+"计算分出"+"");
			ScaleLineGrid.setRowColData(row, 4, "01");
		}
	}
}

// 浏览
function browseForm() {
	fm.edit.style.display = '';
	fm.browse.style.display = 'none';
	divEdit1.style.display = 'none';
	divEdit2.style.display = 'none';
	divEdit3.style.display = 'none';
	divEdit4.style.display = 'none';
	divEdit5.style.display = 'none';
}
// 编辑
function editForm() {
	fm.edit.style.display = 'none';
	fm.browse.style.display = '';
	divEdit1.style.display = '';
	divEdit2.style.display = '';
	divEdit3.style.display = '';
	divEdit4.style.display = '';
	divEdit5.style.display = '';
}
function selectHierarch() {
	// alert(fm.HierarchyNumTypeName.value);
	// if(fm.HierarchyNumTypeName.value='风险保额'){
	// fm.HierarchyNumType.value='风险保额';
	// }
	// if(fm.HierarchyNumTypeName.value='赔付额'){
	// fm.HierarchyNumType.value='赔付额';
	// }
	// if(fm.HierarchyNumTypeName.value='03'){
	// fm.HierarchyNumType.value='赔付率';
	// }
}
