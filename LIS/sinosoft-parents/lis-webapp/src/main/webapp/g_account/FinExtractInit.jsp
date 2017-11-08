<%
/***************************************************************
 * <p>ProName：FinExtractInit.jsp</p>
 * <p>Title：会计分录提取</p>
 * <p>Description：会计分录提取</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2012-01-01
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
		
		initFinExtractGrid();
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


function initFinExtractGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="业务号";
		iArray[i][1]="80px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="80px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="记账日期";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="科目代码";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="借方金额";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="贷方金额";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="科目描述";
		iArray[i][1]="80px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="公司代码";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="银行明细";
		iArray[i][1]="70px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="渠道";
		iArray[i][1]="30px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="险种代码";
		iArray[i][1]="40px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="缴费期";
		iArray[i][1]="30px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		FinExtractGrid = new MulLineEnter("fm", "FinExtractGrid");
		FinExtractGrid.mulLineCount = 0;
		FinExtractGrid.displayTitle = 1;
		FinExtractGrid.canSel = 0;
		FinExtractGrid.canChk = 0;
		FinExtractGrid.hiddenPlus = 1;
		FinExtractGrid.hiddenSubtraction = 1;
		FinExtractGrid.selBoxEventFuncName = "";
		FinExtractGrid.loadMulLine(iArray);
		
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
