
<%
	//**************************************************************************************************
	//程序名称：CardStatInit.jsp
	//程序功能：单证综合报表
	//创建日期： 2009-02-18
	//创建人  ： mw
	//更新记录：  更新人    更新日期     更新原因/内容
	//**************************************************************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
<%
	String strCurDate = PubFun.getCurrentDate();
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strOperator = globalInput.Operator;
	String managecom = globalInput.ManageCom;
%>
// 输入框的初始化（单记录部分）
function initInpBox()
{
  try
  {
  	fm.reset();  	
  	fm.makedateB.value = "<%= strCurDate %>";
  	fm.makedateE.value = "<%= strCurDate %>";
  }
  catch(ex)
  {
    alert("初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initCardPrintInfoGrid();  
    initCardPrintInfo2Grid();   
  }
  catch(re)
  {
    alert("CardSendOutInfoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 单证信息列表的初始化
function initCardPrintInfoGrid()
{                               
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="机构编码";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="机构名称";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
    	iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="单证编码";          		 
		iArray[3][1]="60px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="单证名称";        		   
		iArray[4][1]="120px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="发放";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="本级申请回退";         		   
		iArray[6][1]="70px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;   

		iArray[7]=new Array();
		iArray[7][0]="上级调拨回退";         		   
		iArray[7][1]="70px";            		 
		iArray[7][2]=100;            			   
		iArray[7][3]=0;  
				           			   
		iArray[8]=new Array();
		iArray[8][0]="拒绝入库回退";         		   
		iArray[8][1]="70px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;    

	    CardPrintInfoGrid = new MulLineEnter( "document" , "CardPrintInfoGrid" ); 
	    CardPrintInfoGrid.mulLineCount = 5;   
	    CardPrintInfoGrid.displayTitle = 1;
	    CardPrintInfoGrid.locked = 1;
	    CardPrintInfoGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	    CardPrintInfoGrid.hiddenPlus=1;
	    CardPrintInfoGrid.hiddenSubtraction=1;
	    CardPrintInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

function initCardPrintInfo2Grid()
{                               
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="机构编码";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="机构名称";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
    	iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="单证编码";          		 
		iArray[3][1]="60px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="单证名称";        		   
		iArray[4][1]="120px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="自动缴销";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="手工缴销";         		   
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;   
		           			   
		iArray[7]=new Array();
		iArray[7][0]="使用作废";         		   
		iArray[7][1]="60px";            		 
		iArray[7][2]=100;            			   
		iArray[7][3]=0;    
		
		iArray[8]=new Array();
		iArray[8][0]="停用作废";         		   
		iArray[8][1]="60px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;    
		
		iArray[9]=new Array();
		iArray[9][0]="遗失";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;    
		
		iArray[10]=new Array();
		iArray[10][0]="销毁";         		   
		iArray[10][1]="60px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=0;    

	    CardPrintInfo2Grid = new MulLineEnter( "document" , "CardPrintInfo2Grid" ); 
	    CardPrintInfo2Grid.mulLineCount = 5;   
	    CardPrintInfo2Grid.displayTitle = 1;
	    CardPrintInfo2Grid.locked = 1;
	    CardPrintInfo2Grid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	    CardPrintInfo2Grid.hiddenPlus=1;
	    CardPrintInfo2Grid.hiddenSubtraction=1;
	    CardPrintInfo2Grid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
