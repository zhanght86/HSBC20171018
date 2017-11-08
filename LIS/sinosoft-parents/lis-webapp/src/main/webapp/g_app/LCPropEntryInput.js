/***************************************************************
 * <p>ProName：LCPropEntryInput.js</p>
 * <p>Title：投保书信息录入</p>
 * <p>Description：投保书信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
var mOperate = '';
/**
	初始化页面
*/

function initEntry(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry[0][25]=='0'){
		valDatename.style.display='none';
		valDatename1.style.display='none';
	}
	if (tPropEntry!=null) {
		
		fm.GrpName.value=tPropEntry[0][0];
		fm.MainBusiness.value=tPropEntry[0][1];
		fm.Corporation.value=tPropEntry[0][2];
		fm.CorIDType.value=tPropEntry[0][3];
		fm.CorIDTypeName.value=tPropEntry[0][4];
		fm.CorID.value=tPropEntry[0][5];
		fm.CorIDExpiryDate.value=tPropEntry[0][6];
		fm.BusiCategoryCode.value=tPropEntry[0][7];
		fm.BusiCategory.value=tPropEntry[0][8];
		fm.FoundDate.value=tPropEntry[0][9];
		fm.SocialInsuCode.value=tPropEntry[0][10];
		fm.GrpNature.value=tPropEntry[0][11];
		fm.GrpNatureName.value=tPropEntry[0][12];
		fm.Phone.value=tPropEntry[0][13];
		fm.Fax.value=tPropEntry[0][14];
		fm.EMail.value=tPropEntry[0][15];
		fm.LinkMan.value=tPropEntry[0][16];
		fm.LinkPhone.value=tPropEntry[0][17];
		fm.LinkIDType.value=tPropEntry[0][18];
		fm.LinkIDTypeName.value=tPropEntry[0][19];
		fm.LinkID.value=tPropEntry[0][20];
		fm.LinkIDExpiryDate.value=tPropEntry[0][21];
		fm.SumNumPeople.value=tPropEntry[0][22];
		fm.MainContNumber.value=tPropEntry[0][23];
		fm.RelatedContNumber.value=tPropEntry[0][24];
		fm.ValDateType.value=tPropEntry[0][25];
		fm.ValdateTypeName.value=tPropEntry[0][26];
		fm.ValDate.value=tPropEntry[0][27];
		if(tPropEntry[0][28]=='0'){
			fm.InsuPeriod.value='';
		}else{
			fm.InsuPeriod.value=tPropEntry[0][28];
		}
		fm.InsuPeriodFlag.value=tPropEntry[0][29];
		fm.InsuPeriodName.value=tPropEntry[0][30];
		fm.PayIntv.value=tPropEntry[0][31];
		fm.PayIntvName.value=tPropEntry[0][32];
		fm.PremMode.value=tPropEntry[0][33];
		fm.PremModeName.value=tPropEntry[0][34];
		fm.PayType.value=tPropEntry[0][35];
		fm.PayTypeName.value=tPropEntry[0][36];
		fm.SaleChnl.value=tPropEntry[0][37];
		fm.SaleChnlName.value=tPropEntry[0][38];
		fm.ChnlType.value=tPropEntry[0][39];
		fm.ChnlTypeName.value=tPropEntry[0][40];
		fm.AppManageCom.value=tPropEntry[0][41];
		fm.ManageComName.value=tPropEntry[0][42];
	}
	//联系地址初始化
	tSQLInfo1 = new SqlClass();
	tSQLInfo1.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo1.setSqlId("LCPropEntrySql3");
	tSQLInfo1.addSubPara(tGrpPropNo);
	var tPropEntry1 = easyExecSql(tSQLInfo1.getString(), 1, 0, 1);
	if (tPropEntry1!=null) {
		var tBasicInfo = new Array();
		var i = 0;
		tBasicInfo[i++] = "Province";
		tBasicInfo[i++] = "ProvinceName";
		tBasicInfo[i++] = "City";
		tBasicInfo[i++] = "CityName";
		tBasicInfo[i++] = "County";
		tBasicInfo[i++] = "CountyName";
		tBasicInfo[i++] = "Address";
		tBasicInfo[i++] = "ZipCode";
		for(var t=0; t<i; t++) {
			document.all(tBasicInfo[t]).value = nullToEmpty(tPropEntry1[0][t]);
		}
	}
	//中介机构初始化
		tSQLInfo2 = new SqlClass();
		tSQLInfo2.setResourceName("g_app.LCPropEntrySql");
		tSQLInfo2.setSqlId("LCPropEntrySql4");
		tSQLInfo2.addSubPara(tGrpPropNo);
		turnPage2.queryModal(tSQLInfo2.getString(), ZJGrid, 1, 1);
		if(turnPage2.strQueryResult){
			divZJInfo.style.display='';
		}
	
	//主证件类型
	tSQLInfo1 = new SqlClass();
	tSQLInfo1.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo1.setSqlId("LCPropEntrySql6");
	tSQLInfo1.addSubPara(tGrpPropNo);
	var tPropEntry2 = easyExecSql(tSQLInfo1.getString(), 1, 0, 1);
	if (tPropEntry2!=null) {
		var tBasicInfo = new Array();
		var i = 0;
		tBasicInfo[i++] = "GrpIDType";
		tBasicInfo[i++] = "GrpIDTypeName";
		tBasicInfo[i++] = "GrpIDNo";
		tBasicInfo[i++] = "GrpIDExpiryDate";
		for(var t=0; t<i; t++) {
			document.all(tBasicInfo[t]).value = nullToEmpty(tPropEntry2[0][t]);
		}
	}
	
	//附加证件类型
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql7");
	tSQLInfo.addSubPara(tGrpPropNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), IDInfoGrid, 0, 1);
	
	if (!turnPage1.strQueryResult) {
		divIDInfoGrid.style.display = 'none';
		fm.IDInfoCheck.checked = false;
	}else {
		divIDInfoGrid.style.display = '';
		fm.IDInfoCheck.checked = true;
	}
	//初始化附件管理
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql11");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry3 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry3!=null) {
		if(tPropEntry3.length=='2'){
			fm.AddService.checked=true;
			fm.PersonProp.checked=true;
		}else if(tPropEntry3[0][0]=='1'){
			fm.AddService.checked=true;
		}else{
			fm.PersonProp.checked=true;
		}	
	}
	//特别约定
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql21");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(tGrpPropNo);
	var tGrpSpec = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tGrpSpec!=null && tGrpSpec[0][0]!="") {
		fm.GrpSpec.value=tGrpSpec[0][0];
	}
	return true;
}


