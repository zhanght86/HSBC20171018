<%
//程序名称：LLSecondUWInit.jsp
//程序功能：合同单人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
	
function initParm()
{	
	fm.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	
	fm.all('CaseNo').value = nullToEmpty("<%= tCaseNo%>");	
  	fm.all('InsuredNo').value =nullToEmpty("<%= tInsuredNo %>");	
  	fm.all('InsuredName').value =nullToEmpty("<%= tInsuredName %>");	
}

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
    alter("在SecondUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在SecondUWInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm(tCaseNo,tInsuredNo)
{
  try
  { 
    initInpBox();
    initLCContGrid();
    initParm();
    initLCContGridQuery();
  }
  catch(re)
  {
    alter("在LLReportInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initLCContGrid()
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
    iArray[1][0]="合同号码";
    iArray[1][1]="120px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="投保人客户号码";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=0;
    
    iArray[3]=new Array();
    iArray[3][0]="投保人名称";
    iArray[3][1]="80px";
    iArray[3][2]=120;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="管理机构";
    iArray[4][1]="80px";
    iArray[4][2]=120;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="赔案相关标志";
    iArray[5][1]="80px";
    iArray[5][2]=120;
    iArray[5][3]=3;
        
    iArray[6]=new Array();
    iArray[6][0]="投保书健康告知栏询问号";
    iArray[6][1]="120px";
    iArray[6][2]=60;
    iArray[6][3]=1;
    
    iArray[7]=new Array();
    iArray[7][0]="体检健康告知栏询问号";
    iArray[7][1]="100px";
    iArray[7][2]=120;
    iArray[7][3]=1;

    iArray[8]=new Array();
    iArray[8][0]="对应未告知情况";
    iArray[8][1]="100px";
    iArray[8][2]=120;
    iArray[8][3]=1;
    
    LCContGrid = new MulLineEnter("fm","LCContGrid");
    LCContGrid.mulLineCount =10;
    LCContGrid.displayTitle = 1;
    LCContGrid.locked = 1;
    LCContGrid.canSel =0
    LCContGrid.canChk = 1;;
    LCContGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    LCContGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    LCContGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}


 </script>