//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIDataModify.js
//�����ܣ�ҵ�����ݵ���
//�������ڣ�2011-07-30
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
var sqlresourcename = "reinsure.RIDataModifyInputSql";
var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();

// �� ѯ��ť
function button136() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIDataModifyInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.InsuredNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIAccNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RiskCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.DutyCode.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();

	turnPage.queryModal(strSQL, Mul11Grid);
}

function veriry() {
	for ( var n = 0; n < Mul13Grid.mulLineCount; n++) {
		if (Mul13Grid.getRowColData(n, 1) == null
				|| Mul13Grid.getRowColData(n, 1) == "") {
			myAlert(""+"�޸��ֶδ��ڿ��У�"+"");
			return false;
		}
	}

	// У�鲻��¼���ظ���ͬҪ��
	for ( var n = 0; n < Mul13Grid.mulLineCount; n++) {
		for ( var m = n + 1; m < Mul13Grid.mulLineCount; m++) {
			if (Mul13Grid.getRowColData(n, 1) == Mul13Grid.getRowColData(m, 1)) {
				myAlert(""+"�޸��ֶβ����ظ���"+"");
				return false;
			}
		}
	}
	return true;
}

// �� �İ�ť
function button137() {
	try {
		if (verifyInput()) {
			if (veriry()) {
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIDataModifySave.jsp";
				//fm.submit();
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

function afterCodeSelect(codeName, Field) {
	if (codeName == "ridatamodifycol") {
		var tRow = Mul11Grid.getSelNo();
		if (tRow == 0) {
			myAlert(""+"����б���ѡ���¼��"+"");
			return false;
		}
		var eventno = Mul11Grid.getRowColData(tRow - 1, 1);
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIDataModifyInputSql2");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(Field.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(eventno);// ָ������Ĳ������������˳�����
		var strSQL = mySql.getString();

		var arrResult = easyExecSql(strSQL);
		var rowNum = Mul13Grid.mulLineCount; // ����
		for ( var i = 0; i < rowNum; i++) {
			if (Mul13Grid.getRowColData(i, 1) == Field.value) {
				Mul13Grid.setRowColData(i, 3, arrResult[0][0].toString());
			}
		}
	}
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
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
		// ִ����һ������
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
