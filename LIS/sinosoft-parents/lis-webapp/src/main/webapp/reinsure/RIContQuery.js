var sqlresourcename = "reinsure.RIContQuerySql";
var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

// �ύ�����水ť��Ӧ����
function submitForm() {
	if (fm.PageFlag.value == "CONT") {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIContQuerySql1");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
		var strSQL = mySql.getString();
		turnPage.queryModal(strSQL, ReContGrid);
	}
	// else{
	// strSQL = "select ricontno,ricontname,conttype,(select codename from
	// ldcode where codetype = 'riconttype' and code = ContType),"
	// +"
	// ReInsuranceType,decode(ReInsuranceType,'01','�����ֱ�','02','���ֱ�','03','�������ֱ�'),cvalidate,enddate,"
	// +" State,(select codename from ldcode where code=state and
	// codetype='ristate'),gitype,decode(gitype,'1','�¶�','3','����','12','���')
	// from RIBarGainInfo where 1=1 "
	// + getWherePart("RIContNo","RIContNo")
	// ;
	// strSQL = strSQL +" order by RIContNo";
	// }
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
	if (fm.PageFlag.value == "CONT") {
		// ��ѡ��һ��
		var tRow = ReContGrid.getSelNo();
		if (tRow == 0) {
			myAlert(""+"�����Ƚ���ѡ��!"+"");
			return;
		}

		// var strSQL = "select RIContNo,RIContName,ContType,(select codename
		// from ldcode where codetype = 'riconttype' and code = ContType), "
		// +" ReInsuranceType,(case ReInsuranceType when '01' then '�����ֱ�' when
		// '02' then '���ֱ�' when '03' then '�������ֱ�' end), "
		// +"
		// CValiDate,EndDate,RISignDate,state,decode(State,'0','δ��Ч','1','��Ч',''),gitype,decode(gitype,'1','�¶�','3','����','12','���')
		// "
		// +" from RIBarGainInfo "
		// + "where 1=1 and RIContNo='"+ReContGrid.getRowColData(tRow-1,1)+"'"
		// ;
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIContQuerySql2");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(ReContGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
		var strSQL = mySql.getString();
		strArray = easyExecSql(strSQL);

		// �����ѯ���Ϊ�գ�
		if (strArray == null) {
			myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
			return false;
		}

		// �����ѯ�����Ϊ�գ���Ѳ�ѯ�Ľ�������Զ���䵽��ҳ���Ӧ�Ķ�����
		top.opener.fm.all('RIContNo').value = strArray[0][0];
		top.opener.fm.all('RIContName').value = strArray[0][1];
		// top.opener.fm.all('ContType').value =strArray[0][2];
		// top.opener.fm.all('ContTypeName').value =strArray[0][3];
		top.opener.fm.all('ReCountType').value = strArray[0][2];
		top.opener.fm.all('ReCountTypeName').value = strArray[0][3];
		top.opener.fm.all('RValidate').value = strArray[0][5];
		top.opener.fm.all('RInvalidate').value = strArray[0][6];
		top.opener.fm.all('RSignDate').value = strArray[0][4];
		top.opener.fm.all('ContState').value = strArray[0][7];
		top.opener.fm.all('ContStateName').value = strArray[0][8];
		top.opener.fm.all('UseType').value = strArray[0][9];
		top.opener.fm.all('UseTypeName').value = strArray[0][10];

		// �������������ϵ�˱�RIBarGainSigner�в�ѯ��Ӧ�ļ�¼��
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIContQuerySql3");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(ReContGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
		var strSQL = mySql1.getString();
		strArray = easyExecSql(strSQL);
		top.opener.SignerGrid.clearData();
		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				top.opener.SignerGrid.addOne("SignerGrid");

				top.opener.SignerGrid.setRowColData(k, 1, strArray[k][0]);
				top.opener.SignerGrid.setRowColData(k, 2, strArray[k][1]);
				top.opener.SignerGrid.setRowColData(k, 3, strArray[k][2]);
				top.opener.SignerGrid.setRowColData(k, 4, strArray[k][3]);
				top.opener.SignerGrid.setRowColData(k, 5, strArray[k][4]);
				top.opener.SignerGrid.setRowColData(k, 6, strArray[k][5]);
				top.opener.SignerGrid.setRowColData(k, 7, strArray[k][6]);
				top.opener.SignerGrid.setRowColData(k, 8, strArray[k][7]);
				top.opener.SignerGrid.setRowColData(k, 9, strArray[k][8]);
			}
		}

		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RIContQuerySql4");// ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(ReContGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
		var strSQL = mySql2.getString();
		strArray = easyExecSql(strSQL);
		top.opener.FactorGrid.clearData();
		if (strArray != null) {
			for ( var k = 0; k < strArray.length; k++) {
				top.opener.FactorGrid.addOne("FactorGrid");
				top.opener.FactorGrid.setRowColData(k, 1, strArray[k][0]);
				top.opener.FactorGrid.setRowColData(k, 2, strArray[k][1]);
				top.opener.FactorGrid.setRowColData(k, 3, strArray[k][2]);
			}
		}
	}
	top.close();
}

function ClosePage() {
	top.close();
}
