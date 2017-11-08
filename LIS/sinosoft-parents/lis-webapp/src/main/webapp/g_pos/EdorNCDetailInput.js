/***************************************************************
 * <p>ProName：EdorNCDetailInput.js</p>
 * <p>Title：方案明细信息录入</p>
 * <p>Description：方案明细信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-07-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//方案信息
var tSQLInfo = new SqlClass();

function queryPlanDetail() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql2");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);

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
	if (value1=='edorplan') {

		var tSql = "1";
		tSql = "1 and a.grpcontno=#"+ tPolicyNo + "# and a.edorno=#"+ tEdorNo + "# ";
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

		if (tContPlanType=='00') {//如果是普通险种,看方案是否为公共保额方案,如果是,那么仅下拉出公共保额,如果不是,那么下拉出不含公共 保额的其他普通险种

			if (tPlanType=="00") {//非公共保额

				tSql = "1 and b.startdate<=#"+tCurrentDate+"# and ((case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# or exists (select 1 from ldcode where codetype=#exprisk# and code=#"+tPolicyNo+"# and state=#2#) or exists (select 1 from lcgrppol l where b.riskcode=l.riskcode and l.grpcontno=#"+tPolicyNo+"# )) and a.insuaccflag!=#Y# and b.riskprop!=#I# and not exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#2# and t.code=a.riskcode and t.othersign = #1#)";
				tSql += " and not exists (select 1 from ldcodeexp t where t.codetype = #quotriskexp# and t.code = a.riskcode and t.codeexp =#1# )";//去掉公共保额
			} else if (tPlanType=="01") {

				tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#1# and t.code=a.riskcode)";
			} else {
				return null;
			}
		} else if (tContPlanType=='01') {//建工险

			tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and exists (select 1 from ldcodeexp t where t.codetype=#quotriskexp# and t.codeexp=#2# and t.code=a.riskcode)";
		} else if (tContPlanType=='02') {//账户型
		
			tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and a.insuaccflag=#Y# and b.riskprop!=#I# ";
		} else if (tContPlanType=='03') {//个人产品
		
			tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case  when b.enddate is null  then #9999-12-31# else to_char(b.enddate,#yyyy-mm-dd#) end) >#"+tCurrentDate+"# and b.riskprop=#I# ";
		}

		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
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

		if (tContPlanType=="00") {//普通险种,拓展编码为产品类型+方案类型

			var tPlanType = fm2.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("请选择方案！");
				return false;
			}

			var tSql = "amnttype"+ tContPlanType+tPlanType;
			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		} else if (tContPlanType=="01") {//建工险,拓展编码为产品类型+保费计算方式

			var tPremCalType = fm2.PremCalType.value;
			if (tPremCalType==null || tPremCalType=="") {
				alert("请选择方案！");
				return false;
			}

			var tSql = "amnttype"+ tContPlanType+tPremCalType;

			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}

		} else if (tContPlanType=="03") {//个人险种,拓展编码为产品类型+方案类型

			var tPlanType = fm2.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("请选择方案！");
				return false;
			}

			var tSql = "amnttype"+ tContPlanType+tPlanType;

			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		}
	}

	if (value1=='exceptpremtype') {

		var tSql = "exceptpremtype0";
		var tOccupTypeFlag = fm2.OccupTypeFlag.value;
		
		if (returnType=='0') {
		
			if (tOccupTypeFlag=="2") {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		} else {
		
			if (tOccupTypeFlag=="2") {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
			}
		}
	}
}

function afterCodeSelect(cCodeType, Field) {

	var tCodeType = fmPub.HiddenCodeType.value;
	fmPub.HiddenCodeType.value = "";

	if (cCodeType=="edorplan") {//下拉方案后,如果方案信息与所选方案信息不一致,那么重置方案信息

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

		if (tContPlanType=="01") {

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

			if (tContPlanType!="02") {//账户型产品不进行期望保费处理

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
	} else if (cCodeType=="queryexp") {//选择保额类型后,处理保额动态展示

		if (tCodeType=="amnttype") {
			dealAmntShow(fm2, Field.value);
		}
	}  else if (cCodeType=="trueflag") {
		dealDutyLimitAmnt('Page');
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
		document.getElementById("trRelationRate").style.display = "";
		document.getElementById("divDutyFactorRelation").style.display = "";
	} else {

		document.getElementById("SetRelation").checked = false;
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
	if(tContPlanType=='02'){
		divInfo6.style.display='';
		initPayFeeGrid();
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorNCSql");
		tSQLInfo.setSqlId("EdorNCSql15");
		tSQLInfo.addSubPara(tRiskCode);
		tSQLInfo.addSubPara(tDutyCode);
		tSQLInfo.addSubPara(tPolicyNo);
		tSQLInfo.addSubPara(tSysPlanCode);
		tSQLInfo.addSubPara(tPlanCode);
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);

		turnPage4.queryModal(tSQLInfo.getString(), PayFeeGrid, 1, 1);
	}
}

/**
 * 增加方案明细
 */
