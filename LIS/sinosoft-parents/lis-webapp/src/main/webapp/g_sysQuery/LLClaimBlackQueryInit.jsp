<%
/***************************************************************
 * <p>ProName：LLClaimBlackQueryInput.js</p>
 * <p>Title：黑名单查询</p>
 * <p>Description：黑名单查询</p>
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

		initBlackListGrid();
		
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
	
	fm.GrpContNo.value = "";	
	fm.GrpName.value = "";	
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
// 初始化案件信息列表
function initBlackListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="被保险人编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="被保险人姓名"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;        
    
    iArray[i]=new Array();
    iArray[i][0]="性别";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;          

    iArray[i]=new Array();
    iArray[i][0]="出生日期";             
    iArray[i][1]="80px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型";             
    iArray[i][1]="80px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="客户状态";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="业务类型"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="业务号码"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="黑名单操作人"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="黑名单设置原因"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="黑名单设置备注"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="设置日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                          
    
    BlackListGrid = new MulLineEnter( "fm" , "BlackListGrid" ); 
    //这些属性必须在loadMulLine前
    BlackListGrid.mulLineCount = 0;   
    BlackListGrid.displayTitle = 1;
    BlackListGrid.locked = 0;
    BlackListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
		//BlackListGrid.selBoxEventFuncName ="showSelectDetail"; //响应的函数名，不加扩号        
    BlackListGrid.hiddenPlus=1;
    BlackListGrid.hiddenSubtraction=1;
    BlackListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}
</script>
