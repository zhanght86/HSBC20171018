/***************************************************************
 * <p>ProName：LCInsuredCollectInput.js</p>
 * <p>Title：导入信息汇总查询</p>
 * <p>Description：导入信息汇总查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();


/**
* 被保险人汇总信息
*/
function showList(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql11");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryScanGrid, 0, 2);
}

	
			

