<%
/***************************************************************
 * <p>ProName：LLClaimCaseFlowInput.js</p>
 * <p>Title：赔案流程查询</p>
 * <p>Description：赔案流程查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
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

		initLastCaseListGrid();	
		initLastCaseFlowGrid();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
		initQueryInfo();
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化查询条件
 */
function initParam() {
	
	fm.GrpRgtNo.value = "";	
	fm.GrpName.value = "";	
	fm.AcceptCom.value = "";
	fm.AcceptComName.value = "";	
	fm.CustomerName.value = "";	
	fm.IDNo.value = "";	
	fm.RgtNo.value = "";	
	fm.FlowState.value = "";
	fm.FlowName.value = "";
	fm.ClaimUserCode.value = "";	
	fm.ClaimUserName.value = "";
	fm.PageNo.value = "";
	
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
// 初始化案件信息列表
function initLastCaseListGrid() {
	
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
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="案件类型"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;         

    iArray[i]=new Array();
    iArray[i][0]="投保人名称";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构";             
    iArray[i][1]="60px";
    iArray[i][2]=100;                 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="案件号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="客户号";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;            

    iArray[i]=new Array();
    iArray[i][0]="出险人姓名"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="证件号码"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="立案日期";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="流程状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="当前操作人"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="交接流水号"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="索赔金额"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    LastCaseListGrid = new MulLineEnter( "fm" , "LastCaseListGrid" ); 
    //这些属性必须在loadMulLine前
    LastCaseListGrid.mulLineCount = 0;   
    LastCaseListGrid.displayTitle = 1;
    LastCaseListGrid.locked = 0;
    LastCaseListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	LastCaseListGrid.selBoxEventFuncName ="showTrace"; //响应的函数名，不加扩号    		
    LastCaseListGrid.hiddenPlus=1;
    LastCaseListGrid.hiddenSubtraction=1;
    LastCaseListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}

// 初始化案件信息列表
function initLastCaseFlowGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许
    
    iArray[i]=new Array();
    iArray[i][0]="流程状态"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="操作人"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="操作日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="操作时间"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                     
    
    LastCaseFlowGrid = new MulLineEnter( "fm" , "LastCaseFlowGrid" ); 
    //这些属性必须在loadMulLine前
    LastCaseFlowGrid.mulLineCount = 0;   
    LastCaseFlowGrid.displayTitle = 1;
    LastCaseFlowGrid.locked = 0;
    LastCaseFlowGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）		
    LastCaseFlowGrid.hiddenPlus=1;
    LastCaseFlowGrid.hiddenSubtraction=1;
    LastCaseFlowGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}

</script>
