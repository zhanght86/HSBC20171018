//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIBsnsBillSetm.js
//�����ܣ��˵�����
//�������ڣ�2011/6/17
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillSetmInputSql";

// ��ѯ�˵���ť
function queryBill() {
	if (!verifyInput()) {
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillSetmInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.BillNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.BsnsStateCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.EndDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, Mul10Grid);
	showDiv(divFeeItem1, "false");
	showDiv(divFeeItem2, "false");
	showDiv(divInfoItem, "false");
}

// ��ѯ��ϸ��ť
function queryBillDetail() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"��ѡ���¼��"+"");
		return false;
	}
	var billno = Mul10Grid.getRowColData(tRow - 1, 1);
	var serialno = Mul10Grid.getRowColData(tRow - 1, 8);
	var currency = Mul10Grid.getRowColData(tRow - 1, 9);
	var dctype = Mul10Grid.getRowColData(tRow - 1, 10);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillSetmInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(serialno);// ָ������Ĳ������������˳�����
	mySql.addSubPara(billno);// ָ������Ĳ������������˳�����
	mySql.addSubPara(currency);// ָ������Ĳ������������˳�����
	var strFeeSQL = mySql.getString();
	strArray = easyExecSql(strFeeSQL);
	showDiv(divInfoItem, "true");
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
				Mul12Grid.setRowColData(k, 6, strArray[k][5]);
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
				Mul11Grid.setRowColData(k, 5, strArray[k][5]);
			}
		}
	}

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIBsnsBillSetmInputSql3");// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(serialno);// ָ������Ĳ������������˳�����
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
			Mul13Grid.setRowColData(k, 4, strArray[k][2]);
			Mul13Grid.setRowColData(k, 3, strArray[k][3]);
			Mul13Grid.setRowColData(k, 5, strArray[k][4]);
		}
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
