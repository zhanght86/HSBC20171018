<%
//程序名称：TaskServiceInput.jsp
//程序功能：
//创建日期：2004-12-15 
//创建人  ：ZhangRong
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
}

function initSelBox()
{  
}                                        

function initForm()
{
  try
  {
//alert('1');

	initTaskServerPlanGrid();
//alert('2');
	queryTaskServerPlanGrid();
	
	initTaskServerConfigGrid();
	
//	alert('3');
	initServerGrid();
	queryServerGrid();
	
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initServerGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="参数编码";          	//列名
      iArray[1][1]="30px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="参数名称";         		//列名
      iArray[2][1]="30px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="参数值";      	   		//列名
      iArray[3][1]="30px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1; 

   
      ServerGrid = new MulLineEnter( "fm" , "ServerGrid" ); 
      //这些属性必须在loadMulLine前
      ServerGrid.mulLineCount = 1;   
      ServerGrid.displayTitle = 1;
      ServerGrid.canSel =0;
      ServerGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      ServerGrid.hiddenSubtraction=1;
	 // TaskPlanGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  ServerGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}


function initTaskServerPlanGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="服务节点IP";          	//列名
      iArray[1][1]="60px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="服务节点端口";         		//列名
      iArray[2][1]="40px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="最近刷新日期";      	   		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0; 

      iArray[4]=new Array();
      iArray[4][0]="最近刷新时间";      	   		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     

      iArray[5]=new Array();
      iArray[5][0]="是否有效";      	   		//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=2; 
 			iArray[5][10]="ValidFlag"; 
      iArray[5][11]="0|^0|无效^1|有效"; 
      
      iArray[6]=new Array();
      iArray[6][0]="运行服务数";      	   		//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0; 

   
      TaskServerPlanGrid = new MulLineEnter( "fm" , "TaskServerPlanGrid" ); 
      //这些属性必须在loadMulLine前
      TaskServerPlanGrid.mulLineCount = 1;   
      TaskServerPlanGrid.displayTitle = 1;
      TaskServerPlanGrid.canSel =1;
      TaskServerPlanGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      TaskServerPlanGrid.hiddenSubtraction=1;
	 	  TaskServerPlanGrid.selBoxEventFuncName = "onTaskServerSelected";

	  TaskServerPlanGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initTaskServerConfigGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="参数编码";          	//列名
      iArray[1][1]="30px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="参数名称";         		//列名
      iArray[2][1]="30px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="参数值";      	   		//列名
      iArray[3][1]="30px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1; 

   
      TaskServerConfigGrid = new MulLineEnter( "fm" , "TaskServerConfigGrid" ); 
      //这些属性必须在loadMulLine前
      TaskServerConfigGrid.mulLineCount = 1;   
      TaskServerConfigGrid.displayTitle = 1;
      TaskServerConfigGrid.canSel =0;
      TaskServerConfigGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      TaskServerConfigGrid.hiddenSubtraction=1;
	 // TaskPlanGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  	TaskServerConfigGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
