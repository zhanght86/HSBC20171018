/***************************************************************
 * <p>ProName：LSQuotPrintInput.js</p>
 * <p>Title：报价单打印</p>
 * <p>Description：报价单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 提交数据
 */
function submitForm(obj) {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
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
function afterSubmit(FlagStr, content,cOfferListNo) {
	
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
		
		if (fm.Operate.value == "CREATE") {
			
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
		
		if (fm.Operate.value == "PRINT") {
			
			var tFileName = content.substring(content.lastIndexOf('/')+1,content.length); 
			var tFilePath = content;
			
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
		}
		document.all('divPrintCust').style.display = 'none';
		cleanPreCustomer();
		fm.OfferListNo.value = "";
		fm.NameBJ.value = "";
		fm.QuotNoBJ.value = "";
		fm.QuotTypeBJ.value = "";
		fm.QuotTypeBJName.value = "";
		fm.State.value = "";
		fm.StateName.value = "";
		queryQuotInfo('JS',cOfferListNo);
	}
}

/**
 * 查询
 */
function queryClick(tObj) {
	
	//判断当前登录人是否在特殊项目询价人员表中
	var tSpecial = "0";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql11");
	tSQLInfo.addSubPara(tOperator);
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult[0][0] == "0") {
		tSpecial = "0";
	} else {
		tSpecial = "1";
	}
	
	var tComStirng= aginManage();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql2");
	tSQLInfo.addSubPara(tOperator);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tComStirng);
	tSQLInfo.addSubPara(fm.Name.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(tCurrentDate);
	
	if (tSpecial == "0") {
		tSQLInfo.addSubPara("1");
	} else {
		tSQLInfo.addSubPara("");
	}
	
	initQuotInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), QuotInfoGrid, 2, 1);
	document.all('divPrintCust').style.display = 'none';
	cleanPreCustomer();
	if (tObj == "Page") {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/**
 * 根据登录机构查询使用机构
 * 例：录入：861302 返回：'861302','8613','86'
 */
function aginManage(){
	
	var tempManageCom = "";
	var tempCom = "";
	var tFlag = true;
	var tArr = [];
	tArr.push(tManageCom);
	tempManageCom = tManageCom;
	
	while(tFlag){
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql1");
		tSQLInfo.addSubPara(tempManageCom);
		
		var arrResult = easyExecSql(tSQLInfo.getString());
		
		if (arrResult==null || arrResult[0][0] == "") {
			tFlag = false;
		} else {
			
			tempCom = arrResult[0][0];
			if (checkArray(tArr,tempCom)) {
				tFlag = false;
			} else {
				tArr.push(tempCom);
				tempManageCom = tempCom;
			}
		}
	}
	//拼接字符串
	var tFinalCom = "";
	for (var m=0;m<tArr.length;m++) {
		
		if (m == tArr.length -1) {
			tFinalCom += "'"+tArr[m]+"'";
		} else {
			tFinalCom += "'"+tArr[m]+"',";
		}
	}
	return tFinalCom;
}

/**
 * 判断元素是否在数组内
 */
function checkArray(arr,ch){
	
	var j = arr.length;
	while(j--){
		if (arr[j]===ch) {
			return true;
		}
	}
	return false;
}

/**
 * 展示客户信息
 */
function showPreCustomer() {
	
	document.all('divPrintCust').style.display = '';
	
	var tRow = QuotInfoGrid.getSelNo();
	var tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	var tQuotbatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	var tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	
	if (tQuotType == "00") {//一般询价
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql3");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotbatNo);
		
		var arrResult = easyExecSql(tSQLInfo.getString());
		fm.PreCustomerNo.value = arrResult[0][0];
		fm.PreCustomerName.value = arrResult[0][1];
		fm.IDType.value = arrResult[0][2];
		fm.IDTypeName.value = arrResult[0][3];
		fm.IDNo.value = arrResult[0][4];
		fm.GrpNature.value = arrResult[0][5];
		fm.GrpNatureName.value = arrResult[0][6];
		fm.BusiCategory.value = arrResult[0][7];
		fm.BusiCategoryName.value = arrResult[0][8];
		fm.SumNumPeople.value = arrResult[0][9];
		fm.PreSumPeople.value = arrResult[0][10];
		fm.Address.value = arrResult[0][11];
		
	} else if (tQuotType == "01") {//项目询价
		
		cleanPreCustomer();
	}
}

/**
 * 清空客户信息
 */
function cleanPreCustomer() {
		
	fm.PreCustomerNo.value = "";
	fm.PreCustomerName.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.IDNo.value = "";
	fm.GrpNature.value = "";
	fm.GrpNatureName.value = "";
	fm.BusiCategory.value = "";
	fm.BusiCategoryName.value = "";
	fm.SumNumPeople.value = "";
	fm.PreSumPeople.value = "";
	fm.Address.value = "";
}

/**
 * 查询准客户信息
 */
function queryCustomer() {
	
	var tRow = QuotInfoGrid.getSelNo();
	var tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	var tQuotBatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	var tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	
	var strUrl = "LSQuotPrintQueryCustMain.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&QuotType="+tQuotType;
	window.open(strUrl,"准客户名称查询",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}

/**
 * 查询准客户详细信息并展示
 */
function queryCustInfo() {
	
	var tPreCustomerNo = fm.PreCustomerNo.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql4");
	tSQLInfo.addSubPara(tPreCustomerNo);
	
	var tResult = easyExecSql(tSQLInfo.getString());
	
	fm.IDType.value = tResult[0][0];
	fm.IDTypeName.value = tResult[0][1];
	fm.IDNo.value = tResult[0][2];
	fm.GrpNature.value = tResult[0][3];
	fm.GrpNatureName.value = tResult[0][4];
	fm.BusiCategory.value = tResult[0][5];
	fm.BusiCategoryName.value = tResult[0][6];
	fm.SumNumPeople.value = tResult[0][7];
	fm.PreSumPeople.value = tResult[0][8];
	fm.Address.value = tResult[0][9];
}

/**
 * 生成报价单
 */
function createQuotation() {
	
	//生成报价单必须在3级机构下进行
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql8");
	tSQLInfo.addSubPara(tManageCom);
	
	var tComGradeArr = easyExecSql(tSQLInfo.getString());
	if (tComGradeArr==null) {
		alert("查询机构等级信息失败！");
		return false;
	} else {
		var tComGrade = tComGradeArr[0][0];
		if (tComGrade!="03") {
			alert("该操作应该在3级机构下进行！");
			return false;
		}
	} 
	
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条询价信息！");
		return false;	
	}
	var tQuotState = QuotInfoGrid.getRowColData(tRow-1,6);
	if (tQuotState=="04" || tQuotState =="05") {
		
		alert("该询价已归档,不可生成报价单!");
		return false;	
	}
	
	var tPreCustomerNo = fm.PreCustomerNo.value;
	if (tPreCustomerNo == null || tPreCustomerNo == "") {
		alert("请选择准客户名称！");
		return false;
	}
	if(!confirm('确定要生成报价单吗?')){
		return false;
	}
	
	var tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	var tQuotBatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	var tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	var tProdType = QuotInfoGrid.getRowColData(tRow-1,8);
	var tQuery_QuotNo = fm.Query_QuotNo.value;
	var tQuery_QuotBatNo = fm.Query_QuotBatNo.value;
	
	if (tQuotType == "00") {//一般询价
		
		if (tQuery_QuotNo != null && tQuery_QuotNo != "" && tQuery_QuotBatNo != null && tQuery_QuotBatNo != "") {
			if (tQuotNo != tQuery_QuotNo || tQuotBatNo != tQuery_QuotBatNo) {
				alert("所选询价信息已变动，请重新选择准客户名称！");
				return false;	
			}
		}
	}
	
	fm.Operate.value = "CREATE";
	fm.action = "./LSQuotPrintSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&QuotType="+tQuotType+"&ProdType="+tProdType;
	submitForm(fm);
}

/**
 * 查询报价信息
 */
function queryQuotInfo(cObj,cOfferNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql5");
	if (cObj == "Page") {//页面查询时不限制询价信息
		
		tSQLInfo.addSubPara(fm.NameBJ.value);
		tSQLInfo.addSubPara(fm.QuotNoBJ.value);
		tSQLInfo.addSubPara(fm.QuotTypeBJ.value);
		tSQLInfo.addSubPara(fm.State.value);
		tSQLInfo.addSubPara(fm.OfferListNo.value);
		tSQLInfo.addSubPara(tOperator);
	} else if (cObj == "JS") {//生成报价信息后刷新查询报价信息
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(cOfferNo);
		tSQLInfo.addSubPara(tOperator);
	}
	
	initOfferListGrid();
	turnPage2.queryModal(tSQLInfo.getString(), OfferListGrid, 2, 1);
	
	if (cObj == "Page") {
		if (!turnPage2.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/**
 * 报价单下载
 */
function downloadQuot(parm1) {
	
	var tRow = OfferListGrid.getSelNo();
	//var tOfferListNo = document.all(parm1).all("OfferListGrid1").value;
	var tPrintState = document.all(parm1).all("OfferListGrid8").value;
	if (tPrintState=="0"){
	
	} else {
		var tFileName = document.all(parm1).all("OfferListGrid11").value;
		var tFilePath = document.all(parm1).all("OfferListGrid12").value;
		window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
	}
}

/**
 * 查看报价单
 */
function seeQuotation() {
	
	var tRow = OfferListGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条报价信息！");
		return false;	
	}
	
	var tOfferListNo = OfferListGrid.getRowColData(tRow-1,1);
	var tQuotNo = OfferListGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = OfferListGrid.getRowColData(tRow-1,3);
	var tQuotType = OfferListGrid.getRowColData(tRow-1,4);
	
	var tSrc = "";
	if (tQuotType == "00") {
		tSrc = "./LSQuotETSeeInput.jsp";
	} else if (tQuotType == "01") {
		tSrc = "./LSQuotProjSeeInput.jsp";
	}

	tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo+"&QuotType="+tQuotType+"&QuotQuery=N";
	location.href = tSrc;
}

/**
 * 明细查看
 */
function toPalnDetailView() {
	
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条询价信息！");
		return false;	
	}
	tQuotNo = QuotInfoGrid.getRowColData(tRow-1,1);
	tQuotBatNo = QuotInfoGrid.getRowColData(tRow-1,2);
	tQuotType = QuotInfoGrid.getRowColData(tRow-1,3);
	showPalnDetailView();
}

/**
 * 打印报价单
 */
function printOfferList(o) {
	
	var tRow = OfferListGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条报价信息！");
		return false;	
	}
	var tState = OfferListGrid.getRowColData(tRow-1,8);
	if (tState=="1") {
		alert("报价单已打印，不可再次打印！");
		return false;
	}
	var tOfferListNo = OfferListGrid.getRowColData(tRow-1,1);
	var tQuotNo = OfferListGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = OfferListGrid.getRowColData(tRow-1,3);
	var tQuotType = OfferListGrid.getRowColData(tRow-1,4);
	
	//校验没选择方案时不可打印
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql7");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tPlanCountArr = easyExecSql(tSQLInfo.getString());
	
	if (tPlanCountArr[0][0]==0) {
		alert("请先选择报价方案！");
		return false;
	}
	
	//一般询价账户险种时，校验报价方案必须要有团体账户、个人账户
	var tTranProdType = getTranProdType(tQuotType,tQuotNo, tQuotBatNo);
	
	if (tQuotType=="00" && tTranProdType=="02") {

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql9");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tArr = easyExecSql(tSQLInfo.getString());
		if (tArr.length < 2) {
			alert("账户险所选报价方案必须有团体账户和个人账户方案类型！");
			return false;
		}
	}
	
	//校验项目询价建工险时，是否在选择报价方案时维护了工程造价或工程面积
	if (tQuotType=="01" && tTranProdType=="01") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
		tSQLInfo.setSqlId("LSQuotPrintSql10");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tEnginArr = easyExecSql(tSQLInfo.getString());
		if (tEnginArr == null) {
			alert("请先选择报价方案！");
			return false;
		} else {
			if (tEnginArr[0][0] == "2" && tEnginArr[0][1] == "-1") {
				alert("所选报价方案未维护工程造价！");
				return false;
			}
			if (tEnginArr[0][0] == "3" && tEnginArr[0][2] == "-1") {
				alert("所选报价方案未维护工程面积！");
				return false;
			}
		} 
	}
	
	var tPrintType = "";
	if (o == "pdf") {
		tPrintType = "pdf";
	} else if (o == "doc") {
		tPrintType = "doc";
	}
	fm.Operate.value = "PRINT";
	fm.action = "./LSQuotOfferPrintSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&QuotType="+tQuotType +"&ProdType="+tTranProdType+"&OfferListNo="+tOfferListNo+"&PrintType="+tPrintType;
	submitForm(fm);
}


/**
 * 获取产品类型
 */
function getTranProdType(cQuotType,cQuotNo, cQuotBatNo) {
	
	if (cQuotType=="00") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql8");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
	} else if (cQuotType=="01") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotProjPlanSql");
		tSQLInfo.setSqlId("LSQuotProjPlanSql1");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
	}
	
	var tProdArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tProdArr==null) {
	
	} else {
		return tProdArr[0][0];
	}
	
	return null;
}
