<%
/***************************************************************
 * <p>ProName：LLClaimBenefitInput.jsp</p>
 * <p>Title：暂停或启动案件</p>
 * <p>Description：暂停或启动案件</p>
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
		
		initClaimCaseGrid();
		initClaimCaseTraceGrid();		
		
		queryClaimList();//查询批次下所有案件
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		
		fm.RgtNo.value = mRgtNo;
		fm.CustomerNo.value = mCustomerNo;
		fm.PauseReason.value = "";
		fm.PauseReasonName.value = "";	
		fm.ReasonDesc.value = "";		
		
		
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


/**=========================================================================
    问题件结果集
   =========================================================================
*/
function initClaimCaseGrid(){
	
	turnPage = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="案件号";
		iArray[i][1]="160px";
		iArray[i][2]=160;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="140px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="性别";
		iArray[i][1]="100px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="ClmState";
		iArray[i][1]="60px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="案件状态";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
			
		ClaimCaseGrid = new MulLineEnter("fm","ClaimCaseGrid");
		ClaimCaseGrid.mulLineCount = 0;//默认初始化显示行数
		ClaimCaseGrid.displayTitle = 1;
		ClaimCaseGrid.locked = 0;
		ClaimCaseGrid.canSel = 1;//单选按钮，1显示，0隐藏
		ClaimCaseGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		ClaimCaseGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		ClaimCaseGrid.selBoxEventFuncName ="showSelectTrace"; //响应的函数名，不加扩号				
		ClaimCaseGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化发起问题件列表格报错!");
	}
	
}

function initClaimCaseTraceGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="案件号";
		iArray[i][1]="140px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="140px";
		iArray[i][2]=140;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="暂停操作人";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="暂停日期";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="暂停原因";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="原因描述";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="回复操作人";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="回复日期";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="回复描述";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="启动人";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="启动日期";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;					
		
		ClaimCaseTraceGrid = new MulLineEnter("fm","ClaimCaseTraceGrid");
		ClaimCaseTraceGrid.mulLineCount = 0;//默认初始化显示行数
		ClaimCaseTraceGrid.displayTitle = 1;
		ClaimCaseTraceGrid.locked = 0;
		ClaimCaseTraceGrid.canSel = 0;//单选按钮，1显示，0隐藏
		ClaimCaseTraceGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		ClaimCaseTraceGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示		
		ClaimCaseTraceGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化案件轨迹列表报错!");
	}
	
}

</script>
