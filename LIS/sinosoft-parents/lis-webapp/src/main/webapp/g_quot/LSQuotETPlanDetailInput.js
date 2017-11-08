/***************************************************************
 * <p>ProName：LSQuotETPlanInput.js</p>
 * <p>Title：一般询价方案信息录入</p>
 * <p>Description：一般询价方案信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//方案信息
var tSQLInfo = new SqlClass();
var mQueryRisk = "0";//0--鼠标点击，1--回车模糊查询

function queryPlanDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql21");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanDetailInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanDetailInfoGrid, 1, 1);//第三位表示使用大数据量
}


/**
 * 模拟下拉操作
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	fmPub.all("HiddenCodeType").value = value1;
	if (value1=='quotplan') {
		
		var tSql = "1";
		tSql = "1 and a.quotno=#"+ tQuotNo +"# and a.quotbatno="+ tQuotBatNo;
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}
	
	if (value1=='quotrisk') {
		
		if (isEmpty(fm2.PlanCode)) {
			alert("请先选择方案");
			return false;
		}
		
		var tPlanType = fm2.PlanType.value;
		
		var tSql = "1";
		
		if (tTranProdType=='00') {//如果是普通险种,看方案是否为公共保额方案,如果是,那么仅下拉出公共保额,如果不是,那么下拉出不含公共 保额的其他普通险种
			
			if (tPlanType=="00") {//非公共保额
				
				tSql = "1 and b.startdate <=date#"+tCurrentDate+"# and (case  when b.enddate is null  then date  #9999-12-31# else b.enddate end)  >date#"+tCurrentDate+"# and a.insuaccflag!=#Y# and b.riskprop=#G# and not exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#2# and t.code=a.riskcode and t.othersign=#1#)";
				tSql += " and not exists (select 1 from ldcodeexp t where t.codetype = #quotriskexp# and t.code = a.riskcode and t.codeexp =#1# )"; 
			} else if (tPlanType=="01") {
			
				tSql = "1 and b.startdate<=date#"+tCurrentDate+"# and (case when b.enddate is null then date #9999-12-31# else b.enddate end) >date#"+tCurrentDate+"# and exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#1# and t.code=a.riskcode)";
			} else {
				return null;
			}
		} else if (tTranProdType=='01') {//建工险
			
			tSql = "1 and b.startdate<=date#"+tCurrentDate+"# and (case when b.enddate is null then date #9999-12-31# else b.enddate end) >date#"+tCurrentDate+"# and exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#2# and t.code=a.riskcode)";
		} else if (tTranProdType=='02') {//账户型
		
			tSql = "1 and b.startdate<=date#"+tCurrentDate+"# and (case when b.enddate is null then date #9999-12-31# else b.enddate end) >date#"+tCurrentDate+"# and a.insuaccflag=#Y# and b.riskprop!=#I# ";
		} else if (tTranProdType=='03') {//个人产品
			tSql = "1 and b.startdate<=date#"+tCurrentDate+"# and (case when b.enddate is null then date #9999-12-31# else b.enddate end) >date#"+tCurrentDate+"# and b.riskprop=#I# ";
		}
		
		if (mQueryRisk=="1") {//当回车模糊查询时
			
			mQueryRisk = "0";//初始化为鼠标点击
			var tRiskNameTemp = fm2.RiskName.value;
			if (tRiskNameTemp!="") {
				tSql+= " and a.riskname like #%"+tRiskNameTemp+"%# ";
			}
//			var tSqlAll = "select a.riskcode,a.riskname from lmrisk a,lmriskapp b where 1=1 and a.riskcode=b.riskcode and 1= "
//					+ tSql;
//			
//			var tQueryRiskSQL =  tSqlAll.replace(/#/g,'\'');
//			var tArr = easyExecSql(tQueryRiskSQL, 1, 0, 1);//大写字母变成了小写，暂不适用
//			
//			if (tArr==null) {
//				alert("不存在该险种！");
//				return false;
//			}
		}
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1','300');
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1','300');
		}
	}
	
	if (value1=='quotduty') {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("请先选择险种");
			return false;
		}
		
		var tSql = "1 and a.riskcode=#"+ fm2.RiskCode.value +"#";
		
		if (fm2.PlanType.value=="02") {//个人账户
			tSql += " and a.specflag=#0#";
		} else if (fm2.PlanType.value=="03") {//团体账户
			tSql += " and a.specflag!=#0#";
		}
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}
	
	if (value1=='amnttype') {
		
		if (tTranProdType=="00") {//普通险种,拓展编码为产品类型+方案类型
		
			var tPlanType = fm2.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("请选择方案！");
				return false;
			}
			
			var tSql = "1 and codetype=#amnttype# and codeexp=#"+ tTranProdType+tPlanType+"#";
			
			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
			}
		} else if (tTranProdType=="01") {//建工险,拓展编码为产品类型+保费计算方式
			
			var tPremCalType = fm2.PremCalType.value;
			if (tPremCalType==null || tPremCalType=="") {
				alert("请选择方案！");
				return false;
			}
			
			var tSql = "1 and codetype=#amnttype# and codeexp=#"+ tTranProdType+tPremCalType+"#";
			
			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
			}
		
		} else if (tTranProdType=="03") {//个人险种,拓展编码为产品类型+方案类型
			
			var tPlanType = fm2.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("请选择方案！");
				return false;
			}
			
			var tSql = "1 and codetype=#amnttype# and codeexp=#"+ tTranProdType+tPlanType+"#";
			
			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
			}
		}
	}
	
	if (value1=='exceptpremtype') {
		
		var tSql = "1 and codetype=#exceptpremtype# and codeexp=#0#";
		var tOccupTypeFlag = fm2.OccupTypeFlag.value;
		
		if (returnType=='0') {
	
			if (tOccupTypeFlag=="2") {
				
				//tSql += tOccupTypeFlag;
				return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
			} else {
				return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
			}
		} else {
			
			if (tOccupTypeFlag=="2") {
				
				//tSql += tOccupTypeFlag;
				return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
			}
		}
	}
}

function afterCodeSelect(cCodeType, Field) {
	
	var tCodeType = fmPub.HiddenCodeType.value;
	fmPub.HiddenCodeType.value = "";
	
	if (cCodeType=="quotplan") {//下拉方案后,如果方案信息与所选方案信息不一致,那么重置方案信息
		
		var tPlanCode = fm2.PlanCode.value;
		var tPlanDesc = fm2.PlanDesc.value;
		var tSysPlanCode = fm2.SysPlanCode.value;
		var tPlanType = fm2.PlanType.value;
		var tPremCalType = fm2.PremCalType.value;
		var tPlanFlag = fm2.PlanFlag.value;
		var tOccupTypeFlag = fm2.OccupTypeFlag.value;
		
		var tOldPlanType = fm2.OldPlanType.value;
		var tOldPremCalType = fm2.OldPremCalType.value;
		var tOldOccupTypeFlag = fm2.OldOccupTypeFlag.value;
		
		fm2.OldPlanType.value = tPlanType;
		fm2.OldPremCalType.value = tPremCalType;
		fm2.OldOccupTypeFlag.value = tOccupTypeFlag;
		
		if (tTranProdType=="01") {
			
			if (tOldPremCalType==null || tOldPremCalType=="") {
				return;
			}
			if (tPremCalType!=tOldPremCalType) {
			
				fm2.RiskCode.value = "";
				fm2.RiskName.value = "";
				fm2.DutyCode.value = "";
				fm2.DutyName.value = "";
				
				dealShowDuty(fm2, "divDutyFactor");
			
				document.getElementById("divInfo4").style.display = 'none';
			}
		} else {
			
			if (tOldPlanType==null || tOldPlanType=="") {
				return;
			}
			
			if (tPlanType!=tOldPlanType) {
			
				fm2.RiskCode.value = "";
				fm2.RiskName.value = "";
				fm2.DutyCode.value = "";
				fm2.DutyName.value = "";
				
				dealShowDuty(fm2, "divDutyFactor");
			
				document.getElementById("divInfo4").style.display = 'none';
			}
			
			if (tTranProdType!="02") {//账户型产品不进行期望保费处理
				
				if (tOldOccupTypeFlag=="1" && tOccupTypeFlag=="2") {//只有当单职业类别向多职业类别转变时,才进行才处理
					
						fm2.ExceptPremType.value = "";
						fm2.ExceptPremTypeName.value = "";
						fm2.ExceptPrem.value = "";
				}
			}
		}
	} else if (cCodeType=="quotrisk") {//选完险种后,清空责任及责任动态信息
		
		dealShowDuty(fm2, "divDutyFactor");
	} else if (cCodeType=="quotduty") {//选完责任后,处理责任动态域
		
		var tRiskCode = fm2.RiskCode.value;
		var tDutyCode = fm2.DutyCode.value;
		var tFlag = 0;
		var tObj = fm2;
		var tDivID = "divDutyFactor";
		var tSysPlanCode = fm2.SysPlanCode.value;
		var tPlanCode = fm2.PlanCode.value;
		var tPlanType = fm2.PlanType.value;
		
		dealRiskDuty(tRiskCode, tDutyCode, tFlag, tObj, tDivID, tSysPlanCode, tPlanCode, tPlanType);//再次增加个方案
		
		document.getElementById("idExceptPrem1").style.display = "";
		document.getElementById("idExceptPrem2").style.display = "none";
		document.getElementById("idExceptPrem3").style.display = "none";
		document.getElementById("idExceptPrem4").style.display = "none";
		
	} else if (cCodeType=="queryexp") {//选择保额类型后,处理保额动态展示
		
		if (tCodeType=="amnttype") {
			dealAmntShow(fm2, Field.value);
		} else if (tCodeType=="exceptpremtype") {
			
			var tExceptPremType = Field.value;
			showExceptPrem(tExceptPremType);
		}
	} else if (cCodeType=="trueflag") {
		dealDutyLimitAmnt('Page');
	} else if (cCodeType=="premcalway") {//保费计算方式
		var tPremCalWay = Field.value;
		if (tPremCalWay=="1") {
			
			document.getElementById("idPrem").style.display = "none";
			
			document.getElementById("idPerPrem1").style.display = "";
			document.getElementById("idPerPrem2").style.display = "";
			document.getElementById("hidden1").style.display = "none";
			document.getElementById("hidden2").style.display = "none";
			document.getElementById("idStarPerPrem").style.display = "";
			
			document.getElementById("idStandPerPrem1").style.display = "none";
			document.getElementById("idStandPerPrem2").style.display = "none";
			document.getElementById("hidden3").style.display = "";
			document.getElementById("hidden4").style.display = "";
			
		} else if (tPremCalWay=="0") {
			
			document.getElementById("idPrem").style.display = "";
			
			document.getElementById("idPerPrem1").style.display = "none";
			document.getElementById("idPerPrem2").style.display = "none";
			document.getElementById("hidden1").style.display = "";
			document.getElementById("hidden2").style.display = "";
			document.getElementById("idStarPerPrem").style.display = "none";
			
			document.getElementById("idStandPerPrem1").style.display = "none";
			document.getElementById("idStandPerPrem2").style.display = "none";
			document.getElementById("hidden3").style.display = "";
			document.getElementById("hidden4").style.display = "";
			
		}
	} 
}

/**
 * 选择保额后处理
 */
