<%
//Name:LLInqDistributeInit.jsp
//function：调查任务分配界面的初始化
//author:
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
//接收参数
function initParam()
{
	fm.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");	      
    fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    fm.all('tComCode').value =fm.all('ComCode').value+"%"; //用于根据机构查询调查人
   
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
    fm.all('ClmNo').value = '';
    fm.all('CustomerName').value = '';
    fm.all('CustomerNo').value = '';
    
  }
  catch(ex)
  {
    alter("在LLSuveryDistributInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在LLSuveryDistributInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}
function initForm()
{
  try
  {
    
    initInpBox();
    initInqApplyGrid();
    initParam();

  }
  catch(re)
  {
    alter("在LLReportInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initInqApplyGrid()
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
    iArray[1][0]="赔案号";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="调查序号";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=0;
    
    iArray[3]=new Array();
    iArray[3][0]="调查批次号";
    iArray[3][1]="100px";
    iArray[3][2]=120;
    iArray[3][3]=3;

    iArray[4]=new Array();
    iArray[4][0]="出险人客户号";
    iArray[4][1]="100px";
    iArray[4][2]=60;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="出险人名称";
    iArray[5][1]="100px";
    iArray[5][2]=120;
    iArray[5][3]=0;

    iArray[6]=new Array();
    iArray[6][0]="提起阶段";
    iArray[6][1]="100px";
    iArray[6][2]=60;
    iArray[6][3]=0;

    iArray[7]=new Array();
    iArray[7][0]="调查原因";
    iArray[7][1]="100px";
    iArray[7][2]=60;
    iArray[7][3]=3;
    
    iArray[8]=new Array();
    iArray[8][0]="调查机构";
    iArray[8][1]="100px";
    iArray[8][2]=60;
    iArray[8][3]=0;
    
    iArray[9]=new Array();
    iArray[9][0]="Missionid";             //列名
    iArray[9][1]="100px";                //列宽
    iArray[9][2]=200;                  //列最大值
    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="submissionid";             //列名
    iArray[10][1]="100px";                //列宽
    iArray[10][2]=200;                  //列最大值
    iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许             
     		     
    iArray[11]=new Array();
    iArray[11][0]="activityid";             //列名
    iArray[11][1]="80px";                //列宽
    iArray[11][2]=200;                  //列最大值
    iArray[11][3]=3;     
    
    InqApplyGrid = new MulLineEnter("fm","InqApplyGrid");
    InqApplyGrid.mulLineCount =0;
    InqApplyGrid.displayTitle = 1;
    InqApplyGrid.locked = 1;
    InqApplyGrid.canSel =1;
    InqApplyGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    InqApplyGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    InqApplyGrid.selBoxEventFuncName = "InqApplyGridSelClick";
    InqApplyGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}

 </script>
