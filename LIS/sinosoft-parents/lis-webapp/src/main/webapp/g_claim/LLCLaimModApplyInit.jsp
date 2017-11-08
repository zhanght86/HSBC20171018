<%
/***************************************************************
 * <p>ProName：LLCLaimModApplyInit.jsp</p>
 * <p>Title：保项修改分公司申请申请界面</p>
 * <p>Description：保项修改分公司申请申请界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王超
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
%>
<script language="JavaScript">
 var conditionCode = "1";
/**
 * 初始化界面
 */

function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		initQueryModItemGrid();
		initNotGrpGrid();
		initAcceptGrpGrid();
		initAcceptGrpPlanGrid();
		initPlanGrid();
		

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
		
		document.all("UpAdjustRuleTitle").style.display = "none";
		document.all("UpAdjustRuleInput").style.display = "none";
		document.all("UpAdjustRateTitle").style.display = "none";
		document.all("UpAdjustRateInput").style.display = "none";
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
//保项修改列表
function initQueryModItemGrid() {

	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="机构编码";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保项修改原因编码";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保项修改原因";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="规则类型编码";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="规则类型";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="险种编码";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="险种名称";
		iArray[i][1]="130px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="调整方向编码";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="调整方向名称";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="向上调整规则编码";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="向上调整规则名称";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="向上调整比例";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="保项修改生效起期";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保项修改生效止期";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="申请调整原因";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="申请日期";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="状态";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="申请编码";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="申请批次号";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="复核结论";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
			
		QueryModItemGrid = new MulLineEnter("fm", "QueryModItemGrid");
		QueryModItemGrid.mulLineCount = 0;
		QueryModItemGrid.displayTitle = 1;
    	QueryModItemGrid.locked = 0;
		QueryModItemGrid.canSel = 1;
		QueryModItemGrid.hiddenPlus=1;
		QueryModItemGrid.hiddenSubtraction=1;
		QueryModItemGrid.selBoxEventFuncName = "showClmModApplyInfo";
		QueryModItemGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("初始化界面错误!");
	}  
}
//未选择保单信息列表
function initNotGrpGrid(){
    
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="承保机构";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单生效日期";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单状态";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单生效止期";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		NotGrpGrid = new MulLineEnter("fm", "NotGrpGrid");
		NotGrpGrid.mulLineCount = 0;
		NotGrpGrid.displayTitle = 1;
		NotGrpGrid.locked = 0;
		NotGrpGrid.canChk = 1;		
		NotGrpGrid.hiddenPlus=1;
		NotGrpGrid.hiddenSubtraction=1;
		NotGrpGrid.selBoxEventFuncName = "";
		NotGrpGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("初始化界面错误!");
	}  
}

//已选择保单信息列表
function initAcceptGrpGrid(){
    
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="承保机构";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单生效日期";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单状态";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="申请编码";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="批次号";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="保单类别";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		

		
		AcceptGrpGrid = new MulLineEnter("fm", "AcceptGrpGrid");
		AcceptGrpGrid.mulLineCount = 0;
		AcceptGrpGrid.displayTitle = 1;
		AcceptGrpGrid.locked = 0;
		AcceptGrpGrid.canChk = 1;		
		AcceptGrpGrid.hiddenPlus=1;
		AcceptGrpGrid.hiddenSubtraction=1;
		AcceptGrpGrid.selBoxEventFuncName = "";
		AcceptGrpGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("初始化界面错误!");
	}  
}

//已选择保单信息列表
function initAcceptGrpPlanGrid(){
    
	turnPage4 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="承保机构";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单生效日期";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单状态";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="申请编码";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="批次号";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="保单类别";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		

		
		AcceptGrpPlanGrid = new MulLineEnter("fm", "AcceptGrpPlanGrid");
		AcceptGrpPlanGrid.mulLineCount = 0;
		AcceptGrpPlanGrid.displayTitle = 1;
		AcceptGrpPlanGrid.locked = 0;
		AcceptGrpPlanGrid.canSel = 1;
		AcceptGrpPlanGrid.canChk = 1;
		AcceptGrpPlanGrid.hiddenPlus=1;		
		AcceptGrpPlanGrid.hiddenSubtraction=1;
		AcceptGrpPlanGrid.selBoxEventFuncName = "queryPlan";
		AcceptGrpPlanGrid.loadMulLine(iArray);
		
	} catch(ex) {
		alert("初始化界面错误!");
	}  
}

//方案信息
function initPlanGrid(){
        
	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="系统方案编码";
		iArray[i][1]="0px";
		iArray[i][2]=10;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="方案编码";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="方案名称";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="方案描述";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="是否保存";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
				
		PlanGrid = new MulLineEnter("fm", "PlanGrid");
		PlanGrid.mulLineCount = 0;
		PlanGrid.displayTitle = 1;
		PlanGrid.locked = 0;
		PlanGrid.canSel = 0;
		PlanGrid.canChk = 0;
		PlanGrid.hiddenPlus=1;
		PlanGrid.hiddenSubtraction=0;
		PlanGrid.selBoxEventFuncName = "";
		PlanGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("初始化界面错误!");
	}  
}
</script>
