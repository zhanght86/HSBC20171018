var showInfo;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIContDefineInputSql";
window.onfocus = myonfocus;
var typeRadio = "XXXX";
function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

// �ύ�����水ť��Ӧ����
function submitForm() {
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput()) {
			if ((veriryInput3()) && (veriryInput7())) {
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIContDefineSave.jsp?ModType=" + typeRadio;
				fm.submit(); // �ύ
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput2() {
	if (compareDate(fm.RValidate.value, fm.RInvalidate.value) == 1) {
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}

	var rowNum = SignerGrid.mulLineCount; // ����
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (SignerGrid.getRowColData(i, 1) == 0
				|| SignerGrid.getRowColData(i, 1) == null) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��¼���ͬǩ�������ڹ�˾�����ƣ�"+"");
			return false;
		}
		if (SignerGrid.getRowColData(i, 3) == 0
				|| SignerGrid.getRowColData(i, 3) == null) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��¼���ͬǩ���˵�������"+"");
			return false;
		}

		if (!isTel(SignerGrid.getRowColData(i, 5))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�绰¼���ʽ���ԣ�"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 6))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�ֻ�¼���ʽ���ԣ�"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 7))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"����¼���ʽ���ԣ�"+"");
			return false;
		}
		if (!isEMail(SignerGrid.getRowColData(i, 8))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��������¼���ʽ���ԣ�"+"");
			return false;
		}
	}
	return true;
}

function veriryInput3() {
	if (compareDate(fm.RValidate.value, fm.RInvalidate.value) == 1) {
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContDefineInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	var rs = easyExecSql(strSQL);
	if (rs != null) {
		myAlert(""+"��ͬ����Ѵ��ڣ����������룡"+"");
		return;
	}
	// if (typeRadio == "1") {
	// if (fm.ModRIContNo.value == null || fm.ModRIContNo.value == ""
	// || fm.ModRIContName.value == null
	// || fm.ModRIContName.value == "") {
	// alert("��ͬ�޸�ģʽ"�޸ĺ�ͬ����"��"�޸ĺ�ͬ����"����Ϊ�� ");
	// return false;
	// }
	// }
	var rowNum = SignerGrid.mulLineCount; // ����

	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (SignerGrid.getRowColData(i, 1) == 0
				|| SignerGrid.getRowColData(i, 1) == null) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��¼���ͬǩ�������ڹ�˾�����ƣ�"+"");
			return false;
		}

		if (SignerGrid.getRowColData(i, 3) == 0
				|| SignerGrid.getRowColData(i, 3) == null) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��¼���ͬǩ���˵�������"+"");
			return false;
		}

		// if(SignerGrid.getRowColData(i,4)==0||SignerGrid.getRowColData(i,4)==null)
		// {
		// alert("��"+num+"��,��¼���ͬǩ���˵�ְ��");
		// return false;
		// }

		if (!isTel(SignerGrid.getRowColData(i, 5))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�绰¼���ʽ���ԣ�"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 6))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�ֻ�¼���ʽ���ԣ�"+"");
			return false;
		}
		if (!isTel(SignerGrid.getRowColData(i, 7))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"����¼���ʽ���ԣ�"+"");
			return false;
		}
		if (!isEMail(SignerGrid.getRowColData(i, 8))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��������¼���ʽ���ԣ�"+"");
			return false;
		}
	}

	return true;
}

function queryClick() {
	fm.OperateType.value = "QUERY";
	window.open("./FrameRIContQuery.jsp?RIContNo=" + fm.RIContNo.value
			+ "&PageFlag=CONT");
}

