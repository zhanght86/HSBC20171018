<%
//程序名称：LLColQueryClaimUWMDetailInit.jsp
//程序功能：案件核赔履历表--查询
//创建日期：2005-05-10
//创建人  ：
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
    	//alert(1);
        initParam();
        initLLClaimUWMDetailGrid();
        queryLLClaimUWMDetailGrid();
    }
    catch(re)
    {
        alert("LLColQueryClaimUWMDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
// 案件核赔履历表--初始化
function initLLClaimUWMDetailGrid()
{                               
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                //列宽
        iArray[0][2]=10;                  //列最大值
        iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="赔案号";             //列名
        iArray[1][1]="80px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="核赔次数";             //列名
        iArray[2][1]="80px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[3]=new Array();
        iArray[3][0]="核赔员";             //列名
        iArray[3][1]="60px";                //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="管理机构";             //列名
        iArray[4][1]="100px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="入机日期";             //列名
        iArray[5][1]="100px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0; 

        iArray[6]=new Array();
        iArray[6][0]="dddd";             //列名
        iArray[6][1]="80px";                //列宽
        iArray[6][2]=200;                  //列最大值
        iArray[6][3]=3;                    //是否允许输入,1表示允许，0表示不允许

                               
        LLClaimUWMDetailGrid = new MulLineEnter( "document" , "LLClaimUWMDetailGrid" ); 
        //这些属性必须在loadMulLine前
        LLClaimUWMDetailGrid.mulLineCount = 5;   
        LLClaimUWMDetailGrid.displayTitle = 1;
        LLClaimUWMDetailGrid.locked = 1;
        LLClaimUWMDetailGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLClaimUWMDetailGrid.selBoxEventFuncName = "LLClaimUWMDetailGridClick"; //点击RadioBox时响应的函数名
        LLClaimUWMDetailGrid.hiddenPlus=1;
        LLClaimUWMDetailGrid.hiddenSubtraction=1;
        LLClaimUWMDetailGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
