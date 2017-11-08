<%
//Name:LLClaimImportModifyInit.jsp
//function：
//author: quyang
//Date: 2005-06-22
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
    
var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";

//接收报案页面传递过来的参数
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");  
    mNowYear = mCurrentDate.substring(0,4);
    
    document.all('ClmNo').value = nullToEmpty("<%= tRptNo %>");
    document.all('customerNo').value = nullToEmpty("<%= tCustomerNo %>");
    
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
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
  //try
  //{
    initParam();
    initInpBox();
    initQuery();
    initHisEditReasonGrid();
  //}
  //catch(re)
  //{
    //alert("在LLClaimImportModifyInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  //}
}

/**
	页面初始化
*/
function initInpBox()
{
  try
  {
  
  
  }
  catch(ex)
  {
    alert("在LLClaimImportModifyInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

/**=========================================================================
    住院录入信息
   =========================================================================
*/
function initHisEditReasonGrid()
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
        iArray[1][0]="操作员";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="修改时间";
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="修改原因";
        iArray[3][1]="300px";
        iArray[3][2]=300;
        iArray[3][3]=0;
        
        
                                   
        HisEditReasonGrid = new MulLineEnter("document","HisEditReasonGrid");
        HisEditReasonGrid.mulLineCount = 5;
        HisEditReasonGrid.displayTitle = 1;
        HisEditReasonGrid.locked = 0;
//      HisEditReasonGrid.canChk =1;              //多选按钮，1显示，0隐藏
        HisEditReasonGrid.canSel =1;              //单选按钮，1显示，0隐藏
        HisEditReasonGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        HisEditReasonGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
//        HisEditReasonGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //函数名称
//        HisEditReasonGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        HisEditReasonGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
