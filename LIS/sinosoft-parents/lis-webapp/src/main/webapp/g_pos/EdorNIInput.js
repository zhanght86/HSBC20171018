/***************************************************************
 * <p>ProName��EdorNIInput.js</p>
 * <p>Title��������������</p>
 * <p>Description��������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();
var tQuerFlag;

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(tFlagStr, tContent,tInsuredid) {

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

		if(mOperate == "INSERT"|| mOperate == "CHOSEDEL" || mOperate == "CONDDEL" || mOperate == "ALLDEL"){
			document.all("InsuredType").disabled=false;
			clearPage();
			queryInsured(2);
			initBnfGrid();
			initAmntGrid();
			initPayPlanGrid();
			initInvestGrid();
			initQueryInfoGrid();
			divQuerySpe.style.display="none";
			divQueryShow.style.display="none";
			divPayPlan.style.display ="none";
			divInvest.style.display ="none";
		}else if(mOperate == "UPDATE"){
			clearPage();
			initQueryInfoGrid();
			initBnfGrid();
			//queryBnf(tInsuredid);
			queryInsured(2);
			initAmntGrid();
			initPayPlanGrid();
			initInvestGrid();
			divQueryShow.style.display="none";
			shownewPage.style.display="none";
			divQueryPage.style.display="";
			divQuerybnf.style.display="";
			divQuerySpe.style.display="none";
			divPayPlan.style.display ="none";
			divInvest.style.display ="none";
		} else if(mOperate == "PayPlanSave"){
			queryPayPlan();
		} else if(mOperate == "InvestSave"){
			queryPayPlan();
		}
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
 * ��ѯ
 **/
