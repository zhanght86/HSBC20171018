/***************************************************************
 * <p>ProName：LSQuotProjDetailViewInput.js</p>
 * <p>Title：明细查看(项目询价)</p>
 * <p>Description：明细查看(项目询价)</p>
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
var tTranPremMode;//保费分摊方式

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
 * 选择报价方案
 */
function selectQuotPlan() {
	
	window.open("./LSQuotSelectPlanMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo + "&OfferListNo="+ tOfferListNo +"&TranProdType="+ tTranProdType+"&QuotQuery="+ tQuotQuery,"选择报价方案",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 报价方案明细
 */
function offerPalnDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql7");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tBJPlanNum = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBJPlanNum[0][0]==0) {
		alert("请先选择报价方案!");
		return false;
	}
	window.open("./LSQuotBJPlanDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&TranProdType="+ tTranProdType +"&OfferListNo="+ tOfferListNo+"&QuotQuery="+ tQuotQuery,"报价方案明细",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 返回
 */
function returnPrint() {
	if(tReturnFlag=="1"){
		top.close();
	}else{
		location.href = "./LSQuotPrintInput.jsp";
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
	fm2.all("divPayIntvDiv").innerHTML = showPayIntvDiv("1");
	fm2.all("divSaleChnlDiv").innerHTML = showSaleChnlDiv("1");
	
}

/**
 * 项目询价--初始化基础信息
 */
function initBasicInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tBasicArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBasicArr==null) {
		return false;
	} else {
		
		var tBasicInfo = new Array();
		var i = 0;
		tBasicInfo[i] = "";
		tBasicInfo[i++] = "ProjName";
		tBasicInfo[i++] = "TargetCust";
		tBasicInfo[i++] = "NumPeopleA";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "PreLossRatio";
		tBasicInfo[i++] = "Partner";
		tBasicInfo[i++] = "ExpiryDate";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ElasticFlag";
		tBasicInfo[i++] = "ElasticFlagName";
		tBasicInfo[i++] = "ProjDesc";
		
		for(var t=0; t<i; t++) {
			fm2.all(tBasicInfo[t]).value = tBasicArr[0][t];
		}
		
		var tProdType = fm2.ProdType.value;
		if (tProdType == "00") {//普通险种
			fm2.all("tdElasticFlag1").style.display = '';			
			fm2.all("tdElasticFlag2").style.display = '';
			fm2.all("tdElasticFlag3").style.display = '';
			fm2.all("tdElasticFlag4").style.display = '';
			fm2.all("tdElasticFlag5").style.display = 'none';
			fm2.all("tdElasticFlag6").style.display = 'none';
		} else {
			fm2.all("tdElasticFlag1").style.display = 'none';			
			fm2.all("tdElasticFlag2").style.display = 'none';
			fm2.all("tdElasticFlag3").style.display = '';
			fm2.all("tdElasticFlag4").style.display = '';
			fm2.all("tdElasticFlag5").style.display = '';
			fm2.all("tdElasticFlag6").style.display = '';
		}
	}
	
	//关联适用机构
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage2, AppOrgCodeGrid, tSQLInfo.getString())) {
		initAppOrgCodeGrid();
		return false;
	}
	
	//关联其他项目询价号既往信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql6");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage4, LinkInquiryNoGrid, tSQLInfo.getString())) {
		initLinkInquiryNoGrid();
		return false;
	}
  
	if (LinkInquiryNoGrid.mulLineCount>0) {

		fm2.all('LinkInquiryNo').checked = true;
		fm2.all('divLinkInquiryNo').style.display = '';
	}
}

/**
 * 展示中介机构名称
 */
function showAgencyInfo() {
	
	//关联中介名称
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage3, AgencyNameGrid, tSQLInfo.getString())) {
		initAgencyNameGrid();
		return false;
	}
}

/**
 * 初始化方案信息
 */
function initQuotStep2() {
	
	queryPlanInfo();
	pubInitQuotStep2(fm2, tQuotType, tTranProdType, tTranPremMode, '');
}

/**
 * 初始化方案信息查询
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql25");
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

