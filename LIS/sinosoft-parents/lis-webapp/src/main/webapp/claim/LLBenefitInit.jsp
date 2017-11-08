<!--用户校验类-->
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	//=======================BGN========================
	String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//String tContNo = request.getParameter("ContNo"); 
	String tCustNo = request.getParameter("CustomerNo"); 
	//=======================END========================
%>
<script type="text/javascript">
var mCurrentDate = "";
//接收报案页面传递过来的参数
function initParam(){
	mCurrentDate="<%= CurrentDate %>";
    fm.CustNo.value = nullToEmpty("<%= tCustNo %>");
    
}

//把null的字符串转为空
function nullToEmpty(string){
	if ((string == "null") || (string == "undefined")){
		string = "";
	}
	return string;
}

function initForm(){
    try{
        initParam();
        initBox();                   	//初始化表格
        initPaidDutyGrid();		//承保账号信息
        initPaidFeeGrid();			//理赔账号信息
        initQuery();					//初始化查询
    }catch(re){
        alert("LLMedicalFeeInpInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

/**=========================================================================
    页面初始化
   =========================================================================
*/
function initBox(){
  	try{
  	}catch(ex){
    	alert("LLMedicalFeeInpInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  	}
}

/**=========================================================================
    已赔付给付责任详情
   =========================================================================
*/
function initPaidDutyGrid(){
    var iArray = new Array();
    try{  
    	var i=0;
    	
    	iArray[i]=new Array();
        iArray[i][0]="序号";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="保单号";
        iArray[i][1]="100px";
        iArray[i][2]=10;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="险种编码";
        iArray[i][1]="90px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="责任名称";
        iArray[i][1]="150px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="责任赔付上限";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 

        iArray[i]=new Array();
        iArray[i][0]="给付责任名称";
        iArray[i][1]="120px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="给付责任赔付上限";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        iArray[i]=new Array();
        iArray[i][0]="账单核算总金额";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        iArray[i]=new Array();
        iArray[i][0]="已赔付金额";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="剩余赔付金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
       /* 
        iArray[i]=new Array();
        iArray[i][0]="预授权总金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="已预授权金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="预授权剩余金额";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="预授权总次数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="已预授权次数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="预授权剩余次数";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="预授权总天数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="已预授权天数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="预授权剩余天数";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        */
        iArray[i]=new Array();
        iArray[i][0]="责任编码";
        iArray[i][1]="0px";
        iArray[i][2]=140;
        iArray[i++][3]=3;  
        
        iArray[i]=new Array();
        iArray[i][0]="给付责任编码";
        iArray[i][1]="0px";
        iArray[i][2]=140;
        iArray[i++][3]=3;  
        
        PaidDutyGrid = new MulLineEnter("fm","PaidDutyGrid");
        PaidDutyGrid.mulLineCount = 0;
        PaidDutyGrid.displayTitle = 1;
        PaidDutyGrid.locked = 0;
//      PaidDutyGrid.canChk =1;              //多选按钮，1显示，0隐藏
        PaidDutyGrid.canSel =1;              //单选按钮，1显示，0隐藏
        PaidDutyGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        PaidDutyGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        PaidDutyGrid.selBoxEventFuncName = "getPaidDutyGrid"; //函数名称
//      PaidDutyGrid.selBoxEventFuncParm ="";          //参数        
        PaidDutyGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}

/**=========================================================================
    已赔付账单详情
   =========================================================================
*/
function initPaidFeeGrid(){
    var iArray = new Array();
    try{
    	var i=0;
    	iArray[i]=new Array();
        iArray[i][0]="序号";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="账单名称";
        iArray[i][1]="150px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="费用类型";
        iArray[i][1]="70px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="账单赔付上限";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="账单核算金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        /*
        iArray[i]=new Array();
        iArray[i][0]="已预授权金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        */
        iArray[i]=new Array();
        iArray[i][0]="剩余赔付金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="赔付次数上限";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="已赔付次数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        /*
        iArray[i]=new Array();
        iArray[i][0]="已预授权次数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
         */ 
        iArray[i]=new Array();
        iArray[i][0]="剩余赔付次数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="赔付天数上限";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="已赔付天数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        /*
        iArray[i]=new Array();
        iArray[i][0]="已预授权天数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        */  
        iArray[i]=new Array();
        iArray[i][0]="剩余赔付天数";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        PaidFeeGrid = new MulLineEnter("fm","PaidFeeGrid");
        PaidFeeGrid.mulLineCount = 0;
        PaidFeeGrid.displayTitle = 1;
        PaidFeeGrid.locked = 0;
//      PaidFeeGrid.canChk =1;              //多选按钮，1显示，0隐藏
        PaidFeeGrid.canSel =1;              //单选按钮，1显示，0隐藏
        PaidFeeGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        PaidFeeGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        PaidFeeGrid.selBoxEventFuncName = "getPaidFeeGrid"; //函数名称
//      PaidFeeGrid.selBoxEventFuncParm ="";          //参数        
        PaidFeeGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}
</script>

