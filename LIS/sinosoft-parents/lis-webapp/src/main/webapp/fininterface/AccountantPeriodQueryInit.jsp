<%
//Creator :范昕	
//Date :2008-08-05
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
%>
<script language="JavaScript">
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("网页超时，请重新登录");
		return;
	}
	String strOperator = globalInput.Operator;
%>   
function initInpBox()
{
  try
  {
    fm.reset();      
    document.all('Year').value = '';
    document.all('Month').value = '';  
  	fm.Operator.value = '<%= strOperator %>';     
    document.all('State').value = '';   
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
    alert("AccountantPeriodInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initAccountantPeriodGrid();
  }
  catch(re)
  {
    alert("AccountantPeriodInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAccountantPeriodGrid()
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
      iArray[1][0]="会计年度";    	//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="会计月度";         			//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="月度起期";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="月度止期";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="管理机构";         			//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="操作员";         			//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="入机日期";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[8]=new Array();
      iArray[8][0]="入机时间";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[9]=new Array();
      iArray[9][0]="状态";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用


      AccountantPeriodGrid = new MulLineEnter( "document" , "AccountantPeriodGrid" ); 
      AccountantPeriodGrid.mulLineCount = 5;   
      AccountantPeriodGrid.displayTitle = 1;
      AccountantPeriodGrid.canSel=1;
      AccountantPeriodGrid.locked = 1;	
	  AccountantPeriodGrid.hiddenPlus = 1;
	  AccountantPeriodGrid.hiddenSubtraction = 1;
      AccountantPeriodGrid.loadMulLine(iArray);  
      AccountantPeriodGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
