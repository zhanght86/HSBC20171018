<%
/***************************************************************
 * <p>ProName：LCPolicyAccountInit.jsp</p>
 * <p>Title：账户信息查询</p>
 * <p>Description：账户信息查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
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
		initQueryResultGrid();
		initResultInfoGrid();
		
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
// 查询结果初始化
function initQueryResultGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try{
	
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="团体保单号";
		iArray[i][1]="120px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称"; 
		iArray[i][1]="150px";
		iArray[i][2]=120; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="客户号"; 
		iArray[i][1]="70px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="个人保单号"; 
		iArray[i][1]="120px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="个人险种号"; 
		iArray[i][1]="0px";
		iArray[i][2]=60; 
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="姓名"; 
		iArray[i][1]="60px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件号码"; 
		iArray[i][1]="120px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="险种编码"; 
		iArray[i][1]="50px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账户编码"; 
		iArray[i][1]="0px";
		iArray[i][2]=60; 
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="账户名称"; 
		iArray[i][1]="110px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="交费名称"; 
		iArray[i][1]="110px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账户余额"; 
		iArray[i][1]="60px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="承保机构"; 
		iArray[i][1]="50px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;

		QueryResultGrid = new MulLineEnter( "fm" , "QueryResultGrid");
		//这些属性必须在loadMulLine前
		QueryResultGrid.mulLineCount = 0;
		QueryResultGrid.displayTitle = 1;
		QueryResultGrid.locked = 0;
		QueryResultGrid.canSel =1;
		QueryResultGrid.hiddenPlus=1;
		QueryResultGrid.hiddenSubtraction=1;
		QueryResultGrid.selBoxEventFuncName = "queryInfo";
		QueryResultGrid.loadMulLine(iArray);
	} catch(ex){
	  alert("ex");
	}
}

function initResultInfoGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try{
	
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="业务号";
		iArray[i][1]="120px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="业务类型编码"; 
		iArray[i][1]="80px";
		iArray[i][2]=120; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="业务类型名称"; 
		iArray[i][1]="100px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="业务日期"; 
		iArray[i][1]="70px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="变更类型编码"; 
		iArray[i][1]="100px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="变更类型"; 
		iArray[i][1]="120px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="变更金额"; 
		iArray[i][1]="70px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;

		ResultInfoGrid = new MulLineEnter( "fm" , "ResultInfoGrid");
		//这些属性必须在loadMulLine前
		ResultInfoGrid.mulLineCount = 0;
		ResultInfoGrid.displayTitle = 1;
		ResultInfoGrid.locked = 0;
		ResultInfoGrid.canSel =0;
		ResultInfoGrid.hiddenPlus=1;
		ResultInfoGrid.hiddenSubtraction=1;
		ResultInfoGrid.loadMulLine(iArray);
	} catch(ex){
	  alert("ex");
	}
}
</script>
