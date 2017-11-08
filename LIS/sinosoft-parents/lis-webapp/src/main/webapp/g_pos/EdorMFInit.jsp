<%
/***************************************************************
 * <p>ProName：EdorMFInit.jsp</p>
 * <p>Title：长险费用变更</p>
 * <p>Description：长险费用变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-16
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
		initManageFeeGrid();
		queryManageFee();
		initRefundListGrid();
		initSysRefundInfoGrid();
		querySysRefundInfo();
		
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
		fm2.RiskCode.value = "";
		fm2.RiskName.value = "";
		fm2.AccType.value = "";
		fm2.AccTypeName.value = "";
		fm2.FeeType.value = "";
		fm2.FeeTypeName.value = "";
		fm2.DeductType.value = "";
		fm2.DeductTypeName.value = "";
		fm2.FeeValue.value = "";
		fm2.MonthFeeType.value = "";
		fm2.MonthFeeTypeName.value = "";
		fm2.MonthValue.value = "";
		fm2.YearFeeType.value = "";
		fm2.YearFeeTypeName.value = "";
		fm2.YearStartValue.value = "";
		fm2.YearEndValue.value = "";
		fm2.YearValue.value = "";
		fm3.RiskCode1.value = "";
		fm3.RiskName1.value = "";
		fm3.GetType1.value = "";
		fm3.GetTypeName1.value = "";
		fm3.RiskCode2.value = "";
		fm3.RiskName2.value = "";
		fm3.GetType2.value = "";
		fm3.GetTypeName2.value = "";
		fm3.ValPeriod.value = "";
		fm3.ValPeriodName.value = "";
		fm3.ValStartDate.value = "";
		fm3.ValEndDate.value = "";
		fm3.FeeValues.value = "";
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		if(tActivityID=='1800401002'){
			divInfoButton1.style.display='';
			divInfoButton2.style.display='';
		} else {
			divInfoButton1.style.display='none';
			divInfoButton2.style.display='none';
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
 * 已维护管理费信息列表
 */
function initManageFeeGrid() {
	
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
		iArray[i][0] = "险种";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "账户类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
				
		iArray[i] = new Array();
		iArray[i][0] = "管理费类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "管理费扣除方式/类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "管理费金额(元)/比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "年度起始值(≥)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "年度终止值(<)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "账户类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "管理费类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "管理费扣除方式/类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		ManageFeeGrid = new MulLineEnter("fm2", "ManageFeeGrid");
		ManageFeeGrid.mulLineCount = 0;
		ManageFeeGrid.displayTitle = 1;
		ManageFeeGrid.locked = 0;
		ManageFeeGrid.canSel = 1;
		ManageFeeGrid.canChk = 0;
		ManageFeeGrid.hiddenSubtraction = 1;
		ManageFeeGrid.hiddenPlus = 1;
		ManageFeeGrid.selBoxEventFuncName = "showManageFee";
		ManageFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
/**
 * 退费信息列表
 */
function initRefundListGrid() {
	
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
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "退费类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "起始值";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "终止值";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "单位";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "费用比例";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "退费类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "单位编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		RefundListGrid = new MulLineEnter("fm3", "RefundListGrid");
		RefundListGrid.mulLineCount = 0;
		RefundListGrid.displayTitle = 1;
		RefundListGrid.locked = 0;
		RefundListGrid.canSel = 1;
		RefundListGrid.canChk = 0;
		RefundListGrid.hiddenSubtraction = 1;
		RefundListGrid.hiddenPlus = 1;
		RefundListGrid.selBoxEventFuncName = "showRefundList";
		RefundListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 系统默认退费信息列表
 */
function initSysRefundInfoGrid() {
	
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
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "退费类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "起始值";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "终止值";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "单位";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "费用比例";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "退费类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "单位编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		SysRefundInfoGrid = new MulLineEnter("fm3", "SysRefundInfoGrid");
		SysRefundInfoGrid.mulLineCount = 0;
		SysRefundInfoGrid.displayTitle = 1;
		SysRefundInfoGrid.locked = 0;
		SysRefundInfoGrid.canSel = 0;
		SysRefundInfoGrid.canChk = 0;
		SysRefundInfoGrid.hiddenSubtraction = 1;
		SysRefundInfoGrid.hiddenPlus = 1;
		SysRefundInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
