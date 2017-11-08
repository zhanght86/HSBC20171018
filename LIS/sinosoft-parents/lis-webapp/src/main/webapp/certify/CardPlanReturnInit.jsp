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
    initCardPlanDetailGrid();
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
    fm.RetOperator.value = '<%= strOperator %>';
    fm.MakeDate.value = '<%= strCurTime %>';
    
    document.all('CertifyCode').value = '';
    document.all('CertifyName').value = '';
    document.all('PlanType').value = '';
    document.all('PlanTypeName').value = '';
    
    if(fm.managecom.value=='86'){
      fm.button2.disabled=true;
    }
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
    iArray[1][1]="50px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="计划类型";         			//列名
    iArray[2][1]="50px";            		//列宽
    iArray[2][2]=60;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="申请人";         			//列名
    iArray[3][1]="50px";            		//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="申请时间";         			//列名
    iArray[4][1]="50px";            		//列宽
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

function initCardPlanDetailGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=30;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="计划标识";
    iArray[1][1]="60px";
    iArray[1][2]=60;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="申请机构";         			//列名
    iArray[2][1]="60px";            		//列宽
    iArray[2][2]=60;            			//列最大值
    iArray[2][3]=0;   						//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="计划类型";         			//列名
    iArray[3][1]="60px";            		//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="单证代码";         			//列名
    iArray[4][1]="60px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="单证名称";         			//列名
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=80;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="单证单价";         			//列名
    iArray[6][1]="60px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="申请数量";         			//列名
    iArray[7][1]="60px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="调整数量";         			//列名
    iArray[8][1]="60px";            		//列宽
    iArray[8][2]=60;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="批复数量";         			//列名
    iArray[9][1]="60px";            		//列宽
    iArray[9][2]=60;            			//列最大值
    iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="总价";         			//列名
    iArray[10][1]="60px";            		//列宽
    iArray[10][2]=60;            			//列最大值
    iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    
    CardPlanDetailGrid = new MulLineEnter( "document" , "CardPlanDetailGrid" );
    CardPlanDetailGrid.mulLineCount = 5;
    CardPlanDetailGrid.displayTitle = 1;
    CardPlanDetailGrid.canSel=1;
    CardPlanDetailGrid.canChk=1;
    CardPlanDetailGrid.hiddenPlus=1;
	CardPlanDetailGrid.hiddenSubtraction=1;	
    CardPlanDetailGrid.loadMulLine(iArray);
    CardPlanDetailGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert("初始化定额单险种信息时出错"+ex);
  }
}

</script>


