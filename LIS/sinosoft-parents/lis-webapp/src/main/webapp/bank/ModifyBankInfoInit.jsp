<%
//程序名称：ModifyBankInfoInit.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() { 
	//document.all('PrtNo2').value = '';
	document.all('TempfeeNo').value = '';
	document.all('TempfeeNo1').value = '';
	document.all('BankCode').value = '';
	document.all('AccName').value = '';
	document.all('AccNo').value = '';
	//document.all('PayMode').value = '';
	//document.all('GetNoticeNo2').value = '';
	//document.all('PayModeName').value = '';
}                                     

function initForm() {
  try {
  	initInpBox();
  	initBankGrid();
  }
  catch(re) {
    alert("InitForm 函数中发生异常:初始化界面错误!");
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
    iArray[1][0]="暂交费号";         		//列名
    iArray[1][1]="80px";            	//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="交费通知书号";         		//列名
    iArray[2][1]="100px";            	//列宽
    iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="交费方式";         		//列名
    iArray[3][1]="40px";            	//列宽
    iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[3][10]="PayMode";
    iArray[3][11]="0|^1|现金|^2|现金支票|^3|转账支票^4|银行转账^5|内部转账";
    
    iArray[4]=new Array();
    iArray[4][0]="交费金额";         		//列名
    iArray[4][1]="50px";            	//列宽
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="银行代码";         		//列名
    iArray[5][1]="50px";            	//列宽
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="银行账号";         		//列名
    iArray[6][1]="100px";            	//列宽
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="账户名称";         		//列名
    iArray[7][1]="50px";            	//列宽
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    //这些属性必须在loadMulLine前
    BankGrid.mulLineCount = 1;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    //BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    //BankGrid.selBoxEventFuncName = "showStatistics";
    BankGrid.loadMulLine(iArray);  
    
    //这些操作必须在loadMulLine后面
    //BankGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
