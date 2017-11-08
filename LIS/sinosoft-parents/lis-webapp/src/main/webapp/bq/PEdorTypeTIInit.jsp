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
  document.all('ContNo').value = top.opener.document.all('ContNo').value;
  document.all('EdorType').value = top.opener.document.all('EdorType').value;
  document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;	
  document.all('ContNo').value = top.opener.document.all('ContNoApp').value;
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
    initLCInsuAccGrid();
    QueryEdorInfo();
    //initLPInsuAccGrid();
    QueryBussiness();
  }
  catch(re) {
    //alert("GEdorTypeICInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
    iArray[1][0]="保单险种号码";    				//列名1
    iArray[1][1]="130px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0; 
                 			         //是否允许输入,1表示允许，0表示不允许
    iArray[2]=new Array();
    iArray[2][0]="险种号码";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;   
    
    
    iArray[3]=new Array();
    iArray[3][0]="险种名称";         			//列名2
    iArray[3][1]="220px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;   

 
 
    iArray[4]=new Array();
    iArray[4][0]="客户号";         			//列名2
    iArray[4][1]="80px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="姓名";         			//列名8
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="性别";         			//列名5
    iArray[6][1]="50px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[6][4]="Sex";              	        //是否引用代码:null||""为不引用
    iArray[6][5]="3";              	                //引用代码对应第几列，'|'为分割符
    iArray[6][9]="性别|code:Sex&NOTNULL";
    iArray[6][18]=250;
    iArray[6][19]= 0 ;

    iArray[7]=new Array();
    iArray[7][0]="出生日期";         		//列名6
    iArray[7][1]="80px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=8;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="证件类型";         		//列名6
    iArray[8][1]="80px";            		//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[8][4]="IDType";              	        //是否引用代码:null||""为不引用
    iArray[8][5]="3";              	                //引用代码对应第几列，'|'为分割符
    iArray[8][9]="证件类型|code:IDType&NOTNULL";
    iArray[8][18]=250;
    iArray[8][19]= 0 ;
    

    iArray[9]=new Array();
    iArray[9][0]="证件号码";         		//列名6
    iArray[9][1]="150px";            		//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
  
  
    LCInsuAccGrid = new MulLineEnter( "fm" , "LCInsuAccGrid" ); 
    //这些属性必须在loadMulLine前
    LCInsuAccGrid.mulLineCount = 0;   
    LCInsuAccGrid.displayTitle = 1;
    LCInsuAccGrid.canSel = 1;
    LCInsuAccGrid.hiddenPlus = 1; 
    LCInsuAccGrid.hiddenSubtraction = 1;
    //LCInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
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
    iArray[3][1]="50px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="转出交费账户";         			//列名8
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    
    iArray[5]=new Array();
    iArray[5][0]="转出单位";    				//列名1
    iArray[5][1]="50px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="转出金额";    				//列名1
    iArray[6][1]="50px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=7;              			//是否允许输入,1表示允许，0表示不允许
    iArray[6][23]="0";
    
    iArray[7]=new Array();
    iArray[7][0]="转入客户号码";    				//列名1
    iArray[7][1]="50px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="转入保单险种号码";         			//列名5
    iArray[8][1]="80px";            		//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[9]=new Array();
    iArray[9][0]="转入账户";         		//列名6
    iArray[9][1]="50px";            		//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="转入交费账户";         		//列名6
    iArray[10][1]="50px";            		//列宽
    iArray[10][2]=100;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[11]=new Array();
    iArray[11][0]="转入比例";         		//列名6
    iArray[11][1]="50px";            		//列宽
    iArray[11][2]=100;            			//列最大值
    iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[12]=new Array();
    iArray[12][0]="转入金额";         		//列名6
    iArray[12][1]="50px";            		//列宽
    iArray[12][2]=100;            			//列最大值
    iArray[12][3]=7;              			//是否允许输入,1表示允许，0表示不允许
    iArray[12][23]="0";
        
    iArray[13]=new Array();
    iArray[13][0]="交易流水号";         		//列名6
    iArray[13][1]="80px";            		//列宽
    iArray[13][2]=100;            			//列最大值
    iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[14]=new Array();
		iArray[14][0]="币种";
		iArray[14][1]="60px";
		iArray[14][2]=100;
		iArray[14][3]=2;
		iArray[14][4]="currency";
    
    LPInsuAccGrid = new MulLineEnter( "fm" , "LPInsuAccGrid" ); 
    //这些属性必须在loadMulLine前
    LPInsuAccGrid.mulLineCount = 0;   
    LPInsuAccGrid.displayTitle = 1;
    LPInsuAccGrid.canSel = 1;
    LPInsuAccGrid.hiddenPlus = 1; 
    LPInsuAccGrid.hiddenSubtraction = 1;
    LPInsuAccGrid.detailInfo = "单击显示详细信息";
    LPInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>