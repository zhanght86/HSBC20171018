/***************************************************************
 * <p>ProName��LDQueryUserInput.js</p>
 * <p>Title���û���ѯ</p>
 * <p>Description���û���ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryUser() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_auxi.LDQueryUserSql");
	tSQLInfo.setSqlId("LDQueryUserSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.UserCode.value);
	tSQLInfo.addSubPara(fm.UserName.value);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	
	initUserGrid();
	turnPage1.queryModal(tSQLInfo.getString(), UserGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/***
 * ����׼�ͻ���
 */
function returnUser() {
	
	var tSelNo = UserGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���û���Ϣ��¼��");
		return false;
	}
	var tUserCode = UserGrid.getRowColData(tSelNo,1);
	var tUserName = UserGrid.getRowColData(tSelNo,2);
		
	top.opener.fm.UserCode.value = tUserCode;
	top.opener.fm.UserName.value = tUserName;
	top.opener.fm.SupervisorFlag.value = "";
	top.opener.fm.SupervisorName.value = "";
	top.opener.fm.PopedomLevel.value = "";
	top.opener.fm.PopedomLevelName.value = "";
	
	top.close();
}

