<%
//程序名称：LLParaPupilAmntInit.jsp
//程序功能：未成年人保额标准信息维护
//创建日期：2005-9-15
//创建人  ：zhangyang
//更新记录：  更新人 zhangyang    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
//接收参数
function initParam()
{
   document.all('tOperator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   document.all('tComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
        initLLParaPupilAmntGrid();                  
    }
    catch(re)
    {
        alter("LLParaPupilAmntInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}


function initInpBox()
{
  try
  {
  	fm.ComCodeQ.value ="";
    fm.ComCodeNameQ.value ="";
<!--    fm.UpComCode.value ="";-->
    fm.BaseValue.value ="";
    fm.StartDate.value = "";
    fm.EndDate.value ="";  
  }
  catch(ex)
  {
    alter("LLParaPupilAmntInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


function initLLParaPupilAmntGrid()
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
        iArray[1][0]="管理机构代码";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="管理机构名称";
        iArray[2][1]="120px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="上级机构";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=3;        
        
        iArray[4]=new Array();
        iArray[4][0]="标准保额";
        iArray[4][1]="60px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="启用日期";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="结束时间";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                        

          
        

        LLParaPupilAmntGrid = new MulLineEnter("document","LLParaPupilAmntGrid");
        LLParaPupilAmntGrid.mulLineCount = 5;
        LLParaPupilAmntGrid.displayTitle = 1;
        LLParaPupilAmntGrid.locked = 0;
        LLParaPupilAmntGrid.canSel =1;              //单选按钮，1显示，0隐藏
        LLParaPupilAmntGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        LLParaPupilAmntGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        LLParaPupilAmntGrid.selBoxEventFuncName = "LLParaPupilAmntGridClick"; //函数名称
        LLParaPupilAmntGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

