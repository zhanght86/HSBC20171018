/***************************************************************
 * <p>ProName��LCPropEntryInput.js</p>
 * <p>Title��Ͷ������Ϣ¼��</p>
 * <p>Description��Ͷ������Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
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
	��ʼ��ҳ��
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
	//��ϵ��ַ��ʼ��
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
	//�н������ʼ��
		tSQLInfo2 = new SqlClass();
		tSQLInfo2.setResourceName("g_app.LCPropEntrySql");
		tSQLInfo2.setSqlId("LCPropEntrySql4");
		tSQLInfo2.addSubPara(tGrpPropNo);
		turnPage2.queryModal(tSQLInfo2.getString(), ZJGrid, 1, 1);
		if(turnPage2.strQueryResult){
			divZJInfo.style.display='';
		}
	
	//��֤������
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
	
	//����֤������
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
	//��ʼ����������
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
	//�ر�Լ��
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
 * ����Ͷ��������
 */
function saveClick(){
	clearAddress();
	trimBlank(fm);
	if(!verifyInput2()){
		return false;
	}	
	if(fm.FoundDate.value!="" && fm.FoundDate.value > fm.tCurrentDate.value){
		alert("����ʱ������,����ʱ�䲻�����ڵ�ǰ����!");
		return false;
	}
	var tIDInfoRow = IDInfoGrid.mulLineCount;

	for(var i=0;i<tIDInfoRow;i++){

		if(IDInfoGrid.getRowColData(i,5)!=null&&IDInfoGrid.getRowColData(i,5)!=''){
			if(IDInfoGrid.getRowColData(i,5)<fm.tCurrentDate.value){
				alert("֤����Чֹ������ڵ�ǰ���ڣ�");
				return false;
			}
		}
		if((IDInfoGrid.getRowColData(i,5)!=null&&IDInfoGrid.getRowColData(i,5)!='')&&(IDInfoGrid.getRowColData(i,4)!=null&&IDInfoGrid.getRowColData(i,4)!='')){
			if(IDInfoGrid.getRowColData(i,5)<IDInfoGrid.getRowColData(i,4)){
				alert("֤����Чֹ�������֤����Ч���ڣ�");
				return false;
			}
		}
		for(var j=i+1;j<tIDInfoRow;j++){
			if(IDInfoGrid.getRowColData(i,1)==IDInfoGrid.getRowColData(j,1)){
				alert("����¼���ظ���֤�����ͱ��롿��");
				return false;
			}
		}
		if(fm.GrpIDType.value==IDInfoGrid.getRowColData(i,1)){
			alert("��λ֤���б��е�֤��������¼����е�֤�������ظ�����ȷ�ϣ�");
			return false;
		}
	}
	// У����ϸ��ַ��Ϣ
	if(!checkCity()){		
		return false;
	}
	if(!checkID()){
		return false;
	}
	
	// У�鹴ѡ��λ֤����Ϣ
	if(fm.IDInfoCheck.checked){		
		IDInfoGrid.delBlankLine();	
		var count = IDInfoGrid.mulLineCount;
		if(parseInt(count)==0){
			alert("����д��λ֤����ϸ��Ϣ��");
			return false;
		}
		
		// У�鵥λ֤����Ϣ
		if(!valIDNOInfo()){
			return false;	
		}	
	}

	var tCurrentDate = fm.tCurrentDate.value;		
	if(fm.CorIDExpiryDate.value != ""){
		if(fm.CorIDExpiryDate.value <tCurrentDate){
			alert("����֤����Чֹ�ڱ�����ڵ�ǰ���ڣ�");
			return false;			
		}	
	}
			
	if(fm.GrpIDExpiryDate.value !=""){		
		if(fm.GrpIDExpiryDate.value <tCurrentDate){
			alert("��λ֤����Чֹ�ڱ�����ڵ�ǰ���ڣ�");
			return false;			
		}	
	}
	
	if(fm.LinkIDExpiryDate.value !=""){	
		if(fm.LinkIDExpiryDate.value<tCurrentDate){
			alert("��ϵ��֤����Чֹ�ڱ�����ڵ�ǰ���ڣ�");
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
			alert("��ϵ��ַ�����ڻ��߹�������ȷ!");
			return false;
		}
		
		if(fm.Address.value ==""){
			alert("��ϵ��ַ��ϸ��ַ����Ϊ��!");
			return false; 
		}
	}
	return true;
}

