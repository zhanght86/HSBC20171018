<%
/***************************************************************
 * <p>ProName：LLClaimMedicalInit.jsp</p>
 * <p>Title：非医疗账单初始化页面</p>
 * <p>Description：非医疗账单初始化页面</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
%>

<script language="JavaScript">

function initForm(){
	try{

		initClinicGrid();
		initClinicBillItemGrid();
	 	InitHospBillGrid();
		initHospBillItemGrid();	
		initSpecialClinicBillGrid();
		initSpecialClinicItemGrid();		
		initSpecialHospGrid();
		initSpecialHospItemGrid();
		initParam();
		initButton();
		initDrugFeeMaintainGrid();
		initDrugFeeMaintain1Grid();
		queryClinicBillInfo();
		queryInitHospBillInfo();		
		querySpecialClinicInfo();		
		querySpecialHospInfo();		
	}catch(ex){
		alert("LLMediacalFeeInpInit.jsp-->initForm函数中发生异常:初始化界面错误!");
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
		fm.tCaseNo1.value=tCaseNo;
		fm.tCaseNo2.value=tCaseNo;
		fm.tCaseNo3.value=tCaseNo;
		fm.tCaseNo4.value=tCaseNo;
		
		fm.CaseSource.value=tCaseSource;
		queryEasyDeductItem(ClinicBillItemGrid);
		queryEasyDeductItem(HospBillItemGrid);
		getCaseDateInfo();
		
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
		
		if(tMode==0){
			document.getElementById("addClinicBill1").style.display="";
			document.getElementById("modifyClinicBill1").style.display="";
			document.getElementById("deleteClinicBill1").style.display="";
			document.getElementById("closePage1").style.display="";
 
			document.getElementById("addHospBill2").style.display="";
			document.getElementById("modifyHospBill2").style.display="";
			document.getElementById("deleteHospBill2").style.display="";
			document.getElementById("closePage2").style.display="";

			document.getElementById("addSpeicalClinic3").style.display="";
			document.getElementById("modifySpecialClinic3").style.display="";
			document.getElementById("deleteSpecialClinic3").style.display="";
			document.getElementById("closePage3").style.display="";

			document.getElementById("addSpecialHospBill4").style.display="";
			document.getElementById("modifySpecialHospBill4").style.display="";
			document.getElementById("deleteSpecialHospBill4").style.display="";
			document.getElementById("closePage4").style.display="";
			document.getElementById("closePage5").style.display="none";

		}else if(tMode==1){
			
			document.getElementById("addClinicBill1").style.display="none";
			document.getElementById("modifyClinicBill1").style.display="none";
			document.getElementById("deleteClinicBill1").style.display="none";
			document.getElementById("closePage1").style.display="none";
					 
			document.getElementById("addHospBill2").style.display="none";
			document.getElementById("modifyHospBill2").style.display="none";
			document.getElementById("deleteHospBill2").style.display="none";
			document.getElementById("closePage2").style.display="none";
			
			document.getElementById("addSpeicalClinic3").style.display="none";
			document.getElementById("modifySpecialClinic3").style.display="none";
			document.getElementById("deleteSpecialClinic3").style.display="none";
			document.getElementById("closePage3").style.display="none";
    	
			document.getElementById("addSpecialHospBill4").style.display="none";
			document.getElementById("modifySpecialHospBill4").style.display="none";
			document.getElementById("deleteSpecialHospBill4").style.display="none";
			document.getElementById("closePage4").style.display="none";
			document.getElementById("closePage5").style.display="";
			
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
/**=========================================================================
    门诊账单录入信息
   =========================================================================
*/
function initClinicGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="事件号";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="发票号";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="就诊医院编码";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="就诊医院";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="就诊日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账单发生金额";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除金额";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="合理金额";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="主要诊断编码";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="主要诊断";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="诊断详情编码";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="诊断详情";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除说明";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="影像件关联";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
				
		iArray[i]=new Array();
		iArray[i][0]="其他说明";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="医疗账单编码";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="出险日期";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;		
		
		ClinicGrid = new MulLineEnter("fm","ClinicGrid");
		ClinicGrid.mulLineCount = 0;//默认初始化显示行数
		ClinicGrid.displayTitle = 1;
		ClinicGrid.locked = 0;
		ClinicGrid.canChk = 0;//单选按钮，1显示，0隐藏
		ClinicGrid.canSel = 1;//单选按钮，1显示，0隐藏
		ClinicGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		ClinicGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		ClinicGrid.selBoxEventFuncName = "getClinicInfo"; //函数名称
		ClinicGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化门诊费用明细信息报错!--ClinicGrid");
	}
}
/**
	* 药品费用查询
	*/
