<%
/***************************************************************
 * <p>ProName：LCPropPrintInit.jsp</p>
 * <p>Title：投保书打印</p>
 * <p>Description：投保书打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-22
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
		
		initQuotInfoGrid();
		initPropInfoGrid();
		//queryPropInfo();
		
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
 * 询价查询列表
 */
function initQuotInfoGrid() {
	
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
		iArray[i][0] = "报价单号";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "询价号";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "批次号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "询价类型编码";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "询价类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
	
		iArray[i] = new Array();
		iArray[i][0] = "准客户号码";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
			
		iArray[i] = new Array();
		iArray[i][0] = "准客户名称";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		
		QuotInfoGrid = new MulLineEnter("fm", "QuotInfoGrid");
		QuotInfoGrid.mulLineCount = 0;
		QuotInfoGrid.displayTitle = 1;
		QuotInfoGrid.locked = 1;
		QuotInfoGrid.canSel = 1;
		QuotInfoGrid.canChk = 0;
		QuotInfoGrid.hiddenSubtraction = 1;
		QuotInfoGrid.hiddenPlus = 1;
		QuotInfoGrid.selBoxEventFuncName = "";
		QuotInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
function initPropInfoGrid() {
	
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
		iArray[i][0] = "投保单号";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "报价单号";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "单位名称";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单状态编码";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单状态";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "投保单下载";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i][7] = "downloadAppnt";
		iArray[i++][21] = "2";
		//iArray[i++][24] = "blue";
		
		iArray[i] = new Array();
		iArray[i][0] = "文件名";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "地址";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
			
		PropInfoGrid = new MulLineEnter("fm", "PropInfoGrid");
		PropInfoGrid.mulLineCount = 0;
		PropInfoGrid.displayTitle = 1;
		PropInfoGrid.locked = 1;
		PropInfoGrid.canSel = 1;
		PropInfoGrid.canChk = 0;
		PropInfoGrid.hiddenSubtraction = 1;
		PropInfoGrid.hiddenPlus = 1;
		PropInfoGrid.selBoxEventFuncName = "";
		PropInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
