<%
/***************************************************************
 * <p>ProName：LLClaimCaseQueryInput.js</p>
 * <p>Title：赔案查询</p>
 * <p>Description：赔案查询</p>
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
		initLastCaseDetailGrid();
		
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
	fm.GrpContNo.value = "";	
	fm.GrpName.value = "";	
	fm.RiskCode.value = "";	
	fm.RiskName.value = "";	
	fm.ClmState.value = "";	
	fm.ClmStateName.value = "";	
	fm.CustomerName.value = "";	
	fm.IDNo.value = "";	
	fm.AcceptCom.value = "";
	fm.AcceptComName.value = "";
	
	fm.PageNo.value = "";	
	fm.AccStartDate.value = "";
	fm.AccEndDate.value = "";
	
	fm.StartDate.value = "";
	fm.EndDate.value = "";			
	
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
    iArray[i][0]="案件号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="案件类型"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;        
    
    iArray[i]=new Array();
    iArray[i][0]="投保人编码";             
    iArray[i][1]="120px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;          

    iArray[i]=new Array();
    iArray[i][0]="投保人名称";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="被保险人编码";             
    iArray[i][1]="80px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="被保险人姓名";             
    iArray[i][1]="100px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="员工号";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="证件号码"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="与被保险人关系"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="申请金额"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="赔付金额"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="拒付金额"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="备注"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="申请日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="结案日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="转账账户"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="转账日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="案件状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="审核人"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="复核人"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="审批人"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="交接流水号"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="手机号"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="案件状态编码"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="案件类型编码"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;     
    
    LastCaseListGrid = new MulLineEnter( "fm" , "LastCaseListGrid" ); 
    //这些属性必须在loadMulLine前
    LastCaseListGrid.mulLineCount = 0;   
    LastCaseListGrid.displayTitle = 1;
    LastCaseListGrid.locked = 0;
    LastCaseListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	LastCaseListGrid.selBoxEventFuncName ="showSelectDetail"; //响应的函数名，不加扩号        
    LastCaseListGrid.hiddenPlus=1;
    LastCaseListGrid.hiddenSubtraction=1;
    LastCaseListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}

// 初始化案件赔付明细
function initLastCaseDetailGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="事件号";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="事件状态";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;    
                    
    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="承保计划";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="险种编码";             
    iArray[i][1]="80px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;             

    iArray[i]=new Array();
    iArray[i][0]="险种名称";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任编码";             
    iArray[i][1]="80px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="责任名称";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="责任起期";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任止期";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="出险日期";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;   
    
    iArray[i]=new Array();
    iArray[i][0]="出险类型";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="事故原因";
    iArray[i][1]="140px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="就诊医院";
    iArray[i][1]="80px";
    iArray[i][2]=10;
    iArray[i++][3]=0;                   
    
    iArray[i]=new Array();
    iArray[i][0]="索赔金额";
    iArray[i][1]="60px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="扣除金额"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="第三方支付金额"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        
    
    iArray[i]=new Array();
    iArray[i][0]="审核金额";
    iArray[i][1]="60px";
    iArray[i][2]=10;
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="赔付金额"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="拒付金额"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;            
    
    iArray[i]=new Array();
    iArray[i][0]="赔付比例"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="使用公共保额"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="观察期"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="免赔方式"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="免赔额或免赔天数"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="有效保额"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="次限额"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="赔付结论说明"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="扣除说明"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="备注"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;                  
    
    LastCaseDetailGrid = new MulLineEnter( "fm" , "LastCaseDetailGrid" ); 
    //这些属性必须在loadMulLine前
    LastCaseDetailGrid.mulLineCount = 0;   
    LastCaseDetailGrid.displayTitle = 1;
    LastCaseDetailGrid.locked = 0;
    LastCaseDetailGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
    LastCaseDetailGrid.hiddenPlus=1;
    LastCaseDetailGrid.hiddenSubtraction=1;
    LastCaseDetailGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}
</script>
