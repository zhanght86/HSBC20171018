var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
window.onfocus = myonfocus;
var sqlresourcename = "reinsure.RISchemaInputSql";
var reg = /\D/;
var MAXLIMIT = 999999999.00; // ����޶�
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
		myAlert(""+"����ѡ���ٱ�������"+"");
		return false;
	}
	var accumulateNo = PreceptSearchGrid.getRowColData(tSel - 1, 4);
	window.open("./FrameAccRiskQuery.jsp?AccumulateNo=" + accumulateNo);
}

// �����㷨��Ϣ��ѯ
function receptAthInfo() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0 || tSel == null) {
		myAlert(""+"����ѡ���ٱ�������"+"");
		return false;
	}

	window.open("./RIAthSchemaQueryInput.jsp");
}

// �ύ�����水ť��Ӧ����
function schemaSubmit() {
	if (verifyInput() == true) {
		try {
			if (fm.all('ririskfeature').value == "01") {
				if (fm.YFType.value == "") {
					myAlert(""+"���������ٱ���������Ϊ�գ�"+"");
					return;
				}
				fm.OperateType.value = "SCHFEEDIV";
			} else {
				if (fm.CompanyNum.value == "") {
					myAlert(""+"�ֱ���˾������Ϊ���֣�"+"");
					return;
				}
				if (fm.DivisionNum.value == "") {
					myAlert(""+"�����������Ϊ���֣�"+"");
					return;
				}
				fm.OperateType.value = "SCHMINSERT";
			}
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RISchemaInputSql1");// ָ��ʹ�õ�Sql��id
			mySql.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
			var sql = mySql.getString();

			var rs = easyExecSql(sql);
			if (rs != null) {
				myAlert(""+"�ٱ���������Ѵ��ڣ����������룡"+"");
				return;
			}
			if (fm.PreceptState.value == "01") {// ���״̬��Ч��ҪУ���Ƿ��������ۼƷ����㷨

				var mySql1 = new SqlClass();
				mySql1.setResourceName(sqlresourcename);
				mySql1.setSqlId("RISchemaInputSql2");// ָ��ʹ�õ�Sql��id
				mySql1.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
				var tsql = mySql1.getString();

				var result = easyExecSql(tsql);
				if (result == null) {
					myAlert(""+"û�������ٷ��㷨�򷽰�δ¼����ɣ�������Ϊ��Ч״̬!"+"");
					return false;
				}
			}
			if (verifyInputA1()) {
				if (veriryInput1()) {
					var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
					var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
							+showStr;// encodeURI(encodeURI(showStr));
					showInfo = window
							.showModelessDialog(urlStr, window,
									"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
					fm.action = "./RISchemaSave.jsp";
					fm.submit(); // �ύ
				}
			}
			if (fm.all('AttachFalg').value == "01") {
				var mySql2 = new SqlClass();
				mySql2.setResourceName(sqlresourcename);
				mySql2.setSqlId("RISchemaInputSql3");// ָ��ʹ�õ�Sql��id
				mySql2.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
				var strSQL4 = mySql2.getString();

				turnPage3.queryModal(strSQL4, FeeRateGrid);
			}
		} catch (ex) {
			showInfo.close();
			myAlert(ex);
		}
	}
}
// ��������� ,�ֱ�����������
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
						myAlert(""+"��ֵ������0��"+"" + MAXLIMIT + ""+"֮�������"+"");
						return;
					}
					// if(ContCessGrid.getRowColData(m,4)=="����޶�")
					// {
					// sum=sum+1;
					// }
					if (ContCessGrid.getRowColData(m, 3) == "MaxLimit") {
						if (m != ContCessGrid.mulLineCount - 1) {
							myAlert(""+"MaxLimit���������һ�㣡"+"");
							return;
						}
						if (ContCessGrid.getRowColData(m, 5) != "05") {
							myAlert(""+"����޶���MaxLimit�����Ӧ��"+"");
							return;
						}
						con = con + 1;
					}
					if (ContCessGrid.getRowColData(m, 5) == "05") {
						if (m != ContCessGrid.mulLineCount - 1) {
							myAlert(""+"����޶���������һ�㣡"+"");
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
					myAlert(""+"ֻ����һ������޶�"+"");
					return;
				}
				if (con > 1) {
					myAlert(""+"ֻ����һ��MaxLimit"+"");
					return;
				}
				for ( var m = 0; m < ScaleLineGrid.mulLineCount; m++) {
					if (ScaleLineGrid.getRowColData(m, 4) == "02") {
						len = len + 1;
					}
				}
				if (len != 1) {
					myAlert(""+"������һ��������ֳ�"+"");
					return;
				}
				if (!confirm(""+"�޸���������úͷֱ���˾�������ݣ���Ҫ���½��зֱ��������á�ȷ����?"+"")) {
					return false;
				}
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RISchemaSave.jsp";
				fm.submit(); // �ύ
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
/**
 * ���÷ֱ���������
 */
function scaleSubmit() {
	fm.OperateType.value = "SCALEINSERT";
	try {
		if (verifyInput() && veriryInput4()) {
			if (veriryInput4()) {

				var mySql = new SqlClass();
				mySql.setResourceName(sqlresourcename);
				mySql.setSqlId("RISchemaInputSql4");// ָ��ʹ�õ�Sql��id
				mySql.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
				var tSql = mySql.getString();

				var result = easyExecSql(tSql);
				if (result != null) {
					if (!confirm(""+"ȷ��Ҫ�޸ķֱ�������?"+"")) {
						return false;
					}
				}
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RISchemaSave.jsp";
				fm.submit(); // �ύ
			}
		}
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RISchemaInputSql5");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.all('RIPreceptNo').value);// ָ������Ĳ������������˳�����
		var strSQL4 = mySql1.getString();

		turnPage3.queryModal(strSQL4, FeeRateGrid);
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
/**
 * �ֱ�Ҫ�ر���
 */
function factorSubmit() {
	fm.OperateType.value = "FACTORINSERT";
	try {
		// if(verifyInputA1()==true && verifyInputA2()==true
		// && verifyInput()==true && veriryInput4()==true
		// && verifyInputA3()==true ){
		// if (veriryInput7()==true){
		var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action = "./RISchemaSave.jsp";
		fm.submit(); // �ύ
		// }
		// }
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}

/**
 * ���÷ֱ�����Ӷ����
 */
function feerateSubmit() {
	fm.OperateType.value = "FEERATEINSERT";
	try {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RISchemaInputSql6");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
		var rssql = mySql.getString();

		var result = easyExecSql(rssql);
		if (result == 0) {
			myAlert(""+"�����޸Ļ򱣴��ٱ������Ļ�����Ϣ��"+"");
			return;
		}
		if (!FeeVeriryInput()) {

			return;
		}
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RISchemaInputSql7");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
		var tSql = mySql1.getString();
		var result = easyExecSql(tSql);
		if (result != null) {
			myAlert(""+"�Ѿ������˷ֱ��������ַ��ʱ������ٶ������Ӷ���"+"");
			return false;
		}
		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RISchemaInputSql8");// ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
		var tSql = mySql2.getString();
		result = easyExecSql(tSql);
		if (result == null || result == '') {
			if (!confirm(""+"�ٱ�������û�����÷����㷨"+","+"���÷��ʱ�������ӷ��ʱ��㷨���Ƿ����ã�"+"")) {
				return false;
			}
		}
		if (verifyInput()) {
			var i = 0;
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RISchemaSave.jsp";
			fm.submit(); // �ύ
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
					myAlert(""+"ͬһ����˾"+"" + FeeRateGrid.getRowColData(i, 1)
							+ ""+"�ֱ����ʱ��ű�����ͬ��"+"");
					bool = false;
					return bool;
				}
				if (FeeRateGrid.getRowColData(i, 8) != FeeRateGrid
						.getRowColData(j, 8)) {
					myAlert(""+"ͬһ����˾"+"" + FeeRateGrid.getRowColData(i, 1)
							+ ""+"�ֱ�Ӷ���ʱ��ű�����ͬ��"+"");
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
// ��������� ,�ֱ����������� У��
function veriryInput3() {
	if (!ContCessGrid.checkValue("ContCessGrid")) {
		return false;
	} // ���ContCessGridû�����ݣ��򷵻ؼ�
	if (!ScaleLineGrid.checkValue("ScaleLineGrid")) {
		return false;
	}
	return true;
}

function veriryInput4() {
	var rowNum = CessScaleGrid.mulLineCount;
	var colNum = CessScaleGrid.colCount;
	if (rowNum == 0) {
		myAlert(""+"�ֱ����������б�Ϊ��"+"");
		return false;
	}
	for ( var i = 0; i < rowNum; i++) {

		var amount = 0.0;
		for ( var j = 2; j < colNum; j++) {
			var amt = CessScaleGrid.getRowColData(i, j);
			if (isNaN(amt)) {
				myAlert("" + (CessScaleGrid.getRowColData(i, 1)) + "    "+"����������!"+"");
				return;
			}
			if (parseFloat(amt) < 0) {
				myAlert(""+"�ֱ������������0"+"");
				return false;
			}
			amount = amount + parseFloat(amt);
		}
		if (amount != 1) {
			myAlert("" + (CessScaleGrid.getRowColData(i, 1)) + "    "+"�ֱ������ܺͱ������1"+"");
			return false;
		}
	}
	if (ContCessGrid.getRowColData(0, 5) == "01") {
		for ( var i = 0; i < ScaleLineGrid.mulLineCount; i++) {
			if (ScaleLineGrid.getRowColData(i, 4) == "02") {
				if (CessScaleGrid.getRowColData(rowNum - 1, i + 2) != "1") {
					myAlert(""+"�������Ƿֳ���˾�Լ��е��Ĳ��֣�100"+"%"+"����"+","+"��������Ϊ1"+"");
					return;
				}
			}
		}
	}
	return true;
}
// DeleteУ��
function veriryInput5() {
	return true;
}

// UpdateУ��
function veriryInput6() {
	return true;
}
// Ҫ��У��
function veriryInput7() {
	// У�鲻��¼���ظ���ͬҪ��
	for ( var n = 0; n < FactorGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < FactorGrid.mulLineCount; m++) {
			if (FactorGrid.getRowColData(n, 1) == FactorGrid
					.getRowColData(m, 1)) {
				myAlert(""+"����¼���ظ�Ҫ��!"+"");
				return false;
			}
		}
	}
	for ( var n = 0; n < FactorGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < FactorGrid.mulLineCount; m++) {
			if (FactorGrid.getRowColData(n, 2) == FactorGrid
					.getRowColData(m, 2)) {
				myAlert(""+"����¼���ظ�Ҫ��!"+"");
				return false;
			}
		}
	}
	isNull = false; // ����Ƿ�¼��Ҫ��ֵ���
	for ( var i = 0; i < FactorGrid.mulLineCount; i++) {
		if (FactorGrid.getRowColData(i, 3) == null
				|| FactorGrid.getRowColData(i, 3) == "") {
			isNull = true;
		}
	}
	if (isNull) {
		myAlert(""+"����¼��Ҫ��ֵ!"+"");
		return false;
	}
	return true;
}

function queryClick() {
	fm.OperateType.value = "QUERY";

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RISchemaInputSql9");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, PreceptSearchGrid);
	// var strSQL2 = "select RIPreceptName from RIPrecept where RIPreceptNo =
	// a.RIMAINPRECEPTN";
}

// ��ʾ�ٱ�����
function showPrecept() {
	var tSel = PreceptSearchGrid.getSelNo();
	// �ٱ��������ͣ�01-��ͬ�ֱ���02-��ʱ�ֱ�
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
	// ����
	fm.all('BonusType').value = PreceptSearchGrid.getRowColData(tSel - 1, 29);
	fm.all('BonusName').value = PreceptSearchGrid.getRowColData(tSel - 1, 30);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RISchemaInputSql10");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
	var strSQLName = mySql.getString();

	var rs = easyExecSql(strSQLName);
	fm.YFName.value = rs;
	fm.PreceptStateName.value = PreceptSearchGrid.getRowColData(tSel - 1, 16);

	if (PreceptSearchGrid.getRowColData(tSel - 1, 10) == "02") {
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RISchemaInputSql11");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
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
	// ��������ʱ�趨�ڱ����������շ�����
	if (fm.all('AttachFalg').value == "01") {
		riPreceptNo = fm.all('YFType').value;
	} else {
		riPreceptNo = fm.all('RIPreceptNo').value;
	}
	var mySql11 = new SqlClass();
	mySql11.setResourceName(sqlresourcename);
	mySql11.setSqlId("RISchemaInputSql12");// ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(riPreceptNo);// ָ������Ĳ������������˳�����
	mySql11.addSubPara(MAXLIMIT);// ָ������Ĳ������������˳�����
	var strSQL1 = mySql11.getString();

	var mySql22 = new SqlClass();
	mySql22.setResourceName(sqlresourcename);
	mySql22.setSqlId("RISchemaInputSql13");// ָ��ʹ�õ�Sql��id
	mySql22.addSubPara(riPreceptNo);// ָ������Ĳ������������˳�����
	var strSQL2 = mySql22.getString();

	// �ж��������շ����Ƿ��趨����Ӷ��
	var mySql44 = new SqlClass();
	mySql44.setResourceName(sqlresourcename);
	mySql44.setSqlId("RISchemaInputSql14");// ָ��ʹ�õ�Sql��id
	mySql44.addSubPara(fm.all('RIPreceptNo').value);// ָ������Ĳ������������˳�����
	var strSQL4 = mySql44.getString();

	var strResult222 = easyExecSql(strSQL4);
	if (strResult222 == null) {
		var mySql33 = new SqlClass();
		mySql33.setResourceName(sqlresourcename);
		mySql33.setSqlId("RISchemaInputSql15");// ָ��ʹ�õ�Sql��id
		mySql33.addSubPara(riPreceptNo);// ָ������Ĳ������������˳�����
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
	// ��ʾ�ֱ���������
	addCessScale(riPreceptNo, "SHOW");
	// ��ʾ�ֱ�Ҫ��
	addFactor(riPreceptNo);
	var riRiskFeature = PreceptSearchGrid.getRowColData(tSel - 1, 23);
	// �����Ƿ����������趨ҳ����ʾ��
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
	// �ж��������������յķ����Ƿ�һ��
	// charge();
	// �趨�ֱ���ͬ��
	riPreceptNo = fm.all('RIPreceptNo').value;
	// �趨�ڱ���������д
	fm.RIPreceptNo.readOnly = "true";
	initFactorGrid();
	var mySql55 = new SqlClass();
	mySql55.setResourceName(sqlresourcename);
	mySql55.setSqlId("RISchemaInputSql16");// ָ��ʹ�õ�Sql��id
	mySql55.addSubPara(PreceptSearchGrid.getRowColData(tSel - 1, 2));// ָ������Ĳ������������˳�����
	mySql55.addSubPara(PreceptSearchGrid.getRowColData(tSel - 1, 1));// ָ������Ĳ������������˳�����
	var sqlFi = mySql55.getString();

	turnPage4.queryModal(sqlFi, FactorGrid);
}

// �ж��������������յķ����Ƿ�һ��
function charge() {
	if (fm.all('AttachFalg').value == "01") {
		var mySql11 = new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId("RISchemaInputSql17");// ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(fm.all('RIPreceptNo').value);// ָ������Ĳ������������˳�����
		var strs = mySql11.getString();

		var mySql12 = new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId("RISchemaInputSql18");// ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
		var strs2 = mySql12.getString();

		var rs = easyExecSql(strs);
		var rs2 = easyExecSql(strs2);
		if (rs2 == null) {
			myAlert(""+"������Ϣ"+"'" + fm.all('YFType').value + "'"+"���޸ģ��������趨�ٱ�������"+"");
			FeeRateGrid.clearData();
			fm.YFType.value = "";
			fm.YFName.value = "";
			return;
		}
		if (rs[0][0] != rs2[0][0]) {
			myAlert(""+"������Ϣ"+"'" + fm.all('YFType').value + "'"+"���޸ģ��������趨�ٱ�������"+"");
			FeeRateGrid.clearData();
			fm.YFType.value = "";
			fm.YFName.value = "";
			fm.remark.value = rs2[0][0];
		}

	}
}
/**
 * �����ٷֱ���
 */
function RelaTempPol() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0) {
		myAlert(""+"��ѡ���ٱ�����"+"");
		return false;
	}
	var state = PreceptSearchGrid.getRowColData(tSel - 1, 17);
	var PreceptType = PreceptSearchGrid.getRowColData(tSel - 1, 10);

	if (state == '02' && PreceptType == '02') {
		myAlert(""+"�÷���δ��Ч"+","+"���ܹ�������!"+"");
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
 * ���������ٷֱ���
 */
function RelaGrpTempPol() {
	var tSel = PreceptSearchGrid.getSelNo();
	if (tSel == 0) {
		myAlert(""+"��ѡ���ٱ�����"+"");
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
	mySql33.setSqlId("RISchemaInputSql19");// ָ��ʹ�õ�Sql��id
	mySql33.addSubPara(riPreceptNo);// ָ������Ĳ������������˳�����
	var strSQL = mySql33.getString();

	turnPage3.queryModal(strSQL, FeeRateGrid);
}

// ¼���·���
function inputPrecept() {
	fm.all('RIPreceptNo').value = '';
	fm.all('RIPreceptName').value = '';
	fm.all('AccumulateDefNO').value = ''; //
	fm.all('AccumulateDefName').value = ''; //
	fm.all('DivisionNum').value = ''; // �����
	fm.all('CompanyNum').value = ''; // �ֱ���˾��

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
	fm.PreceptStateName.value = ""+"δ��Ч"+"";

	yfCode.style.display = 'none';
	yfName.style.display = 'none';
	fm.RelaTempPolBut.style.display = 'none';
	fm.RelaGrpTempPolBut.style.display = 'none';
	fm.ArithmeticID.style.display = '';
	fm.ArithmeticName.style.display = '';

	editForm(); // ��ʾ������ť
	ScaleLineGrid.clearData(); // �ֱ����������� ���ֵ���
	ContCessGrid.clearData(); // ��������� ���ֵ���
	CessScaleGrid.clearData(); // �ֱ��������� ���ֵ���
	FactorGrid.clearData(); // ��ͬ�ֱ���Ϣ���ֵ���
	FeeRateGrid.clearData();
	addCessScale("0");
	// �趨�ڱ�������ſ�д
	fm.RIPreceptNo.readOnly = "";
	divNormalRN.style.display = "";
	sfyf.style.display = "";
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
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
			addScale(result); // ���������,�ֱ�����������
		}
		if (fm.OperateType.value == "DIVCOMINSERT") {
			fm.RIPreceptNo.value = result;
			addCessScale(result, "SHOW"); // �ֱ���������
			addFeeRate();
		}
		if (fm.OperateType.value == "SCALEINSERT") {
			fm.RIPreceptNo.value = result;
			addCessScale(result, "SHOW"); // �ֱ���������
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
// �����������ֱ����������� mulLine addOne
function addScale(result) {
	ContCessGrid.clearData();
	ScaleLineGrid.clearData();
	var mySql11 = new SqlClass();
	mySql11.setResourceName(sqlresourcename);
	mySql11.setSqlId("RISchemaInputSql20");// ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(result);// ָ������Ĳ������������˳�����
	var strSQL = mySql11.getString();
	var strArray = easyExecSql(strSQL);
	var divNum = parseInt(strArray[0][0]);
	var companyNum = parseInt(strArray[0][1]);
	for ( var i = 0; i < divNum; i++) {
		ContCessGrid.addOne();
		ContCessGrid.setRowColData(i, 1, ""+"��"+"" + (i + 1) + ""+"��"+"");
	}
	for ( var i = 0; i < companyNum; i++) {

		ScaleLineGrid.addOne();
	}

	addCessScale(result);
}

/**
 * �ֱ��������� mulLine addOne result:RIPreceptNo flag: �Ƿ�������ʾ
 */
function addCessScale(result, flag) {
	// ����
	var mySql21 = new SqlClass();
	mySql21.setResourceName(sqlresourcename);
	mySql21.setSqlId("RISchemaInputSql21");// ָ��ʹ�õ�Sql��id
	mySql21.addSubPara(result);// ָ������Ĳ������������˳�����
	mySql21.addSubPara(MAXLIMIT);// ָ������Ĳ������������˳�����
	var strSQL = mySql21.getString();

	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		x = 0;
	} else {
		x = strArray.length;
	}
	// ����line
	line = new Array(x);
	if (strArray != null) {
		for ( var i = 0; i < strArray.length; i++) {
			line[i] = strArray[i];
		}
	}
	var mySql22 = new SqlClass();
	mySql22.setResourceName(sqlresourcename);
	mySql22.setSqlId("RISchemaInputSql22");// ָ��ʹ�õ�Sql��id
	mySql22.addSubPara(result);// ָ������Ĳ������������˳�����
	var strSQL = mySql22.getString();

	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		y = 0;
	} else {
		y = strArray.length;
	}
	// ����com
	com = new Array(y);
	if (strArray != null) {
		for ( var i = 0; i < strArray.length; i++) {
			com[i] = strArray[i];
		}
	}
	CessScaleGrid.clearData();
	initCessScaleGrid();

	for ( var i = 0; i < line.length; i++) { // ���������
		var level = line.length - i;
		CessScaleGrid.addOne("CessScaleGrid");
		CessScaleGrid.setRowColData(i, 1, ""+"��"+"" + level + ""+"��:"+" " + line[level - 1]
				+ "");

	}
	fm.Line.value = x; // ��������
	fm.Com.value = y; // ��������
	if (flag == "SHOW") {
		var mySql23 = new SqlClass();
		mySql23.setResourceName(sqlresourcename);
		mySql23.setSqlId("RISchemaInputSql23");// ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(result);// ָ������Ĳ������������˳�����
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
	mySql24.setSqlId("RISchemaInputSql24");// ָ��ʹ�õ�Sql��id
	mySql24.addSubPara(result);// ָ������Ĳ������������˳�����
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
		mySql38.setSqlId("RISchemaInputSql38");// ָ��ʹ�õ�Sql��id
		mySql38.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
		var sql = mySql38.getString();
		var result = easyExecSql(sql);
		if (result != 0) {
			myAlert(""+"�÷���������"+","+"���ܱ��޸�!"+"");
			return false;
		}

		var mySql25 = new SqlClass();
		mySql25.setResourceName(sqlresourcename);
		mySql25.setSqlId("RISchemaInputSql25");// ָ��ʹ�õ�Sql��id
		mySql25.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
		var sql = mySql25.getString();

		var result = easyExecSql(sql);
		if (result == null) {
			myAlert(""+"�ٱ�������Ų����ڣ����ȱ���!"+"");
			return false;
		}
		FactorGrid.clearData();
		if (fm.PreceptState.value == "01") {// ���״̬��Ч��ҪУ���Ƿ��������ۼƷ����㷨
			var mySql26 = new SqlClass();
			mySql26.setResourceName(sqlresourcename);
			mySql26.setSqlId("RISchemaInputSql26");// ָ��ʹ�õ�Sql��id
			mySql26.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
			var tsql = mySql26.getString();

			var result = easyExecSql(tsql);
			if (result == null) {
				myAlert(""+"û�������ٱ������㷨��������Ϊ��Ч״̬!"+"");
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
			if (!confirm(""+"���Ѿ������������ֱ���˾���������޸�"+","+"�������������������ߺͷֱ������ߣ�ȷ��Ҫ�޸ĸ÷�����"+"")) {
				return false;
			}
		} else {
			if (!confirm(""+"��ȷ��Ҫ�޸ĸ÷�����"+"")) {
				return false;
			}
		}
		try {
			if (verifyInputA1() == true) {
				if (veriryInput6() == true) {
					var i = 0;
					var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
					var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
							+showStr;// encodeURI(encodeURI(showStr));
					showInfo = window
							.showModelessDialog(urlStr, window,
									"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
					fm.action = "./RISchemaSave.jsp";
					fm.submit(); // �ύ
				} else {
				}
			}
			if (fm.all('AttachFalg').value == "01") {

				var mySql27 = new SqlClass();
				mySql27.setResourceName(sqlresourcename);
				mySql27.setSqlId("RISchemaInputSql27");// ָ��ʹ�õ�Sql��id
				mySql27.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
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
	if (!confirm(""+"��ȷ��Ҫɾ�����ٱ�������"+"")) {
		return false;
	}
	try {
		if (verifyInputA1() == true) {
			if (veriryInput5() == true) {

				var mySql38 = new SqlClass();
				mySql38.setResourceName(sqlresourcename);
				mySql38.setSqlId("RISchemaInputSql38");// ָ��ʹ�õ�Sql��id
				mySql38.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
				var sql = mySql38.getString();
				var result = easyExecSql(sql);
				if (result != 0) {
					myAlert(""+"�÷���������"+","+"���ܱ�ɾ��!"+"");
					return false;
				}

				var mySql28 = new SqlClass();
				mySql28.setResourceName(sqlresourcename);
				mySql28.setSqlId("RISchemaInputSql28");// ָ��ʹ�õ�Sql��id
				mySql28.addSubPara(fm.RIPreceptNo.value);// ָ������Ĳ������������˳�����
				var sql = mySql28.getString();

				var result = easyExecSql(sql);
				if (result == null) {
					myAlert(""+"���ݿ��в����ڸ��ٱ�����!"+"");
					return false;
				}
				var i = 0;
				var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RISchemaSave.jsp";
				fm.submit(); // �ύ
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

// ���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm() {
	try {
		queryClick();
		inputPrecept();
		// �趨�ڱ�������ſ�д
		fm.RIPreceptNo.readOnly = "";
	} catch (re) {
		myAlert(""+"��CertifySendOutInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
	}
}

// �ύǰ��У�顢����
function beforeSubmit() {
	// ��Ӳ���
}

function ClosePage() {
	top.close();
}

function verifyInputA1() {
	if ((fm.RIPreceptNo.value == "") || (fm.RIPreceptNo.value == null)) {
		myAlert(""+"�ۼƷ������"+" "+"����Ϊ��"+"");
		return false;
	}
	if ((fm.AccumulateDefNO.value == "") || (fm.AccumulateDefNO.value == null)) {
		myAlert(""+"�ۼƷ������"+" "+"����Ϊ��"+"");
		return false;
	}
	if ((fm.DivisionNum.value == "") || (fm.DivisionNum.value == null)) {
		myAlert(""+"�������"+" "+"����Ϊ��"+"");
		return false;
	}
	if (fm.CompanyNum.value == "" || fm.CompanyNum.value == null) {
		myAlert(""+"�ֱ���˾��"+" "+"����Ϊ��"+"");
		return false;
	}
	if (parseInt(fm.CompanyNum.value) < 2) {
		myAlert(""+"�ֱ���˾���������1��"+"");
		return false;
	}
	if (fm.PreceptType.value == '02') {

	}
	return true;
}

function verifyInputA2() {
	// ��������� mulline
	var rowNum = ContCessGrid.mulLineCount; // ����
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (ContCessGrid.getRowColData(i, 1) == ''
				|| ContCessGrid.getRowColData(i, 1) == null) {
			myAlert(""+"���������"+","+"��"+"" + num + ""+"��"+","+"�㼶"+" "+"����Ϊ�գ�"+"");
			return false;
		}

		if (ContCessGrid.getRowColData(i, 3) == ''
				|| ContCessGrid.getRowColData(i, 3) == null) {
			myAlert(""+"���������"+","+"��"+"" + num + ""+"��"+","+"��ֵ"+" "+"����Ϊ�գ�"+"");
			return false;
		}

		if (ContCessGrid.getRowColData(i, 4) == ''
				|| ContCessGrid.getRowColData(i, 4) == null) {
			myAlert(""+"���������"+","+"��"+"" + num + ""+"��"+","+"���������"+" "+"����Ϊ�գ�"+"");
			return false;
		}
		if (ContCessGrid.getRowColData(i, 8) == ''
				|| ContCessGrid.getRowColData(i, 5) == null) {
			myAlert(""+"���������"+","+"��"+"" + num + ""+"��"+","+"���ֱ���"+" "+"����Ϊ�գ�"+"");
			return false;
		}
		if (ContCessGrid.getRowColData(i, 3).match(reg) != null
				&& ContCessGrid.getRowColData(i, 3) != "MaxLimit") {
			myAlert(""+"���������"+","+"��"+"" + num + ""+"��"+","+"��������ֵ��MaxLimit��"+"");
			return false;
		}
	}
	// �����û����ͬ�� ��ֵ
	for ( var n = 0; n < ContCessGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < ContCessGrid.mulLineCount; m++) {
			var tData1 = ContCessGrid.getRowColData(n, 3);
			var tData2 = ContCessGrid.getRowColData(m, 3);
			if (tData1 != "MaxLimit" && tData1 == tData2) {
				myAlert(""+"���������"+" "+"��"+"" + (n + 1) + ""+"�����ֵ���ܵ��ڵ�"+"" + (m + 1) + ""+"�����ֵ!"+"");
				return false;
			}
			if (tData1 != "MaxLimit" && parseFloat(tData1) > parseFloat(tData2)) {
				myAlert(""+"���������"+" "+"��"+"" + (n + 1) + ""+"�����ֵ���ܴ��ڵ�"+"" + (m + 1) + ""+"�����ֵ"+" !");
				return false;
			}
		}
	}
	// �ֱ���˾����
	var rowNum = ScaleLineGrid.mulLineCount; // ����
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (ScaleLineGrid.getRowColData(i, 1) == 0
				|| ScaleLineGrid.getRowColData(i, 1) == null) {
			myAlert(""+"�ֱ���˾����"+","+"��"+"" + num + ""+"��"+","+"��˾����"+" "+"����Ϊ�գ�"+"");
			return false;
		}
		if (ScaleLineGrid.getRowColData(i, 2) == 0
				|| ScaleLineGrid.getRowColData(i, 2) == null) {
			myAlert(""+"�ֱ���˾����"+","+"��"+"" + num + ""+"��"+","+"��˾����"+" "+"����Ϊ�գ�"+"");
			return false;
		}
		if (ScaleLineGrid.getRowColData(i, 3) == 0
				|| ScaleLineGrid.getRowColData(i, 3) == null) {
			myAlert(""+"�ֱ���˾����"+","+"��"+"" + num + ""+"��"+","+"�ֱ���˾����"+" "+"����Ϊ�գ�"+"");
			return false;
		}
	}
	// ��������ͬ���ٱ���˾
	for ( var n = 0; n < ScaleLineGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < ScaleLineGrid.mulLineCount; m++) {
			if (ScaleLineGrid.getRowColData(n, 1) == ScaleLineGrid
					.getRowColData(m, 1)) {
				myAlert(""+"�ֱ���˾����"+" "+"��ͬ"+" "+"��"+" "+"����¼����ͬ��"+" "+"�ٱ���˾"+" !");
				return false;
			}
		}
	}
	return true;
}

function verifyInputA3() {
	// �������ֱ���Ϣ
	var rowNum = FactorGrid.mulLineCount;
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (FactorGrid.getRowColData(i, 1) == ''
				|| FactorGrid.getRowColData(i, 1) == null) {
			myAlert(""+"�������ֱ���Ϣ"+","+"��"+"" + num + ""+"��"+","+"Ҫ������"+" "+"����Ϊ�գ�"+"");
			return false;
		}
		if (FactorGrid.getRowColData(i, 2) == ''
				|| FactorGrid.getRowColData(i, 2) == null) {
			myAlert(""+"�������ֱ���Ϣ"+","+"��"+"" + num + ""+"��"+","+"Ҫ�ش���"+" "+"����Ϊ�գ�"+"");
			return false;
		}
		if (FactorGrid.getRowColData(i, 3) == ''
				|| FactorGrid.getRowColData(i, 3) == null) {
			myAlert(""+"�������ֱ���Ϣ"+","+"��"+"" + num + ""+"��"+","+"Ҫ��ֵ"+" "+"����Ϊ�գ�"+"");
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
			myAlert(""+"�ֱ���������"+","+"��"+"" + num + ""+"��"+","+"�㼶"+" "+"����Ϊ�գ�"+"");
			return false;
		}
	}
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		for ( var j = 1; j <= com.length; j++) {
			if (CessScaleGrid.getRowColData(i, j + 1) == null) {
				myAlert(","+"��"+"" + num + ""+"��"+","+"��"+"" + (j + 1) + ""+"��"+" "+"����Ϊ�գ�"+"");
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
		mySql29.setSqlId("RISchemaInputSql29");// ָ��ʹ�õ�Sql��id
		mySql29.addSubPara(tRIContNo);// ָ������Ĳ������������˳�����
		var tSql = mySql29.getString();

		var result = easyExecSql(tSql, 1, 0, 1);

		if (result[0][0] != "0") {
			myAlert(""+"�ٱ���ͬδ��Ч����������ѡ����Ч״̬"+"");
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
		mySql30.setSqlId("RISchemaInputSql30");// ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
		mySql30.addSubPara(MAXLIMIT);// ָ������Ĳ������������˳�����
		var strSQL1 = mySql30.getString();

		var mySql31 = new SqlClass();
		mySql31.setResourceName(sqlresourcename);
		mySql31.setSqlId("RISchemaInputSql31");// ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
		var strSQL2 = mySql31.getString();

		var mySql32 = new SqlClass();
		mySql32.setResourceName(sqlresourcename);
		mySql32.setSqlId("RISchemaInputSql32");// ָ��ʹ�õ�Sql��id
		mySql32.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
		var strSQL3 = mySql32.getString();

		turnPage1.queryModal(strSQL1, ContCessGrid);
		// ScaleLineGrid.clearData();
		turnPage2.queryModal(strSQL2, ScaleLineGrid);
		turnPage3.queryModal(strSQL3, FeeRateGrid);
		// ��ʾ�ֱ���������
		addCessScale(fm.all('YFType').value, "SHOW");
		// ��ʾ�ֱ�Ҫ��
		addFactor(fm.all('YFType').value);
		var mySql33 = new SqlClass();
		mySql33.setResourceName(sqlresourcename);
		mySql33.setSqlId("RISchemaInputSql33");// ָ��ʹ�õ�Sql��id
		mySql33.addSubPara(fm.YFType.value);// ָ������Ĳ������������˳�����
		var tSQL = mySql33.getString();

		var arrResult = easyExecSql(tSQL);
		fm.CompanyNum.value = arrResult[0][0];
		// fm.HierarchyNumTypeName.value=arrResult[0][2];
		fm.DivisionNum.value = arrResult[0][1];

		var mySql34 = new SqlClass();
		mySql34.setResourceName(sqlresourcename);
		mySql34.setSqlId("RISchemaInputSql34");// ָ��ʹ�õ�Sql��id
		mySql34.addSubPara(fm.all('YFType').value);// ָ������Ĳ������������˳�����
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
		mySql35.setSqlId("RISchemaInputSql35");// ָ��ʹ�õ�Sql��id
		mySql35.addSubPara(fm.ArithmeticID.value);// ָ������Ĳ������������˳�����
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
		if (Field.value == ""+"����޶�"+"") {
			ContCessGrid.setRowColData(ContCessGrid.mulLineCount - 1, 3,
					"MaxLimit");
		}
		if (fm.DivisionNum.value == "1" && Field.value == ""+"������"+"") {
			myAlert(""+"�������1ʱ����ѡ��������"+"");
			Field.value = "";
			return;
		}
		if (Field.value == ""+"������"+"") {
			var row = ContCessGrid.lastFocusRowNo;
			if (row > 0) {
				myAlert(""+"������ӦΪ��һ��"+"");
				Field.value = "";
				return;
			}
		}
	} else if (codeName == "reinsuresubtype") {
		// if(Field.value == '06')
		// {
		// fm.HierarchyNumType.value = "03";
		// fm.HierarchyNumTypeName.value = "�⸶��";
		// }
		// else if(Field.value == '04')
		// {
		// fm.HierarchyNumType.value = "02";
		// fm.HierarchyNumTypeName.value = "�⸶��";
		// }
		// else if(Field.value == '05')
		// {
		// fm.HierarchyNumType.value = "02";
		// fm.HierarchyNumTypeName.value = "�⸶��";
		// }else
		// {
		// fm.HierarchyNumType.value = "01";
		// fm.HierarchyNumTypeName.value = "���ձ���";
		// }
	}
	if (codeName == "ririskfeature") {
		if (Field.value == "01") {
			yfCode.style.display = "";
			yfName.style.display = "";
			fm.YFName.value = "";
			fm.YFType.verify = ""+"���շ���"+"|NOTNULL";
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
		mySql36.setSqlId("RISchemaInputSql36");// ָ��ʹ�õ�Sql��id
		mySql36.addSubPara(Field.value);// ָ������Ĳ������������˳�����
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
		mySql37.setSqlId("RISchemaInputSql37");// ָ��ʹ�õ�Sql��id
		mySql37.addSubPara(Field.value);// ָ������Ĳ������������˳�����
		var mSQL = mySql37.getString();

		var result = easyExecSql(mSQL);
		if (result[0][0] == "01") {
			// ����˾
			ScaleLineGrid.setRowColData(row, 3, ""+"������ֳ�"+"");
			ScaleLineGrid.setRowColData(row, 4, "02");

		}
		if (result[0][0] == "02") {
			// �Ǳ���˾
			ScaleLineGrid.setRowColData(row, 3, ""+"����ֳ�"+"");
			ScaleLineGrid.setRowColData(row, 4, "01");
		}
	}
}

// ���
function browseForm() {
	fm.edit.style.display = '';
	fm.browse.style.display = 'none';
	divEdit1.style.display = 'none';
	divEdit2.style.display = 'none';
	divEdit3.style.display = 'none';
	divEdit4.style.display = 'none';
	divEdit5.style.display = 'none';
}
// �༭
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
	// if(fm.HierarchyNumTypeName.value='���ձ���'){
	// fm.HierarchyNumType.value='���ձ���';
	// }
	// if(fm.HierarchyNumTypeName.value='�⸶��'){
	// fm.HierarchyNumType.value='�⸶��';
	// }
	// if(fm.HierarchyNumTypeName.value='03'){
	// fm.HierarchyNumType.value='�⸶��';
	// }
}
