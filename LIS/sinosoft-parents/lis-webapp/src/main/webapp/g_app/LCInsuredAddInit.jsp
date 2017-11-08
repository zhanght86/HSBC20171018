<%
/***************************************************************
 * <p>ProName：LCInsuredAddInit.jsp</p>
 * <p>Title：添加被保人详细</p>
 * <p>Description：添加被保人详细</p>
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
		
		initQueryScanGrid();
		initQueryInfoGrid();
		initPayPlanGrid();
		initInvestGrid();
		initInpBox();
	
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

		clearPage();
		fm.Flag.value= tFlag;
		if(fm.mContNo.value!= "" && fm.mContNo.value !=null){
			amntTypeQer();
			fm.all("addButton").disabled=true;
			fm.all("deleteButton").disabled=false;
			fm.all("modifyButton").disabled=false;
			showRelationtoMain(tContNo);
			getCustomerInfo();
		}else{
			fm.all("deleteButton").disabled=true;
			fm.all("modifyButton").disabled=true;	
			fm.all("addButton").disabled=false;
		}
		if(tFlag =='01'){
			divAddDelButton.style.display="none";
			divInsuredInfo3.style.display="none";
			divPayPlanSaveButton.style.display="none";
		}
		if(fm.InsuredType.value=="2"||fm.InsuredType.value=="3"){
			divAddDelButton.style.display="none";
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
 * 获取被保人信息
 */
function getContInfo(){
	
	if(fm.InsuredSeqNo.value =="" || fm.InsuredSeqNo.value == null){
		fm.all("addButton").disabled=false;
	}else {
		fm.all("addButton").disabled=true;
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
		iArray[i][0] = "个人保单号";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] =3;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人险种号";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] =3;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] =3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额类型";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额（元）";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		if(fm.AmntFlag.value=='04'){
			iArray[i++][3] = 2;		
		}else{
			iArray[i++][3] = 0;			
		}

		iArray[i] = new Array();
		iArray[i][0] = "保费（元）";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		QueryScanGrid = new MulLineEnter("fm", "QueryScanGrid");
		QueryScanGrid.mulLineCount = 0;
		QueryScanGrid.displayTitle = 1;
		QueryScanGrid.locked = 1;
		QueryScanGrid.canSel = 0;
		QueryScanGrid.canChk = 0;
		QueryScanGrid.hiddenSubtraction = 1;
		QueryScanGrid.hiddenPlus = 1;
		QueryScanGrid.selBoxEventFuncName = ""; 
		QueryScanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initQueryInfoGrid() {

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
		iArray[i][0] = "与主被保险人关系";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "附属被保险人姓名";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "年龄";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险方案";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		QueryInfoGrid = new MulLineEnter("fm", "QueryInfoGrid");
		QueryInfoGrid.mulLineCount = 1;
		QueryInfoGrid.displayTitle = 1;
		QueryInfoGrid.locked = 1;
		QueryInfoGrid.canSel = 0;
		QueryInfoGrid.canChk = 0;
		QueryInfoGrid.hiddenSubtraction = 1;
		QueryInfoGrid.hiddenPlus = 1;
		
		QueryInfoGrid.selBoxEventFuncName = "";
		QueryInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initPayPlanGrid() {
	
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
		iArray[i][0] = "个人险种号";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任名称";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费编码";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费名称";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "缴费金额（元）";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		PayPlanGrid = new MulLineEnter("fm", "PayPlanGrid");
		PayPlanGrid.mulLineCount = 0;
		PayPlanGrid.displayTitle = 1;
		PayPlanGrid.locked = 1;
		PayPlanGrid.canSel = 1;
		PayPlanGrid.canChk = 0;
		PayPlanGrid.hiddenSubtraction = 1;
		PayPlanGrid.hiddenPlus = 1;
		PayPlanGrid.selBoxEventFuncName = "queryInvest";
		PayPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}

function initInvestGrid() {
	
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
		iArray[i][0] = "个人险种号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "责任编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资账户编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资账户名称";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资金额（元）";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "投资分配比例";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		InvestGrid = new MulLineEnter("fm", "InvestGrid");
		InvestGrid.mulLineCount = 0;
		InvestGrid.displayTitle = 1;
		InvestGrid.locked = 1;
		InvestGrid.canSel = 0;
		InvestGrid.canChk = 0;
		InvestGrid.hiddenSubtraction = 1;
		InvestGrid.hiddenPlus = 1;
		InvestGrid.selBoxEventFuncName = ""; 
		InvestGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
