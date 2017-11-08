<%
//程序名称：ReportQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('EdorNo').value = '';
    document.all('ContNo').value = '';
    
    //easyQueryClick();
    
  }
  catch(ex)
  {
    alert("在AllGBqQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    
    initInpBox();
    initEdorPrintGrid();   
  }
  catch(ex)
  {
    alert("ChangeEdorPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initEdorPrintGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保全申请号";    	//列名
      iArray[1][1]="200px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;      
      
      iArray[2]=new Array();
      iArray[2][0]="保全批单号";    	//列名
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保全合同号";         			//列名
      iArray[3][1]="200px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="批改类型";         			//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      EdorPrintGrid = new MulLineEnter( "fm" , "EdorPrintGrid" ); 
      //这些属性必须在loadMulLine前
      EdorPrintGrid.mulLineCount = 10;   
      EdorPrintGrid.displayTitle = 1;
      EdorPrintGrid.canSel = 1;
      EdorPrintGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      EdorPrintGrid.hiddenSubtraction=1;    //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      EdorPrintGrid.selBoxEventFuncName = "reportDetailClick";
      EdorPrintGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}



</script>
