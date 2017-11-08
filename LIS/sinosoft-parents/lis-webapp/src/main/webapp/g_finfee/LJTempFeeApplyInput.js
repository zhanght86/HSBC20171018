/***************************************************************
 * <p>ProName：LJTempFeeApplyInput.js</p>
 * <p>Title：进账申请</p>
 * <p>Description：进账申请</p>
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
function queryApplyTempFee(o) {
	
	initTempFeeInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql1");
	tSQLInfo.addSubPara(tOperator);//仅限制操作人为本人即可
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	
	if (o=="0") {
		fm.QueryTempFeeNo.value = "";
	}
	tSQLInfo.addSubPara(fm.QueryTempFeeNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), TempFeeInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 选择时处理
 */
function showTempFeeInfo() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	fm.PayType.value = TempFeeInfoGrid.getRowColData(tSelNo, 2);
	fm.PayTypeName.value = TempFeeInfoGrid.getRowColData(tSelNo, 3);
	fm.CustBankCode.value = TempFeeInfoGrid.getRowColData(tSelNo, 4);
	fm.CustBankName.value = TempFeeInfoGrid.getRowColData(tSelNo, 5);
	fm.CustBankAccNo.value = TempFeeInfoGrid.getRowColData(tSelNo, 6);
	fm.CustAccName.value = TempFeeInfoGrid.getRowColData(tSelNo, 7);
	fm.Money.value = TempFeeInfoGrid.getRowColData(tSelNo, 8);
	fm.GrpName.value = TempFeeInfoGrid.getRowColData(tSelNo, 10);
	fm.AgentName.value = TempFeeInfoGrid.getRowColData(tSelNo, 12);
	
	if (TempFeeInfoGrid.getRowColData(tSelNo, 2)=='01') {
			
			
			document.all("N1").style.display = 'none';
			document.all("N2").style.display = 'none';
		} else {
			
			document.all("N1").style.display = '';
			document.all("N2").style.display = '';
		}
}

/**
 * 增加
 */
function addTempFee() {
	
	if (tComGrade!="03") {
		alert("请在中支机构下进行该操作！");
		return false;
	}

	if (!checkPageElements()) {
		return false;
	}
	
	tOperate = "ADDTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * 修改
 */
function modifyTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("请先选择要进行操作的记录！");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);
	if (!checkPageElements()) {
		return false;
	}
	
	tOperate = "MODIFYTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * 删除
 */
function deleteTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("请先选择要进行操作的记录！");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);

	tOperate = "DELETETEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * 打印交接单
 */
function printTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("请先选择要进行操作的记录！");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);

	tOperate = "PRINTTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * 提交录入
 */
function toInpTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("请先选择要进行操作的记录！");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);

	tOperate = "TOINPTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * 页面元素校验
 */
function checkPageElements() {
	
	if (isEmpty(fm.PayType)) {
		
		alert("交费方式不能为空！");
		return false;
	}
	
	if (fm.PayType.value!='01') {  
		
		if (isEmpty(fm.CustBankCode)) {
			
			alert("客户开户行不能为空！");
			return false;
		}
		
		if (isEmpty(fm.CustBankAccNo)) {
		
			alert("客户银行账号不能为空！");
			return false;
		}
		
		var tCustBankAccNo = fm.CustBankAccNo.value;
		if (tCustBankAccNo.length>30) {
		
			alert("客户银行账号长度应不大于30位！");
			return false;
		}
	}
	
	if (isEmpty(fm.CustAccName)) {
		
		alert("客户账户名/汇款人姓名不能为空!");
		return false;
	}
	
	if (isEmpty(fm.Money)) {
		
		alert("金额不能为空!");
		return false;
	}
	var tMoney = fm.Money.value;
	if (!isNumeric(tMoney) || Number(tMoney)<=0) {
		alert("金额应为大于0的有效数字！");
		return false;
	}
	
	if (!checkDecimalFormat(tMoney, 12, 2)) {
		alert("金额整数位应不大于8位，小数位应不大于2位！");
		return false;
	}
	
	if (isEmpty(fm.GrpName)) {
		
		alert("投保单位名称不能为空!");
		return false;
	}
	
	if (isEmpty(fm.AgentName)) {
		
		alert("客户经理姓名不能为空!");
		return false;
	}
	
	return true;
}


/**
 * 提交数据
 */
function submitForm(obj) {
	
	fmPub.Operate.value = tOperate;
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, filepath,tfileName,cTempFeeNo,tOperate) {
	
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
	if(tOperate=='PRINTTEMPFEE'){
		var filepath1 =filepath+tfileName;
		window.location = "../common/jsp/download.jsp?FilePath="+filepath1+"&FileName="+tfileName;	
		
	}
	
	showInfo.focus();
	}
	
	dealAfterSubmit(cTempFeeNo, FlagStr);
}

/**
 * 成功后处理
 */
function dealAfterSubmit(o, p) {
	
	if (p!="Fail") {
	
		initQueryInfo();
		initEnterInfo();
		tOperate = fmPub.Operate.value;
		fmPub.Operate.value = "";
	
		if (tOperate=="ADDTEMPFEE" || tOperate=="MODIFYTEMPFEE" || tOperate=="PRINTTEMPFEE") {
			
			fm.QueryTempFeeNo.value = o;
			queryApplyTempFee('1');
			TempFeeInfoGrid.radioSel(1);
			showTempFeeInfo();
			
			if (tOperate=="PRINTTEMPFEE") {  
				
				if (o==null || o=="") {
					return;
				}
				
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
				tSQLInfo.setSqlId("LJTempFeeSql7");
				tSQLInfo.addSubPara(o);
				
				var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				var tFileName = tArr[0][0];
				var tFilePath = tArr[0][1];
	
				window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
			}
		} else {
			
			initTempFeeInfoGrid();
		}
	}
}

/**
 * 初始化查询信息
 */
function initQueryInfo() {

	fm.QueryPayType.value = "";
	fm.QueryPayTypeName.value = "";
	fm.QueryCustBankCode.value = "";
	fm.QueryCustBankName.value = "";
	fm.QueryCustBankAccNo.value = "";
	fm.QueryCustAccName.value = "";
	fm.QueryGrpName.value = "";
	fm.QueryAgentName.value = "";
	fm.QueryStartDate.value = "";
	fm.QueryEndDate.value = "";
	fm.QueryTempFeeNo.value = "";
}

/**
 * 初始化录入信息
 */
function initEnterInfo() {
	
	fm.PayType.value = "";
	fm.PayTypeName.value = "";
	fm.CustBankCode.value = "";
	fm.CustBankName.value = "";
	fm.CustBankAccNo.value = "";
	fm.CustAccName.value = "";
	fm.Money.value = "";
	fm.GrpName.value = "";
	fm.AgentName.value = "";
}

/**
 * 导出数据
 */
function expData() {
	
	fm.SheetName.value = "进账申请信息列表";
	
	var tTitle = "暂交费号^|交费方式^|客户开户行^|客户银行账号^|客户账户名^|金额^|投保单位名称^|客户经理姓名" +
			"^|申请日期^|退回日期^|退回原因";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql9");
	tSQLInfo.addSubPara(tOperator);//仅限制操作人为本人即可
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara("");
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}

function afterCodeSelect(cCodeType, cField) {
	
	
	if (cCodeType=='paymode' && cField.name=='PayType') {
		
		if (cField.value=='6') {
			
			
			document.all("N1").style.display = 'none';
			document.all("N2").style.display = 'none';
		} else {
			
			document.all("N1").style.display = '';
			document.all("N2").style.display = '';
		}
	}
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
