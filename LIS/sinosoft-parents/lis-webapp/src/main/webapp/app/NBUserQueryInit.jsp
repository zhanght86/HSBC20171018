<%
//页面名称: NBUserQueryInput.jsp
//页面功能：用户查询页面
//建立人：chenrong
//建立日期：2006.02.22
//修改原因 内容：
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= tGI.Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= tGI.ComCode %>");
   document.all('MngCom').value = nullToEmpty("<%= tGI.ManageCom %>");
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
        initNBUserGrid();
    }
    catch(re)
    {
        alert("NBUserQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//理赔用户列表
function initNBUserGrid()
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
        iArray[1][0]="用户代码";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="用户姓名";
        iArray[2][1]="60px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="机构代码";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        NBUserGrid = new MulLineEnter("fm","NBUserGrid");
        NBUserGrid.mulLineCount = 0;
        NBUserGrid.displayTitle = 1;
        NBUserGrid.locked = 0;
        NBUserGrid.canSel =1;              //单选按钮，1显示，0隐藏
        NBUserGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        NBUserGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        NBUserGrid.selBoxEventFuncName = "NBUserGridClick"; //函数名称
        NBUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}
</script>
