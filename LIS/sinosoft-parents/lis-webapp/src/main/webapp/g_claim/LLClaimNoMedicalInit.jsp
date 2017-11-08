<%
/***************************************************************
 * <p>ProName：LLClaimNoMedicalInit.jsp</p>
 * <p>Title：非医疗账单初始化页面</p>
 * <p>Description：非医疗账单初始化页面</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-05-01
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

		initMaimInfoGrid();
		initMajorBillGrid();
		QueryDefoInfo();
		QueryMajorInfo();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		fm.tRgtNo.value=tRgtNo;
		fm.tCustomerNo.value=tCustomerNo;
		fm.tCaseNo.value=tCaseNo;
		
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
		if(tMode=="0"){
		
			document.getElementById("addMaimBill1").style.display="";
			document.getElementById("modifyMaimBill1").style.display="";
			document.getElementById("deleteMaimBill1").style.display="";
			document.getElementById("resetDefoInfo1").style.display="";
					 
			document.getElementById("addMajorBill1").style.display="";
			document.getElementById("modifyMajorBill1").style.display="";
			document.getElementById("deleteMajorBill1").style.display="";
			document.getElementById("resetMajorInfo1").style.display="";
		
		}else if(tMode=="1"){
			
			document.getElementById("addMaimBill1").style.display="none";
			document.getElementById("modifyMaimBill1").style.display="none";
			document.getElementById("deleteMaimBill1").style.display="none";
			document.getElementById("resetDefoInfo1").style.display="none";
					 
			document.getElementById("addMajorBill1").style.display="none";
			document.getElementById("modifyMajorBill1").style.display="none";
			document.getElementById("deleteMajorBill1").style.display="none";
			document.getElementById("resetMajorInfo1").style.display="none";
		
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
// 初始化伤残账单
function initMaimInfoGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="伤残流水号";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="伤残类型编码";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="伤残类型";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="伤残大类编码";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
	
		iArray[i]=new Array();
		iArray[i][0]="伤残大类名称";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
	
		iArray[i]=new Array();
		iArray[i][0]="伤残级别编码";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
	
		iArray[i]=new Array();
		iArray[i][0]="伤残级别名称";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="伤残代码";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="伤残代码名称";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="残疾给付比例";
		iArray[i][1]="70px";
		iArray[i][2]=300;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="鉴定机构";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="鉴定日期";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
    
    MaimInfoGrid = new MulLineEnter( "fm" , "MaimInfoGrid" ); 
    //这些属性必须在loadMulLine前
    MaimInfoGrid.mulLineCount = 0;   
    MaimInfoGrid.displayTitle = 1;
    MaimInfoGrid.locked = 0;
    MaimInfoGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    MaimInfoGrid.hiddenPlus=1;
    MaimInfoGrid.hiddenSubtraction=1;
    MaimInfoGrid.selBoxEventFuncName ="getDefoInfo"; //响应的函数名，不加扩号
    MaimInfoGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}
// 初始化伤残账单
function initMajorBillGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="重疾流水号";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="重疾类型代码";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="重疾类型";
		iArray[i][1]="60px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="重疾代码";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="重疾名称";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="金额";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="医疗机构";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
				
		iArray[i]=new Array();
		iArray[i][0]="确诊时间";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
    
    MajorBillGrid = new MulLineEnter( "fm" , "MajorBillGrid" ); 
    //这些属性必须在loadMulLine前
    MajorBillGrid.mulLineCount = 1;   
    MajorBillGrid.displayTitle = 1;
    MajorBillGrid.locked = 0;
    MajorBillGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    MajorBillGrid.hiddenPlus=1;
    MajorBillGrid.hiddenSubtraction=1;
    MajorBillGrid.selBoxEventFuncName ="getMajorInfo"; //响应的函数名，不加扩号
    MajorBillGrid.loadMulLine(iArray);
     
}
    catch(ex){
        alert("ex");
    }
}
</script>
