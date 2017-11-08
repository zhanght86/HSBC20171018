/***************************************************************
 * <p>ProName：LSQuotCommonInput.js</p>
 * <p>Title：询价阶段共用域</p>
 * <p>Description：询价阶段共用域</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var tETQuotType = "00";
var tProjQuotType = "01";
var tActivityType = "8001";
var tPlanShowRows = 10;
var tNewColor = "#FCE6C9";
var tDifColor = "#FF0000";

function setTab(m,n){
	var menu=document.getElementById("tab"+m).getElementsByTagName("li");

	for(i=0;i<2;i++)
	{
	   menu[i].className=i==n?"now":""; 
		if (i==n) {
			document.getElementById("tablistdiv"+i).style.display = "block";
		} else {
			document.getElementById("tablistdiv"+i).style.display = "none";
		}
	}
	
	initForm();
}

function setTabOver(m,n){
	var menu=document.getElementById("tab"+m).getElementsByTagName("li");
	menu[n].className="lobutton";
}
	
function setTabOut(m,n){
	var menu=document.getElementById("tab"+m).getElementsByTagName("li");
	menu[n].className="libutton";
}

/**
 * 自定义codename赋值
 */
function auotQuotShowCodeName(cCodeType, cCode, cObj, cCodeName) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql9");
	tSQLInfo.addSubPara(cCodeType);
	tSQLInfo.addSubPara(cCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {

	} else {
		
		document.all(cCodeName).value = tArr[0][0];
	}
}

/**
 * 不分页方法
 */
function noDiv(objPage, objGrid, tSql) {
	
	//为兼容已允许的程序，容错而增加
	objPage = new turnPageClass();
	objPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1, 0, 1);
	//判断是否查询成功
	if (!objPage.strQueryResult) {
		//清空MULTILINE，使用方法见MULTILINE使用说明
		objGrid.clearData();
		return false;
	}
	
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	objPage.arrDataCacheSet = clearArrayElements(objPage.arrDataCacheSet);
	//清空缓存
	objPage.allArrDataCacheSet = clearArrayElements(objPage.allArrDataCacheSet);
	objPage.allCacheSize = 0;
	//查询成功则拆分字符串，返回二维数组
	objPage.arrDataCacheSet = decodeEasyQueryResult(objPage.strQueryResult, 0, 0, objPage);
	objPage.pageLineNum = objPage.queryAllRecordCount;
	var tKey = 1;
	//cTurnPage.allCacheSize ++;
	objPage.allArrDataCacheSet[objPage.allCacheSize%objPage.allArrCacheSize] = {id:tKey,value:objPage.arrDataCacheSet};
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	objPage.pageDisplayGrid = objGrid;
	//保存SQL语句
	objPage.strQuerySql = tSql;
	//设置查询起始位置
	objPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	var arrDataSet = objPage.getData(objPage.arrDataCacheSet, objPage.pageIndex, objPage.pageLineNum);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, objPage.pageDisplayGrid, objPage);
	if (objPage.showTurnPageDiv==1) {
		try {
			objGrid.setPageMark(objPage);
		} catch(ex){}
	}
	
	return true;
}

/**
 * 跳转至目标步骤,o目标步骤
 */
function goToStep(o) {
	
	var tPath = "?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	
	if (o=="1") {//去往第一步时,无条件跳转,仅判断询价类型即可
		
		if (tQuotType==tETQuotType) {
			location.href = "./LSQuotETBasicInput.jsp"+ tPath;
		} else if (tQuotType==tProjQuotType) {
			location.href = "./LSQuotProjBasicInput.jsp"+ tPath;
		}
	} else if (o=="2" || o=="3") {//只要第一步进行了录入,即可向2,3步进行跳转,即只要产品类型
		
		//查询是否为第一步信息已录入
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql7");
		tSQLInfo.addSubPara(tMissionID);
		tSQLInfo.addSubPara(tSubMissionID);
		tSQLInfo.addSubPara(tActivityID);
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(tQuotType);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null) {//未查询到记录
			alert("获取该询价信息失败！");
			return false;
		}
		
		if (tArr[0][0]=="0") {
			alert("该询价基本信息未录入！");
			return false;
		}
		
		if (o=="2") {
			
			if (tQuotType==tETQuotType) {
				location.href = "./LSQuotETPlanInput.jsp"+ tPath;
			} else if (tQuotType==tProjQuotType) {
				location.href = "./LSQuotProjPlanInput.jsp"+ tPath; 
			}
		}
		if (o=="3") {
			
			if (tQuotType==tETQuotType) {
				location.href = "./LSQuotETSubmitInput.jsp"+ tPath;
			} else if (tQuotType==tProjQuotType) {
				location.href = "./LSQuotProjSubmitInput.jsp"+ tPath; 
			}
		}
	}
}

/**
 * 产品参数维护跳转页 o-目标步骤
 */
function goToPordParamStep(o, p) {
	
	var tPath = "?ObjType="+ tObjType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	if (o=="0") {//管理费维护

		location.href = "./LSProdParamInput.jsp" + tPath +"&Flag=0";
	} else if (o=="1") {//保全退费算法维护
		
		location.href = "./LSEdorRefundCalInput.jsp" + tPath;
	}
}

/**
 * 长期险测算跳转页
 */
function goToLongRiskStep(o) {
	
	var tPath = "?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	if (o=="0") {//费率测算
		location.href = "./LSQuotCountRateMain.jsp" + tPath;
	} else if (o=="1") {//收益测算
		location.href = "./LSQuotCountProfitMain.jsp" + tPath;
	}
}

/**
 * 获取产品类型
 */
