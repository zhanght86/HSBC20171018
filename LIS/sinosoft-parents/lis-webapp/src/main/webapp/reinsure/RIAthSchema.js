var showInfo;
var mDebug = "0";
var sqlresourcename = "reinsure.RIAthSchemaInputSql";
var DealWithNam;
var turnPage = new turnPageClass();

// ���㷨��������
function submitForm() {
	if (verifyInput()) {
		var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

		fm.OperateType.value = 'add';
		fm.submit(); // �ύ
	}
}

// ���㷨�����ѯ
function queryClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAthSchemaInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.ArithmeticDefID.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ArithmeticDefName.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ArithSubType.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, ResultGrid);
}

// ���㷨�����޸�
function updateClick() {
	if (verifyInput()) {
		var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

		fm.OperateType.value = 'update';
		fm.submit(); // �ύ
	}
}
// ���㷨����ɾ��
function deleteClick() {
	if (fm.ArithmeticDefID.value == null || fm.ArithmeticDefID.value == "") {
		myAlert(""+"��¼�����㷨����!"+"");
		return false;
	}
	if (!confirm(""+"��ȷ��Ҫ�޸ĸ����㷨��"+"")) {
		return false;
	}
	var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+showStr;// encodeURI(encodeURI(showStr));
	showInfo = window
			.showModelessDialog(urlStr, window,
					"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.OperateType.value = 'delete';
	fm.submit(); // �ύ
}

function getDetail() {
	var tRow = ResultGrid.getSelNo();
	if (tRow > 0) {
		fm.ArithmeticDefID.value = ResultGrid.getRowColData(tRow - 1, 1);
		fm.ArithmeticDefName.value = ResultGrid.getRowColData(tRow - 1, 2);
		fm.AccumulateDefNO.value = ResultGrid.getRowColData(tRow - 1, 5);
		fm.ArithTypeName.value = ResultGrid.getRowColData(tRow - 1, 3);
		fm.ArithSubType.value = ResultGrid.getRowColData(tRow - 1, 7);
		fm.PreceptType.value = ResultGrid.getRowColData(tRow - 1, 6);
		if (fm.PreceptType.value != "") {
			fm.ArithContName.value = ResultGrid.getRowColData(tRow - 1, 4);
		}
		fm.AccumulateDefName.value = ResultGrid.getRowColData(tRow - 1, 8);
		if (fm.ArithSubType.value == "P") {
			divArithType1.style.display = '';
			divArithType2.style.display = '';
		} else {
			divArithType1.style.display = 'none';
			divArithType2.style.display = 'none';
		}
	}
}

// ��ϸ���㷨����
function nextStep() {
	// ��ѡ��һ��
	var tRow = ResultGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"�����Ƚ���ѡ��!"+"");
		return;
	}
	var varSrc = "&ArithmeticID='" + ResultGrid.getRowColData(tRow - 1, 1)
			+ "' ";
	window.open(
			"./FrameMainCalItemBatch.jsp?Interface=RIItemCalDetailInput.jsp"
					+ varSrc, "true1");
}

function afterSubmit(FlagStr, content) {
	showInfo.close();

	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		mOperate = "";
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	if(fm.OperateType.value == "delete"){
		initForm();
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "riatithtype") {
		// ������ȡ���㷨
		if (Field.value == "L") {
			divArithType1.style.display = 'none';
			divArithType2.style.display = 'none';
		}
		// �ֱ��������㷨
		if (Field.value == "P") {
			divArithType1.style.display = '';
			divArithType2.style.display = '';
		}
	}
}
