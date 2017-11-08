<%
//程序名称：LogComponentInit.jsp
//程序功能：
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {          
    document.all('LogSubjectID').value = '';
    document.all('LogSubjectDes').value = '';
    document.all('LogDept').value = '';
    document.all('LogServiceModule').value = '';
    document.all('TaskCode').value = '';
    document.all('LogType').value = '';                         
    document.all('Switch').value = 'Y';
    document.all('SwitchName').value = '开';
  }
  catch(ex)
  {
    alert("在LogComponentInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


function initForm()
{
    initInpBox();
    initLogSubjectGrid();
    LogQuery();
}

function initLogSubjectGrid()
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
		iArray[1][0]="日志主题";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[1][1]="80px";            		//列宽
		iArray[1][2]=10;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="主题描述";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[2][1]="100px";            		//列宽
		iArray[2][2]=10;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="监控部门";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[3][1]="80px";            		//列宽
		iArray[3][2]=10;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="所属业务模块";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[4][1]="80px";            		//列宽
		iArray[4][2]=10;            			//列最大值
		iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
		iArray[4][4]="logservicemodule";   
		
		iArray[5]=new Array();
		iArray[5][0]="业务功能编码";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[5][1]="80px";            		//列宽
		iArray[5][2]=10;            			//列最大值
		iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
		iArray[5][4]="taskcode";
		
		iArray[6]=new Array();
		iArray[6][0]="业务功能描述";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[6][1]="120px";            		//列宽
		iArray[6][2]=10;            			//列最大值
		iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[7]=new Array();
		iArray[7][0]="日志分类";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[7][1]="80px";            		//列宽
		iArray[7][2]=10;            			//列最大值
		iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许    
		iArray[7][4]="logtype";
		iArray[7][17]="1";
		
		iArray[8]=new Array();                                                    
		iArray[8][0]="监控开关";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[8][1]="50px";            		//列宽                                
		iArray[8][2]=10;            			//列最大值                              
		iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许   
		iArray[8][10]="Switch";
		iArray[8][11]="0|^1|开^0|关";

      
		LogSubjectGrid = new MulLineEnter( "fm" , "LogSubjectGrid" ); 
		//这些属性必须在loadMulLine前
		
		LogSubjectGrid.mulLineCount = 0;
		LogSubjectGrid.displayTitle = 1;
		LogSubjectGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogSubjectGrid.hiddenSubtraction = 1;
		LogSubjectGrid.canSel=1;
		LogSubjectGrid.selBoxEventFuncName = "ShowLog";
		LogSubjectGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}


</script>