function dealAmntShow(cObj, tFieldValue) {

	if (tFieldValue=="01") {
		
		document.getElementById("idFixedAmnt00").style.display = "";
		document.getElementById("idFixedAmnt01").style.display = "";
		document.getElementById("idSalaryMult00").style.display = "none";
		document.getElementById("idSalaryMult01").style.display = "none";      
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "none";
		document.getElementById("idMinAmnt01").style.display = "none";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "";
		document.getElementById("idAmnt03").style.display = "";
	} else if (tFieldValue=="02") {
		
		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "";
		document.getElementById("idSalaryMult01").style.display = "";      
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "none";
		document.getElementById("idMinAmnt01").style.display = "none";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "";
		document.getElementById("idAmnt03").style.display = "";
	} else if (tFieldValue=="03") {
		
		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "";
		document.getElementById("idSalaryMult01").style.display = "";      
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "";
		document.getElementById("idMinAmnt01").style.display = "";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "none";
		document.getElementById("idAmnt03").style.display = "none";
	} else if (tFieldValue=="04") {
		
		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "none";
		document.getElementById("idSalaryMult01").style.display = "none";      
		document.getElementById("idMaxAmnt00").style.display = "";
		document.getElementById("idMaxAmnt01").style.display = "";
		document.getElementById("idMinAmnt00").style.display = "";
		document.getElementById("idMinAmnt01").style.display = "";
		document.getElementById("idAmnt00").style.display = "none";
		document.getElementById("idAmnt01").style.display = "none";
		document.getElementById("idAmnt02").style.display = "none";
		document.getElementById("idAmnt03").style.display = "none";
	}
}

/**
 * 点击主附共用配置时处理
 */
function setRelationFlag() {

	if (document.getElementById("divDutyFactorRelation").style.display=="") {
		
		fm2.RelaShareFlag.value = "0";
		dealRelationShow(fm2, "0");
	} else {
		fm2.RelaShareFlag.value = "1";
		dealRelationShow(fm2, "1");
	}
}

/**
 * 根据主副关联标记展示附属域
 */
function dealRelationShow(cObj, tFieldValue) {
	
	if (tFieldValue=="1") {
		
		document.getElementById("SetRelation").checked = true;
		//document.getElementById("trRelation").style.display = "";    
		document.getElementById("trRelationRate").style.display = ""; 
		document.getElementById("divDutyFactorRelation").style.display = "";
	} else {
		
		document.getElementById("SetRelation").checked = false;
		//document.getElementById("trRelation").style.display = "none";
		document.getElementById("trRelationRate").style.display = "none"; 
		document.getElementById("divDutyFactorRelation").style.display = "none";
	}
}

/**
 * 点击方案明细,展示信息
 */
