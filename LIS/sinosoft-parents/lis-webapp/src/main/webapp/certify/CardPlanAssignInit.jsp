
<%
	//Creator :刘岩松
	//Date :2003-04-18
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
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
    initCardPlanListGrid();
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
    fm.AppOperator.value = '<%= strOperator %>';
    fm.MakeDate.value = '<%= strCurTime %>';
    
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
  }
  catch(ex)
  {
    alert("进行初始化是出现错误！！！！");
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
    iArray[1][0]="计划标识";
    iArray[1][1]="80px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="分公司代码";         			//列名
    iArray[2][1]="60px";            		//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="分公司名称";         	//列名
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              			//2表示是代码选择

    iArray[4]=new Array();
    iArray[4][0]="申请批复数量";         			//列名
    iArray[4][1]="60px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="库存分配数量";         			//列名
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[5][9]="库存分配数量|NOTNULL&NUM&value>=0";  //检验格式
    
    CardPlanQueryGrid = new MulLineEnter( "document" , "CardPlanQueryGrid" );
    CardPlanQueryGrid.mulLineCount = 5;
    CardPlanQueryGrid.displayTitle = 1;
    CardPlanQueryGrid.canSel=1;
    CardPlanQueryGrid.canChk=1;
    CardPlanQueryGrid.hiddenPlus=1;
	CardPlanQueryGrid.hiddenSubtraction=1;
    CardPlanQueryGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("初始化库存分配信息时出错"+ex);
  }
}

function initCardPlanListGrid()
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
    iArray[1][0]="分公司代码";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="分公司名称";         			//列名
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="单证编码";         	//列名
    iArray[3][1]="60px";            	//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              			//2表示是代码选择

    iArray[4]=new Array();
    iArray[4][0]="单证名称";         			//列名
    iArray[4][1]="80px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="总公司库存";         			//列名
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="库存分配数量";         			//列名
    iArray[6][1]="60px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="印刷厂名称";         			//列名
    iArray[7][1]="60px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    CardPlanListGrid = new MulLineEnter( "document" , "CardPlanListGrid" );
    CardPlanListGrid.mulLineCount = 5;
    CardPlanListGrid.displayTitle = 1;
    CardPlanListGrid.canSel=1;
    CardPlanListGrid.canChk=0;
    CardPlanListGrid.hiddenPlus=1;
	CardPlanListGrid.hiddenSubtraction=1;
    CardPlanListGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("初始化库存分配信息时出错"+ex);
  }
}
</script>


