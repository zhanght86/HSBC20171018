<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//程序名称：SelfRiskInformationQueryInit.jsp
//程序功能：自助卡单险种信息查询
//创建日期：2008-03-14
//创建人  ：zz
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// 输入框的初始化（单记录部分）                                      
function initForm()
{
  try
  {
		initRiskInfo();
		
  }
  catch(re)
  {
    alert("SelfRiskInformationQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
  
  easyQueryClick();
}

// 险种信息列表的初始化
function initRiskInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="40px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="险种编码";         		  //列名
		iArray[1][1]="30px";            		//列宽
		iArray[1][2]=30;            			  //列最大值
		iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="险种名称";          		//列名
		iArray[2][1]="80px";            		//列宽
		iArray[2][2]=80;            			  //列最大值
		iArray[2][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="保险年期";          		//列名
		iArray[3][1]="30px";            		//列宽
		iArray[3][2]=30;            			  //列最大值
		iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="保险年期类型";          		//列名
		iArray[4][1]="30px";            		//列宽
		iArray[4][2]=30;            			  //列最大值
		iArray[4][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="保费"; //列名
		iArray[5][1]="30px";            		//列宽
		iArray[5][2]=30;            			  //列最大值
		iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

		RiskInfo = new MulLineEnter("fm" , "RiskInfo"); 
		// 这些属性必须在loadMulLine前
		// CardInfo.mulLineCount = 2;   
		RiskInfo.displayTitle = 1;
	  	RiskInfo.hiddenSubtraction=1;
	  	RiskInfo.hiddenPlus=1;
		RiskInfo.locked = 1;
		//RiskInfo.canSel = 1;
		RiskInfo.loadMulLine(iArray);  
		//这些操作必须在loadMulLine后面
		//RiskInfo.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