function showPlanDetailInfo() {
	
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	var tFlag = 1;
	var tObj = fm2;
	var tDivID = "divDutyFactor";
	
	var tPlanDesc = PlanDetailInfoGrid.getRowColData(tSelNo, 3);
	var tPlanType = PlanDetailInfoGrid.getRowColData(tSelNo, 4);
	var tPremCalType = PlanDetailInfoGrid.getRowColData(tSelNo, 6);
	var tPlanFlag = PlanDetailInfoGrid.getRowColData(tSelNo, 8);
	var tOccupTypeFlag = PlanDetailInfoGrid.getRowColData(tSelNo, 10);
	var tRiskName = PlanDetailInfoGrid.getRowColData(tSelNo, 13);
	var tDutyName = PlanDetailInfoGrid.getRowColData(tSelNo, 15);
	
	document.getElementById("SysPlanCode").value = tSysPlanCode;
	document.getElementById("PlanCode").value = tPlanCode;
	document.getElementById("PlanType").value = tPlanType;
	document.getElementById("PremCalType").value = tPremCalType;
	document.getElementById("PlanFlag").value = tPlanFlag;
	document.getElementById("OccupTypeFlag").value = tOccupTypeFlag;
	document.getElementById("OldOccupTypeFlag").value = tOccupTypeFlag;
	document.getElementById("PlanDesc").value = tPlanDesc;
	document.getElementById("RiskCode").value = tRiskCode;
	document.getElementById("RiskName").value = tRiskName;
	document.getElementById("DutyCode").value = tDutyCode;
	document.getElementById("DutyName").value = tDutyName;
	
	document.getElementById("OldPlanType").value = tPlanType;
	document.getElementById("OldPremCalType").value = tPremCalType;
	
	dealRiskDuty(tRiskCode, tDutyCode, tFlag, tObj, tDivID, tSysPlanCode, tPlanCode, tPlanType);

	if (tPlanType=="01") {
		
		document.getElementById("divInfo4").style.display = "";
		queryPubAmntRelaPlanInfo();
		initPubAmntRelaDutyGrid();
	} else {
		
		document.getElementById("divInfo4").style.display = "none";
	}
	
	if (tActivityID == "0800100004" || tActivityID == "0800100005" || tActivityID == "0800100006" 
		|| tActivityID == "0800100007" || tActivityID == null || tActivityID == "") {
		document.getElementById("tryCalButton").style.display = "none";
	} 
}

/**
 * 增加方案明细
 */
function addPlanDetail() {
	
	fmPub.Operate.value = "ADDPLANDETAIL";
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("产品类型已发生改变，不可进行该操作！");
		return false;
	}
	
	dealRedundant(fm2, tTranProdType);
	
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!beforeSubmit(fm2, tTranProdType)) {
		return false;
	}
	fm2.action = "./LSQuotETPlanDetailSave.jsp?Operate=ADDPLANDETAIL&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * 修改方案明细
 */
function modifyPlanDetail() {
	
	fmPub.Operate.value = "MODIFYPLANDETAIL";
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("产品类型已发生改变，不可进行该操作！");
		return false;
	}
	
	if (!checkSame()) {
		return false;
	}
	
	dealRedundant(fm2, tTranProdType);
	
	if (!verifyForm('fm2')) {
		return false;
	}

	if (!beforeSubmit(fm2, tTranProdType)) {
		return false;
	}
	
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	
	fm2.action = "./LSQuotETPlanDetailSave.jsp?Operate=MODIFYPLANDETAIL&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&OSysPlanCode="+ tSysPlanCode +"&OPlanCode="+ tPlanCode +"&ORiskCode="+ tRiskCode +"&ODutyCode="+ tDutyCode +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * 删除方案明细,删除时,需校验所选方案,险种,责任是否一致
 */
function delPlanDetail() {
	
	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的记录！");
		return false;
	}
	
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	
	if (!checkSame()) {
		return false;
	}
	
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	
	fmPub.Operate.value = "DELPLANDETAIL";
	fm2.action = "./LSQuotETPlanDetailSave.jsp?Operate=DELPLANDETAIL&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&OSysPlanCode="+ tSysPlanCode +"&OPlanCode="+ tPlanCode +"&ORiskCode="+ tRiskCode +"&ODutyCode="+ tDutyCode +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * 校验提交的数据是否为选中的记录
 */
function checkSame() {

	var tSelNo = PlanDetailInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要操作的方案明细记录！");
		return false;
	}
	
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);
	
	var tSysPlanCode1 = document.getElementById("SysPlanCode").value;
	var tPlanCode1 = document.getElementById("PlanCode").value;
	var tRiskCode1 = document.getElementById("RiskCode").value;
	var tDutyCode1 = document.getElementById("DutyCode").value;
	
	if (tSysPlanCode!=tSysPlanCode1 || tPlanCode!=tPlanCode1 || tRiskCode!=tRiskCode1 || tDutyCode!=tDutyCode1) {
		alert("进行该操作时，不可改变方案、险种及责任信息！");
		return false;
	}
	
	return true;
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		dealAfterSubmit(content);
	}	
}

/**
 * 提交后处理
 */
function dealAfterSubmit(o) {
	
	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	
	if (tOperate=="ADDPLANDETAIL" || tOperate=="MODIFYPLANDETAIL" || tOperate=="DELPLANDETAIL") {
		
		fm2.PlanType.value = "";
		fm2.OldPlanType.value = "";
		fm2.PremCalType.value = "";
		fm2.OldPremCalType.value = "";
		fm2.PlanFlag.value = "";
		fm2.OccupTypeFlag.value = "";
		fm2.OldOccupTypeFlag.value = "";
		fm2.PlanCode.value = "";
		fm2.PlanDesc.value = "";
		fm2.SysPlanCode.value = "";
		fm2.RiskCode.value = "";
		fm2.RiskName.value = "";
		fm2.DutyCode.value = "";
		fm2.DutyName.value = "";
		
		dealShowDuty(fm2, "divDutyFactor");
		
		queryPlanDetail();
		document.getElementById("divInfo4").style.display = "none";
	} else if (tOperate=="SAVEPUBAMNT" || tOperate=="DELETEPUBAMNT"){
		
		queryPubAmntRelaPlanInfo();
		initPubAmntRelaDutyGrid();
	} else if (tOperate=="TRYCAL") {
		fm2.ExceptPrem.value = o;
	}
}


/**
 * 处理责任显示
 */
function dealShowDuty(cObj, cDivID) {
	
	document.getElementById(cDivID).innerHTML= "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	document.getElementById("DutyCode").value = "";
	document.getElementById("DutyName").value = "";
	document.getElementById("idAmnt").style.display = "none";
	document.getElementById("idPrem").style.display = "none";
	document.getElementById("idFeeRate").style.display = "none";
	document.getElementById("tryCalButton").style.display = "none";
	document.getElementById("AmntType").value = "";
	document.getElementById("AmntTypeName").value = "";
	document.getElementById("FixedAmnt").value = "";
	document.getElementById("MinAmnt").value = "";
	document.getElementById("ExceptPremType").value = "";
	document.getElementById("ExceptPremTypeName").value = "";
	document.getElementById("ExceptPrem").value = "";
	document.getElementById("InitPrem").value = "";
	document.getElementById("ExceptYield").value = "";
	document.getElementById("Remark").value = "";
	
	document.getElementById("trRelation").style.display = "none";
	document.getElementById("SetRelation").checked = false;
	document.getElementById("RelaShareFlag").value = "0";
	document.getElementById("trRelationRate").style.display = "none";
	document.getElementById("RelaToMain").value = "";
	document.getElementById("RelaToMainName").value = "";
	document.getElementById("MainAmntRate").value = "";
	document.getElementById("RelaAmntRate").value = "";
	document.getElementById("divDutyFactorRelation").style.display = "none";
	document.getElementById("divDutyFactorRelation").innerHTML= "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
}

/**
 * 处理产品责任要素展示
 * cRiskCode:险种;cDutyCode:责任;cFlag:标识(0-下拉后处理,1-点击记录);obj:对应的FM;cDivID:需要展示的域;
 */
