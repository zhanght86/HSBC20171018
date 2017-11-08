<%
//程序名称：LLClaimUserInit.jsp
//程序功能：理赔用户管理
//创建日期：2005-7-11 
//创建人  ：曲洋
//更新记录：  更新人 yuejw    更新日期     更新原因/内容
//            zhouming        2006/04/30   添加在岗状态
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
//接收参数
function initParam()
{
   document.all('tOperator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   document.all('tComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
function initForm()
{
    try
    {   		
        initInpBox();        
        initParam();       
        initLLClaimUserGrid();                  
    }
    catch(re)
    {
        alter("LLClaimUserInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}


function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alter("LLClaimUserInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


function initLLClaimUserGrid()
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
        iArray[1][0]="用户编码";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="用户姓名";
        iArray[2][1]="80px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="机构编码";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=0;        

        iArray[4]=new Array();
        iArray[4][0]="权限级别编码";
        iArray[4][1]="60px";
        iArray[4][2]=10;
        iArray[4][3]=3;        
        
        iArray[5]=new Array();
        iArray[5][0]="权限级别";
        iArray[5][1]="120px";
        iArray[5][2]=10;
        iArray[5][3]=0;        

        iArray[6]=new Array();
        iArray[6][0]="上级用户编码";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=3;        

        iArray[7]=new Array();
        iArray[7][0]="上级用户";
        iArray[7][1]="80px";
        iArray[7][2]=10;
        iArray[7][3]=0;    
        
        iArray[8]=new Array();
        iArray[8][0]="报案权限";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="立案权限";
        iArray[9][1]="60px";
        iArray[9][2]=10;
        iArray[9][3]=0;
        
        iArray[10]=new Array();
        iArray[10][0]="呈报权限";
        iArray[10][1]="60px";
        iArray[10][2]=10;
        iArray[10][3]=0;         

        iArray[11]=new Array();
        iArray[11][0]="调查权限";
        iArray[11][1]="60px";
        iArray[11][2]=10;
        iArray[11][3]=0;                

        iArray[12]=new Array();
        iArray[12][0]="预付权限";
        iArray[12][1]="60px";
        iArray[12][2]=10;
        iArray[12][3]=0;  
               
        iArray[13]=new Array();
        iArray[13][0]="简易案件权限";
        iArray[13][1]="80px";
        iArray[13][2]=10;
        iArray[13][3]=0;
               
        iArray[14]=new Array();
        iArray[14][0]="单证扫描权限";
        iArray[14][1]="80px";
        iArray[14][2]=10;
        iArray[14][3]=0;  
               
        iArray[15]=new Array();
        iArray[15][0]="任务分配权限";
        iArray[15][1]="80px";
        iArray[15][2]=10;
        iArray[15][3]=0;
               
		iArray[16]=new Array();
        iArray[16][0]="审核权限";
        iArray[16][1]="60px";
        iArray[16][2]=10;
        iArray[16][3]=0;               
               
        iArray[17]=new Array();
        iArray[17][0]="审核级别";
        iArray[17][1]="60px";
        iArray[17][2]=10;
        iArray[17][3]=3;  
               
        iArray[18]=new Array();
        iArray[18][0]="审批权限";
        iArray[18][1]="60px";
        iArray[18][2]=10;
        iArray[18][3]=0;
                
        iArray[19]=new Array();
        iArray[19][0]="审批级别";
        iArray[19][1]="70px";
        iArray[19][2]=10;
        iArray[19][3]=3;  
               
        iArray[20]=new Array();
        iArray[20][0]="豁免权限";
        iArray[20][1]="70px";
        iArray[20][2]=10;
        iArray[20][3]=0;     
        
        iArray[21]=new Array();
        iArray[21][0]="在岗状态";
        iArray[21][1]="60px";
        iArray[21][2]=10;
        iArray[21][3]=0;  
               
        iArray[22]=new Array();
        iArray[22][0]="工作电话";
        iArray[22][1]="70px";
        iArray[22][2]=10;
        iArray[22][3]=0;     


//------------------------------------------------------------------------------End            
                                
        LLClaimUserGrid = new MulLineEnter("document","LLClaimUserGrid");
        LLClaimUserGrid.mulLineCount = 5;
        LLClaimUserGrid.displayTitle = 1;
        LLClaimUserGrid.locked = 0;
//      LLClaimUserGrid.canChk =1;              //多选按钮，1显示，0隐藏
        LLClaimUserGrid.canSel =1;              //单选按钮，1显示，0隐藏
        LLClaimUserGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
        LLClaimUserGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
        LLClaimUserGrid.selBoxEventFuncName = "LLClaimUserGridClick"; //函数名称
        LLClaimUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

