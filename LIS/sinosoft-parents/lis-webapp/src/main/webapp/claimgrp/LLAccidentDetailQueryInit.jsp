<%
//程序名称：LLAccidentDetailQueryInit.jsp
//程序功能：
//创建日期：2005-10-26
//创建人  ：pd
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%
String accidentDetailName = StrTool.unicodeToGBK(request.getParameter("accidentDetailName"));
String AccResultName =StrTool.unicodeToGBK(request.getParameter("AccResultName"));
String occurReason = request.getParameter("occurReason");

%>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
   // 保单查询条件
      fm.accidentDetailName.value = '<%=accidentDetailName%>';

      fm.AccResultName.value = '<%=AccResultName%>';      
      fm.occurReason.value = '<%=occurReason%>';      

 

  }
  catch(ex)
  {    
    
    alert("在LDPersonQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在LDPersonQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initAccidentGrid();
    afterQuery();
  }
  catch(re)
  {

    alert("LDPersonQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initAccidentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=10;                     //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="出险代码";               //列名
      iArray[1][1]="100px";                  //列宽
      iArray[1][2]=100;                     //列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="出险名称";               //列名
      iArray[2][1]="400px";                  //列宽
      iArray[2][2]=400;                     //列最大值
      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      AccidentGrid = new MulLineEnter( "fm" , "AccidentGrid" ); 
      //这些属性必须在loadMulLine前
      AccidentGrid.mulLineCount = 10;   
      AccidentGrid.displayTitle = 1;
      AccidentGrid.locked = 1;
      AccidentGrid.canSel = 1;
      AccidentGrid.hiddenPlus=1;
      AccidentGrid.hiddenSubtraction=1;
      AccidentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
