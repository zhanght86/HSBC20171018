<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpCalBonusInit.jsp
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
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
	// 保单查询条件
    document.all('FiscalYear').value = '';
    document.all('GrpContNo').value = '';
    document.all('RiskCode').value = '212401';
  }
  catch(ex)
  {
    alert("在GrpCalBonusInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在GrpCalBonusInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();

    initSelBox();

	initPolGrid();
  }
  catch(re)
  {
    alert("GrpCalBonusInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initPolGrid()
{                               
	  var iArray = new Array();

    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="团体保单号";        //列名
      iArray[1][1]="120px";            	//列宽
      iArray[1][2]=30;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="团单名称";         	//列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=30;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="会计年度";         	//列名
      iArray[3][1]="60px";            	//列宽
      iArray[3][2]=30;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种名称";         	//列名
      iArray[4][1]="60px";            	//列宽
      iArray[4][2]=30;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="生效日期";         	//列名
      iArray[5][1]="60px";            	//列宽
      iArray[5][2]=30;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="分红比例"         	//列名
      iArray[6][1]="60px";            	//列宽
      iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="分红状态";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=30;            			//列最大值
      iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[7][10] = "Status";
      iArray[7][11] = "0|^1|团单红利计算完毕^3|部分个单已计算^4|部分个单已分配";
      iArray[7][12] = "7";
      iArray[7][19] = "0";

      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.hiddenPlus = 1;
	    PolGrid.hiddenSubtraction = 1;
      PolGrid.canSel = 1;
      PolGrid.canChk = 0;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
