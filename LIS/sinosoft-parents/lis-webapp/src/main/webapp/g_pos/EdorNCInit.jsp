<%
/***************************************************************
 * <p>ProName：EdorNCInit.jsp</p>
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
		
		initParam();
		initInpBox();
		initButton();
		initPlanInfoGrid();
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
		
		tContPlanType = getContPlanType(tPolicyNo);
		if(tContPlanType==""){
			alert("获取保单产品类型错误！");
			return false;
		}
		var tsFlag= queryPlanFlag(tPolicyNo);
		var tPlanFlag = "00";
		if("S"==tsFlag){
			tPlanFlag = "01";
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorCommonSql");
		tSQLInfo.setSqlId("EdorCommonSql3");
		tSQLInfo.addSubPara("planflag");
		tSQLInfo.addSubPara(tPlanFlag);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null) {
	
		} else {
			fm2.PlanFlag.value = tPlanFlag;
			fm2.PlanFlagName.value = tArr[0][0];
		}
		if(tActivityID=="1800401001"){
			tQueryFlag = "2";
		}
		if(tQueryFlag=="null" || tQueryFlag==""){
			tQueryFlag = "1";
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
		if(nullToEmpty(tEdorNo)==""){
			//进界面后，没有批单号则批单号取受理号
			tEdorNo = tEdorAppNo;
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
		divAddPlanButton.style.display = "none";//方案
		if(tQueryFlag=="2"){
			divAddPlanButton.style.display = "";//方案
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
