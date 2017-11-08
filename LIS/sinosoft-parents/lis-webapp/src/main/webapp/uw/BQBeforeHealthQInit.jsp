<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：BQBeforeHealthQInit.jsp
//程序功能：既往体检资料查询
//创建日期：2008-12-15 11:10:36
//创建人  ：zhangxing
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
  	
  	
  
  	//查询客户信息
  	queryPersonInfo();
	 
	  //查询已承保保单信息
	  queryCont();
	  
  	
  }
  catch(re) {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
          iArray[1][1]="150px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
           
          iArray[2]=new Array();
          iArray[2][0]="投保人客户号";         		//列名
          iArray[2][1]="120px";            		//列宽
          iArray[2][2]=200;            			//列最大值
          iArray[2][3]=3;                			//是否允许输入,1表示允许，0表示不允许
                   
          iArray[3]=new Array();
          iArray[3][0]="投保人姓名";         		//列名
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=200;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="管理机构";         		//列名
          iArray[4][1]="150px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="代理人编码";         		//列名
          iArray[5][1]="120px";            		//列宽
          iArray[5][2]=100;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[6]=new Array();
          iArray[6][0]="代理人姓名";         		//列名
          iArray[6][1]="120px";            		//列宽
          iArray[6][2]=100;            			//列最大值
          iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

                 
          
          ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 

          //这些属性必须在loadMulLine前
          ContGrid.mulLineCount = 5;   
          ContGrid.displayTitle = 1;
          ContGrid.locked = 1;
          ContGrid.canSel = 1;
          ContGrid.hiddenPlus = 1;
          ContGrid.hiddenSubtraction = 1;
          ContGrid.selBoxEventFuncName = "healthInfoClick";
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
                       

