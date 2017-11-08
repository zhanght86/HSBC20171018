<%
//Name：LLLcContQueryInit.jsp
//Function：保单查询页面初始化
//Date：2005.06.21
//Author：quyang
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//接收前面页面传递过来的参数
function initParam()
{
    document.all('AppntNo').value = nullToEmpty("<%= tAppntNo %>");
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
        initLcContStateGrid();
        initQuery();
    }
    catch(re)
    {
        alert("LLLcContQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//保单信息表
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
        iArray[1][0]="团体保单号";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="150px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="个人保单号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[2][1]="130px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许
 
        iArray[3]=new Array();
        iArray[3][0]="管理机构";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[3][1]="70px";                //列宽
        iArray[3][2]=70;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="生效日期";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[4][1]="80px";                //列宽
        iArray[4][2]=60;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="交至日期";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[5][1]="80px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[6]=new Array();
        iArray[6][0]="签单机构";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[6][1]="60px";                //列宽
        iArray[6][2]=60;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="签单日期";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[7][1]="60px";                //列宽
        iArray[7][2]=60;                  //列最大值
        iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许


        iArray[8]=new Array();
        iArray[8][0]="保单类型";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[8][1]="60px";                //列宽
        iArray[8][2]=60;                  //列最大值
        iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="保单状态";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[9][1]="60px";                //列宽
        iArray[9][2]=60;                  //列最大值
        iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[10]=new Array();
        iArray[10][0]="prtno";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[10][1]="60px";                //列宽
        iArray[10][2]=60;                  //列最大值
        iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许

/*        iArray[10]=new Array();
        iArray[10][0]="状态";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[10][1]="100px";                //列宽
        iArray[10][2]=150;                  //列最大值
        iArray[10][3]=2;                    //是否允许输入,1表示允许，0表示不允许
        iArray[10][4]='llcontsuspendstate';      //设置要引用LDcode中的代码
        iArray[10][5]="10|11";             //引用代码对应第几列，'|'为分割符
        iArray[10][6]="1|0";              //上面的列中放置引用代码中第几位值*/
                
/*        iArray[11]=new Array();                //此项不显示，作为保存数据用
        iArray[11][0]="状态编号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[11][1]="50px";                //列宽
        iArray[11][2]=100;                  //列最大值
        iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许*/

        LLLcContSuspendGrid = new MulLineEnter( "document" , "LLLcContSuspendGrid" );
        //这些属性必须在loadMulLine前
        LLLcContSuspendGrid.mulLineCount = 5;
        LLLcContSuspendGrid.displayTitle = 1;
        LLLcContSuspendGrid.canChk = 0;
        LLLcContSuspendGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLLcContSuspendGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
//        LLLcContSuspendGrid.selBoxEventFuncName = "LLLcContSuspendGridClick"; //点击RadioBox时响应的函数名
        LLLcContSuspendGrid.hiddenSubtraction=1; 
        LLLcContSuspendGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }

//保单状态信息表
function initLcContStateGrid()
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
        iArray[1][0]="合同号";             //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="150px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=3;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="被保人号码";         //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[2][1]="130px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=3;                    //是否允许输入,1表示允许，0表示不允许
 
        iArray[3]=new Array();
        iArray[3][0]="保单险种号";            //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[3][1]="150px";                //列宽
        iArray[3][2]=70;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="状态类型";            //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[4][1]="80px";                //列宽
        iArray[4][2]=60;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="状态";            //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[5][1]="60px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[6]=new Array();
        iArray[6][0]="状态原因";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[6][1]="80px";                //列宽
        iArray[6][2]=60;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="状态生效时间";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[7][1]="100px";                //列宽
        iArray[7][2]=60;                  //列最大值
        iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="状态结束时间";        //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[8][1]="100px";                //列宽
        iArray[8][2]=60;                  //列最大值
        iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="备注";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[9][1]="100px";                //列宽
        iArray[9][2]=60;                  //列最大值
        iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[10]=new Array();
        iArray[10][0]="操作员";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[10][1]="80px";                //列宽
        iArray[10][2]=60;                  //列最大值
        iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[11]=new Array();
        iArray[11][0]="操作日期";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[11][1]="80px";                //列宽
        iArray[11][2]=60;                  //列最大值
        iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        LcContStateGrid = new MulLineEnter( "document" , "LcContStateGrid" );
        //这些属性必须在loadMulLine前
        LcContStateGrid.mulLineCount = 5;
        LcContStateGrid.displayTitle = 1;
        LcContStateGrid.canChk = 0;
        LcContStateGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LcContStateGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
//        LcContStateGrid.selBoxEventFuncName = "LcContStateGridClick"; //点击RadioBox时响应的函数名
        LcContStateGrid.hiddenSubtraction=1; 
        LcContStateGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
  
</script>
