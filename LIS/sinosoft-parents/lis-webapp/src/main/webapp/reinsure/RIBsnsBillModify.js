//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIBsnsBillModify.js
//�����ܣ��˵��޸�
//�������ڣ�2011-08-18
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillModifyInputSql";
// ��ѯ�˵���ť
function queryBill() {
	if (!verifyInput()) {
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillModifyInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.BillNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.EndDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, Mul10Grid);
	showDiv(divFeeItem1, "false");
	showDiv(divFeeItem2, "false");
	showDiv(divInfoItem, "false");
	showDiv(divButton, "false");

}

// ��ѯ��ϸ��ť
function queryBillDetail() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"��ѡ���¼��"+"");
		return false;
	}
	var billno = Mul10Grid.getRowColData(tRow - 1, 1);
	var batchno = Mul10Grid.getRowColData(tRow - 1, 8);
	var currency = Mul10Grid.getRowColData(tRow - 1, 9);
	var dctype = Mul10Grid.getRowColData(tRow - 1, 10);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillModifyInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(batchno);// ָ������Ĳ������������˳�����
	mySql.addSubPara(billno);// ָ������Ĳ������������˳�����
	mySql.addSubPara(currency);// ָ������Ĳ������������˳�����
	var strFeeSQL = mySql.getString();

	strArray = easyExecSql(strFeeSQL);
	showDiv(divInfoItem, "true");
	showDiv(divButton, "true");
	if (dctype == "02") {
		showDiv(divFeeItem1, "false");
		showDiv(divFeeItem2, "true");

		Mul12Grid.clearData();
		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				Mul12Grid.addOne("Mul12Grid");
				Mul12Grid.setRowColData(k, 1, strArray[k][0]);
				Mul12Grid.setRowColData(k, 2, strArray[k][1]);
				Mul12Grid.setRowColData(k, 3, strArray[k][3]);
				Mul12Grid.setRowColData(k, 8, strArray[k][4]);
				Mul12Grid.setRowColData(k, 9, strArray[k][5]);
				Mul12Grid.setRowColData(k, 10, strArray[k][6]);
				Mul12Grid.setRowColData(k, (strArray[k][4] == "01")
						^ (strArray[k][2] < 0) ? 6 : 7, Math
						.abs(strArray[k][2]).toString());
				Mul12Grid.setRowColData(k, (strArray[k][4] == "01")
						^ (strArray[k][2] < 0) ? 4 : 5, Math
						.abs(strArray[k][2]).toString());

			}
		}

	} else {
		showDiv(divFeeItem1, "true");
		showDiv(divFeeItem2, "false");

		Mul11Grid.clearData();

		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				Mul11Grid.addOne("Mul11Grid");
				Mul11Grid.setRowColData(k, 1, strArray[k][0]);
				Mul11Grid.setRowColData(k, 2, strArray[k][1]);
				Mul11Grid.setRowColData(k, 3, strArray[k][3]);
				Mul11Grid.setRowColData(k, 4, strArray[k][2]);
				Mul11Grid.setRowColData(k, 5, strArray[k][2]);
				Mul11Grid.setRowColData(k, 6, strArray[k][5]);
				Mul11Grid.setRowColData(k, 7, strArray[k][6]);
			}
		}
	}

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIBsnsBillModifyInputSql3");// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(batchno);// ָ������Ĳ������������˳�����
	mySql1.addSubPara(billno);// ָ������Ĳ������������˳�����
	mySql1.addSubPara(currency);// ָ������Ĳ������������˳�����
	var strInfoSQL = mySql1.getString();

	strArray = easyExecSql(strInfoSQL);
	Mul13Grid.clearData();
	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			Mul13Grid.addOne("Mul13Grid");
			Mul13Grid.setRowColData(k, 1, strArray[k][0]);
			Mul13Grid.setRowColData(k, 2, strArray[k][1]);
			Mul13Grid.setRowColData(k, 3, strArray[k][3]);
			Mul13Grid.setRowColData(k, 4, strArray[k][2]);
			Mul13Grid.setRowColData(k, 5, strArray[k][2]);
			Mul13Grid.setRowColData(k, 6, strArray[k][4]);
			Mul13Grid.setRowColData(k, 7, strArray[k][5]);
		}
	}
}

