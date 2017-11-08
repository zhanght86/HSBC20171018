//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIBussRate.js
//�����ܣ�����ά��
//�������ڣ�2011-6-27
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var sqlresourcename = "reinsure.RIBussRateInputSql";
var turnPage = new turnPageClass();

function veriryInsert() {
	if (fm.EndDate.value != "" && fm.StartDate.value > fm.EndDate.value) {
		myAlert(""+"��Ч���ڲ��ܴ���ʧЧ���ڣ�"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBussRateInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var tSQL = mySql.getString();
	var strArray = easyExecSql(tSQL);
	if (strArray != null) {
		myAlert(""+"��Ч���ڱ�����ڵ�ǰ��Ч��������Ļ���"+"");
		return false;
	}
	return true;

}

function veriryDelete() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBussRateInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.Currency.value);// ָ������Ĳ������������˳�����
	var tSQL = mySql.getString();
	var strArray = easyExecSql(tSQL);
	if (strArray != null) {
		myAlert(""+"ֻ��ɾ����Ч�����������"+"");
		return false;
	}
	return true;
}

// �� �水ť
function button131() {
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput()) {
			if (veriryInsert()) {
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.submit(); // �ύ
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

// �� ѯ��ť
function button132() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBussRateInputSql3");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.CurrencyQuery.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.StartDateQuery.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, Mul11Grid);
}

// ɾ����ť
function button133() {
	var tRow = Mul11Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"��ѡ��Ҫɾ���Ļ��ʣ�"+"");
		return false;
	}
	if (!confirm(""+"��ȷ��Ҫɾ���û�����"+"")) {
		return false;
	}
	var sn = Mul11Grid.getRowColData(tRow - 1, 1);
	fm.OperateType.value = "DELETE";
	try {
		if (veriryDelete()) {
			var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "RIBussRateSave.jsp?sn=" + sn;
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function showFormInfo() {
	var tSel = Mul11Grid.getSelNo();
	var sn = Mul11Grid.getRowColData(tSel - 1, 1);
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBussRateInputSql4");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(sn);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);

	if (strArray == null) {
		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
		return false;
	}

	fm.all('Currency').value = strArray[0][0];
	fm.all('CurrencyName').value = strArray[0][1];
	fm.all('ExchangeRate').value = strArray[0][2];
	fm.all('StartDate').value = strArray[0][3];
	fm.all('EndDate').value = strArray[0][4];
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
		button132();
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
