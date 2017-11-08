<%
//程序名称：LLParaClaimSimple.jsp
//程序功能：简单案例标准
//创建日期：2005-9-19
//创建人  ：wuhao
//更新记录：  更新人 wuhao    更新日期     更新原因/内容
%>
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
    		
        initInpBox();                //初始化表格
        initLLParaClaimSimpleGrid();      
        
    }
    catch(re)
    {
        alter("LLParaClaimSimpleInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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

    fm.reset();
    fm.editSimpleButton.disabled = true;
  
  }
  catch(ex)
  {
    alter("LLParaClaimSimpleInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}





function initLLParaClaimSimpleGrid()
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
        iArray[1][0]="机构编码";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="机构名称";
        iArray[2][1]="120px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="上级机构";
        iArray[3][1]="120px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="最小金额";
        iArray[4][1]="40px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="最大金额";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="启用日期";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                

        iArray[7]=new Array();
        iArray[7][0]="结束日期";
        iArray[7][1]="60px";
        iArray[7][2]=10;
        iArray[7][3]=0;                

        iArray[8]=new Array();
        iArray[8][0]="操作员";
        iArray[8][1]="30px";
        iArray[8][2]=10;
        iArray[8][3]=0;  
        
        iArray[9]=new Array();
        iArray[9][0]="入机日期";
        iArray[9][1]="0px";
        iArray[9][2]=10;
        iArray[9][3]=3;
        
        
        iArray[10]=new Array();
        iArray[10][0]="入机时间";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;  
        
        iArray[11]=new Array();
        iArray[11][0]="最后一次修改日期";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;
                
        iArray[12]=new Array();
        iArray[12][0]="最后一次修改时间";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;  
        
        LLParaClaimSimpleGrid = new MulLineEnter("document","LLParaClaimSimpleGrid");
        LLParaClaimSimpleGrid.mulLineCount = 5;
        LLParaClaimSimpleGrid.displayTitle = 1;
        LLParaClaimSimpleGrid.locked = 0;
        LLParaClaimSimpleGrid.canSel =1;              //单选按钮，1显示，0隐藏
        LLParaClaimSimpleGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        LLParaClaimSimpleGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        LLParaClaimSimpleGrid.selBoxEventFuncName = "LLParaClaimSimpleGridClick"; //函数名称
        LLParaClaimSimpleGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

