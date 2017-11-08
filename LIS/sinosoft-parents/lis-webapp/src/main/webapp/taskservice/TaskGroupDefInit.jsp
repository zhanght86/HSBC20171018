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
	queryTaskGroupInfo();
	initTaskGroupDetailGrid();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initTaskGroupGrid()
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
      iArray[1][0]="任务队列编码";             	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="任务队列描述";         		//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
      TaskGroupGrid = new MulLineEnter( "fm" , "TaskGroupGrid" ); 
      //这些属性必须在loadMulLine前
      TaskGroupGrid.mulLineCount = 1;   
      TaskGroupGrid.displayTitle = 1;
      TaskGroupGrid.canSel =1;
      TaskGroupGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      TaskGroupGrid.hiddenSubtraction=1;
	  TaskGroupGrid.selBoxEventFuncName = "onTaskGroupSelected";

	  TaskGroupGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}



function initTaskGroupDetailGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			//var tSQL = "select taskcode,taskdescribe from ldtask order by taskcode";
			var mySql = new SqlClass();
 			mySql.setResourceName("taskservice.TaskGroupDefSql");
	    mySql.setSqlId("TaskGroupDefSql1");  
	    mySql.addSubPara("1");  
			var ttemp = (easyQueryVer3(mySql.getString(), 1, 0, 1));
			
      iArray[1]=new Array();
      iArray[1][0]="任务编码";          	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
  		iArray[1][10]="TaskCode";  //引用代码:
			iArray[1][11]=ttemp;
    
      iArray[2]=new Array();
      iArray[2][0]="依赖任务编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      /*
      var tSQL1 = " select '000000','无依赖' from dual "
               + " union "
               + " select taskcode,taskdescribe from ldtask  ";
			var ttemp1 = (easyQueryVer3(tSQL1, 1, 0, 1));
			
			
      iArray[2][10]="DependTaskCode";  //引用代码:
			iArray[2][11]=ttemp1;
*/

      iArray[3]=new Array();
      iArray[3][0]="任务描述";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="依赖类型";      	   		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=2; 
      iArray[4][10]="DependType"; 
      iArray[4][11]="0|^00|无依赖^01|父任务需要正确执行完毕才执行^02|父任务只要执行完毕即可执行"; 

      iArray[5]=new Array();
      iArray[5][0]="失败后动作";      	   		//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][10]="ActionAfterFail"; 
      iArray[5][11]="0|^00|无动作^01|挂起"; 

      iArray[6]=new Array();
      iArray[6][0]="执行顺序";      	   		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=1; 

    
		
      TaskGroupDetailGrid = new MulLineEnter( "fm" , "TaskGroupDetailGrid" ); 
      //这些属性必须在loadMulLine前
      TaskGroupDetailGrid.mulLineCount = 1;   
      TaskGroupDetailGrid.displayTitle = 1;
      //TaskGroupDetailGrid.canSel =1;
      //TaskGroupDetailGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      //TaskGroupDetailGrid.hiddenSubtraction=1;
	  	//TaskGroupDetailGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  	TaskGroupDetailGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
