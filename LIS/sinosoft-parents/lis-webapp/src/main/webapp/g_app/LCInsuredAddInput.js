/***************************************************************
 * <p>ProName：LCInsuredAddInput.js</p>
 * <p>Title：添加被保人</p>
 * <p>Description：添加被保人</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date		 : 2014-04-22
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交数据后返回操作

 */
function afterSubmit(tFlagStr, tContent,tContno,tInsuredNo) {
	
	if (typeof(showInfo) == "object" && typeof(showInfo) != "unknown") {
	
		showInfo.close();
	}
	if (tFlagStr == "Fail") {
	
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//当增加完毕时候
		if(mOperate=="INSERT"){
			if(confirm("是否继续录入其他客户？")){
				clearPage();
				return false;
			}
			fm.mContNo.value=tContno;
			fm.InsuredSeqNo.value=tInsuredNo;
			initForm();
		}else if(mOperate=="DELETE"){
	
			fm.mContNo.value="";
			fm.InsuredSeqNo.value="";
			document.all("InsuredType").disabled=false;
			clearPage();
			divQuerySpe.style.display="none";
			initForm();	
		}else if(mOperate=="UPDATE"){
			fm.mContNo.value=tContno;
			fm.InsuredSeqNo.value=tInsuredNo;
			document.all("addButton").disabled=true;	
			initForm();	
		}else if(mOperate=="POLSave"){
			fm.mContNo.value=tContno;
			fm.InsuredSeqNo.value=tInsuredNo;
			queryPol(tContno,tInsuredNo);
		}else if(mOperate=="PayPlanSave"){
			fm.mContNo.value=tContno;
			fm.InsuredSeqNo.value=tInsuredNo;
			queryPayPlan(tContno,tInsuredNo);
		}else if(mOperate=="InvestSave"){
			fm.mContNo.value=tContno;
			fm.InsuredSeqNo.value=tInsuredNo;
			queryPayPlan(tContno,tInsuredNo);
		}
	}	
}


/**
 * 查询被保人险种信息
 **/
function queryPol(Contno,insuredno){	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql6");
	tSQLInfo.addSubPara(Contno);
	tSQLInfo.addSubPara(insuredno);
	tSQLInfo.addSubPara(fm.GrpPrtno.value);
	initQueryScanGrid();
	turnPage1.queryModal(tSQLInfo.getString(), QueryScanGrid, 1, 1);
	if(QueryScanGrid.mulLineCount>=10){
		divQueryInfoPage.style.display = "";
	}else {
		divQueryInfoPage.style.display = "none";
	}
}

/**
 * 查询被保人缴费项信息
 **/
function queryPayPlan(Contno,insuredno){
	
	initPayPlanGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql15");
	tSQLInfo.addSubPara(Contno);
	tSQLInfo.addSubPara(insuredno);
	
	turnPage3.queryModal(tSQLInfo.getString(), PayPlanGrid, 1, 1);
	
	if (turnPage3.strQueryResult) {
		if(tFlag !='01'){
			divPayPlanSaveButton.style.display = "";
		}
		divPayPlan.style.display = "";
		divPol.style.display="none";
	} else {
		divPayPlanSaveButton.style.display = "none";
		divPayPlan.style.display = "none";
	}
	
	divInvest.style.display = "none";
}

/**
 * 查询投资账户信息
 **/
function queryInvest(){
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条缴费项信息！");
		return false;
	}
	
	var tPolNo = PayPlanGrid.getRowColData(tSelNo-1, 1);
	var tDutyCode = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanCode = PayPlanGrid.getRowColData(tSelNo-1, 4);
	
	initInvestGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql16");
	tSQLInfo.addSubPara(tPolNo);
	tSQLInfo.addSubPara(tDutyCode);
	tSQLInfo.addSubPara(tPayPlanCode);
	
	turnPage4.queryModal(tSQLInfo.getString(), InvestGrid, 1, 1);
	
	//如果投资信息超过1行，则录入投资信息，否则直接展示
	divInvest.style.display = "";
	if (InvestGrid.mulLineCount>1) {
		divInvestSaveButton.style.display = "";
	} else {
		divInvestSaveButton.style.display = "none";
	}
}

/**
 * 根据前台页面进行下拉控制
 **/
