/***************************************************************
 * <p>ProName��PreCustomerTraceQueryInput.js</p>
 * <p>Title��׼�ͻ��޸Ĺ켣��ѯ����</p>
 * <p>Description��׼�ͻ��޸Ĺ켣��ѯ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ����
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql6");
	tSQLInfo.addSubPara(tPreCustomerNo);
	//tSQLInfo.addSubPara(fm.PreCustomerName.value);
	tSQLInfo.addSubPara("");
	
	turnPage1.queryModal(tSQLInfo.getString(), PreCustomerGrid, 2,1);
}

/**
 * ѡ�в���
 */
function GotoDetail() {
	
	var tSelNo = PreCustomerGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tTraceID = PreCustomerGrid.getRowColData(tSelNo-1, 1);
	var tPreCustomerNo = PreCustomerGrid.getRowColData(tSelNo-1, 2);
	
	window.location = "./PreCustomerUnlockInput.jsp?TraceID="+tTraceID+"&PreCustomerNo="+tPreCustomerNo;
}
