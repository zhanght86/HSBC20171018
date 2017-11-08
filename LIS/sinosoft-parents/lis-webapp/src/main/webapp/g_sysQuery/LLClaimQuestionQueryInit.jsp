<%
/***************************************************************
 * <p>ProName：LLClaimNoAcceptQueryInput.js</p>
 * <p>Title：问题件查询</p>
 * <p>Description：问题件查询</p>
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

		initQuestionListGrid();
		
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
	fm.PageNo.value = "";	
	fm.GrpContNo.value = "";	
	fm.GrpName.value = "";	
	fm.CustomerName.value = "";
	fm.InputStartDate.value = "";	
	fm.InputEndDate.value = "";	
	fm.State.value = "";
	fm.StateName.value = "";
	fm.DealStartDate.value = "";	
	fm.DealEndDate.value = "";	
	fm.AcceptCom.value = "";
	fm.AcceptComName.value = "";
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
// 初始化问题件列表
function initQuestionListGrid() {
	
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
    iArray[i][0]="交接流转号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;   
    
    iArray[i]=new Array();
    iArray[i][0]="被保险人姓名"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;  
    
    iArray[i]=new Array();
    iArray[i][0]="问题件类型编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="问题件类型"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        
    
    iArray[i]=new Array();
    iArray[i][0]="问题件描述"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="处理状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="问题件发起人"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="发起日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="处理日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="处理时效"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="发起机构"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                                           
    
    QuestionListGrid = new MulLineEnter( "fm" , "QuestionListGrid" ); 
    //这些属性必须在loadMulLine前
    QuestionListGrid.mulLineCount = 0;   
    QuestionListGrid.displayTitle = 1;
    QuestionListGrid.locked = 0;
    QuestionListGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
		//QuestionListGrid.selBoxEventFuncName ="showSelectDetail"; //响应的函数名，不加扩号        
    QuestionListGrid.hiddenPlus=1;
    QuestionListGrid.hiddenSubtraction=1;
    QuestionListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("初始化界面信息出错->QuestionListGrid");
    }
}
</script>
