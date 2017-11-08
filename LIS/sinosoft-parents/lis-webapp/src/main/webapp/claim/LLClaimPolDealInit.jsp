<%
//**************************************************************************************************
//Name：
//Function：
//Author：
//Date: 
//**************************************************************************************************
%>
<html>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
</html>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收页面传递过来的参数
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
}

/**=========================================================================
    Form载入页面时进行初始化
   =========================================================================
*/
function initForm()
{
    try
    {
        initParam();
        initInpBox();                   //初始化表格
        initPolCalResultGrid()         //计算结果
        initQuery()                     //初始查询        
    }
    catch(re)
    {
        alert("LLClaimPolDealInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
        alert("LLClaimPolDealInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

//保单结算的结果
function initPolCalResultGrid()
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
        iArray[1][0]="业务类型";
        iArray[1][1]="40px";
        iArray[1][2]=40;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="业务类型名称";
        iArray[2][1]="150px";
        iArray[2][2]=150;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="子业务类型";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="子业务类型名称";
        iArray[4][1]="150px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="合同号码";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;
                
       iArray[6]=new Array();
        iArray[6][0]="保单号码";
        iArray[6][1]="120px";
        iArray[6][2]=80;
        iArray[6][3]=0; 
                              
        iArray[7]=new Array();
        iArray[7][0]="险种编码";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="给付日期";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
      iArray[9][0]="币种";      	   		//列名
      iArray[9][1]="80px";            			//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[10]=new Array();
        iArray[10][0]="计算金额";
        iArray[10][1]="80px";
        iArray[10][2]=80;
        iArray[10][3]=0;
                                                                        
        PolCalResultGrid = new MulLineEnter("fm","PolCalResultGrid");
        PolCalResultGrid.mulLineCount = 0;
        PolCalResultGrid.displayTitle = 1;
        PolCalResultGrid.locked = 0;
//      PolCalResultGrid.canChk =1;              //多选按钮，1显示，0隐藏
        PolCalResultGrid.canSel =1;              //单选按钮，1显示，0隐藏
        PolCalResultGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        PolCalResultGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
//        PolCalResultGrid.selBoxEventFuncName = "getContReferGrid"; //函数名称
//        PolCalResultGrid.selBoxEventFuncParm ="ContDeal";          //参数        
        PolCalResultGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alert("LLClaimPolDealInit.jsp-->initPolCalResultGrid函数中发生异常:初始化界面错误!");
    }
}

function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

</script>
