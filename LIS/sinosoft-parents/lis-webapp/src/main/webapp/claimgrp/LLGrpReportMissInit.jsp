<%
//程序名称：LLGrpReportMissInit.jsp
//程序功能：
//创建日期：2008-10-25 18:13
//创建人  ：zhangzheng
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
    alert("在LLReportMissInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LLReportMissInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    initSelfLLReportGrid();
    querySelfGrid();

  }
  catch(re)
  {
    alert("LLReportMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//清空页面
function emptyForm() 
{
  //emptyFormElements();     //清空页面所有输入框，在COMMON.JS中实现
   
  initSubInsuredGrid();
  initBnfGrid();
  initImpartGrid();
  initSpecGrid();
  initDutyGrid();
  spanDutyGrid.innerHTML="";
}


// 报案信息列表的初始化
function initSelfLLReportGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=8;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="赔案号";             //列名
    iArray[1][1]="160px";                //列宽
    iArray[1][2]=160;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="报案人姓名";             //列名
    iArray[2][1]="120px";                //列宽
    iArray[2][2]=120;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="团体客户号";             //列名
    iArray[3][1]="120px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="单位名称";             //列名
    iArray[4][1]="160px";                //列宽
    iArray[4][2]=300;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="团体保单号";             //列名
    iArray[5][1]="160px";                //列宽
    iArray[5][2]=160;                  //列最大值
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="报案日期";             //列名
    iArray[6][1]="80px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //列名
    iArray[7][1]="60px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //列名
    iArray[8][1]="80px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //列名
    iArray[9][1]="80px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许            
    
    iArray[10]=new Array();
    iArray[10][0]="报案类型";             //列名AccidentReason
    iArray[10][1]="70px";                //列宽
    iArray[10][2]=70;                  //列最大值
    iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许     
    
    iArray[11]=new Array();
    iArray[11][0]="扫描件";             //列名AccidentReason
    iArray[11][1]="60px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许   
    
    iArray[12]=new Array();
    iArray[12][0]="调查状态";             //列名AccidentReason
    iArray[12][1]="60px";                //列宽
    iArray[12][2]=200;                  //列最大值
    iArray[12][3]=0;                    //是否允许输入,1表示允许，0表示不允许            
    
    SelfLLReportGrid = new MulLineEnter( "document" , "SelfLLReportGrid" ); 
    //这些属性必须在loadMulLine前
    SelfLLReportGrid.mulLineCount = 5;   
    SelfLLReportGrid.displayTitle = 1;
    SelfLLReportGrid.locked = 0;
//    SelfLLReportGrid.canChk = 1;
    SelfLLReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SelfLLReportGrid.selBoxEventFuncName ="SelfLLReportGridClick"; //响应的函数名，不加扩号
//        SelfLLReportGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项    
    SelfLLReportGrid.hiddenPlus=1;
    SelfLLReportGrid.hiddenSubtraction=1;
    SelfLLReportGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
