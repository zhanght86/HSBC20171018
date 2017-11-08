<%
//程序名称：LFGetNoticeInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
		document.all('FiscalYear').value = '';
		document.all('GrpContNo').value = '';
		document.all('RiskCode').value = '212401';
  }
  catch(ex)
  {
    alert("在GrpInterestInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();   
    //document.all('ErrorLog').disabled=true;
  }
  catch(re)
  {
    alert("GrpInterestInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 var PolGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initPolGrid()
{                               
  var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="团单号";        		//列名
	  iArray[1][1]="120px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
	  iArray[2][0]="团单名称";        	//列名
	  iArray[2][1]="200px";            	//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=0; 
	
	  iArray[3]=new Array();
	  iArray[3][0]="管理机构";            //列名
	  iArray[3][1]="80px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0; 									//是否允许输入,1表示允许，0表示不允许
    
	  iArray[4]=new Array();
	  iArray[4][0]="生效日期";            //列名
	  iArray[4][1]="80px";            	//列宽
	  iArray[4][2]=100;            			//列最大值
	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许
    
	  iArray[5]=new Array();
	  iArray[5][0]="印刷号";            //列名
	  iArray[5][1]="0px";            	//列宽
	  iArray[5][2]=100;            			//列最大值
	  iArray[5][3]=2; 									//是否允许输入,1表示允许，0表示不允许
    
//	  iArray[5]=new Array();
//	  iArray[5][0]="团单特约";            //列名
//	  iArray[5][1]="120px";            	//列宽
//	  iArray[5][2]=100;            			//列最大值
//	  iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许
    
    PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
    //这些属性必须在loadMulLine前
    PolGrid.mulLineCount = 5;   
    PolGrid.displayTitle = 1;
    PolGrid.canSel = 0;
    PolGrid.hiddenPlus=1;
    PolGrid.hiddenSubtraction=1;
    PolGrid.loadMulLine(iArray);  
  }
  catch(ex)
  {
    alert("GrpInterestInit.jsp-->initPolGrid函数中发生异常:"+ex);
  }
}
</script>
