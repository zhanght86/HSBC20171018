<%
/***************************************************************
 * <p>ProName：LCBnfQueryInit.jsp</p>
 * <p>Title：受益人信息维护</p>
 * <p>Description：受益人信息维护</p>
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
		initButton();
		initBnfGrid();
		getBnfInfo();
	
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
		if("01"==tFlag){
			div1.style.display="none";
			div2.style.display="";
		}
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}

function initBnfGrid() {
	
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
		iArray[i][0] = "受益人顺序";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnforder";
		iArray[i][5] = "1|2";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		

		iArray[i] = new Array();
		iArray[i][0] = "受益人顺序编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="受益人顺序|code:bnforder&NOTNULL";

		iArray[i] = new Array();
		iArray[i][0] = "姓名";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] =1;
		iArray[i++][9]="姓名|notnull&len<=20";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="sex";
		iArray[i][5] = "4|5";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="性别|code:sex";	

		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "出生日期|DATE";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5] = "7|8";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="证件类型|code:idtype&NOTNULL";	

		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件号码|NOTNULL&len<=20";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "与被保险人关系";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i][3]=2;			//是否允许输入,1表示允许，0表示不允许
		iArray[i][4]="relation";
		iArray[i][5] = "10|11";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;

		iArray[i] = new Array();
		iArray[i][0] = "与被保险人关系编码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3]=3;		
		iArray[i++][9]="与被保人关系|code:relation&NOTNULL";
    
		iArray[i] = new Array();
		iArray[i][0] = "受益比例";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "受益比例|FLOAT&NOTNULL&value>0";
				
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

</script>
