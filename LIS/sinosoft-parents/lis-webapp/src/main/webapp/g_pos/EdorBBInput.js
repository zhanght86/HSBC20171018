/***************************************************************
 * <p>ProName��EdorBBInput.js</p>
 * <p>Title���������˻������ϱ��</p>
 * <p>Description���������˻������ϱ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


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
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
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
		
		if(mOperate == "DELETE"){
			queryOldClick();
			queryUpdateClick();
			clearPage();		
		}else if(mOperate =="INSERT||UPDATE" ){
			queryOldClick();
			queryUpdateClick();
			clearPage();			
		}
	}	
}

/**
 * ���ñ��շ����ķ���
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

function showContPlanCodeName(cObj1,cObjName1,cObjCode1){
	return showCodeListKey('contplan',[cObj1,cObjName1,cObjCode1],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

/**
 * ����ǰ̨ҳ�������������
 **/
function returnShowCodeList (value1, value2, value3) {
	
	if (value1=='city') {
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

	if(tSelectValue=='relation'){
		if (fm.relatomain.value=='00') {
			
			if(fm.relatomain.value == fm.Hidrelatomain.value){
				alert("���������˲����޸�Ϊ����!");
				fm.relatomain.value ="";
				fm.relatomainName.value ="";
				return false;
			}
		}
	}
}

/**
 * ��ѯԭ��������Ϣ
 */
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBBSql");
	tSQLInfo.setSqlId("EdorBBSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCodeOld.value);
	tSQLInfo.addSubPara(tEdorAppNo);
		
	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	clearPage();
	fm.SerialNo.value="";
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1,10);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * չʾԭ��������Ϣ
 */
function showOldInsuredList(){
	
	var tSelNo = OldInsuredInfoGrid.getSelNo();	
	var tContNo = OldInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tInsuredNo =  OldInsuredInfoGrid.getRowColData(tSelNo-1,12);	
	var tOldInsuredName = OldInsuredInfoGrid.getRowColData(tSelNo-1,2);	
	var tOldIdno = OldInsuredInfoGrid.getRowColData(tSelNo-1,8);	
	
	fm.InsuredOldName.value = tOldInsuredName;
	fm.IdOldNo.value = tOldIdno;
	
	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBBSql");
	tSQLInfo.setSqlId("EdorBBSql3");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(tContNo);
	tSQLInfo.addSubPara(tInsuredNo);
	
	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(arrResult !=null){
		fm.relatomain.value = arrResult[0][1];
		fm.relatomainName.value =arrResult[0][2]; 
		Hidrelatomain =  arrResult[0][1];
		
		if(arrResult[0][1]!='00'){		
			document.all("relatomain").disabled=false;
		}else {
			document.all("relatomain").disabled=true;
		}
	}
}

/**
 * ����
 */
function addRecord(){
 	
 	if (!beforeSubmit()) {
		return false;
	}
	
	var tSelNoOld = OldInsuredInfoGrid.getSelNo();
	var tSelNoUpdate = UpdateInsuredInfoGrid.getSelNo();
	
	if(tSelNoOld=='0' && tSelNoUpdate=='0'){		
		alert("��ѡ��һ��ԭ��������Ϣ���޸ĺ�ı�������Ϣ���б��棡");
		return false;		
	}		
		
	mOperate = "INSERT||UPDATE";
	submitForm();
 	
 }
 
 /**
 * ����
 */
function deleteRecord(){
 	
 	var tSelNo = UpdateInsuredInfoGrid.getSelNo();  
 	if(tSelNo=='0'){
 		alert("��ѡ��һ���޸Ĺ��ı���������Ϣ���г�����");
 	  return false;
 	}
 	
 	
 	mOperate = "DELETE";
	submitForm();	
 }
 
 /**
 * ����ǰУ��
 */

function beforeSubmit(){
	document.all("relatomain").disabled=false;
	var tCurrentDate = fm.CurrentDate.value;
	if (!verifyInput2()) {
		document.all("relatomain").disabled=true;
			return false;
		}
		
	// У��֤����Ч��
	if(fm.LiscenceValidDate.value !=""){
		if(fm.LiscenceValidDate.value<tCurrentDate){
			alert("֤����Ч������ڵ�ǰ����");
			return false ;
		}
	}
	
	if(!checkCity()){
		return false;
	}
	
	// У����ϸ��Ϣ¼��	
	if(fm.ProvinceCode.value !=""){
		if(fm.CityCode.value=="" || fm.CountyCode.value ==""|| fm.DetailAddress.value=="" ){
				alert("��ϸ��Ϣ��ʡ��Ϣ��Ϊ�գ���Ҫ¼����/��/��ϸ��Ϣ��");
				return false ;
			}
		}
	
	return true;	
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

 /**
 * ��ѯ�޸ĺ󱻱�����Ϣ
 */
function queryUpdateClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBBSql");
	tSQLInfo.setSqlId("EdorBBSql5");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(NullToEmpty(fm.EdorNo.value));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);

	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	clearPage();
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1,10);
	
	if(o=='1'){
		if (!turnPage2.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}	
	}	
}

