<%
/***************************************************************
 * <p>ProName：LLClaimCustomerInit.jsp</p>
 * <p>Title：系统团体投保人查询</p>
 * <p>Description：系统团体投保人查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-04-17
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

		initCustomerListGrid();
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
//初始化被保人列表
function initCustomerListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）             
		iArray[i][1]="30px";         			//列宽                                                     
		iArray[i][2]=10;          			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                               
		
		iArray[i]=new Array();
		iArray[i][0]="投保单位编码";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保单位";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="客户号";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;            
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="姓名";                                                
		iArray[i][1]="120px";                                             
		iArray[i][2]=180;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="Gender";                                                
		iArray[i][1]="180px";                                             
		iArray[i][2]=180;
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="性别";                                                
		iArray[i][1]="60px";                                             
		iArray[i][2]=180;
		iArray[i++][3]=0;      
		
		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;      
		             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="员工号";                                                
		iArray[i][1]="80px";                                             
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="IDType";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="140px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="140px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="生效起期";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="生效止期";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="手机号";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;		
		      
		CustomerListGrid = new MulLineEnter("fm", "CustomerListGrid");
		CustomerListGrid.mulLineCount = 0;
		CustomerListGrid.displayTitle = 1;
		CustomerListGrid.locked = 1;
		CustomerListGrid.canSel = 1;
		CustomerListGrid.canChk = 0;
		CustomerListGrid.hiddenSubtraction = 1;
		CustomerListGrid.hiddenPlus = 1;
		CustomerListGrid.loadMulLine(iArray);

			
		}catch(ex){}
	}	
	
</script>
