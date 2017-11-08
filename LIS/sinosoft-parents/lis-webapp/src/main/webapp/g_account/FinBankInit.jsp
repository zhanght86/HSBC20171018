<%
/***************************************************************
 * <p>ProName：FinBankInit.jsp</p>
 * <p>Title：财务银行维护</p>
 * <p>Description：财务银行维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2013-01-01
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
		
		initFinBankGrid();
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

/**
 * 账套信息
 */
function initFinBankGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="银行编码";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="银行名称";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="银行账号";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="银行大类编码";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="银行大类名称";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账户性质编码";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="账户性质";
		iArray[i][1]="40px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="财务机构编码";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="财务机构名称";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="状态编码";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="状态";
		iArray[i][1]="40px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		FinBankGrid = new MulLineEnter("fm", "FinBankGrid");
		FinBankGrid.mulLineCount = 0;
		FinBankGrid.displayTitle = 1;
		FinBankGrid.locked = 0;
		FinBankGrid.canSel = 1;
		FinBankGrid.canChk = 0;
		FinBankGrid.hiddenSubtraction = 1;
		FinBankGrid.hiddenPlus = 1;
		FinBankGrid.selBoxEventFuncName = "showFinBankInfo";
		FinBankGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
