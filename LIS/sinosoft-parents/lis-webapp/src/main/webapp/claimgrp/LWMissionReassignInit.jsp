<%
//**************************************************************************************************
//页面名称: LWMissionReassignInit.jsp
//页面功能：任务重新分配界面初始化
//建立人: yuejw    建立日期：2005-7-14   
//修改日期：  修改原因/内容：
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
    
var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";

//接收报案页面传递过来的参数
function initParam()
{
   fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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

//初始化表单
function initForm()
{
    try
    {
//    	initParam();
//    	initInpBox();
    	initLWMissionGrid();
    }
    catch(ex)
    {
        alert("在LWMissionReassignInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//页面输入域初始化
function initInpBox()
{
    try
    {
		fm.DefaultOperator.value="";
		fm.DesignateOperator.value="";	
    }
    catch(ex)
    {
        alert("在LWMissionReassignInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initLWMissionGrid()
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
        iArray[1][0]="赔案号";
        iArray[1][1]="120px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="报案状态";
        iArray[2][1]="0px";
        iArray[2][2]=10;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="出险人编码";
        iArray[3][1]="80px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="出险人姓名";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="出险人性别";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="出险日期";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                
        iArray[7]=new Array();
        iArray[7][0]="申请类型";
        iArray[7][1]="60px";
        iArray[7][2]=10;
        iArray[7][3]=0;                
//		iArray[7][4]="llrgtclass";   
//		iArray[7][18]=100; 
//		iArray[7][19]=1; 
//        iArray[7][20]=1; 

        iArray[8]=new Array();
        iArray[8][0]="操作员";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=0;     
        
        iArray[9]=new Array();
        iArray[9][0]="MissionID";
        iArray[9][1]="0px";
        iArray[9][2]=10;
        iArray[9][3]=3;     
        
        iArray[10]=new Array();
        iArray[10][0]="SubMissionID";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;                     

        iArray[11]=new Array();
        iArray[11][0]="ActivityID";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;     
                                       
        LWMissionGrid = new MulLineEnter("fm","LWMissionGrid");
        LWMissionGrid.mulLineCount = 0;
        LWMissionGrid.displayTitle = 1;
        LWMissionGrid.locked = 0;
//      LWMissionGrid.canChk =1;              //多选按钮，1显示，0隐藏
        LWMissionGrid.canSel =1;              //单选按钮，1显示，0隐藏
        LWMissionGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        LWMissionGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        LWMissionGrid.selBoxEventFuncName = "LWMissionGridClick"; //函数名称
        LWMissionGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
