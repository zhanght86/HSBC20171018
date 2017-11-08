<%
/***************************************************************
 * <p>ProName：LSQuotUWDetailInit.jsp</p>
 * <p>Title：一般询价核保明细</p>
 * <p>Description：一般询价核保明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-31
 ****************************************************************/
%>
<script language="JavaScript">
var tSubUWFlag = 0;//是否经过中支公司核保标记，0-否，1-是
var tTranProdType = "";

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
		tSQLInfo.setSqlId("LSQuotUWSql20");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			tSubUWFlag = 1;
			divSubUW.style.display = "";
		} else {
			divSubUW.style.display = "none";
		}
					
		queryQuotBasic();
		querySaleChannel();
		queryPlanDiv();
		
		initUWMainPointGrid();
		initOtherOpinionGrid();
		initAgencyListGrid();
		initRelaCustListGrid();
		initPlanInfoGrid();
		
		queryQuotAgency();
		queryRelaCust();
		initQuotStep2();
		queryUWMainPoint();
		queryOtherOpinion();
		queryProcess();
		judgeShowQuest(tQuotNo, tQuotBatNo, tActivityID);
		
		if (tActivityID=="0800100002") {
			
			//先把样式表设置
			document.getElementById("tdUWRule1").className = "commontop";
			document.getElementById("tdUWRule2").className = "titleImgtop";
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "none";
			divSubUW.style.display = "";
			
			fm3.UWAnalySaveButton.style.display = "";
			fmEngin.EnginSaveButton.style.display = "";
			fmPlan.AddPlanButton.style.display = "";
			fmPlan.ModifyPlanButton.style.display = "";
			fmPlan.DelPlanButton.style.display = "";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "";
			
			divUW.style.display = "none";
			
		} else if (tActivityID=="0800100003") {
			
			document.getElementById("tdUWRule1").className = "commontop";
			document.getElementById("tdUWRule2").className = "titleImgtop";
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "";
			fmEngin.EnginSaveButton.style.display = "";
			fmPlan.AddPlanButton.style.display = "";
			fmPlan.ModifyPlanButton.style.display = "";
			fmPlan.DelPlanButton.style.display = "";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
			divUW.style.display = "none";
			
		} else if (tActivityID=="0800100004") {
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			
			divUWManagerButton.style.display = "none";
			if (isEmpty(fm1.UWManagerConclu)) {
				divUWManager.style.display = "none";
			} else {
				divUWManager.style.display = "";
			}
			
			divOtherOpinion.style.display = "";
			divUW.style.display = "";
			divOtherOpinion.style.display = "";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			showReinsInfo();
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
		} else if (tActivityID=="0800100005") {
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			divUWManager.style.display = "";
			divOtherOpinion.style.display = "";
			divUW.style.display = "";
			divOtherOpinion.style.display = "";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			divOtherOpinionButton.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "";
			
			showReinsInfo();
			
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
			
		} else if (tActivityID=="0800100006") {
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "";
			divOtherOpinion.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			divOtherOpinionButton.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
			var tTotalFlag = queryTotalFlag();
			if (tTotalFlag=="0") {
				divUW.style.display = "none";
			} else {
				divUW.style.display = "";
			}
		} else if (tActivityID=="0800100007") {
			
			divSubFinal.style.display = "";
			divBranchFinal.style.display = "";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			divOtherOpinionButton.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			divBranchFinalButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			var tTotalFlag = queryTotalFlag();
			if (tTotalFlag=="0") {
				divUW.style.display = "none";
			} else {
				divUW.style.display = "";
			}
		}
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {

		if (tActivityID!="0800100002" && tActivityID!="0800100003" && tActivityID!="0800100004") {
			return;
		}
		
		var b1 = "0";//业务需求
		var b2 = "0";//费用信息
		var b3 = "0";//既往信息
		var b4 = "0";//问卷调查
		var b5 = "0";//附件管理
		var b6 = "0";//特别约定
		var b7 = "0";//共保配置
		
		if (tActivityID=="0800100002") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql36");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				b1 =  tArr[0][0];
				b2 =  tArr[0][1];
				b3 =  tArr[0][2];
				b4 =  tArr[0][3];
				b5 =  tArr[0][4];
				b6 =  tArr[0][5];
				b7 =  tArr[0][6];
			}
		} else if (tActivityID=="0800100003") {
		
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql37");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				b1 =  tArr[0][0];
				b2 =  tArr[0][1];
				b3 =  tArr[0][2];
				b4 =  tArr[0][3];
				b5 =  tArr[0][4];
				b6 =  tArr[0][5];
				b7 =  tArr[0][6];
			}
		} else if (tActivityID=="0800100004") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql38");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				b1 =  tArr[0][0];
				b2 =  tArr[0][1];
				b3 =  tArr[0][2];
				b4 =  tArr[0][3];
				b5 =  tArr[0][4];
				b6 =  tArr[0][5];
				b7 =  tArr[0][6];
			}
		}

		if (b1=="0") {
			fm1.all("RequestButton").className = "cssButton2";
		}
		
		if (b2=="0") {
			fm1.all("FeeButton").className = "cssButton2";
		}
		
		if (b3=="0") {
			fm1.all("PastButton").className = "cssButton2";
		}
		
		if (b4=="0") {
			fm1.all("QuesButton").className = "cssButton2";
		}
		
		if (b5=="0") {
			fm1.all("AttachButton").className = "cssButton2";
		}

		if (b6=="0") {
			fm1.all("SpecButton").className = "cssButton2";
		}
		
		if (tQuotType=="00") {//只有一般询价才有共保配置
		
			if (b7=="0") {
				fm1.all("ShowCoinButton").className = "cssButton2";
			}
		}
		
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}

