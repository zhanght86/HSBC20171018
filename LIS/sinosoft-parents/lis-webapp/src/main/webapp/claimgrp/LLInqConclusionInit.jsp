<%
//程序名称：LLInqConclusionInit.jsp
//程序功能：调查结论页面初始化
//创建日期：2005-05-30
//创建人  ：zhoulei
//更新记录：yuejw
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">
function initParam()
{  
    fm.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
    fm.all('ConNo').value =nullToEmpty("<%=tConNo%>");
    fm.all('Activityid').value =nullToEmpty("<%=tActivityid%>");
    fm.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
    fm.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>");
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
      initLLInqApplyGrid();
//      initLLInqCourseGrid();
        initParam();
        initializeQuery(); 
    }
    catch(re)
    {
        alert("LLInqConclusionInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//调查申请表初始化
function initLLInqApplyGrid()
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
      iArray[1][1]="100px";                //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="调查序号";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="调查批次";             //列名
      iArray[3][1]="80px";                //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出险人客户号";             //列名
      iArray[4][1]="100px";                //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="出险人名称";             //列名
      iArray[5][1]="100px";                //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="VIP";             //列名
      iArray[6][1]="30px";                //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="提起阶段";             //列名
      iArray[7][1]="80px";                //列宽
      iArray[7][2]=100;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="调查原因";             //列名
      iArray[8][1]="100px";                //列宽
      iArray[8][2]=100;                  //列最大值
      iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="调查项目";             //列名
      iArray[9][1]="0px";                //列宽
      iArray[9][2]=100;                  //列最大值
      iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="调查结论";             //列名
      iArray[10][1]="0px";                //列宽
      iArray[10][2]=100;                  //列最大值
      iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="开始日期";             //列名
      iArray[11][1]="80px";                //列宽
      iArray[11][2]=100;                  //列最大值
      iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许 
      
      iArray[12]=new Array();
      iArray[12][0]="结束日期";             //列名
      iArray[12][1]="80px";                //列宽
      iArray[12][2]=100;                  //列最大值
      iArray[12][3]=0;                    //是否允许输入,1表示允许，0表示不允许 

      LLInqApplyGrid = new MulLineEnter( "fm" , "LLInqApplyGrid" ); 
      //这些属性必须在loadMulLine前
      LLInqApplyGrid.mulLineCount = 0;   
      LLInqApplyGrid.displayTitle = 1;
      LLInqApplyGrid.locked = 1;
      LLInqApplyGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLInqApplyGrid.selBoxEventFuncName = "LLInqApplyGridClick"; //点击RadioBox时响应的函数名
      LLInqApplyGrid.hiddenPlus=1;
      LLInqApplyGrid.hiddenSubtraction=1;
      LLInqApplyGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}


//调查过程表初始化
function initLLInqCourseGrid()
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
      iArray[1][1]="100px";                //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="调查序号";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="过程序号";             //列名
      iArray[3][1]="100px";                //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="调查日期";             //列名
      iArray[4][1]="80px";                //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="调查方式";             //列名
      iArray[5][1]="0px";                //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=3; 


      iArray[6]=new Array();
      iArray[6][0]="调查地点";             //列名
      iArray[6][1]="100px";                //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="被调查人";             //列名
      iArray[7][1]="0px";                //列宽
      iArray[7][2]=100;                  //列最大值
      iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="调查过程";             //列名
      iArray[8][1]="100px";                //列宽
      iArray[8][2]=100;                  //列最大值
      iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="调查机构";             //列名
      iArray[9][1]="80px";                //列宽
      iArray[9][2]=100;                  //列最大值
      iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="第一调查人";             //列名
      iArray[10][1]="100px";                //列宽
      iArray[10][2]=100;                  //列最大值
      iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="第二调查人";             //列名
      iArray[11][1]="100px";                //列宽
      iArray[11][2]=100;                  //列最大值
      iArray[11][3]=0;                    //是否允许输入,1表示允许，0表示不允许      


      LLInqCourseGrid = new MulLineEnter( "fm" , "LLInqCourseGrid" ); 
      //这些属性必须在loadMulLine前
      LLInqCourseGrid.mulLineCount = 0;   
      LLInqCourseGrid.displayTitle = 1;
      LLInqCourseGrid.locked = 1;
      LLInqCourseGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLInqCourseGrid.selBoxEventFuncName = "LLInqCourseGridClick"; //点击RadioBox时响应的函数名
      LLInqCourseGrid.hiddenPlus=1;
      LLInqCourseGrid.hiddenSubtraction=1;
      LLInqCourseGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