function dealRiskDuty(cRiskCode, cDutyCode, cFlag, cObj, cDivID, cSysPlanCode, cPlanCode, cPlanType) {
	
	document.getElementById(cDivID).innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	document.getElementById(cDivID+"Relation").innerHTML = "<table class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></table>";
	//1.根据险种类型来判断展示域,责任选择后根据险种来进行判断
	/*
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql17");
	tSQLInfo.addSubPara(cRiskCode);//SQL查询字段
	var tRiskTypeArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	var tRiskType;
	if (tRiskTypeArr==null) {
		
		alert("获取险种类型失败");
		return false;
	} else {//展示域处理
		
		tRiskType = tRiskTypeArr[0][0];
		if (tRiskType=="") {//账户型险种
			
			document.getElementById("idAmnt").style.display = "none";
			document.getElementById("idPrem").style.display = "none";
			document.getElementById("idFeeRate").style.display = "";
			document.getElementById("tryCalButton").style.display = "none"
		} else {
			
			document.getElementById("idAmnt").style.display = "";  
			document.getElementById("idPrem").style.display = "";   
			document.getElementById("idFeeRate").style.display = "none";
			document.getElementById("tryCalButton").style.display = "";
		}
		
		document.getElementById("AmntType").value = "";
		document.getElementById("AmntTypeName").value = "";
		document.getElementById("FixedAmnt").value = "";
		document.getElementById("MinAmnt").value = "";
		document.getElementById("ExceptPremType").value = "";
		document.getElementById("ExceptPremTypeName").value = "";
		document.getElementById("ExceptPrem").value = "";
		document.getElementById("InitPrem").value = "";
		document.getElementById("ExceptYield").value = "";
	}
	*/
	
	document.getElementById("AmntType").value = "";
	document.getElementById("AmntTypeName").value = "";
	document.getElementById("FixedAmnt").value = "";
	document.getElementById("SalaryMult").value = "";
	document.getElementById("MaxAmnt").value = "";
	document.getElementById("MinAmnt").value = "";
	document.getElementById("ExceptPremType").value = "";
	document.getElementById("ExceptPremTypeName").value = "";
	document.getElementById("ExceptPrem").value = "";
	document.getElementById("InitPrem").value = "";
	document.getElementById("ExceptYield").value = "";
	document.getElementById("trRelation").style.display = "none";
	document.getElementById("SetRelation").checked = false;
	document.getElementById("RelaShareFlag").value = "0";
	document.getElementById("trRelationRate").style.display = "none";
	document.getElementById("RelaToMain").value = "";
	document.getElementById("RelaToMainName").value = "";
	document.getElementById("MainAmntRate").value = "";
	document.getElementById("RelaAmntRate").value = "";
	document.getElementById("divDutyFactorRelation").style.display = "none";
	
	if (cFlag=="0") {
		
		document.getElementById("idFixedAmnt00").style.display = "none";
		document.getElementById("idFixedAmnt01").style.display = "none";
		document.getElementById("idSalaryMult00").style.display = "none";
		document.getElementById("idSalaryMult01").style.display = "none";      
		document.getElementById("idMaxAmnt00").style.display = "none";
		document.getElementById("idMaxAmnt01").style.display = "none";
		document.getElementById("idMinAmnt00").style.display = "none";
		document.getElementById("idMinAmnt01").style.display = "none";
		document.getElementById("idAmnt00").style.display = "";
		document.getElementById("idAmnt01").style.display = "";
		document.getElementById("idAmnt02").style.display = "";
		document.getElementById("idAmnt03").style.display = "";
	}
	
	//1.根据产品类型进行展示域处理
	if (tTranProdType=="01") {//建工险
	
		document.getElementById("idAmnt").style.display = "";  
		document.getElementById("idPrem").style.display = "";   
		document.getElementById("idFeeRate").style.display = "none";
		document.getElementById("tryCalButton").style.display = "";
		document.getElementById("trRelation").style.display = "none";
	} else if (tTranProdType=="02") {//账户型险种
		
		document.getElementById("idAmnt").style.display = "none";
		document.getElementById("idPrem").style.display = "none";
		document.getElementById("idFeeRate").style.display = "";
		document.getElementById("tryCalButton").style.display = "none"
		document.getElementById("trRelation").style.display = "none";
	} else if (tTranProdType=="03") {//个人险种
		
		document.getElementById("idAmnt").style.display = "";  
		document.getElementById("idPrem").style.display = "";   
		document.getElementById("idFeeRate").style.display = "none";
		document.getElementById("tryCalButton").style.display = "";
		document.getElementById("trRelation").style.display = "none";
	} else {//其余都默认为一般险种
		
		document.getElementById("idAmnt").style.display = "";  
		document.getElementById("idPrem").style.display = "";   
		document.getElementById("idFeeRate").style.display = "none";
		document.getElementById("tryCalButton").style.display = "";
		
		if (cPlanType=="00") {
			document.getElementById("trRelation").style.display = "";
		} else {
			document.getElementById("trRelation").style.display = "none";
		}
	}
	
	//2.查询出需展示的因子并展示在界面上
	var tArr = getDutyElementArr(cRiskCode, cDutyCode);
	if (tArr==null || tArr.length==0) {
	
	} else {
		
		document.getElementById(cDivID).innerHTML = getDutyElement(tArr, "0");
		if (document.getElementById("trRelation").style.display=="") {
			document.getElementById(cDivID+"Relation").innerHTML = getDutyElement(tArr, "1");	
		}
		
		//add by ZhangC 20150320 处理公共保额新增字段
		for (var i=0; i<tArr.length; i++) {
			
			var tCalFactorN = tArr[i][0];
			if (tCalFactorN == "PerPrem") {
				
				document.getElementById("idPrem").style.display = "none";
				
				document.getElementById("idPerPrem1").style.display = "none";
				document.getElementById("idPerPrem2").style.display = "none";
				document.getElementById("hidden1").style.display = "";
				document.getElementById("hidden2").style.display = "";
			}
			if (tCalFactorN == "StandPerPrem") {
				
				document.getElementById("idStandPerPrem1").style.display = "none";
				document.getElementById("idStandPerPrem2").style.display = "none";
				document.getElementById("hidden3").style.display = "";
				document.getElementById("hidden4").style.display = "";
			}
		}
	}
	
	if (cFlag=="0") {
		return;
	}
	//modify by songsz 20140520 先进行固定域的赋值以便处理附属被保险人方案
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql20");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
	//select a.amnttype,a.amnt,a.minamnt,a.exceptpremtype,a.exceptprem,a.initprem,a.exceptyield from lsquotplandetail a;
	var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr2==null) {
		alert("获取所选记录信息失败！");
		return false;
	} else {//赋值
		
		var tFixedPlanInfo = new Array();
		var i = 0;
		
		tFixedPlanInfo[i++] = "AmntType";
		tFixedPlanInfo[i++] = "AmntTypeName";
		tFixedPlanInfo[i++] = "FixedAmnt";
		tFixedPlanInfo[i++] = "SalaryMult";
		tFixedPlanInfo[i++] = "MaxAmnt";
		tFixedPlanInfo[i++] = "MinAmnt";
		tFixedPlanInfo[i++] = "ExceptPremType";
		
		tFixedPlanInfo[i++] = "ExceptPremTypeName";
		tFixedPlanInfo[i++] = "ExceptPrem";
		tFixedPlanInfo[i++] = "InitPrem";
		tFixedPlanInfo[i++] = "ExceptYield";
		
		tFixedPlanInfo[i++] = "RelaShareFlag";
		/*
		tFixedPlanInfo[i++] = "RelaToMain";
		tFixedPlanInfo[i++] = "RelaToMainName";
		tFixedPlanInfo[i++] = "MainAmntRate";
		tFixedPlanInfo[i++] = "RelaAmntRate";
		*/
		tFixedPlanInfo[i++] = "Remark";
		
		for(var t=0; t<i; t++) {
			document.getElementById(tFixedPlanInfo[t]).value = tArr2[0][t];
		}
		showExceptPrem(fm2.ExceptPremType.value);
		dealAmntShow(fm2, fm2.AmntType.value);
		dealRelationShow(fm2, fm2.RelaShareFlag.value);
	}
	
	var tSQLElement;
	if (tArr==null || tArr.length==0) {
	
	} else {
		
		tSQLElement = getDutySQLElement(tArr);
		
		//3.传递查询字段进行从表查询
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql19");
		tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
		tSQLInfo.addSubPara(cRiskCode);
		tSQLInfo.addSubPara(cDutyCode);
		
		var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		//select * from lsquotplandetaisub a where 1=1 and a.quotno='' and a.quotbatno='' and a.plancode='' and a.riskcode='' and a.dutycode=''
		if (tArr1==null) {
			
		} else {//循环对界面要素赋值
			for (var i=0; i<tArr.length; i++) {
			
				tFactorCode = tArr[i][1];//从表字段
				tFieldType = tArr[i][3];//字段类型
				tValueType = tArr[i][4];//值类型
				
				if (tFieldType=="1") {
					
					if (tFactorCode == "P16") {//公共保额 保费计算方式
						document.getElementById("PremCalWay").value = tArr1[0][i];
						auotQuotShowCodeName(tValueType, tArr1[0][i], cObj, "PremCalWayName");
					} else {
						cObj.all(tFactorCode).value = tArr1[0][i];
						auotQuotShowCodeName(tValueType, tArr1[0][i], cObj, tFactorCode+"Name");
					}
					
				} else {
					
					if (tFactorCode == "P17") {
						cObj.all("PerPrem").value = tArr1[0][i];
					} else if (tFactorCode == "P18") {
						cObj.all("StandPerPrem").value = tArr1[0][i];
					} else {
						cObj.all(tFactorCode).value = tArr1[0][i];
					}
				}
			}
			//add by ZhangC 20150320 处理公共保额 保费计算方式，人均保费展示
			var tDutyCode = document.getElementById("DutyCode").value;
			if (tDutyCode == "2MT007001" || tDutyCode == "2MT012001") {
				var tPremCalWay = document.getElementById("PremCalWay").value;
				
				if (tPremCalWay=="1") {
					
					document.getElementById("idPrem").style.display = "none";
					
					document.getElementById("idPerPrem1").style.display = "";
					document.getElementById("idPerPrem2").style.display = "";
					document.getElementById("hidden1").style.display = "none";
					document.getElementById("hidden2").style.display = "none";
					document.getElementById("idStarPerPrem").style.display = "";
					
					document.getElementById("idStandPerPrem1").style.display = "none";
					document.getElementById("idStandPerPrem2").style.display = "none";
					document.getElementById("hidden3").style.display = "";
					document.getElementById("hidden4").style.display = "";
					
				} else if (tPremCalWay=="0") {
					
					document.getElementById("idPrem").style.display = "";
					
					document.getElementById("idPerPrem1").style.display = "none";
					document.getElementById("idPerPrem2").style.display = "none";
					document.getElementById("hidden1").style.display = "";
					document.getElementById("hidden2").style.display = "";
					document.getElementById("idStarPerPrem").style.display = "none";
					
					document.getElementById("idStandPerPrem1").style.display = "none";
					document.getElementById("idStandPerPrem2").style.display = "none";
					document.getElementById("hidden3").style.display = "";
					document.getElementById("hidden4").style.display = "";
					
				}
			}
		}
	}

	if (document.getElementById("trRelation").style.display=="" && document.getElementById("RelaShareFlag").value=="1") {//只有当附属方案配置展示且被勾选了,才查询附属信息

		//3.传递查询字段进行从表查询
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql30");
		if (tSQLElement==null || tSQLElement=="") {
			tSQLInfo.addSubPara("");//SQL查询字段
		} else {
			tSQLInfo.addSubPara(","+tSQLElement);//SQL查询字段
		}
		
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
		tSQLInfo.addSubPara(cRiskCode);
		tSQLInfo.addSubPara(cDutyCode);
		
		var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		//select * from lsquotplandetaisub a where 1=1 and a.quotno='' and a.quotbatno='' and a.plancode='' and a.riskcode='' and a.dutycode=''
		if (tArr2==null) {
			
		} else {//循环对界面要素赋值
			
			var tRelaFactor = "Relation";
			var tRelaFactorName = "附属方案";
			
			document.getElementById("RelaToMain").value = tArr2[0][0];
			document.getElementById("RelaToMainName").value = tArr2[0][1];
			document.getElementById("MainAmntRate").value = tArr2[0][2];
			document.getElementById("RelaAmntRate").value = tArr2[0][3];
			
			if (tArr==null) {
			
			} else {
				for (var i=0; i<tArr.length; i++) {
			
					tFactorCode = tRelaFactor + tArr[i][1];//从表字段
					tFieldType = tArr[i][3];//字段类型
					tValueType = tArr[i][4];//值类型
					var j = i+4;
					if (tFieldType=="1") {
						
						cObj.all(tFactorCode).value = tArr2[0][j];
						auotQuotShowCodeName(tValueType, tArr2[0][j], cObj, tFactorCode+"Name");
					} else {
						cObj.all(tFactorCode).value = tArr2[0][j];
					}
				}
			}
		}
	}
}

