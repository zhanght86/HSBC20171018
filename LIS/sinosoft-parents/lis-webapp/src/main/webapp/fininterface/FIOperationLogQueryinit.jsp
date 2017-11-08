 <% 
//程序名称：FIOperationLogQueryinit.jsp
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">                           

function initInpBox()
{
  try
  {
  	fm.reset();
	document.all('StartDay').value = '';
	document.all('EndDay').value = '';
	document.all('EventNo').value = '';
	document.all('EventType').value = '';
	document.all('EventNo1').value = '';
	document.all('filepath').value = '';
  }
  catch(ex)
  {
    alert("FIOperationLogQueryinit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("FIOperationLogQueryinit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFIOperationLogGrid();
    initFIOperationParameterGrid();
  }
  catch(re)
  {
    alert("FIOperationLogQueryinit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//
function initFIOperationLogGrid()
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
      iArray[1][0]="事件号码";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="事件类型";         		//列名
      iArray[2][1]="40px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="日志文件名称";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="日志文件路径";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=0;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="执行状态";         		//列名
      iArray[5][1]="30px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="操作日期";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="操作时间";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      FIOperationLogGrid = new MulLineEnter( "document" , "FIOperationLogGrid" ); 
      FIOperationLogGrid.mulLineCount = 5;   
      FIOperationLogGrid.displayTitle = 1;
      FIOperationLogGrid.canSel=1;
      FIOperationLogGrid.locked = 1;	
      FIOperationLogGrid.selBoxEventFuncName = "ReturnData";
      FIOperationLogGrid.hiddenPlus = 1;
      FIOperationLogGrid.hiddenSubtraction = 1;
      FIOperationLogGrid.loadMulLine(iArray);  
      FIOperationLogGrid.detailInfo="单击显示详细信息";
      

      //这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}
	
	function initFIOperationParameterGrid()
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
      iArray[1][0]="事件号码";         		//列名
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="事件类型";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="参数类型";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="参数描述";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="参数值";         		//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
    
      FIOperationParameterGrid = new MulLineEnter( "document" , "FIOperationParameterGrid" ); 
      FIOperationParameterGrid.mulLineCount = 5;   
      FIOperationParameterGrid.displayTitle = 1;
      FIOperationParameterGrid.canSel = 1;
      FIOperationParameterGrid.canChk = 0;
      FIOperationParameterGrid.locked = 1;	
      FIOperationParameterGrid.hiddenPlus = 1;
      FIOperationParameterGrid.hiddenSubtraction = 1;
      FIOperationParameterGrid.loadMulLine(iArray);  
      FIOperationParameterGrid.detailInfo="单击显示详细信息";
      

      //这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
