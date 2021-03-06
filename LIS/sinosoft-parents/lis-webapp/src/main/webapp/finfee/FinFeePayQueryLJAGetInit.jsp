<%
//程序名称：TempFeeQueryInit.jsp
//程序功能：
//创建日期：
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
	// 查询条件
    document.all('ActuGetNo').value = '';
    document.all('PayMode').value = '';
    document.all('OtherNo').value = '';
  }
  catch(ex)
  {
    alert("在FinFeeQueryLJAGetInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();  
    initFinFeeQueryGrid();
  }
  catch(re)
  {
    alert("在FinFeeQueryLJAGetInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

 var QueryLJAGetGrid ;
 
// 保单信息列表的初始化
function initFinFeeQueryGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            		//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="实付号码";   		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            		//列最大值
      iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="其它号码";		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=60;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="号码类型";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=200;            	        //列最大值
      iArray[3][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="付费方式";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=200;            	        //列最大值
      iArray[4][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="币种";      	   		//列名
      iArray[5][1]="80px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="currency";

      iArray[6]=new Array();
      iArray[6][0]="给付金额";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=200;            	        //列最大值
      iArray[6][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="到帐日期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            	        //列最大值
      iArray[7][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="领取人";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=200;            	        //列最大值
      iArray[8][3]=0;                   	//是否允许输入,1表示允许，0表示不允许  

      iArray[9]=new Array();
      iArray[9][0]="领取人身份证";         		//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=200;            	        //列最大值
      iArray[9][3]=0;                   	//是否允许输入,1表示允许，0表示不允许      
      
      iArray[10]=new Array();
      iArray[10][0]="银行编码";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            	        //列最大值
      iArray[10][3]=3;                   	//是否允许输入,1表示允许，0表示不允许  
      
      iArray[11]=new Array();
      iArray[11][0]="银行账户";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=200;            	        //列最大值
      iArray[11][3]=3;                   	//是否允许输入,1表示允许，0表示不允许  
      
      iArray[12]=new Array();
      iArray[12][0]="银行户名";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=200;            	        //列最大值
      iArray[12][3]=3;                   	//是否允许输入,1表示允许，0表示不允许    
       
       
      QueryLJAGetGrid = new MulLineEnter( "fm" , "QueryLJAGetGrid" ); 
      //这些属性必须在loadMulLine前
      QueryLJAGetGrid.mulLineCount = 2;   
      QueryLJAGetGrid.displayTitle = 1;
      QueryLJAGetGrid.hiddenPlus = 1;
      QueryLJAGetGrid.hiddenSubtraction = 1;
      QueryLJAGetGrid.canSel = 1;
      QueryLJAGetGrid.loadMulLine(iArray);
      //QueryLJAGetGrid.selBoxEventFuncName = "GetRecord";  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
