<%
/***************************************************************
 * <p>ProName：LLClaimCasePrintInit.jsp</p>
 * <p>Title：立案打印</p>
 * <p>Description：立案打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
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

		initBatchListGrid();
		initCustomerListGrid();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
		initQueryGrpInfo();
		initQueryRgtInfo();
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化团体批次查询条件
 */
function initQueryGrpInfo() {
	
	fm.GrpRgtNo.value = "";
	fm.GrpName.value = "";
	fm.AcceptCom.value = "";
	fm.AcceptComName.value = "";
	fm.StartDate.value = "";
	fm.EndDate.value = "";
	fm.PrintState.value = "";
	fm.PrintStateName.value = "";	
	fm.ConfirmStartDate.value = "";
	fm.ConfirmEndDate.value = "";
	
}

/**
 * 初始化个人案件查询条件
 */
function initQueryRgtInfo() {
	
	fm.SingleGrpName.value = "";
	fm.SingleCustomerName.value = "";
	fm.SingleIdNo.value = "";
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
// 批次列表信息
function initBatchListGrid() {
	
		turnPage1 = new turnPageClass(); 	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许    

    iArray[i]=new Array();
    iArray[i][0]="批次号";             
    iArray[i][1]="180px";                
    iArray[i][2]=180;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="已受理人数"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="未受理人数"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="批次申请日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="受理日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="受理确认日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="打印状态"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    BatchListGrid = new MulLineEnter( "fm" , "BatchListGrid" ); 
    //这些属性必须在loadMulLine前
    BatchListGrid.mulLineCount = 0;   
    BatchListGrid.displayTitle = 1;
    BatchListGrid.locked = 0;
    BatchListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    BatchListGrid.hiddenPlus=1;
    BatchListGrid.hiddenSubtraction=1;
    BatchListGrid.selBoxEventFuncName ="showSelectPrintInfo"; //响应的函数名，不加扩号
    BatchListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}

// 客户信息列表
function initCustomerListGrid() {

		turnPage2 = new turnPageClass(); 
    var iArray = new Array();
    var i=0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许
    
    iArray[i]=new Array();
    iArray[i][0]="批次号";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="个人案件号";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="160px";
    iArray[i][2]=160; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="CustomerNo"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="被保人姓名"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码"; 
    iArray[i][1]="160px";
    iArray[i][2]=160; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="案件状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="打印次数"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="打印日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="打印状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    CustomerListGrid = new MulLineEnter( "fm" , "CustomerListGrid" ); 
    //这些属性必须在loadMulLine前
    CustomerListGrid.mulLineCount = 0;   
    CustomerListGrid.displayTitle = 1;
    CustomerListGrid.locked = 0;
    CustomerListGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
    CustomerListGrid.canChk =1
    CustomerListGrid.selBoxEventFuncName ="showSelectInfo"; //响应的函数名，不加扩号
    CustomerListGrid.hiddenPlus=1;
    CustomerListGrid.hiddenSubtraction=1;
    CustomerListGrid.loadMulLine(iArray);
    }
    catch(ex){
        alert("初始化界面信息出错->SelfLLClaimReportGrid");
    }
}
</script>
