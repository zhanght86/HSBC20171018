/***************************************************************
 * <p>ProName：LCContPolInput.js</p>
 * <p>Title：新单录入</p>
 * <p>Description：新单录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-28
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var tSQLInfo = new SqlClass();
var mOperate = '';
var tBFFlag = "0";//北京的机构
var tValCheckDate = tCurrentDate;

function getPolicyInfo(){
	if(!getPolicyFlag()){
		return false;
	}
	return true;
}


function showAgentDetail() {

	if(divAgentDetailInfo.style.display=='none') {
		divAgentDetailInfo.style.display = '';
	} else {
		divAgentDetailInfo.style.display = 'none';
		initAgentDetailGrid();

	}
}

function showBusinessArea() {

	if(divBusinessAreaInfo.style.display=='none') {
		divBusinessAreaInfo.style.display = '';
	} else {
		divBusinessAreaInfo.style.display = 'none';
		initBusinessAreaGrid();
	}
}
function showAgentCom() {

	if(divAgentComInfo.style.display=='none') {
		divAgentComInfo.style.display = '';
	} else {
		divAgentComInfo.style.display = 'none';
		initAgentComGrid();
	}
}

function showSaler() {

	if(divSalerInfo.style.display=='none') {
		divSalerInfo.style.display = '';
	} else {
		divSalerInfo.style.display = 'none';
		initSalerInfoGrid();
	}
}
function selectIDInfo() {

	if(divIDInfo.style.display=='none') {
		divIDInfo.style.display = '';
	} else {
		divIDInfo.style.display = 'none';
		initIDInfoGrid();
	}
}
function selectPeopleInfo(){

	if(fm.LinkMan.value=='' || fm.Phone2.value ==''){
		alert("请先录入联系人信息才能勾选！");
		fm.TooContect.checked=false;
		return false;
	}

	if(divLinkPeopleInfo.style.display=='none') {
		divLinkPeopleInfo.style.display = '';
	} else {
		divLinkPeopleInfo.style.display = 'none';
	}
}

/**
 * 选择城市前必须先选择省份
 */
function checkProvince(){

	if(fm.Province.value == ""){
		alert("请先选择省份");
		fm.City.value = "";
		fm.CityName.value = "";
	}
}
/**
 * 选择城市前必须先选择省份
 */
function checkBankProvince(){

	if(fm.BankProvince.value == ""){
		alert("请先选择省份");
		fm.BankCity.value = "";
		fm.BankCityName.value = "";
	}
}

function clearCity(){

	fm.City.value = "";
	fm.CityName.value = "";
}

function clearBankCity(){

	fm.BankCity.value = "";
	fm.BankCityName.value = "";
}

function clearCityAndCounty(){

	fm.CityName.value = "";
	fm.CityCode.value = "";
	fm.CountyName.value = "";
	fm.CountyCode.value = "";
}
function clearCounty(){
	fm.CountyName.value = "";
	fm.CountyCode.value = "";
}
/**
 * 清空关联查询的项目
 */
function clearInput(codeInput,nameInput) {
	codeInput.value = "";
	nameInput.value = "";
}

//基本投保信息
function initEntry(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql3");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.ManageCom.value=tPropEntry[0][0];
		fm.ManageComName.value=tPropEntry[0][1];
		fm.SaleDepart.value=tPropEntry[0][2];
		fm.SaleDepartName.value=tPropEntry[0][3];
		fm.ChnlType.value=tPropEntry[0][4];
		fm.ChnlTypeName.value=tPropEntry[0][5];
		fm.SaleChnl.value=tPropEntry[0][6];
		fm.SaleChnlName.value=tPropEntry[0][7];
		//fm.ProjectType.value=tPropEntry[0][8];
		fm.PolicyAppDate.value=tPropEntry[0][9];
		fm.ValDateType.value=tPropEntry[0][10];
		fm.ValdateTypeName.value=tPropEntry[0][11];
		fm.ValDate.value=tPropEntry[0][12];
		fm.RenewFlag.value=tPropEntry[0][13];
		fm.RenewFlagName.value=tPropEntry[0][14];
		fm.PayintvType.value=tPropEntry[0][15];
		fm.payintvName.value=tPropEntry[0][16];
		if(tPropEntry[0][17]=='0'){
			fm.InsuPeriod.value='';
		}else{
			fm.InsuPeriod.value=tPropEntry[0][17];
		}
		fm.InsuPeriodFlagName.value=tPropEntry[0][18];
		fm.InsuPeriodFlag.value=tPropEntry[0][19];
		fm.GrpSpec.value = tPropEntry[0][20];
		//fm.ProjectTypeName.value=tPropEntry[0][21];
		if(fm.ValDateType.value=='0'){
			valDatename.style.display="none";
			valDatename1.style.display="none";
		}else {
			valDatename.style.display="";
			valDatename1.style.display="";
		}
	}
	fm.Coinsurance.value=tPropEntry[0][22];
	fm.CoinsuranceName.value=tPropEntry[0][23];
	fm.SumPrems.value=tPropEntry[0][24];
	//fm.Segment1.value=tPropEntry[0][25];
}
//投保人资料
function initEntry1(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql4");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {

		fm.GrpName.value=tPropEntry[0][0];
		fm.SumNumPeople.value=tPropEntry[0][1];
		fm.MainBusiness.value=tPropEntry[0][2];
		fm.GrpNature.value=tPropEntry[0][3];
		fm.GrpNatureName.value=tPropEntry[0][4];
		fm.BusiCategory.value=tPropEntry[0][5];
		fm.BusiCategoryName.value=tPropEntry[0][6];
		fm.SocialInsuCode.value=tPropEntry[0][7];
		fm.Phone1.value=tPropEntry[0][8];
		fm.Fax.value=tPropEntry[0][9];
		fm.Corporation.value=tPropEntry[0][10];
		fm.CorIDType.value=tPropEntry[0][11];
		fm.CorIDTypeName.value=tPropEntry[0][12];
		fm.CorID.value=tPropEntry[0][13];
		fm.CorIDExpiryDate.value=tPropEntry[0][14];
		fm.AppntNo.value=tPropEntry[0][15];
	}
}
//初始化省市县
function initEntry2(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql5");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.ProvinceCode.value=tPropEntry[0][1];
		fm.ProvinceName.value=tPropEntry[0][2];
		fm.CityCode.value=tPropEntry[0][3];
		fm.CityName.value=tPropEntry[0][4];
		fm.CountyCode.value=tPropEntry[0][5];
		fm.CountyName.value=tPropEntry[0][6];
		fm.DetailAddress.value=tPropEntry[0][7];
		fm.ZipCode.value=tPropEntry[0][8];
	}
}
//初始化联系人信息
function initEntry3(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql6");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.LinkMan.value=tPropEntry[0][0];
		fm.Phone2.value=tPropEntry[0][1];
		fm.EMail.value=tPropEntry[0][2];
		fm.MobilePhone.value=tPropEntry[0][3];
		fm.Department.value=tPropEntry[0][4];
		fm.IDType.value=tPropEntry[0][5];
		fm.IDTypeName.value=tPropEntry[0][6];
		fm.IDNo.value=tPropEntry[0][7];
		fm.IDEndDate.value=tPropEntry[0][8];
	}
}
//初始化工程信息
function initEntry4(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql7");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.EnginName.value=tPropEntry[0][0];
		fm.ContractorName.value=tPropEntry[0][1];
		fm.ContractorType.value=tPropEntry[0][2];
		fm.ContractorTypeName.value=tPropEntry[0][3];
		fm.EnginPlace.value=tPropEntry[0][4];
		fm.DetailDes.value=tPropEntry[0][5];
		fm.EnginStartDate.value=tPropEntry[0][6];
		fm.EnginEndDate.value=tPropEntry[0][7];
		fm.EnginCost.value=tPropEntry[0][8];
		fm.EnginArea.value=tPropEntry[0][9];
		fm.EnginType.value=tPropEntry[0][10];
		fm.EnginTypeName.value=tPropEntry[0][11];
		fm.PremCalMode.value=tPropEntry[0][12];
		fm.PremCalModeName.value=tPropEntry[0][13];
		fm.PremFeeRate.value=tPropEntry[0][14];
		fm.FirstPrem.value=tPropEntry[0][15];
		fm.ContractorPeoples.value=tPropEntry[0][16];
		if(tPropEntry[0][17]=='1'){
			fm.EnginCondition.checked='true';
		}
		if(tPropEntry[0][18]=='1'){
			fm.SafeProve.checked='true';
		}
	}
}
//银行信息初始化
function initEntry5(){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContSql");
		tSQLInfo.setSqlId("LCContSql18");
		tSQLInfo.addSubPara(tGrpPropNo);
		var kPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(kPropEntry==null||kPropEntry==''){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContSql");
		tSQLInfo.setSqlId("LCContSql17");
		tSQLInfo.addSubPara(tGrpPropNo);
		var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tPropEntry==null) {
			return false;
		} else {
			fm.AccName.value=tPropEntry[0][0];
			fm.BankAccNo.value=tPropEntry[0][1];
			fm.PayType.value=tPropEntry[0][2];
			fm.PayTypeName.value=tPropEntry[0][3];
		}
	}else{
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContSql");
		tSQLInfo.setSqlId("LCContSql8");
		tSQLInfo.addSubPara(tGrpPropNo);
		var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tPropEntry==null) {
			return false;
		} else {
			fm.BankCode.value=tPropEntry[0][0];
			fm.BankCodeName.value=tPropEntry[0][1];
			fm.BankProvince.value=tPropEntry[0][2];
			fm.BankProvinceName.value=tPropEntry[0][3];
			fm.BankCity.value=tPropEntry[0][4];
			fm.BankCityName.value=tPropEntry[0][5];
			fm.AccName.value=tPropEntry[0][6];
			fm.BankAccNo.value=tPropEntry[0][7];
			fm.PayType.value=tPropEntry[0][8];
			fm.PayTypeName.value=tPropEntry[0][9];
		}
	}
}
//计价方式信息
function initEntry12(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql15");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.PricingMode.value=tPropEntry[0][0];
		fm.PricingModeName.value=tPropEntry[0][1];
	}
}
//客户经理
function initEntry6(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql9");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage1.queryModal(tSQLInfo.getString(), AgentDetailGrid, 1, 1);
	if(!turnPage1.strQueryResult){
		initAgentDetailGrid();
	}
}
//业绩归属地
function initEntry7(){
	divBusinessAreaInfo.style.display='';
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql10");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage4.queryModal(tSQLInfo.getString(), BusinessAreaGrid, 1, 1);

	if(!turnPage4.strQueryResult){
		initBusinessAreaGrid();
		fm.BusinessArea.checked='';
		divBusinessAreaInfo.style.display='none';
	}else{
		fm.BusinessArea.checked='true';
	}
}

