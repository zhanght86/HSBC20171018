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
var tTranPremMode;//保费分摊方式
var tPlanDetailOpen;
var tAllDetailOpen;

/**
 * 初始化第二步信息
 */

function initQuotStep2() {
	queryPlanInfo();
	pubInitQuotStep2(fm2, tQuotType, tTranProdType, tTranPremMode, fmEngin);
	if (tTranProdType=="01") {
		queryEnginInfo(fmEngin);
		document.getElementById("divEnginFactor").innerHTML = showEnginFactorDiv(tQuotNo, tQuotBatNo, '0000000000', '000', '0');
		pubShowConditionCheck(fmEngin);
	}
}

/**
 * 初始化方案信息查询
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql14");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 2, 1);//第三位表示使用大数据量,2--大于200小于1000
}

/**
 * 查询工程信息
 */
function queryEnginInfo(cObj) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql23");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		//不做处理
	} else {
		
		var tEnginInfo = new Array();
		var i = 0;
		tEnginInfo[i++] = "EnginName";
		tEnginInfo[i++] = "EnginType";
		tEnginInfo[i++] = "EnginTypeName";
		tEnginInfo[i++] = "EnginArea";
		tEnginInfo[i++] = "EnginCost";
		tEnginInfo[i++] = "EnginPlace";
		tEnginInfo[i++] = "EnginStartDate";
		tEnginInfo[i++] = "EnginEndDate";
		tEnginInfo[i++] = "EnginDesc";
		tEnginInfo[i++] = "EnginCondition";
		tEnginInfo[i++] = "Remark";
		tEnginInfo[i++] = "InsurerName";
		tEnginInfo[i++] = "InsurerType";
		tEnginInfo[i++] = "InsurerTypeName";
		tEnginInfo[i++] = "ContractorName";
		tEnginInfo[i++] = "ContractorType";
		tEnginInfo[i++] = "ContractorTypeName";
		
		for (var t=0; t<i; t++) {
			
			document.getElementById(tEnginInfo[t]).value = tArr[0][t];
		}
	}
}

/**
 * 选择记录后处理
 */
function showPlanInfo() {
	
	pubShowPlanInfo(fm2, tQuotType, tTranProdType);
}

/**
 * 增加方案
 */
function addPlan() {
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("产品类型已发生改变，不可进行该操作！");
		return false;
	}
	dealRedundant(fm2, tQuotType, tTranProdType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tQuotType, tTranProdType)) {//提交前的公用校验
		return false;
	}
	
	fmPub.Operate.value = "ADDPLAN";
	fm2.action = "./LSQuotETPlanSave.jsp?Operate=ADDPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
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
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("产品类型已发生改变，不可进行该操作！");
		return false;
	}
	dealRedundant(fm2, tQuotType, tTranProdType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeModifyPlan(fm2, tTranProdType)) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tQuotType, tTranProdType)) {//提交前的公用校验
		return false;
	}
	
	fmPub.Operate.value = "MODIFYPLAN";
	fm2.action = "./LSQuotETPlanSave.jsp?Operate=MODIFYPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
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
	fm2.action = "./LSQuotETPlanSave.jsp?Operate=DELPLAN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fm2);
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
	} else if (value1=='occuptype2') {//单职业下拉处理
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=="premmode") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql10");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		var tSql = "premmode|"+tArr[0][0];
		if (returnType=='0') {
			
			return showCodeList('queryexp',value2,value3,null,tSql,'codetype|codeexp','1',null);
		} else {
			
			return showCodeListKey('queryexp',value2,value3,null,tSql,'codetype|codeexp','1',null);
		}
	} else if (value1=='occupmidtype') {
		
		var tOccupType = fm2.OccupType.value;
		var conditionOccupMidType = "1 and b.occupationtype= #"+tOccupType+"# ";
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupType)) {
				alert("请先选择职业类别！");
				return false;
			}
			
			return showCodeList('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		} else {
			
			if (isEmpty(fm2.OccupType)) {
				alert("请先选择职业类别！");
				return false;
			}
			return showCodeListKey('occupmidtype',value2,value3,null,conditionOccupMidType,'1','1',null);
		}
	} else if (value1=='occupcode') {
		
		var tOccupType = fm2.OccupType.value;
		var tOccupMidType = fm2.OccupMidType.value;
		var conditionOccupCode = "1 and a.occupationtype= #"+tOccupType+"# and a.occupmidcode = #"+tOccupMidType+"#";
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("请先选择职业中分类！");
				return false;
			}
			return showCodeList('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
		} else {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("请先选择职业中分类！");
				return false;
			}
			return showCodeListKey('occupcode',value2,value3,null,conditionOccupCode,'1','1',null);
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
			
			pubPlanAfterCodeSelect(fm2, tQuotType, tCodeType, Field.value);
			dualEnterpriseRate();
		} else if (tCodeType=="premmode") {
			
			dualEnterpriseRate();
		}
	} else if (cCodeType=="engincaltype") {//选择保费计算方式
		
		pubPlanAfterCodeSelect(fm2, tQuotType, cCodeType, Field.value);
	} else if (cCodeType=="occuptype") {
		
		if (tCodeType=="occuptype1") {
			
			fm2.OccupMidType.value = "";
			fm2.OccupMidTypeName.value = "";
			fm2.OccupCode.value = "";
			fm2.OccupCodeName.value = "";
		} 
	} else if (cCodeType=="occupmidtype") {
		
		fm2.OccupCode.value = "";
		fm2.OccupCodeName.value = "";
	} else if (cCodeType=="occupcode") {
		
		var tOccupCode = fm2.OccupCode.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql45");
		tSQLInfo.addSubPara(tOccupCode);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		fm2.OccupType.value = tArr[0][0];
		fm2.OccupTypeName.value = tArr[0][1];
		fm2.OccupMidType.value = tArr[0][2];
		fm2.OccupMidTypeName.value = tArr[0][3];
	}
}

