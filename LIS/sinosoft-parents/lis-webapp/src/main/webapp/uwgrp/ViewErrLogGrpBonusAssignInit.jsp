<%                                                   //????
//程序名称：ViewErrLogBonusAssignInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->                                              
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

function initForm()
{
  try
  {
		initErrLogBonusGrid();
  }
  catch(re)
  {
    alert("ViewErrLogBonusAssignInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var ErrLogBonusGrid;
// 保单信息列表的初始化
function initErrLogBonusGrid()
{
    var iArray = new Array();

    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="会计年度";         	//列名
      iArray[1][1]="40px";            	//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0; 

      iArray[2]=new Array();
      iArray[2][0]="团体保单号码";      //列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="分红状态";         	//列名
      iArray[3][1]="40px";            	//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[4]=new Array();
      iArray[4][0]="错误原因";         	//列名
      iArray[4][1]="300px";            	//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  
      iArray[5]=new Array();
      iArray[5][0]="入机时间";         	//列名
      iArray[5][1]="80px";            	//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ErrLogBonusGrid = new MulLineEnter( "document" , "ErrLogBonusGrid" );
      //这些属性必须在loadMulLine前
      ErrLogBonusGrid.mulLineCount = 5;
      ErrLogBonusGrid.displayTitle = 1;
      ErrLogBonusGrid.hiddenPlus=1;
      ErrLogBonusGrid.hiddenSubtraction=1;
      ErrLogBonusGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
