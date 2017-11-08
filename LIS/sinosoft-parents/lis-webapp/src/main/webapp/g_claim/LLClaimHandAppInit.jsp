<%
/***************************************************************
 * <p>ProName：LLClaimHandAppInit.jsp</p>
 * <p>Title：交接流转号申请页面</p>
 * <p>Description：交接流转号申请页面</p>
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

		initHandNoListGrid();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		initQueryInfo();//初始化查询条件
		initPageNoInfo();//初始化交接流转信息
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}
/**
 * 初始化查询条件
 */
function initQueryInfo() {
	fm.QueryPageNo.value = "";
	fm.AppOperator.value = mOperator;
	fm.ManageCom.value = "";
	fm.AppStartDate.value = "";
	fm.AppEndDate.value = "";	
}
/**
 * 初始化交接流转信息
 */
function initPageNoInfo() {
	fm.PageNo.value = "";
	fm.SumNum.value = "";
	fm.Remark.value = "";
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
// 交接流转号信息列表
function initHandNoListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="交接流转号";             
    iArray[i][1]="120px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="总关联人数"; 
    iArray[i][1]="60px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="已关联人数"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="申请人"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="申请日期"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="管理机构"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    HandNoListGrid = new MulLineEnter( "fm" , "HandNoListGrid" ); 
    //这些属性必须在loadMulLine前
    HandNoListGrid.mulLineCount = 0;   
    HandNoListGrid.displayTitle = 1;
    HandNoListGrid.locked = 0;
    HandNoListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    HandNoListGrid.selBoxEventFuncName ="selectPageNo"; //响应的函数名，不加扩号    
    HandNoListGrid.hiddenPlus=1;
    HandNoListGrid.hiddenSubtraction=1;
    HandNoListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->HandNoListGrid");
    }
}
</script>
