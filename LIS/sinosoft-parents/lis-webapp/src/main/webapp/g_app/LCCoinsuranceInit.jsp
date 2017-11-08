<%
/***************************************************************
 * <p>ProName：LCCoinsuranceInit.jsp</p>
 * <p>Title：共保设置</p>
 * <p>Description：共保设置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-03
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
		
		initCoinsuranceGrid();
		queryCoinsuranceInfo();
		
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
		fm.MasterSlaveFlag.value = "";
		fm.MasterSlaveName.value = "";
		fm.CoinComCode.value = "";
		fm.CoinComName.value = "";
		fm.AmntShareRate.value = "";
		fm.PremShareRate.value = "";
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
			divButton.style.display = "";
		}else if(tFlag=='1'){
			divButton.style.display='none';
		}else if(tFlag=='3'){
			divButton.style.display='none';
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
 * 共保设置
 */
function initCoinsuranceGrid() {
	
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
		iArray[i][0] = "流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保主/从方标识编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保主/从方标识";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保公司编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "共保公司";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额分摊比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费分摊比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		CoinsuranceGrid = new MulLineEnter("fm", "CoinsuranceGrid");
		CoinsuranceGrid.mulLineCount = 0;
		CoinsuranceGrid.displayTitle = 1;
		CoinsuranceGrid.locked = 1;
		CoinsuranceGrid.canSel = 1;
		CoinsuranceGrid.canChk = 0;
		CoinsuranceGrid.hiddenPlus = 1;
		CoinsuranceGrid.hiddenSubtraction = 1;
		CoinsuranceGrid.selBoxEventFuncName = "showCoinInfo";
		CoinsuranceGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化时出错："+ ex);
	}
}

</script>
