 <%
//程序名称：TempFeeInit.jsp
//程序功能：
//创建日期：2005-06-27 08:49:52
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>  
<script language="JavaScript" charset="GBK">

function initForm()
{
  try
  {  
  	initTempToGrid();
  	initTempClassToGrid();
  	
  	QryTempFee();
  	
  	QryCustomer(); 
  	
  }
  catch(re)
  {
    alert("在UWTempFeeQryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{ 
  try
  { 
  }
  catch(ex)
  {
    alert("在UWTempFeeQryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}


////初始化保存暂交费分类数据的Grid
function initTempToGrid()
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
      iArray[1][0]="收据号/划款协议书号";          		//列名
      iArray[1][1]="80px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="收据类型";          		//列名
      iArray[2][1]="40px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;
      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";          		//列名
      iArray[3][1]="60px";      	      		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="交费金额";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;//7;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[4][23]="0";
      
      iArray[5]=new Array();
      iArray[5][0]="到帐日期";          		//列名
      iArray[5][1]="60px";      	      		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;//8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="管理机构";      	   		//列名
      iArray[6][1]="0px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
			
			iArray[7]=new Array();
			iArray[7][0]="币种";
			iArray[7][1]="60px";
			iArray[7][2]=100;
			iArray[7][3]=2;
			iArray[7][4]="currency";
			
      TempToGrid = new MulLineEnter( "document" , "TempToGrid" ); 
      //这些属性必须在loadMulLine前
      TempToGrid.mulLineCount = 5;   
      TempToGrid.displayTitle = 1;
      TempToGrid.locked=1;
      TempToGrid.hiddenPlus=1;
      TempToGrid.hiddenSubtraction=1;      
      TempToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
}

//初始化保存暂交费分类数据的Grid
function initTempClassToGrid()
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
      iArray[1][0]="收据号/划款协议书号";          		//列名
      iArray[1][1]="100px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="交费方式";          		//列名
      iArray[2][1]="60px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费金额";          		//列名
      iArray[3][1]="80px";      	      		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;//7;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="到帐日期";          		//列名
      iArray[4][1]="0px";      	      		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="票据号码";          		//列名
      iArray[5][1]="100px";      	      		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="支票日期";          		//列名
      iArray[6][1]="80px";      	      		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;//8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="开户银行";      	   		//列名
      iArray[7][1]="60px";            			//列宽
      iArray[7][2]=40;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="银行账号";      	   		//列名
      iArray[8][1]="100px";            			//列宽
      iArray[8][2]=40;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
              
      iArray[9]=new Array();
      iArray[9][0]="户名";      	   		//列名
      iArray[9][1]="60px";            			//列宽
      iArray[9][2]=10;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="收款银行账号";      	   		//列名
      iArray[10][1]="140px";            		//列宽
      iArray[10][2]=40;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
 			iArray[11]=new Array();
			iArray[11][0]="币种";
			iArray[11][1]="60px";
			iArray[11][2]=100;
			iArray[11][3]=2;
			iArray[11][4]="currency";
 
      TempClassToGrid = new MulLineEnter( "document" , "TempClassToGrid" ); 
      //这些属性必须在loadMulLine前
      TempClassToGrid.mulLineCount = 5;   
      TempClassToGrid.displayTitle = 1;
      TempClassToGrid.locked=1;
      TempClassToGrid.hiddenPlus=1;
      TempClassToGrid.hiddenSubtraction=1;
      TempClassToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
	
}
</script>
