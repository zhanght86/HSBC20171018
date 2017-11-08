<%
/***************************************************************
 * <p>ProName：LLClaimNoAcceptQueryInput.js</p>
 * <p>Title：未受理客户查询</p>
 * <p>Description：未受理客户查询</p>
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

		initNoAcceptListGrid();
		
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
	
	fm.GrpRgtNo.value = "";	
	fm.RgtNo.value = "";	
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
function initNoAcceptListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="团体批次号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="案件号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="投保人编码";             
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
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="申请金额";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="账单数"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="未受理原因"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="操作人"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="操作时间"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                         
    
    NoAcceptListGrid = new MulLineEnter( "fm" , "NoAcceptListGrid" ); 
    //这些属性必须在loadMulLine前
    NoAcceptListGrid.mulLineCount = 0;   
    NoAcceptListGrid.displayTitle = 1;
    NoAcceptListGrid.locked = 0;
    NoAcceptListGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
		//NoAcceptListGrid.selBoxEventFuncName ="showSelectDetail"; //响应的函数名，不加扩号        
    NoAcceptListGrid.hiddenPlus=1;
    NoAcceptListGrid.hiddenSubtraction=1;
    NoAcceptListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("初始化界面信息出错->NoAcceptListGrid");
    }
}
</script>
