<%
/***************************************************************
 * <p>ProName：LCPolicySignInit.jsp</p>
 * <p>Title：保单签发</p>
 * <p>Description：保单签发</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-05
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
		initQueryDetailGrid();
		showCheck();		
				
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
		iArray[i][0]="保全受理号";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="投保人名称"; 
		iArray[i][1]="80px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="退/补费金额"; 
		iArray[i][1]="35px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="受理日期"; 
		iArray[i][1]="30px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="管理机构"; 
		iArray[i][1]="30px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		QueryResultGrid = new MulLineEnter( "fm" , "QueryResultGrid");
		QueryResultGrid.mulLineCount = 0;
		QueryResultGrid.displayTitle = 1;
		QueryResultGrid.locked = 0;
		QueryResultGrid.canSel =1;
		QueryResultGrid.hiddenPlus=1;
		QueryResultGrid.hiddenSubtraction=1;
		QueryResultGrid.selBoxEventFuncName = "showEdorInfo";
		QueryResultGrid.loadMulLine(iArray);
	} catch(ex){
	  alert("ex");
	}
}

/**
*保全项目详细信息
*/
function initQueryDetailGrid(){
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
		iArray[i][0]="保全受理号";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="保全批单号";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="录入日期";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="复核日期";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="审核日期";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="核保日期";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		QueryDetailGrid = new MulLineEnter( "fm" , "QueryDetailGrid");
		QueryDetailGrid.mulLineCount = 0;
		QueryDetailGrid.displayTitle = 1;
		QueryDetailGrid.locked = 0;
		QueryDetailGrid.canSel =1;
		QueryDetailGrid.hiddenPlus=1;
		QueryDetailGrid.hiddenSubtraction=1;
		QueryDetailGrid.loadMulLine(iArray);
		
		} catch(ex){
	  alert("ex");
	}
}

</script>