function initDrugFeeMaintainGrid(){
	
	turnPage6 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）             
		iArray[i][1]="30px";         			//列宽                                                     
		iArray[i][2]=10;          			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="药品流水号";    			//列名                                                     
		iArray[i][1]="40px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许                      
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="地区编码";    			//列名                                                     
		iArray[i][1]="40px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;     
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="地区";    			//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
			                                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="更新日期";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
		                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="药品名称";         		//列名                                                     
		iArray[i][1]="100px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;             			//是否允许输入,1表示允许，0表示不允许                      
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="商品名称";         		//列名                                                     
		iArray[i][1]="100px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="剂型";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="规格";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		 
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="自付比例";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="医保类型";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="价格";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="限制内容";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用  
      
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="医保类型编码";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用  
     
     iArray[i]=new Array();                                                                                       
		iArray[i][0]="扣减金额";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=1;   
     
		DrugsGrid = new MulLineEnter("fm", "DrugsGrid");
		DrugsGrid.mulLineCount = 0;
		DrugsGrid.displayTitle = 1;
		DrugsGrid.locked = 1;
		DrugsGrid.canSel = 1;
		DrugsGrid.canChk = 0;
		DrugsGrid.hiddenSubtraction = 1;
		DrugsGrid.hiddenPlus = 1;
		DrugsGrid.selBoxEventFuncName = "showPermissionInfo";
		DrugsGrid.loadMulLine(iArray);

		}catch(ex){
			alert("初始化信息错误");
			return false;
		}
	}
/**=========================================================================
    门诊账单费用明细信息
   =========================================================================
*/
function initClinicBillItemGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="费用项目类型编码";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="费用项目类型";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除金额";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除说明";
		iArray[i][1]="200px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="备注";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=1;		
		
		ClinicBillItemGrid = new MulLineEnter("fm","ClinicBillItemGrid");
		ClinicBillItemGrid.mulLineCount = 1;//默认初始化显示行数
		ClinicBillItemGrid.displayTitle = 1;
		ClinicBillItemGrid.locked = 0;
		ClinicBillItemGrid.canSel = 1;//单选按钮，1显示，0隐藏
		ClinicBillItemGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		ClinicBillItemGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		//ClinicBillItemGrid.selBoxEventFuncName = ""; //函数名称
		ClinicBillItemGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化费用明细信息报错!--BillItemGrid");
	}
}
/**=========================================================================
    住院账单录入信息
   =========================================================================
*/
function InitHospBillGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="事件号";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="发票号";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="就诊医院编码";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="就诊医院";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="入院日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出院日期";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="住院天数";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账单发生金额";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除金额";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="合理金额";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="主要诊断编码";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="主要诊断";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="诊断详情编码";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="诊断详情";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除说明";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="影像件关联";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
				
		iArray[i]=new Array();
		iArray[i][0]="其他说明";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;	
		
	
		
		iArray[i]=new Array();
		iArray[i][0]="医疗账单编码";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="出险日期";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;		
		
		HospBillGrid = new MulLineEnter("fm","HospBillGrid");
		HospBillGrid.mulLineCount = 0;//默认初始化显示行数
		HospBillGrid.displayTitle = 1;
		HospBillGrid.locked = 0;
		HospBillGrid.canChk = 0;//单选按钮，1显示，0隐藏
		HospBillGrid.canSel = 1;
		HospBillGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		HospBillGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		HospBillGrid.selBoxEventFuncName = "getHospBillInfo"; //函数名称
		HospBillGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化住院费用明细信息报错!--HospBillGrid");
	}
}
/**
	* 药品费用查询
	*/
