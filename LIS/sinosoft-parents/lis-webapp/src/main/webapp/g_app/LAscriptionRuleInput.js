/***************************************************************
 * <p>ProName��LAscriptionRuleInput.js</p>
 * <p>Title����������ά��</p>
 * <p>Description����������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
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
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
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

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ){

	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm(){
	try{
		initForm();
	}catch(re){
		alert("��LAAgent.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

function submitFunc(){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//�ύǰ��У�顢����
function beforeSubmit(){  
	if(parseInt(fm.EndYears.value)<=parseInt(fm.StartYears.value)){
		alert("��ֵֹӦ�ô�����ʼֵ!");
		return false;
	}
	return true;
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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
		alert("ְ������ӦΪ���ֻ���ĸ!");
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
		alert("ְ�����벻��ȡ��������ְ������:"+tPropEntry);
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
		alert("��ְ�������Ѿ�����!");
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
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!verifyInput2()){
		return false;
	}
	 if(!chkNotZh(trim(fm.PositionCode.value))){
		alert("ְ������ӦΪ���ֻ���ĸ!");
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
		alert("ְ�����벻��ȡ��������ְ������:"+tPropEntry);
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
		alert("��ְ��������δ����!");
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
		alert("����ѡ��һ����Ϣ��");
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
//��ͨ��������Ĳ���
function saveAscription(){
	if(fm.AscriptionCode.value==""){
		alert("ְ�����벻��Ϊ�գ�");
		return false;
	}
	if(fm.StartYears.value==""||!isNumeric(fm.StartYears.value)){
		alert("��ʼֵ����Ϊ����Ϊ���֣�");
		return false;
	}
	if(fm.EndYears.value==""||!isNumeric(fm.EndYears.value)){
		alert("��ֵֹ����Ϊ����Ϊ���֣�");
		return false;
	}
	if(parseFloat(fm.EndYears.value)<=parseFloat(fm.StartYears.value)){
		alert("��ֵֹ���������ʼֵ��");
		return false;
	}
	if(fm.Rate.value==""||parseFloat(fm.Rate.value)>1||parseFloat(fm.Rate.value)<0||!isNumeric(fm.Rate.value)){
		alert("������������Ϊ����Ϊ���ڵ���0С��1��С����");
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
//		alert("�ù��������Ѿ�����!");
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
		alert("��ѡ��һ����¼���޸�");
		return false;
	}
	
	if(fm.AscriptionCode.value==""){
		alert("ְ�����벻��Ϊ�գ�");
		return false;
	}
	if(fm.StartYears.value==""||!isNumeric(fm.StartYears.value)){
		alert("��ʼֵ����Ϊ����Ϊ���֣�");
		return false;
	}
	if(fm.EndYears.value==""||!isNumeric(fm.EndYears.value)){
		alert("��ֵֹ����Ϊ����Ϊ���֣�");
		return false;
	}
	if(fm.Rate.value==""||parseFloat(fm.Rate.value)>1||parseFloat(fm.Rate.value)<0||!isNumeric(fm.Rate.value)){
		alert("������������Ϊ����Ϊ���ڵ���0С��1��С����");
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
		alert("�Ѿ�������ͬ�������!");
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
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	document.all('mOperate').value = "DELETE1||MAIN";
	document.all('tOperate').value = "Ascription"
	submitFunc();
	fm.action="./LAscriptionRuleSave.jsp?Operate=DELETE1&SerialNo="+AscriptionGrid.getRowColData(tRow-1,6);
	fm.submit();
	
}
//��������������
function saveSpeAscription(){
	if(document.all("multiageflag").checked==true){
		if(fm.SepicalType.value==''||fm.SepicalType.value==null){
			alert("�������Ͳ���Ϊ��!");
			return false;
		}	
		if(!isNumeric(fm.Rate1.value)||parseFloat(fm.Rate1.value)>1||parseFloat(fm.Rate1.value)<0){
			alert("��������Ϊ���ڵ���0С��1��С����");
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
		alert("�ù��������Ѿ�����!");
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
		alert("��ѡ��һ����¼���޸�");
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
		alert("�ù���������δ����!");
		return false;
	}
	if(fm.SepicalType.value==""){
		alert("�������Ͳ���Ϊ�գ�");
		return false;
	}
	if(!isNumeric(fm.Rate1.value)||parseFloat(fm.Rate1.value)>1||parseFloat(fm.Rate1.value)<0){
		alert("��������Ϊ���ڵ���0С��1��С����");
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
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	submitFunc();
	document.all('tOperate').value = "SpeAscription"
	fm.action="./LAscriptionRuleSave.jsp?Operate=DELETE2&SerialNo="+SepicalAscriptionGrid.getRowColData(tRow-1,4);
	fm.submit();
}
function transData1(){
	var colNumb1=PositionGrid.getSelNo();//��ȡ�к�

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
