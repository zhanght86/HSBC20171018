<%
//程序名称：LLReportQueryInit.jsp
//程序功能：
//创建日期： 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
    try
    {                                   


    }
    catch(ex)
    {
        alert("在LLReportQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
       alert("在LLReportQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initLLReportGrid();  
    }
    catch(re)
    {
        alert("LLReportQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
// 保单信息列表的初始化
function initLLReportGrid()
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
      iArray[1][0]="报案号";             //列名
      iArray[1][1]="120px";                //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="报案人姓名";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="报案人电话";             //列名
      iArray[3][1]="80px";                //列宽
      iArray[3][2]=200;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="报案人通讯地址";             //列名
      iArray[4][1]="120px";                //列宽
      iArray[4][2]=500;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="报案人与事故人关系";             //列名
      iArray[5][1]="60px";                //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 


      iArray[6]=new Array();
      iArray[6][0]="报案方式";             //列名
      iArray[6][1]="100px";                //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="出险地点";             //列名
      iArray[7][1]="80px";                //列宽
      iArray[7][2]=200;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="申请受理日期";             //列名
      iArray[8][1]="100px";                //列宽
      iArray[8][2]=200;                  //列最大值
      iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="管辖机构";             //列名
      iArray[9][1]="100px";                //列宽
      iArray[9][2]=200;                  //列最大值
      iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许            
      
      iArray[10]=new Array();
      iArray[10][0]="出险原因";             //列名AccidentReason
      iArray[10][1]="0px";                //列宽
      iArray[10][2]=200;                  //列最大值
      iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许         
      
      LLReportGrid = new MulLineEnter( "fm" , "LLReportGrid" ); 
      //这些属性必须在loadMulLine前
      LLReportGrid.mulLineCount = 10;   
      LLReportGrid.displayTitle = 1;
      LLReportGrid.locked = 0;
//      LLReportGrid.canChk = 1;
      LLReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLReportGrid.hiddenPlus=1;
      LLReportGrid.hiddenSubtraction=1;
      LLReportGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