/**
 * 提交时处理冗余数据
 */
function dealRedundant(cObj, cTranProdType) {

	if (cTranProdType=="00") {//普通险种
	
		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType=="01") {//固定保额
		
			document.getElementById("SalaryMult").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="02") {//月薪倍数
		
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="03") {//月薪倍数与最低保额取大者
			
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
		} else if (tAmntType=="04") {//非固定保额
			
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("SalaryMult").value = "";
		}
		
		//只有普通险种+非公共保额方案+未选择配置时,才进行如下处理
		if (document.getElementById("PlanType").value=="00" && document.getElementById("divDutyFactorRelation").style.display=="none") {
			
			document.getElementById("MainAmntRate").value = "";
			document.getElementById("RelaAmntRate").value = "";
			document.getElementById("RelaToMain").value = "";
			document.getElementById("RelaToMainName").value = "";
			
			var tRiskCode = document.getElementById("RiskCode").value;
			var tDutyCode = document.getElementById("DutyCode").value;
			
			if (tRiskCode==null || tRiskCode =="" || tDutyCode==null || tDutyCode=="") {
				return ;
			}
			var tArr = getDutyElementArr(tRiskCode, tDutyCode);
			dealRedundantRelaElement(cObj, tArr);
		}
		
		var tDutyCode = document.getElementById("DutyCode").value;
		if (tDutyCode == "2MT007001" || tDutyCode == "2MT012001") {
			var tPremCalWay = document.getElementById("PremCalWay").value;
			if (tPremCalWay == "0") {//按保额收费
				document.getElementById("PerPrem").value = "";
			} else if (tPremCalWay == "1") {//按人数收费
				document.getElementById("ExceptPremType").value = "";
				document.getElementById("ExceptPremTypeName").value = "";
				document.getElementById("ExceptPrem").value = "";
			}
		}

		
	} else if (cTranProdType=="01") {//建工险险种
		
		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType=="01") {//固定保额
		
			document.getElementById("SalaryMult").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="02") {//月薪倍数
		
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="03") {//月薪倍数与最低保额取大者
			
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
		} else if (tAmntType=="04") {//非固定保额
			
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("SalaryMult").value = "";
		}
	} else if (cTranProdType=="02") {//账户型险种
	
		document.getElementById("AmntType").value = "";
		document.getElementById("FixedAmnt").value = "";
		document.getElementById("SalaryMult").value = "";
		document.getElementById("MaxAmnt").value = "";
		document.getElementById("MinAmnt").value = "";
	} else if (cTranProdType=="03") {//个人险种
	
		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType=="01") {//固定保额
		
			document.getElementById("SalaryMult").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="02") {//月薪倍数
		
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
			document.getElementById("MinAmnt").value = "";
		} else if (tAmntType=="03") {//月薪倍数与最低保额取大者
			
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("MaxAmnt").value = "";
		} else if (tAmntType=="04") {//非固定保额
			
			document.getElementById("FixedAmnt").value = "";
			document.getElementById("SalaryMult").value = "";
		}
	}
}

