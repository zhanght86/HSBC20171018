<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupUWInit.jsp
//程序功能：集体人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
    // 保单查询条件
    fmQuery.all('GrpProposalContNo').value = '';
//    fmQuery.all('GrpMainProposalNo').value = GrpMainPolNo;
    fmQuery.all('PrtNoHide').value = PrtNo;
    fmQuery.all('Operator').value = '<%= strOperator %>';    
  }
  catch(ex)
  {
    alert("在ContUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 保单基本信息显示框的初始化（单记录部分）
function initPolBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("在ContUWInit.jsp-->InitPolBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    //initInpBox();
    //initPolBox();
    //initPolGrid();
    fmQuery.all('GrpContNo').value = GrpContNo;
    
		   
	
	  if(isContPlan()==false)
	  {
	    initGrpGrid(); 
		  initGrpPolFeeGrid(); 
	    querygrp();
	  }
    else
    {
    	initPlanGrid(); 
	  	initPlanRiskGrid(); 
      queryContPlan();
    }
    showInfo();
  
    makeWorkFlow();
 
   ctrlButtonDisabled(GrpContNo);
   }
  catch(re)
  {
    alert("ContUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="个人投保单号";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保人";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种版本";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="被保人";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="管理机构";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PolGrid = new MulLineEnter( "fmQuery" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
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

// 保单信息列表的初始化
function initGrpGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="集体投保单号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="投保单印刷号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保单位";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种编码";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="险种名称";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="交费周期";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;  
      
      iArray[7]=new Array();
      iArray[7][0]="投保人数";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0; 
      
      iArray[8]=new Array();
      iArray[8][0]="总保额";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0; 
      
      iArray[9]=new Array();
      iArray[9][0]="保险起期";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0; 
      
      iArray[10]=new Array();
      iArray[10][0]="保险止期";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0; 
     

      iArray[11]=new Array();
      iArray[11][0]="集体险种号码";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=3;  

      iArray[12]=new Array();
      iArray[12][0]="管理机构";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[13]=new Array();
      iArray[13][0]="核保结论";         		//列名
      iArray[13][1]="60px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
       
        
      GrpGrid = new MulLineEnter( "fmQuery" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 3;   
      GrpGrid.displayTitle = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.selBoxEventFuncName = "getfee";          
      GrpGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //GrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
function initGrpPolFeeGrid()
{
      var iArray = new Array();
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="实交保费";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="应交保费";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="折扣费率";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="管理费用率";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="销售费用率";         	//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许 
      
      GrpPolFeeGrid = new MulLineEnter( "fmQuery" , "GrpPolFeeGrid" ); 
      GrpPolFeeGrid.mulLineCount = 1;   
      GrpPolFeeGrid.displayTitle = 1;
      GrpPolFeeGrid.hiddenPlus = 1;
      GrpPolFeeGrid.hiddenSubtraction = 1;
      GrpPolFeeGrid.locked = 1;
      GrpPolFeeGrid.canSel = 0;
      GrpPolFeeGrid.loadMulLine(iArray);    
}



// 险种信息列表的初始化
function initPlanGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="产品组合编码";
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="产品组合名称";
		iArray[2][1]="160px";
		iArray[2][2]=50;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="交费期间";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="应保人数";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;

		iArray[5]=new Array();
		iArray[5][0]="参保人数";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="预期保费";
		iArray[6][1]="0px";
		iArray[6][2]=20;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="保费合计";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="投保比例";
		iArray[8][1]="140px";
		iArray[8][2]=20;
		iArray[8][3]=0;
		

		PlanGrid = new MulLineEnter( "fmQuery" , "PlanGrid" );
		//这些属性必须在loadMulLine前
		PlanGrid.mulLineCount = 0;
		PlanGrid.displayTitle = 1;
		PlanGrid.selBoxEventFuncName = "getPlanRiskDetail"; 
		PlanGrid.canSel =1;
		PlanGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		PlanGrid.hiddenSubtraction=1;
		PlanGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

// 险种信息列表的初始化
function initPlanRiskGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种编码";
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="0px";
		iArray[2][2]=20;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="责任编码";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="责任名称";
		iArray[4][1]="100px";
		iArray[4][2]=20;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="保费合计";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="保额合计";
		iArray[6][1]="60px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		

		PlanRiskGrid = new MulLineEnter("fmQuery","PlanRiskGrid");
		//这些属性必须在loadMulLine前
		PlanRiskGrid.mulLineCount = 0;
		PlanRiskGrid.displayTitle = 1;
		PlanRiskGrid.canSel =0; 
		PlanRiskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		PlanRiskGrid.hiddenSubtraction=1;
		PlanRiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
