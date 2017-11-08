var showInfo;
var sqlresourcename = "reinsure.RIFeeRateBatchInputSql";
var turnPage = new turnPageClass();
window.onfocus = myonfocus;
var ImportPath;

function myonfocus() {
	if (showInfo != null) {
		try {
			showInfo.focus();
		} catch (ex) {
			showInfo = null;
		}
	}
}

function queryFeeRateBatch() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateBatchInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.FeeTableNo.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, FeeRateBatchGrid);
}

// �ύ�����水ť��Ӧ����
function submitForm() {
	var mySql2 = new SqlClass();
	mySql2.setResourceName(sqlresourcename);
	mySql2.setSqlId("RIFeeRateBatchInputSql4");// ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.FeeTableNo.value);// ָ������Ĳ������������˳�����
	mySql2.addSubPara(fm.InureDate.value);// ָ������Ĳ������������˳�����
	var sql2 = mySql2.getString();
	var result = easyExecSql(sql2);
	if (result != null) {
		myAlert(""+"��Ч���ڱ�����ڵ�ǰ��Ч������������Σ�"+"");
		return;
	}
	
	
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateBatchInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.FeeTableNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.BatchNo.value);// ָ������Ĳ������������˳�����
	var sql = mySql.getString();
	var result = easyExecSql(sql);
	if (result != null) {
		myAlert(""+"���ʱ����α���Ѵ��ڣ�"+"");
		return;
	}
	if (fm.State.value == '01') {
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIFeeRateBatchInputSql3");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.FeeTableNo.value);// ָ������Ĳ������������˳�����
		var strSQL = mySql1.getString();

		var tState = easyExecSql(strSQL);
		if (tState == '02' || tState == '' || tState == null) {
			myAlert(""+"���ʱ�״̬Ϊ��Ч������״̬����Ϊ��Ч��"+"");
			return false;
		}
	}
	try {
		fm.OperateType.value = "INSERT";
		if (verifyInput()) {
			LapseDateCharge();
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIFeeRateBatchSave.jsp";
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function LapseDateCharge() {
	var row = FeeRateBatchGrid.mulLineCount;
	if (row != 0) {
		if (FeeRateBatchGrid.getRowColData(row - 1, 5) == ""
				|| FeeRateBatchGrid.getRowColData(row - 1, 5) == null) {
			fm.BatchNoAbove.value = FeeRateBatchGrid.getRowColData(row - 1, 3);
		}
	}
	return;
}
function updateClick() {
	fm.OperateType.value = "UPDATE";
	if (fm.State.value == '01') {
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RIFeeRateBatchInputSql3");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.FeeTableNo.value);// ָ������Ĳ������������˳�����
		var strSQL = mySql1.getString();
		var tState = easyExecSql(strSQL);
		if (tState == '02' || tState == '' || tState == null) {
			myAlert(""+"���ʱ�״̬Ϊ��Ч������״̬����Ϊ��Ч��"+"");
			return false;
		}
	}
	try {
		if (verifyInput()) {
			if (veriryInput3()) {
				var row = FeeRateBatchGrid.lastFocusRowNo;
				if (row < FeeRateBatchGrid.mulLineCount - 1
						&& fm.LapseDate.value == "") {
					if (FeeRateBatchGrid.getRowColData(row + 1, 1) != ""
							|| FeeRateBatchGrid.getRowColData(row + 1, 1) != null) {
						myAlert(""+"������һ�����κţ����ܽ����ʱ�ʧЧ������գ�"+"");
						return false;
					}
				}
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action = "./RIFeeRateBatchSave.jsp";
				fm.submit(); // �ύ
			}
		}
		fm.feeBatchNo.readOnly = "";
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function deleteClick() {
	fm.OperateType.value = "DELETE";
	if (!confirm(""+"��ȷ��Ҫɾ���÷��ʱ�������"+"")) {
		return false;
	}
	if (veriryInput3()) {
		try {
			var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIFeeRateBatchSave.jsp";
			fm.submit(); // �ύ
		} catch (ex) {
			showInfo.close();
			myAlert(ex);
		}
	}
}

function veriryInput3() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeRateBatchInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.FeeTableNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.BatchNo.value);// ָ������Ĳ������������˳�����
	var sql = mySql.getString();
	var result = easyExecSql(sql);
	if (result == null) {
		myAlert(""+"���ʱ����α�Ų����ڣ�"+"");
		return false;
	}
	return true;
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		queryFeeRateBatch();
		if (fm.OperateType.value == "DELETE") {
			inputFeeRateBatch();
		}
	}
}

function ShowBatch() {
	var tSel = FeeRateBatchGrid.getSelNo();
	fm.BatchNo.value = FeeRateBatchGrid.getRowColData(tSel - 1, 3);
	fm.InureDate.value = FeeRateBatchGrid.getRowColData(tSel - 1, 4);
	fm.LapseDate.value = FeeRateBatchGrid.getRowColData(tSel - 1, 5);
	fm.SaveDataName.value = FeeRateBatchGrid.getRowColData(tSel - 1, 6);
	fm.State.value = FeeRateBatchGrid.getRowColData(tSel - 1, 8);
	fm.stateName.value = FeeRateBatchGrid.getRowColData(tSel - 1, 9);
	fm.SaveDataNameName.value = FeeRateBatchGrid.getRowColData(tSel - 1, 10);
	fm.feeBatchNo.readOnly = "true";
}

function inputFeeRateBatch() {
	fm.all('BatchNo').value = '';
	fm.all('InureDate').value = '';
	fm.all('LapseDate').value = '';
	fm.SaveDataName.value = "RIFeeRateTable";
	fm.SaveDataNameName.value = ""+"ͨ�÷��ʱ�"+"";
	fm.State.value = "02";
	fm.stateName.value = ""+"δ��Ч"+"";
	fm.feeBatchNo.readOnly = "";
	fmImport.reset();
}
function FeeRateTableImp() {
	if (fmImport.FileName.value == "" || fmImport.FileName.value == null) {
		myAlert(""+"¼�뵼���ļ�·����"+"");
		return false;
	}
	getImportPath();
	ImportFile = fmImport.all('FileName').value;

	var showStr = ""+"�����������ݡ���"+"";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+showStr;// encodeURI(encodeURI(showStr));
	showInfo = window.showModelessDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fmImport.action = "./RIFeeRateImportSave.jsp?ImportPath=" + ImportPath
			+ "&FeeTableNo=" + fm.FeeTableNo.value + "&BatchNo="
			+ fm.BatchNo.value;
	fmImport.submit(); // �ύ
}

function getImportPath() {
	var strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);

	// �ж��Ƿ��ѯ�ɹ�
	var result = easyExecSql(strSQL);
	if (result == null) {
		myAlert(""+"δ�ҵ��ϴ�·��"+"");
		return false;
	}
	ImportPath = result[0][0];
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
function alink() {
	window.location.href = "../temp/reinsure/feerateimp/LRFeeRateImport.xls";
}
