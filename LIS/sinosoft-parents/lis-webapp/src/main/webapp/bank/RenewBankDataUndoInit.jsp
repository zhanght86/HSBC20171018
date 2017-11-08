 <%
//程序名称：RenewBankConfirmInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：hzm创建
//更新记录：  更新人    更新日期     更新原因/内容
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
    alert("在RenewBankConfirmInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
		initCodeGrid();
  }
  catch(re)
  {
    alert("RenewBankConfirmInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var CodeGrid;          //定义为全局变量，提供给displayMultiline使用
function initCodeGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="印刷号码";         		//列名
	  iArray[1][1]="80px";            	//列宽
	  iArray[1][2]=20;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
	  iArray[2][0]="合同号码";       		  //列名
	  iArray[2][1]="120px";            	//列宽
	  iArray[2][2]=20;            			//列最大值
	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[3]=new Array();
	  iArray[3][0]="投保人";       		  //列名
	  iArray[3][1]="80px";            	//列宽
	  iArray[3][2]=20;            			//列最大值
	  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
	  iArray[4][0]="险种代码";       		//列名
	  iArray[4][1]="60px";            	//列宽
	  iArray[4][2]=10;            			//列最大值
	  iArray[4][3]=2; 
	  iArray[4][4]="RiskCode"; 
    iArray[4][9]="险种代码|code:riskcode&NOTNULL";
    
    iArray[5]=new Array();
	  iArray[5][0]="缴至日期";	       	//列名
	  iArray[5][1]="80px";            	//列宽
	  iArray[5][2]=10;            			//列最大值
	  iArray[5][3]=0; 
      
    iArray[6]=new Array();
	  iArray[6][0]="缴费通知书号";	   	//列名
	  iArray[6][1]="0px"; 	           	//列宽
	  iArray[6][2]=10;            			//列最大值
	  iArray[6][3]=0; 
      
    iArray[7]=new Array();
	  iArray[7][0]="流水号";	       	//列名
	  iArray[7][1]="0px";            		//列宽
	  iArray[7][2]=10;            			//列最大值
	  iArray[7][3]=0; 
      
	  CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
	  //这些属性必须在loadMulLine前
	  CodeGrid.mulLineCount = 5;   
	  CodeGrid.displayTitle = 1;
	  CodeGrid.hiddenSubtraction=1;
	  CodeGrid.hiddenPlus=1;
	  CodeGrid.canChk = 1;
    CodeGrid.locked = 1;
	  CodeGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}

</script>
