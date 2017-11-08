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
    document.all('ManageCom').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('EmployDate').value = '';
    document.all('IDType').value = '';
    document.all('IDNumber').value = '';
    document.all('QualityFlag').value = '';
    document.all('UnusualType').value = '';
    document.all('Remark').value = '';
}

function initForm() {
  try {
	//initAll();
	//initData();
	initAgentQualityGrid();
	initAgentGrid();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAgentQualityGrid()
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
      iArray[1][0]="业务员工号-姓名";          		//列名
      iArray[1][1]="90px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="业务员工号";          		//列名
      iArray[2][1]="0px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=3;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="管理机构";         			//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="品质状态";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="原因";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="合同号";      	   		//列名
      iArray[6][1]="60px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;                   //是否允许输入,1表示允许，0表示不允许      


      iArray[7]=new Array();
      iArray[7][0]="录入员";      	   		//列名
      iArray[7][1]="60px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
            
      iArray[8]=new Array();
      iArray[8][0]="录入日期";      	   		//列名
      iArray[8][1]="60px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
     
     
      AgentQualityGrid = new MulLineEnter( "document" , "AgentQualityGrid" ); 
      //这些属性必须在loadMulLine前
      AgentQualityGrid.mulLineCount = 5;   
      AgentQualityGrid.displayTitle = 1;
      AgentQualityGrid.hiddenPlus=1;
      AgentQualityGrid.locked=0;
      AgentQualityGrid.canSel=0;
      AgentQualityGrid.hiddenSubtraction = 1;
      //AgentQualityGrid.selBoxEventFuncName="showAgentQuality";
      AgentQualityGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
function initAgentGrid()
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
      iArray[1][0]="业务员工号-姓名";          		//列名
      iArray[1][1]="90px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="业务员工号";          		//列名
      iArray[2][1]="0px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=3;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="管理机构";         			//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="品质状态";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="原因类别";      	   		//列名
      iArray[5][1]="60px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="原因";      	   		//列名
      iArray[6][1]="100px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
            
      iArray[7]=new Array();
      iArray[7][0]="姓名";      	   		//列名
      iArray[7][1]="0px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
      
      iArray[8]=new Array();
      iArray[8][0]="证件类型";      	   		//列名
      iArray[8][1]="0px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
      
      iArray[9]=new Array();
      iArray[9][0]="证件号码";      	   		//列名
      iArray[9][1]="0px";            			//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
    
     
      AgentGrid = new MulLineEnter( "document" , "AgentGrid" ); 
      //这些属性必须在loadMulLine前
      AgentGrid.mulLineCount = 5;   
      AgentGrid.displayTitle = 1;
      AgentGrid.hiddenPlus=1;
      AgentGrid.locked=0;
      AgentGrid.canSel=1;
      AgentGrid.hiddenSubtraction = 1;
      AgentGrid.selBoxEventFuncName="showAgentQuality";
      AgentGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