//中介机构
function initEntry8(){
	divAgentComInfo.style.display='';

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql11");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage2.queryModal(tSQLInfo.getString(), AgentComGrid, 1, 1);
	if(!turnPage2.strQueryResult){
		initAgentComGrid();
		fm.AgentCom.checked='';
		divAgentComInfo.style.display='none';
	}else{
		fm.AgentCom.checked='true';
	}
}
//代理人
function initEntry9(){
	divSalerInfo.style.display='';

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql12");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage3.queryModal(tSQLInfo.getString(), SalerInfoGrid, 1, 1);
	if(!turnPage3.strQueryResult){
		initSalerInfoGrid();
		fm.SalerInfo.checked='';
		divSalerInfo.style.display='none';
	}else{
		fm.SalerInfo.checked='true';
	}
}
//单位证件
function initEntry10(){
	divIDInfo.style.display='';

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql13");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage5.queryModal(tSQLInfo.getString(), IDInfoGrid, 1, 1);
	if(!turnPage5.strQueryResult){
		initIDInfoGrid();
		fm.IDInfo.checked='';
		divIDInfo.style.display='none';
	}else{
		fm.IDInfo.checked='true';
	}

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql24");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tResu = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tResu!=null) {
		fm.GrpIDType.value=tResu[0][0];
		fm.GrpIDTypeName.value=tResu[0][1];
		fm.GrpID.value=tResu[0][2];
		fm.GrpIDExpiryDate.value=tResu[0][4];
	}
}
//多联系人
function initEntry11(){
	divLinkPeopleInfo.style.display='';

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql14");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage6.queryModal(tSQLInfo.getString(), TooContectGrid, 1, 1);
	if(!turnPage6.strQueryResult){
		initTooContectGrid();
		fm.TooContect.checked='';
		divLinkPeopleInfo.style.display='none';
	}else{
		fm.TooContect.checked='true';
	}
}
/**
 * 保存投保人资料
 */
