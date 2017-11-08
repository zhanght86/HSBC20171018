<%
/***************************************************************
 * <p>ProName：LSQuotETBasicInit.jsp</p>
 * <p>Title：一般询价基本信息录入</p>
 * <p>Description：一般询价基本信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
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
		
		initAgencyListGrid();	
		initRelaCustListGrid();
		initQuotStep1();//初始化第一步询价信息
		judgeShowQuest(tQuotNo, tQuotBatNo, tActivityID);
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
 * 中介机构录入
 */
function initAgencyListGrid() {

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
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		AgencyListGrid = new MulLineEnter("fm2", "AgencyListGrid");
		AgencyListGrid.mulLineCount = 0;
		AgencyListGrid.displayTitle = 1;
		AgencyListGrid.locked = 0;
		AgencyListGrid.canSel = 0;
		AgencyListGrid.canChk = 0;
		AgencyListGrid.hiddenSubtraction = 0;
		AgencyListGrid.hiddenPlus = 0;
		//AgencyListGrid.selBoxEventFuncName = "";
		AgencyListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 其他准客户列表
 */
function initRelaCustListGrid() {
	
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
		iArray[i][0] = "准客户号码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][7] = "queryCustomer";
		
		iArray[i] = new Array();
		iArray[i][0] = "准客户名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RelaCustListGrid = new MulLineEnter("fm2", "RelaCustListGrid");
		RelaCustListGrid.mulLineCount = 0;
		RelaCustListGrid.displayTitle = 1;
		RelaCustListGrid.locked = 0;
		RelaCustListGrid.canSel = 0;
		RelaCustListGrid.canChk = 0;
		RelaCustListGrid.hiddenSubtraction = 0;
		RelaCustListGrid.hiddenPlus = 0;
		RelaCustListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
