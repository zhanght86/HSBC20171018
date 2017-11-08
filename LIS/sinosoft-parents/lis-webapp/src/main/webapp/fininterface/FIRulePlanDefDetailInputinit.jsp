<%
//Creator :范昕	
//Date :2008-08-18
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
    fm.all('VersionNo').value = VersionNo;
    fm.all('RulePlanID').value = RulePlanID;
  	fm.all('RuleID').value = '';        
  	fm.all('RuleID1').value = '';
    fm.all('RuleIDName').value = '';
    fm.all('Sequence').value = '';    
    fm.all('RuleState').value = '';   
    fm.all('RuleStateName').value = '';  
	if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			fm.all('addbutton').disabled = true;	
			fm.all('updatebutton').disabled = true;			
			fm.all('deletebutton').disabled = true;	
		} 
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
    alert("FIRulePlanDefDetailInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{

  try
  {
    initInpBox();
    initSelBox();
    initFIRulePlanDefDetailInputGrid();
  }
  catch(re)
  {
    alert("FIRulePlanDefDetailInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initFIRulePlanDefDetailInputGrid()
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
      iArray[1][0]="版本编号";    	//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="校验计划编码";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="校验规则编码";         			//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="校验顺序";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="校验规则状态";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      FIRulePlanDefDetailInputGrid = new MulLineEnter( "fm" , "FIRulePlanDefDetailInputGrid" ); 
      FIRulePlanDefDetailInputGrid.mulLineCount = 0;   
      FIRulePlanDefDetailInputGrid.displayTitle = 1;
      FIRulePlanDefDetailInputGrid.canSel=1;
      FIRulePlanDefDetailInputGrid.selBoxEventFuncName = "ReturnData";
      FIRulePlanDefDetailInputGrid.locked = 1;	
	 	 	FIRulePlanDefDetailInputGrid.hiddenPlus = 1;
	  	FIRulePlanDefDetailInputGrid.hiddenSubtraction = 1;
      FIRulePlanDefDetailInputGrid.loadMulLine(iArray);  
      FIRulePlanDefDetailInputGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
