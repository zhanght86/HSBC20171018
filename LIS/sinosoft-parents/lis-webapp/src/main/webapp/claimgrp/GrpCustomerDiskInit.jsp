<%
//程序名称：ProposalCopyInput.jsp
//程序功能：
//创建日期：2002-08-21 09:25:18
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<script language="JavaScript">
function initInpBox()
{
  try
  {
    fm.all('FileName').value = '';
    fm.all('RgtNo').value = "<%=request.getParameter("RgtNo")%>";
    fm.all('Operator').value = "<%= tGlobalInput.Operator %>";
  }
  catch(ex)
  {
    alert("在ProposalCopyInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initDiskErrQueryGrid();
  }
  catch(re)
  {
    alert("ProposalCopyInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initDiskErrQueryGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";  //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";  //列宽
    iArray[0][2]=10;    //列最大值
    iArray[0][3]=0;      //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="理赔号";
    iArray[1][1]="80px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="客户号";
    iArray[2][1]="80px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="出险人";
    iArray[3][1]="60px";
    iArray[3][2]=100;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="错误信息";
    iArray[4][1]="200px";
    iArray[4][2]=100;
    iArray[4][3]=0;

    iArray[5]=new Array();
    iArray[5][0]="批次号";
    iArray[5][1]="10px";
    iArray[5][2]=100;
    iArray[5][3]=3;

    iArray[6]=new Array();
    iArray[6][0]="导入序号";
    iArray[6][1]="60px";
    iArray[6][2]=100;
    iArray[6][3]=0;
    
    iArray[7]=new Array();
    iArray[7][0]="导入操作员";
    iArray[7][1]="80px";
    iArray[7][2]=100;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
    iArray[8][0]="导入日期";
    iArray[8][1]="80px";
    iArray[8][2]=100;
    iArray[8][3]=0;        

    iArray[9]=new Array();
    iArray[9][0]="导入时间";
    iArray[9][1]="80px";
    iArray[9][2]=100;
    iArray[9][3]=0;       

    DiskErrQueryGrid = new MulLineEnter( "fmquery" , "DiskErrQueryGrid" );
    //这些属性必须在loadMulLine前
    DiskErrQueryGrid.mulLineCount = 10;
    DiskErrQueryGrid.displayTitle = 1;
    DiskErrQueryGrid.locked = 0;
    DiskErrQueryGrid.hiddenPlus=1;
    DiskErrQueryGrid.canChk =0; //多选按钮，1显示，0隐藏
    DiskErrQueryGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    DiskErrQueryGrid.hiddenSubtraction=1;
    DiskErrQueryGrid.loadMulLine(iArray);
    //这些操作必须在loadMulLine后面
  }
  catch(ex)
  {
    alert(ex);
  }
}
/*
function initDiv()
{
  divImport.style.display='none';
}
*/
</script>