function veriry() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"��ѡ���¼��"+"");
		return false;
	}
	return true;
}

function veriryModify() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"��ѡ���¼��"+"");
		return false;
	}

	var dctype = Mul10Grid.getRowColData(tRow - 1, 10);
	myAlert(dctype);
	if (dctype == "02") {
		for ( var n = 0; n < Mul12Grid.mulLineCount; n++) {
			if (Mul12Grid.getRowColData(n, 10) == "01"
					&& (Mul12Grid.getRowColData(n, 4) != Mul12Grid
							.getRowColData(n, 6) || Mul12Grid.getRowColData(n,
							5) != Mul12Grid.getRowColData(n, 7))) {
				myAlert(""+"�������"+"" + (n + 1) + ""+"�У�ϵͳ��������޸ģ�"+"");
				Mul12Grid.setRowColData(n, 4, Mul12Grid.getRowColData(n, 6));
				Mul12Grid.setRowColData(n, 5, Mul12Grid.getRowColData(n, 7));
				return false;
			}
			if (Mul12Grid.getRowColData(n, 4) != ""
					&& Mul12Grid.getRowColData(n, 5) != "") {
				myAlert(""+"�������"+"" + (n + 1) + ""+"�У����������ͬʱ���ڣ�"+"");
				return false;
			}
			if (Mul12Grid.getRowColData(n, 4) == ""
					&& Mul12Grid.getRowColData(n, 5) == "") {
				myAlert(""+"�������"+"" + (n + 1) + ""+"�У����������ͬʱΪ�գ�"+"");
				return false;
			}
		}
	} else {
		for ( var n = 0; n < Mul11Grid.mulLineCount; n++) {
			if (Mul11Grid.getRowColData(n, 7) == "01"
					&& Mul11Grid.getRowColData(n, 4) != Mul11Grid
							.getRowColData(n, 5)) {
				myAlert(""+"�������"+"" + (n + 1) + ""+"�У�ϵͳ��������޸ģ�"+"");
				Mul11Grid.setRowColData(n, 4, Mul11Grid.getRowColData(n, 5));
				return false;
			}
		}
	}

	for ( var n = 0; n < Mul13Grid.mulLineCount; n++) {
		if (Mul13Grid.getRowColData(n,7) == "01"
				&& Mul13Grid.getRowColData(n, 4) != Mul13Grid.getRowColData(n,
						5)) {
			myAlert(""+"��Ϣ���"+"" + (n + 1) + ""+"�У�ϵͳ��������޸ģ�"+"");
			Mul13Grid.setRowColData(n, 4, Mul13Grid.getRowColData(n, 5));
			return false;
		}
	}

	return true;
}

function veriryUW() {
	if (!veriry()) {
		return false;
	}
	return true;
}

// �� �İ�ť
function modify() {
	fm.OperateType.value = "MODIFY";
	try {
		if (verifyInput() && veriryModify()) {
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

function reCal() {
	fm.OperateType.value = "RECAL";
	try {
		if (verifyInput() && veriryUW()) {
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

function submitUW() {
	if (!confirm(""+"��ȷ�����Ѿ����浱ǰ�޸����ݣ����ύ������ύ��˺󲻿��޸�"+"")) {
		return false;
	}
	fm.OperateType.value = "SUBMIT";
	try {
		if (verifyInput() && veriryUW()) {
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.submit();
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function addbillno(v, s) {
	var billno = Mul10Grid.getRowColData(Mul10Grid.getSelNo() - 1, 1);
	switch (s) {
	case "1":
		Mul11Grid.setRowColData(Mul11Grid.mulLineCount - 1, 6, billno);
		break;
	case "2":
		Mul12Grid.setRowColData(Mul12Grid.mulLineCount - 1, 9, billno);
		break;
	case "3":
		Mul13Grid.setRowColData(Mul13Grid.mulLineCount - 1, 6, billno);
		break;
	}
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
		// ִ����һ������
		queryBill();
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
