/***************************************************************
 * <p>ProName：LSQuotProjUWDetailInput.js</p>
 * <p>Title：项目询价核保明细</p>
 * <p>Description：项目询价核保明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-31
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();
var tTranPremMode;//保费分摊方式
var tPlanDetailOpen;

/**
 * 询价基本信息查询
 */
function queryQuotBasic() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm3.ProjQuotNo.value = tQuotNo;
		fm3.ProjQuotBatNo.value = tQuotBatNo;
		fm3.ProjName.value = strQueryResult[0][0];
		fm3.TargetCust.value = strQueryResult[0][1];
		fm3.NumPeople.value = strQueryResult[0][2];
		fm3.ProjPrePrem.value = strQueryResult[0][3];
		fm3.PreLossRatio.value = strQueryResult[0][4];
		fm3.Partner.value = strQueryResult[0][5];
		fm3.ExpiryDate.value = strQueryResult[0][6];
		tTranProdType = strQueryResult[0][7];
		fm3.ProjProdTypeName.value = strQueryResult[0][8];
		fm3.ProjBlanketFlagName.value = strQueryResult[0][10];
		fm3.ProjDesc.value = strQueryResult[0][11];
		fm3.ElasticFlag.value = strQueryResult[0][12];
		fm3.ElasticFlagName.value = strQueryResult[0][13];
		
		if (tTranProdType=="00") {
			
			document.getElementById("tdElasticFlag1").style.display = '';			
			document.getElementById("tdElasticFlag2").style.display = '';
			document.getElementById("tdElasticFlag3").style.display = '';
			document.getElementById("tdElasticFlag4").style.display = '';
			document.getElementById("tdElasticFlag5").style.display = 'none';
			document.getElementById("tdElasticFlag6").style.display = 'none';
		} else {
			document.getElementById("tdElasticFlag1").style.display = 'none';			
			document.getElementById("tdElasticFlag2").style.display = 'none';
			document.getElementById("tdElasticFlag3").style.display = '';
			document.getElementById("tdElasticFlag4").style.display = '';
			document.getElementById("tdElasticFlag5").style.display = '';
			document.getElementById("tdElasticFlag6").style.display = '';
		}
	}
}

/**
 * 询价渠道类型查询
 */
function querySaleChannel() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara("");
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var tElementCode;//因子编码
	var tElementName;//因子名称
	var tIsSelected;//是否被选中
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>渠道类型</td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][2];
		tIsSelected = tArr[i][3];
		
		tInnerHTML1 += "<input type=checkbox disabled name="+ tElementCode;
		if (tIsSelected=='0') {//询价表中没有保存该因子

			tInnerHTML1 += ">"+ tElementName;
		} else {//询价中保存了该因子

			tInnerHTML1 += " checked>"+ tElementName;
		}
		
		tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	document.getElementById("divSaleChannel").innerHTML = tInnerHTML0;
}

/**
 * 询价中介机构查询
 */
function queryQuotAgency() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage4, AgencyListGrid, tSQLInfo.getString())) {
		initAgencyListGrid();
		return false;
	}
	
	if (AgencyListGrid.mulLineCount>0) {
	
		document.getElementById('divAgencyInfo').style.display = '';
	}
}

/**
 * 询价保障层级划分标准查询
 */
function queryPlanDiv() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var tElementCode;//因子编码
	var tElementName;//因子名称
	var tControlFlag;//是否有录入框
	var tIsSelected;//是否选中
	var tOElementValue;//原始值
	var tNElementValue;//询价值
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>保障层级划分标准</td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];
		tOElementValue = tArr[i][4];
		tNElementValue = tArr[i][5];
		
		tInnerHTML1 += "<input type=checkbox disabled name="+ tElementCode;
		if (tIsSelected=='0') {//询价表中没有保存该因子

			tInnerHTML1 += ">"+ tElementName;
		} else {//询价中保存了该因子

			tInnerHTML1 += " checked>"+ tElementName;
		}
		
		if (tControlFlag=='1') {//存在录入框

			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tControlFlag +"><input style='width:50px' class=readonly name="+ tElementCode +"Value value=\""+ tNElementValue +"\" readonly>";//存在录入框的,置为只读模式
		} else {
			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";	
		}
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	document.getElementById("divPlanDiv").innerHTML = tInnerHTML0;
}

/**
 * 项目型询价缴费方式查询
 */
