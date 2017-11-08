/***************************************************************
 * <p>ProName：LJMoidfyBankConfirmInput.jsp</p>
 * <p>Title: 客户银行信息修改审核</p>
 * <p>Description：客户银行信息修改审核</p>
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

/**
 * 查询
 */
function queryInfo(o) {
	
	initModifyBankInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	if(_DBT == _DBO){
		tSQLInfo.setSqlId("LJModifyBankSql3");
	}else if(_DBT == _DBM){
		tSQLInfo.setSqlId("LJModifyBankSql10");
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryBankAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);

	turnPage1.queryModal(tSQLInfo.getString(), ModifyBankInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 提交
 */
function confirmClick() {

	document.all("ConfirmButton").disabled = true;
	if (!checkInfo()) {
		document.all("ConfirmButton").disabled = false;
		return false;
	}
	
	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	var tApplyBatNo = ModifyBankInfoGrid.getRowColData(tSelNo, 1);
	
	tOperate = "CONFIRMCLICK";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJModifyBankConfirmSave.jsp?Operate="+ tOperate +"&ApplyBatNo="+　tApplyBatNo;
	submitForm(fm);
}

function checkInfo() {
	
	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要进行操作的数据！");
		return false;
	}
	
	if (isEmpty(fm.ConfirmConclusion)) {
		alert("审核结论不能为空！");
		return false;
	}
	var tConfirmConclusion = fm.ConfirmConclusion.value;
	var tConfirmDesc = fm.ConfirmDesc.value;
	if (tConfirmConclusion=="00") {
		
	} else {
		
		if (tConfirmDesc==null || tConfirmDesc=="") {
			alert("审核描述不能为空！");
			return false;
		}
	}
	
	if (tConfirmDesc.length>100) {
		alert("审核描述长度应在100字长内！");
		return false;
	}
	
	return true;
}

function showInfo() {

	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	var tApplyBatNo = ModifyBankInfoGrid.getRowColData(tSelNo, 1);
	
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
	
	dealAfterSubmit(FlagStr);
}

function dealAfterSubmit(cSuccFlag) {
	
	document.all("ConfirmButton").disabled = false;
	if (cSuccFlag!="Fail") {
		
		initModifyBankInfoGrid();
		initHisBankInfoGrid();
		
		fm.ConfirmConclusion.value = "";
		fm.ConfirmConclusionName.value = "";
		fm.ConfirmDesc.value = "";
	}
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


function queryScan() {
	
	var tSelNo = ModifyBankInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要查看的记录！");
		return false;
	}
	
	var tBussType = ModifyBankInfoGrid.getRowColData(tSelNo, 20);
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
	var tTitle = "管理机构编码^|管理机构^|保单号^|投保人名称^|客户经理^|业务类型^|业务号^|金额^|被保险人客户号^|被保险人姓名^|被保险人证件号码^|领取人姓名^|领取人证件号^|客户新开户行^|客户新户行省^|客户新开户行市^|客户新开户行账号^|客户新开户行户名^|修改原因^|失败原因";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJModifyBankSql");
	if(_DBT == _DBO){
		tSQLInfo.setSqlId("LJModifyBankSql7");
	}else if(_DBT == _DBM){
		tSQLInfo.setSqlId("LJModifyBankSql11");
	}
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryHeadBankCode.value);
	tSQLInfo.addSubPara(fm.QueryBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryBankAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	
	var tQuerySQL = tSQLInfo.getString();
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	fm.submit();
}
