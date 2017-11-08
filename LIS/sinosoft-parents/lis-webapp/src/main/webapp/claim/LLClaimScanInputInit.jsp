<%
//程序名称：LLClaimScanInputInit.jsp
//程序功能：
//创建日期：2005-8-26 15:50
//创建人  ：zw
//更新记录：
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
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

// 输入框的初始化
function initInpBox()
{ 
  try
  {                                   
  }
  catch(ex)
  {
    alert("在LLAppealMissInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=男&1=女&2=不详");
  }
  catch(ex)
  {
    alert("在LLAppealMissInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
  }
  catch(re)
  {
    alert("LLAppealMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 立案信息列表的初始化
function initLLAppealGrid()
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
    iArray[1][1]="150px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="申请人";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="申请人与被保人关系";             //列名
    iArray[3][1]="120px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="受托人姓名";             //列名
    iArray[4][1]="100px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="受托人性别";             //列名
    iArray[5][1]="100px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="出险日期";             //列名
    iArray[6][1]="100px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="结案日期";             //列名
    iArray[7][1]="80px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
//    iArray[8]=new Array();
//    iArray[8][0]="submissionid";             //列名
//    iArray[8][1]="100px";                //列宽
//    iArray[8][2]=200;                  //列最大值
//    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
//
//    iArray[9]=new Array();
//    iArray[9][0]="activityid";             //列名
//    iArray[9][1]="100px";                //列宽
//    iArray[9][2]=200;                  //列最大值
//    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
    
//    iArray[10]=new Array();
//    iArray[10][0]="出险原因";             //列名AccidentReason
//    iArray[10][1]="0px";                //列宽
//    iArray[10][2]=200;                  //列最大值
//    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    LLAppealGrid = new MulLineEnter( "document" , "LLAppealGrid" ); 
    //这些属性必须在loadMulLine前
    LLAppealGrid.mulLineCount = 5;   
    LLAppealGrid.displayTitle = 1;
    LLAppealGrid.locked = 0;
//    LLAppealGrid.canChk = 1;
    LLAppealGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLAppealGrid.selBoxEventFuncName ="LLAppealGridClick"; //响应的函数名，不加扩号
//        LLAppealGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项     
    LLAppealGrid.hiddenPlus=1;
    LLAppealGrid.hiddenSubtraction=1;
    LLAppealGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 报案信息列表的初始化
function initSelfLLAppealGrid()
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
    iArray[1][0]="申诉号";             //列名
    iArray[1][1]="120px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="提起人";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="性别";             //列名
    iArray[3][1]="80px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="与被保人关系";             //列名
    iArray[4][1]="120px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="证件类型";             //列名
    iArray[5][1]="100px";                //列宽
    iArray[5][2]=200;                  //列最大值
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="证件号码";             //列名
    iArray[6][1]="120px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="期待完成日期";             //列名
    iArray[7][1]="100px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
//    iArray[8]=new Array();
//    iArray[8][0]="submissionid";             //列名
//    iArray[8][1]="100px";                //列宽
//    iArray[8][2]=200;                  //列最大值
//    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
//
//    iArray[9]=new Array();
//    iArray[9][0]="activityid";             //列名
//    iArray[9][1]="100px";                //列宽
//    iArray[9][2]=200;                  //列最大值
//    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
    
//    iArray[10]=new Array();
//    iArray[10][0]="出险原因";             //列名AccidentReason
//    iArray[10][1]="0px";                //列宽
//    iArray[10][2]=200;                  //列最大值
//    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许         
    
    SelfLLAppealGrid = new MulLineEnter( "document" , "SelfLLAppealGrid" ); 
    //这些属性必须在loadMulLine前
    SelfLLAppealGrid.mulLineCount = 5;   
    SelfLLAppealGrid.displayTitle = 1;
    SelfLLAppealGrid.locked = 0;
//    SelfLLAppealGrid.canChk = 1;
    SelfLLAppealGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SelfLLAppealGrid.selBoxEventFuncName ="SelfLLAppealGridClick"; //响应的函数名，不加扩号
//        SelfLLAppealGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项    
    SelfLLAppealGrid.hiddenPlus=1;
    SelfLLAppealGrid.hiddenSubtraction=1;
    SelfLLAppealGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
