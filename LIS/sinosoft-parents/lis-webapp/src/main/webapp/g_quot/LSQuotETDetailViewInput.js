/***************************************************************
 * <p>ProName：LSQuotETDetailViewInput.js</p>
 * <p>Title：明细查看(一般询价)</p>
 * <p>Description：明细查看(一般询价)</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var tAllDetailOpen;
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
}

/**
 * 初始化询价第一步信息
 */
function initQuotStep1() {
	
	fm2.QuotNo.value = tQuotNo;
	fm2.QuotBatNo.value = tQuotBatNo;
	
	//初始化基础信息
	initBasicInfo();
	
	fm2.all("divPlanDiv").innerHTML = showPlanDiv("1");
}

/**
 * 初始化基础信息
 */
function initBasicInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tBasicArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBasicArr==null) {
		return false;
	} else {
	
		var tBasicInfo = new Array();
		var i = 0;
		//tBasicInfo[i] = "";
		tBasicInfo[i++] = "PreCustomerNo";
		tBasicInfo[i++] = "PreCustomerName";
		tBasicInfo[i++] = "IDType";
		tBasicInfo[i++] = "IDTypeName";
		tBasicInfo[i++] = "IDNo";
		tBasicInfo[i++] = "GrpNature";
		tBasicInfo[i++] = "GrpNatureName";
		tBasicInfo[i++] = "BusiCategory";
		tBasicInfo[i++] = "BusiCategoryName";
		tBasicInfo[i++] = "Address";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "PremModeA";
		tBasicInfo[i++] = "PremModeAName";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "RenewFlag";
		tBasicInfo[i++] = "RenewFlagName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ElasticFlag";
		tBasicInfo[i++] = "ElasticFlagName";
		tBasicInfo[i++] = "PayIntv";
		tBasicInfo[i++] = "PayIntvName";
		tBasicInfo[i++] = "InsuPeriod";
		tBasicInfo[i++] = "InsuPeriodFlag";
		tBasicInfo[i++] = "InsuPeriodFlagName";
		tBasicInfo[i++] = "Coinsurance";
		tBasicInfo[i++] = "CoinsuranceName";
		tBasicInfo[i++] = "ValDateType";
		tBasicInfo[i++] = "ValDateTypeName";
		tBasicInfo[i++] = "AppointValDate";
		tBasicInfo[i++] = "CustomerIntro";
		
		for (var t=0; t<i; t++) {
			fm2.all(tBasicInfo[t]).value = tBasicArr[0][t];
		}
		
		var tValDateType = fm2.ValDateType.value;
		if (tValDateType=="1") {
			
			fm2.all("tdValDate1").style.display = '';			
			fm2.all("tdValDate2").style.display = '';
			fm2.all("tdValDate3").style.display = 'none';
			fm2.all("tdValDate4").style.display = 'none';
		}
		
		var tProdType = fm2.ProdType.value;
		if (tProdType == "00") {//普通险种
			fm2.all("tdElasticFlag1").style.display = '';			
			fm2.all("tdElasticFlag2").style.display = '';
			fm2.all("tdElasticFlag3").style.display = 'none';
			fm2.all("tdElasticFlag4").style.display = 'none';
		} else {
			fm2.all("tdElasticFlag1").style.display = 'none';			
			fm2.all("tdElasticFlag2").style.display = 'none';
			fm2.all("tdElasticFlag3").style.display = '';
			fm2.all("tdElasticFlag4").style.display = '';
		}
		
		//如果是账户险，展示【产品参数信息】按钮
		if (tProdType == "02") {
			fm2.all("productButton").style.display = '';			
		} else {
			fm2.all("productButton").style.display = 'none';
		}
	}
	//销售渠道处理
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql12");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr1==null) {
		
		return false;
	} else {
		
		fm2.SaleChannel.value = tArr1[0][0];
		fm2.SaleChannelName.value = tArr1[0][1];
		
		if (tArr1[0][2].substring(0,1)=="1") {//需要中介
			
			//先查询中介信息
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql22");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			noDiv(turnPage2, AgencyListGrid, tSQLInfo.getString());
			
			fm2.all("divAgencyInfo").style.display = "";
		}
	}

	//关联准客户处理
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql13");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);

	if (!noDiv(turnPage2, RelaCustListGrid, tSQLInfo.getString())) {
		initRelaCustListGrid();
		return false;
  }

  if (RelaCustListGrid.mulLineCount>0) {

		fm2.all('RelaCustomerFlag').checked = true;
		fm2.all('divRelaCustInfo').style.display = '';
  }
}

