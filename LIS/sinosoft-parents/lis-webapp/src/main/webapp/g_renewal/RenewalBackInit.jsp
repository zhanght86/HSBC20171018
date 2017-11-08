<%
/***************************************************************
 * <p>ProName：RenewalBackInit.jsp</p>
 * <p>Title：抽档回退</p>
 * <p>Description：抽档回退</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-17
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
		initContInfoGrid();
		initRiskInfoGrid();
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
 * 初始化期缴保单列表
 */
function initContInfoGrid() {
	
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
		iArray[i][0] = "缴费号";
		iArray[i][1] = "40px";
		iArray[i][2] = 30;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "承保机构";
		iArray[i][1] = "50px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单号";
		iArray[i][1] = "130px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "150px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费频率";
		iArray[i][1] = "50px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "当前保费";
		iArray[i][1] = "60px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单生效日期";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单终止日期";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费终止日期";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "交费期数";
		iArray[i][1] = "50px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "催缴日期";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "应交日期";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "交至日期";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "应交金额(元)";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		ContInfoGrid = new MulLineEnter("fm", "ContInfoGrid");
		ContInfoGrid.mulLineCount = 0;
		ContInfoGrid.displayTitle = 1;
		ContInfoGrid.locked = 1;
		ContInfoGrid.canSel = 1;
		ContInfoGrid.canChk = 0;
		ContInfoGrid.hiddenPlus = 1;
		ContInfoGrid.hiddenSubtraction = 1;
		ContInfoGrid.selBoxEventFuncName = "showRiskInfo";
		ContInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化ContInfoGrid时出错："+ ex);
	}
}

/**
 * 保单应交费用信息
 */
function initRiskInfoGrid() {
	
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
		iArray[i][0] = "保单号";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "200px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "应交金额(元)";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RiskInfoGrid = new MulLineEnter("fm", "RiskInfoGrid");
		RiskInfoGrid.mulLineCount = 0;
		RiskInfoGrid.displayTitle = 1;
		RiskInfoGrid.locked = 1;
		RiskInfoGrid.canSel = 0;
		RiskInfoGrid.canChk = 0;
		RiskInfoGrid.hiddenPlus = 1;
		RiskInfoGrid.hiddenSubtraction = 1;
		RiskInfoGrid.selBoxEventFuncName = "";
		RiskInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化RiskInfoGrid时出错："+ ex);
	}
}
</script>
