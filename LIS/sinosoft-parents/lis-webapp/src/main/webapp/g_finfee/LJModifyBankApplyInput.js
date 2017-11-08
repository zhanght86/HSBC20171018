/***************************************************************
 * <p>ProName：LJMoidfyBankApplyInput.js</p>
 * <p>Title: 客户银行信息修改申请</p>
 * <p>Description：客户银行信息修改申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-08-02
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;
var tScanOpen;

/**
 * 查询
 */
function queryInfo(o) {
	
	initModifyBankInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	if(_DBT == _DBO){
		tSQLInfo.setSqlId("LJModifyBankSql1");
	}else if(_DBT == _DBM){
		tSQLInfo.setSqlId("LJModifyBankSql8");
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryBankAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);

	turnPage1.queryModal(tSQLInfo.getString(), ModifyBankInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 申请提交
 */
function applyClick() {

	document.all("ApplyButton").disabled = true;
	if (!checkInfo()) {
		document.all("ApplyButton").disabled = false;
		return false;
	}
	
	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	var tApplyBatNo = ModifyBankInfoGrid.getRowColData(tSelNo, 1);
	
	tOperate = "APPLYCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJModifyBankApplySave.jsp?Operate="+ tOperate +"&ApplyBatNo="+　tApplyBatNo;
	submitForm(fm);
}

function checkInfo() {
	
	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要进行操作的数据！");
		return false;
	}
	
	if (isEmpty(fm.HeadBankCode)) {
		alert("客户开户银行不能为空！");
		return false;
	}
	/*modify by songsz 20150120 银行信息处理改为后台进行判断
	if (isEmpty(fm.Province)) {
		alert("客户开户银行所在省不能为空！");
		return false;
	}
	
	if (isEmpty(fm.City)) {
		alert("客户开户银行所在市不能为空！");
		return false;
	}*/
	
	if (isEmpty(fm.AccNo)) {
		alert("客户银行账号不能为空！");
		return false;
	}
	
	if (isEmpty(fm.AccName)) {
		alert("客户账户名不能为空！");
		return false;
	}
	
	var tAppDesc = fm.AppDesc.value;
	if (tAppDesc==null || tAppDesc=="" || tAppDesc.length>100) {
		alert("修改原因不能为空，且长度应在100字长内！");
		return false;
	}
	
	return true;
}

function showInfo() {

	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	var tApplyBatNo = ModifyBankInfoGrid.getRowColData(tSelNo, 1);
	var tTarHeadBankCode = ModifyBankInfoGrid.getRowColData(tSelNo, 18);
	var tTarHeadBankName = ModifyBankInfoGrid.getRowColData(tSelNo, 19);
	var tTarProvince = ModifyBankInfoGrid.getRowColData(tSelNo, 20);
	var tTarProvinceName = ModifyBankInfoGrid.getRowColData(tSelNo, 21);
	var tTarCity = ModifyBankInfoGrid.getRowColData(tSelNo, 22);
	var tTarCityName = ModifyBankInfoGrid.getRowColData(tSelNo, 23);
	var tTarAccNo = ModifyBankInfoGrid.getRowColData(tSelNo, 24);
	var tTarAccName = ModifyBankInfoGrid.getRowColData(tSelNo, 25);
	
	fm.HeadBankCode.value = tTarHeadBankCode;
	fm.HeadBankName.value = tTarHeadBankName;
	fm.Province.value = tTarProvince;
	fm.ProvinceName.value = tTarProvinceName;
	fm.City.value = tTarCity;
	fm.CityName.value = tTarCityName;
	fm.AccNo.value = tTarAccNo;
	fm.AccName.value = tTarAccName;
	
	
	initHisBankInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	tSQLInfo.setSqlId("LJModifyBankSql2");
	tSQLInfo.addSubPara(tApplyBatNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), HisBankInfoGrid, 0, 1);
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
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
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}
	
	dealAfterSubmit(FlagStr);
}

function dealAfterSubmit(cSuccFlag) {
	
	document.all("ApplyButton").disabled = false;
	if (cSuccFlag!="Fail") {
		
		initModifyBankInfoGrid();
		initHisBankInfoGrid();
		fm.HeadBankCode.value = "";
		fm.HeadBankName.value = "";
		fm.Province.value = "";
		fm.ProvinceName.value = "";
		fm.City.value = "";
		fm.CityName.value = "";
		fm.AccNo.value = "";
		fm.AccName.value = "";
		fm.AppDesc.value = "";
	}
}

function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=='city') {
		
		if (isEmpty(fm.Province)) {
			alert("请选择客户开户行所在省！");
			return null;
		}
		var tSql = "1 and upplacename in (select t.placecode from ldaddress t where t.placetype=#01# and t.placecode=#"+ fm.Province.value +"#)";
		if (returnType=='0') {
			return showCodeList('city',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('city',value2,value3,null,tSql,'1','1',null);
		}
	}
	
	if (value1=='finbusstype') {
		
		var tSql = "finbusstypeget";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	}
}