function addPlanDetail() {

	if (!beforeSubmit(fm2, tContPlanType)) {
		return false;
	}

	dealRedundant(fm2, tContPlanType);
	if (!verifyForm('fm2')) {
		return false;
	}

	fmPub.Operate.value = "ADDPLANDETAIL";
	fm2.action = "./EdorNCDetailSave.jsp?Operate=ADDPLANDETAIL&PolicyNo="+ tPolicyNo +"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&ContPlanType="+ tContPlanType;
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

	if (!checkSame()) {
		return false;
	}

	dealRedundant(fm2, tContPlanType);

	if (!verifyForm('fm2')) {
		return false;
	}

	if (!beforeSubmit(fm2, tContPlanType)) {
		return false;
	}

	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(tSelNo, 2);
	var tRiskCode = PlanDetailInfoGrid.getRowColData(tSelNo, 12);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(tSelNo, 14);

	fm2.action = "./EdorNCDetailSave.jsp?Operate=MODIFYPLANDETAIL&PolicyNo="+ tPolicyNo+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&OSysPlanCode="+ tSysPlanCode +"&OPlanCode="+ tPlanCode +"&ORiskCode="+ tRiskCode +"&ODutyCode="+ tDutyCode +"&ContPlanType="+ tContPlanType;
	submitForm(fm2);
}

/**
 * 删除方案明细,删除时,需校验所选方案,险种,责任是否一致
 */
