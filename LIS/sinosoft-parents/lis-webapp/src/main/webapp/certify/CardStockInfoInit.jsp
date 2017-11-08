
<%
	//**************************************************************************************************
	//程序名称：CardStockInfoInit.jsp
	//程序功能：库存盘点清单
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
%>

// 输入框的初始化（单记录部分）
function initInpBox()
{
  try
  {
  	fm.reset();
  	fm.ManageCom.value = '';
  	fm.ManageComName.value = '';
  	
  	fm.Grade.value = '';
  	fm.GradeName.value = '';
  		
  	fm.CertifyCode.value = '';
  	fm.CertifyName.value = '';
  	
  	fm.CertifyClass.value = '';
  	fm.CertifyClassName.value = '';
  	
  	fm.CertifyClass2.value = '';
  	fm.CertifyClass2Name.value = '';  	
  		
  	fm.MakeDateB.value = '';
  	fm.MakeDateE.value = '';
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
    initCardSendOutInfoGrid();    
  }
  catch(re)
  {
    alert("CardSendOutInfoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 单证信息列表的初始化
function initCardSendOutInfoGrid()
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
		iArray[3][0]="单证类型";          		 
		iArray[3][1]="60px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="单证编码";        		   
		iArray[4][1]="60px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="单证名称";          		 
		iArray[5][1]="120px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="起始号";         		   
		iArray[6][1]="120px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="终止号";          		   
		iArray[7][1]="120px";            		 
		iArray[7][2]=60;            			   
		iArray[7][3]=0;              			   

		iArray[8]=new Array();
		iArray[8][0]="数量";          		   
		iArray[8][1]="60px";            		 
		iArray[8][2]=60;            			   
		iArray[8][3]=0;     
		
		iArray[9]=new Array();
		iArray[9][0]="盘点记录";          		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=60;            			   
		iArray[9][3]=0;  
		
	    CardSendOutInfoGrid = new MulLineEnter( "document" , "CardSendOutInfoGrid" ); 
	    CardSendOutInfoGrid.mulLineCount = 5;   
	    CardSendOutInfoGrid.displayTitle = 1;
	    CardSendOutInfoGrid.locked = 0;
	    CardSendOutInfoGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
	    CardSendOutInfoGrid.hiddenPlus=1;
	    CardSendOutInfoGrid.hiddenSubtraction=1;
	    CardSendOutInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
