<%@include file="/i18n/language.jsp"%>
 <%
//Creator :张斌
//Date :2008-1-8
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
%>
<script type="text/javascript">
function initInpBox()
{
  try
  { 
  }
  catch(ex)
  {
    myAlert("进行初始化是出现错误！！！！");
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
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFeeRateGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
  }
}

function initFeeRateGrid()
{
	var iArray = new Array();
      
      try
      {
	      iArray[0]=new Array();
	      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	      iArray[0][1]="30px";         			//列宽
	      iArray[0][2]=10;          				//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[1]=new Array();
	      iArray[1][0]="费率表编号";    		//列名
	      iArray[1][1]="50px";            	//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[2]=new Array();
	      iArray[2][0]="费率表名称";      	//列名
	      iArray[2][1]="100px";            	//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	 
	      iArray[3]=new Array();
	      iArray[3][0]="费率表类型编码";      //列名
	      iArray[3][1]="60px";            	//列宽
	      iArray[3][2]=60;            			//列最大值
	      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[4]=new Array();
	      iArray[4][0]="费率表类型";      //列名
	      iArray[4][1]="50px";            	//列宽
	      iArray[4][2]=60;            			//列最大值
	      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[5]=new Array();
	      iArray[5][0]="费率表导入标记";      //列名
	      iArray[5][1]="50px";            	//列宽
	      iArray[5][2]=150;            			//列最大值
	      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[6]=new Array();
	      iArray[6][0]="费率表状态";      //列名
	      iArray[6][1]="50px";            	//列宽
	      iArray[6][2]=150;            			//列最大值
	      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[7]=new Array();
	      iArray[7][0]="适用方式";      //列名
	      iArray[7][1]="50px";            	//列宽
	      iArray[7][2]=150;            			//列最大值
	      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[8]=new Array();
	      iArray[8][0]="适用方式名";      //列名
	      iArray[8][1]="50px";            	//列宽
	      iArray[8][2]=150;            			//列最大值
	      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      				
	      FeeRateGrid = new MulLineEnter( "fm" , "FeeRateGrid" ); 
	      FeeRateGrid.mulLineCount = 0;   
	      FeeRateGrid.displayTitle = 1;
	      FeeRateGrid.canSel=1;
	      FeeRateGrid.locked = 1;	
				FeeRateGrid.hiddenPlus = 1;
				FeeRateGrid.hiddenSubtraction = 1;
	      FeeRateGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>
