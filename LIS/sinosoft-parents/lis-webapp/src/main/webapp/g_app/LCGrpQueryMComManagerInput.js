/***************************************************************
 * <p>ProName��LCGrpQueryMComManagerInput.js</p>
 * <p>Title����ѯ�����ؿͻ�����</p>
 * <p>Description����ѯ�����ؿͻ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-14
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ�����ؿͻ�������Ϣ
 */
function queryManager() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
	tSQLInfo.setSqlId("LCGrpQueryManagerSql2");
	tSQLInfo.addSubPara(fm.PreCustomerNo.value);
	tSQLInfo.addSubPara(fm.PreCustomerName.value);
	tSQLInfo.addSubPara(tManageCom);
	turnPage1.queryModal(tSQLInfo.getString(), ManagerGrid);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/***
 * ����׼�ͻ���
 */
function returnManager() {
	
	var tSelNo = ManagerGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ�������ؿͻ�������Ϣ��¼��");
		return false;
	}
	var tManagerrNo = ManagerGrid.getRowColData(tSelNo,1);
	var tManagerName = ManagerGrid.getRowColData(tSelNo,2);
	
	top.opener.BusinessAreaGrid.setRowColData(tMulLineNo,4,tManagerrNo);
	top.opener.BusinessAreaGrid.setRowColData(tMulLineNo,5,tManagerName);
	top.close();
}

