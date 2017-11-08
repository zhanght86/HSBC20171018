<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                               
    document.all('NO').value = '';
  }
  catch(ex)
  {
    alert("在statusInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在statusInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  { 
    initInpBox(); 
      
    initSelBox();
   
    initNoGrid();
  }
  catch(re)
  {
    alert("在statusInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initNoGrid()
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
      iArray[1][0]="MissionId";         		//列名
      iArray[1][1]="10px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[2]=new Array();
      iArray[2][0]="MainMissionId";         		//列名
      iArray[2][1]="10px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[3]=new Array();
      iArray[3][0]="ProcessId";         		//列名
      iArray[3][1]="10px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[4]=new Array();
      iArray[4][0]="业务类型";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="业务号";            //列名
      iArray[5][1]="120px";             //列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="合同号";         		//列名
      iArray[6][1]="120px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      
      NoGrid = new MulLineEnter( "fm" , "NoGrid" ); 
      //这些属性必须在loadMulLine前
      NoGrid.mulLineCount = 5;   
      NoGrid.displayTitle = 1;
      NoGrid.locked = 1;
      NoGrid.canSel = 1;
      NoGrid.canChk = 0;
      NoGrid.hiddenPlus = 1;
      NoGrid.hiddenSubtraction = 1;
      NoGrid.loadMulLine(iArray);  
      NoGrid.selBoxEventFuncName = "GetStatus";        
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
