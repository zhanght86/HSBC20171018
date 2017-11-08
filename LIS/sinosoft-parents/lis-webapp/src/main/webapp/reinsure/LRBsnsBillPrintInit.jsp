<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-10-24
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 GlobalInput tG = new GlobalInput();
 tG=(GlobalInput)session.getAttribute("GI");
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

<script type="text/javascript">
function initInpBox()
{
  try
  { 
  }
  catch(ex)
  {
    myAlert("初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initBillPrintListGrid();
    queryBillPrint();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"初始化界面错误!");
  }
}

//待审核列表
function initBillPrintListGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="账单编号"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="账单周期";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=0;     

		iArray[3]=new Array();
		iArray[3][0]="账单期次";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=0;  
				
		iArray[4]=new Array();
		iArray[4][0]="分保合同编号";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="分保公司编号";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="起始日期";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=0;   
		
		iArray[7]=new Array();
		iArray[7][0]="终止日期";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=0;   
		
		iArray[8]=new Array();
		iArray[8][0]="账单状态";
		iArray[8][1]="50px";  
		iArray[8][2]=100; 
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="账单状态代码";
		iArray[9][1]="25px";  
		iArray[9][2]=100; 
		iArray[9][3]=3;
			
	  	BillPrintListGrid = new MulLineEnter( "fm" , "BillPrintListGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	BillPrintListGrid.mulLineCount = 0;   
	  	BillPrintListGrid.displayTitle = 1;
	  	BillPrintListGrid.locked = 0;
	  	BillPrintListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	  	BillPrintListGrid.canChk = 0; //是否增加多选框,1为显示CheckBox列，0为不显示 (缺省值)
	  	//TaskListGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号   
	  	BillPrintListGrid.hiddenPlus=1; 
	  	BillPrintListGrid.hiddenSubtraction=1;
	  	BillPrintListGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
</script>