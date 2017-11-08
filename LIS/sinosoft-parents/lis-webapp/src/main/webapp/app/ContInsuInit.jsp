 <%
//程序名称：TempFeeInit.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
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
       
  }
  catch(ex)
  {
    alert("在TempFeeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在TempFeeInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
   // initLCInsuredPolGrid();
    initImpartGrid();  
  
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 暂收费信息列表的初始化
function initLCInsuredPolGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";          		//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[1][5]="1|2";              	                //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";              	        //上面的列中放置引用代码中第几位值
      iArray[1][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[1][18]=300;
      //iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         			//列名
      iArray[2][1]="140px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[2][9]="险种名称|NOTNULL";

      iArray[3]=new Array();
      iArray[3][0]="保费";      	   		//列名
      iArray[3][1]="100px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="交费金额|NUM&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="保额";      	   		//列名
      iArray[4][1]="160px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 
      iArray[3][9]="保费合计|NUM&NOTNULL";             			//是否允许输入,1表示允许，0表示不允许

      LCInsuredPolGrid = new MulLineEnter( "fm" , "LCInsuredPolGrid" ); 
      //这些属性必须在loadMulLine前
      LCInsuredPolGrid.mulLineCount = 0;   
      LCInsuredPolGrid.displayTitle = 1;
      LCInsuredPolGrid.canChk=1;
      LCInsuredPolGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LCInsuredPolGrid. hiddenSubtraction=1;
 
      LCInsuredPolGrid.loadMulLine(iArray);  
	LCInsuredPolGrid.addOne("LCInsuredPolGrid");     
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,1,'111302');
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,2,"[险种名称]");
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,3,"10.00");
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,4,"1000.00");
     
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 告知信息列表的初始化
function initImpartGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="ImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="告知客户类型";         		//列名
      iArray[5][1]="90px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="CustomerType";
      iArray[5][9]="告知客户类型|len<=18";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
  
</script>
