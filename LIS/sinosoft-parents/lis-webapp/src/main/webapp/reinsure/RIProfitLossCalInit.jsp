<%@include file="/i18n/language.jsp"%>
 <html>
<%
//name :LRProfitLossCalInit.jsp
//function : 
//Creator :zhangbin
//date :2007-3-14
%>

<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 	String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);                	               	
 %>
<script type="text/javascript">

function initInpBox()
{
	try
	{
		fm.RIcomCode.value="";
		fm.ContNo.value="";
		fm.CalYear.value="2010";
	}
	catch(ex)
	{
		myAlert("进行初始化是出现错误！");
	}
}
;

// 下拉列表的初始化
function initSelBox(){
  try{
  }
  catch(ex){
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
	try
	{
		initInpBox();
		initSelBox();
		initIncomeGrid();
		initPayoutGrid();
		
		queryAuditTask(); //待审核查询
	}
	catch(re)
	{
		myAlert("3CertifyDescInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

function initIncomeGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="收入支出类型";    //列名
    iArray[1][1]="50px";            	//列宽
    iArray[1][2]=200;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="参数代码";
    iArray[2][1]="80px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="参数名称";     //列名
    iArray[3][1]="100px";            	//列宽
    iArray[3][2]=200;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="录入类型";     //列名
    iArray[4][1]="50px";            	//列宽
    iArray[4][2]=200;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="参数值";     //列名
    iArray[5][1]="50px";            	//列宽
    iArray[5][2]=200;            			//列最大值
    iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
    iArray[5][9]="参数值|notnull&num";
    
    iArray[6]=new Array();
    iArray[6][0]="参数值校验";     //列名
    iArray[6][1]="50px";            	//列宽
    iArray[6][2]=200;            			//列最大值
    iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="录入类型编码";     //列名
    iArray[7][1]="50px";            	//列宽
    iArray[7][2]=200;            			//列最大值
    iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="批次号";     //列名
    iArray[8][1]="50px";            	//列宽
    iArray[8][2]=50;            			//列最大值
    iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
       
    IncomeGrid = new MulLineEnter( "fm" , "IncomeGrid" );
    IncomeGrid.mulLineCount = 0;
    IncomeGrid.displayTitle = 1;
    IncomeGrid.hiddenPlus=1;   
    IncomeGrid.hiddenSubtraction=1; 
    //IncomeGrid.canSel=1;
    IncomeGrid.loadMulLine(iArray);
    IncomeGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    myAlert("初始化时出错:"+ex);
  }
}

function initPayoutGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="支出项代码";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=3;

    iArray[2]=new Array();
    iArray[2][0]="支出项名称";     //列名
    iArray[2][1]="100px";            	//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="录入类型";     //列名
    iArray[3][1]="50px";            	//列宽
    iArray[3][2]=200;            			//列最大值
    iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="支出值";     //列名
    iArray[4][1]="50px";            	//列宽
    iArray[4][2]=200;            			//列最大值
    iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
    iArray[4][9]="支出值|notnull&num";
    
    iArray[5]=new Array();
    iArray[5][0]="支出值校验";     //列名
    iArray[5][1]="50px";            	//列宽
    iArray[5][2]=200;            			//列最大值
    iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    PayoutGrid = new MulLineEnter( "fm" , "PayoutGrid" );
    PayoutGrid.mulLineCount = 0;
    PayoutGrid.displayTitle = 1;
    PayoutGrid.hiddenPlus=1;        
    PayoutGrid.hiddenSubtraction=1; 
    //PayoutGrid.canSel=1;
    PayoutGrid.loadMulLine(iArray);
    PayoutGrid.detailInfo="单击显示详细信息";
  }
  catch(ex){
    myAlert("初始化时出错:"+ex);
  }
}

</script>


