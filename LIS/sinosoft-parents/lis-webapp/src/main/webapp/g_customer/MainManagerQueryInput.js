/***************************************************************
 * <p>ProName��PreCustomerManagerInput.js</p>
 * <p>Title����ѯ�ͻ�����</p>
 * <p>Description����ѯ�ͻ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-07-31
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
	tSQLInfo.setResourceName("g_customer.PreCustomerManagerQuerySql");
	tSQLInfo.setSqlId("PreCustomerManagerQuerySql2");
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(tManageCom);
	turnPage1.queryModal(tSQLInfo.getString(), ManagerGrid, 1, 1);
	
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
	
		top.opener.fm.SalerCode.value=tManagerrNo;
		top.opener.fm.SalerName.value=tManagerName;
	
	top.close();
}

