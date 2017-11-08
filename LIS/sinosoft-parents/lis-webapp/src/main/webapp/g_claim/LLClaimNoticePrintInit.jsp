<%
/***************************************************************
 * <p>ProName：LLClaimNoticePrintInit.jsp</p>
 * <p>Title：理赔通知书打印</p>
 * <p>Description：理赔通知书打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
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
		
		initClaimGrid();
		initCaseGrid();
   
		
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
function initClaimGrid() {
	
  turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="批次号";
    iArray[i][1]="150px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="投保人编码";
    iArray[i][1]="180px";
    iArray[i][2]=120;
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=0;
    
   	iArray[i]=new Array();
    iArray[i][0]="申请人数";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
		iArray[i]=new Array();
    iArray[i][0]="未受理人数";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="未结案人数";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
  	iArray[i]=new Array();
    iArray[i][0]="已结案人数";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="申请金额";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="赔付金额";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="申请日期";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    ClaimGrid = new MulLineEnter("fm","ClaimGrid");
    ClaimGrid.mulLineCount =0;
    ClaimGrid.displayTitle = 1;
    ClaimGrid.locked = 1;
    ClaimGrid.canSel =1;
    ClaimGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    ClaimGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    ClaimGrid. selBoxEventFuncName = "queryCustomer";
    ClaimGrid.loadMulLine(iArray);
  }catch(ex){
		alert("初始化表格报错!");
	}
	
}

//显示出险人信息 add by fengzg 
function initCaseGrid()
{
  turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

		iArray[i]=new Array();
    iArray[i][0]="批次号";
    iArray[i][1]="150px";
    iArray[i][2]=100;
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="案件号";
    iArray[i][1]="150px";
    iArray[i][2]=100;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="个人客户号";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=3;    
    
		iArray[i]=new Array();
    iArray[i][0]="投保人编码";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=0;

    
    iArray[i]=new Array();
    iArray[i][0]="被保险人姓名";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
		iArray[i]=new Array();
    iArray[i][0]="性别编码";
    iArray[i][1]="70px";
    iArray[i][2]=80;
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="性别";
    iArray[i][1]="50px";
    iArray[i][2]=80;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="出生日期";
    iArray[i][1]="80px";
    iArray[i][2]=60;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型";
    iArray[i][1]="80px";
    iArray[i][2]=60;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="赔付金额";
    iArray[i][1]="60px";
    iArray[i][2]=60;
    iArray[i++][3]=0;
  
	iArray[i]=new Array();
    iArray[i][0]="结案日期";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;        
    
    CaseGrid = new MulLineEnter("fm","CaseGrid");
    CaseGrid.mulLineCount =0;
    CaseGrid.displayTitle = 1;
    CaseGrid.locked = 1;
    CaseGrid.canSel =0;
    CaseGrid.canChk =1;
    CaseGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    CaseGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    CaseGrid. selBoxEventFuncName = "onSelSelected";
    CaseGrid.loadMulLine(iArray);
	}catch(ex){
		alert("初始化表格报错!");
	}
	
}
</script>