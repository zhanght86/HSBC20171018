/***************************************************************
 * <p>ProName：LCContPlanInput.js</p>
 * <p>Title：投保方案信息录入</p>
 * <p>Description：投保方案信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-03-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//方案信息
var tSQLInfo = new SqlClass();
var mOperate = "";
var tTranPremMode;//保费分摊方式
var tPlanDetailOpen;
var tAllDetailOpen;
var tWFlag = "";
var tChFlag = "";
/**
* 加载界面要素
*/
function showPageInfo(){
	initPage();
	queryPolicyFlag();
	queryPlanInfo();
	pubInitContStep2(fm2, tContPlanType, "" , fmEngin);
	if(mOperate=="" ){
		initContInfo();
	}
	
}

//报价号查询
function queryOffer(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tArr!=null){
		fmrela.OfferListNo.value=tArr[0][0];
		tContPlanType = tArr[0][1];
	}
}

//建工险处理
function initContInfo() {
	if("01"==tContPlanType){//建工险显示方案信息
		divEngin.style.display = "";
		queryEnginInfo();
		document.getElementById("divEnginFactor").innerHTML = showEnginFactorDiv(tGrpPropNo,'0000000000', '000', '0');
		pubShowConditionCheck(fmEngin);
	}
}


/**
 * 初始化方案信息查询
 */
function queryEnginInfo() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql4");
	tSQLInfo.addSubPara(tGrpPropNo);
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
			fmEngin.all(tEnginInfo[t]).value = tArr[0][t];
		}
	}
}

//承保机构保存
function periodSave(){
	if(fmperiod.AppManageCom.value==""){
		alert("请选择承保机构!");
		fmperiod.AppManageCom.focus();
		return false;
	}
	if(!checkManagecom()){
		return false;
	}
	if(fmperiod.InsuPeriod.value==""){
		alert("请录入保险区间!");
		return false;
	}
	if(fmperiod.InsuPeriodFlag.value==""){
		alert("请选择保险区间单位!");
		return false;
	}
	if (isEmpty(fmperiod.InsuPeriod)) {
		
		alert("保险期间不能为空！");
		fmperiod.InsuPeriod.focus();
		return false;
	}
	if (!checkNumber(fmperiod.InsuPeriod)) {
		fmperiod.InsuPeriod.focus();
		return false;
	}
	if(fmperiod.PolicyFlag.value==""){
		alert("保单类型不能为空!");
		return false;
	}

	mOperate = "PERIODSAVE";
	fmperiod.action = "./LCContPlanTradSave.jsp?Operate=PERIODSAVE&GrpPropNo="+ fmrela.GrpPropNo.value;
	submitForm(fmperiod);
}

//报价关联
function relaClick(){
	if(!checkPolicyInfo()){
		return false;
	}
	if(fmrela.OfferListNo.value==""){
		alert("报价关联，请录入报价单号");
		return false;
	}
	tQueryFlag = "4";
	mOperate = "RELA";
	fmPub.Operate.value = "RELA";
	fmrela.action = "./LCContPlanTradSave.jsp?Operate=RELA&GrpPropNo="+ fmrela.GrpPropNo.value +"&OfferListNo="+ fmrela.OfferListNo.value;
	submitForm(fmrela);
}


