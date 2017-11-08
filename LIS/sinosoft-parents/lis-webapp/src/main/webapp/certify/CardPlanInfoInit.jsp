
<%
	//**************************************************************************************************
	//程序名称：CardPlanInfoInit.jsp
	//程序功能：单证计划查询
	//创建日期： 2009-02-19
	//创建人  ： mw
	//更新记录：  更新人    更新日期     更新原因/内容
	//**************************************************************************************************
%>
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
    document.all('ManageCom').value = "<%= managecom %>";
    //document.all('PlanID').value = '';
    document.all('Grade').value = '';
    
    document.all('PlanType').value = '';
    document.all('PlanTypeName').value = '';   
     
    document.all('PlanState').value = '';
    document.all('PlanStateName').value = '';

    document.all('CertifyClass').value = '';
    document.all('CertifyClassName').value = '';
    
    document.all('CertifyClass2').value = '';
    document.all('CertifyClass2Name').value = '';
                
    document.all('MakeDateB').value = "<%= strCurTime %>";      
    document.all('MakeDateE').value = "<%= strCurTime %>";
  }
  catch(ex)
  {
    alert("进行初始化时出现错误！");
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
    iArray[4][0]="申请日期";         			//列名
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
    alert("初始化查询结果列表时出错"+ex);
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
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="计划标识";
    iArray[1][1]="120px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="申请机构";         			//列名
    iArray[2][1]="50px";            		//列宽
    iArray[2][2]=60;            			//列最大值
    iArray[2][3]=0;   						//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="计划类型";         			//列名
    iArray[3][1]="50px";            		//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="单证代码";         			//列名
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="单证名称";         			//列名
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="单证单价";         			//列名
    iArray[6][1]="60px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="申请数量";         			//列名
    iArray[7][1]="50px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="调整数量";         			//列名
    iArray[8][1]="50px";            		//列宽
    iArray[8][2]=60;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="批复数量";         			//列名
    iArray[9][1]="50px";            		//列宽
    iArray[9][2]=60;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="库存分配数量";         			//列名
    iArray[10][1]="75px";            		//列宽
    iArray[10][2]=75;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="印刷数量";         			//列名
    iArray[11][1]="50px";            		//列宽
    iArray[11][2]=60;            			//列最大值
    iArray[11][3]=0;   //是否允许输入,1表示允许，0表示不允许
               			
    iArray[12]=new Array();
    iArray[12][0]="总价";         			//列名
    iArray[12][1]="60px";            		//列宽
    iArray[12][2]=60;            			//列最大值
    iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[13]=new Array();
    iArray[13][0]="计划状态";         			//列名
    iArray[13][1]="60px";            		//列宽
    iArray[13][2]=60;            			//列最大值
    iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
    iArray[14]=new Array();
    iArray[14][0]="申请日期";         			//列名
    iArray[14][1]="80px";            		//列宽
    iArray[14][2]=60;            			//列最大值
    iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[15]=new Array();
    iArray[15][0]="操作日期";         			//列名
    iArray[15][1]="80px";            		//列宽
    iArray[15][2]=60;            			//列最大值
    iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    CardPlanDetailGrid = new MulLineEnter( "document" , "CardPlanDetailGrid" );
    CardPlanDetailGrid.mulLineCount = 5;
    CardPlanDetailGrid.displayTitle = 1;
    CardPlanDetailGrid.canSel=1;
    CardPlanDetailGrid.canChk=0;
    CardPlanDetailGrid.hiddenPlus=1;
	CardPlanDetailGrid.hiddenSubtraction=1;	
    CardPlanDetailGrid.loadMulLine(iArray);
    CardPlanDetailGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert("初始化明细信息时出错"+ex);
  }
}

</script>
