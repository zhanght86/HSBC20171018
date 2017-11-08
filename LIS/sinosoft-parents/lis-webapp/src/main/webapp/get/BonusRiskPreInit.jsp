<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>  
<script language="JavaScript">

function initForm()
{

  try
  { 
    initInpBox();    
    initNoBonusRiskGrid();  
    initBonusRiskGrid();
    //queryData('1');
    //alert("000000000000000000000");     
  }
  catch(re)
  {
    alert("在BonusRiskPreInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{ 
  try
  {     
    document.all('BonusCalYear').value =getCurrentDate("-").substring(0,4)-1;
    document.all('BonusCalRisk').value = '';
   
  }
  catch(ex)
  {
    alert("在NBonusRiskPreInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
  
  function initNoBonusRiskGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种名称";          	//列名
      iArray[1][1]="50px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[3]=new Array();
      iArray[3][0]="状态";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许



       NoBonusRiskGrid = new MulLineEnter( "document" , "NoBonusRiskGrid" ); 
      //这些属性必须在loadMulLine前
      NoBonusRiskGrid.mulLineCount = 5;   
      NoBonusRiskGrid.displayTitle = 1;
      NoBonusRiskGrid.canSel =1;
      NoBonusRiskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      NoBonusRiskGrid.hiddenSubtraction=1;
      
      NoBonusRiskGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
    function initBonusRiskGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种名称";          	//列名
      iArray[1][1]="50px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


       BonusRiskGrid = new MulLineEnter( "document" , "BonusRiskGrid" ); 
      //这些属性必须在loadMulLine前
      BonusRiskGrid.mulLineCount = 5;   
      BonusRiskGrid.displayTitle = 1;
      BonusRiskGrid.canChk  =1;
      BonusRiskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      BonusRiskGrid.hiddenSubtraction=1;
      
      BonusRiskGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
</script>
