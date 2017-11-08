<%
/***************************************************************
 * <p>ProName：LCinsuredQueryInit.jsp</p>
 * <p>Title：被保人查询</p>
 * <p>Description：被保人查询</p>
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
		
		initCustomerGrid();
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
		
		var mFlag = fm.Flag.value;
		var mGrpPropNo = fm.GrpPropNo.value;
		var mInsuredName = fm.InsuredName.value;
		if (mInsuredName!=null &&mInsuredName!='') {
			if(mFlag =='01'){
				queryCustomer();
			}else if(mFlag =='02'){
				queryMainCustomer();
			}
			
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
 * 初始化客户信列表
 */
function initCustomerGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array()
		iArray[i][0]="性别";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array()
		iArray[i][0]="性别编码";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型编码";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="被保人客户号";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="被保人保单号";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
		CustomerGrid.mulLineCount = 0;
		CustomerGrid.displayTitle = 1;
		CustomerGrid.canSel=1;
		CustomerGrid.canChk=0;
		CustomerGrid.hiddenPlus = 1;
		CustomerGrid.hiddenSubtraction = 1;
		CustomerGrid.selBoxEventFuncName = "";
		CustomerGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化CustomerGrid时出错："+ ex);
	}
}
 
</script>
