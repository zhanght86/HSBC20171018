<%
//Name:LCInsureAccontInit.jsp
//Function：“帐户处理”初始化 
//author: 
//Date:
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
	
//接收报案页面传递过来的参数
function initParam()
{
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initLCInsureAccClassGrid(); //	保险账户分类表
    initLCInsureAccTraceGrid(); //保险帐户表记价履历表
    LCInsureAccClassGridQuery(); //查询初始化页面上部的保险账户分类表

  }
  catch(re)
  {
    alert("在LCInsureAccTraceGrid-->InitForm函数中发生异常:初始化界面错误!");
  }
}


//页面初始化
function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("在LCInsureAccontInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

//	保险账户分类表
function initLCInsureAccClassGrid()
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
        iArray[1][0]="保单号码"; //
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="保险帐户号码"; //InsuAccNo
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="交费计划编码";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
    
        iArray[4]=new Array();
        iArray[4][0]="对应其它号码";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
    
        iArray[5]=new Array()
        iArray[5][0]="对应其它号码类型";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="账户归属属性";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=0;
    
        iArray[7]=new Array();
        iArray[7][0]="被保人客户号码";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
    
        iArray[8]=new Array();
        iArray[8][0]="账户类型";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="结算方式";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=0;
    
        iArray[10]=new Array();
        iArray[10][0]="成立日期";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=0;

        LCInsureAccClassGrid = new MulLineEnter("fm","LCInsureAccClassGrid");
        LCInsureAccClassGrid.mulLineCount = 1;
        LCInsureAccClassGrid.displayTitle = 1;
        LCInsureAccClassGrid.locked = 0;
//        LCInsureAccClassGrid.canChk =1;
        LCInsureAccClassGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LCInsureAccClassGrid.selBoxEventFuncName ="LCInsureAccClassGridClick"; //响应的函数名，不加扩号
        LCInsureAccClassGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项  
        LCInsureAccClassGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LCInsureAccClassGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        LCInsureAccClassGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}


//保险帐户表记价履历表
function initLCInsureAccTraceGrid()
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
        iArray[1][0]="保单险种号码";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="保险帐户号码";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="流水号";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="金额类型";      //也就是理算金额
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="本次金额";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="本次单位数";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="交费日期";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="状态";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        

        LCInsureAccTraceGrid = new MulLineEnter("fm","LCInsureAccTraceGrid");
        LCInsureAccTraceGrid.mulLineCount = 1;       // 显示行数
        LCInsureAccTraceGrid.displayTitle = 1;
        LCInsureAccTraceGrid.locked = 0;
//        LCInsureAccTraceGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
        LCInsureAccTraceGrid.canSel =0;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
        LCInsureAccTraceGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LCInsureAccTraceGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        LCInsureAccTraceGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
