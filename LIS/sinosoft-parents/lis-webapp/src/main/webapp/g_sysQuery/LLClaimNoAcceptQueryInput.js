/***************************************************************
 * <p>ProName��LLClaimLastCaseInput.js</p>
 * <p>Title���ⰸ��ѯ</p>
 * <p>Description���ⰸ��ѯ</p>
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
	tSQLInfo.setResourceName("g_claim.LLClaimNoAcceptQuerySql");
	tSQLInfo.setSqlId("LLClaimNoAcceptQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);	
						
	turnPage1.queryModal(tSQLInfo.getString(),NoAcceptListGrid, 2);
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
	var tTitle = "�������κ�^|������^|Ͷ���˱���^|Ͷ��������^|�������˱���^|������������^|"
							+"�Ա�^|��������^|֤������^|֤������^|������^|�˵���^|δ����ԭ��^|"
							+"������^|����ʱ��";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoAcceptQuerySql");
	tSQLInfo.setSqlId("LLClaimNoAcceptQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}