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

var GrpPolGrid;
var SingleInfoGrid;
                                   
function initForm()
{
  try
  {
    initGrpPolGrid();
    initSingleInfoGrid();
    document.all("divSingleInfo").style.display= "none";
   
    fm.RiskCode.CodeData=getRisk(); 
    
  }
  catch(re)
  {
    alert("GrpPolsingleQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initGrpPolGrid(){                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="个人保单号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="个人客户号";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[3]=new Array();
      iArray[3][0]="姓名";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="生效日期";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=80;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			iArray[6]=new Array();
      iArray[6][0]="终止日期";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=80;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
       
      iArray[7]=new Array();
      iArray[7][0]="出生日期";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=80;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="性别";         		//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=80;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="保费";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="保额";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[11]=new Array();
      iArray[11][0]="保单状态";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=0;
      
      iArray[13]=new Array();
	  iArray[13][0]="币种";         		
	  iArray[13][1]="50px";            		 
	  iArray[13][2]=60;            			
	  iArray[13][3]=2;              		
	  iArray[13][4]="Currency";              	  
	  iArray[13][9]="币种|code:Currency";
      
      //显示用contno 内部查询用polno
      //add by jiaqiangli 2009-10-30
      iArray[12]=new Array();
      iArray[12][0]="polno";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=0;  
        
       
        
      GrpPolGrid = new MulLineEnter( "document" , "GrpPolGrid" ); 
      //这些属性必须在loadMulLine前
      GrpPolGrid.mulLineCount = 5;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.locked = 1;
      GrpPolGrid.canSel = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.loadMulLine(iArray);
      GrpPolGrid.selBoxEventFuncName = "querySingleInfo"; 
      //这些操作必须在loadMulLine后面
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// 客户重要信息列表的初始化
function initSingleInfoGrid(){                               
    var iArray2 = new Array(); 
      try
      {
      iArray2[0]=new Array();
      iArray2[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray2[0][1]="30px";            		//列宽
      iArray2[0][2]=10;            			//列最大值
      iArray2[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray2[1]=new Array();
      iArray2[1][0]="个人客户号";    
      iArray2[1][1]="100px";            		
      iArray2[1][2]=100;            			
      iArray2[1][3]=0;              			

      iArray2[2]=new Array();
      iArray2[2][0]="姓名";         		
      iArray2[2][1]="80px";            		
      iArray2[2][2]=80;            			
      iArray2[2][3]=0;              			

      
      iArray2[3]=new Array();
      iArray2[3][0]="性别";         		
      iArray2[3][1]="40px";            		
      iArray2[3][2]=200;            			
      iArray2[3][3]=0;              			

      iArray2[4]=new Array();
      iArray2[4][0]="出生日期";         		
      iArray2[4][1]="80px";            		
      iArray2[4][2]=100;            			
      iArray2[4][3]=0;              			
      
      iArray2[5]=new Array();
      iArray2[5][0]="证件类型";         		
      iArray2[5][1]="50px";            		
      iArray2[5][2]=80;            			
      iArray2[5][3]=0;              			
      
      iArray2[6]=new Array();
      iArray2[6][0]="证件号码";         		
      iArray2[6][1]="120px";            	
      iArray2[6][2]=80;            		
      iArray2[6][3]=0;              			

      iArray2[7]=new Array();
      iArray2[7][0]="职业代码";         		
      iArray2[7][1]="80px";            		
      iArray2[7][2]=200;            			
      iArray2[7][3]=0;              			

      iArray2[8]=new Array();
      iArray2[8][0]="职业类别";         		
      iArray2[8][1]="40px";            		
      iArray2[8][2]=200;            			
      iArray2[8][3]=0;              			
      
      
      iArray2[9]=new Array();
      iArray2[9][0]="户籍";         		
      iArray2[9][1]="80px";            
      iArray2[9][2]=200;            			
      iArray2[9][3]=0; 
 
      iArray2[10]=new Array();
      iArray2[10][0]="婚姻状况";       
      iArray2[10][1]="60px";            		
      iArray2[10][2]=200;            			
      iArray2[10][3]=0; 
        
      SingleInfoGrid = new MulLineEnter( "document" , "SingleInfoGrid" ); 
      //这些属性必须在loadMulLine前
      SingleInfoGrid.mulLineCount = 5;   
      SingleInfoGrid.displayTitle = 1;
      SingleInfoGrid.locked = 1;
      SingleInfoGrid.canSel = 0;
      SingleInfoGrid.hiddenPlus = 1;
      SingleInfoGrid.hiddenSubtraction = 1;
      SingleInfoGrid.loadMulLine(iArray2);  
      //这些操作必须在loadMulLine后面
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
