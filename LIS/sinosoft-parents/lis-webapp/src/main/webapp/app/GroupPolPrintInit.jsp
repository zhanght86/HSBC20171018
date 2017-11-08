<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupPolPrintInit.jsp
//程序功能：
//创建日期：2002-11-26
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*" %>
<%
GlobalInput globalInput = (GlobalInput)session.getValue("GI");
String strManageCom = globalInput.ManageCom;
%>
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox(){
  try{
		 document.all('ManageCom').value = <%=tGI.ComCode%>;
		 if(document.all('ManageCom').value!=86)
     {
    	 document.all("divErrorMInfo").style.display="none";
     }
  }
  catch(ex)
  {
    alert("在GroupPolPrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
		initGrpContGrid();
		manageCom = '<%= strManageCom %>';
  }
  catch(re)
  {
    alert("GroupPolPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initGrpContGrid(){
	var iArray = new Array();

	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";	//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";	//列宽
		iArray[0][2]=10;	//列最大值
		iArray[0][3]=0;	//是否允许输入,1表示允许，0表示不允许

		iArray[1]=new Array();
		iArray[1][0]="团体保单号";
		iArray[1][1]="160px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="印刷号";
		iArray[2][1]="120px";            	
		iArray[2][2]=100;       
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="团单保费";
		iArray[3][1]="70px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="投保人名称";
		iArray[4][1]="260px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="打印被保人条数";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="签单日期";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		GrpContGrid = new MulLineEnter( "fm" , "GrpContGrid" );
		//这些属性必须在loadMulLine前
		GrpContGrid.mulLineCount = 5;
		GrpContGrid.displayTitle = 1;
		GrpContGrid.hiddenPlus = 1;
		GrpContGrid.hiddenSubtraction = 1;
		GrpContGrid.canSel = 0;
		GrpContGrid.canChk = 1;
		GrpContGrid.locked = 1;
		GrpContGrid.canChk = 1;
		GrpContGrid.loadMulLine(iArray);

		//这些操作必须在loadMulLine后面
		//GrpContGrid.setRowColData(1,1,"asdf");
	}
	catch(ex){
		alert(ex);
	}
}

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
