<%
//Name：LLLClaimQueryInit.jsp
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
        initQuery();
    }
    catch(re)
    {
        alert("LLLClaimQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
//立案附件信息
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
        iArray[1][0]="团体立案号";      //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[1][1]="100px";                //列宽
        iArray[1][2]=100;                  //列最大值
        iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="团体保单号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[2][1]="100px";                //列宽
        iArray[2][2]=100;                  //列最大值
        iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许
 
        iArray[3]=new Array();
        iArray[3][0]="个人客户号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[3][1]="80px";                //列宽
        iArray[3][2]=80;                  //列最大值
        iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="出险人姓名";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[4][1]="60px";                //列宽
        iArray[4][2]=60;                  //列最大值
        iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[5]=new Array();
        iArray[5][0]="出险类型";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[5][1]="70px";                //列宽
        iArray[5][2]=80;                  //列最大值
        iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[6]=new Array();
        iArray[6][0]="案件状态";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[6][1]="50px";                //列宽
        iArray[6][2]=80;                  //列最大值
        iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="结案日期";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[7][1]="80px";                //列宽
        iArray[7][2]=80;                  //列最大值
        iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="险种代码";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[8][1]="60px";                //列宽
        iArray[8][2]=60;                  //列最大值
        iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[9]=new Array();
      iArray[9][0]="币种";      	   		//列名
      iArray[9][1]="80px";            			//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[10]=new Array();
        iArray[10][0]="应赔付金额";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[10][1]="60px";                //列宽
        iArray[10][2]=60;                  //列最大值
        iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许        
        
        iArray[11]=new Array();
        iArray[11][0]="实际赔付金额";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[11][1]="60px";                //列宽
        iArray[11][2]=60;                  //列最大值
        iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许        

        LLLcContSuspendGrid = new MulLineEnter( "document" , "LLLcContSuspendGrid" );
        //这些属性必须在loadMulLine前
        LLLcContSuspendGrid.mulLineCount = 5;
        LLLcContSuspendGrid.displayTitle = 1;
        LLLcContSuspendGrid.canChk = 0;
        LLLcContSuspendGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLLcContSuspendGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLLcContSuspendGrid.selBoxEventFuncName = "LLLClaimQueryGridClick"; //点击RadioBox时响应的函数名
        LLLcContSuspendGrid.hiddenSubtraction=1; 
        LLLcContSuspendGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
          alert(ex);
      }
  }

function initSubReportGrid()
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
        iArray[1][0]="出险人编码"; //原事故者客户号
        iArray[1][1]="150px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="出险人姓名"; //事故者姓名
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="性别";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="出生日期";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;

//        iArray[3]=new Array();
//        iArray[3][0]="医院代码";
//        iArray[3][1]="0px";
//        iArray[3][2]=60;
//        iArray[3][3]=3;
//        iArray[3][4]="HospitalCode";
//        iArray[3][5]="3|4";                                //引用代码对应第几列，'|'为分割符
//        iArray[3][6]="0|1";                        //上面的列中放置引用代码中第几位值
//        iArray[3][9]="医院代码|NOTNULL";

//    iArray[4]=new Array()
//    iArray[4][0]="医院名称";
//    iArray[4][1]="0px";
//    iArray[4][2]=100;
//    iArray[4][3]=3;

//    iArray[5]=new Array()
//    iArray[5][0]="入院日期";
//    iArray[5][1]="0px";
//    iArray[5][2]=100;
//    iArray[5][3]=3;

        iArray[5]=new Array()
        iArray[5][0]="VIP标志";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;

//        iArray[6]=new Array();
//        iArray[6][0]="出险细节";
//        iArray[6][1]="0px";
//        iArray[6][2]=100;
//        iArray[6][3]=3;
//
//        iArray[7]=new Array();
//        iArray[7][0]="治疗医院";
//        iArray[7][1]="0px";
//        iArray[7][2]=100;
//        iArray[7][3]=3;
//
//        iArray[8]=new Array();
//        iArray[8][0]="治疗情况";
//        iArray[8][1]="0px";
//        iArray[8][2]=100;
//        iArray[8][3]=3;
//
//        iArray[9]=new Array();
//        iArray[9][0]="死亡标识";
//        iArray[9][1]="0px";
//        iArray[9][2]=100;
//        iArray[9][3]=3;
//
//        iArray[10]=new Array();
//        iArray[10][0]="备注信息";
//        iArray[10][1]="0px";
//        iArray[10][2]=100;
//        iArray[10][3]=3;

        SubReportGrid = new MulLineEnter("document","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
        SubReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //响应的函数名，不加扩号
        SubReportGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
        SubReportGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
