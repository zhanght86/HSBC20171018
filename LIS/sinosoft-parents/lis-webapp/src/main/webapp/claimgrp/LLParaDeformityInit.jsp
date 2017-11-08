<%
//Name:LLParaDeformityInit.jsp
//function??
//author:赵雁
//Date:2005.06.24
%>
<html>

</html>

<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>


<script language="JavaScript">

/**=========================================================================
    Form载入页面时进行初始化
   =========================================================================
*/
function initForm()
{
    try
    {
        
        initInpBox();                  //初始化表格
        initParaDeformityGrid();      //伤残等级参数录入
        queryParaDeformity();         //伤残等级参数信息查询
        
    }
    catch(re)
    {
        alter("LLParaDeformityInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

/**=========================================================================
    页面初始化
   =========================================================================
*/
function initInpBox()
{
  try
  {

    //伤残等级参数信息初始化
    fm.reset( );
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
  }
  catch(ex)
  {
    alter("LLParaDeformityInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}





/**=========================================================================
    伤残等级参数录入信息
   =========================================================================
*/
function initParaDeformityGrid()
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
        iArray[1][0]="伤残类型";
        iArray[1][1]="30px";
        iArray[1][2]=10;
        iArray[1][3]=0;


        iArray[2]=new Array();
        iArray[2][0]="伤残级别";
        iArray[2][1]="30px";
        iArray[2][2]=10;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="伤残级别名称";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="伤残代码";
        iArray[4][1]="30px";
        iArray[4][2]=10;
        iArray[4][3]=0;        
        
        iArray[5]=new Array();
        iArray[5][0]="伤残代码名称";
        iArray[5][1]="180px";
        iArray[5][2]=10;
        iArray[5][3]=0;    
        
        iArray[6]=new Array();
        iArray[6][0]="残疾给付比例";
        iArray[6][1]="40px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                                
        ParaDeformityGrid = new MulLineEnter("fm","ParaDeformityGrid");
        ParaDeformityGrid.mulLineCount = 0;
        ParaDeformityGrid.displayTitle = 1;
        ParaDeformityGrid.locked = 0;
//      ParaDeformityGrid.canChk =1;              //多选按钮，1显示，0隐藏
        ParaDeformityGrid.canSel =1;              //单选按钮，1显示，0隐藏
        ParaDeformityGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        ParaDeformityGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        ParaDeformityGrid.selBoxEventFuncName = "getParaDeformityGrid"; //函数名称
        //ParaDeformityGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
        ParaDeformityGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

