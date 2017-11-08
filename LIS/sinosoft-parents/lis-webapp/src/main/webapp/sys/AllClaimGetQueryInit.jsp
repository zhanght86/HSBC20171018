<%
//程序名称：ClaimGetQueryInit.jsp
//程序功能：
//创建日期：2003-4-2
//创建人  ：lh
//修改人：刘岩松
//修改日期：2004-2-17
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{
  try
  {
    document.all('InsuredNo').value = tInsuredNo;
  }
  catch(ex)
  {
    alert("在AllClaimGetQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在AllClaimGetQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
		initPolGrid();
    showCaseInfo();
  }
  catch(re)
  {
    alert("AllClaimGetQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var PolGrid;
// 保单信息列表的初始化
function initPolGrid()
{
  var iArray = new Array();

  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            		//列宽
    iArray[0][2]=10;            			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="保单号";         		//列名
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="报案号码";         		//列名
    iArray[2][1]="150px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="立案号码";         		//列名
    iArray[3][1]="150px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="事故者类型";         		//列名
    iArray[4][1]="200px";            		//列宽
    iArray[4][2]=200;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="事故者姓名";         		//列名
    iArray[5][1]="100px";            		//列宽
    iArray[5][2]=200;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="报案日期";         		//列名
    iArray[6][1]="100px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="立案日期";         		//列名
    iArray[7][1]="100px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    

    PolGrid = new MulLineEnter( "fm" , "PolGrid" );
    PolGrid.mulLineCount = 0;
    PolGrid.displayTitle = 1;
    PolGrid.locked = 1;
    PolGrid.canSel = 1;
    PolGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}
</script>
