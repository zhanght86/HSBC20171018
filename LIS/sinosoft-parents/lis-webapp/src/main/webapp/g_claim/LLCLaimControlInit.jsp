<%
/***************************************************************
 * <p>ProName：LLCLaimControlInit.jsp</p>
 * <p>Title：理赔责任控制</p>
 * <p>Description：理赔责任控制</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-05-05
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
		
		initConfDutyControlGrid();
		initPlan1Grid();
		//initDutyControlGrid();
		
		initShareControlGrid();
		initPlan2Grid();
		initConfShareControlGrid();
		initConfDutyShareControlGrid();
		
		initGetControlGrid();
		initPlan3Grid();
		initRiskDutyGrid();
		
		queryClick();
		
		if (tBussType=="QUOT") {
			
			document.getElementById('tablistdiv0').style.display = 'none';
			setTab(1,1);
		
		}
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
		if (tBussType=="QUOT") {
			
			document.getElementById("tab1").innerHTML = "<ul><li onmouseover=\"setTabOver(1,0)\" onmouseout=\"setTabOut(1,0)\" onclick=\"setTab(1,0)\" style=\"display: 'none'\">要素控制</li><li onmouseover=\"setTabOver(1,1)\" onmouseout=\"setTabOut(1,1)\" onclick=\"setTab(1,1)\" class=\"now\">共用控制</li><li onmouseover=\"setTabOver(1,2)\" onmouseout=\"setTabOut(1,2)\" onclick=\"setTab(1,2)\">给付控制</li></ul>";
		} else {
			
			document.getElementById("tab1").innerHTML = "<ul><li onmouseover=\"setTabOver(1,0)\" onmouseout=\"setTabOut(1,0)\" onclick=\"setTab(1,0)\" class=\"now\">要素控制</li><li onmouseover=\"setTabOver(1,1)\" onmouseout=\"setTabOut(1,1)\" onclick=\"setTab(1,1)\" class=\"now\">共用控制</li><li onmouseover=\"setTabOver(1,2)\" onmouseout=\"setTabOut(1,2)\" onclick=\"setTab(1,2)\">给付控制</li></ul>";
		}
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
		
		setTab(1,0);
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		if (tButtonFlag=="1") {
			
			fm.InsertDuty.style.display = "";
			fm.UpdateDuty.style.display = "";
			fm.DeleteDuty.style.display = "";
			fm.InsertShare.style.display = "";
			fm.DeleteShare.style.display = "";
			fm.SaveGet.style.display = "";
		} else {
			
			fm.InsertDuty.style.display = "none";
			fm.UpdateDuty.style.display = "none";
			fm.DeleteDuty.style.display = "none";
			fm.InsertShare.style.display = "none";
			fm.DeleteShare.style.display = "none";
			fm.SaveGet.style.display = "none";
		}
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

/*******************************责任控制****************************************/
function initPlan1Grid() {
	
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
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其它说明";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		Plan1Grid = new MulLineEnter("fm", "Plan1Grid");
		Plan1Grid.mulLineCount = 0;
		Plan1Grid.displayTitle = 1;
		Plan1Grid.locked = 0;
		Plan1Grid.canSel = 1;
		Plan1Grid.canChk = 0;
		Plan1Grid.hiddenSubtraction = 1;
		Plan1Grid.hiddenPlus = 1;
		Plan1Grid.selBoxEventFuncName = "showPlan1Info";
		Plan1Grid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initDutyControlGrid() {
	
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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素明细";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素值";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		DutyControlGrid = new MulLineEnter("fm", "DutyControlGrid");
		DutyControlGrid.mulLineCount = 1;
		DutyControlGrid.displayTitle = 1;
		DutyControlGrid.locked = 0;
		DutyControlGrid.canSel = 0;
		DutyControlGrid.canChk = 0;
		DutyControlGrid.hiddenSubtraction = 0;
		DutyControlGrid.hiddenPlus = 0;
		DutyControlGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initConfDutyControlGrid() {
	
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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素类型名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素明细";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素值";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		ConfDutyControlGrid = new MulLineEnter("fm", "ConfDutyControlGrid");
		ConfDutyControlGrid.mulLineCount = 0;
		ConfDutyControlGrid.displayTitle = 1;
		ConfDutyControlGrid.locked = 0;
		ConfDutyControlGrid.canSel = 1;
		ConfDutyControlGrid.canChk = 0;
		ConfDutyControlGrid.hiddenSubtraction = 1;
		ConfDutyControlGrid.hiddenPlus = 1;
		ConfDutyControlGrid.selBoxEventFuncName = "showConfDutyControlInfo";
		ConfDutyControlGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
/*******************************共用控制****************************************/
function initPlan2Grid() {
	
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
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其它说明";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		Plan2Grid = new MulLineEnter("fm", "Plan2Grid");
		Plan2Grid.mulLineCount = 0;
		Plan2Grid.displayTitle = 1;
		Plan2Grid.locked = 0;
		Plan2Grid.canSel = 1;
		Plan2Grid.canChk = 0;
		Plan2Grid.hiddenSubtraction = 1;
		Plan2Grid.hiddenPlus = 1;
		Plan2Grid.selBoxEventFuncName = "showPlan2Info";
		Plan2Grid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initShareControlGrid() {
	
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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素类型名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素明细";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素值";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		ShareControlGrid = new MulLineEnter("fm", "ShareControlGrid");
		ShareControlGrid.mulLineCount = 0;
		ShareControlGrid.displayTitle = 1;
		ShareControlGrid.locked = 0;
		ShareControlGrid.canSel = 0;
		ShareControlGrid.canChk = 1;
		ShareControlGrid.hiddenSubtraction = 1;
		ShareControlGrid.hiddenPlus = 1;
		ShareControlGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initConfShareControlGrid() {
	
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
		iArray[i][0] = "要素类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素类型名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		ConfShareControlGrid = new MulLineEnter("fm", "ConfShareControlGrid");
		ConfShareControlGrid.mulLineCount = 0;
		ConfShareControlGrid.displayTitle = 1;
		ConfShareControlGrid.locked = 0;
		ConfShareControlGrid.canSel = 1;
		ConfShareControlGrid.canChk = 0;
		ConfShareControlGrid.hiddenSubtraction = 1;
		ConfShareControlGrid.hiddenPlus = 1;
		ConfShareControlGrid.selBoxEventFuncName = "showConfShareControlInfo";
		ConfShareControlGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initConfDutyShareControlGrid() {
	
	turnPage7 = new turnPageClass();
	
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
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "要素值";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		ConfDutyShareControlGrid = new MulLineEnter("fm", "ConfDutyShareControlGrid");
		ConfDutyShareControlGrid.mulLineCount = 0;
		ConfDutyShareControlGrid.displayTitle = 1;
		ConfDutyShareControlGrid.locked = 0;
		ConfDutyShareControlGrid.canSel = 0;
		ConfDutyShareControlGrid.canChk = 0;
		ConfDutyShareControlGrid.hiddenSubtraction = 1;
		ConfDutyShareControlGrid.hiddenPlus = 1;
		ConfDutyShareControlGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
/*******************************给付控制****************************************/
function initPlan3Grid() {
	
	turnPage8 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其它说明";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		Plan3Grid = new MulLineEnter("fm", "Plan3Grid");
		Plan3Grid.mulLineCount = 0;
		Plan3Grid.displayTitle = 1;
		Plan3Grid.locked = 0;
		Plan3Grid.canSel = 1;
		Plan3Grid.canChk = 0;
		Plan3Grid.hiddenSubtraction = 1;
		Plan3Grid.hiddenPlus = 1;
		Plan3Grid.selBoxEventFuncName = "showPlan3Info";
		Plan3Grid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initRiskDutyGrid() {
	
	turnPage9 = new turnPageClass();
	
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
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RiskDutyGrid = new MulLineEnter("fm", "RiskDutyGrid");
		RiskDutyGrid.mulLineCount = 0;
		RiskDutyGrid.displayTitle = 1;
		RiskDutyGrid.locked = 0;
		RiskDutyGrid.canSel = 1;
		RiskDutyGrid.canChk = 0;
		RiskDutyGrid.hiddenSubtraction = 1;
		RiskDutyGrid.hiddenPlus = 1;
		RiskDutyGrid.selBoxEventFuncName = "showGetControlInfo";
		RiskDutyGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initGetControlGrid() {
	
	turnPage10 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "给付责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "给付责任名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "理赔类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "理赔类型";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "等待期（天）";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "免赔方式";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][4] = "deductibletype";
		
		iArray[i] = new Array();
		iArray[i][0] = "免赔额";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "免赔天数";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "赔付比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "年限额";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "次限额";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "单位津贴额";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "津贴单位(D/M/Y)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][14] = "D";
		
		iArray[i] = new Array();
		iArray[i][0] = "计数限额(次/天)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "是否录入";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		GetControlGrid = new MulLineEnter("fm", "GetControlGrid");
		GetControlGrid.mulLineCount = 0;
		GetControlGrid.displayTitle = 1;
		GetControlGrid.locked = 0;
		GetControlGrid.canSel = 0;
		GetControlGrid.canChk = 0;
		GetControlGrid.hiddenSubtraction = 0;
		GetControlGrid.hiddenPlus = 1;
		GetControlGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
