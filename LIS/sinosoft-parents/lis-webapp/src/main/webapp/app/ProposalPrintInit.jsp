<%
//程序名称：ProposalPrintInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<%
GlobalInput globalInput = (GlobalInput)session.getValue("GI");
String strManageCom = globalInput.ComCode;
String strCurDate =PubFun.getCurrentDate();
loggerDebug("ProposalPrintInit","strCurDate"+strCurDate);
%>
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
	try
	{
		 document.all('ManageCom').value = <%=tGI.ComCode%>;
		 var tManageCode=<%=strManageCom%>;
     if(tManageCode!=86)
     {
    	 document.all("divErrorMInfo").style.display="none";
      }
		 //document.all('MakeDate').value = <%=strCurDate%>;
	}
	catch(ex)
	{
		alert("在ProposalPrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initContGrid();
		initErrorGrid();
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(re)
	{
		alert("ProposalPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

//定义为全局变量，提供给displayMultiline使用
var ContGrid;

// 保单信息列表的初始化
function initContGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";	//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";	//列宽
		iArray[0][2]=10;	//列最大值
		iArray[0][3]=0;	//是否允许输入,1表示允许，0表示不允许

		iArray[1]=new Array();
		iArray[1][0]="保单号";
		iArray[1][1]="180px";           
		iArray[1][2]=100;            	
		iArray[1][3]=0;
		
		
		iArray[2]=new Array();
		iArray[2][0]="投保单号";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="印刷号";
		iArray[3][1]="180px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="投保人姓名";
		iArray[4][1]="160px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="签单日期";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="签单时间";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="打印标记";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=3;
		
		iArray[8]=new Array();
		iArray[8][0]="保单类型";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=3;
		
		iArray[9]=new Array();
		iArray[9][0]="家庭单号";
		iArray[9][1]="0px";
		iArray[9][2]=100;
		iArray[9][3]=3;
		
		iArray[10]=new Array();
		iArray[10][0]="VIP保单标记";
		iArray[10][1]="0px";
		iArray[10][2]=100;
		iArray[10][3]=3;
		


		ContGrid = new MulLineEnter( "fm" , "ContGrid" );
		//这些属性必须在loadMulLine前
		ContGrid.mulLineCount = 5;
		ContGrid.displayTitle = 1;
		ContGrid.hiddenPlus = 1;
		ContGrid.hiddenSubtraction = 1;
		ContGrid.canSel = 0;
		ContGrid.canChk = 1;
		ContGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

var ContGrid;

// 保单信息列表的初始化
function initErrorGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";	//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";	//列宽
		iArray[0][2]=10;	//列最大值
		iArray[0][3]=0;	//是否允许输入,1表示允许，0表示不允许

		iArray[1]=new Array();
		iArray[1][0]="保单号";
		iArray[1][1]="140px";           
		iArray[1][2]=100;            	
		iArray[1][3]=0;
		
		
		iArray[2]=new Array();
		iArray[2][0]="出错信息";
		iArray[2][1]="400px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="出错日期";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="出错时间";
		iArray[4][1]="80px";
		iArray[4][2]=200;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="管理机构";
		iArray[5][1]="80px";
		iArray[5][2]=200;
		iArray[5][3]=0;

		ErrorGrid = new MulLineEnter( "fm" , "ErrorGrid" );
		//这些属性必须在loadMulLine前
		ErrorGrid.mulLineCount = 5;
		ErrorGrid.displayTitle = 1;
		ErrorGrid.hiddenPlus = 1;
		ErrorGrid.hiddenSubtraction = 1;
		ErrorGrid.canSel = 0;
		ErrorGrid.canChk = 0;
		ErrorGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
