<%
//程序名称：TempFeeQueryByChqNoInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>                          
<%
   String ManageCom = tGI.ComCode;
%>
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 查询条件
    document.all('TempFeeStatus1').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('Operator').value = '';
    document.all('Operator').value = '';
    //document.all('ManageCom').value = '<%=ManageCom%>';
    //document.all('PolicyCom').value = '<%=ManageCom%>';  
    document.all('ManageCom').value = '';
    document.all('PolicyCom').value = '';
  }
  catch(ex)
  {
    alert("在TempFeeQueryByChqNoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在TempFeeQueryByChqNoInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initTempQueryByChqNoGrid();
  }
  catch(re)
  {
    alert("在TempFeeQueryByChqNoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

 var TempQueryGrid ;
 
// 保单信息列表的初始化
function initTempQueryByChqNoGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            		//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="交费金额";          		//列名
      iArray[1][1]="80px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      
      
      iArray[2]=new Array();
      iArray[2][0]="暂交费收据号";   		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="对应号码";         	//列名
      iArray[3][1]="160px";              	//列宽
      iArray[3][2]=200;            	        //列最大值
      iArray[3][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="交费机构";		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            		//列最大值
      iArray[4][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="管理机构";		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=60;            		//列最大值
      iArray[5][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="投保人名称";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=200;            		//列最大值
      iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="交费方式";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=200;            	        //列最大值
      iArray[7][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="票据号";         		//列名
      iArray[8][1]="120px";            		//列宽
      iArray[8][2]=200;            	        //列最大值
      iArray[8][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="支票日期";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=200;            	        //列最大值
      iArray[9][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="交费日期";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=200;            	        //列最大值
      iArray[10][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="到账日期";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=200;            	        //列最大值
      iArray[11][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="银行编码";         	//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=200;            	        //列最大值
      iArray[12][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="银行账号";         	//列名
      iArray[13][1]="80px";              	//列宽
      iArray[13][2]=200;            	        //列最大值
      iArray[13][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="银行账户名";         	//列名
      iArray[14][1]="100px";              	//列宽
      iArray[14][2]=200;            	        //列最大值
      iArray[14][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
      
      iArray[15]=new Array();
      iArray[15][0]="收款银行";         	//列名
      iArray[15][1]="100px";              	//列宽
      iArray[15][2]=200;            	        //列最大值
      iArray[15][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      
      TempQueryByChqNoGrid = new MulLineEnter( "fm" , "TempQueryByChqNoGrid" ); 
      //这些属性必须在loadMulLine前
      TempQueryByChqNoGrid.mulLineCount = 0;   
      TempQueryByChqNoGrid.displayTitle = 1;
      TempQueryByChqNoGrid.hiddenPlus = 1;
      TempQueryByChqNoGrid.hiddenSubtraction = 1;
//      TempQueryByChqNoGrid.locked = 1;
//      TempQueryByChqNoGrid.canSel = 1;
      TempQueryByChqNoGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