function initDrugFeeMaintain1Grid(){
	
	turnPage7 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）             
		iArray[i][1]="30px";         			//列宽                                                     
		iArray[i][2]=10;          			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="药品流水号";    			//列名                                                     
		iArray[i][1]="40px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许                      
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="地区编码";    			//列名                                                     
		iArray[i][1]="40px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;     
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="地区";    			//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
			                                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="更新日期";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
		                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="药品名称";         		//列名                                                     
		iArray[i][1]="100px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;             			//是否允许输入,1表示允许，0表示不允许                      
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="商品名称";         		//列名                                                     
		iArray[i][1]="100px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="剂型";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="规格";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		 
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="自付比例";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="医保类型";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="价格";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="限制内容";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用  
      
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="医保类型编码";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用  
     
     iArray[i]=new Array();                                                                                       
		iArray[i][0]="扣减金额";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=1;   
     
		Drugs1Grid = new MulLineEnter("fm", "Drugs1Grid");
		Drugs1Grid.mulLineCount = 0;
		Drugs1Grid.displayTitle = 1;
		Drugs1Grid.locked = 1;
		Drugs1Grid.canSel = 1;
		Drugs1Grid.canChk = 0;
		Drugs1Grid.hiddenSubtraction = 1;
		Drugs1Grid.hiddenPlus = 1;
		Drugs1Grid.selBoxEventFuncName = "showPermissionInfo";
		Drugs1Grid.loadMulLine(iArray);

		}catch(ex){
			alert("初始化信息错误");
			return false;
		}
	}
/**=========================================================================
    住院账单费用明细信息
   =========================================================================
*/
function initHospBillItemGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
	iArray[i]=new Array();
		iArray[i][0]="费用项目类型编码";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="费用项目类型";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除金额";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除说明";
		iArray[i][1]="200px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="备注";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=1;		
		
		HospBillItemGrid = new MulLineEnter("fm","HospBillItemGrid");
		HospBillItemGrid.mulLineCount = 1;//默认初始化显示行数
		HospBillItemGrid.displayTitle = 1;
		HospBillItemGrid.locked = 0;
		HospBillItemGrid.canSel = 1;//单选按钮，1显示，0隐藏
		HospBillItemGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		HospBillItemGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		//HospBillItemGrid.selBoxEventFuncName = ""; //函数名称
		HospBillItemGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化费用明细信息报错!--HospBillItemGrid");
	}
}
/**=========================================================================
    特殊门诊单证录入信息
   =========================================================================
*/
function initSpecialClinicBillGrid() {
	
    var iArray = new Array();
    var i=0;
    try{
			iArray[i]=new Array();
			iArray[i][0]="序号";
			iArray[i][1]="30px";//列宽
			iArray[i][2]=10;//列最大值
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="事件号";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="发票号";
			iArray[i][1]="80px";
			iArray[i][2]=120;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="就诊医院编码";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="就诊医院";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="就诊日期";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="账单发生金额";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="扣除金额";
			iArray[i][1]="60px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="合理金额";
			iArray[i][1]="60px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="主要诊断编码";
			iArray[i][1]="50px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="主要诊断";
			iArray[i][1]="100px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="诊断详情编码";
			iArray[i][1]="50px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="诊断详情";
			iArray[i][1]="100px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="扣除说明";
			iArray[i][1]="150px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
				
			iArray[i]=new Array();
			iArray[i][0]="影像件关联";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
					
			iArray[i]=new Array();
			iArray[i][0]="其他说明";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=3;

			iArray[i]=new Array();
			iArray[i][0]="医疗账单编码";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="出险日期";
			iArray[i][1]="100px";
			iArray[i][2]=300;
			iArray[i++][3]=0;			

			SpecialClinicBillGrid = new MulLineEnter("fm","SpecialClinicBillGrid");
			SpecialClinicBillGrid.mulLineCount = 0;
			SpecialClinicBillGrid.displayTitle = 1;
			SpecialClinicBillGrid.locked = 0;
			SpecialClinicBillGrid.canSel =1;              //单选按钮，1显示，0隐藏
			SpecialClinicBillGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
			SpecialClinicBillGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
			SpecialClinicBillGrid.selBoxEventFuncName = "getSpecialClinicBillInfo"; //函数名称
			SpecialClinicBillGrid.loadMulLine(iArray);
    }
    catch(ex) {
        alert(ex);
    }
}
/**=========================================================================
    特殊门诊账单费用明细信息
   =========================================================================
*/
function initSpecialClinicItemGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="费用类型代码";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="费用类型";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i][3]=2;
		iArray[i][4]="llfeeitemtyp2";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][19]=1;

		iArray[i]=new Array();
		iArray[i][0]="费用金额";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="自费";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="部分自费";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="不合理自费";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;        
		
		iArray[i]=new Array();
		iArray[i][0]="扣除金额";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除说明";
		iArray[i][1]="200px";
		iArray[i][2]=120;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="备注";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=1;
		
		SpecialClinicItemGrid = new MulLineEnter("fm","SpecialClinicItemGrid");
		SpecialClinicItemGrid.mulLineCount = 1;//默认初始化显示行数
		SpecialClinicItemGrid.displayTitle = 1;
		SpecialClinicItemGrid.locked = 0;
		SpecialClinicItemGrid.canSel = 0;//单选按钮，1显示，0隐藏
		SpecialClinicItemGrid.hiddenPlus = 0;//＋号，1隐藏，0显示
		SpecialClinicItemGrid.hiddenSubtraction = 0;//－号：1隐藏，0显示
		//SpecialClinicItemGrid.selBoxEventFuncName = ""; //函数名称
		SpecialClinicItemGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("SpecialClinicItemGrid初始化错误!");
	}
}

