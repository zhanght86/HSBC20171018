<%
/***************************************************************
 * <p>ProName：LLClaimNoAcceptQueryInput.js</p>
 * <p>Title：被保险人查询</p>
 * <p>Description：被保险人查询</p>
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

		initLCInsuredListGrid();
		initMainInsuredListGrid();
		
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
function initQueryInfo() {
	
	fm.GrpAppNo.value = "";	
	fm.GrpContNo.value = "";	
	fm.AppntName.value = "";	
	fm.CustomerName.value = "";	
	fm.IDNo.value = "";	
	fm.BirthDay.value = "";	
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
// 初始化未受理客户列表
function initLCInsuredListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="投保人编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="投保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="个人保单号";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;        

    iArray[i]=new Array();
    iArray[i][0]="与主被保险人关系";             
    iArray[i][1]="60px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="被保人客户号";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="被保人姓名";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="性别";             
    iArray[i][1]="40px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="出生日期";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;            

    iArray[i]=new Array();
    iArray[i][0]="保单起期"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="保单止期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="保单状态"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="承保机构"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="主被保人客户号"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;                                               
    
    LCInsuredListGrid = new MulLineEnter( "fm" , "LCInsuredListGrid" ); 
    //这些属性必须在loadMulLine前
    LCInsuredListGrid.mulLineCount = 0;   
    LCInsuredListGrid.displayTitle = 1;
    LCInsuredListGrid.locked = 0;
    LCInsuredListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
		LCInsuredListGrid.selBoxEventFuncName ="showSelectDetail"; //响应的函数名，不加扩号        
    LCInsuredListGrid.hiddenPlus=1;
    LCInsuredListGrid.hiddenSubtraction=1;
    LCInsuredListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("初始化界面信息出错->LCInsuredListGrid");
    }
}

// 初始化未受理客户列表
function initMainInsuredListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="投保人编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="投保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="个人保单号";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;        

    iArray[i]=new Array();
    iArray[i][0]="与主被保险人关系";             
    iArray[i][1]="60px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="被保人客户号";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="被保人姓名";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="性别";             
    iArray[i][1]="40px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="出生日期";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;            

    iArray[i]=new Array();
    iArray[i][0]="责任起期"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任止期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="保单状态"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="承保机构"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                                
    
    MainInsuredListGrid = new MulLineEnter( "fm" , "MainInsuredListGrid" ); 
    //这些属性必须在loadMulLine前
    MainInsuredListGrid.mulLineCount = 0;   
    MainInsuredListGrid.displayTitle = 1;
    MainInsuredListGrid.locked = 0;
    MainInsuredListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）      
    MainInsuredListGrid.hiddenPlus=1;
    MainInsuredListGrid.hiddenSubtraction=1;
    MainInsuredListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("初始化界面信息出错->LCInsuredListGrid");
    }
}
</script>
