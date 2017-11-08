
<%
	//程序名称：ConFeeInit.jsp
	//程序功能：
	//创建日期：2002-08-16 15:39:06
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	if (globalInput == null) {
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('PolNo').value = '';
    document.all('LeavingMoney').value = '';
  }
  catch(ex)
  {
    alert("在ConFeeInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                   

function initForm()
{
  try
  {
    initInpBox();
    initBankGrid();  
  }
  catch(re)
  {
    alert("ConFeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 领取项信息列表的初始化
var BankGrid;
function initBankGrid() {                               
  var iArray = new Array();    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="险种号码";         		//列名
    iArray[1][1]="100px";            	//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
    iArray[2]=new Array();
    iArray[2][0]="险种编码";         		//列名
    iArray[2][1]="40px";            	//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="可退余额";         		//列名
    iArray[3][1]="60px";            	//列宽
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="保费";         		//列名
    iArray[4][1]="60px";            	//列宽
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="基本保额";         		//列名
    iArray[5][1]="60px";            	//列宽
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="签单日期";         		//列名
    iArray[6][1]="50px";            	//列宽
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="交费位置";         		//列名
    iArray[7][1]="40px";            	//列宽
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    BankGrid.mulLineCount = 5;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    BankGrid.selBoxEventFuncName = "showOne";    
    BankGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
