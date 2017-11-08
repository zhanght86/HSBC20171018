<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//程序名称：PostMidplatCertifyQueryInit.jsp
	//程序功能：
	//创建日期：2007-11-07
	//创建人  ：zhangzheng程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
String strCurDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {
  	fm.MakeDateB.value = '<%= strCurDate %>';
  	fm.MakeDateE.value = '<%= strCurDate %>';
  }
  catch(ex)
  {
    alert("在PostMidplatCertifyQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {}
  catch(ex)
  {
    alert("在PostMidplatCertifyQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
	initCardInfo();
  }
  catch(re)
  {
    alert("PostMidplatCertifyQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="40px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="管理机构";         		  //列名
		iArray[1][1]="80px";            		//列宽
		iArray[1][2]=100;            			  //列最大值
		iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="业务员/客户经理工号";          		//列名
		iArray[2][1]="80px";            		//列宽
		iArray[2][2]=100;            			  //列最大值
		iArray[2][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="起始号"; //列名
		iArray[3][1]="120px";            		//列宽
		iArray[3][2]=80;            			  //列最大值
		iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="终止号";            //列名
		iArray[4][1]="120px";            		//列宽
		iArray[4][2]=80;            		 	  //列最大值
		iArray[4][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="数量";         		//列名
		iArray[5][1]="40px";            		//列宽
		iArray[5][2]=80;            			  //列最大值
		iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[6]=new Array();
		iArray[6][0]="发放日期";            //列名
		iArray[6][1]="80px";            		//列宽
		iArray[6][2]=80;            			  //列最大值
		iArray[6][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

		iArray[7]=new Array();
		iArray[7][0]="核销日期";         		    //列名
		iArray[7][1]="80px";            		//列宽
		iArray[7][2]=100;            			  //列最大值
		iArray[7][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	  	iArray[8]=new Array();
		iArray[8][0]="状态";          		  //列名
		iArray[8][1]="80px";            		//列宽
		iArray[8][2]=60;            			  //列最大值
		iArray[8][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

		CardInfo = new MulLineEnter("document" , "CardInfo"); 
		CardInfo.mulLineCount = 5;
		CardInfo.displayTitle = 1;
	  	CardInfo.hiddenSubtraction=1;
	  	CardInfo.hiddenPlus=1;
		CardInfo.locked = 1;
		CardInfo.canSel = 1;
		CardInfo.loadMulLine(iArray);  
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
