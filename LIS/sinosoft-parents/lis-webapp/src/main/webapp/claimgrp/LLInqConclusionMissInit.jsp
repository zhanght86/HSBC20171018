<%
//程序名称：LLInqConclusionMissInit.jsp
//程序功能：调查结论页面初始化
//创建日期：2005-06-27
//创建人  ：yuejw
//更新记录：
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">
//接收参数
function initParam()
{
    fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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


    }
    catch(ex)
    {
        alert("在LLInqConclusionInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
        alert("在LLInqConclusionInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initParam();
        initLLInqConclusionGrid();  
        LLInqConclusionGridQuery();
    }
    catch(re)
    {
        alert("LLInqConclusionInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//调查申请表初始化
function initLLInqConclusionGrid()
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
      iArray[1][1]="100px";                //列宽
      iArray[1][2]=80;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="结论序号";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=80;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="调查批次";             //列名
      iArray[3][1]="80px";                //列宽
      iArray[3][2]=80;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="发起机构";             //列名
      iArray[4][1]="80px";                //列宽
      iArray[4][2]=80;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="调查机构";             //列名
      iArray[5][1]="80px";                //列宽
      iArray[5][2]=80;                  //列最大值
      iArray[5][3]=0; 
              
    iArray[6]=new Array();
    iArray[6][0]="Missionid";             //列名
    iArray[6][1]="0px";                //列宽
    iArray[6][2]=200;                  //列最大值
    iArray[6][3]=3;                    //是否允许输入,1表示允许，0表示不允许
  
    iArray[7]=new Array();
    iArray[7][0]="submissionid";             //列名
    iArray[7][1]="0px";                //列宽
    iArray[7][2]=200;                  //列最大值
    iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许             
                
    iArray[8]=new Array();
    iArray[8][0]="activityid";             //列名
    iArray[8][1]="0px";                //列宽
    iArray[8][2]=200;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许     

      LLInqConclusionGrid = new MulLineEnter( "fm" , "LLInqConclusionGrid" ); 
      //这些属性必须在loadMulLine前
      LLInqConclusionGrid.mulLineCount = 0;   
      LLInqConclusionGrid.displayTitle = 1;
      LLInqConclusionGrid.locked = 1;
      LLInqConclusionGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLInqConclusionGrid.selBoxEventFuncName = "LLInqConclusionGridClick"; //点击RadioBox时响应的函数名
      LLInqConclusionGrid.hiddenPlus=1;
      LLInqConclusionGrid.hiddenSubtraction=1;
      LLInqConclusionGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
