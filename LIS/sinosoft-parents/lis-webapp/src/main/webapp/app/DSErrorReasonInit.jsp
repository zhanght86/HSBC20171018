<%
//程序名称：AbnormityErrAndRecordErr.jsp
//程序功能：异常件错误原因查询以及记录差错功能
//创建日期：2007-08-01 14:32:57
//创建人  ：张征
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox(){   
}

// 下拉框的初始化
function initSelBox(){  
}                                        

function initForm()
{
  try 
  {
    initInpBox();
    initSelBox();   
	initPolGrid();  
	initErrGrid();
	//alert(22);
	initQuery(); //alert(33);
	initErrQuery(); //alert(34);
  }
  catch(re) {
    alert("AbnormityErrAndRecordErrInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var PolGrid;
// 保单信息列表的初始化
function initPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="录入项目";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[2]=new Array();
      iArray[2][0]="录入内容";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0; 
      
      iArray[3]=new Array();
      iArray[3][0]="操作员工号";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0; 
      
      iArray[4]=new Array();
      iArray[4][0]="记录差错";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=90;            			//列最大值
      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][10]="ForYN";
      iArray[4][11]="0|^0|是 ^1|否"; 

	  iArray[5]=new Array();
      iArray[5][0]="错误数";         		    //列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="错误流水号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=90;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      //PolGrid.canChk = 0;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;     
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


//差错记录
function initErrGrid()
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
      iArray[1][0]="录入项目";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();             
      iArray[2][0]="录入内容";         	
      iArray[2][1]="100px";            	
      iArray[2][2]=60;            		
      iArray[2][3]=0;    

      iArray[3]=new Array();
      iArray[3][0]="操作员工号";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=180;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
       
      iArray[4]=new Array();
      iArray[4][0]="操作员姓名";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0; 

    ErrGrid = new MulLineEnter( "fm" , "ErrGrid" );
    ErrGrid.mulLineCount = 0;
    ErrGrid.displayTitle = 1;
    ErrGrid.hiddenPlus = 1;
    ErrGrid.hiddenSubtraction = 1; 
    ErrGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("初始化initErrGrid出错");
  }
}

</script>
