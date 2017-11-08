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
  	
 
	  //查询集体险种单信息
	  esayQueryClick();
	  
  	
  }
  catch(re) {
    alert("NotProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
          iArray[1][0]="集体险种号";         		//列名
          iArray[1][1]="0px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="投保单位";         		//列名
          iArray[2][1]="120px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[3]=new Array();
          iArray[3][0]="险种编码";         		//列名
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=100;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
           
          iArray[4]=new Array();
          iArray[4][0]="投保人数";         		//列名
          iArray[4][1]="120px";            		//列宽
          iArray[4][2]=100;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="保额";         		//列名
          iArray[5][1]="120px";            		//列宽
          iArray[5][2]=200;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[6]=new Array();
          iArray[6][0]="保费";         		//列名
          iArray[6][1]="120px";            		//列宽
          iArray[6][2]=200;            			//列最大值
          iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[7]=new Array();
          iArray[7][0]="浮动费率";         		//列名
          iArray[7][1]="100px";            		//列宽
          iArray[7][2]=100;            			//列最大值
          iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
          
        
          
          ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 

          //这些属性必须在loadMulLine前
          ContGrid.mulLineCount = 5;   
          ContGrid.displayTitle = 1;
          ContGrid.locked = 1;
          ContGrid.canSel = 0;
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
                       