/**
 * 保存投保人资料
 */
function saveClick(){
	clearAddress();
	trimBlank(fm);
	if(!verifyInput2()){
		return false;
	}	
	if(fm.FoundDate.value!="" && fm.FoundDate.value > fm.tCurrentDate.value){
		alert("成立时间有误,成立时间不能晚于当前日期!");
		return false;
	}
	var tIDInfoRow = IDInfoGrid.mulLineCount;

	for(var i=0;i<tIDInfoRow;i++){

		if(IDInfoGrid.getRowColData(i,5)!=null&&IDInfoGrid.getRowColData(i,5)!=''){
			if(IDInfoGrid.getRowColData(i,5)<fm.tCurrentDate.value){
				alert("证件有效止期需大于当前日期！");
				return false;
			}
		}
		if((IDInfoGrid.getRowColData(i,5)!=null&&IDInfoGrid.getRowColData(i,5)!='')&&(IDInfoGrid.getRowColData(i,4)!=null&&IDInfoGrid.getRowColData(i,4)!='')){
			if(IDInfoGrid.getRowColData(i,5)<IDInfoGrid.getRowColData(i,4)){
				alert("证件有效止期需大于证件有效起期！");
				return false;
			}
		}
		for(var j=i+1;j<tIDInfoRow;j++){
			if(IDInfoGrid.getRowColData(i,1)==IDInfoGrid.getRowColData(j,1)){
				alert("请勿录入重复【证件类型编码】！");
				return false;
			}
		}
		if(fm.GrpIDType.value==IDInfoGrid.getRowColData(i,1)){
			alert("单位证件列表中的证件类型与录入框中的证件类型重复，请确认！");
			return false;
		}
	}
	// 校验详细地址信息
	if(!checkCity()){		
		return false;
	}
	if(!checkID()){
		return false;
	}
	
	// 校验勾选单位证件信息
	if(fm.IDInfoCheck.checked){		
		IDInfoGrid.delBlankLine();	
		var count = IDInfoGrid.mulLineCount;
		if(parseInt(count)==0){
			alert("请填写单位证件详细信息！");
			return false;
		}
		
		// 校验单位证件信息
		if(!valIDNOInfo()){
			return false;	
		}	
	}

	var tCurrentDate = fm.tCurrentDate.value;		
	if(fm.CorIDExpiryDate.value != ""){
		if(fm.CorIDExpiryDate.value <tCurrentDate){
			alert("法人证件有效止期必须大于当前日期！");
			return false;			
		}	
	}
			
	if(fm.GrpIDExpiryDate.value !=""){		
		if(fm.GrpIDExpiryDate.value <tCurrentDate){
			alert("单位证件有效止期必须大于当前日期！");
			return false;			
		}	
	}
	
	if(fm.LinkIDExpiryDate.value !=""){	
		if(fm.LinkIDExpiryDate.value<tCurrentDate){
			alert("联系人证件有效止期必须大于当前日期！");
			return false;
		}		
	}
	IDInfoGrid.delBlankLine();
	fm.action = "./LCPropEntrySave.jsp?Operate=INSERT";
	submitForm(fm,"INSERT");
	
}

