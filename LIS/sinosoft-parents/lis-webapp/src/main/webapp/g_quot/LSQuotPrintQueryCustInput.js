/***************************************************************
 * <p>ProName��LSQuotPrintQueryCustInput.js</p>
 * <p>Title�����۵���ӡ--��ѯ׼�ͻ�����</p>
 * <p>Description�����۵���ӡ--��ѯ׼�ͻ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ׼�ͻ���
 */
function queryCustomer() {
	
	if (tQuotType == "00") {//һ��ѯ��
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintQueryCustSql");
		tSQLInfo.setSqlId("LSQuotPrintQueryCustSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(fm.PreCustomerNo.value);
		tSQLInfo.addSubPara(fm.PreCustomerName.value);
		
	} else if (tQuotType == "01") {//��Ŀѯ��
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintQueryCustSql");
		tSQLInfo.setSqlId("LSQuotPrintQueryCustSql2");
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(fm.PreCustomerNo.value);
		tSQLInfo.addSubPara(fm.PreCustomerName.value);
	}
	
	initCustInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), CustInfoGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}

}

/***
 * ����׼�ͻ���
 */
function returnCustomer() {
	
	var tSelNo = CustInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��׼�ͻ���Ϣ��¼��");
		return false;
	}
	var tQuery_PreCustomerNo = CustInfoGrid.getRowColData(tSelNo,1);
	var tQuery_PreCustomerName = CustInfoGrid.getRowColData(tSelNo,2);
	var tQuery_QuotNo = CustInfoGrid.getRowColData(tSelNo,3);
	var tQuery_QuotBatNo = CustInfoGrid.getRowColData(tSelNo,4);
	
	top.opener.fm.PreCustomerNo.value = tQuery_PreCustomerNo;
	top.opener.fm.PreCustomerName.value = tQuery_PreCustomerName;
	top.opener.fm.Query_QuotNo.value = tQuery_QuotNo;
	top.opener.fm.Query_QuotBatNo.value = tQuery_QuotBatNo;
	top.opener.queryCustInfo();
	top.close();
}

