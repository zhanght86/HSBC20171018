//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIProfitLossUW.js
//�����ܣ�ӯ��Ӷ�����
//�������ڣ�2011/8/18
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus = myonfocus;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIProfitLossUWInputSql";
function verifyInput() {
	var tRow = Mul9Grid.getSelNo();
	if (tRow == 0) {
		myAlert(""+"�����Ƚ���ѡ���¼!"+"");
		return;
	}
	if (fm.RILossUWReport.value == "" || fm.RILossUWReport.value == null) {
		myAlert(""+"����ʧ�ܣ�δ�´���˽���"+"");
		return false;
	}

	return true;
}
function verify() {

	if (fm.CalYear.value == "" || fm.CalYear.value == null) {
		myAlert(""+"��Ȳ���Ϊ��"+"");
		return false;
	}
	if (!isNumeric(fm.CalYear.value)) {
		myAlert(""+"��ȸ�ʽ����"+"");
		return false;
	}
	if (fm.RIProfitNo.value == "" || fm.RIProfitNo.value == null) {
		myAlert(""+"���������Ѳ���Ϊ��"+"");
		return false;
	}

	return true;
}

function queryClick() {
	if (verify()) {
				
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossUWInputSql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	/*mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	*/
	var strSQL = mySql.getString();	
	var arr= easyExecSql( strSQL,1,0,1 ); //!!ÿ�γ���easyExeSql()��ѯ��Ҫ�ж��Ƿ�Ϊ��,��:
	if(arr==null)
	{
		myAlert(""+"û�з�������������"+"");
		return;
	}			
		turnPage.queryModal(strSQL, Mul9Grid);
	}

}

function queryClickDetail() {
	var Row = Mul9Grid.getSelNo();

	var riprofitno = Mul9Grid.getRowColData(Row - 1, 1);
	var currency = Mul9Grid.getRowColData(Row - 1, 8);
			
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIProfitLossUWInputSql2");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(riprofitno);// ָ������Ĳ������������˳�����
	mySql.addSubPara(currency);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIProfitNo.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.CalYear.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.RIcomCode.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.ContNo.value);// ָ������Ĳ������������˳�����
	var SQL = mySql.getString();			
	temp = easyExecSql(SQL);

	Mul10Grid.clearData();
	for (var i = 0; i < temp.length; i++) {   
			
	Mul10Grid.addOne();
	Mul10Grid.setRowColData(i, 1, temp[i][0]);
	Mul10Grid.setRowColData(i, 2,temp[i][1]);
	Mul10Grid.setRowColData(i, 3,temp[i][2]);
	Mul10Grid.setRowColData(i, 4, temp[i][3]);
	Mul10Grid.setRowColData(i, 5, temp[i][4]);
	Mul10Grid.setRowColData(i, 6, temp[i][5]);
	Mul10Grid.setRowColData(i, 7, temp[i][6]);
	Mul10Grid.setRowColData(i, 8, temp[i][7]);
	
	}
	
	
}

// ���۱��水ť
function button135() {
	fm.OperateType.value = "UPDATE";
	try {
		if (verifyInput()) {
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

			fm.action = "./RIProfitLossUWSave.jsp";
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
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

		// var strSQL="select a.riprofitno,a.calyear,a.recomcode,(select
		// c.companyname from RIComInfo c where remark = '02' and c.companyno =
		// a.ReComCode),a.ricontno,(select b.RIContName from RIBarGainInfo b
		// where b.RIContNo = a.RIContNo),a.prolosamnt,
		// a.currency,decode(a.state,'02','�޸�','03','ȷ��') from RIProLossResult a
		// where a.state = '02' order by a.calyear";

		// Mul9GridTurnPage.queryModal(strSQL, Mul9Grid);
		divDetail.style.display = "none";
		// ִ����һ������
		Mul9Grid.clearData("Mul9Grid");
		fm.CalYear.value = "";
		fm.RILossUWReport.value = "";
		fm.RILossUWReportName.value = "";
		initForm();
	}
}
function afterCodeSelect(codeName, Field) {
	if (codeName == "riprofityear") {
		Mul9Grid.clearData();
		Mul10Grid.clearData();
	}
}
function showDiv(cDiv, cShow) {
	if (cShow == "true") {
		cDiv.style.display = "";
	} else {
		cDiv.style.display = "none";
	}
}
