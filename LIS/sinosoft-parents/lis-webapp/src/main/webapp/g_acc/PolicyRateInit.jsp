<%
/***************************************************************
 * <p>ProName：PolicyRateInit.jsp</p>
 * <p>Title：保单利率维护</p>
 * <p>Description：保单利率维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-07-01
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
		
		initPolicyGrid();
		initPolicyRateGrid();
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


function initPolicyGrid() {     

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
		iArray[i][0]="承保机构";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保书号";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="生效日期";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="签单日期";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		PolicyGrid = new MulLineEnter("fm", "PolicyGrid");
		PolicyGrid.mulLineCount = 0;
		PolicyGrid.displayTitle = 1;
		PolicyGrid.locked = 0;
		PolicyGrid.canSel = 1;
		PolicyGrid.hiddenPlus=1;
		PolicyGrid.hiddenSubtraction=1;
		PolicyGrid.selBoxEventFuncName = "showPolicyRateInfo";
		PolicyGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("初始化界面错误!");
	}  
}

function initPolicyRateGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="险种编码";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="险种名称";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账户编码";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账户名称";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="利率类型";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i][3]=2;
		iArray[i++][4] = "rateintv";
		
		iArray[i]=new Array();
		iArray[i][0]="利率类型名称";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="利率";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		PolicyRateGrid = new MulLineEnter("fm", "PolicyRateGrid");
		PolicyRateGrid.mulLineCount = 0;
		PolicyRateGrid.displayTitle = 1;
		PolicyRateGrid.locked = 0;
		PolicyRateGrid.canSel = 0;
		PolicyRateGrid.canChk = 0;
		PolicyRateGrid.hiddenSubtraction = 0;
		PolicyRateGrid.hiddenPlus = 1;
		PolicyRateGrid.selBoxEventFuncName = "";
		PolicyRateGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
