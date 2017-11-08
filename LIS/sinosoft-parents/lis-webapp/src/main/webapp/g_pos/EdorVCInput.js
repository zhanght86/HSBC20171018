/***************************************************************
 * <p>ProName：EdorVCInput.js</p>
 * <p>Title：归属规则维护</p>
 * <p>Description：归属规则维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-13
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
	
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else{
		content = "保存成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		if("Position"==fm.tOperate.value){
			fm.PositionCode.value='';
			fm.PositionCodeName.value='';
			fm.CountType.value='';
			fm.CountTypeName.value='';
			initPosition();
			initAscription();
		} else if("Ascription"==fm.tOperate.value){
			fm.AscriptionCode.value='';
			fm.AscriptionCodeName.value='';
			fm.StartYears.value='';
			fm.EndYears.value='';
			fm.Rate.value='';
			initAscription();
		} else if("SpecAscription"==fm.tOperate.value){
			fm.SepicalType.value='';
			fm.SepicalTypeName.value='';
			fm.Rate1.value='';
			initSpeAscription();
		}
		querySerialNo();
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
		alert("年度终止值应该大于年度起始值!");
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
	
	if(!checkPosition()){
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql5");
	tSQLInfo.addSubPara("spepositioncode");
	tSQLInfo.addSubPara(fm.PositionCode.value);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry=='1') {
		mSQLInfo = new SqlClass();
		mSQLInfo.setResourceName("pos.EdorVCSql");
		mSQLInfo.setSqlId("EdorVCSql10");
		mSQLInfo.addSubPara("spepositioncode");
		var tPropEntry = easyExecSql(mSQLInfo.getString(), 1, 0, 1);
		alert("职级代码不能取以下特殊职级代码:"+tPropEntry);
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql6");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(fm.PositionCode.value);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry!=null && tPropEntry[0][0]=='1'){
		alert("该职级代码已经存在!");
		return false;
	}
	mOperate="INSERT";
	fm.tOperate.value="Position";
	submitFunc();
	fm.action="./EdorVCSave.jsp?Operate="+ mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	fm.submit();
}
function updatePosition(){
	var tRow = PositionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if(!checkPosition()){
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql5");
	tSQLInfo.addSubPara("spepositioncode");
	tSQLInfo.addSubPara(fm.PositionCode.value);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry=='1') {
		mSQLInfo = new SqlClass();
		mSQLInfo.setResourceName("pos.EdorVCSql");
		mSQLInfo.setSqlId("EdorVCSql10");
		mSQLInfo.addSubPara("spepositioncode");
		var tPropEntry = easyExecSql(mSQLInfo.getString(), 1, 0, 1);
		alert("职级代码不能取以下特殊职级代码:"+tPropEntry);
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql6");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(PositionGrid.getRowColData(tRow-1,1));
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry!='1'){
		alert("该职级代码尚未定义!");
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql11");
	tSQLInfo.addSubPara(PositionGrid.getRowColData(tRow-1,1));
	tSQLInfo.addSubPara(tGrpContNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry!=null && tPropEntry[0][0]=="1" && PositionGrid.getRowColData(tRow-1,1)!=fm.PositionCode.value){
		alert("该职级代码已经使用，不能修改职级编码!");
		return false;
	}
	
	mOperate="UPDATE";
	fm.tOperate.value="Position";
	submitFunc();
	fm.action="./EdorVCSave.jsp?Operate="+ mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	fm.submit();
}

function checkPosition(){
	
	fm.PositionCode.value = trim(fm.PositionCode.value);
	if(trim(fm.PositionCode.value)==null || trim(fm.PositionCode.value)==""){
		alert("职级代码不能为空!");
		return false;
	}

	if(!chkNotZh(trim(fm.PositionCode.value)) || fm.PositionCode.value.length>4){
		alert("职级代码应为数字或字母,且小于等于4位!");
		return false;
	}
	fm.PositionCodeName.value = trim(fm.PositionCodeName.value);
	if(trim(fm.PositionCodeName.value)==null || trim(fm.PositionCodeName.value)==""){
		alert("职级代码不能为空!");
		return false;
	}
	
	if(fm.PositionCodeName.value.length>20){
		alert("职级名称小于等于20位!");
		return false;
	}
	if(trim(fm.CountType.value)==null || trim(fm.CountType.value)==""){
		alert("请选择归属年度计算方式!");
		return false;
	}
	return true;
}
function deletePosition(){
	var tRow = PositionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql11");
	tSQLInfo.addSubPara(PositionGrid.getRowColData(tRow-1,1));
	tSQLInfo.addSubPara(tGrpContNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry!=null && tPropEntry[0][0]=="1"){
		alert("该职级代码已经使用，不能删除!");
		return false;
	}
	mOperate="DELETE";
	fm.tOperate.value="Position";
 	submitFunc();
	fm.action="./EdorVCSave.jsp?Operate="+ mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	fm.submit();
}
//普通归属规则的操作
function saveAscription(){
	if(fm.AscriptionCode.value==""){
		alert("职级代码不能为空！");
		return false;
	}
	if(fm.StartYears.value==""||!isNumeric(fm.StartYears.value)){
		alert("年度起始值不能为空且为数字！");
		return false;
	}
	if(fm.EndYears.value==""||!isNumeric(fm.EndYears.value)){
		alert("年度终止值不能为空且为数字！");
		return false;
	}
	if(fm.Rate.value==""||fm.Rate.value>1||fm.Rate.value<0||!isNumeric(fm.Rate.value)){
		alert("归属比例不能为空且为大于等于0小于1的小数！");
		return false;
	}	
	if(!beforeSubmit()){
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql7");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.AscriptionCode.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(tPropEntry!=null && tPropEntry[0][0]=='1'){
		alert("该归属规则已经存在或者区间交叉！");
		return false;
	}
	mOperate = "INSERT1";
	document.all('tOperate').value = "Ascription"
	AscriptionGrid.delBlankLine();
	submitFunc();
	fm.action="./EdorVCSave.jsp?Operate="+ mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
		alert("年度起始值不能为空且为数字！");
		return false;
	}
	if(fm.EndYears.value==""||!isNumeric(fm.EndYears.value)){
		alert("年度终止值不能为空且为数字！");
		return false;
	}
	if(fm.Rate.value==""||fm.Rate.value>1||fm.Rate.value<0||!isNumeric(fm.Rate.value)){
		alert("归属比例不能为空且为大于等于0小于1的小数！");
		return false;
	}
	if(!beforeSubmit()){
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql7");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.AscriptionCode.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(fm.StartYears.value);
	tSQLInfo.addSubPara(fm.EndYears.value);
	tSQLInfo.addSubPara(AscriptionGrid.getRowColData(tRow-1,6));
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(tPropEntry!=null && tPropEntry[0][0]=='1'){
		alert("该归属规则已经存在或者区间交叉！");
		return false;
	}
	mOperate = "UPDATE1";
	document.all('tOperate').value = "Ascription"
	submitFunc();
	fm.action="./EdorVCSave.jsp?Operate="+ mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	fm.submit();
}

function deleteAscription(){
	var tRow = AscriptionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	mOperate = "DELETE1";
	document.all('tOperate').value = "Ascription"
	submitFunc();
	fm.action="./EdorVCSave.jsp?Operate="+ mOperate+"&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	fm.submit();

}
//特殊归属规则操作
function saveSpeAscription(){
	if(document.all("multiageflag").checked==true){
		if(fm.SepicalType.value==''||fm.SepicalType.value==null){
			alert("归属类型不能为空!");
			return false;
		}	
		if(fm.Rate1.value>1||fm.Rate1.value<0||!isNumeric(fm.Rate1.value)){
			alert("归属比例为大于等于0小于1的小数！");
			return false;
		}
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql8");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(fm.SepicalType.value);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry=='1'){
		alert("该归属规则已经存在!");
		return false;
	}
	SepicalAscriptionGrid.delBlankLine();
	submitFunc();
	document.all('tOperate').value = "SpecAscription"
	fm.action="./EdorVCSave.jsp?Operate=INSERT2&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	fm.submit();
}
function updateSpeAscription(){
	var tRow = SepicalAscriptionGrid.getSelNo();
	if(tRow==0){
		alert("请选择一条记录在修改");
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql8");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(SepicalAscriptionGrid.getRowColData(tRow-1,1));
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tPropEntry!='1'){
		alert("该归属规则尚未定义!");
		return false;
	}
	if(fm.SepicalType.value==""){
		alert("归属类型不能为空！");
		return false;
	}
	if(fm.Rate1.value>1||fm.Rate1.value<0||!isNumeric(fm.Rate1.value)){
		alert("归属比例为大于等于0小于1的小数！");
		return false;
	}
	SepicalAscriptionGrid.delBlankLine();
	submitFunc();
	document.all('tOperate').value = "SpecAscription"
	fm.action="./EdorVCSave.jsp?Operate=UPDATE2&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	fm.submit();
}
function deleteSpeAscription(){
	var tRow = SepicalAscriptionGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	submitFunc();
	document.all('tOperate').value = "SpecAscription"
	fm.action="./EdorVCSave.jsp?Operate=DELETE2&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql9");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
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
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	initPositionGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PositionGrid, 1, 1);
	
}
function initAscription(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	initAscriptionGrid();
	turnPage2.queryModal(tSQLInfo.getString(), AscriptionGrid, 1, 1);
}
function initSpeAscription(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	initSepicalAscriptionGrid();
	turnPage3.queryModal(tSQLInfo.getString(), SepicalAscriptionGrid, 1, 1);
		
	if(!turnPage3.strQueryResult){
		fm.multiageflag.checked=false;
		DivshowSepical.style.display='none';
	}else{
		fm.multiageflag.checked=true;
		DivshowSepical.style.display='';
	}
}
function querySerialNo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorVCSql");
	tSQLInfo.setSqlId("EdorVCSql4");
	tSQLInfo.addSubPara(tGrpContNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.RiskCode.value=tPropEntry[0][0];
	}
}