function checkCoridtype(){
	
	if(fm.CorID.value.length>0 && fm.CorIDType.value=="") {
	 	 alert("����ѡ��֤�����ͣ�");	
		 return false;
	}
		
	if(fm.CorIDType.value=="11"&&fm.CorID.value.length>0) {
		
		if((fm.CorID.value.length!=15) &&(fm.CorID.value.length!=18)){
			 alert("��������֤��λ������");
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
		 alert("����ѡ��֤�����ͣ�");	
		return false;
	}

	if(fm.LinkIDType.value=="11"&&fm.LinkID.value.length>0) {

		if((fm.LinkID.value.length!=15) &&(fm.LinkID.value.length!=18)){
			alert("��������֤��λ������");
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
			alert("�н�ҵ���������н����������Ϣ��");
			return false;
		}
	}else {
		if(tZJRow>0){
			//alert("���н�ҵ�񣬲���Ҫ¼���н������Ϣ!");
			//return false;
		}
	}
	
	for(var i=0;i<tZJRow;i++){
			if(ZJGrid.getRowColData(i,1)==null||ZJGrid.getRowColData(i,1)==''){
				alert("�������н�������룡");
			return false;
		}
	
		for(var j=i+1;j<tZJRow;j++){
			if(ZJGrid.getRowColData(i,1)==ZJGrid.getRowColData(j,1)){
				alert("����¼���ظ����н�������롿��");
				return false;
			}  
		}
	}
	if(fm.GrpSpec.value.length>1500){
		alert("�����ر�Լ��¼���������¼��1500�ַ�����!");
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
			alert("����¼��������λ֤����Ϣ�ٹ�ѡ!");
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
//����
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
		window.open("./ChargeFeeMain.jsp?state=0&GrpPropNo="+tGrpPropNo,"�н�������ά��",'height=400,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,directories=no,location=no, status=no');
	}else{
		alert("ֱ��ҵ�񣬲���Ҫά���н������ѣ�");
		return false;
	}
}

function propInfo() {
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	window.location="./LCPropEntryInput.jsp";
}

/**
 * ����ǰ̨ҳ�������������
 **/
function returnShowCodeLis (value1, value2, value3) {

	if (value1=='city') {
	
		if (isEmpty(fm.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		var tProvince = fm.Province.value;
		
		return showCodeList(value1,value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='district') {

		if (isEmpty(fm.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		
		if (isEmpty(fm.CityName)) {
			alert("��ѡ���У�");
			return false;
		}
		
		var tCity = fm.City.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

function returnShowCodeLisKey (value1, value2, value3) {
	
	if (value1=='city') {
	
		if (isEmpty(fm.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		var tProvince = fm.Province.value;
		
		return showCodeList(value1,value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='district') {
		
		if (isEmpty(fm.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		if (isEmpty(fm.CityName)) {
			alert("��ѡ���У�");
			return false;
		}
		
		var tCity = fm.City.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

/**
 * ������ѡ�����
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
/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content,patch,fileName1) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
	ZJGrid.delBlankLine();//ɾ������
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //�ύ
}
//add by caiyc
function checkInfo(){
	//У�����Ͷ�������Ƿ�¼������
	if (isEmpty(fm.ValDateType)) {
		alert("��Լ��Ч�������Ͳ���Ϊ�գ�");
		fm.ValDateType.focus();
		return false;
	}
	
	if(fm.ValDateType.value =='1'){		
		if (isEmpty(fm.ValDate)) {
			alert("��Ч���ڲ���Ϊ�գ�");
			fm.ValDate.focus();
			return false;
		}
	}
	
	if (isEmpty(fm.InsuPeriod)) {
		
		alert("�������޲���Ϊ�գ�");
		fm.InsuPeriod.focus();
		return false;
	}

	if (!checkNumber(fm.InsuPeriod)) {
		fm.InsuPeriod.focus();
		return false;
	}
	if(!verifyInteger('�����ڼ�',fm.InsuPeriod.value)){
		alert("�����ڼ����Ϊ����!");
		fm.InsuPeriod.focus();
		return false;
	}
	if (isEmpty(fm.InsuPeriodFlag)) {
		
		alert("�����ڼ䵥λ����Ϊ�գ�");
		fm.InsuPeriodFlag.focus();
		return false;
	}
	if (isEmpty(fm.PayIntv)) {
		
		alert("�ɷѷ�ʽ����Ϊ�գ�");
		fm.PayIntv.focus();
		return false;
	}
	if (isEmpty(fm.PremMode)) {
		
		alert("���ѷ�̯��ʽ����Ϊ�գ�");
		fm.PremMode.focus();
		return false;
	}
	if (isEmpty(fm.PayType)) {
		
		alert("���ʽ����Ϊ�գ�");
		fm.PayType.focus();
		return false;
	}
	
	if (isEmpty(fm.SaleChnl)) {
		
		alert("������������Ϊ�գ�");
		fm.SaleChnl.focus();
		return false;
	}
	if (isEmpty(fm.ChnlType)) {
		
		alert("�����������Ͳ���Ϊ�գ�");
		fm.ChnlType.focus();
		return false;
	}
	if (isEmpty(fm.AppManageCom)) {
		
		alert("�б���������Ϊ�գ�");
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
		alert("���������б�����˾�������ڱ���¼�룡");
		return false;
	}**/
		
	return true;
}
//�ȼ�����
function showInfo(){
	document.all("divPlanDiv").innerHTML = showPlanDiv();
}
function showPlanDiv() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql5");
	tSQLInfo.addSubPara(tGrpPropNo);
	
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var PhonementCode;//���ӱ���
	var PhonementName;//��������
	var tControlFlag;//�Ƿ���¼���
	var tIsSelected;//����quotno���жϸ�����ֵ�Ƿ���ѯ����Լ����
	var tOElementValue;//ԭʼֵ
	var tNElementValue;//ѯ��ֵ
	
	//var tInnerHTML0 = "<table class=common><tr class=common style='display:none'><td class=input></td><td class=input></td><td class=input></td><td class=input></td></tr>";
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>���ϲ㼶���ֱ�׼<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		PhonementCode = tArr[i][0];
		PhonementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];//�����ж��Ƿ�ѡ��
		tOElementValue = tArr[i][4];//ԭʼֵ
		tNElementValue = tArr[i][5];//ѯ��ֵ
		
		tInnerHTML1 += "<input type=checkbox name="+ PhonementCode;
		if (tIsSelected=='0') {//ѯ�۱���û�б��������

			tInnerHTML1 += ">"+ PhonementName;
		} else {//ѯ���б����˸�����

			tInnerHTML1 += " checked>"+ PhonementName;
		}
		
		if (tControlFlag=='1') {//����¼���

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
 * ��null���ַ���תΪ��
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}

	return string;
}
//��ѯ�н����
function queryAgentCom() {
	var tSelNo = ZJGrid.lastFocusRowNo;//�кŴ�0��ʼ
	var agentManageCom = "";//fm.AppManageCom.value;
	var agentChnlType = "";//fm.ChnlType.value;
	var agentSaleChnl = "";//fm.SaleChnl.value;
	var strUrl = "LCGrpQueryAgentComMain.jsp?SelNo="+tSelNo+"&Flag=2&ManageCom="+agentManageCom+"&ChnlType="+agentChnlType+"&SaleChnl="+agentSaleChnl;
	window.open(strUrl,"�н������ѯ",'height=600,width=900,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}
/**
 * ģ����������
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


//У�鱣�ϲ㼶  add by caiyc
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
		alert("���빴ѡ���ϲ㼶!");
		return false;
	}
	if(fm.quotplandiv99.checked==true){
		if( fm.quotplandiv99Value.value==''|| fm.quotplandiv99Value.value==null){
			alert("��ѡ����ʱ������д����!");
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
		alert("���ȱ���Ͷ�������ϣ�");
		return false;
	}
	if(!(tPropEntry6=='13'||tPropEntry6 =='1')){
		alert("���ȱ������Ͷ����Ϣ��");
		return false;
	}
	if("02"==fm.ContPlanType.value){
		alert("�˻��Ͳ�Ʒ���޷�������Ա�嵥ά����");
		return false;
	}
	var strUrl = "LCPropEntrytoAddMain.jsp?GrpPropNo="+tGrpPropNo+"&Flag="+tFlag+"&ActivityID=0&PolicyType="+fm.PolicyType.value;
	window.open(strUrl,"��Ա�嵥ά��",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		
 }
function checkID(){
	if((fm.GrpIDType.value==null||fm.GrpIDType.value=='')&&fm.GrpIDNo.value!=null&&fm.GrpIDNo.value!=''){
		alert("��¼�뵥λ֤������!");
		return false;
	}
	if((fm.GrpIDType.value!=null&&fm.GrpIDType.value!='')&&(fm.GrpIDNo.value==null||fm.GrpIDNo.value=='')){
		alert("��¼�뵥λ֤������!");
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
		alert("���ȱ������Ͷ����Ϣ��");
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
 * �ж��Ƿ�¼���н�������
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
			alert("�����н�û��¼���н�������!");
			return false;
		}
	}
	return true;
}


/**
 * У�鵥λ֤������
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
					alert("��"+(i+1)+"�С�֤�����ͱ��롿��Ϊ����¼�롾֤�����롿��");
					return false;
				}
				
				if(IDInfoGrid.getRowColData(i,1)== fm.GrpIDType.value){
					alert("��"+(i+1)+"�С�֤�����ͱ��롿�Ѿ�¼�벻���ظ���");
					return false;
				}	
			}else {
				if(IDInfoGrid.getRowColData(i,3)!=''){
					alert("��"+(i+1)+"�С�֤�����롿��Ϊ����¼�롾֤�����ͱ��롿��");
					IDInfoGrid.getRowColData(i,3)="";
					return false;
				}
			}
			
			if(IDInfoGrid.getRowColData(i,5) !=''){
				if(IDInfoGrid.getRowColData(i,5)<tCurrentDate){
					alert("��"+(i+1)+"�е�λ֤����Чֹ������ڵ�ǰ����");
					return false;
				}
			}
			
			for (var j=i+1;j<tCount;j++ ){
				
				if(IDInfoGrid.getRowColData(i,1)== IDInfoGrid.getRowColData(j,1)){
					alert("��["+(i+1)+"]�е�λ֤���������["+(j+1)+"]�е�λ֤�������ظ���");
					return false;
				}
				
				if(IDInfoGrid.getRowColData(i,3)== IDInfoGrid.getRowColData(j,3)){
					alert("��["+(i+1)+"]�е�λ֤���������["+(j+1)+"]�е�λ֤�������ظ���");
					return false;
				}
			}
		}
	}
	
	return true;
}

/***
 * �ж��Ƿ��ǵ��Ը����ƻ����Ƿ����˻��ͱ���
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
** �ļ�����
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
//����ȷ����
function printPremConf(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropEntrySql");
	tSQLInfo.setSqlId("LCPropEntrySql12");
	tSQLInfo.addSubPara(tGrpPropNo);
	var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);	
	if(tRes==null || tRes[0][0]!='1'){
		alert("���ȴ�ӡͶ���飡");
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