/**
 * ��ѯ�޸ĺ󱻱�����Ϣչʾ
 */
function showUpdateInsuredList(){
	
	var tSelNo = UpdateInsuredInfoGrid.getSelNo();	
	var tSerialNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tOldInsuredName = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,2);	
	var tOldIdno = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,3);	
	
	fm.InsuredOldName.value = tOldInsuredName;
	fm.IdOldNo.value = tOldIdno;
	fm.SerialNo.value = tSerialNo;

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBBSql");
	tSQLInfo.setSqlId("EdorBBSql6");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(tSerialNo);
	
	var upArrResul= easyExecSql(tSQLInfo.getString());
	if(upArrResul != null){		
		fm.relatomain.value = upArrResul[0][0];
		fm.relatomainName.value = upArrResul[0][1];
		fm.ZipCode.value = upArrResul[0][2];
		fm.EMail.value = upArrResul[0][3];
		fm.MicroNo.value = upArrResul[0][4];
		fm.Mobile.value = upArrResul[0][5]; 
		fm.Phone.value = upArrResul[0][6];
		fm.ProvinceCode.value = upArrResul[0][7]; 
		fm.ProvinceName.value = upArrResul[0][8];
		fm.CityCode.value = upArrResul[0][9];
		fm.CityName.value = upArrResul[0][10];
		fm.CountyCode.value = upArrResul[0][11];
		fm.CountyName.value = upArrResul[0][12];
		fm.DetailAddress.value = upArrResul[0][13];
		fm.JoinCompDate.value = upArrResul[0][14];
		fm.Seniority.value = upArrResul[0][15];
		fm.WorkIDNo.value = upArrResul[0][16];
		fm.ISLongValid.value = upArrResul[0][17];
		fm.ISLongValidName.value = upArrResul[0][18];
		fm.LiscenceValidDate.value = upArrResul[0][19];
		fm.ComponyName.value = upArrResul[0][20]; 
		fm.DeptCode.value = upArrResul[0][21];
		fm.InsureCode.value = upArrResul[0][22];
		//fm.SubCustomerNo.value = upArrResul[0][23];
		//fm.SubCustomerName.value = upArrResul[0][24];
		fm.WorkAddress.value = upArrResul[0][25]; 
		fm.SocialAddress.value = upArrResul[0][26]; 	
		fm.edorValDate.value = upArrResul[0][27];	
	}
		
	if(upArrResul[0][0] !='00'){
			document.all("relatomain").disabled=false;	
		}else {
			document.all("relatomain").disabled=true;				
		}
}

/**
 * ����ҳ��
 */

function clearPage(){
	
	fm.relatomain.value = "";
	fm.relatomainName.value = "";
	fm.ZipCode.value = "";
	fm.EMail.value = "";
	fm.MicroNo.value = "";
	fm.Phone.value = "";
	fm.Mobile.value = "";
	fm.ProvinceCode.value = "";
	fm.ProvinceName.value = "";
	fm.CityCode.value = "";
	fm.CityName.value = "";
	fm.CountyCode.value =  "";
	fm.CountyName.value = "";
	fm.DetailAddress.value = "";
	fm.JoinCompDate.value = "";
	fm.Seniority.value = "";
	fm.WorkIDNo.value = "";
	fm.ISLongValid.value = "";
	fm.ISLongValidName.value = "";
	fm.LiscenceValidDate.value = "";
	fm.ComponyName.value = "";
	fm.DeptCode.value =  "";
	fm.InsureCode.value =  "";
	//fm.SubCustomerNo.value = "";
	//fm.SubCustomerName.value = "";
	fm.WorkAddress.value = "";
	fm.SocialAddress.value = "";
	fm.edorValDate.value = "";
}
