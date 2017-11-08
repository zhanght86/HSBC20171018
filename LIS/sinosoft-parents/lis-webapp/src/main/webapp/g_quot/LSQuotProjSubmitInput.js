/***************************************************************
 * <p>ProName：LSQuotProjSubmitInput.js</p>
 * <p>Title：询价提交</p>
 * <p>Description：询价提交</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-25
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//中介机构 initAgencyListGrid
var turnPage2 = new turnPageClass();//其他准客户关联 initRelaCustListGrid
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var turnPage9 = new turnPageClass();

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
	pubShowAgencyInfoCheck(fm2);
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
		tBasicInfo[i++] = "NumPeople";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "PreLossRatio";
		tBasicInfo[i++] = "Partner";
		tBasicInfo[i++] = "ExpiryDate";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ProjDesc";
		
		for(var t=0; t<i; t++) {
			fm2.all(tBasicInfo[t]).value = tBasicArr[0][t];
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
 * 初始化方案信息查询
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql25");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid,1,1);//第三位表示使用大数据量
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
		
		location.href = "./LSQuotProjQueryInput.jsp";
	}	
}

/**
 * 上一步
 */
function previousStep() {
	
	goToStep(2);
}

/**
 * 回目录
 */
function returnLR() {
	
	goToStep(0);
}

/**
 * 询价信息提交
 */
function quotInfoSubmit() {
	
	fmOther.action = "./LSQuotEnterFinalSave.jsp?Operate=ENTERSUBMIT&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmOther);
}

function showAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID) {
	
	cObj.PageInfo.value = "第"+OnPage+"/"+PageNum+"页";
	document.all("divShowAllPlan").innerHTML = pubShowAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID);
}

/**
 * 展示指定页
 */
function goToPage(OnPage) {
	
	tStartNum = (OnPage-1)*tPlanShowRows+1;
	showAllPlanDetail(fm2, strQueryResult,tStartNum, tQuotType, tTranProdType, tActivityID);
}
