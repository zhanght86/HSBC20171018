<%
/***************************************************************
 * <p>ProName：LLClaimMajorMainInit.jsp</p>
 * <p>Title：重大案件上报公共池界面</p>
 * <p>Description：重大案件上报公共池界面</p>
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

		initLLClaimReportGrid();
		
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
// 报案信息列表的初始化
function initLLClaimReportGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="报案号";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i][3]=0;    
    iArray[i++][7]="enterReport";
          
    iArray[i]=new Array();
    iArray[i][0]="客户号";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="140px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="姓名"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码"; 
    iArray[i][1]="140px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="报案登记人"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="报案登记日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="报案确认人"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="报案确认日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="索赔金额"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;        
    
    
    iArray[i]=new Array();
    iArray[i][0]="是否重大案件"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    
    iArray[i]=new Array();
    iArray[i][0]="关联状态"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="报案机构"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    LLClaimReportGrid = new MulLineEnter( "fm" , "LLClaimReportGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimReportGrid.mulLineCount = 0;   
    LLClaimReportGrid.displayTitle = 1;
    LLClaimReportGrid.locked = 0;
    LLClaimReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimReportGrid.hiddenPlus=1;
    LLClaimReportGrid.hiddenSubtraction=1;
    LLClaimReportGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}
</script>