function delPlanDetail() {
	fmPub.Operate.value = "DELPLANDETAIL";
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


	fm2.action = "./EdorNCDetailSave.jsp?Operate=DELPLANDETAIL&PolicyNo=" + tPolicyNo + "&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&OSysPlanCode="+ tSysPlanCode +"&OPlanCode="+ tPlanCode +"&ORiskCode="+ tRiskCode +"&ODutyCode="+ tDutyCode +"&ContPlanType="+ tContPlanType;
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
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		dealAfterSubmit(content);
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
	if (tContPlanType=="01") {//建工险

		document.getElementById("idAmnt").style.display = "";
		document.getElementById("idPrem").style.display = "";
		document.getElementById("idFeeRate").style.display = "none";
		document.getElementById("tryCalButton").style.display = "";
		document.getElementById("trRelation").style.display = "none";
	} else if (tContPlanType=="02") {//账户型险种

		document.getElementById("idAmnt").style.display = "none";
		document.getElementById("idPrem").style.display = "none";
		document.getElementById("idFeeRate").style.display = "";
		document.getElementById("tryCalButton").style.display = "none"
		document.getElementById("trRelation").style.display = "none";
	} else if (tContPlanType=="03") {//个人险种

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
	}

	if (cFlag=="0") {
		return;
	}

	// 先进行固定域的赋值以便处理附属被保险人方案
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql7");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	//
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

		tFixedPlanInfo[i++] = "Remark";

		for(var t=0; t<i; t++) {
			document.getElementById(tFixedPlanInfo[t]).value = tArr2[0][t];
		}

		dealAmntShow(fm2, fm2.AmntType.value);
		dealRelationShow(fm2, fm2.RelaShareFlag.value);
	}
	var tSQLElement;

	if (tArr==null || tArr.length==0) {

	} else {

		tSQLElement = getDutySQLElement(tArr);

		//3.传递查询字段进行从表查询
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorNCSql");
		tSQLInfo.setSqlId("EdorNCSql8");
		tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
		tSQLInfo.addSubPara(tPolicyNo);
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
		tSQLInfo.addSubPara(cRiskCode);
		tSQLInfo.addSubPara(cDutyCode);
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);

		var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		//
		if (tArr1==null) {

		} else {//循环对界面要素赋值

			for (var i=0; i<tArr.length; i++) {

				tFactorCode = tArr[i][1];//从表字段
				tFieldType = tArr[i][3];//字段类型
				tValueType = tArr[i][4];//值类型

				if (tFieldType=="1") {

					document.all(tFactorCode).value = tArr1[0][i];
					auotContShowCodeName1(tValueType, tArr1[0][i], cObj, tFactorCode+"Name");
				} else {
					document.all(tFactorCode).value = tArr1[0][i];
				}
			}
		}
	}
		if (document.getElementById("trRelation").style.display=="" && document.getElementById("RelaShareFlag").value=="1") {//只有当附属方案配置展示且被勾选了,才查询附属信息

			//3.传递查询字段进行从表查询
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNCSql");
			tSQLInfo.setSqlId("EdorNCSql20");
			if (tSQLElement==null || tSQLElement=="") {
				tSQLInfo.addSubPara("");//SQL查询字段
			} else {
				tSQLInfo.addSubPara(","+tSQLElement);//SQL查询字段
			}
			tSQLInfo.addSubPara(tPolicyNo);
			tSQLInfo.addSubPara(cSysPlanCode);
			tSQLInfo.addSubPara(cPlanCode);
			tSQLInfo.addSubPara(cRiskCode);
			tSQLInfo.addSubPara(cDutyCode);
			tSQLInfo.addSubPara(tEdorNo);
			tSQLInfo.addSubPara(tEdorType);

			var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			//
			if (tArr2==null) {

			} else {//循环对界面要素赋值

				var tRelaFactor = "Relation";
				var tRelaFactorName = "附属方案";
				document.getElementById("RelaToMain").value = tArr2[0][0];
				document.getElementById("RelaToMainName").value = tArr2[0][1];
				document.getElementById("MainAmntRate").value = tArr2[0][2];
				document.getElementById("RelaAmntRate").value = tArr2[0][3];

			}
			if (tArr==null) {

			} else {

			for (var i=0; i<tArr.length; i++) {

				tFactorCode = tRelaFactor + tArr[i][1];//从表字段
				tFieldType = tArr[i][3];//字段类型
				tValueType = tArr[i][4];//值类型
				var j = i+4;
				if (tFieldType=="1") {

					document.all(tFactorCode).value = tArr2[0][j];
					auotContShowCodeName1(tValueType, tArr2[0][j], cObj, tFactorCode+"Name");
				} else {
					document.all(tFactorCode).value = tArr2[0][j];
				}
			}
		}
	}
}
function auotContShowCodeName1(cCodeType, cCode, cObj, cCodeName) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql12");
	tSQLInfo.addSubPara(cCodeType);
	tSQLInfo.addSubPara(cCode);

	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {

	} else {
		document.all(cCodeName).value=tArr[0][0];
	}
}
/**
 * 提交时处理冗余数据
 */
function dealRedundant(cObj, cContPlanType) {

	if (cContPlanType=="00") {//普通险种

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
	} else if (cContPlanType=="01") {//建工险险种

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
	} else if (cContPlanType=="02") {//账户型险种

		document.getElementById("AmntType").value = "";
		document.getElementById("FixedAmnt").value = "";
		document.getElementById("SalaryMult").value = "";
		document.getElementById("MaxAmnt").value = "";
		document.getElementById("MinAmnt").value = "";
	} else if (cContPlanType=="03") {//个人险种

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
function beforeSubmit(cObj, cContPlanType) {

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

	if (cContPlanType=="02") {//对于账户型产品,无保额类型的相关数据,校验期望初始保费及期望收益率

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

					alert("当期望保费类型为期望保费时，期望保费/期望费率/费率折扣整数位不应超过12位,小数位不应超过2位！");
					return false;
				}
			} else if (tExceptPremType=="02") {//费率

				if (!checkDecimalFormat(tExceptPrem, 4, 8)) {

					alert("当期望保费类型为期望费率时，期望保费/期望费率/费率折扣整数位不应超过4位,小数位不应超过6位！");
					return false;
				}
			} else if (tExceptPremType=="03") {//折扣

				if (!checkDecimalFormat(tExceptPrem, 2, 2)) {

					alert("当期望保费类型为费率折扣时，期望保费/期望费率/费率折扣整数位不应超过2位,小数位不应超过2位！");
					return false;
				}
			}
		}
	}
	var tRiskCode = cObj.RiskCode.value;
	var tDutyCode = cObj.DutyCode.value;
	var tArr = getDutyElementArr(tRiskCode, tDutyCode);
	if (!checkDutyElement(cObj, tArr, "0")) {
		return false;
	}

	if (cContPlanType=="00" && fm2.RelaShareFlag.value=="1") {

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
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql10");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);

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
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql11");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);

	initPubAmntRelaDutyGrid();
	//
	noDiv(turnPage3, PubAmntRelaDutyGrid, tSQLInfo.getString());

	var arrResult = turnPage3.arrDataCacheSet;//将MulLine的值转化为二维数组
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
	var tSysPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,1);//系统方案编码
	var tPlanCode = PubAmntRelaPlanGrid.getRowColData(tRow-1,2);//方案编码
	var tLimitAmnt = PubAmntRelaPlanGrid.getRowColData(tRow-1,5);//个人限额

	fmPub.Operate.value = "SAVEPUBAMNT";
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=SAVEPUBAMNT&PolicyNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&LimitAmnt="+ tLimitAmnt+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType;;
	submitForm(fm2);
}
/**
 * 缴费金额--保存
 */