function checkCity(){
		
	var ProvinceCode =document.all('Province').value;
	var CityCode =document.all('City').value;
	var CountyCode =document.all('County').value;
	
	if(CityCode ==''){
		CityCode ='0';
	}
	
	if(CountyCode ==''){
		CountyCode ='0';
	}
	
	if(ProvinceCode !=""){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPropEntrySql");
		tSQLInfo.setSqlId("LCPropEntrySql10");
		tSQLInfo.addSubPara(ProvinceCode);
		tSQLInfo.addSubPara(CityCode);
		tSQLInfo.addSubPara(CountyCode);
			
		var arrResult = easyExecSql(tSQLInfo.getString());	
		if(arrResult =='0'){
			alert("联系地址不存在或者关联不正确!");
			return false;
		}
		
		if(fm.Address.value ==""){
			alert("联系地址详细地址不能为空!");
			return false; 
		}
	}
	return true;
}

function checkCoridtype(){
	
	if(fm.CorID.value.length>0 && fm.CorIDType.value=="") {
	 	 alert("请先选择证件类型！");	
		 return false;
	}
		
	if(fm.CorIDType.value=="11"&&fm.CorID.value.length>0) {
		
		if((fm.CorID.value.length!=15) &&(fm.CorID.value.length!=18)){
			 alert("输入的身份证号位数错误");
			document.all('CorID').value="";
			 return false;
		}
		if(!checkIdCard(fm.CorID.value)) {
			document.all('CorID').value="";
			document.all('CorID').className = "warn";
			 return false;
		}
	}
}

function checkidtype(){
		
	if(fm.LinkID.value.length>0 && fm.LinkIDType.value=="") {
		 alert("请先选择证件类型！");	
		return false;
	}

	if(fm.LinkIDType.value=="11"&&fm.LinkID.value.length>0) {

		if((fm.LinkID.value.length!=15) &&(fm.LinkID.value.length!=18)){
			alert("输入的身份证号位数错误");
			document.all('LinkID').value="";
			return false;
		}

		if(!checkIdCard(fm.LinkID.value)) {
	
		document.all('LinkID').value="";
		document.all('LinkID').className = "warn";
		return false;
		}
	}
}


