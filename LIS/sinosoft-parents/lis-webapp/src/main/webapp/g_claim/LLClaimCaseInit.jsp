<%
/***************************************************************
 * <p>ProName：LLClaimCaseInit.js</p>
 * <p>Title：立案申请初始化</p>
 * <p>Description：立案申请初始化</p>
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
		
		initOffEventDutyListGrid();
		initOnEventDutyListGrid();
		
		initCustomerListGrid();
		initEventlistGrid();
		
		if (mGrpRegisterNo!=null && mGrpRegisterNo!="" && mGrpRegisterNo!="null") {
			
			queryAcceptInfo();
			queryCustomerList();
			//queryCustomerCaseList();
			//showSelectCustomer();				
		} else {
			fm.GrpRgtNo.value="";
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
		initAcceptInfo();
		initCustomerInfo();
		initCaseInfo();
		
	} catch (re) {		
		alert("初始化参数错误！");
	}
}

/**
 * 初始化受理信息
 */
function initAcceptInfo() {
		
		fm.GrpRgtNo.value = mGrpRegisterNo;

		fm.RgtClass.value = "2";
		fm.RgtClassName.value = "团单";
		fm.AppntNo.value = "";
		fm.AppntName.value = "";
		fm.AppDate.value = mCurrentDate;
		fm.AcceptDate.value=mCurrentDate;
		fm.PageNo.value = "";
		fm.AcceptOperator.value = mOperator;
		fm.AcceptOperatorName.value = mOperator;
		fm.AcceptCom.value = mManageCom;
		//fm.AcceptComName.value = mManageComName;
}
/**
 * 初始化客户信息
 */
function initCustomerInfo() {
	
	validCustomerInfo();
	fm.RegisterNo.value = "";
	fm.CustomerNo.value = "";		
	fm.CustomerName.value = "";	
	fm.Birthday.value = "";
	fm.EmployeNo.value = "";
	fm.IDNo.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.Gender.value="";
	fm.GenderName.value = "";
	fm.AppAmnt.value = "";
	fm.BillCount.value = "";
	fm.ScanCount.value = "";
	fm.IsUrgent.value = "0";
	fm.IsUrgentName.value = "否";
	fm.IsOpenBillFlag.value = "0";
	fm.IsOpenBillFlagName.value = "否";
	fm.IsBackBill.value = "0";
	fm.IsBackBillName.value = "否";
	fm.BankCode.value = "";
	fm.BankCodeName.value = "";
	fm.Province.value = "";
	fm.ProvinceName.value = "";
	fm.City.value = "";	
	fm.CityName.value = "";	
	fm.BankAccNo.value = "";	
	fm.AccName.value = "";
	fm.CustomerAppDate.value = "";
	fm.Remark.value = "";
	fm.RegisterNo.value = "";
	fm.SelfAppntNo.value = "";
	fm.TelPhone.value = "";	
}
/**
 * 初始化客户不可用
 */
function disableCustomerInfo() {
		
		fm.CustomerName.disabled = true;	
		fm.Birthday.disabled = true;
		fm.EmployeNo.disabled = true;
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
		fm.EmployeNo.disabled = false;
		fm.IDNo.disabled = false;
		fm.IDType.disabled = false;
		fm.IDTypeName.disabled = false;
		fm.Gender.disabled = false;
		fm.GenderName.disabled = false;		
}
/**
 * 初始化出险人事件信息
 */