/**
 * 提交前校验
 */
function beforeSubmit(cObj, cTranProdType) {

	/**
	 * 校验范围:
	 * 1.根据产品类型进行保额类型校验
	 * 2.根据保额类型进行保额相关字段校验
	 * 3.动态展示域的校验
	 * 4.固定域校验
	 */
	if (isEmpty(cObj.PlanCode)) {
		
		alert("方案编码不能为空！");
		return false;
	}
	
	if (isEmpty(cObj.RiskCode)) {
		
		alert("险种不能为空！");
		return false;
	}
	
	if (isEmpty(cObj.DutyCode)) {
		
		alert("责任不能为空！");
		return false;
	}
	
	if (cTranProdType=="02") {//对于账户型产品,无保额类型的相关数据,校验期望初始保费及期望收益率
		
		var tInitPrem = document.getElementById("InitPrem").value;
		if (tInitPrem==null || tInitPrem=="") {
			alert("初始保费不能为空！");
			return false;
		}
		
		if (!checkDecimalFormat(tInitPrem, 12, 2)) {
			alert("初始保费整数位不应超过12位,小数位不应超过2位！");
			return false;
		}
		
		var tExceptYield = document.getElementById("ExceptYield").value;
		if (tExceptYield==null || tExceptYield=="") {
		
		} else {     
			
			if (!checkDecimalFormat(tExceptYield, 1, 6)) {
				alert("期望收益率整数位不应超过1位,小数位不应超过6位！");
				return false;
			}
		}
	} else {
		
		var tAmntType = document.getElementById("AmntType").value;
		if (tAmntType==null || tAmntType=="") {
			alert("请选择保额类型！");
			return false;
		}

		if (tAmntType=="01") {//固定保额
		
			var tFixedAmnt = document.getElementById("FixedAmnt").value;
			if (tFixedAmnt==null || tFixedAmnt=="") {
				alert("固定保额不能为空！");
				return false;
			}
			
			if (!checkDecimalFormat(tFixedAmnt, 12, 2)) {
				alert("固定保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
		} else if (tAmntType=="02") {//月薪倍数
		
			var tSalaryMult = document.getElementById("SalaryMult").value;
			if (tSalaryMult==null || tSalaryMult=="") {
				alert("月薪倍数不能为空！");
				return false;
			}
			
			if (!checkDecimalFormat(tSalaryMult, 5, 2)) {
				alert("月薪倍数整数位不应超过4位,小数位不应超过2位！");
				return false;
			}
		} else if (tAmntType=="03") {//月薪倍数与最低保额取大者
			
			var tSalaryMult = document.getElementById("SalaryMult").value;
			if (tSalaryMult==null || tSalaryMult=="") {
				alert("月薪倍数不能为空！");
				return false;
			}
			
			if (!checkDecimalFormat(tSalaryMult, 5, 2)) {
				alert("月薪倍数整数位不应超过4位,小数位不应超过2位！");
				return false;
			}
			
			var tMinAmnt = document.getElementById("MinAmnt").value;
			if (tMinAmnt==null || tMinAmnt=="") {
				alert("最低保额不能为空！");
				return false;
			}
			
			if (!checkDecimalFormat(tMinAmnt, 12, 2)) {
				alert("最低保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
		} else if (tAmntType=="04") {//非固定保额
			
			var tMaxAmnt = document.getElementById("MaxAmnt").value;
			if (tMaxAmnt==null || tMaxAmnt=="") {
				alert("最高保额不能为空！");
				return false;
			}
			
			if (!checkDecimalFormat(tMaxAmnt, 12, 2)) {
				alert("最高保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
			
			var tMinAmnt = document.getElementById("MinAmnt").value;
			if (tMinAmnt==null || tMinAmnt=="") {
				alert("最低保额不能为空！");
				return false;
			}
			
			if (!checkDecimalFormat(tMinAmnt, 12, 2)) {
				alert("最低保额整数位不应超过12位,小数位不应超过2位！");
				return false;
			}
			
			if (Number(tMinAmnt)>Number(tMaxAmnt)) {
				alert("最低保额不应大于最高保额！");
				return false;
			}
		}
		//modify by ZhangC 20150320  添加保费计算方式、人均保费，修改校验
		var tPlanType = cObj.PlanType.value;
		if (tPlanType == "01") {//普通险种 并且 公共保额方案
			
			var tPremCalWay = cObj.PremCalWay.value;
			if (tPremCalWay==null || tPremCalWay=="") {
				alert("保费计算方式不能为空！");
				return false;
			} 
			if (tPremCalWay=="0") {//按保额计算
				var tExceptPremType = cObj.ExceptPremType.value;
				if (tExceptPremType==null || tExceptPremType=="") {
					alert("期望保费类型不能为空！");
					return false;
				}
				if (fmPub.Operate.value!="TRYCAL") {       
					
					var tExceptPrem = cObj.ExceptPrem.value;
					if (tExceptPrem==null || tExceptPrem=="") {
						alert("期望保费/期望费率/费率折扣不能为空！");
						return false;
					}
					
					if (tExceptPremType=="01") {//保费,12,2
					
						if (!checkDecimalFormat(tExceptPrem, 12, 2)) {
							
							alert("当期望保费类型为期望保费时，期望保费整数位不应超过12位,小数位不应超过2位！");
							return false;
						}
					} else if (tExceptPremType=="02") {//费率
						
						if (!checkDecimalFormat(tExceptPrem, 4, 8)) {
							
							alert("当期望保费类型为期望费率时，期望费率整数位不应超过4位,小数位不应超过8位！");
							return false;
						}
					} else if (tExceptPremType=="03") {//折扣
						
						if (!checkDecimalFormat(tExceptPrem, 2, 2)) {
							
							alert("当期望保费类型为费率折扣时，费率折扣整数位不应超过2位,小数位不应超过2位！");
							return false;
						}
					}
				}
				
			} else if (tPremCalWay=="1") {//按人均保费计算
				var tPerPrem = cObj.PerPrem.value;
				if (tPerPrem==null || tPerPrem=="") {
					alert("人均保费不能为空！");
					return false;
				}
				if (!checkDecimalFormat(tPerPrem, 12, 2)) {
					
					alert("人均保费整数位不应超过12位,小数位不应超过2位！");
					return false;
				}
			}
		} else {//非普通险种、非账户,逻辑不变
			var tExceptPremType = cObj.ExceptPremType.value;
			if (tExceptPremType==null || tExceptPremType=="") {
				alert("期望保费类型不能为空！");
				return false;
			}
			if (fmPub.Operate.value!="TRYCAL") {       
				
				var tExceptPrem = cObj.ExceptPrem.value;
				if (tExceptPrem==null || tExceptPrem=="") {
					alert("期望保费/期望费率/费率折扣不能为空！");
					return false;
				}
				
				if (tExceptPremType=="01") {//保费,12,2
				
					if (!checkDecimalFormat(tExceptPrem, 12, 2)) {
						
						alert("当期望保费类型为期望保费时，期望保费整数位不应超过12位,小数位不应超过2位！");
						return false;
					}
				} else if (tExceptPremType=="02") {//费率
					
					if (!checkDecimalFormat(tExceptPrem, 4, 8)) {
						
						alert("当期望保费类型为期望费率时，期望费率整数位不应超过4位,小数位不应超过8位！");
						return false;
					}
				} else if (tExceptPremType=="03") {//折扣
					
					if (!checkDecimalFormat(tExceptPrem, 2, 2)) {
						
						alert("当期望保费类型为费率折扣时，费率折扣整数位不应超过2位,小数位不应超过2位！");
						return false;
					}
				}
			}
		}//非普通险种、非账户 END
	} //非账户 END
	var tRiskCode = cObj.RiskCode.value;
	var tDutyCode = cObj.DutyCode.value;
	var tArr = getDutyElementArr(tRiskCode, tDutyCode);
	
	//modify by ZhangC 20150320 公共保额新增动态因子 用上面JS校验，不用动态因子校验方法
	if (tDutyCode == "2MT007001" || tDutyCode == "2MT012001") {
	} else {
		if (!checkDutyElement(cObj, tArr, "0")) {
			return false;
		}
	}
	
	if (cTranProdType=="00" && fm2.RelaShareFlag.value=="1") {

		var tMainAmntRate = fm2.MainAmntRate.value;
		var tRelaAmntRate = fm2.RelaAmntRate.value;
		if (tMainAmntRate==null || tMainAmntRate=="") {
			alert("主被保险人保额占比不能为空！");
			return false;
		} else {
			
			if (!checkDecimalFormat(tMainAmntRate, 1, 2)) {
				alert("主被保险人保额占比整数位不应超过1位,小数位不应超过2位！");
				return false;
			}
		}
		
		if (tRelaAmntRate==null || tRelaAmntRate=="") {
			alert("附属被保人保额占比不能为空！");
			return false;
		} else {
			
			if (!checkDecimalFormat(tRelaAmntRate, 1, 2)) {
				alert("附属被保人保额占比整数位不应超过1位,小数位不应超过2位！");
				return false;
			}
		}
		if (Number(tMainAmntRate)+Number(tRelaAmntRate)<1) {
			
			alert("[主被保险人保额占比]与[附属被保人保额占比]的和应大于等于1！");
			return false;
		} 
		
		if (!checkDutyElement(cObj, tArr, "1")) {
			return false;
		}
	}
	
	return true;
}

/**
 * 查询公共保额方案信息
 */
function queryPubAmntRelaPlanInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql27");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPubAmntRelaPlanGrid();
	turnPage2.queryModal(tSQLInfo.getString(), PubAmntRelaPlanGrid, 1, 1);//第三位表示使用大数据量
}

/**
 * 查询公共保额险种责任信息
 */
function showRelaDuty(){
	
	var tRow = PubAmntRelaPlanGrid.getSelNo();
	var tSysPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,1);
	var tPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,2);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql28");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	
	initPubAmntRelaDutyGrid();
	noDiv(turnPage3, PubAmntRelaDutyGrid, tSQLInfo.getString());
	for(var i=0;i<PubAmntRelaDutyGrid.mulLineCount ;i++){
	
		var tPubFlag = PubAmntRelaDutyGrid.getRowColData(i,5);
		dealDutyLimitAmnt('Query',i,tPubFlag);
	}
}

/**
 * 公共保额使用关联--保存
 */
function saveClick() {
	
	var tRow = PubAmntRelaPlanGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条公共保额方案信息！");
		return false;	
	}
	if (!checkPubAmnt()){
		return false;
	}
	//公共保额，按人均保费时，校验关联方案人数不能为空
	if (!checkNumPeople()){
		return false;
	}
	
	var tSysPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,1);//系统方案编码
	var tPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,2);//方案编码
	var tLimitAmnt = PubAmntRelaPlanGrid.getRowColData(tRow-1,5);//个人限额
	
	fmPub.Operate.value = "SAVEPUBAMNT";
	fm2.action = "./LSQuotPubAmntRelaSave.jsp?Operate=SAVEPUBAMNT&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&LimitAmnt="+ tLimitAmnt;
	submitForm(fm2);
}


