/***************************************************************
 * <p>ProName：PreCustomerManageInput.js</p>
 * <p>Title：准客户维护界面</p>
 * <p>Description：准客户维护界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询所属上级客户
 */
function queryUpCustomerNo(cObj, cObjName, cObj2) {
	
	var keyCode = event.keyCode;
	if(keyCode=="13"||keyCode=="9") {
		
		window.event.keyCode = 0;
		var tUpCustomerName = cObjName.value;
		var tPreCustomerNo = cObj2.value;
		
		if (tUpCustomerName==null || tUpCustomerName=="") {
			alert("上级准客户名称不能为空！");
			return false;
		}
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.PreCustomerSql");
		tSQLInfo.setSqlId("PreCustomerSql11");
		tSQLInfo.addSubPara(tUpCustomerName);
		if (tPreCustomerNo==null || tPreCustomerNo=="") {
			tSQLInfo.addSubPara("");
		} else {
			tSQLInfo.addSubPara(tPreCustomerNo);
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("不存在该客户！");
			cObj.value = "";
			cObjName.value = "";
			return false;
		} else {
			if (tArr.length == 1) {
				cObj.value = tArr[0][0];
				cObjName.value = tArr[0][1];
			} else {
				if (tPreCustomerNo==null || tPreCustomerNo=="") {
					showCodeList('upcustomerno',[cObj, cObjName],[0, 1],null,"1 and precustomername like #%%"+cObjName.value+"%%#","1",'1',null);
				} else {
					showCodeList('upcustomerno',[cObj, cObjName],[0, 1],null,"1 and precustomername like #%%"+cObjName.value+"%%# and precustomerno!=#"+cObj2.value+"#","1",'1',null);
				}
			}
		}
	}
}

/**
 * 新增
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * 修改
 */
function modifyClick() {
	
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	if(!confirm('确定要删除该准客户信息吗?')){
		return false;
	}
	mOperate = "DELETE";
	submitForm();
}

/**
 * 修改轨迹查询
 */
