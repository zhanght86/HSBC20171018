/***************************************************************
 * <p>ProName��LCGrpQuerySalerInput.js</p>
 * <p>Title����ѯ������</p>
 * <p>Description����ѯ������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-15
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ�����ؿͻ�������Ϣ
 */
function querySaler() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
	tSQLInfo.setSqlId("LCGrpQueryManagerSql5");
	tSQLInfo.addSubPara(fm.SalerName.value);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(tAgentCom);
	turnPage1.queryModal(tSQLInfo.getString(), SalerGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/***
 * ���ش�������Ϣ
 */
function returnSaler() {
	
	var tSelNo = SalerGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ��¼��");
		return false;
	}
	var tSalerCode = SalerGrid.getRowColData(tSelNo,1);
	var tSalerName = SalerGrid.getRowColData(tSelNo,2);
	var tTeamName = SalerGrid.getRowColData(tSelNo,3);
	
	top.opener.AgentComGrid.setRowColData(tMulLineNo,3,tSalerCode);
	top.opener.AgentComGrid.setRowColData(tMulLineNo,4,tSalerName);
	top.opener.AgentComGrid.setRowColData(tMulLineNo,5,tTeamName);
	top.close();
}

