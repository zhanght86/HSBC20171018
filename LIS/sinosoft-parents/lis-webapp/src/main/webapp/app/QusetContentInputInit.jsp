<%
//程序名称：SelAssignDuty.jsp
//程序功能：
//创建日期：2008-09-26
//创建人  ：liuqh
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
	document.all('QuestCode').value = "";
	document.all('BackObj').value = "";
	
	document.all('BackObjName').value = "";
	document.all('RecordQuest').value = "";
	document.all('Content').value = "";

}

function initSelBox()
{  
}                                        

function initForm()
{
  try
  {
	initQuestGrid();
	initInpBox();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("QuestContentInput.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initQuestGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="问题件编码";          	//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="问题件内容";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="发送对象";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="是否记入问题件比例";      	   		//列名
      iArray[4][1]="30px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 

      iArray[5]=new Array();
      iArray[5][0]="定义人";      	   		//列名
      iArray[5][1]="30px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0; 
      
      iArray[6]=new Array();
      iArray[6][0]="修改日期";      	   		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0; 
      
      QuestGrid = new MulLineEnter( "document" , "QuestGrid" ); 
      //这些属性必须在loadMulLine前
      QuestGrid.mulLineCount = 5;   
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel =1;
      //QuestGrid.canChk = 1;
      QuestGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      QuestGrid.hiddenSubtraction=1;
	  QuestGrid.selBoxEventFuncName = "displayQuest";

	  QuestGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}






</script>
