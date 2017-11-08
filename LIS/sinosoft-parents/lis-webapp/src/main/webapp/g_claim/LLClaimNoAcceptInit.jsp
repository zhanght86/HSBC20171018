<%
/***************************************************************
 * <p>ProName：LLClaimNoAcceptInit.jsp</p>
 * <p>Title：未受理客户查询</p>
 * <p>Description：未受理客户查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-23
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
		
		queryCustomerList();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}
/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
		initCustomerInfo();//初始化未受理客户信息
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}
/**
 * 初始化参数
 */
function initCustomerInfo() {
	
	fm.GrpRgtNo.value = mGrpRgtNo;
	fm.CustomerNo.value = "";
	fm.RgtNo.value = "";		
	fm.CustomerName.value = "";	
	fm.Birthday.value = "";
	fm.IDNo.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.Gender.value="";
	fm.GenderName.value = "";	
	fm.AppAmnt.value = "";	
	fm.BillCount.value = "";
	fm.ScanCount.value = "";	
	fm.NoAcceptReasonName.value = "";
	fm.NoAcceptReason.value = "";
	getInfo();
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
		
		
		if (mMode=="1") {
			fm.addClickButton.style.display="none";
			fm.modifyClickButton.style.display="none";
			fm.deleteClickButton.style.display="none";
			fm.deleteAllButton.style.display="none";
		}
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
// 未受理客户信息列表
function initNoAcceptListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="RgtNo";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="CustomerNo";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="姓名";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="性别"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="出生日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="申请金额"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="发票数"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="影像件数"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="未受理原因"; 
    iArray[i][1]="200px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;
    
    NoAcceptListGrid = new MulLineEnter( "fm" , "NoAcceptListGrid" ); 
    //这些属性必须在loadMulLine前
    NoAcceptListGrid.mulLineCount = 0;   
    NoAcceptListGrid.displayTitle = 1;
    NoAcceptListGrid.locked = 0;
    NoAcceptListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    NoAcceptListGrid.hiddenPlus=1;
		NoAcceptListGrid.selBoxEventFuncName ="showSelectCustomerInfo"; //响应的函数名，不加扩号
    NoAcceptListGrid.hiddenSubtraction=1;
    NoAcceptListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}
</script>
