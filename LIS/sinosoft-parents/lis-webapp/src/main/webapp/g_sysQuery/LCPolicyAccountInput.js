/***************************************************************
 * <p>ProName��LCPolicyAccountInput.js</p>
 * <p>Title���˻���Ϣ��ѯ</p>
 * <p>Description���˻���Ϣ��ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	initResultInfoGrid();
	if(!verifyInput2()){
		return false;
	}
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
		tSQLInfo.setSqlId("PolicyAccountSql1");
		
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ContNo.value);
		tSQLInfo.addSubPara(fm.InsuredName.value);
		tSQLInfo.addSubPara(fm.IDNo.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 1, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	
}

/**
 * ��������
 */
function exportClick() {

	if (!verifyInput2()) {
		return false;
	}
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "���屣����^|Ͷ��������^|�ͻ���^|���˱�����^|����^|֤������^|���ֱ���^|�˻�����^|��������^|�˻����^|�б�����";
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
		tSQLInfo.setSqlId("PolicyAccountSql3");
		
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ContNo.value);
		tSQLInfo.addSubPara(fm.InsuredName.value);
		tSQLInfo.addSubPara(fm.IDNo.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

function  queryInfo(){
	
	var tSelNo = QueryResultGrid.getSelNo();
	var tPolNo = QueryResultGrid.getRowColData(tSelNo-1,5);
	var tInsuAccNo = QueryResultGrid.getRowColData(tSelNo-1,9);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
	tSQLInfo.setSqlId("PolicyAccountSql2");
		
	tSQLInfo.addSubPara(tPolNo);
	tSQLInfo.addSubPara(tInsuAccNo);
		
	turnPage2.queryModal(tSQLInfo.getString(),ResultInfoGrid, 1, 1);
		
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}

}

/**
 * ��������
 */
function exportClick1() {

	var tSelNo = QueryResultGrid.getSelNo();
	if(tSelNo<1){
		alert("��ѡ��һ������������Ϣ");
		return false;
	}
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	var tPolNo = QueryResultGrid.getRowColData(tSelNo-1,5);
	var tInsuAccNo = QueryResultGrid.getRowColData(tSelNo-1,9);
	//�������
	var tTitle = "ҵ���^|ҵ�����ͱ���^|ҵ����������^|ҵ������^|������ͱ���^|�������^|������";
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyAccountSql");
		tSQLInfo.setSqlId("PolicyAccountSql2");
		
		tSQLInfo.addSubPara(tPolNo);
		tSQLInfo.addSubPara(tInsuAccNo);

	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

function resetClick(){
	fm.GrpContNo.value='';
	fm.GrpName.value='';
	fm.ManageCom.value='';
	fm.ManageComName.value='';
	fm.ContNo.value='';
	fm.InsuredName.value='';
	fm.IDNo.value='';
	
	initQueryResultGrid();
	initResultInfoGrid();
}
