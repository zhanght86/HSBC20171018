<%
//程序名称：LLSubReportDescInputInit.jsp
//程序功能：事故描述信息页面初始化
//创建日期：2005-05-10
//创建人  ：zhoulei
//更新记录：
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{ 
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('WhoNo').value	= nullToEmpty("<%= tCustomerNo %>");
    document.all('StartPhase').value	= nullToEmpty("<%= tStartPhase %>");
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

function initInpBox()
{
    try
    {
//        fm.DescType.value = "";
        fm.Describe.value = "";
    }
    catch(ex)
    {
        alert("在LLSubReportDescInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initSelBox()
{
    try
    {

    }
    catch(ex)
    {
        alert("在LLSubReportDescInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initSelBox();
        initLLSubReportDescGrid();
        addMulline();
    }
    catch(re)
    {
        alert("LLSubReportDescInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
// 调查结论表的初始化
function initLLSubReportDescGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";              //列宽
        iArray[0][2]=10;                  //列最大值
        iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="立案号";             //列名
        iArray[1][1]="160px";                //列宽
        iArray[1][2]=160;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="客户编码";             //列名
        iArray[2][1]="100px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[3]=new Array();
        iArray[3][0]="描述序号";             //列名
        iArray[3][1]="100px";                //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

//      iArray[3]=new Array();
//      iArray[3][0]="性别";             //列名
//      iArray[3][1]="60px";                //列宽
//      iArray[3][2]=200;                  //列最大值
//      iArray[3][3]=2;                    //是否允许输入,1表示允许，0表示不允许
//      iArray[3][10] = "Sex";
//      iArray[3][11] = "0|^0|男^1|女^2|不详";
//      iArray[3][12] = "3";
//      iArray[3][19] = "0";

        iArray[4]=new Array();
        iArray[4][0]="描述类型";             //列名
        iArray[4][1]="100px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="描述内容";             //列名
        iArray[5][1]="150px";                //列宽
        iArray[5][2]=200;                  //列最大值
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="操作员";             //列名
        iArray[6][1]="80px";                //列宽
        iArray[6][2]=200;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="备注时间";             //列名
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=100;                  //列最大值
        iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="入机时间";             //列名
        iArray[8][1]="100px";                //列宽
        iArray[8][2]=100;                  //列最大值
        iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

//      iArray[9]=new Array();
//      iArray[9][0]="";             //列名
//      iArray[9][1]="0px";                //列宽
//      iArray[9][2]=100;                  //列最大值
//      iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        LLSubReportDescGrid = new MulLineEnter( "document" , "LLSubReportDescGrid" );
        //这些属性必须在loadMulLine前
        LLSubReportDescGrid.mulLineCount = 5;
        LLSubReportDescGrid.displayTitle = 1;
        LLSubReportDescGrid.locked = 1;
        LLSubReportDescGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLSubReportDescGrid.selBoxEventFuncName ="LLSubReportDescGridClick"; //响应的函数名，不加扩号
//        LLSubReportDescGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项        
        LLSubReportDescGrid.hiddenPlus=1;
        LLSubReportDescGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        LLSubReportDescGrid.loadMulLine(iArray);
        }
        catch(ex)
        {
            alert(ex);
        }
}

</script>