/**
 * 公共保额使用关联--取消
 */
function cancelClick() {
	
	var tRow = PubAmntRelaPlanGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条公共保额方案信息！");
		return false;	
	}
	
	var tSysPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,1);//系统方案编码
	var tPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,2);//方案编码
	
	fmPub.Operate.value = "DELETEPUBAMNT";
	fm2.action = "./LSQuotPubAmntRelaSave.jsp?Operate=DELETEPUBAMNT&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode;
	submitForm(fm2);
}


/**
 * 校验公共保额、个人限额、责任限额之间的关系
 */
function checkPubAmnt() {
	
	var tPlanRow = PlanDetailInfoGrid.getSelNo();
	//var tAmntType = PlanDetailInfoGrid.getRowColData(tPlanRow-1,15);//保额类型
	var tFixedAmnt = PlanDetailInfoGrid.getRowColData(tPlanRow-1,18);//固定保额
	
	var tPubAmntRow = PubAmntRelaPlanGrid.getSelNo();
	var tLimitAmnt = PubAmntRelaPlanGrid.getRowColData(tPubAmntRow-1,5);//个人限额
	
	if (tLimitAmnt != null && tLimitAmnt != "") {
		
		if (!isNumeric(tLimitAmnt)) {
			alert("个人限额不是有效的数字！");
			return false;
		}
		
		if (Number(tLimitAmnt)<=0) {
			alert("个人限额应大于0！");
			return false;
		}
		
		//校验个人限额不能大于公共保额
		if (Number(tLimitAmnt)>Number(tFixedAmnt)) {
			alert("个人限额不能大于公共保额！");
			return false;
		}
	} 
	
	//校验责任限额
	var tCount = 0;
	var tDutyTypeArr = [];//记录门诊、住院责任数组 
	for(var i=0;i < PubAmntRelaDutyGrid.mulLineCount;i++){
		
		var tPubFlag = PubAmntRelaDutyGrid.getRowColData(i,5);//公共保额使用标志
		var tDutyLimitAmnt = PubAmntRelaDutyGrid.getRowColData(i,7);//责任限额
		
		if (tPubFlag==null || tPubFlag=="") {
			alert("第["+(i+1)+"]行是否使用公共保额标志格式无效！");
			return false;
		}
		
		if (tPubFlag == "1") {
			
			tCount++;
			if(tDutyLimitAmnt == null || tDutyLimitAmnt.trim() == "") {//未录入责任限额
				
				alert("第["+(i+1)+"]行责任限额不能为空！");
				return false;
			} else {//录入了责任限额
				
				if (!isNumeric(tDutyLimitAmnt)) {
					alert("第["+(i+1)+"]行责任限额不是有效的数字！");
					return false;
				}
				
				if (Number(tDutyLimitAmnt)<=0) {
					alert("第["+(i+1)+"]行责任限额应大于0！");
					return false;
				}
				
				var tLimitAmntNew = PubAmntRelaPlanGrid.getRowColData(tPubAmntRow-1,5);//个人限额
				
				if (tLimitAmntNew != null && tLimitAmntNew != "") {
					
					if (Number(tDutyLimitAmnt)>Number(tLimitAmntNew)) {
						alert("第["+(i+1)+"]行责任限额不能大于个人限额！");
						return false;
					}
				} else {
					
					if (Number(tDutyLimitAmnt)>Number(tFixedAmnt)) {
						alert("第["+(i+1)+"]行责任限额不能大于公共保额！");
						return false;
					}
				}
				//add by ZhangC 20150318  校验不能只选择门诊责任
				var tDutyType = PubAmntRelaDutyGrid.getRowColData(i,8);// 1--门诊，0--住院
				tDutyTypeArr.push(tDutyType);
			}
		}
	}
	
	if (!in_Array("0",tDutyTypeArr)) {
		alert("公共保额不能只关联门急诊责任!");
		return false;
	}
	
	if (tCount==0) {
		alert("没有责任使用公共保额！");
		return false;
	}
	return true;
}

