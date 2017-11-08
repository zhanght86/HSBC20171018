/***************************************************************
 * <p>ProName：LSQuotQueryInput.js</p>
 * <p>Title：询价查询</p>
 * <p>Description：询价查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-09-17
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
var mOperate = "";//操作状态

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
		
		if (mOperate == "QUERY_PRINT") {
			
			var tFileName = content.substring(content.lastIndexOf('/')+1,content.length); 
			var tFilePath = content;
			
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
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
	}
}

/**
 * 查询询价信息
 */
function queryClick() {
	
	document.all("enterQuest").style.display = "none";
	document.all("UWDesc").style.display = "none";
	document.all("printButton").style.display = "none";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql1");
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(fm.QuotState.value);
	tSQLInfo.addSubPara(fm.Manager.value);
	tSQLInfo.addSubPara(fm.ApplyCom.value);
	tSQLInfo.addSubPara(fm.ApplyStartDate.value);
	tSQLInfo.addSubPara(fm.ApplyEndDate.value);
	tSQLInfo.addSubPara(fm.UWStartDate.value);
	tSQLInfo.addSubPara(fm.UWEndDate.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	
	initQuotGrid();
	initOfferGrid();
	//turnPage1.pageLineNum = 5;
	turnPage1.queryModal(tSQLInfo.getString(), QuotGrid, 2, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * 报价信息
 */
function clickOfferInfo() {
	
	var tRow = QuotGrid.getSelNo();
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	var tActivityID = QuotGrid.getRowColData(tRow-1,19);
	
	//处理问题件按钮
	showQuestionButton(tQuotNoTemp,tQuotBatNoTemp,tActivityID);
	
	//处理总公司最终意见
	showUWDescButton(tQuotNoTemp,tQuotBatNoTemp,tActivityID);
	
	//处理询价单打印按钮展示
	var tQuotState = QuotGrid.getRowColData(tRow-1,8);
	if (tQuotState =="02" || tQuotState =="04" || tQuotState =="05") {
		document.all("printButton").style.display = "";
	} else {
		document.all("printButton").style.display = "none";
	}
	
	//处理再保临分方案配置按钮
	//showFaculButton(tQuotNoTemp,tQuotBatNoTemp);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql2");
	tSQLInfo.addSubPara(tQuotNoTemp);
	tSQLInfo.addSubPara(tQuotBatNoTemp);
	
	initOfferGrid();
	turnPage2.queryModal(tSQLInfo.getString(), OfferGrid, 2, 1);
}

/**
 * 明细查看
 */
function showQuotInfo() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条询价信息！");
		return false;	
	}
	tQuotNo = QuotGrid.getRowColData(tRow-1,1);
	tQuotBatNo = QuotGrid.getRowColData(tRow-1,2);
	tQuotType = QuotGrid.getRowColData(tRow-1,3);
	
	showPalnDetailView();
}

/**
 * 报价信息查看
 */
function showOfferInfo() {
	
	var tRow = OfferGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条报价信息！");
		return false;	
	}

	var tOfferListNo = OfferGrid.getRowColData(tRow-1,1);
	var tQuotNo = OfferGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = OfferGrid.getRowColData(tRow-1,3);
	var tQuotType = OfferGrid.getRowColData(tRow-1,4);

	var tSrc = "";
	if (tQuotType == "00") {
		tSrc = "./LSQuotETSeeInput.jsp";
	} else if (tQuotType == "01") {
		tSrc = "./LSQuotProjSeeInput.jsp";
	}

	tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo+"&QuotType="+tQuotType+"&QuotQuery=Y&ReturnFlag=1";
	//location.href = tSrc;
	window.open(tSrc,"报价信息查看",'width=2000,height=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


/**
 * 处理问题件按钮的展示
 */
function showQuestionButton(cQuotNo,cQuotBatNo,cActivityID) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql46");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
		
	
	var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr[0][0]=="0") {
		document.all("enterQuest").style.display = "none";
	} else {
		document.all("enterQuest").style.display = "";
	}
}

/**
 * 问题件管理
 */
function goQuestion() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条询价信息！");
		return false;	
	}
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	var tActivityID = QuotGrid.getRowColData(tRow-1,19);
	
	//添加 ShowFlag=1 标记，处理询价查询时，问题件模块按钮的显示隐藏
	window.open("../g_busicommon/LDQuestionMain.jsp?OtherNoType=QUOT&OtherNo="+tQuotNoTemp+"&SubOtherNo="+tQuotBatNoTemp+"&ActivityID="+tActivityID+"&ShowStyle=1&ShowFlag=1","问题件管理",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 处理总公司最终意见展示
 */
function showUWDescButton(cQuotNo,cQuotBatNo,cActivityID) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql4");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	
	var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr[0][0]=="0") {
		document.all("UWDesc").style.display = "none";
	} else {
		document.all("UWDesc").style.display = "";
	}
}

/**
 * 总公司最终意见
 */
function goUWDesc() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条询价信息！");
		return false;	
	}
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	
	window.open("./LSQuotShowUWDescMain.jsp?QuotNo="+ tQuotNoTemp + "&QuotBatNo="+ tQuotBatNoTemp,"总公司最终意见",'height=300,width=900,top=0,left=370,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 打印询价单
 */
function printInquiry(o) {
	
	var tRow = QuotGrid.getSelNo();
	var tQuotNoT = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoT = QuotGrid.getRowColData(tRow-1,2);
	var tQuotTypeT = QuotGrid.getRowColData(tRow-1,3);
	var tTranProdTypeT = QuotGrid.getRowColData(tRow-1,6);
	var tMissionIDT = "";
	var tSubMissionIDT = "";
	var tActivityIDT = "";
	
	var tPrintType = "";
	if (o == "pdf") {
		tPrintType = "pdf";
	} else if (o == "doc") {
		tPrintType = "doc";
	}
	mOperate = "QUERY_PRINT";
	fm.action = "./LSQuotPrintInquirySave.jsp?QuotType="+tQuotTypeT+"&QuotNo="+tQuotNoT+"&QuotBatNo="+tQuotBatNoT+"&MissionID="+tMissionIDT+"&SubMissionID="+tSubMissionIDT+"&ActivityID="+tActivityIDT+"&Operate="+ mOperate +"&ProdType="+ tTranProdTypeT+"&PrintType="+tPrintType;
	submitForm(fm);
} 

/**
 * 处理再保临分方案配置按钮
 */
function showFaculButton(cQuotNo,cQuotBatNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql6");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	
	var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr != null && tArr[0][0]=="2") {
		document.all("faculButton").style.display = "";
	} else {
		document.all("faculButton").style.display = "none";
	}
}

/**
 * 再保临分方案配置
 */
function faculPlanInput() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条询价信息！");
		return false;	
	}
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	
	window.open("../reinsure/RIFaculPreceptMain.jsp?QuotNo="+ tQuotNoTemp + "&QuotBatNo="+ tQuotBatNoTemp+ "&Flag=0","再保临分方案配置",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
