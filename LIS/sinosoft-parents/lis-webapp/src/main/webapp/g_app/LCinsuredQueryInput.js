/***************************************************************
 * <p>ProName��LCinsuredQueryInput.js</p>
 * <p>Title�������˲�ѯ</p>
 * <p>Description�������˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


/**
 * ѡ��һ����¼�󷵻��ϼ�ҳ��
 */
function returnback() {
	
	var selno = CustomerGrid.getSelNo();
	if (selno < 1) {
		alert("��ѡ��һ�пͻ���Ϣ��");
		return false;
	}
	tArr = CustomerGrid.getRowData(selno-1);
	
	var tFlag = fm.Flag.value;
	if(tFlag =='01'){
		top.opener.showUserInfo(tArr);
		}else if(tFlag == '02'){
		top.opener.showMainUserInfo(tArr);	
		}
	top.close();	
}

/**
 * ��ѯ�ͻ���Ϣ
 */
function queryCustomer() {
	
	tSQLInfo.setResourceName("g_app.LCinsuredQuerySql");
	tSQLInfo.setSqlId("LCinsuredQuerySql1");
	tSQLInfo.addSubPara(fm.InsuredName.value);
	
	var res = turnPage1.queryModal(tSQLInfo.getString(), CustomerGrid);
}

/**
 * ��ѯ�������˿ͻ���Ϣ
 */
function queryMainCustomer(){

	tSQLInfo.setResourceName("g_app.LCinsuredQuerySql");
	tSQLInfo.setSqlId("LCinsuredQuerySql2");
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	var res = turnPage1.queryModal(tSQLInfo.getString(), CustomerGrid);
}






