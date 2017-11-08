<%
/***************************************************************
 * <p>ProName：LDUWPopedomInit.jsp</p>
 * <p>Title：核保权限管理</p>
 * <p>Description：核保权限管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-06-26
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
		initUWPopedomGrid();
		
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
		fm.PopedomLevel1.value = "";
		fm.PopedomName1.value = "";
		fm.PopedomLevel.value = "";
		fm.PopedomName.value = "";
		fm.PerLifeAmnt.value = "";
		fm.PerAcciAmnt.value = "";
		fm.PerIllAmnt.value = "";
		fm.PerMedAmnt.value = "";
		fm.PremScale.value = "";
		fm.MainPremRateFloat.value = "";
		fm.MedPremRateFloat.value = "";
		fm.ValDate.value = "";
		fm.EndDate.value = "";
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
 * 退费信息列表
 */
function initUWPopedomGrid() {
	
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
		iArray[i][0] = "权限级别";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "权限名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人寿险保额";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人意外险保额";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人重疾保额";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 30;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人医疗险保额";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费规模";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "主险费率浮动";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "医疗险费率浮动";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		
		iArray[i] = new Array();
		iArray[i][0] = "生效日期";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "终止日期";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		UWPopedomGrid = new MulLineEnter("fm", "UWPopedomGrid");
		UWPopedomGrid.mulLineCount = 0;
		UWPopedomGrid.displayTitle = 1;
		UWPopedomGrid.locked = 0;
		UWPopedomGrid.canSel = 1;
		UWPopedomGrid.canChk = 0;
		UWPopedomGrid.hiddenSubtraction = 1;
		UWPopedomGrid.hiddenPlus = 1;
		UWPopedomGrid.selBoxEventFuncName = "showUWPopedomInfo";
		UWPopedomGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
