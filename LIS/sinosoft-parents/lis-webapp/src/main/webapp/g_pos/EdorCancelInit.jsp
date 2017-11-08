<%
/***************************************************************
 * <p>ProName：EdorCancelInit.jsp</p>
 * <p>Title：保全撤销</p>
 * <p>Description：保全撤销</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-07-14
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
		initEdorAppGrid();
		initEdorGrid();
		initEdorItemGrid();
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
 * 初始化管理机构列表
 */
function initEdorAppGrid() {
	
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
		iArray[i][0] = "受理机构";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全受理号";
		iArray[i][1] = "75px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单号";
		iArray[i][1] = "75px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "扫描日期";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "扫描人员";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "受理日期";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "受理人员";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "总变更费用(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "撤销原因";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		EdorAppGrid = new MulLineEnter("fm", "EdorAppGrid");
		EdorAppGrid.mulLineCount = 0;
		EdorAppGrid.displayTitle = 1;
		EdorAppGrid.locked = 1;
		EdorAppGrid.canSel = 1;
		EdorAppGrid.canChk = 0;
		EdorAppGrid.hiddenPlus = 1;
		EdorAppGrid.hiddenSubtraction = 1;
		EdorAppGrid.selBoxEventFuncName = "showEdorInfo";
		EdorAppGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化EdorAppGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initEdorGrid() {
	
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
		iArray[i][0] = "保全批单号";
		iArray[i][1] = "75px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "复核日期";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "复核人员";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "变更费用(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "批单状态";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "撤销原因";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		EdorGrid = new MulLineEnter("fm", "EdorGrid");
		EdorGrid.mulLineCount = 0;
		EdorGrid.displayTitle = 1;
		EdorGrid.locked = 1;
		EdorGrid.canSel = 1;
		EdorGrid.canChk = 0;
		EdorGrid.hiddenPlus = 1;
		EdorGrid.hiddenSubtraction = 1;
		EdorGrid.selBoxEventFuncName = "showEdorItemInfo";
		EdorGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化EdorGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initEdorItemGrid() {
	
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
		iArray[i][0] = "保全项目编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "变更费用(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "撤销原因";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
		EdorItemGrid.mulLineCount = 0;
		EdorItemGrid.displayTitle = 1;
		EdorItemGrid.locked = 1;
		EdorItemGrid.canSel = 1;
		EdorItemGrid.canChk = 0;
		EdorItemGrid.hiddenPlus = 1;
		EdorItemGrid.hiddenSubtraction = 1;
		EdorItemGrid.selBoxEventFuncName = "";
		EdorItemGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化EdorItemGrid时出错："+ ex);
	}
}
</script>
