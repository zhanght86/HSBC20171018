<%
//程序名称：ClientConjoinQueryInit.jsp
//程序功能：
//创建日期：2002-08-19
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	
  	document.all('QureyPrtNo').value = '';
    document.all('QureyContNo').value = '';      
    document.all('PolNo').value=''; 
    document.all('RiskCode').value='';                          
  }
  catch(ex)
  {
    alert("NBErrorModifyInput.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();
    initOldPlan();
    initNewPlan();
  }
  catch(re)
  {
    alert("NBErrorModifyInput.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


//被保人险种信息列表初始化
function initPolGrid()
{
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		        //列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保险单险种号码";         		//列名
      iArray[1][1]="0px";            		        //列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="40px";            		        //列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      //iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[3]=new Array();
      iArray[3][0]="险种名称";         		//列名
      iArray[3][1]="80px";            		        //列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="保险期间";         		//列名
      iArray[4][1]="40px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="交费年期";         		//列名
      iArray[5][1]="40px";            		        //列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="保险金额";         		//列名
      iArray[6][1]="40px";            		        //列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=7;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="投保份数";         		//列名
      iArray[7][1]="40px";            		        //列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="合计保费(元)";         		//列名
      iArray[8][1]="60px";            		        //列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=7;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="职业加费(元)";         		//列名
      iArray[9][1]="0px";            		        //列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=7;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="币种";         		//列名
      iArray[10][1]="40px";            		        //列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[10][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[10][9]="币种|code:Currency";
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}


//投连险种的投资计划
function initOldPlan()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1] = new Array();
      iArray[1][0] = "投资帐户号";    	        //列名
      iArray[1][1] = "80px";            		//列宽
      iArray[1][2] = 100;            			//列最大值
      iArray[1][3] = 0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "投资帐户名称";         		//列名
      iArray[2][1] = "100px";            		//列宽
      iArray[2][2] = 60;            			//列最大值
      iArray[2][3] = 0;                   			//是否允许输入,1表示允许，0表示不允许
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "投资比例上限";         		//列名
      iArray[3][1] = "0px";            		//列宽
      iArray[3][2] = 60;            			//列最大值
      iArray[3][3] = 3;              			//是否允许输入,1表示允许，0表示不允许
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "投资比例下限";         		//列名
      iArray[4][1] = "0px";            		//列宽
      iArray[4][2] = 50;            			//列最大值
      iArray[4][3] = 3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5] = new Array();
      iArray[5][0] = "投资比例";         		//列名
      iArray[5][1] = "80px";            		//列宽
      iArray[5][2] = 50;            			//列最大值
      iArray[5][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[6] = new Array();
      iArray[6][0] = "缴费编号";         		//列名
      iArray[6][1] = "0px";            		//列宽
      iArray[6][2] = 100;            			//列最大值
      iArray[6][3] = 3;              			//是否允许输入,1表示允许，0表示不允许

        
      iArray[7] = new Array();
      iArray[7][0] = "币种";         		//列名
      iArray[7][1] = "80px";            		//列宽
      iArray[7][2] = 100;            			//列最大值
      iArray[7][3] = 0;


     
      /*iArray[8] = new Array();
      iArray[8][0] = "";         		//列名
      iArray[8][1] = "80px";            		//列宽
      iArray[8][2] = 100;            			//列最大值
      iArray[8][3] = 1;*/
 
      OldPlanGrid = new MulLineEnter( "fm" , "OldPlanGrid" );
      //这些属性必须在loadMulLine前
      OldPlanGrid.mulLineCount = 5;
     OldPlanGrid.displayTitle = 1;
     OldPlanGrid.hiddenPlus = 1;
      OldPlanGrid.hiddenSubtraction = 1;
      OldPlanGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}


//投连险种的投资计划
function initNewPlan()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1] = new Array();
      iArray[1][0] = "投资帐户号";    	        //列名
      iArray[1][1] = "80px";            		//列宽
      iArray[1][2] = 100;            			//列最大值
      iArray[1][3] = 0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "投资帐户名称";         		//列名
      iArray[2][1] = "100px";            		//列宽
      iArray[2][2] = 60;            			//列最大值
      iArray[2][3] = 0;                   			//是否允许输入,1表示允许，0表示不允许
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "投资比例上限";         		//列名
      iArray[3][1] = "0px";            		//列宽
      iArray[3][2] = 60;            			//列最大值
      iArray[3][3] = 3;              			//是否允许输入,1表示允许，0表示不允许
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "投资比例下限";         		//列名
      iArray[4][1] = "0px";            		//列宽
      iArray[4][2] = 50;            			//列最大值
      iArray[4][3] = 3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5] = new Array();
      iArray[5][0] = "投资比例";         		//列名
      iArray[5][1] = "80px";            		//列宽
      iArray[5][2] = 50;            			//列最大值
      iArray[5][3] = 1;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[6] = new Array();
      iArray[6][0] = "缴费编号";         		//列名
      iArray[6][1] = "0px";            		//列宽
      iArray[6][2] = 100;            			//列最大值
      iArray[6][3] = 3;              			//是否允许输入,1表示允许，0表示不允许

        
      iArray[7] = new Array();
      iArray[7][0] = "币种";         		//列名
      iArray[7][1] = "80px";            		//列宽
      iArray[7][2] = 100;            			//列最大值
      iArray[7][3] = 0;


     
      /*iArray[8] = new Array();
      iArray[8][0] = "";         		//列名
      iArray[8][1] = "80px";            		//列宽
      iArray[8][2] = 100;            			//列最大值
      iArray[8][3] = 1;*/
 
      NewPlanGrid = new MulLineEnter( "fm" , "NewPlanGrid" );
      //这些属性必须在loadMulLine前
      NewPlanGrid.mulLineCount = 5;
     NewPlanGrid.displayTitle = 1;
     NewPlanGrid.hiddenPlus = 1;
      NewPlanGrid.hiddenSubtraction = 1;
      NewPlanGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