function getProdType(cQuotNo, cQuotBatNo) {
	
	if (tQuotType==tETQuotType) {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql8");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
	} else if (tQuotType==tProjQuotType) {
		
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

/**
 * 获取保费分摊方式
 */
function getPremMode(cQuotNo, cQuotBatNo) {
   
	if (tTranProdType=="01") {//建工险无保费分摊方式
		
	} else {//进行保费分摊方式获取
		
		if (tQuotType==tETQuotType) {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql10");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
				
			} else {
				return tArr[0][0];
			}
		}
	}
	
	return "";
}

/**
 * 校验数据整数位于小数位长度
 */
function checkDecimalFormat(cValue, cLen1, cLen2) {
	
	if (cValue=='' || cValue==null) {//为空,
		return true;
	}
	
	var tLen =  (""+cValue+"").length;
	var tLen1 = (""+cValue+"").indexOf(".");
	var tLen2 = 0;
	if (tLen1==-1) {
		tLen1 = tLen;
	} else {
		tLen2 = tLen - tLen1 - 1;
	}
	
	if (Number(tLen1)>Number(cLen1)) {
		return false;
	}
	
	if (Number(tLen2)>Number(cLen2)) {
		return false;
	}
	
	return true;
}

/**
 * 业务需求
 */
function showRequest() {
	
	window.open("./LSQuotRequestMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"业务需求",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 展示业务需求
 */
function onlyShowRequest() {
	
	window.open("./LSQuotShowRequestMain.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo,"业务需求",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 费用信息
 */
function showFeeInfo() {
	
	//录入方案明细后才允许录入费用信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql21");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		alert("请先录入方案明细信息！");
		return false;
	}
	
	window.open("./LSQuotFeeMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"费用信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 既往信息
 */
function showPast() {
	
	window.open("./LSQuotPastMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"既往信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 问卷调查
 */
function showQuesnaire() {
	
	window.open("./LSQuotQuesnaireMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID,"问卷调查",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 附件管理
 */
function showAttachment() {
	
	window.open("../g_busicommon/LDAttachmentMain.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID,"附件管理",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 问题件管理
 */
function goToQuestion() {
	
	window.open("../g_busicommon/LDQuestionMain.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&ActivityID="+tActivityID+"&ShowStyle=1","问题件管理",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 特别约定
 */
function showGrpSpec() {
	
	window.open("./LSQuotGrpSpecMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"特别约定",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 方案组合配置
 */
function showPlanCombi() {
	
	window.open("./LSQuotPlanCombiMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"方案组合配置",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 责任拓展
 */
function showExpand() {
	
	window.open("./LSQuotExpandMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"责任拓展",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 核保规则
 */
function showUWRule() {
	
	window.open("./LSQuotUWRuleMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"核保规则",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 明细查看
 */
function showPalnDetailView() {
	
	window.open("./LSQuotDetailViewMain.jsp?QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType,"明细查看",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 长期险测算
 */
function showRateCount() {
	
	window.open("./LSQuotCountRateMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"长期险测算",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 共保设置
 */
function showCoinsurance() {
	
	//共保标识为 否 时，不可维护共保信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql35");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tCoinArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCoinArr[0][0]=="0") {
		alert("请将[是否共保]选为\"是\"且点击\"基本信息保存\"按钮后，再进行共保设置！");
		return false;
	}
	
	window.open("./LSQuotCoinsuranceMain.jsp?QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID,"共保设置",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 *	理赔责任要素控制
 */
function showCMRule() {

	var tCMRuleFlag = "0";
	if(tActivityID=="0800100002" || tActivityID=="0800100003") {
		tCMRuleFlag = "1";
	}
	
	var strUrl="../g_claim/LLCLaimControlMain.jsp?BussType=QUOT&BussNo="+ tQuotNo + "&SubBussNo="+ tQuotBatNo + "&Flag="+ tCMRuleFlag;
	window.open(strUrl,"理赔责任控制",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 初始化自定义翻页信息
 * cMark=1时，只查询单个方案信息
 */
function initPubDetailPageInfo(cQuotNo, cQuotBatNo,cSysPlanCode,cPlanCode,cMark) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
	tSQLInfo.setSqlId("LSQuotAllDetailSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (cMark=="1") {
		tSQLInfo.addSubPara(cSysPlanCode);
		tSQLInfo.addSubPara(cPlanCode);
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult==null || strQueryResult=="") {
		RowNum = 0;
		PageNum = 0;
		divTurnPage.style.display = "none";
	} else {
		
		RowNum = strQueryResult.length;
		//计算出总的页数TotalPage
		if (RowNum%tPlanShowRows==0) {
			PageNum = parseInt(RowNum/tPlanShowRows);
		} else {
			PageNum = parseInt(RowNum/tPlanShowRows)+1;
		}
	}
}

/**
 * 初始化报价方案明细翻页信息
 */
function initQuotPlanDetailPageInfo(cOfferListNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
	tSQLInfo.setSqlId("LSQuotAllDetailSql9");
	tSQLInfo.addSubPara(cOfferListNo);
	
	strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	RowNum = strQueryResult.length;
	
	//计算出总的页数TotalPage
	if (RowNum%tPlanShowRows==0) {
		PageNum = parseInt(RowNum/tPlanShowRows);
	} else {
		PageNum = parseInt(RowNum/tPlanShowRows)+1;
	}
}

/**
 * 明细一览展示处理
 */
function pubShowAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID) {
	
	
	var tNeedCheckFlag = false;
	var tTraceNo = "";
	if (cActivityID=="0800100002" || cActivityID=="0800100003" || cActivityID=="0800100004") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
		tSQLInfo.setSqlId("LSQuotAllDetailSql12");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		tSQLInfo.addSubPara(cActivityID);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]==null || tArr[0][0]=="") {
			
			tNeedCheckFlag = false;
		} else {
			
			tNeedCheckFlag = true;
			tTraceNo =  tArr[0][0];
		}
	}
	
	var tInnerHTML1 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr></table>"
	
	if (cArr==null) {
		return tInnerHTML1;
	}
	
	var tWidthArr = new Array();
	tWidthArr[0] = 30;
	tWidthArr[1] = '';
	tWidthArr[2] = 130;
	tWidthArr[3] = '';
	tWidthArr[4] = 130;
	tWidthArr[5] = 250;
	tWidthArr[6] = '';
	tWidthArr[7] = 80;
	tWidthArr[8] = 70;
	tWidthArr[9] = 70;
	tWidthArr[10] = 60;
	tWidthArr[11] = 80;
	tWidthArr[12] = 60;
	tWidthArr[13] = 80;
	tWidthArr[14] = 60;
	tWidthArr[15] = 80;
	tWidthArr[16] = 60;
	tWidthArr[17] = 80;
	tWidthArr[18] = 80;

	tInnerHTML1 = "";
	for (var i=0; i<tPlanShowRows; i++) {

		if ((tStartNum+i-1)>=RowNum) {
			//相等时,表示总记录数取尽
		} else {
			
			var tSysPlanCode = cArr[tStartNum+i-1][0];
			var tPlanCode = cArr[tStartNum+i-1][1];
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
			tSQLInfo.setSqlId("LSQuotAllDetailSql6");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);

			var tPlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
			var tPlanNeedCheck = false;
			var tOldPlanArr;
			if (tNeedCheckFlag) {//需要校验
			
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
				tSQLInfo.setSqlId("LSQuotAllDetailSql13");
				tSQLInfo.addSubPara(tQuotNo);
				tSQLInfo.addSubPara(tQuotBatNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);     
				tSQLInfo.addSubPara(tTraceNo);
      	
				tOldPlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if (tOldPlanArr==null) {
					tPlanNeedCheck = false;
				} else {
					tPlanNeedCheck = true;
				}
			}

			if (tPlanArr!=null) {
			
				var j = 0;
				var tSysPlanCode = tPlanArr[0][j++];
				var tPlanCode = tPlanArr[0][j++];
				var tPlanDesc = tPlanArr[0][j++];
				var tPlanType =  tPlanArr[0][j++];
				var tPlanTypeName =  tPlanArr[0][j++];
				var tPremCalType =  tPlanArr[0][j++];
				var tPremCalTypeName =  tPlanArr[0][j++];
				var tPlanFlag = tPlanArr[0][j++];
				var tPlanFlagName = tPlanArr[0][j++];
				var tInsuredPeriod = tPlanArr[0][j++];//保险期间
				var tOccupTypeFlag =  tPlanArr[0][j++];
				var tOccupTypeFlagName =  tPlanArr[0][j++];
				var tMinOccupType =  tPlanArr[0][j++];
				var tMinOccupTypeName =  tPlanArr[0][j++];
				var tMaxOccupType =  tPlanArr[0][j++];
				var tMaxOccupTypeName =  tPlanArr[0][j++];
				var tOccupType =  tPlanArr[0][j++];
				var tOccupTypeName =  tPlanArr[0][j++];
				var tOccupMidType =  tPlanArr[0][j++];
				var tOccupMidTypeName =  tPlanArr[0][j++];
				var tOccupCode =  tPlanArr[0][j++];
				var tOccupCodeName =  tPlanArr[0][j++];
				var tNumPeople =  tPlanArr[0][j++];
				var tMaleRate =  tPlanArr[0][j++];
				var tFemaleRate =  tPlanArr[0][j++];
				var tMinAge =  tPlanArr[0][j++];
				var tMaxAge =  tPlanArr[0][j++];
				var tAvgAge =  tPlanArr[0][j++];
				var tMinSalary =  tPlanArr[0][j++];
				var tMaxSalary =  tPlanArr[0][j++];
				var tAvgSalary =  tPlanArr[0][j++];
				var tSocialInsuRate =  tPlanArr[0][j++];//参加社保占比
				var tRetireRate =  tPlanArr[0][j++];//退休占比
				var tOtherDesc = tPlanArr[0][j++];//其他说明

				if (tNeedCheckFlag&&tPlanNeedCheck) {//再次到达当前节点,且该历史存在该方案
					
					j = 0;
					var tSysPlanCode1 = tOldPlanArr[0][j++];
					var tPlanCode1 = tOldPlanArr[0][j++];
					var tPlanDesc1 = tOldPlanArr[0][j++];
					var tPlanType1 =  tOldPlanArr[0][j++];
					var tPlanTypeName1 =  tOldPlanArr[0][j++];
					var tPremCalType1 =  tOldPlanArr[0][j++];
					var tPremCalTypeName1 =  tOldPlanArr[0][j++];
					var tPlanFlag1 = tOldPlanArr[0][j++];
					var tPlanFlagName1 = tOldPlanArr[0][j++];
					var tInsuredPeriod1 = tOldPlanArr[0][j++];//保险期间
					var tOccupTypeFlag1 =  tOldPlanArr[0][j++];
					var tOccupTypeFlagName1 =  tOldPlanArr[0][j++];
					var tMinOccupType1 =  tOldPlanArr[0][j++];
					var tMinOccupTypeName1 =  tOldPlanArr[0][j++];
					var tMaxOccupType1 =  tOldPlanArr[0][j++];
					var tMaxOccupTypeName1 =  tOldPlanArr[0][j++];
					var tOccupType1 =  tOldPlanArr[0][j++];
					var tOccupTypeName1 =  tOldPlanArr[0][j++];
					var tOccupMidType1 =  tOldPlanArr[0][j++];
					var tOccupMidTypeName1 =  tOldPlanArr[0][j++];
					var tOccupCode1 =  tOldPlanArr[0][j++];
					var tOccupCodeName1 =  tOldPlanArr[0][j++];
					var tNumPeople1 =  tOldPlanArr[0][j++];
					var tMaleRate1 =  tOldPlanArr[0][j++];
					var tFemaleRate1 =  tOldPlanArr[0][j++];
					var tMinAge1 =  tOldPlanArr[0][j++];
					var tMaxAge1 =  tOldPlanArr[0][j++];
					var tAvgAge1 =  tOldPlanArr[0][j++];
					var tMinSalary1 =  tOldPlanArr[0][j++];
					var tMaxSalary1 =  tOldPlanArr[0][j++];
					var tAvgSalary1 =  tOldPlanArr[0][j++];
					var tSocialInsuRate1 =  tOldPlanArr[0][j++];//参加社保占比
					var tRetireRate1 =  tOldPlanArr[0][j++];//退休占比
					var tOtherDesc1 = tOldPlanArr[0][j++];//其他说明

					tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
												+"<tr class=common>";
					if (tPlanDesc!=tPlanDesc1) {
						tInnerHTML1 += "	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>方案：</b>"+tPlanCode+"("+tPlanDesc+")</span></td>";
					} else {
						tInnerHTML1 += "	<td class=title colSpan=6><b>方案：</b>"+tPlanCode+"("+tPlanDesc+")</td>";
					}
					tInnerHTML1 += "</tr>";
					if (cTranProdType=="00" || cTranProdType=="02" || cTranProdType=="03") {//普通险种,账户型及个人险种
						
						if (tPlanType=="00" || tPlanType=="02") {
							
							if (tOccupTypeFlag!=tOccupTypeFlag1) {
								
								//职业处理
								if (tOccupTypeFlag=="1") {//单职业
									
									tInnerHTML1 +="<tr class=common>"
															+"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>职业类别：</b>"+tOccupTypeName+"</span>&nbsp;&nbsp;<b><span style=\"background-color:"+ tDifColor +"\">职业中分类：</b>"+tOccupMidTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>工种：</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
															+"</tr>";
								} else if (tOccupTypeFlag=="2") {//多职业
									
									tInnerHTML1 +="<tr class=common>"
															 +"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>职业类别：</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName +"</span>";
															 +"</tr>";
								}
							} else {
								
								//职业处理
								if (tOccupTypeFlag=="1") {//单职业
									
									tInnerHTML1 +="<tr class=common>";
									
									if (tOccupTypeName!=tOccupTypeName1) {
										
										tInnerHTML1 +="	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>职业类别：</b>"+tOccupTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>职业中分类：</b>"+tOccupMidTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>工种：</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
									} else {
										
										tInnerHTML1 +="	<td class=title colSpan=6><b>职业类别：</b>"+tOccupTypeName+"&nbsp;&nbsp;";
										if (tOccupMidTypeName!=tOccupMidTypeName1) {
											tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>职业中分类：</b>"+tOccupMidTypeName+"</span>&nbsp;&nbsp;<span style=\"background-color:"+ tDifColor +"\"><b>工种：</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
										} else {
										
											tInnerHTML1 +="<b>职业中分类：</b>"+tOccupMidTypeName+"&nbsp;&nbsp;<b>";
											if (tOccupCodeName!=tOccupCodeName1) {
												tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>工种：</b>"+tOccupCodeName+"</span>&nbsp;&nbsp;</td>";
											} else {
												tInnerHTML1 +="<b>工种：</b>"+tOccupCodeName+"&nbsp;&nbsp;</td>";
											}
										}
									}
									
									tInnerHTML1 +="</tr>";
								} else if (tOccupTypeFlag=="2") {//多职业
									
									if (tMinOccupTypeName!=tMinOccupTypeName1 || tMaxOccupTypeName!=tMaxOccupTypeName1) {
									
										tInnerHTML1 +="<tr class=common>"
																 +"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>职业类别：</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName +"</span>";
																 +"</tr>";
									} else {
									
										tInnerHTML1 +="<tr class=common>"
																 +"	<td class=title colSpan=6><b>职业类别：</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName;
																 +"</tr>";
									}
								}
							}
							
							tInnerHTML1 +="<tr class=common>"
													+"	<td class=title colSpan=6>";
							if (tNumPeople!=tNumPeople1) {//人数
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>人数：</b>"+ tNumPeople +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>人数：</b>"+ tNumPeople +"&nbsp;&nbsp;";
							}
							
							if (tMaleRate!=tMaleRate1 || tFemaleRate!=tFemaleRate1) {//男女比例
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>男女比例：</b>"+ tMaleRate +":"+ tFemaleRate +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>男女比例：</b>"+ tMaleRate +":"+ tFemaleRate +"&nbsp;&nbsp;";
							}
							
							if (tMinAge!=tMinAge1) {//最低年龄
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>最低年龄：</b>"+ tMinAge +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>最低年龄：</b>"+ tMinAge +"&nbsp;&nbsp;";
							}
							
							if (tMaxAge!=tMaxAge1) {//最高年龄
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>最高年龄：</b>"+ tMaxAge +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>最高年龄：</b>"+ tMaxAge +"&nbsp;&nbsp;";
							}
							
							if (tAvgAge!=tAvgAge1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>平均年龄：</b>"+ tAvgAge +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>平均年龄：</b>"+ tAvgAge +"&nbsp;&nbsp;";
							}
							
							tInnerHTML1 +="</td>"
													+" </tr>"
													+"<tr class=common>"
													+"	<td class=title colSpan=6>";
													
							if (tMinSalary!=tMinSalary1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>最低月薪：</b>"+ tMinSalary +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>最低月薪：</b>"+ tMinSalary +"&nbsp;&nbsp;";
							}
							
							if (tMaxSalary!=tMaxSalary1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>最高月薪：</b>"+ tMaxSalary +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>最高月薪：</b>"+ tMaxSalary +"&nbsp;&nbsp;";
							}
							
							if (tAvgSalary!=tAvgSalary1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>平均月薪：</b>"+ tAvgSalary +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>平均月薪：</b>"+ tAvgSalary +"&nbsp;&nbsp;";
							}
							
							if (tSocialInsuRate!=tSocialInsuRate1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>参加社保占比：</b>"+ tSocialInsuRate +"</span>&nbsp;&nbsp;";
							} else {
								tInnerHTML1 +="<b>参加社保占比：</b>"+ tSocialInsuRate +"&nbsp;&nbsp;";
							}
							
							if (tRetireRate!=tRetireRate1) {
								tInnerHTML1 +="<span style=\"background-color:"+ tDifColor +"\"><b>退休占比：</b>"+ tRetireRate +"&nbsp;&nbsp;</span>";
							} else {
								tInnerHTML1 +="<b>退休占比：</b>"+ tRetireRate +"&nbsp;&nbsp;";
							}
													
							tInnerHTML1 +="</td>"
													+"</tr>";
						}
					}
        	
        	if (tOtherDesc!=tOtherDesc1) {
        		tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6><span style=\"background-color:"+ tDifColor +"\"><b>其他说明：</b>"+tOtherDesc+"</span></td>"
											+"</tr>"
											+"</table>";
        	} else {
        		tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6><b>其他说明：</b>"+tOtherDesc+"</td>"
											+"</tr>"
											+"</table>";
        	}
				} else {
					
					var tStyle = "";
					if (tNeedCheckFlag&&!tPlanNeedCheck) {
						tStyle = "style=\"background-color: "+ tNewColor +"\"";
					}
					
					tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
												+"<tr class=common>"
												+"	<td class=title colSpan=6 "+ tStyle +"><b>方案：</b>"+tPlanCode+"("+tPlanDesc+")</td>"
												+"</tr>";
					if (cTranProdType=="00" || cTranProdType=="02" || cTranProdType=="03") {//普通险种,账户型及个人险种
						
						if (tPlanType=="00" || tPlanType=="02") {
							
							//职业处理
							if (tOccupTypeFlag=="1") {//单职业
								
								tInnerHTML1 +="<tr class=common>"
														+"	<td class=title colSpan=6 "+ tStyle +"><b>职业类别：</b>"+tOccupTypeName+"&nbsp;&nbsp;<b>职业中分类：</b>"+tOccupMidTypeName+"&nbsp;&nbsp;<b>工种：</b>"+tOccupCodeName+"&nbsp;&nbsp;</td>"
														+"</tr>";
							} else if (tOccupTypeFlag=="2") {//多职业
								
								tInnerHTML1 +="<tr class=common>"
														 +"	<td class=title colSpan=6 "+ tStyle +"><b>职业类别：</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName;
														 +"</tr>";
							}
							tInnerHTML1 +="<tr class=common>"
													+"	<td class=title colSpan=6 "+ tStyle +"><b>人数：</b>"+ tNumPeople +"&nbsp;&nbsp;<b>男女比例：</b>"+ tMaleRate +":"+ tFemaleRate +"&nbsp;&nbsp;<b>最低年龄：</b>"+ tMinAge +"&nbsp;&nbsp;<b>最高年龄：</b>"+ tMaxAge +"&nbsp;&nbsp;<b>平均年龄：</b>"+ tAvgAge +"&nbsp;&nbsp;</td>"
													+"</tr>"
													+"<tr class=common>"
													+"	<td class=title colSpan=6 "+ tStyle +"><b>最低月薪：</b>"+ tMinSalary +"&nbsp;&nbsp;<b>最高月薪：</b>"+ tMaxSalary +"&nbsp;&nbsp;<b>平均月薪：</b>"+ tAvgSalary +"&nbsp;&nbsp;<b>参加社保占比：</b>"+ tSocialInsuRate +"&nbsp;&nbsp;<b>退休占比：</b>"+ tRetireRate +"&nbsp;&nbsp;</td>"
													+"</tr>";
						}
					}
        	
					tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6 "+ tStyle +"><b>其他说明：</b>"+tOtherDesc+"</td>"
											+"</tr>"
											+"</table>";
				}
					
				//查询出方案明细信息
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
				tSQLInfo.setSqlId("LSQuotAllDetailSql7");
				tSQLInfo.addSubPara(tQuotNo);
				tSQLInfo.addSubPara(tQuotBatNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);
				
				var tPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				
				if (tPlanDetailArr==null) {
				
				} else {
				
					tInnerHTML1 +="<div>"	
											+"	<table class=common>"
											+"	 <tr class=common>"
											+"	 	<td text-align: left colSpan=1>"
											+"	 		<table class=muline ALIGN=left border=0 CELLSPACING=0 CELLPADDING=0>"
											+"	 			<tr>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='序号' style='width: "+ tWidthArr[0] +"' readonly></td> "
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='险种名称编码' style='width: "+ tWidthArr[1] +"' readonly></td>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='险种名称' style='width: "+ tWidthArr[2] +"' readonly></td>"
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='责任编码' style='width: "+ tWidthArr[3] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='责任名称' style='width: "+ tWidthArr[4] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='保险责任描述' style='width: "+ tWidthArr[5] +"' readonly></td> ";
											
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//普通险种,建工险及个人险种
					
						tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulinetitle value='期望保费类型编码' style='width: "+ tWidthArr[6] +"' readonly></td>"
												+"	<td class=mulinetitle><input class=mulinetitle value='期望保费类型' style='width: "+ tWidthArr[7] +"' readonly></td>";
					
						if (cActivityID=="0800100001") {//询价录入
						
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='参考保费' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='期望保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率①' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='核保保费(中)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='核保保费(分)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='建议保费' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率③' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else if (cActivityID=="0800100002") {//中核
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='参考保费' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='期望保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率①' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='核保保费(中)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='核保保费(分)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='建议保费' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率③' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else if (cActivityID=="0800100003") {//分核
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='参考保费' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='期望保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率①' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='核保保费(中)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='核保保费(分)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='建议保费' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率③' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else if (cActivityID=="0800100004") {//总核
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='参考保费' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='期望保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率①' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='核保保费(中)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='核保保费(分)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='建议保费' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率③' style='width: "+ tWidthArr[16] +"' readonly></td>";
						} else {
							
							tInnerHTML1 +="		<td class=mulinetitle><input class=mulinetitle value='参考保费' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='期望保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率①' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='核保保费(中)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='核保保费(分)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='建议保费' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='折扣率③' style='width: "+ tWidthArr[16] +"' readonly></td>";
						}
						
						tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='初始保费' style='width: "+ tWidthArr[17] +"' readonly></td>"
												+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望收益率' style='width: "+ tWidthArr[18] +"' readonly></td>"
												+"	</tr>";
					} else if (cTranProdType=="02") {//账户型
						
						tInnerHTML1 +="			<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='参考保费' style='width: "+ tWidthArr[8] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率①' style='width: "+ tWidthArr[10] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='核保保费(中)' style='width: "+ tWidthArr[11] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[12] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='核保保费(分)' style='width: "+ tWidthArr[13] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率②' style='width: "+ tWidthArr[14] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='建议保费' style='width: "+ tWidthArr[15] +"' readonly></td>"
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='折扣率③' style='width: "+ tWidthArr[16] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='初始保费' style='width: "+ tWidthArr[17] +"' readonly></td>"
													+"		<td class=mulinetitle><input class=mulinetitle value='期望收益率' style='width: "+ tWidthArr[18] +"' readonly></td>"
													+"	</tr>";
					}

					for (var k=0; k<tPlanDetailArr.length; k++) {
						
						var k1 = 0;
						var tRiskCode = tPlanDetailArr[k][k1++];
						var tRiskName = tPlanDetailArr[k][k1++];
						var tDutyCode = tPlanDetailArr[k][k1++];
						var tDutyName = tPlanDetailArr[k][k1++];
						var tAmntType = tPlanDetailArr[k][k1++];
						var tAmntTypeName = tPlanDetailArr[k][k1++];
						var tFixedAmnt = tPlanDetailArr[k][k1++];
						var tSalaryMult = tPlanDetailArr[k][k1++];
						var tMinAmnt = tPlanDetailArr[k][k1++];
						var tMaxAmnt = tPlanDetailArr[k][k1++];
						var tExceptPremType = tPlanDetailArr[k][k1++];
						var tExceptPremTypeName = tPlanDetailArr[k][k1++];
						var tStandPrem = tPlanDetailArr[k][k1++];
						var tExceptPrem = tPlanDetailArr[k][k1++];
						var tDecuRate1 = tPlanDetailArr[k][k1++];
						var tSubUWValue = tPlanDetailArr[k][k1++];
						var tDecuRate20 = tPlanDetailArr[k][k1++];
						var tBranchUWValue = tPlanDetailArr[k][k1++];
						var tDecuRate21 = tPlanDetailArr[k][k1++];
						var tUWValue = tPlanDetailArr[k][k1++];
						var tDecuRate3 = tPlanDetailArr[k][k1++];
						var tInitPrem = tPlanDetailArr[k][k1++];
						var tExceptYield = tPlanDetailArr[k][k1++];
						var tRelaShareFlag = tPlanDetailArr[k][k1++];
						
						var tDetailDesc = "";
						
						var tOldRiskCode = "";
						var tOldRiskName = "";
						var tOldDutyCode = "";
						var tOldDutyName = "";
						var tOldAmntType = "";
						var tOldAmntTypeName = "";
						var tOldFixedAmnt = "";
						var tOldSalaryMult = "";
						var tOldMinAmnt = "";
						var tOldMaxAmnt = "";
						var tOldExceptPremType = "";
						var tOldExceptPremTypeName = "";
						var tOldStandPrem = "";
						var tOldExceptPrem = "";
						var tOldDecuRate1 = "";
						var tOldSubUWValue = "";
						var tOldDecuRate20 = "";
						var tOldBranchUWValue = "";
						var tOldDecuRate21 = "";
						var tOldUWValue = "";
						var tOldDecuRate3 = "";
						var tOldInitPrem = "";
						var tOldExceptYield = "";
						var tOldRelaShareFlag = "";
						
						var tOldDetailDesc = "";

						if (cTranProdType=="02") {//账户型处理
							
							if (tExceptYield==null || tExceptYield=="") {
								tDetailDesc =  "预期收益率:无;";
							} else {
								tDetailDesc += "预期收益率:"+ tExceptYield +";";
							}
						} else {
							
							tDetailDesc += "保额类型:"+ tAmntTypeName +";";
							if (tAmntType=="01") {
								tDetailDesc += "固定保额(元):"+ tFixedAmnt +";";
							} else if (tAmntType=="02") {
								tDetailDesc += "月薪倍数:"+ tSalaryMult +";";
							} else if (tAmntType=="03") {
								tDetailDesc += "月薪倍数:"+ tSalaryMult +";最低保额(元):"+ tMinAmnt +";";
							} else if (tAmntType=="04") {
								tDetailDesc += "最低保额(元):"+ tMinAmnt +";最高保额(元):"+ tMaxAmnt +";";
							}
							
						}
						
						var tPlanDetailNeedCheck = false;
						var tOldPlanDetailArr;
						if (tNeedCheckFlag&&tPlanNeedCheck) {//需要校验
						
							//找到该险种责任历史记录
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql14");
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							tSQLInfo.addSubPara(tTraceNo);
							
							tOldPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							if (tOldPlanDetailArr==null) {
							
							} else {
								
								tPlanDetailNeedCheck = true;
								k1 = 0;
								tOldRiskCode = tOldPlanDetailArr[0][k1++];
								tOldRiskName = tOldPlanDetailArr[0][k1++];
								tOldDutyCode = tOldPlanDetailArr[0][k1++];
								tOldDutyName = tOldPlanDetailArr[0][k1++];
								tOldAmntType = tOldPlanDetailArr[0][k1++];
								tOldAmntTypeName = tOldPlanDetailArr[0][k1++];
								tOldFixedAmnt = tOldPlanDetailArr[0][k1++];
								tOldSalaryMult = tOldPlanDetailArr[0][k1++];
								tOldMinAmnt = tOldPlanDetailArr[0][k1++];
								tOldMaxAmnt = tOldPlanDetailArr[0][k1++];
								tOldExceptPremType = tOldPlanDetailArr[0][k1++];
								tOldExceptPremTypeName = tOldPlanDetailArr[0][k1++];
								tOldStandPrem = tOldPlanDetailArr[0][k1++];
								tOldExceptPrem = tOldPlanDetailArr[0][k1++];
								tOldDecuRate1 = tOldPlanDetailArr[0][k1++];
								tOldSubUWValue = tOldPlanDetailArr[0][k1++];
								tOldDecuRate20 = tOldPlanDetailArr[0][k1++];
								tOldBranchUWValue = tOldPlanDetailArr[0][k1++];
								tOldDecuRate21 = tOldPlanDetailArr[0][k1++];
								tOldUWValue = tOldPlanDetailArr[0][k1++];
								tOldDecuRate3 = tOldPlanDetailArr[0][k1++];
								tOldInitPrem = tOldPlanDetailArr[0][k1++];
								tOldExceptYield = tOldPlanDetailArr[0][k1++];
								tOldRelaShareFlag = tOldPlanDetailArr[0][k1++];
								
								if (cTranProdType=="02") {//账户型处理
									
									if (tOldExceptYield==null || tOldExceptYield=="") {
										tOldDetailDesc =  "预期收益率:无;";
									} else {
										tOldDetailDesc += "预期收益率:"+ tOldExceptYield +";";
									}
									
								} else {
									
									tOldDetailDesc += "保额类型:"+ tOldAmntTypeName +";";
									if (tAmntType=="01") {
										tOldDetailDesc += "固定保额(元):"+ tOldFixedAmnt +";";
									} else if (tAmntType=="02") {
										tOldDetailDesc += "月薪倍数:"+ tOldSalaryMult +";";
									} else if (tAmntType=="03") {
										tOldDetailDesc += "月薪倍数:"+ tOldSalaryMult +";最低保额(元):"+ tOldMinAmnt +";";
									} else if (tAmntType=="04") {
										tOldDetailDesc += "最低保额(元):"+ tOldMinAmnt +";最高保额(元):"+ tOldMaxAmnt +";";
									}
								}
							}
						}
						
						//保险责任描述 添加 主附共用配置信息
						var tRelaSub = "";
						if (tRelaShareFlag=="1") {
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql16");
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tRelaSubArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							
							tRelaSub = "主被保险人保额占比:"+ tRelaSubArr[0][0]+";附属被保人保额占比:"+tRelaSubArr[0][1]+";";
						}
						
						//保险责任描述 添加 主附共用配置信息  Old
						var tOldRelaSub = "";
						if (tOldRelaShareFlag=="1") {
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql18");
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							tSQLInfo.addSubPara(tTraceNo);
							
							var tOldRelaSubArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							
							tOldRelaSub = "主被保险人保额占比:"+ tOldRelaSubArr[0][0]+";附属被保人保额占比:"+tOldRelaSubArr[0][1]+";";
						}
						
						//获取险种责任动态域数据
						var tDutyArr = getDutyElementArr(tRiskCode, tDutyCode);
						if (tDutyArr==null) {//无责任动态域
							
							tDetailDesc += tRelaSub;
							tOldDetailDesc += tOldRelaSub;
						} else {
							
							var tSQLElement = getDutySQLElement(tDutyArr); 
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotSql");
							tSQLInfo.setSqlId("LSQuotSql19");
							tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tDutyDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

							tDetailDesc += getDutyDetailSub(tDutyArr, tDutyDetailSub,0);
							tDetailDesc += tRelaSub;
							
							//主附共用配置动态因子
							if (tRelaShareFlag=="1") {
								
								tSQLInfo = new SqlClass();
								tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
								tSQLInfo.setSqlId("LSQuotAllDetailSql17");
								tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
								tSQLInfo.addSubPara(tQuotNo);
								tSQLInfo.addSubPara(tQuotBatNo);
								tSQLInfo.addSubPara(tSysPlanCode);
								tSQLInfo.addSubPara(tPlanCode);
								tSQLInfo.addSubPara(tRiskCode);
								tSQLInfo.addSubPara(tDutyCode);
								
								var tRelaSubDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								tDetailDesc += getDutyDetailSub(tDutyArr, tRelaSubDetailSub,1);
							}
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql15");
							tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
							tSQLInfo.addSubPara(tQuotNo);
							tSQLInfo.addSubPara(tQuotBatNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							tSQLInfo.addSubPara(tTraceNo);
							
							var tOldDutyDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

							tOldDetailDesc += getDutyDetailSub(tDutyArr, tDutyDetailSub,0);
							
							//主附共用配置动态因子 Old
							if (tOldRelaShareFlag=="1") {
								
								tSQLInfo = new SqlClass();
								tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
								tSQLInfo.setSqlId("LSQuotAllDetailSql19");
								tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
								tSQLInfo.addSubPara(tQuotNo);
								tSQLInfo.addSubPara(tQuotBatNo);
								tSQLInfo.addSubPara(tSysPlanCode);
								tSQLInfo.addSubPara(tPlanCode);
								tSQLInfo.addSubPara(tRiskCode);
								tSQLInfo.addSubPara(tDutyCode);
								tSQLInfo.addSubPara(tTraceNo);
								
								var tOldRelaSubDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								tOldDetailDesc += getDutyDetailSub(tDutyArr, tOldRelaSubDetailSub,1);
							}
							
						}
						
						if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
							tInnerHTML1		+=" <tr>"
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' value='"+ (k+1) +"' readonly></td>"// value='序号' 
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"' name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='险种名称编码'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"' name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='险种名称'
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"' name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='责任编码'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"' name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> ";// value='责任名称'
														
							if (tDetailDesc!=tOldDetailDesc) {// value='保险责任描述'
								tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' style=\"background-color:"+ tDifColor +"\" name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> ";
							} else {
								tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> ";
							}
						} else {
							
							var tStyle = "";
							if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
							
								tStyle = "style=\"background-color:  "+ tNewColor +"\"";
							}
							tInnerHTML1		+=" <tr>"
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' "+ tStyle +" value='"+ (k+1) +"' readonly></td>"// value='序号' 
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"'  "+ tStyle +" name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='险种名称编码'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"'  "+ tStyle +" name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='险种名称'
														+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"'  "+ tStyle +" name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='责任编码'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"'  "+ tStyle +" name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> "// value='责任名称'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"'  "+ tStyle +" name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> ";// value='保险责任描述'							
						}

						if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//普通险种,建工险及个人险种
						
							if (cActivityID=="0800100001") {//询价录入
								
								tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
													
								tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='参考保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='折扣率①'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='核保保费(中)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='核保保费(分)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='建议保费'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'		
							} else if (cActivityID=="0800100002") {//中核
								
								if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
									if (tExceptPremType!=tOldExceptPremType) {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									} else {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									}
									
									if (tStandPrem!=tOldStandPrem) {// value='参考保费'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";// value='参考保费'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";
									}
									
									if (tExceptPrem!=tOldExceptPrem) {// value='期望保费'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";// value='期望保费'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";
									}
									
									if (tDecuRate1!=tOldDecuRate1) {// value='折扣率①'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";// value='折扣率①'
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";
									}
									
									if (tSubUWValue!=tSubUWValue) {// value='核保保费(中)'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[11] +"' style=\"background-color:"+ tDifColor +"\" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'></td>";
									}
		
									if (tDecuRate20!=tOldDecuRate20) {// value='折扣率②'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									}
	
									tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='核保保费(分)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='建议保费'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'
									
								} else {
									
									var tStyle = "";
									if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
									
										tStyle = "style=\"background-color:  "+ tNewColor +"\"";
									}
									
									tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"'  "+ tStyle +" name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"'  "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									
									tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='参考保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"'  "+ tStyle +" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' "+ tStyle +" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='折扣率①'
														+"		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[11] +"' "+ tStyle +" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'></td>"// value='核保保费(中)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' "+ tStyle +" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' "+ tStyle +" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='核保保费(分)'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' "+ tStyle +" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' "+ tStyle +" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='建议保费'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' "+ tStyle +" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'
								}	
							} else if (cActivityID=="0800100003") {//分核
								
								if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
									if (tExceptPremType!=tOldExceptPremType) {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									} else {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									}
									
									if (tStandPrem!=tOldStandPrem) {// value='参考保费'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";// value='参考保费'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";
									}
									
									if (tExceptPrem!=tOldExceptPrem) {// value='期望保费'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";// value='期望保费'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";
									}
									
									if (tDecuRate1!=tOldDecuRate1) {// value='折扣率①'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";// value='折扣率①'
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";
									}
									
									if (tSubUWValue!=tSubUWValue) {// value='核保保费(中)'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' style=\"background-color:"+ tDifColor +"\" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									}
		
									if (tDecuRate20!=tOldDecuRate20) {// value='折扣率②'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									}
									
									if (tBranchUWValue!=tOldBranchUWValue) {// value='核保保费(分)'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[13] +"' style=\"background-color:"+ tDifColor +"\" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"'></td>";
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' ></td>";
									}
									
									if (tDecuRate21!=tOldDecuRate21) {// value='折扣率②'
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									} else {
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									}

									tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='建议保费'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'
								} else {
									
									var tStyle = "";
									
									if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
									
										tStyle = "style=\"background-color:  "+ tNewColor +"\"";
									}
									
									tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"'  "+ tStyle +" name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"'  "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									
									tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='参考保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"'  "+ tStyle +" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' "+ tStyle +" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='折扣率①'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' "+ tStyle +" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='核保保费(中)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' "+ tStyle +" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[13] +"' "+ tStyle +" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' ></td>"// value='核保保费(分)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' "+ tStyle +" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' "+ tStyle +" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='建议保费'
														+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' "+ tStyle +" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'
								}
							} else if (cActivityID=="0800100004") {//总核
								
								if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
									if (tExceptPremType!=tOldExceptPremType) {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									} else {
										
										tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
														+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									}
									
									if (tStandPrem!=tOldStandPrem) {// value='参考保费'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";// value='参考保费'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>";
									}
									
									if (tExceptPrem!=tOldExceptPrem) {// value='期望保费'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' style=\"background-color:"+ tDifColor +"\" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";// value='期望保费'
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>";
									}
									
									if (tDecuRate1!=tOldDecuRate1) {// value='折扣率①'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";// value='折扣率①'
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>";
									}
									
									if (tSubUWValue!=tSubUWValue) {// value='核保保费(中)'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' style=\"background-color:"+ tDifColor +"\" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>";
									}
		
									if (tDecuRate20!=tOldDecuRate20) {// value='折扣率②'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>";
									}
									
									if (tBranchUWValue!=tOldBranchUWValue) {// value='核保保费(分)'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' style=\"background-color:"+ tDifColor +"\" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>";
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>";
									}
									
									if (tDecuRate21!=tOldDecuRate21) {// value='折扣率②'
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									} else {
										tInnerHTML1 +=" <td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>";
									}
									
									if (tUWValue!=tOldUWValue) {// value='建议保费'
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[15] +"' style=\"background-color:"+ tDifColor +"\" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>";
									} else {
										tInnerHTML1 +="		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>";
									}
									
									if (tDecuRate3!=tOldDecuRate3) {// value='折扣率③'
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' style=\"background-color:"+ tDifColor +"\" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";
									} else {
										tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";
									}
								} else {
									
									var tStyle = "";
									if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
									
										tStyle = "style=\"background-color:  "+ tNewColor +"\"";
									}
									
									tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"'  "+ tStyle +" name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"'  "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
									
									tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' "+ tStyle +" name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='参考保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"'  "+ tStyle +" name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' "+ tStyle +" name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='折扣率①'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' "+ tStyle +" name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"'readonly></td>"// value='核保保费(中)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' "+ tStyle +" name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' "+ tStyle +" name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='核保保费(分)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' "+ tStyle +" name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle><input class=mulinput style='width: "+ tWidthArr[15] +"' "+ tStyle +" name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>"// value='建议保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' "+ tStyle +" name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'
								}
							} else {
								
								tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
													+"	<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>";// value='期望保费类型'
								
								tInnerHTML1 +="		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='参考保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='折扣率①'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='核保保费(中)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='核保保费(分)'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='折扣率②'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"'></td>"// value='建议保费'
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'	
							}
							tInnerHTML1 +="		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[17] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='初始保费'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[18] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='期望收益率'
													+"	</tr>";
							
						} else if (cTranProdType=="02") {//账户型
							
							tInnerHTML1 +="	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
													+"	<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='期望保费类型'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tStandPrem +"' readonly></td>"// value='参考保费'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=DecuRate1"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate1 +"' readonly></td>"// value='折扣率①'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[11] +"' name=SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tSubUWValue +"' readonly></td>"// value='核保保费(中)'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[12] +"' name=DecuRate20"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate20 +"' readonly></td>"// value='折扣率②'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[13] +"' name=BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tBranchUWValue +"' readonly></td>"// value='核保保费(分)'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[14] +"' name=DecuRate21"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate21 +"' readonly></td>"// value='折扣率②'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[15] +"' name=UWValue"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tUWValue +"' readonly></td>"// value='建议保费'
													+"		<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[16] +"' name=DecuRate3"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDecuRate3 +"' readonly></td>";// value='折扣率③'
							if (tNeedCheckFlag&&tPlanNeedCheck&&tPlanDetailNeedCheck) {
								
								if (tInitPrem!=tOldInitPrem) {// value='初始保费'
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[17] +"' style=\"background-color:"+ tDifColor +"\" name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>";
								} else {
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[17] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>";
								}
								
								if (tExceptYield!=tOldExceptYield) {// value='期望收益率'
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[18] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>";
								} else {
									tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[18] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>";// value='期望收益率'
								}
							} else {
								
								var tStyle = "";
								if ((tNeedCheckFlag&&tPlanNeedCheck&&!tPlanDetailNeedCheck) || (tNeedCheckFlag&&!tPlanNeedCheck)) {
								
									tStyle = "style=\"background-color:  "+ tNewColor +"\"";
								}
								
								tInnerHTML1 +="<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[17] +"' "+ tStyle +" name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"
														+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[18] +"' "+ tStyle +" name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>";// value='期望收益率'
							}
							
							tInnerHTML1 +="</tr>";
						}							
					}
					tInnerHTML1 += "				</table>"
												+"			</td>"
												+"		</tr>"
												+"	</table>";
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {
						
						//modify by ZhangC 20150324 处理公共保额为人均保费时，保存按钮的展示
						if (cActivityID=="0800100002" || cActivityID=="0800100003") {
							
							if (tPlanType == "01") {
								
								tSQLInfo = new SqlClass();
								tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
								tSQLInfo.setSqlId("LSQuotAllDetailSql22");
								tSQLInfo.addSubPara(tQuotNo);
								tSQLInfo.addSubPara(tQuotBatNo);
								tSQLInfo.addSubPara(tSysPlanCode);
								tSQLInfo.addSubPara(tPlanCode);
								
								var tPremCalWayArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								var tPremCalWay = tPremCalWayArr[0][0];
								if (tPremCalWay=="0") {//按公共保费收费
									tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"保  存\" onclick=\"saveAllDetail('"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"', '"+ tPlanDetailArr.length +"')\"></div>";
								} 
							} else {
								tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"保  存\" onclick=\"saveAllDetail('"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"', '"+ tPlanDetailArr.length +"')\"></div>";
							}
						} else if (cActivityID=="0800100004") {
						
							tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"保  存\" onclick=\"saveAllDetail('"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"', '"+ tPlanDetailArr.length +"')\"></div>";
						}
						
					}
					tInnerHTML1 += "</div>";
				}
			}
		}
	}
	return tInnerHTML1;
}


/**
 * 报价方案明细
 */
function pubQuotPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cShow,cPrint,cQuotQuery) {
	
	var tWidthArr = new Array();
	tWidthArr[0] = 30;
	tWidthArr[1] = '';
	tWidthArr[2] = 150;
	tWidthArr[3] = '';
	tWidthArr[4] = 130;
	tWidthArr[5] = 200;
	tWidthArr[6] = '';
	tWidthArr[7] = 80;
	tWidthArr[8] = 150;
	tWidthArr[9] = 80;
	tWidthArr[10] = 80;

	var tInnerHTML1 = "";
	for (var i=0; i<tPlanShowRows; i++) {
		if ((tStartNum+i-1)>=RowNum) {
			//相等时,表示总记录数取尽
		} else {
			
			var tOfferListNo = cArr[tStartNum+i-1][0];
			var tSysPlanCode = cArr[tStartNum+i-1][1];
			var tPlanCode = cArr[tStartNum+i-1][2];
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
			tSQLInfo.setSqlId("LSQuotAllDetailSql10");
			tSQLInfo.addSubPara(tOfferListNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);

			var tPlanArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tPlanArr!=null) {
				var j = 0;
				var tSysPlanCode = tPlanArr[0][j++];
				var tPlanCode = tPlanArr[0][j++];
				var tPlanDesc = tPlanArr[0][j++];
				var tPlanType =  tPlanArr[0][j++];
				var tPlanTypeName =  tPlanArr[0][j++];
				var tPremCalType =  tPlanArr[0][j++];
				var tPremCalTypeName =  tPlanArr[0][j++];
				var tPlanFlag = tPlanArr[0][j++];
				var tPlanFlagName = tPlanArr[0][j++];
				var tOccupTypeFlag =  tPlanArr[0][j++];
				var tOccupTypeFlagName =  tPlanArr[0][j++];
				var tMinOccupType =  tPlanArr[0][j++];
				var tMinOccupTypeName =  tPlanArr[0][j++];
				var tMaxOccupType =  tPlanArr[0][j++];
				var tMaxOccupTypeName =  tPlanArr[0][j++];
				var tOccupType =  tPlanArr[0][j++];
				var tOccupTypeName =  tPlanArr[0][j++];
				var tOccupMidType =  tPlanArr[0][j++];
				var tOccupMidTypeName =  tPlanArr[0][j++];
				var tOccupCode =  tPlanArr[0][j++];
				var tOccupCodeName =  tPlanArr[0][j++];
				var tNumPeople =  tPlanArr[0][j++];
				var tMaleRate =  tPlanArr[0][j++];
				var tFemaleRate =  tPlanArr[0][j++];
				var tMinAge =  tPlanArr[0][j++];
				var tMaxAge =  tPlanArr[0][j++];
				var tAvgAge =  tPlanArr[0][j++];
				var tMinSalary =  tPlanArr[0][j++];
				var tMaxSalary =  tPlanArr[0][j++];
				var tAvgSalary =  tPlanArr[0][j++];
				var tSocialInsuRate =  tPlanArr[0][j++];//参加社保占比
				var tRetireRate =  tPlanArr[0][j++];//退休占比
				var tOtherDesc = tPlanArr[0][j++];//其他说明
				var tOfferListNo = tPlanArr[0][j++];//报价单号

				tInnerHTML1 += "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>"
					+"<tr class=common>"
					+"	<td class=title colSpan=6><b>方案：</b>"+tPlanCode+"("+tPlanDesc+")</td>"
					+"</tr>";
				if (cTranProdType=="00" || cTranProdType=="02" || cTranProdType=="03") {//普通险种,账户型及个人险种
				
					if (tPlanType=="00" || tPlanType=="02") {
					
					//职业处理
					if (tOccupTypeFlag=="1") {//单职业
						
						tInnerHTML1 +="<tr class=common>"
												+"	<td class=title colSpan=6><b>职业类别：</b>"+tOccupTypeName+"&nbsp;&nbsp;<b>职业中分类：</b>"+tOccupMidTypeName+"&nbsp;&nbsp;<b>工种：</b>"+tOccupCodeName+"&nbsp;&nbsp;</td>"
												+"</tr>";
					} else if (tOccupTypeFlag=="2") {//多职业
						
						tInnerHTML1 +="<tr class=common>"
												 +"	<td class=title colSpan=6><b>职业类别：</b>"+ tMinOccupTypeName +"-"+ tMaxOccupTypeName;
												 +"</tr>";
					}
					tInnerHTML1 +="<tr class=common>"
											+"	<td class=title colSpan=6><b>人数：</b>"+ tNumPeople +"&nbsp;&nbsp;<b>男女比例：</b>"+ tMaleRate +":"+ tFemaleRate +"&nbsp;&nbsp;<b>最低年龄：</b>"+ tMinAge +"&nbsp;&nbsp;<b>最高年龄：</b>"+ tMaxAge +"&nbsp;&nbsp;<b>平均年龄：</b>"+ tAvgAge +"&nbsp;&nbsp;</td>"
											+"</tr>"
											+"<tr class=common>"
											+"	<td class=title colSpan=6><b>最低月薪：</b>"+ tMinSalary +"&nbsp;&nbsp;<b>最高月薪：</b>"+ tMaxSalary +"&nbsp;&nbsp;<b>平均月薪：</b>"+ tAvgSalary +"&nbsp;&nbsp;<b>参加社保占比：</b>"+ tSocialInsuRate +"&nbsp;&nbsp;<b>退休占比：</b>"+ tRetireRate +"&nbsp;&nbsp;</td>"
											+"</tr>";
					}
				}
				
				tInnerHTML1 +="<tr class=common>"
								+"	<td class=title colSpan=6><b>其他说明：</b>"+tOtherDesc+"</td>"
								+"</tr>"
								+"</table>";

				
				//查询出方案明细信息
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
				tSQLInfo.setSqlId("LSQuotAllDetailSql11");
				tSQLInfo.addSubPara(tOfferListNo);
				tSQLInfo.addSubPara(tSysPlanCode);
				tSQLInfo.addSubPara(tPlanCode);
				
				var tPlanDetailArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				
				if (tPlanDetailArr==null) {
				
				} else {
					
					tInnerHTML1 +="<div>"	
											+"	<table class=common>"
											+"	 <tr class=common>"
											+"	 	<td text-align: left colSpan=1>"
											+"	 		<table class=muline ALIGN=left border=0 CELLSPACING=0 CELLPADDING=0>"
											+"	 			<tr>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='序号' style='width: "+ tWidthArr[0] +"' readonly></td> "
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='险种名称编码' style='width: "+ tWidthArr[1] +"' readonly></td>"
											+"	 				<td class=mulinetitle><input class=mulinetitle value='险种名称' style='width: "+ tWidthArr[2] +"' readonly></td>"
											+"	 				<td class=mulinetitle style='display: none'><input class=mulinetitle value='责任编码' style='width: "+ tWidthArr[3] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='责任名称' style='width: "+ tWidthArr[4] +"' readonly></td> "
											+"	 				<td class=mulinetitle><input class=mulinetitle value='保险责任描述' style='width: "+ tWidthArr[5] +"' readonly></td> ";
											
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//普通险种,建工险及个人险种
					
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费类型编码' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='期望保费类型' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='期望保费(元)/期望费率/费率折扣' style='width: "+ tWidthArr[8] +"' readonly></td>";
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='初始保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望收益率' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";
					} else if (cTranProdType=="02") {//账户型
						
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费类型编码' style='width: "+ tWidthArr[6] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费类型' style='width: "+ tWidthArr[7] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulinetitle value='期望保费(元)/期望费率/费率折扣' style='width: "+ tWidthArr[8] +"' readonly></td>";
			
						tInnerHTML1 +="	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='初始保费' style='width: "+ tWidthArr[9] +"' readonly></td>"
									+"	<td class=mulinetitle style=\"display: ''\"><input class=mulinetitle value='期望收益率' style='width: "+ tWidthArr[10] +"' readonly></td>"
									+"	</tr>";					}
					
					for (var k=0; k<tPlanDetailArr.length; k++) {
						
						var k1 = 0;
						var tRiskCode = tPlanDetailArr[k][k1++];
						var tRiskName = tPlanDetailArr[k][k1++];
						var tDutyCode = tPlanDetailArr[k][k1++];
						var tDutyName = tPlanDetailArr[k][k1++];
						var tAmntType = tPlanDetailArr[k][k1++];
						var tAmntTypeName = tPlanDetailArr[k][k1++];
						var tFixedAmnt = tPlanDetailArr[k][k1++];
						var tSalaryMult = tPlanDetailArr[k][k1++];
						var tMinAmnt = tPlanDetailArr[k][k1++];
						var tMaxAmnt = tPlanDetailArr[k][k1++];
						var tExceptPremType = tPlanDetailArr[k][k1++];
						var tExceptPremTypeName = tPlanDetailArr[k][k1++];
						var tExceptPrem = tPlanDetailArr[k][k1++];
						var tInitPrem = tPlanDetailArr[k][k1++];
						var tExceptYield = tPlanDetailArr[k][k1++];
						var tRelaShareFlag = tPlanDetailArr[k][k1++];
						
						var tDetailDesc = "";
						
						if (cTranProdType=="02") {//账户型处理
							
							if (tExceptYield==null || tExceptYield=="") {
								tDetailDesc =  "预期收益率:无;";
							} else {
								tDetailDesc += "预期收益率:"+ tExceptYield +";";
							}
							
						} else {
							
							tDetailDesc += "保额类型:"+ tAmntTypeName +";";
							if (tAmntType=="01") {
								tDetailDesc += "固定保额(元):"+ tFixedAmnt +";";
							} else if (tAmntType=="02") {
								tDetailDesc += "月薪倍数:"+ tSalaryMult +";";
							} else if (tAmntType=="03") {
								tDetailDesc += "月薪倍数:"+ tSalaryMult +";最低保额(元):"+ tMinAmnt +";";
							} else if (tAmntType=="04") {
								tDetailDesc += "最低保额(元):"+ tMinAmnt +";最高保额(元):"+ tMaxAmnt +";";
							}
						}
						
						var tRelaSub = "";
						if (tRelaShareFlag=="1") {
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
							tSQLInfo.setSqlId("LSQuotAllDetailSql20");
							tSQLInfo.addSubPara(tOfferListNo);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tRelaSubArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
							
							tRelaSub = "主被保险人保额占比:"+ tRelaSubArr[0][0]+";附属被保人保额占比:"+tRelaSubArr[0][1]+";";
						}
						
						
						//获取险种责任动态域数据
						var tDutyArr = getDutyElementArr(tRiskCode, tDutyCode);
						if (tDutyArr==null) {//无责任动态域
							tDetailDesc += tRelaSub;
						} else {
							
							var tSQLElement = getDutySQLElement(tDutyArr); 
							
							tSQLInfo = new SqlClass();
							tSQLInfo.setResourceName("g_quot.LSQuotSql");
							tSQLInfo.setSqlId("LSQuotSql42");
							tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
							tSQLInfo.addSubPara(tOfferListNo);
							tSQLInfo.addSubPara(tSysPlanCode);
							tSQLInfo.addSubPara(tPlanCode);
							tSQLInfo.addSubPara(tRiskCode);
							tSQLInfo.addSubPara(tDutyCode);
							
							var tDutyDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

							tDetailDesc += getDutyDetailSub(tDutyArr, tDutyDetailSub,0);
							tDetailDesc += tRelaSub;
							
							//主附共用配置动态因子
							if (tRelaShareFlag=="1") {
								if (tPlanCode.substring(0,1) == "G" ) {//普通的方案
									
									tSQLInfo = new SqlClass();
									tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
									tSQLInfo.setSqlId("LSQuotAllDetailSql17");
									tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
									tSQLInfo.addSubPara(tQuotNo);
									tSQLInfo.addSubPara(tQuotBatNo);
									tSQLInfo.addSubPara(tSysPlanCode);
									tSQLInfo.addSubPara(tPlanCode);
									tSQLInfo.addSubPara(tRiskCode);
									tSQLInfo.addSubPara(tDutyCode);
								} else {
									//modify by ZhangC 20150310
									//方案T开头，捆绑方案，险种、责任不可重复，为方便查询询价表中动态因子信息，去掉方案编码查询条件
									tSQLInfo = new SqlClass();
									tSQLInfo.setResourceName("g_quot.LSQuotAllDetailSql");
									tSQLInfo.setSqlId("LSQuotAllDetailSql21");
									tSQLInfo.addSubPara(tSQLElement);//SQL查询字段
									tSQLInfo.addSubPara(tQuotNo);
									tSQLInfo.addSubPara(tQuotBatNo);
									tSQLInfo.addSubPara(tRiskCode);
									tSQLInfo.addSubPara(tDutyCode);
								}
								
								var tRelaSubDetailSub = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
								tDetailDesc += getDutyDetailSub(tDutyArr, tRelaSubDetailSub,1);
							}
						}
						
						tInnerHTML1		+=" <tr>"
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[0] +"' value='"+ (k+1) +"' readonly></td>"// value='序号' 
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[1] +"' name=RiskCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskCode +"' readonly></td>"//value='险种名称编码'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[2] +"' name=RiskName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tRiskName +"' readonly></td>"// value='险种名称'
													+"		<td class=mulinetitle style='display: none'><input class=mulcommon style='width: "+ tWidthArr[3] +"' name=DutyCode"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyCode +"' readonly></td> "// value='责任编码'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[4] +"' name=DutyName"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDutyName +"' readonly></td> "// value='责任名称'
													+"		<td class=mulinetitle><input class=mulcommon style='width: "+ tWidthArr[5] +"' name=DetailDesc"+  tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tDetailDesc +"' readonly></td> "// value='保险责任描述'
						
						if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {//普通险种,建工险及个人险种
						
							tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='期望保费类型'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费/费率/折扣'
							
							tInnerHTML1 +=" <td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='初始保费'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='期望收益率'
										+"	</tr>";
							
						} else if (cTranProdType=="02") {//账户型
							
							tInnerHTML1 +="	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[6] +"' name=ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremType +"' readonly></td>"// value='期望保费类型编码'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[7] +"' name=ExceptPremTypeName"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPremTypeName +"' readonly></td>"// value='期望保费类型'
										+"	<td class=mulinetitle style=\"display: 'none'\"><input class=mulcommon style='width: "+ tWidthArr[8] +"' name=ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptPrem +"' readonly></td>"// value='期望保费/费率/折扣'
							
							tInnerHTML1 +=" <td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[9] +"' name=InitPrem"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tInitPrem +"' readonly></td>"// value='初始保费'
										+"	<td class=mulinetitle style=\"display: ''\"><input class=mulcommon style='width: "+ tWidthArr[10] +"' name=ExceptYield"+ tRiskCode + tDutyCode + tSysPlanCode +" value='"+ tExceptYield +"' readonly></td>"// value='期望收益率'
										+"	</tr>";
						}							
					}
					tInnerHTML1 += "				</table>"
												+"			</td>"
												+"		</tr>"
												+"	</table>";
					if (cTranProdType=="00" || cTranProdType=="01" || cTranProdType=="03") {
						
						tInnerHTML1 += "<div align=\"right\"><input class=cssButton type=button value=\"变更保费\" onclick=\"changDetail('"+ tOfferListNo +"','"+ tQuotNo +"','"+ tQuotBatNo +"','"+ tSysPlanCode +"','"+ tPlanCode +"')\"></div>";
					}
					tInnerHTML1 += "</div>";
				}
			}
		}
	}
	return tInnerHTML1;
}

/**
 * 首页
 */
function firstPage() {
	
	OnPage = 1;
	goToPage(1);
}

/**
 * 上一页
 */
function previousPage() {
	
	if (OnPage-1<1) {
		alert("已经达到首页");
		return false;
	}
	
	OnPage = OnPage-1;
	goToPage(OnPage);
}

/**
 * 下一页
 */
function nextPage() {
	
	if (OnPage+1>PageNum) {
		alert("已经达到尾页");
		return false;
	}
	
	OnPage = OnPage+1;
	goToPage(OnPage);
}

/**
 * 尾页
 */
function lastPage() {
	
	OnPage = PageNum;
	goToPage(PageNum);
}

function getDutyElementArr(cRiskCode, cDutyCode) {

	var tCalFactor;//原因子编码
	var tFactorCode;//从表字段
	var tFactorName;//因子名称
	var tFieldType;//字段类型
	var tValueType;//值类型
	var tDefaultValue;//默认值
	var tFieldLength;//字段长度
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql18");
	tSQLInfo.addSubPara(cRiskCode);
	tSQLInfo.addSubPara(cDutyCode);
		
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	return tArr;
}

/**
 * 获取责任动态展示域,modify by songsz 20140520 增加标记字段用来处理附属被保险人的展示,0-非附属设置,1-附属设置
 */
function getDutyElement(tArr, cFlag) {
	
	var tRelaFactor = "";
	var tRelaFactorName = "";
	
	if (cFlag=="1") {
	
		tRelaFactor = "Relation";
		tRelaFactorName = "附属方案";
	}

	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>";
	if (tArr==null) {
		//未查询出数据,表示无动态因子
	} else {
		
		tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input></td><td class=title></td><td class=input></td><td class=title></td><td class=input></td></tr>";
		var tCount = 3;
		for (var i=0; i<tArr.length; i++) {
		
			if (tCount==3) {
				tInnerHTML0 += "<tr class=common>";
			}
			
			tCalFactor = tArr[i][0];
			tFactorCode = tArr[i][1];
			tFactorName = tArr[i][2];
			tFieldType = tArr[i][3];
			tValueType = tArr[i][4];
			tDefaultValue = tArr[i][5];
			tFieldLength = tArr[i][6];
			tMandatoryFlag = tArr[i][8];
			tDefalutName = tArr[i][9];
			
			if (tFieldType=="0") {//根据字段类型进行处理,0-录入框,1-下拉框,2-日期
				
				if (tValueType=="NUM") {
					//modify by ZhangC 20150320 处理公共保额新增字段
					if (tCalFactor =="PerPrem") {
						tInnerHTML0 += "<td class=title id=idPerPrem1 name=idPerPrem1 style=\"display: ''\">"+ tRelaFactorName + tFactorName +"</td><td class=input id=idPerPrem2 name=idPerPrem2 style=\"display: ''\"><input class=common name=PerPrem id=PerPrem verify=\""+ tRelaFactorName + tFactorName +"|num\"";
					} else if (tCalFactor =="StandPerPrem")  {
						tInnerHTML0 += "<td class=title id=idStandPerPrem1 name=idStandPerPrem1 style=\"display: ''\">"+ tRelaFactorName + tFactorName +"</td><td class=input id=idStandPerPrem2 name=idStandPerPrem2 style=\"display: ''\"><input class=common name=StandPerPrem ";
					} else {
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|num\"";
					}
					
				} else if (tValueType=="INT") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" verify=\""+ tRelaFactorName + tFactorName +"|int\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode ;
				}
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += " readonly ";
				}
				//modify by ZhangC 20150320 处理公共保额新增字段
				if (tCalFactor =="PerPrem" ) {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red;display: 'none'\" id=idStarPerPrem name=idStarPerPrem >*</span></td><td class=title id =hidden1 name=hidden1 style=\"display: 'none'\"></td><td class=input id =hidden2 name=hidden2 style=\"display: 'none'\"></td>";
				} else if (tCalFactor =="StandPerPrem") {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> </td><td class=title id =hidden3 name=hidden3 style=\"display: 'none'\"></td><td class=input id =hidden4 name=hidden4 style=\"display: 'none'\"></td>";
				} else {
					tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
				}
				
			} else if (tFieldType=="1") {
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\" readonly> <span style=\"color: red\">*</span></td>";
				} else {
					if (tCalFactor =="PremCalWay") {
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name=PremCalWay id=PremCalWay ondblclick=\"return showCodeList('"+ tValueType +"',[this,PremCalWayName],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,PremCalWayName],[0,1],null,null,null,1)\" readonly><input class=codename name=PremCalWayName readonly> <span style=\"color: red\">*</span></td>";
					} else{
						tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=codeno name="+ tRelaFactor + tFactorCode +" value=\""+ tDefaultValue +"\" ondblclick=\"return showCodeList('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1);\" onkeyup=\"return showCodeListKey('"+ tValueType +"',[this,"+ tRelaFactor + tFactorCode +"Name],[0,1],null,null,null,1)\" readonly><input class=codename name="+ tRelaFactor + tFactorCode +"Name value=\""+ tDefalutName +"\"  readonly> <span style=\"color: red\">*</span></td>";
					} 
				}
			} else if (tFieldType=="2") {
				
				if (tMandatoryFlag=="1") {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=common name="+ tRelaFactor + tFactorCode +" readonly verify=\"'"+ tRelaFactorName + tFactorName +"'|date\"";
				} else {
					tInnerHTML0 += "<td class=title>"+ tRelaFactorName + tFactorName +"</td><td class=input><input class=\"coolDatePicker\" dateFormat=\"short\" name="+ tRelaFactor + tFactorCode +" verify=\"'"+ tRelaFactorName + tFactorName +"'|date\"";
				}
				
				tInnerHTML0 += " value=\""+ tDefaultValue +"\"> <span style=\"color: red\">*</span></td>";
			}
			
			tCount--;
			if (tCount==0) {
				tInnerHTML0 += "</tr>";
				tCount = 3;
			}
		}
		
		if (tCount!=0 && tCount!=3) {//补上空白的字段
			
			for (var i=1; i<=tCount; i++) {
				tInnerHTML0 += "<td class=title></td><td class=input></td>";
			}
			
			tInnerHTML0 += "</tr>";
		}
		
		tInnerHTML0 += "</table>";
	}
	//window.clipboardData.setData("Text",tInnerHTML0) ;
	return tInnerHTML0;
	
}

/**
 * 获取责任动态域需赋值字段SQL
 */
function getDutySQLElement(tArr) {

	var tSQLElement = "";

	if (tArr==null) {
		//未查询出数据,表示无动态因子
	} else {

		for (var i=0; i<tArr.length; i++) {

			var tFactorCode = tArr[i][1];
			if (i==(tArr.length-1)) {
				tSQLElement += tFactorCode;
			} else {
				tSQLElement += tFactorCode + ",";
			}
		}
	}
		
	return tSQLElement;
}

/**
 * 获取从表明细描述
 */
function getDutyDetailSub(tDutyArr, tDutyResultArr,tFlag) {

	var tDetailSubDesc = "";
	for (var i=0; i<tDutyArr.length; i++) {
		
		var tFactorCode = tDutyArr[i][1];
		var tFactorName = tDutyArr[i][2];
		var tFieldType = tDutyArr[i][3];
		var tValueType = tDutyArr[i][4];
		
		if (tFlag=="0") {//普通动态因子
			
			//modify by ZhangC 20150323 处理【保险责任描述】中公共保额的 保费计算方式、人均保费 的展示
			if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//公共保额保费计算方式为按保额
			} else if (tFactorCode == "P18") {
			} else {
				tDetailSubDesc += tFactorName+":";
			}
			
		} else if (tFlag=="1") {//主附共用配置动态因子
			tDetailSubDesc += "附属方案" + tFactorName+":";
		}
		if (tFieldType=="1") {
		
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql9");
			tSQLInfo.addSubPara(tValueType);
			tSQLInfo.addSubPara(tDutyResultArr[0][i]);
			
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
			tDetailSubDesc += tArr[0][0];
		} else {
			
			if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//公共保额保费计算方式为按保额
			} else if (tFactorCode == "P18") {
			} else {
				tDetailSubDesc += tDutyResultArr[0][i];
			}
		}
		
		if (tFactorCode == "P17" && tDutyResultArr[0][i-1] == "0" ) {//公共保额保费计算方式为按保额
		} else if (tFactorCode == "P18") {
		} else {
			tDetailSubDesc += ";";
		}
	}
	
	return tDetailSubDesc;
}

function checkDutyElement(cObj, tArr, cFlag) {
	
	var tRelaFactor = "";
	var tRelaFactorName = "";
	
	if (cFlag=="1") {
	
		tRelaFactor = "Relation";
		tRelaFactorName = "附属方案";
	}
	
	if (tArr==null) {
		//未查询出数据,表示无动态因子
	} else {
		
		for (var i=0; i<tArr.length; i++) {
				
			//tCalFactor = tArr[i][0];
			tFactorCode = tRelaFactor + tArr[i][1];
			tFactorName = tRelaFactorName + tArr[i][2];
			tFieldType = tArr[i][3];
			tValueType = tArr[i][4];
			//tDefaultValue = tArr[i][5];
			tFieldLength = tArr[i][6];
			tValueScope = tArr[i][7];
			
			if (tFieldType=="1") {//下拉框,仅校验是否为空
			
				var tValue = document.all(tFactorCode).value;
				if (tValue==null || tValue=="") {
					alert(tFactorName+"不能为空！");
					return false;
				}
			} else if (tFieldType=="0") {//录入框,取出类型valutype,根据类型及字段长度进行判断
			
				var tValue = document.all(tFactorCode).value;
				if (tValue==null || tValue=="") {
					alert(tFactorName+"不能为空！");
					return false;
				}
				
				if (tValueType=="INT" || tValueType=="NUM") {

					var tSplitArr = tValueScope.split(",");
					
					var tReg1 = tValueScope.substr(0, 1);
					var tReg2 = tSplitArr[0].substr(1);
					var tReg3 = tSplitArr[1].substr(0,tSplitArr[1].length-1);
					var tReg4 = tValueScope.substr(tValueScope.length-1);
					
					if (tReg1=="(") {//开区间
						
						if (Number(tValue)<=Number(tReg2)) {
							alert(tFactorName+"应大于"+ tReg2 +"！");
							return false;
						}
					} else if (tReg1=="[") {
						
						if (Number(tValue)<Number(tReg2)) {
							alert(tFactorName+"应不小于"+ tReg2 +"！");
							return false;
						}
					} else {
						alert(tFactorName+"校验异常！");
						return false;
					}
					
					if (tReg4==")") {//开区间
						
						if (Number(tValue)>=Number(tReg3)) {
							alert(tFactorName+"应小于"+ tReg3 +"！");
							return false;
						}
					} else if (tReg4=="]") {
						
						if (Number(tValue)>Number(tReg3)) {
							alert(tFactorName+"应不大于"+ tReg3 +"！");
							return false;
						}
					} else {
						alert(tFactorName+"校验异常！");
						return false;
					}
				}
				
				if (tValueType=="INT") {
					
					if (!isInteger(tValue)) {
						alert(tFactorName+"应为整数！");
						return false;
					}
				} else if (tValueType=="NUM") {
					
					if (!isNumeric(tValue)) {
						alert(tFactorName+"应为有效数字！");
						return false;
					}
					
					//拆分规则
					var tRegArr = tFieldLength.split(",");
					if (!checkDecimalFormat(tValue, tRegArr[0], tRegArr[1])) {
						alert(tFactorName+"整数位不应超过"+ tRegArr[0] +"位,小数位不应超过"+ tRegArr[1] +"位！");
						return false;
					}
				}
			}
		}
	}
	
	return true;
}

/**
 * 问题件按钮展示判断
 */
function judgeShowQuest(cQuotNo, cQuotBatNo, cActivityID) {
	
	if (cActivityID=="0800100001") {
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql31");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fmOther.all("enterQuest").style.display = "none";
		} else {
			fmOther.all("enterQuest").style.display = "";
		}
	} else if (cActivityID=="0800100002") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql32");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fm3.all("SubUWQuestionButton").style.display = "none";
		} else {
			fm3.all("SubUWQuestionButton").style.display = "";
		}
	} else if (cActivityID=="0800100003") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql33");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fm3.all("BranchUWQuestionButton").style.display = "none";
		} else {
			fm3.all("BranchUWQuestionButton").style.display = "";
		}
	} else if (cActivityID=="0800100004") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql34");
		tSQLInfo.addSubPara(cQuotNo);
		tSQLInfo.addSubPara(cQuotBatNo);
		
		var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr[0][0]=="0") {
			fm3.all("UWQuestionButton").style.display = "none";
		} else {
			fm3.all("UWQuestionButton").style.display = "";
		}
	}
}

/**
 * 回目录
 */
function ReturnList(cQuotType) {
	
	if (tActivityID=="0800100001") {
		
		if (tQuotType==tETQuotType) {
			location.href = "./LSQuotETQueryInput.jsp";
		} else if (tQuotType==tProjQuotType) {
			location.href = "./LSQuotProjQueryInput.jsp";
		}
	} else if (tActivityID=="0800100002") {
		
		location.href = "./LSQuotSubUWQueryInput.jsp";
	} else if (tActivityID=="0800100003") {
		
		location.href = "./LSQuotBranchUWQueryInput.jsp";
	} else if (tActivityID=="0800100004") {
		
		location.href = "./LSQuotUWQueryInput.jsp";
	} else if (tActivityID=="0800100005") {
		
		location.href = "./LSQuotUWManagerQueryInput.jsp";
	} else if (tActivityID=="0800100006") {
		
		location.href = "./LSQuotBranchFinalQueryInput.jsp";
	} else if (tActivityID=="0800100007") {
		
		location.href = "./LSQuotSubFinalQueryInput.jsp";
	}
}

/**
 * 检查证件类型
 */
function checkidtype(cObj) {
	
	if (cObj.IDType.value=="") {
		alert("请先选择证件类型！");
		cObj.IDNo.value="";
	}
}

/**
 * 根据身份证号取得出生日期和性别
 */
function getBirthdaySexByIDNo(iIdNo,cObj) {

	try {
		if (document.all('IDType').value=="11") {
			cObj.Gender.value = "";
			cObj.GenderName.value = "";
			cObj.Birthday.value = "";
			if (iIdNo!=null&&iIdNo!="") {
				if (isNumeric(iIdNo.substring(0,(iIdNo.length-1)))==false) {
					alert("身份证号码只能是数字！");
					return false;
				}
	    
				if (iIdNo.length!=18&&iIdNo.length!=15) {
					alert("身份证长度只能为15或18位！");
					return false;
				}
				if (iIdNo.length==18) {
					if (iIdNo.substring(10,12)>12) {
						alert("身份证中代表出生日期的数字填写错误！");
						return false;
					}
				}
				if (iIdNo.length==18) {
					if (iIdNo.substring(10,12)<=00) {
						alert("身份证中代表出生日期的数字填写错误！");
						return false;
					}
				}
					
				if (iIdNo.length==18) {
					if (iIdNo.substring(10,12)=="12"||iIdNo.substring(10,12)=="01"||iIdNo.substring(10,12)=="03"||iIdNo.substring(10,12)=="05"||iIdNo.substring(10,12)=="07"||iIdNo.substring(10,12)=="08"||iIdNo.substring(10,12)=="10") {
	    				if (iIdNo.substring(12,14)>31) {
	    					alert("身份证中代表出生日期的数字填写错误！");
	    					return false;
	    				}
	    			}
	    		
					if (iIdNo.substring(10,12)=="04"||iIdNo.substring(10,12)=="06"||iIdNo.substring(10,12)=="09"||iIdNo.substring(10,12)=="11")	{
						if (iIdNo.substring(12,14)>30) {
	    					alert("身份证中代表出生日期的数字填写错误！");
	    					return false;
						}
					}
	
					if (iIdNo.substring(10,12)=="02") {
						if (iIdNo.substring(6,10)%4!=0) {
							if(iIdNo.substring(12,14)>28) {
	    						alert("身份证中代表出生日期的数字填写错误！");
	    						return false;
							}
	     				}
						if (iIdNo.substring(6,10)%4==0)	{
							if(iIdNo.substring(12,14)>29) {
								alert("身份证中代表出生日期的数字填写错误！");
								return false;
							}
						}
					}
	    
					//大龄早于1900年的检查
					if (parseInt(iIdNo.substring(6,10),10)<1900 || parseInt(iIdNo.substring(6,10),10)>2100 ) {
						alert("出生日期格式不对，请返回检查");
						return false;
					}
				}			
				// 对于出生日期为1980-08-00类似的错误没有校验
				if(iIdNo.substring(12,14)<1) {
					alert("身份证中代表出生日期的数字填写错误！");
					cObj.IDNo.className = "warn";
					return false;
				}
	  		}
	  
			if (iIdNo.length==15) {
				if(iIdNo.substring(8,10)>12){
					alert("身份证中代表出生日期填写错误！");
					return false;
	    		}
			}
			
				if (iIdNo.length==15) {
				if(iIdNo.substring(8,10)<=00){
					alert("身份证中代表出生日期填写错误！");
					return false;
	    		}
			}
	  
			if (iIdNo.length==15) {
		  		//判断日期
				if (iIdNo.substring(8,10)=="12"||iIdNo.substring(8,10)=="01"||iIdNo.substring(8,10)=="03"||iIdNo.substring(8,10)=="05"||iIdNo.substring(8,10)=="07"||iIdNo.substring(8,10)=="08"||iIdNo.substring(8,10)=="10") {
		  			if (iIdNo.substring(10,12)>31) {
		  				alert("身份证中代表出生日期的数字填写错误！");
		  				return false;
		    		}
		    	}
				if (iIdNo.substring(8,10)=="04"||iIdNo.substring(8,10)=="06"||iIdNo.substring(8,10)=="09"||iIdNo.substring(8,10)=="11") {
		    		if(iIdNo.substring(10,12)>30) {
		    			alert("身份证中代表出生日期的数字填写错误！");
		    			return false;
		    		}
		    	}
				if (iIdNo.substring(8,10)=="02") {
		    		if(19+(iIdNo.substring(6,8))%4!=0) {
		    			if(iIdNo.substring(10,12)>28) {
		    				alert("身份证中代表出生日期的数字填写错误！");
		    				return false;
		    			}
		     		}
					if (19+(iIdNo.substring(6,8)+1900)%4==0) {
						if (iIdNo.substring(10,12)>29) {
							alert("身份证中代表出生日期的数字填写错误！");
							return false;
		    			}
		     		}
		    	}
			    var tmpStr= "19" + iIdNo.substring(6,8); //大龄早于1900年的检查
				if (parseInt(tmpStr.substring(0,4),10)<1900 || parseInt(tmpStr.substring(0,4),10)>2100 ) {
					alert("出生日期格式不对，请返回检查");
					return false;
				}
				 //	15位时数字的校验
				var NUM="0123456789";
				var i;
				for (i=0;i<iIdNo.length;i++) {
						if(NUM.indexOf(iIdNo.charAt(i))<0) {
							alert("身份证为15位时只能是数字！");
							return false;
					}
				}
				// 对于出生日期为1980-08-00类似的错误没有校验
				if (iIdNo.substring(10,12)<1) {			
			    	alert("身份证中代表出生日期的数字填写错误！");
			    	cObj.IDNo.className = "warn";
			    	return false;
			    }
			}

			if (trim(iIdNo).length==18) {
				var sex;
				var sexq;
				var birthday;
				birthday=trim(iIdNo).substring(6,10)+"-"+trim(iIdNo).substring(10,12)+"-"+trim(iIdNo).substring(12,14);
				cObj.Birthday.value=birthday;
				sex=trim(iIdNo).substring(16,17)
				if (sex%2==1){
					sexq='1';
				} else {
					sexq='2';
				}						
				if (sexq=='1') {
					cObj.Gender.value='0';
					cObj.GenderName.value = '男';
				} else if(sexq =='2') {
					cObj.Gender.value='1';
					cObj.GenderName.value = '女';
				}
				var tAge = calAge(birthday);
				cObj.Age.value=tAge;
			}
			if (trim(iIdNo).length==15) {
				var sex;
				var sexq;
				var birthday;
				birthday="19"+trim(iIdNo).substring(6,8)+"-"+trim(iIdNo).substring(8,10)+"-"+trim(iIdNo).substring(10,12);	
				cObj.Birthday.value=birthday;	
				sex=trim(iIdNo).substring(14,15)
				if (sex%2==1) {
					sexq='1';
				} else {
					sexq='2';
				}			
				if (sexq=='1') {
					cObj.Gender.value='0';
					cObj.GenderName.value = '男';
				} else if(sexq =='2') {
					cObj.Gender.value='1';
					cObj.GenderName.value = '女';
				}	
				var tAge = calAge(birthday);
				cObj.Age.value=tAge;
			}
		}
	}catch(ex) {
	}
}