function queryInsured(o){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql6");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.InsuredNameQ.value);
	tSQLInfo.addSubPara(fm.IdNoQ.value);
	tSQLInfo.addSubPara(fm.InsuredNoQ.value);
	tSQLInfo.addSubPara(fm.PlanCodeQ.value);
	tSQLInfo.addSubPara(fm.ImpBatch.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(NullToEmpty(fm.EdorNo.value));
	tSQLInfo.addSubPara(fm.BatchNo.value);

	initEdorDetailGrid();
	clearPage();
	initBnfGrid();
	initAmntGrid();
	initPayPlanGrid();
	initInvestGrid();

	turnPage1.queryModal(tSQLInfo.getString(), EdorDetailGrid, 1, 1);
	divQueryShow.style.display ="none";
	divQuerySpe.style.display = "none";
	divPayPlan.style.display ="none";
	divInvest.style.display ="none";

	if(o=='1'){
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}

}

/**
* ѡ��ɾ��
*/
function choseDelet(){
	
	var tSelNo = EdorDetailGrid.getSelNo();
	if(tSelNo=='0'){
		alert("��ѡ��һ����������Ϣ����ɾ����");
		return false;
	}
	
	mOperate = "CHOSEDEL";
	submitForm();
}

/**
* ����ɾ��
*/
function condDelete(){

	if(fm.InsuredNameQ.value =="" && fm.IdNoQ.value=="" && fm.InsuredNoQ.value=="" && fm.PlanCodeQ.value=="" && fm.ImpBatch.value==""){
		alert("������¼��һ����ѯ������������ɾ��������");
		return false;
	}

	var tCount =  EdorDetailGrid.mulLineCount;
	if(tCount =='0'){
		alert("û����Ҫ���С�����ɾ���������ݣ�");
		return false;
	}

	mOperate = "CONDDEL";
	submitForm();

}

/**
* ȫ��ɾ��
*/
function allDelete(){

	if(!confirm("�Ƿ�ȷ��ִ��ȫ��ɾ��������")){
		return false;
  }

	mOperate = "ALLDEL";
	submitForm();
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

	var tSelNo = EdorDetailGrid.getSelNo();
	if(tSelNo=='0'){
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
 * �޸Ľɷ�����Ϣ
 */
function payPlanSave(){

	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ���ɷ�����Ϣ��");
		return false;
	}

	var tBatchNo = PayPlanGrid.getRowColData(tSelNo-1, 1);
	var tInsuredID = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanID = PayPlanGrid.getRowColData(tSelNo-1, 3);
	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 8);

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

	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 8);

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
 * ����ǰ̨ҳ�������������
 **/
function returnShowCodeList (value1, value2, value3) {

	if (value1=='bankprovince') {

		return showCodeList('province',value2,value3,null,null,null,'1',180);

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

	if (value1=='city') {

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
	
	if(tSelectValue=='bnftype'){
		if(tObj.value == "����������"){
			var rowNumber = BnfGrid.lastFocusRowNo;
			BnfGrid.setRowColData(rowNumber, 14, "1");
		}
	}
	if(tSelectValue=='relation'){
		if (fm.relatomain.value!='00') {
			mainname.style.display="";
			mainname1.style.display="";
			mainIDNo.style.display="";
			mainCustNo1.style.display="";
			id1.style.display="none";
			id2.style.display="none";
			id3.style.display="none";
			id4.style.display="none";

		}else{
			mainname.style.display="none";
			mainname1.style.display="none";
			mainIDNo.style.display="none";
			mainCustNo1.style.display="none";
			id1.style.display="";
			id2.style.display="";
			id3.style.display="";
			id4.style.display="";
		}
	}else if(fm.InsuredType.value=='1'){
		shownewPage.style.display="";
		divQueryPage.style.display="none";
		divQuerybnf.style.display="none";
		}else{
		shownewPage.style.display="none";
		divQueryPage.style.display="";
		divQuerybnf.style.display="";
	}

	if(tSelectValue=='contplan'){
		initAmntGrid();
		var querySql = "";
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorNISql");
		tSQLInfo.setSqlId("EdorNISql12");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.ContPlanCode.value);

		var arrRsult= easyExecSql(tSQLInfo.getString());
		if(arrRsult!=null){
			fm.AmntFlag.value = "ShowAmnt";
			divQueryShow.style.display="";

			turnPage4.queryModal(tSQLInfo.getString(), AmntGrid, 1,1,30);
		}else {
			fm.AmntFlag.value = "";
			divQueryShow.style.display="none";
		}

		if(fm.relatomain.value!='00'){

			if(fm.mainCustName.value==""){
				alert("����¼��������������Ϣ��");
				return false;
			}

			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNISql");
			tSQLInfo.setSqlId("EdorNISql15");
			tSQLInfo.addSubPara(fm.GrpPropNo.value);
			tSQLInfo.addSubPara(fm.mainCustName.value);
			tSQLInfo.addSubPara(fm.mainCustIDNo.value);

			var arrResulter = easyExecSql(tSQLInfo.getString());
			if(arrResulter !=null){
				if(fm.ContPlanCode.value != arrResulter[0][0]){
					alert("�������˵��������˷������ڡ�������������������������Ϊ��"+arrResulter[0][0]+"��");
					fm.ContPlanCode.value="";
					fm.ContPlanCodeName.value="";
					return false;
				}
			}
		}
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
			fm.InsuredBirthDay.value =getBirthdatByIdNo(fm.IDNo.value);
			fm.InsuredGender.value = getSex(fm.IDNo.value);

			if(fm.InsuredGender.value=='0'){
				fm.InsuredGenderName.value ='��';
			}else if(fm.InsuredGender.value=='1'){
				fm.InsuredGenderName.value ='Ů';
			}
			fm.InsuredAppAge.value = calAge(fm.InsuredBirthDay.value);
		}
	}
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
 * ��չ�����ѯ����Ŀ
 */
function clearInput(codeInput,nameInput) {
	codeInput.value = "";
	nameInput.value = "";
}

/**
 * ��ѯ������Ϣ
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	var tSQL = "";
	tSQL = "1 and (a.plantype in (#00#,#02#) or a.premcaltype=#1#) and a.grpcontno=#" + document.all('GrpPropNo').value + "# ";
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,tSQL,'1',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	var tSQL = "";
	tSQL = "1 and (a.plantype in (#00#,#02#) or a.premcaltype=#1#) and a.grpcontno=#" + document.all('GrpPropNo').value + "# ";
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,tSQL,'1',1,null);
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
		tSQLInfo.setResourceName("g_pos.EdorNISql");
		tSQLInfo.setSqlId("EdorNISql4");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.mainCustName.value);
		tSQLInfo.addSubPara(fm.mainCustIDNo.value);

		arrResult = easyExecSql(tSQLInfo.getString());
		if(arrResult=='0'){
			alert("û�в�ѯ����������,ͬʱ���������������ˡ��Ļ���ʹ�õ���ģ�壡");
			fm.mainInsuredNo.value="";
			fm.mainCustName.value="";
			fm.mainIDNo.value="";
			return false;
		}

		var n=arrResult[0];
		if (n>1) {
			showMainLCInsuredInfo();//�������˲�ѯ
			fm.mainInsuredNo.value="";
			fm.mainCustName.value="";
			fm.mainCustIDNo.value="";

		}else if(n==1){

			var arrResult = new Array();
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNISql");
			tSQLInfo.setSqlId("EdorNISql7");
			tSQLInfo.addSubPara(fm.GrpPropNo.value);
			tSQLInfo.addSubPara(fm.mainCustName.value);
			tSQLInfo.addSubPara(fm.mainCustIDNo.value);

			fm.mainInsuredNo.value="";
			fm.mainCustName.value="";
			fm.mainCustIDNo.value="";
			arrResult = new Array();
			arrResult = easyExecSql(tSQLInfo.getString());
			fm.mainInsuredNo.value = arrResult[0][1];
			fm.mainCustName.value= arrResult[0][2];
			fm.mainCustIDNo.value= arrResult[0][3];

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
		tSQLInfo.setResourceName("g_pos.EdorNISql");
		tSQLInfo.setSqlId("EdorNISql3");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.InsuredName.value);

		arrResult = easyExecSql(tSQLInfo.getString());
		if(arrResult==0){
			emptyCustInfo();
			return ;
		}

		var n=arrResult[0];
		if (n>1) {
			showLCInsuredInfo();//�ͻ���ѯ
			emptyCustInfo();//��ձ����˿ͻ�ҳ����Ϣ
		} else if(n==1) {
			var arrResult = new Array();
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNISql");
			tSQLInfo.setSqlId("EdorNISql5");
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
	window.open("./EdorNIQueryMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredName="+tInsuredName+"&ManageCom="+tManageCom,"��ѯ��������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �������˿ͻ������������ʱ����ת���������˿ͻ���ѯҳ��
 */
function showMainLCInsuredInfo() {

	var tGrpPropNo=fm.GrpPropNo.value;
	var tmainCustName=fm.mainCustName.value;
	var tmainIDNO = fm.mainCustIDNo.value;
	var tEdorAppNo = fm.EdorAppNo.value ;
	var tEdorType = fm.EdorType.value ;
	window.open("./EdorMQueryMain.jsp?GrpPropNo="+tGrpPropNo+"&mainCustName="+tmainCustName+"&ManageCom="+tManageCom+"&MainIDNO="+tmainIDNO+"&EdorAppNo="+tEdorAppNo+"&EdorType="+tEdorType,"��ѯ����������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
		//fm.InsuredNo.value = Result[7];
	}
}

/**
 * ��ȡ���ص����ͻ�����
 */
function showMainUserInfo(tArr){

	if (tArr!=null) {
		var Result=new Array();
		Result=tArr;
		fm.mainInsuredNo.value=Result[7];
		fm.mainCustIDNo.value=Result[4];
		fm.mainCustName.value=Result[0];
	}
}


/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {

	document.all("InsuredType").disabled=false;
	if(fm.InsuredType.value=='0'){
		if (!verifyInput2()) {
			document.all("InsuredType").disabled=true;
			return false;
		}

		if(fm.relatomain.value!='00'){
			if(fm.mainCustName.value==''){
				alert("����¼������������Ϣ��");
				return false;
			}
		}

		if(fm.ContPlanCode.value==""){
			alert("�������벻��Ϊ�գ�");
			return false;
		}

//		tSQLInfo = new SqlClass();
//		tSQLInfo.setResourceName("g_pos.EdorNISql");
//		tSQLInfo.setSqlId("EdorNISql10");
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
			if(fm.edorValDate.value ==''){
				alert("��¼�뱣ȫ��Ч���ڣ�");
				return false ;
			}

			// У����Ч����
			var CurrentDate = fm.CurrentDate.value;
			var birthday = fm.InsuredBirthDay.value;
			var LiscenceValidDate = fm.LiscenceValidDate.value;// ֤���Ƿ�����Ч
			if(birthday>CurrentDate){
			 	alert("�������ڱ���С�ڵ�ǰ���ڣ�");
				return false ;
			}

//			// У��ְ����Ϣ
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

			// У����ϸ��Ϣ¼��
			if(fm.ProvinceCode.value !=""){
				if(fm.CityCode.value=="" || fm.CountyCode.value ==""||fm.DetailAddress.value=="" ){
					alert("��ϸ��Ϣ��ʡ��Ϣ��Ϊ�գ���Ҫ¼����/��/��ϸ��Ϣ��");
					return false ;
				}
			}

			// У����ϸ��Ϣ¼��
			if(!checkCity()){
				return false;
			}
			// У��������Ϣ
			var tflag1 = true;
			var bankFlag = 0;
			if(fm.HeadBankCode.value!=null || fm.HeadBankCode.value!=""){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_app.LCContCommonSql");
				tSQLInfo.setSqlId("LCContCommonSql9");
				tSQLInfo.addSubPara(fm.HeadBankCode.value);
				bankFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			}
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

		if(fm.HeadBankCode.value != ''){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorCommonSql");
			tSQLInfo.setSqlId("EdorCommonSql7");
			tSQLInfo.addSubPara(fm.HeadBankCode.value);

			var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(arrReslut==null){
				alert("��ѡ����Ч�Ŀ������� ��");
				fm.HeadBankCode.focus();
				return false;
			}
		}
		if(fm.BankProvince.value != ''){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorCommonSql");
			tSQLInfo.setSqlId("EdorCommonSql8");
			tSQLInfo.addSubPara(fm.BankProvince.value);

			var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(arrReslut==null){
				alert("��ѡ����Ч�Ŀ���������ʡ��");
				fm.BankProvince.focus();
				return false;
			}
		}
		if(fm.BankCity.value != ''){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorCommonSql");
			tSQLInfo.setSqlId("EdorCommonSql9");
			tSQLInfo.addSubPara(fm.BankProvince.value);
			tSQLInfo.addSubPara(fm.BankCity.value);
			var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(arrReslut==null){
				alert("��ѡ����Ч�Ŀ����������У�");
				fm.BankCity.focus();
				return false;
			}
		}
		/**
		if(fm.HeadBankCode.value != ''){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorBISql");
			tSQLInfo.setSqlId("EdorBISql8");
			tSQLInfo.addSubPara(fm.HeadBankCode.value);
			tSQLInfo.addSubPara(fm.Province.value);
			tSQLInfo.addSubPara(fm.City.value);

			var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(arrReslut==null){
				alert("������Ϣ�����Ϲ���Ҫ��");
				return false;
			}
		}**/
		// У��������������Ϣ
		BnfGrid.delBlankLine();
		if(!valBnfInfo()){
			return false;
		}
		if(fm.mainCustName.value !=null && fm.mainCustName.value !=''){
			var arrResult = new Array();
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNISql");
			tSQLInfo.setSqlId("EdorNISql4");
			tSQLInfo.addSubPara(fm.GrpPropNo.value);
			tSQLInfo.addSubPara(fm.mainCustName.value);
			tSQLInfo.addSubPara(fm.mainCustIDNo.value);

			arrResult = easyExecSql(tSQLInfo.getString());
			if(arrResult=='0'){
				alert(" û�в�ѯ����������,ͬʱ���������������ˡ��Ļ���ʹ�õ���ģ�壡");
				fm.mainInsuredNo.value="";
				fm.mainCustName.value="";
				fm.mainCustIDNo.value="";
				return false;
			}
		}
	}else if(fm.InsuredType.value=='1'){

		if(fm.edorValDate.value ==""){
			alert("��ȫ��Ч���ڲ���Ϊ�գ�");
			return false;
		}

		if(fm.JGContPlanCode.value==""){
			alert("�������벻��Ϊ�գ�");
			return false;
		}

		if(fm.InsuredPeoples.value ==""){
			alert("����������������Ϊ�գ�");
			return false;
		}
	}
	AmntGrid.delBlankLine();
	if(!AmntGrid.checkValue("AmntGrid")){
		return false;
	}
	if(AmntGrid.mulLineCount>0){
		for (var i=0; i<AmntGrid.mulLineCount;i++){
			if(AmntGrid.getRowColData(i,7) !=''){
				if(parseFloat(AmntGrid.getRowColData(i,7))>parseFloat(AmntGrid.getRowColData(i,5))){
					alert(AmntGrid.getRowColData(i,4)+"����¼��ı��������߱��");
					return false;
				}
				if(parseFloat(AmntGrid.getRowColData(i,7))<parseFloat(AmntGrid.getRowColData(i,6))){
					alert(AmntGrid.getRowColData(i,4)+"����¼��ı���С����ͱ��");
					return false;
				}
			}
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
	}
	return true;
}


/**
 *У����������Ϣ
 */
function valBnfInfo(){

	var tCount = BnfGrid.mulLineCount;
	var sumRate = 0;
	
	if(parseInt(tCount)>0){
		if(!BnfGrid.checkValue("BnfGrid")){
			return false;
		}
		
		for (var i=0; i<tCount;i++){
			
			for(var j=i+1;j<tCount;j++){
				if(BnfGrid.getRowColData(i,2)!=BnfGrid.getRowColData(j,2)){
					alert("���������ֻ��¼��һ�֣�");
					return false;
				}
			}
			if(BnfGrid.getRowColData(i,4)==''){
				if(BnfGrid.getRowColData(i,2)!='00'||BnfGrid.getRowColData(i,5)!=''||
					BnfGrid.getRowColData(i,7)!=''||
					BnfGrid.getRowColData(i,8)!=''||
					BnfGrid.getRowColData(i,10)!=''||
					BnfGrid.getRowColData(i,11)!=''||
					BnfGrid.getRowColData(i,13)!=''){
					alert("������˳����Ϊ��!");
					return false;
				}
			}else{
				if(BnfGrid.getRowColData(i,5)==''){
					alert("��������������Ϊ��!");
					return false;
				}
				if(BnfGrid.getRowColData(i,2)!='02'){
			
					if(BnfGrid.getRowColData(i,10)==''){
						alert("֤�����Ͳ���Ϊ��!");
						return false;
					}
					if(BnfGrid.getRowColData(i,11)==''){
						alert("֤�����벻��Ϊ��!");
						return false;
					}
					if(BnfGrid.getRowColData(i,10) !=''){
						if(BnfGrid.getRowColData(i,10)!='0' ){	
							if(BnfGrid.getRowColData(i,7)==null || BnfGrid.getRowColData(i,7) =='' && BnfGrid.getRowColData(i,8)==null || BnfGrid.getRowColData(i,8) =='' ){
								alert("�������������Ա�/�������ڣ�");
								return false;
							}
						}else if(BnfGrid.getRowColData(i,10)=='0'){
							if(BnfGrid.getRowColData(i,11).length!='15' && BnfGrid.getRowColData(i,11).length!='18'){
								alert("���������֤�������Ϊ15����18λ��");
								return false;
							}
						}
					}
					if(BnfGrid.getRowColData(i,13)==''){
						alert("�뱻�����˹�ϵ����Ϊ��!");
						return false;
					}
				}
			
				if(BnfGrid.getRowColData(i,14)==''){
					alert("�����������Ϊ��!");
					return false;
				}
			}
		}
	}
	return true;
}


/**
 * ��ȡҳ����Ϣ
 */
function getCustomerInfo(){

	var tSelNo= EdorDetailGrid.getSelNo();
	var tSerialno = EdorDetailGrid.getRowColData(tSelNo-1,16);
	var tInsuredID = EdorDetailGrid.getRowColData(tSelNo-1,17);
	fm.Serialno.value = tSerialno;
	fm.InsuredID.value = tInsuredID;

	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql13");
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(tInsuredID);
	tSQLInfo.addSubPara(fm.EdorType.value);

	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(arrResult !=null){
		queryUnFixedAmnt(tInsuredID);
		fm.AmntFlag.value = "ShowAmnt";
	}else{
		divQueryShow.style.display="none";
		fm.AmntFlag.value = "";
	}

	queryPayPlan();

	queryBnf(tInsuredID);
	document.all("InsuredType").disabled=true;
	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql1");
	tSQLInfo.addSubPara(tSerialno);
	tSQLInfo.addSubPara(tInsuredID);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);

	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(arrResult != null){
		if(arrResult[0][0]=='0'){
			shownewPage.style.display="none";
			divQueryPage.style.display="";
			divQuerybnf.style.display="";

			fm.InsuredType.value=arrResult[0][0];
			fm.InsuredTypeName.value="���˵�";
			fm.relatomain.value = arrResult[0][4];

			if(arrResult[0][4] != '00'){
				mainname.style.display="";
				mainname1.style.display="";
				mainIDNo.style.display="";
				mainCustNo1.style.display="";
			}else{
				mainname.style.display="none";
				mainname1.style.display="none";
				document.getElementById("mainIDNo").style.display="none";
				mainCustNo1.style.display="none";
			}
			showRelationtoMain(arrResult[0][8], arrResult[0][11]);
			fm.relatomainName.value = arrResult[0][5];
			fm.mainCustName.value = arrResult[0][6];
			fm.mainCustIDNo.value = arrResult[0][7];
			fm.InsuredName.value = arrResult[0][8];
			fm.IDType.value = arrResult[0][9];
			fm.IDTypeName.value = arrResult[0][10];
			fm.IDNo.value = arrResult[0][11];
			fm.InsuredGender.value = arrResult[0][12];
			fm.InsuredGenderName.value = arrResult[0][13];
			fm.InsuredBirthDay.value = arrResult[0][14];
			fm.InsuredAppAge.value = calAge(arrResult[0][14]);
			fm.edorValDate.value = arrResult[0][15];
			fm.ContPlanCode.value = arrResult[0][16];
			fm.ContPlanCodeName.value = arrResult[0][17];
			fm.OccupationCode.value = arrResult[0][18];
			fm.OccupationCodeName.value = arrResult[0][19];
			fm.HeadBankCode.value = arrResult[0][20];
			fm.BankCodeName.value = arrResult[0][21];
			fm.AccName.value = arrResult[0][22];
			fm.BankAccNo.value = arrResult[0][23];
			fm.BankProvince.value = arrResult[0][24];
			fm.BankProvinceName.value = arrResult[0][25];
			fm.BankCity.value = arrResult[0][26];
			fm.BankCityName.value = arrResult[0][27];
			fm.ServerArea.value = arrResult[0][28];
			fm.ServiceArName.value = arrResult[0][29];
			fm.Substandard.value = arrResult[0][30];
			fm.SubstandardName.value = arrResult[0][31];
			fm.Social.value = arrResult[0][32];
			fm.SocialName.value = arrResult[0][33];
			fm.Position.value = arrResult[0][34];
			fm.JoinCompDate.value = arrResult[0][35];
			fm.Seniority.value = arrResult[0][36];
			fm.Salary.value = arrResult[0][37];
			fm.WorkIDNo.value = arrResult[0][38];
			fm.ISLongValid.value = arrResult[0][39];
			fm.ISLongValidName.value = arrResult[0][40];
			fm.LiscenceValidDate.value = arrResult[0][41];
			fm.ComponyName.value = arrResult[0][42];
			fm.DeptCode.value = arrResult[0][43];
			fm.InsureCode.value = arrResult[0][44];
			//fm.SubCustomerNo.value = arrResult[0][45];
			fm.WorkAddress.value = arrResult[0][46];
			fm.SocialAddress.value = arrResult[0][47];
			fm.ZipCode.value = arrResult[0][48];
			fm.EMail.value = arrResult[0][49];
			fm.MicroNo.value = arrResult[0][50];
			fm.Phone.value = arrResult[0][51];
			fm.Mobile.value = arrResult[0][52];
			fm.ProvinceCode.value = arrResult[0][53];
			fm.ProvinceName.value = arrResult[0][54];
			fm.CityCode.value = arrResult[0][55];
			fm.CityName.value = arrResult[0][56];
			fm.CountyCode.value = arrResult[0][57];
			fm.CountyName.value = arrResult[0][58];
			fm.DetailAddress.value = arrResult[0][59];

			fm.GetYear.value = arrResult[0][61];
			fm.GetStartType.value = arrResult[0][62];
			fm.GetStartTypeName.value = arrResult[0][63];
			fm.PositionName.value = arrResult[0][64];
			//fm.SubCustomerName.value = arrResult[0][65];

		 // ��ѯְҵ���
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNISql");
			tSQLInfo.setSqlId("EdorNISql2");
		 	tSQLInfo.addSubPara(arrResult[0][18]);
		 	var oarrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		 	if(oarrResult !=null){
		 		fm.OccupationType.value = oarrResult[0][0];
				fm.OccupationTypeName.value = oarrResult[0][1];
		 	}

			//fm.PositionName.value = '';

		}else if(arrResult[0][0]=='1'){
			shownewPage.style.display="";
			divQueryPage.style.display="none";
			divQuerybnf.style.display="none";
			fm.InsuredType.value=arrResult[0][0];
			fm.InsuredTypeName.value=arrResult[0][1];
			fm.edorValDate.value = arrResult[0][15];
			fm.JGContPlanCode.value = arrResult[0][16];
			fm.JGContPlanCodeName.value = arrResult[0][17];
			fm.InsuredPeoples.value =  arrResult[0][60];
		}
	}

 }

 /**
 * ������һҳ��
 */
function returnBack(){
	top.close();
}


/**
 * ���ҳ����Ϣ
 */
function clearPage(){

	fm.InsuredType.value="0";
	fm.InsuredTypeName.value="���˵�";
	fm.ContPlanCode.value="";
	fm.ContPlanCodeName.value="";
	fm.JGContPlanCode.value ="";
	fm.JGContPlanCodeName.value ="";
	fm.InsuredPeoples.value="";

	fm.relatomain.value="00";
	fm.relatomainName.value="����";
	fm.mainCustName.value="";
	fm.mainCustIDNo.value="";
	fm.mainInsuredNo.value ="";
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

	fm.mainCustNameTemp.value="";
	fm.InsuredNameTemp.value ="";
	fm.edorValDate.value="";
	fm.Serialno.value = "";

	$('#mainname').attr('display','none');
	$('#mainname1').attr('display','none');
	$('#mainIDNo').attr('display','none');
	$('#mainCustNo1').attr('display','none');
	
	
	/*fm.mainname.style.display="none";
	fm.mainname1.style.display="none";
	fm.mainIDNo.style.display="none";
	fm.mainCustNo1.style.display="none";*/
}


/**
 * ��ѯ������������Ϣ
 */

 function showRelationtoMain(mainName,mainIDNO){

	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql8");
	tSQLInfo.addSubPara(mainName);
	tSQLInfo.addSubPara(mainIDNO);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);

	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if(arrResult !='0'){
		divQuerySpe.style.display="";
		var querySql = "";
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorNISql");
		tSQLInfo.setSqlId("EdorNISql9");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.EdorAppNo.value);
		tSQLInfo.addSubPara(mainName);
		tSQLInfo.addSubPara(mainIDNO);
		tSQLInfo.addSubPara(fm.EdorType.value);
		tSQLInfo.addSubPara(fm.BatchNo.value);

		turnPage2.queryModal(tSQLInfo.getString(), QueryInfoGrid, 1,1,10);
	}else{
		divQuerySpe.style.display="none";
	}
 }

  /**
 * ��ѯ��������Ϣ
 */
 function queryBnf(insuredid){

 	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql11");
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(insuredid);
	tSQLInfo.addSubPara(fm.BatchNo.value);
 	turnPage3.queryModal(tSQLInfo.getString(), BnfGrid, 1,1,10);
 }

 /**
 * ��ѯ�ǹ̶�������Ϣ
 */
 function queryUnFixedAmnt(insuredid){

 	divQueryShow.style.display="";
 	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql13");
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(insuredid);
	tSQLInfo.addSubPara(fm.EdorType.value);

	turnPage4.queryModal(tSQLInfo.getString(), AmntGrid, 1,1,30);

 }

