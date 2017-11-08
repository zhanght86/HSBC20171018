<%
/***************************************************************
 * <p>ProName：LSQuotProjBasicInit.jsp</p>
 * <p>Title：项目询价基本信息录入</p>
 * <p>Description：项目询价基本信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
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
		
		initAppOrgCodeGrid();//适用机构
		initAgencyNameGrid();//中介名称
		initLinkInquiryNoGrid();//关联询价号
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
 * 适用机构编码
 */
function initAppOrgCodeGrid() {
	
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		/**
		iArray[i] = new Array();
		iArray[i][0] = "适用机构编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][7] = "queryCom";
		**/
		
		iArray[i] = new Array();
		iArray[i][0] = "适用机构编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4] = "conditioncomcode";
		iArray[i][5] = "1|2";
		iArray[i][6] = "0|1";
		iArray[i][15] = "comgrade";
		iArray[i][16] = " #01# or comgrade=#02# or comgrade=#03# ";
		iArray[i++][19] = "1";
		
		iArray[i] = new Array();
		iArray[i][0] = "适用机构名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AppOrgCodeGrid = new MulLineEnter("fm2", "AppOrgCodeGrid");
		AppOrgCodeGrid.mulLineCount = 1;
		AppOrgCodeGrid.displayTitle = 1;
		AppOrgCodeGrid.locked = 0;
		AppOrgCodeGrid.canSel = 0;
		AppOrgCodeGrid.canChk = 0;
		AppOrgCodeGrid.hiddenSubtraction = 0;
		AppOrgCodeGrid.hiddenPlus = 0;
		AppOrgCodeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 中介机构名称
 */
function initAgencyNameGrid() {
	
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
		iArray[i][0] = "中介编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介名称";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		AgencyNameGrid = new MulLineEnter("fm2", "AgencyNameGrid");
		AgencyNameGrid.mulLineCount = 1;
		AgencyNameGrid.displayTitle = 1;
		AgencyNameGrid.locked = 0;
		AgencyNameGrid.canSel = 0;
		AgencyNameGrid.canChk = 0;
		AgencyNameGrid.hiddenSubtraction = 0;
		AgencyNameGrid.hiddenPlus = 0;
		AgencyNameGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 关联询价号
 */
function initLinkInquiryNoGrid() {
	
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
		iArray[i][0] = "关联询价号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		LinkInquiryNoGrid = new MulLineEnter("fm2", "LinkInquiryNoGrid");
		LinkInquiryNoGrid.mulLineCount = 1;
		LinkInquiryNoGrid.displayTitle = 1;
		LinkInquiryNoGrid.locked = 0;
		LinkInquiryNoGrid.canSel = 0;
		LinkInquiryNoGrid.canChk = 0;
		LinkInquiryNoGrid.hiddenSubtraction = 0;
		LinkInquiryNoGrid.hiddenPlus = 0;
		LinkInquiryNoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