function savesClick(){
	
	trimBlank(fm);
	ZJGrid.delBlankLine();
	
	if(!checkInfo()){
		return false;
	}
	
	var tChnlType = fm.ChnlType.value;
	var tZJRow=ZJGrid.mulLineCount;
	
	if(!ZJGrid.checkValue("ZJGrid")){
		 return false;
	}
	if(tChnlType=='2'||tChnlType=='3'||tChnlType=='4'|| tChnlType =='5' ){
		
		if(tZJRow==0){
			alert("中介业务，请输入中介机构机构信息！");
			return false;
		}
	}else {
		if(tZJRow>0){
			//alert("非中介业务，不需要录入中介机构信息!");
			//return false;
		}
	}
	
	for(var i=0;i<tZJRow;i++){
			if(ZJGrid.getRowColData(i,1)==null||ZJGrid.getRowColData(i,1)==''){
				alert("请输入中介机构编码！");
			return false;
		}
	
		for(var j=i+1;j<tZJRow;j++){
			if(ZJGrid.getRowColData(i,1)==ZJGrid.getRowColData(j,1)){
				alert("请勿录入重复【中介机构编码】！");
				return false;
			}  
		}
	}
	if(fm.GrpSpec.value.length>1500){
		alert("保单特别约定录入过长，请录入1500字符以内!");
		return false;
	}
	if(!checkPlanDiv()){
		return false;	
	}
	fm.action = "./LCPropEntrySave.jsp?Operate=SAVE";
	submitForm(fm,"SAVE");
}
function selectIDInfo() {
	var tGrpIDType = fm.GrpIDType.value;
	var tGrpIDNo = fm.GrpIDNo.value;
	if(!((tGrpIDType!=null&&tGrpIDType!='')&&(tGrpIDNo!=null&&tGrpIDNo!=''))){
		if(fm.IDInfoCheck.checked){
			alert("请先录入完整单位证件信息再勾选!");
			fm.IDInfoCheck.checked=false;
			return false;
		}
	}
	if(fm.IDInfoCheck.checked) {
		divIDInfoGrid.style.display = '';
	} else {
		divIDInfoGrid.style.display = 'none';
		initIDInfoGrid();
	}
}
//返回
function turnBack(){
	location.href = "./LCPropPrintInput.jsp";
}

function goToChargeFee(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql13");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry4 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if(tPropEntry4!=null && tPropEntry4[0][0]=='1'){
		window.open("./ChargeFeeMain.jsp?state=0&GrpPropNo="+tGrpPropNo,"中介手续费维护",'height=400,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,directories=no,location=no, status=no');
	}else{
		alert("直销业务，不需要维护中介手续费！");
		return false;
	}
}

function propInfo() {
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请选择一条信息!");
		return false;	
	}
	window.location="./LCPropEntryInput.jsp";
}

/**
 * 根据前台页面进行下拉控制
 **/
