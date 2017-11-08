<%
/***************************************************************
 * <p>ProName：UWPersonQueryInit.jsp</p>
 * <p>Title：人员分布</p>
 * <p>Description：人员分布</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
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
		initYearDistributionGrid();
		queryInfo();
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化界面参数
 */
function initOtherParam() {

	try {
	} catch (ex) {
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


function initYearDistributionGrid(){

	var iArray = new Array();
	var i = 0;
	try{
	
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="年龄段(岁)";
		iArray[i][1]="50px";
		iArray[i][2]=80; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="平均年龄(岁)";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="男女比例";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		 iArray[i]=new Array();
		iArray[i][0]="最高保额(元)";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="有社保人数";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="总人数";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		YearDistributionGrid = new MulLineEnter( "fm" , "YearDistributionGrid" );
		YearDistributionGrid.mulLineCount = 0;
		YearDistributionGrid.displayTitle = 1;
		YearDistributionGrid.hiddenPlus = 1;
		YearDistributionGrid.hiddenSubtraction = 1;
		YearDistributionGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
