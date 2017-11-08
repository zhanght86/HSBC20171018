<%
/***************************************************************
 * <p>ProName：LLClaimCaseInput.js</p>
 * <p>Title：既往赔案查询</p>
 * <p>Description：既往赔案查询</p>
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

		initPolicyListGrid();		
		
		//mMode:1-理赔保单查询;2-其他保单查询
		fm.CustomerNo.value = mCustomerNo;
		fm.RgtNo.value=mRgtNo;
		queryListInfo(); 	
		
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
// 初始化保单信息列表
function initPolicyListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="承保机构";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="投保书号";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="签单日期";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;            

    iArray[i]=new Array();
    iArray[i][0]="生效日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="终止日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="总人数"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="总保费"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="保单状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;        
    
    PolicyListGrid = new MulLineEnter( "fm" , "PolicyListGrid" ); 
    //这些属性必须在loadMulLine前
    PolicyListGrid.mulLineCount = 0;   
    PolicyListGrid.displayTitle = 1;
    PolicyListGrid.locked = 0;
    PolicyListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）		
    PolicyListGrid.hiddenPlus=1;
    PolicyListGrid.hiddenSubtraction=1;
    PolicyListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}

</script>
