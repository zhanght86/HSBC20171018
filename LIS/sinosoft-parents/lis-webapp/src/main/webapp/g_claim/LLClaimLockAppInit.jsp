<%
/***************************************************************
 * <p>ProName：LLClaimLockAppInit.jsp</p>
 * <p>Title：立案解锁申请</p>
 * <p>Description：立案解锁申请</p>
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

		initLockGrid();
		initAppUnLockGrid();
		
		queryAppLock();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		
		fm.GrpContNo.value = "";
		fm.AppntName.value = "";
		fm.SignCom.value = "";
		fm.SignComName.value = "";
		fm.CustomerName.value = "";
		fm.IdNo.value = "";
		fm.BirthDay.value = "";
		fm.CustomerNo.value = "";	
		
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
function initLockGrid(){
	
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
		iArray[i][0]="锁定序号";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="140px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="个人保单号";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="客户号";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="性别";
		iArray[i][1]="40px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="140px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="被保险人状态";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="锁定状态";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="锁定日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="承保机构";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		LockGrid = new MulLineEnter("fm","LockGrid");
		LockGrid.mulLineCount = 0;//默认初始化显示行数
		LockGrid.displayTitle = 1;
		LockGrid.locked = 0;
		LockGrid.canSel = 1;//单选按钮，1显示，0隐藏
		LockGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		LockGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		//FlowStateGrid.selBoxEventFuncName = "getReportMissGrid"; //函数名称
		LockGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化报案信息表格报错!");
	}
	
}

function initAppUnLockGrid(){
	
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="锁定状态";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="锁定日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁申请人";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁申请日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁审核人";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁审核日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁审核结论";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁审核意见";
		iArray[i][1]="220px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		AppUnLockGrid = new MulLineEnter("fm","AppUnLockGrid");
		AppUnLockGrid.mulLineCount = 0;//默认初始化显示行数
		AppUnLockGrid.displayTitle = 1;
		AppUnLockGrid.locked = 0;
		AppUnLockGrid.canSel = 0;//单选按钮，1显示，0隐藏
		AppUnLockGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		AppUnLockGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		//UnLockGrid.selBoxEventFuncName = "getReportMissGrid"; //函数名称
		AppUnLockGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化报案信息表格报错!");
	}
	
}
</script>
