/***************************************************************
 * <p>ProName：LJGetManualAppSubInput.js</p>
 * <p>Title: 手动付费申请明细</p>
 * <p>Description：手动付费申请明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryInfo(o) {
	
	initGetInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql3");
	tSQLInfo.addSubPara(tAppCom);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInsuranceComName.value);
	tSQLInfo.addSubPara(fm.QueryAppntName.value);
	
	
	turnPage1.queryModal(tSQLInfo.getString(), GetInfoGrid, 0, 1);
	if (o=="0") {
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

function queryInitInfo() {
	
	if (tApplyBatchNo==null || tApplyBatchNo=="") {
		alert("申请批次号为空！");
		backClick();
		return;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql2");
	tSQLInfo.addSubPara(tApplyBatchNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		
		fm.ApplyMoney.value = "0";
		fm.ApplyCount.value = "0";
	} else {
		
		fm.ApplyMoney.value = tArr[0][0];
		fm.ApplyCount.value = tArr[0][1];
	}
}


function queryApplyInfo() {

	if (tApplyBatchNo==null || tApplyBatchNo=="") {
		
		alert("申请批次号为空！");
		backClick();
		return;
	}
	
	initApplyGetInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql4");
	tSQLInfo.addSubPara(tApplyBatchNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), ApplyGetInfoGrid, 0, 1);
}

/**
 * 选择
 */
function chooseClick() {
	
	document.all("ChooseButton").disabled = true;
	if (GetInfoGrid.getChkCount()==0) {
		
		document.all("ChooseButton").disabled = false;
		alert("请选择待付费信息数据！");
		return false;
	}
	
	if (isEmpty(fm.GetDealType)) {
		
		document.all("ChooseButton").disabled = false;
		alert("请选择处理方式！");
		return false;
	}
	
	tOperate = "CHOOSECLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate +"&AppCom="+ tAppCom;
	submitForm(fm);
}

/**
 * 撤销
 */
function cancelClick() {
	
	document.all("CancelButton").disabled = true;
	if (ApplyGetInfoGrid.getChkCount()==0) {
		
		document.all("CancelButton").disabled = false;
		alert("请选择要撤销的数据！");
		return false;
	}
	
	tOperate = "CANCELCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * 修改银行
 */
function modifyBankClick() {
	
	document.all("ModifyButton").disabled = true;
	if (ApplyGetInfoGrid.getChkCount()==0) {
		
		document.all("ModifyButton").disabled = false;
		alert("请选择要修改银行信息的数据！");
		return false;
	}

	tOperate = "MODIFYBANK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

function confirmClick() {
	
	document.all("ConfirmButton").disabled = true;
	if (ApplyGetInfoGrid.mullineCount==0) {
		
		document.all("ConfirmButton").disabled = false;
		alert("没有可提交的数据！");
		return false;
	}
	
	/*
	for (var i=0; i<ApplyGetInfoGrid.mulLineCount; i++) {
			
		if (ApplyGetInfoGrid.getChkNo(i)) {
			
			var tBankCode = ApplyGetInfoGrid.getRowColData(i, 15);
			if (tBankCode==null || tBankCode=="") {
			
				document.all("ConfirmButton").disabled = false;
				alert("所选第"+ (i+1) +"行银行信息不全，请先进行修改！");
				return false;
			}
		}
	}*/
	
	tOperate = "CONFIRM";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualAppSubSave.jsp?Operate="+ tOperate;
	submitForm(fm);
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
function afterSubmit(FlagStr, content, cBatchNo) {
	
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
	}
	dealAfterSubmit(FlagStr, cBatchNo);
}

function dealAfterSubmit(FlagStr, cBatchNo) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	
	if (tOperate=="CHOOSECLICK") {
		
		document.all("ChooseButton").disabled = false;
		queryInfo("1");
		queryApplyInfo();
	} else if (tOperate=="CANCELCLICK") {
		
		document.all("CancelButton").disabled = false;
		queryApplyInfo();
	} else if (tOperate=="MODIFYBANK") {
		
		document.all("ModifyButton").disabled = false;
		queryApplyInfo();
	} else if (tOperate=="CONFIRM") {
		backClick();
	} else if (tOperate=="PRINT") {
		
		if ((FlagStr=="Succ")) {
			window.location = "../common/jsp/download.jsp?FilePath="+ cBatchNo +"&FileName=yjtf.pdf";
		}
	}
}

function backClick() {
	
	location.href = "./LJGetManualApplyInput.jsp";
}

/**
 * 模拟下拉操作
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	if (value1=='finbusstype') {
		
		var tSql = "finbusstypeget";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	}
}

/**
 * 退费通知书打印
 */
function printClick() {
	
	if (ApplyGetInfoGrid.getChkCount()==0) {
		
		alert("请选择要打印的溢缴退费记录！");
		return false;
	}
	
	if (ApplyGetInfoGrid.getChkCount()>1) {
		alert("该打印仅支持单条打印！");
		return false;
	}
	
	var tTransNo = "";
	for (var i=0; i<ApplyGetInfoGrid.mulLineCount; i++) {
			
		if (ApplyGetInfoGrid.getChkNo(i)) {
			
			var tBussType = ApplyGetInfoGrid.getRowColData(i, 8);
			tTransNo = ApplyGetInfoGrid.getRowColData(i, 1);
			if (tBussType!="06") {
			
				alert("所选记录不是溢缴退费数据！");
				return false;
			}
		}
	}
	
	tOperate = "PRINT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualPrintSave.jsp?Operate="+ tOperate +"&TransNo="+ tTransNo;
	submitForm(fm);
}