//解除关联
function cancClick(){
	mOperate = "CANC";
	fmrela.Flag.value = 0;
	if(!confirm("确定解除报价关联？")){
		return false;
	}
	tQueryFlag = "3";
	//if(confirm("解除关联是否删除已经关联信息？")){
		fmrela.Flag.value = 1;
	//}else{
		//fmrela.Flag.value = 0;
	//}
	fmPub.Operate.value = "CANC";
	fmrela.action = "./LCContPlanTradSave.jsp?Operate=CANC&GrpPropNo="+ fmrela.GrpPropNo.value + "&Flag="+fmrela.Flag.value;
	submitForm(fmrela);
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
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 保存工程信息
 */
function saveEngin() {
	
	if (!beforeSaveEngin(fmEngin)) {
		return false;
	}
	 fmEngin.action = "./LCContEnginSave.jsp?Operate=SAVEENGIN&GrpPropNo="+ tGrpPropNo +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fmEngin);
}
/**
** 方案确认
*/
function savePlanClick(){
	
	if(!checkPolicyInfo()){
		return false;
	}
	fmsub.action = "./LCContPlanSave.jsp?Operate=SAVEPLAN&GrpPropNo="+ tGrpPropNo +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	mOperate="SAVEPLAN";
	fmPub.Operate.value ="SAVEPLAN";
	submitForm(fmsub);
}


//返回
function turnBack(){
	window.location="./LCContPlanListInput.jsp";
}

/**
 * 初始化方案信息查询
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql1");
	tSQLInfo.addSubPara(tGrpPropNo);

	initPlanInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 1, 1);//第三位表示使用大数据量

}

/**
 * 增加方案
 */
function addPlan() {
	if(!checkPolicyInfo()){
		return false;
	}
	dealRedundant(fm2, tContPlanType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tContPlanType)) {//提交前的公用校验
		return false;
	}
	
	fmPub.Operate.value = "ADDPLAN";
	fm2.action = "./LCContPlanTradSave.jsp?Operate=ADDPLAN&GrpPropNo="+ tGrpPropNo + "&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
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
		initButton();
		dealAfterSubmit();
		initInpBox();
	}	
}


function dealAfterSubmit() {
	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="ADDPLAN") {
		queryPlanInfo();
		pubInitContStep2(fm2, tContPlanType, tTranPremMode, fmEngin);
	} else if (tOperate=="MODIFYPLAN") {
		queryPlanInfo();
		pubInitContStep2(fm2, tContPlanType, tTranPremMode, fmEngin);
	} else if (tOperate=="DELPLAN") {
		queryPlanInfo();
		pubInitContStep2(fm2, tContPlanType, tTranPremMode, fmEngin);
	} else if (tOperate=="SAVEPLAN") {
		turnBack();
	} else {
		initContInfo();
		showPageInfo();
	}
}

/**
 * 选择记录后处理
 */
function showPlanInfo() {
	
	pubShowPlanInfo(fm2, tContPlanType);
}


/**
 * 下拉后处理
 */
