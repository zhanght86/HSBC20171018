<%
/***************************************************************
 * <p>ProName：LCGrpApproveListSave.jsp</p>
 * <p>Title：复核任务池</p>
 * <p>Description：复核任务池</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 甸景
 * @version  : 8.0
 * @date     : 2014-04-14
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
		initGrpApproveListGrid();
		initSelfGrpApproveListGrid();
		
		querySelfClick();
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
 * 待录入复核查询
 */
function initGrpApproveListGrid() {
	
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
		iArray[i][0] = "任务ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "子任务ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ActivityID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "承保机构";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单号";
		iArray[i][1] = "60px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "扫描日期";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "扫描时间";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "时效天数(工作日)";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		 
		GrpApproveListGrid = new MulLineEnter("fm", "GrpApproveListGrid");
		GrpApproveListGrid.mulLineCount = 0;
		GrpApproveListGrid.displayTitle = 1;
		GrpApproveListGrid.locked = 1;
		GrpApproveListGrid.canSel = 1;
		GrpApproveListGrid.canChk = 0;
		GrpApproveListGrid.hiddenSubtraction = 1;
		GrpApproveListGrid.hiddenPlus = 1;
		GrpApproveListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 个人池
 */
function initSelfGrpApproveListGrid() {
	
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
		iArray[i][0] = "任务ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "子任务ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ActivityID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ContPlanType";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "承保机构";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保单号";
		iArray[i][1] = "60px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "申请日期";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "申请时间";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "时效天数(工作日)";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "问题处理状态";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保书号";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		SelfGrpApproveListGrid = new MulLineEnter("fm", "SelfGrpApproveListGrid");
		SelfGrpApproveListGrid.mulLineCount = 0;
		SelfGrpApproveListGrid.displayTitle = 1;
		SelfGrpApproveListGrid.locked = 1;
		SelfGrpApproveListGrid.canSel = 1;
		SelfGrpApproveListGrid.canChk = 0;
		SelfGrpApproveListGrid.hiddenSubtraction = 1;
		SelfGrpApproveListGrid.hiddenPlus = 1;
		SelfGrpApproveListGrid.selBoxEventFuncName = "";
		SelfGrpApproveListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