function saveClick(){

	AgentDetailGrid.delBlankLine();
	BusinessAreaGrid.delBlankLine();
	AgentComGrid.delBlankLine();
	SalerInfoGrid.delBlankLine();
	IDInfoGrid.delBlankLine();
	TooContectGrid.delBlankLine();
	clearAddress();
	if(!checkManageCom(fm.ManageCom.value)){
		return false;
	}
	trimBlank(fm);
	if(!verifyInput2()){
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql22");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara("110000");
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tPropEntry!=null && tPropEntry[0][0]=="1") {
		tBFFlag = "1";
		if(fm.SocialInsuCode.value==''){
			alert("管理机构为北京分公司的保单【社会保险登记号】不能为空!");
			return false;
		}
	}else{
		tBFFlag = "0";
	}

	if(!checkPeriod()){
		return false;
	}

	// 校验客户经理信息
	AgentDetailGrid.delBlankLine();
	var tCount = AgentDetailGrid.mulLineCount;
	var tMoney = 0;
	if(parseInt(tCount)==0){
		alert("请录入客户经理信息!");
		return false;
	}
	if(parseInt(tCount)>0){
		if(!AgentDetailGrid.checkValue("AgentDetailGrid")){
			return false;
		}
		for (var i=0; i<tCount;i++){

			if(AgentDetailGrid.getRowColData(i,1)==null||AgentDetailGrid.getRowColData(i,1)==''){
				alert("请输入客户经理！");
				return false;
			}
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContSql");
			tSQLInfo.setSqlId("LCContSql25");
			tSQLInfo.addSubPara(AgentDetailGrid.getRowColData(i,1));
			tSQLInfo.addSubPara(tValCheckDate);
			tSQLInfo.addSubPara(tValCheckDate);
			var tAgentState = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

			if (tAgentState==null || tAgentState[0][0]!="1") {
				if(tBFFlag=="1"){
					alert("承保地客户经理【"+AgentDetailGrid.getRowColData(i,2)+"】资格证不存在或者不在有效期！");
					return false;
				}else {
					if(!confirm("承保地客户经理【"+AgentDetailGrid.getRowColData(i,2)+"】资格证不存在或者不在有效期！是否继续？")){
						return false;
					}
				}
			}

			if(AgentDetailGrid.getRowColData(i,4)==null||AgentDetailGrid.getRowColData(i,4)==''){
				alert("请输入客户经理佣金比例！");
				return false;
			}
			if(AgentDetailGrid.getRowColData(i,4)>1||AgentDetailGrid.getRowColData(i,4)<=0){
				alert("客户经理比例必须大于0小于1");
				return false;
			}

			var n=AgentDetailGrid.getRowColData(i,4).toString().split(".");

			if(typeof(n[1])!="undefined"&&n[1].length>2){
				alert("客户经理佣金比例小数点后只能输入两位");
				return false;
			}

			for(var j=i+1;j<tCount;j++){
				if(AgentDetailGrid.getRowColData(i,1)==AgentDetailGrid.getRowColData(j,1)){
					alert("请勿录入重复的客户经理！");
					return false;
				}
			}

			tMoney+=Number(AgentDetailGrid.getRowColData(i,4));
		}

		if(tMoney !=1){
			alert("客户经理佣金比例之和应该等于1");
			return false;
		}
	}

	// 校验增加业绩归属信息
	BusinessAreaGrid.delBlankLine();
	var tAreaRow = BusinessAreaGrid.mulLineCount;
	if(fm.BusinessArea.checked&&tAreaRow==0){
		alert("请录入业绩归属地信息!");
		return false;
	}
	/**
	if(fm.BusinessArea.checked==true){
		if(fm.SalerInfo.checked==false){
			alert("如果勾选录入【业绩归属地信息】必须勾选【代理人信息】!");
			return false;
		}
	}
	**/
	var tAreaRate = 0;
	if(fm.BusinessArea.checked&&tAreaRow>0){
		if(!checkBusinessArea())
		return false;
	}

	// 校验中介机构信息
	AgentComGrid.delBlankLine();
	var tComRow = AgentComGrid.mulLineCount;
	if(fm.ChnlType.value=='2' ||fm.ChnlType.value=='3' || fm.ChnlType.value=='4' || fm.ChnlType.value=='5'){
		if(fm.AgentCom.checked==false || tComRow<1){
			alert("中介业务，请录入中介机构信息!");
			return false;
		}
	}else {
		if(tComRow>0){
			//alert("非中介业务，不需要录入中介机构信息!");
			//return false;
		}
	}

	var tComRate = 0;
	if(fm.AgentCom.checked){
		if(!checkAgentCom()){
			return false;
		}
	}

	// 校验代理人信息
	SalerInfoGrid.delBlankLine();
	var tSalerRow =SalerInfoGrid.mulLineCount;
	/**
	if(fm.SalerInfo.checked&&tSalerRow==0){
		alert("请录入代理人信息!");
		return false;
	}

	/**
	if(fm.ChnlType.value=='6'){
		if(fm.SalerInfo.checked==false){
			alert("个人代理（综开）需勾选【代理人信息】!");
			return false;
		}
	}
	**/

//	if(fm.SalerInfo.checked && tSalerRow>0){
//		if(!checkSalerInfo())
//		 return false;
//	}

	var ttCurrentDate = fm.tCurrentDate.value;
	if(fm.CorIDExpiryDate.value !=''){
		if(fm.CorIDExpiryDate.value<ttCurrentDate){
			alert("法人证件有效止期需晚于当前日期");
			return false;
		}
	}
	if(fm.IDEndDate.value !=''){
	if(fm.IDEndDate.value<ttCurrentDate){
		alert("联系人证件有效止期需晚于当前日期");
		return false;
	}
}
	if(fm.CorIDType.value=='0'){
		if(!checkIdCard(fm.CorID.value)){
			return false;
		}
	}
	// 增加北京健康险投保人必须录入联系地址
	if(!checkCity()){
		return false;
	}
	if(fm.DetailAddress.value==""){
		alert("请录入地址信息!");
		fm.DetailAddress.focus();
		return false;
	}
	// 校验单位证件信息
	IDInfoGrid.delBlankLine();
	var tIDInfoRow = IDInfoGrid.mulLineCount;
	if(fm.IDInfo.checked && tIDInfoRow==0){
		alert("请录入单位证件信息!");
		return false;
	}
	if(fm.IDInfo.checked && tIDInfoRow>0){
		if(!checkIDInfo()){
			return false;
		}
	}
	//校验多联系人信息
	TooContectGrid.delBlankLine();
	var tTooContectRow = TooContectGrid.mulLineCount;
	if(fm.TooContect.checked && tTooContectRow==0){
		alert("请录入多联系人信息!");
		return false;
	}
	if(fm.TooContect.checked && tTooContectRow>0){
		if(!checkTooContect())
		return false;
	}
	var bankFlag = 0;
	if(fm.BankCode.value!=null || fm.BankCode.value!=""){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContCommonSql");
		tSQLInfo.setSqlId("LCContCommonSql9");
		tSQLInfo.addSubPara(fm.BankCode.value);
		bankFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	}

	// 校验银行信息
	var tflag1 = false;
	if(bankFlag =='1'){
		if(fm.BankCode.value=='' && fm.BankProvince.value==''&& fm.BankCity.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
			tflag1= true;
		}else if(fm.BankCode.value !=''&& fm.BankProvince.value!=''&& fm.BankCity.value!='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
			tflag1= true;
		}else{
			tflag1=false;
		}
	}else {
		if(fm.BankCode.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
			tflag1= true;
		}else if(fm.BankCode.value !='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
			tflag1= true;
		}else{
			tflag1=false;
		}
	}

	if(tflag1==false){
		alert("请填写银行相关信息！");
		return false;
	}

	if(tContPlanType=='01'){
		if(!checkEngInfo()){
			return false;
		}
	} else if(tContPlanType=='02'){

		if (fm.PayintvType.value!="-1") {
			alert("账户型保单缴费方式只能选择不定期缴！");
			return false;
		}

		//if(isEmpty(fm.PricingMode)){
			//alert("计价方式不能为空!");
			//return false;
		//}
	}
	if(!checkPayType()){
		return false;
	}

	if(fm.GrpSpec.value.length>1500){
		alert("保单特别约定录入过长，请录入1500字符以内!");
		return false;
	}

	if(!checkSale()){
		return false;
	}
	if (fm.ProvinceCode.value!=null && fm.ProvinceCode.value!="") {

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContSql");
		tSQLInfo.setSqlId("LCContSql22");
		tSQLInfo.addSubPara(fm.ManageCom.value);
		tSQLInfo.addSubPara(fm.ProvinceCode.value);
		var tProvinceArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

		if (tProvinceArr==null) {
			if (!confirm("承保机构与投保地址不符，是否确定？")) {
				return false;
			}
		}
	}

	fm.action = "./LCGrpContPolSave.jsp?Operate=INSERT";
	submitForm(fm,"INSERT");

}
//校验中介机构是否和销售部门销售类型销售渠道相符
function checkSale(){
	
	var tAgentComRow = AgentComGrid.mulLineCount;
	for(var i=0;i<tAgentComRow;i++){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCGrpQueryManagerSql");
		tSQLInfo.setSqlId("LCGrpQueryManagerSql7");
		tSQLInfo.addSubPara(AgentComGrid.getRowColData(i,1));
		tSQLInfo.addSubPara(fm.SaleChnl.value);
		tSQLInfo.addSubPara(fm.ManageCom.value);
		var tAgentCom = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
		if (!tAgentCom=='1') {
			alert("中介机构和销售渠道信息不符！");
			return false;
		}	
	}
	return true;
}
function checkTooContect(){
	if(!TooContectGrid.checkValue("TooContectGrid")){
		return false;
	}
	var tTooContectRow = TooContectGrid.mulLineCount;

	for(var i=0;i<tTooContectRow;i++){

		if(TooContectGrid.getRowColData(i,7)!=null&&TooContectGrid.getRowColData(i,7)!=''){

			if(TooContectGrid.getRowColData(i,6)==null||TooContectGrid.getRowColData(i,6)==''){
				alert("请录入联系人证件类型!");
				return false;
			}
		}
		if(TooContectGrid.getRowColData(i,6)!=null&&TooContectGrid.getRowColData(i,6)!=''){

			if(TooContectGrid.getRowColData(i,7)==null||TooContectGrid.getRowColData(i,7)==''){
				alert("请录入联系人证件号码!");
				return false;
			}
		}
		if(TooContectGrid.getRowColData(i,6)=='01'){
			if(!checkIdCard(TooContectGrid.getRowColData(i,7))){
				return false;
			}
		}
		if(TooContectGrid.getRowColData(i,8)!=null&&TooContectGrid.getRowColData(i,8)!=''){
			if(TooContectGrid.getRowColData(i,8)<tCurrentDate){
				alert("多联系人证件有效止期需大于当前日期！");
				return false;
			}
		}
	}
	return true;
}


