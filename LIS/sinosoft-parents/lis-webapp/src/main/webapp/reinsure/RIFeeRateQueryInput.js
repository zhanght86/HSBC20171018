var sqlresourcename = "reinsure.RIFeeRateQueryInputSql";
var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// �ύ�����水ť��Ӧ����
function submitForm() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateQueryInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.FeeTableNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.FeeTableName.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, FeeRateGrid);
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	// showInfo.close();
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

// Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick() {
	// ����������Ӧ�Ĵ���
	myAlert("update click");
}

// Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick() {
	// ����������Ӧ�Ĵ���
	myAlert("query click");
	// ��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
	// ��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

// Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick() {
	// ����������Ӧ�Ĵ���
	myAlert("delete click");
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
	var tRow = FeeRateGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"�����Ƚ���ѡ��!"+"");
		return;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateQueryInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(FeeRateGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	strArray = easyExecSql(strSQL);
	if (strArray == null) {
		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
		return false;
	}
	top.opener.fm.all('FeeTableNo').value = strArray[0][0];
	top.opener.fm.all('FeeTableName').value = strArray[0][1];
	top.opener.fm.all('FeeTableType').value = strArray[0][2];
	top.opener.fm.all('FeeTableTypeName').value = strArray[0][3];
	//IE11-TextArea
	top.opener.fm.ReMark.value = strArray[0][4];
	top.opener.fm.all('State').value = strArray[0][5];
	top.opener.fm.all('stateName').value = strArray[0][6];
	top.opener.fm.all('UseType').value = strArray[0][7];
	top.opener.fm.all('UseTypeName').value = strArray[0][8];
	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RIFeeRateQueryInputSql3");// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(FeeRateGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
	var strSQL = mySql1.getString();
	strArray = easyExecSql(strSQL);
	top.opener.TableClumnDefGrid.clearData();
	if (strArray != null) {
		for ( var k = 0; k < strArray.length; k++) {
			top.opener.TableClumnDefGrid.addOne("TableClumnDefGrid");
			top.opener.TableClumnDefGrid.setRowColData(k, 1, strArray[k][0]);
			top.opener.TableClumnDefGrid.setRowColData(k, 2, strArray[k][1]);
			top.opener.TableClumnDefGrid.setRowColData(k, 3, strArray[k][2]);
			top.opener.TableClumnDefGrid.setRowColData(k, 4, strArray[k][3]);
			top.opener.TableClumnDefGrid.setRowColData(k, 5, strArray[k][4]);
			top.opener.TableClumnDefGrid.setRowColData(k, 7, strArray[k][5]);
			top.opener.TableClumnDefGrid.setRowColData(k, 9, strArray[k][6]);
		}
	}
	top.close();
}

function ClosePage() {
	top.close();
}