function TraceQuery() {
	
	window.open("./PreCustomerTraceQueryMain.jsp?PreCustomerNo="+tPreCustomerNo,"修改轨迹查询",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/*
*	返回
*/
function returnBack() {
	
	window.location="./PreCustomerQueryInput.jsp?Flag=1";
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

	fm.action = "./PreCustomerManageSave.jsp";
	fm.Operate.value = mOperate;
	fm.submit();
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content,cPreCustomerNo) {
	
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
		if (mOperate == "INSERT") {
			tPreCustomerNo = cPreCustomerNo;
			window.location = "./PreCustomerManageInput.jsp?Flag=2&PreCustomerNo="+tPreCustomerNo;
		} else if (mOperate == "UPDATE") {
			initForm();
		} else if (mOperate == "DELETE") {
			window.location = "./PreCustomerQueryInput.jsp?Flag=1";
		}
	}
	
}

/**
 * 查询准客户详细信息
 */
function queryDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql2");
	tSQLInfo.addSubPara(tPreCustomerNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (strQueryResult != null) {
		fm.PreCustomerNo.value = strQueryResult[0][0];
		fm.PreCustomerName.value = strQueryResult[0][1];
		fm.IDType.value = strQueryResult[0][2];
		fm.IDTypeName.value = strQueryResult[0][3];
		fm.IDNo.value = strQueryResult[0][4];
		fm.GrpNature.value = strQueryResult[0][5];
		fm.GrpNatureName.value = strQueryResult[0][6];
		fm.BusiCategory.value = strQueryResult[0][7];
		fm.BusiCategoryName.value = strQueryResult[0][8];
		fm.SumNumPeople.value = strQueryResult[0][9];
		fm.PreSumPeople.value = strQueryResult[0][10];
		fm.PreSumPrem.value = strQueryResult[0][11];
		fm.UpCustomerNo.value = strQueryResult[0][12];
		fm.UpCustomerName.value = strQueryResult[0][13];
		fm.SaleChannel.value = strQueryResult[0][14];
		fm.SaleChannelName.value = strQueryResult[0][15];
		
		fm.ProvinceCode.value = strQueryResult[0][16];
		fm.ProvinceName.value = strQueryResult[0][17];
		fm.CityCode.value = strQueryResult[0][18];
		fm.CityName.value = strQueryResult[0][19];
		fm.CountyCode.value = strQueryResult[0][20];
		fm.CountyName.value = strQueryResult[0][21];
		fm.DetailAddress.value = strQueryResult[0][22];
		fm.CustomerIntro.value = strQueryResult[0][23];
	}
	
	//查询主要联系人信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql3");
	tSQLInfo.addSubPara(tPreCustomerNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.LinkMan.value = strQueryResult[0][0];
		fm.Mobile.value = strQueryResult[0][1];
		fm.Phone.value = strQueryResult[0][2];
		fm.Depart.value = strQueryResult[0][3];
		fm.Post.value = strQueryResult[0][4];
		fm.Email.value = strQueryResult[0][5];
	}
	
	//查询客户经理信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql5");
	tSQLInfo.addSubPara(tPreCustomerNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), SalerGrid, 1, 0);
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	if (fm.CustomerIntro.value.length>500) {
		alert("公司简介应小于500字！");
		return false;
	}
	
	if (mOperate=="INSERT" || mOperate=="UPDATE") {
		
		if (fm.ProvinceCode.value=="" || fm.ProvinceName.value=="") {
			fm.ProvinceCode.value="";
			fm.ProvinceName.value="";
			fm.CityCode.value="";
			fm.CityName.value="";
			fm.CountyCode.value="";
			fm.CountyName.value="";
		}
		if (fm.CityCode.value=="" || fm.CityName.value=="") {
			fm.CityCode.value="";
			fm.CityName.value="";
			fm.CountyCode.value="";
			fm.CountyName.value="";
		}
		if (fm.CountyCode.value=="" || fm.CountyName.value=="") {
			fm.CountyCode.value="";
			fm.CountyName.value="";
		}
		
		if (fm.Mobile.value == "" && fm.Phone.value == "") {
			alert("联系人手机、办公电话至少录入一个！");
			return false;
		}
		
		for(var i=0;i < SalerGrid.mulLineCount;i++){
				
			var tSalerCode = SalerGrid.getRowColData(i,1);
			if(tSalerCode == null || tSalerCode.trim() == "") {
				
				alert("第["+(i+1)+"]行客户经理代码不能为空！");
				return false;
			}
			//人员重复性校验
			for(var j=0;j < i;j++){
				
				var mSalerCode = SalerGrid.getRowColData(j,1);
				if (mSalerCode==tSalerCode) {
					alert("第["+(i+1)+"]行客户经理代码与第["+(j+1)+"]行重复！");
					return false;
				}
			}
		}
	}
	return true;
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
		
		var tSql = "1 and upplacename in (select t.placecode from ldaddress t where t.placetype=#01# and t.placecode=#"+ fm.ProvinceCode.value +"#)";
		
		return showCodeList(value1,value2,value3,null,tSql,1,'1',180);
		//return showCodeList(value1,value2,value3,null,null,null,'1',180);
	} else if (value1=='county') {
		
		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		
		if (isEmpty(fm.CityName)) {
			alert("请选择市！");
			return false;
		}
		
		var tSql = "1 and upplacename in (select t1.placecode from ldaddress t,ldaddress t1 where t.placetype=#01# and t1.placetype=#02# and t1.upplacename=t.placecode and t.placecode=#"+ fm.ProvinceCode.value +"# and t1.placecode=#"+ fm.CityCode.value +"#)";
		//"1 and provincecode=#"+ fm.ProvinceCode.value +"# and citycode=#"+fm.CityCode.value+"#";
		return showCodeList('district',value2,value3,null,tSql,1,'1',180);
	}
}

function returnShowCodeLisKey (value1, value2, value3) {
	
	if (value1=='city') {
	
		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		
		var tSql = "1 and upplacename in (select t.placecode from ldaddress t where t.placetype=#01# and t.placecode=#"+ fm.ProvinceCode.value +"#)";
		
		return showCodeListKey(value1,value2,value3,null,tSql,1,'1',180);
		//return showCodeListKey(value1,value2,value3,null,null,null,'1',180);
	} else if (value1=='county') {
		
		if (isEmpty(fm.ProvinceName)) {
			alert("请选择省！");
			return false;
		}
		
		if (isEmpty(fm.CityName)) {
			alert("请选择市！");
			return false;
		}
		
		var tSql = "1 and upplacename in (select t1.placecode from ldaddress t,ldaddress t1 where t.placetype=#01# and t1.placetype=#02# and t1.upplacename=t.placecode and t.placecode=#"+ fm.ProvinceCode.value +"# and t1.placecode=#"+ fm.CityCode.value +"#)";
		return showCodeListKey('district',value2,value3,null,tSql,1,'1',180);
	}
}

/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tSelectValue, tObj) {

	if (tSelectValue=='province') {
	
		fm.CityCode.value = "";
		fm.CityName.value = "";
		fm.CountyCode.value = "";
		fm.CountyName.value = "";
	} else if (tSelectValue=='city') {
	
		fm.CountyCode.value = "";
		fm.CountyName.value = "";
	}
}

/**
 * 申请询价
 */
function applyQuotClick() {
	
	//申请环节校验应该在3级机构下进行
	if (tComGrade!="03") {
		alert("该操作应该在3级机构下进行！");
		return false;
	}
	var tPreCustomerNo = fm.PreCustomerNo.value;
	fm.action = "../g_quot/LSQuotETQuerySave.jsp?Operate=PREAPPLYET&QuotType=00&PreCustomerNo="+tPreCustomerNo;
	fm.submit();
}

