<%
/***************************************************************
 * <p>ProName：LCInsuredInfoInit.jsp</p>
 * <p>Title：人员清单维护</p>
 * <p>Description：人员清单维护</p>
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
		
		initInpBox();
		initQueryScanGrid();
		//InsuredQuery();
	
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
		
		fm.GrpPropNo.value=tGrpPropNo;
		fm.MissionID.value=tMissionID;
		fm.SubMissionID.value=tSubMissionID;
		fm.ActivityID.value=tActivityID;
		fm.Flag.value=tFlag;
		tContPlanType = getContPlanType(tGrpPropNo);
		fm.ContPlanType.value=tContPlanType;
		fm.CurrentDate.value=tCurrentDate;
		
		divButton.style.display="";
		divUplond.style.display="";
		divButton2.style.display="none";
		divConfime.style.display="";
		
		// 投保书人员清单维护
		if(tFlag =='00'){ 
			divConfime.style.display="none";
			divConfimeC.style.display="";	
		}else if(tFlag =='01'){
			divButton.style.display="none";
			divUplond.style.display="none";
			divButton2.style.display="";
		}
	initContPlanQuery();
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

function initQueryScanGrid() {
	
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
		iArray[i][0] = "被保险人客户号";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人个人保单号";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "与主被保险人关系";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "被保险人姓名";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "年龄";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险方案";
		iArray[i][1] = "130px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费（元）";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人编码";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "主被保险人编码";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人类型";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
			
		QueryScanGrid = new MulLineEnter("fm", "QueryScanGrid");
		QueryScanGrid.mulLineCount = 0;
		QueryScanGrid.displayTitle = 1;
		QueryScanGrid.locked = 1;
		QueryScanGrid.canSel = 1;
		if(fm.Flag.value=='01'){
			QueryScanGrid.canChk = 0;
		}else{
			QueryScanGrid.canChk = 1;
		}
		QueryScanGrid.hiddenSubtraction = 1;
		QueryScanGrid.hiddenPlus = 1;
		QueryScanGrid.selBoxEventFuncName = "goToInfo"; 
		QueryScanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
