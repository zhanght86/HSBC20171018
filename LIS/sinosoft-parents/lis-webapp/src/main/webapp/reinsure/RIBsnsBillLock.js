//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIBsnsBillLock.js
//�����ܣ����ݼ���
//�������ڣ�2011/8/11
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillLockInputSql";
function veriry(state) {
	var str = getCurrentDate();
	if (state == "1") {
		if (fm.LockDate.value == "" || fm.LockDate.value == null) {
			myAlert(""+"�������ڲ���Ϊ�գ�"+"");
			return false;
		}
		if (fm.LockDate.value > str) {
			myAlert(""+"�������ڲ��ܴ��ڵ���!"+"");
			return false;
		}

		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIBsnsBillLockInputSql2");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
		mySql1.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
		var strSql = mySql1.getString();
		var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
		if (strQueryResult) {
			myAlert(""+"�����ѱ������������ظ����������Ƚ�����"+"");
			return false;
		}
	} else {
		if (fm.LockDate.value != str) {
			myAlert(""+"��������ֻ��Ϊ����!"+"");
			return false;
		}

		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RIBsnsBillLockInputSql3");// ָ��ʹ�õ�Sql��id
		mySql2.addSubPara("");
		var SQL = mySql2.getString();
		result = easyExecSql(SQL);
		if (result == null) {
			Mul10Grid.clearData();

			myAlert(""+"��Ч������û�м��������ݣ�"+"");

			return false;
		}
		var mySql3 = new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId("RIBsnsBillLockInputSql4");// ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.AccumulateDefNO.value);
		mySql3.addSubPara(fm.AccumulateDefNO.value);
		var strSql = mySql3.getString();
		var strQueryResult = easyQueryVer3(strSql, 1, 1, 1);
		if (strQueryResult) {
			myAlert(""+"��Ч�������������ǽ���״̬!"+"");
			return false;
		}
	}
	return true;
}

function queryClick() {

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillLockInputSql5");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	result = easyExecSql(strSQL);
	if (result == null) {
		Mul10Grid.clearData();
		myAlert(""+"��ѯ���Ϊ��"+"");
		return false;
	}

	turnPage.queryModal(strSQL, Mul10Grid);

}

// �� �水ť
function saveLock(state) {
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput()) {
			if (veriry(state)) {
				var showStr = ""+"���ڱ������ݣ������Ժ���Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIBsnsBillLockSave.jsp?state=" + state;
				fm.submit(); // �ύ
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
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

		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIBsnsBillLockInputSql6");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
		var strSQL = mySql.getString();

		turnPage.queryModal(strSQL, Mul10Grid);
		fm.lockbutton.style.display = "none";
		fm.unlockbutton.style.display = "none";
	}
}
function afterCodeSelect(codeName, Field) {
	if (codeName == "riaccmucode") {
		Mul10Grid.clearData();
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIBsnsBillLockInputSql7");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(Field.value);// ָ������Ĳ������������˳�����
		var tSQL = mySql.getString();
		var strArray = easyExecSql(tSQL);
		if (strArray == "1") {
			fm.lockbutton.style.display = "none";
			fm.unlockbutton.style.display = "";
			fm.LockDate.value = getCurrentDate();
			divLockDate1.style.display = "none";
			divLockDate2.style.display = "none";
		} else {
			fm.lockbutton.style.display = "";
			fm.unlockbutton.style.display = "none";
			fm.LockDate.value = "";
			divLockDate1.style.display = "";
			divLockDate2.style.display = "";
		}
	}
}
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
