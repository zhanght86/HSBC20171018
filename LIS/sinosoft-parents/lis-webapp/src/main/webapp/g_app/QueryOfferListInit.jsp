<%
/***************************************************************
 * <p>ProName：QueryOfferListInit.jsp</p>
 * <p>Title：查询报价单号</p>
 * <p>Description：查询报价单号</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-06
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
		initOfferListGrid();
		
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

function initOfferListGrid() {
	
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
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "询价号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "批次号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "询价类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "准客户名称";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		
		OfferListGrid = new MulLineEnter("fm", "OfferListGrid");
		OfferListGrid.mulLineCount = 0;
		OfferListGrid.displayTitle = 1;
		OfferListGrid.locked = 0;
		OfferListGrid.canSel = 1;
		OfferListGrid.canChk = 0;
		OfferListGrid.hiddenSubtraction = 1;
		OfferListGrid.hiddenPlus = 1;
		//OfferListGrid.selBoxEventFuncName = "queryAgentInfo";
		OfferListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化init错误!");
	}
}
</script>
 
