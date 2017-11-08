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
    document.all('QureyRiskCode').value = '';
    document.all('QueryState').value = '';
   document.all('QueryRiskCodeName').value = '';
    document.all('QueryStateName').value = '';
    document.all('QueryStartDate').value = '';
      document.all('QureyInsuAccNo').value = '';
    document.all('QueryName').value = '';
    
    document.all('RiskCode').value = '';
    document.all('RiskCodeName').value = '';
    document.all('InsuAccNo').value = '';
    document.all('InsuAccNoName').value = '';
    
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('InvestFlag').value = '';
     document.all('InvestFlagName').value = '';
    document.all('SRateDate').value = '';

    document.all('ARateDate').value = '';
    document.all('UnitPriceBuy').value = '';
    document.all('UnitPriceSell').value = '';
    document.all('RedeemRate').value = '';
    document.all('State').value = '';
    document.all('StateName').value = '';
    document.all('RedeemMoney').value = '';
    document.all('ComChgUnitCount').value = '';
    document.all('CompanyUnitCount').value = '';
    document.all('CustomersUnitCount').value = '';
document.all('ManageFee').value = '';

     document.all('Transact').value = '';
      
   document.all('DealDate').value = '';
     document.all('DoBatch').value = '';
     document.all('SKFlag').value = '';
  }
  catch(ex)
  {
    alert("在toulianInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  //try
  //{
    initInpBox();
    initPiceDate();
    initCollectivityGrid();
  //}
  //catch(re)
  //{
  //  alert("toulianInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  //}
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

      //iArray[1]=new Array();
      //iArray[1][0]="险种编码";         		//列名
      //iArray[1][1]="100px";            		//列宽
      //iArray[1][2]=100;            			//列最大值
      //iArray[1][3]=2;             			//是否允许输入,1表示允许，0表示不允许
      //iArray[1][4]="riskind";
      //iArray[1][15]="risktype3";
      //iArray[1][16]="#3#";
        
      iArray[1]=new Array();
      iArray[1][0]="保险帐户号码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="findinsuacc";
      iArray[1][15]="riskcode";
      iArray[1][17]="1";

      iArray[2]=new Array();
      iArray[2][0]="计价日期";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="买入价格";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="卖出价格";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="状态";         		//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;              //是否允许输入,1表示允许，0表示不允许
      iArray[5][10]="State";
      iArray[5][11]="0|^1|录入^2|复核正确^3|复核错误^4|确认正确^0|生效";
      
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
