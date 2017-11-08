<%
/***************************************************************
 * <p>ProName：GEdorBIDetail.jsp</p>
 * <p>Title：银行信息变更</p>
 * <p>Description：银行信息变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initButton();
		initOldInsuredInfoGrid();
		initUpdateInsuredInfoGrid();
		initFSGrid();
		queryUpClick(2);
		
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
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
		if(tActivityID=='1800401001'){
			divButton02.style.display='';
			divQueryOld.style.display='';
			fm.BatchNo.value = tEdorAppNo;
		}else {
			divButton02.style.display='none';
			divQueryOld.style.display='none';
			fm.BatchNo.value ="";
		}
		
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}


function initOldInsuredInfoGrid() {
	
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
		iArray[i][0] = "个人保单号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人姓名";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险方案";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险方案编码";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单生效日期";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人客户号";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "流水号";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "银行编码";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "银行名称";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户名称";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "账号";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户行所在省";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户行所在省名称";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户行所在市";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户行所在市名称";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "手机号";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		OldInsuredInfoGrid = new MulLineEnter("fm", "OldInsuredInfoGrid");
		OldInsuredInfoGrid.mulLineCount = 0;
		OldInsuredInfoGrid.displayTitle = 1;
		OldInsuredInfoGrid.locked = 1;
		OldInsuredInfoGrid.canSel = 1;
		OldInsuredInfoGrid.canChk = 0;
		OldInsuredInfoGrid.hiddenSubtraction = 1;
		OldInsuredInfoGrid.hiddenPlus = 1;
		OldInsuredInfoGrid.selBoxEventFuncName = "showBankInfo";
		OldInsuredInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}


function initUpdateInsuredInfoGrid() {
	
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
		iArray[i][0] = "流水号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人姓名";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保全生效日期";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "校验状态";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "校验结果描述";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		UpdateInsuredInfoGrid = new MulLineEnter("fm", "UpdateInsuredInfoGrid");
		UpdateInsuredInfoGrid.mulLineCount = 0;
		UpdateInsuredInfoGrid.displayTitle = 1;
		UpdateInsuredInfoGrid.locked = 0;
		UpdateInsuredInfoGrid.canSel = 1;
		UpdateInsuredInfoGrid.canChk = 0;
		UpdateInsuredInfoGrid.hiddenSubtraction = 1;
		UpdateInsuredInfoGrid.hiddenPlus = 1;
		UpdateInsuredInfoGrid.selBoxEventFuncName = "showUpInfo";
		UpdateInsuredInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initFSGrid() {
	
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
		iArray[i][0] = "与被保险人关系";
		iArray[i][1] = "00px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "与被保险人关系";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人姓名";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别编码";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型编码";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保险人客户号";
		iArray[i][1] = "4px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		FSGrid = new MulLineEnter("fm", "FSGrid");
		FSGrid.mulLineCount = 0;
		FSGrid.displayTitle = 1;
		FSGrid.locked = 1;
		FSGrid.canSel = 0;
		FSGrid.canChk = 1;
		FSGrid.hiddenSubtraction = 1;
		FSGrid.hiddenPlus = 1;
		FSGrid.selBoxEventFuncName = "";
		FSGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
