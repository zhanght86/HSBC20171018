<%
//程序名称：UpReportInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
%>
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
    alert("在UpReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("在UpReportInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
  
    initInpBox();
    initSelBox();   
    initRiskGrid();      
  }
  catch(re)
  {
    alert("UpReportInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 
// 投保单信息列表的初始化
// 险种信息列表的初始化
function initRiskGrid()
{

	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种编码";
		iArray[1][1]="60px";
		iArray[1][2]=20;
		iArray[1][3]=2;
		iArray[1][4]="RiskCode";
		iArray[1][5]="1|2";
		iArray[1][6]="0|1";
		iArray[1][9]="险种编码|code:RiskCode&NOTNULL";
		iArray[1][18]=300;
		//iArray[1][19]=1;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="140px";
		iArray[2][2]=20;
		iArray[2][3]=0;
		//iArray[2][9]="险种名称|NOTNULL";

		iArray[3]=new Array();
		iArray[3][0]="缴费方式";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=1;
		

		iArray[4]=new Array();
		iArray[4][0]="缴费期限";
		iArray[4][1]="60px";
		iArray[4][2]=20;
		iArray[4][3]=1;
		

		iArray[5]=new Array();
		iArray[5][0]="领取方式";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=1;
		

		iArray[6]=new Array();
		iArray[6][0]="男女比例";
		iArray[6][1]="60px";
		iArray[6][2]=20;
		iArray[6][3]=1;
		
		iArray[7]=new Array();
		iArray[7][0]="最低个人保额";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=1;
		

		iArray[8]=new Array();
		iArray[8][0]="最高个人保额";
		iArray[8][1]="80px";
		iArray[8][2]=20;
		iArray[8][3]=1;
		

		iArray[9]=new Array();
		iArray[9][0]="保额限制原则";
		iArray[9][1]="120px";
		iArray[9][2]=20;
		iArray[9][3]=1;
		

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" );
		//这些属性必须在loadMulLine前
		RiskGrid.mulLineCount = 0;
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =1;
		RiskGrid. hiddenPlus=0;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		RiskGrid. hiddenSubtraction=0;
		RiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
