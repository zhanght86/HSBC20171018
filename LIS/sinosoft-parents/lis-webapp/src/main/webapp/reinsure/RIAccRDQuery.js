//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIAccRDQuery.js
//�����ܣ��ֳ����ζ���-��Ϣ��ѯ
//�������ڣ�2011/6/16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var sqlresourcename = "reinsure.RIAccRDQueryInputSql";
var turnPage = new turnPageClass();
window.onfocus = myonfocus;

// ��ѯ��ť
function AccumulateQuery() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDQueryInputSql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.AccumulateDefName.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIAccState.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, RIAccumulateGrid);
}

// ���ذ�ť
function ReturnData() {
	var tRow = RIAccumulateGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"�����Ƚ���ѡ��!"+"");
		return;
	}
	
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDQueryInputSql2");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(RIAccumulateGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);

	if (strArray == null) {
		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
		return false;
	}
	top.opener.fm.all('AccumulateDefNO').value = strArray[0][0];
	top.opener.fm.all('AccumulateDefName').value = strArray[0][1];
	top.opener.fm.all('ArithmeticDefID').value = strArray[0][3];
	top.opener.fm.all('ArithmeticDefName').value = strArray[0][9];
	// �ֳ���������
	top.opener.fm.all('RIAccType').value = strArray[0][4];
	top.opener.fm.all('RIAccTypeName').value = strArray[0][5];
	top.opener.fm.all('StandbyFlag').value = strArray[0][6];
	// �ֳ�����״̬
	top.opener.fm.all('RIAccState').value = strArray[0][8];
	top.opener.fm.all('RIAccStateName').value = strArray[0][2];
	top.opener.fm.all('moneyKind').value = strArray[0][10];
	top.opener.fm.all('DIntv').value = strArray[0][11];
	top.opener.fm.all('DIntvName').value = strArray[0][13];
	top.opener.fm.all('BFFlagName').value = strArray[0][14];
	top.opener.fm.all('StandbyFlagName').value = strArray[0][15];
	top.opener.fm.all('BFFlag').value = strArray[0][12];
	top.opener.fm.all('CalOrder').value = strArray[0][16];

/*start jintao 8.18*/
	// �ֱ�����
	//top.opener.fm.all('RIRiskFeature').value = strArray[0][6];
	//top.opener.fm.all('RIRiskFeatureName').value = strArray[0][7];

//	if ("01" == strArray[0][6]) {
//		top.opener.fm.caccspec.style.display = '';
//	} else {
//		top.opener.fm.caccspec.style.display = 'none';
//	}
//	if ("01" == strArray[0][4]) {
//		//top.opener.fm.cacctype.style.display = '';
//		top.opener.divCode1.style.display = "none";
//		top.opener.divName1.style.display = "none";
//	} else {
//		//top.opener.fm.cacctype.style.display = 'none';
//		top.opener.divCode1.style.display = "";
//		top.opener.divName1.style.display = "";
//	}
	/*end jintao 8.18*/
	
//	strArray = easyExecSql(strSQL);
//
//	top.opener.RIAccRiskDutyGrid.clearData();
//
//	if (strArray != null) {
//		for ( var k = 0; k < strArray.length; k++) {
//			top.opener.RIAccRiskDutyGrid.addOne("RIAccRiskDutyGrid");
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 1, strArray[k][0]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 2, strArray[k][1]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 3, strArray[k][2]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 4, strArray[k][3]);
//			top.opener.RIAccRiskDutyGrid.setRowColData(k, 5, strArray[k][4]);
//		}
//	}
//	top.opener.turnPage.queryModal(strSQL, top.opener.RIAccRiskDutyGrid);
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
