<%
/***************************************************************
 * <p>ProName：EdorAcceptDetailInit.jsp</p>
 * <p>Title：保全受理</p>
 * <p>Description：保全受理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
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
		initEdorTypeGrid();
		
		queryEdorAppInfo();
		if (fm.AppMode.value!="") {
			
			queryPolicyInfo();
			queryEdorTypeInfo();
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
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
		
		fm.PolicyNo.value = "";
		fm.AppntName.value = "";
		fm.AppDate.value = "";
		fm.ReceiveDate.value = "";
		fm.AppMode.value = "";
		fm.AppModeName.value = "";
		
		fm.EdorType.value = "";
		fm.EdorTypeName.value = "";
		fm.EdorValDate.value = "";
		fm.GetObj.value = "";
		fm.GetObjName.value = "";
		
		divEdorValDateTitle.style.display = "none";
		divEdorValDateInput.style.display = "none";
		divGetObjTitle.style.display = "none";
		divGetObjInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
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
function initEdorTypeGrid() {
	
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
		iArray[i][0] = "保全项目编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目算法";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全生效日期";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目级别";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全操作节点";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "是否需要录入生效日期";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "退费支付方式编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "退费支付方式";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		EdorTypeGrid = new MulLineEnter("fm", "EdorTypeGrid");
		EdorTypeGrid.mulLineCount = 0;
		EdorTypeGrid.displayTitle = 1;
		EdorTypeGrid.locked = 1;
		EdorTypeGrid.canSel = 1;
		EdorTypeGrid.canChk = 0;
		EdorTypeGrid.hiddenPlus = 1;
		EdorTypeGrid.hiddenSubtraction = 1;
		EdorTypeGrid.selBoxEventFuncName = "showEdorType";
		EdorTypeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化EdorTypeGrid时出错："+ ex);
	}
}
</script>