/**
 * ��ѯ�����˽ɷ�����Ϣ
 **/
function queryPayPlan(){

	var tSelNo= EdorDetailGrid.getSelNo();
	var tInsuredID = EdorDetailGrid.getRowColData(tSelNo-1,17);

	initPayPlanGrid();

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql16");
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(tInsuredID);

	turnPage5.queryModal(tSQLInfo.getString(), PayPlanGrid, 1, 1);

	if (turnPage5.strQueryResult) {
		divPayPlanSaveButton.style.display = "";
		divPayPlan.style.display = "";
		divQueryShow.style.display="none";
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

	var tBatchNo = PayPlanGrid.getRowColData(tSelNo-1, 1);
	var tInsuredID = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanID = PayPlanGrid.getRowColData(tSelNo-1, 3);

	initInvestGrid();

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNISql");
	tSQLInfo.setSqlId("EdorNISql17");
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(tBatchNo);
	tSQLInfo.addSubPara(tInsuredID);
	tSQLInfo.addSubPara(tPayPlanID);

	turnPage6.queryModal(tSQLInfo.getString(), InvestGrid, 1, 1);

	//���Ͷ����Ϣ����1�У���¼��Ͷ����Ϣ������ֱ��չʾ
	divInvest.style.display = "";
	if (InvestGrid.mulLineCount>1) {
		divInvestSaveButton.style.display = "";
	} else {
		divInvestSaveButton.style.display = "none";
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
		tSQLInfo.setResourceName("g_pos.EdorBBSql");
		tSQLInfo.setSqlId("EdorBBSql4");
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