function checkIDInfo(){

	if(!IDInfoGrid.checkValue("IDInfoGrid")){
		return false;
	}

	var tIDInfoRow = IDInfoGrid.mulLineCount;
	for(var i=0;i<tIDInfoRow;i++){
		if(IDInfoGrid.getRowColData(i,1)==fm.GrpIDType.value){
				alert("单位多证件列表与录入的【单位证件类型】重复，请进行调整！");
				return false;
		}
		for(var j=i+1;j<tIDInfoRow;j++){
			if(IDInfoGrid.getRowColData(i,1)==IDInfoGrid.getRowColData(j,1)){
				alert("请勿录入重复【证件类型编码】！");
				return false;
			}
		}
	}
	for (var i=0; i<tIDInfoRow;i++){
		if(IDInfoGrid.getRowColData(i,5)!=null&&IDInfoGrid.getRowColData(i,5)!=''){
			if(IDInfoGrid.getRowColData(i,5)<tCurrentDate){
				alert("单位证件有效止期需大于当前日期！");
				return false;
			}
		}
		if((IDInfoGrid.getRowColData(i,5)!=null&&IDInfoGrid.getRowColData(i,5)!='')&&(IDInfoGrid.getRowColData(i,4)!=null&&IDInfoGrid.getRowColData(i,4)!='')){
			if(IDInfoGrid.getRowColData(i,5)<IDInfoGrid.getRowColData(i,4)){
				alert("证件有效起期需小于证件有效止期！");
				return false;
			}
		}
	}
	return true;
}


function checkBusinessArea(){
	if(!BusinessAreaGrid.checkValue("BusinessAreaGrid")){
		return false;
	}

	var tAreaRow = BusinessAreaGrid.mulLineCount;
	var tAreaRate = 0;
	var tAreaRate2 =0;
	for(var i=0;i<tAreaRow;i++){
		if(BusinessAreaGrid.getRowColData(i,1)==null||BusinessAreaGrid.getRowColData(i,1)==''){
			alert("请输入业绩归属地【归属地代码】！");
			return false;
		}

		if(BusinessAreaGrid.getRowColData(i,3)==null||BusinessAreaGrid.getRowColData(i,3)==''){
			alert("请输入业绩归属地【归属比例】！");
			return false;
		}

		if(BusinessAreaGrid.getRowColData(i,3)>1||BusinessAreaGrid.getRowColData(i,3)<=0){
			alert("业绩归属地【归属比例】必须大于0小于1");
			return false;
		}

		var n=BusinessAreaGrid.getRowColData(i,3).toString().split(".");

		if(typeof(n[1])!="undefined"&&n[1].length>2){
			alert("业绩归属地【归属比例】小数点后只能输入两位");
			return false;
		}

		if(BusinessAreaGrid.getRowColData(i,4)==null||BusinessAreaGrid.getRowColData(i,4)==''){
			alert("请输入业绩归属地【归属地客户经理代码】！");
			return false;
		}
		
		if(BusinessAreaGrid.getRowColData(i,6)==null||BusinessAreaGrid.getRowColData(i,6)==''){
			alert("请输入业绩归属地【分佣比例】！");
			return false;
		}

		if(BusinessAreaGrid.getRowColData(i,6)>1||BusinessAreaGrid.getRowColData(i,6)<=0){
			alert("业绩归属地归属【分佣比例】必须大于0小于1");
			return false;
		}

		var n=BusinessAreaGrid.getRowColData(i,6).toString().split(".");

		if(typeof(n[1])!="undefined"&&n[1].length>2){
			alert("业绩归属地归属【分佣比例】小数点后只能输入两位");
			return false;
		}
		for(var j=i+1;j<tAreaRow;j++){
			if(BusinessAreaGrid.getRowColData(i,4)==BusinessAreaGrid.getRowColData(j,4)){
				alert("请勿录入重复【归属地客户经理代码】！");
				return false;
			}
		}
	}
	return true;
}

