/***************************************************************
 * <p>ProName��PreCustomerQueryInput.js</p>
 * <p>Title��׼�ͻ�ά����ѯ����</p>
 * <p>Description��׼�ͻ�ά����ѯ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��������
 */
function addClick() {
	
	window.location = "./PreCustomerManageInput.jsp?Flag=1";
}

/**
 * ��ѯ����
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql1");
	tSQLInfo.addSubPara(fm.PreCustomerName.value);
	
	if (tFlag=="1") {//׼�ͻ�ά����ѯ
		
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara("");
	} else if (tFlag=="2") {//׼�ͻ�������ѯ
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tManageCom);
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), PreCustomerGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
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
	
	var tPreCustomerNo = PreCustomerGrid.getRowColData(tSelNo-1, 1);
	
	if (tFlag=="1") {//׼�ͻ�ά��ѡ�к�
		
		window.location = "./PreCustomerManageInput.jsp?Flag=2&PreCustomerNo="+tPreCustomerNo;
	} else if (tFlag=="2") {//׼�ͻ�����ѡ�к�
		
		window.location = "./PreCustomerUnlockInput.jsp?PreCustomerNo="+tPreCustomerNo;
	}
	
}

//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "׼�ͻ�����^|׼�ͻ�����^|��λ����^|��ҵ����^|Ԥ��Ͷ��������^|Ԥ�Ʊ��ѹ�ģ(Ԫ)^|�Ƿ�б�";
	
	//��������SQL	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql1");
	tSQLInfo.addSubPara(fm.PreCustomerName.value);
	if (tFlag=="1") {//׼�ͻ�ά����ѯ
		
		tSQLInfo.addSubPara(tOperator);
		tSQLInfo.addSubPara("");
	} else if (tFlag=="2") {//׼�ͻ�������ѯ
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tManageCom);
	}
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	fm.submit();
}
