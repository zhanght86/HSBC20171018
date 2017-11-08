<!--用户校验类-->
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%

	//=======================BGN========================
	String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	String tRgtNo = request.getParameter("RgtNo"); //赔案号
	String tContNo = request.getParameter("ContNo"); 
	String tCustomerNo = request.getParameter("CustomerNo"); 
	System.out.println("xxx"+tCustomerNo);
	//=======================END========================
%>

<script type="text/javascript">
var mCurrentDate = "";
//接收报案页面传递过来的参数
function initParam(){
	<%
	System.out.println("zzz"+tCustomerNo);

	%>
	mCurrentDate=<%=CurrentDate%>;

    fm.RgtNo.value = nullToEmpty(<%=tRgtNo%>);
   
    fm.ContNo.value = nullToEmpty(<%=tContNo%>);
    
    fm.CustNo.value = nullToEmpty(<%=tCustomerNo%>);
   
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
        initThisDutyGrid();		//承保账号信息
        initThisFeeGrid();			//理赔账号信息
        initQuery();			//初始化查询
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
function initThisDutyGrid(){
    var iArray = new Array();
    try{ 
    	var i=0;
    	iArray[i]=new Array();
        iArray[i][0]="序号";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="责任名称";
        iArray[i][1]="120px";
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
        iArray[i][0]="已赔付金额";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="剩余赔付金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="账单实际金额";
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
        iArray[i][0]="是否有预授权";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="预授权金额";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        */
        iArray[i]=new Array();
        iArray[i][0]="是否扣除自负比例";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="自负比例";
        iArray[i][1]="80px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
       
        iArray[i]=new Array();
        iArray[i][0]="免赔额";
        iArray[i][1]="60px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="本次赔付金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="给付责任";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=3; 
       
        ThisDutyGrid = new MulLineEnter("fm","ThisDutyGrid");
        ThisDutyGrid.mulLineCount = 0;
        ThisDutyGrid.displayTitle = 1;
        ThisDutyGrid.locked = 0;
//      ThisDutyGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ThisDutyGrid.canSel =1;              //单选按钮，1显示，0隐藏
        ThisDutyGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ThisDutyGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        ThisDutyGrid.selBoxEventFuncName = "getThisDutyGrid"; //函数名称
//      ThisDutyGrid.selBoxEventFuncParm ="";          //参数        
        ThisDutyGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}

/**=========================================================================
    已赔付账单详情
   =========================================================================
*/
function initThisFeeGrid(){
    var iArray = new Array();
    try{
    	var i= 0 ;
    	iArray[i]=new Array();
        iArray[i][0]="序号";
        iArray[i][1]="30px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="账单名称";
        iArray[i][1]="130px";
        iArray[i][2]=10;
        iArray[i++][3]=0;

        iArray[i]=new Array();
        iArray[i][0]="费用类型";
        iArray[i][1]="70px";
        iArray[i][2]=140;
        iArray[i++][3]=0;        
        
        iArray[i]=new Array();
        iArray[i][0]="账单赔付上限";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;   
        
        iArray[i]=new Array();
        iArray[i][0]="剩余赔付金额";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="账单实际金额";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
        
        iArray[i]=new Array();
        iArray[i][0]="赔付次数上限";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;  
          
        iArray[i]=new Array();
        iArray[i][0]="剩余次数";
        iArray[i][1]="70px";
        iArray[i][2]=140;
        iArray[i++][3]=0; 
        
        iArray[i]=new Array();
        iArray[i][0]="本次赔付次数";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="赔付天数上限";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="剩余赔付天数";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="本次赔付天数";
        iArray[i][1]="100px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="每次赔付金额上限";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        iArray[i]=new Array();
        iArray[i][0]="每天赔付固定金额";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        /*
        iArray[i]=new Array();
        iArray[i][0]="是否预授权标识";
        iArray[i][1]="120px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        */
        iArray[i]=new Array();
        iArray[i][0]="本次核算金额";
        iArray[i][1]="90px";
        iArray[i][2]=140;
        iArray[i++][3]=0;
        
        ThisFeeGrid = new MulLineEnter("fm","ThisFeeGrid");
        ThisFeeGrid.mulLineCount = 0;
        ThisFeeGrid.displayTitle = 1;
        ThisFeeGrid.locked = 0;
//      ThisFeeGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ThisFeeGrid.canSel =1;              //单选按钮，1显示，0隐藏
        ThisFeeGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ThisFeeGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        ThisFeeGrid.selBoxEventFuncName = "getThisFeeGrid"; //函数名称
//      ThisFeeGrid.selBoxEventFuncParm ="";          //参数        
        ThisFeeGrid.loadMulLine(iArray);
    }catch(ex){
        alert(ex);
    }
}
</script>

