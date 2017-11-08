<%
/***************************************************************
 * <p>ProName：LCContPlanDetailInit.jsp</p>
 * <p>Title：基本信息录入</p>
 * <p>Description：基本信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-14
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
		
		initPlanDetailInfoGrid();
		initPubAmntRelaPlanGrid();
		initPubAmntRelaDutyGrid();
		initPayFeeGrid();
		initTZFeeGrid();
		
		queryPlanDetail();
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
		divInfoButton.style.display = "none";//新增修改删除
		divInfoButton2.style.display = "none";//公共保额关联
		divInfoButton3.style.display = "none";//缴费项信息
		divInfoButton4.style.display = "none";//投资账户信息
		if(tQueryFlag=="2"){
			divInfoButton.style.display = "";
			divInfoButton2.style.display = "";
			divInfoButton3.style.display = "";
			divInfoButton4.style.display = "none";
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
 * 询价方案明细信息
 */
function initPlanDetailInfoGrid() {

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
		iArray[i][1] = "40px";
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
		iArray[i][0] = "保费计算方式";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费计算方式";
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
		iArray[i][0] = "职业类别标记编码";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别标记";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "固定保额(元)";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "月薪倍数";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "最高保额(元)";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "最低保额";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "期望保费类型";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "期望保费类型";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "期望保费(元)/期望费率/费率折扣";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "参考保费(元)/参考费率";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "折扣率";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "初始保费(元)";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "期望收益率";
		if (tContPlanType=="00" || tContPlanType=="03") {//普通险种,个人险种
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="01") {//建工险
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="02") {//账户型
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "备注";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PlanDetailInfoGrid = new MulLineEnter("fm2", "PlanDetailInfoGrid");
		PlanDetailInfoGrid.mulLineCount = 0;
		PlanDetailInfoGrid.displayTitle = 1;
		PlanDetailInfoGrid.locked = 1;
		PlanDetailInfoGrid.canSel = 1;
		PlanDetailInfoGrid.canChk = 0;
		PlanDetailInfoGrid.hiddenSubtraction = 1;
		PlanDetailInfoGrid.hiddenPlus = 1;
		PlanDetailInfoGrid.selBoxEventFuncName = "showPlanDetailInfo";
		PlanDetailInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 公共保额个人限额
 */
function initPubAmntRelaPlanGrid() {

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
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案描述";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "是否已保存";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人限额(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;

		PubAmntRelaPlanGrid = new MulLineEnter("fm2", "PubAmntRelaPlanGrid");
		PubAmntRelaPlanGrid.mulLineCount = 0;
		PubAmntRelaPlanGrid.displayTitle = 1;
		PubAmntRelaPlanGrid.locked = 1;
		PubAmntRelaPlanGrid.canSel = 1;
		PubAmntRelaPlanGrid.canChk = 0;
		PubAmntRelaPlanGrid.hiddenSubtraction = 1;
		PubAmntRelaPlanGrid.hiddenPlus = 1;
		PubAmntRelaPlanGrid.selBoxEventFuncName = "showRelaDuty";
		PubAmntRelaPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 公共保额责任限额
 */
function initPubAmntRelaDutyGrid() {

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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "是否使用公共保额编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "是否使用公共保额";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4] = "trueflag";
		iArray[i][5] = "6|5";
		iArray[i][6] = "1|0";
		iArray[i++][19] = "1";
		
		iArray[i] = new Array();
		iArray[i][0] = "责任限额(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;

		PubAmntRelaDutyGrid = new MulLineEnter("fm2", "PubAmntRelaDutyGrid");
		PubAmntRelaDutyGrid.mulLineCount = 0;
		PubAmntRelaDutyGrid.displayTitle = 1;
		PubAmntRelaDutyGrid.locked = 1;
		PubAmntRelaDutyGrid.canSel = 0;
		PubAmntRelaDutyGrid.canChk = 0;
		PubAmntRelaDutyGrid.hiddenSubtraction = 1;
		PubAmntRelaDutyGrid.hiddenPlus = 1;
		//PubAmntRelaDutyGrid.selBoxEventFuncName = "";
		PubAmntRelaDutyGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
function initPayFeeGrid() {
	
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
		iArray[i][0] = "责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "缴费金额（元）";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		PayFeeGrid = new MulLineEnter("fm2", "PayFeeGrid");
		PayFeeGrid.mulLineCount = 1;
		PayFeeGrid.displayTitle = 1;
		PayFeeGrid.locked = 0;
		PayFeeGrid.canSel = 1;
		PayFeeGrid.canChk = 0;
		PayFeeGrid.hiddenSubtraction = 1;
		PayFeeGrid.hiddenPlus = 1;
		PayFeeGrid.selBoxEventFuncName = "showTZInfo";
		//PayFeeGrid.addEventFuncName = "showTZInfo";
		PayFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
function initTZFeeGrid() {
	
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
		iArray[i][0] = "缴费编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险账户编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资账户名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "投资金额（元）";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资分配比例";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		TZFeeGrid = new MulLineEnter("fm2", "TZFeeGrid");
		TZFeeGrid.mulLineCount = 2;
		TZFeeGrid.displayTitle = 1;
		TZFeeGrid.locked = 0;
		TZFeeGrid.canSel = 0;
		TZFeeGrid.canChk = 0;
		TZFeeGrid.hiddenSubtraction = 1;
		TZFeeGrid.hiddenPlus = 1;
		TZFeeGrid.addEventFuncName = "";
		TZFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