function queryPayIntv() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql7");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrPayIntv = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var tElementCode;//因子编码
	var tElementName;//因子名称
	var tIsSelected;//是否选中
	var tPayIntv;//缴费方式
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>缴费方式<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArrPayIntv.length; i++) {
	
		tElementCode = tArrPayIntv[i][0];
		tElementName = tArrPayIntv[i][1];
		tIsSelected = tArrPayIntv[i][2];
		tPayIntv = tArrPayIntv[i][3];
		
		tInnerHTML1 += "<input type=checkbox disabled name="+ tElementCode;
		if (tIsSelected=='0') {//询价表中没有保存该因子
			tInnerHTML1 += ">"+ tElementName;
		} else {//询价中保存了该因子

			tInnerHTML1 += " checked>"+ tElementName;
		}
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	document.getElementById("divPayIntvDiv").innerHTML = tInnerHTML0;
}

/**
 * 项目型询价适用机构查询
 */
function queryCom() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql8");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage3, ComListGrid, tSQLInfo.getString())) {
		initComListGrid();
		return false;
	}
	
	if (ComListGrid.mulLineCount>0) {
	
		document.getElementById('divComInfo').style.display = '';
	}
}

/**
 * 项目型询价关联询价号查询
 */
function queryRelaQuot() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql9");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage5, RelaQuotListGrid, tSQLInfo.getString())) {
		initRelaQuotListGrid();
		return false;
	}
	
	if (RelaQuotListGrid.mulLineCount>0) {
	
		document.getElementById('RelaQuotFlag').checked = true;
		document.getElementById('divRelaQuotInfo').style.display = '';
	}
}

/**
 * 初始化第二步信息
 */
function initQuotStep2() {
	
	queryPlanInfo();
	pubInitQuotStep2(fmPlan, tQuotType, tTranProdType, tTranPremMode, '');
}

/**
 * 初始化方案信息查询
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql25");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanInfoGrid();
	turnPage6.queryModal(tSQLInfo.getString(), PlanInfoGrid, 2, 1);
}

/**
 * 选择后处理
 */
function showPlanInfo() {
	
	pubShowPlanInfo(fmPlan, tQuotType, tTranProdType);
}

/**
 * 选择单多职业类别
 */
function chooseOccupFlag(cQuotFlag) {

	pubChooseOccupFlag(fmPlan, cQuotFlag);
}

/**
 * 增加方案
 */
function addPlan() {
	
	dealRedundant(fmPlan, tQuotType, tTranProdType);
	if (!verifyForm('fmPlan')) {
		return false;
	}
	
	if (!pubBeforeSubmit(fmPlan, tQuotType, tTranProdType)) {//提交前的公用校验
		return false;
	}
	
	fmPub.Operate.value = "ADDPLAN";
	fmPlan.action = "./LSQuotProjPlanSave.jsp?Operate=ADDPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmPlan);
}

/**
 * 修改方案
 */
function modifyPlan() {
	
	var tSelNo = PlanInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
	
		alert("请选择要修改的方案记录！");
		return false;
	}
	
	dealRedundant(fmPlan, tQuotType, tTranProdType);
	if (!verifyForm('fmPlan')) {
		return false;
	}
	
	if (!pubBeforeModifyPlan(fmPlan, tTranProdType)) {
		return false;
	}
	
	if (!pubBeforeSubmit(fmPlan, tQuotType, tTranProdType)) {//提交前的公用校验
		return false;
	}
	
	fmPub.Operate.value = "MODIFYPLAN";
	fmPlan.action = "./LSQuotProjPlanSave.jsp?Operate=MODIFYPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmPlan);
}

/**
 * 删除方案
 */
