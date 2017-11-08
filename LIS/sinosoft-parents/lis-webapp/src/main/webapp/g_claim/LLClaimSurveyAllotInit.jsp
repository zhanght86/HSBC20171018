<%
/***************************************************************
 * <p>PreName：LLClaimSurveyAllotInput.jsp</p>
 * <p>Title：调查任务分配</p>
 * <p>Description：调查任务分配</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-21
 ****************************************************************/
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
/**
 * 初始化界面

 */
function initForm() {
	
	try {
		initParam();
		initInpBox();
		initButton();

    initSurveyTaskGrid();
    initSurveyTask1Grid();
		limitSurveyDept();//查询权限

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


function initSurveyTaskGrid() {
  var iArray = new Array();
  var  i=0;
  try
  {
  	
    iArray[i]=new Array();
    iArray[i][0]="序号";               	//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";                //列宽
    iArray[i][2]=8;                  	//列最大值
    iArray[i++][3]=0;                   //是否允许输入,1表示允许，0表示不允许

		iArray[i]=new Array();
    iArray[i][0]="调查序号";          
    iArray[i][1]="80px";             
    iArray[i][2]=200;                 
    iArray[i++][3]=3;  

    iArray[i]=new Array();
    iArray[i][0]="案件号";            
    iArray[i][1]="120px";              
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

 /*  iArray[i]=new Array();
    iArray[i][0]="出险人客户号";          
    iArray[i][1]="0px";              
    iArray[i][2]=300;                 
    iArray[i++][3]=3; */

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
    
    SurveyTaskGrid = new MulLineEnter("fm","SurveyTaskGrid");
    SurveyTaskGrid.mulLineCount =0;
    SurveyTaskGrid.displayTitle = 1;
    SurveyTaskGrid.locked = 1;
    SurveyTaskGrid.canSel =1;
    SurveyTaskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    SurveyTaskGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    SurveyTaskGrid.selBoxEventFuncName = "SurveyTaskGridSelClick";
    SurveyTaskGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}


function initSurveyTask1Grid() {
  var iArray = new Array();
  var  i=0;
  try
  {
    iArray[i]=new Array();
    iArray[i][0]="序号";               	//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";                //列宽
    iArray[i][2]=8;                  	//列最大值
    iArray[i++][3]=0;                   //是否允许输入,1表示允许，0表示不允许

	iArray[i]=new Array();
    iArray[i][0]="调查序号";            
    iArray[i][1]="80px";              
    iArray[i][2]=80;                  
    iArray[i++][3]=3; 

    iArray[i]=new Array();
    iArray[i][0]="案件号";            
    iArray[i][1]="120px";              
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
/*
    iArray[i]=new Array();
    iArray[i][0]="出险人客户号";          
    iArray[i][1]="0px";              
    iArray[i][2]=300;                 
    iArray[i++][3]=3;                   
*/
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
    iArray[i][0]="调查机构";          
    iArray[i][1]="80px";              
    iArray[i][2]=200;
    iArray[i++][3]=3; 

    SurveyTask1Grid = new MulLineEnter("fm","SurveyTask1Grid");
    SurveyTask1Grid.mulLineCount =0;
    SurveyTask1Grid.displayTitle = 1;
    SurveyTask1Grid.locked = 1;
    SurveyTask1Grid.canSel =1;
    SurveyTask1Grid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    SurveyTask1Grid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    SurveyTask1Grid.selBoxEventFuncName = "SurveyTaskGridSelClick1";
    SurveyTask1Grid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}

</script>