/**=========================================================================
    特殊住院录入信息
   =========================================================================
*/
function initSpecialHospGrid() {
	
    var iArray = new Array();
    var i=0;
    try {
    	
		    iArray[i]=new Array();
				iArray[i][0]="序号";
				iArray[i][1]="30px";//列宽
				iArray[i][2]=10;//列最大值
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="事件号";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="发票号";
				iArray[i][1]="80px";
				iArray[i][2]=120;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="就诊医院编码";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="就诊医院";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
			
				iArray[i]=new Array();
				iArray[i][0]="入院日期";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="出院日期";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="住院天数";
				iArray[i][1]="60px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
			
				iArray[i]=new Array();
				iArray[i][0]="账单发生金额";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="扣除金额";
				iArray[i][1]="60px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="合理金额";
				iArray[i][1]="60px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="主要诊断编码";
				iArray[i][1]="50px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="主要诊断";
				iArray[i][1]="100px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="诊断详情编码";
				iArray[i][1]="50px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="诊断详情";
				iArray[i][1]="100px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="扣除说明";
				iArray[i][1]="150px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
					
				iArray[i]=new Array();
				iArray[i][0]="影像件关联";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
						
				iArray[i]=new Array();
				iArray[i][0]="其他说明";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="医疗账单编码";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="出险日期";
				iArray[i][1]="100px";
				iArray[i][2]=300;
				iArray[i++][3]=0;				
				
				SpecialHospGrid = new MulLineEnter("fm","SpecialHospGrid");
				SpecialHospGrid.mulLineCount = 0;
				SpecialHospGrid.displayTitle = 1;
				SpecialHospGrid.locked = 0;
				SpecialHospGrid.canSel =1;              //单选按钮，1显示，0隐藏
				SpecialHospGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
				SpecialHospGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
				SpecialHospGrid.selBoxEventFuncName = "getSpecialHospInfo"; //函数名称    
				SpecialHospGrid.loadMulLine(iArray);
    }
    catch(ex) {
			alert(ex);
    }
}
/**=========================================================================
    特殊住院账单费用明细信息
   =========================================================================
*/
function initSpecialHospItemGrid() {
	
	turnPage5 = new turnPageClass();
  var iArray = new Array();
  var i = 0;
  try {
  	    	
    iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="费用类型代码";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i++][3]=1;


		iArray[i]=new Array();
		iArray[i][0]="费用类型";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i][3]=2;
		iArray[i][4]="llfeeitemtyp2";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][19]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="费用金额";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="自费";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="部分自费";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="不合理自费";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;        
		
		iArray[i]=new Array();
		iArray[i][0]="扣除金额";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="扣除说明";
		iArray[i][1]="200px";
		iArray[i][2]=120;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="备注";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=1;
      
		SpecialHospItemGrid = new MulLineEnter("fm","SpecialHospItemGrid");
		SpecialHospItemGrid.mulLineCount = 1;
		SpecialHospItemGrid.displayTitle = 1;
		SpecialHospItemGrid.locked = 0;
		//SpecialHospItemGrid.canChk =1;              //多选按钮，1显示，0隐藏
		SpecialHospItemGrid.canSel =0;              //单选按钮，1显示，0隐藏
		SpecialHospItemGrid.hiddenPlus=0;           //＋号，1隐藏，0显示
		SpecialHospItemGrid.hiddenSubtraction=0;    //－号：1隐藏，0显示     
		SpecialHospItemGrid.loadMulLine(iArray);	
		
	} catch(ex) {
		
		alert(ex);
  }
}
</script>
