 <%
//程序名称：FIRulePlanDefQueryInputInit.jsp
//程序功能：校验计划定义
//创建日期：2008-09-17
//创建人  ：范昕  
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) 
	{
		out.println("网页超时，请重新登录");
		return;
	}
	String strOperator = globalInput.Operator;
%>                          
<script language="JavaScript">
function initInpBox()
{
  try
  {     
  	fm.reset();
  	document.all('VersionNo').value = VersionNo;      
	  document.all('RulesPlanID').value = '';        
    document.all('RulesPlanName').value = '';
    document.all('RulePlanState').value = '';        
    document.all('RulePlanStateName').value = '';  
    document.all('CallPointID').value = '';        
    document.all('CallPointIDName').value = '';        
  }
  catch(ex)
  {
    alert("在FIRulePlanDefQueryInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在FIRulePlanDefQueryInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFIRulePlanDefQueryGrid();
  }
  catch(re)
  {
    alert("在FIRulePlanDefQueryInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initFIRulePlanDefQueryGrid()
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
      iArray[2][0]="校验计划编码";    	//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="校验计划名称";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="校验计划状态";         			//列名
      iArray[4][1]="70px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
			iArray[5]=new Array();
      iArray[5][0]="调用节点ID";         			//列名
      iArray[5][1]="70px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="描述";         			//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      FIRulePlanDefQueryGrid = new MulLineEnter( "document" , "FIRulePlanDefQueryGrid" ); 
      FIRulePlanDefQueryGrid.mulLineCount = 5;   
      FIRulePlanDefQueryGrid.displayTitle = 1;
      FIRulePlanDefQueryGrid.canSel=1;
      FIRulePlanDefQueryGrid.locked = 1;	
	 	 	FIRulePlanDefQueryGrid.hiddenPlus = 1;
	  	FIRulePlanDefQueryGrid.hiddenSubtraction = 1;
      FIRulePlanDefQueryGrid.loadMulLine(iArray);  
      FIRulePlanDefQueryGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
