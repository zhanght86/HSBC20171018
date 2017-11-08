<%
//程序名称：GrpPolQueryInit.jsp
//程序功能：
//创建日期：2003-03-14 
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initForm()
{
  try
  {
    
    initLcInsureAccGrid(); 
    initLCInsureAccClassGrid();
  }
  catch(re)
  {
    alert("GpsaAccQueryinit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initLcInsureAccGrid()
  {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保险帐户代码";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="保险帐户名称";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[3]=new Array();
      iArray[3][0]="帐户成立日";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="帐户结算日";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="帐户余额";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
	  iArray[6][0]="币种";         		
	  iArray[6][1]="50px";            		 
	  iArray[6][2]=60;            			
	  iArray[6][3]=2;              		
	  iArray[6][4]="Currency";              	  
	  iArray[6][9]="币种|code:Currency";
      
      LCInsureAccGrid = new MulLineEnter( "fm" , "LCInsureAccGrid" ); 
      //这些属性必须在loadMulLine前
      LCInsureAccGrid.mulLineCount = 1;   
      LCInsureAccGrid.displayTitle = 1;
      LCInsureAccGrid.locked = 1;
      LCInsureAccGrid.canSel = 1;
      LCInsureAccGrid.hiddenPlus = 1;
      LCInsureAccGrid.hiddenSubtraction = 1;
      LCInsureAccGrid.loadMulLine(iArray);  
 
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLCInsureAccClassGrid(){

var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保险帐户号码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="交费计划代码";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[3]=new Array();
      iArray[3][0]="交费计划名称";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="对应其他号码";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="其他号码类型";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="帐户成立日";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="帐户结算日";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="帐户归属属性";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="帐户余额";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[10]=new Array();
	  iArray[10][0]="币种";         		
	  iArray[10][1]="50px";            		 
	  iArray[10][2]=60;            			
	  iArray[10][3]=2;              		
	  iArray[10][4]="Currency";              	  
	  iArray[10][9]="币种|code:Currency";
      
      LCInsureAccClassGrid = new MulLineEnter( "fm" , "LCInsureAccClassGrid" ); 
      //这些属性必须在loadMulLine前
      LCInsureAccClassGrid.mulLineCount = 1;   
      LCInsureAccClassGrid.displayTitle = 1;
      LCInsureAccClassGrid.locked = 1;
      LCInsureAccClassGrid.canSel = 1;
      LCInsureAccClassGrid.hiddenPlus = 1;
      LCInsureAccClassGrid.hiddenSubtraction = 1;
      LCInsureAccClassGrid.loadMulLine(iArray);  
 
      }
      catch(ex)
      {
        alert(ex);
      }


}

</script>
