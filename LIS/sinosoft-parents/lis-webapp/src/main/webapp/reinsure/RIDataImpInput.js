var showInfo;
var turnPage = new turnPageClass();
window.onfocus = myonfocus;
var ImportPath;
var sqlresourcename = "reinsure.RIDataImpInputSql";

function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

function veriryInput3() {
	return true;
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
	}
}

function FeeRateTableImp() {
	if (fmImport.FileName.value == "" || fmImport.FileName.value == null) {
		myAlert(""+"��ѡ�����ļ���"+"");
		return false;
	}
	getImportPath();

	ImportFile = fmImport.all('FileName').value;

	var showStr = ""+"�����������ݡ���"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	showInfo = window.showModelessDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fmImport.action = "./RIDataImpSave.jsp?ImportPath=" + ImportPath;
	fmImport.submit(); // �ύ
}

function getImportPath() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIDataImpInputSql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara("");// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);

	// �ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		myAlert(""+"δ�ҵ��ϴ�·��"+"");
		return;
	}
	// �������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	// ��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

	ImportPath = turnPage.arrDataCacheSet[0][0];
}

// ���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"��CertifySendOutInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
	}
}

function ClosePage() {
	top.close();
}

// �ύǰ��У�顢����
function beforeSubmit() {
	// ��Ӳ���
}
