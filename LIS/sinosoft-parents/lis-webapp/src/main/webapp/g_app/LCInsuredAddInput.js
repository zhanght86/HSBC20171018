/***************************************************************
 * <p>ProName��LCInsuredAddInput.js</p>
 * <p>Title����ӱ�����</p>
 * <p>Description����ӱ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
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
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �ύ���ݺ󷵻ز���

 */
function afterSubmit(tFlagStr, tContent,tContno,tInsuredNo) {
	
	if (typeof(showInfo) == "object" && typeof(showInfo) != "unknown") {
	
		showInfo.close();
	}
	if (tFlagStr == "Fail") {
	
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//���������ʱ��
		if(mOperate=="INSERT"){
			if(confirm("�Ƿ����¼�������ͻ���")){
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
 * ��ѯ������������Ϣ
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
 * ��ѯ�����˽ɷ�����Ϣ
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
 * ��ѯͶ���˻���Ϣ
 **/
function queryInvest(){
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ���ɷ�����Ϣ��");
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
	
	//���Ͷ����Ϣ����1�У���¼��Ͷ����Ϣ������ֱ��չʾ
	divInvest.style.display = "";
	if (InvestGrid.mulLineCount>1) {
		divInvestSaveButton.style.display = "";
	} else {
		divInvestSaveButton.style.display = "none";
	}
}

/**
 * ����ǰ̨ҳ�������������
 **/
function returnShowCodeList (value1, value2, value3) {

	if (value1=='bankprovince') {

		return showCodeList('province',value2,value3,null,null,null,null,180);

	} else if (value1=='bankcity') {
		if (isEmpty(fm.BankProvince)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		var tProvince = fm.BankProvince.value;
		
		return showCodeList('city',value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='city') {
		if (isEmpty(fm.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}	
		var tProvince = fm.ProvinceCode.value;
		
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
		
		var tCity = fm.CityCode.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

function returnShowCodeLisKey (value1, value2, value3) {

	if (value1=='bankprovince') {

		return showCodeList('province',value2,value3,null,null,null,null,180);

	} else if (value1=='bankcity') {
		if (isEmpty(fm.BankProvince)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		var tProvince = fm.BankProvince.value;
		
		return showCodeList('city',value2,value3,null,tProvince,'upplacename','1',180);
	} else if (value1=='city') {

		if (isEmpty(fm2.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		
		var tProvince = fm.ProvinceCode.value;
		
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
		
		var tCity = fm.CityCode.value;
		
		return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
	}
}

/**
 * ������ѡ�����
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
 * У�����֤�Լ����س������ں��Ա�����
 */
function checkidtype(){
	
	if(fm.IDNo.value.length>0 && fm.IDType.value=="") {
	 	 alert("����ѡ��֤�����ͣ�");	
		 return false;
	}
		
	if(fm.IDType.value=="0"&&fm.IDNo.value.length>0) {
		
		if((fm.IDNo.value.length!=15) &&(fm.IDNo.value.length!=18)){
			 alert("��������֤��λ������");
			 document.all('IDNo').value="";
			 return false;
		}
		if(!checkIdCard(fm.IDNo.value)) {
			 document.all('IDNo').value="";
			 document.all('IDNo').className = "warn";
			 return false;
		}else {
			
			if(!checkProvCode(fm.IDNo.value.substring(0,2))){
				alert("���֤��ǰ��λ���������ʹ����ȷ�����֤����!");
				return false;
			}
			fm.InsuredBirthDay.value =getBirthdatByIdNo(fm.IDNo.value);
			fm.InsuredGender.value = getSexByIDNo(fm.IDNo.value);	
			
			if(fm.InsuredGender.value=='0'){
				fm.InsuredGenderName.value ='��';
			}else if(fm.InsuredGender.value=='1'){
				fm.InsuredGenderName.value ='Ů';
			}
			fm.InsuredAppAge.value = calAge(fm.InsuredBirthDay.value);
			if(fm.InsuredAppAge.value<0 || fm.InsuredAppAge.value > 110){
				alert("��ȷ�ϳ��������Ƿ���ȷ,�������110�꣡");
				return false;
			}
		}
	}
	return true;
}

/**
 * ѡ�����ǰ������ѡ��ʡ��
 */
function checkProvince(){
	
	if(fm.Province.value == ""){
		alert("����ѡ��ʡ��");
		fm.City.value = "";
		fm.CityName.value = "";
	}
}
/**
 * ѡ�����ǰ������ѡ��ʡ��
 */
function checkBankProvince(){

	if(fm.BankProvince.value == ""){
		alert("����ѡ��ʡ��");
		fm.BankCity.value = "";
		fm.BankCityName.value = "";
	}
}
/**
 * ��չ�����ѯ����Ŀ

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


//��ʼ��ְ������������
function initPosition(cObj,cName){
	showCodeList('position',[cObj,cName], [0,1], null,document.all('GrpPropNo').value,'grpcontno', 1,null);
}


//ְҵ���
function showOccupationCodeList(obj1,obj1Name,obj2,obj2Name){
	var keycode = event.keyCode;
	//�س���ascii����13
	if(keycode!="13" && keycode!="9") {
		return;
	}
	var subValue= fm.OccupationCodeName.value;
	var subSql = "1 and occuplevel=#3# and occupationname like #%" + subValue + "%#";
	showCodeList('occupationcode',[obj1,obj1Name,obj2,obj2Name],[0,1,2,3],null,subSql,'1',1);
}

function showOccupationCodeListKey(obj1,obj1Name,obj2,obj2Name){
	var keycode = event.keyCode;
	//�س���ascii����13
	if(keycode!="13" && keycode!="9") {
		return;
	}
	var subValue= fm.OccupationCodeName.value;
	var subSql = "1 and occuplevel=#3# and occupationname like #%" + subValue + "%#";
	showCodeList('occupationcode',[obj1,obj1Name,obj2,obj2Name],[0,1,2,3],null,subSql,'1',1);
}


/**
 * ѡ�����ͻ� --���س�����Tab��ʱ����
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
 * ѡ�����ͻ���ϸ����
 */
 
 function selectMainUserInfo(){
	fm.mainCustNameTemp.value = fm.mainCustName.value;
	if(fm.mainCustNameTemp.value != fm.mainCustName.value.trim()){
		alert("¼�����������������ܴ��ո�");
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
			alert("û�в�ѯ���������ˣ�����¼������������Ϣ��");	
			fm.mainContNo.value="";
			fm.mainCustName.value="";
			fm.mainCustNo.value="";	
			return false;
		}
		
		var n=arrResult[0];
		if (n>1) {
			showMainLCInsuredInfo();//�������˲�ѯ
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
 * �������������Ϣ
 */
 function checkMain(){ 	
 	if(fm.relatomain.value !='00'){
 		
 		if(fm.mainCustName.value==''){
 			alert("����¼������������������");
 			fm.InsuredName.value="";
			return false;				
 		}
 	} 	
 }

/**
 * ѡ��ͻ� --���س�����Tab��ʱ����
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
 * ѡ��ͻ���ϸ����
 */
function selectUserInfo() {
	
	fm.InsuredNameTemp.value = fm.InsuredName.value;
	if (fm.InsuredNameTemp.value != fm.InsuredName.value.trim()) {
		alert("¼�뱻�����������ܴ��ո�");
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
			showLCInsuredInfo();//�ͻ���ѯ
			emptyCustInfo();//��ձ����˿ͻ�ҳ����Ϣ	
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
 * ��ձ����˻�����Ϣ
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
 * �ͻ������������ʱ����ת���ͻ���ѯҳ��
 */
function showLCInsuredInfo() {
	
	var tGrpPropNo=fm.GrpPropNo.value;
	var tInsuredName=fm.InsuredName.value;
	var tManageCom=fm.tManageCom.value;
	window.open("./LCinsuredQueryMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredName="+tInsuredName+"&ManageCom="+tManageCom,"��ѯ��������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
}

/**
 * �������˿ͻ������������ʱ����ת���������˿ͻ���ѯҳ��
 */
function showMainLCInsuredInfo() {
	
	var tGrpPropNo=fm.GrpPropNo.value;
	var tmainCustName=fm.mainCustName.value;
	var tManageCom=fm.tManageCom.value;
	window.open("./LCinsuredMainQueryMain.jsp?GrpPropNo="+tGrpPropNo+"&mainCustName="+tmainCustName+"&ManageCom="+tManageCom,"��ѯ����������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
}


/**
 * ��ȡ���صĿͻ�����
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
 * ��ȡ���ص����ͻ�����
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
 * �ύ
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.submit();
}

/**
 * ����
 */
function addRecord() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * �޸�
 */
function modifyRecord() {
	
	if(fm.mContNo.value == "" || fm.InsuredSeqNo.value ==""){
		alert("��ѡ��һ����������Ϣ�����޸ģ�");
		return false;
	}
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}	
	submitForm();
}

/**
 * ɾ��
 */
function deleteRecord() {	
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * �ύǰ��У�顢����
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
				alert("����¼������������Ϣ��");
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
				alert("���������˵ķ����ǹ��ñ����ѡ�������������һ�µı��շ�������롾"+arrResult[0][0]+"����");
				return false ;
			}
		}
		
		
		}else{
			fm.mainCustName.value="";
			fm.mainCustNo.value="";
		}
		
		if(fm.ContPlanCode.value==""){
			alert("�������벻��Ϊ�գ�");
			return false;
		}
		
		if(fm.ProvinceCode.value !=""){
			if(fm.CityCode.value =="" || fm.CountyCode.value ==""){
				alert("��ϸ��ַ����¼���к���/����Ϣ��");
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
//					alert("�������˷�����Ҫ¼����н��");
//					return false ;
//				}
//			}
//		}
		if (mOperate=="INSERT" || mOperate=="UPDATE") {
			
			// У����Ч����
			var CurrentDate = fm.tCurrentDate.value;
			var birthday = fm.InsuredBirthDay.value;
			var LiscenceValidDate = fm.LiscenceValidDate.value;// ֤���Ƿ�����Ч
			if(birthday>CurrentDate){
			 	alert("�������ڱ���С�ڵ�ǰ���ڣ�");
				return false ;
			}
			/**if(fm.IDType.value=="11" && !checkProvCode(fm.IDNo.value.substring(0,2))){
				
			}**/
			// У��ְ����Ϣ
//			if(fm.Position.value !=""){
//				if(fm.JoinCompDate.value=="" || fm.Seniority.value==""){
//					alert("�˻��͹�������д��˾ʱ����䣡");
//					return false ;
//				}
//			}

			// У��֤����Ч��
			if(LiscenceValidDate !=""){
				if(LiscenceValidDate<CurrentDate){
				 alert("֤����Ч������ڵ�ǰ����");
				 return false ;
				}
			}
					
			// У����ϸ��ַ��Ϣ¼��	
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
			// У��������Ϣ
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
				alert("����д���������Ϣ��");
				return false;
			}
		}
	
	}else if(fm.InsuredType.value=='1'){
		
		if(fm.JGContPlanCode.value==""){
			alert("�������벻��Ϊ�գ�");
			return false;	
		}
		
		if (fm.InsuredPeoples.value == "") {
			alert("����������������Ϊ�գ�");
			return false;
		}
		
		if (!isInteger(fm.InsuredPeoples.value)) {
			alert("����������������¼�����0��������");
			return false;
		}
		
		if (fm.InsuredPeoples.value<=0) {
			alert("����������������¼�����0��������");
			return false;
		}
	}
	
	if (fm.Seniority.value!="" && fm.Seniority.value<0) {
		alert("���������ڵ���0��");
		return false;
	}
	
	if (fm.GetYear.value!="" && fm.GetYear.value<0) {
		alert("��ȡ���������ڵ���0��");
		return false;
	}
	
	return true;
}

/**
 *	������Ϣ4Ϊ����
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
 * ��ȡҳ����Ϣ
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
 * ��������Ϣά��
 */
function bnfaddRecord(){
	var tGrpPropNo = fm.GrpPropNo.value;
	var tInsuredSeqNo = fm.InsuredSeqNo.value;
	var tmContNo = fm.mContNo.value
	var tFlag = fm.Flag.value;

	if(tmContNo=="" && tInsuredSeqNo=="" ){
		alert("����¼�뱻������Ϣ����¼�������ˣ�");
		return false;
	}
	
	window.open("./LCinsuredBnfMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredNo="+tInsuredSeqNo+"&ContNo="+tmContNo+"&Flag="+tFlag,"�����������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
}
 
 /**
 * ������һҳ��
 */
function returnBack(){
	top.opener.insuredQuery();
	top.close();
}

/**
 * ���ҳ����Ϣ
 */
function clearPage(){
	
	fm.InsuredType.value="0";
	fm.InsuredTypeName.value="���˵�";
	fm.JGContPlanCode.value="";
	fm.JGContPlanCodeName.value="";
	fm.ContPlanCode.value="";	
	fm.ContPlanCodeName.value="";	
	fm.InsuredPeoples.value="";
		
	fm.relatomain.value="00";
	fm.relatomainName.value="����";
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
	fm.SocialName.value="��";
		
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
 * �޸ı�����Ϣ
 */
function polSave(){
	var cont = QueryScanGrid.mulLineCount;
	if(cont =='0'){
		alert("����������Ϣû����Ҫ�޸ĵı���");
		return false;
	}
	for(var i=0;i < QueryScanGrid.mulLineCount;i++){
		var tContNo = QueryScanGrid.getRowColData(i,1);
		var tRiskCode = QueryScanGrid.getRowColData(i,2);
		var tDutyCode = QueryScanGrid.getRowColData(i,4);
		var tAmnt = QueryScanGrid.getRowColData(i,7);
		if(tAmnt ==""){
			alert("��¼��ڡ�"+(i+1)+"���еı�����Ϣ��");
			return false;	
		}
	}

	mOperate = "POLSave";
	submitForm();
}

/**
 * �޸Ľɷ�����Ϣ
 */
function payPlanSave(){
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ���ɷ�����Ϣ��");
		return false;
	}
	
	var tPolNo = PayPlanGrid.getRowColData(tSelNo-1, 1);
	var tDutyCode = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanCode = PayPlanGrid.getRowColData(tSelNo-1, 4);
	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 6);
	
	if (tPayPlanPrem =="") {
		alert("�ɷѽ���Ϊ�գ�");
		return false;
	}
	
	if (!isNumeric(tPayPlanPrem)) {
		alert("�ڡ�"+(i+1)+"���еĽɷѽ����Ҫ¼����ڵ���0�����֣�");
		return false;
	}
	
	if (tPayPlanPrem<0) {
		alert("�ڡ�"+(i+1)+"���еĽɷѽ����Ҫ¼����ڵ���0�����֣�");
		return false;
	}
	
	mOperate = "PayPlanSave";
	submitForm();
}

/**
 * �޸�Ͷ����Ϣ
 */
function investSave(){
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ���ɷ�����Ϣ��");
		return false;
	}
	
	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 6);
	
	var tCount = InvestGrid.mulLineCount;
	if (tCount==0) {
		alert("û����Ҫ�޸ĵ�Ͷ���˻���Ϣ��");
		return false;
	}
	
	var tInvestMoneyFlag = false;//Ͷ�ʽ��¼���־
	var tInvestRateFlag = false;//Ͷ�ʷ������¼���־
	var tSumInvestMoney = 0;
	var tSumInvestRate = 0;
	for (var i=0;i < InvestGrid.mulLineCount;i++) {
		
		var tInvestMoney = InvestGrid.getRowColData(i,6);
		var tInvestRate = InvestGrid.getRowColData(i,7);
		
		if (tInvestMoney=="" && tInvestRate=="") {
			alert("�ڡ�"+(i+1)+"����Ͷ�ʽ����Ͷ�ʷ����������ͬʱΪ�գ�");
			return false;
		}
		
		if (tInvestMoney!="" && tInvestRate!="") {
			alert("�ڡ�"+(i+1)+"����Ͷ�ʽ����Ͷ�ʷ����������ͬʱ¼�룡");
			return false;
		}
		
		if (tInvestMoney!="") {
			
			tInvestMoneyFlag = true;
			
			if (!isNumeric(tInvestMoney)) {
				alert("�ڡ�"+(i+1)+"���е�Ͷ�ʽ����Ҫ¼����ڵ���0�����֣�");
				return false;
			}
			
			if (tInvestMoney<0) {
				alert("�ڡ�"+(i+1)+"���е�Ͷ�ʽ����Ҫ¼����ڵ���0�����֣�");
				return false;
			}
			
			tSumInvestMoney = tSumInvestMoney + parseFloat(tInvestMoney);
		}
		
		if (tInvestRate!="") {
			
			tInvestRateFlag = true;
			
			if (!isNumeric(tInvestRate)) {
				alert("�ڡ�"+(i+1)+"���е�Ͷ�ʷ��������Ҫ¼��0-1֮���С����");
				return false;
			}
			
			if (tInvestRate>1 || tInvestRate<0) {
				alert("�ڡ�"+(i+1)+"���е�Ͷ�ʷ��������Ҫ¼��0-1֮���С����");
				return false;
			}
			
			tSumInvestRate = tSumInvestRate + parseFloat(tInvestRate);
		}
	}
	
	if (tInvestMoneyFlag && tInvestRateFlag) {
		alert("Ͷ�ʽ����Ͷ�ʷ����������ͬʱ¼�룡");
		return false;
	}
	
	if (tInvestMoneyFlag && tSumInvestMoney!=tPayPlanPrem) {
		alert("Ͷ�ʽ���ܺ���Ҫ��ɷѽ����ȣ�");
		return false;
	}
	
	if (tInvestRateFlag && tSumInvestRate!=1) {
		alert("Ͷ�ʷ������֮�Ͳ�����1��");
		return false;
	}
	
	mOperate = "InvestSave";
	submitForm();
}

/**
 * ��ѯ������������Ϣ
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
 * ��ѯ�����ı�������
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
 * У����ϸ��ַ��Ϣ
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
			alert("��ϵ��ַ�����ڻ��߹�������ȷ");
			return false;
		}
	}
	return true;
}

/**
* ��ʼ����������
*/
function initPlanFlag(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredAddSql");
	tSQLInfo.setSqlId("LCInsuredAddSql14");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.sysPlanCode.value);
		
	fm.PlanFlag.value = easyExecSql(tSQLInfo.getString());
	 
}
