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

		initEdorListGrid();		
		
		//mMode:1-理赔保单查询
		if (mMode=="1") {
			fm.CustomerNo.value = mCustomerNo;
			queryListInfo();			
		}		
		
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
function initEdorListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="投保人名称";             
    iArray[i][1]="160px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="保全受理号";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="保全号";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="保全类型"; 
    iArray[i][1]="140px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;                    

    iArray[i]=new Array();
    iArray[i][0]="受理日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="确认日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="生效日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="contno"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="insuredno"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=3;                    
    
    EdorListGrid = new MulLineEnter( "fm" , "EdorListGrid" ); 
    //这些属性必须在loadMulLine前
    EdorListGrid.mulLineCount = 0;   
    EdorListGrid.displayTitle = 1;
    EdorListGrid.locked = 0;
    EdorListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）		
    EdorListGrid.hiddenPlus=1;
    EdorListGrid.hiddenSubtraction=1;
    EdorListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}

</script>
