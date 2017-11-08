<%
/***************************************************************
 * <p>ProName：LCInsuredCollectInit</p>
 * <p>Title：导入信息汇总查询</p>
 * <p>Description：导入信息汇总查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-04
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initQueryScanGrid();
		showList();
	
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化界面参数
 */
function initOtherParam() {

	try {
	} catch (ex) {
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

function initQueryScanGrid() {
	
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
		iArray[i][0] = "保险方案";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "人数";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费(元)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;

		iArray[i] = new Array();
		iArray[i][0] = "总保费(元)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "总保额(元)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		QueryScanGrid = new MulLineEnter("fm", "QueryScanGrid");
		QueryScanGrid.mulLineCount = 0;
		QueryScanGrid.displayTitle = 1;
		QueryScanGrid.locked = 1;
		QueryScanGrid.canSel = 0;
		QueryScanGrid.canChk = 0;
		QueryScanGrid.hiddenSubtraction = 1;
		QueryScanGrid.hiddenPlus = 1;
		QueryScanGrid.selBoxEventFuncName = ""; 
		QueryScanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
