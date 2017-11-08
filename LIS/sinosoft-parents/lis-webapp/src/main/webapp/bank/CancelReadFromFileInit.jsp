<%
//程序名称：ReadFromFileInit.jsp
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
}                                     

function initForm() {
  try {
  	initInpBox();
  	
  	initBankGrid();
  }
  catch(re) {
    alert("InitForm函数中发生异常:初始化界面错误!");
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
    iArray[1][0]="银行代码";         		//列名
    iArray[1][1]="50px";            	//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="银行名称";         		//列名
    iArray[2][1]="120px";            	//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="批次号";         		//列名
    iArray[3][1]="120px";            	//列宽
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="回盘文件名称";         		//列名
    iArray[4][1]="60px";            	//列宽
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		iArray[5]=new Array();
    iArray[5][0]="回盘日期";         		//列名
    iArray[5][1]="60px";            	//列宽
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="回盘文件笔数";         		//列名
    iArray[6][1]="70px";            	//列宽
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="回盘成功笔数";         		//列名
    iArray[7][1]="70px";            	//列宽
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="发盘文件名称";         		//列名
    iArray[8][1]="60px";            	//列宽
    iArray[8][3]=0;    
    
    iArray[9]=new Array();
    iArray[9][0]="发盘日期";         		//列名
    iArray[9][1]="60px";            	//列宽
    iArray[9][3]=0;    
    BankGrid = new MulLineEnter("fm" , "BankGrid"); 
    //这些属性必须在loadMulLine前
    BankGrid.mulLineCount = 5;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    BankGrid.loadMulLine(iArray);  
    
    //这些操作必须在loadMulLine后面
    //BankGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}
</script>

	
