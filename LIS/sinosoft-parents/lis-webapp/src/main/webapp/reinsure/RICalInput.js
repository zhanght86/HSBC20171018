//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug = "0";
var sqlresourcename = "reinsure.RICalInputSql";
var Action;
var tRowNo = 0;
var turnPage = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var currentTask;

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
	}
	initForm();
}

function initTaskList() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RICalInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara("");// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	arrResult = easyExecSql(strSQL);
	if (arrResult == '0') {
		currentTask = '0';
		var mySql1 = new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId("RICalInputSql2");// ָ��ʹ�õ�Sql��id
		mySql1.addSubPara("");// ָ������Ĳ������������˳�����
		var strSQL = mySql1.getString();
		arrResult = easyExecSql(strSQL);
	} else {
		currentTask = '1';
		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RICalInputSql3");// ָ��ʹ�õ�Sql��id
		mySql2.addSubPara("");// ָ������Ĳ������������˳�����
		var strSQL = mySql2.getString();
		arrResult = easyExecSql(strSQL);
		fm.StartDate.value = arrResult[0][2];
		fm.EndDate.value = arrResult[0][3];
	}
	
	TaskListGrid.clearData();
	if (arrResult != null) {
		for ( var k = 0; k < arrResult.length; k++) {
			TaskListGrid.addOne("TaskListGrid");
			TaskListGrid.setRowColData(k, 1, arrResult[k][0]);
			TaskListGrid.setRowColData(k, 2, arrResult[k][1]);
			TaskListGrid.setRowColData(k, 3, arrResult[k][2]);
			TaskListGrid.setRowColData(k, 4, arrResult[k][3]);
			TaskListGrid.setRowColData(k, 5, arrResult[k][4]);
			TaskListGrid.setRowColData(k, 6, arrResult[k][5]);
		}
	}

	return true;
}

function SubmitForm() {
	if (!verifyInput()) {
		return false;
	}
	if (!verifyInput1()) {
		return false;
	}
	if (fm.ProcessType.value == '00') {
		if (!taskApply()) {
			return false;
		}
	}
	if (fm.ProcessType.value == '01') {
		if (!distillData()) {
			return false;
		}
	}
	if (fm.ProcessType.value == '02') {
		if (!checkData()) {
			return false;
		}
	}
	if (fm.ProcessType.value == '03') {
		if (!calData()) {
			return false;
		}
	}
	if (fm.ProcessType.value == '98') {
		if (!delTask()) {
			return false;
		}
	}
}

function taskApply() {
	if (currentTask == '1') {
		myAlert(""+"�Բ��𡣵�ǰ����δ��ɣ����������´�������"+"");
		return false;
	}

	if (fm.StartDate.value == "" || fm.EndDate.value == "") {
		return false;
		myAlert(""+"��¼����ʼ���ں���ֹ����!"+"");
	}
	var selCount = TaskListGrid.mulLineCount;
	var chkFlag = false;

	for (i = 0; i < selCount; i++) {
		if (TaskListGrid.getChkNo(i) == true) {
			chkFlag = true;
			if (TaskListGrid.getRowColData(i, 6) >= '01') {
				myAlert(""+"��ѡ����ȫ�����й�������ȡ����"+","+"�����ظ���ȡ��"+"");
				return false;
			}
		}
	}
	if (chkFlag == false) {
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}

	try {
		var i = 0;
		var showStr = ""+"���ڽ��������������	�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.submit();

	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function distillData() {
	if (currentTask == '0') {
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ�������������"+"");
		return false;
	}
	var selCount = TaskListGrid.mulLineCount;
	var chkFlag = false;
	for (i = 0; i < selCount; i++) {
		if (TaskListGrid.getChkNo(i) == true) {
			chkFlag = true;
			if (TaskListGrid.getRowColData(i, 6) >= '01') {
				myAlert(""+"��ѡ����ȫ�����й�������ȡ����"+","+"�����ظ���ȡ��"+"");
				return false;
			}
		}
	}
	if (chkFlag == false) {
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	try {
		var i = 0;
		var showStr = ""+"���ڽ���ҵ��������ȡ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.submit();

	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}

}

function checkData() {
	if (currentTask == '0') {
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ�������У�������"+"");
		return false;
	}
	var selCount = TaskListGrid.mulLineCount;
	var chkFlag = false;
	for (i = 0; i < selCount; i++) {
		if (TaskListGrid.getChkNo(i) == true) {
			chkFlag = true;
		}
	}
	if (chkFlag == false) {
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	try {
		var i = 0;
		var showStr = ""+"���ڽ�������У�飬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.submit();

	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function calData() {
	if (currentTask == '0') {
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ�������У�������"+"");
		return false;
	}
	var chkFlag = false;
	var selCount = TaskListGrid.mulLineCount;
	for (i = 0; i < selCount; i++) {
		if (TaskListGrid.getChkNo(i) == true) {
			chkFlag = true;
			if (TaskListGrid.getRowColData(i, 6) < '01') {
				myAlert(""+"����ȡҵ�����ݺ��ٽ����ٱ����㡣"+"");
				return false;
			}
		}
	}
	if (chkFlag == false) {
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	try {
		var i = 0;
		var showStr = ""+"���ڽ����ٱ����㣬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+showStr;// encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.submit();

	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

function delTask() {
	if (currentTask == '0') {
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ���ɾ��������"+"");
		return false;
	}
	var chkFlag = false;
	var selCount = TaskListGrid.mulLineCount;
	for (i = 0; i < selCount; i++) {
		if (TaskListGrid.getChkNo(i) == true) {
			chkFlag = true;
		}
	}
	if (chkFlag == false) {
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	if (confirm(""+"��ɾ��ѡ�е�������־�����и������ҵ�����ݣ�ȷ����?"+"")) {
		try {
			var i = 0;
			var showStr = ""+"���ڽ�������ɾ�������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.submit();
		} catch (ex) {
			showInfo.close();
			myAlert(ex);
		}
	}
}

function verifyInput1() // UPDATE У��
{ // alert(currentDate);
	if (compareDate(fm.StartDate.value, fm.EndDate.value) == 1) {
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}
	if (compareDate(fm.EndDate.value, currentDate) == 1) {
		myAlert(""+"��ֹ���ڲ��ܴ��ڵ�ǰ���ڣ�"+"");
		return false;
	}
	return true;

}
