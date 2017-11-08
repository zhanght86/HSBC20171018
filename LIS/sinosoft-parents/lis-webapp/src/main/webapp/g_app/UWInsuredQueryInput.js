/***************************************************************
 * <p>ProName��UWInsuredQueryInput.js</p>
 * <p>Title����Ա�嵥��ѯ</p>
 * <p>Description����Ա�嵥��ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryInsured() {
	
	if(fm.ContPlanCode.value==''){
		alert("��ѡ�񷽰����в�ѯ!");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWDealSql");
	tSQLInfo.setSqlId("UWDealSql1");
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.InsuredNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(fm.sysPlanCode.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWInsuredGrid, 1 ,1);
}


/**
 * ���շ�����ѯ
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'ProposalGrpContNo',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'ProposalGrpContNo',1,null);
}


/**
 * ������ѯ
 */
function queryTermInsured(Code) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWDealSql");
	
	if (Code == "Grade"){//�ȼ�
		
		tSQLInfo.setSqlId("UWDealSql2");
	}else if (Code == "Age") {//����
		
		tSQLInfo.setSqlId("UWDealSql3");
	} else if (Code == "Amnt") {//����
		
		tSQLInfo.setSqlId("UWDealSql5");
	} else { //��ͨ��ѯ

		tSQLInfo.setSqlId("UWDealSql1");
	}
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.InsuredNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(fm.sysPlanCode.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWInsuredGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
	
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ����
 */
function returnBack(){
	
	top.close();		
}

//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {	
		return false;
	}
	
	//�������
	var tTitle = "���շ���^|��������^|�������˿ͻ���^|����^|�Ա�^|��������^|����^|֤������^|֤������^|���Ԫ��^|���ѣ�Ԫ��^|���������˹�ϵ^|���������˿ͻ���";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWDealSql");
	tSQLInfo.setSqlId("UWDealSql9");
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.InsuredNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(fm.sysPlanCode.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	var tQuerySQL = tSQLInfo.getString();
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	fm.submit();
}
