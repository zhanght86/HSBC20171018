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
    initRiskGrid();  
    getGrpCont();
  
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 险种信息列表的初始化
function initRiskGrid()
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
      iArray[3][0]="投保人数";      	   		//列名
      iArray[3][1]="100px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="交费金额|NUM&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="保费合计";      	   		//列名
      iArray[4][1]="160px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 
      iArray[3][9]="保费合计|NUM&NOTNULL";             			//是否允许输入,1表示允许，0表示不允许

      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //这些属性必须在loadMulLine前
      RiskGrid.mulLineCount = 0;   
      RiskGrid.displayTitle = 1;
      RiskGrid.canChk =1;
      RiskGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      RiskGrid. hiddenSubtraction=1;

      RiskGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

function getGrpCont(){


    try { fm.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
	try { fm.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };

}
  
</script>
