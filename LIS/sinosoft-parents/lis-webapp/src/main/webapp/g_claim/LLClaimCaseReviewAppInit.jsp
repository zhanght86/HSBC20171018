<%
/***************************************************************
 * <p>ProName：LLClaimCaseReviewAppInput.js</p>
 * <p>Title：案件审核申请</p>
 * <p>Description：案件审核申请</p>
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

		initMainCaseGrid();
		initSelfCaseGrid();
		
		querySelf("INIT");
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		
		fm.ActivityID.value = "1800501005";		
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
// 初始化案件信息列表
function initMainCaseGrid() {
	
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
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="案件类型";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;          

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="160px";
    iArray[i][2]=160; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="已受理人数"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="未受理人数"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
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
    iArray[i][0]="时效天数(工作日)"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    MainCaseGrid = new MulLineEnter( "fm" , "MainCaseGrid" ); 
    //这些属性必须在loadMulLine前
    MainCaseGrid.mulLineCount = 0;   
    MainCaseGrid.displayTitle = 1;
    MainCaseGrid.locked = 0;
    MainCaseGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    MainCaseGrid.hiddenPlus=1;
    MainCaseGrid.hiddenSubtraction=1;
    MainCaseGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}

// 初始化个人池下案件信息列表
function initSelfCaseGrid() {
	       
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
    iArray[i][3]=0;
    iArray[i++][7]="enterCase";
    
    iArray[i]=new Array();
    iArray[i][0]="案件类型";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;          

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="160px";
    iArray[i][2]=160; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="已受理人数"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="未受理人数"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
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
    iArray[i][0]="时效天数(工作日)"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    SelfCaseGrid = new MulLineEnter( "fm" , "SelfCaseGrid" ); 
    //这些属性必须在loadMulLine前
    SelfCaseGrid.mulLineCount = 0;   
    SelfCaseGrid.displayTitle = 1;
    SelfCaseGrid.locked = 0;
    SelfCaseGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    //SelfCaseGrid.selBoxEventFuncName ="selfLLReport"; //响应的函数名，不加扩号
    SelfCaseGrid.hiddenPlus=1;
    SelfCaseGrid.hiddenSubtraction=1;
    SelfCaseGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("初始化界面信息出错->SelfCaseGrid");
    }
}
</script>
