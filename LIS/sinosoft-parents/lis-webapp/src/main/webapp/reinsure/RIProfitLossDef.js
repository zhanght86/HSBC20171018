//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIProfitLossDef.js
//�����ܣ�ӯ��Ӷ����
//�������ڣ�2011/8/18
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIProfitLossDefInputSql";

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
// �ύ�����水ť��Ӧ����
function submitForm() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	var strSql = mySql.getString();
	var strQueryResult = easyExecSql(strSql);
	if (strQueryResult!=null) {
		myAlert(""+"ӯ��Ӷ�����Ѵ��ڣ�"+"");
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

				fm.action = "./RIProfitLossDefSave.jsp";
				fm.submit(); // �ύ
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
// �޸�
function updateClick() {
	fm.OperateType.value = "UPDATE";
	try {
		if (verifyInput() == true) {

			{				
				var mySql = new SqlClass();
				mySql.setResourceName(sqlresourcename);
				mySql.setSqlId("RIProfitLossDefInputSql2");// ָ��ʹ�õ�Sql��id
				mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
				var strSQL = mySql.getString();
				var arr= easyExecSql(strSQL,1,0,1 ); //!!ÿ�γ���easyExeSql()��ѯ��Ҫ�ж��Ƿ�Ϊ��,��:
				if(arr==null)
				{
					myAlert(""+"û�з�������������"+"");
					return;
				}				
				var num=Mul9Grid.mulLineCount;
				if(num==''||num==null||num==0){
					
					myAlert(""+"��������Ϊ�գ�"+"");
					return false;
				}
				var mySql1 = new SqlClass();
				mySql1.setResourceName(sqlresourcename);
				mySql1.setSqlId("RIProfitLossDefInputSql3");// ָ��ʹ�õ�Sql��id
				mySql1.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
				var SQL = mySql1.getString();
				var arr1= easyExecSql(SQL,1,0,1 ); //!!ÿ�γ���easyExeSql()��ѯ��Ҫ�ж��Ƿ�Ϊ��,��:
				
				
				
				
				
				var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
				var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
						+showStr;// encodeURI(encodeURI(showStr));
				showInfo = window
						.showModelessDialog(urlStr, window,
								"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

				fm.action = "./RIProfitLossDefSave.jsp";
				fm.submit(); // �ύ
			}
		}
	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
// ɾ��
function deleteClick() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossDefInputSql4");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	
	

	var arr= easyExecSql( strSQL,1,0,1 ); //!!ÿ�γ���easyExeSql()��ѯ��Ҫ�ж��Ƿ�Ϊ��,��:
	if(arr==null)
	{
		myAlert(""+"û�з�������������"+"");
		return;
	}
	fm.OperateType.value = "DELETE";
	if (!confirm(""+"��ȷ��Ҫɾ����������"+"")) {
		return false;
	}
	try {
		if (verifyInput() == true) {

			var i = 0;
			var showStr = ""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
			var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
					+showStr;// encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action = "./RIProfitLossDefSave.jsp";
			fm.submit(); // �ύ
		}

	} catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}
function queryClick() {
	var url = "RIProfitLossDefQueryInput.jsp";
	var param = "";
	window.open("./FrameMainCommon.jsp?Interface=" + url + param, "true");
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
		showDiv(operateButton, "true");
		showDiv(inputButton, "false");
		// ִ����һ������
		if (fm.OperateType.value == "DELETE") {
			initForm();
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
