//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIProfitLossCalm.js
//�����ܣ�ӯ��Ӷ�����
//�������ڣ�2011/8/22
//������  ��dukang
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var Mul10GridTurnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIProfitLossCalmInputSql";
function verify() {
	if (fm.CalYear.value == "" || fm.CalYear.value == null) {
		myAlert(""+"��Ȳ���Ϊ��"+"");
		return false;
	}
	if (!isNumeric(fm.CalYear.value)) {
		myAlert(""+"��ȸ�ʽ����"+"");
		return false;
	}
	if (fm.RIProfitNo.value == "" || fm.RIProfitNo.value == null) {
		myAlert(""+"���������Ѳ���Ϊ��"+"");
		return false;
	}

	return true;
}
// ��ʼ��������ť
function button140() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossCalmInputSql6");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var strSql = mySql.getString();
	var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
	if (strQueryResult) {
		myAlert(""+"����Ѿ�ȷ�ϣ��������ٳ�ʼ������"+"");
		return false;
	}

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIProfitLossCalmInputSql1");// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql1.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql1.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql1.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	mySql1.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var Sql = mySql1.getString();
	var strResult = easyQueryVer3(Sql, 1, 1, 1);

	if (strResult) {
		if (!confirm(""+"�����ѱ���ʼ�������һ�Σ�ȷ��Ҫ���³�ʼ����������"+"")) {
			return false;
		}
	}

	fm.OperateType.value = "ININTPARAM";
	try {
		if (verifyInput()) {
			var showStr = ""+"���ڳ�ʼ��..."+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function queryClick() {

	if (verify()) {

		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIProfitLossCalmInputSql3");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
		mySql1.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
		mySql1.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
		mySql1.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
		mySql1.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
		var strSQL = mySql1.getString();
		temp1 = easyExecSql(strSQL);
		if (temp1 == null) {
			myAlert(""+"δ��ɼ��㣬������Ϊ�գ�"+"");
		} else {
			fm.Result.value = temp1;
		}
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIProfitLossCalmInputSql2");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
		var SQL = mySql.getString();
		temp = easyExecSql(SQL);

		Mul10Grid.clearData();
		for ( var i = 0; i < temp.length; i++) {
			Mul10Grid.addOne();
			Mul10Grid.setRowColData(i, 1, temp[i][0]);
			Mul10Grid.setRowColData(i, 2, temp[i][1]);
			Mul10Grid.setRowColData(i, 3, temp[i][2]);
			Mul10Grid.setRowColData(i, 4, temp[i][3]);
			Mul10Grid.setRowColData(i, 5, temp[i][4]);
			Mul10Grid.setRowColData(i, 6, temp[i][5]);
			Mul10Grid.setRowColData(i, 7, temp[i][6]);
			Mul10Grid.setRowColData(i, 8, temp[i][7]);
			Mul10Grid.setRowColData(i, 9, temp[i][8]);
		}

	}

}

// ���㰴ť
function button141() {

	// ������ȷ���򲻿�������
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossCalmInputSql4");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var strSql = mySql.getString();
	var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
	if (strQueryResult) {
		myAlert(""+"�Ѿ����ȷ�ϣ����������㣡��"+"");
		return false;
	}
	var num = Mul10Grid.mulLineCount;
	if (num == '' || num == null || num == 0) {
		myAlert(""+"����δ���г�ʼ����"+"");
		return false;
	}

	for ( var i = 0; i < Mul10Grid.mulLineCount; i++) {
		num = i + 1;
		if (!isTel(Mul10Grid.getRowColData(i, 5))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"����ֵ¼�벻�ԣ�"+"");
			Mul10Grid.setRowColData(i, 5, Mul10Grid.getRowColData(i, 8));
			return false;
		}
	}
	fm.OperateType.value = "CALCULATE";
	try {
		if (verifyInput()) {
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.submit(); // �ύ

		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function isTel(strValue) {
	var NUM = "0123456789.-";
	var i;
	if (strValue == null || strValue == "")
		return true;
	for (i = 0; i < strValue.length; i++) {
		if (NUM.indexOf(strValue.charAt(i)) < 0)
			return false;

	}
	return true;
}

// �ύǰ��У�顢����
function beforeSubmit() {
	// ��Ӳ���
}

// ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
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
		// var strSQL="select decode(a.InOutType,'01','����','֧��'), b.factorcode,
		// b.factorname, decode(a.inputtype,'01','ϵͳ����','�ֹ�¼��'), b.Factorvalue
		// ,decode(a.currency,'01','�����','02','�۱�','03','��Ԫ'), a.currency from
		// RIProLossRela a,RIProLossValue b where
		// b.RIProfitNo='"+fm.RIProfitNo.value+"' and a.factorcode=b.factorcode
		// and a.Currency =b.Currency order by a.InOutType";
		// l10GridTurnPage.queryModal(strSQL, Mul10Grid);

	}

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossCalmInputSql5");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	temp = easyExecSql(strSQL);
	if (fm.OperateType.value == "CALCULATE") {
		fm.Result.value = temp;
	} else if (fm.OperateType.value == "ININTPARAM") {
		fm.Result.value = "";
	}
}
function afterCodeSelect(codeName, Field) {
	if (codeName == "riprofityear") {
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