function initCaseInfo() {
	
	fm.CaseNo.value = "";
	fm.AccReason.value = "";
	fm.AccReasonName.value = "";
	fm.AccDate.value="";
	fm.ClaimPay.value = "";
	fm.HospitalCode.value = "";
	fm.HospitalName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
	fm.DeformityDate.value = "";
	fm.DeathDate.value = "";
	fm.TreatResult.value = "01";
	fm.TreatResultName.value = "痊愈";	
	fm.CaseSource.value = "01";
	fm.CaseSourceName.value = "手工录入";
	fm.LRCaseNo.value = "";
	
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
 */
function initButton() {
	
	try {
		fm.CustomerSave.style.display="none";
		//根据页面类型设置页面，mode:0-可操作；1-只查看；2-特殊立案
		if(mMode!=null && mMode=="1") {
			fm.CustomerSave.style.display="none";
			fm.PageNoApply.style.display="none";
			fm.AcceptSave.style.display="none";
			fm.Acceptdelete.style.display="none";
			fm.CustomerAdd.style.display="none";
			fm.CustomerModify.style.display="none";
			fm.CustomerDelete.style.display="none";
			fm.noAccept.style.display="none";
			fm.AcceptDelete.style.display="none";
			fm.CaseAdd.style.display="none";
			fm.CaseModify.style.display="none";
			fm.CaseDelete.style.display="none";
			fm.AssociateOff.style.display="none";
			fm.AssociateOn.style.display="none";
			fm.OverCase.style.display="none";
			fm.ImportCustomer.style.display="none";
		} else if (mMode!=null && mMode=="2") {
			
			 fm.AcceptSave.style.display="none";
			 fm.Acceptdelete.style.display="none";
			fm.CustomerAdd.style.display="none";
			fm.CustomerModify.style.display="none";
			fm.CustomerDelete.style.display="none";
			fm.noAccept.style.display="none";
			fm.AcceptDelete.style.display="none";
			 fm.CaseAdd.style.display="";
			fm.CaseModify.style.display="";
			 fm.CaseDelete.style.display="";
			fm.AssociateOff.style.display="";
			fm.AssociateOn.style.display="";
			 fm.OverCase.style.display="none";
			// fm.ImportCustomer.style.display="none"; 
			fm.CustomerSave.style.display="";
			//$("#CustomerListInfo").css("display","none"); 
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

// 初始化客户信息列表
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
    iArray[i][0]="个人案件号";             
    iArray[i][1]="100px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="客户编码";             
    iArray[i][1]="0px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="姓名";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="性别编码";             
    iArray[i][1]="0px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="性别";             
    iArray[i][1]="60px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="出生日期"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="证件类型编码"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="证件类型名称"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码"; 
    iArray[i][1]="120px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="员工号"; 
    iArray[i][1]="80px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="申请金额"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="发票数"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    CustomerListGrid = new MulLineEnter( "fm" , "CustomerListGrid" ); 
    //这些属性必须在loadMulLine前
    CustomerListGrid.mulLineCount = 0;   
    CustomerListGrid.displayTitle = 1;
    CustomerListGrid.locked = 0;
    CustomerListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    CustomerListGrid.selBoxEventFuncName ="showSelectCustomer"; //响应的函数名，不加扩号    
    CustomerListGrid.hiddenPlus=1;
    CustomerListGrid.hiddenSubtraction=1;
    CustomerListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->CustomerListGrid");
    }
}

// 初始化客户的事件信息列表
function initEventlistGrid() {
	  
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
    iArray[i][0]="赔案号";             
    iArray[i][1]="0px";                
    iArray[i][2]=200;                 
    iArray[i++][3]=3; 
    
    iArray[i]=new Array();
    iArray[i][0]="事件号";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="客户号";             
    iArray[i][1]="0px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;      

    iArray[i]=new Array();
    iArray[i][0]="出险原因"; 
    iArray[i][1]="60px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="出险日期"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="医院名称"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;    
    
    /* iArray[i]=new Array();
    iArray[i][0]="医院等级"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="医院性质"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
 */
    iArray[i]=new Array();
    iArray[i][0]="索赔金额"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="审核金额"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="账单数"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    EventlistGrid = new MulLineEnter( "fm" , "EventlistGrid" ); 
    //这些属性必须在loadMulLine前
    EventlistGrid.mulLineCount = 0;   
    EventlistGrid.displayTitle = 1;
    EventlistGrid.locked = 0;
    EventlistGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    EventlistGrid.selBoxEventFuncName ="showSelectCase"; //响应的函数名，不加扩号
    EventlistGrid.hiddenPlus=1;
    EventlistGrid.hiddenSubtraction=1;
    EventlistGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("初始化界面信息出错->EventlistGrid");
    }
}

// 初始化客户的事件已关联责任信息列表
function initOnEventDutyListGrid() {
	  
	  turnPage3 = new turnPageClass();    
    var iArray = new Array();
    var i=0;
    try{
    	
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="100px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PolNo"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="RiskCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="险种名称"; 
    iArray[i][1]="150px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="DutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="责任名称"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="GetDutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="给付责任名称"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任起期"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="责任止期"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="等待期"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PeroidFlag"; 
    iArray[i][1]="0px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="免赔方式"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="免赔额或免赔天数"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="赔付比例"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="有效保额"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="次限额"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任状态"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    OnEventDutyListGrid = new MulLineEnter( "fm" , "OnEventDutyListGrid" ); 
    //这些属性必须在loadMulLine前
    OnEventDutyListGrid.mulLineCount = 0;   
    OnEventDutyListGrid.displayTitle = 1;
    OnEventDutyListGrid.locked = 0;
    OnEventDutyListGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
    OnEventDutyListGrid.canChk =1; // 1 显示 ；0 隐藏（缺省值）
    //OnEventDutyListGrid.selBoxEventFuncName ="selfLLReport"; //响应的函数名，不加扩号
    OnEventDutyListGrid.hiddenPlus=1;
    OnEventDutyListGrid.hiddenSubtraction=1;
    OnEventDutyListGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("初始化界面信息出错->EventlistGrid");
    }
}

// 初始化客户的事件待关联责任信息列表
function initOffEventDutyListGrid() {
	  
	  turnPage4 = new turnPageClass();   
    var iArray = new Array();
    var i=0;
    try{
    	
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="保单号";             
    iArray[i][1]="100px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PolNo"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="RiskCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="险种名称"; 
    iArray[i][1]="150px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="DutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="责任名称"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="GetDutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="给付责任名称"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任起期"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="责任止期"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="等待期"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PeroidFlag"; 
    iArray[i][1]="0px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="免赔方式"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="免赔额或免赔天数"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="赔付比例"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="有效保额"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="次限额"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="责任状态"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                  
    
    OffEventDutyListGrid = new MulLineEnter( "fm" , "OffEventDutyListGrid" ); 
    //这些属性必须在loadMulLine前
    OffEventDutyListGrid.mulLineCount = 0;   
    OffEventDutyListGrid.displayTitle = 1;
    OffEventDutyListGrid.locked = 0;
    OffEventDutyListGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
    OffEventDutyListGrid.canChk =1; // 1 显示 ；0 隐藏（缺省值）
    //OffEventDutyListGrid.selBoxEventFuncName ="selfLLReport"; //响应的函数名，不加扩号
    OffEventDutyListGrid.hiddenPlus=1;
    OffEventDutyListGrid.hiddenSubtraction=1;
    OffEventDutyListGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("初始化界面信息出错->EventlistGrid");
    }
}
</script>