function returnShowCodeList (value1, value2, value3) {

	if (value1=='bankprovince') {

		return showCodeList('province',value2,value3,null,null,null,null,180);

	} else if (value1=='bankcity') {
		if (isEmpty(fm.BankProvince)) {
			alert("请选择省！");
			return false;
		}
		var tProvince = fm.BankProvince.value;
		
		return showCodeList('city',value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='city') {
		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}	
		var tProvince = fm.ProvinceCode.value;
		
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
		
		var tCity = fm.CityCode.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

function returnShowCodeLisKey (value1, value2, value3) {

	if (value1=='bankprovince') {

		return showCodeList('province',value2,value3,null,null,null,null,180);

	} else if (value1=='bankcity') {
		if (isEmpty(fm.BankProvince)) {
			alert("请选择省！");
			return false;
		}
		var tProvince = fm.BankProvince.value;
		
		return showCodeList('city',value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='city') {

		if (isEmpty(fm2.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		
		var tProvince = fm.ProvinceCode.value;
		
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
		
		var tCity = fm.CityCode.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tSelectValue, tObj) {

	if(tSelectValue=='relation'){
		if (fm.relatomain.value!='00') {
			mainname.style.display="";
			mainname1.style.display="";
			maincustno.style.display="";
			mainCustNo1.style.display="";
			id1.style.display="none";
			id2.style.display="none";
			id3.style.display="none";
			id4.style.display="none";
			fm.mainCustName.value="";
			fm.mainCustNo.value="";
		}else{
			mainname.style.display="none";
			mainname1.style.display="none";
			maincustno.style.display="none";
			mainCustNo1.style.display="none";
			id1.style.display="";
			id2.style.display="";
			id3.style.display="";
			id4.style.display="";
		}
	}else if(fm.InsuredType.value=='1'){
		shownewPage.style.display="";
		divQueryPage.style.display="none";
		fm.bnfAdd.style.display = "none";
		}else{
		shownewPage.style.display="none";
		divQueryPage.style.display="";
		fm.bnfAdd.style.display = "";
	}
	if(tSelectValue == "province"){
		if(tObj.name=="BankProvince"){
			fm.BankCity.value="";
			fm.BankCityName.value="";
		}else {

			fm.CityName.value="";
			fm.CityCode.value="";
			fm.CountyName.value="";
			fm.CountyCode.value="";
		}
	} else if(tSelectValue == "city"){

		if(tObj.name=="BankCity"){

		}else {
			fm.CountyName.value="";
			fm.CountyCode.value="";
		}

	}
}

/**
 * 校验身份证以及返回出生日期和性别、年龄
 */
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
			 document.all('IDNo').value="";
			 document.all('IDNo').className = "warn";
			 return false;
		}else {
			
			if(!checkProvCode(fm.IDNo.value.substring(0,2))){
				alert("身份证的前两位代码错误，请使用正确的身份证号码!");
				return false;
			}
			fm.InsuredBirthDay.value =getBirthdatByIdNo(fm.IDNo.value);
			fm.InsuredGender.value = getSexByIDNo(fm.IDNo.value);	
			
			if(fm.InsuredGender.value=='0'){
				fm.InsuredGenderName.value ='男';
			}else if(fm.InsuredGender.value=='1'){
				fm.InsuredGenderName.value ='女';
			}
			fm.InsuredAppAge.value = calAge(fm.InsuredBirthDay.value);
			if(fm.InsuredAppAge.value<0 || fm.InsuredAppAge.value > 110){
				alert("请确认出生日期是否正确,最大年龄110岁！");
				return false;
			}
		}
	}
	return true;
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
/**
 * 清空关联查询的项目

 */
function clearInput(codeInput,nameInput) {
	codeInput.value = "";
	nameInput.value = "";
}

function showContPlanCode(cObj,cObjName,cObjCode,PlanType,PremcalType,PlanFlag){
	var tSQL = "";
	if("0"==fm.InsuredType.value){
		tSQL = "1 and (a.plantype in (#00#,#02#) or a.premcaltype=#1#) and a.grpcontno like #" + document.all('GrpPropNo').value + "%# ";
	}else if("1"==fm.InsuredType.value){
		tSQL = "1 and (a.plantype in (#00#) or a.premcaltype in(#2#,#3#)) and a.grpcontno like #" + document.all('GrpPropNo').value + "%# ";
	}
	return showCodeList('contplan',[cObj,cObjName,cObjCode,PlanType,PremcalType,PlanFlag],[0,1,2,3,4,5],null,tSQL,'1',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode,PlanType,PremcalType,PlanFlag){
	var tSQL = "";
	if("0"==fm.InsuredType.value){
		tSQL = "1 and (a.plantype in (#00#,#02#) or a.premcaltype=#1#) and a.grpcontno like #" + document.all('GrpPropNo').value + "%# ";
	}else if("1"==fm.InsuredType.value){
		tSQL = "1 and a.premcaltype in(#2#,#3#) and a.grpcontno like #" + document.all('GrpPropNo').value + "%# ";
	}
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode,PlanType,PremcalType,PlanFlag],[0,1,2,3,4,5],null,tSQL,'1',1,null);
}


//初始化职级的下拉内容
function initPosition(cObj,cName){
	showCodeList('position',[cObj,cName], [0,1], null,document.all('GrpPropNo').value,'grpcontno', 1,null);
}


//职业类别
function showOccupationCodeList(obj1,obj1Name,obj2,obj2Name){
	var keycode = event.keyCode;
	//回车的ascii码是13
	if(keycode!="13" && keycode!="9") {
		return;
	}
	var subValue= fm.OccupationCodeName.value;
	var subSql = "1 and occuplevel=#3# and occupationname like #%" + subValue + "%#";
	showCodeList('occupationcode',[obj1,obj1Name,obj2,obj2Name],[0,1,2,3],null,subSql,'1',1);
}

function showOccupationCodeListKey(obj1,obj1Name,obj2,obj2Name){
	var keycode = event.keyCode;
	//回车的ascii码是13
	if(keycode!="13" && keycode!="9") {
		return;
	}
	var subValue= fm.OccupationCodeName.value;
	var subSql = "1 and occuplevel=#3# and occupationname like #%" + subValue + "%#";
	showCodeList('occupationcode',[obj1,obj1Name,obj2,obj2Name],[0,1,2,3],null,subSql,'1',1);
}


/**
 * 选择主客户 --按回车键和Tab键时触发
 */
function selectMainUser() {
	
	var keyCode = event.keyCode;
	if (keyCode=="13"|| keyCode=="9") {
		if (!selectMainUserInfo()) {
			return false;
		}
	}
}

/**
 * 选择主客户详细方法
 */
 
 function selectMainUserInfo(){
	fm.mainCustNameTemp.value = fm.mainCustName.value;
	if(fm.mainCustNameTemp.value != fm.mainCustName.value.trim()){
		alert("录入主被保人姓名不能带空格！");
		return false;
	}
	
	if(fm.mainCustName.value !=null && fm.mainCustName.value !=''){
		
		var arrResult = new Array();
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
		tSQLInfo.setSqlId("LCInsuredAddSql4");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.mainCustName.value);
		
		arrResult = easyExecSql(tSQLInfo.getString());
		if(arrResult=='0'){
			alert("没有查询到主被保人，请先录入主被保人信息！");	
			fm.mainContNo.value="";
			fm.mainCustName.value="";
			fm.mainCustNo.value="";	
			return false;
		}
		
		var n=arrResult[0];
		if (n>1) {
			showMainLCInsuredInfo();//主被保人查询
			fm.mainContNo.value="";
			fm.mainCustName.value="";
			fm.mainCustNo.value="";	
						
		}else if(n==1){
						
			var arrResult = new Array();
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
			tSQLInfo.setSqlId("LCInsuredAddSql7");
			tSQLInfo.addSubPara(fm.GrpPropNo.value);
			tSQLInfo.addSubPara(fm.mainCustName.value);
			
			fm.mainContNo.value="";
			fm.mainCustName.value="";
			fm.mainCustNo.value="";
			arrResult = new Array();
			arrResult = easyExecSql(tSQLInfo.getString());
			fm.mainContNo.value = arrResult[0][0];
			fm.mainCustNo.value= arrResult[0][1];
			fm.mainCustName.value= arrResult[0][2];	
			
		}
	}
 }
 
 /**
 * 检查主被保人信息
 */
 function checkMain(){ 	
 	if(fm.relatomain.value !='00'){
 		
 		if(fm.mainCustName.value==''){
 			alert("请先录入主被保险人姓名！");
 			fm.InsuredName.value="";
			return false;				
 		}
 	} 	
 }

/**
 * 选择客户 --按回车键和Tab键时触发
 */
function selectUser() {
	
	var keyCode = event.keyCode;
	if (keyCode=="13"|| keyCode=="9") {
		if (!selectUserInfo()) {
			return false;
		}
	}
}

/**
 * 选择客户详细方法
 */
function selectUserInfo() {
	
	fm.InsuredNameTemp.value = fm.InsuredName.value;
	if (fm.InsuredNameTemp.value != fm.InsuredName.value.trim()) {
		alert("录入被保人姓名不能带空格！");
		return false;
	}
	if (fm.InsuredName.value!=null && fm.InsuredName.value!='') {
	
		var arrResult = new Array();
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
		tSQLInfo.setSqlId("LCInsuredAddSql4");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.InsuredName.value);
		
		arrResult = easyExecSql(tSQLInfo.getString());
		if(arrResult==null || arrResult.length==0){
			return ;
		}
		
		var n=arrResult[0];
		if (n>1) {
			showLCInsuredInfo();//客户查询
			emptyCustInfo();//清空被保人客户页面信息	
		} else if(n==1) {
			var arrResult = new Array();
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
			tSQLInfo.setSqlId("LCInsuredAddSql5");
			tSQLInfo.addSubPara(fm.GrpPropNo.value);
			tSQLInfo.addSubPara(fm.InsuredName.value);
						
			emptyCustInfo();
			arrResult = new Array();
			arrResult = easyExecSql(tSQLInfo.getString());
			fm.InsuredName.value = arrResult[0][0];
			fm.IDType.value = arrResult[0][1];
			fm.IDTypeName.value = arrResult[0][2];
			fm.IDNo.value = arrResult[0][3];
			fm.InsuredGender.value = arrResult[0][4];
			fm.InsuredGenderName.value = arrResult[0][5];
			fm.InsuredBirthDay.value = arrResult[0][6];
			fm.InsuredAppAge.value = arrResult[0][7];	
		} else {
			return;
		}
	}	
}

/**
 * 清空被保人基本信息
 */
function emptyCustInfo(){
	
	fm.InsuredName.value="";
	fm.IDType.value="";
	fm.IDTypeName.value="";
	fm.IDNo.value="";
	fm.InsuredGender.value="";
	fm.InsuredGenderName.value="";
	fm.InsuredBirthDay.value="";
	fm.InsuredAppAge.value="";	
}
	
/**
 * 客户姓名查出多条时，跳转到客户查询页面
 */
function showLCInsuredInfo() {
	
	var tGrpPropNo=fm.GrpPropNo.value;
	var tInsuredName=fm.InsuredName.value;
	var tManageCom=fm.tManageCom.value;
	window.open("./LCinsuredQueryMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredName="+tInsuredName+"&ManageCom="+tManageCom,"查询被保人信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
}

/**
 * 主被保人客户姓名查出多条时，跳转到主被保人客户查询页面
 */
function showMainLCInsuredInfo() {
	
	var tGrpPropNo=fm.GrpPropNo.value;
	var tmainCustName=fm.mainCustName.value;
	var tManageCom=fm.tManageCom.value;
	window.open("./LCinsuredMainQueryMain.jsp?GrpPropNo="+tGrpPropNo+"&mainCustName="+tmainCustName+"&ManageCom="+tManageCom,"查询主被保人信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
}


/**
 * 获取返回的客户数据
 */
function showUserInfo(tArr) {
	
	if (tArr!=null) {
		var Result=new Array();
		Result=tArr;
		fm.InsuredName.value = Result[0];
		fm.InsuredGenderName.value = Result[1];
		fm.InsuredGender.value = Result[6];
		fm.InsuredBirthDay.value= Result[2];
		fm.IDTypeName.value = Result[3];
		fm.IDType.value = Result[5];
		fm.IDNo.value = Result[4];
		fm.InsuredSeqNo.value = Result[7];	
	}
}

/**
 * 获取返回的主客户数据
 */
function showMainUserInfo(tArr){
	
	if (tArr!=null) {
		var Result=new Array();
		Result=tArr;	
		fm.mainContNo.value=Result[8];
		fm.mainCustNo.value=Result[7];
		fm.mainCustName.value=Result[0];
	}	
}

/**
 * 提交
 */
function submitForm() {
	
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.submit();
}

/**
 * 新增
 */
function addRecord() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * 修改
 */
function modifyRecord() {
	
	if(fm.mContNo.value == "" || fm.InsuredSeqNo.value ==""){
		alert("请选择一条被保人信息进行修改！");
		return false;
	}
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}	
	submitForm();
}

/**
 * 删除
 */
function deleteRecord() {	
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	trimBlank(fm);
	initPlanFlag();
	document.all("InsuredType").disabled=false;
	if(fm.InsuredType.value=='0'){
		if (!verifyInput2()) {
			return false;
		}
		
		if(!checkidtype()){
			return false;
		}
		if(fm.relatomain.value!='00'){
			if(fm.mainCustName.value==''){
				alert("请先录入主被保人信息！");
				return false;
			}
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
			tSQLInfo.setSqlId("LCInsuredAddSql10");
			tSQLInfo.addSubPara(fm.GrpPropNo.value);
			tSQLInfo.addSubPara(fm.mainContNo.value);
			tSQLInfo.addSubPara(fm.mainCustNo.value);
			
			var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(arrResult !=null){
			if(arrResult[0][0]!=ContPlanCode){
				alert("主被保险人的方案是共用保额，请选择和主被保险人一致的保险方案编编码【"+arrResult[0][0]+"】！");
				return false ;
			}
		}
		
		
		}else{
			fm.mainCustName.value="";
			fm.mainCustNo.value="";
		}
		
		if(fm.ContPlanCode.value==""){
			alert("方案编码不能为空！");
			return false;
		}
		
		if(fm.ProvinceCode.value !=""){
			if(fm.CityCode.value =="" || fm.CountyCode.value ==""){
				alert("详细地址必须录入市和区/县信息！");
				return false;	
			}
		}
//		tSQLInfo = new SqlClass();
//		tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
//		tSQLInfo.setSqlId("LCInsuredAddSql10");
//		tSQLInfo.addSubPara(fm.GrpPropNo.value);
//		tSQLInfo.addSubPara(fm.ContPlanCode.value);
//		tSQLInfo.addSubPara(fm.sysPlanCode.value);
//		
//		var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);	
//		if(arrResult !=null){
//			if(arrResult[0][0]=='02'){
//				if(fm.Salary.value==''){
//					alert("被保险人方案需要录入月薪！");
//					return false ;
//				}
//			}
//		}
		if (mOperate=="INSERT" || mOperate=="UPDATE") {
			
			// 校验生效日期
			var CurrentDate = fm.tCurrentDate.value;
			var birthday = fm.InsuredBirthDay.value;
			var LiscenceValidDate = fm.LiscenceValidDate.value;// 证件是否长期有效
			if(birthday>CurrentDate){
			 	alert("出生日期必须小于当前日期！");
				return false ;
			}
			/**if(fm.IDType.value=="11" && !checkProvCode(fm.IDNo.value.substring(0,2))){
				
			}**/
			// 校验职级信息
//			if(fm.Position.value !=""){
//				if(fm.JoinCompDate.value=="" || fm.Seniority.value==""){
//					alert("账户型归属请填写入司时间或工龄！");
//					return false ;
//				}
//			}

			// 校验证件有效期
			if(LiscenceValidDate !=""){
				if(LiscenceValidDate<CurrentDate){
				 alert("证件有效期需大于当前日期");
				 return false ;
				}
			}
					
			// 校验详细地址信息录入	
			if(!checkCity()){
				return false;
			}
			var bankFlag = 0;
			if(fm.HeadBankCode.value!=null || fm.HeadBankCode.value!=""){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_app.LCContCommonSql");
				tSQLInfo.setSqlId("LCContCommonSql9");
				tSQLInfo.addSubPara(fm.HeadBankCode.value);
				bankFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			}
			// 校验银行信息
			var tflag1 = true;
			if(bankFlag =='1'){
				if(fm.HeadBankCode.value==''&& fm.BankProvince.value==''&& fm.BankCity.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
					tflag1= true;
				}else if(fm.HeadBankCode.value !=''&& fm.BankProvince.value!=''&& fm.BankCity.value!='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
					tflag1= true;
				}else{
					tflag1=false;
				}
				
			} else {
				if(fm.HeadBankCode.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
					tflag1= true;
				}else if(fm.HeadBankCode.value !='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
					tflag1= true;
				}else{
					tflag1=false;
				}
			}
			if(tflag1==false){
				alert("请填写银行相关信息！");
				return false;
			}
		}
	
	}else if(fm.InsuredType.value=='1'){
		
		if(fm.JGContPlanCode.value==""){
			alert("方案编码不能为空！");
			return false;	
		}
		
		if (fm.InsuredPeoples.value == "") {
			alert("被保险人人数不能为空！");
			return false;
		}
		
		if (!isInteger(fm.InsuredPeoples.value)) {
			alert("被保险人人数必须录入大于0的整数！");
			return false;
		}
		
		if (fm.InsuredPeoples.value<=0) {
			alert("被保险人人数必须录入大于0的整数！");
			return false;
		}
	}
	
	if (fm.Seniority.value!="" && fm.Seniority.value<0) {
		alert("工龄必须大于等于0！");
		return false;
	}
	
	if (fm.GetYear.value!="" && fm.GetYear.value<0) {
		alert("领取年龄必须大于等于0！");
		return false;
	}
	
	return true;
}

/**
 *	银行信息4为隔开
 */
function FormatString(Field){
	
	var oldStr = Field.value;
	var newStr = transformStr(oldStr,4);
	Field.value = newStr;
}

function transformStr(Str,tLimit){

	var newStr = "";
	if(Str==null||Str==""){
		return newStr;
	}
	if(!isInteger(tLimit)){
		tLimit = 4;
	}
	Str = Str.replace(/\s/g,"");	
	var i=0;
	while(i<Str.length){
	
		newStr+=Str.charAt(i);
		if((i+1)%tLimit==0){
			newStr+=" ";
		} 
		i++;
	}
	return newStr;
}

/**
 * 获取页面信息
 */
function getCustomerInfo(){
	
	var tInsuredNo = fm.InsuredSeqNo.value;
	var tContNo = fm.mContNo.value;
	document.all("InsuredType").disabled=true;
	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LCInsuredAddSql1");
	}else if (_DBT==_DBM){
		tSQLInfo.setSqlId("LCInsuredAddSql17");
	}
	tSQLInfo.addSubPara(tContNo);
	tSQLInfo.addSubPara(tInsuredNo);
	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(arrResult != null){
		if(arrResult[0][0]=='0'){
			shownewPage.style.display="none";
			divQueryPage.style.display="";	
			fm.InsuredType.value=arrResult[0][0];
			fm.InsuredTypeName.value=arrResult[0][1];
			fm.relatomain.value = arrResult[0][2];
			if(arrResult[0][2] != '00'){
				mainname.style.display="";
				mainname1.style.display="";
				maincustno.style.display="";
				mainCustNo1.style.display="";
			}else{
				mainname.style.display="none";
				mainname1.style.display="none";
				maincustno.style.display="none";
				mainCustNo1.style.display="none";
			}	
			fm.relatomainName.value = arrResult[0][3];
			fm.InsuredName.value = arrResult[0][4];
			fm.IDType.value = arrResult[0][5];
			fm.IDTypeName.value = arrResult[0][6];
			fm.IDNo.value = arrResult[0][7];
			fm.InsuredGender.value = arrResult[0][8];
			fm.InsuredGenderName.value = arrResult[0][9];
			fm.InsuredBirthDay.value = arrResult[0][10];
			fm.InsuredAppAge.value = arrResult[0][11];
		
			fm.ContPlanCode.value = arrResult[0][12];
			fm.ContPlanCodeName.value = arrResult[0][13];
			fm.OccupationCode.value = arrResult[0][14];
			fm.OccupationCodeName.value = arrResult[0][15];
			fm.OccupationType.value = arrResult[0][16];
			fm.OccupationTypeName.value = arrResult[0][17];
			fm.ServerArea.value = arrResult[0][18];
			fm.ServiceArName.value = arrResult[0][19];
			fm.Substandard.value = arrResult[0][20];
			fm.SubstandardName.value = arrResult[0][21];
			fm.Social.value = arrResult[0][22];
			fm.SocialName.value = arrResult[0][23];
			fm.JoinCompDate.value = arrResult[0][24];
			fm.Seniority.value = arrResult[0][25];
			fm.Salary.value = arrResult[0][26];
			fm.WorkIDNo.value = arrResult[0][27];
			fm.ISLongValid.value = arrResult[0][28];
			fm.ISLongValidName.value = arrResult[0][29];
			fm.LiscenceValidDate.value = arrResult[0][30];
			fm.ComponyName.value = arrResult[0][31];
			fm.DeptCode.value = arrResult[0][32];
			fm.InsureCode.value = arrResult[0][33];
			//fm.SubCustomerNo.value = arrResult[0][34];
			fm.WorkAddress.value = arrResult[0][35];
			fm.SocialAddress.value = arrResult[0][36];
			fm.sysPlanCode.value = arrResult[0][37];
			fm.mainCustNo.value = arrResult[0][38];
			fm.mainCustName.value = arrResult[0][39];
			fm.InsuredPeoples.value = arrResult[0][40];
			fm.Position.value = arrResult[0][41];
			fm.PositionName.value = arrResult[0][42];
			
			if (arrResult[0][43]!=-1 && arrResult[0][43]!=0) {
				
				fm.GetYear.value = arrResult[0][43];
				fm.GetStartType.value = arrResult[0][44];
				fm.GetStartTypeName.value = arrResult[0][45];
			}
			var querySql = "";
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
			tSQLInfo.setSqlId("LCInsuredAddSql2");
			tSQLInfo.addSubPara(tContNo);
			tSQLInfo.addSubPara(tInsuredNo);

			var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(arrResult != null){
		
				fm.ProvinceName.value = arrResult[0][0];
				fm.ProvinceCode.value = arrResult[0][1];
				fm.CityName.value = arrResult[0][2];
				fm.CityCode.value = arrResult[0][3];
				fm.CountyName.value = arrResult[0][4];
				fm.CountyCode.value = arrResult[0][5];
				fm.DetailAddress.value = arrResult[0][6];
				fm.ZipCode.value = arrResult[0][7];
				fm.Phone.value = arrResult[0][8];
				fm.Mobile.value = arrResult[0][9];
				fm.EMail.value = arrResult[0][10];
				fm.MicroNo.value = arrResult[0][11];	
			}
		
			var querySql = "";
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
			tSQLInfo.setSqlId("LCInsuredAddSql3");
			tSQLInfo.addSubPara(tContNo);
			tSQLInfo.addSubPara(tInsuredNo);
	
			var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(arrResult != null){
		
				fm.AccName.value = arrResult[0][0];
				fm.BankAccNo.value = arrResult[0][1];
				fm.HeadBankCode.value = arrResult[0][2];
				fm.BankCodeName.value = arrResult[0][3];
				fm.BankProvince.value = arrResult[0][4];
				fm.BankProvinceName.value = arrResult[0][5];
				fm.BankCity.value = arrResult[0][6];
				fm.BankCityName.value = arrResult[0][7];
			}
			
		}else if(arrResult[0][0]=='1' || arrResult[0][0]=='2' || arrResult[0][0]=='3'){
			shownewPage.style.display="";
			divQueryPage.style.display="none";
			fm.bnfAdd.style.display = "none";
			
			fm.InsuredType.value=arrResult[0][0];
			fm.InsuredTypeName.value=arrResult[0][1];
			fm.JGContPlanCode.value = arrResult[0][12];
			fm.JGContPlanCodeName.value = arrResult[0][13];
			fm.JGsysContPlanCode.value = arrResult[0][37];
			fm.InsuredPeoples.value =  arrResult[0][40];
		}
	}
	divPol.style.display="";
	queryPol(tContNo,tInsuredNo);
	queryPayPlan(tContNo,tInsuredNo);
 }
 
 /**
 * 受益人信息维护
 */
function bnfaddRecord(){
	var tGrpPropNo = fm.GrpPropNo.value;
	var tInsuredSeqNo = fm.InsuredSeqNo.value;
	var tmContNo = fm.mContNo.value
	var tFlag = fm.Flag.value;

	if(tmContNo=="" && tInsuredSeqNo=="" ){
		alert("请先录入被保人信息才能录入受益人！");
		return false;
	}
	
	window.open("./LCinsuredBnfMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredNo="+tInsuredSeqNo+"&ContNo="+tmContNo+"&Flag="+tFlag,"添加受益人信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
}
 
 /**
 * 返回上一页面
 */
function returnBack(){
	top.opener.insuredQuery();
	top.close();
}

/**
 * 清除页面信息
 */
function clearPage(){
	
	fm.InsuredType.value="0";
	fm.InsuredTypeName.value="个人单";
	fm.JGContPlanCode.value="";
	fm.JGContPlanCodeName.value="";
	fm.ContPlanCode.value="";	
	fm.ContPlanCodeName.value="";	
	fm.InsuredPeoples.value="";
		
	fm.relatomain.value="00";
	fm.relatomainName.value="本人";
	fm.mainCustName.value="";
	fm.mainCustNo.value="";
	fm.InsuredName.value="";
	fm.IDType.value="";
	fm.IDTypeName.value="";
	fm.IDNo.value="";
	fm.InsuredGender.value="";
	fm.InsuredGenderName.value="";
	fm.InsuredBirthDay.value="";
	fm.InsuredAppAge.value="";
		
	fm.OccupationCode.value="";
	fm.OccupationCodeName.value="";
	fm.OccupationType.value="";
	fm.OccupationTypeName.value="";
	fm.ZipCode.value="";
	fm.EMail.value="";
	fm.MicroNo.value="";
	fm.Phone.value="";
	fm.Mobile.value="";
	fm.ProvinceName.value="";
	fm.ProvinceCode.value="";
	fm.CityName.value="";
	fm.CityCode.value="";
	fm.CountyName.value="";
	fm.CountyCode.value="";
	fm.DetailAddress.value="";
		
	fm.ServerArea.value="";
	fm.ServiceArName.value="";
	fm.Substandard.value="";
	fm.SubstandardName.value="";
	fm.Social.value="0";
	fm.SocialName.value="无";
		
	fm.Position.value="";
	fm.PositionName.value="";
	fm.JoinCompDate.value="";
	fm.Seniority.value="";
	fm.Salary.value="";
	fm.GetYear.value="";
	fm.GetStartType.value="";
	fm.GetStartTypeName.value="";
		
	fm.HeadBankCode.value="";
	fm.BankCodeName.value="";
	fm.AccName.value="";
	fm.BankAccNo.value="";
	fm.BankProvince.value="";
	fm.BankProvinceName.value="";
	fm.BankCity.value="";
	fm.BankCityName.value="";
	fm.WorkIDNo.value="";
	fm.ISLongValid.value="";
	fm.ISLongValidName.value="";
	fm.LiscenceValidDate.value="";
	fm.ComponyName.value="";
	fm.DeptCode.value="";
	fm.InsureCode.value="";
	//fm.SubCustomerNo.value="";
	//fm.SubCustomerName.value="";
	fm.WorkAddress.value="";
	fm.SocialAddress.value="";
	
	shownewPage.style.display="none";
	divQueryPage.style.display="";
	fm.bnfAdd.style.display = "";
}

/**
 * 修改保额信息
 */
function polSave(){
	var cont = QueryScanGrid.mulLineCount;
	if(cont =='0'){
		alert("个人险种信息没有需要修改的保额");
		return false;
	}
	for(var i=0;i < QueryScanGrid.mulLineCount;i++){
		var tContNo = QueryScanGrid.getRowColData(i,1);
		var tRiskCode = QueryScanGrid.getRowColData(i,2);
		var tDutyCode = QueryScanGrid.getRowColData(i,4);
		var tAmnt = QueryScanGrid.getRowColData(i,7);
		if(tAmnt ==""){
			alert("请录入第【"+(i+1)+"】行的保额信息！");
			return false;	
		}
	}

	mOperate = "POLSave";
	submitForm();
}

/**
 * 修改缴费项信息
 */
function payPlanSave(){
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条缴费项信息！");
		return false;
	}
	
	var tPolNo = PayPlanGrid.getRowColData(tSelNo-1, 1);
	var tDutyCode = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanCode = PayPlanGrid.getRowColData(tSelNo-1, 4);
	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 6);
	
	if (tPayPlanPrem =="") {
		alert("缴费金额不能为空！");
		return false;
	}
	
	if (!isNumeric(tPayPlanPrem)) {
		alert("第【"+(i+1)+"】行的缴费金额需要录入大于等于0的数字！");
		return false;
	}
	
	if (tPayPlanPrem<0) {
		alert("第【"+(i+1)+"】行的缴费金额需要录入大于等于0的数字！");
		return false;
	}
	
	mOperate = "PayPlanSave";
	submitForm();
}

