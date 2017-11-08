<%
//程序名称：OLDCodeQuery.js
//程序功能：
//创建日期：2002-08-16 17:44:48
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
  //$initfield$
  }
  catch(ex)
  {
    alert("在OLDCodeQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在OLDCodeQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initCodeGrid();
  }
  catch(re)
  {
    alert("OLDCodeQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化CodeGrid
 ************************************************************
 */
function initCodeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="100px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

            iArray[1]=new Array();
    iArray[1][0]="编码类型";         //列名
    iArray[1][1]="393px";         //宽度
    iArray[1][2]=100;         //最大长度
    iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

    iArray[2]=new Array();
    iArray[2][0]="编码";         //列名
    iArray[2][1]="278px";         //宽度
    iArray[2][2]=100;         //最大长度
    iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

    iArray[3]=new Array();
    iArray[3][0]="编码名称";         //列名
    iArray[3][1]="261px";         //宽度
    iArray[3][2]=100;         //最大长度
    iArray[3][3]=0;         //是否允许录入，0--不能，1--允许
    
    iArray[4]=new Array();
    iArray[4][0]="编码别名";         //列名
    iArray[4][1]="0px";         //宽度
    iArray[4][2]=0;         //最大长度
    iArray[4][3]=0;         //是否允许录入，0--不能，1--允许
    
    iArray[5]=new Array();
    iArray[5][0]="机构代码";         //列名
    iArray[5][1]="0px";         //宽度
    iArray[5][2]=0;         //最大长度
    iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
    
    iArray[6]=new Array();
    iArray[6][0]="其它标志";         //列名
    iArray[6][1]="0px";         //宽度
    iArray[6][2]=0;         //最大长度
    iArray[6][3]=0;         //是否允许录入，0--不能，1--允许


   CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
      //这些属性必须在loadMulLine前
      CodeGrid.mulLineCount = 10;   
      CodeGrid.displayTitle = 1;
      CodeGrid.locked = 1;
      CodeGrid.canSel = 1;
      CodeGrid.loadMulLine(iArray);  
      CodeGrid.selBoxEventFuncName = "selectItem";
      
      //这些操作必须在loadMulLine后面
      //CodeGrid.setRowColData(1,1,"asdf");
      
       // CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 

        //这些属性必须在loadMulLine前
       // CodeGrid.mulLineCount = 3;   
       // CodeGrid.displayTitle = 1;
       // CodeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化CodeGrid时出错："+ ex);
      }
    }


</script>