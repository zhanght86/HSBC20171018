//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIContQueryAsCom.js
//�����ܣ���ͬ��ѯ
//�������ڣ�2011-7-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIContQueryAsComInputSql";

// �� ѯ��ť
function queryCout() {
	if (!verifyInput()) {
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContQueryAsComInputSql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.ReComCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	
	turnPage.queryModal(strSQL, Mul10Grid);
}

function doreset() {
	fm.ReComCode.value = "";
	fm.ReComName.value = "";
	fm.RIContNo.value = "";
	fm.RIContName.value = "";
	Mul10Grid.clearData(); 
	Mul11Grid.clearData(); 
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "ricompanycode") {
		Mul10Grid.clearData(); 
		Mul11Grid.clearData(); 
	}else if (codeName == "ricontno"){
		Mul10Grid.clearData(); 
		Mul11Grid.clearData(); 
	}
}


function queryPrecept() {
	var tRow = Mul10Grid.getSelNo();
	
	if (tRow == 0) {
		myAlert(""+"����б���ѡ��Ҫ��ѯ���ٱ���ͬ��"+"");
		return false;
	}
	var sn = Mul10Grid.getRowColData(tRow - 1, 3);
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContQueryAsComInputSql2");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.ReComCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(sn);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, Mul11Grid);
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
