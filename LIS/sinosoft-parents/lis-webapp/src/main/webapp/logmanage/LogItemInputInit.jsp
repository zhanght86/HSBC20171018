<%
//程序名称：LogComponentInit.jsp
//程序功能：
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
     String tSubjectID = request.getParameter("SubjectID");
%>                            

<script language="JavaScript">

function initInpBox()
{ 
  try
  {          
    /*document.all('ItemID').value = '';
    document.all('ItemDes').value = '';
    document.all('KeyType').value = '';
    document.all('Grammar').value = '';
    document.all('ClassName').value = '';
    document.all('Func').value = '';                         
   // document.all('ItemSwitch').value = 'Y';
   // document.all('ItemSwitchName').value = '开';
    document.all('Remark').value = '';*/
    document.all('SubIDForItem').value = '<%= tSubjectID %>';
  }
  catch(ex)
  {
    alert("在LogComponentInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
    initInpBox();
    initLogItemGrid();
    ItemQuery();
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
		LogItemGrid.canSel=0;
		//LogItemGrid.selBoxEventFuncName = "ShowItem";
		LogItemGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
