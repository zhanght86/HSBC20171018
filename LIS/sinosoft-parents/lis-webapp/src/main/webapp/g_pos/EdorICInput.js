/***************************************************************
 * <p>ProName：EdorBBInput.js</p>
 * <p>Title：被保险人重要资料变更</p>
 * <p>Description：被保险人重要资料变更</p>
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
 * 查询原被保人信息
 */
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorICSql");
	tSQLInfo.setSqlId("EdorICSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNameQ.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNoQ.value);
	tSQLInfo.addSubPara(fm.OldInsuredNoQ.value);
	tSQLInfo.addSubPara(fm.ContPlanCodeOldQ.value);
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
	clearPage();
	var tSelNo = OldInsuredInfoGrid.getSelNo();	
	var tOldInsuredName = OldInsuredInfoGrid.getRowColData(tSelNo-1,2);	
	var tOldIdno = OldInsuredInfoGrid.getRowColData(tSelNo-1,8);		
	fm.InsuredOldName.value =tOldInsuredName;
	fm.IdOldNo.value = tOldIdno;	
}

/**
 * 保存
 */
 function addRecord(){
 	
 	if (!verifyInput2()) {
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
 * 查询修改后被保人信息
 */
function queryUpdateClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorICSql");
	tSQLInfo.setSqlId("EdorICSql4");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(NullToEmpty(fm.EdorNo.value));
	tSQLInfo.addSubPara(fm.InsuredNameQ.value);
	tSQLInfo.addSubPara(fm.InsuredIDNoQ.value);
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
	tSQLInfo.setResourceName("g_pos.EdorICSql");
	tSQLInfo.setSqlId("EdorICSql6");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(tSerialNo);

	var upArrResul= easyExecSql(tSQLInfo.getString());
	if(upArrResul != null){
		
		fm.edorValDate.value = upArrResul[0][0];
		fm.InsuredName.value =upArrResul[0][1];
		fm.IDType.value =upArrResul[0][2];
		fm.IDTypeName.value =upArrResul[0][3];	
		fm.IDNo.value = upArrResul[0][4];
		fm.InsuredGender.value =upArrResul[0][5];
		fm.InsuredGenderName.value =upArrResul[0][6];
		fm.InsuredBirthDay.value =upArrResul[0][7];		
		fm.InsuredAppAge.value=upArrResul[0][8];
		fm.OccupationCode.value =upArrResul[0][9];
		fm.OccupationCodeName.value = upArrResul[0][10];
		fm.ServerArea.value = upArrResul[0][11];
		fm.ServiceArName.value = upArrResul[0][12];
		fm.Substandard.value =upArrResul[0][13];
		fm.SubstandardName.value =upArrResul[0][14];
		fm.Social.value =upArrResul[0][15];
		fm.SocialName.value =upArrResul[0][16];
		fm.Salary.value =upArrResul[0][18];	
	}
	
		if(upArrResul[0][9] != ''){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorICSql");
			tSQLInfo.setSqlId("EdorICSql3");
			tSQLInfo.addSubPara(upArrResul[0][9]);
		
			var occArrResult = easyExecSql(tSQLInfo.getString());
			if(occArrResult !=null){			
				fm.OccupationCodeName.value =occArrResult[0][0];		
				fm.OccupationTypeName.value =occArrResult[0][1];
			}
		}
}

/**
 * 清理页面
 */

function clearPage(){
	
	fm.edorValDate.value ="";
	fm.InsuredName.value ="";
	fm.IDType.value ="";
	fm.IDTypeName.value ="";
	fm.IDNo.value ="";
	fm.InsuredGender.value ="";
	fm.InsuredGenderName.value ="";
	fm.InsuredBirthDay.value ="";
	fm.InsuredAppAge.value ="";
	fm.OccupationCode.value ="";
	fm.OccupationCodeName.value ="";
	fm.OccupationType.value ="";
	fm.OccupationTypeName.value ="";
	fm.ServerArea.value ="";
	fm.ServiceArName.value ="";
	fm.Substandard.value ="";
	fm.SubstandardName.value ="";
	fm.Social.value ="";
	fm.SocialName.value ="";
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
		tSQLInfo.setResourceName("g_pos.EdorNISql");
		tSQLInfo.setSqlId("EdorNISql3");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.InsuredName.value);
		
		arrResult = easyExecSql(tSQLInfo.getString());
		if(arrResult=='0'){
			emptyCustInfo();
			return ;
		}
		
		var n=arrResult[0];
		if (n>1) {
			showLCInsuredInfo();//客户查询
			emptyCustInfo();//清空被保人客户页面信息	
		} else if(n==1) {
			var arrResult = new Array();
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorNISql");
			tSQLInfo.setSqlId("EdorNISql5");
			tSQLInfo.addSubPara(fm.GrpPropNo.value);
			tSQLInfo.addSubPara(fm.InsuredName.value);
			tSQLInfo.addSubPara("");
			
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
			fm.InsuredNoS.value = arrResult[0][8];
			fm.ContNoS.value = arrResult[0][9];
		} else {
			return;
		}
	}	
}

/**
 * 客户姓名查出多条时，跳转到客户查询页面
 */
function showLCInsuredInfo() {
	
	var tGrpPropNo=fm.GrpPropNo.value;
	var tInsuredName=fm.InsuredName.value;
	window.open("./EdorNIQueryMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredName="+tInsuredName+"&ManageCom="+tManageCom,"查询被保人信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
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
	fm.InsuredNoS.value ="";	
	fm.ContNoS.value ="";
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
 * 清空关联查询的项目

 */
function clearInput(codeInput,nameInput) {
	codeInput.value = "";
	nameInput.value = "";
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
			fm.InsuredBirthDay.value =getBirthdatByIdNo(fm.IDNo.value);
			fm.InsuredGender.value = getSex(fm.IDNo.value);	
			
			if(fm.InsuredGender.value=='0'){
				fm.InsuredGenderName.value ='男';
			}else if(fm.InsuredGender.value=='1'){
				fm.InsuredGenderName.value ='女';
			}
			fm.InsuredAppAge.value = calAge(fm.InsuredBirthDay.value);
		}
	}
}
