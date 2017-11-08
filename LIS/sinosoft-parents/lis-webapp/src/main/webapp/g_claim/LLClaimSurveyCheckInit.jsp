<%
/***************************************************************
 * <p>ProName：LLSurveyReviewCheckInit.jsp</p>
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
		
		//initPublicPoolGrid(); 
		initPrivatePoolGrid(); 
		
		QueryUwInfo();
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
//初始化"任务队列"表格
function initPublicPoolGrid()   
{
  var iArray = new Array();
  var  i=0;
  try
  {
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
		      		                  			 
    PublicPoolGrid= new MulLineEnter( "fm" , "PublicPoolGrid"); 
    PublicPoolGrid.mulLineCount = 1;
    PublicPoolGrid.displayTitle = 1;
    PublicPoolGrid.canSel = 1;      //是否出现RadioBox
    PublicPoolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    PublicPoolGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    PublicPoolGrid.loadMulLine(iArray);      
     }
    catch(ex)
    { 
    	alert(ex); 
    }
    }
  
function initPrivatePoolGrid()
{
	
var iArray = new Array();
  var  i=0;
  try
  {
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
		      		                  			 
    PrivatePoolGrid= new MulLineEnter( "fm" , "PrivatePoolGrid"); 
    PrivatePoolGrid.mulLineCount = 0;
    PrivatePoolGrid.displayTitle = 1;
    PrivatePoolGrid.canSel = 1;      //是否出现RadioBox
    PrivatePoolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    PrivatePoolGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    PrivatePoolGrid.selBoxEventFuncName = "ShowSurveyReviewEntry";//单击RadioBox时响应函数
    PrivatePoolGrid.loadMulLine(iArray);      
	}catch(ex) { 
    	alert(ex); 
   }
}
</script>