<%
//程序名称：TempFeeWithdrawInit.jsp
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
  	initFeeGrid();
  }
  catch(re) {
    alert("TempFeeWithdrawInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 领取项信息列表的初始化
var FeeGrid;
function initFeeGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="退费原因";         		//列名
    iArray[1][1]="120px";            		//列宽
    iArray[1][3]= 2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="bz_cancel";            

    iArray[2]=new Array();
    iArray[2][0]="暂交费收据号码";      //列名
    iArray[2][1]="120px";            		//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="类型";                //列名
    iArray[3][1]="60px";            		//列宽
    iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="tempfeetype";     

    //iArray[4]=new Array();
    //iArray[4][0]="险种编码";         		//列名
    //iArray[4][1]="40px";            		//列宽
    //iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="投保人名称";         		//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="投保单印刷号";         		//列名
    iArray[5][1]="100px";            		//列宽
    iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="交费金额";         			//列名
    iArray[6][1]="100px";            		//列宽
    iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="交费日期";         		//列名
    iArray[7][1]="100px";            		//列宽
    iArray[7][3]=8;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="到帐日期";         		//列名
    iArray[8][1]="100px";            		//列宽
    iArray[8][3]=8;              			//是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="退费金额";         		//列名
    iArray[9][1]="100px";            		//列宽
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="其它号码类型";         		//列名
    iArray[10][1]="0px";            		//列宽
    iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    FeeGrid = new MulLineEnter( "fm" , "FeeGrid" ); 
    //这些属性必须在loadMulLine前
    FeeGrid.mulLineCount = 5;   
    FeeGrid.displayTitle = 1;
    FeeGrid.hiddenPlus = 1;
    FeeGrid.hiddenSubtraction = 1;
    FeeGrid.canChk = 1;
    FeeGrid.loadMulLine(iArray);  
    
    //这些操作必须在loadMulLine后面
    //FeeGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
