<%
//程序名称：BodyCheckPrintInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
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
<script language="JavaScript">
function initInpBox()
{
	try
	{
		document.all('ContNo').value = '';
		document.all('AgentCode').value = '';
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(ex)
	{
		alert("在BodyCheckPrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initPolGrid();
	}
	catch(re)
	{
		alert("BodyCheckPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="通知书号";
		iArray[1][1]="140px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="印刷号";
		iArray[2][1]="140px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="代理人编码";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=2;
		iArray[3][4]="AgentCode";
		iArray[3][5]="3";
		iArray[3][9]="代理人编码|code:AgentCode&NOTNULL";
		iArray[3][18]=250;
		iArray[3][19]= 0 ;

		
		iArray[4]=new Array();
		iArray[4][0]="管理机构";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;
		iArray[4][4]="station";
		iArray[4][5]="3";
		iArray[4][9]="管理机构|code:station&NOTNULL";
		iArray[4][18]=250;
		iArray[4][19]= 0 ;

    iArray[5]=new Array();
	  iArray[5][0]="销售渠道";         	//列名
	  iArray[5][1]="100px";            	//列宽
	  iArray[5][2]=100;            			//列最大值
	  iArray[5][3]=2; 
	  iArray[5][4]="SaleChnl";              	        //是否引用代码:null||""为不引用
    iArray[5][5]="3";              	                //引用代码对应第几列，'|'为分割符
    iArray[5][9]="销售渠道|code:SaleChnl&NOTNULL";
    iArray[5][18]=250;
    iArray[5][19]= 0 ;	

		iArray[6]=new Array();
		iArray[6][0]="工作流任务编码";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="工作流子任务编码";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		PolGrid = new MulLineEnter( "fmSave" , "PolGrid" );
		//这些属性必须在loadMulLine前
		PolGrid.mulLineCount = 10;
		PolGrid.displayTitle = 1;
		PolGrid.canSel = 1;
		PolGrid.hiddenPlus=1;
		PolGrid.hiddenSubtraction=1;
		PolGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
