<%@include file="../common/jsp/UsrCheck.jsp"%>

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
  	//初始化已承保保单multiline
  	initContGrid();
  	
  	//初始化保单险种信息
  	initPolGrid();
  	
  	//查询客户信息
  	queryPersonInfo();
	  
	  //查询已承保保单信息
	  queryCont();
	  
  	
  }
  catch(re) {
    alert("NotProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initPolGrid(){
    var iArray = new Array();
      
      try
      {
           iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="保单号";         		//列名
          iArray[1][1]="100px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="险种保单号";         		//列名
          iArray[2][1]="0px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

          iArray[3]=new Array();
          iArray[3][0]="承保日期";         		//列名
          iArray[3][1]="100px";            		//列宽
          iArray[3][2]=100;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="险种代码";         		//列名
          iArray[4][1]="80px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          //iArray[5]=new Array();
          //iArray[5][0]="险种名称";         		//列名
          //iArray[5][1]="180px";            		//列宽
          //iArray[5][2]=200;            			//列最大值
          //iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          //
          //iArray[6]=new Array();
          //iArray[6][0]="险种状态";         		//列名
          //iArray[6][1]="60px";            		//列宽
          //iArray[6][2]=100;            			//列最大值
          //iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="保额";         		//列名
          iArray[5][1]="100px";            		//列宽
          iArray[5][2]=100;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[6]=new Array();
          iArray[6][0]="保费";         		//列名
          iArray[6][1]="40px";            		//列宽
          iArray[6][2]=100;            			//列最大值
          iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[7]=new Array();
          iArray[7][0]="核保结论";         		//列名
          iArray[7][1]="60px";            		//列宽
          iArray[7][2]=100;            			//列最大值
          iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          //iArray[10]=new Array();
          //iArray[10][0]="加费评点";         		//列名
          //iArray[10][1]="60px";            		//列宽
          //iArray[10][2]=100;            			//列最大值
          //iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 

          //这些属性必须在loadMulLine前
          PolGrid.mulLineCount = 5;   
          PolGrid.displayTitle = 1;
          PolGrid.locked = 1;
          PolGrid.canSel = 1;
          PolGrid.hiddenPlus = 1;
          PolGrid.hiddenSubtraction = 1;
          PolGrid.selBoxEventFuncName = "";
          PolGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


function initContGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="保单号";         		//列名
          iArray[1][1]="180px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="投保人客户号";         		//列名
          iArray[2][1]="120px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许
           
          iArray[3]=new Array();
          iArray[3][0]="投保人姓名";         		//列名
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=100;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="保单生效日期";         		//列名
          iArray[4][1]="120px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="投保单号";         		//列名
          iArray[5][1]="180px";            		//列宽
          iArray[5][2]=200;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          //iArray[6]=new Array();
          //iArray[6][0]="问题件";         		//列名
          //iArray[6][1]="60px";            		//列宽
          //iArray[6][2]=100;            			//列最大值
          //iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          //
          //iArray[7]=new Array();
          //iArray[7][0]="体检件";         		//列名
          //iArray[7][1]="60px";            		//列宽
          //iArray[7][2]=100;            			//列最大值
          //iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          //
          //iArray[8]=new Array();
          //iArray[8][0]="健康告知";         		//列名
          //iArray[8][1]="60px";            		//列宽
          //iArray[8][2]=100;            			//列最大值
          //iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          //
          //iArray[9]=new Array();
          //iArray[9][0]="投保单号";         		//列名
          //iArray[9][1]="0px";            		//列宽
          //iArray[9][2]=100;            			//列最大值
          //iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

           
          
          ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 

          //这些属性必须在loadMulLine前
          ContGrid.mulLineCount = 5;   
          ContGrid.displayTitle = 1;
          ContGrid.locked = 1;
          ContGrid.canSel = 1;
          ContGrid.hiddenPlus = 1;
          ContGrid.hiddenSubtraction = 1;
          ContGrid.selBoxEventFuncName = "contInfoClick";
          ContGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //PolGrid.setRowColData(1,1,"asdf");
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
                       

