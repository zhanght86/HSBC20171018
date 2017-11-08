<%
//GEdorTypeMultiDetailInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  }
  catch(ex) {
    alert("在GEdorTypeMultiRiskInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                     

function initForm() {
  try {
    initInpBox();
    
    initRiskGrid();
    
    initRisk2Grid();
  }
  catch(re) {
    alert("GEdorTypeMultiRiskInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 信息列表的初始化
function initRiskGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="险种保单号";    	//列名1
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="险种编码";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="投保总人数";         			//列名8
    iArray[3][1]="80px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="保费";         		//列名5
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="保额";         		//列名6
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许


    RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
    //这些属性必须在loadMulLine前
    RiskGrid.mulLineCount = 0;    
    RiskGrid.displayTitle = 1;
    RiskGrid.canChk = 1;
    RiskGrid.hiddenPlus = 1; 
    RiskGrid.hiddenSubtraction = 1;
    RiskGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// 信息列表的初始化
function initRisk2Grid() {
  var iArray = new Array();
      
   try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="险种保单号";    	//列名1
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="险种编码";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="投保总人数";         			//列名8
    iArray[3][1]="80px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="保费";         		//列名5
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="保额";         		//列名6
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许


    Risk2Grid = new MulLineEnter( "fm" , "Risk2Grid" ); 
    //这些属性必须在loadMulLine前
    Risk2Grid.mulLineCount = 0;    
    Risk2Grid.displayTitle = 1;
    Risk2Grid.canChk = 1;
    Risk2Grid.hiddenPlus = 1; 
    Risk2Grid.hiddenSubtraction = 1;
    Risk2Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
