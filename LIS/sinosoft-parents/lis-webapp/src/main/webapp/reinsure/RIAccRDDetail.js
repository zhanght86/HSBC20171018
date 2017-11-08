var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIAccRDDetailInputSql";
function queryLink() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDDetailInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, RIAccRiskDutyGrid);
}
function showRiskDuty() {
	var tSel = RIAccRiskDutyGrid.getSelNo();
	fm.all('AssociatedCode').value = RIAccRiskDutyGrid.getRowColData(tSel - 1,1);
	fm.all('AssociatedName').value = RIAccRiskDutyGrid.getRowColData(tSel - 1,2);
	//IE11-��Сд
	fm.all('GetDutyCode').value = RIAccRiskDutyGrid.getRowColData(tSel - 1, 3);
	//IE11-��Сд
	fm.all('GetDutyName').value = RIAccRiskDutyGrid.getRowColData(tSel - 1, 4);
}
function addClick() {
	fm.OperateType.value = "ADD";
	try {
		if (verifyInput()) {
			var mySql = new SqlClass();
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIAccRDDetailInputSql2");// ָ��ʹ�õ�Sql��id
			mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.AssociatedCode.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.GetDutyCode.value);// ָ������Ĳ������������˳�����
			var sql = mySql.getString();
			var rs = easyExecSql(sql);
			if (rs != null) {
				myAlert(""+"�������Ѿ����ڣ����������룡"+"");
				return;
			}
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIAccRDDetailSave.jsp";
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}
function deleteClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRDDetailInputSql2");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.AssociatedCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.GetDutyCode.value);// ָ������Ĳ������������˳�����
	var sql = mySql.getString();
	var rs = easyExecSql(sql);
	if (rs == null) {
		myAlert(""+"�����β����ڣ����������룡"+"");
		return; 
	}
	if (!confirm(""+"��ȷ��Ҫɾ����"+"")) {
		return false;
	}
	fm.OperateType.value = "DEL";
	try {
		if (verifyInput()) {
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIAccRDDetailSave.jsp";
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}
function updateClick() {
	fm.OperateType.value = "UPD";
	try {
		var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action = "./RIAccRDDetailSave.jsp";
		fm.submit(); // �ύ
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
	return true;
}

function resetAcc() {
	fm.AssociatedCode.value = "";
	fm.AssociatedName.value = "";
	fm.GetDutyCode.value = "";
	fm.GetDutyName.value = "";
}
function closePage() {
	top.close();
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
		queryLink();
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "rilmrisk") {
		fm.GetDutyCode.value="";
		fm.GetDutyName.value="";
	}
}
