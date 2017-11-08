<%
//程序名称：AllFeeQueryPDetailInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<script src="../common/javascript/Common.js" type=""></SCRIPT>

<script language="JavaScript" type="">

// 输入框的初始化（单记录部分）
function initInpBox()
{

  try
  {
    // 保单查询条件
    document.all('PayNo').value = tPayNo;
    document.all('SumActuPayMoney').value = tSumActuPayMoney;
  }
  catch(ex)
  {
    alert("在AllFeeQueryPDetailInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();
    ValidPremCal();
    CurrentPremCal();
    easyQueryClick();
  }
  catch(re)
  {
    alert("AllFeeQueryPDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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

    //      iArray[7]=new Array();
    //      iArray[7][0]="保单号码";         		//列名
    //      iArray[7][1]="0px";            		//列宽
    //      iArray[7][2]=100;            			//列最大值
    //      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //
    //      iArray[8]=new Array();
    //      iArray[8][0]="集体保单号码";         		//列名
    //      iArray[8][1]="0px";            		//列宽
    //      iArray[8][2]=100;            			//列最大值
    //      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //
    //      iArray[9]=new Array();
    //      iArray[9][0]="总单/合同号码";         		//列名
    //      iArray[9][1]="0px";            		//列宽
    //      iArray[9][2]=200;            			//列最大值
    //      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="交费收据号码";         		//列名
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=200;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="交费目的分类";         		//列名
    iArray[2][1]="100px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    //  iArray[2][10] = "PayAimClass";
    //  iArray[2][11] = "0|^1|个人交费^2|集体交费";
    //  iArray[2][12] = "11";
    //  iArray[2][19] = "0";

    iArray[3]=new Array();
    iArray[3][0]="总实交金额";         		//列名
    iArray[3][1]="100px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=7;              			//是否允许输入,1表示允许，0表示不允许
		iArray[3][23]="0";
		
    iArray[4]=new Array();
    iArray[4][0]="交费间隔";         		//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="交费核销日期";         		//列名
    iArray[5][1]="100px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=8; 									//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="交费类型";         		//列名
    iArray[6][1]="100px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    // iArray[6][10] = "PayType";
    // iArray[6][11] = "0|^ZC|正常^YET|本次余额^YEL|使用上次余额";
    // iArray[6][12] = "11";
    // iArray[6][19] = "0";


    iArray[7]=new Array();
    iArray[7][0]="现交至日期";         		//列名
    iArray[7][1]="100px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=8; 									//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="交费次数";         		//列名
    iArray[8][1]="60px";            		//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0; 									//是否允许输入,1表示允许，0表示不允许
		
		iArray[9]=new Array();
		iArray[9][0]="币种";
		iArray[9][1]="60px";
		iArray[9][2]=100;
		iArray[9][3]=2;
		iArray[9][4]="currency";
		
    PolGrid = new MulLineEnter( "fm" , "PolGrid" );
    //这些属性必须在loadMulLine前
    PolGrid.mulLineCount = 5;
    PolGrid.displayTitle = 1;
    PolGrid.locked = 1;
    PolGrid.canSel = 1;
    PolGrid.hiddenPlus = 1;
    PolGrid.hiddenSubtraction = 1;
    PolGrid.loadMulLine(iArray);

    //这些操作必须在loadMulLine后面
  }
  catch(ex)
  {
    alert(ex);
  }
}

</script>
