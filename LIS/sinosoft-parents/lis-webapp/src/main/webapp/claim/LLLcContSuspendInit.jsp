<%
//Name：LLLcContSuspendInit.jsp
//Function：保单挂起页面初始化
//Date：
//Author：yuejinwei
//Modify : zhoulei
//Modify date : 2005-11-19 14:18
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

//接收前面页面传递过来的参数
function initParam()
{
    document.all('InsuredNo').value = nullToEmpty("<%= tInsuredNo %>");
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
    	initParam();
        initLLLcContSuspendGrid();
        initLLLcContInGrid();
        initQuery();
    }
    catch(re)
    {
        alert("LLLcContSuspendInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//赔案无关的保单信息
function initLLLcContSuspendGrid()
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
        iArray[1][0]="集体合同号码";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="150px";                //列宽
        iArray[1][2]=120;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="合同号码";           //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[2][1]="130px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许
 
        iArray[3]=new Array();
        iArray[3][0]="总单投保单号码";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[3][1]="100px";               //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="印刷号码";            //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[4][1]="130px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="总单类型";           //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[5][1]="60px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[6]=new Array();
        iArray[6][0]="家庭单类型";          //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[6][1]="60px";                //列宽
        iArray[6][2]=100;                  //列最大值
        iArray[6][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="家庭保障号";          //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=100;                  //列最大值
        iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="原保单状态码";       //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[8][1]="50px";                //列宽
        iArray[8][2]=100;                  //列最大值
        iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="保全挂起";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[9][1]="80px";                //列宽
        iArray[9][2]=100;                  //列最大值
        iArray[9][3]=2;                    //是否允许输入,1表示允许，0表示不允许
        iArray[9][10]="YesOrNo1";          //引用代码："CodeName"为传入数据的名称
        iArray[9][11]="0|^1|是|^0|否";        //"CodeContent" 是传入要下拉显示的代码 

        iArray[10]=new Array();
        iArray[10][0]="续期挂起";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[10][1]="80px";                //列宽
        iArray[10][2]=150;                  //列最大值
        iArray[10][3]=2;                    //是否允许输入,1表示允许，0表示不允许
        iArray[10][10]="YesOrNo2";          //引用代码："CodeName"为传入数据的名称
        iArray[10][11]="0|^1|是|^0|否";        //"CodeContent" 是传入要下拉显示的代码 

        LLLcContSuspendGrid = new MulLineEnter( "document" , "LLLcContSuspendGrid" );
        //这些属性必须在loadMulLine前
        LLLcContSuspendGrid.mulLineCount = 5;
        LLLcContSuspendGrid.displayTitle = 1;
        LLLcContSuspendGrid.canChk = 0;
        LLLcContSuspendGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLLcContSuspendGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLLcContSuspendGrid.selBoxEventFuncName = "LLLcContSuspendGridClick"; //点击RadioBox时响应的函数名
        LLLcContSuspendGrid.hiddenSubtraction=1; 
        LLLcContSuspendGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
}

//赔案相关的保单信息
function initLLLcContInGrid()
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
        iArray[1][0]="集体合同号码";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="150px";                //列宽
        iArray[1][2]=120;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="合同号码";           //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[2][1]="130px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许
 
        iArray[3]=new Array();
        iArray[3][0]="总单投保单号码";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[3][1]="100px";               //列宽
        iArray[3][2]=100;                  //列最大值
        iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="印刷号码";            //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[4][1]="130px";                //列宽
        iArray[4][2]=100;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="总单类型";           //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[5][1]="60px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[6]=new Array();
        iArray[6][0]="家庭单类型";          //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[6][1]="60px";                //列宽
        iArray[6][2]=100;                  //列最大值
        iArray[6][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="家庭保障号";          //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=100;                  //列最大值
        iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="原保单状态码";       //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[8][1]="50px";                //列宽
        iArray[8][2]=100;                  //列最大值
        iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="保全挂起";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[9][1]="80px";                //列宽
        iArray[9][2]=100;                  //列最大值
        iArray[9][3]=2;                    //是否允许输入,1表示允许，0表示不允许
        iArray[9][10]="YesOrNo1";          //引用代码："CodeName"为传入数据的名称
        iArray[9][11]="0|^1|是|^0|否";        //"CodeContent" 是传入要下拉显示的代码 

        iArray[10]=new Array();
        iArray[10][0]="续期挂起";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[10][1]="80px";                //列宽
        iArray[10][2]=150;                  //列最大值
        iArray[10][3]=2;                    //是否允许输入,1表示允许，0表示不允许
        iArray[10][10]="YesOrNo2";          //引用代码："CodeName"为传入数据的名称
        iArray[10][11]="0|^1|是|^0|否";        //"CodeContent" 是传入要下拉显示的代码 

        LLLcContInGrid = new MulLineEnter( "document" , "LLLcContInGrid" );
        //这些属性必须在loadMulLine前
        LLLcContInGrid.mulLineCount = 5;
        LLLcContInGrid.displayTitle = 1;
        LLLcContInGrid.canChk = 0;
        LLLcContInGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLLcContInGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLLcContInGrid.selBoxEventFuncName = "LLLcContInGridClick"; //点击RadioBox时响应的函数名
        LLLcContInGrid.hiddenSubtraction=1; 
        LLLcContInGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
      
}
</script>
