var showInfo;
var mDebug = "0";
var DealWithNam;
var sqlresourcename = "reinsure.RIItemCalDetailInputSql";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
// �㷨�����ѯ
function queryClick() {
	var tRow = KResultGrid.getSelNo();
	if (tRow > 0) {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIItemCalDetailInputSql1");//ָ��ʹ�õ�Sql��id
		mySql.addSubPara(KResultGrid.getRowColData(tRow - 1, 1));// ָ������Ĳ������������˳�����
		mySql.addSubPara(KResultGrid.getRowColData(tRow - 1, 2));// ָ������Ĳ������������˳�����
		mySql.addSubPara(KResultGrid.getRowColData(tRow - 1, 9));// ָ������Ĳ������������˳�����
		mySql.addSubPara(KResultGrid.getRowColData(tRow - 1, 10));// ָ������Ĳ������������˳�����
		var strSQL = mySql.getString();

		var rs = easyExecSql(strSQL);
		fm.KArithmeticDefID.value = rs[0][0];
		fm.KArithmeticDefName.value = rs[0][1];
		fm.ArithmeticType.value = rs[0][2];
		fm.ArithmeticTypeName.value = rs[0][3];
		fm.Business.value = rs[0][4];
		fm.BusinessName.value = rs[0][5];
		fm.CalItemID.value = rs[0][14];
		fm.CalItemIDName.value = rs[0][7];
		fm.ItemCalType.value = rs[0][8];
		fm.ItemCalTypeName.value = rs[0][9];
		fm.Remark.value = rs[0][10];
		fm.DistillSQL.value = rs[0][11];
		fm.DistillClass.value = rs[0][12];
		fm.DoubleValue.value = rs[0][13];
		fm.CalItemOrder.value = KResultGrid.getRowColData(tRow - 1, 10);
		if (fm.ItemCalType.value == "1") {
			classdiv.style.display = 'none';
			sqldiv.style.display = 'none';
			numdiv.style.display = '';
			fm.all('DistillClass').value = '';
			fm.all('DistillSQL').value = '';
		} else if (fm.ItemCalType.value == "2") {
			classdiv.style.display = 'none';
			numdiv.style.display = 'none';
			sqldiv.style.display = '';
			fm.all('DistillClass').value = '';
			fm.all('DoubleValue').value = '';
		} else if (fm.ItemCalType.value == "3") {
			classdiv.style.display = '';
			sqldiv.style.display = 'none';
			numdiv.style.display = 'none';
			fm.all('DistillSQL').value = '';
			fm.all('DoubleValue').value = '';
		} else if (fm.ItemCalType.value == null || fm.ItemCalType.value == '') {
			classdiv.style.display = 'none';
			sqldiv.style.display = 'none';
			numdiv.style.display = 'none';
			fm.all('DistillSQL').value = '';
			fm.all('DoubleValue').value = '';
			fm.all('DistillClass').value = '';
		}
	}

}

function getDetail() {
	if (fm.NewArithmeticType.value == '' || fm.NewArithmeticType.value == null) {
		myAlert(""+"��ϸ�㷨���Ͳ���Ϊ�գ�"+"");
		return;
	}
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIItemCalDetailInputSql2");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.NEWArithmeticDefID.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.NewArithmeticType.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();
	turnPage.queryModal(strSQL, KResultGrid);
}

// �㷨��������
function submitForm() {
	if (verifyInput()) {
		var mySql = new SqlClass();
		mySql.setResourceName(sqlresourcename);
		mySql.setSqlId("RIItemCalDetailInputSql3");//ָ��ʹ�õ�Sql��id
		mySql.addSubPara(fm.NEWArithmeticDefID.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.KArithmeticDefID.value);// ָ������Ĳ������������˳�����
		mySql.addSubPara(fm.CalItemOrder.value);// ָ������Ĳ������������˳�����
		var sql = mySql.getString();
		var rs = easyExecSql(sql);
		if (rs != null) {
			myAlert(""+"����������Ѵ��ڣ����������룡"+"");
			return;
		}
		fm.OperateType.value = "INSERT";
		var showStr = ""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action = "./RIItemCalDetailSave.jsp";
		fm.submit();
	}
}

// �㷨�����޸�
function updateClick() {
	if (verifyInput()) {
		fm.OperateType.value = "UPDATE";
		var showStr = ""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		// lockButton();
		fm.action = "./RIItemCalDetailSave.jsp";
		fm.submit();
	}
}
// �㷨����ɾ��
function deleteClick() {
	if (verifyInput()) {
		fm.OperateType.value = "DELETE";
		var showStr = ""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo = window
				.showModelessDialog(urlStr, window,
						"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action = "./RIItemCalDetailSave.jsp";
		fm.submit();

	}
}

function afterSubmit(FlagStr, content) {
	showInfo.close();

	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		mOperate = "";
	} else {
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr, window,
				"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	
	if(	fm.OperateType.value == "DELETE"){
		newClick();
	}
}

function afterCodeSelect(codeName, Field) {
	if (codeName == "riitemcaltype") {
		if (Field.value == "1") {
			classdiv.style.display = 'none';
			sqldiv.style.display = 'none';
			numdiv.style.display = '';
			fm.all('DistillClass').value = '';
			fm.all('DistillSQL').value = '';
		} else if (Field.value == "2") {
			classdiv.style.display = 'none';
			numdiv.style.display = 'none';
			sqldiv.style.display = '';
			fm.all('DistillClass').value = '';
			fm.all('DoubleValue').value = '';
		} else if (Field.value == "3") {
			classdiv.style.display = '';
			sqldiv.style.display = 'none';
			numdiv.style.display = 'none';
			fm.all('DistillSQL').value = '';
			fm.all('DoubleValue').value = '';
		} else if (Field.value == null || Field.value == '') {
			classdiv.style.display = 'none';
			sqldiv.style.display = 'none';
			numdiv.style.display = 'none';
			fm.all('DistillSQL').value = '';
			fm.all('DoubleValue').value = '';
			fm.all('DistillClass').value = '';
		}
	}
	if (codeName == "arithtype") {
		fm.KArithmeticDefID.value = fm.ArithmeticDefID.value
				+ fm.ArithmeticType.value;
	}
}
function newClick() {
	fm.KArithmeticDefID.value = "";
	fm.KArithmeticDefName.value = "";
	fm.ArithmeticType.value = "";
	fm.ArithmeticTypeName.value = "";
	fm.Business.value = "";
	fm.BusinessName.value = "";
	fm.CalItemID.value = "";
	fm.CalItemIDName.value = "";
	fm.ItemCalType.value = "";
	fm.ItemCalTypeName.value = "";
	fm.Remark.value = "";
	fm.DistillSQL.value = "";
	fm.DistillClass.value = "";
	fm.DoubleValue.value = "";
}
