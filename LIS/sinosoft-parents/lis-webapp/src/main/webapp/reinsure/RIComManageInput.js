var showInfo;
var sqlresourcename = "reinsure.RIComManageInputSql";
var turnPage = new turnPageClass();
window.onfocus = myonfocus;

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
		if (verifyInput() && RelateGrid.checkValue("RelateGrid")) {
			if (veriryInput3()) {
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIComManageSave.jsp";
				fm.submit(); // �ύ
			} else {
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput() {

	return ture;

}

function veriryInput3() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIComManageInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.ReComCode.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	var strResult = easyExecSql(strSQL);

	if (strResult != null && strResult != "") {
		myAlert(""+"���ٱ���˾�Ѵ��ڣ�"+"");
		return false;
	}
	if (fm.ReComName.value == "" || fm.ReComName.value == null) {
		myAlert(""+"��¼�빫˾���ƣ�"+"");
		return false;
	}
	if (!isTel(fm.FaxNo.value)) {
		myAlert(""+"����¼���ʽ���ԣ�"+"");
		return false;
	}
	if (!isInteger1(fm.PostalCode.value)) {
		return false;
	}

	var rowNum = RelateGrid.mulLineCount; // ����

	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (RelateGrid.getRowColData(i, 4) == null
				|| RelateGrid.getRowColData(i, 1) == "") {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��¼����ϵ��������"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 4))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��ϵ�绰¼�벻�ԣ�"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 5))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�ֻ���ʽ¼�벻�ԣ�"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 6))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�����ʽ¼�벻�ԣ�"+"");
			return false;
		}
		if (!isEMail(RelateGrid.getRowColData(i, 7))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��������¼��ʽ�벻�ԣ�"+"");
			return false;
		}
	}

	return true;
}

function queryClick() {
	fm.OperateType.value = "QUERY";

	window.open("./FrameRIComQuery.jsp?ReComCode=" + fm.ReComCode.value);
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
		fm.ReComCode.value = ContentNO;

		// content="����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}

	if (fm.OperateType.value == "DELETE") {
		resetForm();
	}

}

function updateClick() {
	fm.OperateType.value = "UPDATE";
	try {
		if (veriryInput4()) {
			if (verifyInput()) {
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIComManageSave.jsp";
				fm.submit(); // �ύ
			} else {
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function veriryInput4() // UPDATE У��
{
	if (fm.ReComCode.value == "" || fm.ReComCode.value == null) {
		myAlert(""+"���Ȳ�ѯ��˾��Ϣ��"+"");
		return false;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIComManageInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.ReComCode.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	var strResult = easyExecSql(strSQL);

	if (strResult == null) {
		myAlert(""+"���ٱ���˾�����ڣ�"+"");
		return false;
	}
	if (fm.ReComName.value == "" || fm.ReComName.value == null) {
		myAlert(""+"��¼�빫˾���ƣ�"+"");
		return false;
	}
	if (!isTel(fm.FaxNo.value)) {
		myAlert(""+"����¼���ʽ���ԣ�"+"");
		return false;
	}

	if (!isInteger1(fm.PostalCode.value)) {
		return false;
	}

	var rowNum = RelateGrid.mulLineCount; // ����
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;
		if (RelateGrid.getRowColData(i, 1) == ""
				|| RelateGrid.getRowColData(i, 1) == null) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��¼����ϵ��������"+"");
			return false;
		}

		if (!isTel(RelateGrid.getRowColData(i, 4))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�绰¼�벻�ԣ�"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 5))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"�ֻ�¼�벻�ԣ�"+"");
			return false;
		}
		if (!isTel(RelateGrid.getRowColData(i, 6))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"����¼�벻�ԣ�"+"");
			return false;
		}
		if (!isEMail(RelateGrid.getRowColData(i, 7))) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"��������¼�벻�ԣ�"+"");
			return false;
		}
	}
	return true;

}

function deleteClick() {
	fm.OperateType.value = "DELETE";
	if (!confirm(""+"��ȷ��Ҫɾ����������"+"")) {
		return false;
	}
	try {
		if (verifyInput5()) {
			var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIComManageSave.jsp";
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function verifyInput5() {
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

/**
 * ���������Ƿ��ǵ绰��У��
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            ������ֵ���ʽ���ַ������ʽ
 * @return ����ֵ��true--������, false--����������
 */
function isTel(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^[0-9(-)]+$/.test(strValue)) {
		return true;
	} else {
		myAlert(""+"�绰��ʽ����ȷ��"+"");
		return false;
	}
	return true;
}

/**
 * ���������Ƿ�����ַ��У��
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            ������ֵ���ʽ���ַ������ʽ
 * @return ����ֵ��true--������, false--����������
 */
function isWeb(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^http[s?]:\/\//.test(strValue)) {
		return true;
	} else {
		myAlert(""+"��ַ��ʽ����ȷ��"+"");
		return false;
	}
	return true;
}

/**
 * ���������Ƿ����ʱ��У��
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            ������ֵ���ʽ���ַ������ʽ
 * @return ����ֵ��true--������, false--����������
 */
function isInteger1(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^\d{6}$/.test(strValue)) {
		return true;
	} else {
		myAlert(""+"���������ʽ����ȷ��"+"");
		return false;
	}
	return true;
}

/**
 * ���������Ƿ��������У��
 * <p>
 * <b>Example: </b>
 * <p>
 * <p>
 * isInteger("Minim") returns false
 * <p>
 * <p>
 * isInteger("123") returns true
 * <p>
 * 
 * @param strValue
 *            ������ֵ���ʽ���ַ������ʽ
 * @return ����ֵ��true--������, false--����������
 */
function isEMail(strValue) {
	if (strValue == null || strValue == "")
		return true;
	if (/^\w+@\w+\..+$/.test(strValue)) {
		return true;
	} else {
		myAlert(""+"�����ʽ����ȷ��"+"");
		return false;
	}
	return true;
}
