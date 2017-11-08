<%
//页面名称: LLClaimUserQueryInit.jsp
//页面功能：理赔用户查询页面初始化
//建立人：yuejw
//建立日期：2005.07.14
//修改原因/内容：
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= tG.Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= tG.ComCode %>");
   document.all('MngCom').value = nullToEmpty("<%= tG.ManageCom %>");
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
        initLLClaimUserGrid();
    }
    catch(re)
    {
        alert("LLClaimUserQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//理赔用户列表
function initLLClaimUserGrid()
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
        
        iArray[4]=new Array();
        iArray[4][0]="报案权限";
        iArray[4][1]="60px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="立案权限";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="预付权限";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=0;  
        
		iArray[7]=new Array();
        iArray[7][0]="审核权限";
        iArray[7][1]="60px";
        iArray[7][2]=10;
        iArray[7][3]=0;
                
        iArray[8]=new Array();
        iArray[8][0]="审核级别";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=0;  
        
        iArray[9]=new Array();
        iArray[9][0]="审批权限";
        iArray[9][1]="60px";
        iArray[9][2]=10;
        iArray[9][3]=0;
                
        iArray[10]=new Array();
        iArray[10][0]="审批级别";
        iArray[10][1]="70px";
        iArray[10][2]=10;
        iArray[10][3]=0;  

        iArray[11]=new Array();
        iArray[11][0]="简易案件权限";
        iArray[11][1]="80px";
        iArray[11][2]=10;
        iArray[11][3]=0;
        
        iArray[12]=new Array();
        iArray[12][0]="结案权限";
        iArray[12][1]="80px";
        iArray[12][2]=10;
        iArray[12][3]=0;
        
        iArray[13]=new Array();
        iArray[13][0]="有效标识";
        iArray[13][1]="70px";
        iArray[13][2]=10;
        iArray[13][3]=0;          
                                
        LLClaimUserGrid = new MulLineEnter("document","LLClaimUserGrid");
        LLClaimUserGrid.mulLineCount = 5;
        LLClaimUserGrid.displayTitle = 1;
        LLClaimUserGrid.locked = 0;
        LLClaimUserGrid.canSel =1;              //单选按钮，1显示，0隐藏
        LLClaimUserGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        LLClaimUserGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        LLClaimUserGrid.selBoxEventFuncName = "LLClaimUserGridClick"; //函数名称
        LLClaimUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}
</script>
