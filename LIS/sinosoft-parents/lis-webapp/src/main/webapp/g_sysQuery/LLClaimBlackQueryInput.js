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
var turnPage2 = new turnPageClass();
turnPage2.pageLineNum = 100;

/**
 * ��ѯ�ⰸ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackQuerySql");
	tSQLInfo.setSqlId("LLClaimBlackQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
						
	turnPage1.queryModal(tSQLInfo.getString(),BlackListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}
/**
 * ����
 */
function goBack() {
	top.close();
}
/**
 * �����ⰸ��ѯ
 */
function showOldCase() {
	
	var tSelNo = BlackListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����¼");
		return false;
	}
	var tCustomerNo =BlackListGrid.getRowColData(tSelNo,3);
	var strUrl="../g_claim/LLClaimLastCaseMain.jsp?CustomerNo="+tCustomerNo+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "������^|Ͷ��������^|�������˱���^|������������^|"
							+"�Ա�^|��������^|֤������^|֤������^|�ͻ�״̬^|ҵ������^|"
							+"ҵ�����^|������������^|����������ԭ��^|���������ñ�ע^|��������^|";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackQuerySql");
	tSQLInfo.setSqlId("LLClaimBlackQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}