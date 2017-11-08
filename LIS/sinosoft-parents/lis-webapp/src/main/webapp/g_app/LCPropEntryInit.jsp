<%
/***************************************************************
 * <p>ProName：LCPropEntryInit.jsp</p>
 * <p>Title：投保书信息录入</p>
 * <p>Description：投保书信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
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
		
		initIDInfoGrid();
		initZJGrid();
		initEntry();
		showInfo();
		initState();
		getElasticflag();
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
		fm.GrpPropNo.value = tGrpPropNo;
		fm.Flag.value = tFlag;
		fm.QuotType.value = getQuotType(tGrpPropNo);
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
 * 询价查询列表
 */
function initIDInfoGrid() {
	
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
		iArray[i][0] = "证件类型编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][9]="证件类型编码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][9]="证件类型编码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件号码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件有效起期";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件有效起期|date";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件有效止期";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件有效止期|date";
		
		IDInfoGrid = new MulLineEnter("fm", "IDInfoGrid");
		IDInfoGrid.mulLineCount = 0;
		IDInfoGrid.displayTitle = 1;
		IDInfoGrid.locked = 0;
		IDInfoGrid.canSel = 0;
		IDInfoGrid.canChk = 0;
		IDInfoGrid.hiddenSubtraction = 0;
		IDInfoGrid.hiddenPlus = 0;
		IDInfoGrid.selBoxEventFuncName = "";
		IDInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
function initZJGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="中介机构编码|notnull";
				
		iArray[i] = new Array();
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="中介机构编码|notnull";
				
		ZJGrid = new MulLineEnter("fm", "ZJGrid");
		ZJGrid.mulLineCount = 0;
		ZJGrid.displayTitle = 1;
		ZJGrid.locked = 0;
		ZJGrid.canSel = 0;
		ZJGrid.canChk = 0;
		ZJGrid.hiddenSubtraction = 0;
		ZJGrid.hiddenPlus = 0;
		ZJGrid.selBoxEventFuncName = "shouZJ";
		ZJGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
