<%
//Creator :范昕	
//Date :2008-09-19
%>

<!--用户校验类-->


<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
%>
<script language="JavaScript">

function initInpBox()
{
  try
  {

    fm.reset();
    document.all('RuleDealBatchNo').value = RuleDealBatchNo;
    document.all('DataSourceBatchNo').value = DataSourceBatchNo;
  }
  catch(ex)
  {
    alert("进行初始化是出现错误！！！！");
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
    alert("FIRuleDealErrLogInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{

  try
  {
    initInpBox();
    initSelBox();
    initFIRuleDealErrLogGrid();
  }
  catch(re)
  {
    alert("FIRuleDealErrLogInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initFIRuleDealErrLogGrid()
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
      iArray[1][0]="校验批次号码";    	//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="数据源批次号码";         			//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="凭证类型";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="错误信息";         			//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="业务号码标识";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="校验计划";         			//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="校验规则";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="错误状态";         			//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="错误流水号码";         			//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=0;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      

      FIRuleDealErrLogGrid = new MulLineEnter( "fm" , "FIRuleDealErrLogGrid" ); 
      FIRuleDealErrLogGrid.mulLineCount = 10;   
      FIRuleDealErrLogGrid.displayTitle = 1;
      FIRuleDealErrLogGrid.canSel=1;
      FIRuleDealErrLogGrid.selBoxEventFuncName = "ReturnData";
      FIRuleDealErrLogGrid.locked = 1;	
	  FIRuleDealErrLogGrid.hiddenPlus = 1;
	  FIRuleDealErrLogGrid.hiddenSubtraction = 1;
      FIRuleDealErrLogGrid.loadMulLine(iArray);  
      FIRuleDealErrLogGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
