/***************************************************************
 * <p>ProName：EdorBBInput.js</p>
 * <p>Title：被保险人基本资料变更</p>
 * <p>Description：被保险人基本资料变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();


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
 * 调用保险方案的方法
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

function showContPlanCodeName(cObj1,cObjName1,cObjCode1){
	return showCodeListKey('contplan',[cObj1,cObjName1,cObjCode1],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

/**
 * 根据前台页面进行下拉控制
 **/
function returnShowCodeList (value1, value2, value3) {
	
	if (value1=='city') {
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
	
	if (value1=='city') {
	
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
		if (fm.relatomain.value=='00') {
			
			if(fm.relatomain.value == fm.Hidrelatomain.value){
				alert("附属被保人不能修改为本人!");
				fm.relatomain.value ="";
				fm.relatomainName.value ="";
				return false;
			}
		}
	}
}

/**
 * 查询原被保人信息
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
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 展示原被保人信息
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
 * 保存
 */
function addRecord(){
 	
 	if (!beforeSubmit()) {
		return false;
	}
	
	var tSelNoOld = OldInsuredInfoGrid.getSelNo();
	var tSelNoUpdate = UpdateInsuredInfoGrid.getSelNo();
	
	if(tSelNoOld=='0' && tSelNoUpdate=='0'){		
		alert("请选中一条原被保人信息或修改后的被保人信息进行保存！");
		return false;		
	}		
		
	mOperate = "INSERT||UPDATE";
	submitForm();
 	
 }
 
 /**
 * 撤销
 */
function deleteRecord(){
 	
 	var tSelNo = UpdateInsuredInfoGrid.getSelNo();  
 	if(tSelNo=='0'){
 		alert("请选择一条修改过的被保险人信息进行撤销！");
 	  return false;
 	}
 	
 	
 	mOperate = "DELETE";
	submitForm();	
 }
 
 /**
 * 操作前校验
 */

function beforeSubmit(){
	document.all("relatomain").disabled=false;
	var tCurrentDate = fm.CurrentDate.value;
	if (!verifyInput2()) {
		document.all("relatomain").disabled=true;
			return false;
		}
		
	// 校验证件有效期
	if(fm.LiscenceValidDate.value !=""){
		if(fm.LiscenceValidDate.value<tCurrentDate){
			alert("证件有效期需大于当前日期");
			return false ;
		}
	}
	
	if(!checkCity()){
		return false;
	}
	
	// 校验详细信息录入	
	if(fm.ProvinceCode.value !=""){
		if(fm.CityCode.value=="" || fm.CountyCode.value ==""|| fm.DetailAddress.value=="" ){
				alert("详细信息中省信息不为空，需要录入市/县/详细信息！");
				return false ;
			}
		}
	
	return true;	
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
		tSQLInfo.setResourceName("g_pos.EdorBBSql");
		tSQLInfo.setSqlId("EdorBBSql4");
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
 * 查询修改后被保人信息
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
			alert("未查询到符合条件的查询结果！");
		}	
	}	
}

/**
 * 查询修改后被保人信息展示
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
 * 清理页面
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
