/***************************************************************
 * <p>ProName��LSQuotQueryAppComInput.js</p>
 * <p>Title����ѯ���û���</p>
 * <p>Description����ѯ���û���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-29
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ���û���
 */
function queryAppCom() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql7");
	tSQLInfo.addSubPara(fm.ComNo.value);
	tSQLInfo.addSubPara(fm.ComName.value);
	
	initAppComGrid();
	turnPage1.queryModal(tSQLInfo.getString(), AppComGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/***
 * ���ػ�����Ϣ
 */
function returnCom() {
	
	var tSelNo = AppComGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ�����û�����Ϣ��¼��");
		return false;
	}
	var tComNo = AppComGrid.getRowColData(tSelNo,1);
	var tComName = AppComGrid.getRowColData(tSelNo,2);
	top.opener.AppOrgCodeGrid.setRowColData(tMulLineNo,1,tComNo);
	top.opener.AppOrgCodeGrid.setRowColData(tMulLineNo,2,tComName);
	top.close();
}

