<%
//Name：LLUserQueryInit.jsp
//Function：用户查询页面初始化
//Date：2005.07.11
//Author：quyang
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//接收前面页面传递过来的参数
function initParam()
{
   
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
        initLLQueryUserGrid();
        initQuery();
    }
    catch(re)
    {
        alert("LLClaimUserQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
//立案附件信息
function initLLQueryUserGrid()
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
        iArray[1][0]="用户编码";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="50px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="用户姓名";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[2][1]="50px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许
 
        iArray[3]=new Array();
        iArray[3][0]="机构编码";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[3][1]="50px";                //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="用户描述";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[4][1]="100px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="用户状态";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[5][1]="60px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

       

        LLQueryUserGrid = new MulLineEnter( "document" , "LLQueryUserGrid" );
        //这些属性必须在loadMulLine前
        LLQueryUserGrid.mulLineCount = 5;
        LLQueryUserGrid.displayTitle = 1;
        LLQueryUserGrid.canChk = 0;
        LLQueryUserGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLQueryUserGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLQueryUserGrid.selBoxEventFuncName = "LLQueryUserGridClick"; //点击RadioBox时响应的函数名
        LLQueryUserGrid.hiddenSubtraction=1; 
        LLQueryUserGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }
</script>
