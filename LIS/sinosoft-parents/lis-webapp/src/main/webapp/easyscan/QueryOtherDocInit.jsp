<%
//程序名称：EsDocManageInit.jsp
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
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
  try
  {
  }
  catch(ex)
  {
    alert("在QueryOtherDocInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
//    initSubType();
    initCodeGrid();
  }
  catch(re)
  {
    alert("QueryOtherDocInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化CodeGrid
 ************************************************************
 */
var CodeGrid;          //定义为全局变量，提供给displayMultiline使用
function initCodeGrid()
{                               
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         //列宽
      iArray[0][2]=100;            //列最大值
      iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许
      
	  iArray[1]=new Array();
      iArray[1][0]="印刷号";     //列名
      iArray[1][1]="50px";         //列宽
      iArray[1][2]=50;             //列最大值
      iArray[1][3]=0;              //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="管理机构";     //列名
      iArray[2][1]="80px";         //宽度
      iArray[2][2]=80;        		 //最大长度
      iArray[2][3]=0;         		 //是否允许录入，0--不能，1--允许

	  iArray[3]=new Array();
      iArray[3][0]="扫描日期";     //列名
      iArray[3][1]="50px";         //宽度
      iArray[3][2]=50;      		   //最大长度
      iArray[3][3]=0;         		 //是否允许录入，0--不能，1--允许
      
      iArray[4]=new Array();
      iArray[4][0]="申请人";         //列名
      iArray[4][1]="30px";         //宽度
      iArray[4][2]=50;        		 //最大长度
      iArray[4][3]=0;         		 //是否允许录入，0--不能，1--允许

      iArray[5]=new Array();
      iArray[5][0]="申请日期";       //列名
      iArray[5][1]="60px";          //宽度
      iArray[5][2]=50;           	 //最大长度
      iArray[5][3]=0;        			 //是否允许录入，0--不能，1--允许
  
      iArray[6]=new Array();
      iArray[6][0]="申请时间";     //列名
      iArray[6][1]="60px";         //宽度
      iArray[6][2]=50;           	 //最大长度
      iArray[6][3]=0;        			 //是否允许录入，0--不能，1--允许
  
      iArray[7]=new Array();
      iArray[7][0]="申请原因";     //列名
      iArray[7][1]="50px";         //宽度
      iArray[7][2]=50;  		       //最大长度
      iArray[7][3]=0;        			 //是否允许录入，0--不能，1--允许

      iArray[8]=new Array();
      iArray[8][0]="申请原因代码";     //列名
      iArray[8][1]="0px";         //宽度
      iArray[8][2]=50;  		       //最大长度
      iArray[8][3]=0;        			 //是否允许录入，0--不能，1--允许

      iArray[9]=new Array();
      iArray[9][0]="签批标志";     //列名
      iArray[9][1]="30px";         //宽度
      iArray[9][2]=50;  		       //最大长度
      iArray[9][3]=0;        			 //是否允许录入，0--不能，1--允许

      CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
      //这些属性必须在loadMulLine前
      CodeGrid.mulLineCount = 5;   
      CodeGrid.displayTitle = 1;
      CodeGrid.hiddenSubtraction=1;
      CodeGrid.hiddenPlus=1;
      CodeGrid.canChk=1;
      CodeGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert("初始化CodeGrid时出错："+ ex);
    }
}
    
    
</script>
