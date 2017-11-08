<%
/***************************************************************
 * <p>ProName：LLClaimSurveyAppinput.jsp</p>
 * <p>Title：调查录入申请</p>
 * <p>Description：调查录入申请</p>
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
		
		initLLSurveyTaskGrid();
		queryInqInfo();
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		
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


function initLLSurveyTaskGrid()   
{
  var iArray = new Array();
  var  i=0;
  try	{
    iArray[i]=new Array();
    iArray[i][0]="序号";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="调查序号";          
    iArray[i][1]="80px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="报案号/案件号";            
    iArray[i][1]="130px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

    iArray[i]=new Array();
    iArray[i][0]="调查批次";        
    iArray[i][1]="60px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=0;                   

		iArray[i]=new Array();
    iArray[i][0]="投保人名称";          
    iArray[i][1]="120px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=0;                     

    iArray[i]=new Array();
    iArray[i][0]="出险人姓名";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="证件号码";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="调查发起日期";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="调查类型";          
    iArray[i][1]="60px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="调查原因";          
    iArray[i][1]="180px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="调查发起机构";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="是否异地调查";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="调查机构";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=0;     		    
		      		                  			 
    LLSurveyTaskGrid= new MulLineEnter( "fm" , "LLSurveyTaskGrid"); 
    LLSurveyTaskGrid.mulLineCount = 0;
    LLSurveyTaskGrid.displayTitle = 1;
    LLSurveyTaskGrid.canSel = 1;      //是否出现RadioBox
    LLSurveyTaskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    LLSurveyTaskGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    LLSurveyTaskGrid.selBoxEventFuncName = "LLSurveyTaskGridClick";//单击RadioBox时响应函数
    LLSurveyTaskGrid.loadMulLine(iArray);      
		
		}
    catch(ex){ 
    	
    	alert(ex); 
    }
	}


</script>