/***************************************************************
 * <p>ProName��LSQuotSubUWQueryInput.js</p>
 * <p>Title����֧��˾�˱�</p>
 * <p>Description����֧��˾�˱�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-24
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
	tSQLInfo.setResourceName("g_quot.LSQuotProcessSql");
	tSQLInfo.setSqlId("LSQuotProcessSql1");
	tSQLInfo.addSubPara(fm.QuotName.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndtDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuotGrid,0,1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ����ѯ��
 */
function GotoDetail() {
	
	var tSelNo = QuotGrid.getSelNo()-1;
	var tMissionID = QuotGrid.getRowColData(tSelNo, 3);
	var tSubMissionID = QuotGrid.getRowColData(tSelNo, 4);
	var tActivityID = QuotGrid.getRowColData(tSelNo, 5);
	var tQuotNo = QuotGrid.getRowColData(tSelNo, 6);
	var tQuotBatNo = QuotGrid.getRowColData(tSelNo, 7);
	var tQuotType = QuotGrid.getRowColData(tSelNo, 8);
	
	if (tQuotType=="00") {
		location.href = "./LSQuotUWDetailInput.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	} else if (tQuotType=="01") {
		location.href = "./LSQuotProjUWDetailInput.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	}
}
