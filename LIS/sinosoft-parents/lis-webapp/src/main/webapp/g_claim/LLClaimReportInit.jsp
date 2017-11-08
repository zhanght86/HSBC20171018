<%
/***************************************************************
 * <p>ProName：LLClaimReportInit.jsp</p>
 * <p>Title：报案录入界面</p>
 * <p>Description：报案录入界面</p>
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

		initCustomerListGrid();
		initAccidentListGrid();
		initGrpClaimDutyGrid();
		
		queryReportInfo();
		if (fm.RptNo.value!=null && fm.RptNo.value!="") {
			
			queryReportCustomerInfo();
			var tCountNum = CustomerListGrid.mulLineCount;
			if (tCountNum>=1) {
				
			}			
			//queryReportCaseInfo();
			
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
				
		initReportInfo();
		initReportCustomerInfo();
		initCustomerCaseInfo();
	} catch (re) {
		alert("初始化参数错误！");
	}
}
/**
 * 初始化报案信息
 */
function initReportInfo() {
	
	if (mRptNo==null || mRptNo=="null" || mRptNo=="") {
		fm.RptNo.value="";
		fm.ReportSave.style.display = "";
	} else {
		fm.RptNo.value = mRptNo;
		fm.ReportSave.style.display = "none";
	}	
	fm.RgtClass.value = "2";
	fm.RgtClassName.value = "团单";
	fm.AppntNo.value = "";
	fm.AppntName.value = "";
	fm.RptName.value = "";
	fm.RptPhone.value = "";
	fm.RptMode.value = "";
	fm.RptModeName.value = "";
	fm.Relation.value = "";
	fm.RelationName.value = "";
	fm.RptDate.value = mCurrentDate;
	fm.RgtFlag.value = "0";	
	fm.RgtFlagName.value = "否";	
	fm.RptInputOperator.value = mOperator;
	fm.RptInputDate.value = mCurrentDate;
	fm.RptCom.value = mManageCom;
	fm.RptComName.value = "";
	fm.RptConfOperator.value = "";
	fm.RptConfDate.value = "";	
}

/**
 * 初始化报案出险人事件信息
 */
function initReportCustomerInfo() {
	
	fm.SubRptNo.value = "";
	fm.CustomerNo.value = "";
	fm.CustomerName.value = "";
	fm.Birthday.value = "";
	fm.EmpNo.value = "";
	fm.IDNo.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.Gender.value = "";
	fm.GenderName.value = "";
	fm.CustomerRemarks.value = "";
	fm.SelfAppntNo.value = "";
	fm.SelfAppntName.value = "";
	validCustomerInfo();
	
}

/**
 * 初始化客户不可用
 */
function disableCustomerInfo() {
		
		fm.CustomerName.disabled = true;	
		fm.Birthday.disabled = true;
		fm.EmpNo.disabled = true;
		fm.IDNo.disabled = true;
		fm.IDType.disabled = true;
		fm.IDTypeName.disabled = true;
		fm.Gender.disabled = true;
		fm.GenderName.disabled = true;				
}
/**
 * 初始化客户可用
 */
function validCustomerInfo() {
	
		fm.CustomerName.disabled = false;	
		fm.Birthday.disabled = false;
		fm.EmpNo.disabled = false;
		fm.IDNo.disabled = false;
		fm.IDType.disabled = false;
		fm.IDTypeName.disabled = false;
		fm.Gender.disabled = false;
		fm.GenderName.disabled = false;		
}

/**
 * 初始化报案出险人事件信息
 */
