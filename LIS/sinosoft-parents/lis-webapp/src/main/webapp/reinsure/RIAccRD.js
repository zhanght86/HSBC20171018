//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIAccRD.js
//�����ܣ��ֳ����ζ���
//�������ڣ�2011/6/16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var sqlresourcename = "reinsure.RIAccRDInputSql";
window.onfocus = myonfocus;
var turnPage = new turnPageClass();

function veriryInput() {
	var rowNum = RIAccRiskDutyGrid.mulLineCount; // ����
	for ( var i = 0; i < rowNum; i++) {
		num = i + 1;

		if (RIAccRiskDutyGrid.getRowColData(i, 1) == ""
				|| RIAccRiskDutyGrid.getRowColData(i, 1) == null) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"���ִ���¼��Ϊ�գ�"+"");
			return false;
		}
		if (RIAccRiskDutyGrid.getRowColData(i, 3) == ""
				|| RIAccRiskDutyGrid.getRowColData(i, 3) == null) {
			myAlert(""+"��"+"" + num + ""+"��"+","+"���δ���¼��Ϊ�գ�"+"");
			return false;
		}

	}
	for ( var n = 0; n < RIAccRiskDutyGrid.mulLineCount; n++) {
		for ( var m = n + 1; m < RIAccRiskDutyGrid.mulLineCount; m++) {
			if (RIAccRiskDutyGrid.getRowColData(n, 1) == RIAccRiskDutyGrid
					.getRowColData(m, 1)) {
				myAlert(""+"����¼���ظ����ִ���!"+"");
				return false;
			}
		}
	}
	return true;

}
// ���Ӱ�ť
function submitForm() {
	if (existAccumulateDefNO()) {
		myAlert(""+"�ֳ����α����Ѵ��ڣ����������룡"+"");
		return false;
	}
	fm.OperateType.value = "INSERT";
	try {
		if (verifyInput()) {
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIAccRDSave.jsp";
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
// ���ֳ����α���
function existAccumulateDefNO() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();

	var arrResult = easyExecSql(strSQL);
	if (arrResult == null) {
		fm.CalOrder.value = "02";
		return false;
	}
	fm.CalOrder.value = arrResult[0][0];
	return true;
}
// �޸İ�ť
function updateClick() {
	if (!existAccumulateDefNO()) {
		myAlert(""+"�ֳ����α��벻���ڣ����������룡"+"");
		return false;
	}
	fm.OperateType.value = "UPDATE";
	try {
		if (verifyInput()) {
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIAccRDSave.jsp";
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

// ��ѯ��ť
function queryClick() {
	var url = "RIAccRDQueryInput.jsp";
	var param = "";
	window.open("./FrameMainCommon.jsp?Interface=" + url + param, "true");
}

// ɾ����ť
function deleteClick() {
	if (!existAccumulateDefNO()) {
		myAlert(""+"�ֳ����α��벻���ڣ����������룡"+"");
		return false;
	}
	if (!confirm(""+"��ȷ��Ҫɾ����������"+"")) {
		return false;
	}
	fm.OperateType.value = "DELETE";
	try {
		var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

		fm.action = "./RIAccRDSave.jsp";
		fm.submit(); // �ύ
		fm.reset();
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

// �������ö���
function button99() {
	var url = "RIAccDistillInput.jsp";
	var param = "&AccNo=" + fm.AccumulateDefNO.value + "&AccName="
			+ fm.AccumulateDefName.value;

	window.open("./FrameMainDistill.jsp?Interface=" + url + param, "true");
}

// �ֱ����Զ��尴ť
function button100() {
	var tRow = RIAccRiskDutyGrid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"�����Ƚ���ѡ��!"+"");
		return;
	}

	var url = "RIAccFeaturesInput.jsp";
	var param = "&AccNo=" + fm.AccumulateDefNO.value + "&AccName="
			+ fm.AccumulateDefName.value + "&RiskCode="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 1) + "&RiskName="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 2) + "&DutyCode="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 3) + "&DutyName="
			+ RIAccRiskDutyGrid.getRowColData(tRow - 1, 4);

	window.open("./FrameMainAccumu.jsp?Interface=" + url + param, "true");
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+content;// encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		// ִ����һ������
		if (fm.OperateType.value == "DELETE") {
			fm.reset();
		}
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "riacctype") {
		if (Field.value == "01") {
			fm.cacctype.style.display = '';
			divCode1.style.display = "none";
			divName1.style.display = "none";

			fm.caccspec.style.display = 'none';
			fm.RIRiskFeature.value = '';
			fm.RIRiskFeatureName.value = '';
		} else {
			fm.cacctype.style.display = 'none';
			divCode1.style.display = "";
			divName1.style.display = "";

			fm.RIRiskFeature.value = '00';
			fm.RIRiskFeatureName.value = ''+"�����ۼƷֱ�"+'';
		}
	}
	if (codeName == "ririskfeature") {
		if (Field.value == "01") {
			fm.caccspec.style.display = '';
		} else {
			fm.caccspec.style.display = 'none';
		}
	}

}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
function nextStep() {
	if (fm.AccumulateDefNO.value == "") {
		myAlert(""+"���Ȳ�ѯ"+"");
		return;
	}
	var AccumulateDefNO = fm.all('AccumulateDefNO').value;
	var varSrc = "&AccumulateDefNO=" + AccumulateDefNO;
	window.open("./FrameMainCessInfo.jsp?Interface=RIAccRDDetailInput.jsp"
			+ varSrc, "true"); // +varSrc,
}
