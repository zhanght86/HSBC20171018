<%
/***************************************************************
 * <p>ProName：LCPolicyQueryProcessInit.jsp</p>
 * <p>Title：承保保单节点查询</p>
 * <p>Description：承保保单节点查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-08-09
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
		initProcessGrid();
		initTraceGrid();
		
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


function initVirtualGrid() {
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "虚拟字段";
		iArray[i][1] = (window.document.body.clientWidth-30)+"px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		VirtualGrid = new MulLineEnter("fm", "VirtualGrid");
		VirtualGrid.mulLineCount = 0;
		VirtualGrid.displayTitle = 1;
		VirtualGrid.locked = 0;
		VirtualGrid.canSel = 1;
		VirtualGrid.canChk = 0;
		VirtualGrid.hiddenSubtraction = 1;
		VirtualGrid.hiddenPlus = 1;
		//TempFeeInfoGrid.selBoxEventFuncName = "";
		VirtualGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 流程节点信息
 */
function initProcessGrid() {
	
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
		iArray[i][0] = "管理机构";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单号";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单号";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人客户号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
			
		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "当前位置编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "当前位置";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "当前操作人编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "当前操作人";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		ProcessGrid = new MulLineEnter("fm", "ProcessGrid");
		ProcessGrid.mulLineCount = 0;
		ProcessGrid.displayTitle = 1;
		ProcessGrid.locked = 1;
		ProcessGrid.canSel = 1;
		ProcessGrid.canChk = 0;
		ProcessGrid.hiddenSubtraction = 1;
		ProcessGrid.hiddenPlus = 1;
		ProcessGrid.selBoxEventFuncName = "queryTrace";
		ProcessGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}


/**
 * 询价轨迹信息
 */
function initTraceGrid() {
	
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
		iArray[i][0] = "流程节点名称编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "流程节点名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "操作人编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "操作人";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "操作日期";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "操作时间";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		TraceGrid = new MulLineEnter("fm", "TraceGrid");
		TraceGrid.mulLineCount = 0;
		TraceGrid.displayTitle = 1;
		TraceGrid.locked = 1;
		TraceGrid.canSel = 0;
		TraceGrid.canChk = 0;
		TraceGrid.hiddenSubtraction = 1;
		TraceGrid.hiddenPlus = 1;
		TraceGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
