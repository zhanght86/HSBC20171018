<%
//程序名称：ClientConjoinQueryInit.jsp
//程序功能：
//创建日期：2002-08-19
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('QueryState').value = '';
    document.all('QueryStateName').value = '';
    document.all('QueryStartDate').value = '';
      document.all('QureyInsuAccNo').value = '';
    document.all('QueryName').value = '';
    document.all('InsuAccNo').value = '';
    document.all('InsuAccNoName').value = '';
    
    document.all('StartDate').value = '';
    document.all('SKFlag').value = '';
    document.all('SKFlagName').value = '';
    //document.all('InvestFlagName').value = '';
    //document.all('SRateDate').value = '';

    //document.all('ARateDate').value = '';
    document.all('InsuTotalMoney').value = '';
    document.all('Liabilities').value = '';
    //document.all('RedeemRate').value = '';
    document.all('State').value = '1';
    document.all('StateName').value = '1-录入';
    //document.all('RedeemMoney').value = '';
	document.all('CompanyUnitCount').value = '';
	document.all('ComChgUnitCount').value = '0';
	document.all('CustomersUnitCount').value = '';
	myCheckDate = '';
	myCheckInsuAccNo = '';

    document.all('Transact').value = '';

  }
  catch(ex)
  {
    alert("在toulianInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initCollectivityGrid();
  }
  catch(re)
  {
    alert("toulianInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initCollectivityGrid()
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
      iArray[1][0]="投资帐户号码";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="findinsuacc";
      iArray[1][15]="riskcode";
      iArray[1][17]="1";

      iArray[2]=new Array();
      iArray[2][0]="资产评估日期";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      iArray[3]=new Array();
      iArray[3][0]="买入价格";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      iArray[4]=new Array();
      iArray[4][0]="卖出价格";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="账户总资产";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="负债";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="未实现利得营业税";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="账户资产管理费";         		//列名
      iArray[8][1]="90px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="公司投资单位数";         		//列名
      iArray[9][1]="90px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="公司变动单位数";         		//列名
      iArray[10][1]="90px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="状态";         		//列名
      iArray[11][1]="30px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=2;              //是否允许输入,1表示允许，0表示不允许
      iArray[11][10]="State";
      iArray[11][11]="0|^1|录入^2|复核正确^3|复核错误^4|确认正确^5|确认错误^0|生效";
      
      CollectivityGrid = new MulLineEnter( "fm" , "CollectivityGrid" ); 
      //这些属性必须在loadMulLine前
      
      CollectivityGrid.mulLineCount = 5;
      CollectivityGrid.displayTitle = 1;
      CollectivityGrid.hiddenPlus = 1;
    //  CollectivityGrid.locked = 0;
      CollectivityGrid.hiddenSubtraction = 1;
      CollectivityGrid.canSel=1;
      CollectivityGrid.selBoxEventFuncName = "ShowPlan";
      CollectivityGrid.loadMulLine(iArray);
      
   //    CollectivityGrid.mulLineCount = 10;   
  //    CollectivityGrid.displayTitle = 1;
    //  CollectivityGrid.locked = 1;
   ///   CollectivityGrid.hiddenPlus=1;
    //  CollectivityGrid.hiddenSubtraction=1;      
    //  CollectivityGrid.canSel = 1;
    //  CollectivityGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
