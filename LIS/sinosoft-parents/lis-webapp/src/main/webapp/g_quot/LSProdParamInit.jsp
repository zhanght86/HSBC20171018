<%
/***************************************************************
 * <p>ProName：LSProdParamInit.jsp</p>
 * <p>Title：产品参数信息维护--管理费维护</p>
 * <p>Description：产品参数信息维护--管理费维护</p>
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
		initManageFeeGrid();
		queryManageFee();
		
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
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		if (tActivityID=="0800100001" || tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			fm2.AddButton.style.display = "";
			fm2.ModButton.style.display = "";
			fm2.DelButton.style.display = "";
		} else {
			
			fm2.AddButton.style.display = "none";
			fm2.ModButton.style.display = "none";
			fm2.DelButton.style.display = "none";
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
</script>
