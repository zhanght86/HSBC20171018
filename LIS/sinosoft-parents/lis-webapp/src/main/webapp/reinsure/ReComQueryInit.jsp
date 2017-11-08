<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-08-18
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
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initReComGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"初始化界面错误!");
  }
}

function initReComGrid()
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
	      iArray[1][0]="公司代码";    	//列名
	      iArray[1][1]="150px";            		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[2]=new Array();
	      iArray[2][0]="公司名称";         			//列名
	      iArray[2][1]="100px";            		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	 
	      iArray[3]=new Array();
	      iArray[3][0]="公司地址";         			//列名
	      iArray[3][1]="60px";            		//列宽
	      iArray[3][2]=60;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[4]=new Array();
	      iArray[4][0]="邮政编码";         			//列名
	      iArray[4][1]="60px";            		//列宽
	      iArray[4][2]=60;            			//列最大值
	      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[5]=new Array();
	      iArray[5][0]="传真";         			//列名
	      iArray[5][1]="100px";            		//列宽
	      iArray[5][2]=1000;            			//列最大值
	      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
				
	      ReComGrid = new MulLineEnter( "fm" , "ReComGrid" ); 
	      ReComGrid.mulLineCount = 0;   
	      ReComGrid.displayTitle = 1;
	      ReComGrid.canSel=1;
	      ReComGrid.locked = 1;	
				ReComGrid.hiddenPlus = 1;
				ReComGrid.hiddenSubtraction = 1;
	      ReComGrid.loadMulLine(iArray);  
	      ReComGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>