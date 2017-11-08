/***************************************************************
 * <p>ProName：LLCLaimControlApplyInput.js</p>
 * <p>Title：理赔责任控制申请界面</p>
 * <p>Description：理赔责任控制申请界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态

var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLCLaimControlApplySql");
	tSQLInfo.setSqlId("LLCLaimControlApplySql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), PolicyGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}

function applyClick() {
	
	var tSelNo = PolicyGrid.getSelNo();
	if (tSelNo==0) {
		alert("请先选择一条保单信息！");
		return false;	
	}
	
	var tGrpContNo = PolicyGrid.getRowColData(tSelNo-1, 2);
	
	var strUrl="./LLCLaimControlMain.jsp?Flag=1&BussType=NB&BussNo="+ tGrpContNo;
	
	window.open(strUrl,"理赔责任控制",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
