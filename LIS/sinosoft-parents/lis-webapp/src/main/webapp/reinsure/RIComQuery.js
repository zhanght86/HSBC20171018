var showInfo;
var sqlresourcename = "reinsure.RIComQuerySql";
var mDebug = "0";
var turnPage = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// �ύ�����水ť��Ӧ����
function submitForm() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIComQuerySql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.ReComCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ReComName.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, ReComGrid);
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	}
}

// ���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"��Proposal.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
	}
}

// ȡ����ť��Ӧ����
function cancelForm() {
	showDiv(operateButton, "true");
	showDiv(inputButton, "false");
}

// �ύǰ��У�顢����
function beforeSubmit() {
	// ��Ӳ���
}

// Click�¼������������ͼƬʱ�����ú���
function addClick() {
	// ����������Ӧ�Ĵ���
	showDiv(operateButton, "false");
	showDiv(inputButton, "true");
}

// ��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}

// ��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug) {
	if (cDebug == "1") {
		parent.fraMain.rows = "0,0,0,0,*";
	} else {
		parent.fraMain.rows = "0,0,0,82,*";
	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData() {
	var tRow = ReComGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"�����Ƚ���ѡ��!"+"");
		return;
	}

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIComQuerySql2");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(ReComGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);

	if (strArray == null) {
		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
		return false;
	}

	// var ComType=strArray[0][2];
	top.opener.fm.all('ReComCode').value = strArray[0][0];
	top.opener.fm.all('ReComName').value = strArray[0][1];
	top.opener.fm.all('PostalCode').value = strArray[0][2];
	top.opener.fm.all('Address').value = strArray[0][3];
	top.opener.fm.all('FaxNo').value = strArray[0][4];
    top.opener.fm.all('ComType').value 			=strArray[0][5];
    top.opener.fm.all('ComTypeName').value 			=strArray[0][6];
    top.opener.fm.all('Info').value 			=strArray[0][7];

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIComQuerySql3");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(ReComGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();

	strArray = easyExecSql(strSQL);
	top.opener.RelateGrid.clearData();

	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			top.opener.RelateGrid.addOne("RelateGrid");
			top.opener.RelateGrid.setRowColData(k, 1, strArray[k][0]);
			top.opener.RelateGrid.setRowColData(k, 2, strArray[k][1]);
			top.opener.RelateGrid.setRowColData(k, 3, strArray[k][2]);
			top.opener.RelateGrid.setRowColData(k, 4, strArray[k][3]);
			top.opener.RelateGrid.setRowColData(k, 5, strArray[k][4]);
			top.opener.RelateGrid.setRowColData(k, 6, strArray[k][5]);
			top.opener.RelateGrid.setRowColData(k, 7, strArray[k][6]);
			top.opener.RelateGrid.setRowColData(k, 8, strArray[k][7]);
		}
	}
	ClosePage();
}

function ClosePage() {
	top.close();
}
