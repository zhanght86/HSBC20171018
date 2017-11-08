/***************************************************************
 * <p>ProName：LJTempFeeOutApplyInput.js</p>
 * <p>Title：暂收退费申请</p>
 * <p>Description：暂收退费申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

function queryInfo() {
	
	initOutPayInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	tSQLInfo.setSqlId("LJOutPayOutSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), OutPayInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

function queryApplyInfo() {
	
	initOutPayOutInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	tSQLInfo.setSqlId("LJOutPayOutSql2");
	tSQLInfo.addSubPara(tOperator);
	turnPage2.queryModal(tSQLInfo.getString(), OutPayOutInfoGrid, 0, 1);
}

/**
 * 退费申请
 */
function applyClick() {
	
	document.all("ApplyClickButton").disabled = true;
	var tSelNo = OutPayInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		document.all("ApplyClickButton").disabled = false;
		alert("请选择要申请的记录！");
		return false;
	}
	
	var tHeadBankCode = OutPayInfoGrid.getRowColData(tSelNo, 7);
	var tProvinceCode = OutPayInfoGrid.getRowColData(tSelNo, 9);
	var tCityCode = OutPayInfoGrid.getRowColData(tSelNo, 11);
	var tAccNo = OutPayInfoGrid.getRowColData(tSelNo, 14);
	var tAccName = OutPayInfoGrid.getRowColData(tSelNo, 15);
	
	if (tHeadBankCode==null || tHeadBankCode=="" || tProvinceCode==null || tProvinceCode=="" || tCityCode==null || tCityCode=="" || tAccNo==null || tAccNo=="" || tAccName==null || tAccName=="") {
		
		document.all("ApplyClickButton").disabled = false;
		alert("所选记录银行信息不全，不能进行该操作！");
		return false;
	}
	
	tOperate = "APPLYCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJOutPayOutApplySave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * 撤销申请
 */
function cancelSubmit() {
	
	document.all("CancelButton").disabled = true;
	var tSelNo = OutPayOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要进行撤销的记录！");
		return false;
	}
	
	var tApplyBatNo = OutPayOutInfoGrid.getRowColData(tSelNo, 1);
	
	tOperate = "CANCELSUBMIT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJOutPayOutApplySave.jsp?Operate="+ tOperate +"&ApplyBatNo="+ tApplyBatNo;
	submitForm(fm);
}

/**
 * 申请提交
 */
function applySubmit() {
	
	document.all("ApplyButton").disabled = true;
	var tSelNo = OutPayOutInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要进行提交的记录！");
		return false;
	}
	
	var tApplyBatNo = OutPayOutInfoGrid.getRowColData(tSelNo, 1);
	
	tOperate = "APPLYSUBMIT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJOutPayOutApplySave.jsp?Operate="+ tOperate +"&ApplyBatNo="+ tApplyBatNo;
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
	}
	
	dealAfterSubmit(FlagStr);
}

/**
 * 提交返回后处理
 */
function dealAfterSubmit(cFlag) {

	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="APPLYCLICK") {
		
		document.all("ApplyClickButton").disabled = false;
		
		if (cFlag!='Fail') {
			
			initOutPayInfoGrid();
			queryApplyInfo();
		}
	} else if (tOperate=="APPLYSUBMIT") {
		
		document.all("ApplyButton").disabled = false;
		if (cFlag!='Fail') {
			queryApplyInfo();
		}
	} else if (tOperate=="CANCELSUBMIT") {
		
		document.all("CancelButton").disabled = false;
		if (cFlag!='Fail') {
			queryApplyInfo();
		}
	}
}
/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "溢缴信息列表";
	
	var tTitle = "管理机构^|团体保单号^|投保人名称^|溢缴金额(元)^|客户开户银行^|客户开户行所在省^|客户开户行所在市^|客户银行账号^|客户账户名";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	tSQLInfo.setSqlId("LJOutPayOutSql6");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
