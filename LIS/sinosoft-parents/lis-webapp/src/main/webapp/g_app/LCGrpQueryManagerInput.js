/***************************************************************
 * <p>ProName��LCGrpQueryManagerInput.js</p>
 * <p>Title����ѯ�ͻ�����</p>
 * <p>Description����ѯ�ͻ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-13
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ�ͻ�������Ϣ
 */
function queryManager() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
	tSQLInfo.setSqlId("LCGrpQueryManagerSql1");
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
		alert("��ѡ��һ���ͻ�������Ϣ��¼��");
		return false;
	}
	var tManagerrNo = ManagerGrid.getRowColData(tSelNo,1);
	var tManagerName = ManagerGrid.getRowColData(tSelNo,2);
	var tManageCode = ManagerGrid.getRowColData(tSelNo,3);
	
	top.opener.AgentDetailGrid.setRowColData(tMulLineNo,1,tManagerrNo);
	top.opener.AgentDetailGrid.setRowColData(tMulLineNo,2,tManagerName);
	top.opener.AgentDetailGrid.setRowColData(tMulLineNo,3,tManageCode);
	top.close();
}

