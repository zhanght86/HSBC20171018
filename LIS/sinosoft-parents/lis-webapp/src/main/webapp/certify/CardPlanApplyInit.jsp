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
    initCardPlanGrid();
    initCardPlanQueryGrid();
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
    
    document.all('PlanID').value = '';
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
    //document.all('PlanState').value = '';
  }
  catch(ex)
  {
    alert("进行初始化是出现错误！！！！");
  }
}

function RegisterDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divDetailInfo.style.left=ex;
  	divDetailInfo.style.top =ey;
    divDetailInfo.style.display ='';
}

function initCardPlanGrid()
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
    iArray[1][0]="单证编码";     		//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[1][1]="80px";        			//列宽
    iArray[1][2]=80;          			//列最大值
    iArray[1][3]=2;              		//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="CertifyCode";     //是否引用代码:null||""为不引用
    iArray[1][5]="1|2|3";             //引用代码对应第几列，'|'为分割符
    iArray[1][6]="0|1|2";             //上面的列中放置引用代码中第几位值
    iArray[1][9]="单证编码|code:CertifyCode&NOTNULL";

    iArray[2]=new Array();
    iArray[2][0]="单证名称";     		//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[2][1]="180px";        		  //列宽
    iArray[2][2]=180;          			//列最大值
    iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="单证类型";     		//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[3][1]="100px";        		  //列宽
    iArray[3][2]=180;          			//列最大值
    iArray[3][3]=2;              			//2表示是代码选择
    iArray[3][10]="CertifyClass";						//批复状态
    iArray[3][11]="0|^D|重要有价单证^B|重要空白单证^P|普通单证";
    
    iArray[4]=new Array();
    iArray[4][0]="申请数量";         			//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
    iArray[4][9]="申请数量|NOTNULL&INT&value>0"; //检验格式
    
    CardPlanGrid = new MulLineEnter( "document" , "CardPlanGrid" );
    CardPlanGrid.mulLineCount = 5;
	CardPlanGrid.canSel = 1;
    CardPlanGrid.displayTitle = 1;
    CardPlanGrid.loadMulLine(iArray);
    CardPlanGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert("初始化定额单险种信息时出错"+ex);
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
    iArray[1][1]="120px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="单证编码";         			//列名
    iArray[2][1]="60px";            		//列宽
    iArray[2][2]=200;            			//列最大值
    iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
    iArray[2][4]="CertifyCode";     //是否引用代码:null||""为不引用
    //iArray[2][5]="2|3";             //引用代码对应第几列，'|'为分割符
    //iArray[2][6]="0|1";             //上面的列中放置引用代码中第几位值
    iArray[2][9]="单证编码|code:CertifyCode&NOTNULL";
    
    iArray[3]=new Array();
    iArray[3][0]="计划类型";         	//列名
    iArray[3][1]="60px";            	//列宽
    iArray[3][2]=60;            			//列最大值
    iArray[3][3]=2;              			//2表示是代码选择
    iArray[3][10]="PlanType";						//批复状态
    iArray[3][11]="0|^0|预算外^1|一季度^2|二季度^3|三季度^4|四季度^5|新/改版^6|自印单证";

    iArray[4]=new Array();
    iArray[4][0]="单证单价";         			//列名
    iArray[4][1]="60px";            		//列宽
    iArray[4][2]=60;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="申请数量";         			//列名
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=60;            			//列最大值
    iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="总价";         			//列名
    iArray[6][1]="60px";            		//列宽
    iArray[6][2]=60;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="申请人";         			//列名
    iArray[7][1]="60px";            		//列宽
    iArray[7][2]=60;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="申请机构";         			//列名
    iArray[8][1]="60px";            		//列宽
    iArray[8][2]=60;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="计划状态";         			//列名
    iArray[9][1]="60px";            		//列宽
    iArray[9][2]=60;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="入机日期";         			//列名
    iArray[10][1]="60px";            		//列宽
    iArray[10][2]=60;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
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
    alert("初始化单证计划信息时出错"+ex);
  }
}

</script>


