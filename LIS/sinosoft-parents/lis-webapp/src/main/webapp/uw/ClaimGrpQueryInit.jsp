<%
//**************************************************************************************************
//文件名称：ClaimGrpQueryInit.jsp
//程序功能：承保处理-团体保单-人工核保-既往理赔查询响应界面，团体信息的显示，当点击此界面单选按钮
//          时，连接到（ui/uw/ClaimQueryMain.jsp）个险层面的赔案查询。
//创建日期：2006-11-08
//创建人  ：zhaorx
//更新记录：
//**************************************************************************************************
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//接收前面页面传递过来的参数
function initParam()
{
    document.all('GrpAppntNo').value = nullToEmpty("<%= tGrpAppntNo %>");
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
        initLLClaimGrpGrid();
        initQuery();
    }
    catch(re)
    {
        alert("ClaimGrpQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//保单信息表
function initLLClaimGrpGrid()
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
        iArray[1][0]="团体合同号码";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="100px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="团体赔案号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[2][1]="100px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许
 
        iArray[3]=new Array();
        iArray[3][0]="赔案分案号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[3][1]="80px";                //列宽
        iArray[3][2]=70;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="立案日期";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[4][1]="80px";                //列宽
        iArray[4][2]=60;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="出险人姓名";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[5][1]="80px";                //列宽
        iArray[5][2]=100;                  //列最大值
        iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[6]=new Array();
        iArray[6][0]="赔案状态";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[6][1]="60px";                //列宽
        iArray[6][2]=60;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="签单日期";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[7][1]="60px";                //列宽
        iArray[7][2]=60;                  //列最大值
        iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许


//        iArray[8]=new Array();
//        iArray[8][0]="保单类型";               //列名（此列为顺序号，列名无意义，而且不显示）
//        iArray[8][1]="60px";                //列宽
//        iArray[8][2]=60;                  //列最大值
//        iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许
//
//        iArray[9]=new Array();
//        iArray[9][0]="保单状态";               //列名（此列为顺序号，列名无意义，而且不显示）
//        iArray[9][1]="60px";                //列宽
//        iArray[9][2]=60;                  //列最大值
//        iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        

        LLClaimGrpQueryGrid = new MulLineEnter( "fm" , "LLClaimGrpQueryGrid" );
        //这些属性必须在loadMulLine前
        LLClaimGrpQueryGrid.mulLineCount        = 5;
        LLClaimGrpQueryGrid.displayTitle        = 1;
        LLClaimGrpQueryGrid.canChk              = 0;
        LLClaimGrpQueryGrid.canSel              = 1; // 1 显示 ；0 隐藏（缺省值）
        LLClaimGrpQueryGrid.hiddenPlus          = 1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLClaimGrpQueryGrid.selBoxEventFuncName = "ClaimQueryMainClick"; //点击RadioBox时响应的函数名
        LLClaimGrpQueryGrid.hiddenSubtraction   = 1; 
        LLClaimGrpQueryGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }
  
</script>