function afterCodeSelect(cCodeType, Field) {
	
	var tCodeType = fmPub.HiddenCodeType.value;
	fmPub.HiddenCodeType.value = "";
	if (tCodeType=="premmode") {
				
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
	if (cCodeType=="queryexp") {//选择自定义下拉
		
		if (tCodeType=="quotplantype") {
			
			pubPlanAfterCodeSelect(fm2, tCodeType, Field.value);
		} else if (tCodeType=="premmode") {
				
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
	} else if (cCodeType=="engincaltype") {//选择保费计算方式
		
		pubPlanAfterCodeSelect(fm2, cCodeType, Field.value);
	} else	if (cCodeType=="occuptype") {
		
		if (tCodeType=="occuptype1") {
			
			fm2.OccupMidType.value = "";
			fm2.OccupMidTypeName.value = "";
			fm2.OccupCode.value = "";
			fm2.OccupCodeName.value = "";
		}
	}
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
	
	dealRedundant(fm2, tContPlanType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeModifyPlan(fm2, tContPlanType)) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tContPlanType)) {//提交前的公用校验
		return false;
	}
	
	fmPub.Operate.value = "MODIFYPLAN";
	fm2.action = "./LCContPlanTradSave.jsp?Operate=MODIFYPLAN&GrpPropNo="+ tGrpPropNo  +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID ;
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
	
	fmPub.Operate.value = "DELPLAN";
	fm2.action = "./LCContPlanTradSave.jsp?Operate=DELPLAN&GrpPropNo="+ tGrpPropNo +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
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
	if (value1=='quotplantype') {
		
		var tSql = "quotplantype"+tContPlanType;
		
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	} else if (value1=='occuptype1') {//单职业下拉处理
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=="occupmidtype") {
		
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupType)) {
				alert("请先选择职业类别！");
				return false;
			}
			var tSql = "1 and b.occupationtype =#"+fm2.OccupType.value+"#";
			return showCodeList('occupmidtype',value2,value3,null,tSql,1,'1',null);
		} else {
			
			if (isEmpty(fm2.OccupType)) {
				alert("请先选择职业类别！");
				return false;
			}
			var tSql = "1 and b.occupationtype =#"+fm2.OccupType.value+"#";
			return showCodeListKey('occupmidtype',value2,value3,null,tSql,1,'1',null);
		}
	}	else if (value1=="occupcode") {
		
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("请先选择职业中分类！");
				return false;
			}
			var tSql = "1 and a.occupmidcode =#"+fm2.OccupMidType.value+"#";
			return showCodeList('occupcode',value2,value3,null,tSql,1,'1',null);
		} else {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("请先选择职业中分类！");
				return false;
			}
			var tSql = "1 and a.occupmidcode =#"+fm2.OccupMidType.value+"#";
			return showCodeListKey('occupcode',value2,value3,null,tSql,1,'1',null);
		}
	} else if (value1=='occuptype2') {//单职业下拉处理
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=="premmode") {
		
		if (returnType=='0') {
			return showCodeList('premmode',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('premmode',value2,value3,null,null,null,'1',null);
		}
	}
	if (value1=="comcodeall") {
	 
		var tSql = "1 and comgrade=#03# ";//and managecom like #" + tManageCom + "%%# ";
		if (returnType=='0') {
			return showCodeList('comcodeall',value2,value3,null,tSql,'1','1',180);
		} else {
			return showCodeListKey('comcodeall',value2,value3,null,tSql,'1','1',180);
		}
	}
}

function chooseOccupFlag(cQuotFlag) {

	pubChooseOccupFlag(fm2, cQuotFlag);
}


/**
 * 选择单多职业类别
 */
function pubChooseOccupFlag(cObj, cQuotFlag) {

	if (cQuotFlag=='1') {
		
		cObj.OccupTypeFlag.value = '1';
		document.all("OccupTypeRadio1").checked = true;
		document.all("trOccupType1").style.display = "";
		document.all("trOccupType2").style.display = "none";
	} else if (cQuotFlag=='2') {
		
		cObj.OccupTypeFlag.value = '2';
		document.all("OccupTypeRadio2").checked = true;
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "";
	} else {
		
		cObj.OccupTypeFlag.value = '';
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
	}
}

/**
 * 方案明细维护
 */
