//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIProfitLossDefQuery.js
//�����ܣ�ӯ��Ӷ�����ѯ
//�������ڣ�2011/8/20
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var arrResult = new Array();
var sqlresourcename = "reinsure.RIProfitLossDefQueryInputSql";

// ��ѯ��ť
function button138() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefQueryInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIProfitName.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RelaType.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, MulDefQueryGrid);

}

// ���ذ�ť
function button139() {
	var tRow = MulDefQueryGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"�����Ƚ���ѡ��!"+"");
		return;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefQueryInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(MulDefQueryGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);

	if (strArray == null) {
		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
		return false;
	}
    
	top.opener.fm.all('RIProfitNo').value = strArray[0][0];
	top.opener.fm.all('RIProfitName').value = strArray[0][1];
	top.opener.fm.all('RIcomCode').value = strArray[0][2];
	top.opener.fm.all('RIcomName').value = strArray[0][6];
	top.opener.fm.all('ContNo').value = strArray[0][3];
	top.opener.fm.all('ContName').value = strArray[0][7];
	top.opener.fm.all('RelaType').value = strArray[0][4];
	top.opener.fm.all('RelaTypeName').value = strArray[0][8];
	top.opener.fm.all('RIProfitDes').value = strArray[0][5];

	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIProfitLossDefQueryInputSql3");// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(MulDefQueryGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
	var strSQL = mySql1.getString();
	strArray = easyExecSql(strSQL);

	top.opener.Mul9Grid.clearData();

	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			top.opener.Mul9Grid.addOne("Mul9Grid");
			top.opener.Mul9Grid.setRowColData(k, 1, strArray[k][0]);
			top.opener.Mul9Grid.setRowColData(k, 2, strArray[k][1]);
		}
	}
	top.close();
}
// �ر�ҳ��
function button140() {
	top.close();
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