function initCustomerCaseInfo() {
	
	fm.AccReason.value = "";
	fm.AccReasonName.value = "";
	fm.AccDate.value = "";
	fm.HospitalCode.value = "";
	fm.HospitalName.value = "";
	fm.ClaimPay.value = "";
	fm.AccGrade.value = "0";
	fm.AccGradeName.value = "否";
	fm.AccSite.value = "";
	for (var i=0;i<fm.ClaimType.length;i++) {
		
		fm.ClaimType[i].checked = false;
	}	
	fm.AccDesc.value = "";
	fm.CaseRemark.value = "";
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
 * mType:3-不可维护；4-重大案件上报
 */
function initButton() {
	
	try {
		
		if (mType==3) {
			
			fm.ReportSave.disabled = true;
			fm.ReportDelete.disabled = true;
			fm.CustomerAdd.disabled = true;
			fm.CustomerModify.disabled = true;
			fm.CustomerDelete.disabled = true;
			fm.AddCase.disabled = true;
			fm.ModifyCase.disabled = true;
			fm.DeleteCase.disabled = true;
			fm.ReportConfirm.disabled = true;
			$("#divReportConfirmButton").css("display","none");
			
		} else if (mType==4) {
			
			fm.ReportSave.disabled = true;
			fm.ReportDelete.disabled = true;
			fm.CustomerAdd.disabled = true;
			fm.CustomerModify.disabled = true;
			fm.CustomerDelete.disabled = true;
			fm.AddCase.disabled = true;
			fm.ModifyCase.disabled = true;
			fm.DeleteCase.disabled = true;
			fm.ReportConfirm.disabled = true;
			fm.BigReport.disabled = true;
			$("#divReportConfirmButton").css("display","none");
			$("#divMajorInfo").css("display","");
			initMajorInfo();	
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
// 客户信息列表
function initCustomerListGrid() {
		
		turnPage1 = new turnPageClass();
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许
    
    iArray[i]=new Array();
    iArray[i][0]="客户编码";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="姓名";             
    iArray[i][1]="200px";                
    iArray[i][2]=200;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="性别"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="出生日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码"; 
    iArray[i][1]="200px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="员工号"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="备注"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="SubRptNo"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=3;    
    
    CustomerListGrid = new MulLineEnter( "fm" , "CustomerListGrid" ); 
    //这些属性必须在loadMulLine前
    CustomerListGrid.mulLineCount = 0;   
    CustomerListGrid.displayTitle = 1;
    CustomerListGrid.locked = 0;
    CustomerListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
		CustomerListGrid.selBoxEventFuncName ="showSelectCustomerInfo"; //响应的函数名，不加扩号    
    CustomerListGrid.hiddenPlus=1;
    CustomerListGrid.hiddenSubtraction=1;
    CustomerListGrid.loadMulLine(iArray);     
    }
    catch(ex){
        alert("ex");
    }
}

// 事件列表信息
function initAccidentListGrid() {

		turnPage2 = new turnPageClass();  
    var iArray = new Array();
    var i=0;
    try{
    
		iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

		iArray[i]=new Array();
    iArray[i][0]="事件号";             
    iArray[i][1]="180px";                
    iArray[i][2]=180;                 
    iArray[i++][3]=0;
    
		iArray[i]=new Array();
    iArray[i][0]="客户号";             
    iArray[i][1]="120px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
		iArray[i]=new Array();
    iArray[i][0]="出险原因";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="出险日期"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="就诊医院"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="索赔金额"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="是否重大案件"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    AccidentListGrid = new MulLineEnter( "fm" , "AccidentListGrid" ); 
    //这些属性必须在loadMulLine前
    AccidentListGrid.mulLineCount = 0;   
    AccidentListGrid.displayTitle = 1;
    AccidentListGrid.locked = 0;
    AccidentListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    AccidentListGrid.selBoxEventFuncName ="showSelReportCaseInfo"; //响应的函数名，不加扩号
    AccidentListGrid.hiddenPlus=1;
    AccidentListGrid.hiddenSubtraction=1;
    AccidentListGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("初始化界面信息出错->SelfLLClaimReportGrid");
    }
}
// 事件责任信息列表
function initGrpClaimDutyGrid() {

		turnPage3 = new turnPageClass();
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="120px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PolNo"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="RiskCode"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="险种名称"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="DutyCode"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="责任名称"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="GetDutyCode"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="给付责任名称"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任起期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="责任止期"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="等待期"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PeroidFlag"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="免赔方式"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="免赔额或免赔天数"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="赔付比例"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="有效保额"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="次限额"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任状态"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;    
    
    GrpClaimDutyGrid = new MulLineEnter( "fm" , "GrpClaimDutyGrid" ); 
    //这些属性必须在loadMulLine前
    GrpClaimDutyGrid.mulLineCount = 0;   
    GrpClaimDutyGrid.displayTitle = 1;
    GrpClaimDutyGrid.locked = 0;
    GrpClaimDutyGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    GrpClaimDutyGrid.hiddenPlus=1;
    GrpClaimDutyGrid.hiddenSubtraction=1;
    GrpClaimDutyGrid.loadMulLine(iArray);     
    }
    catch(ex){
        alert("ex");
    }
}
</script>
