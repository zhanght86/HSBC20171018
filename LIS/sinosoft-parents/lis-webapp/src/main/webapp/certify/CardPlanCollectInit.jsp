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
    initCardPlanQueryGrid2();
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
    fm.InputMan.value = '<%= strOperator %>';
    fm.InputDate.value = '<%= strCurTime %>';

    document.all('PlanType').value = '';
    document.all('PlanTypeName').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
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
    iArray[1][0]="单证编码";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="单证名称";         			//列名
    iArray[2][1]="120px";            		//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="印刷单价";         	//列名
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=2;              			//2表示是代码选择
	iArray[3][9]="印刷单价|NOTNULL&NUM"; //检验格式：按区站代码格式检验
	iArray[3][22]="calcSumNum";

    iArray[4]=new Array();
    iArray[4][0]="分公司合计印刷量";         			//列名
    iArray[4][1]="80px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="总公司印刷量";         			//列名
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[5][9]="总公司印刷量|NOTNULL&INT"; //检验格式：按区站代码格式检验
    iArray[5][22]="calcSumNum";
          
    iArray[6]=new Array();
    iArray[6][0]="印刷量汇总";         			//列名
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="印刷合计金额";         			//列名
    iArray[7][1]="80px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    CardPlanQueryGrid = new MulLineEnter( "document" , "CardPlanQueryGrid" );
    CardPlanQueryGrid.mulLineCount = 0;
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

function initCardPlanQueryGrid2()
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
    iArray[1][0]="单证编码";
    iArray[1][1]="60px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="单证名称";         			//列名
    iArray[2][1]="120px";            		//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="印刷单价";         	//列名
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=2;              			//2表示是代码选择
	iArray[3][9]="印刷单价|NOTNULL&NUM"; //检验格式：按区站代码格式检验
	iArray[3][22]="calcSumNum";

    iArray[4]=new Array();
    iArray[4][0]="分公司印刷量";         			//列名
    iArray[4][1]="80px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="总公司印刷量";         			//列名
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[5][9]="总公司印刷量|NOTNULL&INT"; //检验格式：按区站代码格式检验
    iArray[5][22]="calcSumNum";
          
    iArray[6]=new Array();
    iArray[6][0]="印刷量汇总";         			//列名
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="印刷合计金额";         			//列名
    iArray[7][1]="80px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="分公司编码";         			//列名
    iArray[8][1]="80px";            		//列宽
    iArray[8][2]=60;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
    iArray[9]=new Array();
    iArray[9][0]="起始号";         			//列名
    iArray[9][1]="120px";            		//列宽
    iArray[9][2]=60;            			//列最大值
    iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[9][9]="起始号|NUM";
    
    iArray[10]=new Array();
    iArray[10][0]="终止号";         			//列名
    iArray[10][1]="120px";            		//列宽
    iArray[10][2]=60;            			//列最大值
    iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[10][9]="终止号|NUM";
    
    CardPlanQueryGrid2 = new MulLineEnter( "document" , "CardPlanQueryGrid2" );
    CardPlanQueryGrid2.mulLineCount = 0;
    CardPlanQueryGrid2.displayTitle = 1;
    CardPlanQueryGrid2.canSel=1;
    CardPlanQueryGrid2.canChk=1;
    CardPlanQueryGrid2.hiddenPlus=1;
	CardPlanQueryGrid2.hiddenSubtraction=1;
    CardPlanQueryGrid2.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("初始化库存分配信息时出错"+ex);
  }
}
</script>


