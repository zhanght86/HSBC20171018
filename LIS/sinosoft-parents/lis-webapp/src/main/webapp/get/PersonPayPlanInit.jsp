
<%
	//程序名称：PersonPayPlanInput.jsp
	//程序功能：
	//创建日期：2002-07-24 08:38:44
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//添加页面控件的初始化。
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
  	document.all('ManageCom').value = '';
  	document.all('timeEnd').value = '';
  	document.all('ContNo').value = '';
    document.all('PolNo').value = '';
    document.all('InsuredNo').value = '';
    //document.all('AppntNo').value = '';	
	//fm.all('timeStart').value = '';	
	document.all('SerialNo').value = '';
  }
  catch(ex)
  {
    alert("在PersonPayPlanInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
//    setOption("t_sex","0=男&1=女&2=不详");
//    setOption("sex","0=男&1=女&2=不详");
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");
  }
  catch(ex)
  {
    alert("在PersonPayPlanInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initGetDrawGrid()
  {
      var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="给付通知书号";    	//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="保单号";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人号";         			//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="应付日期";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="应付金额";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      GetGrid = new MulLineEnter( "document" , "GetGrid" );
      //这些属性必须在loadMulLine前
      GetGrid.mulLineCount = 5;
      GetGrid.displayTitle = 1;
      GetGrid.canSel = 1;
      GetGrid.canChk = 0;
      GetGrid.hiddenPlus=1;
      GetGrid.hiddenSubtraction=1;
      GetGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      //SubPayGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initGetAccGrid()
{
    var iArray = new Array();
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="保单号";    	//列名
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=80;            			//列最大值
    iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="险种编码";         			//列名
    iArray[2][1]="50px";            		//列宽
    iArray[2][2]=80;            			//列最大值
    iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="险种名称";         			//列名
    iArray[3][1]="150px";            		//列宽
    iArray[3][2]=80;            			//列最大值
    iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="生存金类型";         			//列名
    iArray[4][1]="120px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="应付日期";         		//列名
    iArray[5][1]="120px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="应付金额";         		//列名
    iArray[6][1]="120px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许

    GetGridInsurAcc = new MulLineEnter( "document" , "GetGridInsurAcc" );
    //这些属性必须在loadMulLine前
    GetGridInsurAcc.mulLineCount = 5;
    GetGridInsurAcc.displayTitle = 1;
    GetGridInsurAcc.canSel = 1;
    GetGridInsurAcc.canChk = 0;
    GetGridInsurAcc.hiddenPlus=1;
    GetGridInsurAcc.hiddenSubtraction=1;
    GetGridInsurAcc.loadMulLine(iArray);
    //这些操作必须在loadMulLine后面
    //SubPayGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initForm()
{
  try
  {
    initInpBox();
    //initSelBox();
    initGetDrawGrid();
    initGetAccGrid();
  }
  catch(re)
  {
    alert("PersonPayPlanInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>
