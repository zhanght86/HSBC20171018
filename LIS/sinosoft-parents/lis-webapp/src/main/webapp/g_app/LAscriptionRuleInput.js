/***************************************************************
 * <p>ProName：LAscriptionRuleInput.js</p>
 * <p>Title：归属规则维护</p>
 * <p>Description：归属规则维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;
var tSearch = 0;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var arrResult= new Array();
var tName;
var tNo =0;

window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus(){
	if(showInfo!=null){
		try{
			showInfo.focus();
		}
		catch(ex){
			showInfo=null;
		}
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ){

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
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		resetForm();
		if("Position"==fm.tOperate.value){
			initPosition();
			initAscription();
		} else if("Ascription"==fm.tOperate.value){
			initAscription();
		} else if("SpeAscription"==fm.tOperate.value){
			initSpeAscription();
		}
	}
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm(){
	try{
		initForm();
	}catch(re){
		alert("在LAAgent.js-->resetForm函数中发生异常:初始化界面错误!");
	}
}

function submitFunc(){
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

//提交前的校验、计算
function beforeSubmit(){  
	if(parseInt(fm.EndYears.value)<=parseInt(fm.StartYears.value)){
		alert("终止值应该大于起始值!");
		return false;
	}
	return true;
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow){
	if (cShow=="true"){
		cDiv.style.display="";
	}else{
		cDiv.style.display="none";
	}
}

function addPosition(){

	if(!verifyInput2()){
		return false;
	}
	if(!chkNotZh(trim(fm.PositionCode.value))){
		alert("职级代码应为数字或字母!");
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql5");
	tSQLInfo.addSubPara("spepositioncode");
	tSQLInfo.addSubPara(fm.PositionCode.value);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry=='1') {
		mSQLInfo = new SqlClass();
		mSQLInfo.setResourceName("app.LAscriptionRuleSql");
		mSQLInfo.setSqlId("LAscriptionRuleSql10");
		mSQLInfo.addSubPara("spepositioncode");
		var tPropEntry = easyExecSql(mSQLInfo.getString(), 1, 0, 1);
		alert("职级代码不能取以下特殊职级代码:"+tPropEntry);
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql6");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(fm.PositionCode.value);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry=='1'){
		alert("该职级代码已经存在!");
		return false;
	}
	fm.mOperate.value="INSERT||MAIN";
	fm.tOperate.value="Position";
	PositionGrid.delBlankLine();
	submitFunc();
	fm.action="./LAscriptionRuleSave.jsp?Operate=INSERT&GrpContNo="+tBussNo;
	fm.submit();
}
function updatePosition(){
	var tRow = PositionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if(!verifyInput2()){
		return false;
	}
	 if(!chkNotZh(trim(fm.PositionCode.value))){
		alert("职级代码应为数字或字母!");
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql5");
	tSQLInfo.addSubPara("spepositioncode");
	tSQLInfo.addSubPara(fm.PositionCode.value);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry=='1') {
		mSQLInfo = new SqlClass();
		mSQLInfo.setResourceName("app.LAscriptionRuleSql");
		mSQLInfo.setSqlId("LAscriptionRuleSql10");
		mSQLInfo.addSubPara("spepositioncode");
		var tPropEntry = easyExecSql(mSQLInfo.getString(), 1, 0, 1);
		alert("职级代码不能取以下特殊职级代码:"+tPropEntry);
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql6");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(PositionGrid.getRowColData(tRow-1,1));
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry!='1'){
		alert("该职级代码尚未定义!");
		return false;
	}
	fm.mOperate.value="UPDATE||MAIN";
	fm.tOperate.value="Position";
	submitFunc();
	fm.action="./LAscriptionRuleSave.jsp?Operate=UPDATE&SerialNo="+PositionGrid.getRowColData(tRow-1,5);
	fm.submit();
}
function deletePosition(){
	var tRow = PositionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	fm.mOperate.value="DELETE||MAIN";
	fm.tOperate.value="Position";
 	submitFunc();
	fm.action="./LAscriptionRuleSave.jsp?Operate=DELETE&SerialNo="+PositionGrid.getRowColData(tRow-1,5);
	fm.submit();
	fm.PositionCode.value='';
	fm.PositionCodeName.value='';
	fm.CountType.value='';
	fm.CountTypeName.value='';
}
//普通归属规则的操作
function saveAscription(){
	if(fm.AscriptionCode.value==""){
		alert("职级代码不能为空！");
		return false;
	}
	if(fm.StartYears.value==""||!isNumeric(fm.StartYears.value)){
		alert("起始值不能为空且为数字！");
		return false;
	}
	if(fm.EndYears.value==""||!isNumeric(fm.EndYears.value)){
		alert("终止值不能为空且为数字！");
		return false;
	}
	if(parseFloat(fm.EndYears.value)<=parseFloat(fm.StartYears.value)){
		alert("终止值必须大于起始值！");
		return false;
	}
	if(fm.Rate.value==""||parseFloat(fm.Rate.value)>1||parseFloat(fm.Rate.value)<0||!isNumeric(fm.Rate.value)){
		alert("归属比例不能为空且为大于等于0小于1的小数！");
		return false;
	}	
	if(!beforeSubmit()){
		return false;
	}	
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
//	tSQLInfo.setSqlId("LAscriptionRuleSql7");
//	tSQLInfo.addSubPara(tBussNo);
//	tSQLInfo.addSubPara(fm.AscriptionCode.value);
//	tSQLInfo.addSubPara(fm.StartYears.value);
//	tSQLInfo.addSubPara(fm.EndYears.value);
//	tSQLInfo.addSubPara(fm.StartYears.value);
//	tSQLInfo.addSubPara(fm.EndYears.value);
//	tSQLInfo.addSubPara(fm.StartYears.value);
//	tSQLInfo.addSubPara(fm.EndYears.value);
//	tSQLInfo.addSubPara("");
//	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	
//	if(tPropEntry!=null && tPropEntry[0][0]=='1'){
//		alert("该归属规则已经存在!");
//		return false;
//	}
	document.all('mOperate').value = "INSERT1||MAIN";
	document.all('tOperate').value = "Ascription"
	AscriptionGrid.delBlankLine();
	submitFunc();
	fm.action="./LAscriptionRuleSave.jsp?Operate=INSERT1&GrpContNo="+tBussNo;
	fm.submit();
}
function updateAscription(){
	var tRow = AscriptionGrid.getSelNo();
	if(tRow==0){
		alert("请选择一条记录在修改");
		return false;
	}
	
	if(fm.AscriptionCode.value==""){
		alert("职级代码不能为空！");
		return false;
	}
	if(fm.StartYears.value==""||!isNumeric(fm.StartYears.value)){
		alert("起始值不能为空且为数字！");
		return false;
	}
	if(fm.EndYears.value==""||!isNumeric(fm.EndYears.value)){
		alert("终止值不能为空且为数字！");
		return false;
	}
	if(fm.Rate.value==""||parseFloat(fm.Rate.value)>1||parseFloat(fm.Rate.value)<0||!isNumeric(fm.Rate.value)){
		alert("归属比例不能为空且为大于等于0小于1的小数！");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql7");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm.AscriptionCode.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(AscriptionGrid.getRowColData(tRow-1,6));
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(tPropEntry !=null && tPropEntry[0][0]=='1'){
		alert("已经存在相同区间规则!");
		return false;
	}
	if(!beforeSubmit()){
		return false;
	}	
	AscriptionGrid.delBlankLine();
	document.all('mOperate').value = "UPDATE1||MAIN";
	document.all('tOperate').value = "Ascription"
	submitFunc();
	fm.action="./LAscriptionRuleSave.jsp?Operate=UPDATE1&SerialNo="+AscriptionGrid.getRowColData(tRow-1,6);
	fm.submit();
}

function deleteAscription(){
	var tRow = AscriptionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	document.all('mOperate').value = "DELETE1||MAIN";
	document.all('tOperate').value = "Ascription"
	submitFunc();
	fm.action="./LAscriptionRuleSave.jsp?Operate=DELETE1&SerialNo="+AscriptionGrid.getRowColData(tRow-1,6);
	fm.submit();
	
}
//特殊归属规则操作
function saveSpeAscription(){
	if(document.all("multiageflag").checked==true){
		if(fm.SepicalType.value==''||fm.SepicalType.value==null){
			alert("归属类型不能为空!");
			return false;
		}	
		if(!isNumeric(fm.Rate1.value)||parseFloat(fm.Rate1.value)>1||parseFloat(fm.Rate1.value)<0){
			alert("归属比例为大于等于0小于1的小数！");
			return false;
		}
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql8");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(fm.SepicalType.value);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry=='1'){
		alert("该归属规则已经存在!");
		return false;
	}		
	SepicalAscriptionGrid.delBlankLine();
	submitFunc();
	document.all('tOperate').value = "SpeAscription"
	fm.action="./LAscriptionRuleSave.jsp?Operate=INSERT2&GrpContNo="+tBussNo;
	fm.submit();
}
function updateSpeAscription(){
	var tRow = SepicalAscriptionGrid.getSelNo();
	if(tRow==0){
		alert("请选择一条记录在修改");
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql8");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(SepicalAscriptionGrid.getRowColData(tRow-1,1));
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry!='1'){
		alert("该归属规则尚未定义!");
		return false;
	}
	if(fm.SepicalType.value==""){
		alert("归属类型不能为空！");
		return false;
	}
	if(!isNumeric(fm.Rate1.value)||parseFloat(fm.Rate1.value)>1||parseFloat(fm.Rate1.value)<0){
		alert("归属比例为大于等于0小于1的小数！");
		return false;
	}
	SepicalAscriptionGrid.delBlankLine();
	submitFunc();
	document.all('tOperate').value = "SpeAscription"
	fm.action="./LAscriptionRuleSave.jsp?Operate=UPDATE2&SerialNo="+SepicalAscriptionGrid.getRowColData(tRow-1,4);
	fm.submit();
}
function deleteSpeAscription(){
	var tRow = SepicalAscriptionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	submitFunc();
	document.all('tOperate').value = "SpeAscription"
	fm.action="./LAscriptionRuleSave.jsp?Operate=DELETE2&SerialNo="+SepicalAscriptionGrid.getRowColData(tRow-1,4);
	fm.submit();
}
function transData1(){
	var colNumb1=PositionGrid.getSelNo();//获取行号

	fm.PositionCode.value=PositionGrid.getRowColData(colNumb1-1,1);
	fm.PositionCodeName.value=PositionGrid.getRowColData(colNumb1-1,2);
	fm.CountType.value=PositionGrid.getRowColData(colNumb1-1,3);
	fm.CountTypeName.value=PositionGrid.getRowColData(colNumb1-1,4);
 	
}
function transData2(){
	
	var colNumb2=AscriptionGrid.getSelNo();
	
	fm.AscriptionCode.value=AscriptionGrid.getRowColData(colNumb2-1,1);
	fm.AscriptionCodeName.value=AscriptionGrid.getRowColData(colNumb2-1,2);
	fm.StartYears.value=AscriptionGrid.getRowColData(colNumb2-1,3);
	fm.EndYears.value=AscriptionGrid.getRowColData(colNumb2-1,4);
	fm.Rate.value=AscriptionGrid.getRowColData(colNumb2-1,5);
}
function transData3(){
	
	var colNumb3=SepicalAscriptionGrid.getSelNo();
	
	fm.SepicalType.value=SepicalAscriptionGrid.getRowColData(colNumb3-1,1);
	fm.SepicalTypeName.value=SepicalAscriptionGrid.getRowColData(colNumb3-1,2);
	fm.Rate1.value=SepicalAscriptionGrid.getRowColData(colNumb3-1,3);	
	
}
function getData(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql9");
	tSQLInfo.addSubPara(tBussNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	var tPropEntry = easyQueryVer3(tSQLInfo.getString(), 1, 0, 1); 
	if (tPropEntry==null) {
		return false;
	} else {
		document.all("AscriptionCode").CodeData=tPropEntry;
	}	
}
	
function sepicalTpye(){
	if(document.all("multiageflag").checked){
		DivshowSepical.style.display="";
 	}else{
		DivshowSepical.style.display="none";	
	}
}
function initPosition(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql1");
	tSQLInfo.addSubPara(tBussNo);
	initPositionGrid();
	initAscriptionGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PositionGrid, 1, 1);
}
function initAscription(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql2");
	tSQLInfo.addSubPara(tBussNo);
	initAscriptionGrid();
	turnPage2.queryModal(tSQLInfo.getString(), AscriptionGrid, 1, 1);
}
function initSpeAscription(){
	DivshowSepical.style.display='';
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql3");
	tSQLInfo.addSubPara(tBussNo);
	initSepicalAscriptionGrid();
	turnPage3.queryModal(tSQLInfo.getString(), SepicalAscriptionGrid, 1, 1);
	
	if(!turnPage3.strQueryResult){
		fm.multiageflag.checked=false;
		DivshowSepical.style.display='none';
	}else{
		fm.multiageflag.checked=true;
	}
}
function querySerialNo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LAscriptionRuleSql");
	tSQLInfo.setSqlId("LAscriptionRuleSql4");
	tSQLInfo.addSubPara(tBussNo);
	
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.RiskCode.value=tPropEntry[0][0];
	}
}