function planDetailOpen() {
	
	tPlanDetailOpen = window.open("./LCContPlanDetailMain.jsp?GrpPropNo="+ tGrpPropNo +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&Flag=1&QueryFlag="+tQueryFlag,"1", "height="+screen.availHeight+",width="+screen.availWidth+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

function showAllDetail() {
	
	tAllDetailOpen = window.open("./LCContPlanAllDetailMain.jsp?PolicyNo="+ tGrpPropNo +"&ContPlanType="+ tContPlanType, "1", "height="+screen.availHeight+",width="+screen.availWidth+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
function scriptionRuleOpen(){
	tscriptionRuleOpen = window.open("./LAscriptionRuleMain.jsp?ObjType=CONT&BussType=NB&ContPlanType=02"+"&BussNo="+ tGrpPropNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID+"&QueryFlag="+tQueryFlag, "1", "height="+screen.availHeight+",width="+screen.availWidth+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
function contParaOpen(){
	tscriptionRuleOpen = window.open("./LCContParamMain.jsp?ObjType=CONT&BussType=NB&ContPlanType=02"+"&BussNo="+ tGrpPropNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID+"&QueryFlag="+tQueryFlag, "1", "height="+screen.availHeight+",width="+screen.availWidth+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**判断界面的展示*/
function initPage(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tArr!=null){
		fmrela.OfferListNo.value=tArr[0][0];
		//tContPlanType = tArr[0][1];
	
		if(""==tArr[0][0]){
			if(tQueryFlag=="1"){
				divAddPlanButton.style.display = "none";
			}else{
				divAddPlanButton.style.display = "";
			}
		}else{
			divAddPlanButton.style.display = "none";
		}
		
	}
}

function showPageContent(){
	return true;
}

function showManageCom(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql17");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(tArr!=null){
		fmperiod.InsuPeriod.value=tArr[0][0];
		fmperiod.InsuPeriodFlag.value=tArr[0][1];
		fmperiod.InsuPeriodName.value=tArr[0][2];
		fmperiod.AppManageCom.value=tArr[0][3];
		fmperiod.ManageComName.value=tArr[0][4];
	}else{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
		tSQLInfo.setSqlId("LCContPlanTradSql20");
		tSQLInfo.addSubPara(tGrpPropNo);
		var tScanCom = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tScanCom!=null && tScanCom[0][0]!=""){
			fmperiod.AppManageCom.value=tScanCom[0][0];
			fmperiod.ManageComName.value=tScanCom[0][1];
		}
	}
}


//查询保单类型
function queryPolicyFlag(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tArr!=null){
			if(tArr[0][2]!=null&&tArr[0][2]!=''){
			fmperiod.PolicyFlag.value=tArr[0][2];
			fmperiod.PolicyFlagName.value=tArr[0][3];
		}else{
			fmperiod.PolicyFlag.value= "0";
			fmperiod.PolicyFlagName.value="普通保单";
		}
	}else{
			fmperiod.PolicyFlag.value= "0";
			fmperiod.PolicyFlagName.value="普通保单";
	}
}
function checkPolicyInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql21");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tArr==null||tArr[0][0]==""){
		alert("请先录入投保信息");
		fmperiod.AppManageCom.focus();
		return false;
	}
	
	if(NullToEmpty(fmperiod.AppManageCom.value)==""||NullToEmpty(fmperiod.InsuPeriod.value)==""||NullToEmpty(fmperiod.PolicyFlag.value)=="" ){
		alert("请先录入投保信息");
		fmperiod.AppManageCom.focus();
		return false;
	}
	return true;
}
//查询是否关联
function initRelaFlag(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql22");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tRels = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tRels!=null && tRels[0][0]!=""){
		fmPub.RelaFlag.value=tRels[0][0];
	}
}

//校验承保机构
function checkManagecom(){
	if(fmperiod.AppManageCom.value){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
		tSQLInfo.setSqlId("LCContPlanTradSql23");
		tSQLInfo.addSubPara(fmperiod.AppManageCom.value);
		var tFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tFlag==null||tFlag==""){
			alert("请重新录入正确三级机构!");
			return false;
		}
	}
	return true;
}
//查询报价单号
function queryOfferListNo(){
	window.open("./QueryOfferListMain.jsp?GrpPropNo="+tGrpPropNo,"报价单号查询",'height=500,width=1100,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 业务需求
 */
function showRequest() {

	var tOfferListNo = fmrela.OfferListNo.value;
	if(tOfferListNo==null||tOfferListNo==''){
		alert("请选择报价单号!");
		return false;
	}
	window.open("./LCRequestMain.jsp?OfferListNo="+tOfferListNo,"业务需求",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 特别约定
 */
function showGrpSpec() {

	var tOfferListNo = fmrela.OfferListNo.value;
	if(tOfferListNo==null||tOfferListNo==''){
		alert("请选择报价单号!");
		return false;
	}
	window.open("./LCGrpSpecMain.jsp?OfferListNo="+tOfferListNo,"特别约定",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
