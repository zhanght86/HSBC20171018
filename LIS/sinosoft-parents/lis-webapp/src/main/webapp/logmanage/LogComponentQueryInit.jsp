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
    document.all('SubjectID').value = '';
    document.all('ItemID').value = '';
    document.all('KeyNO').value = '';
  }
  catch(ex)
  {
    alert("在LogComponentInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
    initInpBox();
    initLogStateGrid();
    initLogTrackGrid();
}

function initLogStateGrid()
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
		iArray[1][0]="日志序列号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[1][1]="120px";            		//列宽
		iArray[1][2]=10;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="日志主题";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[2][1]="80px";            		//列宽
		iArray[2][2]=10;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[3]=new Array();
		iArray[3][0]="控制点ID";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[3][1]="80px";            		//列宽
		iArray[3][2]=10;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="关键号码类型";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[4][1]="80px";            		//列宽
		iArray[4][2]=10;            			//列最大值
		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="关键号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[5][1]="80px";            		//列宽
		iArray[5][2]=10;            			//列最大值
		iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[6]=new Array();
		iArray[6][0]="业务功能编码";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[6][1]="80px";            		//列宽
		iArray[6][2]=10;            			//列最大值
		iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[7]=new Array();
		iArray[7][0]="业务功能描述";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[7][1]="100px";            		//列宽
		iArray[7][2]=10;            			//列最大值
		iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
		
		iArray[8]=new Array();                                                    
		iArray[8][0]="状态";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[8][1]="50px";            		//列宽                                
		iArray[8][2]=10;            			//列最大值                              
		iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
		
		iArray[9]=new Array();                                                    
		iArray[9][0]="备注";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[9][1]="100px";            		//列宽                                
		iArray[9][2]=10;            			//列最大值                              
		iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
		
		iArray[10]=new Array();                                                    
		iArray[10][0]="修改日期";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[10][1]="70px";            		//列宽                                
		iArray[10][2]=10;            			//列最大值                              
		iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
		
		iArray[11]=new Array();                                                    
		iArray[11][0]="修改时间";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[11][1]="70px";            		//列宽                                
		iArray[11][2]=10;            			//列最大值                              
		iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
		LogStateGrid = new MulLineEnter( "fm" , "LogStateGrid" ); 
		//这些属性必须在loadMulLine前
		
		LogStateGrid.mulLineCount = 5;
		LogStateGrid.displayTitle = 1;
		LogStateGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogStateGrid.hiddenSubtraction = 1;
		//LogStateGrid.canSel=1;
		//LogSubjectGrid.selBoxEventFuncName = "ShowLog";
		LogStateGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

function initLogTrackGrid()
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
		iArray[1][0]="日志序列号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[1][1]="120px";            		//列宽
		iArray[1][2]=10;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="日志主题";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[2][1]="80px";            		//列宽
		iArray[2][2]=10;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[3]=new Array();
		iArray[3][0]="控制点ID";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[3][1]="80px";            		//列宽
		iArray[3][2]=10;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="其他号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[4][1]="80px";            		//列宽
		iArray[4][2]=10;            			//列最大值
		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="关键号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[5][1]="80px";            		//列宽
		iArray[5][2]=10;            			//列最大值
		iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[6]=new Array();
		iArray[6][0]="业务功能编码";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[6][1]="80px";            		//列宽
		iArray[6][2]=10;            			//列最大值
		iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[7]=new Array();
		iArray[7][0]="业务功能描述";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[7][1]="100px";            		//列宽
		iArray[7][2]=10;            			//列最大值
		iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
		
		iArray[8]=new Array();                                                    
		iArray[8][0]="描述信息";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[8][1]="50px";            		//列宽                                
		iArray[8][2]=10;            			//列最大值                              
		iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
		
		iArray[9]=new Array();                                                    
		iArray[9][0]="备注";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[9][1]="100px";            		//列宽                                
		iArray[9][2]=10;            			//列最大值                              
		iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
		
		iArray[10]=new Array();                                                    
		iArray[10][0]="生成日期";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[10][1]="70px";            		//列宽                                
		iArray[10][2]=10;            			//列最大值                              
		iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
		
		iArray[11]=new Array();                                                    
		iArray[11][0]="生成时间";         			//列名（此列为顺序号，列名无意义，而且不
		iArray[11][1]="70px";            		//列宽                                
		iArray[11][2]=10;            			//列最大值                              
		iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      
		LogTrackGrid = new MulLineEnter( "fm" , "LogTrackGrid" ); 
		//这些属性必须在loadMulLine前
		
		LogTrackGrid.mulLineCount = 5;
		LogTrackGrid.displayTitle = 1;
		LogTrackGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogTrackGrid.hiddenSubtraction = 1;
		//LogTrackGrid.canSel=1;
		//LogItemGrid.selBoxEventFuncName = "ShowItem";
		LogTrackGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