/**
 * 判断一个数是否在数组中
 */
function in_Array(search,array){
	
    for(var i in array){
        if(array[i]==search){
            return true;
        }
    }
    return false;
}

/**
 * 当公共保额的保费计算方式为人均保费时，关联方案的人数不能为空
 */
function checkNumPeople() {
	
	var tRow = PlanDetailInfoGrid.getSelNo();
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tRow-1,2);//方案编码
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql47");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tPlanCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		
	} else {
		
		var tRow1 = PubAmntRelaPlanGrid.getSelNo();
		var tPlanCodeRela = PubAmntRelaPlanGrid.getRowColData(tRow1-1,2);//关联方案编码
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql48");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tPlanCodeRela);
		
		var tNumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tNumArr[0][0]=="0" || tNumArr[0][0] == "-1") {
			alert("该关联方案未录入人数！");
			return false;
		}
	}
	
	return true;
}

/**
 * 责任限额是否只读设置
 */
function dealDutyLimitAmnt(cMark,cRow,cPubFlag) {
	
	var rowNumber;
	var tPubFlag;
	if (cMark == "Page") {
		rowNumber = PubAmntRelaDutyGrid.lastFocusRowNo;
		tPubFlag = PubAmntRelaDutyGrid.getRowColData(rowNumber, 5);
	} else if (cMark == "Query") {
		rowNumber = cRow;
		tPubFlag = cPubFlag;
	}
	
	if (tPubFlag == "1") {
		document.getElementById("spanPubAmntRelaDutyGrid"+rowNumber).all('PubAmntRelaDutyGrid7').readOnly = false;
	} else {
		document.getElementById("spanPubAmntRelaDutyGrid"+rowNumber).all('PubAmntRelaDutyGrid7').readOnly = true;
		PubAmntRelaDutyGrid.setRowColData(rowNumber,7,"");
	}
}


function dealRedundantRelaElement(cObj, tArr) {
	
	var tRelaFactor = "Relation";
	if (tArr==null) {
		//未查询出数据,表示无动态因子
	} else {
		
		for (var i=0; i<tArr.length; i++) {
			
			var tCalFactor = tArr[i][0];
			var tFactorCode = tArr[i][1];
			var tFactorName = tArr[i][2];
			var tFieldType = tArr[i][3];
			var tValueType = tArr[i][4];
			var tDefaultValue = tArr[i][5];
			var tFieldLength = tArr[i][6];
			var tMandatoryFlag = tArr[i][8];
			var tDefalutName = tArr[i][9];
			
			if (tDefaultValue==null || tDefaultValue=="") {
				
				tFactorCode = tRelaFactor + tArr[i][1];
				tFieldType = tArr[i][3];
				cObj.all(tFactorCode).value = "";
				if (tFieldType=="1") {//下拉框,而外清空codename
			
					cObj.all(tFactorCode+"Name").value = "";
				}
			} else {//有默认值,赋原值
				
				tFactorCode = tRelaFactor + tArr[i][1];
				tFieldType = tArr[i][3];
				cObj.all(tFactorCode).value = tDefaultValue;
				if (tFieldType=="1") {//下拉框,而外清空codename
			
					cObj.all(tFactorCode+"Name").value = tDefalutName;
				}
			}
		}
	}
	
	return true;
}

/**
 * 试算
 */
function tryCal() {

	fmPub.Operate.value = "TRYCAL";
	
	var tExceptPremType = fm2.ExceptPremType.value;
	if (tExceptPremType==null || tExceptPremType=="") {
		alert("请先选择期望保费类型！");
		return false;
	}
	
	if (tExceptPremType=="03") {
		alert("期望保费类型为费率折扣时，无参考值！");
		return false;
	}
	
	//调用增加方案处理方式
	dealRedundant(fm2, tTranProdType);
	
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!beforeSubmit(fm2, tTranProdType)) {
		return false;
	}
	
	
	
	fm2.action = "./LSQuotTryCalSave.jsp?Operate=TRYCAL&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2); 
}

function afterSubmitTryCal(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		dealAfterSubmit(content);
	}	
}

/**
 * 模糊查询险种
 */
function fuzzyRiskName(Filed,FildName){
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		
		var objCodeName = FildName.value;
		
		if (objCodeName=="") {
			return false;
		}
		mQueryRisk = "1";
		returnShowCode("quotrisk", [Filed, FildName], [0,1], '0');
		//returnShowCode("quotrisk", [Filed, FildName], [0,1], '1');
		
	}	
}

/**
 * 处理期望保费(元)/期望费率/费率折扣的显示
 */
function showExceptPrem(cExceptPremType) {
	
	if (cExceptPremType=="01") {//期望保费
		document.getElementById("idExceptPrem1").style.display = "none";
		document.getElementById("idExceptPrem2").style.display = "";
		document.getElementById("idExceptPrem3").style.display = "none";
		document.getElementById("idExceptPrem4").style.display = "none";
	} else if (cExceptPremType=="02") {//期望费率
		document.getElementById("idExceptPrem1").style.display = "none";
		document.getElementById("idExceptPrem2").style.display = "none";
		document.getElementById("idExceptPrem3").style.display = "";
		document.getElementById("idExceptPrem4").style.display = "none";
	} else if (cExceptPremType=="03") {//费率折扣
		document.getElementById("idExceptPrem1").style.display = "none";
		document.getElementById("idExceptPrem2").style.display = "none";
		document.getElementById("idExceptPrem3").style.display = "none";
		document.getElementById("idExceptPrem4").style.display = "";
	} else {
		document.getElementById("idExceptPrem1").style.display = "";
		document.getElementById("idExceptPrem2").style.display = "none";
		document.getElementById("idExceptPrem3").style.display = "none";
		document.getElementById("idExceptPrem4").style.display = "none";
	}
}
