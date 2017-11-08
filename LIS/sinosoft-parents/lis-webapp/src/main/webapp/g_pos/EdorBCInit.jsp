<%
/***************************************************************
 * <p>ProName:EdorBCInput.jsp</p>
 * <p>Title:  受益人维护</p>
 * <p>Description:受益人维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-12
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
		initInpBox();
		initParam();
		initOldInsuredInfoGrid();
		initUpdateInsuredInfoGrid();
		initBnfUpdateInfoGrid();
		queryUpdateClick(2);
		

	} catch (re) {
		alert("初始化界面错误!");
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
			divShowButton.style.display='';
			divQueryOld.style.display='';
			fm.BatchNo.value = tEdorAppNo;
		}else {
			divShowButton.style.display='none';
			divQueryOld.style.display='none';
			fm.BatchNo.value = "";
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
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		OldInsuredInfoGrid = new MulLineEnter("fm", "OldInsuredInfoGrid");
		OldInsuredInfoGrid.mulLineCount = 0;
		OldInsuredInfoGrid.displayTitle = 1;
		OldInsuredInfoGrid.locked = 0;
		OldInsuredInfoGrid.canSel = 1;
		OldInsuredInfoGrid.canChk = 0;
		OldInsuredInfoGrid.hiddenSubtraction = 1;
		OldInsuredInfoGrid.hiddenPlus = 1;
		OldInsuredInfoGrid.selBoxEventFuncName = "showOldBnfList";
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
		UpdateInsuredInfoGrid.locked = 1;
		UpdateInsuredInfoGrid.canSel = 1;
		UpdateInsuredInfoGrid.canChk = 0;
		UpdateInsuredInfoGrid.hiddenSubtraction = 1;
		UpdateInsuredInfoGrid.hiddenPlus = 1;
		UpdateInsuredInfoGrid.selBoxEventFuncName = "showUpdateInsuredList";
		UpdateInsuredInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

/**
* 修改后受益人信息
*/

function initBnfUpdateInfoGrid(){
	
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
		iArray[i][0] = "受益人类别";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnftype";
		iArray[i][5] = "1|2";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人类别编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="受益人类别|code:bnftype&NOTNULL";
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人顺序";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnforder";
		iArray[i][5] = "3|4";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人顺序编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="受益人顺序|code:bnforder";
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人姓名";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="姓名|len<=30";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人性别";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="sex";
		iArray[i][5] = "6|7";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "受益人性别编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="性别|code:sex";	

		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "25px";
		iArray[i][2] = 10;
		iArray[i][3] = 1;
		iArray[i++][9] = "出生日期|DATE";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5] = "9|10";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="证件类型|code:idtype";	
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件号码|len<=20";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "与被保险人关系";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="relation";
		iArray[i][5] = "12|13";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "与被保险人关系编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="与被保险人关系|code:relation";	
	
		iArray[i] = new Array();
		iArray[i][0] = "受益比例";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="受益比例|len<=6&DECIMAL";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "开户银行";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="headbank";
		iArray[i][5] = "15|16";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户银行编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户名";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="开户名|len<=25";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "账户";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="账户|len<=25";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "开户所在省";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="province";
		iArray[i][5] = "19|20";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户所在省编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户所在市";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4] = "city";
		iArray[i][5] = "21|22";
		iArray[i][6] = "1|0";
		iArray[i][15] = "upplacename";
		iArray[i][17] = "20";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "开户所在市编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "手机号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="手机号|num&len<=11";//校验
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人个人保单号";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "被保人客户号";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;

		BnfUpdateInfoGrid = new MulLineEnter("fm", "BnfUpdateInfoGrid");
		BnfUpdateInfoGrid.mulLineCount = 0;
		BnfUpdateInfoGrid.displayTitle = 1;
		BnfUpdateInfoGrid.locked = 0;
		BnfUpdateInfoGrid.canSel = 0;
		BnfUpdateInfoGrid.canChk = 0;
		BnfUpdateInfoGrid.hiddenSubtraction = 0;
		BnfUpdateInfoGrid.hiddenPlus = 0;
		BnfUpdateInfoGrid.selBoxEventFuncName = "";
		BnfUpdateInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
