<%
//Name:LLClaimPopedomInit.jsp
//function??
//author:赵雁
//Date:2005.06.16
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
        initClaimPopedomGrid();      //理赔权限录入
        
    }
    catch(re)
    {
        alter("LLClaimPopedomInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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

    //理赔权限信息
    fm.reset( );
    fm.editPopedomButton.disabled = true;
  
  }
  catch(ex)
  {
    alter("LLClaimPopedomInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}





/**=========================================================================
理赔权限录入信息
=========================================================================
*/
function initClaimPopedomGrid()
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
    iArray[1][0]="权限级别";
    iArray[1][1]="40px";
    iArray[1][2]=10;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="案件类型编码";
    iArray[2][1]="60px";
    iArray[2][2]=10;
    iArray[2][3]=3;

    iArray[3]=new Array();
    iArray[3][0]="案件类型";
    iArray[3][1]="50px";
    iArray[3][2]=10;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="权限级别名称";
    iArray[4][1]="80px";
    iArray[4][2]=10;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="理赔类型";
    iArray[5][1]="40px";
    iArray[5][2]=10;
    iArray[5][3]=3;        

    iArray[6]=new Array();
    iArray[6][0]="审核审批分类";
    iArray[6][1]="40px";
    iArray[6][2]=10;
    iArray[6][3]=3;        
    
    iArray[7]=new Array();
    iArray[7][0]="最小金额";
    iArray[7][1]="40px";
    iArray[7][2]=10;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
    iArray[8][0]="最大金额";
    iArray[8][1]="40px";
    iArray[8][2]=10;
    iArray[8][3]=0;
    
    iArray[9]=new Array();
    iArray[9][0]="启用日期";
    iArray[9][1]="60px";
    iArray[9][2]=10;
    iArray[9][3]=0;
            

    iArray[10]=new Array();
    iArray[10][0]="结束日期";
    iArray[10][1]="60px";
    iArray[10][2]=10;
    iArray[10][3]=0;                

    iArray[11]=new Array();
    iArray[11][0]="管理机构";
    iArray[11][1]="40px";
    iArray[11][2]=10;
    iArray[11][3]=0;  
    
    iArray[12]=new Array();
    iArray[12][0]="操作员";
    iArray[12][1]="30px";
    iArray[12][2]=10;
    iArray[12][3]=0;
    
    
    iArray[13]=new Array();
    iArray[13][0]="入机日期";
    iArray[13][1]="0px";
    iArray[13][2]=10;
    iArray[13][3]=3;  
    
    iArray[14]=new Array();
    iArray[14][0]="入机时间";
    iArray[14][1]="0px";
    iArray[14][2]=10;
    iArray[14][3]=3;
            
    iArray[15]=new Array();
    iArray[15][0]="最后一次修改日期";
    iArray[15][1]="0px";
    iArray[15][2]=10;
    iArray[15][3]=3;  
    
    iArray[16]=new Array();
    iArray[16][0]="最后一次修改时间";
    iArray[16][1]="0px";
    iArray[16][2]=10;
    iArray[16][3]=3; 
                            
    ClaimPopedomGrid = new MulLineEnter("document","ClaimPopedomGrid");
    ClaimPopedomGrid.mulLineCount = 5;
    ClaimPopedomGrid.displayTitle = 1;
    ClaimPopedomGrid.locked = 0;
//  ClaimPopedomGrid.canChk =1;              //多选按钮，1显示，0隐藏
    ClaimPopedomGrid.canSel =1;              //单选按钮，1显示，0隐藏
    ClaimPopedomGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
    ClaimPopedomGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
    ClaimPopedomGrid.selBoxEventFuncName = "getClaimPopedomGrid"; //函数名称
    ClaimPopedomGrid.selBoxEventFuncParm ="divLLInqCourse";          //参数        
    ClaimPopedomGrid.loadMulLine(iArray);
}
catch(ex)
{
    alter(ex);
}
}


</script>

