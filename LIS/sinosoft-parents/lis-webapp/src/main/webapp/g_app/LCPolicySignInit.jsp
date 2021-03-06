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
		fm.StateFlag.value="1";
		fm.StateFlagName.value="是";
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
		iArray[i][0]="MissionID";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="SubMissionID";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="ActivityID";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="ContPlanType";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="承保机构";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保单号"; 
		iArray[i][1]="110px";
		iArray[i][2]=120; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号"; 
		iArray[i][1]="100px";
		iArray[i][2]=120; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称"; 
		iArray[i][1]="200px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="生效日期"; 
		iArray[i][1]="60px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保费(元)"; 
		iArray[i][1]="60px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="首期应收保费(元)"; 
		iArray[i][1]="70px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="财务到账金额"; 
		iArray[i][1]="60px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="到账日期"; 
		iArray[i][1]="60px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="签发状态"; 
		iArray[i][1]="60px";
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
		QueryResultGrid.loadMulLine(iArray);
	} catch(ex){
	  alert("ex");
	}
}
</script>
