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
 %>
<script type="text/javascript">

function initInpBox()
{
	try
	{
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
		initCatastropheGrid();
	}
	catch(re)
	{
		myAlert("3CertifyDescInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

function initCatastropheGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="保险金额";    //列名
    iArray[1][1]="50px";            	//列宽
    iArray[1][2]=200;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="生存人数";
    iArray[2][1]="50px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="风险总额";     //列名
    iArray[3][1]="50px";            	//列宽
    iArray[3][2]=200;            //列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="自留额";     //列名
    iArray[4][1]="50px";            	//列宽
    iArray[4][2]=200;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
       
    CatastropheGrid = new MulLineEnter( "fm" , "CatastropheGrid" );
    CatastropheGrid.mulLineCount =0;
    CatastropheGrid.displayTitle = 1;
    CatastropheGrid.hiddenPlus=1;   
    CatastropheGrid.hiddenSubtraction=1; 
    //CatastropheGrid.canSel=1;
    CatastropheGrid.loadMulLine(iArray);
    CatastropheGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    myAlert("初始化时出错:"+ex);
  }
}

</script>


