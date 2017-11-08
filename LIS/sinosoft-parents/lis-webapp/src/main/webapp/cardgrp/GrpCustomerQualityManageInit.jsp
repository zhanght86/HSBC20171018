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
    //fm.all('customername').value = '';
    fm.all('CustomerNo').value = '';
    fm.all('Name').value = '';
    fm.all('Birthday').value = '';
    fm.all('Sex').value = '';
    fm.all('IDType').value = '';
    fm.all('IDNumber').value = '';
    fm.all('NameSel').value = '';
    fm.all('CustomerNoSel').value = '';
    fm.all('CQualityFlag').value = '';
    fm.all('GrpCQualityFlagName').value = '';
    fm.all('Reason').value = '';
    fm.all('GrpNature').value = '';
}

function initForm() {
  try {
	initAll();
	//initData();
	initCustomerQualityGrid();
	initCustomerGrid();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCustomerQualityGrid()
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
      iArray[1][0]="客户姓名";          		//列名
      iArray[1][1]="90px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="客户号";          		//列名
      iArray[2][1]="80px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="品质状态";         			//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="原因";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="合同号";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[6]=new Array();
      iArray[6][0]="录入员";      	   		//列名
      iArray[6][1]="100px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="录入日期";      	   		//列名
      iArray[7][1]="60px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
            
     
     
      CustomerQualityGrid = new MulLineEnter( "fm" , "CustomerQualityGrid" ); 
      //这些属性必须在loadMulLine前
      CustomerQualityGrid.mulLineCount = 3;   
      CustomerQualityGrid.displayTitle = 1;
      CustomerQualityGrid.hiddenPlus=1;
      CustomerQualityGrid.hiddenSubtraction = 1;
      CustomerQualityGrid.locked=0;
      CustomerQualityGrid.canSel=0;
      //CustomerQualityGrid.selBoxEventFuncName="showCustomerQuality";
      CustomerQualityGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initCustomerGrid()
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
      iArray[1][0]="客户名称";          		//列名
      iArray[1][1]="90px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="客户号码";          		//列名
      iArray[2][1]="80px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="品质状态";         			//列名
      iArray[3][1]="50px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="原因";      	   		//列名
      iArray[4][1]="250px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="录入员";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="录入日期";      	   		//列名
      iArray[6][1]="100px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
            
     
     
      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" ); 
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 1;   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.hiddenPlus=1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.locked=0;
      CustomerGrid.canSel=1;
      CustomerGrid.selBoxEventFuncName="showCustomerQuality";
      CustomerGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


