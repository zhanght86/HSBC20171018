<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：AmntAccumulateInit.jsp
//程序功能：保额累计
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //添加页面控件的初始化。
%>     
<script language="JavaScript">

function initForm()
{
  try 
  {
  	//初始化保额累计multiline
  	initAmntAccuGrid();
  	
  	//初始化保额累计明细multiline
  	initAmntAccuDetailGrid();
  	
  	//查询客户信息
  	queryPersonInfo();
	
	  //查询保额累计信息
	  queryAmntAccu();
	  
	  //查询保额累计明细信息
	  queryAmntAccuDetail();
	  
  	
  }
  catch(re) {
    alert("AmntAccumulateInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAmntAccuGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="险种类别";         		//列名
          iArray[1][1]="80px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="承保保额累计（元）";         		//列名
          iArray[2][1]="120px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[3]=new Array();
          iArray[3][0]="承保保额累计（份数）";         		//列名
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=200;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="纯风险保额累计（元）";         		//列名
          iArray[4][1]="120px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="纯风险保额累计（份数）";         		//列名
          iArray[5][1]="0px";            		//列宽
          iArray[5][2]=100;            			//列最大值
          iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
          
          
          
          AmntAccuGrid = new MulLineEnter( "fm" , "AmntAccuGrid" ); 

          //这些属性必须在loadMulLine前
          AmntAccuGrid.mulLineCount = 5;   
          AmntAccuGrid.displayTitle = 1;
          AmntAccuGrid.locked = 1;
          AmntAccuGrid.canSel = 0;
          AmntAccuGrid.hiddenPlus = 1;
          AmntAccuGrid.hiddenSubtraction = 1;
          AmntAccuGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //AmntAccuGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}

function initAmntAccuDetailGrid(){
	
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种代码";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         		//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="承保保额累计（元）";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="承保保额累计（份数）";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="纯风险保额累计（元）";         		//列名
      iArray[5][1]="160px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="纯风险保额累计（份数）";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      
      AmntAccuDetailGrid = new MulLineEnter( "fm" , "AmntAccuDetailGrid" ); 
      //这些属性必须在loadMulLine前
      AmntAccuDetailGrid.mulLineCount = 5;   
      AmntAccuDetailGrid.displayTitle = 1;
      AmntAccuDetailGrid.locked = 1;
      AmntAccuDetailGrid.canSel = 0;
      AmntAccuDetailGrid.hiddenPlus = 1;
      AmntAccuDetailGrid.hiddenSubtraction = 1;
      AmntAccuDetailGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //AmntAccuGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


// 输入框的初始化
function initInpBox()
{
}

// 下拉框的初始化
function initSelBox(){  
}                                        


</script>
                       