function saveInfoClick(){
	var tRow = PayFeeGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条缴费项信息！");
		return false;
	}
	if(PayFeeGrid.getRowColData(tRow-1,5)==null||PayFeeGrid.getRowColData(tRow-1,5)==''){
		alert("请录入缴费金额!");
		return false;
	}
	if((!isNumeric(PayFeeGrid.getRowColData(tRow-1,5)))||PayFeeGrid.getRowColData(tRow-1,5)<0){
		alert("缴费金额应为大于等于0的数字!");
		return false;
	}
	if(!beforeSub()){
		return false;
	}
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,1);//系统方案编码
	var tPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,2);//方案编码
	var tRiskCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,12);//险种编码

	fmPub.Operate.value = "SAVEFEE";
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=SAVEFEE&PolicyNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&RiskCode="+ tRiskCode+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType;
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
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=DELETEPUBAMNT&PolicyNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode;
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
	for(var i=0;i < PubAmntRelaDutyGrid.mulLineCount;i++){

		var tPubFlag = PubAmntRelaDutyGrid.getRowColData(i,5);//公共保额使用标志
		var tDutyLimitAmnt = PubAmntRelaDutyGrid.getRowColData(i,7);//责任限额

		if (tPubFlag==null || tPubFlag=="") {
			alert("第["+(i+1)+"]行是否使用公共保额标志格式无效！");
			return false;
		}
		if (tPubFlag == "1") {

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
			}
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
				document.all(tFactorCode).value = "";
				if (tFieldType=="1") {//下拉框,而外清空codename

					document.all(tFactorCode+"Name").value = "";
				}
			} else {//有默认值,赋原值

				tFactorCode = tRelaFactor + tArr[i][1];
				tFieldType = tArr[i][3];
				document.all(tFactorCode).value = tDefaultValue;
				if (tFieldType=="1") {//下拉框,而外清空codename

					document.all(tFactorCode+"Name").value = tDefalutName;
				}
			}
		}
	}

	return true;
}
function showTZInfo(){
	divInfo8.style.display='';
	var tRow = PayFeeGrid.getSelNo();
	var tPayPlanCode =PayFeeGrid.getRowColData(PayFeeGrid.getSelNo()-1, 3);
	var tRiskCode =PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 12);

	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 1);
	var tPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 2);
	var tDutyCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1, 14);
	initTZFeeGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql16");
	tSQLInfo.addSubPara(tRiskCode);
	tSQLInfo.addSubPara(tPayPlanCode);
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	tSQLInfo.addSubPara(tDutyCode);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	turnPage5.queryModal(tSQLInfo.getString(), TZFeeGrid, 1, 1);

	divInfo8.style.display = "";
	if (TZFeeGrid.mulLineCount>1) {
		divInfoButton4.style.display = "";
	} else {
		divInfoButton4.style.display = "none";
	}

}
/**
 * 投资账户信息--保存
 */
