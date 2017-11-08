
<%
	//**************************************************************************************************
	//程序名称：CardPrintFeeStatInit.jsp
	//程序功能：机构印刷费用统计报表
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
  	
    fm.SendOutCom.value = "<%= managecom %>";
    fm.ReceiveCom.value = '';
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
		iArray[1][0]="发放机构";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;    
		
		iArray[2]=new Array();
		iArray[2][0]="接收机构";         		 
		iArray[2][1]="60px";            		 
		iArray[2][2]=100;            			   
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
		iArray[5][0]="单价";        		   
		iArray[5][1]="60px";            		 
		iArray[5][2]=85;            			   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="数量";          		 
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            		 	   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="合计金额";         		   
		iArray[7][1]="60px";            		 
		iArray[7][2]=100;            			   
		iArray[7][3]=0;   
		           			   
		iArray[8]=new Array();
		iArray[8][0]="开始日期";          		 
		iArray[8][1]="60px";            		 
		iArray[8][2]=100;            		 	   
		iArray[8][3]=0;              			   
		
		iArray[9]=new Array();
		iArray[9][0]="终止日期";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;             
		
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

</script>
