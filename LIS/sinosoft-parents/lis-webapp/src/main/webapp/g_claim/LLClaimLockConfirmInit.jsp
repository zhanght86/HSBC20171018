<%
/***************************************************************
 * <p>ProName：LLClaimLockConfirmInit.jsp</p>
 * <p>Title：立案解锁确认</p>
 * <p>Description：立案解锁确认</p>
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

		initAppLockGrid();
		
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
		fm.AuditConlusion.value = "";
		fm.AuditConlusionName.value = "";
		fm.AuditReason.value = "";
				
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

function initAppLockGrid(){
	
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
		iArray[i][0]="锁定号";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="投保人名称";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="客户号";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="性别";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="被保险人状态";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="锁定状态";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="锁定日期";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁申请人";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="解锁申请日期";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="承保机构";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		AppLockGrid = new MulLineEnter("fm","AppLockGrid");
		AppLockGrid.mulLineCount = 0;//默认初始化显示行数
		AppLockGrid.displayTitle = 1;
		AppLockGrid.locked = 0;
		AppLockGrid.canSel = 1;//单选按钮，1显示，0隐藏
		AppLockGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		AppLockGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		//FlowStateGrid.selBoxEventFuncName = "getReportMissGrid"; //函数名称
		AppLockGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化报案信息表格报错!");
	}
	
}
</script>
