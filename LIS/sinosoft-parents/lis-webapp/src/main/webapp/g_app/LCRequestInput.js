/***************************************************************
 * <p>ProName��LCRequestInput.js</p>
 * <p>Title��ҵ������</p>
 * <p>Description��ҵ������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCRequestSql");
	tSQLInfo.setSqlId("LCRequestSql1");
	tSQLInfo.addSubPara(tOfferListNo);
	var arrResult1 = easyExecSql(tSQLInfo.getString());
	if (arrResult1 !== null && arrResult1 !== "") {
		var tQuotNo = arrResult1[0][0];
		var tQuotBatNo = arrResult1[0][1];
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCRequestSql");
		tSQLInfo.setSqlId("LCRequestSql2");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		turnPage1.queryModal(tSQLInfo.getString(), RequestGrid, 1, 1);
	}
}