function returnShowCodeLis (value1, value2, value3) {

	if (value1=='city') {
	
		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		var tProvince = fm.Province.value;
		
		return showCodeList(value1,value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='district') {

		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		
		if (isEmpty(fm.CityName)) {
			alert("请选择市！");
			return false;
		}
		
		var tCity = fm.City.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

function returnShowCodeLisKey (value1, value2, value3) {
	
	if (value1=='city') {
	
		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		var tProvince = fm.Province.value;
		
		return showCodeList(value1,value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='district') {
		
		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		if (isEmpty(fm.CityName)) {
			alert("请选择市！");
			return false;
		}
		
		var tCity = fm.City.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tSelectValue, tObj) {
	
	var tcode = fm.SaleChnl.value;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql8");
	tSQLInfo.addSubPara(tcode);
	var tPropEntry5 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry5==null) {
		return false;
	}else{
		if(tSelectValue=='agenttype'){
			if(tObj.value==tcode){
				fm.ChnlType.value=tPropEntry5[0][0];
				fm.ChnlTypeName.value=tPropEntry5[0][1];
			}
		}	
	}	
	if(tSelectValue=='salechannel'){
		fm.SaleChnl.value='';
		fm.SaleChnlName.value='';	
	}
	if (tSelectValue=='province') {
		fm.City.value = "";
		fm.CityName.value = "";
		fm.County.value = "";
		fm.CountyName.value = "";
	} else if (tSelectValue=='city') {
	
		fm.County.value = "";
		fm.CountyName.value = "";
	}else if(tSelectValue=='valdatetype'){
		
		if(fm.ValDateType.value=='0'){
			valDatename.style.display="none";
			valDatename1.style.display="none";		
		}else if(fm.ValDateType.value=='1'){
			valDatename.style.display="";
			valDatename1.style.display="";	
		}
	}else if(tSelectValue=='comcodeall'){
		initZJGrid();
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
function afterSubmit(FlagStr, content,patch,fileName1) {
	
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
		initState();
		getElasticflag();
		if(mOperate=="PRINT" || mOperate=="PRINTPREM"){
			downFile(patch,fileName1);
		}
	}	
}
function submitForm(obj, tOperate) {
	ZJGrid.delBlankLine();//删除空行
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //提交
}
//add by caiyc
function checkInfo(){
	//校验基本投保资料是否录入完整
	if (isEmpty(fm.ValDateType)) {
		alert("契约生效日期类型不能为空！");
		fm.ValDateType.focus();
		return false;
	}
	
	if(fm.ValDateType.value =='1'){		
		if (isEmpty(fm.ValDate)) {
			alert("生效日期不能为空！");
			fm.ValDate.focus();
			return false;
		}
	}
	
	if (isEmpty(fm.InsuPeriod)) {
		
		alert("保险期限不能为空！");
		fm.InsuPeriod.focus();
		return false;
	}

	if (!checkNumber(fm.InsuPeriod)) {
		fm.InsuPeriod.focus();
		return false;
	}
	if(!verifyInteger('保险期间',fm.InsuPeriod.value)){
		alert("保险期间必须为整数!");
		fm.InsuPeriod.focus();
		return false;
	}
	if (isEmpty(fm.InsuPeriodFlag)) {
		
		alert("保险期间单位不能为空！");
		fm.InsuPeriodFlag.focus();
		return false;
	}
	if (isEmpty(fm.PayIntv)) {
		
		alert("缴费方式不能为空！");
		fm.PayIntv.focus();
		return false;
	}
	if (isEmpty(fm.PremMode)) {
		
		alert("保费分摊方式不能为空！");
		fm.PremMode.focus();
		return false;
	}
	if (isEmpty(fm.PayType)) {
		
		alert("付款方式不能为空！");
		fm.PayType.focus();
		return false;
	}
	
	if (isEmpty(fm.SaleChnl)) {
		
		alert("销售渠道不能为空！");
		fm.SaleChnl.focus();
		return false;
	}
	if (isEmpty(fm.ChnlType)) {
		
		alert("销售渠道类型不能为空！");
		fm.ChnlType.focus();
		return false;
	}
	if (isEmpty(fm.AppManageCom)) {
		
		alert("承保机构不能为空！");
		fm.AppManageCom.focus();
		return false;
	}
	if (!checkManageCom(fm.AppManageCom.value)) {
		fm.ManageCom.focus();
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContSql");
	tSQLInfo.setSqlId("LCContSql22");
	tSQLInfo.addSubPara(fm.AppManageCom.value);
	tSQLInfo.addSubPara("11");
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	/**if (tPropEntry!=null && tPropEntry[0][0]=="1" && fm.FoundDate.value=="") {
		alert("北京机构承保，公司成立日期必须录入！");
		return false;
	}**/
		
	return true;
}
//等级划分
function showInfo(){
	document.all("divPlanDiv").innerHTML = showPlanDiv();
}
function showPlanDiv() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql5");
	tSQLInfo.addSubPara(tGrpPropNo);
	
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var PhonementCode;//因子编码
	var PhonementName;//因子名称
	var tControlFlag;//是否有录入框
	var tIsSelected;//借用quotno来判断该因子值是否在询价特约表中
	var tOElementValue;//原始值
	var tNElementValue;//询价值
	
	//var tInnerHTML0 = "<table class=common><tr class=common style='display:none'><td class=input></td><td class=input></td><td class=input></td><td class=input></td></tr>";
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>保障层级划分标准<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		PhonementCode = tArr[i][0];
		PhonementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];//用来判断是否被选中
		tOElementValue = tArr[i][4];//原始值
		tNElementValue = tArr[i][5];//询价值
		
		tInnerHTML1 += "<input type=checkbox name="+ PhonementCode;
		if (tIsSelected=='0') {//询价表中没有保存该因子

			tInnerHTML1 += ">"+ PhonementName;
		} else {//询价中保存了该因子

			tInnerHTML1 += " checked>"+ PhonementName;
		}
		
		if (tControlFlag=='1') {//存在录入框

			tInnerHTML1 += "<input type=hidden name=Hidden"+ PhonementCode +" value="+ tControlFlag +"><input style='width:90px' class=common name="+ PhonementCode +"Value value="+ tNElementValue +">";	
		} else {
			tInnerHTML1 += "<input type=hidden name=Hidden"+ PhonementCode +" value=0>";	
		}
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

/**
 * 把null的字符串转为空
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}

	return string;
}
//查询中介机构
function queryAgentCom() {
	var tSelNo = ZJGrid.lastFocusRowNo;//行号从0开始
	var agentManageCom = "";//fm.AppManageCom.value;
	var agentChnlType = "";//fm.ChnlType.value;
	var agentSaleChnl = "";//fm.SaleChnl.value;
	var strUrl = "LCGrpQueryAgentComMain.jsp?SelNo="+tSelNo+"&Flag=2&ManageCom="+agentManageCom+"&ChnlType="+agentChnlType+"&SaleChnl="+agentSaleChnl;
	window.open(strUrl,"中介机构查询",'height=600,width=900,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
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

	if (value1=="agenttype") {
		
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
	}
	
	if (value1=="payintv") {
		
		var tQuotType = fm.QuotType.value;
		var tSql = "1";
		if("01"==tQuotType){
			var tSql = ' 1 and code in (select a.payintv from LSProjQuotPayIntv a,LSQuotOfferList b,lcprintappnt c where a.quotno=b.quotno and a.quotbatno=b.quotbatno and b.offerlistno=c.offerlistno  and c.grppropno=#'+tGrpPropNo+'# )';
		} 
		if (returnType=='0') {
			return showCodeList('payintv',value2,value3,null,tSql,'1','1',180);
		} else {
			return showCodeListKey('payintv',value2,value3,null,tSql,'1','1',180);
		}
	}
	if (value1=="comcodeall") {
		
		var tQuotType = fm.QuotType.value;
		var tSql = "1 and comgrade=#03#";
		if("01"==tQuotType){
			var tSql = ' 1 and comgrade=#03# and comcode in (select comcode from ldcom c start with comcode in (select d.managecom from lsprojquotcom d,LSQuotOfferList e,lcprintappnt f where d.quotno=e.quotno and d.quotbatno=e.quotbatno and e.offerlistno=f.offerlistno and f.grppropno=#'+tGrpPropNo+'#) connect by prior comcode=upcomcode) ';
		} 
		if (returnType=='0') {
			return showCodeList('comcodeall',value2,value3,null,tSql,'1','1',180);
		} else {
			return showCodeListKey('comcodeall',value2,value3,null,tSql,'1','1',180);
		}
	}
}


//校验保障层级  add by caiyc
function checkPlanDiv(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql9");
	tSQLInfo.addSubPara("quotplandiv");
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	var flag=false;
	for (var i=0; i<tArr.length; i++) {
		var planValue = tArr[i][0];
		if(document.all(planValue).checked==true){
			flag=true;
		}	
	}
	if(flag==false){
		alert("必须勾选保障层级!");
		return false;
	}
	if(fm.quotplandiv99.checked==true){
		if( fm.quotplandiv99Value.value==''|| fm.quotplandiv99Value.value==null){
			alert("勾选其他时必须填写描述!");
			return false;
		}
	}
	return true;
}

// add by weigh
function goToInsuredInfo(){
	
	var tFlag='';
	var tGrpPropNo = fm.GrpPropNo.value;	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql12");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry6 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);	
	if(tPropEntry6 =='1'){
		tFlag='01';
	}else{
		tFlag ='00';
	}
	if(tPropEntry6=='-1'){
		alert("请先保存投保人资料！");
		return false;
	}
	if(!(tPropEntry6=='13'||tPropEntry6 =='1')){
		alert("请先保存基本投保信息！");
		return false;
	}
	if("02"==fm.ContPlanType.value){
		alert("账户型产品，无法进行人员清单维护！");
		return false;
	}
	var strUrl = "LCPropEntrytoAddMain.jsp?GrpPropNo="+tGrpPropNo+"&Flag="+tFlag+"&ActivityID=0&PolicyType="+fm.PolicyType.value;
	window.open(strUrl,"人员清单维护",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		
 }
function checkID(){
	if((fm.GrpIDType.value==null||fm.GrpIDType.value=='')&&fm.GrpIDNo.value!=null&&fm.GrpIDNo.value!=''){
		alert("请录入单位证件类型!");
		return false;
	}
	if((fm.GrpIDType.value!=null&&fm.GrpIDType.value!='')&&(fm.GrpIDNo.value==null||fm.GrpIDNo.value=='')){
		alert("请录入单位证件号码!");
		return false;
	}
	
	return true;
}

function printPrtProp(){

	if(!checkChangeFee()){
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql12");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);	
	if(tRes==null || tRes[0][0]!='13'){
		alert("请先保存基本投保信息！");
		return false;
	}
	
	fm.action = "./LCPropEntrySave.jsp?Operate=PRINT";
	submitForm(fm,"PRINT");
}
function initState(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql12");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPropEntry7 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry7=='1'){
		savePrint.style.display='none';
		saveProp.style.display='none';
		fm.PrintProp.disabled=true;
	}else{
		savePrint.style.display='';
		saveProp.style.display='';
		fm.PrintProp.disabled=false;
	}
}
 /***
 * 判断是否录入中介手续费
 **/
function checkChangeFee(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql13");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tChargeFalg = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tChargeFalg!=null && tChargeFalg[0][0]=="1"){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPropEntrySql");
		tSQLInfo.setSqlId("LCPropEntrySql14");
		tSQLInfo.addSubPara(tGrpPropNo);
		var tCFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if(tCFlag !=null && tCFlag=='1'){
			alert("还有中介没有录入中介手续费!");
			return false;
		}
	}
	return true;
}


/**
 * 校验单位证件类型
 */
function valIDNOInfo(){
	var tCount = IDInfoGrid.mulLineCount;
	if(parseInt(tCount)>0){	
		if(!IDInfoGrid.checkValue("IDInfoGrid")){
			return false;
		}
		
	for (var i=0;i<tCount;i++){
			if(IDInfoGrid.getRowColData(i,1) !=''){
				if(IDInfoGrid.getRowColData(i,3)==''){
					alert("第"+(i+1)+"行【证件类型编码】不为空需录入【证件号码】！");
					return false;
				}
				
				if(IDInfoGrid.getRowColData(i,1)== fm.GrpIDType.value){
					alert("第"+(i+1)+"行【证件类型编码】已经录入不能重复！");
					return false;
				}	
			}else {
				if(IDInfoGrid.getRowColData(i,3)!=''){
					alert("第"+(i+1)+"行【证件号码】不为空需录入【证件类型编码】！");
					IDInfoGrid.getRowColData(i,3)="";
					return false;
				}
			}
			
			if(IDInfoGrid.getRowColData(i,5) !=''){
				if(IDInfoGrid.getRowColData(i,5)<tCurrentDate){
					alert("第"+(i+1)+"行单位证件有效止期需大于当前日期");
					return false;
				}
			}
			
			for (var j=i+1;j<tCount;j++ ){
				
				if(IDInfoGrid.getRowColData(i,1)== IDInfoGrid.getRowColData(j,1)){
					alert("第["+(i+1)+"]行单位证件类型与第["+(j+1)+"]行单位证件类型重复！");
					return false;
				}
				
				if(IDInfoGrid.getRowColData(i,3)== IDInfoGrid.getRowColData(j,3)){
					alert("第["+(i+1)+"]行单位证件号码与第["+(j+1)+"]行单位证件号码重复！");
					return false;
				}
			}
		}
	}
	
	return true;
}

/***
 * 判断是否是弹性福利计划，是否是账户型保单
 **/
function getElasticflag(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql15");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tPolFalg = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPolFalg!=null){
		fm.PolicyType.value = tPolFalg[0][0];
		fm.ContPlanType.value = tPolFalg[0][1];
	}
}
/**
** 文件下载
**/
function downFile(patch,fileName1){
	
	window.location = "../common/jsp/download.jsp?FilePath="+patch+"&FileName="+fileName1;
	
}

function clearAddress(){
	
	if(""==fm.ProvinceName.value){
		fm.Province.value="";
		fm.City.value="";
		fm.County.value="";
	}
	if(""==fm.CityName.value){
		fm.City.value="";
		fm.County.value="";
	}
	if(""==fm.CountyName.value){
		fm.County.value="";
	}
	return true;
}
//保费确认书
function printPremConf(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql12");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);	
	if(tRes==null || tRes[0][0]!='1'){
		alert("请先打印投保书！");
		return false;
	}
	
	fm.action = "./LCPropEntrySave.jsp?Operate=PRINTPREM";
	submitForm(fm,"PRINTPREM");
}

function clearCityAndCounty(){

	fm.CityName.value = "";
	fm.City.value = "";
	fm.CountyName.value = "";
	fm.CountyCode.value = "";
}
function clearCounty(){
	fm.CountyName.value = "";
	fm.CountyCode.value = "";
}
