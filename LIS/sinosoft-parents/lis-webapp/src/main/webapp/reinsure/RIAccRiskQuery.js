//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "reinsure.RIAccRiskQuerySql";
// �ύ�����水ť��Ӧ����
function submitForm() {
}

function accQuery() {
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIAccRiskQuerySql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateNo.value);// ָ������Ĳ������������˳�����
	var tSQL = mySql.getString();
	turnPage.queryModal(tSQL, AccumulateGrid);
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {

	// showInfo.close();
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	}
}

// ���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert(""+"��Proposal.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
	}
}

function ClosePage() {
	top.close();
}
