<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2008-04-10
%>
<!--用户校验类-->

<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file = "../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>


<%
 	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getAttribute("GI");
  String Operator=tG.Operator;
  String Comcode=tG.ManageCom;
 	String CurrentDate= PubFun.getCurrentDate(); 
%>
<script type="text/javascript">
function initInpBox()
{
  try
  {
    fm.all('StartDate').value= '';
    fm.all('EndDate').value= '';
  }         
  catch(ex) 
  {
    myAlert("进行初始化是出现错误！");
  }
}
;

// 下拉框的初始化
function initSelBox()
{
  try{
  }
  catch(ex)
  {
    myAlert("在LRContInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try{
    initInpBox();
    initSelBox();
    initTaskListGrid();
   
    initTaskList();
  }
  catch(re){
    myAlert("ReContManageInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
  }
}

////任务列表的初始化
function initTaskListGrid(){
    var iArray = new Array();   
    try{
			iArray[0]=new Array();
			iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1]="30px";            		//列宽
			iArray[0][2]=40;            			  //列最大值
			iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
			
			iArray[1]=new Array();
			iArray[1][0]="批次号"; 
			iArray[1][1]="40px"; 
			iArray[1][2]=100; 
			iArray[1][3]=0; 
			
			iArray[2]=new Array();
			iArray[2][0]="分出责任编号";
			iArray[2][1]="40px";  
			iArray[2][2]=100; 
			iArray[2][3]=0;     
			
			iArray[3]=new Array();
			iArray[3][0]="起始日期";
			iArray[3][1]="40px";  
			iArray[3][2]=100; 
			iArray[3][3]=0;   
			
			iArray[4]=new Array();
			iArray[4][0]="终止日期";
			iArray[4][1]="40px";  
			iArray[4][2]=100; 
			iArray[4][3]=0;   
			
			iArray[5]=new Array();
			iArray[5][0]="任务状态";
			iArray[5][1]="50px";  
			iArray[5][2]=100; 
			iArray[5][3]=0;
			
			iArray[6]=new Array();
			iArray[6][0]="任务状态代码";
			iArray[6][1]="25px";  
			iArray[6][2]=100; 
			iArray[6][3]=3;
			
	  	TaskListGrid = new MulLineEnter( "fm" , "TaskListGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	TaskListGrid.mulLineCount = 0;   
	  	TaskListGrid.displayTitle = 1;
	  	TaskListGrid.locked = 0;
	  	TaskListGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
	  	TaskListGrid.canChk = 1; //是否增加多选框,1为显示CheckBox列，0为不显示 (缺省值)
	  	//TaskListGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号   
	  	TaskListGrid.hiddenPlus=1; 
	  	TaskListGrid.hiddenSubtraction=1;
	  	TaskListGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex){
        myAlert(ex);
    }
}

</script>


