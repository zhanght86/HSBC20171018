<%
//程序名称：LABranchGroupInit.jsp
//程序功能：
//创建日期：
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
  //$initfield$document.all('BranchType').value=top.opener.document.all('BranchType').value;
  }
  catch(ex)
  {
    alert("在LABranchGroupQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LABranchGroupQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initBranchGroupGrid();
  }
  catch(re)
  {
    alert("LABranchGroupQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化BranchGroupGrid
 ************************************************************
 */
function initBranchGroupGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="30px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

        iArray[1]=new Array();
        iArray[1][0]="展业机构代码";         //列名
        iArray[1][1]="80px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="展业机构名称";         //列名
        iArray[2][1]="130px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="管理机构";         //列名
        iArray[3][1]="80px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[4]=new Array();
        iArray[4][0]="成立标志日期";         //列名
        iArray[4][1]="70px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[5]=new Array();
        iArray[5][0]="停业";         //列名
        iArray[5][1]="40px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
        
         iArray[6]=new Array();
        iArray[6][0]="展业机构内部代码";         //列名
        iArray[6][1]="0px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许


  
        BranchGroupGrid = new MulLineEnter( "fm" , "BranchGroupGrid" ); 

        //这些属性必须在loadMulLine前
        BranchGroupGrid.mulLineCount = 0;   
        BranchGroupGrid.displayTitle = 1;
        BranchGroupGrid.locked=1;
        BranchGroupGrid.canSel=1;
        BranchGroupGrid.canChk=0;
        BranchGroupGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化BranchGroupGrid时出错："+ ex);
      }
    }


</script>
