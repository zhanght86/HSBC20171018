
<%
	//程序名称：QueryForPayFeeInit.jsp
	//程序功能：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String CurrentDate = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//添加页面控件的初始化。
%>

<script language="JavaScript">

/*function initInpBox()
{ 
  try
  {   		
  }
  catch(ex)
  {
    alert("在QueryForPayFeeInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}*/


function initForm()
{
  try
  {
    //initInpBox();
    initErrLogGrid();  
  }
  catch(re)
  {
    alert("QueryForPayFeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 暂收费信息列表的初始化
//如果是新单交费的话则如下初始化
function initErrLogGrid()
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
      iArray[1][0]="校验批次号码";      	   		//列名
      iArray[1][1]="80px";            			//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="校验规则";      	   		//列名
      iArray[2][1]="80px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
     
      iArray[3]=new Array();
      iArray[3][0]="校验结果";      	   		//列名
      iArray[3][1]="110px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="校验人";      	   		//列名
      iArray[4][1]="70px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="校验日期";      	   		//列名
      iArray[5][1]="70px";            			//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[6]=new Array();
      iArray[6][0]="校验规则编码";      	   		//列名
      iArray[6][1]="0px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
             
      
      ErrLogGrid = new MulLineEnter( "document" , "ErrLogGrid" ); 
      //这些属性必须在loadMulLine前
      ErrLogGrid.mulLineCount = 5;   
      ErrLogGrid.displayTitle = 1;
      ErrLogGrid.locked=1;   
      //TempReceiptGrid.canChk=1;  
       ErrLogGrid.canSel=1;
      //PEdorMainGrid.selBoxEventFuncName ="reportClick"
      ErrLogGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
