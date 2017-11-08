<%
/***************************************************************
 * <p>ProName：LLClaimCaseCheckInit.jsp</p>
 * <p>Title：赔案审批录入初始化</p>
 * <p>Description：赔案审批录入初始化</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<script language="JavaScript">

var conditionCode = "1";

/**
 * 初始化界面

 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();

		//已关联事件的责任信息
		initOnEventDutyListGrid();
		//待关联事件的责任信息
		initOffEventDutyListGrid();
		//事件赔付信息列表
		initCaseDutyPayGrid();
		//客户信息列表
		initCustomerListGrid();
		//事件信息列表
		initAccidentListGrid();
		//个人历史赔付信息
		initPayInfoListGrid();		
		
		if (mGrpRegisterNo!=null && mGrpRegisterNo!="" && mGrpRegisterNo!="null") {
			
			queryAcceptInfo();		
			queryCustomerList();			
			//showSelectCustomer();
			
			if (fm.CaseType.value!="02") {
				
				/* fm.BPOCheckInfo.style.display = "none";				
				fm.QuestionQuery.style.display = "none"; */
				
				if (fm.CaseType.value!="01") {
					conditionCode = "1 and (codealias=#"+fm.CaseType.value+"# or codealias is null) and code<>#6# ";
				} else {
					conditionCode = "1 and (codealias=#00# or codealias is null) and code<>#6# ";
				}
				
			} else {
				
				conditionCode = "1 and (codealias=#00# or codealias is null) and code<>#6# ";
			}			
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
		initPayInfo();
		initCaseReviewInfo();
		initChkConClusion();
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}
/**
 * 初始化受理信息
 */
function initAcceptInfo() {
		
		fm.GrpRgtNo.value = mGrpRegisterNo;
		
		fm.AppntName.value = "";
		fm.PageNo.value = "";
		fm.ConfirmDate.value = "";
		fm.ConfirmOperator.value = "";		
		fm.AcceptCom.value = "";
		fm.AcceptComName.value = "";
				
		fm.SumRealPay.value = "";
		fm.CaseType.value = "";
		fm.CaseTypeName.value = "";
		fm.PayType.value = "02";//01-团单给付；02-个人给付
		fm.PayType.checked = false;
}
/**
 * 初始化客户信息
 */
function initCustomerInfo() {
	
	fm.BankName.value = "";		
	fm.BankProvince.value = "";	
	fm.BankCity.value = "";
	fm.AccNo.value = "";
	fm.AccName.value = "";
	fm.OldClmNo.value = "";
}
/**
 * 初始化出险人事件信息
 */
function initCaseInfo() {

	mFlag = "";
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
	fm.TreatResult.value = "";
	fm.TreatResultName.value = "";
	fm.CaseSource.value = "";
	fm.CaseSourceName.value = "";
	fm.LRCaseNo.value = "";

	/* fm.ProvinceName.value = "";
	fm.Province.value = "";	
	fm.CityName.value = "";	
	fm.City.value = "";	
	fm.CountyName.value = "";
	fm.County.value = ""; */
	fm.AccidentPlace.value = "";
	fm.AccidentRemarks.value = "";
	fm.Remarks.value = "";
	
	for (var i=0;i<fm.ClaimType.length;i++) {
		
		fm.ClaimType[i].checked = false;
	}
	
	var tCloseInfo = document.getElementById("divCloseAccident");
	tCloseInfo.style.display="none";
	fm.CloseReasonDesc.value = "";
	fm.CloseRemarkDesc.value = "";
	
	//$("#divPayModify").css("display","none");	
}
/**
 * 初始化责任赔付信息
 */
function initPayInfo() {
	
	fm.GiveType.value = "";
	fm.GiveTypeName.value = "";
	fm.AdjReason.value = "";
	fm.AdjReasonName.value = "";
	fm.RealPay.value = "";
	fm.NoGiveReason.value = "";
	fm.NoGiveReasonName.value = "";
	fm.SpecialRemark.value = "";
	fm.AdjRemark.value = "";		
}
/**
 * 初始化审核结论信息
 */