function checkAgentCom(){
	if(!AgentComGrid.checkValue("AgentComGrid")){
		return false;
	}

	var tComRow = AgentComGrid.mulLineCount;
	var tComRate = 0;

	for(var i=0;i<tComRow;i++){

		if(AgentComGrid.getRowColData(i,1)==null||AgentComGrid.getRowColData(i,1)==''){
			alert("请输入中介机构编码！");
			return false;
		}
		
		if(AgentComGrid.getRowColData(i,1)!=""){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContSql");
			tSQLInfo.setSqlId("LCContSql26");
			tSQLInfo.addSubPara(AgentComGrid.getRowColData(i,1));
			tSQLInfo.addSubPara(tValCheckDate);
			tSQLInfo.addSubPara(tValCheckDate);
			var tAgentState = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

			if (tAgentState==null || tAgentState[0][0]!="1") {
				if(tBFFlag=="1"){
					alert("中介机构【"+AgentComGrid.getRowColData(i,1)+"】许可证不存在或者不在有效期！");
					return false;
				}else {
					if(!confirm("中介机构【"+AgentComGrid.getRowColData(i,1)+"】许可证不存在或者不在有效期！是否继续？")){
						return false;
					}
				}
			}

			if(AgentComGrid.getRowColData(i,4)==""){
				AgentComGrid.setRowColData(i,3,"");
				AgentComGrid.setRowColData(i,5,"");
			}

			if(AgentComGrid.getRowColData(i,3)!=null && AgentComGrid.getRowColData(i,3)!=""){

				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_app.LCContSql");
				tSQLInfo.setSqlId("LCContSql25");
				tSQLInfo.addSubPara(AgentComGrid.getRowColData(i,3));
				tSQLInfo.addSubPara(tValCheckDate);
				tSQLInfo.addSubPara(tValCheckDate);
				var tAgentState = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if (tAgentState==null || tAgentState[0][0]!="1") {

					if(tBFFlag=="1"){
						alert("中介机构代理人【"+AgentComGrid.getRowColData(i,3)+"】资格证不存在或者不在有效期！");
						return false;
					} else {
						if(!confirm("中介机构代理人【"+AgentComGrid.getRowColData(i,3)+"】资格证不存在或者不在有效期！是否继续？")){
							return false;
						}
					}
				}
			}
		}
		
		for(var j=i+1;j<tComRow;j++){
			if(AgentComGrid.getRowColData(i,3)!="" && AgentComGrid.getRowColData(i,3)!=null
			&& AgentComGrid.getRowColData(j,3)!="" && AgentComGrid.getRowColData(j,3)!=null
			&& AgentComGrid.getRowColData(i,3)==AgentComGrid.getRowColData(j,3)){
				alert("请勿录入重复【中介代理人代码】！");
				return false;
			}
		}
	}
	return true;
}


function checkSalerInfo(){

	if(!SalerInfoGrid.checkValue("SalerInfoGrid")){
			return false;
	}
	var tSalerRow =SalerInfoGrid.mulLineCount;
	var tSalerRate=0;

	for(var i=0;i<tSalerRow;i++){

		if(SalerInfoGrid.getRowColData(i,1)==null||SalerInfoGrid.getRowColData(i,1)==''){
			alert("请输入代理人代码！");
			return false;
		}

		if(SalerInfoGrid.getRowColData(i,4)==null||SalerInfoGrid.getRowColData(i,4)==''){
			alert("请输入代理人【分佣比例】！");
			return false;
		}

		if(SalerInfoGrid.getRowColData(i,4)>1||SalerInfoGrid.getRowColData(i,4)<=0){
			alert("代理人【分佣比例】必须大于0小于1");
			return false;
		}

		var n=SalerInfoGrid.getRowColData(i,4).toString().split(".");

		if(typeof(n[1])!="undefined"&&n[1].length>2){
			alert("代理人【分佣比例】小数点后只能输入两位");
			return false;
		}

		for(var j=i+1;j<tSalerRow;j++){
			if(SalerInfoGrid.getRowColData(i,1)==SalerInfoGrid.getRowColData(j,1)){
				alert("请勿录入重复【代理人代码】！");
				return false;
			}
		}
	tSalerRate +=Number(SalerInfoGrid.getRowColData(i,4));
	}

	if(tSalerRate !=1){
		alert("代理人【分佣比例】之和应该等于1");
		return false;
	}
	return true;
}


// 校验法人的证件号码
function checkCoridtype(){

	if(fm.CorID.value.length>0 && fm.CorIDType.value=="") {
		alert("请先选择证件类型！");
		return false;
	}

	if(fm.CorIDType.value=="0"&&fm.CorID.value.length>0) {

		if((fm.CorID.value.length!=15) &&(fm.CorID.value.length!=18)){
			alert("输入的身份证号位数错误");
			document.all('CorID').value="";
			return false;
		}

		if(!checkIdCard(fm.CorID.value)) {

			alert("身份证号码不合法") ;
			document.all('CorID').value="";
			document.all('CorID').className = "warn";
			return false;
		}
	}
}


function checkCity(){
	var ManageCom =document.all('ManageCom').value;
	var ProvinceCode =document.all('ProvinceCode').value;
	var CityCode =document.all('CityCode').value;
	var CountyCode =document.all('CountyCode').value;

	if(tBFFlag=="1"){
		if(ProvinceCode ==''){
			alert("北京保单联系地址(省级)不能为空!");
			fm.ProvinceName.focus();
			return false;
		}
		if(CityCode =="" ){
			alert("北京保单联系地址(地级)不能为空");
			return false;
		}
		if(CountyCode =="" ){
			alert("北京保单联系地址(县级)不能为空");
			return false;
		}
	}

	if(ProvinceCode !=""){
		if(CityCode ==''){
			CityCode ='0';
		}
		if(CountyCode ==''){
			CountyCode ='0';
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPropEntrySql");
		tSQLInfo.setSqlId("LCPropEntrySql10");
		tSQLInfo.addSubPara(ProvinceCode);
		tSQLInfo.addSubPara(CityCode);
		tSQLInfo.addSubPara(CountyCode);

		var arrResult = easyExecSql(tSQLInfo.getString());
		if(arrResult==null || arrResult =='0'){
			alert("联系地址不存在或者关联不正确");
			return false;
		}
	}
	return true;
}


function checkidtype(){

	if(fm.IDNo.value.length>0 && fm.IDType.value=="") {
		alert("请先选择证件类型！");
		return false;
	}

	if(fm.IDType.value=="0"&&fm.IDNo.value.length>0) {
		if((fm.IDNo.value.length!=15) &&(fm.IDNo.value.length!=18)){
			alert("输入的身份证号位数错误");
			document.all('IDNo').value="";
			return false;
		}
		if(!checkIdCard(fm.IDNo.value)) {
			alert("身份证号码不合法") ;
			document.all('IDNo').value="";
			document.all('IDNo').className = "warn";
			return false;
		}
	}
}
/**
* 返回
**/
function turnBack(){
	/**if(tFlag=='0'){
		top.parent.location.href = "./LCGrpContListInput.jsp";
	} else if(tFlag=='1'){
		top.parent.location.href = "./LCGrpApproveListInput.jsp";
	}**/
	top.close();
	top.opener.querySelfClick();

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

	if (value1=='city') {

		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}

		var tProvince = fm.ProvinceCode.value;

		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tProvince,'upplacename','1',180);
		} else {
			return showCodeListKey(value1,value2,value3,null,tProvince,'upplacename','1',180);
		}
	} else if (value1=='bankcity') {

		if (isEmpty(fm.BankProvinceName)) {
			alert("请选择省！");
			return false;
		}

		var tProvince = fm.BankProvince.value;

		if (returnType=='0') {
			return showCodeList('city',value2,value3,null,tProvince,'upplacename','1',180);
		} else {
			return showCodeListKey('city',value2,value3,null,tProvince,'upplacename','1',180);
		}
	} else if (value1=='district') {

		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}

		if (isEmpty(fm.CityName)) {
			alert("请选择市！");
			return false;
		}

		var tCity = fm.CityCode.value;

		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
		} else {
			return showCodeListKey(value1,value2,value3,null,tCity,'upplacename','1',180);
		}
	} else if (value1=='bankprovince') {

		var tSql = "1";
		if (returnType=='0') {
			return showCodeList('province',value2,value3,null,tSql,1,'1',180);
		} else {
			return showCodeListKey('province',value2,value3,null,tSql,1,'1',180);
		}

	} else if (value1=="agenttype") {

		if (isEmpty(fm.ChnlType)) {
			if (returnType=='0') {
				return showCodeList(value1,value2,value3);
			} else {
				return showCodeListKey(value1,value2,value3);
			}
		}

		var tChnlType = fm.ChnlType.value;

		var tSql = "agenttype"+tChnlType;

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',180);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',180);
		}
	}else if (value1=="comcodeall") {

		var tSql = "1 and comgrade=#03# and comcode like #" + tManageCom + "%%# ";
		if (returnType=='0') {
			return showCodeList('comcodeall',value2,value3,null,tSql,'1','1',180);
		} else {
			return showCodeListKey('comcodeall',value2,value3,null,tSql,'1','1',180);
		}
	}
}