function afterCodeSelect(cCodeType, cField) {

	if (cCodeType=="province") {
		
		fm.City.value = "";
		fm.CityName.value = "";
	}
}

function queryScan() {
	
	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要查看的记录！");
		return false;
	}
	
	var tBussType = ModifyBankInfoGrid.getRowColData(tSelNo, 27);
	
	if (tBussType=="05") {
		tBussType = "G_CM";
	} else if (tBussType=="03") {
		tBussType = "G_POS";
	} else {
		alert("仅支持契约和理赔的影像件查看！");
		return false;
	}
	
	var tBussNo = ModifyBankInfoGrid.getRowColData(tSelNo, 7);
	tScanOpen = window.open("../easyscan/ScanPagesQueryMainInput.jsp?BussType="+ tBussType +"&BussNo="+tBussNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function exportExcel() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "管理机构编码^|管理机构^|保单号^|投保人名称^|客户经理^|业务类型^|业务号^|金额^|被保险人客户号^|被保险人姓名^|被保险人证件号码^|领取人姓名^|领取人证件号^|客户当前开户行^|客户当前开户行所在省^|客户当前开户行所在市^|客户当前银行账号^|客户当前账户名^|目标开户行名称^|目标开户行省^|目标开户行市^|目标开户行账号^|目标开户行户名^|退回原因^|失败原因";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	if(_DBT == _DBO){
		tSQLInfo.setSqlId("LJModifyBankSql6");
	}else if(_DBT == _DBM){
		tSQLInfo.setSqlId("LJModifyBankSql9");
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryBankAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	
	var tQuerySQL = tSQLInfo.getString();
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	fm.submit();
}

//模糊查询总行编码
function fuzzyQueryHeadBank(Filed,FildName) {

	var objCodeName = FildName.value;
	if (objCodeName=="") {
		return false;
	}
	if (window.event.keyCode == "13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
		
			alert("请输入总行!");
			return false;
		} else {
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_config.LDBankSql");
			tSQLInfo.setSqlId("LDBankSql2");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(),1,0,1);
			if (arr == null) {
				alert("总行不存在！");
				return false;
			} else {
				if (arr.length == 1) {
					Filed.value = arr[0][0];
					FildName.value = arr[0][1];
					afterCodeSelect('headbank', Filed);
				}else {
					var queryCondition = "1 and headbankname like #%"+objCodeName+"%#";
					showCodeList('headbank', [Filed, FildName], [0,1], null,queryCondition, '1', 1, '300');
				}
			}
		}
	}
}

//输入模糊查询
function fuzzyQueryProvince(Filed,FildName) {

	var objCodeName = FildName.value;
	if (objCodeName=="") {
		return false;
	}
	if (window.event.keyCode == "13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
		
			alert("请输入省!");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_config.LDBankSql");
			tSQLInfo.setSqlId("LDBankSql3");
			tSQLInfo.addSubPara(objCodeName); 
			var arr = easyExecSql(tSQLInfo.getString(),1,0,1);
			if (arr == null) {
				alert("不存在该省！");
				return false;
			} else {
				if (arr.length == 1) {
					Filed.value = arr[0][0];
					FildName.value = arr[0][1];
					afterCodeSelect('province', Filed);
				}else {
					var queryCondition = "1 and provincename like #%"+objCodeName+"%#";
					showCodeList('province', [Filed, FildName], [0,1], null,queryCondition, '1', 1, '300');
				}
			}
		}
	}
}

//输入模糊查询市
function fuzzyQueryCity(Filed,FildName,pearentFiled) {
	
	var objCodeName = FildName.value;
	if (objCodeName=="") {
		return false;
	}
	if (window.event.keyCode == "13") {
		window.event.keyCode = 0;
		
		if (pearentFiled.value==null || pearentFiled.value=="") {
			alert("请输入省!");
			return false;
		}
		
		if (objCodeName==null || trim(objCodeName)=="") {
		
			alert("请输入市!");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_config.LDBankSql");
			tSQLInfo.setSqlId("LDBankSql4");
			tSQLInfo.addSubPara(pearentFiled.value);
			tSQLInfo.addSubPara(objCodeName);
			var arr = easyExecSql(tSQLInfo.getString(),1,0,1);
			if (arr == null) {
				alert("不存在该市！");
				return false;
			} else {
				if (arr.length == 1) {
					Filed.value = arr[0][0];
					FildName.value = arr[0][1];
					afterCodeSelect('city', Filed);
				}else {
					var queryCondition = "1 and placename like #%"+objCodeName+"%# and upplacename in (select t.placecode from ldaddress t where t.placetype=#01# and t.placecode=#"+ fm.Province.value +"#)";
					//var queryCondition = "1 and provincecode=#"+pearentFiled.value+"# and cityname like #%"+objCodeName+"%#";
					showCodeList('city', [Filed, FildName], [0,1], null,queryCondition, '1', 1, '300');
				}
			}
		}
	}
}
