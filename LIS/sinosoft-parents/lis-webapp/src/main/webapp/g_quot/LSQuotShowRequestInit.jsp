<%
/***************************************************************
 * <p>ProName：LSQuotShowRequestInit.jsp</p>
 * <p>Title：业务需求</p>
 * <p>Description：业务需求</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-26
 ****************************************************************/
%>
<script language="JavaScript">
var tSubUWFlag = 0;//是否经过中支公司核保标记，0-否，1-是

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
		tSQLInfo.setSqlId("LSQuotUWSql20");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			tSubUWFlag = 1;
		}
		
		initRequestGrid();
		queryClick();
		
		if (tSubUWFlag==1) {
			divSubUWOpinion.style.display = "";
		} else {
			divSubUWOpinion.style.display = "none";
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
		
		divOtherTypeDescTitle.style.display = "none";
		divOtherTypeDescInput.style.display = "none";
		divRiskTitle.style.display = "none";
		divRiskInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "";
		divTD4.style.display = "";
		
		fm.RequestType.value = "";
		fm.RequestTypeName.value = "";
		fm.OtherTypeDesc.value = "";
		fm.RiskCode.value = "";
		fm.RiskName.value = "";
		fm.RequestDesc.value = "";
		fm.SubUWOpinion.value = "";
		fm.BranchUWOpinion.value = "";
		
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
function initRequestGrid() {
	
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
		iArray[i][0] = "业务需求流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务需求分类代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务需求分类";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "其他分类描述";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务需求内容";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		if (tSubUWFlag==1) {
			
			iArray[i] = new Array();
			iArray[i][0] = "中支公司核保意见";
			iArray[i][1] = "60px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else {
			
			iArray[i] = new Array();
			iArray[i][0] = "中支公司核保意见";
			iArray[i][1] = "60px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
			
		iArray[i] = new Array();
		iArray[i][0] = "分公司核保意见";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "系统方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		RequestGrid = new MulLineEnter("fm", "RequestGrid");
		RequestGrid.mulLineCount = 0;
		RequestGrid.displayTitle = 1;
		RequestGrid.locked = 0;
		RequestGrid.canSel = 1;
		RequestGrid.canChk = 0;
		RequestGrid.hiddenPlus = 1;
		RequestGrid.hiddenSubtraction = 1;
		RequestGrid.selBoxEventFuncName = "showDetail";
		RequestGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化RequestGrid时出错："+ ex);
	}
}
</script>
