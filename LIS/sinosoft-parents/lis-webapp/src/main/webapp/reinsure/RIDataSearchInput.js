//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug = "0";
var DealWithNam;
var turnPage = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass(); // ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var eventType;
var sqlresourcename = "reinsure.RIDataSearchInputSql";
function queryClick() {
	dataQuery();

}

function dataQuery() {
	if (!verifyInput()) {
		return false;
	}
	// var tSQL = "select a.nodestate from riwflog a where a.batchno='" +
	// fm.BatchNo.value+"'";
	// var result = easyExecSql(tSQL);
	// if(result[0][0]=='00')
	// {
	// alert("������û������");
	// return false;
	// }
	// else
	// if(result[0][0]=='99')
	// {
	// tSQL = "select a.contno,a.insuredno,a.insuredname,(case when
	// a.ripreceptno is null then '' else a.ripreceptno end),(case when
	// a.accamnt is null then 0 else a.accamnt end) from ripolrecordbake a where
	// a.batchno='"+fm.BatchNo.value+"' ";
	// }else
	// {
	// tSQL = "select a.contno,a.insuredno,a.insuredname,(case when
	// a.ripreceptno is null then '' else a.ripreceptno end),(case when
	// a.accamnt is null then 0 else a.accamnt end) from ripolrecord a where
	// a.batchno='"+fm.BatchNo.value+"' ";
	// }
	// alert(111);

	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIDataSearchInputSql1");// ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.AccumulateDefNO.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.InsuredNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.InsuredName.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.EventType.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.BFFlag.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIContType.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.EndDate.value);// ָ������Ĳ������������˳�����
	var d = new Date();
	var years = "2017";
	var month = d.getMonth()+1;
	var days = d.getDate();
	var ndate = years+"-"+month+"-"+days;
	mySql.addSubPara(ndate);
	var tSQL = mySql.getString();
	RIDataDetailGrid.clearData();
	result = easyExecSql(tSQL);
	if (result == null) {
		RIDataGrid.clearData();
		myAlert(""+"��ѯ���Ϊ��"+"");
		return false;
	}
	turnPage1.queryModal(tSQL, RIDataGrid);
}

// �ֱ���ϸ���ݲ�ѯ
function queryDetail() {
	var num = RIDataGrid.getSelNo();
	var EventNo = RIDataGrid.getRowColData(num - 1, 1);
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	if (RIDataGrid.getRowColData(num - 1, 11) == 'L01010060010') {
		mySql.setSqlId("RIDataSearchInputSql3");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(EventNo);// ָ������Ĳ������������˳�����

		var tSQL = mySql.getString();
		turnPage2.queryModal(tSQL, RIDataUCoDetailGrid);
		RIDataDetailGrid.clearData();
		divUCoDetail.style.display = "";
		divCessDetail.style.display = "none";
	} else {
		mySql.setSqlId("RIDataSearchInputSql2");// ָ��ʹ�õ�Sql��id
		mySql.addSubPara(EventNo);// ָ������Ĳ������������˳�����

		var tSQL = mySql.getString();
		turnPage2.queryModal(tSQL, RIDataDetailGrid);
		RIDataUCoDetailGrid.clearData();
		divUCoDetail.style.display = "none";
		divCessDetail.style.display = "";
	}
}

function verifyInput1() {

	return true;
}

function resetPage() {
	fm.reset();
	fm.all("BFFlag").value = "01";
	fm.all("BFFlagName").value = ""+"ҵ����"+"";
	RIDataGrid.clearData();
	RIDataUCoDetailGrid.clearData();
	RIDataDetailGrid.clearData();
}

// ��ϸ�ֱ���������
function DownLoadExcel() {
	fm.OperateType.value = "DOWNLOAD";

	fm.target = "fraSubmit";
	fm.action = "./RIDataSearchSave.jsp";
	fm.submit(); // �ύ

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
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "riaccmucode") {
		RIDataGrid.clearData();
		RIDataUCoDetailGrid.clearData();
		RIDataDetailGrid.clearData();
		divUCoDetail.style.display = "none";
		divCessDetail.style.display = "none";
	}
}
