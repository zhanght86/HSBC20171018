<%
//程序名称：AbnormityErrAndRecordErr.jsp
//程序功能：异常件错误原因查询以及记录差错功能
//创建日期：2008-06-26
//创建人  ：ln
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
	initQuery();
	initErrQuery();
  }
  catch(re) {
    alert("AbnormityErrAndRecordErrInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var PolGrid;
// 财务单信息列表的初始化
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
      iArray[1][0]="单证印刷号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[2]=new Array();
      iArray[2][0]="错误数";         		//列名
      iArray[2][1]="30px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0; 
      
      iArray[3]=new Array();
      iArray[3][0]="错误内容";         		//列名
      iArray[3][1]="220px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0; 
      
      iArray[4]=new Array();
      iArray[4][0]="外包方名称";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0; 

      
      iArray[5]=new Array();
      iArray[5][0]="入机日期";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="入机时间";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      PolGrid = new MulLineEnter( "fm1" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
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
      iArray[1][0]="差错类别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="errType";                     //新版投保单对应告知
      iArray[1][5]="1";
      iArray[1][6]="0";
      iArray[1][9]="差错类别|len<=1";

      iArray[2]=new Array();             
      iArray[2][0]="差错编码";         	
      iArray[2][1]="30px";            	
      iArray[2][2]=60;            		
      iArray[2][3]=2;    
      iArray[2][4]="errcontent";         
      iArray[2][5]="2|3";                
      iArray[2][6]="0|1";  
      iArray[2][15]="ComCode";                
      iArray[2][17]="1";             //依赖第1列的值，即传入的查询条件为 ComCode ='1'           		


      iArray[3]=new Array();
      iArray[3][0]="差错内容";         		//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=180;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
       

    ErrGrid = new MulLineEnter( "fm" , "ErrGrid" );
    ErrGrid.mulLineCount = 0;
    ErrGrid.displayTitle = 1;
    ErrGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("在此出错");
  }
}

</script>
