/***************************************************************
 * <p>ProName��LCInsuredCollectInput.js</p>
 * <p>Title��������Ϣ���ܲ�ѯ</p>
 * <p>Description��������Ϣ���ܲ�ѯ</p>
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
* �������˻�����Ϣ
*/
function showList(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql11");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryScanGrid, 0, 2);
}

	
			