function initCaseReviewInfo() {
	
	fm.Conclusion.value = "";
	fm.ConclusionName.value = "";
	fm.ReviewAdvice.value = "";
	fm.AgainReviewAdvice.value = "";	
}
/**
 * 初始化复核结论
 */
function initChkConClusion() {
	
	fm.ChkConclusion.value = "";
	fm.ChkConclusionName.value = "";
	fm.ChkAdvice.value = "";
	fm.ChkAdviceName.value = "";	
	fm.CheckAdvice.value = "";
}
/**
 * 初始化审批结论
 */
function initAppConClusion() {
	
	fm.AppConclusion.value = "";
	fm.AppConclusionName.value = "";
	fm.AppAdvice.value = "";
	fm.AppAdviceName.value = "";	
	fm.ApproveAdvice.value = "";
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
		
		//mMode:0-可操作；1-查看
		if (mMode=="1") {
			fm.showTrace.style.display = "none";
			fm.CaseConfirm.style.display = "none";
			fm.BatchCaseConfirm.style.display = "none";
			mClmState = " and a.clmstate in ('50','60','70','80')";			
		} else {
			
			fm.showTrace.style.display = "";
			fm.CaseConfirm.style.display = "";
			fm.BatchCaseConfirm.style.display = "none";
			mClmState = " and a.clmstate='50' ";			
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
//客户信息列表
function initCustomerListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="28px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="案件号";
		iArray[i][1]="120px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="姓名";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Gender";
		iArray[i][1]="0px";
		iArray[i][2]=60;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="性别";
		iArray[i][1]="40px";
		iArray[i][2]=40;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出生日期";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="IDType";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="证件类型";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="证件号码";
		iArray[i][1]="120px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="员工号";
		iArray[i][1]="70px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="申请金额";
		iArray[i][1]="50px";
		iArray[i][2]=60;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="赔付金额";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="发票数";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="回传发票数";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保全标志";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="录入天数";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="案件状态";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="黑名单标识";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		           
    
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
        alert("ex");
    }
}

//客户保单赔付信息
function initPayInfoListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="28px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="单位名称";
		iArray[i][1]="200px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="200px";
		iArray[i][2]=140;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="赔付金额";
		iArray[i][1]="180px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="赔付次数";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="赔付事件数";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=0;		
    
		PayInfoListGrid = new MulLineEnter( "fm" , "PayInfoListGrid" ); 
	    //这些属性必须在loadMulLine前
	    PayInfoListGrid.mulLineCount = 0;   
	    PayInfoListGrid.displayTitle = 1;
	    PayInfoListGrid.locked = 0;
	    PayInfoListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）   
	    PayInfoListGrid.hiddenPlus=1;
	    PayInfoListGrid.hiddenSubtraction=1;
	    PayInfoListGrid.loadMulLine(iArray);     
    }
    catch(ex){
        alert("ex");
    }
}

// 事件信息列表
function initAccidentListGrid() {
	       
    var iArray = new Array();
    var i=0;
    try{
    
			iArray[i]=new Array();
			iArray[i][0]="序号";
			iArray[i][1]="30px";
			iArray[i][2]=30;
			iArray[i++][3]=0;
		
			iArray[i]=new Array();
			iArray[i][0]="事件号";
			iArray[i][1]="80px";
			iArray[i][2]=120;
			iArray[i++][3]=0;
			      
			iArray[i]=new Array();
			iArray[i][0]="出险原因";
			iArray[i][1]="60px";
			iArray[i][2]=60;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="出险日期";
			iArray[i][1]="80px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="医院等级";
			iArray[i][1]="60px";
			iArray[i][2]=60;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="保险方案";
			iArray[i][1]="60px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="与主被保险人关系";
			iArray[i][1]="80px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="主被保险人姓名";
			iArray[i][1]="80px";
			iArray[i][2]=100;
			iArray[i++][3]=0;
		
			iArray[i]=new Array();
			iArray[i][0]="索赔金额";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="审核金额";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="赔付金额";
			iArray[i][1]="50px";	
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="理算标志";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
		   	iArray[i]=new Array();
			iArray[i][0]="casestate";
			iArray[i][1]="0px";
			iArray[i][2]=80;
			iArray[i++][3]=3;	 
			
		  	iArray[i]=new Array();
			iArray[i][0]="事件状态";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			AccidentListGrid = new MulLineEnter("fm","AccidentListGrid");
			AccidentListGrid.mulLineCount = 0;
			AccidentListGrid.displayTitle = 1;
			AccidentListGrid.locked = 0;
			AccidentListGrid.canSel =1;
			AccidentListGrid.selBoxEventFuncName ="showSelectCase";
			AccidentListGrid.selBoxEventFuncParm ="1";
			AccidentListGrid.hiddenPlus=1;
			AccidentListGrid.hiddenSubtraction=1;
			AccidentListGrid.loadMulLine(iArray); 

    } catch(ex){
        alert("初始化界面信息出错->AccidentListGrid");
    }
}

