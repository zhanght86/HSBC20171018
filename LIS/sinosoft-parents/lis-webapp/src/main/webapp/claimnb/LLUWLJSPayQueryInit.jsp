<%
//程序名称：LLUWLJSPayQueryInit.jsp
//程序功能：
//创建日期：2006-01-05 
//创建人  ：万泽辉
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//添加页面控件的初始化。
GlobalInput globalInput = (GlobalInput)session.getValue("GI");

if(globalInput == null) 
{
	out.println("session has expired");
	return;
}

String strOperator = globalInput.Operator;
String strManageCom = globalInput.ComCode;
%>
<script language="JavaScript">

function initForm()
{
	try
	{
	    initLJSPayGrid();
		LJSPayQuery();
		initLJSPayPersonGrid();
		//LJSPayPersonGrid();
	}
	catch(re)
	{
		alert("initForm.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

var LJSPayGrid;          
// 投保单信息列表的初始化
function initLJSPayGrid()
{

	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="通知书号码";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="投保人客户号";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="应收总金额(元)";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		

		iArray[4]=new Array();
		iArray[4][0]="交费日期";
		iArray[4][1]="120px";
		iArray[4][2]=200;
		iArray[4][3]=0;
	    
		iArray[5]=new Array();
		iArray[5][0]="复核人编码";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="其他";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=3;

		LJSPayGrid = new MulLineEnter( "document" , "LJSPayGrid" ); 
        //这些属性必须在loadMulLine前
         
        LJSPayGrid.mulLineCount      = 5;   
	    LJSPayGrid.displayTitle      = 1;
	    LJSPayGrid.locked            = 1;
	    LJSPayGrid.canSel            = 1; 
	    LJSPayGrid.selBoxEventFuncName = "GetLJSPayPerson";
        LJSPayGrid.hiddenPlus        = 1;
	    LJSPayGrid.hiddenSubtraction = 1;
	    LJSPayGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
		 alert(ex);
	}
}

//应收加费信息详细列表
var LJSPayPersonGrid;
function initLJSPayPersonGrid()
{
    var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="合同号";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="责任编码";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="交费计划编码";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		

		iArray[4]=new Array();
		iArray[4][0]="交费类型";
		iArray[4][1]="120px";
		iArray[4][2]=200;
		iArray[4][3]=0;
	    
		iArray[5]=new Array();
		iArray[5][0]="总应交金额";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="原交至日期";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;
        
        iArray[7]=new Array();
		iArray[7][0]="现交至日期";
		iArray[7][1]="100px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		 LJSPayPersonGrid = new MulLineEnter( "document" , "LJSPayPersonGrid" ); 
         //这些属性必须在loadMulLine前
         
         LJSPayPersonGrid.mulLineCount      = 5;   
	     LJSPayPersonGrid.displayTitle      = 1;
	     LJSPayPersonGrid.locked            = 1;
	     LJSPayPersonGrid.canSel            = 0; 
	     LJSPayPersonGrid.hiddenPlus        = 1;
	     LJSPayPersonGrid.hiddenSubtraction = 1;
	     LJSPayPersonGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
		 alert(ex);
	}
}
</script>
