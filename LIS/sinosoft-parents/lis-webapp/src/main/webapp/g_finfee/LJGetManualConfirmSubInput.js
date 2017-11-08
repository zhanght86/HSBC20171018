/***************************************************************
 * <p>ProName：LJGetManualConfirmSubInput.js</p>
 * <p>Title: 手动付费确认明细</p>
 * <p>Description：手动付费确认明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tOperate;

/**
 * 查询
 */
function queryInfo() {
	
	initApplyGetInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJGetManualSql");
	tSQLInfo.setSqlId("LJGetManualSql7");
	tSQLInfo.addSubPara(tApplyBatchNo);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInsuranceComName.value);
	tSQLInfo.addSubPara(fm.QueryAppntName.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), ApplyGetInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
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
	tSQLInfo.setSqlId("LJGetManualSql6");
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

function confirmClick() {
	
	document.all("ConfirmButton").disabled = true;
	
	if (!checkInfo()) {
		
		document.all("ConfirmButton").disabled = false;
		return false;
	}
	
	var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
	var tTransNo = ApplyGetInfoGrid.getRowColData(tSelNo, 1);
	var tGetDealType = ApplyGetInfoGrid.getRowColData(tSelNo, 2);
	
	tOperate = "CONFIRM";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualConfirmSubSave.jsp?Operate="+ tOperate +"&TransNo="+ tTransNo +"&GetDealType="+ tGetDealType;
	submitForm(fm);
}

function checkInfo() {

	var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("请选择要进行操作的数据！");
		return false;
	}
	
	var tConfirmConclusion = fm.ConfirmConclusion.value;
	if (tConfirmConclusion==null || tConfirmConclusion=="") {
		alert("付费结论不能为空！");
		return false;
	}
	
	if (tConfirmConclusion=="00") {
		
		var tGetDealType = ApplyGetInfoGrid.getRowColData(tSelNo, 2);
		if (tGetDealType=="00") { 
			
			if (isEmpty(fm.GetType)) {
				alert("付款方式不能为空！");
				return false;
			}
			
			if (isEmpty(fm.OutBankCode)) {
				alert("付款银行不能为空！");
				return false;
			}
			
			if (isEmpty(fm.EnterAccDate)) {
				alert("付款日期不能为空！");
				return false;
			}
			
			if (compareDate(tCurrentDate,fm.EnterAccDate.value)==2) {
				alert("付款日期不能大于当前日期！");
				return false;
			}
		}
	} else {
	
		var tConfirmDesc = fm.ConfirmDesc.value;
		if (tConfirmDesc==null ||tConfirmDesc=='' || tConfirmDesc.length>100) {
			alert("结论描述不能为空且长度应在100字长内！");
			return false;
		}
	}
	
	
	return true;
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
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px")
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

function dealAfterSubmit(cSuccFlag, cBatchNo) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	
	if (tOperate=="CONFIRM") {
		
		document.all("ConfirmButton").disabled = false;
		
		if (cSuccFlag!='Fail') {
			
			queryInitInfo();
			initApplyGetInfoGrid();
			
			document.all("tr1").style.display = "none";
			document.all("tr2").style.display = "none";
			document.all("tr3").style.display = "none";
			
			fm.ConfirmConclusion.value = "";
			fm.ConfirmConclusionName.value = "";
			fm.ConfirmDesc.value = "";
			fm.GetType.value = "";
			fm.GetTypeName.value = "";
			fm.OutBankCode.value = "";
			fm.OutBankName.value = "";
			fm.OutBankAccNo.value = "";
			fm.EnterAccDate.value = "";
		}
	} else if (tOperate=="PRINT") {
		
		if ((cSuccFlag=="Succ")) {
			window.location = "../common/jsp/download.jsp?FilePath="+ cBatchNo +"&FileName=yjtf.pdf";
		}
	}
}

function backClick() {
	
	location.href = "./LJGetManualConfirmInput.jsp";
}


function afterCodeSelect(cCodeType, cField) {
	
	if (cCodeType=="succflag") {
		
		if (cField.value=="00") {//成功
			
			document.all("tr1").style.display = "none";
			
			var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
			if (tSelNo<0) {
				return;
			}
			var tGetDealType = ApplyGetInfoGrid.getRowColData(tSelNo, 2);
			if (tGetDealType=="01") {
				
				document.all("tr2").style.display = "none";
				document.all("tr3").style.display = "none";
				
				fm.GetType.value = "";
				fm.GetTypeName.value = "";
				fm.OutBankCode.value = "";
				fm.OutBankName.value = "";
				fm.OutBankAccNo.value = "";
				fm.EnterAccDate.value = "";
				fm.ConfirmDesc.value = "";
			} else {
				
				document.all("tr2").style.display = "";
				document.all("tr3").style.display = "";
				fm.GetType.value = "";
			}
		} else {//失败
			
			document.all("tr1").style.display = "";
			document.all("tr2").style.display = "none";
			document.all("tr3").style.display = "none";

			fm.GetType.value = "";
			fm.GetTypeName.value = "";
			fm.OutBankCode.value = "";
			fm.OutBankName.value = "";
			fm.OutBankAccNo.value = "";
			fm.EnterAccDate.value = "";
		}
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
		
		var tSql = "1 and codetype=#finbusstype# and codeexp=#get#";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='outfinbank') {
		
		var tSql = "1 and a.fincomcode in (select b.findb from ldcom b where b.comcode like #"+ tManageCom +"%#)";
		if (returnType=='0') {
			return showCodeList('outfinbank',value2,value3,null,tSql,'and 1','1',null);
		} else {
			return showCodeListKey('outfinbank',value2,value3,null,tSql,'and 1','1',null);
		}
	}
}

/**
 * 选择后将界面动态展示域初始化
 */
function chooseClick() {
	
	document.all("tr1").style.display = "none";
	document.all("tr2").style.display = "none";
	document.all("tr3").style.display = "none";
	
	fm.ConfirmConclusion.value = "";
	fm.ConfirmConclusionName.value = "";
	fm.ConfirmDesc.value = "";
	fm.GetType.value = "";
	fm.GetTypeName.value = "";
	fm.OutBankCode.value = "";
	fm.OutBankName.value = "";
	fm.OutBankAccNo.value = "";
	fm.EnterAccDate.value = "";
}

/**
 * 退费通知书打印
 */
function printClick() {

	var tSelNo = ApplyGetInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择要打印的溢缴退费数据！");
		return false;
	}
	
	var tTransNo = ApplyGetInfoGrid.getRowColData(tSelNo, 1);
	var tBussType = ApplyGetInfoGrid.getRowColData(tSelNo, 8);
	if (tBussType!="06") {
		alert("所选记录不是溢缴退费数据！");
		return false;
	}
	
	tOperate = "PRINT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJGetManualPrintSave.jsp?Operate="+ tOperate +"&TransNo="+ tTransNo;
	submitForm(fm);
}
