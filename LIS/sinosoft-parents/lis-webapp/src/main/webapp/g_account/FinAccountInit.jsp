<%
/***************************************************************
 * <p>ProName：FinAccountInit.jsp</p>
 * <p>Title：会计科目维护界面</p>
 * <p>Description：维护财务中涉及的会计科目</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
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
		
		initFinAccountGrid();
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

function initFinAccountGrid() {
	
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
		iArray[i][0]="会计科目编码";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="会计科目名称";
		iArray[i][1]="180px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="会计科目类型编码";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="会计科目类型";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="备注";
		iArray[i][1]="100px";
		iArray[i][2]=400;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="录入人";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="录入日期";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="最后修改操作人";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="最后修改日期";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		FinAccountGrid = new MulLineEnter("fm", "FinAccountGrid");
		FinAccountGrid.mulLineCount = 0;
		FinAccountGrid.displayTitle = 1;
		FinAccountGrid.locked = 0;
		FinAccountGrid.canSel = 1;
		FinAccountGrid.canChk = 0;
		FinAccountGrid.hiddenSubtraction = 1;
		FinAccountGrid.hiddenPlus = 1;
		FinAccountGrid.selBoxEventFuncName = "showFinAccountInfo";
		FinAccountGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