function saveAccClick(){
	var tRow = TZFeeGrid.mulLineCount;
	for(var index=0;index<tRow;index++){
		if(TZFeeGrid.getRowColData(index,5)==null||TZFeeGrid.getRowColData(index,5)==''){
			alert("请录入个人投资金额!");
			return false;
		}
		if((!isNumeric(TZFeeGrid.getRowColData(index,5)))||TZFeeGrid.getRowColData(index,5)<0){
			alert("个人投资金额应为大于等于0的数字!");
			return false;
		}
		if(TZFeeGrid.getRowColData(index,6)==null||TZFeeGrid.getRowColData(index,6)==''){
			alert("请录入个人投资分配比例!");
			return false;
		}
		if((!isNumeric(TZFeeGrid.getRowColData(index,6)))||TZFeeGrid.getRowColData(index,6)<0||TZFeeGrid.getRowColData(index,6)>1){
			alert("个人投资分配比例应为大于等于0且小于等于1的数字!");
			return false;
		}
		if(!beforeSub()){
			return false;
		}
	}
	var num = PayFeeGrid.getSelNo();
	var tSysPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,1);//系统方案编码
	var tPlanCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,2);//方案编码
	var tRiskCode = PlanDetailInfoGrid.getRowColData(PlanDetailInfoGrid.getSelNo()-1,12);//险种编码
	var tDutyCode = PayFeeGrid.getRowColData(num-1,1);
	var tPayPlanCode = PayFeeGrid.getRowColData(num-1,3);

	fmPub.Operate.value = "SAVEACC";
	fm2.action = "./EdorNCPubAmntRelaSave.jsp?Operate=SAVEACC&GrpPropNo="+ tPolicyNo +"&SysPlanCode="+ tSysPlanCode +"&PlanCode="+ tPlanCode+"&RiskCode="+ tRiskCode+"&DutyCode="+tDutyCode+"&PayPlanCode="+tPayPlanCode;
	submitForm(fm2);
}
//校验缴费金额需等于初始保费
function beforeSub(){
	var tInitPrem = fm2.InitPrem.value;
	var rowNum = PayFeeGrid.mulLineCount;
	var index=0;
	for(var i=0;i < rowNum;i++){
		var k = new Number(PayFeeGrid.getRowColData(i,5));
		index=index+k;
	}
	if(!(index==tInitPrem)){
		alert("缴费金额之和应等于初始保费!");
		return false;
	}
	return true;
}
//校验个人投资金额之和等于缴费金额
function beforeSubmiUl(){
	var rowNum = PayFeeGrid.getSelNo();
	var index = PayFeeGrid.getRowColData(rowNum-1,5);
	var tRowNum = TZFeeGrid.mulLineCount;
	var index1=0;
	for(var i=0;i < tRowNum;i++){
		var k = new Number(TZFeeGrid.getRowColData(i,5));
		index1=index1+k;
	}
	if(!(index==index1)){
		alert("个人投资金额之和应等于缴费金额!");
		return false;
	}
	var index2=0;
	for(var i=0;i < tRowNum;i++){
		var k = new Number(TZFeeGrid.getRowColData(i,6));
		index2=index2+k;
	}
	if(!(index2==1)){
		alert("个人投资分配比例之和应等于1!");
		return false;
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
	dealRedundant(fm2, tContPlanType);

	if (!verifyForm('fm2')) {
		return false;
	}

	if (!beforeSubmit(fm2, tContPlanType)) {
		return false;
	}

	fm2.action = "./EdorNCTryCalSave.jsp?Operate=TRYCAL&PolicyNo="+ tPolicyNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&ContPlanType="+ tContPlanType+"&EdorNo="+ tEdorNo+"&EdorType="+ tEdorType;
	submitForm(fm2);
}

function afterSubmitTryCal(FlagStr, content) {

	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}

	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	} else {

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
		document.getElementById("divInfo5").style.display = "none";
		document.getElementById("divInfo6").style.display = "none";
		document.getElementById("divInfo8").style.display = "none";

		initPubAmntRelaDutyGrid();
		initPubAmntRelaPlanGrid();
		initPayFeeGrid();
		initTZFeeGrid();

	} else if (tOperate=="SAVEPUBAMNT" || tOperate=="DELETEPUBAMNT"){

		queryPubAmntRelaPlanInfo();
		initPubAmntRelaDutyGrid();
	} else if (tOperate=="TRYCAL") {
		fm2.ExceptPrem.value = o;
	} else if(tOperate=="SAVEFEE" ){
		showTZInfo();
	} else if(tOperate=="SAVEACC"){
		showTZInfo();
	}
}
