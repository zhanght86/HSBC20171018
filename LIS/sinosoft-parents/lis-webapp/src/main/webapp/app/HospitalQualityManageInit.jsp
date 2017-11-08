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
    //document.all('customername').value = '';
    document.all('HospitalCode').value = '';
    document.all('HospitalName').value = '';
    document.all('ManageCom').value = '';
    document.all('ManageComName').value = '';
    document.all('QualityFlag').value = '';
    document.all('QualityFlagName').value = '';
    document.all('QualityFlagType').value = '';
    document.all('QualityFlagTypeName').value = '';
    document.all('Reason').value = '';
}

function initForm() {
  try {
	//initAll();
	//initData();
	//initCustomerQualityGrid();
	initHospitalGrid();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initHospitalGrid()
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
      iArray[1][0]="医院代码";          		//列名
      iArray[1][1]="90px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="医院名称";          		//列名
      iArray[2][1]="80px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="所属机构";         			//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="品质状态";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="签约状态";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="原因";      	   		//列名
      iArray[6][1]="100px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
      
      
      iArray[7]=new Array();
      iArray[7][0]="录入人员";      	   		//列名
      iArray[7][1]="50px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
    
      iArray[8]=new Array();
      iArray[8][0]="原因类别";      	   		//列名
      iArray[8][1]="0px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;                   //是否允许输入,1表示允许，0表示不允许      

      iArray[9]=new Array();
      iArray[9][0]="录入日期";      	   		//列名
      iArray[9][1]="80px";            			//列宽
      iArray[9][2]=80;            			//列最大值
      iArray[9][3]=0;                   //是否允许输入,1表示允许，0表示不允许  
     
      HospitalGrid = new MulLineEnter( "document" , "HospitalGrid" ); 
      //这些属性必须在loadMulLine前
      HospitalGrid.mulLineCount = 5;   
      HospitalGrid.displayTitle = 1;
      HospitalGrid.hiddenPlus=1;
      HospitalGrid.hiddenSubtraction = 1;
      HospitalGrid.locked=0;
      HospitalGrid.canSel=1;
      HospitalGrid.selBoxEventFuncName="showHospitalDetail";
      HospitalGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