/**
 * 根据保费分摊方式处理企业负担占比
 */
function dualEnterpriseRate() {
	
	var tPremMode = fm2.PremMode.value;
	if (tPremMode=="1") {//企业负担
		
		fm2.EnterpriseRate.readOnly = true;
		fm2.EnterpriseRate.value = "1";
	} else if (tPremMode=="2") {//个人负担
	
		fm2.EnterpriseRate.readOnly = true;
		fm2.EnterpriseRate.value = "0";
	} else {
		
		fm2.EnterpriseRate.readOnly = false;
	}
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
		
		dealAfterSubmit();
	}
	
	if (fmPub.Operate.value=="UPLOAD") {
		fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
	}
	
}

function dealAfterSubmit() {
	
	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="ADDPLAN" || tOperate=="MODIFYPLAN" || tOperate=="DELPLAN" || tOperate=="UPLOAD") {
		
		queryPlanInfo();
		pubInitQuotStep2(fm2, tQuotType, tTranProdType, tTranPremMode, fmEngin);
		
		if (tTranProdType == "00") {
			
			fm2.PlanType.value = "00";
			fm2.PlanTypeName.value = "普通方案";
			pubPlanAfterCodeSelect(fm2, tQuotType, "plantype", "00");
			dualEnterpriseRate();
		}
	}
	if (tOperate=="UPLOAD") {
		fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
	}
}

/**
 * 上一步
 */
function previousStep() {
	
	goToStep(1);
}

/**
 * 下一步
 */
function nextStep() {
	
	goToStep(3);
}

/**
 * 方案明细维护
 */
function planDetailOpen() {
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("产品类型已发生改变，不可进行该操作！");
		return false;
	}
	
	tPlanDetailOpen = window.open("./LSQuotETPlanDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * 保存工程信息
 */
function saveEngin() {
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("产品类型已发生改变，不可进行该操作！");
		return false;
	}
	if (!beforeSaveEngin(fmEngin)) {
		return false;
	}
		
	fmPub.Operate.value = "SAVEENGIN";
	fmEngin.action = "./LSQuotETEnginSave.jsp?Operate=SAVEENGIN&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmEngin);
}

function chooseOccupFlag(cQuotFlag) {

	pubChooseOccupFlag(fm2, cQuotFlag);
}

/**
 * 产品参数维护
 */
function showProdParam() {
	
	var tProdTypeNew = getProdType(tQuotNo, tQuotBatNo);
	if (tTranProdType != tProdTypeNew) {
		alert("产品类型已发生改变，不可进行该操作！");
		return false;
	}
	window.open("./LSProdParamMain.jsp?ObjType=QUOT&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"产品参数维护",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 点击下拉
 */
function showConditionCheck() {

	pubShowConditionCheck(fmEngin);
}

function showAllDetail() {
	
	tAllDetailOpen = window.open("./LSQuotPlanAllDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
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
		
		var tOccupType = fm2.OccupType.value;
		var tOccupMidType = fm2.OccupMidType.value;
		
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
				
				fm2.OccupType.value = tArr[0][0];
				fm2.OccupTypeName.value = tArr[0][1];
				fm2.OccupMidType.value = tArr[0][2];
				fm2.OccupMidTypeName.value = tArr[0][3];
				fm2.OccupCode.value = tArr[0][4];
				fm2.OccupCodeName.value = tArr[0][5];
			} else {
				var conditionOccupCode = "1 and a.occupationname like #%"+objCodeName+"%# ";
				
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
 * 方案导入
 */
function impPlanSubmit() {
	
	if (tQuotNo == null || tQuotNo == "") {
		alert("获取询价号失败！");
		return false;
	}
	if (tQuotBatNo == null || tQuotBatNo == "") {
		alert("获取批次号失败！");
		return false;
	}
	if (tQuotType == null || tQuotType == "") {
		alert("获取询价类型失败！");
		return false;
	}
	if (tTranProdType == null || tTranProdType == "") {
		alert("获取产品类型失败！");
		return false;
	}
	var filePath = fmupload.UploadPath.value;
	if(filePath == null || filePath == ""){
		alert("请选择导入文件路径！");
		return false;
	}
	
	var indexFirst = filePath.lastIndexOf("\\");
	var indexLast = filePath.lastIndexOf(".xlsx");
	if(indexFirst < 0 || indexLast < 0 || indexLast <= indexFirst) {
		alert("文件路径不合法或选择的文件格式不正确，请重新选择！");
		return false;
	}

	fmPub.Operate.value = "UPLOAD";
	fmupload.action="./LQuotPlanImpSave.jsp?Operate=UPLOAD&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ProdType="+tTranProdType+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fmupload);
}

/**
 * 根据询价类型，产品类型选择下载模版
 */
function downTemplate() {
	
	if (tQuotType=="00" && tTranProdType=="00") {
		//location.href="../template/quot/QuotETPlanImport_00.xlsx";
		location.href="../template/EdorInsuredList1.xlsx";
		
	} else if (tQuotType=="00" && tTranProdType=="01") {
		//location.href="../template/quot/QuotETPlanImport_01.xlsx";
		location.href="../template/EdorInsuredList2.xlsx";
	} else if (tQuotType=="00" && tTranProdType=="02") {
		//location.href="../template/quot/QuotETPlanImport_02.xlsx";
		location.href="../template/EdorInsuredList3.xlsx";
	}
}

/**
 * 展示方案导入信息
 */
function showPlanSubmit() {
	
	window.open("./LSQuotPlanImpInfoMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo + "&TranProdType="+ tTranProdType ,"询价方案导入信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
