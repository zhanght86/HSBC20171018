// ���ļ��а����ͻ�����Ҫ����ĺ������¼�
// window.onfocus=myonfocus;
var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();
var myCheckInsuAccNo = "";
var myCheckDate = "";
var sqlresourcename = "productdef.LDRiskToRateSql";

function init() {
	// ��дSQL���
	initCollectivityGrid();
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("LDRiskToRateSql0");
	mySql.addSubPara("");
	mySql.addSubPara("");
	// alert(mySql.getString());
	turnPage.queryModal(mySql.getString(), CollectivityGrid);
	if (CollectivityGrid.mulLineCount <= 0) {
		myAlert(""+"û�з������������ݣ�"+"");
		return false;
	}
}

function CDate(sDateStr) {
	var myDateStr = sDateStr.replace("-", ":");
	var newDate = new Date(myDateStr);
	return newDate;
}
// ��ѯ��Ʒ����
function easyQueryClick() {
	// ��ʼ�����
	initCollectivityGrid();
	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	if(0==fm.RiskCode.value.length&&0!=fm.RateType.value.length){
		mySql1.setSqlId("LDRiskToRateSql0");
		mySql1.addSubPara("");
		mySql1.addSubPara(fm.RateType.value);
		
	}else if(0==fm.RateType.value&&0!=fm.RiskCode.value.length){
		mySql1.setSqlId("LDRiskToRateSql0");
		mySql1.addSubPara(fm.RiskCode.value);
		mySql1.addSubPara("");
	}else if(0==fm.RiskCode.value.length&&0==fm.RiskCode.value.length ){
		mySql1.setSqlId("LDRiskToRateSql0");
		mySql1.addSubPara("");
		mySql1.addSubPara("");
	}else{
			var mySql3= new SqlClass();
			mySql3.setResourceName(sqlresourcename);
			mySql3.setSqlId("LDRiskToRateSql7");
			mySql3.addSubPara(fm.RiskCode.value);
			mySql3.addSubPara(fm.RateType.value);
			mySql3.addSubPara("");
			var strSQL1 = mySql3.getString();
			var dataResult = easyExecSql(strSQL1);	
			if(null !=dataResult){
				
				mySql1.setSqlId("LDRiskToRateSql5");
				// alert(mySql1.getString());
				mySql1.addSubPara(fm.RiskCode.value);
				mySql1.addSubPara(fm.RateType.value);
				mySql1.addSubPara(fm.RiskCode.value);
				mySql1.addSubPara(fm.RateType.value);
			}else{
				
				mySql1.setSqlId("LDRiskToRateSql0");
				mySql1.addSubPara(fm.RiskCode.value);
				mySql1.addSubPara(fm.RateType.value);
			}
			 
	}
	turnPage.queryModal(mySql1.getString(), CollectivityGrid);
	if (CollectivityGrid.mulLineCount <= 0) {
		myAlert(""+"û�з������������ݣ�"+"");
		return false;
	}
}

function ShowPlan() {
	var tSel = CollectivityGrid.getSelNo(); // ���ر�ѡ���е��к�
	var tGrpNo1 = CollectivityGrid.getRowColData(tSel - 1, 1);
	var tGrpNo2 = CollectivityGrid.getRowColData(tSel - 1, 3);
	var tGrpNo3 = CollectivityGrid.getRowColData(tSel - 1, 4);
	var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("LDRiskToRateSql7");
	mySql1.addSubPara(tGrpNo1);
	mySql1.addSubPara(tGrpNo2);
	mySql1.addSubPara(tGrpNo3);
	var strSQL1 = mySql1.getString();
	var dataResult = easyExecSql(strSQL1);
	//alert(dataResult);
	var mySql2 = new SqlClass();
	mySql2.setResourceName(sqlresourcename);
	if (null != dataResult) {
		mySql2.setSqlId("LDRiskToRateSql6");
		mySql2.addSubPara(tGrpNo1);
		mySql2.addSubPara(tGrpNo2);
		mySql2.addSubPara(tGrpNo3);
		var strSQL2 = mySql2.getString();
		var arrResult = easyExecSql(strSQL2);
		// alert(arrResult);
		fm.all('changeReason').value = arrResult[0][0];// ��ʾѡ���еı��ԭ��
		fm.all('auditConclusion').value = arrResult[0][1];// ��ʾѡ���е���˽���
	} else {
		fm.all('changeReason').value = '';
		fm.all('auditConclusion').value = '';
	}

}

function download() {
	var tSel = CollectivityGrid.getSelNo(); // ���ر�ѡ���е��к�
	var tGrpNo1 = CollectivityGrid.getRowColData(tSel - 1, 1);
	var tGrpNo2 = CollectivityGrid.getRowColData(tSel - 1, 3);
	var tGrpNo3 = CollectivityGrid.getRowColData(tSel - 1, 4);
	if (null == tGrpNo1 || null == tGrpNo2 || null == tGrpNo3 || "" == tGrpNo1
			|| "" == tGrpNo2 || "" == tGrpNo3) {
		myAlert(""+"����ѡ����Ҫ���ص��У�"+"");
		return false;
	}
	var parameter = tGrpNo1 + "," + tGrpNo2 + "," + tGrpNo3;
	fm.State.value = parameter;
	fm.Transact.value = "DATAD";
	fm.submit();
}

function rateDownloadClick() {
	var tSel = CollectivityGrid.getSelNo(); // ���ر�ѡ���е��к�
	var tGrpNo1 = CollectivityGrid.getRowColData(tSel - 1, 1);
	var tGrpNo2 = CollectivityGrid.getRowColData(tSel - 1, 3);
	var tGrpNo3 = CollectivityGrid.getRowColData(tSel - 1, 4);
	if (null == tGrpNo1 || null == tGrpNo2 || null == tGrpNo3 || "" == tGrpNo1
			|| "" == tGrpNo2 || "" == tGrpNo3) {
		myAlert(""+"����ѡ����Ҫ���ص��У�"+"");
		return false;
	}
	var parameter = tGrpNo1 + "," + tGrpNo2 + "," + tGrpNo3;
	fm.State.value = parameter;
	fm.Transact.value = "RATED";
	
	fm.submit();
}

function afterSubmit(FlagStr, content) {
	// alert(FlagStr);
	 showInfo.close();
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+ content;
	showModalDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	initForm();
}

function resetForm() {
	try {
		initForm();
	} catch (re) {
		myAlert("toulian.js-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
	}
}
