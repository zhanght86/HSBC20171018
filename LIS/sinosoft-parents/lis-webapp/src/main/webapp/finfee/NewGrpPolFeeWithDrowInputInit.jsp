 <%
//程序名称：NewGrpPolFeeWithDrowInputInit.jsp
//程序功能：
//创建日期：2006-10-18 9:49
//创建人  ：lujun 
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<script language="JavaScript">
function initInpBox()
{ 
  try
  {
  	fm.GrpContNo.value = '';
  	fm.PrtNo.value = '';
  	fm.GrpName.value = '';
  	fm.AgentCode.value = '';
 // 	fm.Dif.value = 0;
  }
  catch(ex)
  {
    alert("在NewGrpPolFeeWithDrowInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();   
    initFinFeeWithDrawGrid();   	
  }
  catch(re)
  {
    alert("NewGrpPolFeeWithDrowInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

 var FinFeeWithDrawGrid ;
function initFinFeeWithDrawGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            		//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="团体合同号";   		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            		//列最大值
      iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单位名称";		//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=60;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      

      iArray[3]=new Array();
      iArray[3][0]="退费金额";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=200;            	        //列最大值
      iArray[3][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="签单日期";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            	        //列最大值
      iArray[4][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
	
	  
      iArray[5]=new Array();
      iArray[5][0]="单位开户银行";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            	        //列最大值
      iArray[5][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="交费号";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            	        //列最大值
      iArray[6][3]=3;                   	//是否允许输入,1表示允许，0表示不允许
      

      iArray[7]=new Array();
      iArray[7][0]="单位银行帐号";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            	        //列最大值
      iArray[7][3]=0;                   	//是否允许输入,1表示允许，0表示不允许  
      
      iArray[8]=new Array();
      iArray[8][0]="管理机构";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=200;            	        //列最大值
      iArray[8][3]=3;   
/*
      iArray[8]=new Array();
      iArray[8][0]="批单号或理赔号";         		//列名
      iArray[8][1]="120px";            		//列宽
      iArray[8][2]=200;            	        //列最大值
      iArray[8][3]=0;                   	//是否允许输入,1表示允许，0表示不允许  
      
      iArray[9]=new Array();
      iArray[9][0]="保单号";         		//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=200;            	        //列最大值
      iArray[9][3]=0;                   	//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="实付号码";         		//列名
      iArray[10][1]="120px";            		//列宽
      iArray[10][2]=200;            	        //列最大值
      iArray[10][3]=3;                   	//是否允许输入,1表示允许，0表示不允许 
      */       
 
      FinFeeWithDrawGrid = new MulLineEnter( "fm" , "FinFeeWithDrawGrid" ); 
      //这些属性必须在loadMulLine前
      FinFeeWithDrawGrid.mulLineCount = 5;
      FinFeeWithDrawGrid.displayTitle = 1;
      FinFeeWithDrawGrid.hiddenPlus = 1;
      FinFeeWithDrawGrid.hiddenSubtraction = 1;
      FinFeeWithDrawGrid.canSel = 1;
      FinFeeWithDrawGrid.canChk = 0;
      FinFeeWithDrawGrid.loadMulLine(iArray);
      FinFeeWithDrawGrid.chkBoxEventFuncName = "GetRecord";  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