function ChooseOldCont() {
	for ( var i = 0; i < fm.ContModType.length; i++) {
		if (fm.ContModType[i].checked) {
			typeRadio = fm.ContModType[i].value;
			break;
		}
	}
	// if (typeRadio == "0") {
	// divTitle2.style.display = "none";
	// divInput2.style.display = "none";
	// fm.ModRIContNo.value = "";
	// fm.ModRIContName.value = "";
	// }
	// if (typeRadio == "1") {
	// divTitle2.style.display = "";
	// divInput2.style.display = "";
	// fm.OperateType.value = "CHOICE";
	// window.open("./FrameRIChoiceQuery.jsp");
	// }
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content, ContentNO, ReComCode, CertifyCode) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

	} else {
		fm.RIContNo.value = ContentNO;
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		if (fm.OperateType.value == "DELETE") {
			resetForm();
		}
	}
}
// �޸�
function updateClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContDefineInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if (arrResult == null) {
		myAlert(""+"��ͬ��Ų����ڣ�"+"");
		return;
	}
	try {
		fm.OperateType.value = "UPDATE";
		if (verifyInput()) {
			if (!confirm(""+"��ȷ��Ҫ�޸ĸú�ͬ��"+"")) {
				return false;
			}
			if ((veriryInput2()) && (veriryInput7()) && (checkContState())) {
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIContDefineSave.jsp?ModType=" + typeRadio;
				fm.submit(); // �ύ
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function checkContState() {
	var tContState = fm.ContState.value;
	if (tContState == "01") {
		try {
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIContDefineInputSql3");// ָ��ʹ�õ�Sql��id
			mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
			var tSql = mySql.getString();
			var result = easyExecSql(tSql, 1, 0, 1);

			if (result != 0) {
				myAlert(""+"����ͬ����δ��Ч�ٱ�����������������ٱ�����Ϊ��Ч���޸�"+"");
				return false;
			}
		} catch (ex) {
			myAlert(ex);
			return false;
		}
	}
	return true;
}

function veriryInput4() // UPDATE У��
{
	if (compareDate(fm.RValidate.value, fm.RInvalidate.value) == 1) {
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}
	return true;
}

// ɾ��
function deleteClick() {
	if (!confirm(""+"��ȷ��Ҫɾ���ú�ͬ��"+"")) {
		return false;
	}
	fm.OperateType.value = "DELETE";
	try {
		if (verifyInput()) {
			if (veriryInput5()) {
				var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIContDefineSave.jsp";
				fm.submit(); // �ύ
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput5() {
	return true;
}

function afterQuery() {
}

// ���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"��CertifySendOutInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
	}
}

// �ύǰ��У�顢����
function beforeSubmit() {
	// ��Ӳ���
}

function nextStep() {
	if (fm.RIContNo.value == "" || fm.RIContNo.value == null) {
		myAlert(""+"���Ȳ�ѯ�ٱ���ͬ����ӷֱ���Ϣ��"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIContDefineInputSql4");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIContNo.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if (arrResult == null || arrResult == "") {
		myAlert(""+"��ͬ���Ϊ�ջ�δ�����ͬ������Ϣ��"+"");
		return false;
	}
	var reContCode = fm.all("RIContNo").value;
	var reCountType = fm.all("ReCountType").value;
	var varSrc = "&ReContCode=" + reContCode;
	window.open(
			"./FrameMainCessInfo.jsp?Interface=RISchemaInput.jsp&reCountType="
					+ reCountType + "" + varSrc, "true"); // +varSrc,
}

// �����������ʽ�ǲ�����ȷ
function isEMail(strValue) {
	var NUM1 = "@";
	var NUM2 = ".";
	var i;
	if (strValue == null || strValue == "")
		return true;
	for (i = 0; i < strValue.length; i++) {
		if (strValue.indexOf(NUM1) < 0) {
			return false;
		}
		if (strValue.indexOf(NUM2) < 0) {
			return false;
		}
	}
	return true;
}

// ������ĵ绰���������֤
function isTel(strValue) {
	var NUM = "0123456789-() ";
	var i;
	if (strValue == null || strValue == "")
		return true;
	for (i = 0; i < strValue.length; i++) {
		if (NUM.indexOf(strValue.charAt(i)) < 0)
			return false;

	}
	return true;
}

// Ҫ��У��
function veriryInput7() {
	// У�鲻��¼���ظ���ͬҪ��
	for ( var n = 0; n < FactorGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < FactorGrid.mulLineCount; m++) {
			if (FactorGrid.getRowColData(n, 1) == FactorGrid
					.getRowColData(m, 1)) {
				myAlert(""+"����¼���ظ�Ҫ��!"+"");
				return false;
			}
		}
	}
	for ( var n = 0; n < FactorGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < FactorGrid.mulLineCount; m++) {
			if (FactorGrid.getRowColData(n, 2) == FactorGrid
					.getRowColData(m, 2)) {
				myAlert(""+"����¼���ظ�Ҫ��!"+"");
				return false;
			}
		}
	}
	isNull = false; // ����Ƿ�¼��Ҫ��ֵ���
	for ( var i = 0; i < FactorGrid.mulLineCount; i++) {
		if (FactorGrid.getRowColData(i, 3) == null
				|| FactorGrid.getRowColData(i, 3) == "") {
			isNull = true;
		}
	}
	if (isNull) {
		myAlert(""+"����¼��Ҫ��ֵ!"+"");
		return false;
	}
	return true;
}
