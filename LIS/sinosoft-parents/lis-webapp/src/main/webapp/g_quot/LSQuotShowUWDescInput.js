/***************************************************************
 * <p>ProName：LSQuotShowUWDescInit.js</p>
 * <p>Title：询价查询--总公司最终意见</p>
 * <p>Description：询价查询--总公司最终意见</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2015-01-14
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();


/**
 * 查询已保存特别约定
 */
function quryGrpSpec() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult !== null && arrResult !== "") {
		fm.UWConclu.value = arrResult[0][0];
		fm.UWConcluName.value = arrResult[0][1];
		fm.UWOpinion.value = arrResult[0][2];
	}
}

