<%
//name :RateCardInput.jsp
//Creator :zz
//date :2008-06-20
//卡单费率表查询功能
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.bl.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
%>
<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('Riskcode').value = '';
    document.all('ProductPlan').value = '';
    document.all('InsuYear').value = '';
    document.all('InsuYearFlag').value = '';
    document.all('Prem').value = '';
  }
  catch(ex)
  {
    alert("RateCardQueryInit-initInpBox进行初始化是出现错误！！！！");
  }
}


//初始化函数
function initForm()
{
  try
  {
    initInpBox();
    initRateCardGrid();
  }
  catch(re)
  {
    alert("initForm:CertifyFeeQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initRateCardGrid()
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
      iArray[1][0]="险种编码";    	//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         			//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[3]=new Array();
      iArray[3][0]="保险年期";         			//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[4]=new Array();
      iArray[4][0]="保险年期单位";         			//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="保费";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=40;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="份数";         			//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=40;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[7]=new Array();
      iArray[7][0]="产品计划";         			//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=40;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      RateCardGrid = new MulLineEnter( "fm" , "RateCardGrid" ); 
      RateCardGrid.mulLineCount = 5;   
      RateCardGrid.displayTitle = 1;
	  RateCardGrid.hiddenSubtraction=1;
	  RateCardGrid.hiddenPlus=1;
      RateCardGrid.canSel=1;
      RateCardGrid.locked = 1;	
      RateCardGrid.loadMulLine(iArray);  
      RateCardGrid.detailInfo="单击显示详细信息";

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
