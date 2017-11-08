<%
//Creator :刘岩松
//Date :2003-04-18
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	String strOperator = globalInput.Operator;
	String managecom = globalInput.ManageCom;
	String strCurTime = PubFun.getCurrentDate();
%>
<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initCardPrintQueryGrid();
  }
  catch(re)
  {
    alert("CardPlanPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{
  try
  {       
    fm.managecom.value = '<%= managecom %>';
    fm.PrintOperator.value = '<%= strOperator %>';
    fm.PrintDate.value = '<%= strCurTime %>';
    
    document.all('CertifyCode').value = '';
    document.all('CertifyName').value = '';
    document.all('PlanType').value = '';
    document.all('PlanTypeName').value = '';  
  }
  catch(ex)
  {
    alert("初始化时出现错误！！！！");
  }
}

function initCardPrintQueryGrid()
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
    iArray[1][0]="印刷批次号";
    iArray[1][1]="90px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="计划类型";         			//列名
    iArray[2][1]="50px";            		//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=2;
    iArray[2][10]="PlanType";						//批复状态
    iArray[2][11]="0|^0|预算外^1|一季度^2|二季度^3|三季度^4|四季度^5|新/改版^6|自印单证";

    iArray[3]=new Array();
    iArray[3][0]="单证编码";         	//列名
    iArray[3][1]="60px";            	//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="单证名称";         			//列名
    iArray[4][1]="120px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="印刷总量";         			//列名
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="起始号码";         			//列名
    iArray[6][1]="150px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=2;                         //是否允许输入,1表示允许，0表示不允许
               			
    iArray[7]=new Array();
    iArray[7][0]="终止号码";         			//列名
    iArray[7][1]="150px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="印刷单价";         			//列名
    iArray[8][1]="60px";            		//列宽
    iArray[8][2]=60;            			//列最大值
    iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="印刷厂名称";         			//列名
    iArray[9][1]="120px";            		//列宽
    iArray[9][2]=60;            			//列最大值
    iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="印刷厂电话";         			//列名
    iArray[10][1]="80px";            		//列宽
    iArray[10][2]=60;            			//列最大值
    iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="印刷厂联系人";         			//列名
    iArray[11][1]="80px";            		//列宽
    iArray[11][2]=60;            			//列最大值
    iArray[11][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[12]=new Array();
    iArray[12][0]="使用截止日期";         			//列名
    iArray[12][1]="80px";            		//列宽
    iArray[12][2]=60;            			//列最大值
    iArray[12][3]=2;              			//是否允许输入,1表示允许，0表示不允许    
        
    CardPrintQueryGrid = new MulLineEnter( "document" , "CardPrintQueryGrid" );
    CardPrintQueryGrid.mulLineCount = 5;
    CardPrintQueryGrid.displayTitle = 1;
    CardPrintQueryGrid.canSel=1;
    CardPrintQueryGrid.canChk=1;
    CardPrintQueryGrid.hiddenPlus=1;
	CardPrintQueryGrid.hiddenSubtraction=1;
    CardPrintQueryGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("初始化查询结果列表时出错"+ex);
  }
}

</script>