function afterApplyQuotSubmit(FlagStr, content, cQuotNo, cQuotBatNo) {

	if (FlagStr=="Fail") {
		alert(content);
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql10");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		
		alert("请到一般询价录入菜单处进行查看！");
		return false;
	} else {
		
		var tMissionID = tArr[0][0];
		var tSubMissionID = tArr[0][1];
		var tActivityID = tArr[0][2];
		var tQuotNo = tArr[0][3];
		var tQuotBatNo = tArr[0][4];
		var tQuotType = tArr[0][5];
		
		var tPath = "?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
		location.href = "./LSQuotETBasicInput.jsp"+ tPath;
	}
}
//查询客户经理
function queryManager() {

	var tSelNo = SalerGrid.lastFocusRowNo;//行号从0开始
	var strUrl = "PreCustomerManagerQueryMain.jsp?SelNo="+tSelNo;
	window.open(strUrl,"客户经理查询",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}


/**
 * 模糊查询:省
 */
function fuzzyQueryProvince(Filed,FildName) {
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		var objCodeName = FildName.value;
		if (objCodeName=="") {
			return false;
		}
		var tProvinceName = fm.ProvinceName.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.PreCustomerSql");
		tSQLInfo.setSqlId("PreCustomerSql13");
		tSQLInfo.addSubPara(tProvinceName);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("不存在该省！");
			fm.ProvinceCode.value="";
			fm.ProvinceName.value="";
			return false;
		} else {
			if (tArr.length == 1) {
				Filed.value = tArr[0][0];
				FildName.value = tArr[0][1];
			} else {
				var conditionProvince = "1 and placename like #%"+tProvinceName+"%#";
				return showCodeList('province',[Filed, FildName], [0,1],null,conditionProvince,1,'1',180);
			}
		}
	}
}

/**
 * 模糊查询:市
 */
function fuzzyQueryCity(Filed,FildName,UpFiled) {
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		var objCodeName = FildName.value;
		if (objCodeName=="") {
			return false;
		}
		
		var tProvinceCode = UpFiled.value;
		if (tProvinceCode==null || tProvinceCode=="") {
			alert("请先选择省！");
			fm.CityCode.value="";
			fm.CityName.value="";
			fm.CountyCode.value="";
			fm.CountyName.value="";
			return false;
		}
		
		var tCityName = FildName.value;
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.PreCustomerSql");
		tSQLInfo.setSqlId("PreCustomerSql14");
		tSQLInfo.addSubPara(tProvinceCode);
		tSQLInfo.addSubPara(tCityName);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("不存在该市！");
			fm.CityCode.value="";
			fm.CityName.value="";
			fm.CountyCode.value="";
			fm.CountyName.value="";
			return false;
		} else {
			if (tArr.length == 1) {
				Filed.value = tArr[0][0];
				FildName.value = tArr[0][1];
			} else {
				
				fm.CountyCode.value="";
				fm.CountyName.value="";
				
				var conditionCity = "1 and placename like #%"+tCityName+"%# and upplacename in (select t.placecode from ldaddress t where t.placetype=#01# and t.placecode=#"+ tProvinceCode +"#)";
				return showCodeList('city',[Filed, FildName], [0,1],null,conditionCity,1,'1',180);
			}
		}
	}
}


/**
 * 模糊查询:区/县
 */
function fuzzyQueryCounty(Filed,FildName,TopFiled,UpFiled) {
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		var objCodeName = FildName.value;
		if (objCodeName=="") {
			return false;
		}
		var tProvinceCode = TopFiled.value;
		var tCityCode = UpFiled.value;
		
		if (tCityCode==null || tCityCode=="") {
			alert("请先选择市！");
			fm.CountyCode.value="";
			fm.CountyName.value="";
			return false;
		}
		
		var tCountyName = FildName.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.PreCustomerSql");
		tSQLInfo.setSqlId("PreCustomerSql15");
		tSQLInfo.addSubPara(tProvinceCode);
		tSQLInfo.addSubPara(tCityCode);
		tSQLInfo.addSubPara(tCountyName);
	
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("不存在该区/县！");
			fm.CountyCode.value="";
			fm.CountyName.value="";
			return false;
		} else {
			if (tArr.length == 1) {
				Filed.value = tArr[0][0];
				FildName.value = tArr[0][1];
			} else {
				
				var conditionCounty = "1 and placename like #%"+tCountyName+"%# and upplacename in (select t1.placecode from ldaddress t,ldaddress t1 where t.placetype=#01# and t1.placetype=#02# and t1.upplacename=t.placecode and t.placecode=#"+ tProvinceCode +"# and t1.placecode=#"+ tCityCode +"#)";
				 //"1 and provincecode = #"+tProvinceCode+"# and citycode = #"+tCityCode+"# and countyname like #%"+tCountyName+"%#";
				return showCodeList('district',[Filed, FildName], [0,1],null,conditionCounty,1,'1',180);
			}
		}
	}
}