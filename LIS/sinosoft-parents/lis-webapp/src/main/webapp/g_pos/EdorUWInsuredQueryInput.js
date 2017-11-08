/***************************************************************
 * <p>ProName：EdorUWInsuredQueryInput.js</p>
 * <p>Title：人员清单查询</p>
 * <p>Description：人员清单查询</p>
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
 * 查询
 */
function queryInsured() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorUWDealSql");
	tSQLInfo.setSqlId("EdorUWDealSql1");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWInsuredGrid, 1 ,1);
}


/**
 * 保险方案查询
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpContNo.value,'grpcontno',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpContNo.value,'grpcontno',1,null);
}


/**
 * 条件查询
 */
function queryTermInsured(Code) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorUWDealSql");
	
	if (Code == "Grade"){//等级
		
		tSQLInfo.setSqlId("EdorUWDealSql2");
	}else if (Code == "Age") {//年龄
		
		tSQLInfo.setSqlId("EdorUWDealSql3");
	} else if (Code == "Amnt") {//保额
		
		tSQLInfo.setSqlId("EdorUWDealSql5");
	} else { //普通查询

		tSQLInfo.setSqlId("EdorUWDealSql1");
	}
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWInsuredGrid, 1, 1);
	
	if (!turnPage1.strQueryResult) {
	
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 返回
 */
function returnBack(){
	
	top.close();		
}

//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {	
		return false;
	}
	
	//报表标题
	var tTitle = "保全类型^|保险方案^|方案描述^|被保险人客户号^|姓名^|性别^|出生日期^|年龄^|证件类型^|证件号码^|保额（元）^|保费（元）^|与主被保人关系^|主被保险人";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorUWDealSql");
	tSQLInfo.setSqlId("EdorUWDealSql6");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	
	var tQuerySQL = tSQLInfo.getString();
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	fm.submit();
}