/**
 * 把null的字符串转为空
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}

/**
 * 初始化列表
 */
function initUWMainPointGrid() {

	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "产品编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "产品名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保要点编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "要点描述";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		if (tActivityID=="0800100002") {
			
			iArray[i] = new Array();
			iArray[i][0] = "中支公司要点分析";
			iArray[i][1] = "90px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		
			iArray[i] = new Array();
			iArray[i][0] = "分公司要点分析";
			iArray[i][1] = "90px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else {
			
			if (tSubUWFlag==1) {
			
				iArray[i] = new Array();
				iArray[i][0] = "中支公司要点分析";
				iArray[i][1] = "90px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
			} else {
				
				iArray[i] = new Array();
				iArray[i][0] = "中支公司要点分析";
				iArray[i][1] = "90px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
			}
			
			iArray[i] = new Array();
			iArray[i][0] = "分公司要点分析";
			iArray[i][1] = "90px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		UWMainPointGrid = new MulLineEnter("fm3", "UWMainPointGrid");
		UWMainPointGrid.mulLineCount = 0;
		UWMainPointGrid.displayTitle = 1;
		UWMainPointGrid.locked = 1;
		UWMainPointGrid.canSel = 1;
		UWMainPointGrid.canChk = 0;
		UWMainPointGrid.hiddenSubtraction = 1;
		UWMainPointGrid.hiddenPlus = 1;
		UWMainPointGrid.selBoxEventFuncName = "showUWMainPoint";
		UWMainPointGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化UWMainPointGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initOtherOpinionGrid() {

	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其他部门意见流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "意见类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "意见类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其他部门意见";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件路径";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件(双击下载)";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i++][7] = "downFile";
		
		OtherOpinionGrid = new MulLineEnter("fm2", "OtherOpinionGrid");
		OtherOpinionGrid.mulLineCount = 0;
		OtherOpinionGrid.displayTitle = 1;
		OtherOpinionGrid.locked = 1;
		OtherOpinionGrid.canSel = 1;
		OtherOpinionGrid.canChk = 0;
		OtherOpinionGrid.hiddenSubtraction = 1;
		OtherOpinionGrid.hiddenPlus = 1;
		OtherOpinionGrid.selBoxEventFuncName = "showOtherOpinion";
		OtherOpinionGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化OtherOpinionGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initAgencyListGrid() {

	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AgencyListGrid = new MulLineEnter("fm3", "AgencyListGrid");
		AgencyListGrid.mulLineCount = 0;
		AgencyListGrid.displayTitle = 1;
		AgencyListGrid.locked = 1;
		AgencyListGrid.canSel = 0;
		AgencyListGrid.canChk = 0;
		AgencyListGrid.hiddenSubtraction = 1;
		AgencyListGrid.hiddenPlus = 1;
		AgencyListGrid.selBoxEventFuncName = "";
		AgencyListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化AgencyListGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initRelaCustListGrid() {
	
	turnPage4 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "准客户号码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "准客户名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RelaCustListGrid = new MulLineEnter("fm3", "RelaCustListGrid");
		RelaCustListGrid.mulLineCount = 0;
		RelaCustListGrid.displayTitle = 1;
		RelaCustListGrid.locked = 1;
		RelaCustListGrid.canSel = 0;
		RelaCustListGrid.canChk = 0;
		RelaCustListGrid.hiddenSubtraction = 1;
		RelaCustListGrid.hiddenPlus = 1;
		RelaCustListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化RelaCustListGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initPlanInfoGrid() {

	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案描述";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案类型";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案标识编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案标识";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费计算方式编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费计算方式";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "0px";
		} else {
			iArray[i][1] = "40px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 3;
		} else {
			iArray[i++][3] = 0;
		}

		iArray[i] = new Array();
		iArray[i][0] = "职业类别标记";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别标记";//OccupTypeFlagName
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别编码";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业中分类编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业中分类";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "工种编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "工种";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "最低年龄(岁)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "最高年龄(岁)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "平均年龄(岁)";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "人数";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "参加社保占比";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "男女比例";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "退休占比";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费分摊方式编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费分摊方式";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "企业负担占比";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "最低月薪(元)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "最高月薪(元)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "平均月薪(元)";
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "其他说明";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业比例";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		PlanInfoGrid = new MulLineEnter("fmPlan", "PlanInfoGrid");
		PlanInfoGrid.mulLineCount = 0;
		PlanInfoGrid.displayTitle = 1;
		PlanInfoGrid.locked = 1;
		PlanInfoGrid.canSel = 1;
		PlanInfoGrid.canChk = 0;
		PlanInfoGrid.hiddenSubtraction = 1;
		PlanInfoGrid.hiddenPlus = 1;
		PlanInfoGrid.selBoxEventFuncName = "showPlanInfo";
		PlanInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
