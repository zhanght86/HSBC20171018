<%
/***************************************************************
 * <p>ProName：LJMatchApplyInit.jsp</p>
 * <p>Title：保费匹配界面</p>
 * <p>Description：保费匹配界面</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-09
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
		
		initFinDataGrid();
		initOverDataGrid();
		initChoosedDataGrid();
		initBusinessDataGrid();

		initMatchingConfirmGrid();
		initChoosedData1Grid();
		initBusinessData1Grid();
		initUploadFileGrid();
		
		queryMatchData();
		queryAttachmentInfo();
	} catch(ex) {
		alert("初始化界面错误!");
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

function initFinDataGrid() {
	
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
		iArray[i][0] = "保费来源编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费来源";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务来源";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "暂收费号码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户开户行编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户开户行";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户账号";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户账户名";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单位编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单位名称";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "收款银行编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "收款银行";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "收款账号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "到账日期";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;	
		
		iArray[i] = new Array();
		iArray[i][0] = "到账金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		FinDataGrid = new MulLineEnter("fm", "FinDataGrid");
		FinDataGrid.mulLineCount = 0;//初始化行数，以后修改为0
		FinDataGrid.displayTitle = 1;
		FinDataGrid.locked = 0;
		FinDataGrid.canSel = 0;
		FinDataGrid.canChk = 1;
		FinDataGrid.hiddenSubtraction = 1;
		FinDataGrid.hiddenPlus = 1;
		FinDataGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initOverDataGrid() {
	
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
		iArray[i][0] = "保费来源编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费来源";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "溢缴流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单号";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务号";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "溢缴金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "本次使用金额";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;

		OverDataGrid = new MulLineEnter("fm", "OverDataGrid");
		OverDataGrid.mulLineCount = 0;//初始化行数，以后修改为0
		OverDataGrid.displayTitle = 1;
		OverDataGrid.locked = 0;
		OverDataGrid.canSel = 0;
		OverDataGrid.canChk = 1;
		OverDataGrid.hiddenSubtraction = 1;
		OverDataGrid.hiddenPlus = 1;
		OverDataGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initChoosedDataGrid() {
	
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
		iArray[i][0] = "保费来源编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
	
		iArray[i] = new Array();
		iArray[i][0] = "保费来源";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "来源号码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "团体保单号";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务号码";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户开户行编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "客户开户行";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户账号";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户账户名";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单位编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单位名称";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "收费银行编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "收费银行";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "收费账号";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "到账日期";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "到账金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "溢缴金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		ChoosedDataGrid = new MulLineEnter("fm1", "ChoosedDataGrid");
		ChoosedDataGrid.mulLineCount = 0;
		ChoosedDataGrid.displayTitle = 1;
		ChoosedDataGrid.locked = 0;
		ChoosedDataGrid.canSel = 0;
		ChoosedDataGrid.canChk = 1;
		ChoosedDataGrid.hiddenSubtraction = 0;
		ChoosedDataGrid.hiddenPlus = 1;
		ChoosedDataGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initBusinessDataGrid() {
	
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
		iArray[i][0] = "保单号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;		
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务号码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "投保单位编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i]=new Array();
		iArray[i][0] = "业务日期";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "应收金额";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "溢缴金额";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保公司编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保公司";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		BusinessDataGrid = new MulLineEnter("fm1", "BusinessDataGrid");
		BusinessDataGrid.mulLineCount = 0;
		BusinessDataGrid.displayTitle = 1;
		BusinessDataGrid.locked = 0;
		BusinessDataGrid.canSel = 0;
		BusinessDataGrid.canChk = 1;
		BusinessDataGrid.hiddenSubtraction = 1;
		BusinessDataGrid.hiddenPlus = 1;
		BusinessDataGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initMatchingConfirmGrid() {
	
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
		iArray[i][0] = "保费申请批次";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "交费类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "交费类型";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i]=new Array();
		iArray[i][0]="收费金额";
		iArray[i][1]="13px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="使用溢缴金额";
		iArray[i][1]="15px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="应收金额";
		iArray[i][1]="13px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="本次溢缴金额";
		iArray[i][1]="13px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="是否需上传材料标识";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="是否需上传材料";
		iArray[i][1]="13px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="匹配日期";
		iArray[i][1]="13px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="退回日期";
		iArray[i][1]="13px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="退回原因";
		iArray[i][1]="20px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
				
		MatchingConfirmGrid = new MulLineEnter("fm2", "MatchingConfirmGrid");
		MatchingConfirmGrid.mulLineCount = 0;
		MatchingConfirmGrid.displayTitle = 1;
		MatchingConfirmGrid.locked = 0;
		MatchingConfirmGrid.canSel = 0;
		MatchingConfirmGrid.canChk = 0;
		MatchingConfirmGrid.hiddenSubtraction = 1;
		MatchingConfirmGrid.hiddenPlus = 1;
		//MatchingConfirmGrid.selBoxEventFuncName = "showMatchingInfo";
		MatchingConfirmGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initChoosedData1Grid() {
	
	turnPage6 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "保费来源编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
	
		iArray[i] = new Array();
		iArray[i][0] = "保费来源";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "来源号码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "团体保单号";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务号码";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户开户行编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "客户开户行";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户账号";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户账户名";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单位编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单位名称";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "收费银行编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "收费银行";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "收费账号";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "到账日期";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "到账金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "溢缴金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		ChoosedData1Grid = new MulLineEnter("fm2", "ChoosedData1Grid");
		ChoosedData1Grid.mulLineCount = 2;
		ChoosedData1Grid.displayTitle = 1;
		ChoosedData1Grid.locked = 0;
		ChoosedData1Grid.canSel = 0;
		ChoosedData1Grid.canChk = 0;
		ChoosedData1Grid.hiddenSubtraction = 1;
		ChoosedData1Grid.hiddenPlus = 1;
		ChoosedData1Grid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initBusinessData1Grid() {
	
	turnPage7 = new turnPageClass();
	
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
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;		
		
		iArray[i] = new Array();
		iArray[i][0] = "业务类型";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务号码";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "投保单位编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i]=new Array();
		iArray[i][0] = "业务日期";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "应收金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "溢缴金额";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保公司编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保公司";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		BusinessData1Grid = new MulLineEnter("fm2", "BusinessData1Grid");
		BusinessData1Grid.mulLineCount = 1;
		BusinessData1Grid.displayTitle = 1;
		BusinessData1Grid.locked = 0;
		BusinessData1Grid.canSel = 0;
		BusinessData1Grid.canChk = 0;
		BusinessData1Grid.hiddenSubtraction = 1;
		BusinessData1Grid.hiddenPlus = 1;
		BusinessData1Grid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initUploadFileGrid() {

	turnPage8 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件系统名称";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件路径";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "下载";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][7] = "downLoadClick";
		iArray[i++][14]= "[下载]";
		
		
		UploadFileGrid = new MulLineEnter("fm4", "UploadFileGrid");
		UploadFileGrid.mulLineCount = 0;
		UploadFileGrid.displayTitle = 1;
		UploadFileGrid.locked = 0;
		UploadFileGrid.canSel = 0;
		UploadFileGrid.canChk = 0;
		UploadFileGrid.hiddenSubtraction = 1;
		UploadFileGrid.hiddenPlus = 1;
		UploadFileGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
