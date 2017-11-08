/***************************************************************
 * <p>ProName：UWPersonQueryInput.js</p>
 * <p>Title：人员分部查询</p>
 * <p>Description：人员分部查询</p>
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
function queryInfo(){
	
	var tbranchSub = fm.branchSub.value;

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWDealSql");
	if(tbranchSub=='0'){
		tSQLInfo.setSqlId("UWDealSql6");
	}else if(tbranchSub=='1'){
		tSQLInfo.setSqlId("UWDealSql7");
		tSQLInfo.addSubPara(fm.sysPlanCode.value);
		tSQLInfo.addSubPara(fm.ContPlanCode.value);
	}else if(tbranchSub=='2'){
		tSQLInfo.setSqlId("UWDealSql8");
		tSQLInfo.addSubPara(fm.RiskCode.value);
	}	
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), YearDistributionGrid, 1, 0, 20);
}


/**
 * 保险方案查询
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}


/**
 * 查询险种信息
 */
function showRiskCode(cObj,cObjName){
	return showCodeList('contplanrisk',[cObj,cObjName],[0,1],null,fm.GrpPropNo.value,'policyno',1,null);
}

function showRiskCodeName(cObj,cObjName){
	return showCodeListKey('contplanrisk',[cObj,cObjName],[0,1],null,fm.GrpPropNo.value,'policyno',1,null);	
}


/**
 * 下拉后处理
 */
function afterCodeSelect(CodeName, Field ){
	
	if(CodeName == "distritype") {
		if(fm.branchSub.value=="0"){
			divr1.style.display="";
			divr2.style.display="none";
			divr3.style.display="none";
			initYearDistributionGrid();
			queryInfo();
		}else if(fm.branchSub.value=="1"){
			divr1.style.display="";
			divr2.style.display="";
			divr3.style.display="none";
			initYearDistributionGrid();
		}else if(fm.branchSub.value=="2"){
			divr1.style.display="";
			divr2.style.display="none";
			divr3.style.display="";
			initYearDistributionGrid();
		}
	}
	
	if(CodeName=='contplan'){
		if(fm.ContPlanCode.value!=''){
			queryInfo();
		}
	}
		
	if(CodeName=='contplanrisk'){
		if(fm.RiskCode.value!=''){
			queryInfo();
		}	
	}
}
