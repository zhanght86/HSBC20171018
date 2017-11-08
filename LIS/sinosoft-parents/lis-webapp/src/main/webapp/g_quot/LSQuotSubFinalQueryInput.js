/***************************************************************
 * <p>ProName：LSQuotSubFinalQueryInput.js</p>
 * <p>Title：中支公司报价生成</p>
 * <p>Description：中支公司报价生成</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询操作
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProcessSql");
	tSQLInfo.setSqlId("LSQuotProcessSql8");
	tSQLInfo.addSubPara(fm.QuotName.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndtDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuotGrid, 2,1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 进入询价
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
