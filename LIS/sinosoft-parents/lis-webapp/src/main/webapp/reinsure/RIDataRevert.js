//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIDataRevert.js
//�����ܣ����ݻع�
//�������ڣ�2011-07-30
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIDataRevertInputSql";
var str = getCurrentDate();
// ��ѯ��ť
function button130() {
	if (verifyInput() && verifyInput1()) {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);	
		mySql.setSqlId("RIDataRevertInputSql1");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
		var strSQL = mySql.getString();
		var arrResult = easyExecSql(strSQL);
		var tBFFlag = "";
		//IE11-Null
		if(arrResult!=null){
			tBFFlag = arrResult[0][0];
		}
		mySql = new SqlClass();
		if(tBFFlag==""){
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIDataRevertInputSql2");// ָ��ʹ�õ�Sql��id
			mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.InsuredNo.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(str);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.RiskCode.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.StartDate1.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.EndDate1.value);// ָ������Ĳ������������˳�����
			
			var tSQL = mySql.getString();
			turnPage.queryModal(tSQL, Mul11Grid);
		}else{
			mySql.setResourceName(sqlresourcename);
			mySql.setSqlId("RIDataRevertInputSql3");// ָ��ʹ�õ�Sql��id
			mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(str);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.InsuredNo.value);// ָ������Ĳ������������˳�����
			
			
			mySql.addSubPara(fm.RiskCode.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.StartDate1.value);// ָ������Ĳ������������˳�����
			mySql.addSubPara(fm.EndDate1.value);// ָ������Ĳ������������˳�����
			
			var tSQL = mySql.getString();
			turnPage.queryModal(tSQL, Mul11Grid);
		}
		
	}
}

function verifyInput1() {
	if (fm.StartDate.value > str) {
		myAlert(""+I18NMsg(/*��ʼ���ڲ��ܴ��ڵ���!*/"L0000014322")+"");
		return false;
	}
	return true;
}

// ���ݻع���ť
function button131() {
	fm.OperateType.value = "REVERT";
	
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);	
	mySql.setSqlId("RIDataRevertInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	var tBFFlag = "";
	//IE11-Null
	if(arrResult!=null){
		tBFFlag = arrResult[0][0];
	}
	mySql = new SqlClass();
	if(tBFFlag==""){
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIDataRevertInputSql4");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.InsuredNo.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(str);// ָ������Ĳ������������˳�����
		
		
		mySql.addSubPara(fm.RiskCode.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.StartDate1.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.EndDate1.value);// ָ������Ĳ������������˳�����
	}else{
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIDataRevertInputSql5");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(str);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.InsuredNo.value);// ָ������Ĳ������������˳�����
		
		
		mySql.addSubPara(fm.RiskCode.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.StartDate1.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.EndDate1.value);// ָ������Ĳ������������˳�����
	}

	var strSQL = mySql.getString();
	temp = easyExecSql(strSQL);
	
	if (temp == null) {
		myAlert(""+I18NMsg(/*�ع�����Ϊ�գ����Ȳ�ѯ��*/"L0000014323")+"");
		return false;
	}
	try {
		if (verifyInput() && verifyInput1()) {
			var showStr = ""+I18NMsg(/*���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��*/"M0000050069")+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIDataRevertSave.jsp";
			fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
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

		initForm();
		// ִ����һ������
	}
}

function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
