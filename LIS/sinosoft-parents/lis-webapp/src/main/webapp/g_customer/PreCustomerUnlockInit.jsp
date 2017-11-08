<%
/***************************************************************
 * <p>ProName：PreCustomerUnlockInit.jsp</p>
 * <p>Title：准客户解锁界面</p>
 * <p>Description：准客户解锁界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/
%>
<script language="JavaScript">
var isTraceFlag = 0;

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		if (tTraceID!=null && tTraceID!="" && tTraceID!="null") {
			
			isTraceFlag = 1;
			divMainSaler.style.display = "none";
			fm.UnlockButton.style.display = "none";
			fm.ModifyButton.style.display = "none";
		} else {
			
			divMainSaler.style.display = "";
			fm.UnlockButton.style.display = "";
			fm.ModifyButton.style.display = "";
		}
		
		initSalerGrid();
		queryDetail();
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

/**
 * 初始化管理机构列表
 */
function initSalerGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户经理代码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][7] = "queryManager";
		
		iArray[i] = new Array();
		iArray[i][0] = "客户经理姓名";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "分支机构";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		SalerGrid = new MulLineEnter("fm", "SalerGrid");
		SalerGrid.mulLineCount = 0;
		SalerGrid.displayTitle = 1;
		SalerGrid.locked = isTraceFlag;
		SalerGrid.canSel = 0;
		SalerGrid.canChk = 0;
		SalerGrid.hiddenPlus = isTraceFlag;
		SalerGrid.hiddenSubtraction = isTraceFlag;
		SalerGrid.selBoxEventFuncName = "";
		SalerGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化SalerGrid时出错："+ ex);
	}
}
</script>
