<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2007-2-7
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 GlobalInput tG = new GlobalInput();
 tG=(GlobalInput)session.getAttribute("GI");
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

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
    myAlert("2在LRGetBsnsDataInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initRIDataGrid();
    initRIDataDetailGrid();
  }
  catch(re)
  {
    myAlert("3在LRGetBsnsDataInit.jsp-->"+"初始化界面错误!");
  }
}


// 接口业务数据
function initRIDataGrid()
{	
	var iArray = new Array();   
  try
  {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="保单号";
		iArray[1][1]="80px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="业务类型";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;
				
		iArray[3]=new Array();
		iArray[3][0]="被保人客户号";
		iArray[3][1]="80px";
		iArray[3][2]=85;
		iArray[3][3]=0;
				
		iArray[4]=new Array();
		iArray[4][0]="被保人姓名"; 
		iArray[4][1]="80px"; 
		iArray[4][2]=85;
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="再保合同编号";
		iArray[5][1]="100px";            		 
		iArray[5][2]=85;            			   
		iArray[5][3]=0;
				
		iArray[6]=new Array();
		iArray[6][0]="再保方案编号";
		iArray[6][1]="100px";            		 
		iArray[6][2]=85;            			   
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="累计风险编号";
		iArray[7][1]="100px";            		 
		iArray[7][2]=85;            			   
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="当前风险保额";
		iArray[8][1]="80px";
		iArray[8][2]=85;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="风险保额变化量";
		iArray[9][1]="80px";
		iArray[9][2]=85;
		iArray[9][3]=0;
						
		iArray[10]=new Array();
		iArray[10][0]="累计风险保额";
		iArray[10][1]="80px";
		iArray[10][2]=85;
		iArray[10][3]=0;

		iArray[11]=new Array();
		iArray[11][0]="理赔额";
		iArray[11][1]="80px";
		iArray[11][2]=85;
		iArray[11][3]=0;
		
		iArray[12]=new Array();
		iArray[12][0]="业务日期";
		iArray[12][1]="80px";
		iArray[12][2]=85;
		iArray[12][3]=0;

		iArray[13]=new Array();
		iArray[13][0]="EventNo";
		iArray[13][1]="80px";
		iArray[13][2]=85;
		iArray[13][3]=3;
						
		RIDataGrid = new MulLineEnter( "fm" , "RIDataGrid" ); 
		//这些属性必须在loadMulLine前
		RIDataGrid.mulLineCount = 0;   
		RIDataGrid.displayTitle = 1;
		RIDataGrid.locked = 0;
		RIDataGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
		RIDataGrid.selBoxEventFuncName ="queryDetail"; //响应的函数名，不加扩号   
		RIDataGrid.hiddenPlus=1;
		RIDataGrid.hiddenSubtraction=1;
		RIDataGrid.loadMulLine(iArray); 
  }
  catch(ex)
  {
      myAlert(ex);
  }
}

// 分保详细信息
function initRIDataDetailGrid()
{	
	var iArray = new Array();   
  try
  {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="保单号";
		iArray[1][1]="80px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种编码";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="分保公司编码";
		iArray[3][1]="80px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="分保公司名称";
		iArray[4][1]="80px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="分保区域";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;
						
		iArray[6]=new Array();
		iArray[6][0]="分保比例";
		iArray[6][1]="80px";
		iArray[6][2]=85;
		iArray[6][3]=0;
				
		iArray[7]=new Array();
		iArray[7][0]="分保保额"; 
		iArray[7][1]="80px"; 
		iArray[7][2]=85;
		iArray[7][3]=0; 

		iArray[8]=new Array();
		iArray[8][0]="分保保费";
		iArray[8][1]="100px";            		 
		iArray[8][2]=85;            			   
		iArray[8][3]=0;
				
		iArray[9]=new Array();
		iArray[9][0]="分保佣金";
		iArray[9][1]="100px";            		 
		iArray[9][2]=85;            			   
		iArray[9][3]=0;

		iArray[10]=new Array();
		iArray[10][0]="理赔摊回金额";
		iArray[10][1]="80px";
		iArray[10][2]=85;
		iArray[10][3]=0;
				
		iArray[11]=new Array();
		iArray[11][0]="分保业务日期";
		iArray[11][1]="80px";
		iArray[11][2]=85;
		iArray[11][3]=0;
		
		RIDataDetailGrid = new MulLineEnter( "fm" , "RIDataDetailGrid" ); 
		//这些属性必须在loadMulLine前
		RIDataDetailGrid.mulLineCount = 0;   
		RIDataDetailGrid.displayTitle = 1;
		RIDataDetailGrid.locked = 0;
		RIDataDetailGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
		//RIDataGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号   
		RIDataDetailGrid.hiddenPlus=1;
		RIDataDetailGrid.hiddenSubtraction=1;
		RIDataDetailGrid.loadMulLine(iArray); 
  }
  catch(ex)
  {
      myAlert(ex);
  }
}
</script>