function submitFunc()
{
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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

		if("SAVEPOLICY"==mOperate || "SAVEAPPROVE"==mOperate){
			turnBack();
		}else if("INSERT"==mOperate){
			initForm();
		}
	}
}
function submitForm(obj, tOperate) {

	submitFunc();
	mOperate = tOperate;
	obj.submit();
}


//工程明细
function showInfoEngin(){
	document.all("divEnginFactor").innerHTML = showPlanDiv();
}
function showPlanDiv() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql2");
	tSQLInfo.addSubPara(tGrpPropNo);

	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if(tArr!=null){
		var tElementCode;//因子编码
		var tElementName;//因子名称
		var tControlFlag;//是否有录入框
		var tIsSelected;//借用quotno来判断该因子值是否在询价特约表中
		var tOElementValue;//原始值
		var tNElementValue;//询价值

		//var tInnerHTML0 = "<table class=common><tr class=common style='display:none'><td class=input></td><td class=input></td><td class=input></td><td class=input></td><td class=input></td><td class=input></td></tr>";
		var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></tr>";
		var tInnerHTML1 = "<tr class=common><td class=title>工程明细</td><td class=input colspan=5 >";

		for (var i=0; i<tArr.length; i++) {

			tElementCode = tArr[i][0];
			tElementName = tArr[i][1];
			tControlFlag = tArr[i][2];
			tIsSelected = tArr[i][3];//用来判断是否被选中
			tOElementValue = tArr[i][4];//原始值
			tNElementValue = tArr[i][5];//询价值

			tInnerHTML1 += "<input type=checkbox name="+ tElementCode;
			if (tIsSelected=='0') {//询价表中没有保存该因子

				tInnerHTML1 += ">"+ tElementName;
			} else {//保存了该因子

				tInnerHTML1 += " checked>"+ tElementName;
			}

			if (tControlFlag=='1') {//存在录入框

				tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tControlFlag +"><input style='width:90px' class=common name="+ tElementCode +"Value value="+ tNElementValue +">";
			} else {
				tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";
			}
		}

		tInnerHTML1 += "</td></tr></table>";
		tInnerHTML0 = tInnerHTML0+tInnerHTML1;

		return tInnerHTML0;
	}
}
function checkEngInfo(){
		if(isEmpty(fm.EnginName)){
			alert("工程名称不能为空!");
			return false;
		}
		if(isEmpty(fm.ContractorName)){
			alert("施工方名称不能为空!");
			return false;
		}
		if(isEmpty(fm.ContractorType)){
			alert("施工方资质不能为空!");
			return false;
		}
		if(isEmpty(fm.EnginPlace)){
			alert("工程地点不能为空!");
			return false;
		}
		if(!checkNumber(fm.EnginCost)){
			alert("工程造价必须为数字!");
			return false;
		}
		if(!checkNumber(fm.EnginArea)){
			alert("建筑面积必须为数字!");
			return false;
		}
		if(isEmpty(fm.EnginType)){
			alert("工程类型不能为空!");
			return false;
		}
		if(isEmpty(fm.PremCalMode)){
			alert("保费计算方式不能为空!");
			return false;
		}
		if("1"==fm.PremCalMode.value){
			if(fm.PremFeeRate.value!=null && fm.PremFeeRate.value!="" && !isNumeric(fm.PremFeeRate.value)){
				alert("保险费率必须为有效数字!");
				return false;
			}
		} else if("2"==fm.PremCalMode.value){
			if(isEmpty(fm.PremFeeRate)){
				alert("保险费率不能为空!");
				return false;
			}
			if(!isNumeric(fm.PremFeeRate.value)){
				alert("保险费率必须为有效数字!=");
				return false;
			}
			if(fm.PremFeeRate.value.length >=7){
				alert("保险费率长度不能超过6位!");
				return false;
			}
			//modify by dianj 20141128申请去掉校验
			/**var valuefee = fm.EnginCost.value*fm.PremFeeRate.value/1000;
			if(valuefee>fm.SumPrems.value){
				alert("按照保险费率所算出的保费应小于等于总保费!");
				return false;
			}**/

		} else if("3"==fm.PremCalMode.value){
			if(isEmpty(fm.PremFeeRate)){
				alert("保险费率长度不能为空!");
				return false;
			}
			if(fm.PremFeeRate.value.length>=7){
				alert("保险费率长度不能超过6位");
				return false;
			}
			if(!isNumeric(fm.PremFeeRate.value)){
				alert("保险费率必须为有效数字!");
				return false;
			}
			//modify by dianj 20141128申请去掉校验
			/**var valuefee = fm.EnginArea.value*fm.PremFeeRate.value;
			if(valuefee>fm.SumPrems.value){
				alert("按照保险费率所算出的保费应小于等于总保费!");
				return false;
			}**/
		}
		if(isEmpty(fm.FirstPrem)){
			alert("趸交保费不能为空!");
			return false;
		}
		if(!checkNumber(fm.FirstPrem)){
			alert("趸交保费必须为有效数字!");
			return false;
		}
		if(isEmpty(fm.ContractorPeoples)){
			alert("施工人数不能为空!");
			return false;
		}
		if(!checkNumber(fm.ContractorPeoples)){
			alert("施工人数必须为有效数字!");
			return false;
		}
		return true;
	}

