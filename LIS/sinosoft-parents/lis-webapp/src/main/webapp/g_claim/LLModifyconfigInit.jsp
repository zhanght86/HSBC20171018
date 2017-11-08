<%
/***************************************************************
 * <p>ProName：LlmodifyconfigInit.jsp</p>
 * <p>Title：保项修改规则配置界面</p>
 * <p>Description：保项修改规则配置确认</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王志豪
 * @version  : 8.0
 * @date     : 2014-06-01
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
        initRuleTypeGrid();
        initPolTypeGrid();
        initTiaoZhengGrid();
        
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
 * 规则类型列表
 */
function initRuleTypeGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保项修改原因编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保项修改原因名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则类型编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则类型名称";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RuleTypeGrid = new MulLineEnter("fm", "RuleTypeGrid");
		RuleTypeGrid.mulLineCount = 0;
		RuleTypeGrid.displayTitle = 1;
		RuleTypeGrid.canSel = 0;
		RuleTypeGrid.canChk = 1;
		RuleTypeGrid.hiddenPlus = 1;
		RuleTypeGrid.hiddenSubtraction = 1;
		RuleTypeGrid.selBoxEventFuncName = "";
		RuleTypeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化RuleTypeGrid时出错："+ ex);
	}
}

function initPolTypeGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保项修改原因编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保项修改原因名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种类型编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种类型名称";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PolTypeGrid = new MulLineEnter("fm", "PolTypeGrid");
		PolTypeGrid.mulLineCount = 0;
		PolTypeGrid.displayTitle = 1;
		PolTypeGrid.canSel = 0;
		PolTypeGrid.canChk = 1;
		PolTypeGrid.hiddenPlus = 1;
		PolTypeGrid.hiddenSubtraction = 1;
		PolTypeGrid.selBoxEventFuncName = "";
		PolTypeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化PolTypeGrid时出错："+ ex);
	}
}

function initTiaoZhengGrid() {
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保项修改原因编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保项修改原因名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "调整方向编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "调整方向名称";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		TiaoZhengGrid = new MulLineEnter("fm", "TiaoZhengGrid");
		TiaoZhengGrid.mulLineCount = 0;
		TiaoZhengGrid.displayTitle = 1;
		TiaoZhengGrid.canSel = 0;
		TiaoZhengGrid.canChk = 1;
		TiaoZhengGrid.hiddenPlus = 1;
		TiaoZhengGrid.hiddenSubtraction = 1;
		TiaoZhengGrid.selBoxEventFuncName = "";
		TiaoZhengGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化TiaoZhengGrid时出错："+ ex);
	}
}


</script>
