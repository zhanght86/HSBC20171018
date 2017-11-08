<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：NotProposalQueryInit.jsp
//程序功能：未承保查询
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
          iArray[1][0]="投保单号";         		//列名
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
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=100;            			//列最大值
          iArray[3][3]=8;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="险种代码";         		//列名
          iArray[4][1]="80px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="险种名称";         		//列名
          iArray[5][1]="180px";            		//列宽
          iArray[5][2]=200;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[6]=new Array();
          iArray[6][0]="险种状态";         		//列名
          iArray[6][1]="60px";            		//列宽
          iArray[6][2]=100;            			//列最大值
          iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[7]=new Array();
          iArray[7][0]="保额";         		//列名
          iArray[7][1]="100px";            		//列宽
          iArray[7][2]=100;            			//列最大值
          iArray[7][3]=7;              			//是否允许输入,1表示允许，0表示不允许
          iArray[7][23]="0";

          iArray[8]=new Array();
          iArray[8][0]="份数";         		//列名
          iArray[8][1]="40px";            		//列宽
          iArray[8][2]=100;            			//列最大值
          iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[9]=new Array();
          iArray[9][0]="核保结论";         		//列名
          iArray[9][1]="60px";            		//列宽
          iArray[9][2]=100;            			//列最大值
          iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[10]=new Array();
          iArray[10][0]="加费评点";         		//列名
          iArray[10][1]="100px";            		//列宽
          iArray[10][2]=100;            			//列最大值
          iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[11]=new Array();
					iArray[11][0]="币种";
					iArray[11][1]="60px";
					iArray[11][2]=100;
					iArray[11][3]=2;
					iArray[11][4]="currency";
          
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
          iArray[1][1]="0px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="投保单号";         		//列名
          iArray[2][1]="120px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[3]=new Array();
          iArray[3][0]="投保人客户号";         		//列名
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=100;            			//列最大值
          iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许
           
          iArray[4]=new Array();
          iArray[4][0]="投保人姓名";         		//列名
          iArray[4][1]="120px";            		//列宽
          iArray[4][2]=100;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="被保人姓名";         		//列名
          iArray[5][1]="120px";            		//列宽
          iArray[5][2]=100;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
          //tongmeng 2007-12-03 add 
          iArray[6]=new Array();
          iArray[6][0]="核保日期";         		//列名
          iArray[6][1]="120px";            		//列宽
          iArray[6][2]=200;            			//列最大值
          iArray[6][3]=8;              			//是否允许输入,1表示允许，0表示不允许


          iArray[7]=new Array();
          iArray[7][0]="保单生效日期";         		//列名
          iArray[7][1]="120px";            		//列宽
          iArray[7][2]=200;            			//列最大值
          iArray[7][3]=8;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[8]=new Array();
          iArray[8][0]="逾期状态";         		//列名
          iArray[8][1]="80px";            		//列宽
          iArray[8][2]=200;            			//列最大值
          iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[9]=new Array();
          iArray[9][0]="核保通知书";         		//列名
          iArray[9][1]="100px";            		//列宽
          iArray[9][2]=100;            			//列最大值
          iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[10]=new Array();
          iArray[10][0]="体检件";         		//列名
          iArray[10][1]="60px";            		//列宽
          iArray[10][2]=100;            			//列最大值
          iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[11]=new Array();
          iArray[11][0]="升调件";         		//列名
          iArray[11][1]="60px";            		//列宽
          iArray[11][2]=100;            			//列最大值
          iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[12]=new Array();
          iArray[12][0]="健康告知";         		//列名
          iArray[12][1]="60px";            		//列宽
          iArray[12][2]=100;            			//列最大值
          iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[13]=new Array();
          iArray[13][0]="印刷号";         		//列名
          iArray[13][1]="0px";            		//列宽
          iArray[13][2]=100;            			//列最大值
          iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[14]=new Array();
          iArray[14][0]="保单状态";         		//列名
          iArray[14][1]="0px";            		//列宽
          iArray[14][2]=100;            			//列最大值
          iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[15]=new Array();
          iArray[15][0]="保单类型";         		//列名
          iArray[15][1]="80px";            		//列宽
          iArray[15][2]=100;            			//列最大值
          iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
          
          iArray[16]=new Array();
          iArray[16][0]="保单类型编码";         		//列名
          iArray[16][1]="0px";            		//列宽
          iArray[16][2]=100;            			//列最大值
          iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许

          iArray[17]=new Array();
          iArray[17][0]="核保结论";         		//列名
          iArray[17][1]="100px";            		//列宽
          iArray[17][2]=100;            			//列最大值
          iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
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
                       

