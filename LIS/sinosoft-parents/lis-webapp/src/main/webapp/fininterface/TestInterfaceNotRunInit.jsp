<%@include file="/i18n/language.jsp"%>
<% 
//程序名称：TestInterfaceNotRunInit.jsp
%>
<!--用户校验�?-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
     //添加页面控件的初始化�?

%>                            

<script language="JavaScript">

function initInpBox()
{
  try
  {
	//查询条件置空
	fm.all('checkData').value = '';
	fm.all('checkDataName').value = '';
	fm.all('StartDay').value = '';
	fm.all('EndDay').value = '';
  }
  catch(ex)
  {
    myAlert("<%=bundle.getString("M0000250951")%>");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initCheckQueryDataGrid();  //初始化共享工作池
  }
  catch(re)
  {
    myAlert("<%=bundle.getString("M0000250952")%>");
  }
}

function initCheckQueryDataGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="<%=bundle.getString("M0000047081")%>";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大�??

      iArray[0][3]=0;              			//是否允许输入,1表示允许�?0表示不允�?


      iArray[1]=new Array();
      iArray[1][0]="<%=bundle.getString("M0000054839")%>";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=170;            			//列最大�??

      iArray[1][3]=0;              			//是否允许输入,1表示允许�?0表示不允�?


      iArray[2]=new Array();
      iArray[2][0]="<%=bundle.getString("M0000250960")%>";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大�??

      iArray[2][3]=0;              			//是否允许输入,1表示允许�?0表示不允�?


      iArray[3]=new Array();
      iArray[3][0]="<%=bundle.getString("M0000250962")%>";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大�??

      iArray[3][3]=0;              			//是否允许输入,1表示允许�?0表示不允�? 
      
      iArray[4]=new Array();
      iArray[4][0]="<%=bundle.getString("M0000250961")%>";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=100;            			//列最大�??

      iArray[4][3]=0;              			//是否允许输入,1表示允许�?0表示不允�? 
      
      iArray[5]=new Array();
      iArray[5][0]="<%=bundle.getString("M0000045938")%>";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大�??

      iArray[5][3]=0;              			//是否允许输入,1表示允许�?0表示不允�? 

      CheckQueryDataGrid = new MulLineEnter( "fm" , "CheckQueryDataGrid" ); 
      //这些属�?�必须在loadMulLine�?

      CheckQueryDataGrid.mulLineCount = 5;   
      CheckQueryDataGrid.displayTitle = 1;
      CheckQueryDataGrid.locked = 1;
      CheckQueryDataGrid.canSel = 1;
      CheckQueryDataGrid.canChk = 0;
      //FinDayTestGrid.hiddenPlus = 1;
      //FinDayTestGrid.hiddenSubtraction = 1;        
      CheckQueryDataGrid.loadMulLine(iArray);  

      //这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		myAlert(ex);
	}
}
</script>