/** 询价--第一步--结束*/

/**
 * 展示工程信息
 */
function showEnginInfo() {
	
	if (tTranProdType=="00") {
		fm2.all('divEngin').style.display = 'none';
	} else if (tTranProdType=="02") {
		fm2.all('divEngin').style.display = 'none';
	} else {
		fm2.all('divEngin').style.display = '';
		queryEnginInfo(fm2);
		fm2.all("divEnginFactor").innerHTML = showEnginFactorDiv(tQuotNo, tQuotBatNo, '0000000000', '000', '1');
		pubShowConditionCheck(fm2);
	}
}

/**
 * 查询工程信息
 */
function queryEnginInfo(cObj) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql23");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		//不做处理
	} else {
		
		var tEnginInfo = new Array();
		var i = 0;
		tEnginInfo[i++] = "EnginName";
		tEnginInfo[i++] = "EnginType";
		tEnginInfo[i++] = "EnginTypeName";
		tEnginInfo[i++] = "EnginArea";
		tEnginInfo[i++] = "EnginCost";
		tEnginInfo[i++] = "EnginPlace";
		tEnginInfo[i++] = "EnginStartDate";
		tEnginInfo[i++] = "EnginEndDate";
		tEnginInfo[i++] = "EnginDesc";
		tEnginInfo[i++] = "EnginCondition";
		tEnginInfo[i++] = "Remark";
		tEnginInfo[i++] = "InsurerName";
		tEnginInfo[i++] = "InsurerType";
		tEnginInfo[i++] = "InsurerTypeName";
		tEnginInfo[i++] = "ContractorName";
		tEnginInfo[i++] = "ContractorType";
		tEnginInfo[i++] = "ContractorTypeName";
		
		for (var t=0; t<i; t++) {
			
			document.all(tEnginInfo[t]).value = tArr[0][t];
		}
	}
}

/**
 * 初始化方案信息查询
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql14");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 2, 1);
}


/**
 * 选择方案记录后处理
 */
function showPlanDetailInfo() {
	
	fm2.all('divInfo2').style.display = '';
	pubShowPlanInfo(fm2, tQuotType, tTranProdType);
	fm2.SexRate.value = fm2.MaleRate.value + " : " + fm2.FemaleRate.value;
}

/**
 * 关闭方案明细
 */
function closePlanDetail() {
	
	fm2.all('divInfo2').style.display = 'none';
}

/**
 * 查询核保处理信息
 */
function queryFinalConclu() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql6");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrUW = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tArrUW != null){
		if (tArrUW[0][2]==null || tArrUW[0][2] == "") {
			
			fm2.FinalOpinion.value = tArrUW[0][3];
			fm2.FinalConclu.value = tArrUW[0][4];
			fm2.FinalConcluName.value = tArrUW[0][5];
			
		} else {
			fm2.FinalOpinion.value = tArrUW[0][0];
			fm2.FinalConclu.value = tArrUW[0][1];
			fm2.FinalConcluName.value = tArrUW[0][2];
		}
	}
}

/**
 * 方案明细
 */
function showAllDetail() {
	
	tAllDetailOpen = window.open("./LSQuotPlanAllDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * 产品参数维护
 */
function showProdParam() {
	
	window.open("./LSProdParamMain.jsp?ObjType=QUOT&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"产品参数维护",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 方案明细维护
 */
function planDetailOpen() {
	
	tPlanDetailOpen = window.open("./LSQuotETPlanDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID, "1", "height==800,width=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
