<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//程序名称：GrpFeeInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
	//添加页面控件的初始化。
%>
<script language="JavaScript">

function initInpBox()
{ 
  try
  {                                   
    document.all('ProposalGrpContNo').value = GrpContNo;
    document.all('GrpContNo').value = GrpContNo;
  }
  catch(ex)
  {
    alert("在GroupPolInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}


function initSelBox()
{
  try
  {
   
  }
  catch(ex)
  {
    alert("在LLClaimConfListInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}
function initForm()
{
  try
  {
    
    initInpBox();
    initSelBox();
    initCaseGrid();
    //selectLMChargeRate();

  }
  catch(re)
  {
    alter("在LLClaimConfListInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initCaseGrid()
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
    iArray[1][0]="集体合同号";
    iArray[1][1]="70px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="险种编码";
    iArray[2][1]="70px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    
      iArray[3]=new Array();
      iArray[3][0]="手续费比率";         		//列名
      iArray[3][1]="70px";            		//列宽
      iArray[3][2]= 100;            			//列最大值
      iArray[3][3]= 0;              			//是否允许输入,1表示允许，0表示不允许   
    

    CaseGrid = new MulLineEnter("fm","CaseGrid");
    CaseGrid.mulLineCount =0;
    CaseGrid.displayTitle = 1;        //标题
    CaseGrid.locked = 1;        //可写
    CaseGrid.canSel =1;        //单选多选
    CaseGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    CaseGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    CaseGrid.selBoxEventFuncName = "flagLMChargeRate";
    //CaseGrid.chkBoxEventFuncName = "flagLMChargeRate";
    CaseGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}

</script>
