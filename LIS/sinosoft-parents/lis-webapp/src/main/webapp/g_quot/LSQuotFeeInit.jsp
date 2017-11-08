<%
/***************************************************************
 * <p>ProName：LSQuotFeeInit.jsp</p>
 * <p>Title：费用信息</p>
 * <p>Description：费用信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<script language="JavaScript">
var tAgencyFlag = 0;//是否含中介机构标记，0-否，1-是
var tAgencyInputFlag = 1;
/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
		tSQLInfo.setSqlId("LSQuotFeeSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			tAgencyFlag = 1;
		}
		
		initRiskFeeGrid();
		//initChargeFeeGrid();
		initOtherFeeGrid();
		
		if (tActivityID == "0800100001") {
			
			if (tAgencyFlag==0) {
				divFee.style.display = "none";
			} else {
				divFee.style.display = "";
			}
			divWeightFee.style.display = "none";
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
		} else if (tActivityID == "0800100002") {
			
			divFee.style.display = "";
			divWeightFee.style.display = "";
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "none";
		} else if (tActivityID == "0800100003") {
			
			divFee.style.display = "";
			divWeightFee.style.display = "";
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "none";
		} else {
			
			divFee.style.display = "";
			divWeightFee.style.display = "";
			
			fm.SaveButton.style.display = "none";
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
		}
		
		//展示费用率信息时才触发查询
		if (divFee.style.display=="") {
			queryRiskFee();
		}
		//展示加权费用率信息时才触发查询
		if (divWeightFee.style.display=="") {
			queryWeightFee();
		}
		queryOtherFee();
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
 * 初始化列表
 */
function initRiskFeeGrid() {
	
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
		iArray[i][0] = "险种编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "参考佣金比例";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "期望佣金比例";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		//含中介机构时展示手续费，否则不展示
		if (tActivityID=="0800100001" || tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			if (tAgencyFlag==1) {
				
				iArray[i] = new Array();
				iArray[i][0] = "参考手续费比例";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
				
				iArray[i] = new Array();
				iArray[i][0] = "期望手续费比例";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 1;
			} else {
			
				iArray[i] = new Array();
				iArray[i][0] = "参考手续费比例";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
				
				iArray[i] = new Array();
				iArray[i][0] = "期望手续费比例";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
			}
		} else {
		
			if (tAgencyFlag==1) {
				
				iArray[i] = new Array();
				iArray[i][0] = "参考手续费比例";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
				
				iArray[i] = new Array();
				iArray[i][0] = "期望手续费比例";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
			} else {
			
				iArray[i] = new Array();
				iArray[i][0] = "参考手续费比例";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
				
				iArray[i] = new Array();
				iArray[i][0] = "期望手续费比例";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
			}
		}
		
		//录入环节不展示逾期理赔率、渠道公摊等比例，其他环节展示
		if (tActivityID=="0800100001") {
			
			iArray[i] = new Array();
			iArray[i][0] = "参考业务费用率";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "期望业务费用率";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		
			iArray[i] = new Array();
			iArray[i][0] = "预期理赔率";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "渠道公摊";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "税费";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "直接管理费";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "参考费用率合计";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "期望费用率合计";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "险种保费合计";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else {
			
			iArray[i] = new Array();
			iArray[i][0] = "参考业务费用率";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			if (tActivityID=="0800100002" || tActivityID=="0800100003") {
			
				iArray[i] = new Array();
				iArray[i][0] = "期望业务费用率";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 1;
				
				iArray[i] = new Array();
				iArray[i][0] = "预期理赔率";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 1;
			} else {
				
				iArray[i] = new Array();
				iArray[i][0] = "期望业务费用率";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
				
				iArray[i] = new Array();
				iArray[i][0] = "预期理赔率";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
			}
			
			iArray[i] = new Array();
			iArray[i][0] = "渠道公摊";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "税费";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "直接管理费";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "参考费用率合计";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "期望费用率合计";
			iArray[i][1] = "45px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "险种保费合计";
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		RiskFeeGrid = new MulLineEnter("fm", "RiskFeeGrid");
		RiskFeeGrid.mulLineCount = 0;
		RiskFeeGrid.displayTitle = 1;
		RiskFeeGrid.locked = 0;
		RiskFeeGrid.canSel = 1;
		RiskFeeGrid.canChk = 0;
		RiskFeeGrid.hiddenPlus = 1;
		RiskFeeGrid.hiddenSubtraction = 1;
		//RiskFeeGrid.selBoxEventFuncName = "showRiskFeeDetail";
		RiskFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化RiskFeeGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initChargeFeeGrid() {
	
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
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介期望手续费比例";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		ChargeFeeGrid = new MulLineEnter("fm", "ChargeFeeGrid");
		ChargeFeeGrid.mulLineCount = 0;
		ChargeFeeGrid.displayTitle = 1;
		ChargeFeeGrid.locked = 0;
		ChargeFeeGrid.canSel = 0;
		ChargeFeeGrid.canChk = 0;
		ChargeFeeGrid.hiddenPlus = 1;
		ChargeFeeGrid.hiddenSubtraction = 1;
		ChargeFeeGrid.selBoxEventFuncName = "";
		ChargeFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化ChargeFeeGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initOtherFeeGrid() {
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其他费用流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "费用编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "费用名称";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其他费用名称";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "费用值";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "备注";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		OtherFeeGrid = new MulLineEnter("fm", "OtherFeeGrid");
		OtherFeeGrid.mulLineCount = 0;
		OtherFeeGrid.displayTitle = 1;
		OtherFeeGrid.locked = 0;
		OtherFeeGrid.canSel = 1;
		OtherFeeGrid.canChk = 0;
		OtherFeeGrid.hiddenPlus = 1;
		OtherFeeGrid.hiddenSubtraction = 1;
		OtherFeeGrid.selBoxEventFuncName = "showOtherFeeDetail";
		OtherFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化OtherFeeGrid时出错："+ ex);
	}
}
</script>
