<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
//接收参数
function initParam()
{
   document.all('tOperator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   document.all('tComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
   document.all('HosCode').value = nullToEmpty("<%= tCode %>");
   document.all('HosName').value = nullToEmpty("<%= tName %>");
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
        initInpBox();
        initParam();
        initLLColQueryAccDetailGrid();
    }
    catch(re)
    {
        alter("LLColQueryAccDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}


function initInpBox()
{
  try
  {
  }
  catch(ex)
  {
    alter("LLColQueryAccDetailInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


function initLLColQueryAccDetailGrid()
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
        iArray[1][0]="意外细节代码";
        iArray[1][1]="80px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="意外细节名称";
        iArray[2][1]="450px";
        iArray[2][2]=10;
        iArray[2][3]=0;

//        iArray[3]=new Array();
//        iArray[3][0]="医院名称";
//        iArray[3][1]="120px";
//        iArray[3][2]=10;
//        iArray[3][3]=0;
//
//        iArray[4]=new Array();
//        iArray[4][0]="医院等级";
//        iArray[4][1]="60px";
//        iArray[4][2]=10;
//        iArray[4][3]=0;
//
//        iArray[5]=new Array();
//        iArray[5][0]="定点标志";
//        iArray[5][1]="60px";
//        iArray[5][2]=10;
//        iArray[5][3]=0;
//
//        iArray[6]=new Array();
//        iArray[6][0]="残疾鉴定资质标志";
//        iArray[6][1]="60px";
//        iArray[6][2]=10;
//        iArray[6][3]=0;
//
//
//        iArray[7]=new Array();
//        iArray[7][0]="医院状态";
//        iArray[7][1]="60px";
//        iArray[7][2]=10;
//        iArray[7][3]=0;
//
//        iArray[8]=new Array();
//        iArray[8][0]="管理机构";
//        iArray[8][1]="60px";
//        iArray[8][2]=10;
//        iArray[8][3]=0;

        LLColQueryAccDetailGrid = new MulLineEnter("fm","LLColQueryAccDetailGrid");
        LLColQueryAccDetailGrid.mulLineCount = 0;
        LLColQueryAccDetailGrid.displayTitle = 1;
        LLColQueryAccDetailGrid.locked = 0;
        LLColQueryAccDetailGrid.canSel =1;              //单选按钮，1显示，0隐藏
        LLColQueryAccDetailGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        LLColQueryAccDetailGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
//        LLColQueryAccDetailGrid.selBoxEventFuncName = "LLColQueryAccDetailGridClick"; //函数名称
        LLColQueryAccDetailGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>

