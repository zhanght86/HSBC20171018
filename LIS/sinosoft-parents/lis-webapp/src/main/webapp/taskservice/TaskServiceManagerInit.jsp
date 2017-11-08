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
	initTaskPlanGrid();
//alert('2');
	queryTaskPlanInfo();
//	alert('3');
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initTaskPlanGrid()
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
      iArray[1][0]="任务计划编码";          	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="任务编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="任务描述";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="是否启用";      	   		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=2; 
      iArray[4][10]="RunFlagList"; 
      iArray[4][11]="0|^0|等待^1|启动"; 

      iArray[5]=new Array();
      iArray[5][0]="循环类型";      	   		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][10]="RecycleTypeList"; 
      iArray[5][11]="0|^11|每分钟一次^21|每小时一次^31|每日一次^41|每周一次^51|每月一次^61|每年一次^72|多次"; 

      iArray[6]=new Array();
      iArray[6][0]="起始时间";      	   		//列名
      iArray[6][1]="140px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0; 

      iArray[7]=new Array();
      iArray[7][0]="终止时间";      	   		//列名
      iArray[7][1]="140px";            		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0; 

      iArray[8]=new Array();
      iArray[8][0]="循环间隔";            //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=3; 

      iArray[9]=new Array();
      iArray[9][0]="循环次数";      	   		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=3; 

      iArray[10]=new Array();
      iArray[10][0]="运行状态";      	   		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=2; 
      iArray[10][10]="RunStateList"; 
      iArray[10][11]="0|^0|等待^1|启动^2|暂停^3|正常终止^4|强行终止^5|异常终止^6|挂起"; 
      
      iArray[11]=new Array();
      iArray[11][0]="任务/队列";      	   		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=20;            			//列最大值
      iArray[11][3]=2; 
      iArray[11][10]="RunStateList"; 
      iArray[11][11]="0|^T|任务^G|队列"; 
	  
	  iArray[12]=new Array();
      iArray[12][0]="任务进度";      	   		//列名
      iArray[12][1]="170px";            		//列宽
      iArray[12][2]=20;            			//列最大值
      iArray[12][3]=0; 
      TaskPlanGrid = new MulLineEnter( "fm" , "TaskPlanGrid" ); 
      //这些属性必须在loadMulLine前
      TaskPlanGrid.mulLineCount = 1;   
      TaskPlanGrid.displayTitle = 1;
      TaskPlanGrid.canSel =1;
      TaskPlanGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      TaskPlanGrid.hiddenSubtraction=1;
	 // TaskPlanGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  TaskPlanGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}


</script>
