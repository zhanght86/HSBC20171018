/***************************************************************
 * <p>ProName��LCGrpQueryAgentComInput.js</p>
 * <p>Title����ѯ�н����</p>
 * <p>Description����ѯ�н����</p>
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
 * ��ѯ��Ϣ
 */
function queryAgent() {
	if(tFlag=='0'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
		tSQLInfo.setSqlId("LCGrpQueryManagerSql3");
		tSQLInfo.addSubPara(fm.AgentName.value);
		tSQLInfo.addSubPara(NullToEmpty(tSaleChnl));
		tSQLInfo.addSubPara(tManageCom);
		turnPage1.queryModal(tSQLInfo.getString(), AgentGrid, 1, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}	
	} else if(tFlag=='1') {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
		tSQLInfo.setSqlId("LCGrpQueryManagerSql4");
		tSQLInfo.addSubPara(fm.AgentName.value);
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(NullToEmpty(tSaleDepart));
		tSQLInfo.addSubPara(NullToEmpty(tChnlType));
		tSQLInfo.addSubPara(NullToEmpty(tSaleChnl));
		turnPage1.queryModal(tSQLInfo.getString(), AgentGrid,1,1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	} else if(tFlag=='2') {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
		tSQLInfo.setSqlId("LCGrpQueryManagerSql6");
		tSQLInfo.addSubPara(fm.AgentName.value);
		tSQLInfo.addSubPara(NullToEmpty(tSaleChnl));
		tSQLInfo.addSubPara(tManageCom);
		turnPage1.queryModal(tSQLInfo.getString(), AgentGrid,1,1);
		
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/***
 * ������Ϣ
 */
function returnAgent() {
	if(tFlag=='0'){
		var tSelNo = AgentGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("��ѡ��һ���н������Ϣ��¼��");
			return false;
		}
		var tAgentCom = AgentGrid.getRowColData(tSelNo,1);
		var tAgentName = AgentGrid.getRowColData(tSelNo,2);
		
		top.opener.AgentComGrid.setRowColData(tMulLineNo,1,tAgentCom);
		top.opener.AgentComGrid.setRowColData(tMulLineNo,2,tAgentName);
		top.close();
	}else if(tFlag=='1'){
		var tSelNo = AgentGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("��ѡ��һ�����������Ϣ��¼��");
			return false;
		}
		var tAgentCom = AgentGrid.getRowColData(tSelNo,1);
		var tAgentName = AgentGrid.getRowColData(tSelNo,2);
		
		top.opener.AgentComGrid.setRowColData(tMulLineNo,3,tAgentCom);
		top.opener.AgentComGrid.setRowColData(tMulLineNo,4,tAgentName);
		top.close();
	}else{
		var tSelNo = AgentGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("��ѡ��һ���н������Ϣ��¼��");
			return false;
		}
		var tAgentCom = AgentGrid.getRowColData(tSelNo,1);
		var tAgentName = AgentGrid.getRowColData(tSelNo,2);
		
		top.opener.ZJGrid.setRowColData(tMulLineNo,1,tAgentCom);
		top.opener.ZJGrid.setRowColData(tMulLineNo,2,tAgentName);
		top.close();
	}
}

