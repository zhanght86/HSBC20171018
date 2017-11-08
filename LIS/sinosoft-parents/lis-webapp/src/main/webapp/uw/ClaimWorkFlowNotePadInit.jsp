<%
//程序名称：WorkFlowNotePadInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("在WorkFlowNotePadInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在WorkFlowNotePadInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {

    initInpBox();
    initSelBox();    
	initParam();
	
	if(fm.UnSaveFlag.value == "1")
	{
	   divButton.style.display = 'none';
	   divReturn.style.display = '';
	}
	else
	{
	   divButton.style.display = '';
	   divReturn.style.display = 'none';
	}
	initQuestGrid();
	initQuery();
  }
  catch(re)
  {
    alert("WorkFlowNotePadInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initQuestGrid() {                              
  var iArray = new Array();
    
  try  {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			 //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			    //列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="印刷号";    	     //列名
    iArray[1][1]="90px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="记事本内容";         			//列名
    iArray[2][1]="280px";            		//列宽
    iArray[2][2]=400;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="录入位置";         			//列名
    iArray[3][1]="0px";            		//列宽
    iArray[3][2]=40;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                             
    iArray[4]=new Array();
    iArray[4][0]="录入员";         			//列名
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=40;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="录入日期";         			//列名
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=50;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="录入时间";         			//列名
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=50;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
    //这些属性必须在loadMulLine前                         
    QuestGrid.mulLineCount = 0;
    QuestGrid.displayTitle = 1;
    QuestGrid.canSel = 1;
    QuestGrid.hiddenPlus = 1;
    QuestGrid.hiddenSubtraction = 1;
    QuestGrid.loadMulLine(iArray); 
    QuestGrid.selBoxEventFuncName = "showContent";
 
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
