/***************************************************************
 * <p>ProName��LCGrpSpecInput.js</p>
 * <p>Title���ر�Լ��</p>
 * <p>Description���ر�Լ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-04-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


/**
 * ��ѯ�ѱ����ر�Լ��
 */
function quryGrpSpec() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpSpecSql");
	tSQLInfo.setSqlId("LCGrpSpecSql1");
	tSQLInfo.addSubPara(tOfferListNo);
	var arrResult1 = easyExecSql(tSQLInfo.getString());
	if (arrResult1 !== null && arrResult1 !== "") {
		var tQuotNo = arrResult1[0][0];
		var tQuotBatNo = arrResult1[0][1];
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpSpecSql");
		tSQLInfo.setSqlId("LCGrpSpecSql2");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		var arrResult = easyExecSql(tSQLInfo.getString());
		if (arrResult !== null && arrResult !== "") {
			fm.GrpSpec.value = arrResult[0][0];
		}
	}
	
}