/**
 * 修改投资信息
 */
function investSave(){
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条缴费项信息！");
		return false;
	}
	
	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 6);
	
	var tCount = InvestGrid.mulLineCount;
	if (tCount==0) {
		alert("没有需要修改的投资账户信息！");
		return false;
	}
	
	var tInvestMoneyFlag = false;//投资金额录入标志
	var tInvestRateFlag = false;//投资分配比例录入标志
	var tSumInvestMoney = 0;
	var tSumInvestRate = 0;
	for (var i=0;i < InvestGrid.mulLineCount;i++) {
		
		var tInvestMoney = InvestGrid.getRowColData(i,6);
		var tInvestRate = InvestGrid.getRowColData(i,7);
		
		if (tInvestMoney=="" && tInvestRate=="") {
			alert("第【"+(i+1)+"】行投资金额与投资分配比例不能同时为空！");
			return false;
		}
		
		if (tInvestMoney!="" && tInvestRate!="") {
			alert("第【"+(i+1)+"】行投资金额与投资分配比例不能同时录入！");
			return false;
		}
		
		if (tInvestMoney!="") {
			
			tInvestMoneyFlag = true;
			
			if (!isNumeric(tInvestMoney)) {
				alert("第【"+(i+1)+"】行的投资金额需要录入大于等于0的数字！");
				return false;
			}
			
			if (tInvestMoney<0) {
				alert("第【"+(i+1)+"】行的投资金额需要录入大于等于0的数字！");
				return false;
			}
			
			tSumInvestMoney = tSumInvestMoney + parseFloat(tInvestMoney);
		}
		
		if (tInvestRate!="") {
			
			tInvestRateFlag = true;
			
			if (!isNumeric(tInvestRate)) {
				alert("第【"+(i+1)+"】行的投资分配比例需要录入0-1之间的小数！");
				return false;
			}
			
			if (tInvestRate>1 || tInvestRate<0) {
				alert("第【"+(i+1)+"】行的投资分配比例需要录入0-1之间的小数！");
				return false;
			}
			
			tSumInvestRate = tSumInvestRate + parseFloat(tInvestRate);
		}
	}
	
	if (tInvestMoneyFlag && tInvestRateFlag) {
		alert("投资金额与投资分配比例不能同时录入！");
		return false;
	}
	
	if (tInvestMoneyFlag && tSumInvestMoney!=tPayPlanPrem) {
		alert("投资金额总和需要与缴费金额相等！");
		return false;
	}
	
	if (tInvestRateFlag && tSumInvestRate!=1) {
		alert("投资分配比例之和不等于1！");
		return false;
	}
	
	mOperate = "InvestSave";
	submitForm();
}

