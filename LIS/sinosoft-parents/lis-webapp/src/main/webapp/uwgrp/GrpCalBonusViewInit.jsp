<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CutBonusInit.jsp
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                             

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
    document.all('FiscalYear').value = '';
    document.all('GrpContNo').value = '';
    document.all('RiskCode').value = '212401';
  }
  catch(ex)
  {
    alert("在GrpCalBonusViewInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
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
    alert("在GrpCalBonusViewInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("GrpCalBonusViewInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="分红年度";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="集体保单号";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=120;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="生效日期";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许            			

      iArray[4]=new Array();
      iArray[4][0]="分红比例";         	//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=80;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="分红状态";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=80;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][10] = "Status";
      iArray[5][11] = "0|^0|已设置^1|团单红利计算完毕^2|团单红利分配完毕^3|部分个单已计算^4|部分个单已分配";
      iArray[5][12] = "5";
      iArray[5][19] = "0";
      

      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.canChk = 0;
      PolGrid.hiddenPlus = 1;
	    PolGrid.hiddenSubtraction = 1;
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
