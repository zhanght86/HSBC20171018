/***************************************************************
 * <p>ProName��LLClaimQuestionQueryInput.js</p>
 * <p>Title���������ѯ</p>
 * <p>Description���������ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tSQLInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

/**
 * ��ѯ��ť
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimQuestionQuerySql");
	tSQLInfo.setSqlId("LLClaimQuestionQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.InputStartDate.value);
	tSQLInfo.addSubPara(fm.InputEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.DealStartDate.value);
	tSQLInfo.addSubPara(fm.DealEndDate.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(mManageCom);	
						
	turnPage1.queryModal(tSQLInfo.getString(),QuestionListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}
//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "�������κ�^|������^|������ת��^|Ͷ��������^|������������^|"
							+"��������ͱ���^|���������^|���������^|����״̬^|�����������^|��������^|"
							+"�������������^|�����ʱЧ^|�������";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimQuestionQuerySql");
	tSQLInfo.setSqlId("LLClaimQuestionQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.PageNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.InputStartDate.value);
	tSQLInfo.addSubPara(fm.InputEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.DealStartDate.value);
	tSQLInfo.addSubPara(fm.DealEndDate.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}