/**
 * 查询附属被保人信息
 */
 
 function showRelationtoMain(tContNo){
	
	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql8");
	tSQLInfo.addSubPara(tContNo);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.InsuredSeqNo.value);
	
	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(arrResult !='0'){
		divQuerySpe.style.display="";
		var querySql = "";
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
		tSQLInfo.setSqlId("LCInsuredAddSql9");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(tContNo);
		tSQLInfo.addSubPara(fm.InsuredSeqNo.value);
		
		turnPage2.queryModal(tSQLInfo.getString(), QueryInfoGrid,1, 1);
	}else{
		divQuerySpe.style.display="none";
	}
 }
 
 
 /**
 * 查询方案的保额类型
 */
 
 function amntTypeQer(){
	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql12");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.mContNo.value);
	
	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(arrResult !=null){
		fm.AmntFlag.value = arrResult[0][0];
		divPol.style.display="";
		divInsuredInfo3.style.display="";
	}else{
		fm.AmntFlag.value='';
		divPol.style.display="none";
		divInsuredInfo3.style.display="none";
	}
}


 /**
 * 校验详细地址信息
 */
function checkCity(){	
	var ProvinceCode =document.all('ProvinceCode').value;
	var CityCode =document.all('CityCode').value;
	var CountyCode =document.all('CountyCode').value;
		
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
		if(arrResult =='0'){
			alert("联系地址不存在或者关联不正确");
			return false;
		}
	}
	return true;
}

/**
* 初始化方案类型
*/
function initPlanFlag(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql14");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.sysPlanCode.value);
		
	fm.PlanFlag.value = easyExecSql(tSQLInfo.getString());
	 
}
