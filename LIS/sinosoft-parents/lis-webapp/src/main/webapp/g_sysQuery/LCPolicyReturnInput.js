/***************************************************************
 * <p>ProName��LCPolicyReturnInput.js</p>
 * <p>Title����ִ��ѯ</p>
 * <p>Description����ִ��ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	if(!verifyInput2()){
		return false;
	}
	
	if(fm.SignStartDate.value !="" && fm.SignEndDate.value !="" ){
		 if(fm.SignStartDate.value > fm.SignEndDate.value){
		 	alert("ǩ��������С��ǩ��ֹ�ڣ�" );
		 	return false;
		 }
	}
	if(fm.ReturnStartDate.value !="" && fm.ReturnEndDate.value !="" ){
		 if(fm.ReturnStartDate.value > fm.ReturnEndDate.value){
		 	alert("��ִ����������С�ڻ�ִ����ֹ�ڣ�" );
		 	return false;
		 }
	}
	if(fm.CardState.value=='3'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql3");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}else if(fm.CardState.value=='6'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql2");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}else{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql1");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
	
}

/**
 * ��������
 */
function exportClick() {

	if (!verifyInput2()) {
		return false;
	}
	if(fm.SignStartDate.value !="" && fm.SignEndDate.value !="" ){
		 if(fm.SignStartDate.value > fm.SignEndDate.value){
		 	alert("ǩ��������С��ǩ��ֹ�ڣ�" );
		 	return false;
		 }
	}
	if(fm.ReturnStartDate.value !="" && fm.ReturnEndDate.value !="" ){
		 if(fm.ReturnStartDate.value > fm.ReturnEndDate.value){
		 	alert("��ִ����������С�ڻ�ִ����ֹ�ڣ�" );
		 	return false;
		 }
	}
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "�������^|���屣����^|Ͷ��������^|�ͻ��������^|�ͻ�����^|ǩ������^|ǩ������^|��������^|״̬";
	
	if(fm.CardState.value=='3'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql3");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}else if(fm.CardState.value=='6'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql2");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}else{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_sysQuery.PolicyReturnSql");
		tSQLInfo.setSqlId("PolicyReturnSql1");
		
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.GrpContNo.value);
		tSQLInfo.addSubPara(fm.GrpName.value);
		tSQLInfo.addSubPara(fm.SignStartDate.value);
		tSQLInfo.addSubPara(fm.SignEndDate.value);
		tSQLInfo.addSubPara(fm.ReturnStartDate.value);
		tSQLInfo.addSubPara(fm.ReturnEndDate.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		
		turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}


//����
function resetClick(){
	
	fm.ManageCom.value='';
	fm.ManageComName.value='';
	fm.GrpContNo.value='';
	fm.GrpName.value='';
	fm.SignStartDate.value='';
	fm.SignEndDate.value='';
	fm.CardState.value='';
	fm.CardStateName.value='';
	fm.ReturnStartDate.value='';
	fm.ReturnEndDate.value='';
	
}
