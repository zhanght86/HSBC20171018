/***************************************************************
 * <p>ProName��LJDebtsPayQueryInput.jsp</p>
 * <p>Title�����˲�ѯ</p>
 * <p>Description�����˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-09-20
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tOperate;

/**
 * ��ѯ
 */
function queryInfo() {
	
	initDebtsPayInfoGrid();
	initDebtsPayDetailInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.DebtsPaySql");
	tSQLInfo.setSqlId("DebtsPaySql3");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryDebtsType.value);
	tSQLInfo.addSubPara(fm.QueryDebtsState.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), DebtsPayInfoGrid, 2, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

function showDetailInfo() {
	
	initDebtsPayDetailInfoGrid();
	
	var tSelNo = DebtsPayInfoGrid.getSelNo()-1;
	var tDebtsNo = DebtsPayInfoGrid.getRowColData(tSelNo, 1);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.DebtsPaySql");
	tSQLInfo.setSqlId("DebtsPaySql4");
	tSQLInfo.addSubPara(tDebtsNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), DebtsPayDetailInfoGrid, 0, 1);	
}
