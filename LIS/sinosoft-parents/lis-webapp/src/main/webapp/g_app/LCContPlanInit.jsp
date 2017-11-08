<%
/***************************************************************
 * <p>ProName：LCContPlanInit.jsp</p>
 * <p>Title：投保方案信息录入</p>
 * <p>Description：投保方案信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-03-26
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		initRelaFlag();
		showManageCom();
		initParam();
		initInpBox();
		initButton();
		//initPlanInfoGrid();
		showPageInfo();
	
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		fmrela.GrpPropNo.value=tGrpPropNo;
		tContPlanType = getContPlanType(tGrpPropNo);
		var prttype = tGrpPropNo.substr(3,1);
		if("RELA"==fmPub.RelaFlag.value){
			if(tQueryFlag=="null" || tQueryFlag==""){
				tQueryFlag = "4";
			}
		}
		if("1"==prttype){
			if(tQueryFlag=="null" || tQueryFlag==""){
				tQueryFlag = "2";
			}
		}else if("2"==prttype){
			if(tQueryFlag=="null" || tQueryFlag==""){
				tQueryFlag = "3";
			}
		}
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
	
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
		tSQLInfo.setSqlId("LCContPlanTradSql25");
		
		if(tPrtNo=="null"||tPrtNo==''){
			tSQLInfo.addSubPara(tGrpPropNo);
		}else{
			tSQLInfo.addSubPara(tPrtNo);
		}
		
		var tArrre = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tArrre==null||tArrre==''){
			fmrela.RequestButton.disabled=true;
			fmrela.GrpSpecButton.disabled=true;
		}else{			
			fmrela.OfferListNo.value=tArrre[0][0];
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
			tSQLInfo.setSqlId("LCContPlanTradSql26");
			tSQLInfo.addSubPara(fmrela.OfferListNo.value);
			var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(tArr1==0){
				fmrela.RequestButton.disabled=true;
			}else{
				fmrela.RequestButton.disabled=false;
			}
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
			tSQLInfo.setSqlId("LCContPlanTradSql27");
			tSQLInfo.addSubPara(fmrela.OfferListNo.value);
			var tArr2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(tArr2==0){
				fmrela.GrpSpecButton.disabled=true;
			}else{
				fmrela.GrpSpecButton.disabled=false;
			}
		}
		
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		divPeriod.style.display = "none";//保存机构
		divRela.style.display = "none";//关联
		divEnginSaveButton.style.display = "none";//建工险
		divAddPlanButton.style.display = "none";//方案
		divInfo5.style.display = "none";//参数维护
		divInfo4.style.display = "none";//录入完毕
		divInfoClose.style.display = "";//关闭
		fmrela.OfferListNo.className="readonly";
		
		if(tQueryFlag=="2"){
			divPeriod.style.display = "none";//保存机构
			divRela.style.display = "none";//关联
			
			divEnginSaveButton.style.display = "none";//建工险
			divAddPlanButton.style.display = "none";//方案
			if("02"==tContPlanType){
				divInfo5.style.display = "";//参数维护
			}
			divInfo4.style.display = "";//录入完毕
			divInfoClose.style.display = "none";//关闭
		}else if(tQueryFlag=="3"){
			if(tActivityID=='1800201002'){
				fmrela.queryOffLis.style.display='';
			}
			divPeriod.style.display = "";//保存机构
			divRela.style.display = "";//关联
			fmrela.OfferListNo.className="common";
			if("01"==tContPlanType){
				divEnginSaveButton.style.display = "";//建工险
			}
			divAddPlanButton.style.display = "";//方案
			if("02"==tContPlanType){
				divInfo5.style.display = "";//参数维护
			}
			divInfo4.style.display = "";//录入完毕
			divInfoClose.style.display = "none";//关闭
		}else if(tQueryFlag=="4"){
			divPeriod.style.display = "";//保存机构
			divRela.style.display = "";//关联
			fmrela.OfferListNo.className="common";
			if("01"==tContPlanType){
				divEnginSaveButton.style.display = "none";//建工险
			}
			divAddPlanButton.style.display = "none";//方案
			if("02"==tContPlanType){
				divInfo5.style.display = "";//参数维护
			}
			divInfo4.style.display = "";//录入完毕
			divInfoClose.style.display = "none";//关闭
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
 * 方案信息
 */
function initPlanInfoGrid() {

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
		iArray[i][1] = "40px";
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
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

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
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
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
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "人数";
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {//建工险也进行展示
			iArray[i][1] = "20px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "参加社保占比";
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "男女比例";
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
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
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tContPlanType=='00' || tContPlanType=='02') {//普通险种或账户型险种展示
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
		
		PlanInfoGrid = new MulLineEnter("fm2", "PlanInfoGrid");
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
