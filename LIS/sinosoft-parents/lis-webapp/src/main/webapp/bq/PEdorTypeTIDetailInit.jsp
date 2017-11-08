<%
//GEdorTypeDetailInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
  document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
  document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;	
  document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  document.all('CustomerNo').value = "";	
  }
  catch(ex) {
    //alert("在GEdorTypeICInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                     

function initForm() {
  try {
    initInpBox();
    initLCGrpInsuAccGrid();
    initLPInsuAccGrid();
    initTempOutInsuAccGrid();
    QueryEdorInfo();
    queryClick();
    showInsuAccIn("");
  }
  catch(re) {
    //alert("GEdorTypeICInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 信息列表的初始化
function initLCGrpInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="客户号码";    				//列名1
    iArray[1][1]="50px";            		//列宽
    iArray[1][2]=50;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="保单险种号码";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="投资账户";         			//列名8
    iArray[3][1]="50px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="投资账户名称";         			//列名5
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

  //  iArray[5]=new Array();
  //  iArray[5][0]="交费账户";         		//列名6
  //  iArray[5][1]="0px";            		//列宽
  //  iArray[5][2]=100;            			//列最大值
  //  iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允?
  //  
  //  iArray[6]=new Array();
  //  iArray[6][0]="交费账户名称";         		//列名6
  //  iArray[6][1]="0px";            		//列宽
  //  iArray[6][2]=100;            			//列最大值
  //  iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许?
    
    iArray[5]=new Array();
    iArray[5][0]="投资单位结余";         		//列名6
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=7;              			//是否允许输入,1表示允许，0表示不允许
    iArray[5][23]="0";
    
    iArray[6]=new Array();
    iArray[6][0]="可转出单位";         		//列名6
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="已冻结单位数";         		//列名6
    iArray[7][1]="70px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="单位价格";         		//列名6
    iArray[8][1]="60px";            		//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=7;              			//是否允许输入,1表示允许，0表示不允许
    iArray[8][23]="0";
    
    iArray[9]=new Array();
    iArray[9][0]="转出单位";         		//列名6
    iArray[9][1]="50px";            		//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="转出金额";         		//列名6
    iArray[10][1]="0px";            		//列宽
    iArray[10][2]=100;            			//列最大值
    iArray[10][3]=7;              			//是否允许输入,1表示允许，0表示不允许
    iArray[10][23]="0";
    
    iArray[11]=new Array();
		iArray[11][0]="币种";
		iArray[11][1]="60px";
		iArray[11][2]=100;
		iArray[11][3]=0;
		//iArray[11][4]="currency";
    
    LCGrpInsuAccGrid = new MulLineEnter( "fm" , "LCGrpInsuAccGrid" ); 
    //这些属性必须在loadMulLine前
    LCGrpInsuAccGrid.mulLineCount = 0;    
    LCGrpInsuAccGrid.displayTitle = 1;
    LCGrpInsuAccGrid.canSel = 1;
    LCGrpInsuAccGrid.hiddenPlus = 1; 
    LCGrpInsuAccGrid.hiddenSubtraction = 1;
    //LCGrpInsuAccGrid.selBoxEventFuncName = "showInsuAccIn";
    LCGrpInsuAccGrid.detailInfo = "单击显示详细信息";
    LCGrpInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}


// 信息列表的初始化
function initLCInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="客户号码";    				//列名1
    iArray[1][1]="50px";            		//列宽
    iArray[1][2]=50;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="保单险种号码";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="投资账户";         			//列名8
    iArray[3][1]="50px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="账户名称";         			//列名5
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

  //  iArray[5]=new Array();
  //  iArray[5][0]="交费账户";         		//列名6
  //  iArray[5][1]="0px";            		//列宽
  //  iArray[5][2]=100;            			//列最大值
  //  iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
  //
  //  iArray[6]=new Array();
  //  iArray[6][0]="交费账户名称";         		//列名6
  //  iArray[6][1]="0px";            		//列宽
  //  iArray[6][2]=100;            			//列最大值
  //  iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="账户单位";         		//列名6
    iArray[5][1]="50px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="单位价格";         		//列名6
    iArray[6][1]="50px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=7;              			//是否允许输入,1表示允许，0表示不允许
    iArray[6][23]="0";
    
    iArray[7]=new Array();
    iArray[7][0]="转入比例";         		//列名6
    iArray[7][1]="50px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="转入金额";         		//列名6
    iArray[8][1]="0px";            		//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=7;              			//是否允许输入,1表示允许，0表示不允许
		iArray[8][23]="0";
		
		iArray[9]=new Array();
		iArray[9][0]="币种";
		iArray[9][1]="60px";
		iArray[9][2]=100;
		iArray[9][3]=0;
		//iArray[9][4]="currency";
		
    LCInsuAccGrid = new MulLineEnter( "fm" , "LCInsuAccGrid" ); 
    //这些属性必须在loadMulLine前
    LCInsuAccGrid.mulLineCount = 0;    
    LCInsuAccGrid.displayTitle = 1;
    LCInsuAccGrid.canChk = 1;
    LCInsuAccGrid.hiddenPlus = 1; 
    LCInsuAccGrid.hiddenSubtraction = 1;
    //LCInsuAccGrid.selBoxEventFuncName = "reportDetailClick";
    LCInsuAccGrid.detailInfo = "单击显示详细信息";
    LCInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// 信息列表的初始化
function initLPInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="转入客户号码";    				//列名1
    iArray[1][1]="50px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="转入保单险种号码";    				//列名1
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="转入账户";         			//列名2
    iArray[3][1]="50px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="转入交费账户";         			//列名8
    iArray[4][1]="0px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    
    iArray[5]=new Array();
    iArray[5][0]="转入比例";    				//列名1
    iArray[5][1]="50px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    //iArray[6]=new Array();
    //iArray[6][0]="转出金额";    				//列名1
    //iArray[6][1]="0px";            		//列宽
    //iArray[6][2]=100;            			//列最大值
    //iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //
    //iArray[7]=new Array();
    //iArray[7][0]="转入客户号码";    				//列名1
    //iArray[7][1]="0px";            		//列宽
    //iArray[7][2]=100;            			//列最大值
    //iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //
    //iArray[8]=new Array();
    //iArray[8][0]="转入保单险种号码";         			//列名5
    //iArray[8][1]="0px";            		//列宽
    //iArray[8][2]=100;            			//列最大值
    //iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
    //
    //iArray[9]=new Array();
    //iArray[9][0]="转入账户";         		//列名6
    //iArray[9][1]="50px";            		//列宽
    //iArray[9][2]=100;            			//列最大值
    //iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //
    //iArray[10]=new Array();
    //iArray[10][0]="转入交费账户";         		//列名6
    //iArray[10][1]="50px";            		//列宽
    //iArray[10][2]=100;            			//列最大值
    //iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //
    //iArray[11]=new Array();
    //iArray[11][0]="转入比例";         		//列名6
    //iArray[11][1]="50px";            		//列宽
    //iArray[11][2]=100;            			//列最大值
    //iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //
    //iArray[12]=new Array();
    //iArray[12][0]="转入金额";         		//列名6
    //iArray[12][1]="0px";            		//列宽
    //iArray[12][2]=100;            			//列最大值
    //iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //    
    //iArray[13]=new Array();
    //iArray[13][0]="交易流水号";         		//列名6
    //iArray[13][1]="80px";            		//列宽
    //iArray[13][2]=100;            			//列最大值
    //iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    LPInsuAccGrid = new MulLineEnter( "fm" , "LPInsuAccGrid" ); 
    //这些属性必须在loadMulLine前
    LPInsuAccGrid.mulLineCount = 0;   
    LPInsuAccGrid.displayTitle = 1;
    LPInsuAccGrid.canSel = 0;
    LPInsuAccGrid.hiddenPlus = 1; 
    LPInsuAccGrid.hiddenSubtraction = 1;
    //LPInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    LPInsuAccGrid.detailInfo = "单击显示详细信息";
    LPInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}




//2007-09-07----鲁哲
// 信息列表的初始化
function initTempOutInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="转出客户号码";    				//列名1
    iArray[1][1]="50px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="转出保单险种号码";    				//列名1
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="转出账户";         			//列名2
    iArray[3][1]="100px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="转出交费账户";         			//列名8
    iArray[4][1]="0px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    
    iArray[5]=new Array();
    iArray[5][0]="转出单位";    				//列名1
    iArray[5][1]="50px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="操作日期";    				//列名1
    iArray[6][1]="70px";            		//列宽
    iArray[6][2]=70;            			//列最大值
    iArray[6][3]=8;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="操作时间";    				//列名1
    iArray[7][1]="70px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="币种";    				//列名1
    iArray[8][1]="0px";            		//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//
//    iArray[8]=new Array();
//    iArray[8][0]="转入保单险种号码";         			//列名5
//    iArray[8][1]="0px";            		//列宽
//    iArray[8][2]=100;            			//列最大值
//    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
//
//    iArray[9]=new Array();
//    iArray[9][0]="转入账户";         		//列名6
//    iArray[9][1]="50px";            		//列宽
//    iArray[9][2]=100;            			//列最大值
//    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//
//    iArray[10]=new Array();
//    iArray[10][0]="转入交费账户";         		//列名6
//    iArray[10][1]="50px";            		//列宽
//    iArray[10][2]=100;            			//列最大值
//    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//
//    iArray[11]=new Array();
//    iArray[11][0]="转入比例";         		//列名6
//    iArray[11][1]="50px";            		//列宽
//    iArray[11][2]=100;            			//列最大值
//    iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//    
//    iArray[12]=new Array();
//    iArray[12][0]="转入金额";         		//列名6
//    iArray[12][1]="0px";            		//列宽
//    iArray[12][2]=100;            			//列最大值
//    iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//        
//    iArray[13]=new Array();
//    iArray[13][0]="交易流水号";         		//列名6
//    iArray[13][1]="80px";            		//列宽
//    iArray[13][2]=100;            			//列最大值
//    iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    TempOutInsuAccGrid = new MulLineEnter( "fm" , "TempOutInsuAccGrid" ); 
    //这些属性必须在loadMulLine前
    TempOutInsuAccGrid.mulLineCount = 0;   
    TempOutInsuAccGrid.displayTitle = 1;
    TempOutInsuAccGrid.canSel = 1;
    TempOutInsuAccGrid.hiddenPlus = 1; 
    TempOutInsuAccGrid.hiddenSubtraction = 1;
    TempOutInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    TempOutInsuAccGrid.detailInfo = "单击显示详细信息";
    TempOutInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>