function delPlan() {
	
	var tSelNo = PlanInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
	
		alert("请选择要删除的方案记录！");
		return false;
	}
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	fmPub.Operate.value = "DELPLAN";
	fmPlan.action = "./LSQuotProjPlanSave.jsp?Operate=DELPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmPlan);
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

	document.getElementById("HiddenCodeType").value = value1;
	if (value1=='plantype') {
		
		//var tSql = "plantype|"+tTranProdType;
		var tSql = "1 and codetype=#quotplantype# and codeexp=#"+ tTranProdType +"#";
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='occuptype1') {//单职业下拉处理
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=='occupmidtype') {
		
		var tOccupType = fmPlan.OccupType.value;
		var conditionOccupMidType = "1 and b.occupationtype= #"+tOccupType+"# ";
		if (returnType=='0') {
			
			if (isEmpty(fmPlan.OccupType)) {
				alert("请先选择职业类别！");
				return false;
			}
			
			return showCodeList('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		} else {
			
			if (isEmpty(fmPlan.OccupType)) {
				alert("请先选择职业类别！");
				return false;
			}
			return showCodeListKey('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		}
	} else if (value1=='occupcode') {
		
		var tOccupType = fmPlan.OccupType.value;
		var tOccupMidType = fmPlan.OccupMidType.value;
		var conditionOccupCode = "1 and a.occupationtype= #"+tOccupType+"# and a.occupmidcode = #"+tOccupMidType+"#";
		if (returnType=='0') {
			
			if (isEmpty(fmPlan.OccupMidType)) {
				alert("请先选择职业中分类！");
				return false;
			}
			return showCodeList('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
		} else {
			
			if (isEmpty(fmPlan.OccupMidType)) {
				alert("请先选择职业中分类！");
				return false;
			}
			return showCodeListKey('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
		}
	} else if (value1=='occuptype2') {//单职业下拉处理
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	}
}

/**
 * 下拉后处理
 */
function afterCodeSelect(cCodeType, Field) {
	
	var tCodeType = fmPub.HiddenCodeType.value;
	fmPub.HiddenCodeType.value = "";
	if (cCodeType=="queryexp") {//选择自定义下拉
		
		if (tCodeType=="plantype") {
			
			pubPlanAfterCodeSelect(fmPlan, tQuotType, tCodeType, Field.value);
		}
	} else if (cCodeType=="engincaltype") {//选择保费计算方式
		
		pubPlanAfterCodeSelect(fmPlan, tQuotType, cCodeType, Field.value);
	} else	if (cCodeType=="occuptype") {
		
		if (tCodeType=="occuptype1") {
			
			fmPlan.OccupMidType.value = "";
			fmPlan.OccupMidTypeName.value = "";
			fmPlan.OccupCode.value = "";
			fmPlan.OccupCodeName.value = "";
		}
	} else if (cCodeType=="premmode") {
			
		var tPremMode = fm2.PremMode.value;
		if (tPremMode=="1") {//企业负担
			
			fmPlan.EnterpriseRate.readOnly = true;
			fmPlan.EnterpriseRate.value = "1";
		} else if (tPremMode=="2") {//个人负担
		
			fmPlan.EnterpriseRate.readOnly = true;
			fmPlan.EnterpriseRate.value = "0";
		} else {
			
			fmPlan.EnterpriseRate.readOnly = false;
		}
	}  else if (cCodeType=="occupmidtype") {
		
		fmPlan.OccupCode.value = "";
		fmPlan.OccupCodeName.value = "";
	} else if (cCodeType=="reinsarrange") {
		
		fm3.FaculReason.value = "";
		fm3.FaculReasonName.value = "";
		document.getElementById("FaculOtherDesc").value = "";
		document.getElementById("tFaculOtherDesc").style.display = "none";
		
		if (Field.value == "2" && ( tActivityID=="0800100004" || tActivityID=="0800100005")) {
			
			document.getElementById("tdFacul1").style.display = "";
			document.getElementById("tdFacul2").style.display = "";
			document.getElementById("tdFacul3").style.display = "";
			document.getElementById("tdFacul4").style.display = "";
			document.getElementById("tdFacul5").style.display = "none";
			document.getElementById("tdFacul6").style.display = "none";
			document.getElementById("tdFacul7").style.display = "none";
			document.getElementById("tdFacul8").style.display = "none";
			
		} else {
			document.getElementById("tdFacul1").style.display = "none";
			document.getElementById("tdFacul2").style.display = "none";
			document.getElementById("tdFacul3").style.display = "none";
			document.getElementById("tdFacul4").style.display = "none";
			document.getElementById("tdFacul5").style.display = "";
			document.getElementById("tdFacul6").style.display = "";
			document.getElementById("tdFacul7").style.display = "";
			document.getElementById("tdFacul8").style.display = "";
		}
	} else if (cCodeType=="faculreason") {
		
		document.getElementById("FaculOtherDesc").value = "";
		if (Field.value == "15") {
			document.getElementById("tFaculOtherDesc").style.display = "";
		} else {
			document.getElementById("tFaculOtherDesc").style.display = "none";
		}
	} else if (cCodeType=="occupcode") {
		
		var tOccupCode = fmPlan.OccupCode.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql45");
		tSQLInfo.addSubPara(tOccupCode);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		fmPlan.OccupType.value = tArr[0][0];
		fmPlan.OccupTypeName.value = tArr[0][1];
		fmPlan.OccupMidType.value = tArr[0][2];
		fmPlan.OccupMidTypeName.value = tArr[0][3];
	}
}

/**
 * 方案明细维护
 */
function planDetailOpen() {
	
	tPlanDetailOpen = window.open("./LSQuotETPlanDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * 方案明细一览
 */
function showAllDetail() {
	
	tAllDetailOpen = window.open("./LSQuotPlanAllDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * 产品参数维护
 */
function showProdParam() {
	
	window.open("./LSProdParamMain.jsp?ObjType=QUOT&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"产品参数维护",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 核保要点查询
 */
function queryUWMainPoint() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql21");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWMainPointGrid, 2,1);
}

/**
 * 核保要点展示
 */
function showUWMainPoint() {
	
	divUWMainPointDetail.style.display = "";
	
	if (tActivityID=="0800100002") {
		
		divSubUWAnaly.style.display = "";
		divBranchUWAnaly.style.display = "none";
	} else {
		
		if (tSubUWFlag==1) {
			divSubUWAnaly.style.display = "";
		} else {
			divSubUWAnaly.style.display = "none";
		}
		divBranchUWAnaly.style.display = "";
	}
	
	var tSelNo = UWMainPointGrid.getSelNo()-1;
	
	fm3.SubUWAnaly.value = UWMainPointGrid.getRowColData(tSelNo, 5);
	fm3.BranchUWAnaly.value = UWMainPointGrid.getRowColData(tSelNo, 6);
}

/**
 * 要点分析保存
 */
function analySaveClick() {
	
	var tSelNo = UWMainPointGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm3.UWMainPointRiskCode.value = UWMainPointGrid.getRowColData(tSelNo-1, 1);
	fm3.UWMainPointRuleCode.value = UWMainPointGrid.getRowColData(tSelNo-1, 3);
	
	mOperate = "ANALYSAVE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm3.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate;
	submitForm(fm3);
}

/**
 * 要点分析关闭
 */
function analyCloseClick() {
	
	initUWMainPointGrid();
	queryUWMainPoint();
	divUWMainPointDetail.style.display = "none";
}

/**
 * 其他部门意见查询
 */
function queryOtherOpinion() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql22");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), OtherOpinionGrid, 0,1);
	var rowNum = turnPage2.queryAllRecordCount;//总行数
	if (parseInt(rowNum,10) > 10) {
		document.getElementById("divTurnPage").style.display = '';
	} else {
		document.getElementById("divTurnPage").style.display = 'none';
	}
}

/**
 * 其他部门意见展示
 */
function showOtherOpinion() {
	
	var tSelNo = OtherOpinionGrid.getSelNo()-1;
	
	fm2.OpinionType.value = OtherOpinionGrid.getRowColData(tSelNo, 2);
	fm2.OpinionTypeName.value = OtherOpinionGrid.getRowColData(tSelNo, 3);
	fm2.OpinionDesc.value = OtherOpinionGrid.getRowColData(tSelNo, 4);
}

/**
 * 其他部门意见附件下载
 */
function downFile(parm1,parm2) {
	
	var tFilePath = fm2.all(parm1).all('OtherOpinionGrid6').value;
	var tFileName = fm2.all(parm1).all('OtherOpinionGrid7').value;
	
	if (tFilePath==null || tFilePath=="") {
		return false;
	}
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * 增加其他部门意见
 */
function OtherOpinionAdd() {
	
	mOperate = "OPINIONADD";
	if (!beforeSubmit()) {
		return false;
	}
	
	if (!checkFileSuffix()) {
		return false;
	}
	
	fm2.action = "./LSQuotOtherOpinionSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachType=03&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * 修改其他部门意见
 */
function OtherOpinionModify() {
	
	var tSelNo = OtherOpinionGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm2.OtherOpinionSerialNo.value = OtherOpinionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "OPINIONMODIFY";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm2.action = "./LSQuotOtherOpinionSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachType=03&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * 删除其他部门意见
 */
function OtherOpinionDelete() {
	
	var tSelNo = OtherOpinionGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm2.OtherOpinionSerialNo.value = OtherOpinionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "OPINIONDELETE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm2.action = "./LSQuotOtherOpinionSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachType=03&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
}

/**
 * 各环节意见、结论查询
 */
function queryProcess() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql19");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm3.SubUWOpinion.value = strQueryResult[0][0];
		fm3.SubUWConclu.value = strQueryResult[0][1];
		fm3.SubUWConcluName.value = strQueryResult[0][2];
		fm3.BranchUWOpinion.value = strQueryResult[0][3];
		fm3.BranchUWConclu.value = strQueryResult[0][4];
		fm3.BranchUWConcluName.value = strQueryResult[0][5];
		
		if (strQueryResult[0][5]=="1") {
			
			fm3.BranchUrgentFlag.value = "1";
			fm3.BranchUrgentFlag_CK.checked = true;
		} else {
			
			fm3.BranchUrgentFlag.value = "0";
			fm3.BranchUrgentFlag_CK.checked = false;
		}
		
		fm3.ReinsArrange.value = strQueryResult[0][7];
		fm3.ReinsArrangeName.value = strQueryResult[0][8];
		fm3.UWOpinion.value = strQueryResult[0][9];
		fm3.UWConclu.value = strQueryResult[0][10];
		fm3.UWConcluName.value = strQueryResult[0][11];
		fm1.UWManagerOpinion.value = strQueryResult[0][12];
		fm1.UWManagerConclu.value = strQueryResult[0][13];
		fm1.UWManagerConcluName.value = strQueryResult[0][14];
		fm1.BranchFinalOpinion.value = strQueryResult[0][15];
		fm1.BranchFinalConclu.value = strQueryResult[0][16];
		fm1.BranchFinalConcluName.value = strQueryResult[0][17];
		fm1.SubFinalOpinion.value = strQueryResult[0][18];
		fm1.SubFinalConclu.value = strQueryResult[0][19];
		fm1.SubFinalConcluName.value = strQueryResult[0][20];
	}
}

/**
 * 综合意见保存
 */
function UWOpinionSave() {
	
	mOperate = "UWOPINIONSAVE";
	if (!beforeSubmit()) {
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql24");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tResult != null) {
		var tReinsType = tResult[0][0];
		var tState = tResult[0][1];
		var tReinsArrange = fm3.ReinsArrange.value;
		if (tReinsType != tReinsArrange && tState != '0'&& tReinsArrange != '2') {
			if(!confirm('再保安排发生改变，将删除再保临分方案配置，是否继续?')){
				return false;
			}
		}
	}
	
	fm3.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&TranProdType="+ tTranProdType;
	submitForm(fm3);
}

/**
 * 询价核保提交
 */
function UWSubmit() {
	
	mOperate = "UWSUBMIT";
	if (!beforeSubmit()) {
		return false;
	}
	if (tActivityID=="0800100002" || tActivityID=="0800100003" || tActivityID=="0800100004") {
		
		if (tActivityID=="0800100003" || tActivityID=="0800100004") {
			
			if(!confirm('确定要核保提交吗?')){
				return false;
			}
		}

		fm3.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&TranProdType="+tTranProdType;
		submitForm(fm3);
	} else {
		
		fm1.action = "./LSQuotUWDetailSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&TranProdType="+tTranProdType;
		submitForm(fm1);
	}
}

/**
 * 回目录
 */
function ReturnList() {
	
	if (tActivityID=="0800100002") {
		
		location.href = "./LSQuotSubUWQueryInput.jsp";
	} else if (tActivityID=="0800100003") {
		
		location.href = "./LSQuotBranchUWQueryInput.jsp";
	} else if (tActivityID=="0800100004") {
		
		location.href = "./LSQuotUWQueryInput.jsp";
	} else if (tActivityID=="0800100005") {
		
		location.href = "./LSQuotUWManagerQueryInput.jsp";
	} else if (tActivityID=="0800100006") {
		
		location.href = "./LSQuotBranchFinalQueryInput.jsp";
	} else if (tActivityID=="0800100007") {
		
		location.href = "./LSQuotSubFinalQueryInput.jsp";
	}
}

/**
 * 是否加急勾选框
 */
function FlagCheck() {
	
	if (fm3.BranchUrgentFlag_CK.checked) {
		
		fm3.BranchUrgentFlag.value='1';
	} else {
		
		fm3.BranchUrgentFlag.value='0';
	}
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var   showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		if (mOperate == "PRINT") {
			
			var tFileName = content.substring(content.lastIndexOf('/')+1,content.length); 
			var tFilePath = content;
			
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
		} else {
			
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		    var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			
			if (mOperate=="OPINIONDELETE" || mOperate=="OPINIONMODIFY" || mOperate=="OPINIONADD") {
				
				fm2.OpinionType.value = "";
				fm2.OpinionTypeName.value = "";
				fm2.OpinionDesc.value = "";
				fm2.all("UploadPath").outerHTML = fm2.all("UploadPath").outerHTML;
				queryOtherOpinion();
			} else if (mOperate=="UWOPINIONSAVE") {
				
			} else if (mOperate == "UWSUBMIT") {
				ReturnList();
			} else {
				initForm();
			}
		}
	}
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="ANALYSAVE") {
		
		if (tActivityID=="0800100002" && fm3.SubUWAnaly.value=="") {
			alert("中支公司要点分析不能为空！");
			return false;
		} else if (tActivityID=="0800100003" && fm3.BranchUWAnaly.value=="") {
			alert("分公司要点分析不能为空！");
			return false;
		}
	}
	
	if (mOperate=="OPINIONADD") {
		
		if (fm2.OpinionType.value=="") {
			alert("意见类型不能为空！");
			return false;
		}
		
		if (fm2.UploadPath.value=="" && fm2.OpinionDesc.value=="") {
			alert("附件和其他部门意见不能同时为空！");
			return false;
		}
	}
	
	if (mOperate=="UWOPINIONSAVE") {
		
		if (tActivityID=="0800100002" && fm3.SubUWOpinion.value=="") {
			alert("中支公司综合意见不能为空！");
			return false;
		} else if (tActivityID=="0800100003" && fm3.BranchUWOpinion.value=="") {
			alert("分公司综合意见不能为空！");
			return false;
		} else if (tActivityID=="0800100004") {
			
			if (fm3.ReinsArrange.value=="") {
				alert("再保安排不能为空！");
				return false;
			} else {
				if (fm3.ReinsArrange.value=="2" && fm3.FaculReason.value=="") {
					alert("临分原因不能为空！");
					return false;
				} 
				if (fm3.FaculReason.value=="15" && fm3.FaculOtherDesc.value=="") {
					alert("临分其他描述不能为空！");
					return false;
				} 
			}
			
			if (fm3.UWOpinion.value=="") {
				alert("总公司综合意见不能为空！");
				return false;
			}
		}
	}
	
	if (mOperate=="UWSUBMIT") {
		
		if (tActivityID=="0800100002") {
			
			//校验核保要点是否都已填写要点分析(中支公司)
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
			tSQLInfo.setSqlId("LSQuotUWSql25");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			
			var tPointAll = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPointAll != null) {
				if (tPointAll[0][0] != '0') {
					alert("存在未填写[中支公司要点分析]的核保要点！");
					return false;
				}
			}
			
			if (fm3.SubUWOpinion.value=="") {
				alert("中支公司综合意见不能为空！");
				return false;
			}
			
			if (fm3.SubUWConclu.value=="") {
				alert("中支公司核保结论不能为空！");
				return false;
			}
		} else if (tActivityID=="0800100003") {
			
			//校验核保要点是否都已填写要点分析(分公司)
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
			tSQLInfo.setSqlId("LSQuotUWSql26");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			
			var tPointAll = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPointAll != null) {
				if (tPointAll[0][0] != '0') {
					alert("存在未填写[分公司要点分析]的核保要点！");
					return false;
				}
			}
			
			if (fm3.BranchUWOpinion.value=="") {
				alert("分公司综合意见不能为空！");
				return false;
			}
			
			if (fm3.BranchUWConclu.value=="") {
				alert("分公司核保结论不能为空！");
				return false;
			}
		} else if (tActivityID=="0800100004") {//调整该处顺序,并进行区别校验
			
			if (fm3.UWConclu.value=="") {
				alert("总公司核保结论不能为空！");
				return false;
			}
			
			var tUWConclu = fm3.UWConclu.value;
			if (tUWConclu=="0") {//同意
				
				if (fm3.ReinsArrange.value=="") {
					alert("再保安排不能为空！");
					return false;
				} else {
					if (fm3.ReinsArrange.value=="2" && fm3.FaculReason.value=="") {
						alert("临分原因不能为空！");
						return false;
					} 
					if (fm3.FaculReason.value=="15" && fm3.FaculOtherDesc.value=="") {
						alert("临分其他描述不能为空！");
						return false;
					} 
				}
			
				if (fm3.UWOpinion.value=="") {
					alert("总公司综合意见不能为空！");
					return false;
				}
				
				//校验临分方案信息
				if (!checkFaculPlan()) {
					return false;
				}
				
			} else if (tUWConclu=="1") {//上报
				
				if (fm3.ReinsArrange.value=="") {
					alert("再保安排不能为空！");
					return false;
				} else {
					if (fm3.ReinsArrange.value=="2" && fm3.FaculReason.value=="") {
						alert("临分原因不能为空！");
						return false;
					} 
					if (fm3.FaculReason.value=="15" && fm3.FaculOtherDesc.value=="") {
						alert("临分其他描述不能为空！");
						return false;
					} 
				}
				
				if (fm3.UWOpinion.value=="") {
					alert("总公司综合意见不能为空！");
					return false;
				}
				
				//校验临分方案信息
				if (!checkFaculPlan()) {
					return false;
				}
				
			} else {//问题件及不同意时,意见处均不能为空
				
				if (fm3.UWOpinion.value=="") {
					alert("总公司综合意见不能为空！");
					return false;
				}
			}
		
		} else if (tActivityID=="0800100005") {
			
			if (fm1.UWManagerOpinion.value=="") {
				alert("核保经理意见不能为空！");
				return false;
			}
			
			if (fm1.UWManagerConclu.value=="") {
				alert("核保经理结论不能为空！");
				return false;
			}
		} else if (tActivityID=="0800100006") {
			
			if (fm1.BranchFinalOpinion.value=="") {
				alert("分公司最终意见不能为空！");
				return false;
			}
			
			if (fm1.BranchFinalConclu.value=="") {
				alert("分公司最终结论不能为空！");
				return false;
			}
		} else if (tActivityID=="0800100007") {
			
			if (fm1.SubFinalOpinion.value=="") {
				alert("中支公司最终意见不能为空！");
				return false;
			}
			
			if (fm1.SubFinalConclu.value=="") {
				alert("中支公司最终结论不能为空！");
				return false;
			}
		}
	}
	
	return true;
}

/**
 * 模糊查询工种
 */
function fuzzyQueryOccupCode(Filed,FildName) {
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		var objCodeName = FildName.value;
		if (objCodeName=="") {
			return false;
		}
		
		var tOccupType = fmPlan.OccupType.value;
		var tOccupMidType = fmPlan.OccupMidType.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql44");
		tSQLInfo.addSubPara(objCodeName);
		
		if (tOccupType==null || tOccupType=="") {
			tSQLInfo.addSubPara("");
		} else {
			tSQLInfo.addSubPara(tOccupType);
		}
		if (tOccupMidType==null || tOccupMidType=="") {
			tSQLInfo.addSubPara("");
		} else {
			tSQLInfo.addSubPara(tOccupMidType);
		}
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("不存在该工种！");
			return false;
		} else {
			if (tArr.length == 1) {
				
				fmPlan.OccupType.value = tArr[0][0];
				fmPlan.OccupTypeName.value = tArr[0][1];
				fmPlan.OccupMidType.value = tArr[0][2];
				fmPlan.OccupMidTypeName.value = tArr[0][3];
				fmPlan.OccupCode.value = tArr[0][4];
				fmPlan.OccupCodeName.value = tArr[0][5];
			} else {
				var conditionOccupCode = "1 and a.occupationname like #%"+objCodeName+"%#";
				
				if (tOccupType==null || tOccupType=="") {
				} else {
					conditionOccupCode += " and a.occupationtype= #"+tOccupType+"# ";
				}
				if (tOccupMidType==null || tOccupMidType=="") {
				} else {
					conditionOccupCode += " and a.occupmidcode = #"+tOccupMidType+"# ";
				}
				
				showCodeList('occupcode', [Filed, FildName], [0,1], null,conditionOccupCode, '1', '1',null);
			}
		}
	}
}

/**
 * 展示再保信息
 */
function showReinsInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql23");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tResult != null ) {
		var tReinsType = tResult[0][0];
		if (tReinsType == "2") {
			document.getElementById("tdFacul1").style.display = "";
			document.getElementById("tdFacul2").style.display = "";
			document.getElementById("tdFacul3").style.display = "";
			document.getElementById("tdFacul4").style.display = "";
			document.getElementById("tdFacul5").style.display = "none";
			document.getElementById("tdFacul6").style.display = "none";
			document.getElementById("tdFacul7").style.display = "none";
			document.getElementById("tdFacul8").style.display = "none";
			
			fm3.FaculReason.value = tResult[0][1];
			fm3.FaculReasonName.value = tResult[0][2];
			
			if (tResult[0][1] == "15") {
				document.getElementById("FaculOtherDesc").value = tResult[0][3];
				document.getElementById("tFaculOtherDesc").style.display = "";
			}
			
		} else {
			document.getElementById("tdFacul1").style.display = "none";
			document.getElementById("tdFacul2").style.display = "none";
			document.getElementById("tdFacul3").style.display = "none";
			document.getElementById("tdFacul4").style.display = "none";
			document.getElementById("tdFacul5").style.display = "";
			document.getElementById("tdFacul6").style.display = "";
			document.getElementById("tdFacul7").style.display = "";
			document.getElementById("tdFacul8").style.display = "";
			
			fm3.FaculReason.value = "";
			fm3.FaculReasonName.value = "";
			document.getElementById("FaculOtherDesc").value = "";
			document.getElementById("tFaculOtherDesc").style.display = "none";
		}
		
	}
}

/**
 * 临分方案录入
 */
function faculPlanInput() {
	
	if (fm3.ReinsArrange.value != "2") {
		alert("再保安排非临分，不可录入临分方案！");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql24");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tResult==null) {
		alert("请先保存综合意见信息!");
		return false;
	} else {
		var tReinsType = tResult[0][0];
		if (tReinsType!='2') {
			alert("再保安排非临分，请重新保存综合意见信息!");
			return false;
		}
	}
	
	var tFlag = "";
	if (tActivityID=="0800100004") {
		tFlag = "1";
	} else {
		tFlag = "0";
	}
	window.open("../reinsure/RIFaculPreceptMain.jsp?QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo+ "&Flag="+ tFlag,"再保临分方案配置",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


/**
 * 总公司核保环节，如果是临分再保，提交审核时校验临分方案信息
 */
function checkFaculPlan() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql23");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);

	var tArrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArrResult==null) {
		
		alert("请先保存综合意见信息！");
		return false;
	} else {
		
		var tReinsArrange = tArrResult[0][0];
		if (fm3.ReinsArrange.value  != tReinsArrange) {
			alert("再保安排已发生改变，请保存综合意见信息！");
			return false;
		}
		if (tReinsArrange=="2") {
			
			var tState = tArrResult[0][4];
			if (tState == "1") {//0--未录入，1--录入未完毕，2--录入完毕
				alert("临分方案未录入完毕，请完善！");
				return false;
			}
		}
	}
	return true;
}

/**
 * 校验附件格式是否合法
 */
function checkFileSuffix() {
	
	var tFilePath = fm2.UploadPath.value;
	
	if (tFilePath!=null && tFilePath !="") {
		
		var tFileName = tFilePath.substring(tFilePath.lastIndexOf("\\")+1);
		var pattern2 = /^[^\s\+\&]+$/;
		if (!pattern2.test(tFileName)) {
			alert("上传的文件不能含有空格、‘+’，‘&’！");
			return false;
		}		
		
		var tFileSuffix = tFilePath.substring(tFilePath.lastIndexOf("."));
		if (tFileSuffix==".eml" || tFileSuffix==".EML" || tFileSuffix==".txt" || tFileSuffix==".TXT" ||
			 tFileSuffix==".doc" || tFileSuffix==".DOC" || tFileSuffix==".docx"  || tFileSuffix==".DOCX" ||
		     tFileSuffix==".xls" || tFileSuffix==".XLS" || tFileSuffix==".xlsx"|| tFileSuffix==".XLSX" || 
		     tFileSuffix==".pdf" || tFileSuffix==".PDF" || tFileSuffix==".zip" || tFileSuffix==".ZIP" || 
		     tFileSuffix==".rar" || tFileSuffix==".RAR" || tFileSuffix==".gif" || tFileSuffix==".GIF") {
		} else {
			alert("不支持此文件类型上传！");
			return false;
		}
	} 
	return true;
	
}

/**
 * 查询流程是否经过中支公司核保，处理中支信息展示
 */
function querySubUWFlag() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql27");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	return tResult[0][0];
	
}

/**
 * 查询流程是否经过总公司核保，处理总公司核保信息展示
 */
function queryTotalFlag() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
	tSQLInfo.setSqlId("LSQuotUWSql28");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	return tResult[0][0];
	
}

/**
 * 打印询价单
 */
function printInquiry(o) {
	
	mOperate = "PRINT";
	
	var tPrintType = "";
	if (o == "pdf") {
		tPrintType = "pdf";
	} else if (o == "doc") {
		tPrintType = "doc";
	}
	
	fm3.action = "./LSQuotPrintInquirySave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Operate="+mOperate +"&ProdType="+ tTranProdType+"&PrintType="+tPrintType
	submitForm(fm3);
} 
