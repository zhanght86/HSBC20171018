<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2007-01-14
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
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<script type="text/javascript">
function initInpBox()
{
  try
  { 
  }
  catch(ex)
  {
    myAlert("初始化界面错误!");
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
    initProfitLossGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"初始化界面错误!");
  }
}

function initProfitLossGrid()
{
	var iArray = new Array();
      
      try
      {
	      iArray[0]=new Array();
	      iArray[0][0]="NO.";         			//列名（此列为顺序号，列名无意义，而且不显示）
	      iArray[0][1]="30px";         			//列宽
	      iArray[0][2]=10;          				//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
				
	      iArray[1]=new Array();
	      iArray[1][0]="纯溢手续费序号";   	//列名
	      iArray[1][1]="70px";          		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[2]=new Array();
	      iArray[2][0]="再保合同代号";    	//列名
	      iArray[2][1]="100px";          		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许
				
	      iArray[3]=new Array();
	      iArray[3][0]="再保合同名称";      //列名
	      iArray[3][1]="150px";          		//列宽
	      iArray[3][2]=100;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[4]=new Array();
	      iArray[4][0]="再保公司代码";    	//列名
	      iArray[4][1]="100px";          		//列宽
	      iArray[4][2]=100;            			//列最大值
	      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许
				
	      iArray[5]=new Array();
	      iArray[5][0]="再保公司名称";      //列名
	      iArray[5][1]="150px";          		//列宽
	      iArray[5][2]=100;            			//列最大值
	      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	 			
	      iArray[6]=new Array();
	      iArray[6][0]="计算年度";      		//列名
	      iArray[6][1]="60px";           		//列宽
	      iArray[6][2]=60;            			//列最大值
	      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[7]=new Array();
	      iArray[7][0]="纯溢手续费金额";		//列名
	      iArray[7][1]="80px";           		//列宽
	      iArray[7][2]=80;            			//列最大值
	      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      ProfitLossGrid = new MulLineEnter( "fm" , "ProfitLossGrid" ); 
	      ProfitLossGrid.mulLineCount = 0;   
	      ProfitLossGrid.displayTitle = 1;
	      ProfitLossGrid.canSel=1;
	      ProfitLossGrid.locked = 1;	
		  ProfitLossGrid.hiddenPlus = 1;
		  ProfitLossGrid.hiddenSubtraction = 1;
	      ProfitLossGrid.loadMulLine(iArray);  
	      ProfitLossGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>