function goToChargeFee(){

	var tChnlType = fm.ChnlType.value;

	window.open("./LCGrpChargeFeeMain.jsp?GrpPropNo="+tGrpPropNo+"&Flag="+tFlag,"中介手续费维护",'height=400,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
function goToBalance(){
	window.open("./LCBalanceOnMain.jsp?GrpPropNo="+tGrpPropNo+"&Flag="+tFlag,"定期结算维护",'height=300,width=1100,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
function afterCodeSelect(o, p){
	var tcode = fm.SaleChnl.value;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql16");
	tSQLInfo.addSubPara(tcode);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	}else{
		if(o=='agenttype'){
			if(p.value==tcode){
				fm.ChnlType.value=tPropEntry[0][0];
				fm.ChnlTypeName.value=tPropEntry[0][1];
			}
		}
	}
	if(o=='salechannel'){
		fm.SaleChnl.value='';
		fm.SaleChnlName.value='';
	}
	if(o=='valdatetype'){
		if(fm.ValDateType.value=='0'){
			valDatename.style.display="none";
			valDatename1.style.display="none";
		}else if(fm.ValDateType.value=='1'){
			valDatename.style.display="";
			valDatename1.style.display="";
		}
	}
	if(o=='comcodeall'){
		if(p.name == "BusinessAreaGrid1") {
			var rowNumber = BusinessAreaGrid.lastFocusRowNo;
			BusinessAreaGrid.setRowColData(rowNumber, 4, "");
			BusinessAreaGrid.setRowColData(rowNumber, 5, "");
			BusinessAreaGrid.setRowColData(rowNumber, 6, "");
		}
	}
	if(o=="province"){
		if(p.name=="BankProvince"){
			fm.BankCity.value="";
			fm.BankCityName.value="";

		} else {
			fm.CityCode.value="";
			fm.CityName.value="";
			fm.CountyName.value="";
			fm.CountyCode.value="";
		}
	} else if(o=="city"){
		if(p.name=="CityName"){
			fm.CountyName.value="";
			fm.CountyCode.value="";
		}
	}
}
function savePolicyClick(){

//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_app.LCContSql");
//	tSQLInfo.setSqlId("LCContSql31");
//	tSQLInfo.addSubPara(tCurrentDate);
//	tSQLInfo.addSubPara(tGrpPropNo);
//	var tRelaName = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	if (tRelaName!=null && tRelaName[0][0]=="1") {
//		tSQLInfo = new SqlClass();
//		tSQLInfo.setResourceName("g_app.LCContSql");
//		tSQLInfo.setSqlId("LCContSql32");
//		tSQLInfo.addSubPara(tCurrentDate);
//		tSQLInfo.addSubPara(tGrpPropNo);
//		var tRelaE = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//		if (tRelaE!=null && tRelaE[0][0]=="1"){
//			alert("此交易为重大关联交易，请关注是否完成审批!");
//			//return false;
//		}else {
//			alert("此交易为一般关联交易，请关注是否完成审批!");
//			//return false;
//		}
//	}
	if(AgentDetailGrid.mulLineCount>2){
		if(confirm("机构存在客户经理多于2个,是否通过?")){
			fm.action = "./LCGrpContPolSave.jsp?Operate=SAVEPOLICY";
			fm.Operate.value = "SAVEPOLICY";
			submitForm(fm,"SAVEPOLICY");
		}else{
			return false;
		}
	}else{
		fm.action = "./LCGrpContPolSave.jsp?Operate=SAVEPOLICY";
		fm.Operate.value = "SAVEPOLICY";
		submitForm(fm,"SAVEPOLICY");
	}
}

function saveApproveClick(){

	if(AgentDetailGrid.mulLineCount>2){
		if(confirm("机构存在客户经理多于2个,是否通过?")){
			fm.action = "./LCGrpContPolSave.jsp?Operate=SAVEAPPROVE";
			fm.Operate.value = "SAVEAPPROVE";
			submitForm(fm,"SAVEAPPROVE");
		}else{
			return false;
		}
	}else{
		fm.action = "./LCGrpContPolSave.jsp?Operate=SAVEAPPROVE";
		fm.Operate.value = "SAVEAPPROVE";
		submitForm(fm,"SAVEAPPROVE");
	}
}
//险种查询
function  queryRisk(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql19");
	tSQLInfo.addSubPara(tGrpPropNo);
	initRiskGrid();
	turnPage7.queryModal(tSQLInfo.getString(), RiskGrid, 1, 1);

	if(RiskGrid.mulLineCount>=10){
		divQueryInfoPage.style.display = "";
	}else {
		divQueryInfoPage.style.display = "none";
	}
}
//查询客户经理
function queryManager() {
	var tManageCom = fm.ManageCom.value;
	var tSelNo = AgentDetailGrid.lastFocusRowNo;//行号从0开始
	var strUrl = "LCGrpQueryManagerMain.jsp?SelNo="+tSelNo+"&ManageCom="+tManageCom;
	window.open(strUrl,"客户经理查询","height=600,width=900,left=0,top=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//查询归属地客户经理
function queryMComManager() {
	var tSelNo = BusinessAreaGrid.lastFocusRowNo;//行号从0开始
	var tManageCom = BusinessAreaGrid.getRowColData(tSelNo,1);
	var strUrl = "LCGrpQueryMComManagerMain.jsp?SelNo="+tSelNo+"&ManageCom="+tManageCom;
	window.open(strUrl,"归属地客户经理查询",'height=600,width=900,left=0,top=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//查询中介机构
function queryAgentCom() {
	var tSaleChnl=fm.SaleChnl.value;
	var tSelNo = AgentComGrid.lastFocusRowNo;//行号从0开始
	var tAgentManageCom = fm.ManageCom.value;
	var strUrl = "LCGrpQueryAgentComMain.jsp?SelNo="+tSelNo+"&SaleChnl="+tSaleChnl+"&Flag=0&ManageCom="+tAgentManageCom;
	window.open(strUrl,"中介机构查询",'height=600,width=900,left=0,top=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//查询网点
function queryAgentCode() {
	var tSaleDepart="";
	var tChnlType="";
	var tSaleChnl="";
	var tSelNo = AgentComGrid.lastFocusRowNo;//行号从0开始
	var tManageCom = "";
	var strUrl = "LCGrpQueryAgentComMain.jsp?SelNo="+tSelNo+"&ManageCom="+tManageCom+"&SaleDepart="+tSaleDepart+"&ChnlType="+tChnlType+"&SaleChnl="+tSaleChnl+"&Flag=1";
	window.open(strUrl,"网点查询",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}
//查询中介代理人
function querySalerInfo(){
	var tSelNo = AgentComGrid.lastFocusRowNo;//行号从0开始
	var tAgentCom = AgentComGrid.getRowColData(tSelNo,1);
	var strUrl = "LCGrpQuerySalerMain.jsp?SelNo="+tSelNo+"&AgentCom="+tAgentCom;
	window.open(strUrl,"中介代理人查询",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}
//查询代理人信息
function querySaler(){
	var salManageCom = fm.ManageCom.value;
	var tAreaRow = BusinessAreaGrid.mulLineCount;
	for(var i=0;i<tAreaRow;i++){
		if(BusinessAreaGrid.getRowColData(i,1)!=null&&BusinessAreaGrid.getRowColData(i,1)!=""){
			salManageCom = salManageCom + "|"+BusinessAreaGrid.getRowColData(i,1) +"";
		}
	}
	var tSelNo = SalerInfoGrid.lastFocusRowNo;//行号从0开始
	var strUrl = "LCGrpQuerySalerInfoMain.jsp?SelNo="+tSelNo+"&Flag=1&ManageCom="+salManageCom;
	window.open(strUrl,"代理人查询",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}

/**
*检查保险期间
*/
function checkPeriod(){

	var tInsuPeriod = fm.InsuPeriod.value;
	if(parseInt(tInsuPeriod)<=0){
		alert("请录入大于0的数值");
		return false;
	}
	if(fm.ValDateType.value==''){
		alert("请选择生效日期类型");
		fm.ValDateType.focus();
		return false;
	}
	if(fm.ValDateType.value=='1'&&fm.ValDate.value==''){
		alert("请录入生效日期");
		fm.ValDate.focus();
		return false;
	}
	if(fm.ValDateType.value=='1'){
		tValCheckDate = fm.ValDate.value;
	}
	if(fm.PayintvType.value==""){
		alert("请选择缴费方式");
		fm.PayintvType.focus();
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql27");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(fm.PayintvType.value);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tPropEntry!=null && tPropEntry[0][0]=="1") {
		alert("保单下的产品不支持该缴费方式，请重新选择。");
		fm.PayintvType.focus();
		return false;
	}
	if(fm.PayintvType.value!="0"){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContSql");
		tSQLInfo.setSqlId("LCContSql28");
		tSQLInfo.addSubPara(tGrpPropNo);
		var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

		if (tPropEntry!=null && tPropEntry[0][0]=="1") {
			alert("保单开通定期结算，只能选择趸缴。");
			fm.PayintvType.focus();
			return false;
		}
	}
	return true;
}

/**
*被保人清单查询
*/
function goToInsuredList(){

	window.open("./LCPropEntrytoAddMain.jsp?GrpPropNo="+tGrpPropNo+"&Flag=01","被保人清单核对",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


/**
 * 共保设置
 */
function showCoinsurance() {

	//共保标识为 否 时，不可维护共保信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql23");
	tSQLInfo.addSubPara(tGrpPropNo);

	var tCoinArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCoinArr[0][0]=="" || tCoinArr[0][0]=="0") {
		alert("[是否共保]为\"否\",不可进行共保设置,请先进行保存！");
		return false;
	}
	var tQueryFlag = "0";
	if(tFlag==null || tFlag==""){
		tQueryFlag = "1";
	}
	window.open("./LCCoinsuranceMain.jsp?GrpContNo="+ tGrpPropNo +"&QueryFlag="+ tQueryFlag+"&Flag="+tFlag,"共保设置",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function checkPayType(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCBalanceOnSql");
	tSQLInfo.setSqlId("LCBalanceOnSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(tGrpPropNo);
	var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var payType = fm.PayType.value;
	if (tRes==null) {
		alert("查询保单类型错误!");
		return false;
	} else {
		var policyFlag = tRes[0][0];
		if("S"!=policyFlag && payType == "09"){
			alert("弹性福利计划保单，付款方式不能选择【个人代扣】!");
			return false;
		}
	}
	return true;
}

function getPolicyFlag(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCBalanceOnSql");
	tSQLInfo.setSqlId("LCBalanceOnSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(tGrpPropNo);
	var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tRes==null) {
		alert("查询保单类型错误!");
		return false;
	} else {
		fm.PolicyFlagName.value=tRes[0][1];
	}
	return true;
}
/**
*中介机构调整
**/
function changeAgentType(){
	initAgentComGrid();
}

function clearAddress(){

	if(""==fm.ProvinceName.value){
		fm.ProvinceCode.value="";
		fm.CityCode.value="";
		fm.CountyCode.value="";
	}
	if(""==fm.CityName.value){
		fm.CityCode.value="";
		fm.CountyCode.value="";
	}
	if(""==fm.CountyName.value){
		fm.CountyCode.value="";
	}
	return true;
}

//关联划分
function showRelaInfo(){
	document.all("spanRelaPay").innerHTML = showRelaDiv();
}
//展示关联
function showRelaDiv() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql29");
	tSQLInfo.addSubPara("relabuss");

	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var CheckCode;//因子编码
	var CheckName;//因子名称
	var CheckDesc;//名称展示
	var tInnerHTML0 = "<table><tr><td class=common><img src='../common/images/butExpand.gif' style='cursor:hand;' onclick='showPage(this, divRelaPayInfo);'></td><td class=titleImg>合规关联</td></tr></table>";
	var tInnerHTML1 = "<div id='divRelaPayInfo'><table class=common>";

	for (var i=0; i<tArr.length; i++) {

		CheckCode = tArr[i][0];
		CheckName = tArr[i][0];
		CheckDesc = tArr[i][1];
		var CheckValue="";
		var Checked="";

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContSql");
		tSQLInfo.setSqlId("LCContSql30");
		tSQLInfo.addSubPara(CheckCode);
		tSQLInfo.addSubPara(tGrpPropNo);
		tSQLInfo.addSubPara("NB");

		var tArrArg = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tArrArg!=null && tArrArg[0][0]!=null){
			CheckValue = tArrArg[0][0];
			if(CheckValue=="1"){
				Checked="checked";
			}else {
				Checked="";
			}
		}

		if(i%3==0){
			tInnerHTML1 += "<tr class=common>";
		}
		tInnerHTML1 += "<td class=common><input class=checkbox type=checkbox id=Hidden"+ CheckCode +" name=Check"+ CheckName +" value='"+CheckValue+"' "+Checked+" onclick='setRelaFlag(this);'>"+ CheckDesc +"</td>";
		if((i+1)%3==0){
			tInnerHTML1 += "</tr>";
		}
	}
	var remarks =  "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql30");
	tSQLInfo.addSubPara("Para13");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara("NB");

	var tArrArg = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tArrArg!=null && tArrArg[0][0]!=null){
		remarks = tArrArg[0][0];
	}
	tInnerHTML1 += "<table class=common><table class=common><tr class= common><td class= title>合规备注说明</td></tr><tr class= common><td class=title><textarea name='Para13' cols='110' rows='3' class='common' >"+remarks+"</textarea></td></tr></table><hr size='2' color='#D7E1F6' /></div>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;

	return tInnerHTML0;
}
///展示值
function setRelaFlag(obj){
	if(obj.checked==true){
		obj.value="1";
	}else {
		obj.value="0";
	}
}
/**
*投保方案查询
**/
function showPlanQuery(){

	window.open("./LCContPlanQueryMain.jsp?ContPlanType="+tContPlanType+"&GrpPropNo="+tGrpContNo+"&PolicyNo="+tGrpPropNo ,"方案查询",'height='+screen.availHeight+',width='+screen.availWidth+',channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
	* 理赔责任控制
*/
function claimDutyControl(){
	var strUrl="../g_claim/LLCLaimControlMain.jsp?BussType=NB&Flag=1&BussNo="+ fm.GrpPropNo.value;

	window.open(strUrl,"理赔责任控制",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
