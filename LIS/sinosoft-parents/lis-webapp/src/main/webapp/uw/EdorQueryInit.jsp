<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorQueryInit.jsp
//程序功能：保全查询
//创建日期：2005-6-10 14:46
//创建人  ：guomy 
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
  	//初始化保全信息
  	initEdorGrid();
  	
  	//初始化保全项目信息
  	initEdorItemGrid();

    
  	//查询客户信息
  	//queryPersonInfo();

	     
	  //查询既往保全信息
	  queryEdor();
	  
  	
  }
  catch(re) { 
    alert("EdorQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initEdorItemGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="批单号";         		//列名
          iArray[1][1]="100px";            		//列宽
          iArray[1][2]=100;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="保单号";         		//列名
          iArray[2][1]="100px";            		//列宽 
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          iArray[3]=new Array();
          iArray[3][0]="申请保全项目";         		//列名
          iArray[3][1]="120px";            		//列宽
          iArray[3][2]=100;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="变更内容";         		//列名
          iArray[4][1]="180px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[5]=new Array();
          iArray[5][0]="变更申请时间";         		//列名
          iArray[5][1]="60px";            		//列宽
          iArray[5][2]=200;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[6]=new Array();
          iArray[6][0]="变更生效日期";         		//列名
          iArray[6][1]="60px";            		//列宽
          iArray[6][2]=100;            			//列最大值
          iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[7]=new Array();
          iArray[7][0]="保全项目核保结论";         		//列名
          iArray[7][1]="0px";            		//列宽
          iArray[7][2]=100;            			//列最大值
          iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
          
          
          EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" ); 

          //这些属性必须在loadMulLine前
          EdorItemGrid.mulLineCount = 5;   
          EdorItemGrid.displayTitle = 1;
          EdorItemGrid.locked = 1;
          EdorItemGrid.canSel = 1; 
          EdorItemGrid.hiddenPlus = 1;
          EdorItemGrid.hiddenSubtraction = 1; 
          EdorItemGrid.selBoxEventFuncName = "";
          EdorItemGrid.loadMulLine(iArray);  
          
          //这些操作必须在loadMulLine后面
          //EdorItemGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


function initEdorGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";            		//列宽
          iArray[0][2]=10;            			//列最大值
          iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[1]=new Array();
          iArray[1][0]="批单号";         		//列名
          iArray[1][1]="100px";            		//列宽 
          iArray[1][2]=90;            			//列最大值
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[2]=new Array();
          iArray[2][0]="投保人姓名";         		//列名 
          iArray[2][1]="120px";            		//列宽
          iArray[2][2]=100;            			//列最大值
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[3]=new Array();
          iArray[3][0]="变更申请日期";         		//列名
          iArray[3][1]="70px";            		//列宽
          iArray[3][2]=200;            			//列最大值
          iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
          
          iArray[4]=new Array();
          iArray[4][0]="变更生效日期";         		//列名
          iArray[4][1]="70px";            		//列宽
          iArray[4][2]=200;            			//列最大值
          iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
          
          iArray[5]=new Array();
          iArray[5][0]="核保结论";         		//列名
          iArray[5][1]="250px";            		//列宽
          iArray[5][2]=250;            			//列最大值
          iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

          
          
          EdorGrid = new MulLineEnter( "fm" , "EdorGrid" ); 

          //这些属性必须在loadMulLine前
          EdorGrid.mulLineCount = 5;   
          EdorGrid.displayTitle = 1;
          EdorGrid.locked = 1;
          EdorGrid.canSel = 1;
          EdorGrid.hiddenPlus = 1;
          EdorGrid.hiddenSubtraction = 1;
          EdorGrid.selBoxEventFuncName = "getEdorItemInfo";
          EdorGrid.loadMulLine(iArray);   
          
          //这些操作必须在loadMulLine后面
          //EdorItemGrid.setRowColData(1,1,"asdf");
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
                       

