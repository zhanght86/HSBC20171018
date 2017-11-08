<%
/***************************************************************
 * <p>ProName：EdorUWGErrInit.jsp</p>
 * <p>Title：核保处理</p>
 * <p>Description：核保处理</p>
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
		initUWGCErrGrid();
		initUWContErrGrid();
		initUWGErrGrid();
		initCCErrGrid();
		initCpolErrGrid();
		initCpolTErrGrid();
		queryUWErr();

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
		
		divUWGErr1.style.display = 'none';
		divUWGErr2.style.display = 'none';
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
		tSQLInfo.setSqlId("EdorUWGErrSql10");
		tSQLInfo.addSubPara(tGrpPropNo);
		tSQLInfo.addSubPara(tEdorNo);
		
		var arrRrsult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if(arrRrsult != null){
			showReins.style.display = '';	
			fm.ReinsFlag.value = "1";
		}else {
			showReins.style.display = 'none';
			fm.ReinsFlag.value = "0";
		}
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
 * 保单自动核保信息
 */
function initUWGCErrGrid() {
	
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
		iArray[i][0] = "规则编码";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "保单号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "核保顺序号";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则层级";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则层级代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "规则类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "团体险种编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "险种";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "自动核保规则";
		iArray[i][1] = "260px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "自动自核日期";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保结论编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保结论";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保意见";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		UWGCErrGrid = new MulLineEnter("fm", "UWGCErrGrid");
		UWGCErrGrid.mulLineCount = 2;
		UWGCErrGrid.displayTitle = 1;
		UWGCErrGrid.locked = 1;
		UWGCErrGrid.canSel = 1;
		UWGCErrGrid.canChk = 0;
		UWGCErrGrid.hiddenSubtraction = 1;
		UWGCErrGrid.hiddenPlus = 1;
		UWGCErrGrid.selBoxEventFuncName = "showUWGErrGrid";
		UWGCErrGrid.loadMulLine(iArray);
		
	} catch(ex) {
		alert("初始化界面错误!");
	}
}

/**
 * 被保险人信息
 */
function initUWContErrGrid() {
	
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
		iArray[i][0] = "姓名";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
	
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "年龄";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		UWContErrGrid = new MulLineEnter("fm", "UWContErrGrid");
		UWContErrGrid.mulLineCount = 1;
		UWContErrGrid.displayTitle = 1;
		UWContErrGrid.locked = 1;
		UWContErrGrid.canSel = 0;
		UWContErrGrid.canChk = 0;
		UWContErrGrid.hiddenSubtraction = 1;
		UWContErrGrid.hiddenPlus = 1;
		UWContErrGrid.selBoxEventFuncName = "";
		UWContErrGrid.loadMulLine(iArray);
		
	} catch(ex) {
	
		alert("初始化界面错误!");
	}
}


/**
 * 被保险人列表显示中被保人信息
 */
function initUWGErrGrid() {
	
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
		iArray[i][0] = "被保人客户号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人个人保单号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保顺序号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全项目名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
				
		iArray[i] = new Array();
		iArray[i][0] = "姓名";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
	
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "年龄";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "职业类别";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人核保结论编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人核保结论";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人核保结论意见";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		UWGErrGrid = new MulLineEnter("fm", "UWGErrGrid");
		UWGErrGrid.mulLineCount = 0;
		UWGErrGrid.displayTitle = 1;
		UWGErrGrid.canSel=1;
		UWGErrGrid.canChk=1;
		UWGErrGrid.hiddenPlus=1;
		UWGErrGrid.hiddenSubtraction=1;
		UWGErrGrid.selBoxEventFuncName = "showInsInfo";
		UWGErrGrid.loadMulLine(iArray);
		
	} catch(ex) {
	
		alert("初始化界面错误!");
	}
}

/**
 * 个人核保信息
 */
function initCCErrGrid() {
	
	turnPage4 = new turnPageClass();
	
	var iArray = new Array();
	
	var i = 0;
	
	try {
	
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保规则编号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "险种";
		iArray[i][1] = "130px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "自动核保规则";
		iArray[i][1] = "200px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保权限";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "自动核保日期";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		CCErrGrid = new MulLineEnter("fm", "CCErrGrid");
		CCErrGrid.mulLineCount = 0;
		CCErrGrid.displayTitle = 1;
		CCErrGrid.canSel=0;
		CCErrGrid.canChk=0;
		CCErrGrid.hiddenPlus=1;
		CCErrGrid.hiddenSubtraction=1;
		CCErrGrid.loadMulLine(iArray);
	} catch(ex) {
	
		alert("初始化界面错误!");
	}
}


/**
 * 个人险种责任信息
 */

function initCpolErrGrid() {
	
	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	
	var i = 0;
	
	try {
	
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人个人保单号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "方案编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人险种号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "原保费(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "原保额(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "调整后保费(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		iArray[i] = new Array();
		iArray[i][0] = "调整后保额(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人客户号";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人再保结论编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人再保结论";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		CpolErrGrid = new MulLineEnter("fm", "CpolErrGrid");
		CpolErrGrid.mulLineCount = 0;
		CpolErrGrid.displayTitle = 1;
		CpolErrGrid.canSel=0;
		CpolErrGrid.canChk=0;
		CpolErrGrid.hiddenPlus=1;
		CpolErrGrid.hiddenSubtraction=1;
		CpolErrGrid.loadMulLine(iArray);
	} catch(ex) {
	
		alert("初始化界面错误!");
	}
}

/**
 * 个人险种责任信息
 */

function initCpolTErrGrid() {
	
	turnPage6 = new turnPageClass();
	
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
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "原保费(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "原保额(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "退费金额(元)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人再保结论编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人再保结论";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		CpolTErrGrid = new MulLineEnter("fm", "CpolTErrGrid");
		CpolTErrGrid.mulLineCount = 0;
		CpolTErrGrid.displayTitle = 1;
		CpolTErrGrid.canSel=0;
		CpolTErrGrid.canChk=0;
		CpolTErrGrid.hiddenPlus=1;
		CpolTErrGrid.hiddenSubtraction=1;
		CpolTErrGrid.loadMulLine(iArray);
	} catch(ex) {	
		alert("初始化界面错误!");
	}
}
</script>
