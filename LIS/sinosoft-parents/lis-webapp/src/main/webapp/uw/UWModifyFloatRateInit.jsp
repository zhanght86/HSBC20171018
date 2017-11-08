<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWCustomerQualityInit.jsp
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
           
<script language="JavaScript">
	
function initAll() {
    
}

function initForm() {
  try {
	initRiskFloatRateGrid();
	initFloatRate();
	initSpecIdea();
  }
  catch(re) {
    alert("UWModifyFloatRateInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initHide()
{
	document.all('ContNo').value=contNo;
}

function initRiskFloatRateGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种名称";          		//列名
      iArray[1][1]="90px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="保险期间";          		//列名
      iArray[2][1]="40px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="当前浮动费率";         			//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="修改浮动费率";      	   		//列名
      iArray[4][1]="40px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

     
      iArray[5]=new Array();
      iArray[5][0]="保单号";      	   		//列名
      iArray[5][1]="0px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
  
      iArray[6]=new Array();
      iArray[6][0]="领取类型";      	   		//列名
      iArray[6][1]="0px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

     
      RiskFloatRateGrid = new MulLineEnter( "fm" , "RiskFloatRateGrid" ); 
      //这些属性必须在loadMulLine前
      RiskFloatRateGrid.mulLineCount = 3;   
      RiskFloatRateGrid.displayTitle = 1;
      RiskFloatRateGrid.hiddenPlus=1;
      RiskFloatRateGrid.locked=0;
      RiskFloatRateGrid.canSel=0;
      RiskFloatRateGrid.canChk=1;
      RiskFloatRateGrid.selBoxEventFuncName="";
      RiskFloatRateGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


