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
    initCardPlanQueryGrid();
    initCardPlanQueryDetailGrid();
    initCardPlanPackGrid();
  }
  catch(re)
  {
    alert("CertifyDescInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{
  try
  {       
    fm.managecom.value = '<%= managecom %>'; 
    fm.ConOperator.value = '<%= strOperator %>';
    fm.MakeDate.value = '<%= strCurTime %>';
  }
  catch(ex)
  {
    alert("进行初始化时出现错误！！！！");
  }
}

function initCardPlanQueryGrid()
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
    iArray[1][0]="申请机构";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="计划类型";         			//列名
    iArray[2][1]="60px";            		//列宽
    iArray[2][2]=60;            			//列最大值
    iArray[2][3]=2;
    iArray[2][10]="PlanType";						//批复状态
    iArray[2][11]="0|^0|预算外^1|一季度^2|二季度^3|三季度^4|四季度^5|新/改版^6|自印单证";    

    iArray[3]=new Array();
    iArray[3][0]="申请人";         			//列名
    iArray[3][1]="60px";            		//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="操作日期";         			//列名
    iArray[4][1]="60px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    CardPlanQueryGrid = new MulLineEnter( "document" , "CardPlanQueryGrid" );
    CardPlanQueryGrid.mulLineCount = 5;
    CardPlanQueryGrid.displayTitle = 1;
    CardPlanQueryGrid.canSel=1;
    CardPlanQueryGrid.hiddenPlus=1;
	CardPlanQueryGrid.hiddenSubtraction=1;
	CardPlanQueryGrid.selBoxEventFuncName = "showDetail";
    CardPlanQueryGrid.loadMulLine(iArray);
    CardPlanQueryGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert("初始化定额单险种信息时出错"+ex);
  }
}

function initCardPlanQueryDetailGrid()
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
    iArray[1][0]="计划标识";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="单证类型";         			//列名
    iArray[2][1]="60px";            		//列宽
    iArray[2][2]=60;            			//列最大值
    iArray[2][3]=2;
    iArray[2][10]="CertifyClass";						//批复状态
    iArray[2][11]="0|^D|重要有价单证^B|重要空白单证^P|普通单证";  

    iArray[3]=new Array();
    iArray[3][0]="单证编码";         			//列名
    iArray[3][1]="60px";            		//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="单证名称";         			//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=80;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="单证单价";         			//列名
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="申请数量";         			//列名
    iArray[6][1]="60px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="调整数量";         			//列名
    iArray[7][1]="60px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[7][9]="调整数量|NOTNULL&INT&value>=0";  //检验格式

    iArray[8]=new Array();
    iArray[8][0]="总价";         			//列名
    iArray[8][1]="60px";            		//列宽
    iArray[8][2]=60;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
    CardPlanQueryDetailGrid = new MulLineEnter( "document" , "CardPlanQueryDetailGrid" );
    CardPlanQueryDetailGrid.mulLineCount = 5;
    CardPlanQueryDetailGrid.displayTitle = 1;
	CardPlanQueryDetailGrid.canSel=1;
    CardPlanQueryDetailGrid.canChk=1;
    CardPlanQueryDetailGrid.hiddenPlus=1;
	CardPlanQueryDetailGrid.hiddenSubtraction=1;
    CardPlanQueryDetailGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("初始化定额单险种信息时出错"+ex);
  }
}

function initCardPlanPackGrid()
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
    iArray[1][0]="计划类型";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=2;
    iArray[1][10]="PlanType";						//批复状态
    iArray[1][11]="0|^0|预算外^1|一季度^2|二季度^3|三季度^4|四季度^5|新/改版^6|自印单证";    

    iArray[2]=new Array();
    iArray[2][0]="单证类型";
    iArray[2][1]="60px";
    iArray[2][2]=100;
    iArray[2][3]=2;
    iArray[2][10]="CertifyClass";						//批复状态
    iArray[2][11]="0|^D|重要有价单证^B|重要空白单证^P|普通单证";  
    
    iArray[3]=new Array();
    iArray[3][0]="单证编码";     		//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[3][1]="60";        			//列宽
    iArray[3][2]=80;          			//列最大值
    iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="单证名称";         			//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="单证单价";         			//列名
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="调整数量";         			//列名
    iArray[6][1]="60px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="总价";         			//列名
    iArray[7][1]="60px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    CardPlanPackGrid = new MulLineEnter( "document" , "CardPlanPackGrid" );
    CardPlanPackGrid.mulLineCount = 5;
    CardPlanPackGrid.displayTitle = 1;
	CardPlanPackGrid.canSel=1;
    CardPlanPackGrid.hiddenPlus=1;
	CardPlanPackGrid.hiddenSubtraction=1;	
    CardPlanPackGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("初始化定额单险种信息时出错"+ex);
  }
}
</script>


