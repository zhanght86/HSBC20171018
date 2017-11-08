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
	
	
	initLogItemGrid();
	ItemQuery();
	
	//queryTaskRunLogTodayGrid();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function getBusinessState()
{
	  var iArray = new Array();      
	  //          

    	
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="日志号";             	//列名
      iArray[1][1]="50px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="任务批次号码";         		//列名
      iArray[2][1]="40px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="关键号码";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="logkeytype";
	  	iArray[3][17]="1";
		
      iArray[4]=new Array();
      iArray[4][0]="业务状态";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="日志信息";         		//列名
      iArray[5][1]="180px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="日志辅助信息";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="日志产生日期";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="日志产生时间";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="日志修改日期";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="日志修改时间";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      return iArray;
}

function getTrackResult()
{
	  var iArray = new Array();      
	  //          
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="日志号";             	//列名
      iArray[1][1]="50px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="任务批次号码";         		//列名
      iArray[2][1]="40px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="关键号码";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="logkeytype";
	  	iArray[3][17]="1";
		
      iArray[4]=new Array();
      iArray[4][0]="控制点日志信息";         		//列名
      iArray[4][1]="180px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="日志辅助信息";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="日志产生日期";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="日志产生时间";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
     
      return iArray;
}

function initTaskRunLogTodayGrid(tItemID)
{                               
    var iArray = new Array();      
    try
    {
    	if(tItemID=='01'||tItemID=='04')
    	{
    	  iArray = getBusinessState();
		  }
		  else
		  {
		  	 iArray = getTrackResult();
		  }
      TaskRunLogTodayGrid = new MulLineEnter( "fm" , "TaskRunLogTodayGrid" ); 
      //这些属性必须在loadMulLine前
      TaskRunLogTodayGrid.mulLineCount = 1;   
      TaskRunLogTodayGrid.displayTitle = 1;
      TaskRunLogTodayGrid.canSel =0;
      TaskRunLogTodayGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      TaskRunLogTodayGrid.hiddenSubtraction=1;
	 	 // TaskRunLogTodayGrid.selBoxEventFuncName = "onTaskGroupSelected";

	  	TaskRunLogTodayGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}


function initTaskRunLogBeforeGrid(tItemID)
{                               
    var iArray = new Array();      
    try
    {
    	if(tItemID=='01'||tItemID=='04')
    	{
    	  iArray = getBusinessState();
		  }
		  else
		  {
		  	 iArray = getTrackResult();
		  }
      
		
      TaskRunLogBeforeGrid = new MulLineEnter( "fm" , "TaskRunLogBeforeGrid" ); 
      //这些属性必须在loadMulLine前
      TaskRunLogBeforeGrid.mulLineCount = 1;   
      TaskRunLogBeforeGrid.displayTitle = 1;
      TaskRunLogBeforeGrid.canSel =0;
      TaskRunLogBeforeGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      TaskRunLogBeforeGrid.hiddenSubtraction=1;
	 	  //TaskRunLogBeforeGrid.selBoxEventFuncName = "onTaskGroupSelected";

	  	TaskRunLogBeforeGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}



function initLogItemGrid()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=10;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="控制点ID";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[1][1]="80px";            		//列宽
		iArray[1][2]=10;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="日志监控点ID";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[2][1]="100px";            		//列宽
		iArray[2][2]=10;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[3]=new Array();
		iArray[3][0]="日志监控点名称";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[3][1]="100px";            		//列宽
		iArray[3][2]=10;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		
		
		iArray[4]=new Array();
		iArray[4][0]="控制点关键号码类型";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[4][1]="80px";            		//列宽
		iArray[4][2]=10;            			//列最大值
		iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
		iArray[4][4]="logkeytype";
		iArray[4][17]="1";
		
		iArray[5]=new Array();                                                    
		iArray[5][0]="监控开关";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[5][1]="50px";            		//列宽                                
		iArray[5][2]=10;            			//列最大值                              
		iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许   
		iArray[5][10]="ItemSwitch";
		iArray[5][11]="0|^Y|开^N|关";
		
	

      
		LogItemGrid = new MulLineEnter( "fm" , "LogItemGrid" ); 
		//这些属性必须在loadMulLine前
		
		LogItemGrid.mulLineCount = 0;
		LogItemGrid.displayTitle = 1;
		LogItemGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogItemGrid.hiddenSubtraction = 1;
		LogItemGrid.canSel=1;
		LogItemGrid.selBoxEventFuncName = "ShowItem";
		LogItemGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
