//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIRateMaint.js
//�����ܣ�����ά��
//�������ڣ�2011/6/17
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var sqlresourcename = "reinsure.RIRateMaintInputSql";
var turnPage = new turnPageClass();

function veriryInsert() {
	if (fm.EndDate.value != "" && fm.StartDate.value > fm.EndDate.value) {
		myAlert(""+"��Ч���ڲ��ܴ���ʧЧ���ڣ�"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.InterestType.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RiskCode.value);// ָ������Ĳ������������˳�����
	var tSQL = mySql.getString();
	var strArray = easyExecSql(tSQL);
	if (strArray != null) {
		myAlert(""+"��Ч���ڱ�����ڵ�ǰ��Ч�������������"+"");
		return false;
	}
	return true;
}

function veriryDelete() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.InterestType.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RiskCode.value);// ָ������Ĳ������������˳�����
	var tSQL = mySql.getString();
	var strArray = easyExecSql(tSQL);
	if (strArray != null) {
		myAlert(""+"ֻ��ɾ����Ч�����������"+"");
		return false;
	}
	return true;
}
// ���水ť
function button103() {
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

// ��ѯ��ť
function button104() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql3");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RiskCodeQuery.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.InterestTypeQuery.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.StartDateQuery.value);// ָ������Ĳ������������˳�����
	var tSQL = mySql.getString();
	turnPage.queryModal(tSQL, Mul10Grid);
}

// ɾ����ť
function button105() {
	var tRow = Mul10Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"��ѡ��Ҫɾ�������Σ�"+"");
		return false;
	}
	if (!confirm(""+"��ȷ��Ҫɾ����������"+"")) {
		return false;
	}
	var sn = Mul10Grid.getRowColData(tRow - 1, 1);
	fm.OperateType.value = "DELETE";
	try {
		if (veriryDelete()) {
			var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "RIRateMaintSave.jsp?sn=" + sn;
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function showFormInfo() {
	var sn = Mul10Grid.getRowColData(Mul10Grid.getSelNo() - 1, 1);
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIRateMaintInputSql4");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(sn);// ָ������Ĳ������������˳�����
	var tSQL = mySql.getString();
   var	strArray = easyExecSql(tSQL);
	if (strArray == null) {
		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
		return false;
	}

	fm.all('RiskCode').value = strArray[0][0];
	fm.all('RiskName').value = strArray[0][1];
	fm.all('InterestType').value = strArray[0][4];
	fm.all('InterestTypeName').value = strArray[0][5];
	fm.all('InterestRate').value = strArray[0][6];
	fm.all('StartDate').value = strArray[0][7];
	fm.all('EndDate').value = strArray[0][8];
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
		button104();
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
