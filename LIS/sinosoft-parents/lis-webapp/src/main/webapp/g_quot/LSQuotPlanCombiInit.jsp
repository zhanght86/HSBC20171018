<%
/***************************************************************
 * <p>ProName：LSQuotPlanCombiInit.jsp</p>
 * <p>Title：方案组合配置</p>
 * <p>Description：方案组合配置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-02
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
		initNoPlanCombiGrid();
		initPlanListGrid();
		
		queryPlanList();//查询方案列表
		queryNoPlanCombi();
		
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
		fm.CombiType.value = "";
		fm.CombiTypeName.value = "";
		fm.LimitType.value = "";
		fm.LimitTypeName.value = "";
		
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
			
			fm.DelButton.style.display = "";
			fm.AddButton.style.display = "";
		} else {
			
			fm.DelButton.style.display = "none";
			fm.AddButton.style.display = "none";
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
 * 不允许的方案组合
 */
function initNoPlanCombiGrid() {
	
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
		iArray[i][0] = "序号1";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案组合";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "限制范围";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "组合类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		NoPlanCombiGrid = new MulLineEnter("fm", "NoPlanCombiGrid");
		NoPlanCombiGrid.mulLineCount = 0;
		NoPlanCombiGrid.displayTitle = 1;
		NoPlanCombiGrid.locked = 1;
		NoPlanCombiGrid.canSel = 1;
		NoPlanCombiGrid.canChk = 0;
		NoPlanCombiGrid.hiddenPlus = 1;
		NoPlanCombiGrid.hiddenSubtraction = 1;
		//NoPlanCombiGrid.selBoxEventFuncName = "";
		NoPlanCombiGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initPlanListGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "23px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i++][7] = "showPlanDetail";
		//iArray[i++][24] = "blue";
		
		iArray[i] = new Array();
		iArray[i][0] = "方案描述";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;	
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;	
		
		PlanListGrid = new MulLineEnter("fm", "PlanListGrid");
		PlanListGrid.mulLineCount = 0;
		PlanListGrid.displayTitle = 1;
		PlanListGrid.locked = 1;
		PlanListGrid.canSel = 0;
		PlanListGrid.canChk = 1;
		PlanListGrid.hiddenPlus = 1;
		PlanListGrid.hiddenSubtraction = 1;
		//PlanListGrid.selBoxEventFuncName = "";
		PlanListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}
</script>
