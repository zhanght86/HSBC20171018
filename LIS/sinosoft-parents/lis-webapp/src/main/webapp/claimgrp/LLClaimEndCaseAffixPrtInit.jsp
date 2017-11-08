<%
//文件名称:LLClaimEndCaseAffixPrtInit.jsp
//文件功能：“结案时所有单证打印”初始化 
//建立人:                      建立日期：
//文件描述:
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    fm.all('CustNo').value = nullToEmpty("<%= tCustNo %>");
    
    fm.all('ClaimNo').value =fm.all('ClmNo').value;
    fm.all('RptNo').value =fm.all('ClmNo').value;
    
    fm.all('customerNo').value =fm.all('CustNo').value;
//    fm.all('customerNo').value =fm.all('CustNo').value;
//    alert(fm.all('customerNo').value);
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
		//
	}
	catch(re)
	{
		alert("在LLClaimEndCaseAffixPrtInit.jsp-->initInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
  try
  {
    initParam();
    initLLInqApplyGrid();
  }
  catch(re)
  {
    alert("在LLClaimEndCaseAffixPrtInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//调查申请表初始化
function initLLInqApplyGrid()
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
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="100px";                //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="调查序号";             //列名
      iArray[2][1]="80px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="调查批次";             //列名
      iArray[3][1]="0px";                //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出险人客户号";             //列名
      iArray[4][1]="80px";                //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="调查原因";             //列名
      iArray[5][1]="0px";                //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=3; 

      iArray[6]=new Array();
      iArray[6][0]="发起机构";             //列名
      iArray[6][1]="80px";                //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="调查机构";             //列名
      iArray[7][1]="80px";                //列宽
      iArray[7][2]=100;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="调查分配人";             //列名
      iArray[8][1]="100px";                //列宽
      iArray[8][2]=100;                  //列最大值
      iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="调查分配日期";             //列名
      iArray[9][1]="80px";                //列宽
      iArray[9][2]=100;                  //列最大值
      iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="调查结束日期";             //列名
      iArray[10][1]="80px";                //列宽
      iArray[10][2]=100;                  //列最大值
      iArray[10][3]=0;                    //是否允许输入,1表示允许，0表示不允许 

      LLInqApplyGrid = new MulLineEnter( "fm" , "LLInqApplyGrid" ); 
      //这些属性必须在loadMulLine前
      LLInqApplyGrid.mulLineCount = 0;   
      LLInqApplyGrid.displayTitle = 1;
      LLInqApplyGrid.locked = 1;
      LLInqApplyGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLInqApplyGrid.hiddenPlus=1;
      LLInqApplyGrid.hiddenSubtraction=1;
      LLInqApplyGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
