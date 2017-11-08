<%
/***************************************************************
 * <p>ProName：LLClaimBlackListInit.jsp</p>
 * <p>Title：黑名单管理</p>
 * <p>Description：黑名单管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
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
		
		initCustomerStateListGrid();
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
		
		fm.AdjustReason.value = "";
		fm.AdjustReasonName.value = "";
		fm.AdjustRemark.value = "";
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
		if(mMode=="0"){
			document.getElementById("blackConform").style.display="";	
			document.getElementById("blackRelase").style.display="";	
		}else if(mMode=="1"){
			document.getElementById("blackConform").style.display="none";	
			document.getElementById("blackRelase").style.display="none";	
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

function initCustomerListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="RgtNo";
		iArray[i][1]="60px";
		iArray[i][2]=60;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="60px";
		iArray[i][2]=60;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="性别";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="90px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="State";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="客户当前状态";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		CustomerListGrid = new MulLineEnter("fm","CustomerListGrid");
		CustomerListGrid.mulLineCount = 0;//默认初始化显示行数
		CustomerListGrid.displayTitle = 1;
		CustomerListGrid.locked = 0;
		CustomerListGrid.canSel = 1;//单选按钮，1显示，0隐藏
		CustomerListGrid.selBoxEventFuncName ="showCustomerTrace"; //响应的函数名，不加扩号    		
		CustomerListGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		CustomerListGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		CustomerListGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化发起问题件列表格报错!");
	}
}

function initCustomerStateListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="调整原因";
		iArray[i][1]="120px";
		iArray[i][2]=180;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="原因描述";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="客户调整状态";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="操作人";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="操作日期";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="操作时间";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		CustomerStateListGrid = new MulLineEnter("fm","CustomerStateListGrid");
		CustomerStateListGrid.mulLineCount = 0;//默认初始化显示行数
		CustomerStateListGrid.displayTitle = 1;
		CustomerStateListGrid.locked = 0;
		CustomerStateListGrid.canSel = 0;//单选按钮，1显示，0隐藏
		CustomerStateListGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		CustomerStateListGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		CustomerStateListGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化发起黑名单列表格报错!");
	}
}

</script>