// 事件已关联责任信息列表
function initOnEventDutyListGrid() {
	       
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
				
		OnEventDutyListGrid = new MulLineEnter("fm","OnEventDutyListGrid");
		OnEventDutyListGrid.mulLineCount = 0;
		OnEventDutyListGrid.displayTitle = 1;
		OnEventDutyListGrid.locked = 0;
		OnEventDutyListGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
		OnEventDutyListGrid.canChk= 1;
		//OnEventDutyListGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号
		//OnEventDutyListGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
		OnEventDutyListGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		OnEventDutyListGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		OnEventDutyListGrid.loadMulLine(iArray);

    }
    catch(ex){
        alert("初始化界面信息出错->CaseDutyInfoGrid");
    }
}

// 事件待关联信息列表
function initOffEventDutyListGrid() {
	
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
			
		OffEventDutyListGrid = new MulLineEnter("fm","OffEventDutyListGrid");
		OffEventDutyListGrid.mulLineCount = 0;
		OffEventDutyListGrid.displayTitle = 1;
		OffEventDutyListGrid.locked = 0;
		OffEventDutyListGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
		OffEventDutyListGrid.canChk= 1;
		//OffEventDutyListGrid.selBoxEventFuncName ="showCaseDetail"; //响应的函数名，不加扩号
		//OffEventDutyListGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
		OffEventDutyListGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		OffEventDutyListGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		OffEventDutyListGrid.loadMulLine(iArray); 
    }
    catch(ex){
			alert("初始化界面信息出错->NoCaseDutyInfoGrid");
    }
}

// 事件赔付信息
function initCaseDutyPayGrid() {
	       
    var iArray = new Array();
    var i=0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=30;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="100px";
		iArray[i][2]=100;
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
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="DutyCode";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;			
		
		iArray[i]=new Array();
		iArray[i][0]="责任名称";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="GettDutyCode";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;			
		
		iArray[i]=new Array();
		iArray[i][0]="给付责任名称";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="GetDutyKind";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;			
		
		iArray[i]=new Array();
		iArray[i][0]="理赔类型";
		iArray[i][1]="60px";
		iArray[i][2]=120;
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
		iArray[i][0]="审核金额";
		iArray[i][1]="50px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="理算金额";
		iArray[i][1]="50px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="赔付金额";
		iArray[i][1]="50px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="使用公共保额";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="冲减后保额";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="GiveType";
		iArray[i][1]="0px";
		iArray[i][2]=150;
		iArray[i++][3]=3;						
		     
		iArray[i]=new Array();
		iArray[i][0]="赔付结论";
		iArray[i][1]="50px";
		iArray[i][2]=150;
		iArray[i++][3]=0;			
		
		CaseDutyPayGrid = new MulLineEnter("fm","CaseDutyPayGrid");
		CaseDutyPayGrid.mulLineCount = 0;
		CaseDutyPayGrid.displayTitle = 1;
		CaseDutyPayGrid.locked = 0;
		CaseDutyPayGrid.canSel =1;
		CaseDutyPayGrid.hiddenPlus=1;
		CaseDutyPayGrid.selBoxEventFuncName ="showAjustInfo"; //响应的函数名，不加扩号			
		CaseDutyPayGrid.hiddenSubtraction=1;
		CaseDutyPayGrid.loadMulLine(iArray); 

    }
    catch(ex){
        alert("初始化界面信息出错->CaseDutyPayGrid");
    }
}
</script>
