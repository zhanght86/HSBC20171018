<%
/***************************************************************
 * <p>ProName：LSQuotUWRuleInit.jsp</p>
 * <p>Title：核保规则</p>
 * <p>Description：核保规则</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-04
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
		initUWRuleGrid();
		initEdorRuleGrid();
		queryUWRuleInfo();
		queryEdorRuleInfo();
		showPage('0');
		
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
		fm.RuleType.value = "";
		fm.RuleTypeName.value = "";
		fm.EdorCode.value = "";
		fm.EdorCodeName.value = "";
		fm.CalCode.value = "";
		fm.CalCodeName.value = "";
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
		if (tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "";
			fm.ModButton.style.display = "";
			fm.DelButton.style.display = "";
		} else {
			
			fm.SaveButton.style.display = "none";
			fm.AddButton.style.display = "none";
			fm.ModButton.style.display = "none";
			fm.DelButton.style.display = "none";
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

/**
 * 核保规则
 */
function initUWRuleGrid() {
	
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
		iArray[i][0] = "规则类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则类型";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "产品编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "产品名称";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则描述";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "参数";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "参考参数值";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "参数值";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		UWRuleGrid = new MulLineEnter("fm", "UWRuleGrid");
		UWRuleGrid.mulLineCount = 1;
		UWRuleGrid.displayTitle = 1;
		UWRuleGrid.locked = 0;
		UWRuleGrid.canSel = 1;
		UWRuleGrid.canChk = 0;
		UWRuleGrid.hiddenPlus = 1;
		UWRuleGrid.hiddenSubtraction = 1;
		//UWRuleGrid.selBoxEventFuncName = "";
		UWRuleGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}

/**
 * 保全规则列表
 */
function initEdorRuleGrid() {
	
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
		iArray[i][0] = "保全规则类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全规则类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目/类型描述编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全算法编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "算法描述";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "流水号";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		EdorRuleGrid = new MulLineEnter("fm", "EdorRuleGrid");
		EdorRuleGrid.mulLineCount = 0;
		EdorRuleGrid.displayTitle = 1;
		EdorRuleGrid.locked = 0;
		EdorRuleGrid.canSel = 1;
		EdorRuleGrid.canChk = 0;
		EdorRuleGrid.hiddenPlus = 1;
		EdorRuleGrid.hiddenSubtraction = 1;
		EdorRuleGrid.selBoxEventFuncName = "showEdorRuleInfo";
		EdorRuleGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}
</script>
