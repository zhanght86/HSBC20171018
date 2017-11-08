<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpCutPersonBonusInit.jsp
//程序功能：分红处理
//创建日期：2005-08-15
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {
		//
  }
  catch(ex)
  {
    alert("在GrpCutPersonBonusInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在GrpCutPersonBonusInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();

    initSelBox();

		initGrpPolGrid();
		
		queryGrpPol();
  }
  catch(re)
  {
    alert("GrpCutPersonBonusInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initGrpPolGrid()
{                               
  var iArray = new Array();
    
  try
  {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="50px";          		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="个人保单号";      	//列名
      iArray[1][1]="120px";           	//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="团体保单号";     		//列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="会计年度";         	//列名
      iArray[3][1]="80px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="帐户类型";       		//列名
      iArray[4][1]="100px";          		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="红利金额";       		//列名
      iArray[5][1]="100px";          		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="应领日期";       		//列名
      iArray[6][1]="100px";          		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  
      GrpPolGrid = new MulLineEnter( "document" , "GrpPolGrid" ); 
      //这些属性必须在loadMulLine前
			GrpPolGrid.mulLineCount = 5;   
			GrpPolGrid.displayTitle = 1;
			GrpPolGrid.locked = 1;
			GrpPolGrid.canSel = 1;
			GrpPolGrid.canChk = 0;
			GrpPolGrid.hiddenSubtraction=1;
			GrpPolGrid.hiddenPlus=1;
			GrpPolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //GrpPolGrid.setRowColData(1,1,"asdf");
  }
  catch(ex)
  {
    alert(ex);
  }
}

</script>
