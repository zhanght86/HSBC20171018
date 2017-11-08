<%
/***************************************************************
 * <p>ProName：EdorNIInit.jsp</p>
 * <p>Title：新增被保险人</p>
 * <p>Description：新增被保险人</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initEdorDetailGrid();
		initQueryInfoGrid();
		initBnfGrid();
		initAmntGrid();
		initPayPlanGrid();
		initInvestGrid();
		initInpBox();
		queryInsured(2);
		
	
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
		
		clearPage();
		if(tActivityID=='1800401001'){
			divButton01.style.display="";
			divInsuredInfo2.style.display="";
			fm.BatchNo.value = tEdorAppNo;
		}else{
			divButton01.style.display="none";
			divButton02.style.display="";
			divInsuredInfo2.style.display="none";
			fm.BatchNo.value = "";
		}
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

function initEdorDetailGrid(){
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i]=new Array();
		iArray[i][0]="被保险人客户号";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="个人保单号";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="被保险人姓名";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="性别编码";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="性别";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型编码";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保险方案编码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保险方案";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="保费(元)";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		if(tActivityID=="1800401001"){
			iArray[i++][3]=3;
		}else{
			iArray[i++][3]=0;
		}
		
		iArray[i]=new Array();
		iArray[i][0]="保全生效日期";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i] = new Array();
		iArray[i][0] = "导入批次号";
		iArray[i][1] = "80px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "主附属关系";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		EdorDetailGrid = new MulLineEnter("fm", "EdorDetailGrid");
		EdorDetailGrid.mulLineCount = 0;
		EdorDetailGrid.displayTitle = 1;
		EdorDetailGrid.locked = 1;
		EdorDetailGrid.canSel = 1;
		EdorDetailGrid.canChk = 1;
		EdorDetailGrid.hiddenSubtraction = 1;
		EdorDetailGrid.hiddenPlus = 1;
		EdorDetailGrid.selBoxEventFuncName = "getCustomerInfo";
		EdorDetailGrid.loadMulLine(iArray);
		
		} catch (ex) {
			alert("初始化界面错误!");
		}
	}
	

function initBnfGrid(){
	
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
		iArray[i][0] = "受益人类别";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnftype";
		iArray[i][5] = "1|2";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人类别编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="受益人类别|code:bnftype&NOTNULL";
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人顺序";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnforder";
		iArray[i][5] = "3|4";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人顺序编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="受益人顺序|code:bnforder";

		iArray[i] = new Array();
		iArray[i][0] = "姓名";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] =1;
		iArray[i++][9]="姓名|len<=20";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="sex";
		iArray[i][5] = "6|7";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="性别|code:sex";	

		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "出生日期|DATE";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5] = "9|10";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="证件类型|code:idtype";	

		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件号码|len<=20";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "与被保险人关系";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i][3]=2;			//是否允许输入,1表示允许，0表示不允许
		iArray[i][4]="relation";
		iArray[i][5] = "12|13";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "与被保险人关系编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3]=3;		
		iArray[i++][9]="与被保人关系|code:relation";
		
		iArray[i] = new Array();
		iArray[i][0] = "受益比例";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "受益比例|FLOAT&NOTNULL";
				
		BnfGrid = new MulLineEnter("fm", "BnfGrid");
		BnfGrid.mulLineCount = 0;
		BnfGrid.displayTitle = 1;
		BnfGrid.locked = 0;
		BnfGrid.canSel = 0;
		BnfGrid.canChk = 0;
		BnfGrid.hiddenSubtraction = 0;
		BnfGrid.hiddenPlus = 0;
		BnfGrid.selBoxEventFuncName = ""; 
		BnfGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
	
}

function initQueryInfoGrid() {

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
		iArray[i][0] = "与主被保险人关系";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "附属被保险人姓名";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "年龄";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险方案";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		QueryInfoGrid = new MulLineEnter("fm", "QueryInfoGrid");
		QueryInfoGrid.mulLineCount = 0;
		QueryInfoGrid.displayTitle = 1;
		QueryInfoGrid.locked = 1;
		QueryInfoGrid.canSel = 0;
		QueryInfoGrid.canChk = 0;
		QueryInfoGrid.hiddenSubtraction = 1;
		QueryInfoGrid.hiddenPlus = 1;
		
		QueryInfoGrid.selBoxEventFuncName = "";
		QueryInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

// 非固定保额
function initAmntGrid() {

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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "最高保额(元)";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "最低保额(元)";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额(元)";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="保额|notnull&num";
		
		AmntGrid = new MulLineEnter("fm", "AmntGrid");
		AmntGrid.mulLineCount = 0;
		AmntGrid.displayTitle = 1;
		AmntGrid.locked = 1;
		AmntGrid.canSel = 0;
		AmntGrid.canChk = 0;
		AmntGrid.hiddenSubtraction = 1;
		AmntGrid.hiddenPlus = 1;
		
		AmntGrid.selBoxEventFuncName = "";
		AmntGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initPayPlanGrid() {
	
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
		iArray[i][0] = "批次号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任缴费ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "缴费金额（元）";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		PayPlanGrid = new MulLineEnter("fm", "PayPlanGrid");
		PayPlanGrid.mulLineCount = 0;
		PayPlanGrid.displayTitle = 1;
		PayPlanGrid.locked = 1;
		PayPlanGrid.canSel = 1;
		PayPlanGrid.canChk = 0;
		PayPlanGrid.hiddenSubtraction = 1;
		PayPlanGrid.hiddenPlus = 1;
		PayPlanGrid.selBoxEventFuncName = "queryInvest";
		PayPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initInvestGrid() {
	
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
		iArray[i][0] = "批次号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任缴费ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资账户编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资账户名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资金额（元）";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资分配比例";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		InvestGrid = new MulLineEnter("fm", "InvestGrid");
		InvestGrid.mulLineCount = 0;
		InvestGrid.displayTitle = 1;
		InvestGrid.locked = 1;
		InvestGrid.canSel = 0;
		InvestGrid.canChk = 0;
		InvestGrid.hiddenSubtraction = 1;
		InvestGrid.hiddenPlus = 1;
		InvestGrid.selBoxEventFuncName = ""; 
		InvestGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
