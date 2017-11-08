<%
/***************************************************************
 * <p>ProName：LCGrpChargeFeeInit.jsp</p>
 * <p>Title：中介手续费维护</p>
 * <p>Description：中介手续费维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-05
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
		initFeeRateInfoGrid();
		initZJGrid();
		queryPropInfo();
		
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
		fm.GrpPropNo.value=tGrpPropNo;
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		if(tFlag=='0'){
			fm.FeeRateSaveButton.style.display='';
		}else if(tFlag=='1' ||tFlag=='3'){
			fm.FeeRateSaveButton.style.display='none';
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

function initFeeRateInfoGrid() {
	
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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "手续费比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "参考手续费比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种期望手续费比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		
		
		FeeRateInfoGrid = new MulLineEnter("fm", "FeeRateInfoGrid");
		FeeRateInfoGrid.mulLineCount = 0;
		FeeRateInfoGrid.displayTitle = 1;
		FeeRateInfoGrid.locked = 0;
		FeeRateInfoGrid.canSel = 1;
		FeeRateInfoGrid.canChk = 0;
		FeeRateInfoGrid.hiddenSubtraction = 1;
		FeeRateInfoGrid.hiddenPlus = 1;
		FeeRateInfoGrid.selBoxEventFuncName = "queryAgentInfo";
		FeeRateInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
function initZJGrid() {
	
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
		iArray[i][0] = "中介机构编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介手续费比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][9] = "中介手续费比例|NUM";
		
		iArray[i] = new Array();
		iArray[i][0] = "参考手续费比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介期望手续费比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		ZJGrid = new MulLineEnter("fm", "ZJGrid");
		ZJGrid.mulLineCount =0;
		ZJGrid.displayTitle = 1;
		ZJGrid.locked = 0;
		ZJGrid.canSel = 0;
		ZJGrid.canChk = 0;
		ZJGrid.hiddenSubtraction = 1;
		ZJGrid.hiddenPlus = 1;
		ZJGrid.selBoxEventFuncName = "";
		ZJGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
 
